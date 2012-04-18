#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to the set of defined OIDs in the
ANATOMY database.  These are defined in the ANA_OBJECT table and therefore
this file should be called Objects.py, but I couldn't bring myself to
create such a poorly named file.

Low level, record at a time, access to the ANA_OBJECT table is provided
by the AnaObjectDb moudle.

This reads in all defined OIDs, and then provides in-memory access to
them from that point on.
"""

from hgu.anatomyDb.version006 import AnaObjectDb


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Version number to use if no OIDs exist yet.
DEFAULT_STARTING_OID = 1



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of all objects, indexed by version OID
_byOid = None


# The maximum currently allocated OID.
_maxOid = DEFAULT_STARTING_OID



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------


def Iterator():
    """
    Return an iterator that walks all objects in no particular order.
    """
    return _byOid.itervalues()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all anatomy OIDs into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """

    global _byOid, _maxOid

    _byOid      = {}

    for obj in AnaObjectDb.Iterator():
        oid = obj.getOid()
        _byOid[oid] = obj
        if oid > _maxOid:
            _maxOid = oid

    return None


def getByOid(oid):
    """
    Return the object record with the given OID.  Fails if no record has that OID.
    """
    return _byOid[oid]




def createNextOid(creationDatetime = None, creatorOid = None):
    """
    Create the next ANA_OBJECT record.  Returns the OID of the new ANA_OBJCT.

    All arguments are optional.
    """
    global _maxOid

    newObj = AnaObjectDb.AnaObjectDbRecord()
    _maxOid += 1

    newObj.setOid(_maxOid)
    newObj.setCreationDatetime(creationDatetime)
    newObj.setCreatorOid(creatorOid)

    _byOid[_maxOid] = newObj

    newObj.insert()

    return _maxOid




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().

