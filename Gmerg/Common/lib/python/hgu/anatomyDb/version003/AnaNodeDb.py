#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# Wrapper around the ANA_NODE table in a GMERG style anatomy relational
# database.  Nodes represent abstract (that is, not stage specific)
# components in the anatomy ontology.
#

from hgu.db import DbAccess             # DB Connection.
from hgu.db import DbRecord             # DB Records
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
PUBLIC_ID_COLUMN      = "ANO_PUBLIC_ID"



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

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing a
        Node database record.
        """
        self.__dbRecord = dbRecord;
        
        return None

    def getDbRecord(self):
        return self.__dbRecord

    def getOid(self):
        return self.getDbRecord().getColumnValue(OID_COLUMN)
    
    def getSpecies(self):
        return self.getDbRecord().getColumnValue(SPECIES_NAME_COLUMN)
    
    def getComponentName(self):
        return self.getDbRecord().getColumnValue(COMPONENT_NAME_COLUMN)

    def isPrimary(self):
        return self.getDbRecord().getColumnValue(IS_PRIMARY_COLUMN)
    
    def getPublicId(self):
        return self.getDbRecord().getColumnValue(PUBLIC_ID_COLUMN)
    


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
    
    return AnaNodeDbRecord(DbAccess.selectOne(
        _table,
        where = ( 
            """
            not exists (
                  select 'x'
                    from """ + AnaRelationshipDb.TABLE_NAME + """ 
                    where """ + AnaRelationshipDb.CHILD_OID_COLUMN +
                          " = " + OID_COLUMN + ")" )))


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
_table.registerColumn(PUBLIC_ID_COLUMN,      DbTable.IS_NOT_KEY)




