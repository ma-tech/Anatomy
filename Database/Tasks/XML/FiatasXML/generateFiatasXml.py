#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Control program fro translating relational anatomy database definition to
a Fiatas formatted XML file

Usage
  generateFiatasXml.py _configFile_

  See the _config global variable below for the parameters that need
  to be defined in the configuration file.

Some high level details on Fiatas format is:
 1. One file per stage.
 2. It is very closely modelled on the EMAP XML files on the web site at:
    http://genex.hgu.mrc.ac.uk/Databases/Anatomy/new/
 3. There are differences in how the root node and synonyms are handled,
    and the files at the web site don't yet deal with Groups.
 4. Existing Fiatas formatted XML files are in CVS at:
    ee2/src/ee2/FIATAS/TS*.xml
"""

import sets
import sys
import xml.dom.Document                 # Main XML

from hgu import Util                    # error/warnings, common routines.

# Database access
from hgu.db import DbAccess             # Database access
from hgu.anatomyDb.version004 import AnaNodeDb
from hgu.anatomyDb.version004 import AnaNodeInPerspectiveDb
from hgu.anatomyDb.version004 import AnadPartOfDb
from hgu.anatomyDb.version004 import AnaStageDb
from hgu.anatomyDb.version004 import AnaSynonymDb
from hgu.anatomyDb.version004 import AnaTimedNodeDb
from hgu.anatomyDb.version004 import AnaVersionDb


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Items to exclude from the filter.

_FILTER_EXCLUDES = sets.Set([
    'EMAPA:28506', # developing capillary loop stage
    'EMAPA:28485', # maturing nephron
    'EMAPA:28500', # early tubule
    'EMAPA:28477', # primitive collecting duct group
    'EMAPA:28445', # stage IV immature nephron
    'EMAPA:17954'  # early nephron
    ])



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# List of expected configuration parameters

_config = {
    "DB_HOST":     None,
    "DB_USER":     None,
    "DB_DATABASE": None,
    "DB_PASSWORD": None,
    "DEBUGGING":   None,
    "GROUPS":      None,
    "OUTPUT_DIRECTORY": None
    }

_stageByOid      = {}
_stageBySequence = []



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _addHeaderToMainXml(mainXmlDoc, stageDbRec):
    """
    Add header elements to the main XML document for the given stage.

    Returns the root component element.
    """
    hguMrcEdinburgh = Util.addXmlElement(mainXmlDoc, "HGU_MRC_Edinburgh")
    versionDbRec = AnaVersionDb.getLatestVersion()
    Util.addXmlElement(hguMrcEdinburgh, "date", str(versionDbRec.getDate()))
    Util.addXmlElement(hguMrcEdinburgh, "species", "mouse")

    # The top level component is the whole organism and has different
    # sub elements from the rest of the components.
    rootNodeDbRec = AnaNodeDb.getRoot()
    timedNodeDbRec = AnaTimedNodeDb.getByNodeStage(
        rootNodeDbRec.getOid(), stageDbRec.getOid())
    rootComponent = Util.addXmlElement(hguMrcEdinburgh, "component")
    rootComponent.setAttribute("name", rootNodeDbRec.getComponentName())
    rootComponent.setAttribute(
        "id", Util.stripPublicIdPrefix(timedNodeDbRec.getPublicId()))
    stageElement = Util.addXmlElement(rootComponent, "stage")
    stageElement.setAttribute("name", stageDbRec.getName())

    return rootComponent



def _addHeaderToViewXml(viewXmlDoc, stageDbRec):
    """
    Add header elements to the main XML document for the given stage.
    """
    hguMrcEdinburgh = Util.addXmlElement(viewXmlDoc, "HGU_MRC_Edinburgh")
    versionDbRec = AnaVersionDb.getLatestVersion()
    Util.addXmlElement(hguMrcEdinburgh, "date", str(versionDbRec.getDate()))
    Util.addXmlElement(hguMrcEdinburgh, "species", "mouse")
    Util.addXmlElement(hguMrcEdinburgh, "stage", stageDbRec.getName())

    return hguMrcEdinburgh



def _thisPodsGroupStatusIsIncluded(pod, groupProcessing):
    """
    Return true if this node should be included based on its group status.
    """
    if groupProcessing == "EXPAND":
        include = True
    else:
        # exclude it if is a group or group descendent.
        include = False
        if pod.isPrimaryPath():
            node = AnaNodeDb.getByOid(pod.getNodeOid())
            if node.isPrimary():
                include = True
    return include



def _addFullViewToViewXml(headerElement, stageDbRec, groupProcessing):
    """
    Add the "full" view to the View XML document for current stage.

    The full view is a list of the immediate children of the root node.
    """

    # Add the full view.
    fullView = Util.addXmlElement(headerElement, "anatomy_view")
    Util.addXmlElement(fullView, "name", "full")
    fullComponentList = Util.addXmlElement(fullView, "component_list")

    # Add the top level children of the whole organism to the full view
    stageOid = stageDbRec.getOid()
    rootNodeDbRec = AnaNodeDb.getRoot()
    tnChildren = AnaTimedNodeDb.getPartOfChildrenOfNodeAtStage(
        rootNodeDbRec.getOid(), stageOid)

    for childDbRec in tnChildren:
        node = AnaNodeDb.getByOid(childDbRec.getNodeOid())
        if (node.isPrimary() or
            groupProcessing == "EXPAND"):
            viewChild = Util.addXmlElement(fullComponentList, "component")
            Util.addXmlElement(viewChild, "emapId", childDbRec.getPublicId())
            Util.addXmlElement(viewChild, "level", "-1")

    return fullView



def _addViewToViewXml(headerElement, stageDbRec, viewName, groupProcessing):
    """
    Add the specified view to the View XML document for the current stage.
    """

    # Add the View
    view = Util.addXmlElement(headerElement, "anatomy_view")
    Util.addXmlElement(view, "name", viewName)
    componentList = Util.addXmlElement(view, "component_list")

    # Add the timed nodes that are in the perspective definition.
    stageOid = stageDbRec.getOid()
    for nipDbRecord in AnaNodeInPerspectiveDb.Iterator(viewName):
        timedNode = AnaTimedNodeDb.getByNodeStage(nipDbRecord.getNodeOid(),
                                                  stageOid)
        if timedNode != None:
            # Node exists at this stage.
            node = AnaNodeDb.getByOid(timedNode.getNodeOid())
            if (node.isPrimary() or
                groupProcessing == "EXPAND"):
                viewChild = Util.addXmlElement(componentList, "component")
                Util.addXmlElement(viewChild, "emapId", timedNode.getPublicId())
                Util.addXmlElement(viewChild, "level", "-1")

    return componentList




def _createMainComponent(pod, stage, parentElement):
    """
    Create a component element in the Main XML document as a child of the given
    parent element.
    """
    # NOTE: Fiatas only cares about these attributes:
    #   component: name, id, isPrimary, synonym
    #
    # Everything else is ignored by Fiatas, and is included here only because
    # it is in the existing XML files that are fed to Fiatas.

    nodeOid = pod.getNodeOid()
    node = AnaNodeDb.getByOid(nodeOid)
    timedNode = AnaTimedNodeDb.getByNodeStage(nodeOid, stage.getOid())
    timedNodeStrippedId = Util.stripPublicIdPrefix(timedNode.getPublicId())

    # Get stage range where node exists
    nodeStartStage = _stageByOid[pod.getNodeStartStageOid()]
    nodeEndStage   = _stageByOid[pod.getNodeEndStageOid()]
    nodeStartStageNameStripped = nodeStartStage.getName()[2:]
    nodeEndStageNameStripped   = nodeEndStage.getName()[2:]

    # Get synonyms if any exist
    syns = AnaSynonymDb.getByOidSynonymIsFor(nodeOid)

    component = Util.addXmlElement(parentElement, "component")
    component.setAttribute("name", node.getComponentName())
    component.setAttribute("id", timedNodeStrippedId)
    component.setAttribute("isPrimary", Util.boolToJavaBool(node.isPrimary()))

    # to generate print name, remove root component from full path
    fullPath = pod.getFullPath()
    printName = fullPath[fullPath.index(".") + 1:]
    Util.addXmlElement(component, "printName", printName)
    Util.addXmlElement(component, "abbreviation")
    for syn in syns:
        Util.addXmlElement(component, "synonym", syn.getSynonym())
    Util.addXmlElement(component, "startEmbryoStage", nodeStartStageNameStripped)
    Util.addXmlElement(component, "stopEmbryoStage", nodeEndStageNameStripped)
    Util.addXmlElement(component, "parentId", parentElement.getAttribute("id"))

    Util.addXmlElement(parentElement, "childrenId", timedNodeStrippedId)

    return component



def _createViewComponent(pod, stage, parentElement):
    """
    Create a component element in the View XML document as a child of the given
    parent element.
    """
    nodeOid = pod.getNodeOid()
    timedNode = AnaTimedNodeDb.getByNodeStage(nodeOid, stage.getOid())

    component = Util.addXmlElement(parentElement, "component")
    Util.addXmlElement(component, "emapId", timedNode.getPublicId())
    Util.addXmlElement(component, "level", "-1")

    return component



def _createFilterComponent(pod, stage, parentElement):
    """
    Create a component element in the Filter XML document as a child of the given
    parent element.
    """
    nodeOid = pod.getNodeOid()
    node = AnaNodeDb.getByOid(nodeOid)
    component = None
    if (node.getComponentName() != "mouse" and
        node.getPublicId() not in _FILTER_EXCLUDES):
        timedNode = AnaTimedNodeDb.getByNodeStage(nodeOid, stage.getOid())
        if timedNode:
            # component exists at this stage
            component = Util.addXmlElement(parentElement, "Filter")
            publicId = timedNode.getPublicId()
            component.setAttribute("id", publicId[publicId.index(":")+1:])
            fullPath = pod.getFullPath()
            Util.addXmlElement(component, "printName",
                            fullPath[fullPath.index(".")+1:])

    return component



def _generateMainXmlFile(stage, outputDir, groupProcessing):
    """
    Walk the ANAD_PART_OF table from first to last record, using the tree
    structure that is built into it to create the TSxx.xml files.

    POD stack keeps track of the set of ancestors for the current POD.
    First component is special and is already added to XML by
    initialisation code.
    """
    mainXmlDoc = xml.dom.Document.Document(None)
    rootComponent = _addHeaderToMainXml(mainXmlDoc, stage)
    xmlStack = [rootComponent]

    podIter = AnadPartOfDb.SequenceIteratorForStage(stage)
    podStack = [podIter.next()]

    for pod in podIter:

        if _thisPodsGroupStatusIsIncluded(pod, groupProcessing):
            # Update the Part Of Derived stack based on depth.
            # if depth is shallower, then pop ancestor elements up to depth of
            # (possibly great) aunt/uncle element.
            podDepth = pod.getDepth()
            while len(podStack) > podDepth:
                podStack.pop()
                xmlStack.pop()

            podStack.append(pod)
            xmlStack.append(
                _createMainComponent(pod, stage, xmlStack[-1]))

    mainXmlFileName = (outputDir + "/" + stage.getName() + ".xml")
    Util.writeXmlFile(mainXmlFileName, mainXmlDoc)
    del mainXmlDoc                      # free up that memory

    return mainXmlFileName


def _generateViewXmlFile(stage, outputDir, groupProcessing):
    """
    Add elements to the view document if they occur in the perspective
    definition
    """
    viewXmlDoc = xml.dom.Document.Document(None)
    viewHeader = _addHeaderToViewXml(viewXmlDoc, stage)
    stageName = stage.getName()
    if stageName == "TS28":
        _addViewToViewXml(viewHeader, stage, "Adult Kidney (GenePaint)",
                          groupProcessing)
    _addViewToViewXml(viewHeader, stage, "Renal", groupProcessing)
    _addFullViewToViewXml(viewHeader, stage, groupProcessing)

    viewXmlFileName = (outputDir + "/" + stageName + "_View.xml")
    Util.writeXmlFile(viewXmlFileName, viewXmlDoc)
    del viewXmlDoc

    return viewXmlFileName


# def _generateFilterXmlFile(stage):
    """
    Add elements to the filter document if they occur in the perspective
    definition
    """
    """
    stageName = stage.getName()
    filterXmlDoc = xml.dom.Document.Document(None)
    filterHeader = Util.addXmlElement(filterXmlDoc, "Anatomy_Filter")
    filterHeader.setAttribute("stage", stageName)
    filterHeader.setAttribute("FullTree", stageName + ".xml")

    # The filter is everything in the Adult Kidney (GenePaint) view
    for pod in AnadPartOfDb.PerspectiveIterator("Adult Kidney (GenePaint)"):
        _createFilterComponent(pod, stage, filterHeader)

    filterXmlFileName = _config["OUTPUT_DIRECTORY"] + "/" + stageName + ".filter.xml"
    Util.writeXmlFile(filterXmlFileName, filterXmlDoc)
    del filterXmlDoc

    return filterXmlFileName
    """


def __generateAndWriteXmlStageTrees(outputDir, groupProcessing):
    """
    Generate and write XML trees for each stage.

    Writes out one stage's XML files before starting on the next one.
    Fiatas requires 2 XML files and supports an optional 3rd XML file for
    each stage:
     o TSxx.xml      - Fully indented XML document describing anatomy as a tree.
     o TSxx_View.xml - Subsets of anatomy, i.e., perspectives.  This is a linear
                       list.
     o TSxx.filter.xml - A single subset of full anatomy.  If this exists, then
                         the views presented to the user will be all anatomy between
                         the nodes in the view definition and the nodes in the filter
                         definition, inlcusive.
    This code does *not* create filter files.
    """

    for stage in _stageBySequence:

        Util.statusMessage(["Generating stage " + stage.getName()])

        _generateMainXmlFile(stage, outputDir, groupProcessing)
        _generateViewXmlFile(stage, outputDir, groupProcessing)
        # _generateFilterXmlFile(stage)

    return None


def __initialise(configParams):
    """
    Initialise all the background including the database connection.
    """

    if configParams["DEBUGGING"] == "ON":
        Util.setDebugging(True)
    else:
        Util.setDebugging(False)

    # Initialise Database
    DbAccess.initialise(
        dbHost = configParams["DB_HOST"],
        dbName = configParams["DB_DATABASE"],
        dbUser = configParams["DB_USER"],
        dbPass = configParams["DB_PASSWORD"],
        charset= "latin1")



    # Read in stages to avoid constantly looking them up.
    for stage in AnaStageDb.SequenceIterator():
        stgOid = stage.getOid()
        _stageByOid[stgOid] = stage
        _stageBySequence.append(stage)

    return None





# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------


# There are (at least) two obvious high-level ways to generate the
# XML files.
#  1. Walk the tree structure, generating XML as we go.  This requires
#     us to reproduce the code that produces the ANAD_PART_OF table.
#     This approach is inherently recursive.
#  2. Walk the ANAD_PART_OF table, from start to finish, generating XML
#     as we go.  This takes advantage of the fact that ANAD_PART_OF
#     already has the tree built.  This approach is inherently non-recursive.
#
# Another choice to make is
#  1. Do we build the XML files one after the other, or
#  2. Do we build the XML files in parallel.
#
# The first attempt at this used options (2,2) and ran out of memory.
# The second attempt uses options (2,1), and did not run out of memory.


Util.readConfiguration(sys.argv[1], _config)

__initialise(_config)

__generateAndWriteXmlStageTrees(_config["OUTPUT_DIRECTORY"],
                                _config["GROUPS"])

sys.exit(0)

