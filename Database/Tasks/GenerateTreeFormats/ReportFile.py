# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Tree Report File module.  Maps a Report Tree object to a specific
format in a file.
"""

import PyRTF.Elements                            # Rich Text Format files
import unicodedata
import xml.dom.Document


from hgu import Util

from hgu.anatomyDb.version006 import AnaTimedNodeDb
from hgu.anatomyDb.version006 import Stages

import ReportTree


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# SUPPORTED FORMATS ------------------------

PLAIN_TEXT = "PLAIN_TEXT"
RICH_TEXT  = "RICH_TEXT"
XML        = "XML"
JSON       = "JSON"

# Define a low-budget lookup to hold facts about each format

FORMAT_EXTENSION = 0
FORMAT_SUBDIRECTORY = 1

FORMATS = {
    PLAIN_TEXT: ["txt", "Text"],
    RICH_TEXT:  ["rtf", "RTF"],
    XML:        ["xml", "XML"],
    JSON:       ["json", "JSON"]
    }



# REPORT TYPES ------------------------------

GROUPS_EMBEDDED = "GROUPS_EMBEDDED"
GROUPS_TRAILING = "GROUPS_TRAILING"

# Define a low budget lookup to hold facts about each report type

REPORT_TYPE_NAME = 0

REPORT_TYPES = {
    GROUPS_EMBEDDED: ["GroupsEmbedded"],
    GROUPS_TRAILING: ["GroupsTrailing"]
    }


# FORMAT TYPES ------------------------------

CONTENT          = "CONTENT"
STRUCTURE        = "STRUCTURE"
CONTENTSTRUCTURE = "CONTENTSTRUCTURE"

# Define a low budget lookup to hold facts about each report format

FORMAT_TYPE_NAME = 0

FORMAT_TYPES = {
    CONTENT:          ["Content"],
    STRUCTURE:        ["Structure"],
    CONTENTSTRUCTURE: ["ContentStructure"]
    }


# STAGE FILES TOGETHERNESS ------------------

CONCATENATED = "CONCATENATED"
SEPARATE     = "SEPARATE"


# RTF Unicode Indent symbols

L = unicodedata.lookup("BOX DRAWINGS LIGHT UP AND RIGHT")
T = unicodedata.lookup("BOX DRAWINGS LIGHT VERTICAL AND RIGHT")
PIPE = unicodedata.lookup("BOX DRAWINGS LIGHT VERTICAL")
EMPTY = " "

# RTF Unicode Group Symbols

RTF_DIRECT_DESCENDANT = ">"
RTF_INDIRECT_DESCENDANT = unicodedata.lookup(
    "RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK")

RTF_UNICODE_INDENT_SYMBOLS = {
    ReportTree.L:     L,
    ReportTree.T:     T,
    ReportTree.PIPE:  PIPE,
    ReportTree.EMPTY: EMPTY
    }



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_prev_indent = 0
_prev_depth = 0
_count = 0
_depth = 0
_second_root = False

# ------------------------------------------------------------------
# REPORT FILE
# ------------------------------------------------------------------

class ReportFile:
    """
    This is an abstract class that serves as a skeleton for other classes
    that implement it.
    """

    def __init__(self, filePath, reportTree):
        """
        Open a report file.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.            terminator = "},"

        """
        self.GROUP_DISPLAY = None
        self._reportTree = None
        self._document = None
        Util.methodNotOverriddenError()


    def closeReportFile(self, formatType):
        """
        Close a report file.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()


    def getReportTree(self):
        """
        Return the report tree this file is for.
        """
        return self._reportTree



    def getDocument(self):
        """
        Get the document that is being written to.  Depending on report
        type, this may be an actual file, or just a memory structure
        that will become a file.
        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        return self._document



    # Generic Methods to add generic types of things.

    def addPageBreak(self):
        """
        Add a page break.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()


    def addTextLine(self, textLine):
        """
        Add a normal line of text.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()


    def addAbstractAnatomy(self, indentLines, formatType):
        """
        Add the given ordered list on anatomy items to the report.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        global _prev_indent, _prev_depth, _depth
        _prev_indent = 0
        _prev_depth = 0
        _depth = 0

        for indentLine in indentLines:
            self.addAbstractAnatomyLine(indentLine, formatType)


    def addAbstractAnatomyLine(self, anatomyLine, formatType):
        """
        Add an anatomy line in abstract format.  This includes the EMAPA id and
        the stage range.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()


    def addStageAnatomy(self, stage, indentLines, formatType):
        """
        Add the given ordered list on anatomy items to the report.
        """
        global _prev_indent, _prev_depth, _depth
        _prev_indent = 0
        _prev_depth = 0
        _depth = 0
        _second_root = False

        #print 'STAGE = ' + stage.getName()
        #print '====='
        
        for indentLine in indentLines:
            self.addStageAnatomyLine(stage, indentLine, formatType)



    def addStageAnatomyLine(self, stage, indentLine, formatType):
        """
        Add this line in a single stage format.  This includes the EMAP id but
        not the stage name.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    # Methods to add specific parts of report, using generic methods above.

    def addReportHeader(self):
        """
        Add the report header that is common to all reports.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    def addExcludingGroupsHeader(self, prefix = None):
        """
        Add header for part of file that lists anatomy without groups.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    def addOnlyGroupsHeader(self, prefix = None):
        """
        Add header for part of file that lists only anatomy in groups.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    def addGroupKey(self, formatType):
        """
        Adds the group symbol expanation to the report.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    def addStageHeader(self, stage):
        """
        Add header for this specific stage.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()




# ------------------------------------------------------------------
# LINEAR REPORT FILE
# ------------------------------------------------------------------

class LinearReportFile (ReportFile):
    """
    Abstract class covering reports that have a linear, as distinct from nested,
    structure.  Plain text files are an example of a linear structure.

    This is an abstract class that serves as a skeleton for other classes
    that implement it.
    """

    def __init__(self, filePath, reportTree):
        """
        Open a linear report file.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    # Define methods that are unique to LinearReportFiles, and must be
    # overridden by implementing subclasses.


    def addH1(self, textLine):
        """
        Add a very large header line.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    def addH2(self, textLine):
        """
        Add a large header line.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    def addH3(self, textLine):
        """
        Add a normal header line.

        This method must be overriden by a derived class.  Raises exception
        if it is ever called.
        """
        Util.methodNotOverriddenError()



    # Define methods that are shared by all subclasses.

    def addReportHeader(self):
        """
        Add the report header that is common to all reports.
        """

        version = self.getReportTree().getVersion()
        self.addH1(
            "Anatomy Database Version " +
            str(version.getNumber()) + ", " +
            str(version.getDate()))
        self.addH2(self.getReportTree().getPerspectiveName() + " Anatomy")

        return



    def addExcludingGroupsHeader(self, prefix = None):
        """
        Add header for part of file that lists anatomy without groups.
        """
        if prefix == None:
            prefix = ""
        else:
            prefix += " "

        self.addH3(
            prefix +
            self.getReportTree().getPerspectiveName() + " Excluding Groups")

        return



    def addOnlyGroupsHeader(self, prefix = None):
        """
        Add header for part of file that lists only anatomy in groups.
        """
        if prefix == None:
            prefix = ""
        else:
            prefix += " "

        self.addH3(
            prefix +
            self.getReportTree().getPerspectiveName() +
            " Showing Only Groups")

        return



    def addGroupKey(self, formatType):
        """
        Adds the group symbol expanation to the report.
        """
        self.addH3("Second column indicates the path's group status.")
        self.addTextLine(
            "'" + self.GROUP_DISPLAY[ReportTree.GROUP_AVERSE] +
            "' : Term is not a group nor is it contained in a group.")
        self.addTextLine(
            "'" + self.GROUP_DISPLAY[ReportTree.GROUP] +
            "' : Term is a group.")
        self.addTextLine(
            "'" + self.GROUP_DISPLAY[ReportTree.GROUP_DIRECT_DESCENDANT] +
            "' : Term is directly contained in a group term.")
        self.addTextLine(
            "'" + self.GROUP_DISPLAY[ReportTree.GROUP_INDIRECT_DESCENDANT] +
            "' : Term is indirectly contained in a group term.")
        self.addTextLine("")
        return



    def addStageHeader(self, stage):
        """
        Add header for this specific stage.
        """
        self.addH3("Stage " + stage.getName())
        return



    def formatSynonyms(self, anatomyLine):
        """
        Format the synonym list for this anatomyLine.
        """
        formattedSyns = ""
        synonyms = anatomyLine.getSynonyms()
        if len(synonyms) > 0:
            formattedSyns = " (syn: " + "; ".join(synonyms) + ")"

        return formattedSyns




