/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        ComponentCommentDAO.java
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
package daojdbc;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import utility.Wrapper;

import daomodel.ComponentComment;

import daointerface.ComponentCommentDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

public final class ComponentCommentDAOJDBC implements ComponentCommentDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ACC_OID, ACC_OBO_ID, ACC_OBO_GENERAL_COMMENT, ACC_OBO_USER_COMMENT, ACC_OBO_ORDER_COMMENT " +
        "FROM ANA_OBO_COMPONENT_COMMENT " +
        "WHERE ACC_OBO_GENERAL_COMMENT LIKE ? " +
        "AND ACR_OBO_ID LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_COMMENT " +
        "WHERE ACC_OBO_GENERAL_COMMENT LIKE ? " +
        "AND ACR_OBO_ID LIKE ? ";

    private static final String SQL_ROW_COUNT_ALL =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_COMMENT ";

    private static final String SQL_FIND_BY_OID =
        "SELECT ACC_OID, ACC_OBO_ID, ACC_OBO_GENERAL_COMMENT, ACC_OBO_USER_COMMENT, ACC_OBO_ORDER_COMMENT " +
        "FROM ANA_OBO_COMPONENT_COMMENT " +
        "WHERE ACC_OID = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT ACC_OID, ACC_OBO_ID, ACC_OBO_GENERAL_COMMENT, ACC_OBO_USER_COMMENT, ACC_OBO_ORDER_COMMENT " +
        "FROM ANA_OBO_COMPONENT_COMMENT ";
    
    private static final String SQL_LIST_ALL_BY_OBO_ID =
        "SELECT ACC_OID, ACC_OBO_ID, ACC_OBO_GENERAL_COMMENT, ACC_OBO_USER_COMMENT, ACC_OBO_ORDER_COMMENT " +
        "FROM ANA_OBO_COMPONENT_COMMENT " +
        "WHERE ACC_OBO_ID = ? ";
            
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBO_COMPONENT_COMMENT " +
        "(ACC_OBO_ID, ACC_OBO_GENERAL_COMMENT, ACC_OBO_USER_COMMENT, ACC_OBO_ORDER_COMMENT) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBO_COMPONENT_COMMENT SET " +
        "ACC_OBO_ID = ?, " +
        "ACC_OBO_GENERAL_COMMENT = ?, " + 
        "ACC_OBO_USER_COMMENT = ?, " + 
        "ACC_OBO_ORDER_COMMENT = ? " + 
        "WHERE ACC_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBO_COMPONENT_COMMENT " +
        "WHERE ACC_OID = ?";

    private static final String SQL_EMPTY =
        "DELETE FROM ANA_OBO_COMPONENT_COMMENT";

    private static final String SQL_EXIST_OID =
        "SELECT ACC_OID " +
        "FROM ANA_OBO_COMPONENT_COMMENT " +
        "WHERE ACC_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a ComponentComment DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public ComponentCommentDAOJDBC() {
    	
    }
    
    public ComponentCommentDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the daocomponentcomment from the database matching the given OID, otherwise null.
     */
    public ComponentComment findByOid(Long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the daocomponentcomments from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentComment> listByOboId(String oboid) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_OBO_ID, oboid);
    }
    
    /*
     * Returns a list of ALL componentcomments, otherwise null.
     */
    public List<ComponentComment> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given daocomponentcomment OID exists in the database.
     */
    public boolean existOid(String oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given daocomponentcomment in the database.
     * 
     *  If the ComponentComment OID is null, 
     *   then it will invoke "create(ComponentComment)", 
     *   else it will invoke "update(ComponentComment)".
     */
    public void save(ComponentComment daocomponentcomment) throws Exception {
     
    	if (daocomponentcomment.getOid() == null) {
    		
            create(daocomponentcomment);
        }
    	else {
    		
            update(daocomponentcomment);
        }
    }

    /*
     * Returns the daocomponentcomment from the database matching the given 
     *  SQL query with the given values.
     */
    private ComponentComment find(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ComponentComment daocomponentcomment = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                daocomponentcomment = mapComponentComment(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return daocomponentcomment;
    }
    
    /*
     * Returns a list of all componentcomments from the database. 
     *  The list is never null and is empty when the database does not contain any componentcomments.
     */
    public List<ComponentComment> list(String sql, Object... values) throws Exception {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ComponentComment> componentcomments = new ArrayList<ComponentComment>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                componentcomments.add(mapComponentComment(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return componentcomments;
    }
    
    /*
     * Create the given daocomponentcomment in the database. 
     * 
     *  The daocomponentcomment OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentcomment OID value is unknown, rather use save(ComponentComment).
     *   After creating, the DAO will set the obtained ID in the given daocomponentcomment.
     */
    public void create(ComponentComment daocomponentcomment) throws IllegalArgumentException, Exception {

    	Object[] values = {
        	daocomponentcomment.getId(),
            daocomponentcomment.getGeneral(),
            daocomponentcomment.getUser(),
        	daocomponentcomment.getOrder()
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
                	
                    throw new DAOException("Creating ComponentComment failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ALL ANA_OBO_COMPONENT_COMMENT Skipped", "***", daoFactory.getLevel());
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
     * Update the given daocomponentcomment in the database.
     * 
     *  The daocomponentcomment OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentcomment OID value is unknown, rather use save(ComponentComment)}.
     */
    public void update(ComponentComment daocomponentcomment) throws Exception {
    	
        if (daocomponentcomment.getOid() == null) {
        	
            throw new IllegalArgumentException("ComponentComment is not created yet, so the daocomponentcomment OID cannot be null.");
        }

        Object[] values = {
            daocomponentcomment.getId(),
            daocomponentcomment.getGeneral(),
            daocomponentcomment.getUser(),
            daocomponentcomment.getOrder(),
           	daocomponentcomment.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating ComponentComment failed, no rows affected.");
                } 
                else {
                	
                	daocomponentcomment.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ALL ANA_OBO_COMPONENT_COMMENT Skipped", "***", daoFactory.getLevel());
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
     *  Delete the given daocomponentcomment from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentcomment to null.
     */
    public void delete(ComponentComment daocomponentcomment) throws Exception {
    	
        Object[] values = { 
        	daocomponentcomment.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting ComponentComment failed, no rows affected.");
                } 
                else {
                	
                	daocomponentcomment.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ALL ANA_OBO_COMPONENT_COMMENT Skipped", "***", daoFactory.getLevel());
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
     *  Delete the given daocomponentcomment from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentcomment to null.
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
                	
                    throw new DAOException("Deleting ALL ComponentComments failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ALL ANA_OBO_COMPONENT_COMMENT Skipped", "***", daoFactory.getLevel());
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
     * Returns list of ComponentComments for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentComment> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        String sqlSortField = "ACC_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "ACC_OID";       
        }
        if (sortField.equals("id")) {
        	sqlSortField = "ACC_OBO_ID";         
        }
        if (sortField.equals("general")) {
        	sqlSortField = "ACC_OBO_GENERAL_COMMENT";         
        }
        if (sortField.equals("user")) {
        	sqlSortField = "ACC_OBO_USER_COMMENT";         
        }
        if (sortField.equals("order")) {
        	sqlSortField = "ACC_OBO_USER_COMMENT";         
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
        
        List<ComponentComment> dataList = new ArrayList<ComponentComment>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapComponentComment(resultSet));
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
     * Map the current row of the given ResultSet to a ComponentComment.
     */
    private static ComponentComment mapComponentComment(ResultSet resultSet) throws SQLException {

    	return new ComponentComment(
      		resultSet.getLong("ACC_OID"), 
       		resultSet.getString("ACC_OBO_ID"), 
       		resultSet.getString("ACC_OBO_GENERAL_COMMENT"), 
       		resultSet.getString("ACC_OBO_USER_COMMENT"), 
       		resultSet.getString("ACC_OBO_ORDER_COMMENT")
        );
    }
}
