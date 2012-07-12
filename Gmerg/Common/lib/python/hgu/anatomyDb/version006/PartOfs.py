#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level fast access to records in the ANAD_PART_OF
table.  This module abstracts much of the low-level, record-at-a-time
direct database access in the AnadPartOfDb module.

This reads in all ANAD_PART_OF records, and then provides in-memory access to
them from that point on.
"""

from hgu import Util

from hgu.db import DbTable

# Low level access
from hgu.anatomyDb.version006 import AnadPartOfDb  # low-level access

# High level access
from hgu.anatomyDb.version006 import Nodes
from hgu.anatomyDb.version006 import PartOfPerspectives
from hgu.anatomyDb.version006 import Relationships
from hgu.anatomyDb.version006 import Stages



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Define initialisation methods.
__READ_TABLE   = "READ TABLE"
__DERIVE_TABLE = "DERIVE TABLE"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of part of records, indexed by APO OID
_partOfByOid      = None

# List of part of records, indexed by sequence, and in sequence order.
_partOfBySequence = None

# List of part of records, in reverse sequence order.
_partOfInReverseSequence = None

# Dictionary of primary path part of records, indexed by node OID.
_primaryPathByNodeOid = None

# Dictionary of part of records, indexed by APO OID.  Each entry is a
# list of APO records that are direct children of the given APO record.
_byParentOid = None

# DbTable object for the table this module provides high level access to.
_table = None



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _initialiseGlobals():
    """
    Initialise this module's global variables.
    """
    global _partOfByOid, _partOfBySequence, _partOfInReverseSequence
    global _primaryPathByNodeOid, _byParentOid, _table

    _table = DbTable.getByName(AnadPartOfDb.TABLE_NAME)
    _partOfByOid      = {}
    _partOfBySequence = []
    _partOfInReverseSequence = []
    _primaryPathByNodeOid = {}
    _byParentOid = {}

    return


def _addToKnowledge(anatApo, deferReverse = False ):
    """
    Add the part of record to this module's internal knowledge.

    If deferReverse is True then do not update the
    _partOfInReverseSequence global variable.
    """
    _partOfByOid[anatApo.getOid()] = anatApo
    _partOfBySequence.append(anatApo)
    parentOid = anatApo.getParentApoOid()
    if parentOid != None:
        if parentOid not in _byParentOid:
            _byParentOid[parentOid] = []
        _byParentOid[parentOid].append(anatApo)

    if anatApo.isPrimaryPath():
        nodeOid = anatApo.getNodeOid()
        #if nodeOid in _primaryPathByNodeOid:
        #    Util.fatalError([
        #        "Node OID " + str(nodeOid) + " occurs more than once " +
        #        "in " + AnadPartOfDb.TABLE_NAME])
        _primaryPathByNodeOid[nodeOid] = anatApo

    if not deferReverse:
        global _partOfInReverseSequence
        _partOfInReverseSequence = _partOfBySequence[:]
        _partOfInReverseSequence.reverse()

    return



def _initialise(initMethod):
    """
    Initialise this table, based on initMethod:
      __READ_TABLE read already existing records from the table
      __#DERIVE_TABLE regenerate the contents of the table.
    """
    global _partOfInReverseSequence
    _initialiseGlobals()

    #print "Method = " + initMethod

    if initMethod == __READ_TABLE:
        # Read in everything, ignoring perspective for time being.
        for partOf in AnadPartOfDb.SequenceIterator():
            _addToKnowledge(partOf, deferReverse = True)

    elif initMethod == __DERIVE_TABLE:
        # Walk the tree depth first, left to right
        deleteAll()
        _processNode(node = Nodes.getRoot(), parentApo = None,
                     rank = 0, depth = 0, isPrimaryPath = True)
    else:
        Util.fatalError([
            "Unrecognised initMethod: " + initMethod])

    #print "Method = " + initMethod + "; Going through Existing ANAD_PART_OF rows now!"

    # Verify that sequence starts at 0, and is dense.  Otherwise
    # this code will fail.
    #for index, partOf in enumerate(_partOfBySequence):
        #print "Method = " + initMethod + "; Idx = " + str(index) + "; Seq = " + str(partOf.getSequence())
        #if index != partOf.getSequence():
        #    Util.fatalError([
        #        AnadPartOfDb.TABLE_NAME + " sequence does not start at 0, " +
        #        "and/or is not dense.",
        #        "Record has index " + str(index) +
        #        " but has sequence " + str(partOf.getSequence()) + ".",
        #        "Code requires 0-relative, dense " + AnadPartOfDb.TABLE_NAME +
        #        " sequences."])

    _partOfInReverseSequence = _partOfBySequence[:]
    _partOfInReverseSequence.reverse()

    return None


def _processNode(node, parentApo, rank, depth, isPrimaryPath):
    """
    Generate part of records for an anatomy node, and all its children.
    """

    if Util.debugging():
        Util.debugMessage([
            "In _processnode.",
            "Node: " + node.getPublicId() + " '" +
            node.getComponentName() + "'",
            "Rank: " + str(rank) + "  Depth: " + str(depth)])

    # Generate record for this node.
    anatApo = AnadPartOfDb.AnadPartOfDbRecord()
    anatApo.setOid(rank)
    anatApo.setSpecies(node.getSpecies())
    anatApo.setNodeOid(node.getOid())
    anatApo.setSequence(rank)
    anatApo.setDepth(depth)
    anatApo.setIsPrimaryPath(isPrimaryPath)

    # Gather stage range of anatomy node
    nodeStartStage, nodeEndStage = Nodes.getStageWindowForNodeOid(node.getOid())
    StartStage = Stages.getByOid(nodeStartStage.getOid())
    EndStage = Stages.getByOid(nodeEndStage.getOid())

    if parentApo != None:
        anatApo.setParentApoOid(parentApo.getOid())
        anatApo.setFullPath(parentApo.getFullPath() + "." +
                            node.getComponentName())
        anatApo.setFullPathOids(parentApo.getFullPathOids() + "." +
                            str(node.getOid()))
        anatApo.setFullPathJsonHead(parentApo.getFullPathJsonHead() + 
                                '{\"attr\": { \"ext_id\": \"' + node.getPublicId() + 
                                '\", \"id\": \"li.node.BRANCH.Abstract.id' + str(node.getOid()) + 
                                '\", \"name\": \"' + node.getComponentName() + 
                                '\", \"start\": \"' + StartStage.getName() +
                                '\", \"end\": \"' + EndStage.getName() +
                                '\"}, \"data\": \"' + node.getComponentName() + 
                                '\", \"state\": \"open\", \"children\": [')
        anatApo.setFullPathJsonTail(parentApo.getFullPathJsonTail() + ']}')
    else:
        anatApo.setFullPath(node.getComponentName())
        anatApo.setFullPathOids(str(node.getOid()))
        anatApo.setFullPathJsonHead(
                                '{\"attr\": { \"ext_id\": \"' + node.getPublicId() + 
                                '\", \"id\": \"li.node.BRANCH.Abstract.id' + str(node.getOid()) + 
                                '\", \"name\": \"' + node.getComponentName() + 
                                '\", \"start\": \"' + StartStage.getName() +
                                '\", \"end\": \"' + EndStage.getName() +
                                '\"}, \"data\": \"' + node.getComponentName() + 
                                '\", \"state\": \"open\", \"children\": [')
        anatApo.setFullPathJsonTail(']}')

        
    # Gather stage range of anatomy node
    anatApo.setNodeStartStageOid(nodeStartStage.getOid())
    anatApo.setNodeEndStageOid(nodeEndStage.getOid())

    # Path start and end stages may be narrower than node start and
    # end stages.  Used to reason about what parts of the tree to display
    # for what stages.
    pathStartStage = nodeStartStage
    pathEndStage   = nodeEndStage
    if parentApo:
        parentPathStartStage = Stages.getByOid(parentApo.getPathStartStageOid())
        parentPathEndStage   = Stages.getByOid(parentApo.getPathEndStageOid())
        if pathStartStage.getSequence() < parentPathStartStage.getSequence():
            pathStartStage = parentPathStartStage
        if pathEndStage.getSequence() > parentPathEndStage.getSequence():
            pathEndStage = parentPathEndStage
    anatApo.setPathStartStageOid(pathStartStage.getOid())
    anatApo.setPathEndStageOid(pathEndStage.getOid())

    if pathStartStage.getSequence() > pathEndStage.getSequence():
        Util.fatalError(["Bad path stage range"])

    # part of record fully populated.  Add it to database and to this module's
    # knowledge base.
    anatApo.insert()
    _addToKnowledge(anatApo, deferReverse = True)
    # Other globals populated after everything has been created.

    rank += 1

    # Generate records, recursively, depth first, left to right, for all
    # its children.
    childIsPrimary = isPrimaryPath and node.isPrimary()
    #childRels = Relationships.getByParentOidRelType(node.getOid(),
    #                                                Relationships.PART_OF)
    childRels = Relationships.getByParentOid(node.getOid())
    
    for childRel in childRels:
        childOid = childRel.getChildOid()
        
        #print "childOid = %s" % childOid
        
        # Filter out children that are outside current path's stage range
        childStartStage, childEndStage = Nodes.getStageWindowForNodeOid(childOid)
        childStart = childStartStage.getSequence()
        childEnd   = childEndStage.getSequence()
        pathStart  = Stages.getByOid(anatApo.getPathStartStageOid()).getSequence()
        pathEnd    = Stages.getByOid(anatApo.getPathEndStageOid()).getSequence()
        if (   (childStart >= pathStart and childStart <= pathEnd)
            or (childEnd   >= pathStart and childEnd   <= pathEnd)
            or (childStart <  pathStart and childEnd   >  pathEnd)):
            rank = _processNode(node = Nodes.getByOid(childOid),
                                parentApo = anatApo,
                                rank = rank, depth = depth + 1,
                                isPrimaryPath = childIsPrimary)

    return rank


# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def SequenceIterator():
    """
    Return an iterator that walks all ANAD_PART_OF records in sequence
    order.
    """
    return iter(_partOfBySequence)



def ReverseSequenceIterator():
    """
    Return an iterator that walks all of ANAD_PART_OF in reverse sequence
    order.
    """
    return iter(_partOfInReverseSequence)



class PerspectiveSequenceIterator:
    """
    An iterator walks all ANAD_PART_OF records in the given perspective,
    in sequence order.
    """
    def __init__(self, perspectiveName):
        """
        Initialise an iterator to walk all ANAD_PART_OF records that participate
        in the given perspective.
        """
        self.__seqIter = SequenceIterator()
        self.__perspectiveName = perspectiveName
        return


    def __iter__(self):
        return self


    def next(self):
        """
        Get the next ANAD_PART_OF record in this iterator's perspective.
        """
        apo = self.__seqIter.next() # may raise a StopIteration
        while not PartOfPerspectives.apoOidInPerspective(apo.getOid(),
                                                         self.__perspectiveName):
            apo = self.__seqIter.next() # may raise a StopIteration
        return apo


class PerspectiveReverseSequenceIterator:
    """
    An iterator walks all ANAD_PART_OF records in the given perspective,
    in reverse sequence order.
    """
    def __init__(self, perspectiveName):
        """
        Initialise an iterator to walk all ANAD_PART_OF records that participate
        in the given perspective in reverse sequence order.
        """
        self.__seqIter = ReverseSequenceIterator()
        self.__perspectiveName = perspectiveName
        return


    def __iter__(self):
        return self


    def next(self):
        """
        Get the next ANAD_PART_OF record in this iterator's perspective.
        """
        apo = self.__seqIter.next() # may raise a StopIteration
        while not PartOfPerspectives.apoOidInPerspective(apo.getOid(),
                                                         self.__perspectiveName):
            apo = self.__seqIter.next() # may raise a StopIteration
        return apo



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all ANAD_PART_OF records into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """
    return _initialise(__READ_TABLE)


