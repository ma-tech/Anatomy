#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Wrapper around all access to a database in a relational DBMS.
See also DbTable and DbRecord.
"""

#import sys
import datetime
# PYTHON Version
import MySQLdb
import re                               # regular expressions

from hgu.db import DbRecord
from hgu import Util

# JYTHON Version
#from com.ziclix.python.sql import zxJDBC

# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_dbConn = None
_outputFile = None


# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _buildSingleTableQuery(dbTable, columnNames, where = None, orderBy = None):
    """
    Given a table, list of column names, and optional where and order
    by clauses, build a query.
    """
    columnList = reduce(Util.commaConcat, columnNames)
    if where:
        whereClause = " where (" + where + ") "
    else:
        whereClause = ""
    if orderBy:
        orderByClause = " order by " + orderBy + " "
    else:
        orderByClause = ""

    query = (
        "select " + columnList +
        "  from " + dbTable.getName() +
        whereClause +
        orderByClause)

    return query


def _createDbRecord(dbTable, sqlRecord, columnNames):
    """
    Given and SQL record, and a list of column names, create a DbRecord.

    Perhaps the constructor for DbRecord should be rewritten?
    """
    if len(sqlRecord) != len(columnNames):
        Util.fatalError([
            "Attempt to generate DB record from different length " +
            "SQL records (" + str(len(sqlRecord)) + ")",
            "and column name list (" + str(len(columnNames)) + ")."])

    columnValues = {}
    for value, name in zip(sqlRecord, columnNames):
        columnValues[name] = value
    return DbRecord.DbRecord(dbTable, columnValues)




# ------------------------------------------------------------------
# ITERATORS / CURSORS
# ------------------------------------------------------------------

class Iterator:
    """
    Iterate through a table.

    This makes a DB cursor appear as a Python iterator.
    """

    def __init__(self, dbTable, where = None, orderBy = None):

        self.__dbTable = dbTable
        self.__cursor = _dbConn.cursor()
        self.__columns = dbTable.getColumnNames()
        self.__query = _buildSingleTableQuery(
            dbTable, self.__columns, where, orderBy)
        self.__cursor.execute(self.__query)

        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Returns a database record of the type for the current table.
        """
        sqlRecord = self.__cursor.fetchone()

        # map what is returned by cursor to a DbRecord
        if sqlRecord:
            _createDbRecord(
                self.__dbTable, sqlRecord, self.__columns)
            columnValues = {}
            for value, name in zip(sqlRecord, self.__columns):
                columnValues[name] = value

            recordClass = self.__dbTable.getRecordClass()
            return recordClass(DbRecord.DbRecord(self.__dbTable, columnValues))

        else:
            self.__cursor.close()
            raise StopIteration





# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------

def initialise(dbHost, dbName, dbUser, dbPass,
               outputDir = None, outputFilePath = None, charset = None):
    """
    Initialise the database connection.

    The outputDir and outputFilePath parameters are optional and are
    only needed if you are updating the code.  outputDir is deprecated.
    Use outputFilePath instead.

    charset should be set to "latin1" if you are having difficulties
            writing non-ASCII characters.
    """

    global _dbConn, _outputFile

    # PYTHON Connection
    _dbConn = MySQLdb.connect(
        user = dbUser, passwd = dbPass, host = dbHost, db = dbName,
        charset = charset, unix_socket = '/tmp/mysql.sock' )
    
    # JYTHON Connection
    #dbUrl = "jdbc:mysql://" + dbHost + "/" + dbName
    #dbDriver = "org.gjt.mm.mysql.Driver"
    
    #_dbConn = zxJDBC.connect(dbUrl, dbUser, dbPass, dbDriver)
    
    """
    try :
    # if called from command line with .login CLASSPATH setup right,this works
        _dbConn = zxJDBC.connect(dbUrl, dbUser, dbPass, dbDriver)
    except:
    # if called from Apache or account where the .login has not set CLASSPATH
    # need to use run-time CLASSPATH Hacker
        try :
            jarLoad = classPathHacker()
            a = jarLoad.addFile("/net/homehost/export/home/mwicks/Downloads/JarRegenerateDerivedData/lib/mm.mysql-2.0.14-bin.jar")
            _dbConn = zxJDBC.connect(dbUrl, dbUser, dbPass, dbDriver)
        except :
            sys.exit ("still failed \n%s" % (sys.exc_info()))
    """


    # Output dir only defined if producing SQL.
    if outputDir != None:
        # Open single output file to hold all generated SQL.
        fileName = outputDir + "/" + "db.sql"
        _outputFile = open(fileName, "w")
        Util.warning([
            "The 'outputDir' parameter to 'DbAccess.initialise()' has been "
            "deprecated.",
            "Please use the 'outputFilePath' parameter instead."
            ])
    elif outputFilePath != None:
        # Open single output file to hold all generated SQL.
        _outputFile = open(outputFilePath, "w")
    else:
        _outputFile = None

    return


