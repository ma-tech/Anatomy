#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************

Internal python structures to represent Synonyms
"""

import sets                             # builtin in 2.4

import AnatomyObject                    # Ties it all together
import DbAccess
import Util                             # Error handling



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# DB related

TABLE   = "ANA_SYNONYM"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_synonyms = None
_synonymsByText = None
_synonymsByReducedText = None



# ------------------------------------------------------------------
# SYNONYM
# ------------------------------------------------------------------

class Synonym:
    """
    Defines a synonym.
    """

    def __init__(self, synonymText, objectSynIsFor):
        """
        Generate a synonym, given the synonym text, and the object the
        synonym is for.
        """
        self.__anatomyObject = None
        self.__dbRecord = None
        self.__text = synonymText
        self.__reducedText = Util.reduceName(self.__text)
        self.__objectSynonymIsFor = objectSynIsFor
        return None


    def assignOid(self):
        """
        Synonyms don't have explicit creation dates in the CIOF file
        use the creation date from the object the synonym is for.
        """
        creationDateTime = self.getObjectSynonymIsFor().getCreationDateTime()
        self.__anatomyObject = AnatomyObject.AnatomyObject(
            self, creationDateTime = creationDateTime)
        self.__anatomyObject.addToKnowledgeBase()

    def getOid(self):
        """
        Return OId of synonym.
        """
        return self.__anatomyObject.getOid()

    def getSynonymText(self):
        """
        Return the synonym text.
        """
        return self.__text

    def getSynonymReducedText(self):
        """
        Return the reduced text version of the synonym.
        """
        return self.__reducedText

    def getObjectSynonymIsFor(self):
        """
        Return the object that the synonym is a synonym for.
        """
        return self.__objectSynonymIsFor

    def getObjectOidSynonymIsFor(self):
        """
        Return OID of the object the synonym is a synonym for.
        """
        return self.getObjectSynonymIsFor().getOid()

    def isDeleted(self):
        """
        Returns True if synonym is deleted.
        """
        # Look up isDeleted status every time as it can change.
        return self.getObjectSynonymIsFor().isDeleted()


    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fiels are returned as a list of strings.
        """
        return [str(self.getOid()),
                str(self.getObjectOidSynonymIsFor()),
                self.getSynonymText()]


    def addToKnowledgeBase(self):
        """
        Inserts the synonym into the knowledge
        base of all things we know about anatomy.
        """
        _synonyms.append(self)

        synText = self.getSynonymText()
        if synText in _synonymsByText:
            _synonymsByText[synText].append(self)
        else:
            _synonymsByText[synText] = [self]

        synReducedText = self.getSynonymReducedText()
        if synReducedText in _synonymsByReducedText:
            _synonymsByReducedText[synReducedText].append(self)
        else:
            _synonymsByReducedText[synReducedText] = [self]

        return None


    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        """
        Return database record for this synonym.
        """
        return self.__dbRecord


    def setDbInfo(self, dbRecord):
        """
        Associates a DB record with this object, and vice versa,
        and looks up the AnatomyObject for this synonym.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
            self.assignOid()
        else:
            # we have a db record, means we also have an AnatomyObject
            dbRecord.bindPythonObject(self)
            oid = dbRecord.getColumnValue("SYN_OID")
            self.__anatomyObject = AnatomyObject.bindAnatomyObject(oid, self)

        self.__dbRecord = dbRecord

        return dbRecord



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all synonyms, including deleted ones.
    """

    def __init__(self):
        self.__length = len(_synonyms)
        self.__position = -1         # Most recent synonym returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return next synonym.
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _synonyms[self.__position]



