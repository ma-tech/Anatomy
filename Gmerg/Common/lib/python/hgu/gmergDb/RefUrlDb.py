#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ref url data from the relational databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


TABLE_NAME = "REF_URL"

OID_COLUMN        = "URL_OID"
URL_COLUMN        = "URL_URL"
SUFFIX_COLUMN     = "URL_SUFFIX"
INSTITUTE_COLUMN  = "URL_INSTITUTE"
SHORT_NAME_COLUMN = "URL_SHORT_NAME"
TYPE_COLUMN       = "URL_TYPE"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_table = None



# ------------------------------------------------------------------
# REF URL DB RECORD
# ------------------------------------------------------------------

class RefUrlDbRecord:
    """
    Python wrapper around DbRecord containing a Ref Url database record.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing a
        Ref Url database record.
        """
        self.__dbRecord = dbRecord

        return None

    def getDbRecord(self):
        """
        Return the DB Record for this REF URL.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the OID of this REF URL record.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def getUrl(self):
        """
        Return the base URL.
        """
        return self.getDbRecord().getColumnValue(URL_COLUMN)

    def getSuffix(self):
        """
        Get suffix to append to URLs for this site.  These are usually
        appended after an accession number.
        """
        return self.getDbRecord().getColumnValue(SUFFIX_COLUMN)

    def getInstitute(self):
        """
        A long name for who gave us this data.

        Paul Simon once sang
          I was walking down the street
          When I thought I heard this voice say
          Say, ain't we walking down the same street together
          On the very same day
          I said hey Senorita that's astute
          I said why don't we get together
          And call ourselves an institute
        """
        return self.getDbRecord().getColumnValue(INSTITUTE_COLUMN)

    def getShortName(self):
        """
        Short name for the institute.
        """
        return self.getDbRecord().getColumnValue(SHORT_NAME_COLUMN)

    def getType(self):
        """
        This might be a unique text name for the URL, or not.
        """
        return self.getDbRecord().getColumnValue(TYPE_COLUMN)




# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByInstituteType(instituteName, urlType):
    """
    Return ref url record for institute with type.
    """
    where = (
        "    " + INSTITUTE_COLUMN + " = " +
        DbAccess.formatSqlValue(instituteName) + " " +
        "and " + TYPE_COLUMN + " = " + DbAccess.formatSqlValue(urlType))

    dbRecord = DbAccess.selectOne(_table, where = where)
    if dbRecord:
        return RefUrlDbRecord(dbRecord)
    else:
        return None


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

_table = DbTable.registerTable(TABLE_NAME, RefUrlDbRecord)

_table.registerColumn(OID_COLUMN,        DbTable.IS_KEY)
_table.registerColumn(URL_COLUMN,        DbTable.IS_NOT_KEY)
_table.registerColumn(SUFFIX_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(INSTITUTE_COLUMN,  DbTable.IS_NOT_KEY)
_table.registerColumn(SHORT_NAME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(TYPE_COLUMN,       DbTable.IS_NOT_KEY)
