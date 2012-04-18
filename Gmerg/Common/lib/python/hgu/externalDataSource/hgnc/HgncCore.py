#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
# Setup a dictionary of HGNC (HUGO Gene Nomenclature Committee) Core Data.
#
# This dictionary is built from files available via HTTP from HGNC.
# It downloads only the "Core Data" file.  Core data by chromosome and an
# "All Data" download are also available.  They are available in tab separated
# or HTML format.

import csv                              # Tab delimited files.
import os                               # file permissions
import re                               # regular expressions
import sets
import sys                              # error reporting
import urllib                           #

from hgu import Util



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# HGNC Basics.

HGNC_ID_PREFIX = "HGNC:"


#
# Define input files and URLs
#
# The links to everything are available at:
# http://www.gene.ucl.ac.uk/nomenclature/data/gdlw_index.html
#
# The links themselves all start with the same URL, but have different
# parameters.  The parameter lists are highly customisable.  See
# http://www.gene.ucl.ac.uk/cgi-bin/nomenclature/gdlw.pl

# URL prefix to get the various files:
HGNC_URL = "http://www.gene.ucl.ac.uk/cgi-bin/nomenclature/gdlw.pl"
CORE_URL_EXTENSION = "?title=Core+Data;col=gd_hgnc_id;col=gd_app_sym;col=gd_app_name;col=gd_status;col=gd_prev_sym;col=gd_aliases;col=gd_pub_chrom_map;col=gd_pub_acc_ids;col=gd_pub_refseq_ids;status=Approved;status=Approved+Non-Human;status=Entry+Withdrawn;status_opt=3;=on;where=;order_by=gd_app_sym_sort;limit=;format=text;submit=submit;.cgifields=;.cgifields=status;.cgifields=chr"
CORE_FILE_URL = HGNC_URL + CORE_URL_EXTENSION


# What should we call it on local disk?
# 
CORE_FILE  = "HGNCCore.csv"


# 
CORE_FILE_HEADER = [
    "HGNC ID",
    "Approved Symbol",
    "Approved Name",
    "Status",
    "Previous Symbols",
    "Aliases",
    "Chromosome",
    "Accession Numbers",
    "RefSeq IDs"
    ]


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_nomenclatureBySymbol = {}
_verbose = False




# ------------------------------------------------------------------
# GENE NOMENCLATURE
# ------------------------------------------------------------------

class GeneNomenclature:
    """
    Gene nomenclature for an HGNC gene
    """

    def __init__(self, csvLine):
        self.__csvColumns = {}
        for colName, colValue in zip(CORE_FILE_HEADER, csvLine):
            self.__csvColumns[colName] = colValue

        return

    def getHgncId(self):
        """
        Get the HGNC ID for this gene, including the HGNC: prefix.
        """
        return HGNC_ID_PREFIX + self.__csvColumns["HGNC ID"]

    def getSymbol(self):
        return self.__csvColumns["Approved Symbol"]

    def getName(self):
        return self.__csvColumns["Approved Name"]

    def getStatus(self):
        return self.__csvColumns["Status"]

    def isWithdrawn(self):
        return self.__csvColumns["Status"] == "Entry withdrawn"


    def spew(self, label=None):
        if not label:
            label = ""
        print "Gene Nomen entry:", label
        for colName in CORE_FILE_HEADER:
            print "  ", colName + ":", self.__csvColumns[colName]

        return




# ---------------------------------------------------------------------
# Internal Functions
# ---------------------------------------------------------------------


def __verifyLine(expectedLine, actualLine, lineDesc):
    "Compare line read with line that was expected and throw exception if they disagree"

    if len(expectedLine) != len(actualLine):
        Util.fatalError([
            lineDesc + " has different number of columns than expected",
            '  Expected # Columns: "' + str(len(expectedLine)) + '"',
            '  Actual # Columns:   "' + str(len(actualLine)) + '"'])

    for expected,reality in zip(expectedLine, actualLine):
        if expected != reality:
            Util.fatalError([
                lineDesc + " not as expected",
                '  Expected: "' + expected + '"',
                '  Actual:   "' + reality + '"'])

    return None                         # no error detected



def __addGeneToKnowledge(geneNomen):
    """
    Add this gene to what we know about HGNC genes.
    """
    
    symbol = geneNomen.getSymbol()

    # symbol
    if symbol in _nomenclatureBySymbol:
        Util.fatalError([
            "Symbol " + symbol + " occurs twice in HGNC Core Data file."])
    _nomenclatureBySymbol[symbol] = geneNomen

    return None


def __readCore(coreFileName):
    """
    Read lines from HGNC Core Data file saving information about genes.
    """

    coreFile = csv.reader(open(coreFileName, "r"), dialect="excel-tab")
    __verifyLine(CORE_FILE_HEADER, coreFile.next(),
                 "HGNC Core Data File Column Header Line")

    # now pointing at first data row; read until you have read all the 
    # core data
    withdrawn = {} # indexed by symbol
    coreLine = "Bogus"
    for coreLine in coreFile:
        nomen = GeneNomenclature(coreLine)
        __addGeneToKnowledge(nomen)

    return



# ---------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ---------------------------------------------------------------------

def initialise(copyDir = "/tmp", reuse = False, verbose = False):
    """
    Initialise the HGNC Core data module.  This includes transferring files
    via HTTP and then parsing them.  This takes a while and it requires
    parameters, so we can't do it at module load time.

    copyDir: Directory to put copied file into.  This directory must
             already exist.
    reuse:   True:  If file already exista in copyDir, then just re-parse
                    instead of re-getting them (yes, I have many regets)
             False: Fetch files from HGNC, no matter what.
    verbose: True:  Spew forth lots of warnings.
             False: hold your tongue.
    """
    global _verbose
    _verbose = verbose

    coreFileLocal = copyDir + "/" + CORE_FILE

    coreFileExists = os.access(coreFileLocal, os.R_OK)

    if (reuse and coreFileExists):
        # don't need to transfer anything in
        pass

    elif not os.access(copyDir, os.W_OK):
        Util.fatalError([
            "Cannot write HGNC files to directory " + copyDir])

    else:
        if not reuse or not coreFileExists:
            # transfer core file
            urllib.urlretrieve(CORE_FILE_URL, coreFileLocal)

    # Done getting file, now read it in.
    __readCore(coreFileLocal)

    return



def getGeneNomenclatureBySymbol(symbol):
    """
    Return the GeneNomenclature object for the given gene symbol.
    Returns None if symbol does not map to any gene.
    """

    return _nomenclatureBySymbol.get(symbol.upper())




# ---------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------

# Initialisation done in GLOBALS section and by initialise().