class AllIterByReducedText:
    """
    Iterate through all groups of synonyms, grouped by reduced text.

    Each call to the iterator returns a list of synonyms that
    all have the same reduced text.

    :TODO: This is problematic as it returns deleted synonyms as well.
    """

    def __init__(self):
        self.__length = len(_synonymsByReducedText)
        self.__reducedTexts = _synonymsByReducedText.keys()
        self.__reducedTexts.sort()
        self.__position = -1         # Most recent group returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Return the next set of synonyms, grouped by reduced text.
        """
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _synonymsByReducedText[self.__reducedTexts[self.__position]]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Initialisation for Synonyms
    """
    global _synonyms, _synonymsByText, _synonymsByReducedText

    # Define a series of data structures to hold the CIOF entities,
    # organised by their various parts.
    _synonyms = []                      # unindexed
    _synonymsByText = {}
    _synonymsByReducedText = {}

    tableInfo = DbAccess.registerClassTable(Synonym, TABLE,
                                            DbAccess.IN_ANA_OBJECT)
    # setup mappings betweeen Ciof values and db values
    tableInfo.addColumnMethodMapping(
        "SYN_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "SYN_OBJECT_FK", "getObjectOidSynonymIsFor", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "SYN_SYNONYM", "getSynonymText", DbAccess.IS_NOT_KEY)

    return None


def readDb():
    """
    Read in synonym records from the database.
    """
    allSynonyms = DbAccess.readClassAll(Synonym)

    for synRec in allSynonyms:
        # Tie synonym record to synonym object via Object OID FK,
        # and synonym text.
        synText = synRec.getColumnValue("SYN_SYNONYM")
        objectSynIsForOid = synRec.getColumnValue("SYN_OBJECT_FK")

        synonym = None
        if synText in _synonymsByText:
            syns = _synonymsByText[synText]
            for syn in syns:
                if syn.getObjectOidSynonymIsFor() == objectSynIsForOid:
                    synonym = syn
                    break

        if synonym:
            synonym.setDbInfo(synRec)
        else:
            synRec.registerAsOrphan()

    return None


def getByReducedText(reducedText):
    """
    Return a list of undeleted synonyms with the same reduced text as is passed in.

    Returns empty list if there are no undeleted synonyms with that reduced text.
    """
    synList = []
    if reducedText in _synonymsByReducedText:
        for syn in _synonymsByReducedText[reducedText]:
            if not syn.isDeleted():
                synList.append(syn)
    return synList



def getByText(text):
    """
    Return a list of undeleted synonyms with the same text as is passed in.

    Returns empty list if there are no synonyms with that text.
    """
    synList = []
    if text in _synonymsByText:
        for syn in _synonymsByText[text]:
            if not syn.isDeleted():
                synList.append(syn)
    return synList



def genReducedNameSet():
    """
    Generate set containing all reduced names for undeleted synonyms.

    The returned set is then owned by the caller.
    """
    reducedNameSet = sets.Set()
    for reducedName, syns in _synonymsByReducedText.iteritems():
        for syn in syns:
            if not syn.isDeleted():
                reducedNameSet.add(reducedName)
                break

    return reducedNameSet




def report():
    """
    Report synonyms that might need further investiageiton
    """
    reducedSyns = _synonymsByReducedText.keys()
    reducedSyns.sort()
    prevSynText = ""
    for reducedSyn in reducedSyns:
        prevSyn = None
        prevNode = None
        for synonym in _synonymsByReducedText[reducedSyn]:
            if not synonym.isDeleted():
                synText = synonym.getSynonymText()
                if (prevSyn != None and
                    prevSynText != synText):
                    obj = synonym.getObjectSynonymIsFor()
                    Util.warning([
                        "Synonyms differ only in capitalisation or "
                        "punctuation.",
                        "May want to make them be identical text.",
                        "  '" + prevSynText +
                        "' (" + prevNode.getPublicId() + ")",
                        "       Stages: [" + prevNode.genTimedNodesString() + "]",
                        "  '" + synText +
                        "' (" + obj.getPublicId() + ")",
                        "       Stages: [" + obj.genTimedNodesString() + "]",])
                prevSyn = synonym
                prevSynText = synText
                prevNode = synonym.getObjectSynonymIsFor()

    return None



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above

