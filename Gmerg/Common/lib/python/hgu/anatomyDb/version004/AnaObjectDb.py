#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Low level wrapper around the ANA_OBJECT table in a GMERG style anatomy
relational database.

In hindsight, I perhaps should have called this the ANA_OID table.

The high-level wrapper around the ANA_OBJECT table is the Oids module,
because I really didn't want to create an Objects module.
"""

from hgu.db import DbRecord
from hgu.db import DbTable              # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


TABLE_NAME = "ANA_OBJECT"

OID_COLUMN                = "OBJ_OID"
CREATION_DATETIME_COLUMN  = "OBJ_CREATION_DATETIME"
CREATOR_OID_COLUMN        = "OBJ_CREATOR_FK"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# STAGE DB RECORD
# ------------------------------------------------------------------

class AnaObjectDbRecord:
    """
    Wrapper around database records from the ANA_OBJECT table.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an ANA_OBJECT
        database record.

        If dbRecord is none, create a record with None in all columns.
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
        Return the database OID for this record.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def setOid(self, oid):
        """
        Set the OID column.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, oid)


    def getCreationDateTime(self):
        """
        Return the creation datetime the object this OID is for was created.

        A value of None means we don't know or don't care.

        In the long run, should this be replaced with a version identifier
        instead?
        """
        return self.getDbRecord().getColumnValue(CREATION_DATETIME_COLUMN)

    def setCreationDatetime(self, datetime):
        """
        Set the creation datetime for the object this OID is for.
        """
        return self.getDbRecord().setColumnValue(CREATION_DATETIME_COLUMN,
                                                 datetime)

    def getCreatorOid(self):
        """
        Return tyhe OID of the creator if known.

        Now there's a philosophical question: What is the OID of the
        Creator?
        """
        return self.getDbRecord().getColumnValue(CREATOR_OID_COLUMN)

    def setCreatorOid(self, creatorOid):
        """
        Save the OID of the creator of the object this OID is for.
        """
        return self.getDbRecord().setColumnValue(CREATOR_OID_COLUMN, creatorOid)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

def Iterator():
    """
    Iterate through all ANA_OBJECT records, in no particular order
    """
    return _table.Iterator()


# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Has to be after AnaObjectDbRecord is defined.

_table = DbTable.registerTable("ANA_OBJECT", AnaObjectDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,               DbTable.IS_KEY)
_table.registerColumn(CREATION_DATETIME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(CREATOR_OID_COLUMN,       DbTable.IS_NOT_KEY)
