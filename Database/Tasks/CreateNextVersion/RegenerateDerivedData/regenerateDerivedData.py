#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
The anatomy database has several derived tables in it.  This script
regenerates those tables from the base data.
"""

import sys

from hgu import Util
from hgu.db import DbAccess
from hgu.anatomyDb.version004 import Anatomy # High level anatomy DB access

# List of expected configuration parameters
config = {
    "DB_HOST":          None,
    "DB_USER":          None,
    "DB_DATABASE":      None,
    "DB_PASSWORD":      None,
    "OUTPUT_FILEPATH":  None
    }

configFile = sys.argv[1]
Util.readConfiguration(configFile, config, printConfig=True)
Anatomy.initialise(dbHost = config["DB_HOST"], dbName = config["DB_DATABASE"],
                   dbUser = config["DB_USER"], dbPass = config["DB_PASSWORD"],
                   outputFilePath = config["OUTPUT_FILEPATH"])
Anatomy.generateDerivedData()
DbAccess.finalise()

sys.exit(0)
