#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Control program for generating anatomy database reports that present
the anatomy as a tree (even though it is a DAG).  Tree reports are
well-suited for presenting the anatomy as a tree.  However, they are
poor for reasoning about the ontologies.  DAG-based formats are better
for reasoning about the ontology.

Usage:
  ./generateTreeReports.py _configFile_

  See the config global variable below for the parameters that need
  to be defined in the configuration file.  See example.config for
  an explanation of each parameter.
"""

import sys

from hgu import Util

from hgu.anatomyDb.version006 import Anatomy

import ReportTree
import ReportFile

# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------





def __initialise(configFile, configParams):
    """
    Initialise program.  This includes establishing the database
    connection, and setting debugging.
    """
    Util.readConfiguration(configFile, config, printConfig = True)

    if configParams["DEBUGGING"] == "ON":
        Util.setDebugging(True)
    else:
        Util.setDebugging(False)

    # Several of the parameters can be space separated lists.
    # Convert these to python lists in configParams.
    configParams["OUTPUT_FORMATS"] = configParams["OUTPUT_FORMATS"].split()
    configParams["STAGE_FILES"]    = configParams["STAGE_FILES"] .split()

    if configParams["ABSTRACT_REPORTS"] == "NONE":
        configParams["ABSTRACT_REPORTS"] = []
    else:
        configParams["ABSTRACT_REPORTS"] = configParams["ABSTRACT_REPORTS"].split()

    if configParams["STAGE_REPORTS"] == "NONE":
        configParams["STAGE_REPORTS"] = []
    else:
        configParams["STAGE_REPORTS"] = configParams["STAGE_REPORTS"].split()

    if configParams["DEPTH_LIMIT"] == "NONE":
        configParams["DEPTH_LIMIT"] = sys.maxint
    else:
        configParams["DEPTH_LIMIT"] = int(configParams["DEPTH_LIMIT"])

    # process the PROJECT flag
    if configParams["PROJECT"] not in ["EMAP", "GUDMAP"]:
        Util.fatalError(["Unrecognised PROJECT parameter: " + configParams["PROJECT"]])


    # Initialise Database and read the whole thing in.
    Util.statusMessage(["Reading in anatomy database."])
    Anatomy.initialise(
        sortProject = config["PROJECT"],
        dbHost = configParams["DB_HOST"],
        dbName = configParams["DB_DATABASE"],
        dbUser = configParams["DB_USER"],
        dbPass = configParams["DB_PASSWORD"])


    return None



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

config = {
    "DB_HOST":          None,
    "DB_USER":          None,
    "DB_DATABASE":      None,
    "DB_PASSWORD":      None,
    "PERSPECTIVE":      None,
    "OUTPUT_DIRECTORY": None,
    "OUTPUT_FORMATS":   None,
    "ABSTRACT_REPORTS": None,
    "STAGE_REPORTS":    None,
    "STAGE_FILES":      None,
    "DEPTH_LIMIT":      None,
    "DEBUGGING":        None,
    "PROJECT":          None 
    }


__initialise(sys.argv[1], config)

Util.statusMessage(["Generating report tree data stucture."])

perspectiveTree = ReportTree.ReportTree(config["PERSPECTIVE"],
                                        config["DEPTH_LIMIT"])

for format in config["OUTPUT_FORMATS"]:
    for abstractType in config["ABSTRACT_REPORTS"]:
        Util.statusMessage([
            "Generating " + format + " abstract " + abstractType + " report."])
        ReportFile.writeAbstractReport(
            perspectiveTree, config["OUTPUT_DIRECTORY"], format, abstractType)

    for stageType in config["STAGE_REPORTS"]:
        for togetherness in config["STAGE_FILES"]:
            Util.statusMessage([
                "Generating " + format + " stage " + stageType +
                " with stages in " + togetherness + " file(s) report."])
            ReportFile.writeStageReport(
                perspectiveTree, config["OUTPUT_DIRECTORY"], format, stageType,
                togetherness)


Util.statusMessage(["Done"])

sys.exit(0)
