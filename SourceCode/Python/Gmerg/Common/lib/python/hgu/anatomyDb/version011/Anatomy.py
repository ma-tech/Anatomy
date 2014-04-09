#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to the relational anatomy database
used by the GMERG projects.

This module reads the entire anatomy database into memory on initialisation.
If this is not what you want then please use some combination of the lower
level routines:
  DbAccess: establish a connection to the DB.
  Nodes, Stages, PartOfs, etc.: High-level table modules that read in all
        records in a given table.  This module uses this, but you can also
        use them directly if you don't want the whole DB read in.
  AnaNodeDb, AnaStageDb, AnadPartOfDb, etc.: Table modules that provide low
        level record-at-a-time access to the named table.  These modules don't
        cache anything in memory.  Every new request results in an access to
        the database.

However, if you want to think the least about the anatomy database then use
this module to read the whole database, and then the high-level table modules
to access the data you need.
"""

from hgu.db import DbAccess

# High level modules for base tables
import Nodes
import Oids
import Perspectives
import PerspectiveAmbits
import Relationships
import Stages
import Synonyms
import TimedNodes
import Versions

# High level modules for derived tables.
import RelationshipsTransitive
import PartOfs
import PartOfPerspectives


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise(sortProject=None, 
               dbHost=None, dbName=None, dbUser=None, dbPass=None,
               outputDir=None, outputFilePath=None, charset=None):
    """
    Initialise DB connection and read the whole darn anatomy database into
    memory.
    """

    if dbHost != None:
        # Only create connection if connection params provided.
        # If not provided then we assume we already have an open
        # connection.
        DbAccess.initialise(
            dbHost = dbHost, dbName = dbName, dbUser = dbUser, dbPass = dbPass,
            outputDir = outputDir, outputFilePath = outputFilePath,
            charset = charset)

    # Read in every table we care about.
    # Base tables
    Oids.initialise()
    Versions.initialise()
    Stages.initialise()
    Nodes.initialise()
    TimedNodes.initialise()
    Relationships.initialise(sortProject)
    Synonyms.initialise()
    Perspectives.initialise()
    PerspectiveAmbits.initialise()

    # Derived tables
    RelationshipsTransitive.initialise()
    PartOfs.initialise()
    PartOfPerspectives.initialise()

    connectTheDots()

    return None


def connectTheDots():
    """
    Called after all the anatomy has been read into memory to connect
    it all together.

    NOTE: This implementation is a hack.  It creates additional structures
    in the high level modules, rather than directly modifies the individual
    objects.

    For each node, create:
        list of all its parent nodes
        list of all its child nodes
        list of all its timed nodes, by stage

    For each timed node, create:
        list of all its parent timed nodes
        list of all its child timed nodes

    """
    Nodes.connectTheDots()
    TimedNodes.connectTheDots()

    return


def generateDerivedData():
    """
    Regenerates all derived data in the anatomy database.

    This will (generate code to) empty and repopulate all derived tables.
    """
    # Empty all derivied tables, in reverse order that they are populated.
    #PartOfPerspectives.deleteAll()
    #PartOfs.deleteAll()
    #RelationshipsTransitive.deleteAll()

    # Derive the transitive closure of the relationship graph
    RelationshipsTransitive.regenerateTable()

    # Derive the quick tree display
    PartOfs.regenerateTable()

    # Derive the part of perspective tree for each perspective
    PartOfPerspectives.regenerateTable()

    return



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
