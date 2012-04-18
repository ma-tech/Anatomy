#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Wrapper around anatomy Versions from the relational databases.
#

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord             # DB Records
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

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing a
        Version database record.
        """
        self.__dbRecord = dbRecord;
        
        return None

    def getDbRecord(self):
        return self.__dbRecord

    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)
    
    def getNumber(self):
        return self.getDbRecord().getColumnValue(NUMBER_COLUMN)
    
    def getDate(self):
        return self.getDbRecord().getColumnValue(DATE_COLUMN)
    
    def getComments(self):
        return self.getDbRecord().getColumnValue(COMMENTS_COLUMN)

    

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
    """

    return AnaVersionDbRecord(
        DbAccess.selectOne(
            _table,
            where = (
                DATE_COLUMN + " = (select max(" + DATE_COLUMN + ") from " +
                TABLE_NAME + ")" ) ) )




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

