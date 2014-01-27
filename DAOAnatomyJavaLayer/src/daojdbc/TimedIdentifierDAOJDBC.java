/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        TimedIdentifierDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Timed Identifier DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Timed Identifier DTO and a SQL database.
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

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.TimedIdentifier;

import daointerface.TimedIdentifierDAO;

import utility.Wrapper;

import daolayer.DAOFactory;
import daolayer.DAOException;

public final class TimedIdentifierDAOJDBC implements TimedIdentifierDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ATI_OID, ATI_OLD_PUBLIC_ID, ATI_OLD_DISPLAY_ID " +
        "FROM ANA_TIMED_IDENTIFIER " +
        "WHERE ATI_OLD_PUBLIC_ID LIKE ? " +
        "AND ATI_OLD_DISPLAY_ID LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_TIMED_IDENTIFIER " +
        "WHERE ATI_OLD_PUBLIC_ID LIKE ? " +
        "AND ATI_OLD_DISPLAY_ID LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT ATI_OID, ATI_OLD_PUBLIC_ID, ATI_OLD_DISPLAY_ID " +
        "FROM ANA_TIMED_IDENTIFIER " +
        "WHERE ATI_OID = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT ATI_OID, ATI_OLD_PUBLIC_ID, ATI_OLD_DISPLAY_ID " +
        "FROM ANA_TIMED_IDENTIFIER ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_TIMED_IDENTIFIER " +
        "(ATI_OID, ATI_OLD_PUBLIC_ID, ATI_OLD_DISPLAY_ID) " +
        "VALUES (?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_TIMED_IDENTIFIER SET " +
        "ATI_OLD_PUBLIC_ID = ?, " + 
        "ATI_OLD_DISPLAY_ID = ? " + 
        "WHERE ATI_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_TIMED_IDENTIFIER " +
        "WHERE ATI_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT ATI_OID " +
        "FROM ANA_TIMED_IDENTIFIER " +
        "WHERE ATI_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a TimedIdentifier DAO for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public TimedIdentifierDAOJDBC() {
    	
    }

    public TimedIdentifierDAOJDBC(DAOFactory daoFactory) {

    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the TimedIdentifier from the database matching the given OID, otherwise null.
     */
    public TimedIdentifier findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL timedidentifiers, otherwise null.
     */
    public List<TimedIdentifier> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given timedidentifier OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given timedidentifier in the database.
     * 
     *  If the TimedIdentifier OID is null, 
     *   then it will invoke "create(TimedIdentifier)", 
     *   else it will invoke "update(TimedIdentifier)".
     */
    public void save(TimedIdentifier timedidentifier) throws Exception {
     
    	if (timedidentifier.getOid() == null) {
    		
            create(timedidentifier);
        }
    	else {
    		
            update(timedidentifier);
        }
    }
    
    /*
     * Returns the timedidentifier from the database matching the given 
     *  SQL query with the given values.
     */
    private TimedIdentifier find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TimedIdentifier timedidentifier = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                timedidentifier = mapTimedIdentifier(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return timedidentifier;
    }
    
    /*
     * Returns a list of all timedidentifiers from the database.
     * 
     *  The list is never null and is empty when the database does not contain any timedidentifiers.
     */
    public List<TimedIdentifier> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TimedIdentifier> timedidentifiers = new ArrayList<TimedIdentifier>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                timedidentifiers.add(mapTimedIdentifier(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return timedidentifiers;
    }

    /*
     * Create the given timedidentifier in the database.
     *  
     *  The timedidentifier OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the timedidentifier OID value is unknown, rather use save(TimedIdentifier).
     *   After creating, the DAO will set the obtained ID in the given timedidentifier.
     */
    public void create(TimedIdentifier timedidentifier) throws IllegalArgumentException, Exception {
    	
    	Object[] values = {
   			timedidentifier.getOid(),
   			timedidentifier.getOldPublicId(),
   			timedidentifier.getOldDisplayId()
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
                	
                    throw new DAOException("Creating TimedIdentifier failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_TIMED_IDENTIFIER Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given timedidentifier in the database.
     * 
     *  The timedidentifier OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the timedidentifier OID value is unknown, rather use save(TimedIdentifier).
     */
    public void update(TimedIdentifier timedidentifier) throws Exception {
    	
        if (timedidentifier.getOid() == null) {
            throw new IllegalArgumentException("TimedIdentifier is not created yet, so the timedidentifier OID cannot be null.");
        }

    	Object[] values = {
  			timedidentifier.getOldPublicId(),
  			timedidentifier.getOldDisplayId(),
  			timedidentifier.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating TimedIdentifier failed, no rows affected.");
                } 
                else {
                	
                	timedidentifier.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_TIMED_IDENTIFIER Skipped", "***", daoFactory.getMsgLevel());
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
     * Delete the given timedidentifier from the database. 
     * 
     *  After deleting, the DAO will set the ID of the given timedidentifier to null.
     */
    public void delete(TimedIdentifier timedidentifier) throws Exception {
    	
        Object[] values = { 
        	timedidentifier.getOid() 
        };

        if (timedidentifier.getOid() == null) {
            throw new IllegalArgumentException("TimedIdentifier is not created yet, so the timedidentifier OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting timedidentifier failed, no rows affected.");
                } 
                else {
                	
                	timedidentifier.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_TIMED_IDENTIFIER Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of TimedIdentifiers for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<TimedIdentifier> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "ATI_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "ATI_OID";       
        }
        if (sortField.equals("nodeFK")) {
        	sqlSortField = "ATI_NODE_FK";      
        }
        if (sortField.equals("stageFK")) {
        	sqlSortField = "ATI_STAGE_FK";         
        }
        if (sortField.equals("stageModifierFK")) {
        	sqlSortField = "ATI_STAGE_MODIFIER_FK";      
        }
        if (sortField.equals("publicId")) {
        	sqlSortField = "ATI_PUBLIC_ID";         
        }
        if (sortField.equals("displayId")) {
        	sqlSortField = "ATI_DISPLAY_ID";         
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
        
        List<TimedIdentifier> dataList = new ArrayList<TimedIdentifier>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapTimedIdentifier(resultSet));
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
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
                count = resultSet.getInt("VALUE");
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
     * Map the current row of the given ResultSet to an User.
     */
    private static TimedIdentifier mapTimedIdentifier(ResultSet resultSet) throws SQLException {

    	return new TimedIdentifier(
      		resultSet.getLong("ATI_OID"), 
       		resultSet.getString("ATI_OLD_PUBLIC_ID"),
       		resultSet.getString("ATI_OLD_DISPLAY_ID")
        );
    }
}
