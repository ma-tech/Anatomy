#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ISH_SUBMISSION table in a GMERG style ISH relational
databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbTable                          # DB Tables



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ISH_SUBMISSION"

OID_COLUMN          = "SUB_OID"
LOCAL_ID_COLUMN     = "SUB_LOCAL_ID"
ASSAY_TYPE_COLUMN   = "SUB_ASSAY_TYPE"
EMBRYO_STAGE_COLUMN = "SUB_EMBRYO_STG"
ACCESSION_ID_COLUMN = "SUB_ACCESSION_ID"
PUBLIC_FLAG_COLUMN  = "SUB_IS_PUBLIC"
DB_STATUS_FK_COLUMN = "SUB_DB_STATUS_FK"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# ISH SUBMISSION DB RECORD
# ------------------------------------------------------------------

class IshSubmissionDbRecord:
    """
    Python wrapper around DbRecord containing an ISH_SUBMISSION database record.

    NOTE: This is not a complete wrapper.  It only provides access to the data
    that we have needed so far.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing an ISH_SUBMISSION
        database record.
        """
        self.__dbRecord = dbRecord
        return None


    def getDbRecord(self):
        """
        Return the ISH_SUBMISSION record for this object.
        """
        return self.__dbRecord


    def getOid(self):
        """
        Return the OID of this submission.
        """
        #return self.getDbRecord().getColumnValue(OID_COLUMN)
        if self.getDbRecord() == None:
            return None
        else:
            return self.getDbRecord().getColumnValue(OID_COLUMN)


    def getAccessionId(self):
        """
        Return the accession (public) ID of this submission.
        """
        return self.getDbRecord().getColumnValue(ACCESSION_ID_COLUMN)


    def getLocalId(self):
        """
        local ID = public ID = "ERGxxx", note the missing ":".
        """
        return self.getDbRecord().getColumnValue(LOCAL_ID_COLUMN)


    def getAssayType(self):
        """
        Return the type of assay, e.g., ISH, OPT, ...
        """
        return self.getDbRecord().getColumnValue(ASSAY_TYPE_COLUMN)


    def getStageName(self, prefix = "TS"):
        """
        Return the stage name.  The name is stored without the leading prefix.

        If prefix is not provided then TS will be used as defualt.
        """
        # Stored without leading prefix.
        return prefix + self.getDbRecord().getColumnValue(EMBRYO_STAGE_COLUMN)

    def getPublicFlag(self):
        """
        Return the public flag for this assay, e.g., 1 (Private) or 0 (Public)
        """
        return self.getDbRecord().getColumnValue(PUBLIC_FLAG_COLUMN)


    def setPublicFlag(self, value):
        """
        Set the public flag (true / false) for this record.
        """
        return self.getDbRecord().setColumnValue(PUBLIC_FLAG_COLUMN, value)


    def getDbStatusFk(self):
        """
        Return the public flag for this assay, e.g., 1 (Private) or 0 (Public)
        """
        return self.getDbRecord().getColumnValue(DB_STATUS_FK_COLUMN)


    def setDbStatusFk(self, value):
        """
        Set the public flag (true / false) for this record.
        """
        return self.getDbRecord().setColumnValue(DB_STATUS_FK_COLUMN, value)

    
    def update(self):
        """
        Generate update statement to update this record in the database.
        """
        self.getDbRecord().update()




# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------


def UnannotatedSubmissionsIterator():
    """
    Iterate through all submission records that don't have annotation.
    """

    # Not sure how to define "submission that needs to be annotated".
    # Start with:
    #   "Any submission without annotation needs to be annotated."
    where = (
        """
        not exists (
              select 'x'
                from ISH_EXPRESSION
                where EXP_SUBMISSION_FK = """ + OID_COLUMN + """)
        """)

    # :TODO: Move this sort of query into ExpressionDb object and
    # call it something like
    #
    #  ExpressionDb.getExistsForSubmissionSubquery()
    #
    # and then put a "not" in front of it (or write
    # DbAccess.notCondition(condition)

    return _table.Iterator(where = where)


def Iterator():
    """
    Iterate through all submissions.

    This makes a DB cursor appear as a Python iterator.
    """
    return _table.Iterator()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByLocalId(localId):
    """
    Returns the submission record with the given local ID, or None, if
    record does not exit.  Throws exception if more than one record exists.

    localId: The ID the submission had back when it was a submission in
             an EMAGE style local database.  (That is, when it was an XML
             file in a collection of XML files.)
    """

    sqlLocalId = DbAccess.formatSqlValue(localId)
    return IshSubmissionDbRecord(DbAccess.selectOne(
        _table, LOCAL_ID_COLUMN + " = " + sqlLocalId))


def getByAccessionId(accessionId):
    """
    Returns the submission record with the given accession (public) ID, or None, if
    record does not exit.  Throws exception if more than one record exists.

    accessionId: The public ID of the submission, e.g., ERG:123.
    """

    sqlAccessionId = DbAccess.formatSqlValue(accessionId)
    
    if IshSubmissionDbRecord(DbAccess.selectOne(_table, ACCESSION_ID_COLUMN + " = " + sqlAccessionId)) == None:
        print "NONE"
        
    return IshSubmissionDbRecord(DbAccess.selectOne(
        _table, ACCESSION_ID_COLUMN + " = " + sqlAccessionId))



def getByOid(oid):
    """
    Returns the submission record with the given OID, or None, if
    record does not exit.  Throws exception if more than one record exists.
    """

    sqlOid = DbAccess.formatSqlValue(oid)
    return IshSubmissionDbRecord(DbAccess.selectOne(
        _table, OID_COLUMN + " = " + sqlOid))




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until IshSubmissionDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, IshSubmissionDbRecord)

_table.registerColumn(OID_COLUMN,          DbTable.IS_KEY)
_table.registerColumn(LOCAL_ID_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(ASSAY_TYPE_COLUMN,   DbTable.IS_NOT_KEY)
_table.registerColumn(EMBRYO_STAGE_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(ACCESSION_ID_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(PUBLIC_FLAG_COLUMN,  DbTable.IS_NOT_KEY)
_table.registerColumn(DB_STATUS_FK_COLUMN,  DbTable.IS_NOT_KEY)
