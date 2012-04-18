#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Utility routines
"""

import cStringIO                        # print & translate XML
import re
import sys                              # error reporting
import xml.dom.minidom as minidom


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

_MONTHS = {'Jan': 1,
           'Feb': 2,
           'Mar': 3,
           'Apr': 4,
           'May': 5,
           'Jun': 6,
           'Jul': 7,
           'Aug': 8,
           'Sep': 9,
           'Oct': 10,
           'Nov': 11,
           'Dec': 12}


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_debugging = False



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# ERROR / WARNING SUBROUTINES
# ------------------------------------------------------------------

def _showMessages(prefix, messages):
    """
    Common routine called by specific message routintes to actually
    show the mesage.
    """
    sys.stderr.write(prefix + ":")

    for message in messages:
        sys.stderr.write("  " + message + "\n")

    sys.stderr.write("\n")
    return


def fatalError(messages):
    """
    Displays error message, encoded as a list of one or more strings,
    and then raises a generic exception
    """
    _showMessages("ERROR", messages)
    raise Exception


def warning(messages):
    """
    Displays warning message, encoded as a list of one or more strings,
    and then returns to the caller
    """
    _showMessages("WARNING", messages)
    return None


def deprecatedWarning(replacedBy=None):
    """
    Displays deprecated warning message.  Called from routines that have
    been deprecated.

    replacedBy: List of routines that have replaced the deprecated routine.
                If None, then routine is just going away.
    """
    # Get caller off of call stack
    callerFrame = sys._getframe(1)
    callerName = callerFrame.f_code.co_name
    callerFile = callerFrame.f_code.co_filename
    caller = callerFile + "." + callerName

    messages = [caller + " is deprecated."]
    if replacedBy:
        messages.append("Please call its replacement(s) in the future:")
        messages.extend(replacedBy)
    else:
        messages.append("There is no replacement for it.")

    _showMessages("DEPRECATED", messages)

    return None



def methodNotOverriddenError():
    """
    Called by methods that define interface, but not implementation.  Such
    methods have to be overriden by derived classes.  If they are ever called
    directly, they fail by calling this routine.
    """
    # Get caller off of call stack
    callerFrame = sys._getframe(1)
    callerName = callerFrame.f_code.co_name
    callerFile = callerFrame.f_code.co_filename
    caller = callerFile + "." + callerName

    fatalError([
        caller,
        "must be overridden by a derived class.  Methods that raise this",
        "error define interface but not implementation and must be",
        "overridden by any derived class that implements this interface."])




def statusMessage(messages):
    """
    Displays status message, encoded as a list of one or more strings,
    and then returns to the caller.
    """
    _showMessages("STATUS", messages)
    return None



# ------------------------------------------------------------------
# DEBUGGING SUBROUTINES
# ------------------------------------------------------------------

def debugging():
    """
    Return True if debugging is on, False if it is off.
    """
    return _debugging



def setDebugging(state):
    """
    Turn debugging on or off.

    True  - turn it on
    False - turn it off
    """
    global _debugging
    _debugging = state

    return _debugging



def debugMessage(messages):
    """
    Displays debugging messages if debugging is turned on.  Messages are
    passed in as a list of one or more strings.
    """

    sys.stderr.write("DEBUG:")

    for message in messages:
        sys.stderr.write("  " + message + "\n")
    sys.stderr.write("\n")

    return None



# ------------------------------------------------------------------
# Conversion SUBROUTINES
# ------------------------------------------------------------------


def boolToInt(boolean):
    """
    Converts a boolean value to an integer so that MySQL can cope.
    """
    if boolean:
        return 1
    else:
        return 0


def boolToJavaBool(boolean):
    """
    Converts a python boolean value to the constants used for
    booleans in Java.
    """
    if boolean:
        return "true"
    else:
        return "false"


def monthNameToInt(monthName):
    """
    Converts a three letter month abbreviation to an integer
    between 1 and 12
    """
    return _MONTHS[monthName]


# ------------------------------------------------------------------
# STRING MANIPULATION ROUTINES
# ------------------------------------------------------------------

def reduceName(name):
    """
    Reduces a string.  What does that mean?  It converts it to lower
    case and then strips out any characters that are not [A-Za-z0-9]
    """
    lowerName = name.lower()
    reducedName = ""
    for char in lowerName:
        if re.match("[A-Za-z0-9]", char):
            reducedName = reducedName + char

    return reducedName


def commaConcat(str1, str2):
    """
    Concats two string values with a ", " in between them.
    """
    return str1 + ", " + str2



def stripPublicIdPrefix(publicId):
    """
    Remove prefix from public database IDs.  This assumes that the prefix
    followed by a colon.  Everything up to and including the colon are
    removed.  If there is no colon in the ID then the ID is returned
    unchanged.
    """
    return publicId[publicId.find(":") + 1:]



# ------------------------------------------------------------------
# XML Utilty Routines
# ------------------------------------------------------------------


def addXmlElement(parent, elementName, elementValue=None):
    "Create and append an element to parent, return newly created element"

    # Unicode is the bane of my life
    
    return None



def getXmlElementValueByTagName(parent, tagName):
    """
    Given an XML tree to search, and a tag name, return the value of the single
    occurrence of that tag in the tree.

    Returns None if tagName does not exist, or has no value.  Throws an
    exception if it has more than one occurrence or more than one value.
    """
    value = None
    
    elements = parent.getElementsByTagName(tagName)
    
    if len(elements) > 0:
        if len(elements) > 1:
            fatalError([
                parent.nodeName + " contains more than 1 '" +
                tagName + "' elements."])
    
        if len(elements[0].childNodes) > 1:
            fatalError([
                "'" + elements[0].nodeName + "' has more than 1 child"])
        
        elif len(elements[0].childNodes) == 1:
            value = elements[0].firstChild.nodeValue

    return value



def writeXmlFile(xmlFileName, xmlDoc):
    """
    Write XML document to a file.
    """

    # Write the document.  This is more complicated then it should be because
    # Python XML produces attribute values quoted in single quotes by
    # default.  Java code can choke on this however, so as a final
    # step we convert these single quotes to double quotes.

    return None




# ------------------------------------------------------------------
# Configuration File Routines
# ------------------------------------------------------------------

def readConfiguration(configFileName, configDict, printConfig=False):
    """
    Reads in a configuration file in a 'standard' format into the
    configuration dictionary provided.

    The list of expected configuration parameters are the keys in
    configDict.  This routine populates the values in configDict.
    All values in configDict are expected to be None on input.

    Issues warnings if
     o expected configuration parameters are not found, or
     o unexpected configuration parameters are found.
     o a configuration parameter occurs twice in the file.

    The standard is just something we made up.  It just has a common
    format that all our python programs use.

    File format rules
    1. Blank lines and lines that start with '#' or ' ' are ignored.
    2. Lines that set parameters have
       o Parameter name, starting in first column
       o Followed by a space, an equals sign, and another space,
       o followed by the value to assign to that space.
    """
    message = ["Using Configuration in file " + configFileName + ":"]
    configFile = open(configFileName, "r")
    line = configFile.readline()
    while line != "":                   # while not at EOF
        if not line[0] in [" ", "#", "\n" ]:   # ignore blank lines and comments
            lineWithoutNewline = line[:-1]
            name, value = lineWithoutNewline.split(" = ")
            if name not in configDict:
                warning([
                    "Unexpected configuration parameter '" + name +
                    "' in configuration file.  Ignoring it."])
            elif configDict[name]:
                warning([
                    "Configuration parameter '" + name +
                    "' occurs more than once in configuration file.",
                    "Using first occurrence only."])
            else:
                configDict[name] = value
                if name.lower().rfind("password") < 0:
                    message.append(lineWithoutNewline)

        line = configFile.readline()

    for name, value in configDict.iteritems():
        if not value:
            warning([
                "No value for expected configuration parameter '" +
                name + "' occured in configuration file."])

    if printConfig:
        statusMessage(message)

    return configDict


# ------------------------------------------------------------------
# SEQUENCE ROUTINES
# ------------------------------------------------------------------

def isNtSequence(sequence):
    """
    Returns True if sequence is a valid nucleotide sequence, False otherwise.
    """
    return re.match(r"^[acgtn\s]+$", sequence, re.I)



def sequenceToFasta(sequence, lineSize = 10):
    """
    Convert a one line string into a multi-line FASTA string.
    """
    fastaString = ""

    sequence = sequence.replace(" ", "")

    # chop sequence into shorter lengths
    seqLen = len(sequence)
    processed = 0
    while processed < seqLen:
        fastaString += sequence[processed:processed + lineSize] + " "
        processed += lineSize

    return fastaString



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