# ------------------------------------------------------------------
# JSON REPORT FILE
# ------------------------------------------------------------------

class JsonReportFile (LinearReportFile):
    """
    JSON text anatomy report file class.  This class is used to create a
    JSON text file show the anatomy.
    """

    def __init__(self, filePath, reportTree):
        """
        Initialise a plain text report file.  That is, open it.
        """
        self._reportTree = reportTree
        self.__filePath = filePath
        self._document = open(self.__filePath, "w")

        return None


    def closeReportFile(self, formatType):
        """
        Close a plain text report file.
        """
        global _depth, _second_root

        if _second_root == False:
            if formatType != CONTENT:
                dispLine = "}}"
                self.getDocument().write(dispLine)

        count = 0
        
        while count < _depth:
            if formatType != CONTENT:
                dispLine = "]}}"
                self.getDocument().write(dispLine)
            count = count + 1
        
        if _second_root == True:
            if formatType != CONTENT:
                dispLine = "]}}"
                self.getDocument().write(dispLine)
            count = count + 1

        if formatType == CONTENT:
            dispLine = "]"
            self.getDocument().write(dispLine)

        _second_root = False
            
        self._document.close()
        self._document = None
        return


    def addPageBreak(self):
        """
        Add a page break.

        Text reports don'thave page breaks.  Generate a text separator instead.
        """
        return


    def addH1(self, textLine):
        """
        Add a very large header line to a plain text file.
        """
        return



    def addH2(self, textLine):
        """
        Add a large header line to a plain text file.
        """

        return



    def addH3(self, textLine):
        """
        Add a normal header line to a plain text file.
        """
        return

    def addGroupKey(self, formatType):
        """
        Adds the group symbol expanation to the report.
        
        Reset the file to start again from the root node 
        """
        global _depth, _count, _second_root

        _count = _count - 1
        
        if formatType != CONTENT:
            dispLine = "}}"
            self.getDocument().write(dispLine)

        count = 1
        
        while count < _depth:
            if formatType != CONTENT:
                dispLine = "]}}"
                self.getDocument().write(dispLine)
            count = count + 1

        dispLine = "\n"
        self.getDocument().write(dispLine)
        
        if formatType == CONTENT:
            _second_root = False
        else:
            _second_root = False
        
        return


    def addTextLine(self, textLine):
        """
        Add a normal line of text to an XML file.
        """
        self.getDocument().write(
            textLine + "\n")
        return



    def addAbstractAnatomyLine(self, indentLine, formatType):
        """
        Add an anatomy line in abstract format.  This includes the EMAPA id and
        the stage range.
        """
        global _prev_indent, _count, _prev_depth, _depth, _second_root

        _count = _count + 1

        if formatType == CONTENT:
            if _count == 1: 
                dispLine = "[\n"
                self.getDocument().write(dispLine)
            if _count > 1:
                if _second_root == False:
                    dispLine = ","
                    self.getDocument().write(dispLine)
                if _second_root == True:
                    _second_root = False
        
        anatomyLine = indentLine.getLine()
        partOf      = anatomyLine.getPartOf()
        node        = anatomyLine.getNode()
        startStage  = Stages.getByOid(partOf.getNodeStartStageOid())
        endStage    = Stages.getByOid(partOf.getNodeEndStageOid())

        indent     = " ".join(indentLine.getIndent())
        if len(indent) > 0:
            indent += " "
        
        indent_diff = 0
        indent_diff = len(indent) - _prev_indent
        
        new_indent = indent.replace("|"," ")
        indent = new_indent.replace("\\"," ")
        new_indent = indent.replace("+"," ")
            
        _depth = 0
        direction = ""
        depth_diff = 0

        if len(indent) == 0:
            _depth = 0
            direction = "Root Node "
        else:
            _depth = len(indent) / 2
            if _depth == _prev_depth:
                direction = "Same Depth"
            if _depth > _prev_depth:
                direction = "Increasing"
            if _depth < _prev_depth:
                direction = "Decreasing"

        depth_diff = _prev_depth - _depth
        count = 1
            
        if formatType != CONTENT:
            if direction == "Increasing":
                if _second_root == False:
                    dispLine = ",\n\"children\":["
                else:
                    dispLine = ","
                _second_root = False
                self.getDocument().write(dispLine)
        
            if direction == "Decreasing":
                while count <= depth_diff:
                    if count == 1: 
                        dispLine = "}}]}}"
                    else:
                        dispLine = "]}}"
                    self.getDocument().write(dispLine)
                    count = count + 1

            if direction == "Same Depth":
                dispLine = "}},\n"
                self.getDocument().write(dispLine)

            if direction == "Increasing":
                dispLine = "\n"
                self.getDocument().write(dispLine)

            if direction == "Decreasing":
                dispLine = ",\n"
                self.getDocument().write(dispLine)

        if direction == "Root Node " and _count > 1:
            _second_root = True
        else:
            if formatType == CONTENT:
                if _count > 1:
                    if _second_root == False:
                        dispLine = "\n"
                        self.getDocument().write(dispLine)
                dispLine = "{\"nodeId\":\"%s\",\"extId\":\"%s\",\"starts\":\"%s\",\"ends\":\"%s\",\"name\":\"%s\"}" % (
                        _count,
                        node.getPublicId().strip(" "),
                        startStage.getName(),
                        endStage.getName(),
                        node.getComponentName()
                        )
                self.getDocument().write(dispLine)
            if formatType == CONTENTSTRUCTURE:
                dispLine = "{\"node\":{\"nodeId\":\"%s\",\"extId\":\"%s\",\"starts\":\"%s\",\"ends\":\"%s\",\"name\":\"%s\"" % (
                    _count,
                    node.getPublicId().strip(" "),
                    startStage.getName(),
                    endStage.getName(),
                    node.getComponentName()
                    )
                self.getDocument().write(dispLine)
            if formatType == STRUCTURE:
                dispLine = "{\"node\":{\"nodeId\":\"%s\"" % (
                    _count
                    )
                self.getDocument().write(dispLine)

        _prev_indent = len(indent)
        _prev_depth = _depth

        return


    def addStageAnatomyLine(self, stage, indentLine, formatType):
        """
        Add this line in a single stage format.  This includes the EMAP id but
        not the stage name.
        """
        global _prev_indent, _count, _prev_depth, _depth, _second_root

        _count = _count + 1
        
        if formatType == CONTENT:
            if _count == 1: 
                dispLine = "[\n"
                self.getDocument().write(dispLine)
            if _count > 1:
                if _second_root == False:
                    dispLine = ""
                if _second_root == True:
                    dispLine = ""
                    _second_root = False
                self.getDocument().write(dispLine)

        anatomyLine = indentLine.getLine()
        node       = anatomyLine.getNode()
        timedNode  = AnaTimedNodeDb.getByNodeStage(node.getOid(),
                                                   stage.getOid())
        #print "stage.getOid() = %d" % stage.getOid()
        #print "node.getOid() = %d" % node.getOid() 
        #print "timedNode.getPublicId() = " + timedNode.getPublicId() 
        indent     = " ".join(indentLine.getIndent())
        if len(indent) > 0:
            indent += " "
        
        indent_diff = 0
        indent_diff = len(indent) - _prev_indent
        
        new_indent = indent.replace("|"," ")
        indent = new_indent.replace("\\"," ")
        new_indent = indent.replace("+"," ")
            
        _depth = 0
        direction = ""
        depth_diff = 0

        if len(indent) == 0:
            _depth = 0
            direction = "Root Node "
        else:
            _depth = len(indent) / 2
            if _depth == _prev_depth:
                direction = "Same Depth"
            if _depth > _prev_depth:
                direction = "Increasing"
            if _depth < _prev_depth:
                direction = "Decreasing"

        depth_diff = _prev_depth - _depth
        count = 1
            
        if formatType != CONTENT:
            if direction == "Increasing":
                if _second_root == False:
                    dispLine = ",\n\"children\":["
                else:
                    dispLine = ","
                _second_root = False
                self.getDocument().write(dispLine)
        
            if direction == "Decreasing":
                while count <= depth_diff:
                    if count == 1: 
                        dispLine = "}}]}}"
                    else:
                        dispLine = "]}}"
                    self.getDocument().write(dispLine)
                    count = count + 1

            if direction == "Same Depth":
                dispLine = "}},\n"
                self.getDocument().write(dispLine)

            if direction == "Increasing":
                dispLine = "\n"
                self.getDocument().write(dispLine)

            if direction == "Decreasing":
                dispLine = ",\n"
                self.getDocument().write(dispLine)

        if direction == "Root Node " and _count > 1:
            _second_root = True
        else:
            if formatType == CONTENT:
                if _count > 1:
                    if _second_root == False:
                        dispLine = ",\n"
                        self.getDocument().write(dispLine)
                dispLine = "{\"nodeId\":\"%s\",\"extId\":\"%s\",\"name\":\"%s\"}" % (
                    _count,
                    timedNode.getPublicId().strip(" "),
                    node.getComponentName()
                    )
            if formatType == CONTENTSTRUCTURE:
                dispLine = "{\"node\":{\"nodeId\":\"%s\",\"extId\":\"%s\",\"name\":\"%s\"" % (
                    _count,
                    timedNode.getPublicId().strip(" "),
                    node.getComponentName()
                    )
            if formatType == STRUCTURE:
                dispLine = "{\"node\":{\"nodeId\":\"%s\"" % (
                    _count
                    )
            self.getDocument().write(dispLine)

        _prev_indent = len(indent)
        _prev_depth = _depth

        return


