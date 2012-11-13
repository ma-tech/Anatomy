#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to the set of anatomy database
versions defined in the ANA_VERSION table.  This module abstracts the
low-level, record-at-a-time direct database access in the AnaVersionDb
module.

This reads in all defined versions, and then provides in-memory access to
them from that point on.
"""

from hgu.anatomyDb.version010 import Oids
from hgu.anatomyDb.version010 import AnaVersionDb


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of all versions, indexed by version OID
_byOid = None


# The latest version.
_latest = None


# Version number to use if no versions exist yet.
DEFAULT_STARTING_NUMBER = 1


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator():
    """
    Return an iterator that walks all versions in no particular order.
    """
    return _byOid.itervalues()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all versions into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """

    global _byOid, _latest

    _byOid      = {}

    for ver in AnaVersionDb.Iterator():
        verOid = ver.getOid()
        _byOid[verOid] = ver

    # Want _latest to point to same python object as is in _byOid
    latest = AnaVersionDb.getLatestVersion()
    if latest == None:
        _latest = None
    else:
        _latest = getByOid(latest.getOid())

    return _latest


def getByOid(versionOid):
    """
    Return the version with the given OID.  Fails if no version has that OID.
    """
    return _byOid[versionOid]



def getLatest():
    """
    Return the most recent version.  Returns None if no versions exist.
    """
    return _latest



def createNextVersion(date, comments):
    """
    Given the date and comments for a database version, create version
    record for the next version of the db.

    This then becomes the latest version for the duration of the program.
    """
    global _latest

    newVer = AnaVersionDb.AnaVersionDbRecord()
    oid = Oids.createNextOid()

    if _latest == None:
        verNumber = DEFAULT_STARTING_NUMBER
    else:
        verNumber = _latest.getNumber() + 1
    newVer.setNumber(verNumber)

    newVer.setOid(oid)
    newVer.setDate(date)
    newVer.setComments(comments)

    newVer.insert()
    _byOid[oid] = newVer
    _latest = newVer

    return newVer



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
