#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to synonyms in the anatomy database
defined in the ANA_SYNONYM table.  This module abstracts the
low-level, record-at-a-time direct database access in the AnaSynonymDb
module.

At initialisation, this reads in all synonyms, and then provides in-memory
access to them from that point on.
"""

from hgu.anatomyDb.version006 import AnaSynonymDb


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Dictionary of all synonyms, indexed by synonym OID
_byOid = None


# Dictionary of all synonyms, indexed by OID of object the synonyms are
# for.  Each entry is itself a list of synonym records.
_byOidSynonymIsFor = None



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator():
    """
    Return an iterator that walks all synonyms in no particular order.
    """
    return _byOid.itervalues()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all syonyms into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """

    global _byOid, _byOidSynonymIsFor

    _byOid       = {}
    _byOidSynonymIsFor = {}

    for syn in AnaSynonymDb.Iterator():
        synOid = syn.getOid()
        _byOid[synOid] = syn

        oidSynIsfor = syn.getOidSynonymIsFor()
        synsForObject = _byOidSynonymIsFor.get(oidSynIsfor)
        if synsForObject == None:
            _byOidSynonymIsFor[oidSynIsfor] = []
        _byOidSynonymIsFor[oidSynIsfor].append(syn)


    return None


def getByOid(synOid):
    """
    Return the synonym with the given OID.  Fails if no synonym has that OID.
    """
    return _byOid[synOid]




def getSynonymsForOid(oid):
    """
    Return the list of synonyms for the given OID.  Returns empty list
    if there are no synonyms for that OID.
    """
    syns = _byOidSynonymIsFor.get(oid)
    if syns == None:
        syns = []
    return syns



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
