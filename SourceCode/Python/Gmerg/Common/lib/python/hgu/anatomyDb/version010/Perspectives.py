#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to the perspectives defined in
the ANA_PERSPECTIVE and ANA_PERSPECTIVE_AMBIT tables.  This module
abstracts much of the low-level, record-at-a-time direct database access
in the AnaPerspectiveDb and AnaPerspectiveAmbit modules.

This reads in all defined perspetives, and then provides in-memory access
to them from that point on.
"""

from hgu import Util

# Low level DB access.
from hgu.anatomyDb.version010 import AnaPerspectiveDb

# High level DB access
from hgu.anatomyDb.version010 import PerspectiveAmbits

# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of perspectives, indexed by name

_byName      = None         # set by initialise()



# ------------------------------------------------------------------
# PERSPECTIVES
# ------------------------------------------------------------------

class Perspective:
    """
    Defines an anaotmy database perspective, which is a subset of
    the anatomy.
    """

    def __init__(self, dbRecord):
        """
        Create a perspective given the ANA_PERSPECTIVE record that
        defines it.
        """
        self.__dbRecord = dbRecord


    def getName(self):
        """
        Return the name of the perspective.
        """
        return self.__dbRecord.getName()


    def getAmbit(self):
        """
        Return list of ambit records that defines the ambit (border)
        of this perspective.
        """
        return PerspectiveAmbits.getAmbitForPerspective(self.getName())


    def getStartAmbit(self):
        """
        Return list of this perspective's ambit records that are start nodes.
        """
        return PerspectiveAmbits.getStartAmbitForPerspective(self.getName())


    def getStopAmbit(self):
        """
        Return list of this perspective's ambit records that are stop nodes.
        """
        return PerspectiveAmbits.getStopAmbitForPerspective(self.getName())



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator():
    """
    Return an iterator that walks all perspectives in no particular
    order
    """
    return _byName.itervalues()


# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all perspectives into memory.

    This must be called after DbAccess.initialise() and Nodes.initialise
    and before calling any other function in this module.
    """
    global _byName

    _byName = {}

    for perspectiveRec in AnaPerspectiveDb.Iterator():
        perspective = Perspective(perspectiveRec)
        _byName[perspective.getName()] = perspective

    return None


def getByName(perspectiveName):
    """
    Return the perspective with the given name.  Fails if no perspective has
    that name.
    """
    return _byName[perspectiveName]



def createPerspective(name, comments):
    """
    Create a new perspective record.
    """
    if name in _byName:
        Util.fatalError([
            "Attempt to create perspective with name '" + name +
            "' when perspective with that name already exists."])
    perspective = AnaPerspectiveDb.AnaPerspectiveDbRecord()
    perspective.setName(name)
    perspective.setComments(comments)
    perspective.insert()
    _byName[name] = perspective

    return perspective



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
