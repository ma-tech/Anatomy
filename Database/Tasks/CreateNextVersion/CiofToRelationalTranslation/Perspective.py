#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Internal python structures to represent anatomy perspectives.
Perspective definitions are not listed in the CIOF file.
"""
import sets

import DbAccess
import Util                             # Error handling



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# DB

TABLE   = "ANA_PERSPECTIVE"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------


_perspectivesByName = None
_perspectives = None


# ------------------------------------------------------------------
# PERSPECTIVE
# ------------------------------------------------------------------

class Perspective:
    """
    Defines an anatomy database perspective, which is a subset of the
    anatomy database.
    """

    def __init__(self, dbRecord):
        """
        Create an anatomy perspective given a perspective
        record from the database.
        """
        self.__name = dbRecord.getColumnValue("PSP_NAME")
        self.__comments = dbRecord.getColumnValue("PSP_COMMENTS")
        self.__ambitStartNodes = sets.Set()
        self.__ambitStopNodes = sets.Set()
        dbRecord.bindPythonObject(self)
        self.__dbRecord = dbRecord

        return None


    def getName(self):
        """
        Return perspective name.
        """
        return self.__name

    def getComments(self):
        """
        Return description of perspective.
        """
        return self.__comments

    def addToKnowledgeBase(self):
        """
        Inserts the anatomy perspective into the knowledge
        base of all things we know about anatomy.
        """
        perspectiveName = self.getName()
        if perspectiveName in _perspectivesByName:
            Util.fatalError([perspectiveName + " alaready defined"])
        _perspectivesByName[perspectiveName] = self
        _perspectives.append(self)

        return None


    def addAmbitNode(self, perspectiveAmbit):
        """
        Add a border node to the perspective's ambit lists.

        Perspective ambits are a minimal defintion of the border around
        a perspective.
        """
        node = perspectiveAmbit.getNode()
        if perspectiveAmbit.isStart():
            self.__ambitStartNodes.add(node)
        if perspectiveAmbit.isStop():
            self.__ambitStopNodes.add(node)

        return None



    def getStartNodes(self):
        """
        Return the set of start nodes for this perspective.
        """
        return self.__ambitStartNodes



    def getStopNodes(self):
        """
        Return the set of stop nodes for this perspective.
        """
        return self.__ambitStopNodes


    def hasStartNode(self, node):
        """
        Returns True if the given node is a start node for this perspective.
        """
        return node in self.__ambitStartNodes



    def hasStopNode(self, node):
        """
        Returns True if the given node is a stop node for this perspective.
        """
        return node in self.__ambitStopNodes



    def getDbRecord(self):
        """
        Return the database record for this perspective.
        """
        return self.__dbRecord



    def spew(self, label=""):
        """
        debugging routine.  Displays contents of perspective
        """
        print "Perspective:", label
        print "Name:", self.getName()
        print "Comment:" + self.getComments()

        return None





# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# PERSPECTIVE ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all anatomy perspectives
    """

    def __init__(self):
        self.__length = len(_perspectives)
        self.__position = -1         # Most recent returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return next perspective.
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _perspectives[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Initialise meta data for perspectives.
    """
    global _perspectivesByName, _perspectives

    _perspectivesByName = {}
    _perspectives = []              # unindexed

    tableInfo = DbAccess.registerClassTable(Perspective, TABLE,
                                            DbAccess.NOT_IN_ANA_OBJECT)

    # Map instance methods to columns
    tableInfo.addColumnMethodMapping(
        "PSP_NAME", "getName", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "PSP_COMMENTS", "getComments",
        DbAccess.IS_NOT_KEY)

    return None


def readDb():
    """
    Read in perspectives from database.
    """

    allPerspectives = DbAccess.readClassAll(Perspective)

    for dbPerspective in allPerspectives:
        perspective = Perspective(dbPerspective)
        perspective.addToKnowledgeBase()

    return None


def getByName(perspectiveName):
    """
    return perspective with this name.
    """
    if perspectiveName in _perspectivesByName:
        return _perspectivesByName[perspectiveName]
    else:
        return []



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above


