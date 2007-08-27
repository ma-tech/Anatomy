#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Internal python structures to represent the ANA_PERSPECTVE_AMBIT table.
This represents the nodes that define the border of perspectives in
the anatomy database.  This table replaced the earlier
ANA_NODE_IN_PERSPECTIVE table.

Perspective definitions and their ambits do not occur in the CIOF
file.  They are stored directly in the database.
"""

import DbAccess
import Node
import Perspective


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# DB

TABLE   = "ANA_PERSPECTIVE_AMBIT"

OID_COLUMN              = "PAM_OID"
PERSPECTIVE_NAME_COLUMN = "PAM_PERSPECTIVE_FK"
NODE_OID_COLUMN         = "PAM_NODE_FK"
IS_START_COLUMN         = "PAM_IS_START"
IS_STOP_COLUMN          = "PAM_IS_STOP"
COMMENTS_COLUMN         = "PAM_COMMENTS"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------


_nodesByPerspectiveName = None


# ------------------------------------------------------------------
# PERSPECTIVE AMBIT
# ------------------------------------------------------------------

class PerspectiveAmbit:
    """
    Defines an ANA_PERSPECTIVE_AMBIT record, whcih defines a border node
    for a perspective.
    """

    def __init__(self, dbRecord):
        """
        Create an anatomy perspective ambit given a perspective ambit
        record from the database.
        """
        self.__oid             = dbRecord.getColumnValue("PAM_OID")
        self.__perspectiveName = dbRecord.getColumnValue("PAM_PERSPECTIVE_FK")
        self.__node          = Node.getByOid(dbRecord.getColumnValue("PAM_NODE_FK"))
        self.__isStart         = dbRecord.getColumnValue("PAM_IS_START")
        self.__isStop          = dbRecord.getColumnValue("PAM_IS_STOP")
        dbRecord.bindPythonObject(self)
        self.__dbRecord = dbRecord

        return None


    def getOid(self):
        """
        Return the OID of the perspective ambit record.  This OID occurs in ANA_OBJECT.
        """
        return self.__oid


    def getPerspectiveName(self):
        """
        Return the name of the perspective this node is a border node for.
        """
        return self.__perspectiveName


    def getNode(self):
        """
        Return the node that is a border node for this perspective.
        """
        return self.__node


    def getNodeOid(self):
        """
        Return the OID of the node that is a border node for this perspective.
        """
        return self.__node.getOid()


    def isStart(self):
        """
        Return True if the node is a START node.  That is, return True if this
        node defines where the perspective starts.
        """
        if self.__isStart:
            return True
        return False


    def isStop(self):
        """
        Return True if the node is a STOP node.  That is, return True if this
        node defines where the perspective ends.
        """
        if self.__isStop:
            return True
        return False


    def getDbRecord(self):
        """
        Return the DB Record for this object.
        """
        return self.__dbRecord


    def addToKnowledgeBase(self):
        """
        Inserts the perspective ambit into the knowledge
        base of all things we know about anatomy.

        Add it to both the knowledge in this module and in the perspective module.
        """
        perspectiveName = self.getPerspectiveName()
        if perspectiveName in _nodesByPerspectiveName:
            _nodesByPerspectiveName[perspectiveName][self.getNodeOid()] = self
        else:
            _nodesByPerspectiveName[perspectiveName] = {self.getNodeOid(): self}

        # Find the perspective this node in perspective belongs to and add it.

        perspective = Perspective.getByName(self.getPerspectiveName())
        perspective.addAmbitNode(self)

        return None



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------


def PerspectiveIterator(perspectiveName):
    """
    Return iterator over all ambit nodes for a given perspective, in no particular order.
    """

    return _nodesByPerspectiveName[perspectiveName].itervalues()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Establish metadata about perspectie ambits.
    """

    global _nodesByPerspectiveName

    _nodesByPerspectiveName = {}

    tableInfo = DbAccess.registerClassTable(PerspectiveAmbit, TABLE,
                                            DbAccess.IN_ANA_OBJECT)

    # Map instance methods to columns
    tableInfo.addColumnMethodMapping(
        OID_COLUMN, "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        PERSPECTIVE_NAME_COLUMN, "getPerspectiveName", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        NODE_OID_COLUMN, "getNodeOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        IS_START_COLUMN, "isStart", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        IS_STOP_COLUMN, "isStop", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        COMMENTS_COLUMN, "getComments", DbAccess.IS_NOT_KEY)

    return None



def readDb():
    """
    Read all node in perspective records from the DB.
    """
    allPams = DbAccess.readClassAll(PerspectiveAmbit)

    for dbPam in allPams:
        perspectiveAmbit = PerspectiveAmbit(dbPam)
        perspectiveAmbit.addToKnowledgeBase()

    return None



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above


