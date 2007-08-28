#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Internal python structures to represent anatomy relationships.
Anatomy relationships exist between 2 anatomy nodes or between 2
anatomy timed nodes.
"""

import sets

import AnatomyObject
import DbAccess
import RelationshipSequenceStream
import RelationshipType
import TimedNode                        # timed anatomy
import Util                             # Error handling
import Version


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# DB related

TABLE = "ANA_RELATIONSHIP"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_relationshipsByChildAndParentPublicId = None
_undeletedRelationshipsByChildPublicId = None
_undeletedRelationshipsByParentPublicId = None
_relationships = None


# ------------------------------------------------------------------
# RELATIONSHIP
# ------------------------------------------------------------------

class Relationship:
    """
    Defines a binary, directional (parent-child) relationship between
    either two anatomy timed nodes, or two anatomy nodes
    """

    def __init__(self, relType = None, child = None, parent = None,
                 lineageLinkEntity = None):
        """
        Generate an anatomy relationship  between two nodes or between
        two timed nodes.

        This can be created in one of two ways.
        Either the explicit description of a relationship can be passed in,
        or a LineageLinkEntity from the CIOF file.
        """
        if ((lineageLinkEntity and not (relType or child or parent)) or
            (not lineageLinkEntity and relType and child and parent)):
            pass
        else:
            Util.fatalError(["Invalid combination of parameters to " +
                             "Relationship constructor.",
                             "Rel Type: " + str(relType),
                             "child:    " + str(child),
                             "parent:   " + str(parent)])

        # Create relationship
        self.__ciofEntity = lineageLinkEntity
        self.__dbRecord      = None
        self.__anatomyObject = None
        self.__sequence      = None
        self.__isDeleted     = False

        if (lineageLinkEntity):
            parentId = "EMAP:" + self.__ciofEntity.getAttributeValue("From")
            childId  = "EMAP:" + self.__ciofEntity.getAttributeValue("To")
            self.__parent = TimedNode.getByPublicId(parentId)
            self.__child  = TimedNode.getByPublicId(childId)
            self.__creationDateTime = self.__ciofEntity.getCreationDateTime()

            # Use derives-from, even though in some cases, the path is merely
            # different, or it is transformation-of.
            self.__relationshipType = RelationshipType.getByName(
                RelationshipType.DERIVES_FROM)
        else:
            self.__relationshipType = relType
            self.__child = child
            self.__parent = parent

            # Most relationships don't have their own explicit creation time
            # in CIOF files.  Use the greater of the creation dates of the
            # parent and child.
            childCdt = self.__child.getCreationDateTime()
            parentCdt = self.__parent.getCreationDateTime()
            self.__creationDateTime = max(childCdt, parentCdt)

        return None

    def assignOid(self):
        """
        Assign an OId to this object.
        """
        self.__anatomyObject = AnatomyObject.AnatomyObject(
            self, creationDateTime = self.__creationDateTime)
        self.__anatomyObject.addToKnowledgeBase()

        return None

    def getOid(self):
        """
        Chicanery necessary because deleted relationships won't have
        an OID.
        """
        if self.__anatomyObject:
            return self.__anatomyObject.getOid()
        else:
            return None

    def getAnatomyObject(self):
        """
        Return the anatomy object associated with this relationship.
        """
        return self.__anatomyObject

    def getRelationshipType(self):
        """
        What type of relationship is this?
        """
        return self.__relationshipType

    def getRelationshipTypeName(self):
        """
        Return the name of the relationship type.
        """
        return self.__relationshipType.getName()

    def getChild(self):
        """
        Return the child object in this relationship.
        """
        return self.__child

    def getChildOid(self):
        """
        Return the OID of the child in this relationship.
        """
        return self.__child.getOid()

    def getParent(self):
        """
        Return the parent object in this relationship.
        """
        return self.__parent

    def getParentOid(self):
        """
        Return the OID of the parent in this relationship.
        """
        return self.__parent.getOid()

    def getSequence(self):
        """
        Return the sequence of the child when it is displayed relative
        to its parent's other children.
        """
        return self.__sequence

    def setSequence(self, sequence):
        """
        Set the sequnce that the child should be dipslayed in, relative to
        its parent's other children.
        """
        self.__sequence = sequence
        return self.__sequence

    def isDeleted(self):
        """
        Determine if this relationship is deleted.

        Possible that parent and or child are deleted.
        """
        # Once a relationship is flagged as delete, it never again becomes
        # undeleted
        if not self.__isDeleted:
            child = self.getChild()
            parent = self.getParent()

            if parent.isDeleted() or child.isDeleted():
                self.setDeleted(True)
                # Took out this warning because it is just the way the CIOF
                # file is set up.
                # Util.warning([
                #    "Detected a relationship between a deleted parent and/or child",
                #    "Parent Node " + parent.getPublicId(),
                #    "Child Node  " + child.getPublicId()])

        return self.__isDeleted


    def setDeleted(self, isDeleted):
        """
        Flag this relationship as deleted.
        """
        self.__isDeleted = isDeleted
        if isDeleted:
            # If it is now marked as deleted then drop it from undeleted lists.
            childPublicId = self.getChild().getPublicId()
            childRels = _undeletedRelationshipsByChildPublicId.get(childPublicId)
            if childRels != None:
                if self in childRels:
                    childRels.remove(self)

            parentPublicId = self.getParent().getPublicId()
            parentRels = _undeletedRelationshipsByParentPublicId.get(parentPublicId)
            if parentRels != None:
                if self in parentRels:
                    parentRels.remove(self)

        return self.__isDeleted


    def genDumpFields(self):
        """
        Generate all fields from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fields are returned as a list of strings.
        """
        childOid  = self.getChild().getOid()
        parentOid = self.getParent().getOid()

        return [str(self.getOid()),
                self.getRelationshipTypeName(),
                str(childOid), str(parentOid)]


    def addToKnowledgeBase(self):
        """
        Inserts the anatomy relationship into the knowledge
        base of all things we know about anatomy.
        """
        # Code does not detect duplicate insertions.

        childPublicId = self.getChild().getPublicId()
        parentPublicId = self.getParent().getPublicId()
        happyFamily = _makeKeyToAHappyFamily(childPublicId = childPublicId,
                                             parentPublicId = parentPublicId)
        if happyFamily in _relationshipsByChildAndParentPublicId:
            _relationshipsByChildAndParentPublicId[happyFamily].append(self)
        else:
            _relationshipsByChildAndParentPublicId[happyFamily] = [self]

        if not self.isDeleted():
            if childPublicId in _undeletedRelationshipsByChildPublicId:
                _undeletedRelationshipsByChildPublicId[childPublicId].append(self)
            else:
                _undeletedRelationshipsByChildPublicId[childPublicId] = [self]

            if parentPublicId in _undeletedRelationshipsByParentPublicId:
                _undeletedRelationshipsByParentPublicId[parentPublicId].append(self)
            else:
                _undeletedRelationshipsByParentPublicId[parentPublicId] = [self]

        _relationships.append(self)

        return None



    def spew(self, label=""):
        """
        debugging routine.  Displays contents of relationship
        """
        print "Relationship:", label
        print "Rel Type:", self.getRelationshipTypeName()
        print "Parent : ", self.getParent().getPublicId()
        print "Child  : ", self.getChild().getPublicId()
        print "IsDeleted : ", self.isDeleted()
        return None

    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        """
        Return the DB record for this relationship.
        """
        return self.__dbRecord

    def setDbInfo(self, dbRecord):
        """
        Associates a DB record with this object, and vice versa,
        and looks up the AnatomyObject for this relationship.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
            self.assignOid()
        else:
            # we have a db record, means we also have an AnatomyObject
            dbRecord.bindPythonObject(self)
            oid = dbRecord.getColumnValue("REL_OID")
            self.__anatomyObject = AnatomyObject.bindAnatomyObject(oid, self)
            # Sequence may be overridden later if and when we read sequence file
            self.__sequence = dbRecord.getColumnValue("REL_SEQUENCE")
        self.__dbRecord = dbRecord

        return dbRecord



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