# ------------------------------------------------------------------
# TXT REPORT FILE
# ------------------------------------------------------------------

class TxtReportFile (LinearReportFile):
    """
    Plain text anatomy report file class.  This class is used to create a
    plain text file show the anatomy.
    """

    def __init__(self, filePath, reportTree):
        """
        Initialise a plain text report file.  That is, open it.
        """
        self._reportTree = reportTree
        self.__filePath = filePath
        self._document = open(self.__filePath, "w")

        self.GROUP_DISPLAY = {
            ReportTree.GROUP_AVERSE:              " ",
            ReportTree.GROUP_ANCESTOR:            " ",
            ReportTree.GROUP:                     "G",
            ReportTree.GROUP_DIRECT_DESCENDANT:   ">",
            ReportTree.GROUP_INDIRECT_DESCENDANT: "~"
            }

        return None


    def closeReportFile(self, formatType):
        """
        Close a plain text report file.
        """
        self._document.close()
        self._document = None
        return



    def addPageBreak(self):
        """
        Add a page break.

        Text reports don'thave page breaks.  Generate a text separator instead.
        """
        document = self.getDocument()
        document.write("\n\n\n")
        document.write(
            "==================================================================")
        document.write("\n\n\n")
        return


    def addH1(self, textLine):
        """
        Add a very large header line to a plain text file.
        """
        headerLine = "===== " + textLine + " ====="
        gaudyLine = len(headerLine) * "="

        self.getDocument().write(
            "\n" +
            gaudyLine + "\n" +
            headerLine + "\n" +
            gaudyLine + "\n" +
            "\n\n\n")
        return



    def addH2(self, textLine):
        """
        Add a large header line to a plain text file.
        """

        headerLine = "===== " + textLine + " ====="
        self.getDocument().write(
            "\n" +
            headerLine + "\n" +
            "\n\n")
        return



    def addH3(self, textLine):
        """
        Add a normal header line to a plain text file.
        """
        self.getDocument().write(
            "\n" +
            textLine + "\n" +
            "\n")
        return



    def addTextLine(self, textLine):
        """
        Add a normal line of text to an XML file.
        """
        self.getDocument().write(
            textLine + "\n")
        return



    def addAbstractAnatomyLine(self, indentLine, formatType):
        """
        Add an anatomy line in abstract format.  This includes the EMAPA id and
        the stage range.
        """
        anatomyLine = indentLine.getLine()
        partOf     = anatomyLine.getPartOf()
        node       = anatomyLine.getNode()
        startStage = Stages.getByOid(partOf.getNodeStartStageOid())
        endStage   = Stages.getByOid(partOf.getNodeEndStageOid())
        indent     = " ".join(indentLine.getIndent())
        if len(indent) > 0:
            indent += " "
        dispLine = "%-11s %s %s-%s %s%s%s\n" % (
            node.getPublicId(),
            self.GROUP_DISPLAY[anatomyLine.getGroupStatus()],
            startStage.getName(), endStage.getName(),
            indent,
            node.getComponentName(),
            self.formatSynonyms(anatomyLine))
        # self.getDocument().write(dispLine.encode("iso-8859-1"))
        self.getDocument().write(dispLine)

        return



    def addStageAnatomyLine(self, stage, indentLine, formatType):
        """
        Add this line in a single stage format.  This includes the EMAP id but
        not the stage name.
        """
        anatomyLine = indentLine.getLine()
        node       = anatomyLine.getNode()
        timedNode  = AnaTimedNodeDb.getByNodeStage(node.getOid(),
                                                   stage.getOid())
        #print "stage.getOid() = %d" % stage.getOid()
        #print "node.getOid() = %d" % node.getOid() 
        #print "timedNode.getPublicId() = " + timedNode.getPublicId() 
        indent     = " ".join(indentLine.getIndent())
        if len(indent) > 0:
            indent += " "
        dispLine = "%-10s %s %s%s%s\n" % (
            timedNode.getPublicId(),
            self.GROUP_DISPLAY[anatomyLine.getGroupStatus()],
            indent,
            node.getComponentName(),
            self.formatSynonyms(anatomyLine))
        # self.getDocument().write(dispLine.encode("iso-8859-1"))
        self.getDocument().write(dispLine)

        return



