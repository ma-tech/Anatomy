#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************


Internal python structures to represent Attributions
"""

import AnatomyObject                    # Ties it all together
import DbAccess
import Editor
import Relationship                     #
import Source
import TimedNode
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


TABLE = "ANA_ATTRIBUTION"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_attributions = None
_attributionsByObjectSourceEvidence = None


# ------------------------------------------------------------------
# ATTRIBUTION
# ------------------------------------------------------------------

class Attribution:
    """
    Defines a attribution.
    """

    def __init__(self, ciofEntity):
        """
        Generate a attribution
        """
        self.__anatomyObject = None
        self.__dbRecord      = None
        self.__ciofEntity = ciofEntity

        # All attributions from CIOF file are for lineage.

        parentPublicId = "EMAP:" + self.__ciofEntity.getAttributeValue("From")
        childPublicId = "EMAP:" + self.__ciofEntity.getAttributeValue("To")
        relationships = Relationship.getByChildAndParentPublicIds(
            childId = childPublicId, parentId = parentPublicId)
        if len(relationships) > 1:
            Util.fatalError([
                str(len(relationships)) + " relationships between child " +
                childPublicId + " and parent " +
                parentPublicId,
                "found when 1 was expected."])
        elif len(relationships) == 0:
            Util.fatalError([
                "No lineage relationship found between child " +
                childPublicId + " and parent " + parentPublicId])
        self.__objectAttributionIsFor = relationships[0]

        self.__source = Source.getByAlias(
                              self.__ciofEntity.getAttributeValue("Reference"))
        self.__evidence = self.__ciofEntity.getAttributeValue("EvidenceType")
        self.__comments = self.__ciofEntity.getAttributeValuesJoined("Comments")
        childAtn  = TimedNode.getByPublicId(childPublicId)
        parentAtn = TimedNode.getByPublicId(parentPublicId)
        if childAtn.isDeleted() or parentAtn.isDeleted():
            self.__isDeletedAttribution = True
        else:
            self.__isDeletedAttribution = False
        return None

    def assignOid(self):
        """
        Assign an OID to this attribution.
        """
        creationDateTime = self.__ciofEntity.getCreationDateTime()
        creatorName = self.__ciofEntity.getAttributeValue("Creator").strip('"')
        creator = Editor.getByName(creatorName)
        self.__anatomyObject = AnatomyObject.AnatomyObject(
            self, creationDateTime = creationDateTime, creator = creator)
        self.__anatomyObject.addToKnowledgeBase()

        return self.getOid


    def getOid(self):
        """
        Return this attribution's OID.
        """
        return self.__anatomyObject.getOid()

    def getAnatomyObject(self):
        """
        Return the anatomy object (OID definition) for this attribution.
        """
        return self.__anatomyObject

    def getObjectAttributionIsFor(self):
        """
        Return the object that this attribution is about.
        """
        return self.__objectAttributionIsFor

    def getObjectOidAttributionIsFor(self):
        """
        Return the OID of the object that this attribution is about.
        """
        return self.__objectAttributionIsFor.getOid()

    def getSource(self):
        """
        Get source cited by this attribution.
        """
        return self.__source

    def getSourceOid(self):
        """
        Get OID of source that is cited by this attribution.
        """
        return self.__source.getOid()

    def getEvidence(self):
        """
        Get evidence that supports this atribution.
        """
        return self.__evidence

    def getComments(self):
        """
        Get comments for this attribution.
        """
        return self.__comments

    def isDeleted(self):
        """
        Return true if this attribution is deleted, false otherwise.
        """
        return self.__isDeletedAttribution

    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.

        The fields are returned as a list of strings.
        """
        objId = str(self.getObjectOidAttributionIsFor())
        srcId = str(self.getSourceOid())
        return [str(self.getOid()), objId, srcId,
                self.getEvidence(), self.getComments()]


    def addToKnowledgeBase(self):
        """
        Inserts the attribution into the knowledge
        base of all things we know about anatomy.
        """
        _attributions.append(self)

        altKey = self.getObjectAttributionIsFor(), self.getSource(), self.getEvidence()

        if altKey in _attributionsByObjectSourceEvidence:
            self.getObjectAttributionIsFor().spew()
            Util.fatalError([
                "Two attributions with same alternate key exist:"])
        else:
            _attributionsByObjectSourceEvidence[altKey] = self

        return None



    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        """
        Return the database record for this attribution.
        """
        return self.__dbRecord


    def setDbInfo(self, dbRecord):
        """
        Associates a DB record with this object, and vice versa,
        and looks up the AnatomyObject for this attribution.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
            self.assignOid()
        else:
            # we have a db record, means we also have an AnatomyObject
            dbRecord.bindPythonObject(self)
            oid = dbRecord.getColumnValue("ATR_OID")
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
    Iterate through all attributions, inlcuding deleted ones.
    """

    def __init__(self):
        self.__length = len(_attributions)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Get next attribution.
        """
        self.__position = self.__position + 1
        if self.__position == self.__length:
            raise StopIteration
        return _attributions[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Initialise for attributions.
    """
    global _attributions, _attributionsByObjectSourceEvidence

    _attributions = []                  # unindexed list
    _attributionsByObjectSourceEvidence = {}

    tableInfo = DbAccess.registerClassTable(Attribution, TABLE,
                                            DbAccess.IN_ANA_OBJECT)
    # setup mappings betweeen Ciof values and db values
    tableInfo.addColumnMethodMapping(
        "ATR_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "ATR_OBJECT_FK", "getObjectOidAttributionIsFor", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ATR_SOURCE_FK", "getSourceOid", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ATR_EVIDENCE_FK", "getEvidence", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "ATR_COMMENTS", "getComments", DbAccess.IS_NOT_KEY)

    return None


def readDb():
    """
    Read in attribution records from the database.
    """
    allAttribs = DbAccess.readClassAll(Attribution)

    for attribRec in allAttribs:
        # Tie attribution record to attribution object via alternate key
        objectOidAttrIsFor = attribRec.getColumnValue("ATR_OBJECT_FK")
        objectAttrIsFor = (
            AnatomyObject.getByOid(objectOidAttrIsFor).getObjectThisIsFor())
        sourceOid = attribRec.getColumnValue("ATR_SOURCE_FK")
        source = Source.getByOid(sourceOid)
        evidence = attribRec.getColumnValue("ATR_EVIDENCE_FK")

        attrib = getByObjectSourceEvidence(objectAttrIsFor, source, evidence)
        if attrib:
            attrib.setDbInfo(attribRec)
        else:
            attribRec.registerAsOrphan()

    return None



def getByObjectSourceEvidence(objAttribIsFor, source, evidence):
    """
    Does what it says.
    """
    altKey = objAttribIsFor, source, evidence
    return _attributionsByObjectSourceEvidence.get(altKey)


# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above
