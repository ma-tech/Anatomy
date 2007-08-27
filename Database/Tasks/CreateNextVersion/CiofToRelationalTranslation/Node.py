#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Internal python structures to represent anatomy nodes.


import sets                             #
import AnatomyObject                    # Ties it all together
import Ciof                             # CIOF entities and attributes
import DbAccess
import NodeName
import Relationship
import RelationshipType
import Stage
import Synonym
import TimedNode                        # Timed Nodes
import Util                             # Error handling



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# Database related defs:

TABLE   = "ANA_NODE"

CLASS_TEXT = "Node (Component)"


# ------------------------------------------------------------------
# NODE
# ------------------------------------------------------------------

class Node:

    def __init__(self, ciofEnt, speciesName):
        """
        Create an Anatomy node from a CIOF entity, which will either be an
        AnatomicalPart, or a Group
        """
        # Does all the work, even if the node is deleted.
        # Some nodes are flagged as deleted.  Others are deleted only because
        # they have an ancestor that is deleted.
        self.__ciofEntity = ciofEnt
        self.__dbRecord   = None
        self.__deleted    = self.__ciofEntity.isDeleted()
        self.__publicId   = "EMAPA:" + self.__ciofEntity.getId()
        self.__anatomyObject = None     # set much later.
        self.__speciesName = speciesName
        self.__name = self.__ciofEntity.getAttributeValuesJoined("Name")
        self.__description = None       # does not exist in CIOF file.

        ciofType = ciofEnt.getType()
        if ciofType == "AnatomicalPart":
            self.__isPrimaryNode = True
            self.__groupChildren = sets.Set()
            self.__setCanonicalParentNodeForPrimary()
        elif ciofType == "Group":
            self.__isPrimaryNode = False
            self.__setGroupChildren()
            self.__setCanonicalParentNodeForGroup()
        else:
            Util.fatalError(["Attempt to create Node from unexpected " +
                             "CIOF node type: " + entType])
        self.__genRelationships()
        self.__genTimedNodes()
        self.__genSynonyms()

        return None

    def assignOid(self):
        creationDateTime = self.getCreationDateTime()
        self.__anatomyObject = AnatomyObject.AnatomyObject(
            self, creationDateTime = creationDateTime)
        self.__anatomyObject.addToKnowledgeBase()

        return self.getOid()

    def getOid(self):

        # Chicanery necessary because deleted anatomy objects won't have
        # an OID.
        if self.__anatomyObject:
            return self.__anatomyObject.getOid()
        else:
            return None

    def getAnatomyObject(self):
        return self.__anatomyObject

    def getPublicId(self):
        return self.__publicId

    def getSpecies(self):
        return self.__speciesName

    def getDescription(self):
        """
        Description is not available in CIOF, so this will always be none.
        """
        return self.__description

    def getCreationDateTime(self):
        return self.__ciofEntity.getCreationDateTime()

    def getModificationDateTime(self):
        """
        This is useless.  Do not use it.
        """
        return self.__ciofEntity.getModificationDateTime()

    def isPrimary(self):
        return self.__isPrimaryNode

    def isGroup(self):
        return not self.isPrimary()

    def isDeleted(self):
        return self.__deleted

    def getSynonyms(self):
        return self.__synonyms

    def getDbRecord(self):
        return self.__dbRecord

    def isRoot(self):
        if self.getCanonicalParentNode():
            isRoot = False
        else:
            isRoot = True
        return isRoot


    def getName(self):
        return self.__name


    def getStageWindow(self):
        """
        Return an ordered pair with the min and max stage that this anatomy
        node exists at
        """
        minStage = None
        maxStage = None

        for timedNode in self.__timedNodesInStageOrder:
            if not timedNode.isDeleted():
                if minStage == None:
                    minStage = timedNode.getStage()
                maxStage = timedNode.getStage()

        return minStage, maxStage


    def dealWithTcFallout(self):
        """
        TC CIOF entries can delete a timed node.  Unfortunately, they occur at the
        very end of the CIOF file, long after much calculation has already been
        done.  After they have been read in, some things need to be undone.

        Routine deals with these issues:
        o Group nodes with stage ranges that do not overlap with their kids'
          stage ranges.  This can be caused by TC CIOF deletions.
        """
        if self.isGroup():
            minStage, maxStage = self.getStageWindow()
            minStageSequence = minStage.getSequence()
            maxStageSequence = maxStage.getSequence()

            for groupKidRel in self.__relationshipsWhereGroupParent:
                kidNode = groupKidRel.getChild()
                if not kidNode.isDeleted():
                    kidMinStage, kidMaxStage = kidNode.getStageWindow()

                    # Report and fix Group nodes with stage ranges that do not
                    # overlap with their kids' stage ranges.  This can be caused
                    # by TC CIOF deletion entries.
                    if (kidMinStage.getSequence() > maxStageSequence or
                        kidMaxStage.getSequence() < minStageSequence):
                        Util.warning([
                            "Group '" + self.getName() +
                            "' (" + self.getPublicId() + ")",
                            "  contains child '" + kidNode.getName() + "' (" +
                            kidNode.getPublicId() + ")",
                            "  with a stage range that does not overlap the " +
                            "group's stage range.",
                            "  Group's range: " +
                            minStage.getName() + "-" +
                            maxStage.getName(),
                            "  Child range:   " +
                            kidMinStage.getName() + "-" +
                            kidMaxStage.getName()])
                        groupKidRel.setDeleted(True)
        return None




    def reportStageWindowProblems(self):
        """
        Report on problems with stages ranges.  This includes:
         o holes in anatomy node stage ranges that were created either by
           deletion of timed nodes with TC CIOF entities,
         o holes in anatomy node stage ranges that exist because they weren't
           defined in the CIOF in the first place.
         o stage window of this node is not contained in the stage range of its
           canonical parent.
         o Group nodes with stage ranges that exceed the sum of the stage
           ranges of their children
        We can't report holes caused by deletion until after all the TC CIOF
        entities have been processed.
        """

        # Stages on either end can be deleted, but there must be a
        # single contiguous block of undeleteds in the middle
        minStage, maxStage = self.getStageWindow()
        minStageSequence = minStage.getSequence()
        maxStageSequence = maxStage.getSequence()
        stageCount = 0

        for timedNode in self.__timedNodesInStageOrder:
            if not timedNode.isDeleted():
                stageCount += 1

        if stageCount != maxStageSequence - minStageSequence + 1:
            warning = [
                "Hole in stage range for component " + self.getPublicId() + ":"]
            for timedNode in self.__timedNodesInStageOrder:
                if timedNode.isDeleted():
                    delString = "Deleted "
                else:
                    delString = "        "
                warning.append(
                    delString + timedNode.getStage().getName() + " " +
                    timedNode.getPublicId())
            Util.warning(warning)

        # Report if stage range specified in CIOF file for this node is not
        # contained within the stage range of its canonical parent.
        canonicalParent = self.getCanonicalParentNode()
        if canonicalParent:
            minParentStage, maxParentStage = canonicalParent.getStageWindow()
            minParentSequence = minParentStage.getSequence()
            maxParentSequence = maxParentStage.getSequence()
            if (minStageSequence < minParentSequence or
                maxStageSequence > maxParentSequence):
                Util.warning([
                    "Child has stage range outside its parent's stage range.",
                    "Child Node " + self.getPublicId() + ", '" + self.getName() + "'",
                    "has declared stage range (" +
                    minStage.getStageName() + "-" +
                    maxStage.getStageName() + ")",
                    "Parent Node " + canonicalParent.getPublicId() + ", '" +
                    canonicalParent.getName() + "'",
                    "has declared stage range (" +
                    minParentStage.getStageName() + "-" +
                    maxParentStage.getStageName() + ")",
                    "Deleting out of range timed nodes."])
                for stgSequence in range(minStageSequence, maxStageSequence):
                    if (stgSequence < minParentSequence or
                        stgSequence > maxParentSequence):
                        badStageName = Stage.getBySequence(stgSequence).getName()
                        timedNode = self.__timedNodesByStageName[badStageName]
                        timedNode.setDeleted()

        if self.isGroup():
            # For group nodes, check that stage range does not exceed the sum
            # of the stage ranges of its children.  Warn if it does not.
            kidsMinStage = None
            kidsMaxStage = None
            for groupKidRel in self.__relationshipsWhereGroupParent:
                kidNode = groupKidRel.getChild()
                if not kidNode.isDeleted():
                    kidMinStage, kidMaxStage = kidNode.getStageWindow()
                    if (not kidsMinStage or
                        kidMinStage.getSequence() < kidsMinStage.getSequence()):
                        kidsMinStage = kidMinStage
                    if (not kidsMaxStage or
                        kidMaxStage.getSequence() > kidsMaxStage.getSequence()):
                        kidsMaxStage = kidMaxStage

            if (kidsMinStage.getSequence() > minStage.getSequence() or
                kidsMaxStage.getSequence() < maxStage.getSequence()):
                Util.warning([
                    "Group '" + self.getName() +
                    "' (" + self.getPublicId() + ")",
                    "  has a stage range greater than the sum of its children.",
                    "  Group's range: " +
                    minStage.getName() + "-" +
                    maxStage.getName(),
                    "  Children's range: " +
                    kidsMinStage.getName() + "-" +
                    kidsMaxStage.getName()])

        return None


    def reportGroupMemberNameProblems(self):
        """
        Report problems with group member names.  Currently, this means only
        reporting duplcate names.
        """
        # Detect children that have the same name
        memberNames = {}

        for groupMember in self.__groupChildren:
            memberName = groupMember.getName()
            reducedName = Util.reduceName(memberName)
            if reducedName in memberNames:
                memberNames[reducedName].append(groupMember)
            else:
                memberNames[reducedName] = [groupMember]

        worrisomeChildren = []
        for reducedName, membersWithName in memberNames.iteritems():
            if len(membersWithName) > 1:
                for member in membersWithName:
                    worrisomeChildren.extend([
                        "  '" + member.getName() + "' " + member.getPublicId(),
                        "      Stages: [" + member.genTimedNodesString() + "]"])
                worrisomeChildren.append("")

        if len(worrisomeChildren) > 0:
            warning = ["'" + self.getName() + "' " + self.getPublicId() +
                       "  Group has multiple children with same name:"]
            warning.extend(worrisomeChildren)
            Util.warning(warning)

        return None




    def getCanonicalParentNode(self):
        """
        Return node's canonical (space) parent.  If node is root, returns None.
        """
        return self.__canonicalParentNode




    def __genTimedNodes(self):
        """
        Generate anatomy timed nodes for each stage the anatomy node
        exists in.

        List of stages this node exists in, and the public ID it has
        in each stage, is included in the entity.

        __genRelationships must have been called prior to this.
        """
        self.__timedNodesByStageName = {}
        self.__timedNodesInStageOrder = []

        timedNodesByPublicId = {}       # used for error checking

        for pair in self.__ciofEntity.getAttributeValues("Stages"):
            stageName, nodeIdAtStage = pair.split(":")
            nodeIdAtStage = "EMAP:" + nodeIdAtStage
            stage = Stage.getByName(stageName)

            # Add timed nodes, even if they exist outside the parent's stage
            # window.  Some (hopefully all) of these will be flagged as deleted
            # when we process the TC entries at the end of the CIOF file.
            # Any that aren't will be reported by reportStageWindowProblems.

            timedNode = TimedNode.TimedNode(self, stage, nodeIdAtStage)
            # Check Stage name and timed node IDs not defined twice
            if stageName in self.__timedNodesByStageName:
                Util.fatalError([
                    "Stage " + stageName +" defined twice in Node " +
                    self.getPublicId()])
            if nodeIdAtStage in timedNodesByPublicId:
                Util.fatalError(["ID " + nodeIdAtStage +
                                 " defined for two stages in Node " +
                                 self.getPublicId()])
            self.__timedNodesByStageName[stageName] = timedNode
            self.__timedNodesInStageOrder.append(timedNode)

        def cmpStageSequence(timedNode1, timedNode2):
            return cmp(timedNode1.getStage().getSequence(),
                       timedNode2.getStage().getSequence())
        self.__timedNodesInStageOrder.sort(cmpStageSequence)

        return None




    def __genRelationships(self):
        """
        Generate the anatomy relationships this node participates in.

        In particular, generate:
          primary part-of relationships where this node is child
          group part-of relationships where this node is parent.
        This will cover all anatomy node relationships, by the time the
        whole tree/DAG has been generated.
        """
        # generate primary part-of relationships where this node is child
        partOf = RelationshipType.getByName(RelationshipType.PART_OF)
        parentNode = self.getCanonicalParentNode()
        if parentNode:
            self.__relationshipWhereChild = Relationship.Relationship(
                relType = partOf, child= self, parent = parentNode)
        else:
            self.__relationshipWhereChild = None

        # generate group part-of relationships where this node is parent.
        #
        # Note that the deleted status of relationships in this list may be
        # modified later.  At the time this list is generated we haven't yet
        # processed the TC entries.  TC entries can specify that a timed
        # component has been deleted.
        #
        # A group sometimes includes a node for only part of its
        # lifespan, and it is possible for that node to have been
        # deleted across all the stages that the group covers.  This
        # causes the node not to appear in the group in the DB.
        #
        self.__relationshipsWhereGroupParent = [
            Relationship.Relationship(
                relType = partOf, child = childId, parent = self)
            for childId in self.__groupChildren]

        return None



    def __genSynonyms(self):
        """
        Some nodes have synonyms specified.  Store them if they do.
        """
        self.__synonyms = []
        ciofSyns = self.__ciofEntity.getAttributeValues("Synonym")
        for synonymText in ciofSyns:
            if synonymText in self.__synonyms:
                util.warning([
                    "Node '" + self.getName() +
                    "' (" + self.getPublicId() + ") has same synonym " +
                    "defined twice",
                    ciofSyns])
            else:
                synonym = Synonym.Synonym(synonymText, self)
                self.__synonyms.append(synonym)




    def __setCanonicalParentNodeForPrimary(self):
        """
        Set primary node's canonical (space) parent.

        If node is root, sets it to None.
        """
        parentId = self.__ciofEntity.getAttributeValue("SpaceParent")

        if not parentId:
            # node is a root
            parentNode = None
        else:
            # node thinks it has a parent
            parentId = "EMAPA:" + parentId
            parentNode = getByPublicId(parentId)
            if parentNode.isDeleted():
                # but parent is no more.
                if not self.isDeleted():
                    Util.warning([
                        "Node " + self.getPublicId() +
                        " has deleted parent, but",
                        "is itself not marked as deleted.  Marking it" +
                        "as deleted."])
                    self.__deleted = True

        self.__canonicalParentNode = parentNode

        return self.__canonicalParentNode


    def __setGroupChildren(self):
        """
        Converts the list of children IDs from the CIOF into a list of
        child nodes.  It also removes deleted nodes and redundant nodes
        from group definition.
        """

        # Build list of undeleted child nodes.  There are two kinds of
        # kids are defined in the CIOF. We don't care about the distinction.
        undeletedNodes = sets.Set()

        def concatPublicId(childId):
            return "EMAPA:" + childId
        childIds = map(concatPublicId,
                       self.__ciofEntity.getAttributeValues("ComponentChildren"))
        childIds.extend(map(concatPublicId,
                            self.__ciofEntity.getAttributeValues("SubGroups")))
        for nodeId in childIds:
            node = getByPublicId(nodeId)
            if node.isDeleted():
                # Warn about deleted node in group definition.
                # Can't print timed node string for this group because we cant
                # generate them until we have a canonical parent node.
                Util.warning([
                    "Group '" + self.getName() + "' (" + self.getPublicId() + ")",
                    "contains deleted child '" + node.getName() + "' " +
                    node.getPublicId()])
            else:
                 undeletedNodes.add(node)

        if len(undeletedNodes) == 0:
           Util.fatalError([
               "Group '" + self.getName() + "' (" + self.getPublicId() +
               ") contains only deleted children"])

        # Remove redundant nodes from list of children.  A node is redundant if
        # one of its canonical ancestors is also a member of the group.

        redundantNodes = sets.Set()
        for node in undeletedNodes:
            ancestorNode = node.getCanonicalParentNode()
            while ancestorNode:
                if ancestorNode in undeletedNodes:
                    # This node is redundant
                    redundantNodes.add(node)
                    Util.warning([
                        "Group '" + self.getName() +
                        "' (" + self.getPublicId() + ")",
                        "has redundant node.",
                        "Group child node '" + node.getName() + "' (" +
                        node.getPublicId() + ") is a descendant of",
                        "Other child node '" + ancestorNode.getName() + "' (" +
                        ancestorNode.getPublicId() + ").",
                        "Dropping '" + node.getName() + "' (" +
                        node.getPublicId() + ") from group"])
                    break
                ancestorNode = ancestorNode.getCanonicalParentNode()

        self.__groupChildren = undeletedNodes.difference(redundantNodes)

        if len(self.__groupChildren) == 1:
           Util.warning([
               "Group '" + self.getName() + "' (" + self.getPublicId() +
               ") contains only one child."])

        return self.__groupChildren



    def __setCanonicalParentNodeForGroup(self):
        """
        The parent node of a group is the nearest common ancestor of all
        the group's children that is not itself a group.
        """

        # Start with path of first child, and then compare it with the other
        # children paring it back whenever they diverge.
        # Could do this with set intersections instead, but then have to
        # re-establish node order.
        nearestCommonPath = []

        for groupMember in self.__groupChildren:

            if len(nearestCommonPath) == 0:
                # first time through.  Start with first node's path as
                # nearest common path
                ancestorNode = groupMember.getCanonicalParentNode()
                while ancestorNode:
                    nearestCommonPath.append(ancestorNode)
                    ancestorNode = ancestorNode.getCanonicalParentNode()
                nearestCommonPath.reverse()
            else:
                # pare common path back based on this kid
                memberPath = []
                memberAncestor = groupMember.getCanonicalParentNode()
                while memberAncestor:
                    memberPath.append(memberAncestor)
                    memberAncestor = memberAncestor.getCanonicalParentNode()
                memberPath.reverse()

                maxLength = min(len(nearestCommonPath), len(memberPath))
                pathIndex = 0
                while (pathIndex < maxLength and
                       nearestCommonPath[pathIndex] == memberPath[pathIndex]):
                    pathIndex += 1

                del nearestCommonPath[pathIndex:]

        self.__canonicalParentNode = nearestCommonPath[-1]

        if Util.debugging():
            Util.debugMessage([
                "Canonical parent for group node: " + self.getPublicId() + ", " +
                self.getName(),
                "is node: " + self.__canonicalParentNode.getPublicId() + ", " +
                self.__canonicalParentNode.getName(),])

        return self.__canonicalParentNode



    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fiels are returned as a list of strings.
        """
        return [str(self.getOid()), self.getSpecies(),
                self.getName(),
                str(int(self.isPrimary())),
                self.getPublicId()]



    def addToKnowledgeBase(self):
        """
        Inserts the node into the knowledge base of all things we know
        about anatomy.  Also inserts any dependent timed nodes, and
        part-of relationships
        """
        id = self.getPublicId()
        if id in _anatomyNodesByPublicId:
            Util.fatalError([
                "Node with id '" + id + "' already defined."])

        _anatomyNodesByPublicId[id] = self
        _anatomyNodes.append(self)

        # Add relationships.
        if self.__relationshipWhereChild:
            self.__relationshipWhereChild.addToKnowledgeBase()
        for rel in self.__relationshipsWhereGroupParent:
            rel.addToKnowledgeBase()

        # Add synonyms
        for synonym in self.getSynonyms():
            synonym.addToKnowledgeBase()

        # Add timed nodes.
        for timedNode in self.__timedNodesInStageOrder:
            timedNode.addToKnowledgeBase()

        return None



    def genTimedNodesString(self):
        """
        Generate a list (as a string) of Stage Name:EMAP ID pairs for
        all (as yet) undeleted timed nodes for this node.

        This is used mainly for debugging and error reporting.
        """
        if len(self.__timedNodesInStageOrder) == 0:
            print self.getName(), self.getPublicId() , "has no timed nodes"

        try:
            timedNodePairs = []
            for atn in self.__timedNodesInStageOrder:
                if not atn.isDeleted():
                    atnString = (atn.getStage().getName() +
                                 "(" + atn.getPublicId() + ")")
                    timedNodePairs.append(atnString)
            return reduce(Util.commaConcat, timedNodePairs)

        except AttributeError:
            return ""



    # --------------------------
    # Database Methods
    # --------------------------

    def setDbInfo(self, dbRecord):
        """
        Associates a DB record with this object, and vice versa,
        and looks up the AnatomyObject for this node.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
            self.assignOid()
        else:
            # we have a db record, means we also have an AnatomyObject
            dbRecord.bindPythonObject(self)
            oid = dbRecord.getColumnValue("ANO_OID")
            self.__anatomyObject = AnatomyObject.bindAnatomyObject(oid, self)

        _anatomyNodesByOid[self.getOid()] = self
        self.__dbRecord = dbRecord

        return dbRecord