# ------------------------------------------------------------------
# RTF (RICH TEXT FORMAT) REPORT FILE
# ------------------------------------------------------------------

class RtfReportFile (LinearReportFile):
    """
    Rich Text Format (RTF) anatomy report file class.  This class is used
    to create an RTF file to show the anatomy.
    """

    def __init__(self, filePath, reportTree):
        """
        Initialise an RTF report file.  That is, open it.
        """
        self._reportTree = reportTree
        self.__filePath = filePath
        self._document = PyRTF.Elements.Document()
        self.__styleSheet = self._document.StyleSheet

        # there is only one section.
        self.__section = PyRTF.Elements.Section()
        self._document.Sections.append(self.__section)

        self.GROUP_DISPLAY = {
            ReportTree.GROUP_AVERSE:              " ",
            ReportTree.GROUP_ANCESTOR:            " ",
            ReportTree.GROUP:                     "G",
            ReportTree.GROUP_DIRECT_DESCENDANT:   RTF_DIRECT_DESCENDANT,
            ReportTree.GROUP_INDIRECT_DESCENDANT: RTF_INDIRECT_DESCENDANT
            }

        return None


    def closeReportFile(self, formatType):
        """
        Close an RTF report file.
        """
        renderer = PyRTF.Renderer()
        renderer.Write( self.getDocument(), open(self.__filePath, "w"))
        self._document = None

        return

    def getSection(self):
        """
        Return the single section in this document.
        """
        return self.__section


    def getLastParagraph(self):
        """
        Return the last paragraph in the document.
        """
        return self.getSection()[-1]


    def appendParagraph(self, para):
        """
        Append a paragraph to the end of the document.
        """
        self.__section.append(para)
        return


    def addPageBreak(self):
        """
        Add a page break.
        """
        paragraphProperties = PyRTF.PropertySets.ParagraphPS()
        paragraphProperties.SetPageBreakBefore(True)
        paragraph = PyRTF.Elements.Paragraph(paragraphProperties)
        self.appendParagraph(paragraph)

        return


    def addH1(self, textLine):
        """
        Add a very large header line to an RTF file.
        """
        # examples.pu MakeExample2 has some example code like this:
        #   section = PyRTF.Elements.Section()
        #   self.__document.Sections.append(section)
        #   para = PyRTF.Elements.Paragraph(
        #       self.__styleSheet.ParagraphStyles.Heading1)
        #   para.append(stringToRtf(textLine))
        #   section.append(para)
        # But it doesn't change the display at all when I run it.

        para = PyRTF.Elements.Paragraph()
        para.append(PyRTF.Elements.TEXT(stringToRtf(textLine), size = 40))
        self.appendParagraph(para)

        # Add an empty paragraph at the end to provide spacing, and/or to
        # give a trailing paragraph to add text to.
        para = PyRTF.Elements.Paragraph()
        self.appendParagraph(para)

        return



    def addH2(self, textLine):
        """
        Add a large header line to a RTF file.
        """
        para = PyRTF.Elements.Paragraph()
        para.append(PyRTF.Elements.TEXT(stringToRtf(textLine), size = 32))
        self.appendParagraph(para)

        # Add an empty paragraph at the end to provide spacing, and/or to
        # give a trailing paragraph to add text to.
        para = PyRTF.Elements.Paragraph()
        self.appendParagraph(para)

        return



    def addH3(self, textLine):
        """
        Add a normal header line to a RTF file.
        """
        para = PyRTF.Elements.Paragraph()
        para.append(PyRTF.Elements.TEXT(stringToRtf(textLine), size = 24))
        self.appendParagraph(para)

        # Add an empty paragraph at the end to provide spacing, and/or to
        # give a trailing paragraph to add text to.
        para = PyRTF.Elements.Paragraph()
        self.appendParagraph(para)

        return



    def addTextLine(self, textLine):
        """
        Add a normal line of text to a RTF file.
        """
        para = self.getLastParagraph()
        para.append(stringToRtf(textLine))
        para.append("\line")

        return



    def addAnatomyLine(self, leader, trailer):
        """
        Add a line showing anatomy to an RTF file.
        """
        para = self.getLastParagraph()
        para.append(
            PyRTF.Elements.TEXT(stringToRtf(leader), size = 16,
                                font = self.__styleSheet.Fonts.CourierNew))
        para.append(
            PyRTF.Elements.TEXT(stringToRtf(trailer), size = 16))
        para.append("\line ")

        return



    def addAbstractAnatomyLine(self, indentLine, formatType):
        """
        Add an anatomy line in abstract format.  This includes the EMAPA id and
        the stage range.
        """
        anatomyLine = indentLine.getLine()
        partOf     = anatomyLine.getPartOf()
        node       = anatomyLine.getNode()
        startStage = Stages.getByOid(partOf.getNodeStartStageOid())
        endStage   = Stages.getByOid(partOf.getNodeEndStageOid())
        indent     = indentLine.getIndent()
        unicodeIndent = u""

        if len(indent) > 0:
            for indentFlag in indent:
                unicodeIndent += RTF_UNICODE_INDENT_SYMBOLS[indentFlag] + " "

        leader = "%-11s %s %s-%s %s" % (
            node.getPublicId(),
            self.GROUP_DISPLAY[anatomyLine.getGroupStatus()],
            startStage.getName(), endStage.getName(),
            unicodeIndent)

        trailer = "%s%s" % (
            node.getComponentName(),
            self.formatSynonyms(anatomyLine))

        self.addAnatomyLine(leader, trailer)

        return



    def addStageAnatomyLine(self, stage, indentLine, formatType):
        """
        Add this line in a single stage format.  This includes the EMAP id but
        not the stage name.
        """
        anatomyLine = indentLine.getLine()
        node       = anatomyLine.getNode()
        timedNode  = AnaTimedNodeDb.getByNodeStage(node.getOid(),
                                                   stage.getOid())
        indent     = indentLine.getIndent()
        unicodeIndent = u""
        if len(indent) > 0:
            for indentFlag in indent:
                unicodeIndent += RTF_UNICODE_INDENT_SYMBOLS[indentFlag] + " "

        leader = "%-10s %s %s" % (
            timedNode.getPublicId(),
            self.GROUP_DISPLAY[anatomyLine.getGroupStatus()],
            unicodeIndent)
        trailer = "%s%s" % (
            node.getComponentName(),
            self.formatSynonyms(anatomyLine))

        self.addAnatomyLine(leader, trailer)

        return


