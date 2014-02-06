#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the derived ANAD_RELATIONSHIP_TRANSITIVE table in a
GMERG style relational database.

This stores the transitive closure of all part-of relationships in
the ANA_RELATIONSHIP table.  Eventually it may store other types
of relationships as well.
"""

from hgu import Util

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord
from hgu.db import DbTable              # DB Tables

import Relationships


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANAD_RELATIONSHIP_TRANSITIVE"

OID_COLUMN                    = "RTR_OID"
RELATIONSHIP_TYPE_NAME_COLUMN = "RTR_RELATIONSHIP_TYPE_FK"
DESCENDENT_OID_COLUMN         = "RTR_DESCENDENT_FK"
ANCESTOR_OID_COLUMN           = "RTR_ANCESTOR_FK"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# NODE IN PERSPECTIVE RECORD
# ------------------------------------------------------------------

class AnadRelationshipTransitiveDbRecord:
    """
    Python wrapper around DbRecord containing an
    ANAD_RELATIONSHIP_TRANSITIVE database record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ANAD_RELATIONSHIP_TRANSITIVE database record.

        If dbRecord is None then set all columns to None.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)

        self.__dbRecord = dbRecord

        return None


    def getDbRecord(self):
        """
        Return the DbRecord for this object.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the database OID for this object.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def setOid(self, oid):
        """
        Set the database OID for this object.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, oid)


    def getRelationshipType(self):
        """
        Return the relationship type name.
        """
        return self.getDbRecord().getColumnValue(RELATIONSHIP_TYPE_NAME_COLUMN)

    def setRelationshipType(self, relType):
        """
        Set the relationship type name.
        """
        return self.getDbRecord().setColumnValue(RELATIONSHIP_TYPE_NAME_COLUMN,
                                                 relType)

    def getDescendentOid(self):
        """
        Return the datbase OID of the descendent record in this relationship.
        """
        return self.getDbRecord().getColumnValue(DESCENDENT_OID_COLUMN)

    def setDescendentOid(self, descendentOid):
        """
        Set the datbase OID of the descendent record in this relationship.
        """
        return self.getDbRecord().setColumnValue(DESCENDENT_OID_COLUMN,
                                                 descendentOid)


    def getAncestorOid(self):
        """
        Return the database OID of the ancestor record in this relationship.
        """
        return self.getDbRecord().getColumnValue(ANCESTOR_OID_COLUMN)

    def setAncestorOid(self, ancestorOid):
        """
        Set the database OID of the ancestor record in this relationship.
        """
        return self.getDbRecord().setColumnValue(ANCESTOR_OID_COLUMN,
                                                 ancestorOid)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return


    def spew(self, label = ""):
        """
        Debugging support routine.
        """
        self.getDbRecord().spew(label)



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator(where = None, orderBy = None):
    """
    Iterate through relationship records
    """
    return _table.Iterator(where = where, orderBy = orderBy)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByAncestorOid(ancestorOid, relType = Relationships.PART_OF):
    """
    Return the all records with given OID as ancestor.
    """

    if relType != Relationships.PART_OF:
        # Take this error check out if and when we support transitive
        # closure of other relationship types.
        Util.fatalError([
            "AnadRelationshipTransitive.getByAncestorOid() currently only",
            "works with " + Relationships.PART_OF + " relationships."])

    ancestorIter = Iterator(
        where = (
            ANCESTOR_OID_COLUMN + " = " +
            DbAccess.formatSqlValue(ancestorOid) +
            " and " +
            RELATIONSHIP_TYPE_NAME_COLUMN + " = " +
            DbAccess.formatSqlValue(relType)))

    rels = []

    for dbRec in ancestorIter:
        rels.append(dbRec)

    return rels




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't be set until AnaRelationshipTransitiveDbRecord
# is defined.

_table = DbTable.registerTable(TABLE_NAME, AnadRelationshipTransitiveDbRecord,
                               DbTable.NOT_IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,                    DbTable.IS_KEY)
_table.registerColumn(RELATIONSHIP_TYPE_NAME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(DESCENDENT_OID_COLUMN,         DbTable.IS_NOT_KEY)
_table.registerColumn(ANCESTOR_OID_COLUMN,           DbTable.IS_NOT_KEY)

