#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Defines a graph for representing the anatomy.

This graph can be extended by subclasses to support annotation
and then reasoning and propagating that annotation with the graph.
"""

import sets

from hgu.anatomyDb.version010 import Nodes
from hgu.anatomyDb.version010 import Relationships
# from hgu.anatomyDb.version010 import Stages
# from hgu.anatomyDb.version010 import TimedNodes
# from hgu import Util


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# GLOBALS - initialised when module is loaded
# ------------------------------------------------------------------


# These two should really be embedded in Expression and ExpressionNote modules
# but I don't feel like creating them just yet.

_lastExpressionOid = None
_lastExpressionNoteOid = None


# ------------------------------------------------------------------
# ANATOMY ANNOTATION GRAPH
# ------------------------------------------------------------------

class AnatomyGraph:
    """
    Used to represent the anatomy as a graph.
    """

    def __init__(self):
        """
        Create an anatomy graph.  The Anatomy module must have been
        initialised prior to creating the anatomy graph.
        """
        # Define graph
        self.__graphByNodeOid = {}
        self.__root = None

        # Populate it.
        self.__buildGraph()

        return None



    def __buildGraph(self):
        """
        Build the anatomy graph.
        """
        # Start at the root and walk downward.  Do a depth first walk
        # of the graph because that way the recursion takes care of the
        # stack and we don't have to.

        self.__root = self.__addToGraph(None, Nodes.getRoot())

        return



    def __addToGraph(self, parentGraphNode, childNode):
        """
        Adds this parent-child relationship to the graph.
        If a graph node does not yet exist for the childNode then
        create one.
        """

        childOid = childNode.getOid()

        # Create graph node for child, if it is not already in the graph
        if childOid not in self.__graphByNodeOid:
            childGraphNode = GraphNode(childNode, self)
            self.__graphByNodeOid[childOid] = childGraphNode
            addGrandChildren = True
        else:
            childGraphNode = self.__graphByNodeOid[childOid]
            addGrandChildren = False

        # Add parent child relationship
        if parentGraphNode:
            parentGraphNode.addChild(childGraphNode)
            childGraphNode.addParent(parentGraphNode)

        # If the child was just added to the graph, then add its kids as well.
        if addGrandChildren:
            for rel in Relationships.getByParentOidRelType(
                          childOid, Relationships.PART_OF):
                grandChild = Nodes.getByOid(rel.getChildOid())
                self.__addToGraph(childGraphNode, grandChild)

        return childGraphNode

    def getRoot(self):
        """
        Return the root GraphNode of the graph.
        """
        return self.__root



    def spewDepthFirstTraversal(self):
        """
        Print the graph using a depth first traversal.

        Note that
        1. There are problems with non-ASCII characters.
        2. Siblings are not displayed in any particular order.
        3. This method is intended to support debugging.  See the separate
           Tree Report program to get a really quite charming and entirely
           correct tree display of the graph.
        """
        self.__root.spewDepthFirstTraversal(0)

        return


# ------------------------------------------------------------------
# GRAPH ITERATORS
# ------------------------------------------------------------------

class BreadthFirstIterator:
    """
    A Breadth First iterator traverses the graph in node dependency order.
    That is, it will only return nodes that have already had all of their
    ancestor nodes returned by previous calls to the iterator.

    If multiple nodes could be returned then the order in which they will be
    returned is not defined.
    """

    def __init__(self, graph):
        """
        Create a breadth first iterator to walk a graph in node dependency order.
        """
        self.__graph = graph
        self.__processedGraphNodes = sets.Set()
        self.__readyQueue = [graph.getRoot()]

        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Returns a graphnode that has already had all of its ancestors returned
        by previous calls to the iterator.
        """
        # Get next node in ready queue, add any of its children that are ready
        # to the ready queue, and then return the node.

        if len(self.__readyQueue) > 0:
            graphNode = self.__readyQueue.pop(0)
            self.__processedGraphNodes.add(graphNode)

            for child in graphNode.getChildren():
                if child.getParents().issubset(self.__processedGraphNodes):
                    # All child's parents have been processed; add child to ready Q
                    self.__readyQueue.append(child)

            return graphNode
        else:
            raise StopIteration





# ------------------------------------------------------------------
# GRAPH NODE
# ------------------------------------------------------------------

class GraphNode:
    """
    Wrapper around nodes that adds knowledge of parents and children.
    """

    def __init__(self, node, graph):
        """
        Create a graph node, which is just a node plus knowledge of
        its parents and children.
        """
        self.__node = node
        self.__graph = graph
        self.__children = sets.Set()
        self.__parents  = sets.Set()

        return

    def getNode(self):
        """
        Return the node record for this graph node.
        """
        return self.__node

    def getOid(self):
        """
        Return the OID of the node this graph node is for.
        """
        return self.getNode().getOid()

    def getPublicId(self):
        """
        Return the public ID (EMAPA ID) of the node this graph node is
        for.
        """
        return self.getNode().getPublicId()

    def getName(self):
        """
        Return the name of this node.
        """
        return self.getNode().getComponentName()

    def getChildren(self):
        """
        Return the children of this node as a set (in no particular order).
        """
        return self.__children

    def getParents(self):
        """
        Return the parents of this node as a set (in no particualr order).
        """
        return self.__parents


    def addChild(self, childGraphNode):
        """
        Add a child to this node's list of children.
        """
        self.__children.add(childGraphNode)
        return


    def addParent(self, parentGraphNode):
        """
        Add a parent to this node's list of parents.
        """
        self.__parents.add(parentGraphNode)
        return



    def spew(self, label=None):
        """
        Print the graph node to stdout.  This is useful for debugging.
        """
        if label:
            printLabel = label + " "
        else:
            printLabel = ""
        format = "%s %11s %s"
        print format % (printLabel, self.getPublicId(), self.getName())
        return



    def spewDepthFirstTraversal(self, depth):
        """
        Print this graph node, and then print all of its children in
        no particular order.  This displays the graph rooted at this
        node as a tree.

        This method is intended to support debugging.  See the separate
        Tree Report program to get a really quite charming and entirely
        correct tree display of the graph.
        """
        indent = ". " * depth

        format = "%11s %s%s"
        print format % (self.getPublicId(), indent, self.getName())
        for child in self.getChildren():
            child.printDepthFirstTraversal(depth+1)

        return




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


