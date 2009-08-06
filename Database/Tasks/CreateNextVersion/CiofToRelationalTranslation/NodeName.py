#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************


Internal python structures to represent anatomy node names
"""


import sets                             # builtin in Python 2.4

import Node
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_nodeNamesByNameReduced = None



# ------------------------------------------------------------------
# NODE NAMES
# ------------------------------------------------------------------

class NodeName:

    """
    Each anatomy node has a name, which is the last name in
    its full path.

    Node names are not first class objects in
    anatomy databases per se.  This class only exists as an
    afterthought to support the analysis of nomenclature in the database
    after the whole knowledge base has been built.
    """
    def __init__(self, anatomyNode):
        """
        Initialise a node name object given the node.
        """
        self.__node = anatomyNode
        self.__name = anatomyNode.getName()
        self.__nameReduced = Util.reduceName(self.__name)

        return None

    def getNode(self):
        """
        Return the node this node name object is for.
        """
        return self.__node

    def getName(self):
        """
        Return the name of this node, unaltered.
        """
        return self.__name

    def getNameReduced(self):
        """
        Return the reduced name of this node.  Reduced names are all in
        one case and have punctuation stripped out.
        """
        return self.__nameReduced


    def addToKnowledgeBase(self):
        """
        Inserts the node into the node name knowledge base
        """
        nodeName = self.getName()
        nodeNameReduced = self.getNameReduced()

        if nodeName in _nodeNamesByName:
            _nodeNamesByName[nodeName].append(self)
        else:
            _nodeNamesByName[nodeName] = [self]

        if nodeNameReduced in _nodeNamesByNameReduced:
            _nodeNamesByNameReduced[nodeNameReduced].append(self)
        else:
            _nodeNamesByNameReduced[nodeNameReduced] = [self]

        _nodeNames.append(self)

        return None



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all node names
    """

    def __init__(self):
        self.__length = len(_nodeNames)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return next node name object.
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _nodeNames[self.__position]



# ------------------------------------------------------------------
# CLASS LEVEL METHODS
# ------------------------------------------------------------------

def printNodeNames():
    """
    List node names in alphabetical order
    """
    reducedNames = _nodeNamesByNameReduced.keys()
    reducedNames.sort()
    for reducedName in reducedNames:
        prevName = None
        for nodeName in _nodeNamesByNameReduced[reducedName]:
            name = nodeName.getName()
            if prevName == None or prevName != name:
                print name
                prevName = name

    return None


def report():
    """
    Report node names that might need further investigation
    """
    reducedNames = _nodeNamesByNameReduced.keys()
    reducedNames.sort()
    for reducedName in reducedNames:
        prevName = None
        prevNode = None
        for nodeName in _nodeNamesByNameReduced[reducedName]:
            name = nodeName.getName()
            if prevName != None and prevName != name:
                node = nodeName.getNode()
                Util.warning([
                    "Component names differ only in capitalisation or "
                    "punctuation.",
                    "May want to make them identical names.",
                    "  '" + prevName + "' " + prevNode.getPublicId() + "'",
                    "       Stages: [" + prevNode.genTimedNodesString() + "]",
                    "  '" + name + "' " + node.getPublicId() + "'",
                    "       Stages: [" + node.genTimedNodesString() + "]"])
            prevName = name
            prevNode = nodeName.getNode()

    return None



def genNodeNames():
    """
    Generate a full list of node names from all anatomy nodes.
    """
    nodeIter = Node.AllIter()

    for node in nodeIter:
        if not node.isDeleted():
            nodeName = NodeName(node)
            nodeName.addToKnowledgeBase()
    return None



def getByReducedName(nodeReducedName):
    """
    Return list of node names that all have the same reduced name.
    The input name is assumed to already be reduced.
    """
    if nodeReducedName in _nodeNamesByNameReduced:
        return _nodeNamesByNameReduced[nodeReducedName]
    else:
        return []


def genReducedNameSet():
    """
    Generate set containing all reduced names.

    The returned set is then owned by the caller.
    """
    return sets.Set(_nodeNamesByNameReduced.keys())


# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.

_nodeNamesByName = {}          # Indexed by name, unique
_nodeNamesByNameReduced = {}   # Indexed by lower case, no punc name
_nodeNames = []                # Unindexed

