#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to transitive relationships in the
anatomy database defined in the derived ANAD_RELATIONSHIP_TRANSITIVE table.
This module abstracts the low-level, record-at-a-time direct database access
in the AnadRelationshipTranstiveDb module.

At initialisation, this reads in all transitive relationships, and then
provides in-memory access to them from that point on.
"""

import sets

from hgu import Util

from hgu.db import DbAccess
from hgu.db import DbTable

# Low level DB access
import AnadRelationshipTransitiveDb

# High level DB access
import Nodes
import Relationships



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# List of all relationships, unindexed.
_rels = None

# Dictionary of all transitive relationships, indexed by relationship
# type.  Each entry is an unordered list of transitive relationships
# of that type.
_byRelType = None

# Dictionary of all transitive relationships, keyed by ancestor OID and
# relationship type.
# Each entry is a dictionary of relationships, keyed by descendent OID
_byAncestorOidRelType = None

# Dictionary of all transitive relationships, keyed by descendent OID and
# relationship type.
# Each entry is a dictionary of relationships, keyed by ancestor OID.
_byDescendentOidRelType = None


# DbTable object for the table this module provides high level access to.
_table = None

# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _initialiseGlobals():
    """
    Set the globals in this module to their pristine state.
    """
    global _rels, _byRelType, _byAncestorOidRelType, _byDescendentOidRelType
    global _table

    _table = DbTable.getByName(AnadRelationshipTransitiveDb.TABLE_NAME)
    _rels = []
    _byRelType = {}
    _byAncestorOidRelType = {}
    _byDescendentOidRelType = {}

    return




def _addToKnowledge(rel):
    """
    Add the transitive relationship to this module's knowledge of
    transitive relationships.
    """
    descendentOid = rel.getDescendentOid()
    ancestorOid = rel.getAncestorOid()
    relType = rel.getRelationshipType()
    ancestorTuple = (ancestorOid, relType)
    descendentTuple = (descendentOid, relType)

    _rels.append(rel)

    if relType not in _byRelType:
        _byRelType[relType] = []
    _byRelType[relType].append(rel)

    if ancestorTuple not in _byAncestorOidRelType:
        _byAncestorOidRelType[ancestorTuple] = {}
    _byAncestorOidRelType[ancestorTuple][descendentOid] = rel

    if descendentTuple not in _byDescendentOidRelType:
        _byDescendentOidRelType[descendentTuple] = {}
    _byDescendentOidRelType[descendentTuple][ancestorOid] = rel

    return


def _createImmediateRelsWhereChild(currentNode):
    """
    Create transitive relationships for immeditate relationships where the
    given node is the child.
    """
    currentOid = currentNode.getOid()

    # Add relationships from this node to its immediate parents
    relsWithParents = Relationships.getByChildOidRelType(
        currentOid, Relationships.PART_OF)
    for parentRel in relsWithParents:
        parentOid = parentRel.getParentOid()
        if not exists(Relationships.PART_OF, descendentOid = currentOid,
                      ancestorOid = parentOid):
            start, stop = Nodes.getStageWindowForNodeOid(currentOid)

            DbAccess.writeSql("/* direct */")
            createRelationshipTransitive(
                Relationships.PART_OF, descendentOid = currentOid,
                ancestorOid = parentOid)

            # Add relationships based on its parents' already defined
            # transitive relationships.  Transitive relationships
            # do not cross relationship types.
            ancestorRels = _byDescendentOidRelType.get((parentOid,
                                                        Relationships.PART_OF))
            if ancestorRels != None:
                for ancestorRel in ancestorRels.itervalues():
                    ancestorOid = ancestorRel.getAncestorOid()
                    ancestor = Nodes.getByOid(ancestorOid)
                    if not exists(relType = Relationships.PART_OF,
                                  descendentOid = currentOid,
                                  ancestorOid = ancestorOid):
                        # if reltype is part-of then only generate a transitive
                        # relationship if the ancestor and this node overlap in time.
                        # Group ancestors sometimes do not overlap with their
                        # descendents.
                        ancestorStart, ancestorStop = (
                            Nodes.getStageWindowForNodeOid(ancestorOid))
                        if (start.getSequence() > ancestorStop.getSequence() or
                            stop.getSequence()  < ancestorStart.getSequence()):
                            if not ancestor.isGroup():
                                # oops, supposed to be a group
                                Util.fatalError([
                                    "Ancestor stage window does not overlap " +
                                    "with descendent stage window and",
                                    "ancestor is not a group.",
                                    "Ancestor ID:   " + ancestor.getPublicId() +
                                    " Name: " + ancestor.getName(),
                                    "  Start-Stop: " + ancestorStart.getName() +
                                    "-" + ancestorStop.getName(),
                                    "Descendent ID: " + currentNode.getPublicId() +
                                    " Name: " + currentNode.getName(),
                                    "  Start-Stop: " + start.getName() +
                                    "-" + stop.getName()])
                        else:
                            DbAccess.writeSql("/* from ancestor */")
                            createRelationshipTransitive(Relationships.PART_OF,
                                                         descendentOid = currentOid,
                                                         ancestorOid = ancestorOid)
    return




# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator(relType = None):
    """
    Return an iterator that walks transtive relationships in no particular order.

    If relType is provided, then only relationships of that type are included in
    the iterator.
    """
    if relType != None:
        return iter(_byRelType[relType])
    else:
        return iter(_rels)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all transitive relationships into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """
    _initialiseGlobals()

    for rel in AnadRelationshipTransitiveDb.Iterator():
        _addToKnowledge(rel)

    return None


