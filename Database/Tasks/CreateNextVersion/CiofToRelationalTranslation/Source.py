#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************


Internal python structures to represent Sources
"""

import DbAccess



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE   = "ANA_SOURCE"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_sources = None
_sourcesByName = None
_sourceAliases = None
_sourcesByOid = None


# ------------------------------------------------------------------
# SOURCE
# ------------------------------------------------------------------

class Source:
    """
    Defines a source.

    Sources are not explicitly defined as separate objects in the CIOF
    file.  The same source is referred to with many slightly different
    variations throughout the CIOF file.

    This code reads sources form the database, and then maps the
    variations in the CIOF file to the records in the database.
    """

    def __init__(self, dbRecord):
        """
        Create a source, given its database record
        """
        self.__oid     = dbRecord.getColumnValue("SRC_OID")
        self.__name    = dbRecord.getColumnValue("SRC_NAME")
        self.__authors = dbRecord.getColumnValue("SRC_AUTHORS")
        self.__format  = dbRecord.getColumnValue("SRC_FORMAT_FK")
        self.__year    = dbRecord.getColumnValue("SRC_YEAR")
        self.__dbRecord = None
        self.__setDbRecord(dbRecord)

        return None

    def getOid(self):
        """
        Get OID of the source.
        """
        return self.__oid

    def getName(self):
        """
        Get name of the source.
        """
        return self.__name

    def getAuthors(self):
        """
        Get authors of the source.
        """
        return self.__authors

    def getFormat(self):
        """
        What format is the source in?
        """
        return self.__format

    def getYear(self):
        """
        What year was the source published / sent?
        """
        return self.__year


    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.

        The fields are returned as a list of strings.
        """
        return [str(self.getOid()), self.getName(), self.getAuthors(),
                self.getFormat(), str(self.getYear())]


    def addToKnowledgeBase(self):
        """
        Inserts the source into the knowledge
        base of all things we know about anatomy.
        """
        _sources.append(self)
        _sourcesByName[self.getName()] = self
        _sourcesByOid[self.getOid()] = self

        return None


    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        """
        Return record for this source.
        """
        return self.__dbRecord


    def __setDbRecord(self, dbRecord):
        """
        Associates a DB record with this object and vice versa.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
        else:
            dbRecord.bindPythonObject(self)
        self.__dbRecord = dbRecord

        return dbRecord




# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all sources
    """

    def __init__(self):
        self.__length = len(_sources)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Get next source.
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _sources[self.__position]



# ------------------------------------------------------------------
# CLASS LEVEL METHODS
# ------------------------------------------------------------------

def initialise():
    """
    Read sources in from database.

    Sources are only used in the CIOF file in lineage attribution
    """
    global _sources, _sourcesByName, _sourceAliases, _sourcesByOid

    _sources = []                           # unindexed list
    _sourcesByName = {}                     # sources by Name
    _sourcesByOid  = {}
    _sourceAliases = {}

    tableInfo = DbAccess.registerClassTable(Source, TABLE,
                                            DbAccess.IN_ANA_OBJECT)
    tableInfo.addColumnMethodMapping(
        "SRC_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "SRC_NAME", "getName", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "SRC_AUTHORS", "getAuthors", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "SRC_FORMAT_FK", "getFormat", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "SRC_YEAR", "getYear", DbAccess.IS_NOT_KEY)

    # read all species from DB
    allSources = DbAccess.readClassAll(Source)

    for dbSource in allSources:
        source = Source(dbSource)
        source.addToKnowledgeBase()

    # Lookup sources so we can do mapping.

    grays       = _sourcesByName["Gray's Anatomy, 38th Edition"]
    kaufmanBard = _sourcesByName["The Anatomical Basis of Mouse Development"]
    kaufmanPc   = _sourcesByName["Matthew H. Kaufman personal communication"]

    # :TODO: might be able to pin down the year of Matt's personal
    # communications using creation date from annotation.

    # Map strings found in CIOF file to full names of sources.
    _sourceAliases["Gray's 38th 1995"]                 = grays
    _sourceAliases["Gray's - 38th edtn 1995"]          = grays
    _sourceAliases["Gray's 38th edtn 1995"]            = grays
    _sourceAliases["Gray's Anatomy . 38th edtn 1995"]  = grays
    _sourceAliases["Kaufman and Bard"]                 = kaufmanBard
    _sourceAliases["Kaufman (personal comm.)"]         = kaufmanPc
    _sourceAliases["Kaufman  personal communication"]  = kaufmanPc
    _sourceAliases["Kaufman (personal communication)"] = kaufmanPc
    _sourceAliases["Kaufman personal communication" ]  = kaufmanPc

    return None


def getByAlias(alias):
    """
    Given a source alias from the CIOF file, return the definition of
    the source
    """
    return _sourceAliases[alias]


def getByOid(oid):
    """
    Get a source, given its OID.
    """
    return _sourcesByOid[oid]



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above


