#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Report tree module.  Defines the tree from which the reports are generated.

This module is heavily driven by the ANAD_PART_OF table.
"""

import sets

from hgu import Util

from hgu.anatomyDb.version011 import Nodes
from hgu.anatomyDb.version011 import PartOfs
from hgu.anatomyDb.version011 import Stages
from hgu.anatomyDb.version011 import AnaSynonymDb
from hgu.anatomyDb.version011 import AnaVersionDb

# ALSO SEE IMPORTS BELOW GLOBAL SECTION

# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------



# Indent Flags

L = "\\"
T = "+"
PIPE = "|"
EMPTY = " "


# Each line has a group status.

GROUP_AVERSE              = "GROUP_AVERSE"
GROUP_ANCESTOR            = "GROUP_ANCESTOR"
GROUP                     = "GROUP"
GROUP_DIRECT_DESCENDANT   = "GROUP_DIRECT_DESCENDANT"
GROUP_INDIRECT_DESCENDANT = "GROUP_INDIRECT_DESCENDANT"




# ------------------------------------------------------------------
# REPORT TREE
# ------------------------------------------------------------------

class ReportTree:
    """
    Report tree from which a report is generated.  A report tree is for
    a specific perspective.  You can gennerate multiple reports from
    the same tree.

    depthLimit: Only report the tree up to this depth.  0 means report only
                the root.
    """

    def __init__(self, perspectiveName, depthLimit):
        """
        Initialise a report tree for the given perspective.
        """
        self.__perspectiveName = perspectiveName
        self.__depthLimit = depthLimit
        self.__version = AnaVersionDb.getLatestVersion()
        self.__linesInSequenceOrder = []
        self.__linesByOid = {}

        print "ReportTree011 - Init"
        print perspectiveName

        for partOf in PartOfs.PerspectiveSequenceIterator(perspectiveName):

            print "partOf.getOid()"
            print partOf.getOid()
            print "partOf.getNodeOid()"
            print partOf.getNodeOid()

            if partOf.getDepth() <= self.__depthLimit:
                line = ReportTreeLine(partOf, self)
                self.__linesInSequenceOrder.append(line)
                self.__linesByOid[partOf.getOid()] = line

        # Save lines in reverse order for populating indents.
        self.__linesInReverseSequenceOrder = self.__linesInSequenceOrder[:]
        self.__linesInReverseSequenceOrder.reverse()

        self.__setGroupStatus()

        return None



    def getLineByOid(self, oid):
        """
        Return the line with this ANAD_PART_OF.APO_OID.
        """
        
        print "ReportTree - getLineByOid"
        print oid 
        return self.__linesByOid[oid]


    def getPerspectiveName(self):
        """
        Return name of perspective this report is for.
        """
        return self.__perspectiveName


    def getVersion(self):
        """
        Return version of anatomy database this report is for.
        """
        return self.__version


    def __setGroupStatus(self):
        """
        Recursively set the group status for every line in the tree.
        """
        self.__linesInSequenceOrder[0].setGroupStatus()

        return


    def genAbstractAllList(self):
        """
        Generate a list of all anatomy lines with their indents.
        """
        lines = []
        nextIndent = None
        for line in self.__linesInReverseSequenceOrder:
            indent = line.genIndent(nextIndent)
            lines.append(IndentLine(line, indent))
            nextIndent = indent

        lines.reverse()

        return lines



    def genAbstractExcludingGroupsList(self):
        """
        Generate a list of all anatomy lines with their indents, excluding any
        line that is a group or is below a group.
        """
        lines = []
        nextIndent = None
        for line in self.__linesInReverseSequenceOrder:
            if line.inExcludingGroupsReport():
                indent = line.genIndent(nextIndent)
                lines.append(IndentLine(line, indent))
                nextIndent = indent

        lines.reverse()

        return lines



    def genAbstractOnlyGroupsList(self):
        """
        Generate a list of all anatomy lines with their indents, including only
        lines that are groups or are below a groups, or ancestors of groups.
        """
        lines = []
        nextIndent = None
        for line in self.__linesInReverseSequenceOrder:
            if line.inOnlyGroupsReport():
                indent = line.genIndent(nextIndent)
                lines.append(IndentLine(line, indent))
                nextIndent = indent

        lines.reverse()

        return lines



    def genStageAllList(self, stage):
        """
        Generate a list of all anatomy items that exist in a given stage,
        including their indents.
        """
        lines = []
        nextIndent = None
        for line in self.__linesInReverseSequenceOrder:
            if line.inStage(stage):
                indent = line.genIndent(nextIndent)
                lines.append(IndentLine(line, indent))
                nextIndent = indent

        lines.reverse()

        return lines



    def genStageExcludingGroupsList(self, stage):
        """
        Generate a list of anatomy items that exist in a given stage that are
        not a group or below a group item.

        Returns an ordered llist of IndentLines.
        """
        lines = []
        nextIndent = None
        for line in self.__linesInReverseSequenceOrder:
            if line.inStage(stage) and line.inExcludingGroupsReport():
                indent = line.genIndent(nextIndent)
                lines.append(IndentLine(line, indent))
                nextIndent = indent

        lines.reverse()

        return lines



    def genStageOnlyGroupsList(self, stage):
        """
        Generat a list of anatomy items that exist in a given stage that are
        groups or are below a group, or are ancestors of groups.

        Returns an ordered llist of IndentLines.
        """
        lines = []
        nextIndent = None
        for line in self.__linesInReverseSequenceOrder:
            if line.inStage(stage) and line.inOnlyGroupsReport():
                indent = line.genIndent(nextIndent)
                lines.append(IndentLine(line, indent))
                nextIndent = indent

        lines.reverse()

        return lines




# ------------------------------------------------------------------
# REPORT TREE LINE
# ------------------------------------------------------------------

class ReportTreeLine:
    """
    An individual line in the report tree.
    """

    def __init__(self, partOf, reportTree):
        """
        Initialise a report tree line given the ANAD_PART_OF record, and
        the ReportTreeLine that follows this one.
        """
        self.__partOf = partOf
        self.__reportTree = reportTree
        self.__groupStatus = None
        self.__childLines = sets.Set()

        # populate synonym list
        self.__synonyms = []
        synRecs = AnaSynonymDb.getByOidSynonymIsFor(self.getNodeOid())
        for synRec in synRecs:
            self.__synonyms.append(synRec.getSynonym())

        # Register yourself with your parent line.  This only works because
        # we read the tree in parent-child order.
        parentApoOid = self.getParentApoOid()
        apoOid = self.getApoOid()

        print "ReportTreeLine - parentApoOid"
        print parentApoOid
        print "ReportTreeLine - apoOid"
        print apoOid

        if parentApoOid == 0 and apoOid == 0:
            print "SPACES"
            
        if parentApoOid != None:
            
            if apoOid != 0:
                parentLine = self.__reportTree.getLineByOid(self.getParentApoOid())
                parentLine.addChildLine(self)

        return


    def genIndent(self, nextIndent):
        """
        Generate the indent for this line, given the next line in this report.

        Indents are set by walking backwards up the tree.
        """
        indent = []
        for index in range(self.getDepth()):
            indent.append(EMPTY)

        depth = self.getDepth()
        lastItem = depth - 1
        if nextIndent != None:
            nextDepth = len(nextIndent)
        else:
            nextDepth = -1

        if depth > 0:
            # set everything up to the last item
            for index in range(depth - 1):
                if nextDepth > index:
                    indent[index] = nextIndent[index]
                else:
                    indent[index] = EMPTY

            # Set last item.
            if nextIndent == None:
                indent[lastItem] = L

            elif depth < nextDepth:
                if nextIndent[lastItem] == PIPE:
                    indent[lastItem] = T
                else:
                    indent[lastItem] = L

            elif depth > nextDepth:
                indent[lastItem] = L
                indent[nextDepth - 1] = PIPE

            elif depth == nextDepth:
                indent[lastItem] = T

        return indent


    def getSynonyms(self):
        """
        Return a list of all synonyms for this anatomy item.
        """
        return self.__synonyms


    def getGroupStatus(self):
        """
        Return this line's group status.
        """
        return self.__groupStatus

    def inExcludingGroupsReport(self):
        """
        Return True if this line is in Excluding Groups reports.
        """
        return self.getGroupStatus() in [GROUP_ANCESTOR, GROUP_AVERSE]

    def inOnlyGroupsReport(self):
        """
        Return True if this line is in the show only groups reports.
        """
        return self.getGroupStatus() != GROUP_AVERSE

    def getPartOf(self):
        """
        Return the ANAD_PART_OF record for this line.
        """
        
        print "ReportTreeLine - getPartOf"

        return self.__partOf

    def getApoOid(self):
        """
        Return the OID of this line in ANAD_PART_OF.
        """
        return self.getPartOf().getOid()

    def getDepth(self):
        """
        Get the depth of this line in the tree in ANAD_PART_OF.
        """
        return self.getPartOf().getDepth()

    def getParentApoOid(self):
        """
        Get the OID of this line's parent line in ANAD_PART_OF.
        """
        
        print "ReportTreeLine - getParentApoOid"

        #print self.getPartOf().getOid()
        #print self.getPartOf().getParentApoOid()

        return self.getPartOf().getParentApoOid()

    def getNodeOid(self):
        """
        Get the OID of the anatomy node this line is for.
        """
        return self.getPartOf().getNodeOid()

    def getPathStartStage(self):
        """
        Return the stage that this line comes into existince (which may be
        later than the stage this line's node comes into existence).
        """
        return Stages.getByOid(self.getPartOf().getPathStartStageOid())

    def getPathEndStage(self):
        """
        Return the stage that this line goes out of existence (which may be
        earlier than the stage this line's node goes out of existence).
        """
        return Stages.getByOid(self.getPartOf().getPathEndStageOid())

    def getNode(self):
        """
        Get the node for this line.
        """
        return Nodes.getByOid(self.getNodeOid())

    def inStage(self, stage):
        """
        Return true if this line occurs in the given stage.
        """
        stageSequence = stage.getSequence()

        startStage = self.getPathStartStage()
        endStage   = self.getPathEndStage()
        return (startStage.getSequence() <= stageSequence and
                endStage.getSequence()   >= stageSequence)


    def addChildLine(self, childLine):
        """
        Adds the given line to this line's list of child lines.
        """
        self.__childLines.add(childLine)
        return


    def setGroupStatus(self):
        """
        Set the group status for this line, and all of its descendant lines.
        May also change the group status for its ancestor lines as well.
        """
        # Have two sources of information:
        # Node.isPrimary: is this node a primary node or a group node?
        # PartOf.isPrimary/isGroup: Is this the primary path to this node, or
        #                           was this node reached by going through a
        #                           group node?

        # Set status for this line.
        parentApoOid = self.getParentApoOid()
        if parentApoOid == None:
            # At root.  Status will change to GROUP_ANCESTOR when we hit the
            # first group.
            self.__groupStatus = GROUP_AVERSE
        else:
            parentLine = self.__reportTree.getLineByOid(parentApoOid)
            parentStatus = parentLine.getGroupStatus()
            if parentStatus in [GROUP_AVERSE, GROUP_ANCESTOR]:
                node = self.getNode()
                if not node.isPrimary():
                    # We have a group that is not itself in a group path.
                    self.__groupStatus = GROUP

                    # walk up parents marking them as ancestors.
                    ancestorLine = parentLine
                    while (ancestorLine != None and
                           ancestorLine.getGroupStatus() != GROUP_ANCESTOR):
                        ancestorLine.__groupStatus = GROUP_ANCESTOR
                        ancestorApoOid = ancestorLine.getParentApoOid()
                        if ancestorApoOid != None:
                            ancestorLine = self.__reportTree.getLineByOid(
                                ancestorApoOid)
                        else:
                            ancestorLine = None

                else: # Not a group, and not in a group path
                    self.__groupStatus = GROUP_AVERSE

            elif parentStatus == GROUP:
                # That makes us a direct descendent
                self.__groupStatus = GROUP_DIRECT_DESCENDANT

            elif parentStatus in [GROUP_DIRECT_DESCENDANT,
                                  GROUP_INDIRECT_DESCENDANT]:
                # That makes us an indirect descendent
                self.__groupStatus = GROUP_INDIRECT_DESCENDANT

            else:
                Util.fatalError([
                    "Should have covered all cases.",
                    "ParentStatus: ", parentStatus])

        # Set status for all kid lines.
        for line in self.__childLines:
            line.setGroupStatus()

        return



# ------------------------------------------------------------------
# LINE WITH INDENT
# ------------------------------------------------------------------

class IndentLine:
    """
    Reports consist of an ordered series of ReportTreeLines.  However
    each report can have a different indent value for each line.
    This object binds together lines with indents.
    """
    def __init__(self, line, indent):
        """
        Bind a ReportTreeLine with an indent for a specific report.
        """
        self.__line = line
        self.__indent = indent

        return

    def getLine(self):
        """
        Return the line part of this pairing.
        """
        return self.__line

    def getIndent(self):
        """
        Return the indent that goes with this indent in the current report.
        """
        return self.__indent




# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

