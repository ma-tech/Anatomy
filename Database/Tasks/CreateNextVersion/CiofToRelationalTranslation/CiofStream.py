#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
# Code to parse .ciof anatomy files.
#
# ciof files are exported from objectstore and define anatomy.  The code
# in this file reads entries from a ciof file and returns them in a well
# defined structure.
#
# This is a generic parser.  It does not care what the actual entries in
# the ciof stream are as long as they conform to the expected syntactic
# structure.

import datetime
import re                               # regular expressions
import sys                              # error reporting
import Ciof                             # CIOF basics, Entities and attributes
import Util                             # Error Handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# CIOF_STREAM
# ------------------------------------------------------------------


class CiofInputStream:
    """
    A stream of CIOF entities.
    """

    def __init__(self, fileName):
        """
        A CIOF stream is just a file that contains a header and then long list
        of CIOF entities.
        """

        # open file, validate header, and get Date of version.
        self.__ciofStream = open(fileName, "r")

        headerLine = "% Mouse Atlas Database - Copyright\(©\) 1994-1998 MRC HGU\n"
        actualLine = self.__ciofStream.readline()
        if not re.match(headerLine, actualLine):
            Util.fatalError([
                'ciof file header not as expected',
                '  Expected: "' + headerLine + '"\n',
                '  Actual:   "' + actualLine + '"\n'])

        dateLine = "% Saved on .*"
        actualLine = self.__ciofStream.readline()
        if not re.match(dateLine, actualLine):
            Util.fatalError([
                'CIOF date line not as expected',
                '  Expected: "' + dateLine + '"\n',
                '  Actual:   "' + actualLine + '"\n'])

        # Everything verified, now extract date.
        dateFields = actualLine.split()
        timeFields = dateFields[6].split(":")
        self.__dateTime = datetime.datetime(
            year   = int(dateFields[7]),
            month  = Util.monthNameToInt(dateFields[4]),
            day    = int(dateFields[5]),
            hour   = int(timeFields[0]),
            minute = int(timeFields[1]),
            second = int(timeFields[2]))

        return None



    def getNextEntity(self):
        """
        Read the next ciof entity in the ciofStream, returns None if at EOF.

        Each ciof entity consists of:
          entitytype
          entityId
          attributes, a list of
            attributeName
            values, a list of
              strings
        """

        # The right thing to do here is build a full fledged parser.
        # Maybe later.

        # An example entry:
        #
        # @AnatomicalPart{16138,
        # CreationTime={923580687},
        # LastModificationTime={923580687},
        # Name={cavity},
        # SpaceParent={16137},
        # Stages={TS12:231,TS13:437,TS14:697,TS15:1063,TS16:1538}}
        #
        # Some entries are deleted.  Return them anyway

        # Read to first line of entity
        line = self.__ciofStream.readline()
        while line == "\n":
            line = self.__ciofStream.readline()
        if line == "":                  # at EOF
            return None                 # !!!! EARLY RETURN !!!!

        entityType, entityId = re.match("^@([^{]*){([^,]*),\n", line).groups()

        entity = Ciof.CiofEntity(entityType, entityId)

        # Parse attributes until end of entity
        attribRe = re.compile("^([^=]*)={([^}\n]*)(}|)([^\n]*)\n")
        attribMultiLineRe = re.compile("^([^}\n]*)(}|)([^\n]*)\n")
        lineTerminator = None
        while lineTerminator != "}":
            line = self.__ciofStream.readline()
            [attrName, values, entityTerminator,
             lineTerminator] = attribRe.match(line).groups()
            # deal with multiline values.  Strip embedded newlines.
            while lineTerminator == "":
                line = self.__ciofStream.readline()
                [valuesContinued, entityTerminator,
                 lineTerminator] = attribMultiLineRe.match(line).groups()
                values = values + " " + valuesContinued

            entity.addAttribute(attrName, values)

            if lineTerminator not in ["}",","]:
                Util.fatalError([
                    "Line in entity defintion ends with something other than ',' or '}'",
                    '  Line: "' + line + '"'])

        return entity



    def getDateTime(self):
        """
        Return the timestamp from the top of the file.

        This is as close to version number as the file gets.
        """
        return self.__dateTime



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# run first time module is loaded.
