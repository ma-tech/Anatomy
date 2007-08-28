#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Internal python structures to represent the derived part of relationship
information that allows us to transform the graph into a tree for display
purposes.
"""

import DbAccess
import Node                             # untimed anatomy
import Relationship
import RelationshipType
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


TABLE = "ANAD_PART_OF"

ALPHABETICAL_SORT_ORDER  = "alphabetical"
CHRONOLOGICAL_SORT_ORDER = "chronological"
DEFINED_SORT_ORDERS = [ALPHABETICAL_SORT_ORDER, CHRONOLOGICAL_SORT_ORDER]



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_derivedPartOfs = None



# ------------------------------------------------------------------
# ANATOMY DISPLAY
# ------------------------------------------------------------------

class PartOfDerived:
    """
    Defines a record that is destined for the anatomy part of derived table.

    This says where and how in the tree the anatomy should be displayed.
    """
    def __init__(self, node, parentPod, rank, indentDepth,
                 isPrimaryPath):
        """
        Create an anatomy part of derived record for displaying node
        information.
        """
        self.__oid = rank
        self.__node = node
        if parentPod:
            self.__fullName = parentPod.getFullName() + "." + node.getName()
        else:
            self.__fullName = self.getNode().getName()
        self.__parentApo = parentPod
        self.__displayRank = rank
        self.__depth = indentDepth
        self.__isPrimaryPath = isPrimaryPath
        self.__dbRecord = None

        # Gather stage range of anatomy node
        self.__nodeStartStage, self.__nodeEndStage = self.getNode().getStageWindow()

        # Path start and end stages may be narrower than node start and
        # end stages.  Used to reason about what parts of the tree to display
        # for what stages.
        self.__pathStartStage = self.getNodeStartStage()
        self.__pathEndStage   = self.getNodeEndStage()
        if parentPod:
            parentPathStartStage = parentPod.getPathStartStage()
            parentPathEndStage   = parentPod.getPathEndStage()
            if self.__pathStartStage.getSequence() < parentPathStartStage.getSequence():
                self.__pathStartStage = parentPathStartStage
            if self.__pathEndStage.getSequence() > parentPathEndStage.getSequence():
                self.__pathEndStage = parentPathEndStage
        if self.getPathStartStage().getSequence() > self.getPathEndStage().getSequence():
            Util.warning(self.genDumpFields())
            Util.fatalError(["Bad path stage range"])
        return None

    def getOid(self):
        """
        Get OID of this record.
        """
        return self.__oid

    def getNode(self):
        """
        Get the node this line is for.
        """
        return self.__node

    def getNodeOid(self):
        """
        Get the OID of the node tis line is for.
        """
        return self.getNode().getOid()

    def getSpecies(self):
        """
        Get the species of the node this line is for.
        """
        return self.getNode().getSpecies()


    def getFullName(self):
        """
        Return the full path to this node in the tree.
        """
        return self.__fullName

    def getDisplayRank(self):
        """
        Return the absolute display rank of this record.
        """
        return self.__displayRank

    def getDepth(self):
        """
        How deep in the tree is the record.
        """
        return self.__depth

    def getNodeStartStage(self):
        """
        At what stage does this node start?
        """
        return self.__nodeStartStage

    def getNodeEndStage(self):
        """
        At what stage does this node stop?
        """
        return self.__nodeEndStage

    def getNodeStartStageOid(self):
        """
        Return the OID of this node's start stage.
        """
        return self.getNodeStartStage().getOid()

    def getNodeEndStageOid(self):
        """
        Return the OID of this node's end stage.
        """
        return self.getNodeEndStage().getOid()


    def getPathStartStage(self):
        """
        Return the start stage of this node in its current path.
        This can be later than the node's start stage.
        """
        return self.__pathStartStage

    def getPathEndStage(self):
        """
        Return the end stage of this node in its current path.
        This can be earlier than the node's start stage.
        """
        return self.__pathEndStage

    def getPathStartStageOid(self):
        """
        Return the OID of the start stage of this node in its current path.
        """
        return self.getPathStartStage().getOid()

    def getPathEndStageOid(self):
        """
        Return the OID of the end stage of this node in its current path.
        """
        return self.getPathEndStage().getOid()

    def isPrimaryPath(self):
        """
        Return True if this is the primary path to this node.  That means that
        none of the nodes along the way to this one are groups.
        """
        return self.__isPrimaryPath

    def getParentApoOid(self):
        """
        Return the OID of the parent record in the display tree.
        """
        parentApoOid = None
        if self.__parentApo:
            parentApoOid = self.__parentApo.getOid()
        return parentApoOid

    def isDeleted(self):
        """
        Always returns false.

        Objects are not generated for deleted nodes.
        """
        return False


    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fiels are returned as a list of strings.
        """
        return [None, self.getSpecies(),
                str(self.getNodeStartStageOid()),
                str(self.getNodeEndStageOid()),
                str(self.getPathStartStageOid()),
                str(self.getPathEndStageOid()),
                str(self.getNodeOid()), str(self.getDisplayRank()),
                str(self.getDepth()), self.getFullName(),
                str(Util.boolToInt(self.isPrimaryPath()))]


    def addToKnowledgeBase(self):
        """
        Inserts the anatomy display record into the knowledge
        base of all things we know about anatomy.
        """
        _derivedPartOfs.append(self)

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
            Util.warning(self.genDumpFields())
            Util.fatalError(["DbRecord passed to this routine must be None."])

        self.__dbRecord = DbAccess.DbRecord(pythonObject = self)

        return self.getDbRecord()

    def getDbRecord(self):
        """
        Return DB record for this object.
        """
        return self.__dbRecord



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _processNode(node, parentPod, rank, depth, isPrimaryPath, sortOrder):
    """
    Generate records for an anatomy node, and all its children.
    """

    if Util.debugging():
        Util.debugMessage([
            "In _processnode.",
            "Node: " + node.getPublicId() + " '" + node.getName() + "'",
            "Rank: " + str(rank) + "  Depth: " + str(depth)])

    # Generate record for this node.
    anatPod = PartOfDerived(node, parentPod, rank, depth,
                            isPrimaryPath)
    anatPod.addToKnowledgeBase()
    rank += 1

    # Generate records, recursively, depth first, left to right, for all
    # its children.

    childIsPrimary = isPrimaryPath and node.isPrimary()
    allTypes = Relationship.getUndeletedByParentPublicId(node.getPublicId())
    unsorted = Relationship.filterByRelationshipType(
        allTypes,
        RelationshipType.getByName(RelationshipType.PART_OF))

    if sortOrder == ALPHABETICAL_SORT_ORDER:
        childRels = Relationship.sortByChildSeqNameStageSequence(unsorted)
    elif sortOrder == CHRONOLOGICAL_SORT_ORDER:
        childRels = Relationship.sortByChildSeqStageSequenceName(unsorted)
    else:
        Util.fatalError(["Undefined abstract sort order: " + sortOrder])

    for childRel in childRels:
        if not childRel.isDeleted():
            child = childRel.getChild()
            # Filter out children that are outside current path's stage range
            childStartStage, childEndStage = child.getStageWindow()
            childStart = childStartStage.getSequence()
            childEnd   = childEndStage.getSequence()
            pathStart  = anatPod.getPathStartStage().getSequence()
            pathEnd    = anatPod.getPathEndStage().getSequence()
            if (   (childStart >= pathStart and childStart <= pathEnd)
                or (childEnd   >= pathStart and childEnd   <= pathEnd)
                or (childStart <  pathStart and childEnd   >  pathEnd)):
                rank = _processNode(child, anatPod, rank, depth + 1,
                                    childIsPrimary, sortOrder)

    return rank



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all anatomy displays
    """

    def __init__(self):
        self.__length = len(_derivedPartOfs)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return next anatomy display record.
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _derivedPartOfs[self.__position]




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def initialise():
    """
    Initialisation for the anatomy part of derived table.
    """

    global _derivedPartOfs

    _derivedPartOfs = []                # unindexed

    tableInfo = DbAccess.registerClassTable(
        PartOfDerived, TABLE, DbAccess.NOT_IN_ANA_OBJECT)
    # setup mappings betweeen Ciof values and db values
    tableInfo.addColumnMethodMapping(
        "APO_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_NODE_START_STAGE_FK", "getNodeStartStageOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_NODE_END_STAGE_FK",   "getNodeEndStageOid",   DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_PATH_START_STAGE_FK", "getPathStartStageOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_PATH_END_STAGE_FK",   "getPathEndStageOid",   DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_SPECIES_FK", "getSpecies", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_NODE_FK", "getNodeOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_SEQUENCE", "getDisplayRank", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_DEPTH", "getDepth", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_FULL_PATH", "getFullName", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_IS_PRIMARY", "isPrimaryPath", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_IS_PRIMARY_PATH", "isPrimaryPath", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "APO_PARENT_APO_FK", "getParentApoOid", DbAccess.IS_NOT_KEY)

    return None


def derivePartOf(abstractSortOrder):
    """
    Generate the anatomy part of dervied table, an ordered inidented
    list of all parts, showing all full paths to all parts.
    """
    # Anatomy relationships form a directed acyclic graph (DAG)
    # This routine renders that DAG into a tree for
    # quick and easy display of the information.

    # Walk the tree depth first, left to right
    _processNode(Node.getRoot(), None, 0, 0, True, abstractSortOrder)

    return None


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above.

