#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around ISH_EXPRESSION_NOTE records from the ISH relational databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord                         # DB Records
from hgu.db import DbTable                          # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME        = "ISH_EXPRESSION_NOTE"

OID_COLUMN                 = "ENT_OID"
EXPRESSION_OID_COLUMN      = "ENT_EXPRESSION_FK"
VALUE_COLUMN               = "ENT_VALUE"
SEQ_COLUMN                 = "ENT_SEQ"

VALUE_COLUMN_SIZE = 2000


# ------------------------------------------------------------------
# GLOBALS - initialised when module is loaded
# ------------------------------------------------------------------

_table = None




# ------------------------------------------------------------------
# ISH EXPRESSION DB RECORD
# ------------------------------------------------------------------

class IshExpressionNoteDbRecord:
    """
    Python wrapper around DbRecord containing a ISH_EXPRESSION_NOTE record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ISH_EXPRESSION_NOTE database record.

        If dbRecord is None, create an empty record.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None

    def __getDbRecord(self):
        """
        Return the ISH_EXPRESSION_NOTE record.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Get the OID of this expression note.  This is unique within the table,
        but not within the database.
        """
        return self.__getDbRecord().getColumnValue(OID_COLUMN)

    def getExpressionOid(self):
        """
        Get the OID of the ISH_EXPRESSION record this note is for.
        """
        return self.__getDbRecord().getColumnValue(EXPRESSION_OID_COLUMN)

    def getValue(self):
        """
        Get the text of the note.
        """
        return self.__getDbRecord().getColumnValue(VALUE_COLUMN)

    def getSeq(self):
        """
        Notes longer than VALUE_COLUMN_SIZE are divided up into multiple
        records.  The sequence gives the order of each chunk.
        """
        return self.__getDbRecord().getColumnValue(SEQ_COLUMN)



    def setOid(self, oid):
        """
        Set the OID (unique identifier) of this note / note chunk.  This is
        unique within the table.
        """
        return self.__getDbRecord().setColumnValue(OID_COLUMN, oid)


    def setExpressionOid(self, expressionOid):
        """
        Set the ISH_EXPRESSION record this note is for.
        """
        return self.__getDbRecord().setColumnValue(EXPRESSION_OID_COLUMN,
                                                   expressionOid)

    def setValue(self, value):
        """
        Set the text of the note.
        """
        return self.__getDbRecord().setColumnValue(VALUE_COLUMN, value)

    def setSeq(self, seq):
        """
        Set the chunk number of this note chunk.
        """
        return self.__getDbRecord().setColumnValue(SEQ_COLUMN, seq)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.__getDbRecord().insert()

        return




# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

def Iterator():
    """
    Iterate through all expression note records, in no particular order
    """
    return _table.Iterator()




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByOid(exprOid):
    """
    Return the ISH_EXPRESSION_NOTE DB record with the given OID.
    """

    return IshExpressionNoteDbRecord(DbAccess.selectOne(
        _table, where = OID_COLUMN + " = " + str(exprOid)))


def getMaxOid():
    """
    Return the current maximum OID in the table.
    """

    return DbAccess.selectMaxOid(_table)



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until IshExpressionNoteDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME,  IshExpressionNoteDbRecord)

_table.registerColumn(OID_COLUMN, DbTable.IS_KEY)

_table.registerColumn(EXPRESSION_OID_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(VALUE_COLUMN,          DbTable.IS_NOT_KEY)
_table.registerColumn(SEQ_COLUMN,            DbTable.IS_NOT_KEY)




