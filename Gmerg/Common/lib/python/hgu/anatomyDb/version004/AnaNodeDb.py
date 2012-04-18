#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Wrapper around the ANA_NODE table in a GMERG style anatomy relational
database.  Nodes represent abstract (that is, not stage specific)
components in the anatomy ontology.
"""

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord
from hgu.db import DbTable              # DB Tables

import AnaRelationshipDb



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


TABLE_NAME = "ANA_NODE"

OID_COLUMN            = "ANO_OID"
SPECIES_NAME_COLUMN   = "ANO_SPECIES_FK"
COMPONENT_NAME_COLUMN = "ANO_COMPONENT_NAME"
IS_PRIMARY_COLUMN     = "ANO_IS_PRIMARY"
IS_GROUP_COLUMN       = "ANO_IS_GROUP"
PUBLIC_ID_COLUMN      = "ANO_PUBLIC_ID"
DESCRIPTION_COLUMN    = "ANO_DESCRIPTION"



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

# Database globals

_table = None




# ------------------------------------------------------------------
# NODE DB RECORD
# ------------------------------------------------------------------

class AnaNodeDbRecord:
    """
    Python wrapper around DbRecord containing a Node database record.
    """

    def __init__(self, dbRecord = None):
        """
        Create Python wrapper around DbRecord containing a
        Node database record.

        If dbRecord is None, initialise all columns to None.
        """
        if not dbRecord:
            dbRecord = DbRecord.DbRecord(_table)
        self.__dbRecord = dbRecord

        return None

    def getDbRecord(self):
        """
        Return the DbRecord for this node.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the database OID for this node.
        """
        return self.getDbRecord().getColumnValue(OID_COLUMN)

    def setOid(self, oid):
        """
        Set the database OID for this node.
        """
        return self.getDbRecord().setColumnValue(OID_COLUMN, oid)


    def getSpecies(self):
        """
        Return the species name for this node.
        """
        return self.getDbRecord().getColumnValue(SPECIES_NAME_COLUMN)

    def setSpecies(self, speciesName):
        """
        Set the species name for this node.
        """
        return self.getDbRecord().setColumnValue(SPECIES_NAME_COLUMN,
                                                 speciesName)


    def getComponentName(self):
        """
        Return the component name for this node.  The component name is
        the name of the node without any leading path.
        """
        return self.getDbRecord().getColumnValue(COMPONENT_NAME_COLUMN)

    def setComponentName(self, name):
        """
        Set the component name for this node.  The component name is
        the name of the node without any leading path.
        """
        return self.getDbRecord().setColumnValue(COMPONENT_NAME_COLUMN,
                                                 name)


    def isPrimary(self):
        """
        Return True if the node is <strong>not</strong> a group node, and
        False if it is a group node.
        """
        return not self.getDbRecord().getColumnValue(IS_GROUP_COLUMN)

    def isGroup(self):
        """
        Return True if the node is a group node, and
        False if it is not a group node.
        """
        return self.getDbRecord().getColumnValue(IS_GROUP_COLUMN)

    def setIsGroup(self, isGroup):
        """
        Set's the group status of the node to True, indicating it is a
        group node, or False.
        """
        self.getDbRecord().setColumnValue(IS_PRIMARY_COLUMN, not isGroup)
        return self.getDbRecord().setColumnValue(IS_GROUP_COLUMN, isGroup)


    def getPublicId(self):
        """
        Return the node's public ID, e.g. EMAPA:12345.
        """
        return self.getDbRecord().getColumnValue(PUBLIC_ID_COLUMN)

    def setPublicId(self, publicId):
        """
        Set the node's public ID, e.g. EMAPA:12345.
        """
        return self.getDbRecord().setColumnValue(PUBLIC_ID_COLUMN, publicId)


    def getDescription(self):
        """
        Return the node's text description, if it has one.
        """
        return self.getDbRecord().getColumnValue(DESCRIPTION_COLUMN)

    def setDescription(self, description):
        """
        Set the node's text description.  This can be None.
        """
        return self.getDbRecord().setColumnValue(DESCRIPTION_COLUMN, description)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return


    def spew(self, label = ""):
        """
        Debugging routine to print the conents of the record
        """
        self.getDbRecord().spew(label)



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------


def Iterator():
    """
    Iterate through all nodes, in no particular order
    """
    return _table.Iterator()


def ComponentNameIterator(componentName):
    """
    Iterate through all nodes with the given name, in no particular order
    """
    where = (
        COMPONENT_NAME_COLUMN + " = " + DbAccess.formatSqlValue(componentName))
    return _table.Iterator(where = where)





# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def getByOid(nodeOid):
    """
    Return the NODE DB record with the given OID.
    """

    return AnaNodeDbRecord(DbAccess.selectOne(
        _table, where = OID_COLUMN + " = " + DbAccess.formatSqlValue(nodeOid)))



def getRoot():
    """
    Return the root node.
    """

    # :TODO: Rewrite this to call a routine in AnaRelationshipDb like
    # AnaRelationshipDb.genRelWithChildOidExistsSubquery(OID_COLUMN), or
    # AnaRelationshipDb.genChildOidExistsSubquery(OID_COLUMN)
    # AnaRelationshipDb.genExistsAsChildSubquery(OID_COLUMN)

    dbRec = DbAccess.selectOne(
        _table,
        where = (
            """
            not exists (
                  select 'x'
                    from """ + AnaRelationshipDb.TABLE_NAME + """
                    where """ + AnaRelationshipDb.CHILD_OID_COLUMN +
                          " = " + OID_COLUMN + ")" ))
    if dbRec != None:
        dbRec = AnaNodeDbRecord(dbRec)

    return dbRec



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Database globals.  This has to occur after AnaNodeDbRecord has bee
# defined.

_table = DbTable.registerTable(TABLE_NAME, AnaNodeDbRecord,
                               DbTable.IN_ANA_OBJECT)

_table.registerColumn(OID_COLUMN,            DbTable.IS_KEY)
_table.registerColumn(SPECIES_NAME_COLUMN,   DbTable.IS_NOT_KEY)
_table.registerColumn(COMPONENT_NAME_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(IS_PRIMARY_COLUMN,     DbTable.IS_NOT_KEY)
_table.registerColumn(IS_GROUP_COLUMN,       DbTable.IS_NOT_KEY)
_table.registerColumn(PUBLIC_ID_COLUMN,      DbTable.IS_NOT_KEY)
_table.registerColumn(DESCRIPTION_COLUMN,    DbTable.IS_NOT_KEY)