# ------------------------------------------------------------------
# XML REPORT FILE
# ------------------------------------------------------------------

class XmlReportFile (ReportFile):
    """
    XML anatomy report file class.  This class is used to create an
    XML file showing the anatomy.

    XML files are 'nested' in comparison to the other 'linear' file
    types.  By nested, we mean that everything that is added to an XML file
    is added nested inside something that is already in the file.
    """

    def __init__(self, filePath, reportTree):
        """
        Initialise an XML report file.  That is, open it.
        """
        self._reportTree = reportTree
        self.__filePath = filePath
        self._document = xml.dom.Document.Document(None)
        self.__reportElement = None
        self.__lastHeaderElement = None
        # Initial contents of stack don't matter.  Just has to be > max depth
        self.__anatomyStack = range(20)

        self.GROUP_DISPLAY = {
            ReportTree.GROUP_AVERSE:              "notInGroupPath",
            ReportTree.GROUP_ANCESTOR:            "notInGroupPath",
            ReportTree.GROUP:                     "isGroup",
            ReportTree.GROUP_DIRECT_DESCENDANT:   "isGroupDirectDescendant",
            ReportTree.GROUP_INDIRECT_DESCENDANT: "isGroupIndirectDescendant"
            }

        return None


    def closeReportFile(self, formatType):
        """
        Close an XML report file.
        """
        Util.writeXmlFile(self.__filePath, self._document)
        self._document = None
        return


    def addPageBreak(self):
        """
        Add a page break.

        This doesn't mean or do anything in an XML report
        """
        return



    def addReportHeader(self):
        """
        Add the report element that is the root of everything else
        """
        reportElement = Util.addXmlElement(
            self.getDocument(), "mouseAnatomyReport")
        self.__reportElement = reportElement
        self.__lastHeaderElement = reportElement
        self.__anatomyStack[0] = reportElement

        version = self.getReportTree().getVersion()
        versionElement = Util.addXmlElement(
            reportElement, "anatomyDatabaseVersion")
        versionElement.setAttribute("number", str(version.getNumber()))
        versionElement.setAttribute("date", str(version.getDate()))
        Util.addXmlElement(reportElement, "perspective",
                        self.getReportTree().getPerspectiveName())
        return



    def addExcludingGroupsHeader(self, prefix = None):
        """
        Add header for part of file that lists anatomy without groups.

        XML reports ignore the prefix.
        """
        excludingGroupsElement = Util.addXmlElement(
            self.__reportElement, "excludingGroupsTree")
        self.__lastHeaderElement = excludingGroupsElement
        self.__anatomyStack[0]   = excludingGroupsElement

        return



    def addOnlyGroupsHeader(self, prefix = None):
        """
        Add header for part of file that lists only anatomy in groups.

        XML reports ignore the prefix parameter.
        """

        onlyGroupsElement = Util.addXmlElement(
            self.__reportElement, "onlyGroupsTree")
        self.__lastHeaderElement = onlyGroupsElement
        self.__anatomyStack[0]   = onlyGroupsElement

        return



    def addGroupKey(self, formatType):
        """
        Adds the group symbol expanation to the report.
        """
        parent = self.__lastHeaderElement

        key = Util.addXmlElement(parent, "groupStatusKey")

        status = Util.addXmlElement(
            key, "groupStatus",
            "Term is not a group nor is it contained in a group.")
        status.setAttribute("status", self.GROUP_DISPLAY[ReportTree.GROUP_AVERSE])

        status = Util.addXmlElement(
            key, "groupStatus",
            "Term a group.")
        status.setAttribute("status", self.GROUP_DISPLAY[ReportTree.GROUP])

        status = Util.addXmlElement(
            key, "groupStatus",
            "Term is directly contained in a group.")
        status.setAttribute(
            "status", self.GROUP_DISPLAY[ReportTree.GROUP_DIRECT_DESCENDANT])

        status = Util.addXmlElement(
            key, "groupStatus",
            "Term is indirectly contained in a group.")
        status.setAttribute(
            "status", self.GROUP_DISPLAY[ReportTree.GROUP_INDIRECT_DESCENDANT])

        return



    def addStageHeader(self, stage):
        """
        Add header for this specific stage.
        """
        Util.addXmlElement(self.__lastHeaderElement, "stage", stage.getName())

        return



    def addAbstractAnatomy(self, indentLines, formatType):
        """
        Add the given ordered list on anatomy items to the report.
        """
        for indentLine in indentLines:
            self.addAbstractAnatomyLine(indentLine, formatType)

        return



    # Don't define addTextLine(self, textLine):
    # It should never be called for XML.


    def addAbstractAnatomyLine(self, indentLine, formatType):
        """
        Add an anatomy line in abstract format.  This includes the EMAPA id and
        the stage range.
        """

        anatomyLine = indentLine.getLine()
        partOf     = anatomyLine.getPartOf()
        node       = anatomyLine.getNode()
        startStage = Stages.getByOid(partOf.getPathStartStageOid())
        endStage   = Stages.getByOid(partOf.getPathEndStageOid())

        depth      = partOf.getDepth()
        parent = self.__anatomyStack[depth]
        item = Util.addXmlElement(parent, "abstractComponent")
        self.__anatomyStack[depth+1] = item

        item.setAttribute("emapaId", node.getPublicId())
        Util.addXmlElement(item, "name", node.getComponentName())
        Util.addXmlElement(item, "pathStartStage", startStage.getName())
        Util.addXmlElement(item, "pathEndStage", endStage.getName())
        # :TODO: Put a list of stage specific EMAP IDs here
        Util.addXmlElement(item, "groupPathStatus",
                        self.GROUP_DISPLAY[anatomyLine.getGroupStatus()])

        # Add Synonyms
        for syn in anatomyLine.getSynonyms():
            Util.addXmlElement(item, "synonym", syn)

        return



    def addStageAnatomyLine(self, stage, indentLine, formatType):
        """
        Add this line in a single stage format.  This includes the EMAP id but
        not the stage name.
        """
        anatomyLine = indentLine.getLine()
        partOf     = anatomyLine.getPartOf()
        node       = anatomyLine.getNode()
        timedNode  = AnaTimedNodeDb.getByNodeStage(node.getOid(),
                                                   stage.getOid())
        depth      = partOf.getDepth()
        parent = self.__anatomyStack[depth]
        item = Util.addXmlElement(parent, "timedComponent")
        self.__anatomyStack[depth+1] = item

        item.setAttribute("emapId", timedNode.getPublicId())
        Util.addXmlElement(item, "name", node.getComponentName())
        Util.addXmlElement(item, "emapaId", node.getPublicId())
        Util.addXmlElement(item, "groupPathStatus",
                        self.GROUP_DISPLAY[anatomyLine.getGroupStatus()])

        # Add Synonyms
        for syn in anatomyLine.getSynonyms():
            Util.addXmlElement(item, "synonym", syn)

        return




# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


def stringToRtf(textString):
    """
    Converts a single string in ASCII or Unicode to an RTF string.

    Conversions include:
      \ becomes \\
      Unicode characters are encoded as RTF expects them.
    """

    rtfString = ""
    for i in range(len(textString)):
        char = textString[i]
        if char == "\\":
            rtfString += "\\\\"
        else:
            charInt = ord(char)
            if charInt > 127:
                # we have a unicode character.  RTFise it.
                rtfString += "{\\u" + str(charInt) + "\\u}"
            else:
                rtfString += chr(ord(char))

    return rtfString





def _openReportFile(filePath, reportTree, format):
    """
    Given a file name, a report tree, and a format, open a file of the
    correct format.
    """
    if format not in FORMATS:
        Util.fatalError([
            "Given format '" + format + "' not in list of supported formats:",
            ", ".join(FORMATS)])
    elif format == PLAIN_TEXT:
        reportFile = TxtReportFile(filePath, reportTree)
    elif format == RICH_TEXT:
        reportFile = RtfReportFile(filePath, reportTree)
    elif format == XML:
        reportFile = XmlReportFile(filePath, reportTree)
    elif format == JSON:
        reportFile = JsonReportFile(filePath, reportTree)
    else:
        Util.fatalError([
            "Given format '" + format + "' in list of supported formats, " +
            "but is not handled by the code.  Update code."])

    return reportFile



