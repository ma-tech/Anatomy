#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Internal python structures to represent the transitive closure on
# anatomy relationships.

import sets                             # Not part of core in 2.3

import DbAccess
import Node                             # untimed anatomy
import Relationship
import RelationshipType
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


TABLE = "ANAD_RELATIONSHIP_TRANSITIVE"



# ------------------------------------------------------------------
# RELATIONSHIP TRANSITVE
# ------------------------------------------------------------------

class RelationshipTransitive:
    """
    Defines a binary, directional (ancestor-descendent) relationship
    """ 
    def __init__(self, ancestor, descendent, relType):
        """
        Given ancestor and descendent anatomy nodes, generate
        a relationship between them.
        """
        self.__relationshipType = relType
        self.__ancestor = ancestor
        self.__descendent = descendent
        self.__dbRecord = None
            
        return None


    def getOid(self):
        return None  # doesn't have one until after loaded in DB

    def getRelationshipType(self):
        return self.__relationshipType

    def getRelationshipTypeName(self):
        return self.getRelationshipType().getName()

    def getAncestor(self):
        return self.__ancestor

    def getAncestorOid(self):
        return self.getAncestor().getOid()

    def getDescendent(self):
        return self.__descendent

    def getDescendentOid(self):
        return self.getDescendent().getOid()


    def isDeleted(self):
        """
        Always returns false.

        Objects are not generated for deleted relationships.
        """
        return False


    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.

        The fields are returned as a list of strings.
        """
        relTypeName    = self.getRelationshipTypeName()
        descendentOid = self.getDescendentOid()
        ancestorOid   = self.getAncestorOid()

        return [None, relTypeName, str(descendentOid), str(ancestorOid)]


    def addToKnowledgeBase(self):
        """
        Inserts the anatomy transitive relationship into the knowledge
        base of all things we know about anatomy.
        """
        descendentPublicId = self.getDescendent().getPublicId()
        ancestorPublicId = self.getAncestor().getPublicId()
        pairKey = _genHappyFamilyKey(ancestorPublicId, descendentPublicId)

        # Code does not detect duplicate insertions.
        # 
        if pairKey in _artsByAncestorDescendentPublicId:
            Util.fatalError(["Attempt to create same transitive relationship twice:",
                             " Ancestor:   " + ancestorPublicId,
                             " Descendent: " + descendentPublicId])
        else:
            _artsByAncestorDescendentPublicId[pairKey] = self

        if descendentPublicId in _artsByDescendentPublicId:
            _artsByDescendentPublicId[descendentPublicId].append(self)
        else:
            _artsByDescendentPublicId[descendentPublicId] = [self]

        if ancestorPublicId in _artsByAncestorPublicId:
            _artsByAncestorPublicId[ancestorPublicId].append(self)
        else:
            _artsByAncestorPublicId[ancestorPublicId] = [self]
        _arts.append(self)

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
        return self.__dbRecord



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _genHappyFamilyKey(ancestorPublicId, descendentPublicId):
    return ancestorPublicId + ":" + descendentPublicId


def _genAncestorTransitiveRelsByDescendent(descendent, relType):
    """
    Get all defined transitive relationships where descendent is the
    descendent in the relationship, and the relationship is of type relType.

    The returned list is owned by the caller.
    """
    descendentId = descendent.getPublicId()
    if descendentId in _artsByDescendentPublicId:
        return [rel for rel in _artsByDescendentPublicId[descendentId]
                if rel.getRelationshipType() == relType]
    else:
        return []



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all transitive anatomy relationships
    """

    def __init__(self):
        self.__length = len(_arts)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _arts[self.__position]




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def initialise():
    """
    Initialisation for the relationship transitive table.
    """

    global _artsByDescendentPublicId, _artsByAncestorPublicId
    global _artsByAncestorDescendentPublicId, _arts
    
    # Lists of all relationships
    _artsByDescendentPublicId = {}
    _artsByAncestorPublicId = {}
    _artsByAncestorDescendentPublicId = {}
    _arts = []    # unindexed

    tableInfo = DbAccess.registerClassTable(
        RelationshipTransitive, TABLE, DbAccess.NOT_IN_ANA_OBJECT)
    # setup mappings betweeen Ciof values and db values
    tableInfo.addColumnMethodMapping(
        "RTR_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "RTR_RELATIONSHIP_TYPE_FK", "getRelationshipTypeName", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "RTR_DESCENDENT_FK",   "getDescendentOid",   DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "RTR_ANCESTOR_FK", "getAncestorOid", DbAccess.IS_NOT_KEY)

    return None




def deriveRelationshipsTransitive():
    """
    Generate the transitive closure of all relationships
    """
    # Anatomy relationships form a directed acyclic graph (DAG)
    #
    # Walk the graph top to bottom, breadth first, generating
    # transitive closure relationships as we go.

    # Start with the root of everything.
    nodeQueue = [Node.getRoot()]
    beenQueued = sets.Set(nodeQueue) # what has been put on the Q
    beenProcessed = sets.Set()       # what has been pulled from the Q
    partOfRelType = RelationshipType.getByName(RelationshipType.PART_OF)

    start = stop = None                  # start, stop stages for current node

    while len(nodeQueue) > 0:
        current = nodeQueue.pop(0)
        currentId = current.getPublicId()

        # Add relationships from this node to its immediate parents
        relsWithParents = Relationship.getUndeletedByChildPublicId(currentId)
        for parentRel in relsWithParents:
            parent = parentRel.getParent()
            relType = parentRel.getRelationshipType()
            if relType == partOfRelType:
                start, stop = current.getStageWindow()

            parentRelTrans = RelationshipTransitive(parent, current, relType)
            parentRelTrans.addToKnowledgeBase()

            # Add relationships based on its parents' already defined
            # transitive relationships.  Note that transitive relationships
            # do not cross relationship types.
            ancestorRels = _genAncestorTransitiveRelsByDescendent(parent, relType)
            for ancestorRel in ancestorRels:
                ancestor = ancestorRel.getAncestor()
                pairKey = _genHappyFamilyKey(ancestor.getPublicId(), currentId)
                if pairKey not in _artsByAncestorDescendentPublicId:
                    # if reltype is part-of then only generate a transitive
                    # relationship if the ancestor and this node overlap in time.
                    # Group ancestors sometimes do not overlap with their
                    # descendents.
                    generateIt = True
                    if relType == partOfRelType:
                        ancestorStart, ancestorStop = ancestor.getStageWindow()
                        if (start.getSequence() > ancestorStop.getSequence() or
                            stop.getSequence()  < ancestorStart.getSequence()):
                            if ancestor.isPrimary():
                                # oops, supposed to be a group
                                Util.fatalError([
                                    "Ancestor stage window does not overlap " +
                                    "with descendent stage window and",
                                    "ancestor is not a group.",
                                    "Ancestor ID:   " + ancestor.getPublicId() +
                                    " Name: " + ancestor.getName(),
                                    "  Start-Stop: " + ancestorStart.getName() +
                                    "-" + ancestorStop.getName(),
                                    "Descendent ID: " + current.getPublicId() +
                                    " Name: " + current.getName(),
                                    "  Start-Stop: " + start.getName() +
                                    "-" + stop.getName()])
                            generateIt = False

                    if generateIt:
                        ancestorRelTrans = RelationshipTransitive(
                            ancestor, current, relType)
                        ancestorRelTrans.addToKnowledgeBase()

        # This node's children can be added to the queue if all that
        # child's parents have been processed.
        beenProcessed.add(current)
        relsWithChildren = Relationship.getUndeletedByParentPublicId(currentId)
        for childRel in relsWithChildren:
            child = childRel.getChild()
            if child not in beenQueued:
                # Get all the child's parents
                childsRelsWithParents = Relationship.getUndeletedByChildPublicId(child.getPublicId())
                allParentsProcessed = True
                for childsRelWithParent in childsRelsWithParents:
                    childsParent = childsRelWithParent.getParent()
                    if childsParent not in beenProcessed:
                        allParentsProcessed = False
                if allParentsProcessed:
                    nodeQueue.append(child)
                    beenQueued.add(child)
    
    # Add self-referential relationships
    for node in beenProcessed:
        selfRefRel = RelationshipTransitive(node, node, partOfRelType)
        selfRefRel.addToKnowledgeBase()

    return None




# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above


