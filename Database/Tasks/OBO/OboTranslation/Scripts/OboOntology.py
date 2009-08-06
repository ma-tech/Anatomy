#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************


Defines an OBO Ontology.  Currently this file builds a single global
OBO ontology.
"""

import OboEntry
import OboStream


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_header = None


# Originally stored entries by ID and namespace, but there is an
# assumption within the OBO file that IDs are unique across all
# namespaces that are defined in the file.  Therefore, now just store
# entries by ID.
_entriesById = None   # does not contain obsolete entries.
_obsoleteEntriesById = None

_entriesByNamespace = None # does not contain obsolete entries


# ------------------------------------------------------------------
# LOCAL ROUTINES
# ------------------------------------------------------------------

def _haveFamilyReunion():
    """
    After the entire OBO file has been read in make connections between
    the entries.
    """
    for entry in _entriesById.itervalues():
        entry.prepareForFamilyReunion()

    for entry in _entriesById.itervalues():
        entry.haveFamilyReunion()



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def buildOntologyFromFile(oboFile):
    """
    Given an OBO file, read that file and create an OBO ontology in this
    program based on the conents of that file.
    """
    global _header, _entriesById, _obsoleteEntriesById, _entriesByNamespace

    _entriesById = {}
    _obsoleteEntriesById = {}
    _entriesByNamespace = {} # unordered list for each namespace

    oboStream = OboStream.OboInputStream(oboFile)
    _header = oboStream.getHeader()
    defaultNamespace = _header.getDefaultNamespace()
    _entriesByNamespace[defaultNamespace] = []

    oboEntry = oboStream.getNextEntry()
    while not oboEntry.isEof():
        entryId = oboEntry.getId()
        if oboEntry.isObsolete():
            _obsoleteEntriesById[entryId] = oboEntry
        else:
            _entriesById[entryId] = oboEntry
            namespace = oboEntry.getNamespace()
            if namespace not in _entriesByNamespace:
                _entriesByNamespace[namespace] = []
            _entriesByNamespace[namespace].append(oboEntry)

        oboEntry = oboStream.getNextEntry()

    # Everything is now read in, connect the entities together.
    _haveFamilyReunion()

    return


def getById(entryId):
    """
    Return the non-obsolete entry with the given ID.  If no such
    entry exists then raise exception.
    """
    return _entriesById[entryId]



def getHeader():
    """
    Returns the header object for the ontology.
    """
    return _header


def spew(label = ""):
    """
    Debugging routine to just dump the ontology to the screen.
    """
    print "Spewing Ontology:", label
    _header.spew(label)
    print "Non-obsoleted Entries:", label
    for namespace in _entriesByNamespace.keys():
        print "Spewing entries in namespace", namespace
        for term in NamespaceIterator(namespace):
            term.spew()

    print
    return



def getAllStageWindows():
    """
    Return a dictionary, indexed by term ID, of the stage windows for each
    term.  Each entry in the dictionary is a tuple of the form
      (startStageId, stopStageId)
    If the start and/or stop stage is not defined for the entry then the
    ID will be None.
    """
    stageWindows = {}
    for entry in NamespaceIterator(getHeader().getDefaultNamespace()):
        if entry.getType() == OboEntry.TERM:
            stageWindows[entry.getId()] = entry.getStageWindowIds()

    return stageWindows



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------


def NamespaceIterator(namespace):
    """
    Iterate over all the non-obsolete entries in the given namespace,
    in no particular order.

    Raises exception of namespace does not exist.
    """
    return iter(_entriesByNamespace[namespace])




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------


