/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ThingDAO.java
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
* Version:      1
*
* Description:  This class represents a SQL Database Access Object for the Thing (Object) DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Thing (Object) DTO and a SQL database.
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package daojdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import utility.Wrapper;

import daomodel.Thing;

import daointerface.ThingDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;
import daolayer.DAOConfigurationException;

import static daolayer.DAOUtil.*;

public final class ThingDAOJDBC implements ThingDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK, OBJ_DESCRIPTION, OBJ_TABLE " +
        "FROM ANA_OBJECT " +
        "WHERE OBJ_DESCRIPTION LIKE ? " +
        "AND OBJ_TABLE LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBJECT " +
        "WHERE OBJ_DESCRIPTION LIKE ? " +
        "AND OBJ_TABLE LIKE ? ";

    private static final String SQL_MAX_OID =
        "SELECT MAX(OBJ_OID) AS MAXIMUM " +
        "FROM ANA_OBJECT";

    private static final String SQL_FIND_BY_OID =
        "SELECT OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK, OBJ_DESCRIPTION, OBJ_TABLE " +
        "FROM ANA_OBJECT " +
        "WHERE OBJ_OID = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK, OBJ_DESCRIPTION, OBJ_TABLE " +
        "FROM ANA_OBJECT ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBJECT " +
        "(OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK, OBJ_DESCRIPTION, OBJ_TABLE) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBJECT SET " +
        "OBJ_CREATION_DATETIME = ?, " +
        "OBJ_CREATOR_FK = ?, " + 
        "OBJ_DESCRIPTION = ?, " +
        "OBJ_TABLE = ? " + 
        "WHERE OBJ_OID = ? ";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBJECT " +
        "WHERE OBJ_OID = ? ";

    private static final String SQL_EXIST_OID =
        "SELECT OBJ_OID " +
        "FROM ANA_OBJECT " +
        "WHERE OBJ_OID = ? ";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Thing Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public ThingDAOJDBC() {
    	
    }

    public ThingDAOJDBC(DAOFactory daoFactory) {

    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}

    /*
     * Return the OBO Factory Debug flag - from the OBO properties file
     */
    public String getLevel() throws DAOException {

        try {
        	
            return daoFactory.getMsgLevel();
        } 
        catch (DAOConfigurationException e) {
        	
            throw new DAOException(e);
        } 
    }

    /*
     * Returns the maximum Oid.
     */
    public long maximumOid() throws Exception {
    	
        return maximum(SQL_MAX_OID);
    }
    
    /*
     * Returns the Thing from the database matching the given OID, otherwise null.
     */
    public Thing findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL things, otherwise null.
     */
    public List<Thing> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given thing OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given thing in the database.
     * 
     *  If the Thing OID is null, 
     *   then it will invoke "create(Thing)", 
     *   else it will invoke "update(Thing)".
     */
    public void save(Thing thing) throws Exception {
     
    	if (thing.getOid() == null) {
    		
            create(thing);
        }
    	else {
    		
            update(thing);
        }
    }

    /*
     * Returns the thing from the database matching the given 
     *  SQL query with the given values.
     */
    private Thing find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Thing thing = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                thing = mapThing(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return thing;
    }
    
    /*
     * Returns a list of all things from the database. 
     *  The list is never null and is empty when the database does not contain any things.
     */
    public List<Thing> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Thing> things = new ArrayList<Thing>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                things.add(mapThing(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return things;
    }
    
    /*
     * Create the given thing in the database. 
     * 
     *  The thing OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the thing OID value is unknown, rather use save(Thing).
     *   After creating, the Data Access Object will set the obtained ID in the given thing.
     */
    public void create(Thing thing) throws IllegalArgumentException, Exception {
    	
    	Object[] values = {
    		thing.getOid(),
    		thing.getCreationDateTime(),
    		thing.getCreatorFK(),
   			thing.getDescription(),
   			thing.getTable()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Creating Thing failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_OBJECT Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, generatedKeys);
        }
    }
    
    /*
     * Update the given thing in the database.
     * 
     *  The thing OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the thing OID value is unknown, rather use save(Thing)}.
     */
    public void update(Thing thing) throws Exception {
    	
        if (thing.getOid() == null) {
            throw new IllegalArgumentException("Thing is not created yet, so the thing OID cannot be null.");
        }

    	Object[] values = {
    		thing.getCreationDateTime(),
    		thing.getCreatorFK(),
   			thing.getDescription(),
    		thing.getTable(),
   			thing.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating Thing failed, no rows affected.");
                } 
                else {
                	
                	thing.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_OBJECT Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(),connection, preparedStatement);
        }
    }
    
    /*
     * Delete the given thing from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given thing to null.
     */
    public void delete(Thing thing) throws Exception {
    	
        Object[] values = { 
        	thing.getOid() 
        };

        if (thing.getOid() == null) {
            throw new IllegalArgumentException("Thing is not created yet, so the thing OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting thing failed, no rows affected.");
                } 
                else {
                	
                	thing.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_OBJECT Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(),connection, preparedStatement);
        }
    }
    
    /*
     * Returns true if the given SQL query with the given values returns at least one row.
     */
    private boolean exist(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return exist;
    }
    
    /*
     * Returns list of Things for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Thing> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "OBJ_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "OBJ_OID";       
        }
        if (sortField.equals("creationDateTime")) {
        	sqlSortField = "OBJ_CREATION_DATETIME";      
        }
        if (sortField.equals("creatorFK")) {
        	sqlSortField = "OBJ_CREATOR_FK";         
        }
        if (sortField.equals("description")) {
        	sqlSortField = "OBJ_DESCRIPTION";      
        }
        if (sortField.equals("table")) {
        	sqlSortField = "OBJ_TABLE";         
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
        
        List<Thing> dataList = new ArrayList<Thing>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapThing(resultSet));
            }
            
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return dataList;
    }
    
    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchFirst, String searchSecond) throws Exception {

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
        long count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
                count = resultSet.getLong("VALUE");
            }
            
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return count;
    }

    /*
     * Returns total amount of rows in table.
     */
    public long maximum(String sql) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long maximum = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
            	maximum = resultSet.getLong("MAXIMUM");
            }
            
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return maximum;
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static Thing mapThing(ResultSet resultSet) throws SQLException {

    	return new Thing(
      		resultSet.getLong("OBJ_OID"), 
       		resultSet.getString("OBJ_CREATION_DATETIME"), 
       		resultSet.getLong("OBJ_CREATOR_FK"), 
       		resultSet.getString("OBJ_DESCRIPTION"),
       		resultSet.getString("OBJ_TABLE")
        );
    }
}
