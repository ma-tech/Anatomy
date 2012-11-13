#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ANA_STAGE table in a GMERG style anatomy relational database.
"""

from hgu.db import DbRecord
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
PUBLIC_ID_COLUMN        = "STG_PUBLIC_ID"


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

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing a
        Stage database record.

        if dbRecord is None, create a record with None in every column.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)
        self.__dbRecord = dbRecord

        return None

    def getDbRecord(self):
        """
        Return the DbRecord for this stage.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the database OID for this stage.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def setOid(self, oid):
        """
        Set the database OID for this stage.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, oid)


    def getSpecies(self):
        """
        Return the species name this stage is for.
        """
        return self.getDbRecord().getColumnValue(SPECIES_NAME_COLUMN)

    def setSpecies(self, species):
        """
        Set the species name this stage is for.
        """
        return self.getDbRecord().setColumnValue(SPECIES_NAME_COLUMN, species)


    def getName(self):
        """
        Return the name of this stage (e.g. TS20)
        """
        return self.getDbRecord().getColumnValue(NAME_COLUMN)

    def setName(self, name):
        """
        Set the name of this stage (e.g. TS20)
        """
        return self.getDbRecord().setColumnValue(NAME_COLUMN, name)


    def getSequence(self):
        """
        Return the sequence of this stage relative to other stages in the
        same staging series.  This value is used to order the stages.
        """
        return self.getDbRecord().getColumnValue(SEQUENCE_COLUMN)

    def setSequence(self, sequence):
        """
        Set the sequence of this stage relative to other stages in the
        same staging series.  This value is used to order the stages.
        """
        return self.getDbRecord().setColumnValue(SEQUENCE_COLUMN, sequence)


    def getDescription(self):
        """
        Get a description of the stage.
        """
        return self.getDbRecord().getColumnValue(DESCRIPTION_COLUMN)

    def setDescription(self, description):
        """
        Set a description of the stage.
        """
        return self.getDbRecord().setColumnValue(DESCRIPTION_COLUMN, description)


    def getShortExtraText(self):
        """
        Get a short text string describing the stage.
        """
        return self.getDbRecord().getColumnValue(SHORT_EXTRA_TEXT_COLUMN)

    def setShortExtraText(self, shortExtraText):
        """
        Set a short text string describing the stage.
        """
        return self.getDbRecord().setColumnValue(SHORT_EXTRA_TEXT_COLUMN,
                                                 shortExtraText)


    def getPublicId(self):
        """
        Get the public ID of the stage.  None if stage does not have one.
        """
        return self.getDbRecord().getColumnValue(PUBLIC_ID_COLUMN)

    def setPublicId(self, publicId):
        """
        Set the public ID of the stage.
        """
        return self.getDbRecord().setColumnValue(PUBLIC_ID_COLUMN, publicId)



    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return



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
_table.registerColumn(PUBLIC_ID_COLUMN,        DbTable.IS_NOT_KEY)

