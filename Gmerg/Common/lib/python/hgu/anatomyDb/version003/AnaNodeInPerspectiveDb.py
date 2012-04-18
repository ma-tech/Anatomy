#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Wrapper around the ANA_NODE_IN_PERSPECTIVE table in a GMERG style relational
# anatomy database.  This table contains the minimal defintion of what is in
# a perspective.  A perspective is a subset of the whole anatomy.
#

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord             # DB Records
from hgu.db import DbTable              # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_NODE_IN_PERSPECTIVE"

OID_COLUMN              = "NIP_OID"
PERSPECTIVE_NAME_COLUMN = "NIP_PERSPECTIVE_FK"
NODE_OID_COLUMN         = "NIP_NODE_FK"




# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None



# ------------------------------------------------------------------
# NODE IN PERSPECTIVE RECORD
# ------------------------------------------------------------------

class AnaNodeInPerspectiveDbRecord:
    """
    Python wrapper around DbRecord containing an ANA_NODE_IN_PERSPECTIVE
    database record.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing an
        ANA_NODE_IN_PERSPECTIVE database record.
        """
        self.__dbRecord = dbRecord;
        
        return None

    def getDbRecord(self):
        return self.__dbRecord

    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)
    
    def getPerspective(self):
        """
        We all need perspective.
        """
        return self.getDbRecord().getColumnValue(PERSPECTIVE_NAME_COLUMN)
    
    def getNodeOid(self):
        return self.getDbRecord().getColumnValue(NODE_OID_COLUMN)




# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def Iterator(perspectiveName):
    """
    Iterate through node in perspective records for the given perspective.
    """
    where = (
        PERSPECTIVE_NAME_COLUMN + " = " +
        DbAccess.formatSqlValue(perspectiveName))
    return _table.Iterator(where = where)




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't be set until AnaNodeInPerspectiveDbRecord
# is defined.

_table = DbTable.registerTable(TABLE_NAME, AnaNodeInPerspectiveDbRecord,
                               DbTable.NOT_IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,              DbTable.IS_KEY)
_table.registerColumn(PERSPECTIVE_NAME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(NODE_OID_COLUMN,         DbTable.IS_NOT_KEY)

