/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        ComponentRelationshipDAO.java
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
* Description:  This class represents a SQL Database Access Object for the ComponentRelationship DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ComponentRelationship DTO and a SQL database.
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

import daomodel.ComponentRelationship;

public final class ComponentRelationshipDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OBO_PARENT LIKE ? " +
        "AND ACR_OBO_CHILD LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OBO_PARENT LIKE ? " +
        "AND ACR_OBO_CHILD LIKE ? ";

    private static final String SQL_ROW_COUNT_ALL =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP ";

    private static final String SQL_FIND_BY_OID =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT  " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OID = ? ";
    
    private static final String SQL_FIND_BY_OBO_ID =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OBO_CHILD = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP ";
    
    private static final String SQL_LIST_ALL_BY_OBO_ID =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT  " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OBO_CHILD = ? ";
            
    private static final String SQL_LIST_ALL_BY_PARENT =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT  " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OBO_PARENT = ? ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBO_COMPONENT_RELATIONSHIP " +
        "(ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT ) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBO_COMPONENT_RELATIONSHIP SET " +
        "ACR_OBO_CHILD = ?, " +
        "ACR_OBO_CHILD_START = ?, " +
        "ACR_OBO_CHILD_STOP = ?, " +
        "ACR_OBO_TYPE = ?, " + 
        "ACR_OBO_PARENT = ? " + 
        "WHERE ACR_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OID = ?";

    private static final String SQL_EMPTY =
        "DELETE FROM ANA_OBO_COMPONENT_RELATIONSHIP";

    private static final String SQL_EXIST_OID =
        "SELECT ACR_OID " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a ComponentRelationship DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    ComponentRelationshipDAO(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentrelationship from the database matching the given OID, otherwise null.
     */
    public ComponentRelationship findByOid(Long oid) throws DAOException {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the daocomponentrelationships from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentRelationship> listByOboId(String oboid) throws DAOException {
    	
        return list(SQL_LIST_ALL_BY_OBO_ID, oboid);
    }
    
    /*
     * Returns the daocomponentrelationships from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentRelationship> listByParent(String parent) throws DAOException {
    	
        return list(SQL_LIST_ALL_BY_PARENT, parent);
    }
    
    /*
     * Returns a list of ALL componentrelationships, otherwise null.
     */
    public List<ComponentRelationship> listAll() throws DAOException {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given daocomponentrelationship OID exists in the database.
     */
    public boolean existOid(String oid) throws DAOException {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given daocomponentrelationship in the database.
     * 
     *  If the ComponentRelationship OID is null, 
     *   then it will invoke "create(ComponentRelationship)", 
     *   else it will invoke "update(ComponentRelationship)".
     */
    public void save(ComponentRelationship daocomponentrelationship) throws DAOException {
     
    	if (daocomponentrelationship.getOid() == null) {
            create(daocomponentrelationship);
        }
    	else {
            update(daocomponentrelationship);
        }
    }

    /*
     * Returns the daocomponentrelationship from the database matching the given 
     *  SQL query with the given values.
     */
    private ComponentRelationship find(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ComponentRelationship daocomponentrelationship = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
                daocomponentrelationship = mapComponentRelationship(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return daocomponentrelationship;
    }
    
    /*
     * Returns a list of all componentrelationships from the database. 
     *  The list is never null and is empty when the database does not contain any componentrelationships.
     */
    public List<ComponentRelationship> list(String sql, Object... values) throws DAOException {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ComponentRelationship> componentrelationships = new ArrayList<ComponentRelationship>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                componentrelationships.add(mapComponentRelationship(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return componentrelationships;
    }
    
    /*
     * Create the given daocomponentrelationship in the database. 
     *  The daocomponentrelationship OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentrelationship OID value is unknown, rather use save(ComponentRelationship).
     *   After creating, the DAO will set the obtained ID in the given daocomponentrelationship.
     */
    public void create(ComponentRelationship daocomponentrelationship) throws IllegalArgumentException, DAOException {

    	Object[] values = {
        	daocomponentrelationship.getChild(),
        	daocomponentrelationship.getChildStart(),
        	daocomponentrelationship.getChildStop(),
            daocomponentrelationship.getType(),
        	daocomponentrelationship.getParent()
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
                    throw new DAOException("Creating ComponentRelationship failed, no rows affected.");
                } 
            }
            else {
            	System.out.println("UPDATE: Create ANA_OBO_COMPONENT_RELATIONSHIP Skipped");
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
     * Update the given daocomponentrelationship in the database.
     *  The daocomponentrelationship OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentrelationship OID value is unknown, rather use save(ComponentRelationship)}.
     */
    public void update(ComponentRelationship daocomponentrelationship) throws DAOException {
    	
        if (daocomponentrelationship.getOid() == null) {
            throw new IllegalArgumentException("ComponentRelationship is not created yet, so the daocomponentrelationship OID cannot be null.");
        }

        Object[] values = {
          	daocomponentrelationship.getChild(),
           	daocomponentrelationship.getChildStart(),
           	daocomponentrelationship.getChildStop(),
            daocomponentrelationship.getType(),
           	daocomponentrelationship.getParent(),
           	daocomponentrelationship.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Updating ComponentRelationship failed, no rows affected.");
                } 
                else {
                	daocomponentrelationship.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Update ANA_OBO_COMPONENT_RELATIONSHIP Skipped");
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
     *  Delete the given daocomponentrelationship from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentrelationship to null.
     */
    public void delete(ComponentRelationship daocomponentrelationship) throws DAOException {
    	
        Object[] values = { 
        	daocomponentrelationship.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Deleting ComponentRelationship failed, no rows affected.");
                } 
                else {
                	daocomponentrelationship.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Delete ANA_OBO_COMPONENT_RELATIONSHIP Skipped");
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
     *  Delete the given daocomponentrelationship from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentrelationship to null.
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
                    throw new DAOException("Deleting ALL ComponentRelationships failed, no rows affected.");
                } 
            }
            else {
            	System.out.println("UPDATE: Delete ALL ANA_OBO_COMPONENT_RELATIONSHIP Skipped");
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
     * Returns list of ComponentRelationships for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentRelationship> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws DAOException {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        String sqlSortField = "ACR_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "ACR_OID";       
        }
        if (sortField.equals("child")) {
        	sqlSortField = "ACR_OBO_CHILD";         
        }
        if (sortField.equals("childStart")) {
        	sqlSortField = "ACR_OBO_CHILD_START";         
        }
        if (sortField.equals("childStop")) {
        	sqlSortField = "ACR_OBO_CHILD_STOP";         
        }
        if (sortField.equals("type")) {
        	sqlSortField = "ACR_OBO_TYPE";         
        }
        if (sortField.equals("parent")) {
        	sqlSortField = "ACR_OBO_PARENT";         
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
        
        List<ComponentRelationship> dataList = new ArrayList<ComponentRelationship>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapComponentRelationship(resultSet));
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
     * Map the current row of the given ResultSet to a ComponentRelationship.
     */
    private static ComponentRelationship mapComponentRelationship(ResultSet resultSet) throws SQLException {
      
    	return new ComponentRelationship(
      		resultSet.getLong("ACR_OID"), 
       		resultSet.getString("ACR_OBO_CHILD"), 
       		resultSet.getLong("ACR_OBO_CHILD_START"), 
       		resultSet.getLong("ACR_OBO_CHILD_STOP"), 
       		resultSet.getString("ACR_OBO_TYPE"), 
       		resultSet.getString("ACR_OBO_PARENT")
        );
    }
}