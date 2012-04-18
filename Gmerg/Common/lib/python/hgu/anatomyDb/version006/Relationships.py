#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to relationships in the anatomy database
defined in the ANA_RELATIONSHIP table.  This module abstracts the
low-level, record-at-a-time direct database access in the AnaRelationshipDb
module.

At initialisation, this reads in all relationships, and then provides in-memory
access to them from that point on.
"""

import sets

from hgu import Util

# Low level DB access
import AnaRelationshipDb

import AnaRelationshipProjectDb

# High level DB access
import Nodes
import Oids
import TimedNodes



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

PART_OF = "part-of" # :TODO: put in AnaRelationshipTypeDb if and when we create it.
IS_A    = "is-a" # :TODO: put in AnaRelationshipTypeDb if and when we create it.




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of all relationships, indexed by relationship OID
_byOid = None

# Set of all part-of relationships
_partOfs = None

# Which Project are we dealing with
_sortProject = None


# Dictionary of all relationships, keyed by parent OID and relationship type.
# Each entry is an ordered list of relationships.  Ordering is done first
# by rel sequence, and then alphabetically by component name.
_byParentOidRelType = None

# Dictionary of all relationships, keyed by child OID and relationship type.
# Each entry is an unordered list of relationships.
_byChildOidRelType = None


# Dictionary of all relationships, keyed by parent OID, child _addRelToKnowledgeOID, and
# relationship type, the three things that uniquely identify a relationship.
# Each entry in the dictionary is a single relationship.
_byParentChildOidsRelType = None


# Dictionary of all relationships, keyed by parent OID.
# Each entry is an ordered list of relationships.  Ordering is done first
# by rel sequence, and then alphabetically by component name.
_byParentOids = None


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _relCmp(rel1, rel2):
    """
    Sort comparison routine to compare 2 relationships.
    First sorts by rel sequence (if set), and then by
    child component names if sequence is a tie.
    """
    relType1 = rel1.getRelationshipType()
    relType2 = rel2.getRelationshipType()
    #if relType1 != relType2:
    #    Util.fatalError([
    #        "Attempting to sort relationships of two different types:",
    #        "Type 1: " + relType1 + " Type 2: " + relType2
    #        ])
    if rel1.getParentOid() != rel2.getParentOid():
        Util.fatalError([
            "Attempting to sort relationships that have different parents."])

    relProj1 = AnaRelationshipProjectDb.getByRelationShipFkAndProject(rel1.getOid(), _sortProject)
    relProj2 = AnaRelationshipProjectDb.getByRelationShipFkAndProject(rel2.getOid(), _sortProject)

    seq1 = relProj1.getSequence()
    seq2 = relProj2.getSequence()

    #if seq1 != None and seq2 != None:
        #Util.statusMessage(["Relationship 1: " + str(rel1.getOid()) + "\n" +
        #                    "Type 1: " + relType1 + "\n" +
        #                    "Sequence 1: " + str(seq1) + "\n" +
        #                    "Project: " + _sortProject])
        #Util.statusMessage(["Relationship 2: " + str(rel2.getOid()) + "\n" +
        #                    "Type 2: " + relType2 + "\n" +
        #                    "Sequence 2: " + str(seq2) + "\n" + 
        #                    "Project: " + _sortProject])
    
    if seq1 != seq2:
        # sort based purely on sequence
        if seq1 != None and seq2 != None:
            if seq1 < seq2:
                return -1
            elif seq1 > seq2:
                return +1
        elif seq1 != None:
            return -1
        elif seq2 != None:
            return +1
    else:
        # sequence same, sort based on name.
        if relType1 == PART_OF or relType1 == IS_A:
            return cmp(Nodes.getByOid(rel1.getChildOid()).getComponentName(),
                       Nodes.getByOid(rel2.getChildOid()).getComponentName())
        else:
            # assume relationship is between timed nodes.
            """
            Util.statusMessage(["THERE"])
            Util.statusMessage(["Relationship 1: " + str(rel1.getOid()) + "\n" +
                            "Type 1: " + relType1 + "\n" +
                            "Sequence 1: " + str(seq1) + "\n" +
                            "Project: " + _sortProject])
            Util.statusMessage(["Relationship 2: " + str(rel2.getOid()) + "\n" +
                            "Type 2: " + relType2 + "\n" +
                            "Sequence 2: " + str(seq2) + "\n" + 
                            "Project: " + _sortProject])
            """
            nodeOid1 = TimedNodes.getByOid(rel1.getChildOid()).getNodeOid()
            nodeOid2 = TimedNodes.getByOid(rel2.getChildOid()).getNodeOid()
            return cmp(Nodes.getByOid(nodeOid1).getComponentName(),
                       Nodes.getByOid(nodeOid2).getComponentName())


def _addRelToKnowledge(rel, sortWithSiblings = True):
    """
    Add the relationship to our store of knowledge about relationships.

    sortWithSiblings: If True then resort this relationship's parent's list of
        children. If False, then the new relationship is added to the end.
    """
    relOid = rel.getOid()
    childOid = rel.getChildOid()
    parentOid = rel.getParentOid()
    relType = rel.getRelationshipType()
    parentTuple = (parentOid, relType)
    parentOnlyTuple = (parentOid)
    childTuple = (childOid, relType)
    all3Tuple = (parentOid, childOid, relType)

    # add rel to unique dictionaries
    _byOid[relOid] = rel
    if all3Tuple in _byParentChildOidsRelType:
        Util.fatalError([
            "Same relationship exists more than once.  Should not happen.",
            "Parent OID: " + str(parentOid),
            "Child OID:  " + str(childOid),
            "Rel Type:   " + relType])
    _byParentChildOidsRelType[all3Tuple] = rel

    # add rel to what we know about parent
    whereParentRels = _byParentOidRelType.get(parentTuple)
    if whereParentRels == None:
        _byParentOidRelType[parentTuple] = []
    _byParentOidRelType[parentTuple].append(rel)
    if sortWithSiblings:
        # sort the children of each parent.
        _byParentOidRelType[parentTuple].sort(_relCmp)

    # add rel to what we know about parent
    whereParentOnlyRels = _byParentOids.get(parentOnlyTuple)
    if whereParentOnlyRels == None:
        _byParentOids[parentOnlyTuple] = []
    _byParentOids[parentOnlyTuple].append(rel)
    if sortWithSiblings:
        # sort the children of each parent.
        _byParentOids[parentOnlyTuple].sort(_relCmp)

    # add rel to what we know about child
    whereChildRels = _byChildOidRelType.get(childTuple)
    if whereChildRels == None:
        _byChildOidRelType[childTuple] = []
    _byChildOidRelType[childTuple].append(rel)

    if relType == PART_OF:
        _partOfs.add(rel)

    return None



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator():
    """
    Return an iterator that walks all relationships in no particular order.
    """
    return _byOid.itervalues()


def PartOfIterator():
    """
    Return an iterator over every part of relationship, in no particular order.
    """
    # :TODO: This is a hack.  I don't know how to return an iterator on a set
    # so just return the set directly, which works, but seems immoral.
    return _partOfs


# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise(project):
    """
    Read all relationships into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """

    global _byOid, _byParentOidRelType, _byChildOidRelType, _partOfs
    global _byParentChildOidsRelType, _byParentOids, _sortProject

    _byOid = {}
    _partOfs = sets.Set()
    _sortProject = project
    _byParentOidRelType = {}
    _byChildOidRelType = {}
    _byParentChildOidsRelType = {}
    _byParentOids = {}

    # Postpone sorting of each parent's kids until all relationships added.
    for rel in AnaRelationshipDb.Iterator():
        _addRelToKnowledge(rel, sortWithSiblings = False)

    # sort the children of each parent.
    for kids in _byParentOidRelType.itervalues():
        kids.sort(_relCmp)

    for kids in _byParentOids.itervalues():
        kids.sort(_relCmp)

    return None


def getByOid(relOid):
    """
    Return the relationship with the given OID.  Fails if no relationship
    has that OID.
    """
    return _byOid[relOid]


def getByParentOid(parentOid):
    """
    Return list of relationships with given parent.
    List is returned in relationship sequence order.

    If no such relationships exist then the empty list is returned.
    """
    rels = _byParentOids.get((parentOid))
    if rels == None:
        rels = []
    return rels


def getByParentOidRelType(parentOid, relType):
    """
    Return list of relationships with given parent of given relationship type.
    List is returned in relationship sequence order.

    If no such relationships exist then the empty list is returned.
    """
    rels = _byParentOidRelType.get((parentOid, relType))
    if rels == None:
        rels = []
    return rels


def getByChildOidRelType(childOid, relType):
    """
    Return list of relationships with given child of given relationship type.
    If no such relationships exist then the empty list is returned.
    """
    rels = _byChildOidRelType.get((childOid, relType))
    if rels == None:
        rels = []
    return rels


def getByParentChildOidsRelType(parentOid, childOid, relType):
    """
    Return the relationship for the given parent OID, child OID and
    relationship type combination.  If relationship does not exist
    then None is returned.
    """
    return _byParentChildOidsRelType.get((parentOid, childOid, relType))



def createRelationship(relationshipType, childOid, parentOid, sequence = None):
    """
    Create a new relationship record.
    """
    # Create the record and generate the insert
    rel = AnaRelationshipDb.AnaRelationshipDbRecord()
    oid = Oids.createNextOid()

    rel.setOid(oid)
    rel.setRelationshipType(relationshipType)
    rel.setChildOid(childOid)
    rel.setParentOid(parentOid)

    rel.insert()

    # add relationship to this module's knowledge.
    _addRelToKnowledge(rel)

    # Add relationship to other module's knowledge
    Nodes.connectTheDotsForRelationship(rel)

    return rel


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
