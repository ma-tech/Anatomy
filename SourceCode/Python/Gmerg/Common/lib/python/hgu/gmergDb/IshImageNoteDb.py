#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around ISH_IMAGE_NOTE records from the ISH relational databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord                         # DB Records
from hgu.db import DbTable                          # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME        = "ISH_IMAGE_NOTE"

OID_COLUMN        = "INT_OID"
IMAGE_OID_COLUMN  = "INT_IMAGE_FK"
VALUE_COLUMN      = "INT_VALUE"
SEQ_COLUMN        = "INT_SEQ"

VALUE_COLUMN_SIZE = 2000  # number of chars that can fit in INT_VALUE.


# ------------------------------------------------------------------
# GLOBALS - initialised when module is loaded
# ------------------------------------------------------------------

_table = None




# ------------------------------------------------------------------
# ISH IMAGE NOTE DB RECORD
# ------------------------------------------------------------------

class IshImageNoteDbRecord:
    """
    Python wrapper around DbRecord containing a ISH_IMAGE_NOTE record.

    These notes are attached to images in the ISH_ORIGINAL_IMAGE table.
    Each record contains up to 2000 characters of text.  If a note is
    longer than that, then mubliple records are created.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ISH_IMAGE_NOTE database record.

        If dbRecord is None, create an empty record.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None


    def __getDbRecord(self):
        """
        Return the ISH_IMAGE_NOTE DB record.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of the ISH_IMAGE_NOTE record.  This is unique within
        the table, but not across the GMERG database.
        """
        return self.__getDbRecord().getColumnValue(OID_COLUMN)


    def getImageOid(self):
        """
        Return the OID of the ISH_ORIGINAL_IMAGE record this note
        is associated with.
        """
        return self.__getDbRecord().getColumnValue(IMAGE_OID_COLUMN)


    def getValue(self):
        """
        Return the note.
        """
        return self.__getDbRecord().getColumnValue(VALUE_COLUMN)


    def getSeq(self):
        """
        Return the sequence value for this part of the note.  Notes that
        are longer than 2000 characters are split up into multiple records,
        each with a different seq value.  Seq values start at 1.
        """
        return self.__getDbRecord().getColumnValue(SEQ_COLUMN)



    def setOid(self, oid):
        """
        Set the OID column.  This is unique within the ISH_IMAGE_NOTE table,
        but is not unique across tables in the GMERG tables.
        """
        return self.__getDbRecord().setColumnValue(OID_COLUMN, oid)


    def setImageOid(self, imageOid):
        """
        Set the original image record that this note record is for.
        """
        return self.__getDbRecord().setColumnValue(IMAGE_OID_COLUMN,
                                                   imageOid)

    def setValue(self, value):
        """
        Set the value (the note) of this record.
        """
        return self.__getDbRecord().setColumnValue(VALUE_COLUMN, value)



    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.__getDbRecord().insert()

        return


    def update(self):
        """
        Generate update statement to update this record in the database.
        """
        self.__getDbRecord().update()



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------


def ImageOidIterator(imageOid):
    """
    Iterate through all image note records for the given OID, ordered by
    note sequence.
    """
    where = IMAGE_OID_COLUMN + " = " + DbAccess.formatSqlValue(imageOid)
    orderBy = SEQ_COLUMN

    return _table.Iterator(where = where, orderBy = orderBy)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByOid(exprOid):
    """
    Return the ISH_IMAGE_NOTE DB record with the given OID.
    """

    return IshImageNoteDbRecord(DbAccess.selectOne(
        _table, where = OID_COLUMN + " = " + str(exprOid)))





def getMaxOid():
    """
    Return the current maximum OID in the table.
    """

    return DbAccess.selectMaxOid(_table)


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until IshExpressionDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, IshImageNoteDbRecord)

_table.registerColumn(OID_COLUMN, DbTable.IS_KEY)

_table.registerColumn(IMAGE_OID_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(VALUE_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(SEQ_COLUMN,       DbTable.IS_NOT_KEY)


