#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the MIC_PLATFORM table in a GMERG style MIC relational
databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables
from hgu.db import DbRecord                         # DB Records


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "MIC_PLATFORM"

OID_COLUMN          = "PLT_OID"
GEO_COLUMN          = "PLT_GEO_ID"
TITLE_COLUMN        = "PLT_TITLE"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None


# ------------------------------------------------------------------
# ISH SUBMISSION DB RECORD
# ------------------------------------------------------------------

class MicPlatformDbRecord:
    """
    Python wrapper around DbRecord containing an MIC_PLATFORM database record.

    NOTE: This is not a complete wrapper.
     It only provides access to the data that we have needed so far.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an MIC_PLATFORM
        database record.
        
        if dbRecord is None, create an empty record.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)
            
        self.__dbRecord = dbRecord
        return None

    """
    GET Methods
    """

    def getDbRecord(self):
        """
        Return the MIC_PLATFORM record for this object.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of this platform item.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)


    def getGeoId(self):
        """
        Return the GEO ID of this Platform item.
        """
        return self.getDbRecord().getColumnValue(GEO_COLUMN)


    def getTitle(self):
        """
        Return the Title of this Platform item.
        """
        return self.getDbRecord().getColumnValue(TITLE_COLUMN)


    """
    SET Methods
    """
    def setOid(self, value):
        """
        set the OID of this platform item.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, value)


    def setGeoId(self, value):
        """
        set the GEO ID of this platform item.
        """
        return self.getDbRecord().setColumnValue(GEO_COLUMN, value)


    def setTitle(self, value):
        """
        set the title of this platform item.
        """
        return self.getDbRecord().setColumnValue(TITLE_COLUMN, value)
    
    
    """
    Database Methods
    """
    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return

    def update(self):
        """
        Generate update statement to update this record in the database.
        """
        self.getDbRecord().update()


# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------
def Iterator():
    """
    Iterate through all gene list items.

    This makes a DB cursor appear as a Python iterator.
    """
    return _table.Iterator()


# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------
def getByOid(oid):
    """
    Returns the sample row with the given OID, 
     or None, if row does not exist.
      Throws exception if more than one record exists.
    """

    sqlOid = DbAccess.formatSqlValue(oid)
    return MicPlatformDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))


def getByGeoId(fname):
    """
    Returns the sample row with the given File Name,
     or None, if row does not exist.
      Throws exception if more than one record exists.
    """

    sqlFname = DbAccess.formatSqlValue(fname)
    return MicPlatformDbRecord(DbAccess.selectOne(
        _table, GEO_COLUMN + " = " + sqlFname))


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until MicSampleDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, MicPlatformDbRecord)

_table.registerColumn(OID_COLUMN,          DbTable.IS_KEY)
_table.registerColumn(GEO_COLUMN,          DbTable.IS_NOT_KEY)
_table.registerColumn(TITLE_COLUMN,        DbTable.IS_NOT_KEY)

