#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Internal python structures to represent Editors

import AnatomyObject                    # Ties it all together
import DbAccess
import Util                             # Error handling



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# DB defs.

TABLE = "ANA_EDITOR"


# ------------------------------------------------------------------
# EDITOR
# ------------------------------------------------------------------

class Editor:
    """
    Defines a editor.

    Editors are defined in the database.  They are referenced, but not
    defeined inn the CIOF file.
    """

    def __init__(self, dbRecord):
        """
        Generate a editor, given an editor record from the database.
        """
        self.__oid  = dbRecord.getColumnValue("EDI_OID")
        self.__name = dbRecord.getColumnValue("EDI_NAME")
        self.__setDbRecord(dbRecord)

        return None

    def getName(self):
        return self.__name

    def getOid(self):
        return self.__oid


    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fiels are returned as a list of strings.
        """
        return [str(self.getOid()), self.getName()]


    def addToKnowledgeBase(self):
        """
        Inserts the editor into the knowledge
        base of all things we know about anatomy.
        """
        _editors.append(self)
        _editorsByName[self.getName()] = self
        _editorsByOid[self.getOid()]   = self

        return None


    def spew(self, label=""):
        """
        debugging routine.  Displays contents of editor
        """
        print "Editor:", label
        print "Name:", self.getName()
        print "OID:", self.getOid()

        return None


    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
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
    Iterate through all editors
    """

    def __init__(self):
        self.__length = len(_editors)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _editors[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all editors in from database.
    """
    global _editors, _editorsByName, _editorsByOid

    _editors = []                           # unindexed list
    _editorsByName = {}                     # editors by Name
    _editorsByOid = {}

    tableInfo = DbAccess.registerClassTable(Editor, TABLE,
                                            DbAccess.NOT_IN_ANA_OBJECT)
    tableInfo.addColumnMethodMapping(
        "EDI_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "EDI_NAME", "getName", DbAccess.IS_NOT_KEY)

    # read all editors from DB
    allEds = DbAccess.readClassAll(Editor)

    for dbEd in allEds:
        editor = Editor(dbEd)
        editor.addToKnowledgeBase()

    return None


def getByName(name):
    
    return _editorsByName[name]


def getByOid(oid):
    
    return _editorsByOid[oid]



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above.
