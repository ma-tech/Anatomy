#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Wrapper around anatomy Versions from the relational databases.
"""

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord
from hgu.db import DbTable              # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_VERSION"

OID_COLUMN      = "VER_OID"
NUMBER_COLUMN   = "VER_NUMBER"
DATE_COLUMN     = "VER_DATE"
COMMENTS_COLUMN = "VER_COMMENTS"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None





# ------------------------------------------------------------------
# VERSION DB RECORD
# ------------------------------------------------------------------

class AnaVersionDbRecord:
    """
    Python wrapper around DbRecord containing a Version database record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing a
        Version database record.

        If dbRecord is None, create a record with None in all columns.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None

    def getDbRecord(self):
        """
        Return the DbRecord for this version.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the database OID for this version.  This OID is a foreign key
        to ANA_OBJECT and is unique across the DB.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def setOid(self, oid):
        """
        Set the version's OID.  This OID is a foreign key to ANA_OBJECT.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, oid)


    def getNumber(self):
        """
        Return this version's number.
        """
        return self.getDbRecord().getColumnValue(NUMBER_COLUMN)

    def setNumber(self, number):
        """
        Set the version number for the version.
        """
        return self.getDbRecord().setColumnValue(NUMBER_COLUMN, number)


    def getDate(self):
        """
        Return the date of this version.  This is the dump date of the CIOF
        or OBO file from which this version was created.
        """
        return self.getDbRecord().getColumnValue(DATE_COLUMN)

    def setDate(self, date):
        """
        Set the date of this version.  This is meant to be the date the
        CIOF or OBO file was created, not the date that it was ported to
        an anatomy database.
        """
        return self.getDbRecord().setColumnValue(DATE_COLUMN, date)


    def getComments(self):
        """
        Return a description of the changes in this version.
        """
        return self.getDbRecord().getColumnValue(COMMENTS_COLUMN)

    def setComments(self, comments):
        """
        Set the description of this version.
        """
        return self.getDbRecord().setColumnValue(COMMENTS_COLUMN, comments)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------


def Iterator():
    """
    Iterate through all versions, in no particular order
    """
    return _table.Iterator()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def getByOid(versionOid):
    """
    Return the VERSION DB record with the given OID.
    """

    return AnaVersionDbRecord(DbAccess.selectOne(
        _table, where = OID_COLUMN + " = " + str(versionOid)))


def getLatestVersion():
    """
    Return the newest VERSION DB record.

    Returns None if there are no VERSION records.
    """
    baseRec = (
        DbAccess.selectOne(
            _table,
            where = (
                DATE_COLUMN + " = (select max(" + DATE_COLUMN + ") from " +
                TABLE_NAME + ")" ) ) )

    if baseRec == None:
        verRec = None
    else:
        verRec = AnaVersionDbRecord(baseRec)

    return verRec


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't be set until after AnaVersionDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, AnaVersionDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,      DbTable.IS_KEY)
_table.registerColumn(NUMBER_COLUMN,   DbTable.IS_NOT_KEY)
_table.registerColumn(DATE_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(COMMENTS_COLUMN, DbTable.IS_NOT_KEY)

