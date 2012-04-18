#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Defines EMAP Anatomy XML files.  This presents the EMAP anatomy as a
DAG, which makes it easier to reason with than a tree presentation would be,
but makes it harder to present.
"""

import xml.dom.Document


from hgu import Util

from hgu.anatomyDb.version006 import AnatomyGraph
from hgu.anatomyDb.version006 import Nodes
from hgu.anatomyDb.version006 import Relationships
from hgu.anatomyDb.version006 import Stages
from hgu.anatomyDb.version006 import Synonyms
from hgu.anatomyDb.version006 import TimedNodes
from hgu.anatomyDb.version006 import Versions

from hgu.anatomyDb.version006 import AnaRelationshipProjectDb

# ------------------------------------------------------------------
# ANATOMY XML GRAPH FILE
# ------------------------------------------------------------------

class XmlGraphFile:
    """
    Defines Anatomy Graph XML files.  This borrows heavily from the
    XmlReportFile class in the the tree reporting code.
    """

    def __init__(self, filePath, graph, project):
        """
        Initialise an anatomy XML Graph file.

        Note this is not actually a file until it is written out.
        """

        self.__graph = graph
        self.__filePath = filePath
        self.__project = project
        self.__document = xml.dom.Document.Document(None)
        self.__anatomy = Util.addXmlElement(self.__document, "emapAnatomy")

        return None


    def close(self):
        """
        Close an XML graph file.
        """
        Util.writeXmlFile(self.__filePath, self.__document)
        self.__document = None
        self.__anatomy = None
        return



    def addHeader(self):
        """
        Add header information to XML graph docuemnt.  This includes
        things like species and anatomy database version.
        """

        Util.addXmlElement(self.__anatomy, "species", "mouse")

        version = Versions.getLatest()
        versionElement = Util.addXmlElement(self.__anatomy,
                                            "anatomyDatabaseVersion")
        Util.addXmlElement(versionElement, "number", str(version.getNumber()))
        Util.addXmlElement(versionElement, "date", str(version.getDate()))

        return


    def addStages(self):
        """
        List the defined stages.
        """
        for stage in Stages.SequenceIterator():
            stageElement = Util.addXmlElement(self.__anatomy, "stage")
            Util.addXmlElement(stageElement, "name", stage.getName())
            Util.addXmlElement(stageElement, "sequence", str(stage.getSequence()))
            Util.addXmlElement(stageElement, "description", stage.getDescription())
            Util.addXmlElement(stageElement, "shortExtraText",
                               stage.getShortExtraText())

        return



    def addGraph(self):
        """
        Add the anatomy graph to the document.
        """
        for graphNode in AnatomyGraph.BreadthFirstIterator(self.__graph):
            node = graphNode.getNode()
            nodeOid = node.getOid()
            componentElement = Util.addXmlElement(self.__anatomy, "component")
            componentElement.setAttribute("id", node.getPublicId())
            Util.addXmlElement(componentElement, "name", node.getComponentName())
            Util.addXmlElement(componentElement, "isGroup",
                               Util.boolToJavaBool(node.isGroup()))

            # Add parents
            for rel in Relationships.getByChildOidRelType(nodeOid,
                                                          Relationships.PART_OF):
                parent = Nodes.getByOid(rel.getParentOid())
                partOfElement = Util.addXmlElement(componentElement,
                                                   "partOfComponent")
                Util.addXmlElement(partOfElement, "parentId",
                                   parent.getPublicId())

                relProj = AnaRelationshipProjectDb.getByRelationShipFkAndProject(rel.getOid(), self.__project)
                seq = relProj.getSequence()

                #childSequence = rel.getSequence()
                childSequence = relProj.getSequence()
                if childSequence != None:
                    Util.addXmlElement(partOfElement, "childSequence",
                                       str(relProj.getSequence()))

            # Add Timed Components
            for timedNode in TimedNodes.getByNodeOidInStageOrder(nodeOid):
                timedComponent = Util.addXmlElement(componentElement,
                                                    "timedComponent")
                timedComponent.setAttribute("id", timedNode.getPublicId())
                Util.addXmlElement(
                    timedComponent, "stageName",
                    Stages.getByOid(timedNode.getStageOid()).getName())

            # Add Synonyms
            for syn in Synonyms.getSynonymsForOid(nodeOid):
                Util.addXmlElement(componentElement, "componentSynonym",
                                   syn.getSynonym())

        return

