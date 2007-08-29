#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Wrapper around all access to the anatomy database in the relational DBMS.

NOTE: Don't build any more programs around this file.  This version is
      specific to the CIOF to Relational Translator.  It has many quirks
      and flaws.

      If you need access to the anatomy database, use the AnatomyDb package
      in the HGU Python Library:

          Gmerg/Common/lib/python/hgu/anatomyDb/

      It has fewer quirks and flaws.
"""

import datetime
import MySQLdb
import re                               # regular expressions

import AnatomyObject
import Util                             # Error handling
import Version


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# Used in calls to registerClassTable to tell routine if table's OIDs are
# in the ANA_OBJECT table.

IN_ANA_OBJECT     = True
NOT_IN_ANA_OBJECT = False


# Used in calls to addColumnMethodMapping to say if column is a key or not

IS_KEY     = True
IS_NOT_KEY = False


_ACTION_INSERT = "inserts"
_ACTION_UPDATE = "updates"
_ACTION_DELETE = "deletes"


# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_anatomyConn = None
_tableInfoByTableName = None
_tableInfoByClass = None
_outputFileName = None



# ------------------------------------------------------------------
# TABLE INFO
# ------------------------------------------------------------------

class TableInfo:
    """
    Stores information about a table.
    """

    def __init__(self, anatomyClass, anatomyTable, inAnaObject):
        """
        Records association between DBMS table and Python class,
        and any other needed info on tables.

        This class is public for one reason: so that the
        addColumnMethodMapping() method can be called after the table
        is registered.  Other than that, none of its methods, or even
        its constructor should be called outside this module.

        Parameters:
          anatomyClass:  Class of python objects that represent this table
                         in python.  This class holds the CIOF info.
          anatomyTable:  Name of table
          inAnaObect:    One of these values:
                         IN_ANA_OBJECT:     The key of this table exists in
                                            the ANATOMY_OBJECT table.
                         NOT_IN_ANA_OBJECT: The key of this table DOES NOT exist
                                            in the ANATOMY_OBJECT table.
        """
        self.__anatomyClass = anatomyClass
        self.__anatomyTable = anatomyTable
        self.__inAnaObject  = inAnaObject
        self.__methodMappings = {}
        self.__keyMappings    = {}
        self.__nonKeyMappings = {}
        self.__dbOrphans    = []
        self.__inserts      = []
        self.__updates      = []
        self.__deletes      = []

        return None

    def addColumnMethodMapping(self, columnName, methodName, isKey):
        """
        Add a mapping between DB column name and the name of an instance
        method to
        extract the same value from the CIOF generated data.
        """

        mapping = _ColumnMethodMapping(columnName, methodName, isKey)
        self.__methodMappings[columnName] = mapping

        if mapping.isKey():
            self.__keyMappings[columnName] = mapping
        else:
            self.__nonKeyMappings[columnName] = mapping

        return None

    def getAnatomyClass(self):
        """
        Return the class of python objects that represent this table
        in python.  This class holds the CIOF info.
        """
        return self.__anatomyClass

    def getTableName(self):
        """
        Return the name of this database table.
        """
        return self.__anatomyTable

    def getColumnNames(self):
        """
        Return the names of the columns in the table as a list.
        """
        return self.__methodMappings.keys()

    def inAnaObject(self):
        """
        Return True if the table has an OID column that is a foreign
        key to the ANA_OBJECT table.
        """
        return self.__inAnaObject

    def getMethodMappings(self):
        """
        Return the dictionary of _columnMethodMappings for the columns in
        this table.  The dictionary is keyed by column name.
        """
        return self.__methodMappings

    def getKeyMappings(self):
        """
        Return the dictionary of _columnMethodMappings for the KEY columns in
        this table.  The dictionary is keyed by column name.
        """
        return self.__keyMappings

    def getNonKeyMappings(self):
        """
        Return the dictionary of _columnMethodMappings for the NON-KEY
        columns in this table.  The dictionary is keyed by column name.
        """
        return self.__nonKeyMappings

    def registerOrphan(self, orphanDbRecord):
        """
        A record in the table no longer exists in the CIOF.  Register that
        record as an orphan.
        """
        self.__dbOrphans.append(orphanDbRecord)


    def getDbOrphans(self):
        """
        Return the list of orphan records in this table.  A record is an
        orphan if the information in it no longer appears in the CIOF.
        """
        return self.__dbOrphans

    def saveInsertStatement(self, statement):
        """
        Add an SQL insert statement to the list of insert statements for this
        table.  These insert statements are written out later.
        """
        self.__inserts.append(statement + "show warnings;")
        return None

    def saveUpdateStatement(self, statement):
        """
        Add an SQL update statement to the list of update statements for this
        table.  These update statements are written out later.
        """
        self.__updates.append(statement + "show warnings;")
        return None

    def saveDeleteStatement(self, statement):
        """
        Add an SQL delete statement to the list of delete statements for this
        table.  The delete statements are written out later.
        """
        self.__deletes.append(statement + "show warnings;")
        return None


    def writeSql(self, action, sqlFile):
        """
        Write pending SQL statements for the given action to the file.
        """
        # Find the requested type of statement
        sqlStatements = None
        if action == _ACTION_DELETE:
            sqlStatements = self.__deletes
        elif action == _ACTION_INSERT:
            sqlStatements = self.__inserts
        elif action == _ACTION_UPDATE:
            sqlStatements = self.__updates
        else:
            Util.fatalError(["Unrecognised table action: " + action])

        # Spew them to the file.
        _writeDisplayText(sqlFile,
                          "Processing " + self.getTableName() + " " + action)
        for stmt in sqlStatements:
            sqlFile.write(stmt + "\n")

        Util.statusMessage([
            str(len(sqlStatements)) + " " + action + " on " +
            self.getTableName()])

        return len(sqlStatements)




# ------------------------------------------------------------------
# DB RECORD
# ------------------------------------------------------------------

class DbRecord:
    """
    Encapsulates a DB record as returned by the DBMS
    """

    def __init__(self, anatomyClass = None, columnValues = None,
                 pythonObject = None):
        """
        Create generate a DbRecord instance.

        If columnValues are provided then this is an existing record
        from the database.  If not, then this is a new record that
        will be inserted into the database.
        """
        self.__anatomyClass  = anatomyClass
        if pythonObject:
            self.bindPythonObject(pythonObject)
        else:
            self.__pythonObject = None
        self.__columnValues  = columnValues

        return None

    def __getAnatomyClass(self):
        """
        Get the python class associated with this type of record.
        """
        return self.__anatomyClass

    def getPythonObject(self):
        """
        Get the object representing this record, based on data from
        the CIOF.
        """
        return self.__pythonObject

    def __getTableInfo(self):
        """
        Get information about the table this record is in.
        """
        return _getTableInfoByClass(self.__getAnatomyClass())

    def __getTableName(self):
        """
        Get database table name this record is in.
        """
        return self.__getTableInfo().getTableName()

    def __getColumnNames(self):
        """
        Get databaase column names for this record.
        """
        return self.__getTableInfo().getColumnNames()

    def __inAnaObject(self):
        """
        Return true if this record is for a table that has
        an OID column that is a foreign key to ANA_OBJECT.
        """
        return self.__getTableInfo().inAnaObject()

    def __getMethodMappings(self):
        """
        Get the CIOF-based <-> Database-based method mappings
        for every column in the record.
        """
        return self.__getTableInfo().getMethodMappings()

    def __getKeyMappings(self):
        """
        Get the CIOF-based <-> Database-based method mappings
        for every key column in the record.
        """
        return self.__getTableInfo().getKeyMappings()

    def __getNonKeyMappings(self):
        """
        Get the CIOF-based <-> Database-based method mappings
        for every non key column in the record.
        """
        return self.__getTableInfo().getNonKeyMappings()

    def bindPythonObject(self, pythonObject):
        """
        Associate this db record with the equivalent CIOF-base object.
        """
        self.__pythonObject = pythonObject
        anatomyClass = self.__getAnatomyClass()
        if anatomyClass:
            if anatomyClass != pythonObject.__class__:
                Util.fatalError([
                    "antomyClass (" + str(anatomyClass) +
                    ") for dbRecord disagrees with class of pythonObject (" +
                    str(pythonObject.__class__) + ")"])
        else:
            self.__anatomyClass = pythonObject.__class__

        return None

    def registerAsOrphan(self):
        """
        Orphans are database records that do not have a corresponding
        entry in the CIOF file.

        That is, they used to exist in the CIOF, but they are now completely
        gone.
        """
        tableInfo = self.__getTableInfo()
        tableInfo.registerOrphan(self)

        return None


    def getColumnValue(self, columnName):
        """
        Given the name of a column, return that column's value
        """
        columnNames = self.__getColumnNames()
        return self.__columnValues[columnNames.index(columnName)]


    def getMethodValue(self, columnName):
        """
        Given the name of a column, return the value of that column from
        a method call.
        """
        mappings = self.__getMethodMappings()
        return mappings[columnName].getMethodValue(self)



    def updateIfChanged(self):
        """
        Update record if the non-key values from CIOF are different
        from those from the database.

        Only non-key fields are checked and updated.  Returns True if
        values had changed, false if not.
        """
        changed = False
        updateMappings = []

        for mapping in self.__getNonKeyMappings().values():
            ciofVal = mapping.getMethodValue(self)
            colVal  = mapping.getColumnValue(self)
            if ciofVal != colVal:
                updateMappings.append(mapping)

        if updateMappings:
            changed = True
            self.__update(updateMappings)

        return changed


    def delete(self):
        """
        Delete record from database
        """

        keyList = self.__genKeyCondition()

        query = ("delete from " + self.__getTableName() +
                 "  where " + keyList + ";")
        self.__getTableInfo().saveDeleteStatement(query)

        if self.__inAnaObject():
            # Could use FK cascading deletes to just delete from ANA_OBJECT
            # However, that would be hard to understand when debugging.
            self.deleteFromAnaObject()
            self.__logChange(self.__getMethodMappings().values())

        return None


    def deleteFromAnaObject(self):
        """
        By definition, tables that point back to ANA_OBJECT
        have a single column primary key that is a foreign
        key pointing to ANA_OBJECT.
        """
        keys = self.__getKeyMappings().values()
        if len(keys) != 1:
            Util.fatalError([
                self.__getTableName() + " has " + len(keys) +
                " key columns when it should have 1."])
        keyCond = ("OBJ_OID = " +
                   _formatSqlValue(keys[0].getColumnValue(self)))
        query = ("delete from ANA_OBJECT" +
                 "  where " + keyCond + ";")
        self.__getTableInfo().saveDeleteStatement(query)

        return None



    def insert(self):
        """
        Generate an SQL insert statement to add this record to the database.
        """
        newObj = self.getPythonObject()
        if self.__inAnaObject():
            anaObj = AnatomyObject.getByOid(newObj.getOid())
            anaObj.getDbRecord().insert()

        # Have to generate the names and values in sync with each other.
        columnNames = None
        columnValues = None
        for mapping in self.__getMethodMappings().values():
            if columnNames:
                columnNames  += ", " + mapping.getColumnName()
                columnValues += ", " + _formatSqlValue(mapping.getMethodValue(self))
            else:
                columnNames  = mapping.getColumnName()
                columnValues = _formatSqlValue(mapping.getMethodValue(self))

        query = ("insert into " + self.__getTableName() +
                 "    ( " + columnNames + ")" +
                 "  values" +
                 "    ( " + columnValues + ");")
        self.__getTableInfo().saveInsertStatement(query)

        return None



    def historicDelete(self):
        """
        Record that an item was deleted before this database was created.

        This routine will only exist for one database update iteration.
        After that it will be an error to find a deleted item that is not
        already recorded as deleted in the databse.
        """

        Util.statusMessage([":TODO: Historic Delete"])

        return None


    def __update(self, updateMappings):
        """
        Update columns in the updateMappings list.
        """

        setList = _genSetList(self, updateMappings)
        keyList = self.__genKeyCondition()

        query = ("update " + self.__getTableName() +
                 "  set " + setList +
                 "  where " + keyList + ";")
        self.__getTableInfo().saveUpdateStatement(query)

        # If updated record has an OID in ANA_OBJECT, then log
        # the updates
        if self.__inAnaObject():
            self.__logChange(updateMappings)

        return None


    def __genKeyCondition(self):
        """
        Generate an AND separated list of column=value pairings where
        values come from the database record
        """
        keyList = [key.getColumnName() + " = " +
                   _formatSqlValue(key.getColumnValue(self))
                   for key in self.__getKeyMappings().values()]
        keyListAnd = reduce(_andConcat, keyList)

        return keyListAnd


    def __logChange(self, changedMappings):
        """
        Record a change in the log table.
        """

        if not self.__inAnaObject():
            Util.fatalError([
                "Attempt to log change to a record that does not have its",
                "primary key in ANA_OBJECT."])

        logTable = _getTableInfoByTableName("ANA_LOG")
        versionOid = Version.getOid()

        # Can't get OID of changed object from CIOF based object
        # if the change is that it is missing from the CIOF file.
        # Get the OID from the key.
        singleKey = self.__getKeyMappings().values()[0]
        changedOid = singleKey.getColumnValue(self)

        insertHeader = (
            "insert into ANA_LOG " +
            "    ( LOG_LOGGED_OID, LOG_VERSION_FK, " +
            "      LOG_COLUMN_NAME, LOG_OLD_VALUE ) " +
            "  values " +
            "    ( " + _formatSqlValue(changedOid) + ", " +
            "      " + _formatSqlValue(versionOid) + ", " +
            "      ")

        for mapping in changedMappings:
            query = (
                insertHeader +
                _formatSqlValue(mapping.getColumnName()) + ", " +
                _formatSqlValue(mapping.getColumnValue(self)) +
                " ); ")
            logTable.saveInsertStatement(query)

        return None




# ------------------------------------------------------------------
# LOCAL CLASSES
# ------------------------------------------------------------------

class _ColumnMethodMapping:
    """
    Class used to map between column names and methods
    """
    def __init__(self, columnName, methodName, isKey):
        self.__columnName = columnName
        self.__methodName = methodName
        self.__isKey      = isKey

        return None


    def getColumnName(self):
        """
        Get database column name this mapping is for.
        """
        return self.__columnName

    def getColumnValue(self, dbRecord):
        """
        Get the value of the database column.
        """
        return dbRecord.getColumnValue(self.__columnName)

    def getMethodName(self):
        """
        Get the name of the method to get the value from the CIOF-based
        oject.
        """
        return self.__methodName

    def getMethodValue(self, dbRecord):
        """
        Get the value of this column in the CIOF-based object.
        """
        return getattr(dbRecord.getPythonObject(), self.getMethodName())()

    def isKey(self):
        """
        Returns True if this is a key column, False otherwise.
        """
        return self.__isKey



# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------

def _getTableInfoByTableName(tableName):
    """
    Return the table information about the given table name.
    """
    return _tableInfoByTableName[tableName]


def _getTableInfoByClass(anatomyClass):
    """
    Given a python CIOF-based class, return the table info for the
    table that implements that class in the database.
    """
    return _tableInfoByClass[anatomyClass]


def _andConcat(str1, str2):
    """
    Concat two strings with an AND.
    """
    return str1 + " AND " + str2

def _genCommaSeparatedColumnList(columnList):
    """
    Given a list of column names, return them as a comma separated
    string.
    """
    return reduce(Util.commaConcat, columnList)


def _genSetList(dbRecord, updateMappings):
    """
    Generates a comma separated list of column=value pairings where
    values come from method calls (the CIOF file)
    """
    setList = [newVal.getColumnName() + " = " +
               _formatSqlValue(newVal.getMethodValue(dbRecord))
               for newVal in updateMappings]
    setListComma = reduce(Util.commaConcat, setList)

    return setListComma


def _formatSqlValue(value):
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
    elif value.__class__ == str:
        # need to deal with embedded quotes.
        doubleValue = re.sub('"', '""', value)
        formatted = '"' + doubleValue + '"'
    elif value.__class__ == datetime.datetime:
        formatted = '"' + value.isoformat(' ') + '"'
    else:
        Util.fatalError(["Unrecognized datatype: ", str(value.__class__)])

    return formatted


def _registerLogTable():
    """
    Registers the ANA_LOG table, which does not have a corresponding
    anatomy class.
    """

    registerClassTable(None, "ANA_LOG", NOT_IN_ANA_OBJECT)
    # Don't bother registering individual columns.

    return None


def _writeSqlComment(ciofChangesFile, comment):
    """
    Write an SQL comment to the output file.  This wraps the comment in
    /* and */.  This code does not detect embedded comment stops.
    """
    ciofChangesFile.write("\n/***** " + comment + " *****/\n\n")

    return None


def _writeDisplayText(ciofChangesFile, displayText):
    """
    Generates SQL that will display the given text when the script is
    run.  This code does not detect or escape embedded double quotes.
    """
    ciofChangesFile.write('\nselect "' + displayText + '" as "";\n\n')

    return None



def _writeTimedNodeDeferConstraints(ciofChangesFile):
    """
    Defer some constraints on timed node table until all activity
    on it is done.
    """
    _writeDisplayText(
        ciofChangesFile,
        """
        Deferring some constraints of timed node table.

        Yep, well, MySQL does not support defered constraints.
        Therefore, drop the constraint, and add it back later.
        However, cannot just drop the constraint because of a known bug.  See
          http://bugs.mysql.com/bug.php?id=21395
        Have to drop the FKs that go into the index first.
        """)

    ciofChangesFile.write(
        "alter table ANA_TIMED_NODE drop foreign key ANA_TIMED_NODE_ibfk_1;" +
        "show warnings;\n\n")
    ciofChangesFile.write(
        "alter table ANA_TIMED_NODE drop foreign key ANA_TIMED_NODE_ibfk_4;" +
        "show warnings;\n\n")
    ciofChangesFile.write(
        "alter table ANA_TIMED_NODE drop key ATN_AK2_INDEX;" +
        "show warnings;\n\n")

    return None



def _writeTimedNodeEnableConstraints(ciofChangesFile):
    """
    Enable the constraints on timed node table that were deferred earlier.
    """
    _writeDisplayText(
        ciofChangesFile,
        """
        Enable the constraints on timed node table that were deferred earlier.

        Yep, well, MySQL does not support defered constraints.  Therefore have
        to readd the constraints that were dropped earlier.
        """)

    ciofChangesFile.write(
        """
        alter table ANA_TIMED_NODE
          add constraint
            unique ATN_AK2_INDEX (ATN_NODE_FK, ATN_STAGE_FK);
        show warnings
        """)
    ciofChangesFile.write(
        """
        alter table ANA_TIMED_NODE
          add constraint ANA_TIMED_NODE_ibfk_1
            foreign key (ATN_STAGE_FK)
              REFERENCES ANA_STAGE (STG_OID)
              ON DELETE NO ACTION
              ON UPDATE NO ACTION;
        show warnings;
        """)
    ciofChangesFile.write(
        """
        alter table ANA_TIMED_NODE
          add CONSTRAINT ANA_TIMED_NODE_ibfk_4
            FOREIGN KEY (ATN_NODE_FK)
              REFERENCES ANA_NODE (ANO_OID)
              ON DELETE NO ACTION
              ON UPDATE NO ACTION;
        show warnings;
        """)

    return None


def _writeSynonymDeferConstraints(ciofChangesFile):
    """
    Defer some constraints on ANA_SYNONYM until all activity on it is done.
    """
    _writeDisplayText(
        ciofChangesFile,
        """
        Defer some constraints on ANA_SYNONYM until all activity on it is done.

        Problem occurs if capitalisation of a synonym changes.  This problem
        is MySQL specific.

        The constraint causing us problems is
           unique SYN_AK_INDEX (SYN_OBJECT_FK, SYN_SYNONYM)
        However, there is a later foreign key constraint that reuses the
        created index, so you first have to drop the FK.
        """)

    ciofChangesFile.write(
        "alter table ANA_SYNONYM drop foreign key ANA_SYNONYM_ibfk_2;" +
        "show warnings;\n\n")
    ciofChangesFile.write(
        "alter table ANA_SYNONYM drop key SYN_AK_INDEX; show warnings;\n\n")
    return None



def _writeSynonymEnableConstraints(ciofChangesFile):
    """
    Enable the constraints on ANA_SYNONYM table that were deferred earlier.
    """
    _writeDisplayText(
        ciofChangesFile,
        """
        Enable the constraints on ANA_SYNONYM table that were deferred earlier.

        Yep, well, MySQL does not support defered constraints.  Therefore have
        to readd the constraints that were dropped earlier.
        """)

    ciofChangesFile.write(
        """
        alter table ANA_SYNONYM
          add constraint
            unique SYN_AK_INDEX (SYN_OBJECT_FK, SYN_SYNONYM);
        show warnings;
        """)

    ciofChangesFile.write(
        """
        alter table ANA_SYNONYM
           add constraint ANA_SYNONYM_ibfk_2
             FOREIGN KEY (SYN_OBJECT_FK)
               REFERENCES ANA_OBJECT (OBJ_OID)
               ON DELETE NO ACTION
               ON UPDATE NO ACTION;
        show warnings;
        """)
    return None



def _printActionDebug(action, obj):
    """
    Debugging routine.
    """
    debugPublicId = "n/a"
    debugOid = "n/a"
    try:
        debugPublicId = obj.getPublicId()
    except AttributeError:
        pass                  # Not everything has a public ID
    try:
        debugOid = str(obj.getOid())
    except AttributeError:
        pass                  # Not everything has an OID

    Util.debugMessage([
        "DB Action: " + action +
        "  Public Id: " + debugPublicId + " OID: " + debugOid])

    return None


# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------






# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES, GENERIC
# ------------------------------------------------------------------

def initialise(outputFileName, dbHost, dbName, dbUser, dbPass):
    """
    Initialise the database connection.
    """
    global _anatomyConn, _tableInfoByTableName, _tableInfoByClass
    global _outputFileName

    # Dictionaries that maps Python anatomy classes to DB tables.

    _tableInfoByTableName = {}
    _tableInfoByClass = {}

    _registerLogTable()

    _anatomyConn = MySQLdb.connect(
        user = dbUser, passwd = dbPass, host = dbHost, db = dbName)

    # Where to write SQL scripts.
    _outputFileName = outputFileName

    return None


def registerClassTable(anatomyClass, anatomyTable,
                       inAnaObject):
    """
    Record the association between python classes and relational DBMS
    tables.
    """
    tableInfo = TableInfo(anatomyClass, anatomyTable, inAnaObject)
    _tableInfoByTableName[anatomyTable] = tableInfo
    _tableInfoByClass[anatomyClass]     = tableInfo

    return tableInfo



def readClassAll(anatomyClass):
    """
    Read everything from the database about this anatomy class and
    return a sequence of database records
    """
    tableInfo = _getTableInfoByClass(anatomyClass)
    columns = _genCommaSeparatedColumnList(tableInfo.getColumnNames())
    query = ("select " + columns +
             "  from " + tableInfo.getTableName() + ";")

    allCursor = _anatomyConn.cursor()
    allCursor.arraysize = 1000
    allCursor.execute(query)
    dbRows = allCursor.fetchall()

    allCursor.close()

    dbRecords = []

    for dbRow in dbRows:
        record = DbRecord(anatomyClass, dbRow)
        dbRecords.append(record)

    return dbRecords




def genClassAllSql(anatomyClass, anatomyAllIter):
    """
    Figure out differences between CIOF-based and Database-based data
    and generate SQL to bring them into synch with each other.
    """

    # Matrix of actions to take
    #                                   DB State
    #                  Active            Deleted   Missing
    #                +-----------------+-----------+---------------+
    #         Active | Detect changes, | Error(1)  | Create record |
    #                | Update record,  |           |               |
    #                | Log update    AA|         AD|             AM|
    #                +-----------------+-----------+---------------+
    #  CIOF  Deleted | Delete record,  | No action | No action (4) |
    # State          | log delete    DA|         DD|             DM|
    #                +-----------------+-----------+---------------+
    #        Missing | Warning(2),     | Error(3)  | No action     |
    #                | Delete rec.   MA|         MD|             MM|
    #                +-----------------+-----------+---------------+
    #
    # (1) Entity flagged as deleted in previous CIOF, but now is active.
    # (2) Entity active in previous CIOF, now completely gone.
    # (3) Entity flagged as deleted in previous CIOF, but now entirely
    #     missing.
    # (4) Could log historical deletes, and will probably have to
    #     eventually

    # Walk through all CIOF-based data first.  This will deal with
    # The top six cases: AA, AD, AM, DA, DD, DM

    for obj in anatomyAllIter():

        action = "Unknown"              # used in debugging
        dbRecord = obj.getDbRecord()

        if obj.isDeleted():
            if dbRecord:
                # DA: Active in previous version of CIOF, but is
                #     now marked as deleted.
                action = "DA"
                dbRecord.delete()
            else:
                # DD: Deleted in both places.  No action.
                # DM: Existed in some previous version of CIOF,
                #     but hasn't existed in any version we have checked
                #     since we started keeping track.  IGNORE
                action = "DD OR DM"
        else:
            # deal with A* cases
            try:
                publicId = obj.getPublicId()
            except AttributeError:
                publicId = None       # Not everything has a public ID

            if dbRecord:
                # AA: active in both CIOF and DB
                action = "AA"
                dbRecord.updateIfChanged()
            elif publicId and publicIdIsDeleted(publicId):
                # AD: Flagged as deleted in previous CIOF, but is now
                #     active again.
                Util.fatalError([
                    "Object flagged as deleted in previous CIOF is active " +
                    "again.",
                    "Public ID: " + publicId])
            else:
                # AM: New in this version of CIOF file
                action = "AM"
                dbRecord = obj.setDbInfo(None)
                dbRecord.insert()

        if Util.debugging():
            _printActionDebug(action, obj)

    # Deal with the MA case.  WE IGNORE THE MD case.
    tableInfo = _getTableInfoByClass(anatomyClass)

    for orphan in tableInfo.getDbOrphans():
        # MA: Not in CIOF at all, but DB says it's active.
        if Util.debugging():
            _printActionDebug("MA", orphan)
        orphan.delete()

    if len(tableInfo.getDbOrphans()):
        Util.warning([
            str(len(tableInfo.getDbOrphans())) + " "
            + tableInfo.getTableName() +
            " record(s) had counterpart in previous version of CIOF file.",
            "Now completely gone from CIOF file.  " +
            "Deleting them."])

    return None




def publicIdIsDeleted(publicId):
    """
    Returns true if the given public ID is recorded in the database
    as having been deleted.
    """
    dpiCursor = _anatomyConn.cursor()
    dpiCursor.execute("select * " +
                      "  from ANA_DELETED_PUBLIC_ID " +
                      "  where DPI_DELETED_PUBLIC_ID = " +
                      _formatSqlValue(publicId))
    if dpiCursor.rowcount:
        if dpiCursor.rowcount > 1:
            Util.fatalError([
                "Public ID " + publicId + " flagged as deleted " +
                dpiCursor.rowcount + " times"])
        return True

    return False




def finalise():
    """
    Finalisation tasks for database.  Creats SQL script that will synchronise
    the database with the CIOF.
    """
    ciofChangesFile = open(_outputFileName, "w")
    _writeDisplayText(
        ciofChangesFile,
        """
        Starting """ + _outputFileName + """
        This will synchronise the relational database with the CIOF file that
        was used to generate this script.
        """)

    # defer some pesky constraints until updates are done.
    _writeTimedNodeDeferConstraints(ciofChangesFile)
    _writeSynonymDeferConstraints(ciofChangesFile)

    # empty derived tables.  These are worthless once we start updating the
    # base tables.  They will be populated by a later script.
    _writeDisplayText(ciofChangesFile, "Emptying derived tables.")
    ciofChangesFile.write("delete from ANAD_PART_OF_PERSPECTIVE;\n")
    ciofChangesFile.write("delete from ANAD_PART_OF;\n")
    ciofChangesFile.write("delete from ANAD_RELATIONSHIP_TRANSITIVE;\n\n")

    # Get tableInfos for all tables we care about IN FOREIGN KEY
    # DEPENDENCY ORDER!
    # :TODO: This introduces a bad dependency between individual tables
    # and this code.  It would be much better to have the registration
    # process determine the order things are run, rather than have it
    # hard-coded here.
    tableInfos = []
    tableInfos.append(_getTableInfoByTableName("ANA_OBJECT"))
    tableInfos.append(_getTableInfoByTableName("ANA_VERSION"))
    tableInfos.append(_getTableInfoByTableName("ANA_STAGE"))
    tableInfos.append(_getTableInfoByTableName("ANA_NODE"))
    tableInfos.append(_getTableInfoByTableName("ANA_TIMED_NODE"))
    tableInfos.append(_getTableInfoByTableName("ANA_RELATIONSHIP"))
    tableInfos.append(_getTableInfoByTableName("ANA_SYNONYM"))
    tableInfos.append(_getTableInfoByTableName("ANA_ATTRIBUTION"))
    tableInfos.append(_getTableInfoByTableName("ANA_LOG"))

    # Generates inserts, and then updates, both in FK order.
    for action in [_ACTION_INSERT, _ACTION_UPDATE]:
        for tableInfo in tableInfos:
            tableInfo.writeSql(action, ciofChangesFile)

    # Generate deletes, in reverse FK order.  Reverse the list in place
    # because we are done with it.
    tableInfos.reverse()
    for tableInfo in tableInfos:
        tableInfo.writeSql(_ACTION_DELETE, ciofChangesFile)

    # re-enable any constraints we disabled
    _writeSynonymEnableConstraints(ciofChangesFile)
    _writeTimedNodeEnableConstraints(ciofChangesFile)

    _writeDisplayText(
        ciofChangesFile,
        """
        Finished applying CIOF changes to database.
        """)

    ciofChangesFile.close()

    _anatomyConn.close()    # Close DB connection.

    return None




# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES, CLASS / TABLE SPECIFIC
# ------------------------------------------------------------------


# ------------------------------------------------------------------
# Object
# ------------------------------------------------------------------

def readMaxOid(anatomyObjectClass):
    """
    Returns the maximum OID in use in the DB.
    """
    tableInfo = _getTableInfoByClass(anatomyObjectClass)
    query = ("select max(OBJ_OID)" +
             "  from " + tableInfo.getTableName() + ";")
    cursor = _anatomyConn.cursor()
    cursor.execute(query)
    maxOid = cursor.fetchone()[0]
    cursor.close()

    return maxOid



# ------------------------------------------------------------------
# Version
# ------------------------------------------------------------------

def readLatestVersion(versionClass):
    """
    Read the latest version information from the database
    """
    tableInfo = _getTableInfoByClass(versionClass)
    tableName = tableInfo.getTableName()
    columns   = _genCommaSeparatedColumnList(tableInfo.getColumnNames())
    query = ("select " + columns +
             "  from " + tableName +
             "  where VER_DATE = (" +
             "          select max(VER_DATE)" +
             "            from " + tableName + ");")
    cursor = _anatomyConn.cursor()
    cursor.execute(query)
    latestVer = DbRecord(versionClass, cursor.fetchone())
    cursor.close()

    return latestVer



# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------

# Run first time module is loaded.  See Initialise above

