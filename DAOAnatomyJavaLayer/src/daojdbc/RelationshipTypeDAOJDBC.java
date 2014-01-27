/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        RelationshipTypeDAO.java
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
* Description:  This class represents a SQL Database Access Object for the RelationshipType DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the RelationshipType DTO and a SQL database.
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

import daomodel.RelationshipType;

import daointerface.RelationshipTypeDAO;

import utility.Wrapper;

import daolayer.DAOFactory;
import daolayer.DAOException;


public final class RelationshipTypeDAOJDBC implements RelationshipTypeDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT RTY_NAME, RTY_CHILD_TO_PARENT_DISPLAY, RTY_PARENT_TO_CHILD_DISPLAY " +
        "FROM ANA_RELATIONSHIP_TYPE " +
        "WHERE RTY_CHILD_TO_PARENT_DISPLAY LIKE ? " +
        "AND RTY_PARENT_TO_CHILD_DISPLAY LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_RELATIONSHIP_TYPE " +
        "WHERE RTY_CHILD_TO_PARENT_DISPLAY LIKE ? " +
        "AND RTY_PARENT_TO_CHILD_DISPLAY LIKE ? ";

    private static final String SQL_FIND_BY_NAME =
        "SELECT RTY_NAME, RTY_CHILD_TO_PARENT_DISPLAY, RTY_PARENT_TO_CHILD_DISPLAY " +
        "FROM ANA_RELATIONSHIP_TYPE " +
        "WHERE RTY_NAME = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT RTY_NAME, RTY_CHILD_TO_PARENT_DISPLAY, RTY_PARENT_TO_CHILD_DISPLAY " +
        "FROM ANA_RELATIONSHIP_TYPE ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_RELATIONSHIP_TYPE " +
        "(RTY_NAME, RTY_CHILD_TO_PARENT_DISPLAY, RTY_PARENT_TO_CHILD_DISPLAY) " +
        "VALUES (?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_RELATIONSHIP_TYPE SET " +
        "RTY_CHILD_TO_PARENT_DISPLAY = ?, " +
        "RTY_PARENT_TO_CHILD_DISPLAY = ?, " +
        "WHERE RTY_NAME = ? ";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_RELATIONSHIP_TYPE " +
        "WHERE RTY_NAME = ? ";

    private static final String SQL_EXIST_NAME =
        "SELECT RTY_NAME " +
        "FROM ANA_RELATIONSHIP_TYPE " +
        "WHERE RTY_NAME = ? ";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a RelationshipType DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public RelationshipTypeDAOJDBC() {
    	
    }

    public RelationshipTypeDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the RelationshipType from the database matching the given OID, otherwise null.
     */
    public RelationshipType findByName(String name) throws Exception {
    	
        return find(SQL_FIND_BY_NAME, name);
    }
    
    /*
     * Returns a list of ALL relationshiptypes, otherwise null.
     */
    public List<RelationshipType> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given relationshiptype OID exists in the database.
     */
    public boolean existName(String name) throws Exception {
    	
        return exist(SQL_EXIST_NAME, name);
    }

    /*
     * Save the given relationshiptype in the database.
     * 
     *  If the RelationshipType OID is null, 
     *   then it will invoke "create(RelationshipType)", 
     *   else it will invoke "update(RelationshipType)".
     */
    public void save(RelationshipType relationshiptype) throws Exception {
     
    	if (relationshiptype.getName() == null) {
    		
            create(relationshiptype);
        }
    	else {
    		
            update(relationshiptype);
        }
    }
    
    /*
     * Returns the relationshiptype from the database matching the given 
     *  SQL query with the given values.
     */
    private RelationshipType find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RelationshipType relationshiptype = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                relationshiptype = mapRelationshipType(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationshiptype;
    }

    /*
     * Returns a list of all relationshiptypes from the database. 
     *  The list is never null and is empty when the database does not contain any relationshiptypes.
     */
    public List<RelationshipType> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RelationshipType> relationshiptypes = new ArrayList<RelationshipType>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                relationshiptypes.add(mapRelationshipType(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationshiptypes;
    }
    
    /*
     * Create the given relationshiptype in the database. 
     *  The relationshiptype OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the relationshiptype OID value is unknown, rather use save(RelationshipType).
     *   After creating, the DAO will set the obtained ID in the given relationshiptype.
     */
    public void create(RelationshipType relationshiptype) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
       		relationshiptype.getName(),
       		relationshiptype.getParent2Child(),
       		relationshiptype.getChild2Parent()
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
                	
                    throw new DAOException("Creating RelationshipType failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_RELATIONSHIP_TYPE Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given relationshiptype in the database.
     * 
     *  The relationshiptype OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the relationshiptype OID value is unknown, rather use save(RelationshipType)}.
     */
    public void update(RelationshipType relationshiptype) throws Exception {
    	
        if (relationshiptype.getName() == null) {
            throw new IllegalArgumentException("RelationshipType is not created yet, so the relationshiptype OID cannot be null.");
        }

        Object[] values = {
       		relationshiptype.getParent2Child(),
       		relationshiptype.getChild2Parent(),
       		relationshiptype.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating RelationshipType failed, no rows affected.");
                } 
                else {
                	
                	relationshiptype.setName(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_RELATIONSHIP_TYPE Skipped", "***", daoFactory.getMsgLevel());
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
     * Delete the given relationshiptype from the database. 
     * 
     *  After deleting, the DAO will set the ID of the given relationshiptype to null.
     */
    public void delete(RelationshipType relationshiptype) throws Exception {
    	
        Object[] values = { 
        	relationshiptype.getName() 
        };

        if (relationshiptype.getName() == null) {
            throw new IllegalArgumentException("RelationshipType is not created yet, so the relationshiptype OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting relationshiptype failed, no rows affected.");
                } 
                else {
                	
                	relationshiptype.setName(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_RELATIONSHIP_TYPE Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of RelationshipTypes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<RelationshipType> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "RTY_NAME";

    	if (sortField.equals("name")) {
        	sqlSortField = "RTY_NAME";       
        }
        if (sortField.equals("child2parent")) {
        	sqlSortField = "RTY_CHILD_TO_PARENT_DISPLAY";      
        }
        if (sortField.equals("parent2child")) {
        	sqlSortField = "RTY_PARENT_TO_CHILD_DISPLAY";         
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
        
        List<RelationshipType> dataList = new ArrayList<RelationshipType>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapRelationshipType(resultSet));
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
    private static RelationshipType mapRelationshipType(ResultSet resultSet) throws SQLException {

    	return new RelationshipType(
      		resultSet.getString("RTY_NAME"), 
       		resultSet.getString("RTY_CHILD_TO_PARENT_DISPLAY"), 
       		resultSet.getString("RTY_PARENT_TO_CHILD_DISPLAY")
        );
    }
}
