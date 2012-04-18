#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************

Internal python structures to represent Anatomy Version.

There are multiple versions in the database, but the only thing
this code does with them is determine what the most recent version
was.  This code deals primarily with the version that is being
created by this run of the program.
"""

import AnatomyObject
import DbAccess
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Database related defs

TABLE   = "ANA_VERSION"            # RDBMS table this class stored in



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_version = None



# ------------------------------------------------------------------
# ANATOMY VERSION
# ------------------------------------------------------------------

class Version:
    """
    Defines a version.
    """

    def __init__(self, versionNumber, dateTime, comments):
        """
        Generate a version, given its timestamp
        """
        self.__number = versionNumber
        self.__dateTime = dateTime
        self.__comments = comments
        self.__anatomyObject = None
        self.__assignOid()
        self.__dbRecord = None

        return None

    def getOid(self):
        """
        Return the OID of this version.
        """
        return self.__anatomyObject.getOid()

    def getVersionNumber(self):
        """
        Return version number.
        """
        return self.__number

    def getDateTime(self):
        """
        Get date time stamp of this version.
        """
        return self.__dateTime

    def getComments(self):
        """
        Get comments associated with this version.
        """
        return self.__comments

    def isDeleted(self):
        """
        Returns True if this version is deleted.  That never happens.
        """
        return False

    def __assignOid(self):
        """
        Assign an OID to this (new) version.
        """
        self.__anatomyObject = AnatomyObject.AnatomyObject(
            self, creationDateTime = self.getDateTime())

        return self.getOid()

    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fiels are returned as a list of strings.
        """
        return [None, str(self.__number), self.getDateTime().isoformat(' '),
                self.__comments]


    def addToKnowledgeBase(self):
        """
        Inserts the specie into the knowledge
        base of all things we know about anatomy.
        """
        global _version

        if _version:
            Util.fatalError([
                "Attempt to create more than one version object.",
                "Code can't deal with that."])
        _version = self
        self.__anatomyObject.addToKnowledgeBase()

        return None


    def spew(self, label=""):
        """
        debugging routine.  Displays contents of version
        """
        print "Version:", label
        print "TimeStamp:", self.getDateTime()

        return None


    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        """
        Return the DB record for this version.
        """
        return self.__dbRecord

    def setDbInfo(self, dbRecord):
        """
        Associates a DB record with this object, and vice versa,
        and looks up the AnatomyObject for this node.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
        else:
            # we have a db record, means we also have an AnatomyObject
            Util.warning([
                "Assigning an existing db record to a version.  Code can " +
                "deal with this.",
                "However, since the code only expects to deal with the " +
                "single new version",
                "this is unexpected.  Please investigate."])
            dbRecord.bindPythonObject(self)
            oid = dbRecord.getColumnValue("ANO_OID")
            self.__anatomyObject = AnatomyObject.bindAnatomyObject(oid, self)

        self.__dbRecord = dbRecord

        return dbRecord


    def genSql(self):
        """
        Generate SQL to insert new version into database.
        """
        self.getDbRecord().insert()

        return None



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    This iterator runs only once, returning the only version it knows about.
    """

    def __init__(self):
        self.__called = False
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return the latest version and then stop.
        """
        if self.__called:
            raise StopIteration
        self.__called = True
        return _version



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise(dateTime, comments):
    """
    Reads in most recent version defined in the database, and then
    creates the next version based on the previous version.
    """

    global _version

    _version = None

    tableInfo = DbAccess.registerClassTable(Version, TABLE,
                                            DbAccess.IN_ANA_OBJECT)

    # Map instance methods to columns
    tableInfo.addColumnMethodMapping(
        "VER_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "VER_NUMBER", "getVersionNumber", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "VER_DATE", "getDateTime", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "VER_COMMENTS", "getComments", DbAccess.IS_NOT_KEY)

    prevVer    = DbAccess.readLatestVersion(Version)
    prevVerNum = prevVer.getColumnValue("VER_NUMBER")

    # Populate with defined version
    ver = Version(prevVerNum + 1, dateTime, comments)
    ver.addToKnowledgeBase()

    return None



def genSql():
    """
    Generate insert of new version.
    """
    _version.genSql()
    Util.statusMessage(["Version " + str(_version.getVersionNumber()) +
                        " added."])

    return None


def getOid():
    """
    Return the OID of the version being created.
    """
    return _version.getOid()



def getVersion():
    """
    Return the version this run of the program is producing.
    """
    version = None
    for ver in AllIter():
        if version:
            Util.fatalError([
                "More than one version returned by version iterator."
                "It should only return the version this run is producing."])
        else:
            version = ver
    return version



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above