def formatSqlValue(value):
    """
    Formats a data value in such a way that it works in an SQL statement.
    """
    formatted = None

    if value == None:
        formatted = "NULL"
    elif value.__class__ == bool:
        formatted = str(int(value))     # MySQL supports int.
    elif value.__class__ == int:
        formatted = str(value)
    elif value.__class__ == long:
        formatted = str(value)
    elif value.__class__ in [str, unicode]:
        # need to deal with embedded quotes.
        doubleValue = re.sub('"', '""', value)
        formatted = '"' + doubleValue + '"'
    elif value.__class__ == datetime.datetime:
        formatted = '"' + value.isoformat(' ') + '"'
    else:
        Util.fatalError(["Unrecognized datatype: ", str(value.__class__)])

    return formatted


def selectOne(dbTable, where):
    """
    Select a single record from a single table.

    If record does not exist then None is returned.
    """
    columnNames = dbTable.getColumnNames()
    query = _buildSingleTableQuery(dbTable, columnNames, where = where)

    cursor = _dbConn.cursor()
    cursor.execute(query)
    if cursor.rowcount > 1:
        Util.fatalError([
            "Query expected to return 0 or 1 row returned " +
            str(cursor.rowcount) + " rows.  Query:",
            query])
    elif cursor.rowcount == 1:
        sqlRecord = cursor.fetchone()
        dbRecord = _createDbRecord(
            dbTable, sqlRecord, columnNames)
    else:
        dbRecord = None

    cursor.close()

    return dbRecord


def selectMaxOid(dbTable):
    """
    Select the maximum OID from the given table.  Returns 0 if there are
    no records in the table.

    Throws an exception if the table has
    o a multi-key primary key or
    o has no primary key, or
    o if the name of the single column in the primary key does not
      match xxx_OID.
    """
    keyColumnNames = dbTable.getKeyColumnNames()
    if len(keyColumnNames) == 0:
        Util.fatalError([
            "Called getMaxOid() on table " + dbTable.getName() +
            " which does not have a primary key."])
    elif len(keyColumnNames) > 1:
        Util.fatalError([
            "Called getMaxOid() on table " + dbTable.getName() +
            " with multi-column primary key."])
    oidColumn = keyColumnNames[0]
    if not re.match("..._OID$", oidColumn):
        Util.fatalError([
            "Called getMaxOid() on table " + dbTable.getName() +
            " whose single column primary key is not xxx_OID.",
            "PK Column: " + oidColumn])

    query = "select max(" + oidColumn + ") from " + dbTable.getName()

    cursor = _dbConn.cursor()
    cursor.execute(query)
    maxOid = cursor.fetchone()[0]
    cursor.close()
    if not maxOid:
        maxOid = 0

    return maxOid



def executeDdl(ddlStatement):
    """
    Executes the given DDL statement.
    """
    cursor = _dbConn.cursor()
    cursor.execute(ddlStatement)
    cursor.close()

    return None


def executeDml(dmlStatement):
    """
    Executes an arbitrary SQL DML statement.

    This method should be used for multi-record inserts, deletes, and
    updates only.  For selects and single record modifications use the
    routines built specifically for those purposes.
    """

    cursor = _dbConn.cursor()
    cursor.execute(dmlStatement)
    cursor.close()

    return None


def writeSql(sqlStatement):
    """
    Routine to write arbitrary SQL to the output file.

    There should be a much cleaner way to do this, and someday there
    will be.
    """
    _outputFile.write(sqlStatement + "\n\n")

    return None


def finalise():
    """
    Finalisation tasks for database.
    """
    _outputFile.close()

    _dbConn.close()

    return None
