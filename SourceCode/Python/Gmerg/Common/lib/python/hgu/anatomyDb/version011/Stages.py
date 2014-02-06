#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Module to provide high-level access to the set of stages defined in
the ANA_STAGE table.  This module abstracts much of the low-level,
record-at-a-time direct database access in the AnaStageDb module.

This reads in all defined stages, and then provides in-memory access to
them from that point on.
"""

from hgu import Util

import Oids
import AnaStageDb  # low-level access


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_byOid      = None                 # set by initialise()
_bySequence = None                 # ditto
_byName     = None

# dictionary of stages by their public IDs.  If the stages for the
# current species don't have Public IDs then this will be empty.
_byPublicId = None


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def SequenceIterator(startSeq = None, stopSeq = None):
    """
    Return an iterator that walks stages in sequence (chronological)
    order.

    If startSeq is provided, it will start iterating at stage with that
    sequence.
    If stopSeq is provided, it will stop iterating at the stage with a
    sequence one less than that.

    In other words, startSeq and stopSeq follow the usual python
    semantics for specifying slices.
    """
    if startSeq == None:
        startSeq = 0
    if stopSeq == None:
        stopSeq = len(_bySequence)

    return iter(_bySequence[startSeq:stopSeq])


# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Read all stages into memory.

    This must be called after DbAccess.initialise() and before calling any
    other function in this module.
    """
    global _byOid, _bySequence, _byName, _byPublicId

    _byOid      = {}
    _bySequence = []
    _byName     = {}
    _byPublicId = {}

    for stage in AnaStageDb.SequenceIterator():
        _addToKnowledge(stage)

    # Verify that stage sequence starts at 0, and is dense.  Otherwise
    # this code will fail.
    for index, stage in enumerate(_bySequence):
        if index != stage.getSequence():
            Util.fatalError([
                "Stage sequence does not start at 0, and/or is not dense.",
                "Stage " + stage.getName() + " has index "
                 + str(index) +
                " but has sequence " + str(stage.getSequence()) + ".",
                "Code requires 0-relative, dense stage sequences."])

    return None


def getByOid(stageOid):
    """
    Return the stage with the given OID.  Fails if no stage has that OID.
    """
    return _byOid[stageOid]


def getBySequence(stageSeq):
    """
    Return the stage with the given sequence.  Fails if no stage has that
    sequence.
    """
    return _bySequence[stageSeq]


def getByName(stageName):
    """
    Return the stage with the given name.  Fails if no stage has that
    name.
    """
    return _byName[stageName]


def getByPublicId(publicId):
    """
    Return the stage with the given public Id.  Fails if no stage has
    that public ID.
    """
    return _byPublicId[publicId]


def getEarliest():
    """
    Return the earliest stage in the sequence.
    """
    return _bySequence[0]


def getLatest():
    """
    Return the latest stage in the sequence.
    """
    return _bySequence[-1]



def createStage(species, name, sequence,
                description = None, shortExtraText = None, publicId = None):
    """
    Create a new stage record.

    sequence must be one greater than the current maximum stage sequence value.
    """
    if sequence != len(_bySequence):
        Util.fatalError([
           "Attempt to create stage '" + name + "' out of sequence.",
           "New sequence must be 1 greater than current maximum sequence.",
           "Sequence of last existing stage: " + str(len(_bySequence)),
           "Sequence requested: " + str(sequence)])

    stg = AnaStageDb.AnaStageDbRecord()
    oid = Oids.createNextOid()

    stg.setOid(oid)
    stg.setSpecies(species)
    stg.setName(name)
    stg.setSequence(sequence)
    stg.setDescription(description)
    stg.setShortExtraText(shortExtraText)
    stg.setPublicId(publicId)

    stg.insert()
    _addToKnowledge(stg)

    return stg



def _addToKnowledge(stage):
    """
    Add this stage to this modules internal data structures.
    """
    _byOid[stage.getOid()] = stage
    _bySequence.append(stage)
    _byName[stage.getName()] = stage
    publicId = stage.getPublicId()
    if publicId != None:
        _byPublicId[publicId] = stage

    return


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Main does no initialisation.  It can't until the connection to the DB
# has been established.
#
# See initialise().
