#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Control program that translates a EMAP CIOF file into a relational anatomy
# database.
#
# For anatomy database version 4, the process of creating the database update
# scripts became an even bigger hack than they already were.  This is because
# we now include anatomy tree ordering information in the update process.
#
# This is a giant hack for a number of reasons:
# 1. It doesn't get this information from the CIOF file, and the very
#    name of this script/directory process implies that is all this does.
#    Internally (i.e., in the data structures), this program makes it look
#    like sequence information originates in the CIOF, when it does not.
# 2. You now actually have to run this script twice to do an update.  The
#    recipe is now:
#    A. Run this program, without a relationship sequence file.  This
#       generates a big batch of database update scripts, which update
#       many things, but not relationsip sequence.
#    B. Run those scripts against a database, producing the next version
#       of the database.
#    C. Run the tree reports against that database.  This will produce
#       reports showing the tree using the sequence from the previous
#       version of the database.
#    D. Manually edit those tree reports, rearranging lines in the order
#       you want them to be.
#    E. Reload your database with the previous version.
#    G. Rerun this script, this time providing the sequence file.
#    This results in a set of database update scripts that reflect both
#    the contents of the new CIOF and the preferred sequence of terms
#    in the new version of the database.
#
# It's a chicken-egg problem.  It results in a scramble.  :-(


import re                               # regular expressions
import sys                              # error reporting

import AnatomyBase                      # Anatomy knowledge encode in Python
import Ciof                             # CIOF basics: entities and attributes
import CiofStream                       # Read ciof file
import Util                             # debugging/error messages



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------


# These are all defined in the configuration file and set by __setConfiguration.
_dbHost = None
_dbName = None
_dbUser = None
_dbPass = None
_versionComments = None
_outputDir = None
_ciofFile = None
_showSynonymOverlapWarnings = False
_debugging = False


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def __setConfiguration(configFilename):
    """
    Read configuration from configuration file.
    """
    global _dbHost, _dbName, _dbUser, _dbPass
    global _versionComments, _outputDir, _ciofFile
    global _sequenceFile, _abstractSortOrder, _debugging

    print "Using Configuration:"
    configFile = open(configFilename, "r")
    line = configFile.readline()
    while line != "":                   # while not at EOF
        if not line[0] in [" ", "#", "\n" ]:   # ignore blank lines and comments
            lineWithoutNewline = line[:-1]
            name, value = lineWithoutNewline.split(" = ")
            if name != "PASSWORD":
                print lineWithoutNewline

            if name == "HOST":
                _dbHost = value
            elif name == "DBNAME":
                _dbName = value
            elif name == "USER":
                _dbUser = value
            elif name == "PASSWORD":
                _dbPass = value
            elif name == "VERSION":
                _versionComments = value
            elif name == "CIOFFILE":
                _ciofFile = value
            elif name == "OUTPUTDIR":
                _outputDir = value
            elif name == "SEQUENCEFILE":
                if value.lower() <> "none":
                    _sequenceFile = value
                else:
                    _sequenceFile = None
            elif name == "ABSTRACTSORTORDER":
                _abstractSortOrder = value.lower()
            elif name == "NAME_SYNONYM_OVERLAP_WARNINGS":
                if value.lower() in ["on", "true", "1"]:
                    _showSynonymOverlapWarnings = True
                elif value.lower() not in ["off", "false", "0"]:
                    Util.fatalError([
                        "Unrecognised value for " +
                        "NAME_SYNONYM_OVERLAP_WARNINGS parameter: " + value])
            elif name == "DEBUG":
                if value.lower() in ["on", "true", "1"]:
                    _debugging = True
                elif value.lower() not in ["off", "false", "0"]:
                    Util.fatalError([
                        "Unrecognised value for DEBUG parameter: " + value])
            else:
                Util.fatalError([
                    "Unexpected parameter in the " + configFilename + " configuration file.",
                    "Name: '" + name + ",  Value: '" + value + "'"])
        line = configFile.readline()

    print
    print
    return



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Read in configuration

configFile = sys.argv[1]
__setConfiguration(configFile)
Util.initialise(_debugging)


# Open and read in header of CIOF file.
ciofStream = CiofStream.CiofInputStream(_ciofFile)

# Read knowledge in from database, and prepare for reading in CIOF
AnatomyBase.initialiseKnowledge(ciofStream.getDateTime(),
                                _versionComments, _outputDir,
                                _dbHost, _dbName, _dbUser, _dbPass)

# Read the rest of the CIOF File
entity = ciofStream.getNextEntity()
while entity:
    AnatomyBase.addToKnowledge(entity)
    entity = ciofStream.getNextEntity()

AnatomyBase.dealWithTcFallout() # TC entries require post-processing.

AnatomyBase.readDb()          # Read in corresponding data from database
AnatomyBase.readRelationshipSequence(_sequenceFile) # Get item display order
AnatomyBase.deriveKnowledge(_abstractSortOrder) # know facts, derive the rest
AnatomyBase.exportKnowledge() # dump it in DB format

# Perform final analysis and report any potential problems.
AnatomyBase.genReport(_showSynonymOverlapWarnings)

AnatomyBase.finalise()
