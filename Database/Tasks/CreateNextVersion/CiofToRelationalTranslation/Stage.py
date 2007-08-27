#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Internal python structures to represent stages.

import AnatomyObject                    # Ties it all together
import Ciof                             # CIOF entities and attributes
import DbAccess
import Species
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Database related defs:

TABLE   = "ANA_STAGE"



# ------------------------------------------------------------------
# STAGE
# ------------------------------------------------------------------

class Stage:
    """
    Defines a developmental stage.  
    """

    def __init__(self, name, species, sequence):
        """
        Create a stage in the canonical staging series.
        """
        # Stages do not have an explicit create date in the CIOF file.
        # Also, they are the first thing we read from the CIOF file,
        # so, deriving them from something else at this point won't work
        # because nothing else has been read in.
        # :TODO: Figure out what creation date for stages should be.
        #   Could derive them from the earliest creation date of any anatomy
        #   timed node that exists at that stage.  This is approximately
        #   right for everything but TS28.
        
        self.__anatomyObject = None
        self.__dbRecord   = None
        self.__name       = name
        self.__species    = species
        self.__sequence   = sequence
        self.__description = None
        self.__shortExtraText = None
        self.__publicId   = None        # only used by Xenopus
        self.__isDeleted = False
        
        return None

    def getName(self):
        return self.__name

    def getSpecies(self):
        return self.__species

    def getSequence(self):
        return self.__sequence

    def assignOid(self):
        self.__anatomyObject = AnatomyObject.AnatomyObject(self)
        self.__anatomyObject.addToKnowledgeBase()

        return self.getOid()

    def getOid(self):
        return self.__anatomyObject.getOid()

    def getDescription(self):
        return self.__description

    def getShortExtraText(self):
        """Return the short extra text for this stage."""
        return self.__shortExtraText

    def getPublicId(self):
        """
        Mouse stages don't yet have public IDs.  This should always be None.
        """
        return self.__publicId


    def isDeleted(self):
        return self.__isDeleted

    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fiels are returned as a list of strings.
        """
        return [str(self.getOid()), self.getSpecies(), self.getName(),
                str(self.getSequence()), self.getDescription()]


    def addToKnowledgeBase(self):
        """
        Add this stage to what we know.
        """
        _stagesByName[self.getName()] = self
        _stagesBySequence[self.getSequence()] = self
        _stages.append(self)

        return None


    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        return self.__dbRecord


    def setDbInfo(self, dbRecord):
        """
        Associates a DB record with this object, and vice versa, 
        and looks up the AnatomyObject for this stage.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
            self.assignOid()
        else:
            # we have a db record, means we also have an AnatomyObject
            dbRecord.bindPythonObject(self)
            oid = dbRecord.getColumnValue("STG_OID")
            self.__anatomyObject = AnatomyObject.bindAnatomyObject(oid, self)
        
        self.__dbRecord = dbRecord

        return dbRecord



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all stages
    """

    def __init__(self):
        self.__length = len(_stages)
        self.__position = -1         # Most recent stage returned
        return None

    def __iter__(self):
        return self

    def next(self):
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _stages[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def initialise():
    """
    Initialisation for Stages
    """
    global _stagesByName, _stagesBySequence, _stages

    # Define a series of data structures to hold the CIOF entities,
    # organised by their various parts.
    _stagesByName = {}                  # Indexed by stage name, e.g. TS01
    _stagesBySequence = {}              # Indexed by stage sequence
    _stages = []                        # no index

    tableInfo = DbAccess.registerClassTable(Stage, TABLE, 
                                            DbAccess.IN_ANA_OBJECT)
    tableInfo.addColumnMethodMapping(
        "STG_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "STG_SPECIES_FK", "getSpecies", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "STG_NAME", "getName", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "STG_SEQUENCE", "getSequence", DbAccess.IS_NOT_KEY)

    # Don't register description, extra text, or public ID.  These will never
    # occur in the CIOF file, therefore don't care if they are different.
    # tableInfo.addColumnMethodMapping(
    #     "STG_DESCRIPTION", "getDescription", DbAccess.IS_NOT_KEY)
    # tableInfo.addColumnMethodMapping(
    #     "STG_SHORT_EXTRA_TEXT", "getShortExtraText", DbAccess.IS_NOT_KEY)
    # tableInfo.addColumnMethodMapping(
    #     "STG_PUBLIC_ID", "getPublicId", DbAccess.IS_NOT_KEY)

    return None



def addStagesToKnowledgeBase(stagesEntity):
    """
    Creates stages in knowledge base, based on contents of Stage entity,
    which defines all the stages, in time order.
    """
    stageNames = stagesEntity.getAttributeValues("Time")
    sequence = 0                        # Assumes stages listed in time order

    for stageName in stageNames:

        # Deal with trailing comma in stage list
        if stageName != "":
            stage = Stage(stageName, Species.MOUSE, sequence)

            if stageName in _stagesByName:
                Util.fatalError(["Stage with name '" + stageName +
                                 "' already defined."])

            # We have a clean stage.  Add it.
            stage.addToKnowledgeBase()
            sequence = sequence + 1

    return None


def readDb():
    """
    Read in stage records from the database.
    """
    allStages = DbAccess.readClassAll(Stage)

    for stgRec in allStages:
        stgName = stgRec.getColumnValue("STG_NAME")
        stage = getByName(stgName)
        if stage:
            stage.setDbInfo(stgRec)
        else:
            stgRec.registerAsOrphan()

    return None



def getByName(stgName):
    return _stagesByName[stgName]


def getBySequence(stgSequeunce):
    return _stagesBySequence[stgSequence]



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above.

