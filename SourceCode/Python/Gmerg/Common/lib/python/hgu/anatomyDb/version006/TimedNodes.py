#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to the set of Timed Nodes defined in
the ANA_TIMED_NODE table.  This module abstracts much of the low-level,
record-at-a-time direct database access in the AnaTimedNodeDb module.

This reads in all defined timed nodes, and then provides in-memory access to
them from that point on.
"""

import sets

from hgu import Util

# Low level DB imports
import AnaTimedNodeDb

# High level DB imports
import Nodes
import Oids
import Stages


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of all timed nodes, indexed by timed node OID
_byOid      = None

# Dictionary of all timed nodes, indexed by Node OID.  Each entry is
# itself a dictionary indexed by Stage OID.
_byNodeOid  = None

# Dictionary of all timed nodes, indexed by Node OID.  Each entry is
# itself a list sorted in stage sequence order
_byNodeOidSorted = None

# Dictionary of all timed nodes, indexed by public ID.
_byPublicId = None

# Dictionary of part-of child timed nodes indexed by parent timed node.
# Each entry is a set of timed nodes that are part-of children of the
# parent timed node.
_partOfChildrenForParent = None

# Dictionary of part-of parent timed nodes indexed by child timed node.
# Each entry is a set of timed nodes that are part-of parents of the
# child timed node.
_partOfParentsForChild = None


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _stageSeqCmp(tn1, tn2):
    """
    Sort two timed nodes in stage sequence order.
    """
    stage1 = Stages.getByOid(tn1.getStageOid())
    stage2 = Stages.getByOid(tn2.getStageOid())

    return cmp(stage1.getSequence(), stage2.getSequence())



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator():
    """
    Return an iterator that walks all timed nodes in no particular order.
    """
    return _byOid.itervalues()



def NodeOidIterator(nodeOid):
    """
    Return an iterator that walks all timed nodes for a particular
    node OID, in no particular order.

    Raises an exception if nodeOid does not exist.
    """
    return _byNodeOid[nodeOid].itervalues()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all timed nodes into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """

    global _byOid, _byNodeOid, _byPublicId, _byNodeOidSorted

    _byOid = {}
    _byNodeOid = {}
    _byPublicId = {}
    _byNodeOidSorted = {}

    for timedNode in AnaTimedNodeDb.Iterator():
        oid = timedNode.getOid()
        _byOid[oid] = timedNode

        _byPublicId[timedNode.getPublicId()] = timedNode

        nodeOid = timedNode.getNodeOid()
        if nodeOid not in _byNodeOid:
            _byNodeOid[nodeOid] = {}
        _byNodeOid[nodeOid][timedNode.getStageOid()] = timedNode

    for nodeOid, timedNodeDict in _byNodeOid.items():
        _byNodeOidSorted[nodeOid] = timedNodeDict.values()
        sortTimedNodes(nodeOid)

    return None


def getByOid(timedNodeOid):
    """
    Return the timed node with the given OID.  Fails if no node has that OID.
    """
    return _byOid[timedNodeOid]



def getByNodeOidInStageOrder(nodeOid):
    """
    Returns a list of timed nodes that exist for the given node, in stage order.
    Fails if the given node OID has no timed nodes.
    """
    return _byNodeOidSorted[nodeOid]


def getStageWindowForNodeOid(nodeOid):
    """
    Returns the stage records for the earliest and latest timed nodes
    for the given node.

    Raises exception if nodeOid does not exist.

    This method belongs in the Nodes module, but the information needed
    to implement it is in this module.
    """
    timedNodes = _byNodeOidSorted[nodeOid]
    stage1 = Stages.getByOid(timedNodes[0].getStageOid())
    stage2 = Stages.getByOid(timedNodes[-1].getStageOid())

    return (stage1, stage2)


def getByNodeStageOids(nodeOid, stageOid):
    """
    Return the timed node for the given node and stage.
    Raises an error if node OID does not exist.
    Returns None if node OID exists, but not at the requested stage.
    """
    return _byNodeOid[nodeOid].get(stageOid)


