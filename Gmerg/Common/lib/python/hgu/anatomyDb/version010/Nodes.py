#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to the set of Nodes defined in
the ANA_NODE table.  This module abstracts much of the low-level,
record-at-a-time direct database access in the AnaNodeDb module.

This reads in all defined nodes, and then provides in-memory access to
them from that point on.
"""

import sets

from hgu import Util

# Low level DB imports
import AnaNodeDb

# High level DB imports.
import Oids
import Relationships
import TimedNodes


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------


# Dictionary of all nodes by OID
_byOid      = None

# Dictionary of all nodes by their public ID (e.g. EMAPA:123)
_byPublicId = None

# Dictionary of nodes indexed by their component name.  Each entry is an
# unordered list of every node with that component name.
_byComponentName = None


# Dictionary of part-of child nodes indexed by parent node.  Each entry
# is a set of nodes that are part-of children of the parent node.
_partOfChildrenByParent = None

# Dictionary of part-of parent nodes indexed by child node.  Each entry
# is a set of nodes that are part-of parents of the child node.
_partOfParentsByChild = None

_rootNode   = None



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _addToKnowledge(node):
    """
    Add the node to this module's internal knowledge about nodes.

    NOTE: This does not update the root node, or anything that is
    updated by connectTheDots().
    """
    _byOid[node.getOid()] = node
    _byPublicId[node.getPublicId()] = node
    name = node.getComponentName()
    if name not in _byComponentName:
        _byComponentName[name] = []
    _byComponentName[name].append(node)

    return



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator():
    """
    Return an iterator that walks all nodes in no particular order.
    """
    return _byOid.itervalues()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all nodes into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """

    global _byOid, _byPublicId, _byComponentName, _rootNode

    _byOid      = {}
    _byPublicId = {}
    _byComponentName = {}

    for node in AnaNodeDb.Iterator():
        _addToKnowledge(node)

    # Get the root, if there is one,  have it point at same python object
    # as is in _byOid
    _rootNode = AnaNodeDb.getRoot()
    if _rootNode != None:
        _rootNode = _byOid[AnaNodeDb.getRoot().getOid()]

    return None


def getByOid(nodeOid):
    """
    Return the node with the given OID.  Fails if no node has that OID.
    """
    #print nodeOid
    
    return _byOid[nodeOid]



def getByPublicId(nodePublicId):
    """
    Return the node with the given public ID (e.g. EMAPA:123).
    Fails if no node has that public ID..
    """
    return _byPublicId[nodePublicId]



def getByComponentName(componentName):
    """
    Return an unordered list of all nodes with the given component name.

    Raises a KeyError exception if the component name does not exist.
    """
    return _byComponentName[componentName]



def getRoot():
    """
    Return the root node.  Each anatomy ontology has a single root node
    from which everything else is hung off of.
    """
    return _rootNode


def checkForNewRoot():
    """
    Check if ontology has a new root node.  Update this module's internal
    state if there is a new root, and return the root node if it has changed
    or not.
    """
    # Hmm, could do this the efficient but unsafe way: pick a node randomly
    # and follow its part of path upward until it ends.
    # Or could check every node and report an error if we find more than one
    # root in the list.
    # Do it the slow, safe way.
    global _rootNode

    roots = []
    for node in Iterator():
        if len(getPartOfParentsForNode(node)) == 0:
            roots.append(node)

    if len(roots) == 0:
        Util.warning(["No root node found in ontology."])
    elif len(roots) == 1:
        _rootNode = roots[0]
    else:
        message = [str(len(roots)) + " root nodes found."]
        for node in roots:
            message.append("ID: " + node.getPublicId() +
                           " Name: " + node.getComponentName())
        Util.fatalError(message)

    return _rootNode


def setRootByPublicId(publicId):
    """
    Given the public ID of a node, update the internal state of this
    module to make that node be the root node.

    This :HACK: is necessary because for Xenopus, checkForNewRoot finds
    200+ root nodes.
    """
    global _rootNode

    _rootNode = _byPublicId[publicId]

    if len(getPartOfParentsForNode(_rootNode)) > 0:
        Util.fatalError([
            "Attmept to set root node to a node ('" + _rootNode.getPublicId() +
            "', '" + _rootNode.getComponentName() + "')",
            "that is the child in " +
            str(len(getPartOfParentsForNode(_rootNode))) +
            " part-of relationships."])

    return _rootNode


def connectTheDots():
    """
    Called by Anatomy module after everything has been read in to connect the
    different data types together.  This routine does that connection for
    nodes.
    """
    global _partOfChildrenByParent, _partOfParentsByChild

    # Process relationships
    _partOfChildrenByParent = {}
    _partOfParentsByChild   = {}

    for rel in Relationships.PartOfIterator():
        connectTheDotsForRelationship(rel)

    return


def connectTheDotsForRelationship(rel):
    """
    Does what connectTheDots does for all relationships, except this
    does it for only one relationships.
    """
    parent = getByOid(rel.getParentOid())
    child  = getByOid(rel.getChildOid())

    if parent in _partOfChildrenByParent:
        _partOfChildrenByParent[parent].add(child)
    else:
        _partOfChildrenByParent[parent] = sets.Set([child])
    if child in _partOfParentsByChild:
        _partOfParentsByChild[child].add(parent)
    else:
        _partOfParentsByChild[child] = sets.Set([parent])

    return



def getPartOfChildrenForNode(parentNode):
    """
    Return a set of part-of children for the given node.
    Returns empty set if node has no children.
    """
    if parentNode in _partOfChildrenByParent:
        kids = _partOfChildrenByParent[parentNode]
    else:
        kids = sets.Set()

    return kids



def getPartOfParentsForNode(childNode):
    """
    Return a set of part-of parents for the given node.
    Returns empty set if node has no parents.
    """
    if childNode in _partOfParentsByChild:
        parents = _partOfParentsByChild[childNode]
    else:
        parents = sets.Set()

    return parents


def getStageWindowForNodeOid(nodeOid):
    """
    Returns the stage records for the earliest and latest stages
    the given node exists at.

    Raises exception if nodeOid does not exist.
    """
    # This method belongs in the Nodes module, but the information needed
    # to implement it is in the TimedNodes module.

    return TimedNodes.getStageWindowForNodeOid(nodeOid)



def createNode(species, componentName, isGroup, publicId,
               description = None):
    """
    Create a new node record.
    """
    # Validate first
    if publicId in _byPublicId:
        Util.fatalError([
            "Attempt to create a node with public ID '" + publicId +
            " but node with that ID already exists."])

    node = AnaNodeDb.AnaNodeDbRecord()
    oid = Oids.createNextOid()

    node.setOid(oid)
    node.setSpecies(species)
    node.setComponentName(componentName)
    node.setIsGroup(isGroup)
    node.setPublicId(publicId)
    node.setDescription(description)

    node.insert()
    _addToKnowledge(node)

    return node



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
