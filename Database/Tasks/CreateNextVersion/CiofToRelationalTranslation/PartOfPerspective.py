#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Internal python structures to represent the derived part of perspective
information that allows us to quickly show the subset of the ANAD_PART_OF
tree that belongs to a perspective.
"""

import sets
import sys

import DbAccess
import PartOfDerived
import Perspective
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


TABLE = "ANAD_PART_OF_PERSPECTIVE"

# States when walking through ANAD_PART_OF to generate ANAD_PART_OF_PERSPECTIVE

ABOVE_START    = "ABOVE START"
IN_PERSPECTIVE = "IN PERSPECTIVE"
BELOW_STOP     = "BELOW STOP"


# ------------------------------------------------------------------
# PART OF PERSPECTIVE DERIVED
# ------------------------------------------------------------------

class PartOfPerspective:
    """
    Defines a record that is destined for the anatomy part of perspective
    derived table.

    This is a quick way to determine if an ANAD_PART_OF line should be shown
    for a given perspective.
    """
    def __init__(self, perspective, partOfDerived, isAncestor):
        """
        Create an anatomy part of perspective derived record for displaying node
        information within the context of a perspective.
        """
        self.__perspective = perspective
        self.__partOfDerived = partOfDerived
        self.__isAncestor = isAncestor
        self.__dbRecord = None

        if Util.debugging():
            Util.debugMessage([
                "Creating POP for Perspective " + perspective.getName() +
                " " + partOfDerived.getFullName()])

        return None

    def getOid(self):
        return None  # doesn't have one

    def getPerspective(self):
        return self.__perspective

    def getPerspectiveName(self):
        return self.__perspective.getName()

    def getPartOfDerived(self):
        return self.__partOfDerived

    def getPartOfDerivedOid(self):
        return self.__partOfDerived.getOid()

    def getNode(self):
        return self.__partOfDerived.getNode()

    def getNodeOid(self):
        return self.getNode().getOid()

    def isAncestor(self):
        """
        Returns True if this record is included only because it is an ancestor
        of a node that is in the perspective.  Such records are included to
        allow applications to show context for perspectives, if they so choose to.
        """
        return self.__isAncestor


    def isDeleted(self):
        """
        These items are derived from records in other tables, which have already
        been verified as not deleted.  Therefore, these items are never flagged
        as deleted.
        """
        return False


    def addToKnowledgeBase(self):
        """
        Inserts the anatomy part of perspective record into the knowledge
        base of all things we know about anatomy.
        """
        global _partOfPerspectives

        _partOfPerspectives.append(self)

        return None


    # --------------------------
    # Database Methods
    # --------------------------

    def setDbInfo(self, dbRecord):
        """
        Associates a DB record with this object, and vice versa.

        However, since this is a derived table, the passed in dbRecord had
        better always be None.
        """
        if dbRecord:
            Util.fatalError(["DbRecord passed to this routine must be None."])

        self.__dbRecord = DbAccess.DbRecord(pythonObject = self)

        return self.getDbRecord()


    def getDbRecord(self):
        return self.__dbRecord



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def __alterStack(partOfStack, partOf):
    """
    Got new part of record, modify the stack according the new part of's depth.
    """
    depth = partOf.getDepth()
    prevDepth = len(partOfStack) - 1
    if depth == prevDepth:
        # new node is sibling of previous one.  Replace it.
        partOfStack[depth] = partOf
        ruling = "Sibling"
    elif depth == prevDepth + 1:
        # new node is a child of previous one.  Append it.
        partOfStack.append(partOf)
        ruling = "Child"
    elif depth < prevDepth:
        # new node is a great uncle of previous one.
        del partOfStack[depth:]
        partOfStack.append(partOf)
        ruling = "great uncle"
    else:
        Util.fatalError([
            "Depth increased by more than one.",
            "Previous sequence: " +
            str(partOfStack[prevDepth].getDisplayRank()) +
            " Depth: " + str(prevDepth),
            "Current sequence: " + str(partOf.GetSequence()) +
            " Depth: " + str(depth),
            "Previous Path: ", partOfStack[prevDepth].getFullName(),
            "Current line:  ", partOf.getFullName()])

    if Util.debugging():
        Util.debugMessage([
            partOf.getFullName(),
            "is " + ruling + " to previoius component."])

    return depth




# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all part of perspective records
    """

    def __init__(self):
        global _partOfPerspectives
        self.__length = len(_partOfPerspectives)
        self.__position = -1         # Most recent POP returned
        return None

    def __iter__(self):
        return self

    def next(self):
        global _partOfPerspectives
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _partOfPerspectives[self.__position]




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def initialise():
    """
    Initialisation for the part of perspective derived table.
    """

    global _partOfPerspectives

    _partOfPerspectives = []                # unindexed

    tableInfo = DbAccess.registerClassTable(
        PartOfPerspective, TABLE, DbAccess.NOT_IN_ANA_OBJECT)
    # setup mappings betweeen python object values and db values
    tableInfo.addColumnMethodMapping(
        "POP_PERSPECTIVE_FK", "getPerspectiveName", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "POP_APO_FK", "getPartOfDerivedOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "POP_IS_ANCESTOR", "isAncestor", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "POP_NODE_FK",   "getNodeOid",   DbAccess.IS_NOT_KEY)

    return None




def derivePartOfPerspective():
    """
    Generate the anatomy part of perspective dervied table, which indentifies
    which ANAD_PART_OF records are in each perspective.
    """

    for perspective in Perspective.AllIter():
        print "processing Perspective", perspective.getName()
        alreadyProcessedNodes = sets.Set()
        partOfsByAncestorNode = {}
        partOfStack = []
        state = ABOVE_START
        startDepth = None
        stopDepth = None

        for partOf in PartOfDerived.AllIter():
            print "STATE:", state, "PROCESSING partOf:", partOf.getFullName()
            node = partOf.getNode()
            depth = partOf.getDepth()
            createPop = False
            isStart = partOf.isPrimaryPath() and perspective.hasStartNode(node)
            __alterStack(partOfStack, partOf)

            if state == ABOVE_START:
                if perspective.getName() == "Adult Kidney (GenePaint)": print 1
                if isStart:  # found a start node
                    if perspective.getName() == "Adult Kidney (GenePaint)": print 1.1
                    createPop = True
                    if not perspective.hasStopNode(node):
                        if perspective.getName() == "Adult Kidney (GenePaint)": print 1.2
                        startDepth = depth
                        state = IN_PERSPECTIVE

            elif state == IN_PERSPECTIVE:
                if perspective.getName() == "Adult Kidney (GenePaint)": print 2
                if (depth <= startDepth and
                    not perspective.hasStartNode(node)):
                    if perspective.getName() == "Adult Kidney (GenePaint)": print 2.1
                    # no longer under a start node
                    state = ABOVE_START
                else: # still under a start node
                    if perspective.getName() == "Adult Kidney (GenePaint)": print 2.2
                    createPop = True
                    if (perspective.hasStartNode(node) and
                        depth < startDepth):
                        if perspective.getName() == "Adult Kidney (GenePaint)": print 2.3
                        # Under a new start node higher up
                        startDepth = depth
                        alreadyProcessedNodes.add(node)
                    elif perspective.hasStopNode(node):
                        if perspective.getName() == "Adult Kidney (GenePaint)": print 2.4
                        # below both a start and stop node
                        state = BELOW_STOP
                        stopDepth = depth

            elif state == BELOW_STOP:
                if perspective.getName() == "Adult Kidney (GenePaint)": print 3
                if depth <= stopDepth:
                    if perspective.getName() == "Adult Kidney (GenePaint)": print 3.1
                    # no longer below old stop node
                    if depth > startDepth:
                        if perspective.getName() == "Adult Kidney (GenePaint)": print 3.2
                        # but still under same start node
                        createPop = True
                        if not perspective.hasStopNode(node):
                            if perspective.getName() == "Adult Kidney (GenePaint)": print 3.3
                            state = IN_PERSPECTIVE
                    else:
                        # above or next to old start node
                        if (isStart):
                            if perspective.getName() == "Adult Kidney (GenePaint)": print 3.4
                            createPop = True
                            if not perspective.hasStopNode(node):
                                if perspective.getName() == "Adult Kidney (GenePaint)": print 3.5
                                startDepth = depth
                                state = IN_PERSPECTIVE

            if createPop:
                if perspective.getName() == "Adult Kidney (GenePaint)": print 4
                pop = PartOfPerspective(perspective, partOf, False)
                pop.addToKnowledgeBase()
                if partOf.isPrimaryPath():
                    if perspective.getName() == "Adult Kidney (GenePaint)": print 4.1
                    alreadyProcessedNodes.add(node)

            if isStart:
                if perspective.getName() == "Adult Kidney (GenePaint)": print 5
                print "Found primary occurrence of start node", node.getName()
                # Found primary occurrence of a start node in ANAD_PART_OF.
                # Record all ancestor nodes for later processing.
                for stackIdx in range(len(partOfStack)-2, -1, -1):
                    ancesNode = partOfStack[stackIdx].getNode()
                    partOfsByAncestorNode[ancesNode] = partOfStack[stackIdx]


        # Add ancestors if they aren't already in as non-ancestors
        for node, partOf in partOfsByAncestorNode.items():
            if node not in alreadyProcessedNodes:
                pop = PartOfPerspective(perspective, partOf, True)
                pop.addToKnowledgeBase()
                alreadyProcessedNodes.add(node) # doesn't really matter

    return None

# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above.