def _makeKeyToAHappyFamily(childPublicId, parentPublicId):
    """
    This routine is stupid.  Just replace it with a tuple.
    """
    return childPublicId + ":" + parentPublicId


def _childSeqCompare(left, right):
    """
    Compare two anatomy relationships based on their sequence.
    Null sequence values sort last.
    """
    if left.getParent() != right.getParent():
        Util.fatalError([
            "Comparing child component names of two anatomy relationships ",
            "that have different parents."])
    leftSeq = left.getSequence()
    rightSeq = right.getSequence()

    if leftSeq == None:
        if rightSeq == None:
            return 0
        else:
            return -1
    elif rightSeq == None:
        return +1
    elif leftSeq < rightSeq:
        return -1
    elif leftSeq > rightSeq:
        return +1
    else:
        return 0



def _childNameCompare(left, right):
    """
    Compare two anatomy relationships based on their child component name
    """
    if left.getParent() != right.getParent():
        Util.fatalError([
            "Comparing child component names of two anatomy relationships ",
            "that have different parents."])
    leftCompName = left.getChild().getName().lower()
    rightCompName = right.getChild().getName().lower()
    if leftCompName < rightCompName:
        return -1
    elif leftCompName > rightCompName:
        return +1
    else:
        # can have same name if parent is a group
        return 0


