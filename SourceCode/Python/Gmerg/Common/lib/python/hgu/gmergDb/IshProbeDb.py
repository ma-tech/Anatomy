#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ISH_PROBE table in a GMERG style ISH relational
database.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ISH_PROBE"

OID_COLUMN            = "PRB_OID"
SUBMISSION_OID_COLUMN = "PRB_SUBMISSION_FK"
PROBE_NAME_COLUMN     = "PRB_PROBE_NAME"
GENE_SYMBOL_COLUMN    = "PRB_GENE_SYMBOL"
CLONE_NAME_COLUMN     = "PRB_CLONE_NAME"
GENE_TYPE_COLUMN      = "PRB_GENE_TYPE" # really a sense/anti flag
GENE_ID_COLUMN        = "PRB_GENE_ID"

# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# PROBE DB RECORD
# ------------------------------------------------------------------

class IshProbeDbRecord:
    """
    Python wrapper around DbRecord containing an ISH__PROBE database record.

    NOTE: This is not a complete wrapper.  It only provides access to the data
    that we have needed so far.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing an ISH_PROBE
        database record.
        """
        self.__dbRecord = dbRecord
        return None


    def getDbRecord(self):
        """
        Return the DB record for this object.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of this probe record.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)


    def getSubmissionOid(self):
        """
        Return the OID of the submission this probe record is associated with.
        """
        return self.getDbRecord().getColumnValue(SUBMISSION_OID_COLUMN)


    def getProbeName(self):
        """
        Return the name of the probe.
        """
        return self.getDbRecord().getColumnValue(PROBE_NAME_COLUMN)


    def getGeneSymbol(self):
        """
        Return the gene symbol we believe this probe is for.
        """
        return self.getDbRecord().getColumnValue(GENE_SYMBOL_COLUMN)

    def getGeneId(self):
        """
        :TODO: Find out what's in this column.
        """
        return self.getDbRecord().getColumnValue(GENE_ID_COLUMN)


    def getCloneName(self):
        """
        Get name of clone for probe.
        """
        return self.getDbRecord().getColumnValue("PRB_CLONE_NAME")


    def getSense(self):
        """
        Who knew it was this easy!
        """
        return self.getDbRecord().getColumnValue("PRB_GENE_TYPE")



# ------------------------------------------------------------------
# ITERATORS / CURSORS
# ------------------------------------------------------------------

def GeneSymbolIterator(geneSymbol):
    """
    Iterate through all records with the given gene symbol

    This makes a DB cursor appear as a Python iterator.
    """
    where = GENE_SYMBOL_COLUMN + " = " + DbAccess.formatSqlValue(geneSymbol)
    return _table.Iterator(where = where)



def ProbeNameIterator(probeName):
    """
    Iterate through all records with the given probe name

    This makes a DB cursor appear as a Python iterator.
    """
    where = PROBE_NAME_COLUMN + " = " + DbAccess.formatSqlValue(probeName)
    return _table.Iterator(where = where)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByOid(oid):
    """
    Returns the probe record with the given OID, or None, if
    record does not exit.  Throws exception if more than one record exists.
    """

    sqlOid = DbAccess.formatSqlValue(oid)
    return IshProbeDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))



def getBySubmissionOid(subOid):
    """
    Returns the probe record with the given submission OID, or None, if
    record does not exit.  Throws exception if more than one record exists.
    """

    sqlOid = DbAccess.formatSqlValue(subOid)
    return IshProbeDbRecord(DbAccess.selectOne(
        _table, SUBMISSION_OID_COLUMN + " = " + sqlOid))




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals.  Can't be initialised until IshProbeDbRecord has been
# defined.

_table = DbTable.registerTable(TABLE_NAME, IshProbeDbRecord)

_table.registerColumn(OID_COLUMN,            DbTable.IS_KEY)
_table.registerColumn(SUBMISSION_OID_COLUMN, DbTable.IS_KEY)
_table.registerColumn(PROBE_NAME_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(GENE_SYMBOL_COLUMN,    DbTable.IS_NOT_KEY)
_table.registerColumn(GENE_ID_COLUMN,        DbTable.IS_NOT_KEY)
_table.registerColumn(CLONE_NAME_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(GENE_TYPE_COLUMN,      DbTable.IS_NOT_KEY)

