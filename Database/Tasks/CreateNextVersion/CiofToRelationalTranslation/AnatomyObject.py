#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Internal python structures to represent the anatomy object table

import AnatomyBase                      # Ties it all together
import DbAccess
import Util                             # Error handling



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# Internal, Database related.

_TABLE   = "ANA_OBJECT"



# ------------------------------------------------------------------
# ANATOMY OBJECT
# ------------------------------------------------------------------

class AnatomyObject:
    """
    Anatomy object is the common table to which all/most persistent
    OIDs in anatomy tables point to.  
    """

    def __init__(self, objectThisIsFor = None, creationDateTime = None,
                 creator = None, dbRecord = None):
        """
        Generate an anatomy object, given either a DB record, or
        information from a CIOF file.
        """
        self.__objectThisIsFor = objectThisIsFor
        if dbRecord:
            self.__oid = dbRecord.getColumnValue("OBJ_OID")
            self.__creationDateTime = dbRecord.getColumnValue("OBJ_CREATION_DATETIME")
            self.__creatorOid = dbRecord.getColumnValue("OBJ_CREATOR_FK")
        else:
            self.__oid = _genNextOid()
            self.__creationDateTime = creationDateTime
            if creator:                 # heavy question
                self.__creatorOid = creator.getOid()
            else:
                self.__creatorOid = None
        self.setDbRecord(dbRecord)

        return None


    def getOid(self):
        return self.__oid

    def getObjectThisIsFor(self):
        return self.__objectThisIsFor

    def setObjectThisIsFor(self, objectThisIsFor):
        self.__objectThisIsFor = objectThisIsFor

    def getCreationDateTime(self):
        return self.__creationDateTime

    def getCreatorOid(self):
        return self.__creatorOid

    def genDumpFields(self):
        """
        Generate all fields that from this object that will be loaded
        into a database, in the order the fields occur in the target
        table.  The fiels are returned as a list of strings.
        """
        dt = self.getCreationDateTime()
        if dt != None:
            dt = self.getCreationDateTime().isoformat(' ')

        creatorOid = self.getCreatorOid()
        if creatorOid:
            creatorOid = str(creatorOid)
        else:
            creatorOid = None

        return [str(self.getOid()), dt, creatorOid]


    def addToKnowledgeBase(self):
        """
        Hmm.  Not sure what the semantics are here.  The ID is allocated
        by the constructor.  However, the object won't end up in the DB
        until this is called.  Sounds fair.
        """
        _objects.append(self)
        _objectsByOid[self.getOid()] = self
        
        return None


    def spew(self, label=""):
        """
        debugging routine.  Displays contents of anatomy object
        """
        print "Object:", label
        print "OID:   ", self.getOid()
        print "Creation:   ", self.getCreationDateTime()

        return None


    # --------------------------
    # Database Methods
    # --------------------------

    def getDbRecord(self):
        return self.__dbRecord


    def setDbRecord(self, dbRecord):
        """
        Associates a DB record with this object and vice versa.

        If no DB record is passed in, then this creates an empty DB Record.
        """
        if not dbRecord:
            dbRecord = DbAccess.DbRecord(pythonObject = self)
        else:
            dbRecord.bindPythonObject(self)
        self.__dbRecord = dbRecord
       
        return dbRecord


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _genNextOid():
    global _lastGlobalOid
    _lastGlobalOid = _lastGlobalOid + 1
    return _lastGlobalOid



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------

class AllIter:
    """
    Iterate through all anatomy objects
    """

    def __init__(self):
        global _objects
        self.__length = len(_objects)
        self.__position = -1         # Most recent anatomy node returned
        return None

    def __iter__(self):
        return self

    def next(self):
        global _objects
        self.__position += 1
        if self.__position == self.__length:
            raise StopIteration
        return _objects[self.__position]



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise():
    """
    Initialise information relating to anatomy objects.

    Reads in every anatomy object definition in Database
    """
    global _lastGlobalOid, _objects, _objectsByOid
    
    _objects = []              # unindexed
    _objectsByOid = {}

    tableInfo = DbAccess.registerClassTable(AnatomyObject, _TABLE,
                                            DbAccess.NOT_IN_ANA_OBJECT)
    # Map instance methods to columns
    tableInfo.addColumnMethodMapping(
        "OBJ_OID", "getOid", DbAccess.IS_KEY)
    tableInfo.addColumnMethodMapping(
        "OBJ_CREATION_DATETIME", "getCreationDateTime", DbAccess.IS_NOT_KEY)
    tableInfo.addColumnMethodMapping(
        "OBJ_CREATOR_FK", "getCreatorOid", DbAccess.IS_KEY)

    _lastGlobalOid = DbAccess.readMaxOid(AnatomyObject)

    allObjs = DbAccess.readClassAll(AnatomyObject)

    for dbRec in allObjs:
        anaObj = AnatomyObject(dbRecord = dbRec)
        anaObj.addToKnowledgeBase()

    return None


def getByOid(oid):
    return _objectsByOid[oid]


def bindAnatomyObject(oid, objectThisIsFor):
    anaObj = getByOid(oid)
    anaObj.setObjectThisIsFor(objectThisIsFor)

    return anaObj



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.  See initialise above.