def _writeAbstractGroupsEmbeddedReport(reportTree, reportFile, format, formatType):
    """
    Write an abstract report with groups shown in same tree as
    primary paths.  An abstract report shows the entire anatomy,
    including the stage window for each item.
    """
    global _count
    _count = 0

    if format != JSON:
       reportFile.addGroupKey(formatType)

    # report on every line in tree.
    indentLines = reportTree.genAbstractAllList()
    reportFile.addAbstractAnatomy(indentLines, formatType)

    return



def _writeAbstractGroupsTrailingReport(reportTree, reportFile, formatType):
    """
    Write an abstract report first showing only
    primary paths, and then again showing only group paths plus minimal
    context.
    """
    global _count
    _count = 0

    # Generate tree without groups.
    reportFile.addExcludingGroupsHeader("Abstract")
    indentLines = reportTree.genAbstractExcludingGroupsList()
    reportFile.addAbstractAnatomy(indentLines, formatType)

    # Generate tree showing only groups.
    reportFile.addPageBreak()
    reportFile.addOnlyGroupsHeader("Abstract")
    reportFile.addGroupKey(formatType)
    indentLines = reportTree.genAbstractOnlyGroupsList()
    reportFile.addAbstractAnatomy(indentLines, formatType)

    return



def _writeStageGroupsEmbeddedReport(reportTree, reportFile, stage, format, formatType):
    """
    Write a report for the given stage in the anatomy, showing groups and
    primary paths in the same tree.
    """
    global _count
    _count = 0

    reportFile.addStageHeader(stage)
    
    if format != JSON:
       reportFile.addGroupKey(formatType)

    indentLines = reportTree.genStageAllList(stage)
    reportFile.addStageAnatomy(stage, indentLines, formatType)

    return


