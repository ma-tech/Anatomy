#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Internal python structures to represent anatomy relationship types.
# There is no list of types explicitly listed in the CIOF file.

import DbAccess
import Util                             # Error handling



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Relationship Types, defined from child to parent

PART_OF      = "part-of"
DERIVES_FROM = "derives-from"

# DB 

TABLE   = "ANA_RELATIONSHIP_TYPE"


# ------------------------------------------------------------------
# RELATIONSHIP TYPE
# ------------------------------------------------------------------

class RelationshipType:
    """
    Defines a binary, directional (parent-child) relationship type.

    Relationship types are defined in the database.  They are implicit
    in the CIOF file.
    """

    def __init__(self, dbRecord):
        """
        Create an anatomy relationship type given a relationship type
        record from the database.
        """
        self.__name = dbRecord.getColumnValue("RTY_NAME")
        self.__childToParentDisplay = dbRecord.getColumnValue("RTY_CHILD_TO_PARENT_DISPLAY")
        self.__parentToChildDisplay = dbRecord.getColumnValue("RTY_PARENT_TO_CHILD_DISPLAY")
        self.__setDbRecord(dbRecord)
        
        return None


    def getName(self):
        return self.__name

    def getChildToParentDisplay(self):
        return self.__childToParentDisplay

    def getParentToChildDisplay(self):
        return self.__parentToChildDisplay

    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.

        The fields are returned as a list of strings.
        """
        return [self.getName(), self.getChildToParentDisplay(),
                self.getParentToChildDisplay()]


    def addToKnowledgeBase(self):
        """
        Inserts the anatomy relationship type into the knowledge
        base of all things we know about anatomy.
        """
        relTypeName = self.getName()
        if relTypeName in _relationshipTypesByName:
            Util.fatalError([relTypeName + " alaready defined"])
        _relationshipTypesByName[relTypeName] = self
        _relationshipTypes.append(self)

        return None


    def spew(self, label=""):
        """
        debugging routine.  Displays contents of relationship type
        """
        print "RelationshipType:", label
        print "Name:", self.getName()
        print "Child to Parent:" + self.getChildToParentDisplay()
        print "Parent to child:" + self.getParentToChildDisplay()

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
# RELATIONSHIP TYPE ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all anatomy relationship types
    """

    def __init__(self):
        self.__length = len(_relationshipTypes)
        self.__position = -1         # Most recent returned
        return None

    def __iter__(self):
        return self

    def next(self):
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _relationshipTypes[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():

    global _relationshipTypesByName, _relationshipTypes

    _relationshipTypesByName = {}
    _relationshipTypes = []              # unindexed

    tableInfo = DbAccess.registerClassTable(RelationshipType, TABLE,
                                            DbAccess.NOT_IN_ANA_OBJECT)

    # Map instance methods to columns
    tableInfo.addColumnMethodMapping(
        "RTY_NAME", "getName", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "RTY_CHILD_TO_PARENT_DISPLAY", "getChildToParentDisplay",
        DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "RTY_PARENT_TO_CHILD_DISPLAY", "getParentToChildDisplay",
        DbAccess.IS_NOT_KEY)

    # read all types from DB
    allRelTypes = DbAccess.readClassAll(RelationshipType)

    for dbRelType in allRelTypes:
        relType = RelationshipType(dbRelType)
        relType.addToKnowledgeBase()

    return None



def getByName(relTypeName):
    """
    return relationship type with this name.
    """
    global _relationshipTypesByName
    if relTypeName in _relationshipTypesByName:
        return _relationshipTypesByName[relTypeName]
    else:
        return []



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above


