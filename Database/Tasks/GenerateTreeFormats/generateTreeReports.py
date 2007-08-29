#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Control program for generating tree view reports of the anatomy database.

Usage:
 /generateTreeReport.py _configFile_

  See the _config global variable below for the parameters that need
  to be defined in the configuration file.
"""

import sys

from hgu import Util

from hgu.anatomyDb.version004 import Anatomy

import ReportTree
import ReportFile



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_config = {
    "DB_HOST":          None,
    "DB_USER":          None,
    "DB_DATABASE":      None,
    "DB_PASSWORD":      None,
    "OUTPUT_DIRECTORY": None,
    "OUTPUT_FORMAT":    None,
    "PERSPECTIVE":      None,
    "STAGE_REPORT_FILES": None,
    "DEPTH_LIMIT":      None,
    "DEBUGGING":        None
    }


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def __initialise(configParams):
    """
    Initialise program.  This includes establishing the database
    connection, and setting debugging.
    """
    if configParams["DEBUGGING"] == "ON":
        Util.setDebugging(True)
    else:
        Util.setDebugging(False)

    # Initialise Database and read the whole thing in.
    Anatomy.initialise(
        dbHost = configParams["DB_HOST"],
        dbName = configParams["DB_DATABASE"],
        dbUser = configParams["DB_USER"],
        dbPass = configParams["DB_PASSWORD"],
        charset = "latin1")

    return None



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Generate reports for each stage, and then for the abstract mouse as well.
# Each report goes in a separate file.

Util.readConfiguration(sys.argv[1], _config, printConfig = True)

__initialise(_config)

perspectiveTree = ReportTree.ReportTree(_config["PERSPECTIVE"],
                                        int(_config["DEPTH_LIMIT"]))

ReportFile.writeAbstractReport(perspectiveTree,
                               _config["OUTPUT_DIRECTORY"],
                               _config["OUTPUT_FORMAT"])
ReportFile.writeAbstractGroupsSplitReport(perspectiveTree,
                                          _config["OUTPUT_DIRECTORY"],
                                          _config["OUTPUT_FORMAT"])

if _config["STAGE_REPORT_FILES"] == "MANY":
    manyStageFiles = True
elif _config["STAGE_REPORT_FILES"] == "ONE":
    manyStageFiles = False
else:
    Util.fatalError([
        "Unexpected value '" + _config["STAGE_REPORT_FILES"] +
        " for configuration parameter STAGE_REPORT_FILES"])

ReportFile.writeStageGroupsSplitReports(perspectiveTree,
                                        _config["OUTPUT_DIRECTORY"],
                                        _config["OUTPUT_FORMAT"],
                                        manyStageFiles)

print "Done"

sys.exit(0)