def _childStageWindowCompare(left, right):
    """
    Compare two anatomy relationships based on their child stage ranges.
    """
    sortOrder = None
    if left.getParent() != right.getParent():
        Util.fatalError([
            "Comparing two anatomy relationships ",
            "that have different parents."])
    leftStartStage,  leftEndStage  = left.getChild().getStageWindow()
    rightStartStage, rightEndStage = right.getChild().getStageWindow()

    # First compare start stage
    leftStartSeq  = leftStartStage.getSequence()
    rightStartSeq = rightStartStage.getSequence()
    if leftStartSeq < rightStartSeq:
        sortOrder = -1
    elif leftStartSeq > rightStartSeq:
        sortOrder = +1
    else:
        # Next compare end stage
        leftEndSeq  = leftEndStage.getSequence()
        rightEndSeq = rightEndStage.getSequence()
        if leftEndSeq < rightEndSeq:
            sortOrder = -1
        elif leftEndSeq > rightEndSeq:
            sortOrder = +1
        else:
            sortOrder = 0

    return sortOrder



def _childStageSequenceNameCompare(left, right):
    """
    Compare two anatomy relationships based on the stage
    windows, and then be component names.

    The rules
      1. Sort by start stage, earliest first
      2. Break ties with end stage, earliest first
      3. Break ties on component name
    """
    sortOrder = _childStageWindowCompare(left, right)
    if not sortOrder:
        sortOrder = _childNameCompare(left, right)

    return sortOrder



def _childNameStageSequenceCompare(left, right):
    """
    Compare two anatomy relationships based on component names, and
    then stage windows.

    The rules
      1. Sort by component name
      2. Break ties with start stage, earliest first
      3. Break ties with end stage, earliest first
    """
    # Try component name
    sortOrder = _childNameCompare(left, right)

    if not sortOrder: # names are the same
        sortOrder = _childStageWindowCompare(left, right)

    return sortOrder



def _childSeqNameStageSequenceCompare(left, right):
    """
    Compare two anatomy relationships based on relationship seuquence,
    ccomponent names, and then stage windows.

    The rules
      1. Sort by relationship seuqence (nulls are last)
      2. Break ties with component name
      3. Break ties with start stage, earliest first
      4. Break ties with end stage, earliest first
    """
    sortOrder = _childSeqCompare(left, right)
    if not sortOrder:

        # Try component name
        sortOrder = _childNameCompare(left, right)

        if not sortOrder: # names are the same
            sortOrder = _childStageWindowCompare(left, right)

    return sortOrder




def _childSeqStageSequenceNameCompare(left, right):
    """
    Compare two anatomy relationships based on relationship sequence,
    stage windows, and then be component names.

    The rules
      1. Sort by relationship seuqence (nulls are last)
      2. Sort by start stage, earliest first
      3. Break ties with end stage, earliest first
      4. Break ties on component name
    """
    sortOrder = _childSeqCompare(left, right)
    if not sortOrder:

        # Try stage window
        sortOrder = _childStageWindowCompare(left, right)

        if not sortOrder: # Stage windows are same
            sortOrder = _childNameCompare(left, right)

    return sortOrder

