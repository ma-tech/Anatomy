#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
#
# Wrapper around the ANA_STAGE table in a GMERG style anatomy relational database.
#

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord             # DB Records
from hgu.db import DbTable              # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


TABLE_NAME = "ANA_STAGE"

OID_COLUMN              = "STG_OID"
SPECIES_NAME_COLUMN     = "STG_SPECIES_FK"
NAME_COLUMN             = "STG_NAME"
SEQUENCE_COLUMN         = "STG_SEQUENCE"
DESCRIPTION_COLUMN      = "STG_DESCRIPTION"
SHORT_EXTRA_TEXT_COLUMN = "STG_SHORT_EXTRA_TEXT"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# STAGE DB RECORD
# ------------------------------------------------------------------

class AnaStageDbRecord:
    """
    Python wrapper around DbRecord containing a Stage database record.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing a
        Stage database record.
        """
        self.__dbRecord = dbRecord;
        
        return None

    def getDbRecord(self):
        return self.__dbRecord

    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)
    
    def getSpecies(self):
        return self.getDbRecord().getColumnValue(SPECIES_NAME_COLUMN)
    
    def getName(self):
        return self.getDbRecord().getColumnValue(NAME_COLUMN)

    def getSequence(self):
        return self.getDbRecord().getColumnValue(SEQUENCE_COLUMN)
    
    def getDescription(self):
        return self.getDbRecord().getColumnValue(DESCRIPTION_COLUMN)
    
    def getShortExtraText(self):
        return self.getDbRecord().getColumnValue(SHORT_EXTRA_TEXT_COLUMN)




# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------


def SequenceIterator():
    """
    Iterate through all stage records, in ascending sequence
    (chronological) order.
    """
    return _table.Iterator(orderBy = SEQUENCE_COLUMN)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Has to be after AnaStageDbRecord is defined.

_table = DbTable.registerTable("ANA_STAGE", AnaStageDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,              DbTable.IS_KEY)
_table.registerColumn(SPECIES_NAME_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(NAME_COLUMN,             DbTable.IS_NOT_KEY)
_table.registerColumn(SEQUENCE_COLUMN,         DbTable.IS_NOT_KEY)
_table.registerColumn(DESCRIPTION_COLUMN,      DbTable.IS_NOT_KEY)
_table.registerColumn(SHORT_EXTRA_TEXT_COLUMN, DbTable.IS_NOT_KEY)

