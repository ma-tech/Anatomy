#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around single Database records.
"""

from hgu import Util


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------



# ------------------------------------------------------------------
# DB RECORD
# ------------------------------------------------------------------

class DbRecord:
    """
    Encapsulates a DB record either returned from, or destined for the DBMS.
    """

    def __init__(self, dbTable, columnValues = None):
        """
        Create a DbRecord instance.

        Parameters
          dbTable      a Python DbTable object.  The record is associated with
                       this table
          columnValues Dictionary of name-value pairs for each column in table.
                       If this is None, a dictionary with column names in it
                       will be created, all with value = None.
        """
        self.__dbTable  = dbTable

        if not columnValues:
            columnValues = {}
            for columnName in self.getColumnNames():
                columnValues[columnName] = None
        self.__columnValues  = columnValues

        return None

    def getTable(self):
        """
        Return the DbTable object that this record came from.
        """
        return self.__dbTable

    def getTableName(self):
        """
        Return the name of the table this record came from.
        """
        return self.getTable().getName()

    def getColumnNames(self):
        """
        Return a list of column names in this record.
        """
        return self.getTable().getColumnNames()

    def inAnaObject(self):
        """
        Return True if the OID from this record exists in ANA_OBJECT; return
        False if it does not.
        """
        return self.getTable().inAnaObject()

    def getColumnValue(self, columnName):
        """
        Given the name of a column, return that column's value
        """
        return self.__columnValues[columnName]

    def setColumnValue(self, columnName, columnValue):
        """
        Set the given column to the given value
        """
        self.__columnValues[columnName] = columnValue
        return self.__columnValues[columnName]


    def insert(self):
        """
        Generate SQL to insert this record into the database.
        """
        # import here to avoid obscure circular import problem
        from hgu.db import DbAccess

        columnNames = None
        columnValues = None

        for name, value in self.__columnValues.items():
            sqlValue = DbAccess.formatSqlValue(value)
            if columnNames:
                columnNames += ", " + name
                columnValues += ", " + sqlValue
            else:
                columnNames = name
                columnValues = sqlValue

        query = """
            insert into """ + self.getTableName() + """
                ( """ + columnNames + """ )
              values
                ( """ + columnValues + """ );"""

        DbAccess.writeSql(query)

        return



    def update(self):
        """
        Generate SQL to update this record in the databse.  This
        assumes that none of the primary key fields are being changed.
        """
        # import here to avoid obscure circular import problem
        from hgu.db import DbAccess

        table = self.getTable()

        # build where clause
        whereList = []
        for keyColumnName in table.getKeyColumnNames():
            cond = (
                keyColumnName + " = "+
                DbAccess.formatSqlValue(self.getColumnValue(keyColumnName)))
            whereList.append(cond)
        whereClause = reduce(Util.commaConcat, whereList)

        # build set clause
        setList = []
        for nonKeyColumnName in table.getNonKeyColumnNames():
            assign = (
                nonKeyColumnName + " = "+
                DbAccess.formatSqlValue(self.getColumnValue(nonKeyColumnName)))
            setList.append(assign)
        setClause = reduce(Util.commaConcat, setList)

        query = """
            update """ + table.getName() + """
               set """ + setClause + """
               where """ + whereClause + """;"""

        DbAccess.writeSql(query)

        return


    def spew(self, label=""):
        """
        Debugging routine to spew the contents of a database record.
        """
        print self.getTableName(), "record", label
        for colName, colValue in self.__columnValues.iteritems():
            print colName, colValue
        print

        return
