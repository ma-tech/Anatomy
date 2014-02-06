#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the MIC_SAMPLE table in a GMERG style MIC relational
databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables
from hgu.db import DbRecord                         # DB Records


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "MIC_SAMPLE"

OID_COLUMN          = "SMP_OID"
FILE_NAME_COLUMN    = "SMP_TXT_FILENAME"
FILE_LOC_COLUMN     = "SMP_FILE_LOCATION"

LABEL_PROTOCOL      = "SMP_LABEL_PROTOCOL"
SAMPLE_TYPE         = "SMP_EXTRA_1"
DISEASE_STATE       = "SMP_EXTRA_2"
CELL_TYPE           = "SMP_EXTRA_3"
CELL_LINE           = "SMP_EXTRA_4"
GROWTH_CONDITIONS   = "SMP_EXTRA_5"
SAMPLE_TREATMENT    = "SMP_EXTRA_6"

GEO_ID              = "SMP_GEO_ID"
SUB_FK              = "SMP_SUBMISSION_FK"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None


# ------------------------------------------------------------------
# ISH SUBMISSION DB RECORD
# ------------------------------------------------------------------

class MicSampleDbRecord:
    """
    Python wrapper around DbRecord containing an MIC_SAMPLE database record.

    NOTE: This is not a complete wrapper.
     It only provides access to the data that we have needed so far.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an MIC_SAMPLE
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
        Return the MIC_SAMPLE record for this object.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of this Sample item.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)


    def getFileName(self):
        """
        Return the Sample ID (Foreign Key) of this Sample item.
        """
        return self.getDbRecord().getColumnValue(FILE_NAME_COLUMN)

    def getFileLocation(self):
        """
        Return the Serial Number of this Sample item.
        """
        return self.getDbRecord().getColumnValue(FILE_LOC_COLUMN)


    def getLabelProtocol(self):
        """
        Return the Labeling Protocol of this Sample item.
        """
        return self.getDbRecord().getColumnValue(LABEL_PROTOCOL)

    def getSampleType(self):
        """
        Return the Sample Type of this Sample item.
        """
        return self.getDbRecord().getColumnValue(SAMPLE_TYPE)

    def getDiseaseState(self):
        """
        Return the Disease State of this Sample item.
        """
        return self.getDbRecord().getColumnValue(DISEASE_STATE)

    def getCellType(self):
        """
        Return the Cell Type of this Sample item.
        """
        return self.getDbRecord().getColumnValue(CELL_TYPE)

    def getCellLine(self):
        """
        Return the Cell Line of this Sample item.
        """
        return self.getDbRecord().getColumnValue(CELL_LINE)

    def getGrowthConditions(self):
        """
        Return the Growth Conditions of this Sample item.
        """
        return self.getDbRecord().getColumnValue(GROWTH_CONDITIONS)

    def getSampleTreatment(self):
        """
        Return the Sample Treatment of this Sample item.
        """
        return self.getDbRecord().getColumnValue(SAMPLE_TREATMENT)

    def getGeoId(self):
        """
        Return the GEO Id  of this Sample item.
        """
        return self.getDbRecord().getColumnValue(GEO_ID)

    def getSubFk(self):
        """
        Return the Submission Foreign Key of this Sample item.
        """
        return self.getDbRecord().getColumnValue(SUB_FK)


    """
    SET Methods
    """
    def setOid(self, value):
        """
        Return the OID of this Sample item.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, value)

    def setFileName(self, value):
        """
        Return the Sample ID (Foreign Key) of this Sample item.
        """
        return self.getDbRecord().setColumnValue(FILE_NAME_COLUMN, value)

    def setFileLocation(self, value):
        """
        Return the Serial Number of this Sample item.
        """
        return self.getDbRecord().setColumnValue(FILE_LOC_COLUMN, value)
    

    def setLabelProtocol(self, value):
        """
        Return the Labeling Protocol of this Sample item.
        """
        return self.getDbRecord().setColumnValue(LABEL_PROTOCOL, value)

    def setSampleType(self, value):
        """
        Return the Sample Type of this Sample item.
        """
        return self.getDbRecord().setColumnValue(SAMPLE_TYPE, value)

    def setDiseaseState(self, value):
        """
        Return the Disease State of this Sample item.
        """
        return self.getDbRecord().setColumnValue(DISEASE_STATE, value)

    def setCellType(self, value):
        """
        Return the Cell Type of this Sample item.
        """
        return self.getDbRecord().setColumnValue(CELL_TYPE, value)

    def setCellLine(self, value):
        """
        Return the Cell Line of this Sample item.
        """
        return self.getDbRecord().setColumnValue(CELL_LINE, value)

    def setGrowthConditions(self, value):
        """
        Return the Growth Conditions of this Sample item.
        """
        return self.getDbRecord().setColumnValue(GROWTH_CONDITIONS, value)

    def setSampleTreatment(self, value):
        """
        Return the Sample Treatment of this Sample item.
        """
        return self.getDbRecord().setColumnValue(SAMPLE_TREATMENT, value)

    def setGeoId(self, value):
        """
        Set the GEO ID of this Sample item.
        """
        return self.getDbRecord().setColumnValue(GEO_ID, value)

    def setSubFk(self, value):
        """
        Set the Submission foreign key of this Sample item.
        """
        return self.getDbRecord().setColumnValue(SUB_FK, value)

    
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
    return MicSampleDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))


def getBySubFk(subfk):
    """
    Returns the sample row with the given Sub ID (FK), 
     or None, if row does not exist.
      Throws exception if more than one record exists.
    """

    sqlSubFk = DbAccess.formatSqlValue(subfk)
    return MicSampleDbRecord(DbAccess.selectOne(
        _table, SUB_FK + " = " + sqlSubFk))


def getByFileName(fname):
    """
    Returns the sample row with the given File Name,
     or None, if row does not exist.
      Throws exception if more than one record exists.
    """

    sqlFname = DbAccess.formatSqlValue(fname)
    return MicSampleDbRecord(DbAccess.selectOne(
        _table, FILE_NAME_COLUMN + " = " + sqlFname))


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until MicSampleDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, MicSampleDbRecord)

_table.registerColumn(OID_COLUMN,          DbTable.IS_KEY)
_table.registerColumn(FILE_NAME_COLUMN,    DbTable.IS_NOT_KEY)
_table.registerColumn(FILE_LOC_COLUMN,     DbTable.IS_NOT_KEY)

_table.registerColumn(LABEL_PROTOCOL,      DbTable.IS_NOT_KEY)
_table.registerColumn(SAMPLE_TYPE,         DbTable.IS_NOT_KEY)
_table.registerColumn(DISEASE_STATE,       DbTable.IS_NOT_KEY)
_table.registerColumn(CELL_TYPE,           DbTable.IS_NOT_KEY)
_table.registerColumn(CELL_LINE,           DbTable.IS_NOT_KEY)
_table.registerColumn(GROWTH_CONDITIONS,   DbTable.IS_NOT_KEY)
_table.registerColumn(SAMPLE_TREATMENT,    DbTable.IS_NOT_KEY)

_table.registerColumn(GEO_ID,              DbTable.IS_NOT_KEY)
_table.registerColumn(SUB_FK,              DbTable.IS_NOT_KEY)
