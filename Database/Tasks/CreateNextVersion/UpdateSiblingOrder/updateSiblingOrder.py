#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************

Control program for updating the display order of sibling components
in the anatomy database.

Usage:
  ./updateSiblingOrder.py _configFile_

  See the _config global variable below for the parameters that need
  to be defined in the configuration file.
"""

import sets
import sys

from hgu import Util
from hgu.anatomyDb.version004 import Anatomy
from hgu.anatomyDb.version004 import Nodes
from hgu.anatomyDb.version004 import Relationships
from hgu.anatomyDb.version004 import Versions

import SiblingOrderStream


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
    "OUTPUT_FILE":      None,
    "SIBLING_ORDER_SPEC": None,
    "EXCLUDES":         None,
    "DEBUGGING":        None
    }


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def __initialise(configFile, configParams):
    """
    Initialise program.  This includes establishing the database
    connection, and setting debugging.
    """
    Util.readConfiguration(configFile, configParams, printConfig = True)

    if configParams["DEBUGGING"] == "ON":
        Util.setDebugging(True)
    else:
        Util.setDebugging(False)

    # EXCLUDES parameter is a space separated list of EMAPA IDs.  Do not
    # update the sibling ordering of these items.  Convert the space
    # separated list to a python set.
    configParams["EXCLUDES"] = sets.Set(configParams["EXCLUDES"].split())

    # Initialise Database and read the whole thing in.
    Anatomy.initialise(
        dbHost = configParams["DB_HOST"],
        dbName = configParams["DB_DATABASE"],
        dbUser = configParams["DB_USER"],
        dbPass = configParams["DB_PASSWORD"],
        outputFilePath = configParams["OUTPUT_FILE"],
        charset = "latin1")

    return None



def _checkVersion(siblingOrderStream):
    """
    Check the version of the database in the sibling order file against
    the current version of the database.  If they disagree, then issue
    a warning.
    """
    version = Versions.getLatest()
    if (siblingOrderStream.getVersionNumber() != version.getNumber() or
        siblingOrderStream.getDateTime()      != version.getDate()):
        Util.warning([
            "Version program is creating and version in relationship sequence " +
            "file disagree.",
            "File Version: " + str(siblingOrderStream.getVersionNumber()) + " " +
            str(siblingOrderStream.getDateTime()),
            "Prgm Version: " + str(version.getNumber()) + " " +
            str(version.getDate())])

    return None



def _getByParentChildPublicIdsRelType(parentPublicId, childPublicId, relType):
    """
    Return relationship with this type, this child and this parent.
    """
    parentNode = Nodes.getByPublicId(parentPublicId)
    childNode  = Nodes.getByPublicId(childPublicId)

    return Relationships.getByParentChildOidsRelType(
                         parentNode.getOid(), childNode.getOid(), relType)




def __readSiblingOrderFromFile(siblingOrderFile, excludeSet):
    """
    Read in the sequence the tree is supposed to be displayed in.

    siblinOrderFile: Name of file containing sequence information.
    excludeSet: Set of EMAPA ids to ignore.  That is, the ordering for
    these items will not be set.
    """
    # Open file and read in header.
    siblingOrderStream = SiblingOrderStream.SiblingOrderStream(siblingOrderFile)
    _checkVersion(siblingOrderStream)

    # For each depth keep track of current component stack
    componentStack = []
    previousDepth = -1
    processedRelationships = sets.Set()
    parentCounts = {}

    componentLine = siblingOrderStream.getNextComponent()
    while componentLine:
        # Update stack
        depth = componentLine.getDepth()
        if depth == previousDepth:
            # new component is a sibling of previous component
            componentStack[depth] = componentLine
        elif depth < previousDepth:
            # new component is a great uncle of previous component
            componentStack = componentStack[0:depth+1]
            componentStack[depth] = componentLine
        elif depth == previousDepth + 1:
            # new component is a child of previous component
            componentStack.append(componentLine)
        else:
            Util.fatalError([
                "Depth increased by more than one.  Previous depth: " +
                str(previousDepth) + "  Current depth: " + str(depth),
                "Previous line: ", componentStack[previousDepth].getLine(),
                "Current line:  ", componentLine.getLine()])

        if depth > 0:
            publicId = componentLine.getPublicId()
            name = componentLine.getName()
            parentPublicId = componentStack[depth-1].getPublicId()
            parentName = componentStack[depth-1].getName()
            rel = _getByParentChildPublicIdsRelType(
                componentStack[depth-1].getPublicId(), publicId,
                Relationships.PART_OF)
            if not rel:
                Util.warning([
                    "Cut and paste error.  " +
                    "Component placed under a component which is not its parent.",
                    "Parent " + parentPublicId + ", '" + parentName +"'",
                    "Child  " + publicId + ", '" + name + "'",
                    "Ignoring component."])
            elif rel in processedRelationships:
                if siblingOrderStream.inPrimarySection():
                    Util.warning([
                        "Parent child relationship between ",
                        "Parent " + parentPublicId + ", '" + parentName +"'",
                        "Child  " + publicId + ", '" + name + "'",
                        "occurs more than once in non-group part of "
                        "relationship sequence file.",
                        "Ignoring later occurrence."])
                    # Don't report dups from group section b/c relationships
                    # can occur many times there and we don't want to force
                    # editors to reorder all of them, just the first one.
            elif publicId in excludeSet:
                Util.statusMessage([
                    "NOT setting ordering information for relationsip between",
                    "Parent " + parentPublicId + ", '" + parentName +"'",
                    "Child  " + publicId + ", '" + name + "'",
                    "because child is in EXCLUDE list in configuration file."])
            else:
                # We have an ordering for this child.
                if parentPublicId not in parentCounts:
                    parentCounts[parentPublicId] = -1
                parentCounts[parentPublicId] += 1
                if rel.getSequence() != parentCounts[parentPublicId]:
                    # And the ordering is different from what it was.
                    rel.setSequence(parentCounts[parentPublicId])
                    rel.update()
                processedRelationships.add(rel)

        previousDepth = depth
        componentLine = siblingOrderStream.getNextComponent()

    return None



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Generate reports for each stage, and then for the abstract mouse as well.
# Each report goes in a separate file.

__initialise(sys.argv[1], _config)

__readSiblingOrderFromFile(_config["SIBLING_ORDER_SPEC"],
                           _config["EXCLUDES"])

print "Done"

sys.exit(0)
