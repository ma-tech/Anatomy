/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ComponentOrderDAO.java
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
* Description:  This class represents a SQL Database Access Object for the ComponentOrder DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ComponentOrder DTO and a SQL database.
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

import utility.Wrapper;

import daomodel.ComponentOrder;

public final class ComponentOrderDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ACO_OID, ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OBO_PARENT LIKE ? " +
        "AND ACO_OBO_CHILD LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OBO_PARENT LIKE ? " +
        "AND ACO_OBO_CHILD LIKE ? ";

    private static final String SQL_ROW_COUNT_ALL =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_ORDER ";

    private static final String SQL_FIND_BY_OID =
        "SELECT ACO_OID, ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OID = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT ACO_OID, ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER " +
        "FROM ANA_OBO_COMPONENT_ORDER ";
    
    private static final String SQL_LIST_ALL_ORDER_BY_PARENT_SPECIAL_ORDER =
        "SELECT ACO_OID, ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OBO_TYPE = 'PART_OF' " +
        "ORDER BY ACO_OBO_PARENT, ACO_OBO_SPECIAL_ORDER ";
    
    private static final String SQL_LIST_ALL_ORDER_BY_PARENT_ALPHA_ORDER =
        "SELECT ACO_OID, ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OBO_TYPE = 'PART_OF' " +
        "ORDER BY ACO_OBO_PARENT, ACO_OBO_ALPHA_ORDER ";
        
    private static final String SQL_LIST_ALL_BY_CHILD =
        "SELECT ACO_OID, ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OBO_CHILD = ? ";
            
    private static final String SQL_LIST_ALL_BY_PARENT =
        "SELECT ACO_OID, ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OBO_PARENT = ? ";
        
    private static final String SQL_LIST_BY_CHILD_AND_PARENT =
        "SELECT ACO_OID, ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OBO_CHILD = ? " +
        "AND ACO_OBO_PARENT = ? ";
            
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBO_COMPONENT_ORDER " +
        "(ACO_OBO_CHILD, ACO_OBO_PARENT, ACO_OBO_TYPE, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER ) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBO_COMPONENT_ORDER SET " +
        "ACO_OBO_CHILD = ?, " +
        "ACO_OBO_PARENT = ?, " + 
        "ACO_OBO_TYPE = ?, " + 
        "ACO_OBO_ALPHA_ORDER = ?, " + 
        "ACO_OBO_SPECIAL_ORDER = ? " + 
        "WHERE ACO_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OID = ?";

    private static final String SQL_EMPTY =
        "DELETE FROM ANA_OBO_COMPONENT_ORDER";

    private static final String SQL_EXIST_OID =
        "SELECT ACO_OID " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "WHERE ACO_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a ComponentOrder DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    ComponentOrderDAO(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public ComponentOrder findByOid(Long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public List<ComponentOrder> listOrderByParentBySpecialOrder() throws Exception {
    	
        return list(SQL_LIST_ALL_ORDER_BY_PARENT_SPECIAL_ORDER);
    }
    
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public List<ComponentOrder> listOrderByParentByAlphaOrder() throws Exception {
    	
        return list(SQL_LIST_ALL_ORDER_BY_PARENT_ALPHA_ORDER);
    }
    
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public List<ComponentOrder> listByChildIdAndParentID(String childId, String parentId) throws Exception {
    	
        return list(SQL_LIST_BY_CHILD_AND_PARENT, childId, parentId);
    }
    
    /*
     * Returns the daocomponentorders from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentOrder> listByChild(String child) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_CHILD, child);
    }
    
    /*
     * Returns the daocomponentorders from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentOrder> listByParent(String parent) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_PARENT, parent);
    }
    
    /*
     * Returns a list of ALL componentorders, otherwise null.
     */
    public List<ComponentOrder> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given daocomponentorder OID exists in the database.
     */
    public boolean existOid(String oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given daocomponentorder in the database.
     * 
     *  If the ComponentOrder OID is null, 
     *   then it will invoke "create(ComponentOrder)", 
     *   else it will invoke "update(ComponentOrder)".
     */
    public void save(ComponentOrder daocomponentorder) throws Exception {
     
    	if (daocomponentorder.getOid() == null) {
    		
            create(daocomponentorder);
        }
    	else {
    		
            update(daocomponentorder);
        }
    }

    /*
     * Returns the daocomponentorder from the database matching the given 
     *  SQL query with the given values.
     */
    private ComponentOrder find(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ComponentOrder daocomponentorder = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                daocomponentorder = mapComponentOrder(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return daocomponentorder;
    }
    
    /*
     * Returns a list of all componentorders from the database. 
     *  The list is never null and is empty when the database does not contain any componentorders.
     */
    public List<ComponentOrder> list(String sql, Object... values) throws Exception {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ComponentOrder> componentorders = new ArrayList<ComponentOrder>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                componentorders.add(mapComponentOrder(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return componentorders;
    }
    
    /*
     * Create the given daocomponentorder in the database. 
     *  The daocomponentorder OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentorder OID value is unknown, rather use save(ComponentOrder).
     *   After creating, the DAO will set the obtained ID in the given daocomponentorder.
     */
    public void create(ComponentOrder daocomponentorder) throws IllegalArgumentException, Exception {

    	Object[] values = {
        	daocomponentorder.getChild(),
        	daocomponentorder.getParent(),
        	daocomponentorder.getType(),
        	daocomponentorder.getAlphaorder(),
        	daocomponentorder.getSpecialorder()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            
            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Creating ComponentOrder failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_OBO_COMPONENT_ORDER Skipped", "***", daoFactory.getLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, generatedKeys);
        }
    }
    
    /*
     * Update the given daocomponentorder in the database.
     *  The daocomponentorder OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentorder OID value is unknown, rather use save(ComponentOrder)}.
     */
    public void update(ComponentOrder daocomponentorder) throws Exception {
    	
        if (daocomponentorder.getOid() == null) {
        	
            throw new IllegalArgumentException("ComponentOrder is not created yet, so the daocomponentorder OID cannot be null.");
        }

        Object[] values = {
          	daocomponentorder.getChild(),
           	daocomponentorder.getParent(),
           	daocomponentorder.getType(),
           	daocomponentorder.getAlphaorder(),
           	daocomponentorder.getSpecialorder(),
           	daocomponentorder.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating ComponentOrder failed, no rows affected.");
                } 
                else {
                	
                	daocomponentorder.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_OBO_COMPONENT_ORDER Skipped", "***", daoFactory.getLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(),connection, preparedStatement);
        }
    }
     
    /*
     *  Delete the given daocomponentorder from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentorder to null.
     */
    public void delete(ComponentOrder daocomponentorder) throws Exception {
    	
        Object[] values = { 
        	daocomponentorder.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting ComponentOrder failed, no rows affected.");
                } 
                else {
                	
                	daocomponentorder.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_OBO_COMPONENT_ORDER Skipped", "***", daoFactory.getLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(),connection, preparedStatement);
        }
    }
    
    /*
     *  Delete the given daocomponentorder from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentorder to null.
     */
    public void empty() throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_EMPTY, false);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting ALL ComponentOrders failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_OBO_COMPONENT_ORDER Skipped", "***", daoFactory.getLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(),connection, preparedStatement);
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
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return exist;
    }

    /*
     * Returns list of ComponentOrders for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentOrder> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        String sqlSortField = "ACO_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "ACO_OID";       
        }
        if (sortField.equals("child")) {
        	sqlSortField = "ACO_OBO_CHILD";         
        }
        if (sortField.equals("parent")) {
        	sqlSortField = "ACO_OBO_PARENT";         
        }
        if (sortField.equals("type")) {
        	sqlSortField = "ACO_OBO_TYPE";         
        }
        if (sortField.equals("specialorder")) {
        	sqlSortField = "ACO_OBO_SPECIAL_ORDER";         
        }
        if (sortField.equals("alphaorder")) {
        	sqlSortField = "ACO_OBO_ALPHA_ORDER";         
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
        
        List<ComponentOrder> dataList = new ArrayList<ComponentOrder>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapComponentOrder(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return dataList;
    }
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws Exception {

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
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
                count = resultSet.getInt("VALUE");
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return count;
    }

    /*
     * Returns total amount of rows in table.
     */
    public int countAll() throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_ALL, false);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
                count = resultSet.getInt("VALUE");
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return count;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a ComponentOrder.
     */
    private static ComponentOrder mapComponentOrder(ResultSet resultSet) throws SQLException {
      
    	return new ComponentOrder(
      		resultSet.getLong("ACO_OID"), 
       		resultSet.getString("ACO_OBO_CHILD"), 
       		resultSet.getString("ACO_OBO_PARENT"),
       		resultSet.getString("ACO_OBO_TYPE"),
      		resultSet.getLong("ACO_OBO_ALPHA_ORDER"),
      		resultSet.getLong("ACO_OBO_SPECIAL_ORDER")
        );
    }
}
