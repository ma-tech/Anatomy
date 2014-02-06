#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around ISH_PATTERN records from the ISH relational databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord                         # DB Records
from hgu.db import DbTable                          # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME        = "ISH_PATTERN"

OID_COLUMN                 = "PTN_OID"
EXPRESSION_OID_COLUMN      = "PTN_EXPRESSION_FK"
PATTERN_COLUMN             = "PTN_PATTERN"



# ------------------------------------------------------------------
# GLOBALS - initialised when module is loaded
# ------------------------------------------------------------------

_table = None




# ------------------------------------------------------------------
# ISH PATTERN DB RECORD
# ------------------------------------------------------------------

class IshPatternDbRecord:
    """
    Python wrapper around DbRecord containing a ISH_PATTERN record.

    Patterns used to be a column in the ISH_EXPRESSION table.  However,
    GUDMAP wanted the ability to have more than one pattern per
    component, so patterns were put into a separate table.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ISH_PATTERN database record.

        If dbRecord is None, create an empty record.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None


    def __getDbRecord(self):
        """
        Return the ISH_PATTERN DB record.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of the ISH_PATTERN record.  This is unique within
        the table, but not across the GMERG database.
        """
        return self.__getDbRecord().getColumnValue(OID_COLUMN)


    def getExpressionOid(self):
        """
        Return the OID of the ISH_EXPRESSION record this expression result
        is associated with.
        """
        return self.__getDbRecord().getColumnValue(EXPRESSION_OID_COLUMN)


    def getPattern(self):
        """
        Return the pattern of expression.
        """
        return self.__getDbRecord().getColumnValue(PATTERN_COLUMN)


    def setOid(self, oid):
        """
        Set the OID column.  This is unique within the ISH_PATTERN table,
        but is not unique across tables in the GMERG tables.
        """
        return self.__getDbRecord().setColumnValue(OID_COLUMN, oid)

    def setExpressionOid(self, expressionOid):
        """
        Set the expression record that this pattern record is for.
        """
        return self.__getDbRecord().setColumnValue(EXPRESSION_OID_COLUMN,
                                                   expressionOid)

    def setPattern(self, pattern):
        """
        Set the pattern of this record.
        """
        return self.__getDbRecord().setColumnValue(PATTERN_COLUMN, pattern)



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
    Iterate through all pattern records, in no particular order
    """
    return _table.Iterator()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByOid(exprOid):
    """
    Return the ISH_PATTERN DB record with the given OID.
    """

    return IshPatternDbRecord(DbAccess.selectOne(
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

_table = DbTable.registerTable(TABLE_NAME, IshPatternDbRecord)

_table.registerColumn(OID_COLUMN, DbTable.IS_KEY)

_table.registerColumn(EXPRESSION_OID_COLUMN,      DbTable.IS_NOT_KEY)
_table.registerColumn(PATTERN_COLUMN,             DbTable.IS_NOT_KEY)


