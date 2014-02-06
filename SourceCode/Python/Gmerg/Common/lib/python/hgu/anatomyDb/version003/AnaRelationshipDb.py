#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Wrapper around the ANA_RELATIONSHIP table in a GMERG style relational
# database.  Relationships can be between nodes or timed nodes.
#

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord             # DB Records
from hgu.db import DbTable              # DB Tables



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_RELATIONSHIP"

OID_COLUMN                    = "REL_OID"
RELATIONSHIP_TYPE_NAME_COLUMN = "REL_RELATIONSHIP_TYPE_FK"
CHILD_OID_COLUMN              = "REL_CHILD_FK"
PARENT_OID_COLUMN             = "REL_PARENT_FK"




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# NODE IN PERSPECTIVE RECORD
# ------------------------------------------------------------------

class AnaRelationshipDbRecord:
    """
    Python wrapper around DbRecord containing an ANA_RELATIONSHIP
    database record.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing an
        ANA_RELATIONSHIP database record.
        """
        self.__dbRecord = dbRecord;
        
        return None

    def getDbRecord(self):
        return self.__dbRecord

    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)
    
    def getRelationshipType(self):
        return self.getDbRecord().getColumnValue(RELATIONSHIP_TYPE_NAME_COLUMN)
    
    def getChildOid(self):
        return self.getDbRecord().getColumnValue(CHILD_OID_COLUMN)

    def getParentOid(self):
        return self.getDbRecord().getColumnValue(PARENT_OID_COLUMN)




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


def getByParent(parentOid, relType):
    """
    Return the all records with given OID as parent.
    """

    iter = Iterator(
        where = (
            PARENT_OID_COLUMN + " = " + DbAccess.formatSqlValue(parentOid) +
            " and " +
            RELATIONSHIP_TYPE_NAME_COLUMN + " = " +
            DbAccess.formatSqlValue(relType)))

    rels = []

    for dbRec in iter:
        rels.append(dbRec)

    return rels




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't be set until AnaRelationshipDbRecord is defined.

_table = DbTable.registerTable(TABLE_NAME, AnaRelationshipDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,                    DbTable.IS_KEY)
_table.registerColumn(RELATIONSHIP_TYPE_NAME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(CHILD_OID_COLUMN,              DbTable.IS_NOT_KEY)
_table.registerColumn(PARENT_OID_COLUMN,             DbTable.IS_NOT_KEY)

