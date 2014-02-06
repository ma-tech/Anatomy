#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Setup a dictionary of Xenbase genes.  This dictionary is built from
files available via FTP from Xenbase. Once built, you can look up the
 current gene name,
 current gene symbol,
 Xenbase accession number,
 gene function,
 synonyms

This gets the files via FTP and then parses them.

The Gene file contains pointers to all tropicalis/laevis genes.
"""

import csv                              # Parsing tab separated files.
import os                               # file permissions
import urllib                           # Use url to get FTP

from hgu import Util



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

#
# Define input files and URLs
#

# Contains list of all genes in Xenbase, include laevis only, tropicalis only,
# and laevis and tropicalis.   There is no indicator in file as to what
# evidence each gene has.
#
# Gene file does not have a header.
GENE_FILE  = "GenePageGeneralInfo_ManuallyCurated.txt"

FTP_URL = "ftp://ftp.xenbase.org/pub/GenePageReports/"
GENE_FILE_URL = FTP_URL + GENE_FILE

# Columns in the CSV file

ACCESSION_COLUMN = 0
SYMBOL_COLUMN    = 1
NAME_COLUMN      = 2
FUNCTION_COLUMN  = 3
SYNONYM_COLUMN   = 4


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Gene dictionaries, all indexed by different things.  The strings
# used to index the dictionaries are all in lower case.
_geneByAccession = {}
_geneByName = {}
_geneBySymbol = {}

# By any name excludes synonyms.  Includes symbol, name and accession.
_geneByAnyName = {}

# Synonyms can lead to more than one gene.  Each entry in dictionary is
# a list of genes.
_geneBySynonym = {}


_verbose = False


# ------------------------------------------------------------------
# XENBASE GENE
# ------------------------------------------------------------------

class Gene:
    """
    Xenbase gene information.
    """

    def __init__(self, csvLine):
        """
        Given a line from the gene file, create a Xenbase gene object.
        csvLine: list of values from line in the gene file.  Each column
                 in file is an entry in the list.
        """
        self.__symbol = csvLine[SYMBOL_COLUMN]
        self.__name = csvLine[NAME_COLUMN]
        self.__accession = csvLine[ACCESSION_COLUMN]
        if len(csvLine[FUNCTION_COLUMN]) > 0:
            self.__function = csvLine[FUNCTION_COLUMN]
        else:
            self.__function = None
        if len(csvLine[SYNONYM_COLUMN]) > 0:
            self.__synonyms = csvLine[SYNONYM_COLUMN].split("|")
        else:
            self.__synonyms = []
        return


    def getSymbol(self):
        """
        Return the current official Xenbase symbol for the gene.
        """
        return self.__symbol

    def getName(self):
        """
        Return the current official Xenbase name for the gene.
        """
        return self.__name

    def getAccession(self):
        """
        Return the Xenbase accession number for the gene.
        """
        return self.__accession

    def getFunction(self):
        """
        Return the function, if any, of this gene.  Returns None if
        gene function was not given.
        """
        return self.__function

    def getSynonyms(self):
        """
        Return list of synonyms for this gene.  Returns the empty list if
        the gene has no synonyms.
        """
        return self.__synonyms



    def spew(self, label=""):
        """
        Debugging routine for showing the contents of an entry.
        """
        print "Xenbase Gene entry:", label
        print "  symbol:   ", self.getSymbol()
        print "  name:     ", self.getName()
        print "  accession:", self.getAccession()
        print "  function: ", self.getFunction()
        print "  syonyms:  ", self.getSynonyms()

        return




# ---------------------------------------------------------------------
# Internal Functions
# ---------------------------------------------------------------------


def _addToByAnyName(anyName, gene):
    """
    Add the given name to the byAnyName lookup for the given gene.
    """
    anyNameLower = anyName.lower()
    if anyNameLower in _geneByAnyName:
        Util.fatalError([
            "String '" + anyName +
            "' associated with more than one gene in Xenbase gene file.",
            "Rewrite the code to deal with this."])
    else:
        _geneByAnyName[anyNameLower] = gene

    return


def _addGeneToKnowledge(gene):
    """
    Add this gene to what we know about Xenbase genes.
    """

    # Accession
    accessionLower = gene.getAccession().lower()
    if accessionLower in _geneByAccession:
        Util.fatalError([
            "Accession ID '" + gene.getAccession() +
            "' occurs more than once in Xenbase gene file."])
    else:
        _geneByAccession[accessionLower] = gene
        _addToByAnyName(gene.getAccession(), gene)

    # Symbol
    symbolLower = gene.getSymbol().lower()
    if symbolLower in _geneBySymbol:
        Util.fatalError([
            "Gene Symbol '" + gene.getSymbol() +
            "' occurs more than once in Xenbase gene file."])
    else:
        _geneBySymbol[symbolLower] = gene
        _addToByAnyName(gene.getSymbol(), gene)

    # Name
    nameLower = gene.getName().lower()
    if nameLower != symbolLower:
        if nameLower in _geneByName:
            Util.fatalError([
                "Gene Name '" + gene.getName() +
                "' occurs more than once in Xenbase gene file."])
        else:
            _geneByName[nameLower] = gene
            _addToByAnyName(gene.getName(), gene)

    # Synonyms
    for syn in gene.getSynonyms():
        synLower = syn.lower()
        if synLower == symbolLower:
            if _verbose:
                Util.warning([
                    "Synonym '" + syn + "' same as symbol. Ignoring synonym."])
        else:
            if synLower not in _geneBySynonym:
                _geneBySynonym[synLower] = []
            _geneBySynonym[synLower].append(gene)

    return None




def __readGenes(geneFileName):
    """
    Read lines from Xenbase gene file saving information about genes.
    """

    geneFile = csv.reader(open(geneFileName, "r"), dialect="excel-tab")

    # now pointing at first data row; read until you have read all the
    # gene data
    for geneLine in geneFile:

        gene = Gene(geneLine)
        _addGeneToKnowledge(gene)

    return






# ---------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ---------------------------------------------------------------------

def initialise(copyDir = "/tmp", reuse = False, verbose = False):
    """
    Initialise the Xenbase gene module.  This includes transferring files
    via FTP and then parsing them.  This takes a while and it requires
    parameters, so we can't do it at module load time.

    copyDir: Directory to put copied files into.  This directory must
             already exist.
    reuse:   True:  If files already exist in copyDir, then just re-parse
                    instead of re-getting them
             False: Fetch files from Xenbase, no matter what.
    verbose: True:  Spew forth lots of warnings.
             False: hold your tongue.
    """
    global _verbose
    _verbose = verbose

    geneFileLocal  = copyDir + "/" + GENE_FILE

    geneFileExists = os.access(geneFileLocal, os.R_OK)

    if (reuse and geneFileExists):
        # don't need to FTP anything in
        pass

    elif not os.access(copyDir, os.W_OK):
        Util.fatalError([
            "Cannot write Xenbase Marker files to directory " + copyDir])

    else:
        if not reuse or not geneFileExists:
            # transfer gene file
            urllib.urlretrieve(GENE_FILE_URL, geneFileLocal)


    # Done getting files, now read them in.
    __readGenes(geneFileLocal)

    return



def getGeneByAnyName(anyName):
    """
    Return the Xenbase Gene object that the given string maps to.
    Returns None if string does not map to any gene.

    anyName can be a gene name, gene symbol, accesion number, or gene
    synonym.  It is case insensitive.
    """
    anyNameLower = anyName.lower()
    gene = _geneByAnyName.get(anyNameLower)
    if gene == None:
        if anyNameLower in _geneBySynonym:
            if len(_geneBySynonym[anyNameLower]) > 1:
                Util.warning([
                    "'" + anyName + "' is a synonym for multiple genes.",
                    "Can't resolve which one so returning None."])
            else:
                gene = _geneBySynonym[anyNameLower][0]

    if gene == None and _verbose:
        Util.warning([
            "The string '" + anyName + "' matches no genes at Xenbase."])

    return gene



# ---------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------

# Initialisation done in GLOBALS section and by initialise().

