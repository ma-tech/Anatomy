#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ANAD_PART_OF_PERSPECTIVE derived table in a GMERG
style relational database.  This derived tells which records in
ANAD_PART_OF belong to each perspective.  This table gives fast access to
an indented tree representation of the anatomy in each perspective.

The AnadPartOfDb module is aware of the ANAD_PART_OF_PERSPECTIVE table.
It may not even be necessary to use this module.
"""
from hgu.db import DbRecord
from hgu.db import DbTable                          # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANAD_PART_OF_PERSPECTIVE"

PERSPECTIVE_NAME_COLUMN       = "POP_PERSPECTIVE_FK"
APO_OID_COLUMN                = "POP_APO_FK"
IS_ANCESTOR_COLUMN            = "POP_IS_ANCESTOR"
NODE_OID_COLUMN               = "POP_NODE_FK"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# PART OF DERIVED DB RECORD
# ------------------------------------------------------------------

class AnadPartOfPerspectiveDbRecord:
    """
    Python wrapper around DbRecord containing an ANAD_PART_OF_PERSPECTIVE
    database record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ANAD_PART_OF_PERSPECTIVE database record.

        If dbRecord is None create the record with None in all the columns.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None

    def getDbRecord(self):
        """
        Return the DB record for this object.
        """
        return self.__dbRecord

    def getPerspectiveName(self):
        """
        Return the name of the perspective the ANAD_PART_OF record is in.
        """
        return self.getDbRecord().getColumnValue(PERSPECTIVE_NAME_COLUMN)

    def setPerspectiveName(self, perspectiveName):
        """
        Set the name of the perspective the ANAD_PART_OF record is in.
        """
        return self.getDbRecord().setColumnValue(PERSPECTIVE_NAME_COLUMN,
                                                 perspectiveName)


    def getApoOid(self):
        """
        Return the database OID of the ANAD_PART_OF record that is in
        this perspective.
        """
        return self.getDbRecord().getColumnValue(APO_OID_COLUMN)

    def setApoOid(self, apoOid):
        """
        Set the database OID of the ANAD_PART_OF record that is in
        this perspective.
        """
        return self.getDbRecord().setColumnValue(APO_OID_COLUMN, apoOid)


    def isAncestor(self):
        """
        Returns True if this node/APO is included in this perspective only because
        it is an ancestor on nodes/APOs that are in this this perspective.  Ancestor
        nodes are included in case the application wants to display the context of
        the perspective, as well as the nodes in the perspective itself.
        """
        return self.getDbRecord().getColumnValue(IS_ANCESTOR_COLUMN)

    def setIsAncestor(self, isAncestor):
        """
        isAncestor = True means this node/APO is included in this perspective only because
        it is an ancestor on nodes/APOs that are in this this perspective.  Ancestor
        nodes are included in case the application wants to display the context of
        the perspective, as well as the nodes in the perspective itself.
        """
        return self.getDbRecord().setColumnValue(IS_ANCESTOR_COLUMN,
                                                 isAncestor)


    def getNodeOid(self):
        """
        Return the database OID of the node the ANAD_PART_OF record is for.
        """
        return self.getDbRecord().getColumnValue(NODE_OID_COLUMN)

    def setNodeOid(self, nodeOid):
        """
        Set the database OID of the node the ANAD_PART_OF record is for.
        """
        return self.getDbRecord().setColumnValue(NODE_OID_COLUMN,
                                                 nodeOid)

    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator():
    """
    Iterate through all ANAD_PART_OF_PERSPECTIVE records, in no particular order
    """
    return _table.Iterator()





# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't initiliaise these until after
# AnadPartOfPerspectiveDbRecord is defined above.

_table = DbTable.registerTable(TABLE_NAME, AnadPartOfPerspectiveDbRecord,
                               DbTable.NOT_IN_ANA_OBJECT)

_table.registerColumn(PERSPECTIVE_NAME_COLUMN,     DbTable.IS_KEY)
_table.registerColumn(APO_OID_COLUMN,              DbTable.IS_KEY)
_table.registerColumn(IS_ANCESTOR_COLUMN,          DbTable.IS_NOT_KEY)
_table.registerColumn(NODE_OID_COLUMN,             DbTable.IS_NOT_KEY)