# ------------------------------------------------------------------
# ANATOMY RELATIONSHIP ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all anatomy relationships, inlcuding deleted ones.
    """

    def __init__(self):
        self.__length = len(_relationships)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Get the next relationship.
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _relationships[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def initialise():
    """
    Initialisation for Anatomy Relationships.
    """
    global _relationshipsByChildAndParentPublicId
    global _undeletedRelationshipsByChildPublicId
    global _undeletedRelationshipsByParentPublicId, _relationships

    # Lists of all known relationships
    _relationshipsByChildAndParentPublicId = {}
    _undeletedRelationshipsByChildPublicId = {}
    _undeletedRelationshipsByParentPublicId = {}
    _relationships = []          # unindexed

    tableInfo = DbAccess.registerClassTable(Relationship, TABLE,
                                            DbAccess.IN_ANA_OBJECT)

    # setup mappings betweeen Ciof values and db values
    tableInfo.addColumnMethodMapping(
        "REL_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "REL_RELATIONSHIP_TYPE_FK", "getRelationshipTypeName", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "REL_CHILD_FK", "getChildOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "REL_PARENT_FK", "getParentOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "REL_SEQUENCE", "getSequence", DbAccess.IS_NOT_KEY)

    return None


def readDb():
    """
    Read in relationship records from the database.
    """
    allRels = DbAccess.readClassAll(Relationship)

    for relRec in allRels:
        childOid    = relRec.getColumnValue("REL_CHILD_FK")
        parentOid   = relRec.getColumnValue("REL_PARENT_FK")
        relTypeName = relRec.getColumnValue("REL_RELATIONSHIP_TYPE_FK")
        relType     = RelationshipType.getByName(relTypeName)
        child  = AnatomyObject.getByOid(childOid).getObjectThisIsFor()
        parent = AnatomyObject.getByOid(parentOid).getObjectThisIsFor()

        # if parent or child does not exist, indicates it was dropped
        # in this revision.  Therefore, also drop the relationship.
        # :TODO: Still need to figure out what objects the relationship is
        # for.
        isOrphan = False
        if child and parent:
            childPublicId  = child.getPublicId()
            parentPublicId = parent.getPublicId()

            rels = getByChildAndParentPublicIds(childPublicId, parentPublicId)
            rel = filterByRelationshipType(rels, relType)
            if len(rel) == 1:
                rel[0].setDbInfo(relRec)
            elif len(rel) == 0:
                isOrphan = True
            else:
                Util.fatalError([
                    str(len(rel)) + " relationships found when 0 or 1 expected."])
        else:
            isOrphan = True

        if isOrphan:
            # DB record is an orphan.  It does not have a partner entry
            # in the CIOF
            relRec.registerAsOrphan()

    return None



def readSequenceFromFile(sequenceFile):
    """
    Read in the sequence the tree is supposed to be displayed in.

    sequenceFile: Name of file containing sequence information.  See below
        for how this is produced.

    See the comments at the top of translateCiof.py for this is a giant
    hack for a number of reasons.
    """

    # Open file and read in header.
    sequenceStream = RelationshipSequenceStream.RelationshipSequenceStream(
                                                                   sequenceFile)

    # make sure file is for right version.
    version = Version.getVersion()
    if (sequenceStream.getVersionNumber() != version.getVersionNumber() or
        sequenceStream.getDateTime()      != version.getDateTime()):
        Util.warning([
            "Version program is creating and version in relationship sequence " +
            "file disagree.",
            "File Version: " + str(sequenceStream.getVersionNumber()) + " " +
            str(sequenceStream.getDateTime()),
            "Prgm Version: " + str(version.getVersionNumber()) + " " +
            str(version.getDateTime())])

    # For each depth keep track of current component stack
    partOf = RelationshipType.getByName(RelationshipType.PART_OF)
    componentStack = []
    previousDepth = -1
    processedRelationships = sets.Set()
    parentCounts = {}

    componentLine = sequenceStream.getNextComponent()
    while componentLine:
        depth = componentLine.getDepth()

        if depth == previousDepth:
            # new component is a sibling of previous component
            componentStack[depth] = componentLine
        elif depth < previousDepth:
            # new component is a great uncle of previous component
            componentStack = componentStack[0:depth+1]
            componentStack[depth] = componentLine
        elif depth == previousDepth + 1:
            # new component is a child of previous component
            componentStack.append(componentLine)
        else:
            Util.fatalError([
                "Depth increased by more than one.  Previous depth: " +
                str(previousDepth) + "  Current depth: " + str(depth),
                "Previous line: ", componentStack[previousDepth].getLine(),
                "Current line:  ", componentLine.getLine()])

        if depth > 0:
            publicId = componentLine.getPublicId()
            name = componentLine.getName()
            parentPublicId = componentStack[depth-1].getPublicId()
            parentName = componentStack[depth-1].getName()
            rel = getByTypeChildAndParentPublicIds(
                partOf, publicId, componentStack[depth-1].getPublicId())

            if not rel:
                Util.warning([
                    "Cut and paste error.  " +
                    "Component placed under a component which is not its parent.",
                    "Parent " + parentPublicId + ", '" + parentName +"'",
                    "Child  " + publicId + ", '" + name + "'",
                    "Ignoring component."])
            elif rel in processedRelationships:
                if sequenceStream.inPrimarySection():
                    Util.warning([
                        "Parent child relationship between ",
                        "Parent " + parentPublicId + ", '" + parentName +"'",
                        "Child  " + publicId + ", '" + name + "'",
                        "occurs more than once in non-group part of "
                        "relationship sequence file.",
                        "Ignoring later occurrence."])
                    # Don't report dups from group section b/c relationships
                    # can occur many times there and we don't want to force
                    # editors to reorder all of them, just the first one.
            else:
                if parentPublicId not in parentCounts:
                    parentCounts[parentPublicId] = -1
                parentCounts[parentPublicId] += 1
                rel.setSequence(parentCounts[parentPublicId])
                processedRelationships.add(rel)

        previousDepth = depth
        componentLine = sequenceStream.getNextComponent()

    return None



def getByChildAndParentPublicIds(childId, parentId):
    """
    Return list of all relationships with this child and this parent.
    """
    happyFamily = _makeKeyToAHappyFamily(childPublicId = childId,
                                         parentPublicId = parentId)
    if happyFamily in _relationshipsByChildAndParentPublicId:
        return _relationshipsByChildAndParentPublicId[happyFamily]
    else:
        return []


def getByTypeChildAndParentPublicIds(relType, childId, parentId):
    """
    Return relationship with this type, this child and this parent.
    """
    happyFamily = _makeKeyToAHappyFamily(childPublicId = childId,
                                         parentPublicId = parentId)
    if happyFamily in _relationshipsByChildAndParentPublicId:
        for rel in _relationshipsByChildAndParentPublicId[happyFamily]:
            if rel.getRelationshipType() == relType:
                return rel
    return None

def getUndeletedByChildPublicId(childId):
    """
    Return list of all relationships with this child.
    """
    if childId in _undeletedRelationshipsByChildPublicId:
        return _undeletedRelationshipsByChildPublicId[childId]
    else:
        return []


def getUndeletedByParentPublicId(parentId):
    """
    Return list of all relationships with this parent.
    """
    if parentId in _undeletedRelationshipsByParentPublicId:
        return _undeletedRelationshipsByParentPublicId[parentId]
    else:
        return []

def filterByRelationshipType(anatRelList, relType):
    """
    Given a list of relationships, return a list containing only relationships
    of the given type.
    """
    filteredList = []
    for anatRel in anatRelList:
        if anatRel.getRelationshipType() == relType:
            filteredList.append(anatRel)
    return filteredList


def sortByChildName(anatRelList):
    """
    Given a list of relationships, all with a common parent,
    return a list of relationships sorted in
    alphabetical order by child name.
    """
    anatRels = anatRelList[:]
    anatRels.sort(_childNameCompare)

    return anatRels


def sortByChildStageSequenceName(anatRelList):
    """
    Given a list of relationships, all with a common parent,
    return a list of relationships sorted in
    child stage sequence, and then child name order.
    """
    anatRels = anatRelList[:]
    anatRels.sort(_childStageSequenceNameCompare)

    return anatRels

def sortByChildNameStageSequence(anatRelList):
    """
    Given a list of relationships, all with a common parent,
    return a list of relationships sorted in
    alphabetical order by child name, and then by child stage sequence.
    """
    anatRels = anatRelList[:]
    anatRels.sort(_childNameStageSequenceCompare)

    return anatRels

def sortByChildSeqNameStageSequence(anatRelList):
    """
    Given a list of relationships, all with a common parent,
    return a list of relationships sorted in
    child sequence, and then by child name, and finally by child stage
    sequence order.
    """
    anatRels = anatRelList[:]
    anatRels.sort(_childSeqNameStageSequenceCompare)

    return anatRels

def sortByChildSeqStageSequenceName(anatRelList):
    """
    Given a list of relationships, all with a common parent,
    return a list of relationships sorted in
    child sequence, child stage sequence, and then child name order.
    """
    anatRels = anatRelList[:]
    anatRels.sort(_childSeqStageSequenceNameCompare)

    return anatRels


# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  see initialise above

