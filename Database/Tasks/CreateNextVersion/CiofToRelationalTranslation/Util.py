#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Utility routines 

import re
import sys                              # error reporting

# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# ERROR / WARNING SUBROUTINES
# ------------------------------------------------------------------

def fatalError(messages):
    """
    Displays error message, encoded as a list of one or more strings, 
    and then raises a generic exception
    """
    sys.stderr.write("ERROR:")
    
    for message in messages:
        sys.stderr.write("  " + message + "\n")

    sys.stderr.write("\n")
    raise Exception


def warning(messages):
    """
    Displays warning message, encoded as a list of one or more strings, 
    and then returns to the caller
    """
    sys.stderr.write("WARNING:")
    
    for message in messages:
        sys.stderr.write("  " + message + "\n")
    sys.stderr.write("\n")

    return None


def statusMessage(messages):
    """
    Displays status message, encoded as a list of one or more strings, 
    and then returns to the caller.
    """
    sys.stderr.write("STATUS:")
    
    for message in messages:
        sys.stderr.write("  " + message + "\n")
    sys.stderr.write("\n")

    return None



# ------------------------------------------------------------------
# DEBUGGING SUBROUTINES
# ------------------------------------------------------------------

def debugging():
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

def monthNameToInt(monthName):
    """
    Converts a three letter month abbreviation to an integer
    between 1 and 12
    """
    return _months[monthName]


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


# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

def initialise(debugging = False):

    global _months, _debugging

    _debugging = debugging

    _months = {'Jan': 1,
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
