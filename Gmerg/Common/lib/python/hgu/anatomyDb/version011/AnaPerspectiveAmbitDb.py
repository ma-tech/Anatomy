#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ANA_PERSPECTIVE_AMBIT table in a GMERG style relational
anatomy database.  This table contains the minimal defintion of what is in
a perspective.  It replaces the ANA_NODE_IN_PERSPECTIVE table.

A perspective is a subset of the whole anatomy.
"""

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord
from hgu.db import DbTable              # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_PERSPECTIVE_AMBIT"

OID_COLUMN              = "PAM_OID"
PERSPECTIVE_NAME_COLUMN = "PAM_PERSPECTIVE_FK"
NODE_OID_COLUMN         = "PAM_NODE_FK"
IS_START_COLUMN         = "PAM_IS_START"
IS_STOP_COLUMN          = "PAM_IS_STOP"
COMMENTS_COLUMN         = "PAM_COMMENTS"




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None



# ------------------------------------------------------------------
# NODE IN PERSPECTIVE RECORD
# ------------------------------------------------------------------

class AnaPerspectiveAmbitDbRecord:
    """
    Python wrapper around DbRecord containing an ANA_PERSPECTIVE_AMBIT
    database record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ANA_PERSPECTIVE_AMBIT database record.

        if dbRecord = None, populate all columns with None.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None

    def getDbRecord(self):
        """
        Returns the DbRecord for this ANA_PERSPECTIVE_AMBIT record.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the database OID for this record.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def setOid(self, oid):
        """
        Set the database OID for this record.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, oid)


    def getPerspectiveName(self):
        """
        We all need perspective.
        """
        return self.getDbRecord().getColumnValue(PERSPECTIVE_NAME_COLUMN)

    def setPerspectiveName(self, perspectiveName):
        """
        Set your perspective.
        """
        return self.getDbRecord().setColumnValue(PERSPECTIVE_NAME_COLUMN,
                                                 perspectiveName)

    def getNodeOid(self):
        """
        Return the Node OID of the node in this perspective ambit.
        """
        return self.getDbRecord().getColumnValue(NODE_OID_COLUMN)

    def setNodeOid(self, nodeOid):
        """
        Set the Node OID of the node in this perspective ambit.
        """
        return self.getDbRecord().setColumnValue(NODE_OID_COLUMN, nodeOid)


    def isStart(self):
        """
        Return True if this node is a START node. Start nodes say
        include this node and everything below it in the perspective.
        Inclusion below can be optionally stopped with a STOP node.
        """
        return self.getDbRecord().getColumnValue(IS_START_COLUMN)

    def setIsStart(self, isStart):
        """
        Set to True if this node is a START node. Start nodes say
        include this node and everything below it in the perspective.
        Inclusion below can be optionally stopped with a STOP node.
        """
        return self.getDbRecord().setColumnValue(IS_START_COLUMN, isStart)


    def isStop(self):
        """
        Return True if this node is a STOP node. Stop nodes stop the
        recursive inclusion that is started with START nodes.  The stop
        node itself is included in the perspective, but none of its
        children are.  A given node can be both a start and a stop node.
        """
        return self.getDbRecord().getColumnValue(IS_STOP_COLUMN)

    def setIsStop(self, isStop):
        """
        Set to True if this node is a STOP node. Stop nodes stop the
        recursive inclusion that is started with START nodes.  The stop
        node itself is included in the perspective, but none of its
        children are.  A given node can be both a start and a stop node.
        """
        return self.getDbRecord().setColumnValue(IS_STOP_COLUMN, isStop)


    def getComments(self):
        """
        Return the optional comments describing why this node is a
        start and/or stop node in this perspective.
        """
        return self.getDbRecord().getColumnValue(COMMENTS_COLUMN)

    def setComments(self, comments):
        """
        Set the optional comments describing why this node is a
        start and/or stop node in this perspective.
        """
        return self.getDbRecord().setColumnValue(COMMENTS_COLUMN, comments)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return


    def spew(self, label = ""):
        """
        Debugging routine to print the conents of the record
        """
        self.getDbRecord().spew(label)



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator():
    """
    Iterate through all perspective ambit records in no particular
    order.
    """
    return _table.Iterator()


def PerspectiveIterator(perspectiveName):
    """
    Iterate through perspective ambit records for the given perspective.

    Records are returned in this order
      all start records first,
      followed by all records that are both start and stop records,
      followed by all stop records.
    """
    where = (
        PERSPECTIVE_NAME_COLUMN + " = " +
        DbAccess.formatSqlValue(perspectiveName))
    orderBy = IS_START_COLUMN + " desc, " + IS_STOP_COLUMN + " asc"

    return _table.Iterator(where = where, orderBy = orderBy)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't be set until AnaPerspectiveAmbitDbRecord
# is defined.

_table = DbTable.registerTable(TABLE_NAME, AnaPerspectiveAmbitDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,              DbTable.IS_KEY)
_table.registerColumn(PERSPECTIVE_NAME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(NODE_OID_COLUMN,         DbTable.IS_NOT_KEY)
_table.registerColumn(IS_START_COLUMN,         DbTable.IS_NOT_KEY)
_table.registerColumn(IS_STOP_COLUMN,          DbTable.IS_NOT_KEY)
_table.registerColumn(COMMENTS_COLUMN,         DbTable.IS_NOT_KEY)

