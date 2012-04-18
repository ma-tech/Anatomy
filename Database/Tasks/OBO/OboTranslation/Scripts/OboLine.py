#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************

OboStreams are made up of OboEntries and they are made up of OboLines.
OboLines have a common format.

"""

import sets

from hgu import Util


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# List of different line types / tags

# Header lines / tags
FORMAT_VERSION = "format-version"
DATE = "date"
SAVED_BY = "saved-by"
AUTO_GENERATED_BY = "auto-generated-by"
SUBSETDEF = "subsetdef"
DEFAULT_NAMESPACE = "default-namespace"

HEADER_TAGS = sets.Set([
    FORMAT_VERSION,
    DATE,
    SAVED_BY,
    AUTO_GENERATED_BY,
    SUBSETDEF,
    DEFAULT_NAMESPACE
    ])

# Entry lines / tags
ID              = "id"
NAME            = "name"
RELATIONSHIP    = "relationship"
RELATED_SYNONYM = "related_synonym"
IS_A            = "is_a"
SUBSET          = "subset"
DEF             = "def"
NAMESPACE       = "namespace"
IS_OBSOLETE     = "is_obsolete"

ENTRY_TAGS = sets.Set([
    ID,
    NAME,
    RELATIONSHIP,
    RELATED_SYNONYM,
    IS_A,
    SUBSET,
    DEF,
    NAMESPACE,
    IS_OBSOLETE
    ])


# Dictionary to say what tags result in what classes.  This is
# populated by main, because it can't be done until all the classes
# have been defined.

CLASS_FOR_TAG = None



# OBO line separators

TAG_END = ":"
COMMENT_START = "!"
DBXREF_START = "["
DBXREF_STOP = "]"


# ------------------------------------------------------------------
# OBO LINE
# ------------------------------------------------------------------

class Line:
    """
    Lines are the building blocks from which OboEntries are built.
    """

    def __init__(self, textLine=None):
        """
        Initialise an OboLine.
        """
        if textLine != None and textLine[-1] == "\n":
            self.__textLine = textLine[0:-1]
        else:
            self.__textLine = textLine
        self.__tag = None
        self.__value = None
        self.__dbxref = None
        self.__comment = None

        if self.__textLine != None:

            # :TODO: Change this to use regular expressions.
            # Parse the basic parts of a line, start with tag
            tagEndCol = self.__textLine.find(TAG_END)
            if tagEndCol == -1:
                Util.fatalError([
                    "OBO line does not contain a valid tag separator '" +
                    TAG_END + "'",
                    self.__textLine])
            self.__tag = self.__textLine[:tagEndCol].strip()

            # Now get optional comment.
            commStartCol = self.__textLine.find(COMMENT_START)
            if commStartCol >= 0:
                self.__comment = self.__textLine[commStartCol+1:].strip()
            else:
                commStartCol = len(self.__textLine)

            # Get optional DBXREF
            dbxrefStartCol = self.__textLine.find(DBXREF_START)
            if dbxrefStartCol >= 0:
                dbxrefStopCol = self.__textLine.find(DBXREF_STOP)
                self.__dbxref = self.__textLine[dbxrefStartCol+1:dbxrefStopCol]
            else:
                dbxrefStartCol = commStartCol

            # Get the required value.
            self.__value = self.__textLine[tagEndCol+1:dbxrefStartCol].strip()

        return


    def spew(self, label=""):
        """
        Debugging routine to spew the contents of a line.
        """
        print "OboLine:", label
        print "     Text:", self.getText()
        print "      Tag:", self.getTag()
        print "    Value:", self.getValue()
        if self.getDbxref():
            print "   dbxref:", self.getDbxref()
        if self.getComment():
            print "  comment:", self.getComment()

        return


    def getText(self):
        """
        Return the text of the line.
        """
        return self.__textLine

    def getTag(self):
        """
        Get the tag for the line.
        """
        return self.__tag

    def getValue(self):
        """
        Get the line's value.
        """
        return self.__value


    def getDbxref(self):
        """
        Get the DBXREF clause for this line.  May be None.
        """
        return self.__dbxref


    def getComment(self):
        """
        Get the comment for this line.  May be None.
        """
        return self.__comment


# ------------------------------------------------------------------
# LINE SUBCLASSES
# ------------------------------------------------------------------
#
# Certain types of lines have additional information.  Define subclasses
# for those line types.



# ------------------------------------------------------------------
# SUBSETDEF LINE
# ------------------------------------------------------------------

class SubsetdefLine (Line):
    """
    These occur in the OBO file header.
    """

    def __init__(self, textLine=None):
        """
        Initialise a subset definition line.
        """
        Line.__init__(self, textLine)

        # The value further subdivides.
        value = self.getValue()
        sep = value.index(" ")

        self.__id = value[0:sep]
        self.__name = value[sep+1:]

        return


    def getId(self):
        """
        Return the identifier of the subset.
        """
        return self.__id

    def getName(self):
        """
        Return the name of the subset.
        """
        return self.__name


# ------------------------------------------------------------------
# RELATIONSHIP LINE
# ------------------------------------------------------------------

class RelationshipLine (Line):
    """
    These occur in OBO entries.  They have the form:

      relationship: starts_at XAO:1000045 ! NF stage 29 and 30
    """

    def __init__(self, textLine=None):
        """
        Initialise a relationship line.
        """
        Line.__init__(self, textLine)

        # The value further subdivides.
        value = self.getValue()
        sep = value.index(" ")

        self.__relType = value[0:sep]
        self.__objectId = value[sep+1:]

        return


    def getRelationshipType(self):
        """
        Return the type of the relationship.  This will be the ID of
        one of the Typedef entries at the end of the file.
        """
        return self.__relType



    def getObjectOfRelationshipsId(self):
        """
        Return the identifier of the object of the relationship.
        """
        return self.__objectId







# ------------------------------------------------------------------
# LOCAL ROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def createLine(textLine, expectedTag = None):
    """
    Given a text line from an OBO file, create an OBO line object from it.

    If expectedTag is provided then the tag must be of this type.
    """

    tag = textLine[0:textLine.index(TAG_END)]
    if expectedTag != None and tag != expectedTag:
        Util.fatalError([
            "Expected tag '" + expectedTag + "' but got tag '" + tag + "'.",
            "  " + textLine
            ])
    return CLASS_FOR_TAG[tag](textLine)




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Initialise the tag to class lookup.  This can't be done until
# all classes have been defined.

CLASS_FOR_TAG = {
    AUTO_GENERATED_BY: Line,
    DATE:              Line,
    DEF:               Line,
    DEFAULT_NAMESPACE: Line,
    FORMAT_VERSION:    Line,
    ID:                Line,
    IS_A:              Line,
    IS_OBSOLETE:       Line,
    NAME:              Line,
    NAMESPACE:         Line,
    RELATED_SYNONYM:   Line,
    RELATIONSHIP:      RelationshipLine,
    SAVED_BY:          Line,
    SUBSET:            Line,
    SUBSETDEF:         SubsetdefLine
    }
