#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
# Retrieve information about Clones from the I.M.A.G.E. Consortium.
#
# IMAGE does not have a handy download file for getting information
# about clones (other than sequence information).  You have to screen
# scrape it from the web site.
#

import HTMLParser
import os                               # file permissions
import re                               # regular expressions
import sets
import sys                              # error reporting
import urllib                           #

from hgu import Util


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

IMAGE_CLONE_URL = "http://image.llnl.gov/image/IQ/bin/singleCloneQuery"


# The image clone page uses a table to display information about the clone.
# Use the column headers as a way to keep track of where we are in the
# document.

COLUMN_HEADERS = [
    "Result Number",
    "CLONE ID",
    "COLLECTION NAME",
    "PLATE",
    "ROW POS",
    "COL POS",
    "LIBR NAME",
    "CDNA LIBR ID",
    "SPECIES",
    "TISSUE TYPE",
    "VECTOR NAME",
    "INSERT DIGEST",
    "GB ACCNUM",
    "GB GI",
    "GB DATE CREATED",
    "DATE MODIFIED",
    "ENDEDNESS",
    "SEQ",
    "MASKED SEQ",
    "SEQ LENGTH",
    "QUALITY SEQ START",
    "QUALITY SEQ STOP",
    "IS LOW QUALITY",
    "EXTERNAL REF NAME",
    "EXT DB ID",
    "PROBLEM TYPE",
    "COMMENTS"
    ]


# Parser States.
#
# We don't care about most things in the file, but care about a few
# and we have a state for each.

DONT_CARE = 0                           # not in table
IN_HEADER_ROW = 1                       # in the table heading row.
IN_COLUMN_HEADER = 2                    # in a column heading
IN_DATA_ROW = 3                         # in a data row
IN_DATA_COLUMN = 4                      # in a data column

PARSER_STATES = [
    "Don't care",
    "In Header Row",
    "In column header",
    "In data row",
    "In data column"
    ]


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_imageClonesByImageId = {}
_failedImageIds = sets.Set()




# ---------------------------------------------------------------------
# IMAGE CLONE
# ---------------------------------------------------------------------

class ImageClone:
    """
    Describes an IMAGE consortium clone.
    """

    def __init__(self, clonePage):
        """
        Initialise an IMAGE consortium clone
        """
        self.__attributes = {}
        self.__parser = _ImageCloneHtmlParser(self)
        self.__parser.feed(clonePage.read())
        self.__parser.close()

        return None


    def _setAttribute(self, name, value):

        self.__attributes[name] = value
        return
    

    def getImageId(self):
        """
        Return IMAGE ID without the IMAGE: prefix.
        """
        return self.__attributes["CLONE ID"]


    def getLibraryName(self):
        return self.__attributes["LIBR NAME"]

    def getSpecies(self):
        return self.__attributes["SPECIES"]

    def getTissue(self):
        return self.__attributes["TISSUE TYPE"]

    def getVectorName(self):
        return self.__attributes["VECTOR NAME"]

    def getInsertDigest(self):
        return self.__attributes["INSERT_ DIGEST"]

    def getGenbankAccessionNumber(self):
        return self.__attributes["GB ACCNUM"]

    def getEndedness(self):
        return self.__attributes["ENDEDNESS"]
    
    def getSequenceLength(self):
        return self.__attributes["SEQ LENGTH"]

    def getSequence(self):
        return self.__attributes["SEQ"]

    def getQualitySequenceStart(self):
        return self.__attributes["QUALITY SEQ START"]

    def getQualitySequenceStop(self):
        return self.__attributes["QUALITY SEQ STOP"]

    def sequenceIsLowQuality(self):
        return self.__attributes["IS LOW QUALITY"] == "1"

    def getProblemType(self):
        problemType = self.__attributes["PROBLEM TYPE"]
        if problemType == "NULL":
            problemType = None
        return problemType
    
    def getProblemComments(self):
        problemComments = self.__attributes["COMMENTS"]
        if problemComments == "NULL":
            problemComments = None
        return problemComments
    

    def getFastaHeader(self):
        """
        Generate a FASTA header line for this clone.
        """
        return (
            "I.M.A.G.E. Consortium clone IMAGE:" +
            self.getImageId() +
            " Genbank accession: " + self.getGenbankAccessionNumber())

    def getCloneComments(self):
        """
        Generate comment string to describe the non-sequence aspects of the
        clone.
        """
        return (
            "Library: " + self.getLibraryName() +
            ";  Vector: " + self.getVectorName() +
            ";  Insert Digest: " + self.getInsertDigest())

    def getSequenceComments(self):
        """
        Generate comment string specifically describing the sequence.
        """
        comment = (
            "Length: " + self.getSequenceLength() +
            ";  Quality start/stop positions: (" +
            self.getQualitySequenceStart() + "/" +
            self.getQualitySequenceStop() + ").")

        if self.sequenceIsLowQuality():
            comment += "  Sequence flagged by I.M.A.G.E. as low quality."

        if self.getProblemType():
            comment += "  Sequence has problem: " + self.getProblemType() + "."

        if self.getProblemComments():
            comment += "  " + self.getProblemComments()

        return comment

    

