#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Generate a description of the anatomy database in an XML format.

This generates the the database in minimal format, as a directed
acyclic graph (DAG).  This is a minimal non-redundant format that
is good for reasoning about the database.  However, it takes some
work to convert the DAG to a tree for display purposes.  If all you
want to do is display the anatomy as a tree then use one of the many
tree based formats.

This generates two types of files:
  - An abstract mouse.  This contains all information about the mouse
    across all stages.  It is still non-redundant.
  - Persepctives: A single file that defines all perspectives in the
    anatomy.

Usage:
  ./generateEmapXml.py _configFile_
"""


import sys

from hgu import Util                    # error/warnings, common routines.

# Database access

from hgu.anatomyDb.version004 import Anatomy
from hgu.anatomyDb.version004 import AnatomyGraph


import XmlGraphFile
import XmlPerspectivesFile

# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# List of expected configuration parameters

_config = {
    "DB_HOST":     None,
    "DB_USER":     None,
    "DB_DATABASE": None,
    "DB_PASSWORD": None,
    "OUTPUT_DIRECTORY": None,
    "DEBUGGING":   None
    }

# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Read in the anatomy and then spew it in XML.

Util.readConfiguration(sys.argv[1], _config, printConfig = True)

graphFile = _config["OUTPUT_DIRECTORY"] + "/" + "emap.xml"

Anatomy.initialise(dbHost = _config["DB_HOST"],
                   dbName = _config["DB_DATABASE"],
                   dbUser = _config["DB_USER"],
                   dbPass = _config["DB_PASSWORD"])

Util.statusMessage(["Anatomy initialised."])

# Create the graph file
graph = AnatomyGraph.AnatomyGraph()

xmlGraph = XmlGraphFile.XmlGraphFile(graphFile, graph)

xmlGraph.addHeader()
xmlGraph.addStages()
xmlGraph.addGraph()
xmlGraph.close()

Util.statusMessage(["Graph file " + graphFile + " created."])

# Create the perspectives file.

perspectivesFile = _config["OUTPUT_DIRECTORY"] + "/" + "emapPerspectives.xml"
xmlPerspectives = XmlPerspectivesFile.XmlPerspectivesFile(perspectivesFile)
xmlPerspectives.addHeader()
xmlPerspectives.addPerspectives()
xmlPerspectives.close()

Util.statusMessage(["Perspectives file " + perspectivesFile + " created"])
Util.statusMessage(["Done"])

