/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        LogDAO.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  This class represents a SQL Database Access Object for the Log DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Log DTO and a SQL database.
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package daolayer;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.Log;

public final class LogDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT LOG_OID, LOG_LOGGED_OID, LOG_VERSION_FK, LOG_COLUMN_NAME, LOG_OLD_VALUE, LOG_COMMENTS " +
        "FROM ANA_LOG " +
        "WHERE LOG_COLUMN_NAME LIKE ? " +
        "AND LOG_OLD_VALUE LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_LOG " +
        "WHERE LOG_COLUMN_NAME LIKE ? " +
        "AND LOG_OLD_VALUE LIKE ? ";

    private static final String SQL_MAX_OID =
        "SELECT MAX(LOG_OID) AS MAXIMUM " +
        "FROM ANA_LOG";

    private static final String SQL_MAX_LOGGED_OID =
        "SELECT MAX(LOG_LOGGED_OID) AS MAXIMUM " +
        "FROM ANA_LOG";

    private static final String SQL_FIND_BY_OID =
        "SELECT LOG_OID, LOG_LOGGED_OID, LOG_VERSION_FK, LOG_COLUMN_NAME, LOG_OLD_VALUE, LOG_COMMENTS " +
        "FROM ANA_LOG " +
        "WHERE LOG_OID = ? ";
    
    private static final String SQL_FIND_BY_LOGGED_OID =
        "SELECT LOG_OID, LOG_LOGGED_OID, LOG_VERSION_FK, LOG_COLUMN_NAME, LOG_OLD_VALUE, LOG_COMMENTS " +
        "FROM ANA_LOG " +
        "WHERE LOG_LOGGED_OID = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT LOG_OID, LOG_LOGGED_OID, LOG_VERSION_FK, LOG_COLUMN_NAME, LOG_OLD_VALUE, LOG_COMMENTS " +
        "FROM ANA_LOG ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_LOG " +
        "(LOG_OID, LOG_LOGGED_OID, LOG_VERSION_FK, LOG_COLUMN_NAME, LOG_OLD_VALUE, LOG_COMMENTS) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_LOG SET " +
        "LOG_LOGGED_OID = ?, " +
        "LOG_VERSION_FK = ?, " + 
        "LOG_COLUMN_NAME = ?, " +
        "LOG_OLD_VALUE = ?, " +
        "LOG_COMMENTS = ? " + 
        "WHERE LOG_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_LOG " +
        "WHERE LOG_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT LOG_OID " +
        "FROM ANA_LOG " +
        "WHERE LOG_OID = ?";


    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Log DAO for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the DAO package only.
     */
    LogDAO(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum Oid.
     */
    public int maximumOid() throws DAOException {
    	
        return maximum(SQL_MAX_OID);
    }
    
    /*
     * Returns the maximum Logged Oid.
     */
    public int maximumLoggedOid() throws DAOException {
    	
        return maximum(SQL_MAX_LOGGED_OID);
    }
    
    /*
     * Returns the log from the database matching the given OID, otherwise null.
     */
    public Log findByOid(Long oid) throws DAOException {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the log from the database matching the given Logged OID, otherwise null.
     */
    public Log findByLoggedOid(Long loggedOid) throws DAOException {
    	
        return find(SQL_FIND_BY_LOGGED_OID, loggedOid);
    }
    
    /*
     * Returns a list of ALL logs, otherwise null.
     */
    public List<Log> listAll() throws DAOException {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given log OID exists in the database.
     */
    public boolean existOid(Long oid) throws DAOException {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given log in the database.
     * 
     *  If the Log OID is null, 
     *   then it will invoke "create(Log)", 
     *   else it will invoke "update(Log)".
     */
    public void save(Log log) throws DAOException {
     
    	if (log.getOid() == null) {
            create(log);
        }
    	else {
            update(log);
        }
    }

    /*
     * Returns the log from the database matching the given 
     *  SQL query with the given values.
     */
    private Log find(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Log log = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
                log = mapLog(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return log;
    }
    
    /*
     * Returns a list of all logs from the database. 
     *  The list is never null and is empty when the database does not contain any logs.
     */
    public List<Log> list(String sql, Object... values) throws DAOException {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Log> logs = new ArrayList<Log>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                logs.add(mapLog(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return logs;
    }
    
    /*
     * Create the given log in the database. 
     * 
     *  The log OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the log OID value is unknown, rather use save(Log).
     *   After creating, the DAO will set the obtained ID in the given log.
     */
    public void create(Log log) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
        	log.getOid(),
        	log.getLoggedOid(),
            log.getVersionFK(),
        	log.getColumnName(),
        	log.getOldValue(),
        	log.getComments()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            
            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Creating Log failed, no rows affected.");
                } 
            }
            else {
            	System.out.println("UPDATE: Create ANA_LOG Skipped");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }
    
    /*
     * Update the given log in the database.
     * 
     *  The log OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the log OID value is unknown, rather use save(Log)}.
     */
    public void update(Log log) throws DAOException {
    	
        if (log.getOid() == null) {
            throw new IllegalArgumentException("Log is not created yet, so the log OID cannot be null.");
        }

        Object[] values = {
          	log.getLoggedOid(),
            log.getVersionFK(),
           	log.getColumnName(),
           	log.getOldValue(),
           	log.getComments(),           	
           	log.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Updating Log failed, no rows affected.");
                } 
                else {
                	log.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Update ANA_LOG Skipped");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
     
    /*
     *  Delete the given log from the database. 
     *  
     *  After deleting, the DAO will set the ID of the given log to null.
     */
    public void delete(Log log) throws DAOException {
    	
        Object[] values = { 
        	log.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Deleting Log failed, no rows affected.");
                } 
                else {
                	log.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Delete ANA_LOG Skipped");
            }

        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
    
    /*
     * Returns true if the given SQL query with the given values returns at least one row.
     */
    private boolean exist(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return exist;
    }
    
    /*
     * Returns list of Logs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Log> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws DAOException {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "LOG_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "LOG_OID";       
        }
        if (sortField.equals("loggedOid")) {
        	sqlSortField = "LOG_LOGGED_OID";      
        }
        if (sortField.equals("versionFK")) {
        	sqlSortField = "LOG_VERSION_FK";         
        }
        if (sortField.equals("columnName")) {
        	sqlSortField = "LOG_COLUMN_NAME";         
        }
        if (sortField.equals("oldValue")) {
        	sqlSortField = "LOG_OLD_VALUE";         
        }
        if (sortField.equals("comments")) {
        	sqlSortField = "LOG_COMMENTS";         
        }
        
        if (searchFirst.equals("")) {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
    	}
        else {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
        }

        if (searchSecond.equals("")) {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
    	}
        else {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
        }
        
        Object[] values = {
       		searchFirstWithWildCards, 
       		searchSecondWithWildCards,
            firstRow, 
            rowCount
        };

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<Log> dataList = new ArrayList<Log>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapLog(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return dataList;
    }
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws DAOException {

        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        if (searchFirst.equals("")) {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
    	}
        else {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
        }

        if (searchSecond.equals("")) {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
    	}
        else {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
        }

        Object[] values = {
       		searchFirstWithWildCards, 
       		searchSecondWithWildCards
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("VALUE");
            }
            
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return count;
    }

    /*
     * Returns total amount of rows in table.
     */
    public int maximum(String sql) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int maximum = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	maximum = resultSet.getInt("MAXIMUM");
            }
            
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return maximum;
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a Log.
     */
    private static Log mapLog(ResultSet resultSet) throws SQLException {
      
    	return new Log(
      		resultSet.getLong("LOG_OID"), 
       		resultSet.getLong("LOG_LOGGED_OID"), 
       		resultSet.getLong("LOG_VERSION_FK"), 
       		resultSet.getString("LOG_COLUMN_NAME"),
       		resultSet.getString("LOG_OLD_VALUE"), 
       		resultSet.getString("LOG_COMMENTS")
        );
    }
}