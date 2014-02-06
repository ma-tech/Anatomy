#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the MIC_GENE_LIST_ITEM table in a GMERG style MIC relational
databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables
from hgu.db import DbRecord                         # DB Records



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "MIC_GENE_LIST_ITEM"

OID_COLUMN          = "GLI_OID"
SAMPLE_COLUMN       = "GLI_SAMPLE_FK"
SERIAL_COLUMN       = "GLI_SERIAL_NO"
PROBE_SET_COLUMN    = "GLI_PROBE_SET_NAME"
STAT_PAIRS_COLUMN   = "GLI_STAT_PAIRS"
PAIRS_USED_COLUMN   = "GLI_STAT_PAIRS_USED"
SIGNAL_COLUMN       = "GLI_SIGNAL"
DETECTION_COLUMN    = "GLI_DETECTION"
PVALUE_COLUMN       = "GLI_P_VALUE"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None


# ------------------------------------------------------------------
# ISH SUBMISSION DB RECORD
# ------------------------------------------------------------------

class MicGeneListItemDbRecord:
    """
    Python wrapper around DbRecord containing an MIC_GENE_LIST_ITEM database record.

    NOTE: This is not a complete wrapper.
     It only provides access to the data that we have needed so far.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an MIC_GENE_LIST_ITEM
        database record.
        
        if dbRecord is None, create an empty record.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord
        return None

    def getDbRecord(self):
        """
        Return the MIC_GENE_LIST_ITEM record for this object.
        """
        return self.__dbRecord

    """
    GET Methods
    """
    def getOid(self):
        """
        Return the OID of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)


    def getSampleFK(self):
        """
        Return the Sample ID (Foreign Key) of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(SAMPLE_COLUMN)

    def getSerialNo(self):
        """
        Return the Serial Number of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(SERIAL_COLUMN)
    
    def getProbeSetId(self):
        """
        Return the Probe Set ID of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(PROBE_SET_COLUMN)
    
    def getStatPairs(self):
        """
        Return the Stat Pairs of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(STAT_PAIRS_COLUMN)
    
    def getStatPairsUsed(self):
        """
        Return the Stat Pairs Used of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(PAIRS_USED_COLUMN)
    
    def getSignal(self):
        """
        Return the Signal of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(SIGNAL_COLUMN)
    
    def getDetection(self):
        """
        Return the Detection of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(DETECTION_COLUMN)
    
    def getPValue(self):
        """
        Return the P Value of this Gene List item.
        """
        return self.getDbRecord().getColumnValue(PVALUE_COLUMN)



    """
    SET Methods
    """
    def setOid(self, value):
        """
        Return the OID of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, value)

    def setSampleFK(self, value):
        """
        Return the Sample ID (Foreign Key) of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(SAMPLE_COLUMN, value)

    def setSerialNo(self, value):
        """
        Return the Serial Number of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(SERIAL_COLUMN, value)
    
    def setProbeSetId(self, value):
        """
        Return the Probe Set ID of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(PROBE_SET_COLUMN, value)
    
    def setStatPairs(self, value):
        """
        Return the Stat Pairs of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(STAT_PAIRS_COLUMN, value)
    
    def setStatPairsUsed(self, value):
        """
        Return the Stat Pairs Used of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(PAIRS_USED_COLUMN, value)
    
    def setSignal(self, value):
        """
        Return the Signal of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(SIGNAL_COLUMN, value)
    
    def setDetection(self, value):
        """
        Return the Detection of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(DETECTION_COLUMN, value)
    
    def setPValue(self, value):
        """
        Return the P Value of this Gene List item.
        """
        return self.getDbRecord().setColumnValue(PVALUE_COLUMN, value)
    
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
    Returns the gene list item row with the given OID, or None, if
    row does not exist.
    Throws exception if more than one record exists.
    """

    sqlOid = DbAccess.formatSqlValue(oid)
    return MicGeneListItemDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))
    

def getMaxOid():
    """
    Return the current maximum OID in the table.
    """

    return DbAccess.selectMaxOid(_table)


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until IshSubmissionDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, MicGeneListItemDbRecord)

_table.registerColumn(OID_COLUMN,          DbTable.IS_KEY)
_table.registerColumn(SAMPLE_COLUMN,       DbTable.IS_NOT_KEY)
_table.registerColumn(SERIAL_COLUMN,       DbTable.IS_NOT_KEY)
_table.registerColumn(STAT_PAIRS_COLUMN,   DbTable.IS_NOT_KEY)
_table.registerColumn(PAIRS_USED_COLUMN,   DbTable.IS_NOT_KEY)
_table.registerColumn(SIGNAL_COLUMN,       DbTable.IS_NOT_KEY)
_table.registerColumn(DETECTION_COLUMN,    DbTable.IS_NOT_KEY)
_table.registerColumn(PVALUE_COLUMN,       DbTable.IS_NOT_KEY)
