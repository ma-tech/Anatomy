#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ANA_RELATIONSHIP_PROJECT table in a GMERG style relational
database.  

Relationship_Project Rows exist between Relationships and Projects
 The exist to determine different Sequencing in different projects
"""

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord
from hgu.db import DbTable              # DB Tables



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_RELATIONSHIP_PROJECT"

OID_COLUMN                    = "RLP_OID"
RELATIONSHIP_FK_COLUMN        = "RLP_RELATIONSHIP_FK"
PROJECT_COLUMN                = "RLP_PROJECT_FK"
SEQUENCE_COLUMN               = "RLP_SEQUENCE"




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# NODE IN PERSPECTIVE RECORD
# ------------------------------------------------------------------

class AnaRelationshipProjectDbRecord:
    """
    Python wrapper around DbRecord containing an ANA_RELATIONSHIP_PROJECT
    database record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ANA_RELATIONSHIP_PROJECT database record.

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


    def getRelationshipFk(self):
        """
        Return the relationship type name.
        """
        return self.getDbRecord().getColumnValue(RELATIONSHIP_FK_COLUMN)

    def setRelationshipFk(self, relFk):
        """
        Set the relationship type name.
        """
        return self.getDbRecord().setColumnValue(RELATIONSHIP_FK_COLUMN,
                                                 relFk)

    def getProject(self):
        """
        Return the datbase OID of the child record in this relationship.
        """
        return self.getDbRecord().getColumnValue(PROJECT_COLUMN)

    def setProject(self, project):
        """
        Set the datbase OID of the child record in this relationship.
        """
        return self.getDbRecord().setColumnValue(PROJECT_COLUMN,
                                                 project)

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
def getByRelationShipFkAndProject(relfk, project):
    """
    Returns the Relationship Project row with the given 
     Relationship Id and Project, 
     or None, if row does not exist.
      Throws exception if more than one record exists.
    """

    sqlRelFk = DbAccess.formatSqlValue(relfk)
    sqlProjectFk = DbAccess.formatSqlValue(project)
    
    return AnaRelationshipProjectDbRecord(DbAccess.selectOne(_table, 
        RELATIONSHIP_FK_COLUMN + " = " + sqlRelFk + " AND " + 
        PROJECT_COLUMN + " = " + sqlProjectFk))


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't be set until AnaRelationshipDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, AnaRelationshipProjectDbRecord,
                               DbTable.NOT_IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,                    DbTable.IS_KEY)
_table.registerColumn(RELATIONSHIP_FK_COLUMN,        DbTable.IS_NOT_KEY)
_table.registerColumn(PROJECT_COLUMN,                DbTable.IS_NOT_KEY)
_table.registerColumn(SEQUENCE_COLUMN,               DbTable.IS_NOT_KEY)

