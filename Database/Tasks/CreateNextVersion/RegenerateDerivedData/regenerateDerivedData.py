#!/usr/bin/env /usr/local/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
The anatomy database has several derived tables in it.  This script
regenerates those tables from the base data.
"""

import sys

from hgu import Util
from hgu.db import DbAccess
from hgu.anatomyDb.version006 import Anatomy # High level anatomy DB access

#sys.setrecursionlimit(3000)

#print "sys.getrecursionlimit = %s" % sys.getrecursionlimit()

# List of expected configuration parameters
config = {
    "DB_HOST":          None,
    "DB_USER":          None,
    "DB_DATABASE":      None,
    "DB_PASSWORD":      None,
    "OUTPUT_FILEPATH":  None,
    "PROJECT":          None
    }

configFile = sys.argv[1]
Util.readConfiguration(configFile, config, printConfig=True)

# process the PROJECT flag
if config["PROJECT"].lower() not in ["emap", "gudmap"]:
    Util.fatalError(["Unrecognised PROJECT parameter: " + config["PROJECT"]])


Util.statusMessage(["Reading in anatomy database."])


Anatomy.initialise(sortProject = config["PROJECT"],
                   dbHost = config["DB_HOST"], 
                   dbName = config["DB_DATABASE"],
                   dbUser = config["DB_USER"], 
                   dbPass = config["DB_PASSWORD"],
                   outputFilePath = config["OUTPUT_FILEPATH"])

Util.statusMessage(["Generating SQL to repopulate derived tables."])
Anatomy.generateDerivedData()
DbAccess.finalise()

Util.statusMessage(["Done. SQL script is in " + config["OUTPUT_FILEPATH"]])
sys.exit(0)