def getByPublicId(timedNodePublicId):
    """
    Given the PUBLIC ID of a timed node, return that timed node.

    Throws exception if no such timed node exists.
    """
    return _byPublicId[timedNodePublicId]


def connectTheDots():
    """
    Called by the Anatomy module (if you are using it) after all of
    the anatomy has been read into memory.  This connects the different
    object types together in many different ways.
    """
    global _partOfParentsForChild, _partOfChildrenForParent

    _partOfParentsForChild = {}
    _partOfChildrenForParent = {}

    for timedNode in Iterator():
        node = Nodes.getByOid(timedNode.getNodeOid())
        for childNode in Nodes.getPartOfChildrenForNode(node):
            childTimedNode = getByNodeStageOids(childNode.getOid(),
                                                timedNode.getStageOid())
            if childTimedNode:
                if childTimedNode in _partOfParentsForChild:
                    _partOfParentsForChild[childTimedNode].add(timedNode)
                else:
                    _partOfParentsForChild[childTimedNode] = sets.Set([timedNode])

        for parentNode in Nodes.getPartOfParentsForNode(node):
            parentTimedNode = getByNodeStageOids(parentNode.getOid(),
                                                 timedNode.getStageOid())
            if parentTimedNode:
                if parentTimedNode in _partOfChildrenForParent:
                    _partOfChildrenForParent[parentTimedNode].add(timedNode)
                else:
                    _partOfChildrenForParent[parentTimedNode] = sets.Set([timedNode])

    return


def getPartOfChildrenForParent(parent):
    """
    Return a set of part-of children for the given timed node.
    Returns empty set if timed node has no children.
    """
    if parent in _partOfChildrenForParent:
        kids = _partOfChildrenForParent[parent]
    else:
        kids = sets.Set()

    return kids



def getPartOfParentsForChild(child):
    """
    Return a set of part-of parents for the given timed node.
    Returns empty set if timed node has no parents.
    """
    if child in _partOfParentsForChild:
        parents = _partOfParentsForChild[child]
    else:
        parents = sets.Set()

    return parents



def createTimedNode(nodeOid, stageOid, stageModifier = None, publicId = None,
                    postponeSort = False):
    """
    Create a new timed node record.

    postponeSort: If True then update, but do not sort, the internal data
                  structure that maintains a list of timed nodes for nodeOid
                  in stage sequence sorted order.
                  This is an optimisation to be used when creating many timed
                  nodes in a row.  sortTimedNodes(nodeOid) should be called
                  once all timed nodes have been created for that node.
    """
    # Validate first
    if publicId in _byPublicId:
        Util.fatalError([
            "Attempt to create a timed node with public ID '" + publicId +
            " but timed node with that ID already exists."])

    timedNode = AnaTimedNodeDb.AnaTimedNodeDbRecord()
    oid = Oids.createNextOid()

    timedNode.setOid(oid)
    timedNode.setNodeOid(nodeOid)
    timedNode.setStageOid(stageOid)
    timedNode.setStageModifier(stageModifier)
    timedNode.setPublicId(publicId)

    timedNode.insert()
    _byOid[oid] = timedNode
    _byPublicId[publicId] = timedNode

    if nodeOid not in _byNodeOid:
        _byNodeOid[nodeOid] = {}
    _byNodeOid[nodeOid][stageOid] = timedNode

    if nodeOid not in _byNodeOidSorted:
        _byNodeOidSorted[nodeOid] = []
    _byNodeOidSorted[nodeOid].append(timedNode)
    if not postponeSort:
        sortTimedNodes(nodeOid)

    return timedNode


def sortTimedNodes(nodeOid):
    """
    Called to sort the timed nodes for the given node, in stage
    sequence order.

    This should be called after calling createTimedNode() with the
    postponeSort parameter set to True.
    """
    _byNodeOidSorted[nodeOid].sort(_stageSeqCmp)





# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
