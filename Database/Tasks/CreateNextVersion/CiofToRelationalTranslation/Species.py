#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Internal python structures to represent Species
"""

import DbAccess


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Species names.

MOUSE      = "mouse"

TABLE   = "REF_SPECIES"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_species = None



# ------------------------------------------------------------------
# SPECIES
# ------------------------------------------------------------------

class Species:
    """
    Defines a species.

    Species are defined in the database.  They are never defined in the
    CIOF file.  They are implicit in the CIOF file.
    """

    def __init__(self, dbRecord):
        """
        Generate a species, given its data from the database
        """
        self.__name      = dbRecord.getColumnValue("RSP_NAME")
        self.__latinName = dbRecord.getColumnValue("RSP_LATIN_NAME")
        self.__timedNodeIdPrefix = dbRecord.getColumnValue(
                                               "RSP_TIMED_NODE_ID_PREFIX")
        self.__nodeIdPrefix = dbRecord.getColumnValue("RSP_NODE_ID_PREFIX")
        self.__dbRecord = None
        self.__setDbRecord(dbRecord)

        return None


    def getName(self):
        """
        Get common name of species.
        """
        return self.__name

    def getLatinName(self):
        """
        Get latin name of species.
        """
        return self.__latinName

    def getTimedNodeIdPrefix(self):
        """
        Return the prefix for all timed nodes for this species.
        """
        return self.__timedNodeIdPrefix

    def getNodeIdPrefix(self):
        """
        Return the prefix for all abstract nodes for this species.
        """
        return self.__nodeIdPrefix


    # Deleted genDumpFields in revision 1.4.  No longer used and didn't want
    # to maintain it.

    def addToKnowledgeBase(self):
        """
        Inserts the species into the knowledge
        base of all things we know about anatomy.
        """
        _species.append(self)

        return None


    def spew(self, label=""):
        """
        debugging routine.  Displays contents of species
        """
        print "Species:", label
        print "  Name:", self.getName()
        print "  Latin Name:", self.getLatinName()
        print "  Timed Node Public ID Prefix:", self.getTimedNodeIdPrefix()
        print "  Node Public ID Prefix:", self.getNodeIdPrefix()

        return None

    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        """
        Return DB record for this species.
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
    Iterate through all species
    """

    def __init__(self):
        self.__length = len(_species)
        self.__position = -1         # Most recent species returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return next species
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _species[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Initialise this module.
    """

    global _species

    # You know, we only have one species.  However, to make this ref table
    # behave like the others, we need to define iterators, and things for
    # the iterators to access.
    _species = []              # unindexed


    tableInfo = DbAccess.registerClassTable(Species, TABLE,
                                            DbAccess.NOT_IN_ANA_OBJECT)

    tableInfo.addColumnMethodMapping(
        "RSP_NAME", "getName", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "RSP_LATIN_NAME", "getLatinName", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "RSP_TIMED_NODE_ID_PREFIX", "getTimedNodeIdPrefix", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "RSP_NODE_ID_PREFIX", "getNodeIdPrefix", DbAccess.IS_NOT_KEY)

    # read all species from DB
    allSpecies = DbAccess.readClassAll(Species)

    for dbSpecies in allSpecies:
        species = Species(dbSpecies)
        species.addToKnowledgeBase()

    return None




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above

