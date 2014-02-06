#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ANA_RELATIONSHIP table in a GMERG style relational
database.  Relationships can be between nodes or timed nodes.
"""

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord
from hgu.db import DbTable              # DB Tables



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_RELATIONSHIP"

OID_COLUMN                    = "REL_OID"
RELATIONSHIP_TYPE_NAME_COLUMN = "REL_RELATIONSHIP_TYPE_FK"
CHILD_OID_COLUMN              = "REL_CHILD_FK"
PARENT_OID_COLUMN             = "REL_PARENT_FK"
SEQUENCE_COLUMN               = "REL_SEQUENCE"




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# NODE IN PERSPECTIVE RECORD
# ------------------------------------------------------------------

class AnaRelationshipDbRecord:
    """
    Python wrapper around DbRecord containing an ANA_RELATIONSHIP
    database record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ANA_RELATIONSHIP database record.

        If dbRecord is None then set all columns to None.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None

    def getDbRecord(self):
        """
        Return the DbRecord for this object.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the database OID for this object.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def setOid(self, oid):
        """
        Set the database OID for this object.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, oid)


    def getRelationshipType(self):
        """
        Return the relationship type name.
        """
        return self.getDbRecord().getColumnValue(RELATIONSHIP_TYPE_NAME_COLUMN)

    def setRelationshipType(self, relType):
        """
        Set the relationship type name.
        """
        return self.getDbRecord().setColumnValue(RELATIONSHIP_TYPE_NAME_COLUMN,
                                                 relType)

    def getChildOid(self):
        """
        Return the datbase OID of the child record in this relationship.
        """
        return self.getDbRecord().getColumnValue(CHILD_OID_COLUMN)

    def setChildOid(self, childOid):
        """
        Set the datbase OID of the child record in this relationship.
        """
        return self.getDbRecord().setColumnValue(CHILD_OID_COLUMN,
                                                 childOid)


    def getParentOid(self):
        """
        Return the database OID of the parent record in this relationship.
        """
        return self.getDbRecord().getColumnValue(PARENT_OID_COLUMN)

    def setParentOid(self, parentOid):
        """
        Set the database OID of the parent record in this relationship.
        """
        return self.getDbRecord().setColumnValue(PARENT_OID_COLUMN,
                                                 parentOid)


    def getSequence(self):
        """
        Return the order of this child relative to the parent's other children.
        """
        return self.getDbRecord().getColumnValue(SEQUENCE_COLUMN)

    def setSequence(self, sequence):
        """
        Set the order of this child relative to the parent's other children.
        """
        return self.getDbRecord().setColumnValue(SEQUENCE_COLUMN, sequence)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return


    def update(self):
        """
        Generate an update statement to update all non-key fields.
        """
        self.getDbRecord().update()

        return


    def spew(self, label = ""):
        """
        Debugging support routine.
        """
        self.getDbRecord().spew(label)

        return


# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator(where = None, orderBy = None):
    """
    Iterate through relationship records
    """
    return _table.Iterator(where = where, orderBy = orderBy)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByParent(parentOid, relType):
    """
    Return the all records with given OID as parent.
    """

    parentIter = Iterator(
        where = (
            PARENT_OID_COLUMN + " = " + DbAccess.formatSqlValue(parentOid) +
            " and " +
            RELATIONSHIP_TYPE_NAME_COLUMN + " = " +
            DbAccess.formatSqlValue(relType)))

    rels = []

    for dbRec in parentIter:
        rels.append(dbRec)

    return rels




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't be set until AnaRelationshipDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, AnaRelationshipDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,                    DbTable.IS_KEY)
_table.registerColumn(RELATIONSHIP_TYPE_NAME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(CHILD_OID_COLUMN,              DbTable.IS_NOT_KEY)
_table.registerColumn(PARENT_OID_COLUMN,             DbTable.IS_NOT_KEY)
_table.registerColumn(SEQUENCE_COLUMN,               DbTable.IS_NOT_KEY)

