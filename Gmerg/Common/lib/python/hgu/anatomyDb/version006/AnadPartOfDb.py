#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ANAD_PART_OF derived table in a GMERG style relational
database.  This derived table gives fast access to an indented tree
representation of the anatomy.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord
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
FULL_PATH_OIDS_COLUMN         = "APO_FULL_PATH_OIDS"
FULL_PATH_EMAPAS_COLUMN       = "APO_FULL_PATH_EMAPAS"
FULL_PATH_JSON_HEAD_COLUMN    = "APO_FULL_PATH_JSON_HEAD"
FULL_PATH_JSON_TAIL_COLUMN    = "APO_FULL_PATH_JSON_TAIL"
IS_PRIMARY_COLUMN             = "APO_IS_PRIMARY" # depreacted in V4
IS_PRIMARY_PATH_COLUMN        = "APO_IS_PRIMARY_PATH"
PARENT_APO_OID_COLUMN         = "APO_PARENT_APO_FK"


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

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing an
        ANAD_PART_OF database record.

        If dbRecord is None, then set all columns to None.
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
        Return the database OID of this object.  This is not persistent, nor is it
        unique across the database.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def setOid(self, oid):
        """
        Set the database OID for this object.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, oid)


    def getSpecies(self):
        """
        Return the name of the species this record is for.
        """
        return self.getDbRecord().getColumnValue(SPECIES_NAME_COLUMN)

    def setSpecies(self, speciesName):
        """
        Set the name of the species this record is for.
        """
        return self.getDbRecord().setColumnValue(SPECIES_NAME_COLUMN, speciesName)


    def getNodeStartStageOid(self):
        """
        Return the database OID of the first stage this record's node exists at.
        """
        return self.getDbRecord().getColumnValue(NODE_START_STAGE_OID_COLUMN)

    def setNodeStartStageOid(self, nodeStartStageOid):
        """
        Set the database OID of the first stage this record's node exists at.
        """
        return self.getDbRecord().setColumnValue(NODE_START_STAGE_OID_COLUMN,
                                                 nodeStartStageOid)


    def getNodeEndStageOid(self):
        """
        Return the database OID of the last stage this record's node exists at.
        """
        return self.getDbRecord().getColumnValue(NODE_END_STAGE_OID_COLUMN)

    def setNodeEndStageOid(self, nodeEndStageOid):
        """
        Set the database OID of the last stage this record's node exists at.
        """
        return self.getDbRecord().setColumnValue(NODE_END_STAGE_OID_COLUMN,
                                                 nodeEndStageOid)


    def getPathStartStageOid(self):
        """
        Return the database OID of the first stage this record's node exists
        at <strong>along this path this record is in.</strong>.  This will
        always be the same or later than the node's start stage.
        """
        return self.getDbRecord().getColumnValue(PATH_START_STAGE_OID_COLUMN)

    def setPathStartStageOid(self, pathStartStageOid):
        """
        Set the database OID of the first stage this record's node exists
        at <strong>along this path this record is in.</strong>.  This will
        always be the same or later than the node's start stage.
        """
        return self.getDbRecord().setColumnValue(PATH_START_STAGE_OID_COLUMN,
                                                 pathStartStageOid)


    def getPathEndStageOid(self):
        """
        Return the database OID of the last stage this record's node exists
        at <strong>along this path this record is in.</strong>.  This will
        always be the same or earlier than the node's end stage.
        """
        return self.getDbRecord().getColumnValue(PATH_END_STAGE_OID_COLUMN)

    def setPathEndStageOid(self, pathEndStageOid):
        """
        Set the database OID of the last stage this record's node exists
        at <strong>along this path this record is in.</strong>.  This will
        always be the same or earlier than the node's end stage.
        """
        return self.getDbRecord().setColumnValue(PATH_END_STAGE_OID_COLUMN,
                                                 pathEndStageOid)


    def getNodeOid(self):
        """
        Return the database OID of the node this record is for.
        """
        return self.getDbRecord().getColumnValue(NODE_OID_COLUMN)

    def setNodeOid(self, nodeOid):
        """
        Set the database OID of the node this record is for.
        """
        return self.getDbRecord().setColumnValue(NODE_OID_COLUMN, nodeOid)


    def getSequence(self):
        """
        Return the position/seqeunce of this record within all the other
        ANAD_PART_OF records.  This field provides an absolute ordering
        of every path in the tree.
        """
        return self.getDbRecord().getColumnValue(SEQUENCE_COLUMN)

    def setSequence(self, sequence):
        """
        Set the position/seqeunce of this record within all the other
        ANAD_PART_OF records.  This field provides an absolute ordering
        of every path in the tree.
        """
        return self.getDbRecord().setColumnValue(SEQUENCE_COLUMN, sequence)


    def getDepth(self):
        """
        Return how deep this path is in the tree.  The root node has a
        depth of 0.
        """
        return self.getDbRecord().getColumnValue(DEPTH_COLUMN)

    def setDepth(self, depth):
        """
        Set how deep this path is in the tree.  The root node has a
        depth of 0.
        """
        return self.getDbRecord().setColumnValue(DEPTH_COLUMN, depth)


    def getFullPath(self):
        """
        Return the full path of this record from the root of the tree.
        """
        return self.getDbRecord().getColumnValue(FULL_PATH_COLUMN)

    def setFullPath(self, fullPath):
        """
        Set the full path of this record from the root of the tree.
        """
        return self.getDbRecord().setColumnValue(FULL_PATH_COLUMN, fullPath)


    def getFullPathOids(self):
        """
        Return the full path node OIDs of this record from the root of the tree.
        """
        return self.getDbRecord().getColumnValue(FULL_PATH_OIDS_COLUMN)

    def setFullPathOids(self, fullPathOids):
        """
        Set the full path node OIDs of this record from the root of the tree.
        """
        return self.getDbRecord().setColumnValue(FULL_PATH_OIDS_COLUMN, fullPathOids)


    def getFullPathEmapas(self):
        """
        Return the full path node EMAPAs of this record from the root of the tree.
        """
        return self.getDbRecord().getColumnValue(FULL_PATH_EMAPAS_COLUMN)

    def setFullPathEmapas(self, fullPathEmapas):
        """
        Set the full path node EMAPAs of this record from the root of the tree.
        """
        return self.getDbRecord().setColumnValue(FULL_PATH_EMAPAS_COLUMN, fullPathEmapas)


    def getFullPathJsonHead(self):
        """
        Return the full path of this record from the root of the tree.
        """
        return self.getDbRecord().getColumnValue(FULL_PATH_JSON_HEAD_COLUMN)

    def setFullPathJsonHead(self, fullPathJsonHead):
        """
        Set the full path of this record from the root of the tree.
        """
        return self.getDbRecord().setColumnValue(FULL_PATH_JSON_HEAD_COLUMN, fullPathJsonHead)


    def getFullPathJsonTail(self):
        """
        Return the full path of this record from the root of the tree.
        """
        return self.getDbRecord().getColumnValue(FULL_PATH_JSON_TAIL_COLUMN)

    def setFullPathJsonTail(self, fullPathJsonTail):
        """
        Set the full path of this record from the root of the tree.
        """
        return self.getDbRecord().setColumnValue(FULL_PATH_JSON_TAIL_COLUMN, fullPathJsonTail)


    def isPrimaryPath(self):
        """
        Return True if this is the primary path to this node; return False
        if this record's path to this node goes through a group node.

        Note that this does not indicate if the current node is a group or not,
        but rather if the current node is below a group in this record's path.
        Thus the primary path to a group node will return True.
        """
        return self.getDbRecord().getColumnValue(IS_PRIMARY_PATH_COLUMN)

    def setIsPrimaryPath(self, isPrimaryPath):
        """
        Sets the primary path state of this record.
        True if this is the primary path to this node; False
        if this record's path to this node goes through a group node.

        Note that this does not indicate if the current node is a group or not,
        but rather if the current node is below a group in this record's path.
        Thus the primary path to a group node will return True.
        """
        # set deprecated column until it goes away.
        self.getDbRecord().setColumnValue(IS_PRIMARY_COLUMN, isPrimaryPath)
        return self.getDbRecord().setColumnValue(IS_PRIMARY_PATH_COLUMN,
                                                 isPrimaryPath)


    def getParentApoOid(self):
        """
        Return the database OID of this record's parent ANAD_PART_OF record.
        """
        return self.getDbRecord().getColumnValue(PARENT_APO_OID_COLUMN)

    def setParentApoOid(self, parentApoOid):
        """
        Set the database OID of this record's parent ANAD_PART_OF record.
        """
        return self.getDbRecord().setColumnValue(PARENT_APO_OID_COLUMN,
                                                 parentApoOid)

    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return


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

def getByOid(apoOid):
    """
    Return the AND_PART_OF record with the given OID.  Returns None
    if no record with that OID exists.
    """
    where = (
        "where " + OID_COLUMN + " = " + DbAccess.formatSqlValue(apoOid))

    return DbAccess.selectOne(_table, where)




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  Can't initiliaise until after AnadPartOfDbRecord is
# defined.

_table = DbTable.registerTable(TABLE_NAME, AnadPartOfDbRecord,
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
_table.registerColumn(FULL_PATH_OIDS_COLUMN,       DbTable.IS_NOT_KEY)
_table.registerColumn(FULL_PATH_EMAPAS_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(FULL_PATH_JSON_HEAD_COLUMN,  DbTable.IS_NOT_KEY)
_table.registerColumn(FULL_PATH_JSON_TAIL_COLUMN,  DbTable.IS_NOT_KEY)
_table.registerColumn(IS_PRIMARY_COLUMN,           DbTable.IS_NOT_KEY)
_table.registerColumn(IS_PRIMARY_PATH_COLUMN,      DbTable.IS_NOT_KEY)
_table.registerColumn(PARENT_APO_OID_COLUMN,       DbTable.IS_NOT_KEY)

