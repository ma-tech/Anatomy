#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Defines Anatomy Perspectives XML file.
"""

import xml.dom.Document


from hgu import Util

from hgu.anatomyDb.version006 import Nodes
from hgu.anatomyDb.version006 import Perspectives
from hgu.anatomyDb.version006 import Versions


# ------------------------------------------------------------------
# ANATOMY PERSPECTIVES XML FILE
# ------------------------------------------------------------------

class XmlPerspectivesFile:
    """
    Defines Anatomy Perspective XML files.  These contain the minimal
    definitions of all the perspectives defined in the anatomy database.
    """

    def __init__(self, filePath):
        """
        Initialise an anatomy perspectives XML file.

        Note this is not actually a file until it is written out.
        """

        self.__filePath = filePath
        self.__document = xml.dom.Document.Document(None)
        self.__perspectives = Util.addXmlElement(self.__document,
                                                 "anatomyPerspectives")

        return None


    def close(self):
        """
        Close an anatomy perspectives XML file.
        """
        Util.writeXmlFile(self.__filePath, self.__document)
        self.__document = None
        self.__perspectives = None
        return



    def addHeader(self):
        """
        Add header information to docuemnt.  This includes
        things like species and anatomy database version.
        """

        Util.addXmlElement(self.__perspectives, "species", "mouse")

        version = Versions.getLatest()
        versionElement = Util.addXmlElement(self.__perspectives,
                                            "anatomyDatabaseVersion")
        Util.addXmlElement(versionElement, "number", str(version.getNumber()))
        Util.addXmlElement(versionElement, "date", str(version.getDate()))

        return


    def addPerspectives(self):
        """
        List all the defined perspectives.
        """
        for perspective in Perspectives.Iterator():
            perspectiveElement = Util.addXmlElement(self.__perspectives,
                                                    "perspective")
            perspectiveElement.setAttribute("name", perspective.getName())
            ambit = perspective.getAmbit()
            for nodeOid, ambitRec in ambit.iteritems():
                node = Nodes.getByOid(nodeOid)
                ambitNodeElement = Util.addXmlElement(perspectiveElement,
                                                      "ambitComponent")
                Util.addXmlElement(ambitNodeElement, "componentId",
                                   node.getPublicId())
                Util.addXmlElement(ambitNodeElement, "isStart",
                                   Util.boolToJavaBool(ambitRec.isStart()))
                Util.addXmlElement(ambitNodeElement, "isStop",
                                   Util.boolToJavaBool(ambitRec.isStop()))

        return

