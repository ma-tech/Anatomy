#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around REF_SEQUENCES record from the REF relational databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord                         # DB Records
from hgu.db import DbTable                          # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME        = "REF_SEQUENCES"

SUBMISSION_COLUMN = "SEQ_SUBMISSION"
EXPRESSION_COLUMN = "SEQ_EXPRESSION"



# ------------------------------------------------------------------
# GLOBALS - initialised when module is loaded
# ------------------------------------------------------------------

_table = None




# ------------------------------------------------------------------
# ISH EXPRESSION DB RECORD
# ------------------------------------------------------------------

class RefSequencesDbRecord:
    """
    Python wrapper around DbRecord containing the REF_SEQUENCES record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        REF_SEQUENCES database record.

        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)
            
        self.__dbRecord = dbRecord

        return None
    

    def __getDbRecord(self):
        """
        Return the REF_SEQUENCES DB record.
        """
        return self.__dbRecord


    def getSubmission(self):
        """
        Return the SEQ_SUBMISSION of the REF_SEQUENCES record.
        """
        return self.__getDbRecord().getColumnValue(SUBMISSION_COLUMN)

    def getExpression(self):
        """
        Return the SEQ_EXPRESSION of the REF_SEQUENCES record.
        """
        return self.__getDbRecord().getColumnValue(EXPRESSION_COLUMN)


    def setSubmission(self, submission):
        """
        Set the SEQ_SUBMISSION column.
        """
        return self.__getDbRecord().setColumnValue(SUBMISSION_COLUMN, submission)

    def setExpression(self, expression):
        """
        Set the SEQ_EXPRESSION column.
        """
        return self.__getDbRecord().setColumnValue(EXPRESSION_COLUMN, expression)


    def update(self):
        """
        Generate update statement to update this record in the database.
        """
        self.__getDbRecord().update()


# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getBySubmission():
    """
    Return the REF_SEQUENCES DB record with the given OID.
    """

    return RefSequencesDbRecord(DbAccess.selectOne(_table, where = ""))

# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until IshExpressionDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, RefSequencesDbRecord)

_table.registerColumn(SUBMISSION_COLUMN, DbTable.IS_KEY)

_table.registerColumn(EXPRESSION_COLUMN, DbTable.IS_NOT_KEY)