def _writeStageGroupsTrailingReport(reportTree, reportFile, stage, formatType):
    """
    Write a report for the given stage in the anatomy, splitting it into two
    parts, one showing everything but groups, and the second showing only
    groups plus minimal context.
    """
    global _count
    _count = 0

    reportFile.addStageHeader(stage)

    # Generate tree without groups.
    reportFile.addExcludingGroupsHeader(stage.getName())
    indentLines = reportTree.genStageExcludingGroupsList(stage)
    reportFile.addStageAnatomy(stage, indentLines, formatType)

    # Generate tree showing only groups.
    reportFile.addPageBreak()
    reportFile.addOnlyGroupsHeader(stage.getName())
    reportFile.addGroupKey(formatType)
    indentLines = reportTree.genStageOnlyGroupsList(stage)
    reportFile.addStageAnatomy(stage, indentLines, formatType)

    return



# ------------------------------------------------------------------
# MODULE ROUTINES
# ------------------------------------------------------------------


def writeAbstractReport(reportTree, outputDir, format, reportType, formatType):
    """
    Write an abstract report.  An abstract report shows the entire anatomy,
    including the stage window for each item.
    """
    if format == JSON:
        filePath = (outputDir + "/" +
                FORMATS[format][FORMAT_SUBDIRECTORY] + "/Abstract/" +
                FORMAT_TYPES[formatType][FORMAT_TYPE_NAME] +
                "/Abstract" +
                REPORT_TYPES[reportType][REPORT_TYPE_NAME] + 
                FORMAT_TYPES[formatType][FORMAT_TYPE_NAME] +
                "." +
                FORMATS[format][FORMAT_EXTENSION])
    else:
        filePath = (outputDir + "/" +
                FORMATS[format][FORMAT_SUBDIRECTORY] + "/Abstract/Abstract" +
                REPORT_TYPES[reportType][REPORT_TYPE_NAME] + "." +
                FORMATS[format][FORMAT_EXTENSION])

    reportFile = _openReportFile(filePath, reportTree, format)
    reportFile.addReportHeader()

    if reportType == GROUPS_EMBEDDED:
        _writeAbstractGroupsEmbeddedReport(reportTree, reportFile, format, formatType)
    elif reportType == GROUPS_TRAILING:
        _writeAbstractGroupsTrailingReport(reportTree, reportFile, formatType)

    reportFile.closeReportFile(formatType)

    return



def writeStageReport(reportTree, outputDir, format, reportType,
                     togetherness, formatType):
    """
    Write stage specific reports, one for each stage.
    """
    if format == XML and togetherness == CONCATENATED:
        # Reject outright any request for concatenated XML stage reports.
        # There is not enough memory on my machine to produce these.
        Util.warning([
            "You have requested an XML report that would include a separate",
            "tree for each stage, all in the same XML file.  This is a",
            "reasonable request, but past experience indicates that there",
            "is not enough memory on most workstations to build such a large",
            "XML file.  Therefore, THE REQUESTED REPORT WILL NOT BE GENERATED."])
        return None

    if format == JSON and togetherness == CONCATENATED:
        # A Concatenated JSON stage report is a nonsensical request.
        #  Therefore it will not be produced and a warning generated.
        Util.warning([
            "You have requested an JSON file that would include a separate",
            "tree for each stage, all in the same JSON file.  This is NOT",
            "reasonable request, as a JSON file should only have a single ",
            "root node.  Therefore, THE REQUESTED REPORT WILL NOT BE ",
            "GENERATED."])
        return None
    
    if format == JSON:
        reportDir = (outputDir + "/" +
                FORMATS[format][FORMAT_SUBDIRECTORY] + "/ByStage/" +
                REPORT_TYPES[reportType][REPORT_TYPE_NAME] + "/" +
                FORMAT_TYPES[formatType][FORMAT_TYPE_NAME])
    else:
        reportDir = (outputDir + "/" +
                 FORMATS[format][FORMAT_SUBDIRECTORY] + "/ByStage/" +
                 REPORT_TYPES[reportType][REPORT_TYPE_NAME])
    reportFile = None

    if togetherness == CONCATENATED and format != JSON:
        # Open one report file.
        fileName = (reportDir + "/AllStages" +
                    REPORT_TYPES[reportType][REPORT_TYPE_NAME] + "." +
                    FORMATS[format][FORMAT_EXTENSION])
        reportFile = _openReportFile(fileName, reportTree, format)
        reportFile.addReportHeader()

    for stage in Stages.SequenceIterator():

        if togetherness == SEPARATE and format != JSON:
            # open report file for each stage
            fileName = (reportDir + "/" + stage.getName() +
                        REPORT_TYPES[reportType][REPORT_TYPE_NAME] +
                        "." + FORMATS[format][FORMAT_EXTENSION])
            reportFile = _openReportFile(fileName, reportTree, format)
            reportFile.addReportHeader()

        if togetherness == SEPARATE and format == JSON:
            # open report file for each stage
            fileName = (reportDir + "/" + stage.getName() +
                        REPORT_TYPES[reportType][REPORT_TYPE_NAME] +
                        FORMAT_TYPES[formatType][FORMAT_TYPE_NAME] +
                        "." + FORMATS[format][FORMAT_EXTENSION])
            reportFile = _openReportFile(fileName, reportTree, format)
            reportFile.addReportHeader()

        if reportType == GROUPS_EMBEDDED:
            _writeStageGroupsEmbeddedReport(reportTree, reportFile, stage, format, formatType)
        elif reportType == GROUPS_TRAILING:
            _writeStageGroupsTrailingReport(reportTree, reportFile, stage, formatType)

        if togetherness == SEPARATE:
            reportFile.closeReportFile(formatType)
        else:
            reportFile.addPageBreak()

    if togetherness == CONCATENATED and format != JSON:
        reportFile.closeReportFile(formatType)

    return
