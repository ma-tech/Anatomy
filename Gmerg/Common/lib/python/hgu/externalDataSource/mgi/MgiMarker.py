#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Setup a dictionary of MGI gene symbols, names and accession numbers.

This dictionary is built from files available via FTP from MGI, and has
a very specific purpose.  Once built, you can look up the
 current gene name,
 current gene symbol,
 MGI accession number
Given
 the current symbol or a current synonym.


 This gets the files via FTP and then parses them.
"""

import os                               # file permissions
import re                               # regular expressions
import sets
import urllib                           # Use url to get FTP

from hgu import Util



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

#
# Define input files and URLs
#

# Markers, including withdrawn names
MARKER_FILE  = "MRK_List1.sql.rpt"

# List of synonyms
SYNONYM_FILE = "MRK_Synonym.sql.rpt"


FTP_URL = "ftp://ftp.informatics.jax.org/pub/reports/"
MARKER_FILE_URL = FTP_URL + MARKER_FILE
SYNONYM_FILE_URL = FTP_URL + SYNONYM_FILE




# All Files have headers.  Part of the header is
# repeated accross files, and part is unique to each file.
# Lines are defined as regular expressions to deal with variability on a
# few lines.

_MGI_COMMON_EXPECTED_HEADER = [
    "# The Jackson Laboratory - Mouse Genome Informatics \(MGI\)\n",
    "# Copyright 1996, 1999, 2002, 2005, 2008 The Jackson Laboratory\n",
    "# All Rights Reserved\n",
    "# Date Generated:  .*",
    "#\n",
    "# \*Conditions of use:\*\n",
    "# \n",
    "# Copyright Notice:\* This software and data are provided to enhance\n",
    "# knowledge and encourage progress in the scientific community and are\n",
    "# to be used only for research and educational purposes.Any reproduction\n",
    "# or use for commercial purpose is prohibited without the prior express\n",
    "# written permission of the Jackson Laboratory.\*\n",
    "# Copyright 1996, 1999, 2002, 2005, 2008 by The Jackson Laboratory\n",
    "# All Rights Reserved\n",
    "# \n",
    "# \*Citing these data:\*\n",
    "# Use of this data must be properly acknowledged in all forms of\n",
    "# publications \(web sites, databases, research papers\) by citing the Mouse\n",
    "# Genome Informatics Database project that made the data available. For this\n",
    "# report, we recommend the following electronic and published citations:\n",
    "# \n",
    "# Mouse Genome Informatics \(MGI\) Web, The Jackson Laboratory, Bar Harbor,\n",
    "# Maine. World Wide Web \(URL: http://www.informatics.jax.org\). \n",
    "# \[Type in date \(month, yr\) when you retrieved data cited.\]\n",
    "# \n",
    "# Bult CJ, Richardson JE, Blake JA, Kadin JA, Ringwald M, Eppig JT, and\n",
    "# the Mouse Genome Database Group. 2000. Mouse genome informatics in a\n",
    "# new age of biological inquiry. Proceedings of the IEEE International\n",
    "# Symposium on Bio-Informatics and Biomedical Engineering: 29-32.\n",
    "# \n",
    "# See also http://www.informatics.jax.org/mgihome/other/citation.shtml\n",
    "# for additional guidelines.\n",
    "# \n",
    "# \n",
    " \n"
    ]


#
# Marker file and marker synonym file have very similar headers
# and identical columns
#

_MGI_MF_EXPECTED_HEADER = _MGI_COMMON_EXPECTED_HEADER + [
    "Genetic Marker List \(sorted alphabetically/includes withdrawns\)\n",
    " \n",
    " MGI Accession ID               Chr      cM Position Symbol                                             Status Name                                                                                                                                                   Type                      \n",
    " ------------------------------ -------- ----------- -------------------------------------------------- ------ ------------------------------------------------------------------------------------------------------------------------------------------------------ ------------------------- \n"]

# NOTE: If either of the last two lines fails to validate because the
# column order, numberof columns, or column sizes changed, then the
# _MGI_MF_*_COL constants will also need to be changed.

# Define marker file column layout; define as a series of starting colums

_MGI_MF_ACCESSION_COL = 1               # MGI accesion number
_MGI_MF_LOCATION_COL  = 32              # Chromosome and cM position
_MGI_MF_SYMBOL_COL    = 53              # Gene Symbol
_MGI_MF_STATUS_COL    = 104             # O for active, W for withdrawn
_MGI_MF_NAME_COL      = 111             # Gene Name, or withdrawn, = gene symbol
_MGI_MF_TYPE_COL      = 262             # Gene, BAC/YAC etc
_MGI_MF_TRAILER_COL   = 288             # end of data


_MGI_MF_WITHDRAWN_STRING = "withdrawn, "
_MGI_MF_WITHDRAWN_SUFFIX1_STRING = "= "
_MGI_MF_WITHDRAWN_SUFFIX2_STRING = "[Aa]llele (of|at) "

_MGI_MF_WITHDRAWN_STRING_LEN = len(_MGI_MF_WITHDRAWN_STRING)
_MGI_MF_WITHDRAWN_SUFFIX1_STRING_LEN = len(_MGI_MF_WITHDRAWN_SUFFIX1_STRING)
_MGI_MF_WITHDRAWN_SUFFIX2_STRING_LEN = 10



# The column layout could be determined and used in a better way.  Maybe later.



#
# Define synonym file header
#
_MGI_SF_EXPECTED_HEADER = _MGI_COMMON_EXPECTED_HEADER + [
    "Marker Symbols and their Synonyms \(ordered by Marker Symbol\)\n",
    " \n",
    " MGI Accession ID               Chr      cM Position Marker Symbol                                      Synonym                                                                                    \n",
    " ------------------------------ -------- ----------- -------------------------------------------------- ------------------------------------------------------------------------------------------ \n"]

# NOTE: If either of the last two lines fails to validate because the
# column order, numberof columns, or column sizes changed, then the
# _MGI_SF_*_COL constants will also need to be changed.

# Define synonym file column layout; define as a series of starting colums

_MGI_SF_ACCESSION_COL = 1               # MGI accesion number
_MGI_SF_LOCATION_COL  = 32              # Chromosome and cM position
_MGI_SF_SYMBOL_COL    = 53              # Symbol
_MGI_SF_SYNONYM_COL   = 104             # Synonym
_MGI_SF_TRAILER_COL   = 194             # end of data

# The column layout could be determined and used in a better way.  Maybe later.




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Nomenclature dictionaries, all indexed by different things.  The strings
# that are used to index the dictionaries are all in lower case.
_nomenclatureByAccession = {}
_nomenclatureByName = {}
_nomenclatureBySymbol = {}
_nomenclatureByAnyName = {}
_nomenclatureBySynonym = {}  # different from the rest; values are lists
                             # since withdrawn names can lead to splits.
_nomenclatureByWithdrawnName = {}
_verbose = False


# ------------------------------------------------------------------
# GENE NOMENCLATURE
# ------------------------------------------------------------------

class GeneNomenclature:
    """
    Gene nomenclature for a gene at MGI.
    """

    def __init__(self, symbol, name, accession, markerType):
        self.__symbol = symbol
        self.__name = name
        self.__accession = accession
        self.__markerType = markerType

        return


    def getSymbol(self):
        """
        Return the current official MGI symbol for the gene.
        """
        return self.__symbol

    def getName(self):
        """
        Return the current official MGI name for the gene.
        """
        return self.__name

    def getAccession(self):
        """
        Return the MGI accession number for the gene.
        """
        return self.__accession

    def getMarkerType(self):
        """
        Return the marker type of the entry in the file.  Not every entry in
        the file is for a first class gene.
        """
        return self.__markerType

    def isGene(self):
        """
        Return True if this entry is for a first class gene.
        """
        return self.__markerType == "Gene"

    def isWithdrawn(self):
        """
        Return True if this entry is flagged as withdrawn.  Withdrawn
        entries sometimes include a pointer to their replacements.
        See getReplacementSymbol().
        """
        return re.match(_MGI_MF_WITHDRAWN_STRING, self.getName())

    def getReplacementSymbol(self):
        """
        Gets the replacement symbol from a withdrawn gene nomenclature entry.
        Raises an exception if the entry is not withdrawn.
        """
        if not self.isWithdrawn():
            Util.fatalError([
                "getReplacementSymbol called on a gene nomenclature entry" +
                " that is not withdrawn:",
                "Name: " + self.getName()])
        nameTail = self.getName()[_MGI_MF_WITHDRAWN_STRING_LEN:]
        if (nameTail[0:_MGI_MF_WITHDRAWN_SUFFIX1_STRING_LEN] ==
            _MGI_MF_WITHDRAWN_SUFFIX1_STRING):
            start = _MGI_MF_WITHDRAWN_SUFFIX1_STRING_LEN
        elif (re.match(_MGI_MF_WITHDRAWN_SUFFIX2_STRING,
                       nameTail[0:_MGI_MF_WITHDRAWN_SUFFIX2_STRING_LEN])):
            start = _MGI_MF_WITHDRAWN_SUFFIX2_STRING_LEN
        else:
            Util.fatalError([
                "Unrecognised suffix in replacement name string:",
                self.getName()])
        return nameTail[start:]



    def spew(self, label=""):
        """
        Debugging routine for showing the contents of an entry.
        """
        print "Gene Nomen entry:", label
        print "  symbol:   ", self.getSymbol()
        print "  name:     ", self.getName()
        print "  accession:", self.getAccession()
        print "  mrkr type:", self.getMarkerType()
        return




# ---------------------------------------------------------------------
# Internal Functions
# ---------------------------------------------------------------------

def __verifyStaticFileSection(fileHandle, expectedLines, errorMessage):
    """
    Abort if contents of a static section of a file do not look like
    what they are supposed to.
    """
    for expectedLine in expectedLines:
        actualLine = fileHandle.readline()
        if not re.match(expectedLine, actualLine):
            Util.fatalError([
                errorMessage,
                'Expected: "' + expectedLine + '"',
                'Actual:   "' + actualLine + '"'])

    return



def __addGeneToKnowledge(geneNomen):
    """
    Add this gene to what we know about MGI genes.
    """

    name = geneNomen.getName()
    symbol = geneNomen.getSymbol()
    accession = geneNomen.getAccession()

    # accession
    if accession:
        accessionLower = accession.lower()
        if accessionLower in _nomenclatureByAnyName:
            Util.fatalError([
                "Accession " + accession +
                " occurs twice in MGI marker files."])
        _nomenclatureByAccession[accessionLower] = geneNomen
        _nomenclatureByAnyName[accessionLower] = geneNomen

    # symbol
    symbolLower = symbol.lower()
    if symbolLower in _nomenclatureByAnyName:
        Util.warning([
            "Symbol " + symbol + " occurs twice in MGI marker files.",
            "Ignoring earlier occurrence."])
    _nomenclatureByAnyName[symbolLower] = geneNomen
    _nomenclatureBySymbol[symbolLower] = geneNomen

    # name can be same as Symbol
    nameLower = name.lower()
    _nomenclatureByName[nameLower] = geneNomen
    if nameLower not in _nomenclatureByAnyName:
        _nomenclatureByAnyName[nameLower] = geneNomen

    return None



def __addSynonymToKnowledge(synonym, geneNomen):
    """
    We have a valid synonym for a gene.  Add the synonym to list of known
    names for genes.
    """
    synonymLower = synonym.lower()
    if synonymLower in _nomenclatureBySynonym:
        _nomenclatureBySynonym[synonymLower].append(geneNomen)
    else:
        _nomenclatureBySynonym[synonymLower] = [geneNomen]

    return None



def _processWithdrawns(withdrawn):
    """
    Process withdrawn nomenclature from the marker file.  Withdrawn
    nomenclature forms a graph that terminates at valid gene names.
    The names in the graph become synonyms for the gene names at the
    end of the graph.

    withdrawn: Dictionary indexed by lower case withdrawn symbol.
               Each entry is a list of GeneNomenclature objects
               for that symbol.
    """
    # Walk through withdrawn nomenclature, collating them and adding them
    # to the known nomenclature.
    for symbolLower, withdrawnNomens in withdrawn.iteritems():
        processedNomens = sets.Set()
        horizon = sets.Set(withdrawnNomens)
        geneNomens = sets.Set()
        while len(horizon) > 0:
            withdrawnNomen = horizon.pop()
            geneSymbol = withdrawnNomen.getReplacementSymbol()
            geneSymbolLower = geneSymbol.lower()
            geneNomen = _nomenclatureByAnyName.get(geneSymbolLower)
            if geneNomen:
                geneNomens.add(geneNomen)
            else:
                nextGeneration = withdrawn.get(geneSymbolLower)
                if nextGeneration:
                    for nextNomen in nextGeneration:
                        if nextNomen not in processedNomens:
                            horizon.add(nextNomen)
                        elif _verbose:
                            Util.warning([
                                "Cirucular withdrawn list for symbol '" +
                                symbolLower + "'",
                                "Loop starts at symbol: '" +
                                nextNomen.getSymbol() + "'"
                                "       ends at Symbol: '" +
                                withdrawnNomen.getSymbol() + "'"])

                elif _verbose:
                    Util.warning([
                        "MGI Withdrawn symbol '" + symbolLower +
                        "' leads to a dead end at symbol '" +
                        geneSymbol + "'."])
            processedNomens.add(withdrawnNomen)

        if len(geneNomens) > 0:
            if symbolLower not in _nomenclatureByWithdrawnName:
                _nomenclatureByWithdrawnName[symbolLower] = geneNomens
            else:
                _nomenclatureByWithdrawnName[symbolLower] += geneNomens

    return


def __readGenes(markerFileName):
    """
    Read lines from marker file saving information about genes.
    """

    markerFile = open(markerFileName, "r")
    __verifyStaticFileSection(markerFile, _MGI_MF_EXPECTED_HEADER,
                              "MGI marker file header not as expected")

    # now pointing at first data row; read until you have read all the
    # marker data
    withdrawn = {} # indexed by lower case symbol
    markerLine = "Bogus"
    while markerLine not in [None, "", "\n"]:
        # read until end of markers
        markerLine = markerFile.readline()
        markerType = markerLine[_MGI_MF_TYPE_COL:_MGI_MF_TRAILER_COL].strip()

        if markerType in ["Gene", "DNA Segment"]:
            symbol = markerLine[_MGI_MF_SYMBOL_COL:_MGI_MF_STATUS_COL].strip()
            name   = markerLine[_MGI_MF_NAME_COL:_MGI_MF_TYPE_COL].strip()
            accession = (
                markerLine[_MGI_MF_ACCESSION_COL:_MGI_MF_LOCATION_COL].strip())
            if accession == "NULL":
                accession = None
            nomen = GeneNomenclature(symbol, name, accession, markerType)
            if nomen.isWithdrawn():
                if nomen.getSymbol() == nomen.getReplacementSymbol():
                    if _verbose:
                        Util.warning([
                            "Ignoring self-referential withdrawn MGI record " +
                            "for symbol '" + nomen.getSymbol() + "'"])
                else:
                    symbolLower = symbol.lower()
                    if symbolLower in withdrawn:
                        withdrawn[symbolLower].append(nomen)
                    else:
                        withdrawn[symbolLower] = [nomen]
            else:
                __addGeneToKnowledge(nomen)

    markerFile.close()

    # Add withdrawn nomenclature
    _processWithdrawns(withdrawn)

    return



def __readGeneSynonyms(synonymFileName):
    """
    Read lines from synonym file saving synonyms for genes that are already
    in the dictionary.
    """

    synFile = open(synonymFileName, "r")
    __verifyStaticFileSection(synFile, _MGI_SF_EXPECTED_HEADER,
                              "MGI synonym file header not as expected")

    # now pointing at first data row; read until you have read all the
    # synonym data

    synLine = None
    while synLine not in ["", "\n"]:

        # read until end of synonyms
        synLine = synFile.readline()
        symbol = synLine[_MGI_SF_SYMBOL_COL:_MGI_SF_SYNONYM_COL].strip()
        symbolLower = symbol.lower()
        geneNomen = _nomenclatureBySymbol.get(symbolLower)
        if geneNomen != None:
            # found a synonym for a gene.  save it.
            synonym = synLine[_MGI_SF_SYNONYM_COL:_MGI_SF_TRAILER_COL].strip()

            # Can't validate acc num from 2 files against each other.  Acc
            # num in synonym file is for the synonym, not the gene.
            __addSynonymToKnowledge(synonym, geneNomen)

    synFile.close()

    return


def __finishSynonyms():
    """
    Add synonyms to the byAnyName lookup.

    We now have synoyms from two places.
    1. The synonym file.
    1. The withdrawn names from the gene file.

    Add the synonyms from the synonym file, as long as they don't conflict
    with an existing name, and the synonym maps to a unique entry.

    Add the withdrawn names as synonyms only if they conflict with an
    existing name or synonym, and the withdrawn name maps to a unique entry.
    """
    # process synonyms
    for synLower, geneNomens in _nomenclatureBySynonym.iteritems():
        if synLower in _nomenclatureByAnyName:
            # then synonym does not matter, ignore it.
            pass
        else:
            if len(geneNomens) == 1:
                # embrace the one true synonym
                _nomenclatureByAnyName[synLower] = geneNomens[0]
            else:
                # synonym maps to multiple genes and there is no clear
                # winner.  Issue a warning if ever used.
                pass

    # process withdrawn names:
    for withdrawnNameLower, geneNomens in _nomenclatureByWithdrawnName.iteritems():
        if withdrawnNameLower in _nomenclatureByAnyName:
            # then synonym does not matter, ignore it.
            pass
        else:
            if len(geneNomens) == 1:
                # Make the withdrawn name a synonym
                _nomenclatureByAnyName[withdrawnNameLower] = geneNomens.pop()
            else:
                # Withdrawn name maps to multiple genes and there is no clear
                # winner.  Issue a warning at time it is used.
                pass

    return None



# ---------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ---------------------------------------------------------------------

def initialise(copyDir = "/tmp", reuse = False, verbose = False):
    """
    Initialise the MGI marker module.  This includes transferring files
    via FTP and then parsing them.  This takes a while and it requires
    parameters, so we can't do it at module load time.

    copyDir: Directory to put copied files into.  This directory must
             already exist.
    reuse:   True:  If files already exist in copyDir, then just re-parse
                    instead of re-getting them (yes, I have many regets)
             False: Fetch files from MGI, no matter what.
    verbose: True:  Spew forth lots of warnings.
             False: hold your tongue.
    """
    global _verbose
    _verbose = verbose

    markerFileLocal  = copyDir + "/" + MARKER_FILE
    synonymFileLocal = copyDir + "/" + SYNONYM_FILE

    markerFileExists = os.access(markerFileLocal, os.R_OK)
    synonymFileExists = os.access(synonymFileLocal, os.R_OK)

    if (reuse and markerFileExists and synonymFileExists):
        # don't need to FTP anything in
        pass

    elif not os.access(copyDir, os.W_OK):
        Util.fatalError([
            "Cannot write MGI Marker files to directory " + copyDir])

    else:
        if not reuse or not markerFileExists:
            # transfer marker file
            urllib.urlretrieve(MARKER_FILE_URL, markerFileLocal)

        if not reuse or not synonymFileExists:
            # transfer synonym file
            urllib.urlretrieve(SYNONYM_FILE_URL, synonymFileLocal)

    # Done getting files, now read them in.
    __readGenes(markerFileLocal)
    __readGeneSynonyms(synonymFileLocal)
    __finishSynonyms()

    return



def getGeneNomenclature(anyName):
    """
    Return the GeneNomenclature object that the given string maps to.
    Returns None if string does not map to any gene.

    anyName can be a gene name, gene symbol, accesion number, or gene
    synonym.  It is case insensitive.
    """
    anyNameLower = anyName.lower()
    geneNomen = _nomenclatureByAnyName.get(anyNameLower)
    if geneNomen:
        if not geneNomen.isGene():
            Util.warning([
                "'" + anyName + "' refers to a " + geneNomen.getMarkerType() +
                " at MGI, which is not a gene."])
    else:
        # Name is not recognised in names, accessions, symbols, or
        # synonyms that map to one entry.
        # Check if string maps to a synonym that maps to multiple entries
        multipleSyns = _nomenclatureBySynonym.get(anyNameLower)
        if multipleSyns:
            message = [
                "The string '" + anyName +
                "' is a synonym that maps to multiple " +
                "entries at MGI."]
            for synNomen in multipleSyns:
                message.append(synNomen.getSymbol())
            message.append("Returning none as can't tell which one to pick.")
            Util.warning(message)
        else:
            # Check if string maps to a withdrawn name that maps to multiple
            # entries.
            multipleSyns = _nomenclatureByWithdrawnName.get(anyNameLower)
            if multipleSyns:
                message = [
                    "The string '" + anyName + "' is a withdrawn name that " +
                    "maps to multiple entries at MGI."]
                for synNomen in multipleSyns:
                    message.append(synNomen.getSymbol())
                message.append(
                    "Returning none as can't tell which one to pick.")
                Util.warning(message)
            else:
                Util.warning([
                    "The string '" + anyName + "' matches no genes at MGI."])

    return geneNomen



# ---------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------

# Initialisation done in GLOBALS section and by initialise().
