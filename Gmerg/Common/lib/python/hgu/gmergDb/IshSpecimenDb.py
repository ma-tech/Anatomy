#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Wrapper around the ISH_SPECIMEN table in a GMERG style ISH relational
# database.
#

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord                         # DB Records
from hgu.db import DbTable                          # DB Tables



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ISH_SPECIMEN"

OID_COLUMN          = "SPN_OID"
SUBMISSION_OID_COLUMN = "SPN_SUBMISSION_FK"
STAGE_FORMAT_COLUMN = "SPN_STAGE_FORMAT"
GIVEN_STAGE_COLUMN  = "SPN_STAGE"
_COLUMN = "SPN_"
_COLUMN = "SPN_"
_COLUMN = "SPN_"
_COLUMN = "SPN_"
_COLUMN = "SPN_"
_COLUMN = "SPN_"
_COLUMN = "SPN_"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# SPECIMEN DB RECORD
# ------------------------------------------------------------------

class IshSpecimenDbRecord:
    """
    Python wrapper around DbRecord containing an ISH_SPECIMEN database record.

    NOTE: This is not a complete wrapper.  It only provides access to the data
    that we have needed so far.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing an ISH_SPECIMEN
        database record.
        """
        self.__dbRecord = dbRecord;
        return None


    def getDbRecord(self):
        return self.__dbRecord


    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    
    def getSubmissionOid(self):
        """
        local ID = public ID = "ERGxxx", note the missing ":".
        """
        return self.getDbRecord().getColumnValue(SUBMISSION_OID_COLUMN)

    
    def getGivenStageFormat(self):
        """
        Return the type of stage notation used with this specimen.
        """
        return self.getDbRecord().getColumnValue(STAGE_FORMAT_COLUMN)


    def getGivenStageValue(self):
        """
        Return the particular stage that was provided with the sample.
        This stage will be in the format specified by getStageFormat().
        """
        return self.getDbRecord().getColumnValue(GIVEN_STAGE_COLUMN)



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByOid(oid):
    """
    Returns the specimen record with the given OID, or None, if
    record does not exit.  Throws exception if more than one record exists.
    """

    sqlOid = DbAccess.formatSqlValue(oid)
    return IshSpecimenDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))



def getBySubmissionOid(subOid):
    """
    Returns the specimen record with the given submission OID, or None, if
    record does not exit.  Throws exception if more than one record exists.
    """

    sqlOid = DbAccess.formatSqlValue(subOid)
    return IshSpecimenDbRecord(DbAccess.selectOne(
        _table, SUBMISSION_OID_COLUMN + " = " + sqlOid))



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until IshSpecimenDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, IshSpecimenDbRecord,
                               DbTable.NOT_IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,            DbTable.IS_KEY)
_table.registerColumn(SUBMISSION_OID_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(STAGE_FORMAT_COLUMN,   DbTable.IS_NOT_KEY)
_table.registerColumn(GIVEN_STAGE_COLUMN,    DbTable.IS_NOT_KEY)

