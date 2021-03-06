/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
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
* Version:      1
*
* Description:  This class represents a SQL Database Access Object for the ComponentRelationship DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the ComponentRelationship DTO and a SQL database.
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

import utility.Wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.ComponentRelationship;

import daointerface.ComponentRelationshipDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class ComponentRelationshipDAOJDBC implements ComponentRelationshipDAO {
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
    
    private static final String SQL_LIST_ALL =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP ";
    
    private static final String SQL_LIST_ALL_BY_CHILD =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT  " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OBO_CHILD = ? ";
            
    private static final String SQL_LIST_ALL_BY_PARENT =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT  " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "WHERE ACR_OBO_PARENT = ? ";
        
    private static final String SQL_LIST_ALL_ALPHA_BY_PARENT_PART_OF =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "JOIN ANA_OBO_COMPONENT ON ACR_OBO_CHILD = AOC_OBO_ID " +
        "WHERE ACR_OBO_TYPE = 'PART_OF' " +
        "ORDER BY ACR_OBO_PARENT, AOC_NAME";
            
    private static final String SQL_LIST_ALL_ALPHA_BY_PARENT_NOT_PART_OF =
        "SELECT ACR_OID, ACR_OBO_CHILD, ACR_OBO_CHILD_START, ACR_OBO_CHILD_STOP, ACR_OBO_TYPE, ACR_OBO_PARENT " +
        "FROM ANA_OBO_COMPONENT_RELATIONSHIP " +
        "JOIN ANA_OBO_COMPONENT ON ACR_OBO_CHILD = AOC_OBO_ID " +
        "WHERE ACR_OBO_TYPE <> 'PART_OF' " +
        "ORDER BY ACR_OBO_PARENT, AOC_NAME";
                
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
     * Construct a ComponentRelationship Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public ComponentRelationshipDAOJDBC() {
    	
    }
    
    public ComponentRelationshipDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the daocomponentrelationship from the database matching the given OID, otherwise null.
     */
    public ComponentRelationship findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the daocomponentrelationships from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentRelationship> listByChild(String child) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_CHILD, child);
    }
    
    /*
     * Returns the daocomponentrelationships from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentRelationship> listByParent(String parent) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_PARENT, parent);
    }
    
    /*
     * Returns a list of ALL componentrelationships, otherwise null.
     */
    public List<ComponentRelationship> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns the daocomponentrelationship from the database matching the given OID, otherwise null.
     */
    public List<ComponentRelationship> listAllAlphabeticWithinParentIdPartOF() throws Exception {
    	
        return list(SQL_LIST_ALL_ALPHA_BY_PARENT_PART_OF);
    }
    
    /*
     * Returns the daocomponentrelationship from the database matching the given OID, otherwise null.
     */
    public List<ComponentRelationship> listAllAlphabeticWithinParentIdNotPartOf() throws Exception {
    	
        return list(SQL_LIST_ALL_ALPHA_BY_PARENT_NOT_PART_OF);
    }
    
    /*
     * Returns true if the given daocomponentrelationship OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given daocomponentrelationship in the database.
     * 
     *  If the ComponentRelationship OID is null, 
     *   then it will invoke "create(ComponentRelationship)", 
     *   else it will invoke "update(ComponentRelationship)".
     */
    public void save(ComponentRelationship daocomponentrelationship) throws Exception {
     
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
    private ComponentRelationship find(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ComponentRelationship daocomponentrelationship = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                daocomponentrelationship = mapComponentRelationship(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return daocomponentrelationship;
    }
    
    /*
     * Returns a list of all componentrelationships from the database. 
     *  The list is never null and is empty when the database does not contain any componentrelationships.
     */
    public List<ComponentRelationship> list(String sql, Object... values) throws Exception {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ComponentRelationship> componentrelationships = new ArrayList<ComponentRelationship>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                componentrelationships.add(mapComponentRelationship(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return componentrelationships;
    }
    
    /*
     * Create the given daocomponentrelationship in the database. 
     *  The daocomponentrelationship OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentrelationship OID value is unknown, rather use save(ComponentRelationship).
     *   After creating, the Data Access Object will set the obtained ID in the given daocomponentrelationship.
     */
    public void create(ComponentRelationship daocomponentrelationship) throws IllegalArgumentException, Exception {

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
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            
            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Creating ComponentRelationship failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_OBO_COMPONENT_RELATIONSHIP Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given daocomponentrelationship in the database.
     *  The daocomponentrelationship OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentrelationship OID value is unknown, rather use save(ComponentRelationship)}.
     */
    public void update(ComponentRelationship daocomponentrelationship) throws Exception {
    	
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
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

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
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_OBO_COMPONENT_RELATIONSHIP Skipped", "***", daoFactory.getMsgLevel());
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
     *  Delete the given daocomponentrelationship from the database. 
     *  After deleting, the Data Access Object will set the ID of the given daocomponentrelationship to null.
     */
    public void delete(ComponentRelationship daocomponentrelationship) throws Exception {
    	
        Object[] values = { 
        	daocomponentrelationship.getOid() 
        };

        if (daocomponentrelationship.getOid() == null) {
            throw new IllegalArgumentException("ComponentRelationship is not created yet, so the daocomponentrelationship OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

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
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_OBO_COMPONENT_RELATIONSHIP Skipped", "***", daoFactory.getMsgLevel());
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
     *  Delete the given daocomponentrelationship from the database. 
     *  After deleting, the Data Access Object will set the ID of the given daocomponentrelationship to null.
     */
    public void empty() throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_EMPTY, false);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting ALL ComponentRelationships failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_OBO_COMPONENT_RELATIONSHIP Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of ComponentRelationships for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentRelationship> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
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
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapComponentRelationship(resultSet));
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
    public long countAll() throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_ALL, false);

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
