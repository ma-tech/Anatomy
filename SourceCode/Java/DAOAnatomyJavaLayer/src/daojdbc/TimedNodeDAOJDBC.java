/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        TimedNodeDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Timed Node DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Timed Node DTO and a SQL database.
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

import daomodel.TimedNode;

import daointerface.TimedNodeDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class TimedNodeDAOJDBC implements TimedNodeDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_NODE_FK LIKE ? " +
        "AND ATN_PUBLIC_ID LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_NODE_FK LIKE ? " +
        "AND ATN_PUBLIC_ID LIKE ? ";

    private static final String SQL_MAX_EMAP =
        "SELECT MAX(CAST( SUBSTRING(ATN_PUBLIC_ID, 6) AS SIGNED )) AS MAXIMUM " +
        "FROM ANA_TIMED_NODE";

    private static final String SQL_FIND_BY_OID =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_OID = ?";
    
    private static final String SQL_LIST_BY_NODE_FK =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_NODE_FK = ?";
    
    private static final String SQL_LIST_BY_STAGE_FK =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_STAGE_FK = ?";
        
    private static final String SQL_LIST_BY_PUBLIC_ID =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_PUBLIC_ID = ?";
        
    private static final String SQL_LIST_BY_DISPLAY_ID =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_DISPLAY_ID = ?";
            
    private static final String SQL_LIST_ALL =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE ";
        
    private static final String SQL_LIST_ALL_ORDER_BY_PUBLIC_ID =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE " +
        "ORDER BY CAST( SUBSTRING(ATN_PUBLIC_ID, 6) AS SIGNED ) ";
            
    private static final String SQL_LIST_ALL_ORDER_BY_DISPLAY_ID =
        "SELECT ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID " +
        "FROM ANA_TIMED_NODE " +
        "ORDER BY ATN_DISPLAY_ID ";
                
    private static final String SQL_INSERT =
        "INSERT INTO ANA_TIMED_NODE " +
        "(ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_TIMED_NODE SET " +
        "ATN_NODE_FK = ?, " +
        "ATN_STAGE_FK = ?, " + 
        "ATN_STAGE_MODIFIER_FK = ?, " +
        "ATN_PUBLIC_ID = ?, " + 
        "ATN_DISPLAY_ID = ? " + 
        "WHERE ATN_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_TIMED_NODE " +
        "WHERE ATN_OID = ?";

    private static final String SQL_DELETE_BY_NODE_AND_STAGE =
        "DELETE FROM ANA_TIMED_NODE " +
        "WHERE ATN_NODE_FK = ? " +
        "AND ATN_STAGE_FK = ?";

    private static final String SQL_EXIST_OID =
        "SELECT ATN_OID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_OID = ?";

    private static final String SQL_EXIST_BY_PUBLIC_ID =
        "SELECT ATN_OID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_PUBLIC_ID = ?";

    private static final String SQL_EXIST_BY_DISPLAY_ID =
        "SELECT ATN_OID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_DISPLAY_ID = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a TimedNode Data Access Object for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public TimedNodeDAOJDBC() {
    	
    }

    public TimedNodeDAOJDBC(DAOFactory daoFactory) {

    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the maximum EMAP id.
     */
    public long maximumEmap() throws Exception {
    	
        return maximum(SQL_MAX_EMAP);
    }
    
    /*
     * Returns the TimedNode from the database matching the given OID, otherwise null.
     */
    public TimedNode findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL timednodes by Parent FK, otherwise null.
     */
    public List<TimedNode> listByNodeFK(long nodeFK) throws Exception {
    	
        return list(SQL_LIST_BY_NODE_FK, nodeFK);
    }
    
    /*
     * Returns a list of ALL timednodes by Parent FK, otherwise null.
     */
    public List<TimedNode> listByStageFK(long stageFK) throws Exception {
    	
        return list(SQL_LIST_BY_STAGE_FK, stageFK);
    }
    
    /*
     * Returns a list of ALL timednodes by Parent FK, otherwise null.
     */
    public List<TimedNode> listByPublicID(String publicID) throws Exception {
    	
        return list(SQL_LIST_BY_PUBLIC_ID, publicID);
    }
    
    /*
     * Returns a list of ALL timednodes By Display Id, otherwise null.
     */
    public List<TimedNode> listByDisplayId() throws Exception {
    	
        return list(SQL_LIST_BY_DISPLAY_ID);
    }
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<TimedNode> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<TimedNode> listAllOrderByPublicId() throws Exception {
    	
        return list(SQL_LIST_ALL_ORDER_BY_PUBLIC_ID);
    }
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<TimedNode> listAllOrderByDisplayId() throws Exception {
    	
        return list(SQL_LIST_ALL_ORDER_BY_DISPLAY_ID);
    }
    
    /*
     * Returns true if the given timednode OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Returns true if the given timednode publicId exists in the database.
     */
    public boolean existPublicId(String publicId) throws Exception {
    	
        return exist(SQL_EXIST_BY_PUBLIC_ID, publicId);
    }

    /*
     * Returns true if the given timednode displayId exists in the database.
     */
    public boolean existDisplayId(String displayId) throws Exception {
    	
        return exist(SQL_EXIST_BY_DISPLAY_ID, displayId);
    }

    /*
     * Save the given timednode in the database.
     * 
     *  If the TimedNode OID is null, 
     *   then it will invoke "create(TimedNode)", 
     *   else it will invoke "update(TimedNode)".
     */
    public void save(TimedNode timednode) throws Exception {
     
    	if (timednode.getOid() == null) {
    		
            create(timednode);
        }
    	else {
    		
            update(timednode);
        }
    }
    
    /*
     * Returns the timednode from the database matching the given 
     *  SQL query with the given values.
     */
    private TimedNode find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TimedNode timednode = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                timednode = mapTimedNode(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return timednode;
    }
    
    /*
     * Returns a list of all timednodes from the database.
     * 
     *  The list is never null and is empty when the database does not contain any timednodes.
     */
    public List<TimedNode> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TimedNode> timednodes = new ArrayList<TimedNode>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                timednodes.add(mapTimedNode(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return timednodes;
    }

    /*
     * Create the given timednode in the database.
     *  
     *  The timednode OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the timednode OID value is unknown, rather use save(TimedNode).
     *   After creating, the Data Access Object will set the obtained ID in the given timednode.
     */
    public void create(TimedNode timednode) throws IllegalArgumentException, Exception {
    	
    	Object[] values = {
   			timednode.getOid(),
   			timednode.getNodeFK(),
   			timednode.getStageFK(),
   			timednode.getStageModifierFK(),
   			timednode.getPublicId(),
   			timednode.getDisplayId()
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
                	
                    throw new DAOException("Creating TimedNode failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_TIMED_NODE Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given timednode in the database.
     * 
     *  The timednode OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the timednode OID value is unknown, rather use save(TimedNode).
     */
    public void update(TimedNode timednode) throws Exception {
    	
        if (timednode.getOid() == null) {
            throw new IllegalArgumentException("TimedNode is not created yet, so the timednode OID cannot be null.");
        }

    	Object[] values = {
   			timednode.getNodeFK(),
   			timednode.getStageFK(),
   			timednode.getStageModifierFK(),
  			timednode.getPublicId(),
  			timednode.getDisplayId(),
  			timednode.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating TimedNode failed, no rows affected.");
                } 
                else {
                	
                	timednode.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_TIMED_NODE Skipped", "***", daoFactory.getMsgLevel());
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
     * Delete the given timednode from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given timednode to null.
     */
    public void delete(TimedNode timednode) throws Exception {
    	
        Object[] values = { 
        	timednode.getOid() 
        };

        if (timednode.getOid() == null) {
            throw new IllegalArgumentException("TimedNode is not created yet, so the timednode OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting timednode failed, no rows affected.");
                } 
                else {
                	
                	timednode.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_TIMED_NODE Skipped", "***", daoFactory.getMsgLevel());
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
     * Delete the given timednode from the database.
     * 
     *  After deleting, the Data Access Object will set the ID of the given timednode to null.
     */
    public void deleteByNodeAndStage(long nodeFK, long stageFK) throws Exception {
    	
        Object[] values = { 
        	nodeFK, 
        	stageFK 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE_BY_NODE_AND_STAGE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting timednode failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_TIMED_NODE Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of TimedNodes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<TimedNode> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "ATN_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "ATN_OID";       
        }
        if (sortField.equals("nodeFK")) {
        	sqlSortField = "ATN_NODE_FK";      
        }
        if (sortField.equals("stageFK")) {
        	sqlSortField = "ATN_STAGE_FK";         
        }
        if (sortField.equals("stageModifierFK")) {
        	sqlSortField = "ATN_STAGE_MODIFIER_FK";      
        }
        if (sortField.equals("publicId")) {
        	sqlSortField = "ATN_PUBLIC_ID";         
        }
        if (sortField.equals("displayId")) {
        	sqlSortField = "ATN_DISPLAY_ID";         
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
        
        List<TimedNode> dataList = new ArrayList<TimedNode>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapTimedNode(resultSet));
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
    private static TimedNode mapTimedNode(ResultSet resultSet) throws SQLException {

    	return new TimedNode(
      		resultSet.getLong("ATN_OID"), 
       		resultSet.getLong("ATN_NODE_FK"), 
       		resultSet.getLong("ATN_STAGE_FK"), 
       		resultSet.getString("ATN_STAGE_MODIFIER_FK"),
       		resultSet.getString("ATN_PUBLIC_ID"),
       		resultSet.getString("ATN_DISPLAY_ID")
        );
    }
}