def exists(relType, descendentOid, ancestorOid):
    """
    Returns True if a transitive relationship record already exists for
    the given parameters.
    """
    found = False
    rels = _byDescendentOidRelType.get((descendentOid, relType))
    if rels != None:
        if ancestorOid in rels:
            found = True
    return found



def getByAncestorOidRelType(ancestorOid, relType = Relationships.PART_OF):
    """
    Return dictionary of transitive relationships with given ancestor of
    given relationship type.  Dictionary is keyed by descenendent OID.

    If no such relationships exist then an empty dictionary is returned.
    """
    rels = _byAncestorOidRelType.get((ancestorOid, relType))
    if rels == None:
        rels = {}
    return rels


def getByDescendentOidRelType(descendentOid, relType):
    """
    Return dictionary of transitive relationships with given descendent
    of given relationship type.  Dictionary is keyed by ancestor OID.

    If no such relationships exist then the empty dictionary is returned.
    """
    rels = _byDescendentOidRelType.get((descendentOid, relType))
    if rels == None:
        rels = {}
    return rels


def createRelationshipTransitive(relationshipType, descendentOid, ancestorOid):
    """
    Create a new relationship record.
    """
    # Create the record and generate the insert
    rel = AnadRelationshipTransitiveDb.AnadRelationshipTransitiveDbRecord()

    rel.setRelationshipType(relationshipType)
    rel.setDescendentOid(descendentOid)
    rel.setAncestorOid(ancestorOid)

    rel.insert()

    # add relationship to this module's knowledge.
    _addToKnowledge(rel)

    return rel



def regenerateTable():
    """
    Called to completely regenerate the contents of this table.  First,
    it (generates the sql to) empties the table, and then it repopulates
    it.
    """
    # Anatomy part-of relationships form a directed acyclic graph (DAG)
    #
    # Walk the graph top to bottom, breadth first, generating
    # transitive closure relationships as we go.

    # Delete everything in existing table.
    deleteAll()
    _initialiseGlobals()

    # Start with the root of everything.
    nodeQueue = [Nodes.getRoot()]
    beenQueued = sets.Set(nodeQueue) # what has been put on the Q
    beenProcessed = sets.Set()       # what has been pulled from the Q

    while len(nodeQueue) > 0:
        current = nodeQueue.pop(0)
        currentOid = current.getOid()

        _createImmediateRelsWhereChild(current)

        # This node's children can be added to the queue if all that
        # child's parents have been processed.
        beenProcessed.add(current)
        relsWithChildren = Relationships.getByParentOidRelType(
            currentOid, Relationships.PART_OF)
        for childRel in relsWithChildren:
            childOid = childRel.getChildOid()
            child = Nodes.getByOid(childOid)
            if child not in beenQueued:
                # Get all the child's parents
                childsRelsWithParents = Relationships.getByChildOidRelType(
                    childOid, Relationships.PART_OF)
                allParentsProcessed = True
                for childsRelWithParent in childsRelsWithParents:
                    childsParent = Nodes.getByOid(
                        childsRelWithParent.getParentOid())
                    if childsParent not in beenProcessed:
                        allParentsProcessed = False
                if allParentsProcessed:
                    nodeQueue.append(child)
                    beenQueued.add(child)

    # Add self-referential relationships
    for node in beenProcessed:
        createRelationshipTransitive(Relationships.PART_OF,
                                     descendentOid = node.getOid(),
                                     ancestorOid = node.getOid())

    return None


def deleteAll():
    """
    Delete every record in the table.
    """
    _table.deleteAll()
    _initialiseGlobals()

    return




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
