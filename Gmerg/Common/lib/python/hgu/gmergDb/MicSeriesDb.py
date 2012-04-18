#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the MIC_SERIES table in a GMERG style MIC relational
databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables
from hgu.db import DbRecord                         # DB Records


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME          = "MIC_SERIES"

OID_COLUMN          = "SER_OID"
PLATFORM_COLUMN     = "SER_PLATFORM_FK"
GEO_COLUMN          = "SER_GEO_ID"
TITLE_COLUMN        = "SER_TITLE"
SUMMARY_COLUMN      = "SER_SUMMARY"
TYPE_COLUMN         = "SER_TYPE"
DESIGN_COLUMN       = "SER_OVERALL_DESIGN"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None


# ------------------------------------------------------------------
# ISH SUBMISSION DB RECORD
# ------------------------------------------------------------------

class MicSeriesDbRecord:
    """
    Python wrapper around DbRecord containing an MIC_SERIES database record.

    NOTE: This is not a complete wrapper.
     It only provides access to the data that we have needed so far.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an MIC_SERIES
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
        Return the MIC_SERIES record for this object.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of this Series item.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)


    def getPlatformFK(self):
        """
        Return the Platform ID (Foreign Key) of this Series item.
        """
        return self.getDbRecord().getColumnValue(PLATFORM_COLUMN)


    def getGeoId(self):
        """
        Return the GEO Id of this Series item.
        """
        return self.getDbRecord().getColumnValue(GEO_COLUMN)


    def getTitle(self):
        """
        Return the Title of this Series item.
        """
        return self.getDbRecord().getColumnValue(TITLE_COLUMN)


    def getSummary(self):
        """
        Return the Summary of this Series item.
        """
        return self.getDbRecord().getColumnValue(SUMMARY_COLUMN)


    def getType(self):
        """
        Return the Type of this Series item.
        """
        return self.getDbRecord().getColumnValue(TYPE_COLUMN)


    def getDesign(self):
        """
        Return the Design of this Series item.
        """
        return self.getDbRecord().getColumnValue(DESIGN_COLUMN)


    """
    SET Methods
    """
    def setOid(self, value):
        """
        Set the OID of this Series item.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, value)


    def setPlatformFK(self, value):
        """
        Set the Platform ID (Foreign Key) of this Series item.
        """
        return self.getDbRecord().setColumnValue(PLATFORM_COLUMN, value)


    def setGeoId(self, value):
        """
        Set the GEO Id of this Series item.
        """
        return self.getDbRecord().setColumnValue(GEO_COLUMN, value)


    def setTitle(self, value):
        """
        Set the Title of this Series item.
        """
        return self.getDbRecord().setColumnValue(TITLE_COLUMN, value)


    def setSummary(self, value):
        """
        Set the Summary of this Series item.
        """
        return self.getDbRecord().setColumnValue(SUMMARY_COLUMN, value)


    def setType(self, value):
        """
        Set the Type of this Series item.
        """
        return self.getDbRecord().setColumnValue(TYPE_COLUMN, value)


    def setDesign(self, value):
        """
        Set the Design of this Series item.
        """
        return self.getDbRecord().setColumnValue(DESIGN_COLUMN, value)
    
    
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
    return MicSeriesDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until MicSeriesDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, MicSeriesDbRecord)

_table.registerColumn(OID_COLUMN,          DbTable.IS_KEY)
_table.registerColumn(PLATFORM_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(GEO_COLUMN,          DbTable.IS_NOT_KEY)
_table.registerColumn(TITLE_COLUMN,        DbTable.IS_NOT_KEY)
_table.registerColumn(SUMMARY_COLUMN,      DbTable.IS_NOT_KEY)
_table.registerColumn(TYPE_COLUMN,         DbTable.IS_NOT_KEY)
_table.registerColumn(DESIGN_COLUMN,       DbTable.IS_NOT_KEY)
