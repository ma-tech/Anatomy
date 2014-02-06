#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around ISH_EXPRESSION records from the ISH relational databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord                         # DB Records
from hgu.db import DbTable                          # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME        = "ISH_EXPRESSION"

OID_COLUMN                 = "EXP_OID"
SUBMISSION_OID_COLUMN      = "EXP_SUBMISSION_FK"
STRENGTH_COLUMN            = "EXP_STRENGTH"
ADDITIONAL_STRENGTH_COLUMN = "EXP_ADDITIONAL_STRENGTH"
COMPONENT_PUBLIC_ID_COLUMN = "EXP_COMPONENT_ID"



# ------------------------------------------------------------------
# GLOBALS - initialised when module is loaded
# ------------------------------------------------------------------

_table = None




# ------------------------------------------------------------------
# ISH EXPRESSION DB RECORD
# ------------------------------------------------------------------

class IshExpressionDbRecord:
    """
    Python wrapper around DbRecord containing a ISH_EXPRESSION record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ISH_EXPRESSION database record.

        If dbRecord is None, create an empty record.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None

    def __getDbRecord(self):
        """
        Return the ISH_EXPRESSION DB record.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the OID of the ISH_EXPRESSION record.  This is unique within
        the table, but not across the GMERG database.
        """
        return self.__getDbRecord().getColumnValue(OID_COLUMN)

    def getSubmissionOid(self):
        """
        Return the OID of the ISH_SUBMISSION record this expression result
        is associated with.
        """
        return self.__getDbRecord().getColumnValue(SUBMISSION_OID_COLUMN)

    def getStrength(self):
        """
        Returns a high level indicator of strength.  Either:
        not detected, present, or possible.
        """
        return self.__getDbRecord().getColumnValue(STRENGTH_COLUMN)

    def getAdditionalStrength(self):
        """
        Returns a finer grained indicator of strength for detected
        annotations:  strong, moderate, weak.
        """
        return self.__getDbRecord().getColumnValue(ADDITIONAL_STRENGTH_COLUMN)

    def getComponentPublicId(self):
        """
        Return the public id (EMAP number) of the timed component that was
        annotated.
        """
        return self.__getDbRecord().getColumnValue(COMPONENT_PUBLIC_ID_COLUMN)


    def setOid(self, oid):
        """
        Set the OID column.  This is unique within the ISH_EXPRESSION table,
        but is not unique across tables in the GMERG tables.
        """
        return self.__getDbRecord().setColumnValue(OID_COLUMN, oid)

    def setSubmissionOid(self, submissionOid):
        """
        Set the submission that this expression record is for.
        """
        return self.__getDbRecord().setColumnValue(SUBMISSION_OID_COLUMN,
                                                   submissionOid)

    def setStrength(self, strength):
        """
        Set a high level indicator of whether expression was detected or not.
        """
        return self.__getDbRecord().setColumnValue(STRENGTH_COLUMN, strength)

    def setAdditionalStrength(self, additionalStrength):
        """
        Set a finer-grained strength level for detected annotations.
        """
        return self.__getDbRecord().setColumnValue(ADDITIONAL_STRENGTH_COLUMN,
                                                   additionalStrength)

    def setComponentPublicId(self, publicId):
        """
        Set the anatomical timed component in which the expression was
        detected or not.
        Specify the timed component using its public ID (its EMAP ID).
        """
        return self.__getDbRecord().setColumnValue(COMPONENT_PUBLIC_ID_COLUMN,
                                                   publicId)

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
    Iterate through all expression records, in no particular order
    """
    return _table.Iterator()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByOid(exprOid):
    """
    Return the ISH_EXPRESSION DB record with the given OID.
    """

    return IshExpressionDbRecord(DbAccess.selectOne(
        _table, where = OID_COLUMN + " = " + DbAccess.formatSqlValue(exprOid)))


def getMaxOid():
    """
    Return the current maximum OID in the table.
    """

    return DbAccess.selectMaxOid(_table)


def getBySubmissionOid(submissionOid):
    """
    Return list of expression records with the given submission OID, in
    no particulary order

    Returns an empty list if submission does not have any expression
    records.
    """
    expSubList = []
    for expRec in _table.Iterator(
                  where = (SUBMISSION_OID_COLUMN + " = " +
                           DbAccess.formatSqlValue(submissionOid))):
        expSubList.append(expRec)

    return expSubList



def getBySubmissionOidTimedNodePublicId(submissionOid, timedNodePublicId):
    """
    Return the expression record with the given submission OID and
    timed node public ID.

    Return None if record does not exist.
    """
    dbRec = DbAccess.selectOne(
        _table,
        where = (
            SUBMISSION_OID_COLUMN + " = " +
                DbAccess.formatSqlValue(submissionOid) +
            " and " +
            COMPONENT_PUBLIC_ID_COLUMN + " = " +
                DbAccess.formatSqlValue(timedNodePublicId)))

    if dbRec != None:
        expRec = IshExpressionDbRecord(dbRec)
    else:
        expRec = None

    return expRec



def deleteAllBySubmissionOid(submissionOid):
    """
    Delete all expression records for the given submission OID.
    """
    # :TODO: Add delete support to DbAccess and/or DbTable so all caller has
    #        to provide is the where clause.
    delete = (
        "delete from " + TABLE_NAME +
        "  where " + SUBMISSION_OID_COLUMN + " = " +
        DbAccess.formatSqlValue(submissionOid) + ";")

    DbAccess.writeSql(delete)

    return



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't set these until IshExpressionDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, IshExpressionDbRecord)

_table.registerColumn(OID_COLUMN, DbTable.IS_KEY)

_table.registerColumn(SUBMISSION_OID_COLUMN,      DbTable.IS_NOT_KEY)
_table.registerColumn(STRENGTH_COLUMN,            DbTable.IS_NOT_KEY)
_table.registerColumn(ADDITIONAL_STRENGTH_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(COMPONENT_PUBLIC_ID_COLUMN, DbTable.IS_NOT_KEY)


