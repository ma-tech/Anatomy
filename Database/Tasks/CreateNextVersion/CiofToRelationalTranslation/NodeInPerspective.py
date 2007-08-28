#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Internal python structures to represent the ANA_NODE_IN_PERSPECTVE
table which defines the nodes that occur in each perspective
Perspective definitions are not listed in the CIOF file.
"""

import DbAccess
import Node
import Perspective



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# DB

TABLE   = "ANA_NODE_IN_PERSPECTIVE"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_nodesByPerspectiveName = None
_nodesInPerspectives = None



# ------------------------------------------------------------------
# NODE IN PERSPECTIVE
# ------------------------------------------------------------------

class NodeInPerspective:
    """
    Defines an anatomy database node in perspective record.  Everything
    underneath the node is considered to also be in the perspective.
    """

    def __init__(self, dbRecord):
        """
        Create an anatomy node in perspective given a node in perspective
        record from the database.
        """
        self.__oid = dbRecord.getColumnValue("NIP_OID")
        self.__perspectiveName = dbRecord.getColumnValue("NIP_PERSPECTIVE_FK")
        self.__node = Node.getByOid(dbRecord.getColumnValue("NIP_NODE_FK"))
        self.__dbRecord = None
        self.__setDbRecord(dbRecord)

        return None


    def getOid(self):
        """
        Get the OID of the record.
        """
        return self.__oid

    def getPerspectiveName(self):
        """
        Get name of perspective the node is in.
        """
        return self.__perspectiveName

    def getNode(self):
        """
        Return the node.
        """
        return self.__node

    def getNodeOid(self):
        """
        Return the node OID.
        """
        return self.__node.getOid()


    def addToKnowledgeBase(self):
        """
        Inserts the anatomy node in perspective into the knowledge
        base of all things we know about anatomy.
        """
        perspectiveName = self.getPerspectiveName()
        if perspectiveName in _nodesByPerspectiveName:
            _nodesByPerspectiveName[perspectiveName][self.getNodeOid()] = self
        else:
            _nodesByPerspectiveName[perspectiveName] = {self.getNodeOid(): self}
        _nodesInPerspectives.append(self)

        # Find the perspective this node in perspective belongs to and add it.

        perspective = Perspective.getByName(self.getPerspectiveName())
        perspective.addNode(self.getNode())

        return None


    def spew(self, label=""):
        """
        debugging routine.  Displays contents of perspective
        """
        print "Node In Perspective:", label
        print "Perspective:", self.getPerspectiveName()
        self.getNode().spew()
        print ""

        return None


    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        """
        Return the database record.
        """
        return self.__dbRecord


    def __setDbRecord(self, dbRecord):
        """
        Associates a DB record with this object and vice versa.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
        else:
            dbRecord.bindPythonObject(self)
        self.__dbRecord = dbRecord

        return dbRecord



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# NODE IN PERSPECTIVE ITERATOR
# ------------------------------------------------------------------


class PerspectiveIter:
    """
    Iterate through all node in perspectives, for a given perspective
    """

    def __init__(self, perspectiveName):
        self.__iterator = _nodesByPerspectiveName[perspectiveName].itervalues()
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return the next node in perspective object.
        """
        return self.__iterator.next()


# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Initialise knowledge about node in perspective objects.
    """

    global _nodesByPerspectiveName, _nodesInPerspectives

    _nodesByPerspectiveName = {}
    _nodesInPerspectives = []

    tableInfo = DbAccess.registerClassTable(NodeInPerspective, TABLE,
                                            DbAccess.NOT_IN_ANA_OBJECT)

    # Map instance methods to columns
    tableInfo.addColumnMethodMapping(
        "NIP_OID", "getName", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "NIP_PERSPECTIVE_FK", "getPerspectiveName",
        DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "NIP_NODE_FK", "getNodeOid",
        DbAccess.IS_NOT_KEY)

    return None



def readDb():
    """
    Read all node in perspective records from the DB.
    """
    allNips = DbAccess.readClassAll(NodeInPerspective)

    for dbNip in allNips:
        nodeInPerspective = NodeInPerspective(dbNip)
        nodeInPerspective.addToKnowledgeBase()

    return None



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above


