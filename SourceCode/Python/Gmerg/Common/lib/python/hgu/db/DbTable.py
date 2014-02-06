#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Wrapper around database tables
"""

from hgu.db import DbAccess
from hgu import Util


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Used when creating table objects.  Specifies if table's OIDs are
# in the ANA_OBJECT table or not.

IN_ANA_OBJECT     = True
NOT_IN_ANA_OBJECT = False

# Flag key and non-key columns as such.

IS_KEY     = True
IS_NOT_KEY = False



# ------------------------------------------------------------------
# GLOBALS - initialised when module is loaded
# ------------------------------------------------------------------

_tablesByTableName = {}





# ------------------------------------------------------------------
# DB TABLE
# ------------------------------------------------------------------

class DbTable:
    """
    Defines a database table in Python.
    """

    def __init__(self, tableName, recordClass, inAnaObject):
        """
        Create a database table definition.

        Parameters:
          tableName:     Name of table
          recordClass:   Python class that wraps around records in this table.
          inAnaObect:    One of these values:
                         IN_ANA_OBJECT:     The key of this table exists in
                                            the ANATOMY_OBJECT table.
                         NOT_IN_ANA_OBJECT: The key of this table DOES NOT exist
                                            in the ANATOMY_OBJECT table.
        """
        self.__name          = tableName
        self.__recordClass   = recordClass
        self.__inAnaObject   = inAnaObject
        self.__keyColumns    = {}
        self.__nonKeyColumns = {}
        self.__columns       = {}

        return None


    def registerColumn(self, columnName, isKey = IS_NOT_KEY):
        """
        Add a column definition to the table definition.
        """
        self.__columns[columnName] = columnName
        if isKey:
            self.__keyColumns[columnName] = columnName
        else:
            self.__nonKeyColumns[columnName] = columnName

        return None

    def getName(self):
        """
        Return the name of the table.
        """
        return self.__name

    def getRecordClass(self):
        """
        Return the class that contains records from this table.
        """
        return self.__recordClass

    def getColumnNames(self):
        """
        Get list of column names that occur in this table.
        """
        return self.__columns.keys()

    def getKeyColumnNames(self):
        """
        Return the list of column names that are key columns.
        """
        return self.__keyColumns.keys()

    def getNonKeyColumnNames(self):
        """
        Return the list of column names that are not in the primary key.
        """
        return self.__nonKeyColumns.keys()

    def inAnaObject(self):
        """
        Return True if the OIDs that uniquely identify records in this table
        are stored in the ANA_OBJECT table; return False if they are not.
        """
        return self.__inAnaObject

    def genCommaSeparatedColumnList(self):
        """
        Return a comma separate list of column names.
        """
        return reduce(Util.commaConcat, self.getColumnNames())



    def deleteAll(self):
        """
        Delete every record in the table.
        """
        delete = "delete from " + self.getName() + ";"
        DbAccess.writeSql(delete)

        return


    def Iterator(self, where = None, orderBy = None):
        """
        Iterate through this table using the optional where and order by
        clauses.  This returns DbRecord objects.
        """
        return DbAccess.Iterator(dbTable = self, where = where,
                                 orderBy = orderBy)



# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------


def registerTable(tableName, recordClass, inAnaObject = NOT_IN_ANA_OBJECT):
    """
    Register a database table.

    This needs to be followed by subsequent calls to register columns.
    """
    dbTable = DbTable(tableName, recordClass, inAnaObject)
    _tablesByTableName[tableName] = dbTable

    return dbTable


def getByName(tableName):
    """
    Returns the DbTable object for the table with that name.  If the name
    is unknown then raise an exception.
    """
    return _tablesByTableName[tableName]
