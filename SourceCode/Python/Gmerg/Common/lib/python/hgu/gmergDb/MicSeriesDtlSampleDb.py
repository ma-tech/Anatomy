#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the MIC_SERIES_DTL_SAMPLE table in a GMERG style MIC relational
databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables
from hgu.db import DbRecord                         # DB Records


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME          = "MIC_SERIES_DTL_SAMPLE"

OID_COLUMN          = "SDS_OID"
SERIES_COLUMN       = "SDS_SERIES_DETAIL_FK"
SAMPLE_COLUMN       = "SDS_SAMPLE_FK"
SAMPLE_ID_COLUMN    = "SDS_SAMPLE_ID"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None


# ------------------------------------------------------------------
# ISH SUBMISSION DB RECORD
# ------------------------------------------------------------------

class MicSeriesDtlSampleDbRecord:
    """
    Python wrapper around DbRecord containing an MIC_SERIES_DTL_SAMPLE database record.

    NOTE: This is not a complete wrapper.
     It only provides access to the data that we have needed so far.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an MIC_SERIES_DTL_SAMPLE
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
        Return the MIC_SERIES_DTL_SAMPLE record for this object.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of this Series_Dtl_Sample item.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)


    def getSeriesFK(self):
        """
        Return the Series ID (Foreign Key) of this Series_Dtl_Sample item.
        """
        return self.getDbRecord().getColumnValue(SERIES_COLUMN)


    def getSampleFK(self):
        """
        Return the Sample ID (Foreign Key) of this Series_Dtl_Sample item.
        """
        return self.getDbRecord().getColumnValue(SAMPLE_COLUMN)


    def getSampleId(self):
        """
        Return the Sample Id of this Series_Dtl_Sample item.
        """
        return self.getDbRecord().getColumnValue(SAMPLE_ID_COLUMN)


    """
    SET Methods
    """
    def setOid(self, value):
        """
        Return the OID of this Series_Dtl_Sample item.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, value)


    def setSeriesFK(self, value):
        """
        Return the Series ID (Foreign Key) of this Series_Dtl_Sample item.
        """
        return self.getDbRecord().setColumnValue(SERIES_COLUMN, value)


    def setSampleFK(self, value):
        """
        Return the Sample Id (Foreign Key) of this Series_Dtl_Sample item.
        """
        return self.getDbRecord().setColumnValue(SAMPLE_COLUMN, value)


    def setSampleId(self, value):
        """
        Return the Sample Id of this Series_Dtl_Sample item.
        """
        return self.getDbRecord().setColumnValue(SAMPLE_ID_COLUMN, value)
    
    
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
    return MicSeriesDtlSampleDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until MicSeriesDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, MicSeriesDtlSampleDbRecord)

_table.registerColumn(OID_COLUMN,          DbTable.IS_KEY)
_table.registerColumn(SERIES_COLUMN,       DbTable.IS_NOT_KEY)
_table.registerColumn(SAMPLE_COLUMN,       DbTable.IS_NOT_KEY)
_table.registerColumn(SAMPLE_ID_COLUMN,    DbTable.IS_NOT_KEY)