def getByOid(partOfOid):
    """
    Return the partOf with the given OID.  Fails if no partOf has that OID.
    """
    return _partOfByOid[partOfOid]


def getBySequence(partOfSeq):
    """
    Return the partOf with the given sequence.  Fails if no partOf has that
    sequence.
    """
    return _partOfBySequence[partOfSeq]


def getPrimaryPathApoForNodeOid(nodeOid):
    """
    Return the primary ANAD_PART_OF record for the given node OID.  Each node
    has exactly one primary record in the table.
    """
    return _primaryPathByNodeOid[nodeOid]



def getByParentOid(parentApoOid):
    """
    Return the list of ANAD_PART_OF records that are direct children of the
    given parent APO.  Returns the empty list if it has no children.
    """
    kidApos = _byParentOid.get(parentApoOid)
    if kidApos == None:
        kidApos = []
    return kidApos



def deleteAll():
    """
    Delete every record in the table.
    """
    _table.deleteAll()
    _initialiseGlobals()

    return


def regenerateTable():
    """
    Generate the anatomy part of dervied table, an ordered inidented
    list of all parts, showing all full paths to all parts.
    """
    # Anatomy relationships form a directed acyclic graph (DAG)
    # This routine renders that DAG into a tree for
    # quick and easy display of the information.

    return _initialise(__DERIVE_TABLE)



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
