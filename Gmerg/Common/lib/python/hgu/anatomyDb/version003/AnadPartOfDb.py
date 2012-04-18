#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Wrapper around the ANAD_PART_OF derived table in a GMERG style relational
# database.  This derived table gives fast access to an indented tree
# representation of the anatomy.
#

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord                         # DB Records
from hgu.db import DbTable                          # DB Tables


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ANAD_PART_OF"

OID_COLUMN                    = "APO_OID"
SPECIES_NAME_COLUMN           = "APO_SPECIES_FK"
NODE_START_STAGE_OID_COLUMN   = "APO_NODE_START_STAGE_FK"
NODE_END_STAGE_OID_COLUMN     = "APO_NODE_END_STAGE_FK"
PATH_START_STAGE_OID_COLUMN   = "APO_PATH_START_STAGE_FK"
PATH_END_STAGE_OID_COLUMN     = "APO_PATH_END_STAGE_FK"
NODE_OID_COLUMN               = "APO_NODE_FK"
SEQUENCE_COLUMN               = "APO_SEQUENCE"
DEPTH_COLUMN                  = "APO_DEPTH"
FULL_PATH_COLUMN              = "APO_FULL_PATH"
IS_PRIMARY_COLUMN             = "APO_IS_PRIMARY"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# PART OF DERIVED DB RECORD
# ------------------------------------------------------------------

class AnadPartOfDbRecord:
    """
    Python wrapper around DbRecord containing an ANAD_PART_OF database record.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing an
        ANAD_PART_OF database record.
        """
        self.__dbRecord = dbRecord;
        
        return None

    def getDbRecord(self):
        return self.__dbRecord

    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)
    
    def getSpecies(self):
        return self.getDbRecord().getColumnValue(SPECIES_NAME_COLUMN)
    
    def getNodeStartStageOid(self):
        return self.getDbRecord().getColumnValue(NODE_START_STAGE_OID_COLUMN)

    def getNodeEndStageOid(self):
        return self.getDbRecord().getColumnValue(NODE_END_STAGE_OID_COLUMN)

    def getPathStartStageOid(self):
        return self.getDbRecord().getColumnValue(PATH_START_STAGE_OID_COLUMN)

    def getPathEndStageOid(self):
        return self.getDbRecord().getColumnValue(PATH_END_STAGE_OID_COLUMN)

    def getNodeOid(self):
        return self.getDbRecord().getColumnValue(NODE_OID_COLUMN)

    def getSequence(self):
        return self.getDbRecord().getColumnValue(SEQUENCE_COLUMN)
    
    def getDepth(self):
        return self.getDbRecord().getColumnValue(DEPTH_COLUMN)

    def getFullPath(self):
        return self.getDbRecord().getColumnValue(FULL_PATH_COLUMN)

    def isPrimary(self):
        return self.getDbRecord().getColumnValue(IS_PRIMARY_COLUMN)



# ------------------------------------------------------------------
# ITERATORS
# ------------------------------------------------------------------

def SequenceIterator():
    """
    Iterate through all anatomy part of derived records, in ascending
    sequence order.

    That is, in the same order they are displayed in.  This is an
    iterator in Python, but a cursor in SQL.
    """
    orderBy = SEQUENCE_COLUMN
    return _table.Iterator(orderBy = orderBy)




def PerspectiveIterator(perspectiveName):
    """
    Iterate through all nodes in the specified perspective.
    """
    where = """
      exists (
        select 'x'
          from ANAD_PART_OF_PERSPECTIVE
          where POP_PERSPECTIVE_FK = '""" + perspectiveName + """'
            and POP_APO_FK = """ + OID_COLUMN + """ ) """
    orderBy = SEQUENCE_COLUMN
    return _table.Iterator(where = where, orderBy = orderBy)



def SequenceIteratorForStage(stageDbRec):
    """
    Iterate through all anatomy part of derived records that exist at given stage,
    in ascending sequence order.
    """

    sequence = DbAccess.formatSqlValue(stageDbRec.getSequence())

    where = (
        """
        exists (
          select 'x'
            from ANA_STAGE pathStartStage,
                 ANA_STAGE pathEndStage
            where pathStartStage.STG_OID = """ + PATH_START_STAGE_OID_COLUMN + """
              and pathEndStage.STG_OID   = """ + PATH_END_STAGE_OID_COLUMN + """
              and """ + sequence + """ >= pathStartStage.STG_SEQUENCE
              and """ + sequence + """ <= pathEndStage.STG_SEQUENCE )"""
        )
            
    orderBy = SEQUENCE_COLUMN
    return _table.Iterator(where = where, orderBy = orderBy)





# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't initiliaise until after AnadPartOfDbRecord is
# defined.

_table = DbTable.registerTable("ANAD_PART_OF", AnadPartOfDbRecord,
                               DbTable.NOT_IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,                  DbTable.IS_KEY)
_table.registerColumn(NODE_START_STAGE_OID_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(NODE_END_STAGE_OID_COLUMN,   DbTable.IS_NOT_KEY)
_table.registerColumn(PATH_START_STAGE_OID_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(PATH_END_STAGE_OID_COLUMN,   DbTable.IS_NOT_KEY)
_table.registerColumn(SPECIES_NAME_COLUMN,         DbTable.IS_NOT_KEY)
_table.registerColumn(NODE_OID_COLUMN,             DbTable.IS_NOT_KEY)
_table.registerColumn(SEQUENCE_COLUMN,             DbTable.IS_NOT_KEY)
_table.registerColumn(DEPTH_COLUMN,                DbTable.IS_NOT_KEY)
_table.registerColumn(FULL_PATH_COLUMN,            DbTable.IS_NOT_KEY)
_table.registerColumn(IS_PRIMARY_COLUMN,           DbTable.IS_NOT_KEY)

