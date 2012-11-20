#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to the perspective ambits defined in
the ANA_PERSPECTIVE_AMBIT table.  This module abstracts much of the low-level,
record-at-a-time direct database access in the AnaPerspectiveAmbitDb module.

This reads in all defined ambit records, and then provides in-memory access to
them from that point on.
"""

from hgu import Util

# Low level DB imports
import AnaPerspectiveAmbitDb

# High level DB imports
import Oids


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of all ambit records, indexed by perspective name.  Each
# entry is a dictionary indexed by node OID.
_byPerspective = None

# Dictionary of all start ambit records, indexed by perspective name.  Each
# entry is a dictionary indexed by node OID.
_startsByPerspective = None

# Dictionary of all stop ambit records, indexed by perspective name.  Each
# entry is a dictionary indexed by node OID.
_stopsByPerspective = None


# Dictionary of all ambit records, indexed by node OID.  Each entry is
# a dictionary indexed by perspective name.
_byNodeOid = None



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _addToKnowledge(ambit):
    """
    Add the given record to this modules internal knowledge base.
    """
    perspective = ambit.getPerspectiveName()
    if perspective not in _byPerspective:
        _byPerspective[perspective] = {}
        _startsByPerspective[perspective] = {}
        _stopsByPerspective[perspective] = {}
    nodeOid = ambit.getNodeOid()
    if nodeOid not in _byNodeOid:
        _byNodeOid[nodeOid] = {}

    _byPerspective[perspective][nodeOid] = ambit
    if ambit.isStart():
        _startsByPerspective[perspective][nodeOid] = ambit
    if ambit.isStop():
        _stopsByPerspective[perspective][nodeOid] = ambit
    _byNodeOid[nodeOid][perspective] = ambit

    return


# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all perspective ambits into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """

    global _byPerspective, _startsByPerspective, _stopsByPerspective
    global _byNodeOid

    _byPerspective = {}
    _startsByPerspective = {}
    _stopsByPerspective = {}
    _byNodeOid = {}

    for ambit in AnaPerspectiveAmbitDb.Iterator():
        _addToKnowledge(ambit)

    for perspectiveName, startAmbit in _startsByPerspective.iteritems():
        if len(startAmbit) == 0:
            Util.fatalError([
                "Perspective '" + perspectiveName +
                "' has no start ambit records."])

    return None



def getAmbitForPerspective(perspectiveName):
    """
    Return a dictionary of ambit records for the given perspective.
    The dictionary is indexed by node OID.

    Raises an exception if the perspective has no ambit records.
    """
    return _byPerspective[perspectiveName]



def getStartAmbitForPerspective(perspectiveName):
    """
    Return a dictionary of ambit start records for the given perspective.
    The dictionary is indexed by node OID.

    Raises an exception if the perspective has no ambit records.
    """
    return _startsByPerspective[perspectiveName]



def getStopAmbitForPerspective(perspectiveName):
    """
    Return a dictionary of ambit stop records for the given perspective.
    The dictionary is indexed by node OID.

    Raises an exception if the perspective has no ambit records.
    """
    return _stopsByPerspective[perspectiveName]



def createPerspectiveAmbit(perspectiveName, nodeOid, isStart, isStop,
                           comments = None):
    """
    Create a new perspective ambit record.
    """

    ambit = AnaPerspectiveAmbitDb.AnaPerspectiveAmbitDbRecord()
    oid = Oids.createNextOid()

    ambit.setOid(oid)
    ambit.setPerspectiveName(perspectiveName)
    ambit.setNodeOid(nodeOid)
    ambit.setIsStart(isStart)
    ambit.setIsStop(isStop)
    ambit.setComments(comments)

    ambit.insert()
    _addToKnowledge(ambit)

    return ambit


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
