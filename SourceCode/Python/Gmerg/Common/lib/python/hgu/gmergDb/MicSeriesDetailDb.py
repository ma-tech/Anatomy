#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the MIC_SERIES_DETAIL table in a GMERG style MIC relational
databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables
from hgu.db import DbRecord                         # DB Records


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME          = "MIC_SERIES_DETAIL"

OID_COLUMN          = "SDL_OID"
SERIES_COLUMN       = "SDL_SERIES_FK"
TABLE_COLUMN        = "SDL_TABLE"
TYPE_COLUMN         = "SDL_TYPE"
DESCRIPTION_COLUMN  = "SDL_DESCRIPTION"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None


# ------------------------------------------------------------------
# ISH SUBMISSION DB RECORD
# ------------------------------------------------------------------

class MicSeriesDetailDbRecord:
    """
    Python wrapper around DbRecord containing an MIC_SERIES_DETAIL database record.

    NOTE: This is not a complete wrapper.
     It only provides access to the data that we have needed so far.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an MIC_SERIES_DETAIL
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
        Return the MIC_SERIES_DETAIL record for this object.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of this Series_Detail item.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def getSeriesFK(self):
        """
        Return the Series ID (Foreign Key) of this Series_Detail item.
        """
        return self.getDbRecord().getColumnValue(SERIES_COLUMN)


    def getTable(self):
        """
        Return the Table this Series_Detail item.
        """
        return self.getDbRecord().getColumnValue(TABLE_COLUMN)


    def getType(self):
        """
        Return the Type of this Series_Detail item.
        """
        return self.getDbRecord().getColumnValue(TYPE_COLUMN)


    def getDescription(self):
        """
        Return the Description of this Series_Detail item.
        """
        return self.getDbRecord().getColumnValue(DESCRIPTION_COLUMN)


    """
    SET Methods
    """
    def setOid(self, value):
        """
        Set the OID of this Series_Detail item.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, value)


    def setSeriesFK(self, value):
        """
        Set the Series ID (Foreign Key) of this Series_Detail item.
        """
        return self.getDbRecord().setColumnValue(SERIES_COLUMN, value)


    def setTable(self, value):
        """
        Set the Table this Series_Detail item.
        """
        return self.getDbRecord().setColumnValue(TABLE_COLUMN, value)


    def setType(self, value):
        """
        Set the Type of this Series_Detail item.
        """
        return self.getDbRecord().setColumnValue(TYPE_COLUMN, value)


    def setDescription(self, value):
        """
        Set the Description of this Series_Detail item.
        """
        return self.getDbRecord().setColumnValue(DESCRIPTION_COLUMN, value)

    
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
    return MicSeriesDetailDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until MicSeriesDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, MicSeriesDetailDbRecord)

_table.registerColumn(OID_COLUMN,          DbTable.IS_KEY)
_table.registerColumn(SERIES_COLUMN,       DbTable.IS_NOT_KEY)
_table.registerColumn(TABLE_COLUMN,        DbTable.IS_NOT_KEY)
_table.registerColumn(TYPE_COLUMN,         DbTable.IS_NOT_KEY)
_table.registerColumn(DESCRIPTION_COLUMN,  DbTable.IS_NOT_KEY)
