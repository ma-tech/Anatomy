#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************


Script to parse in an OBO file and produce an antomy database from it.

HOWEVER, I NEVER FINISHED THIS PROGRAM.  THERE WERE JUST TOO MANY
DIFFFERENCES BETWEEN THE XENOPUS VIEW OF THE WORLD AND THE MOUSE
VIEW, THAT I DECIDED TO DEFER THE TASK OF CONVERTING FROM AN OBO FILE TO A
MOUSE ATLAS STYLE ANATOMY DATABASE TO YOU.

See the README for more.

Usage:
   xenopusOboToAnatomy.py _configFile_

"""
import re
import sys

from hgu import Util

from hgu.anatomyDb.version004 import Anatomy # Read in anatomy DB.
from hgu.anatomyDb.version004 import Nodes
from hgu.anatomyDb.version004 import Relationships
from hgu.anatomyDb.version004 import Stages
from hgu.anatomyDb.version004 import TimedNodes
from hgu.anatomyDb.version004 import Versions

import OboEntry
import OboOntology



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# List of expected configuration parameters
DB_HOST                  = "DB_HOST"
DB_USER                  = "DB_USER"
DB_DATABASE              = "DB_DATABASE"
DB_PASSWORD              = "DB_PASSWORD"
OBO_FILE                 = "OBO_FILE"
STAGE_NAMESPACE          = "STAGE_NAMESPACE"
ROOT_ID                  = "ROOT_ID"
VERSION_COMMENTS         = "VERSION_COMMENTS"
SPECIES_NAME             = "SPECIES_NAME"
OUTPUT_FILEPATH          = "OUTPUT_FILEPATH"
DEBUGGING                = "DEBUGGING"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_config = {
    DB_HOST:          None,
    DB_USER:          None,
    DB_DATABASE:      None,
    DB_PASSWORD:      None,
    OBO_FILE:         None,
    STAGE_NAMESPACE:  None,
    ROOT_ID:          None,
    VERSION_COMMENTS: None,
    SPECIES_NAME:     None,
    OUTPUT_FILEPATH:  None,
    DEBUGGING:        None
    }


# ------------------------------------------------------------------
# LOCAL ROUTINES
# ------------------------------------------------------------------

def _xenopusStageCmp(xenStage1, xenStage2):
    """
    Sort 2 xenopus stages in chrnonological order.
    """
    # This takes advantage of the numerical naming of xenopus stages.
    # Everything starts with "NF stage " followed by a single number
    # or a pair of numbers seperated with a hyphen.

    stg1Name = xenStage1.getName()
    stg2Name = xenStage2.getName()
    if stg1Name == "death":
        sortVal = +1 # Death always comes last.
    elif stg2Name == "death":
        sortVal = -1
    elif stg1Name == "juvenile":
        sortVal = +1
    elif stg2Name == "juvenile":
        sortVal = -1
    else:
        stg1Num = float(re.match("NF stage (\d+(\.\d*)?)", stg1Name).group(1))
        stg2Num = float(re.match("NF stage (\d+(\.\d*)?)", stg2Name).group(1))

        if stg1Num == 2 and stg2Num == 2:
            if stg1Name == "NF stage 2 minus":
                sortVal = -1
            elif stg2Name == "NF stage 2 minus":
                sortVal = +1
            else:
                sortVal = 0
        elif stg1Num > stg2Num:
            sortVal = +1
        elif stg1Num < stg2Num:
            sortVal = -1
        else:
            sortVal = 0

    return sortVal



def _buildStages():
    """
    Build the stage records based on what is in the OBO ontology.
    """

    # Stage definitions are idiosynchratic per species.  OBO format
    # does not support directly specifying relative order of stages
    # so we have to tease it out.

    stageList = []
    if _config[SPECIES_NAME] == "Xenopus laevis":
        # Xenopus has a hierarchical set of stage definitions.  Get
        # only those stages that exist at bottom of hierarchy.
        for xenStage in OboOntology.NamespaceIterator(_config[STAGE_NAMESPACE]):
            if xenStage.getType() == OboEntry.TERM:
                if len(xenStage.getSubjectIdsInRelsWhereObject(
                                                           OboEntry.IS_A)) == 0:
                    stageList.append(xenStage)

        # Now have leaf stages, sort them.
        stageList.sort(_xenopusStageCmp)

    else:
        Util.fatalError([
            "Need to add code to process stage definitions for species " +
            _config[SPECIES_NAME]
            ])

    for seq, stg in enumerate(stageList):
        # Generate stage records
        Stages.createStage(
            species = _config[SPECIES_NAME],
            name = stg.getName(),
            sequence = seq,
            description = stg.getDef(),
            publicId = stg.getId())

    return



def _buildNodes():
    """
    Build node records based on what is in the OBO ontology.
    """
    header = OboOntology.getHeader()

    # Assumes that nodes are defined under the default namespace, which is
    # true for Xenopus, but maybe not others.
    for term in OboOntology.NamespaceIterator(header.getDefaultNamespace()):
        if term.getType() == OboEntry.TERM:
            Nodes.createNode(
                species = _config[SPECIES_NAME],
                componentName = term.getName(),
                isGroup = False,  # :TODO: Does primary mean anything in Xenopus?
                publicId = term.getId(),
                description = term.getDef()
                )

    return


def _buildPartOfRelationships():
    """
    Build part-of relationships based on what is in the OBO ontology.
    """
    header = OboOntology.getHeader()
    rootNodeOid = Nodes.getByPublicId(_config[ROOT_ID]).getOid()

    # Generate them relationships.
    for term in OboOntology.NamespaceIterator(header.getDefaultNamespace()):
        if term.getType() == OboEntry.TERM:
            childNode = Nodes.getByPublicId(term.getId())
            childOid = childNode.getOid()
            parents = term.getObjectIdsInRelsWhereSubject(OboEntry.PART_OF)
            for parentPublicId in parents:
                parentNode = Nodes.getByPublicId(parentPublicId)
                Relationships.createRelationship(
                    relationshipType = Relationships.PART_OF,
                    childOid = childOid,
                    parentOid = parentNode.getOid()
                    )
            # Special case for xenopus:
            # Many terms have no part of parents.  Tie them to root.
            if (_config[SPECIES_NAME] == "Xenopus laevis" and
                len(parents) == 0 and
                term.getId() != _config[ROOT_ID]):
                Util.warning([
                    "Term ('" + term.getId() + ", '" + term.getName() + "') ",
                    "does not have a part of parent term.  Assigning to root."])
                Relationships.createRelationship(
                    relationshipType = Relationships.PART_OF,
                    childOid = childOid,
                    parentOid = rootNodeOid)


    # Root of the ontology may have chagned.  Update it.
    # Originally wrote checkForNewRoot to do this automatically, but
    # in xenopus there are 200+ terms without a parent part of.
    Nodes.checkForNewRoot()
    Nodes.setRootByPublicId(_config[ROOT_ID])

    return


def _buildTimedNodes():
    """
    Build timed nodes based on information in the OBO ontology.
    """
    # OBO Edit does not require start-stop stages for everthing.
    # For each term:
    #   if it has a start stage use it
    #   if it has a stop stage use it
    #   while you don't have a complete start:stop pair:
    #     get parent.
    #     get missing start and/or stop from it
    #   If you get to top of tree, use species min and max stages.

    minStage = Stages.getEarliest()
    maxStage = Stages.getLatest()

    # collect start and stop stages for each term.  Not all terms have
    # these specified
    stageWindows = OboOntology.getAllStageWindows()

    for entryId, (oboStartId, oboStopId) in stageWindows.iteritems():
        nodePublicId = entryId
        node = Nodes.getByPublicId(nodePublicId)
        startId = oboStartId
        stopId  = oboStopId
        while ((startId == None or stopId == None) and
               nodePublicId != _config[ROOT_ID]):
            # Get part-of Parent.  Get this from anatomy DB instead of
            # from OBO because we have forced every item in the
            # anatomy DB to have a part-of parent, while this is
            # not true in the OBO ontology.
            parents = Nodes.getPartOfParentsForNode(node)
            if len(parents) == 1:
                node = list(parents)[0]
                nodePublicId = node.getPublicId()
                if startId == None:
                    startId = stageWindows[nodePublicId][0]
                if stopId == None:
                    stopId = stageWindows[nodePublicId][1]
            elif len(parents) > 1:
                # haven't written code to cope with this yet and don't
                # expect it in Xenopus.
                Util.fatalError([
                    "Term ('" + node.getPublicId() + ", '" +
                    node.getComponentName() + "') ",
                    "has more than one part-of parent.  Code can't cope " +
                    "with that yet."])
            elif len(parents) == 0 and nodePublicId != _config[ROOT_ID]:
                Util.fatalError([
                    "Term ('" + node.getPublicId() + ", '" +
                    node.getComponentName() + "') ",
                    "has no " + Relationships.PART_OF + " parent."])

        if startId == None:
            startStage = minStage
        else:
            startStage = Stages.getByPublicId(startId)
        if stopId == None:
            stopStage = maxStage
        else:
            stopStage = Stages.getByPublicId(stopId)

        # Build timed nodes from start to stop stage.
        node = Nodes.getByPublicId(entryId)
        nodeOid = node.getOid()
        for stage in Stages.SequenceIterator(startStage.getSequence(),
                                             stopStage.getSequence() + 1):
            # How to set the public ID for the timed node depends on the
            # species.
            if _config[SPECIES_NAME] == "Xenopus laevis":
                publicId = entryId + "." + stage.getName()
            else:
                Util.fatalError([
                    "Code needs to be modifed to handle timed nodes for " +
                    _config[SPECIES_NAME] + "."])

            TimedNodes.createTimedNode(nodeOid = node.getOid(),
                                       stageOid = stage.getOid(),
                                       publicId = publicId)
    return



def __buildAnatomyDatabase():
    """
    Create a copy of an anatomy database from the ontology.
    """
    oboHeader = OboOntology.getHeader()
    Versions.createNextVersion(oboHeader.getDate(), _config[VERSION_COMMENTS])

    _buildStages()
    _buildNodes()
    _buildPartOfRelationships()
    _buildTimedNodes()

    Anatomy.generateDerivedData()

    return




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

configFile = sys.argv[1]
Util.readConfiguration(configFile, _config, printConfig=True)

# read that ontology in
OboOntology.buildOntologyFromFile(_config[OBO_FILE])
#OboOntology.spew("Obo")

# Read the existing DB in
Anatomy.initialise(dbHost = _config[DB_HOST], dbName = _config[DB_DATABASE],
                   dbUser = _config[DB_USER], dbPass = _config[DB_PASSWORD],
                   outputFilePath = _config[OUTPUT_FILEPATH])

# build an anatomy database from it.
__buildAnatomyDatabase()



