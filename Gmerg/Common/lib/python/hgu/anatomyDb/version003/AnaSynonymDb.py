#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Wrapper around the ANA_SYNONYM table in a GMERG style anatomy relational
# database.
#

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord             # DB Records
from hgu.db import DbTable              # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_SYNONYM"

OID_COLUMN         = "SYN_OID"
OBJECT_OID_COLUMN  = "SYN_OBJECT_FK"
SYNONYM_COLUMN     = "SYN_SYNONYM"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# NODE IN PERSPECTIVE RECORD
# ------------------------------------------------------------------

class AnaSynonymDbRecord:
    """
    Python wrapper around DbRecord containing an ANA_SYNONYM
    database record.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing an
        ANA_SYNONYM database record.
        """
        self.__dbRecord = dbRecord;
        
        return None

    def getDbRecord(self):
        return self.__dbRecord

    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def getOidSynonymIsFor(self):
        return self.getDbRecord().getColumnValue(OBJECT_OID_COLUMN)

    def getSynonym(self):
        return self.getDbRecord().getColumnValue(SYNONYM_COLUMN)




# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator(where = None, orderBy = None):
    """
    Iterate through synonym records
    """
    return _table.Iterator(where = where, orderBy = orderBy)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def getByOidSynonymIsFor(objectOid):
    """
    Return the all synonym records for the given OID.
    """

    iter = Iterator(
        where = OBJECT_OID_COLUMN + " = " + DbAccess.formatSqlValue(objectOid),
        orderBy = "lower(" + SYNONYM_COLUMN + ")")

    syns = []

    for syn in iter:
        syns.append(syn)

    return syns



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set until AnaSynonymDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, AnaSynonymDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,        DbTable.IS_KEY)
_table.registerColumn(OBJECT_OID_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(SYNONYM_COLUMN,    DbTable.IS_NOT_KEY)

