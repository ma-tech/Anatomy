#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************



Internal python structures to represent anatomy timed nodes.
"""

import AnatomyObject                    # Ties it all together
import DbAccess
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Database related defs:

TABLE   = "ANA_TIMED_NODE"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_timedNodesByPublicId = None
_timedNodes = None



# ------------------------------------------------------------------
# TIMED NODE
# ------------------------------------------------------------------

class TimedNode:
    """
    Timed nodes are anatomy nodes that are tied to a single specific stage.
    """

    def __init__(self, anatomyNode, stage, publicId):
        """
        Create a timed anatomy node given the anatomy node it is
        based on, and the specific stage the timed anatomy node is for.
        """
        self.__dbRecord = None
        self.__node = anatomyNode
        self.__anatomyObject = None
        self.__stage = stage
        # stageModifier and isDeleted may be overridden later by TC CIOF entity
        self.__stageModifier = None
        self.__publicId = publicId
        self.__isDeleted = self.getNode().isDeleted()

        return None



    def addTcCiofEntityToKnowledgeBase(self, tcCiofEntity):
        """
        Add information for a CIOF TC entity to this timed node.

        TC entities are optional and do not exist for most timed nodes.
        """
        self.__isDeleted = self.__isDeleted or tcCiofEntity.isDeleted()
        self.__stageModifier = tcCiofEntity.getAttributeValue("Phase")

        return None


    def assignOid(self):
        """
        Anatomy Timed nodes do not have explicit creation dates in the
        CIOF file.  Best we can do is use the create date on the
        anatomy node.
        """
        creationDateTime = self.__node.getCreationDateTime()
        self.__anatomyObject = AnatomyObject.AnatomyObject(
            self, creationDateTime = creationDateTime)
        self.__anatomyObject.addToKnowledgeBase()

        return self.getOid()


    def getPublicId(self):
        """
        Public ID is the EMAP ID.
        """
        return self.__publicId

    def getOid(self):
        """
        Return OID of timed node.
        """
        return self.__anatomyObject.getOid()

    def isDeleted(self):
        """
        Return True if timed node is deleted.
        """
        return self.__isDeleted

    def setDeleted(self, state=True):
        """
        Changes the deleted state of the timed node.
        """
        self.__isDeleted = state
        return self.__isDeleted


    def getNode(self):
        """
        Returrn the node for this timed node.
        """
        return self.__node

    def getNodeOid(self):
        """
        Return the OID of the node this timed node is for.
        """
        return self.__node.getOid()

    def getStage(self):
        """
        Get stage this timed node is for.
        """
        return self.__stage

    def getStageOid(self):
        """
        Get OID of stage this timed node is for.
        """
        return self.__stage.getOid()

    def getStageModifier(self):
        """
        A small number of timed nodes have modifiers indicating when in the
        stage the component comes into or goes out of existence.
        """
        return self.__stageModifier

    def addToKnowledgeBase(self):
        """
        Inserts the timed node into the knowledge base of all things we know
        about anatomy.
        """
        publicId = self.getPublicId()
        if publicId in _timedNodesByPublicId:
            Util.fatalError(
                ["Public ID " + publicId +
                 " defined for two different anatomy timed nodes"])
        _timedNodesByPublicId[publicId] = self
        _timedNodes.append(self)

        return None


    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fiels are returned as a list of strings.
        """
        anodeOid = self.getNode().getOid()
        stgOid   = self.getStage().getOid()
        return [str(self.getOid()), str(anodeOid), str(stgOid),
                self.getStageModifier(), self.getPublicId()]



    def setStageModifier(self, tcCiofEntity):
        """
        Set the stage modifier in an antomy timed node, given the TC CIOF
        entity for this timed node
        """
        self.__stageModifier = tcCiofEntity.getAttributeValue("Phase")
        return self.__stageModifier


    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        """
        Return DB record for this timed node.
        """
        return self.__dbRecord


    def setDbInfo(self, dbRecord):
        """
        Associates a DB record with this object, and vice versa,
        and looks up the AnatomyObject for this anatomy timed node.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
            self.assignOid()
        else:
            # we have a db record, means we also have an AnatomyObject
            dbRecord.bindPythonObject(self)
            oid = dbRecord.getColumnValue("ATN_OID")
            self.__anatomyObject = AnatomyObject.bindAnatomyObject(oid, self)

        self.__dbRecord = dbRecord

        return dbRecord



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all anatomy timed Nodes, including deleted ones.
    """

    def __init__(self):
        self.__length = len(_timedNodes)
        self.__position = -1         # Most recent anatomy timed node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return next timed node.
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _timedNodes[self.__position]



class AllUndeletedIter:
    """
    Iterate through all undeleted anatomy timed Nodes.
    """

    def __init__(self):
        self.__length = len(_timedNodes)
        self.__position = -1         # Most recent anatomy timed node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return next undeleted timed node.
        """
        self.__position += 1
        while (self.__position < self.__length and
               _timedNodes[self.__position].isDeleted()):
            self.__position += 1

        if self.__position == self.__length:
            raise StopIteration
        return _timedNodes[self.__position]


# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Initialisation for Anatomy Timed Nodes
    """
    global _timedNodesByPublicId, _timedNodes

    _timedNodesByPublicId = {}    # Indexed by EMAP ID
    _timedNodes = []              # All, no index

    tableInfo = DbAccess.registerClassTable(TimedNode, TABLE,
                                            DbAccess.IN_ANA_OBJECT)
    # setup mappings betweeen Ciof values and db values

    tableInfo.addColumnMethodMapping(
        "ATN_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "ATN_NODE_FK", "getNodeOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ATN_STAGE_FK", "getStageOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ATN_STAGE_MODIFIER_FK", "getStageModifier", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ATN_PUBLIC_ID", "getPublicId", DbAccess.IS_NOT_KEY)

    return None


def readDb():
    """
    Read in anatomy timed node records from the database.
    """
    allTimedNodes = DbAccess.readClassAll(TimedNode)

    for timedNodeRec in allTimedNodes:
        publicId = timedNodeRec.getColumnValue("ATN_PUBLIC_ID")
        timedNode = getByPublicId(publicId)
        if timedNode:
            timedNode.setDbInfo(timedNodeRec)
        else:
            # DB record is an orphan.  It does not have a partner entry
            # in the CIOF
            timedNodeRec.registerAsOrphan()

    return None



def getByPublicId(publId):
    """
    Find an anatomy timed node, given its public id.

    Return None if no such anatomy timed node exists.
    """
    try:
        timedNode = _timedNodesByPublicId[publId]
    except KeyError:
        timedNode = None
    return timedNode



def addTcCiofEntityToKnowledgeBase(tcCiofEntity):
    """
    TC CIOF entities modify existing timed components
    """
    tnId = "EMAP:" + tcCiofEntity.getId()
    tnToModify = getByPublicId(tnId)
    tnToModify.addTcCiofEntityToKnowledgeBase(tcCiofEntity)

    return None




# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above