# ------------------------------------------------------------------
# INTERNAL ROUTINES
# ------------------------------------------------------------------

def __reportNodeNameSynonymOverlap():
    """
    Report the case where the name of a node is the synonym for another node.
    """

    reducedNodeNames = NodeName.genReducedNameSet()
    reducedSynNames  = Synonym.genReducedNameSet()
    commonReducedNames = reducedNodeNames & reducedSynNames
    for reducedName in commonReducedNames:

        warning = [
            "Overlap between synonyms and node names",
            "Name For: "]

        # produce list of node names participating in overlap
        nodeNameInfo = {}
        nodeNames = NodeName.getByReducedName(reducedName)
        for nodeName in nodeNames:
            nameNode = nodeName.getNode()
            nodeSynList = nameNode.getSynonyms()
            if nodeSynList:
                def concat(earlierSyns, nextSyn):
                    if earlierSyns == "   (Synonyms: ":
                        return earlierSyns + "'" + nextSyn.getSynonymText() + "'"
                    else:
                        return earlierSyns + ", '" + nextSyn.getSynonymText() + "'"
                nodeSyns = reduce(concat, nodeSynList, "   (Synonyms: ") + ")"
            else:
                nodeSyns = ""
            nameNodePublicIdNum = int(nameNode.getPublicId().split(":")[1])
            nodeNameInfo[nameNodePublicIdNum] = [
                "  " + nameNode.getPublicId() + " '" + nameNode.getName() + "'" +
                nodeSyns,
                "    " + nameNode.genTimedNodesString() ]
        sortedIds = nodeNameInfo.keys()
        sortedIds.sort()
        for nodeId in sortedIds:
            nni = nodeNameInfo[nodeId]
            for line in nni:
                warning.append(line)

        # produce list of synonyms participating in overlap
        warning.append("Synonym for:")
        synonymInfo = {}
        synonyms   = Synonym.getByReducedText(reducedName)
        for synonym in synonyms:
            synNode = synonym.getObjectSynonymIsFor()
            synNodePublicIdNum = int(synNode.getPublicId().split(":")[1])
            synonymInfo[synNodePublicIdNum] = [
                "  " + synNode.getPublicId() + " '" + synNode.getName() +
                "'   (Synonym: " + "'" + synonym.getSynonymText() + "')",
                "    " + synNode.genTimedNodesString() ]
        sortedIds = synonymInfo.keys()
        sortedIds.sort()
        for nodeId in sortedIds:
            synInfo = synonymInfo[nodeId]
            for line in synInfo:
                warning.append(line)

        Util.warning(warning)

    return None




