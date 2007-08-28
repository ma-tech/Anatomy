#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Code to parse Relationship Sequence Files

Relationship sequence files are produced by the anatomy tree reporting
scripts, using the relationship sequence that existed in the previous
version of the DB.  These files are then manually edited (using cut
and paste) to produce the ordering used in the next revision.
"""

import datetime
import re                               # regular expressions

import Util                             # Error Handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Define various header lines.  Some of these use regular expressions.

BOILERPLATE_LINE = "===========================================================\n"

VERSION_LINE = (
    "^=+ Anatomy Database Version (\d+), (\d{4})-(\d\d)-(\d\d) (\d\d):(\d\d):(\d\d) =+\n")

HEADER_LINES     = [
    "^=+ .+ Anatomy =+\n",
    "^Abstract .+ Excluding Groups\n"
]

GROUP_HEADER_LINE  = "^Abstract .+ Showing Only Groups\n"
GROUP_NOTE_LINES   = [
    "Second column indicates the path's group status.\n",
    "' ' : Term is not a group nor is it contained in a group.\n",
    "'G' : Term is a group.\n",
    "'>' : Term is directly contained in a group term.\n",
    "'~' : Term is indirectly contained in a group term.\n"
]

COMPONENT_LINE = "^(EMAPA:\d+) [ G>\~] TS\d\d-TS\d\d ([\+\~\|\\\ ]*)([\w \-/]+)"


# ------------------------------------------------------------------
# CIOF_STREAM
# ------------------------------------------------------------------


class RelationshipSequenceStream:
    """
    A stream of relationship sequences.

    That may be true, but it is not helpful.  This parser reads in files
    that show the anatomy in an indented ordered form.  The order that items
    occur in this file is then used by the callers to update the order in
    which items are displayed.
    """

    def __init__(self, fileName):
        """
        Open and read the header of the file.
        """

        # open file, validate header, and get Date of version.
        self.__stream = open(fileName, "r")

        actualLine = self.getNextNonBlankLine()
        matchGroups = re.match(VERSION_LINE, actualLine)
        if not matchGroups:
            Util.fatalError([
                'File header database version line not as expected',
                '  Expected: "' + VERSION_LINE + '"\n',
                '  Actual:   "' + actualLine + '"\n'])
        self.__versionNumber = int(matchGroups.group(1))

        self.__datetime = datetime.datetime(
            year   = int(matchGroups.group(2)),
            month  = int(matchGroups.group(3)),
            day    = int(matchGroups.group(4)),
            hour   = int(matchGroups.group(5)),
            minute = int(matchGroups.group(6)),
            second = int(matchGroups.group(7)))

        for expectedLine in HEADER_LINES:
            actualLine = self.getNextNonBlankLine()
            if not re.match(expectedLine, actualLine):
                Util.fatalError([
                    'File header not as expected',
                    '  Expected: "' + expectedLine + "'",
                    '  Actual:   "' + actualLine + '"'])

        self.__inPrimarySection = True

        # All of header read in.  Next line should be a sequence line.

        return None


    def getVersionNumber(self):
        """
        Return the anatomy database version number from the file.
        """
        return self.__versionNumber

    def getDateTime(self):
        """
        Return the anatomy database date time from the file.
        """
        return self.__datetime

    def inPrimarySection(self):
        """
        Return True if in the primary section of the file, as opposed
        to the separate Groups section.
        """
        return self.__inPrimarySection

    def inGroupSection(self):
        """
        Return True if in the groups section, as opposed to the separate
        primary section.
        """
        return not self.__inPrimarySection



    def getNextNonBlankLine(self):
        """
        Returns the next non blank line in the stream.

        Returns None if at EOF.
        """

        line = self.__stream.readline()
        while line and line in ["\n", BOILERPLATE_LINE]:
            line = self.__stream.readline()

        if line and Util.debugging():
            Util.debugMessage([line])

        return line



    def getNextComponent(self):
        """
        Reads in the next line with an anatomical component on it,
        returns None if at EOF

        Each Component line consists of:
          Public ID
          Group Indicator
          stage range
          depth indicator
          component name
          ...

        MOVE THESE COMMENTS TO CALLING ROUTINE
        The behaviour of this method is different depending on if it has
        read in the group header or not.  Only the primary tree is listed
        before the group header.  Therefore each non-group component should
        occur exactly once in this section.  Warnings are issued if a
        component is listed more than once in this section.

        After the group header, we expect to see only new group terms, and
        repeats of primary terms we have already seen.  Therefore, warnings
        are not issued when a component re-occurs in the groups section.
        """

        # An example of what the input looks like:
        # EMAPA:25765   TS01-TS28 mouse
        # EMAPA:16103   TS11-TS28 . organ system
        # EMAPA:16245   TS12-TS28 . . visceral organ
        # EMAPA:17366   TS19-TS28 . . | urinary system
        # EMAPA:17377   TS19-TS20 . . | . nephric duct, metanephric portion (syn: Wolffian duct)
        # EMAPA:18323   TS22-TS28 . . | . urachus
        #
        # and from the groups section:
        # EMAPA:25765   TS01-TS28 mouse
        # EMAPA:16103   TS11-TS28 . organ system
        # EMAPA:28215 G TS25-TS28 . . | arcuate artery
        # EMAPA:28221 G TS25-TS28 . . | arcuate vein
        # EMAPA:28212 G TS25-TS28 . . | interlobular artery
        # EMAPA:28218 G TS25-TS28 . . | interlobular vein

        line = self.getNextNonBlankLine()
        if line == "":                  # at EOF
            return None                 # !!!! EARLY RETURN !!!!

        if re.match(GROUP_HEADER_LINE, line):
            # Start of groups section.
            self.__inPrimarySection = False
            for expectedLine in GROUP_NOTE_LINES:
                line = self.getNextNonBlankLine()
                if expectedLine != line:
                    Util.fatalError([
                        'Group Section Header not as expected.',
                        'Expected: "' + expectedLine +'"',
                        'Actual:   "' + line + '"'])

            line = self.getNextNonBlankLine()
            if line == "":                  # at EOF
                return None                 # !!!! EARLY RETURN !!!!

        return ComponentLine(line)



class ComponentLine:
    """
    Contains information available from a component line in the relationship
    sequence stream.
    """

    def __init__(self, line):
        """
        Parse a component line from a relationship sequence file into
        its various components.
        """
        self.__line = line
        lineGroups = re.match(COMPONENT_LINE, line)
        if not lineGroups:
            Util.fatalError([
                "Component line does not match expected pattern.",
                "Expected: " + COMPONENT_LINE,
                "Actual:   " + line])
        self.__publicId = lineGroups.group(1)
        self.__depth    = len(lineGroups.group(2)) / 2
        self.__name     = lineGroups.group(3)

        return None

    def getPublicId(self):
        """
        Return the public ID (the EMAPA id) of the component on this line.
        """
        return self.__publicId

    def getDepth(self):
        """
        Return the depth in the tree of this line.
        """
        return self.__depth

    def getName(self):
        """
        Return the component name on this line.
        """
        return self.__name

    def getLine(self):
        """
        Return the whole text line.
        """
        return self.__line


# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# run first time module is loaded.