# ---------------------------------------------------------------------
# IMAGE CLONE HTML PARSER
# ---------------------------------------------------------------------

class _ImageCloneHtmlParser(HTMLParser.HTMLParser):
    """
    Parser for IMAGE clone HTML pages.
    """

    def __init__(self, imageClone):
        """
        Initialise an IMAGE Clone HTML parser.

        imageClone:  The parser will populate this image clone.
        """
        HTMLParser.HTMLParser.__init__(self)

        self.__imageClone = imageClone
        self.__state = DONT_CARE
        self.__columnNumber = None

        return None


    def handle_starttag(self, tag, attrs):
        """
        Handle a start tag.
        """
        if tag == "tr":
            self.__start_tr()
        elif tag == "td":
            self.__start_td()
        # don't care about any other tags.
        
        return


    def handle_endtag(self, tag):
        """
        Handle an end tag.
        """
        if tag == "td":
            self.__end_td()
        # don't care about any other tags.
        
        return


    def __start_tr(self):
        """
        A table row has started.  Change state accordingly.
        """
        if self.__state == DONT_CARE:
            self.__state = IN_HEADER_ROW
            self.__columnNumber = -1
            
        elif self.__state in [IN_HEADER_ROW, IN_DATA_ROW]:
            # Some entries have multiple data rows.  Always use the last one
            # as it is always the most recent.
            self.__state = IN_DATA_ROW
            self.__columnNumber = -1

        else:
            Util.fatalError([
                "IMAGE Clone HTML parser error.",
                "Current state: " + PARSER_STATES[self.__state],
                "Next Tag: tr"])

        return



    def __start_td(self):
        """
        A table data element has started.  Change state accordingly.
        """
        if self.__state in [IN_HEADER_ROW, IN_COLUMN_HEADER]:
            self.__state = IN_COLUMN_HEADER
            self.__columnNumber += 1
            
        elif self.__state in [IN_DATA_ROW, IN_DATA_COLUMN]:
            self.__state = IN_DATA_COLUMN
            self.__columnNumber += 1

        else:
            Util.fatalError([
                "IMAGE Clone HTML parser error.",
                "Current state: " + PARSER_STATES[self.__state],
                "Next Tag: td"])

        return


    def __end_td(self):
        """
        A table data element has ended.  Change state accordingly.
        """
        if self.__state in [IN_COLUMN_HEADER]:
            self.__state = IN_HEADER_ROW
            
        elif self.__state in [IN_DATA_COLUMN]:
            self.__state = IN_DATA_ROW

        else:
            Util.fatalError([
                "IMAGE Clone HTML parser error.",
                "Current state: " + PARSER_STATES[self.__state],
                "Next Closing Tag: td"])

        return


    def handle_data(self, data):
        """
        Called when text is encountered in the page.  This includes text
        between start and end tags.
        """
        if self.__state == IN_COLUMN_HEADER:
            # verify column header is as expected
            if data != COLUMN_HEADERS[self.__columnNumber]:
                Util.fatalError([
                    "IMAGE Clone HTML parser error.",
                    "Unexpected column header.",
                    "Expected: ", COLUMN_HEADERS[self.__columnNumber],
                    "Actual:   ", data])
        elif self.__state == IN_DATA_COLUMN:
            self.__imageClone._setAttribute(
                COLUMN_HEADERS[self.__columnNumber], data)

        # ignore everything else

        return

    

# ---------------------------------------------------------------------
# Internal Functions
# ---------------------------------------------------------------------

# ---------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ---------------------------------------------------------------------

def getCloneByImageId(imageId):
    """
    Return information about an IMAGE clone, given its IMAGE ID.

    imageId: IMAGE clone ID, with or without the leading IMAGE:.
    """

    if imageId[0:6] == "IMAGE:":
        cloneId = imageId[6:]
    else:
        cloneId = imageId

    if cloneId in _imageClonesByImageId:
        imageClone = _imageClonesByImageId[cloneId]
    elif cloneId in _failedImageIds:
        imageClone = None
    else:
        # do lookup at IMAGE.
        params = urllib.urlencode({"clone_id": cloneId})
        clonePage = urllib.urlopen(IMAGE_CLONE_URL, params)
        imageClone = ImageClone(clonePage)
        _imageClonesByImageId[cloneId] = imageClone

    return imageClone
    



# ---------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------

# Initialisation done in GLOBALS section and by initialise().