# ------------------------------------------------------------------
# NODE ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all anatomy Nodes, including deleted ones
    """

    def __init__(self):
        self.__length = len(_anatomyNodes)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _anatomyNodes[self.__position]


class AllUndeletedIter:
    """
    Iterate through all undeleted anatomy Nodes.
    """

    def __init__(self):
        self.__length = len(_anatomyNodes)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        self.__position += 1
        while (self.__position < self.__length and
               _anatomyNodes[self.__position].isDeleted()):
            self.__position += 1

        if self.__position == self.__length:
            raise StopIteration
        return _anatomyNodes[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def initialise():
    """
    Initialisation for Anatomy Nodes
    """
    global _anatomyNodesByPublicId, _anatomyNodesByOid, _anatomyNodes

    # Define a series of data structures to hold the CIOF entities,
    # organised by their various parts.
    _anatomyNodesByPublicId = {}        # Indexed by EMAPA ID
    _anatomyNodesByOid = {}
    _anatomyNodes = []                  # All anatomy nodes, no index

    tableInfo = DbAccess.registerClassTable(
        Node, TABLE, DbAccess.IN_ANA_OBJECT)
    # setup mappings betweeen Ciof values and db values
    tableInfo.addColumnMethodMapping(
        "ANO_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "ANO_SPECIES_FK", "getSpecies", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ANO_COMPONENT_NAME", "getName", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ANO_IS_PRIMARY", "isPrimary", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ANO_IS_GROUP", "isGroup",     DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ANO_PUBLIC_ID", "getPublicId", DbAccess.IS_NOT_KEY)
    # Don't register Description as it is not available in CIOF.  Therefore
    # we don't care if they are different.
    # tableInfo.addColumnMethodMapping(
    #     "ANO_DESCRIPTION", "getDescription", DbAccess.IS_NOT_KEY)

    return None


def dealWithTcFallout():
    """
    TC CIOF entries can delete a timed node.  Unfortunately, they occur at the
    very end of the CIOF file, long after much calculation has already been
    done.  After they have been read in, some things need to be undone.

    This routine undoes them.
    """
    for node in _anatomyNodes:
        node.dealWithTcFallout()

    return None



def readDb():
    """
    Read in anatomy node records from the database and matches them with
    entries from CIOF file.
    """
    allNodes = DbAccess.readClassAll(Node)

    for nodeRec in allNodes:
        publicId = nodeRec.getColumnValue("ANO_PUBLIC_ID")
        node = getByPublicId(publicId)
        if node:
            node.setDbInfo(nodeRec)
        else:
            # DB record is an orphan.  It does not have a partner entry
            # in the CIOF.
            nodeRec.registerAsOrphan()

    return None



def getByPublicId(publId):
    if publId in _anatomyNodesByPublicId:
        return _anatomyNodesByPublicId[publId]
    else:
        return None



def getByOid(oid):
    if oid in _anatomyNodesByOid:
        return _anatomyNodesByOid[oid]
    else:
        return None



def getRoot():
    """
    Find the root.  Not too efficient.
    """
    rootNodeList = []
    nodes = AllIter()
    for node in nodes:
        if node.isRoot():
            rootNodeList.append(node)
    if len(rootNodeList) == 0:
        Util.fatalError(["No root nodes found."])
    if len(rootNodeList) > 1:
        Util.fatalError(["more than one root node found."])

    return rootNodeList[0]



def genNomenclatureReport(showNameSynonymOverlapWarnings):
    """
    Perform final analysis on anatomy node nomenclature and report
    any issues that may need attention.

    This means looking at node names (component names), synonyms,
    and anything else, and reporting anything that looks like it
    might be out of order.
    """
    # Report on group naming issues
    for node in AllUndeletedIter():
        node.reportGroupMemberNameProblems()

    # Report on node names in isolation
    NodeName.genNodeNames()
    NodeName.report()

    # Report on synonyms in isolation
    Synonym.report()

    if showNameSynonymOverlapWarnings:
        __reportNodeNameSynonymOverlap()

    return None



def genStageWindowReport():
    """
    Report on stage window problems.
    """

    for node in AllUndeletedIter():
        node.reportStageWindowProblems()

    return None



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above.
