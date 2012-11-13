#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ANA_PERSPECTIVE in a GMERG style relational anatomy
database.
"""

from hgu.db import DbRecord
from hgu.db import DbTable                          # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_PERSPECTIVE"

NAME_COLUMN       = "PSP_NAME"
COMMENTS_COLUMN   = "PSP_COMMENTS"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# PART OF DERIVED DB RECORD
# ------------------------------------------------------------------

class AnaPerspectiveDbRecord:
    """
    Python wrapper around DbRecord containing an ANA_PERSPECTIVE
    database record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ANA_PERSPECTIVE database record.

        If dbRecord is none then set all columns to None.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None

    def getDbRecord(self):
        """
        Return the DbRecord for this ANA_PERSPECTIVE record.
        """
        return self.__dbRecord

    def getName(self):
        """
        Return the name of this perspective.
        """
        return self.getDbRecord().getColumnValue(NAME_COLUMN)

    def setName(self, name):
        """
        Set the name of this perspective.
        """
        return self.getDbRecord().setColumnValue(NAME_COLUMN, name)


    def getComments(self):
        """
        Return the description of this perspective.
        """
        return self.getDbRecord().getColumnValue(COMMENTS_COLUMN)

    def setComments(self, comments):
        """
        Set the description of this perspective.
        """
        return self.getDbRecord().setColumnValue(COMMENTS_COLUMN, comments)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return

# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

# See the AnadPartOfDb module for iterators that access ANAD_PART_OF records
# based on perspective membership.


# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

def Iterator():
    """
    Iterate through all perspective definitions, in no particular order.
    """
    return _table.Iterator()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't initiliaise these until after
# AnaPerspectiveDbRecord is defined above.

_table = DbTable.registerTable(TABLE_NAME, AnaPerspectiveDbRecord,
                               DbTable.NOT_IN_ANA_OBJECT)

_table.registerColumn(NAME_COLUMN,     DbTable.IS_KEY)
_table.registerColumn(COMMENTS_COLUMN, DbTable.IS_NOT_KEY)
