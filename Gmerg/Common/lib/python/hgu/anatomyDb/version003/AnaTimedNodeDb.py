#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
#
# Wrapper around the ANA_TIMED_NODE table in a GMERG style anatomy relational
# database.  Timed nodes represent abstract nodes at a single specific stage.
#

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord             # DB Records
from hgu.db import DbTable              # DB Tables

import AnaRelationshipDb



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANA_TIMED_NODE"

OID_COLUMN                 = "ATN_OID"
NODE_OID_COLUMN            = "ATN_NODE_FK"
STAGE_OID_COLUMN           = "ATN_STAGE_FK"
STAGE_MODIFIER_NAME_COLUMN = "ATN_STAGE_MODIFIER_FK"
PUBLIC_ID_COLUMN           = "ATN_PUBLIC_ID"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# TIMED NODE DB RECORD
# ------------------------------------------------------------------

class AnaTimedNodeDbRecord:
    """
    Python wrapper around DbRecord containing a Timed Node database record.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing a
        Timed Node database record.
        """
        self.__dbRecord = dbRecord;
        
        return None

    def getDbRecord(self):
        return self.__dbRecord

    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)
    
    def getNodeOid(self):
        return self.getDbRecord().getColumnValue(NODE_OID_COLUMN)
    
    def getStageOid(self):
        return self.getDbRecord().getColumnValue(STAGE_OID_COLUMN)

    def getStageModifier(self):
        return self.getDbRecord().getColumnValue(STAGE_MODIFIER_NAME_COLUMN)
    
    def getPublicId(self):
        return self.getDbRecord().getColumnValue(PUBLIC_ID_COLUMN)
    

# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def getByOid(timedNodeOid):
    """
    Return the TIMED NODE DB record with the given OID.
    """

    dbRecord = DbAccess.selectOne(
        _table, where = OID_COLUMN + " = " + DbAccess.formatSqlValue(timedNodeOid))
    if dbRecord:
        return AnaTimedNodeDbRecord(dbRecord)
    else:
        return None


def getByPublicId(timedPublicId):
    """
    Return the TIMED NODE DB record with the given public ID.
    """

    dbRecord = DbAccess.selectOne(
        _table, where = (PUBLIC_ID_COLUMN + " = " +
                         DbAccess.formatSqlValue(timedPublicId)))
    if dbRecord:
        return AnaTimedNodeDbRecord(dbRecord)
    else:
        return None



def getByNodeStage(nodeOid, stageOid):
    """
    Return the timed node for given node at the given stage.
    """

    dbRecord = DbAccess.selectOne(
        _table,
        where = (
            NODE_OID_COLUMN  + " = " + DbAccess.formatSqlValue(nodeOid) +
            " and " +
            STAGE_OID_COLUMN + " = " + DbAccess.formatSqlValue(stageOid))
        )
    if dbRecord:
        return AnaTimedNodeDbRecord(dbRecord)
    else:
        return None



def getByNode(nodeOid):
    """
    Return all timed nodes for given node OID, in a dictionary indexed by
    stage OID.

    This routine exists purely because I hope it will be faster than calling
    getByNodeStage once per stage.
    """

    iter = _table.Iterator(
        where = NODE_OID_COLUMN + " = " + DbAccess.foramtSqlValue(nodeOid))
    timedNodes = {}
    for timedNode in iter:
        timedNodes[timedNode.getStageOid()] = timedNode

    return timedNodes


    
def getPartOfChildrenOfNodeAtStage(nodeOid, stageOid):
    """
    Get all timed nodes that exist at the given stage that are children of
    the the given node in a part of relationship.
    """

    rels = AnaRelationshipDb.getByParent(nodeOid, "part-of")
    childTimedNodes = []
    for rel in rels:
        timedNode = getByNodeStage(rel.getChildOid(), stageOid)
        if timedNode:
            childTimedNodes.append(timedNode)

    return childTimedNodes


# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals; has to be after AnaTimedNodeDbRecord is defined.

_table = DbTable.registerTable("ANA_TIMED_NODE", AnaTimedNodeDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,                 DbTable.IS_KEY)
_table.registerColumn(NODE_OID_COLUMN,            DbTable.IS_NOT_KEY)
_table.registerColumn(STAGE_OID_COLUMN,           DbTable.IS_NOT_KEY)
_table.registerColumn(STAGE_MODIFIER_NAME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(PUBLIC_ID_COLUMN,           DbTable.IS_NOT_KEY)

