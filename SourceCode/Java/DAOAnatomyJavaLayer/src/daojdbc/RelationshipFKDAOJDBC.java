/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        RelationshipFKDAO.java
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
* Description:  This class represents a SQL Database Access Object for the RelationshipFK DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the RelationshipFK DTO and a SQL database.
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

import daomodel.RelationshipFK;

import daointerface.RelationshipFKDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class RelationshipFKDAOJDBC implements RelationshipFKDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, a.ANO_PUBLIC_ID, b.ANO_PUBLIC_ID " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE a ON a.ANO_OID = REL_CHILD_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = REL_PARENT_FK " +
        "WHERE REL_RELATIONSHIP_TYPE_FK LIKE ? " +
        "AND REL_CHILD_FK LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_RELATIONSHIP_TYPE_FK LIKE ? " +
        "AND REL_CHILD_FK LIKE ? ";

    private static final String SQL_MAX_OID =
        "SELECT MAX(REL_OID) AS MAXIMUM " +
        "FROM ANA_RELATIONSHIP";

    private static final String SQL_FIND_BY_OID =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, a.ANO_PUBLIC_ID, b.ANO_PUBLIC_ID " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE a ON a.ANO_OID = REL_CHILD_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = REL_PARENT_FK " +
        "WHERE REL_OID = ? ";
    
    private static final String SQL_LIST_BY_CHILD_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, a.ANO_PUBLIC_ID, b.ANO_PUBLIC_ID " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE a ON a.ANO_OID = REL_CHILD_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = REL_PARENT_FK " +
        "WHERE REL_CHILD_FK = ? ";
    
    private static final String SQL_LIST_BY_PARENT_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, a.ANO_PUBLIC_ID, b.ANO_PUBLIC_ID " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE a ON a.ANO_OID = REL_CHILD_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = REL_PARENT_FK " +
        "WHERE REL_PARENT_FK = ? ";
    
    private static final String SQL_LIST_BY_PARENT_FK_AND_CHILD_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, a.ANO_PUBLIC_ID, b.ANO_PUBLIC_ID " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE a ON a.ANO_OID = REL_CHILD_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = REL_PARENT_FK " +
        "AND REL_CHILD_FK = ? ";
        
    private static final String SQL_LIST_BY_RELATIONSHIP_TYPE_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, a.ANO_PUBLIC_ID, b.ANO_PUBLIC_ID " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE a ON a.ANO_OID = REL_CHILD_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = REL_PARENT_FK " +
        "WHERE REL_RELATIONSHIP_TYPE_FK = ? ";
        
    private static final String SQL_LIST_ALL =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, a.ANO_PUBLIC_ID, b.ANO_PUBLIC_ID " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE a ON a.ANO_OID = REL_CHILD_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = REL_PARENT_FK " +
        "ORDER BY REL_OID";
        
    private static final String SQL_EXIST_OID =
        "SELECT REL_OID " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_OID = ? ";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a RelationshipFK Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public RelationshipFKDAOJDBC() {
    	
    }

    public RelationshipFKDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the maximum Oid.
     */
    public long maximumOid() throws Exception {
    	
        return maximum(SQL_MAX_OID);
    }
    
    /*
     * Returns the RelationshipFK from the database matching the given OID, otherwise null.
     */
    public RelationshipFK findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL relationshipfks by Parent FK, otherwise null.
     */
    public List<RelationshipFK> listByParentFK(long parentFK) throws Exception {
    	
        return list(SQL_LIST_BY_PARENT_FK, parentFK);
    }
    
    /*
     * Returns a list of ALL relationshipfks by Child FK, otherwise null.
     */
    public List<RelationshipFK> listByChildFK(long childFK) throws Exception {
    	
        return list(SQL_LIST_BY_CHILD_FK, childFK);
    }
    
    /*
     * Returns a list of ALL relationshipfks by Parent FK AND Child FK, otherwise null.
     */
    public List<RelationshipFK> listByParentFKAndChildFK(long parentFK, long childFK) throws Exception {
    	
        return list(SQL_LIST_BY_PARENT_FK_AND_CHILD_FK, parentFK, childFK);
    }
    
    /*
     * Returns a list of ALL relationshipfks by RelationshipFK Type FK, otherwise null.
     */
    public List<RelationshipFK> listByRelationshipFKTypeFK(String relationshipfkTypeFK) throws Exception {
    	
        return list(SQL_LIST_BY_RELATIONSHIP_TYPE_FK, relationshipfkTypeFK);
    }
    
    /*
     * Returns a list of ALL relationshipfks, otherwise null.
     */
    public List<RelationshipFK> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given relationshipfk OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Returns the relationshipfk from the database matching the given 
     *  SQL query with the given values.
     */
    private RelationshipFK find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RelationshipFK relationshipfk = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                relationshipfk = mapRelationshipFK(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationshipfk;
    }

    /*
     * Returns a list of all relationshipfks from the database. 
     *  The list is never null and is empty when the database does not contain any relationshipfks.
     */
    public List<RelationshipFK> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RelationshipFK> relationshipfks = new ArrayList<RelationshipFK>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                relationshipfks.add(mapRelationshipFK(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationshipfks;
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
     * Returns list of RelationshipFKs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<RelationshipFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "REL_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "REL_OID";       
        }
        if (sortField.equals("typeFK")) {
        	sqlSortField = "REL_RELATIONSHIP_TYPE_FK";      
        }
        if (sortField.equals("childFK")) {
        	sqlSortField = "REL_CHILD_FK";         
        }
        if (sortField.equals("parentFK")) {
        	sqlSortField = "REL_PARENT_FK";         
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
        
        List<RelationshipFK> dataList = new ArrayList<RelationshipFK>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapRelationshipFK(resultSet));
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
    private static RelationshipFK mapRelationshipFK(ResultSet resultSet) throws SQLException {

    	return new RelationshipFK(
      		resultSet.getLong("REL_OID"), 
       		resultSet.getString("REL_RELATIONSHIP_TYPE_FK"), 
       		resultSet.getString("a.ANO_PUBLIC_ID"), 
       		resultSet.getString("b.ANO_PUBLIC_ID")
        );
    }
}
