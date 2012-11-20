#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level fast access to records in the derived
ANAD_PART_OF_PERSPECTIVE table.  This module abstracts much of the low-level,
record-at-a-time direct database access in the AnadPartOfPerspectiveDb module.

This reads in all ANAD_PART_OF_PERSPECTIVE records, and then provides
in-memory access to them from that point on.
"""

import sets

from hgu.db import DbTable

# Low level access
from hgu.anatomyDb.version011 import AnadPartOfPerspectiveDb

# High level access
import PartOfs
import Perspectives
import PerspectiveAmbits



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of records indexed by perspective.  Each entry is an unordered
# list of POP records in that perspective.
_byPerspective = None

# Dictionary indexed by Node OID.  Each enty is a set listing the perspectives
# the node belongs to.
_byNodeOid = None

# Dictionary indexed by APO OID.  Each entry is a dictionary of POP records
# indexed by perspective.
_byApoOid = None

# Database table object this module provides high-level access to.
_table = None


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _initialiseGlobals():
    """
    Initialise this module's global variables.
    """
    global _byPerspective, _byNodeOid, _byApoOid, _table

    _table = DbTable.getByName(AnadPartOfPerspectiveDb.TABLE_NAME)
    _byPerspective = {}
    _byNodeOid = {}
    _byApoOid = {}

    return



def _addToKnowledge(pop):
    """
    Add the given part of perspective record to this module's internal knowledge base.
    """
    perspective = pop.getPerspectiveName()
    if perspective not in _byPerspective:
        _byPerspective[perspective] = []
    _byPerspective[perspective].append(pop)
    nodeOid = pop.getNodeOid()
    if nodeOid not in _byNodeOid:
        _byNodeOid[nodeOid] = sets.Set()
    _byNodeOid[nodeOid].add(perspective)
    apoOid = pop.getApoOid()
    if apoOid not in _byApoOid:
        _byApoOid[apoOid] = {}
    _byApoOid[apoOid][perspective] = pop




# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all ANAD_PART_OF records into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """
    _initialiseGlobals()
    for pop in AnadPartOfPerspectiveDb.Iterator():
        _addToKnowledge(pop)
    return


def getByPerspectiveApoOid(perspectiveName, apoOid):
    """
    Return the record for the given perspective name and ANAD_PART_OF oid.

    Returns None if given ANAD_PART_OF record is not in the given perspective.
    Raises exception if given apoOid does not participate in any perspective.
    """
    # Dictionary indexed by APO OID.  Each entry is a dictionary of POP records
    # indexed by perspective.
    return _byApoOid[apoOid].get(perspectiveName)



def apoOidInPerspective(apoOid, perspectiveName):
    """
    Returns True if apoOid is in the given perspective.
    """
    inPerspective = False
    if apoOid in _byApoOid:
        if perspectiveName in _byApoOid[apoOid]:
            inPerspective = True
    return inPerspective


def deleteAll():
    """
    Delete every record in the table.
    """
    _table.deleteAll()
    _initialiseGlobals()

    return


def regenerateTable():
    """
    Generate the anatomy part of perspecitve dervied table, which indicates
    which records in ANAD_PART_OF participate in which perspectives.
    """
    deleteAll()

    # Start generating records from start nodes, and continue generating
    # records for their children until either the bottom of the ANAD_PART_OF
    # tree is reached, or stop nodes are reached.

    for perspective in Perspectives.Iterator():
        perspectiveName = perspective.getName()
        starts = PerspectiveAmbits.getStartAmbitForPerspective(perspectiveName)
        stops  = PerspectiveAmbits.getStopAmbitForPerspective(perspectiveName)
        startNodeOids = sets.Set(starts.keys())
        stopNodeOids  = sets.Set(stops.keys())
        startApos = [PartOfs.getPrimaryPathApoForNodeOid(nodeOid)
                     for nodeOid in startNodeOids]
        apoList = startApos[:]

        while len(apoList) > 0:
            partOf = apoList.pop()

            # create POP record for this part of.
            
            pop = AnadPartOfPerspectiveDb.AnadPartOfPerspectiveDbRecord()
            pop.setPerspectiveName(perspectiveName)
            pop.setApoOid(partOf.getOid())
            pop.setIsAncestor(False)
            pop.setNodeOid(partOf.getNodeOid())
            pop.insert()
            _addToKnowledge(pop)

            # if this is not a stop node, then add all its part-of kids
            # to the list of APOs to generate POP records for.
            if partOf.getNodeOid() not in stopNodeOids:
                apoList.extend(PartOfs.getByParentOid(partOf.getOid()))

        # for each start node, add any ancestor APOs that were not added
        # by the above process.
        ancesApos = sets.Set()
        for apo in startApos:
            parentApoOid = apo.getParentApoOid()
            if parentApoOid != None:
                parentApo = PartOfs.getByOid(parentApoOid)
                if (_byApoOid.get(parentApoOid) == None or
                    _byApoOid[parentApoOid].get(perspectiveName) == None):
                    ancesApos.add(parentApo)

        while len(ancesApos) > 0:
            ancesApo = ancesApos.pop()
            # create POP record for this ancestor
            pop = AnadPartOfPerspectiveDb.AnadPartOfPerspectiveDbRecord()
            pop.setPerspectiveName(perspectiveName)
            pop.setApoOid(ancesApo.getOid())
            pop.setIsAncestor(True)
            pop.setNodeOid(ancesApo.getNodeOid())
            pop.insert()
            _addToKnowledge(pop)

            # if this APO has a parent that hasn't yet been processed then
            # add it to list of ancestor APOs to generate records for.
            parentApoOid = ancesApo.getParentApoOid()
            if (parentApoOid != None and
                (_byApoOid.get(parentApoOid) == None or
                 _byApoOid[parentApoOid].get(perspectiveName) == None)):
                parentApo = PartOfs.getByOid(parentApoOid)
                ancesApos.add(parentApo)

    return



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
