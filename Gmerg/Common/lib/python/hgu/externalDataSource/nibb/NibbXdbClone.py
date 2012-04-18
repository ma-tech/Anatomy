#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
National Institute fpr Basic Biology (NIBB) Xenopus laevis EST Database (XDB)
External Data Source python library.

Information on NIBB XDB is available at

  http://xenopus.nibb.ac.jp/

This module pulls sequence information via HTTP and returns library
information that is hard-coded in this module.  The library information
was pulled from a NIBB page.
"""

import HTMLParser
import sets
import urllib                           # HTTP access.

from hgu import Util

from hgu.externalDataSource.nibb import NibbXdbLibrary


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# NIBB XDB Basics

NIBB_XDB_PREFIX = "NIBB:"
NIBB_XDB_PREFIX_LENGTH = len(NIBB_XDB_PREFIX)




# INPUT URLS
#
# The sequence is pulled from this URL.  The NIBB XDB ID (without the leading
# NIBB) is appended to the end

SEQUENCE_URL = "http://xenopus.nibb.ac.jp/cgi-bin/getSeq?"

# From the web site, this is reached by
# 1. Going to the home page: http://xenopus.nibb.ac.jp/
# 2. Enterring the NIBB XDB ID (without the leading NIBB:) in the "Search Clone"
#    box and hitting "Search"
# 3. Clicking on either the link that starts with "f" or "r" followed by the
#    NIBB XDB ID.
# 4. And finally by clicking on the "[sequences]" link at the top of that page.



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_clonesByNibbXdbId = {}                 # indexed by unprefixed ID.
_failedNibbXdbIds = sets.Set()




# Parser States.
#
# We don't care about most things in the file, but care about a few
# and we have a state for each.

DONT_CARE           = "D0NT_CARE"       # not in sequence
IN_FASTA            = "IN_FASTA"        # Expecting to see FASTA entries next
IN_FORWARD_SEQUENCE = "IN_FORWARD_SEQUENCE"
IN_REVERSE_SEQUENCE = "IN_REVERSE_SEQUENCE"
IN_FULL_INSERT_SEQUENCE = "IN_FULL_INSERT_SEQUENCE" # XL087e10 has this.


# ------------------------------------------------------------------
# NIBB XDB CLONE
# ------------------------------------------------------------------

class NibbXdbClone:
    """
    Information about a NIBB XDB Clone.  See http://xenopus.nibb.ac.jp/
    for more information.
    """

    def __init__(self, sequencePage, library):
        """
        Initialise a NIBB XDB clone, given an HTML page containing its
        sequence, and the library it belongs to.
        """

        self.__library = library
        self.__forwardSequenceFastaHeader = None
        self.__reverseSequenceFastaHeader = None
        self.__fullInsertSequenceFastaHeader = None
        self.__forwardSequence = None
        self.__reverseSequence = None
        self.__fullInsertSequence = None

        parser = _NibbXdbCloneSequenceHtmlParser(self)
        parser.feed(sequencePage.read())
        parser.close()

        return None

    def getForwardSequenceFastaHeader(self):
        """
        Forward sequence FASTA header line, without leading >.
        """
        return self.__forwardSequenceFastaHeader

    def getReverseSequenceFastaHeader(self):
        """
        Reverse sequence FASTA header line, without leading >.
        """
        return self.__reverseSequenceFastaHeader

    def getFullInsertSequenceFastaHeader(self):
        """
        Full insert sequence FASTA header line, without leading >.
        """
        return self.__fullInsertSequenceFastaHeader

    def getForwardSequence(self):
        """
        Return the forward sequence of the clone.
        """
        return self.__forwardSequence

    def getReverseSequence(self):
        """
        Return the reverse sequence of the clone
        """
        return self.__reverseSequence

    def getFullInsertSequence(self):
        """
        Return the full insert sequence of the clone, if available.
        """
        return self.__fullInsertSequence


    def getLibraryName(self):
        """
        Return the name of the library the clone is in.
        """
        return self.__library.getName()

    def getStage(self):
        """
        Return the stage the clone was created from.
        """
        return self.__library.getStage()

    def getSpecies(self):
        """
        Return the species the clone was created from.
        """
        return self.__library.getSpecies()

    def getTissue(self):
        """
        Return the tissue the clone was created from.
        """
        return self.__library.getTissue()


    def setForwardSequenceFastaHeader(self, header):
        """
        Pull the forward sequence in FASTA format from the header.
        """
        self.__forwardSequenceFastaHeader = header[header.find(">")+1:]
        return


    def setReverseSequenceFastaHeader(self, header):
        """
        Pull the reverse sequence in FASTA format from the header.
        """
        self.__reverseSequenceFastaHeader = header[header.find(">")+1:]
        return


    def setFullInsertSequenceFastaHeader(self, header):
        """
        Pull the full insert sequence in FASTA format from the header, if available.
        """
        self.__fullInsertSequenceFastaHeader = header[header.find(">")+1:]
        return


    def setForwardSequence(self, sequence):
        """
        Set the forward sequence.
        """
        self.__forwardSequence = sequence
        return


    def setReverseSequence(self, sequence):
        """
        Set the reverse sequence.
        """
        self.__reverseSequence = sequence
        return


    def setFullInsertSequence(self, sequence):
        """
        Set the full insert sequence.
        """
        self.__fullInsertSequence = sequence
        return



# ---------------------------------------------------------------------
# NIBB XDB CLONE HTML PARSER
# ---------------------------------------------------------------------

class _NibbXdbCloneSequenceHtmlParser(HTMLParser.HTMLParser):
    """
    Parser for NIBB XDB clone sequence HTML pages.
    """

    def __init__(self, nibbXdbClone):
        """
        Initialise an NIBB Clone HTML parser.

        nibbXdbClone:  The parser will put sequence information into this.
        """
        HTMLParser.HTMLParser.__init__(self)

        self.__nibbXdbClone = nibbXdbClone
        self.__state = DONT_CARE

        return None


    def handle_starttag(self, tag, attrs):
        """
        Handle a start tag.
        """
        # :PYLINT: flags attrs as unused param.  However, the signature of this
        #          method is determined by HTMLParser.  Leave it be.
        if tag == "pre":
            self.__start_pre()
        # don't care about any other tags.

        return


    def handle_endtag(self, tag):
        """
        Handle an end tag.
        """
        if tag == "pre":
            self.__end_pre()
        # don't care about any other tags.

        return


    def __start_pre(self):
        """
        The pre section containing the sequence has started.
        Change state accordingly.
        """
        if self.__state == DONT_CARE:
            self.__state = IN_FASTA

        else:
            self.__fatalError("pre")

        return



    def __end_pre(self):
        """
        The pre section containing the sequence has ended.
        Change state accordingly.
        """
        if self.__state in [IN_REVERSE_SEQUENCE, IN_FULL_INSERT_SEQUENCE]:
            self.__state = DONT_CARE

        else:
            self.__fatalError("/pre")

        return


    def handle_data(self, data):
        """
        Called when text is encountered in the page.  This includes text
        between start and end tags.
        """
        if self.__state == IN_FASTA:
            # Data is in fasta format, showing first the forward sequence,
            # and then the reverse sequence.
            lines = data.split()

            forwardSequence = ""
            reverseSequence = ""
            fullInsertSequence = ""
            for line in lines:
                # print "line:", line
                # print "Start state:", self.__state
                if line[0:2] == ">f":
                    # print "In >f"
                    # Start of forward sequence
                    if self.__state == IN_FASTA:
                        # print "In >f, IN_FASTA"
                        self.__nibbXdbClone.setForwardSequenceFastaHeader(line)
                        self.__state = IN_FORWARD_SEQUENCE
                    else:
                        self.__fatalError(line)

                elif line[0:2] == ">r":
                    # print "In >r"
                    # Start of reverse sequence
                    if self.__state == IN_FORWARD_SEQUENCE:
                        # print "In >r", self.__state
                        self.__nibbXdbClone.setReverseSequenceFastaHeader(line)
                        self.__state = IN_REVERSE_SEQUENCE
                    else:
                        self.__fatalError(line)

                elif line[0:2] == ">i":
                    # print "In >i"
                    # Start of full insert sequence
                    if self.__state == IN_REVERSE_SEQUENCE:
                        # print "In >i,", self.__state
                        self.__nibbXdbClone.setFullInsertSequenceFastaHeader(line)
                        self.__state = IN_FULL_INSERT_SEQUENCE
                    else:
                        self.__fatalError(line)

                elif self.__state == IN_FORWARD_SEQUENCE:
                    #print "IN_FORWARD_SEQUENCE"
                    if Util.isNtSequence(line):
                        #print "IN_FORWARD_SEQUENCE and is sequence"
                        forwardSequence += " " + line
                    else:
                        self.__fatalError(line)

                elif self.__state == IN_REVERSE_SEQUENCE:
                    #print "IN_REVERSE_SEQUENCE"
                    if Util.isNtSequence(line):
                        #print self.__state, "and is sequence"
                        reverseSequence += " " + line
                    else:
                        self.__fatalError(line)

                elif self.__state == IN_FULL_INSERT_SEQUENCE:
                    #print self.__state
                    if Util.isNtSequence(line):
                        #print self.__state, "and is sequence"
                        fullInsertSequence += " " + line
                    else:
                        self.__fatalError(line)
                else:
                    self.__fatalError(line)

                #print "End state:", self.__state
                #print

            if self.__state in [IN_REVERSE_SEQUENCE, IN_FULL_INSERT_SEQUENCE]:
                self.__nibbXdbClone.setForwardSequence(forwardSequence.strip())
                self.__nibbXdbClone.setReverseSequence(reverseSequence.strip())
            else:
                self.__fatalError(
                    "Sequence data did not end with expected sequence.")

            if self.__state == IN_FULL_INSERT_SEQUENCE:
                self.__nibbXdbClone.setFullInsertSequence(fullInsertSequence.strip())

        return



    def __fatalError(self, offender):
        """
        Something unexpected encountered in input.  Raise a fatal exception.
        """
        Util.fatalError([
            "NIBB Clone Sequence HTML parser error.",
            "Current state: " + self.__state,
            "Unexpected tag/line: " + offender])



# ---------------------------------------------------------------------
# INTERNAL ROUTINES
# ---------------------------------------------------------------------



# ---------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ---------------------------------------------------------------------

def getCloneByNibbXdbId(nibbXdbId):
    """
    Return information about a NIBB XDB clone, given its NIBB XDB ID.

    nibbXdbId: NIBB XDB clone ID, with or without the leading NIBB:.
    """

    cloneId = Util.stripPublicIdPrefix(nibbXdbId)
    nibbXdbClone = None

    if cloneId in _clonesByNibbXdbId:
        nibbXdbClone = _clonesByNibbXdbId[cloneId]
    elif cloneId in _failedNibbXdbIds:
        pass
    else:
        # build clone from sequence and library.
        library = NibbXdbLibrary.getLibraryByNibbXdbId(cloneId)
        if library:
            sequencePage = urllib.urlopen(SEQUENCE_URL + cloneId )
            nibbXdbClone = NibbXdbClone(sequencePage, library)
            _clonesByNibbXdbId[cloneId] = nibbXdbClone
        else:
            Util.warning([
                "Failed to locate NIBB clone library for " + nibbXdbId])
            _failedNibbXdbIds.add(nibbXdbId)


    return nibbXdbClone




# ---------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------

# Initialisation done in GLOBALS section and by initialise().
