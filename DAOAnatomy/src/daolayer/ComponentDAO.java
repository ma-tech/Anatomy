/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        ComponentDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Component DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Component DTO and a SQL database.
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

import daomodel.Component;

public final class ComponentDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT AOC_OID, AOC_NAME, AOC_OBO_ID, AOC_DB_ID, AOC_NEW_ID, AOC_NAMESPACE, AOC_DEFINITION, AOC_GROUP, AOC_START, AOC_END, AOC_PRESENT, AOC_STATUS_CHANGE, AOC_STATUS_RULE " +
        "FROM ANA_OBO_COMPONENT " +
        "WHERE AOC_OBO_ID LIKE ? " +
        "AND AOC_NAME LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT " +
        "WHERE AOC_OBO_ID LIKE ? " +
        "AND AOC_NAME LIKE ? ";

    private static final String SQL_ROW_COUNT_ALL =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT ";

    private static final String SQL_FIND_BY_OID =
        "SELECT AOC_OID, AOC_NAME, AOC_OBO_ID, AOC_DB_ID, AOC_NEW_ID, AOC_NAMESPACE, AOC_DEFINITION, AOC_GROUP, AOC_START, AOC_END, AOC_PRESENT, AOC_STATUS_CHANGE, AOC_STATUS_RULE " +
        "FROM ANA_OBO_COMPONENT " +
        "WHERE AOC_OID = ? ";
    
    private static final String SQL_FIND_BY_OBO_ID =
        "SELECT AOC_OID, AOC_NAME, AOC_OBO_ID, AOC_DB_ID, AOC_NEW_ID, AOC_NAMESPACE, AOC_DEFINITION, AOC_GROUP, AOC_START, AOC_END, AOC_PRESENT, AOC_STATUS_CHANGE, AOC_STATUS_RULE " +
        "FROM ANA_OBO_COMPONENT " +
        "WHERE AOC_OBO_ID = ? ";
    
    private static final String SQL_FIND_BY_OBO_NAME =
        "SELECT AOC_OID, AOC_NAME, AOC_OBO_ID, AOC_DB_ID, AOC_NEW_ID, AOC_NAMESPACE, AOC_DEFINITION, AOC_GROUP, AOC_START, AOC_END, AOC_PRESENT, AOC_STATUS_CHANGE, AOC_STATUS_RULE " +
        "FROM ANA_OBO_COMPONENT " +
        "WHERE AOC_NAME = ? ";
        
    private static final String SQL_LIST_ALL =
        "SELECT AOC_OID, AOC_NAME, AOC_OBO_ID, AOC_DB_ID, AOC_NEW_ID, AOC_NAMESPACE, AOC_DEFINITION, AOC_GROUP, AOC_START, AOC_END, AOC_PRESENT, AOC_STATUS_CHANGE, AOC_STATUS_RULE " +
        "FROM ANA_OBO_COMPONENT ";
    
    private static final String SQL_LIST_ALL_ORDER_BY_EMAPA =
        "SELECT AOC_OID, AOC_NAME, AOC_OBO_ID, AOC_DB_ID, AOC_NEW_ID, AOC_NAMESPACE, AOC_DEFINITION, AOC_GROUP, AOC_START, AOC_END, AOC_PRESENT, AOC_STATUS_CHANGE, AOC_STATUS_RULE " +
        "FROM ANA_OBO_COMPONENT ";

    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBO_COMPONENT " +
        "(AOC_NAME, AOC_OBO_ID, AOC_DB_ID, AOC_NEW_ID, AOC_NAMESPACE, AOC_DEFINITION, AOC_GROUP, AOC_START, AOC_END, AOC_PRESENT, AOC_STATUS_CHANGE, AOC_STATUS_RULE) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBO_COMPONENT SET " +
        "AOC_NAME = ?, " +
        "AOC_OBO_ID = ?, " + 
        "AOC_DB_ID = ?, " +
        "AOC_NEW_ID = ?, " +
        "AOC_NAMESPACE = ?, " +
        "AOC_DEFINITION = ?, " +
        "AOC_GROUP = ?, " + 
        "AOC_START = ?, " +
        "AOC_END = ?, " +
        "AOC_PRESENT = ?, " +
        "AOC_STATUS_CHANGE = ?, " +
        "AOC_STATUS_RULE = ? " + 
        "WHERE AOC_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBO_COMPONENT " +
        "WHERE AOC_OID = ?";

    private static final String SQL_EMPTY =
        "DELETE FROM ANA_OBO_COMPONENT";

    private static final String SQL_EXIST_OID =
        "SELECT AOC_OID " +
        "FROM ANA_OBO_COMPONENT " +
        "WHERE AOC_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Component DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    ComponentDAO(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponent from the database matching the given OID, otherwise null.
     */
    public Component findByOid(Long oid) throws DAOException {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the daocomponent from the database matching the given OBO ID, otherwise null.
     */
    public Component findByOboId(String oboid) throws DAOException {
    	
        return find(SQL_FIND_BY_OBO_ID, oboid);
    }
    
    /*
     * Returns the daocomponent from the database matching the given OBI Name, otherwise null.
     */
    public Component findByOboName(String oboname) throws DAOException {
    	
        return find(SQL_FIND_BY_OBO_NAME, oboname);
    }
    
    /*
     * Returns a list of ALL components, otherwise null.
     */
    public List<Component> listAll() throws DAOException {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of ALL components, otherwise null.
     */
    public List<Component> listAllOrderByEMAPA() throws DAOException {
    	
        return list(SQL_LIST_ALL_ORDER_BY_EMAPA);
    }
    
    /*
     * Returns true if the given daocomponent OID exists in the database.
     */
    public boolean existOid(String oid) throws DAOException {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given daocomponent in the database.
     * 
     *  If the Component OID is null, 
     *   then it will invoke "create(Component)", 
     *   else it will invoke "update(Component)".
     */
    public void save(Component daocomponent) throws DAOException {
     
    	if (daocomponent.getOid() == null) {
            create(daocomponent);
        }
    	else {
            update(daocomponent);
        }
    }

    /*
     * Returns the daocomponent from the database matching the given 
     *  SQL query with the given values.
     */
    private Component find(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Component daocomponent = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
                daocomponent = mapComponent(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return daocomponent;
    }
    
    /*
     * Returns a list of all components from the database. 
     *  The list is never null and is empty when the database does not contain any components.
     */
    public List<Component> list(String sql, Object... values) throws DAOException {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Component> components = new ArrayList<Component>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                components.add(mapComponent(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return components;
    }
    
    /*
     * Create the given daocomponent in the database. 
     *  The daocomponent OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponent OID value is unknown, rather use save(Component).
     * After creating, the DAO will set the obtained ID in the given daocomponent.
     */
    public void create(Component daocomponent) throws IllegalArgumentException, DAOException {

    	Object[] values = {
        	daocomponent.getName(),
        	daocomponent.getId(),
            daocomponent.getDbId(),
        	daocomponent.getNewId(),
        	daocomponent.getNamespace(),
        	daocomponent.getDefinition(),
        	daocomponent.getGroup(),
        	daocomponent.getStart(),
        	daocomponent.getEnd(),
        	daocomponent.getPresent(),
        	daocomponent.getStatusChange(),
        	daocomponent.getStatusRule()
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
                    throw new DAOException("Creating Component failed, no rows affected.");
                } 
            }
            else {
            	System.out.println("UPDATE: Create ANA_OBO_COMPONENT Skipped");
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
     * Update the given daocomponent in the database.
     * 
     *  The daocomponent OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponent OID value is unknown, rather use save(Component)}.
     */
    public void update(Component daocomponent) throws DAOException {
    	
        if (daocomponent.getOid() == null) {
            throw new IllegalArgumentException("Component is not created yet, so the daocomponent OID cannot be null.");
        }

        Object[] values = {
            daocomponent.getName(),
            daocomponent.getId(),
            daocomponent.getDbId(),
            daocomponent.getNewId(),
            daocomponent.getNamespace(),
        	daocomponent.getDefinition(),
            daocomponent.getGroup(),
            daocomponent.getStart(),
            daocomponent.getEnd(),
            daocomponent.getPresent(),
            daocomponent.getStatusChange(),
            daocomponent.getStatusRule(),
           	daocomponent.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Updating Component failed, no rows affected.");
                } 
                else {
                	daocomponent.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Update ANA_OBO_COMPONENT Skipped");
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
     *  Delete the given daocomponent from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponent to null.
     */
    public void delete(Component daocomponent) throws DAOException {
    	
        Object[] values = { 
        	daocomponent.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Deleting Component failed, no rows affected.");
                } 
                else {
                	daocomponent.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Delete ANA_OBO_COMPONENT Skipped");
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
     *  Delete the given daocomponent from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponent to null.
     */
    public void empty() throws DAOException {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_EMPTY, false);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Deleting ALL Components failed, no rows affected.");
                } 
            }
            else {
            	System.out.println("UPDATE: Delete ALL ANA_OBO_COMPONENT Skipped");
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
     * Returns list of Components for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Component> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws DAOException {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";
        
    	String sqlSortField = "AOC_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "AOC_OID";       
        }
        if (sortField.equals("name")) {
        	sqlSortField = "AOC_NAME";      
        }
        if (sortField.equals("id")) {
        	sqlSortField = "AOC_OBO_ID";         
        }
        if (sortField.equals("dbid")) {
        	sqlSortField = "AOC_DB_ID";         
        }
        if (sortField.equals("newid")) {
        	sqlSortField = "AOC_NEW_ID";         
        }
        if (sortField.equals("namespace")) {
        	sqlSortField = "AOC_NAMESPACE";         
        }
        if (sortField.equals("definition")) {
        	sqlSortField = "AOC_DEFINITION";         
        }
    	if (sortField.equals("group")) {
        	sqlSortField = "AOC_GROUP";       
        }
        if (sortField.equals("start")) {
        	sqlSortField = "AOC_START";      
        }
        if (sortField.equals("end")) {
        	sqlSortField = "AOC_END";         
        }
        if (sortField.equals("present")) {
        	sqlSortField = "AOC_PRESENT";         
        }
        if (sortField.equals("statuschange")) {
        	sqlSortField = "AOC_STATUS_CHANGE";         
        }
        if (sortField.equals("statusrule")) {
        	sqlSortField = "AOC_STATUS_RULE";         
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
        
        List<Component> dataList = new ArrayList<Component>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapComponent(resultSet));
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
    public int countAll() throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_ALL, false);

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

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a Component.
     */
    private static Component mapComponent(ResultSet resultSet) throws SQLException {
      
    	return new Component(
      		resultSet.getLong("AOC_OID"), 
       		resultSet.getString("AOC_NAME"), 
       		resultSet.getString("AOC_OBO_ID"), 
       		resultSet.getString("AOC_DB_ID"),
       		resultSet.getString("AOC_NEW_ID"), 
       		resultSet.getString("AOC_NAMESPACE"),      		
       		resultSet.getString("AOC_DEFINITION"),      		
       		resultSet.getInt("AOC_GROUP"), 
       		resultSet.getString("AOC_START"), 
       		resultSet.getString("AOC_END"), 
       		resultSet.getInt("AOC_PRESENT"),
       		resultSet.getString("AOC_STATUS_CHANGE"), 
       		resultSet.getString("AOC_STATUS_RULE")

        );
    }
}