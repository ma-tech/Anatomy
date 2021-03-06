/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        RelationshipDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Relationship DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Relationship DTO and a SQL database.
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

import daomodel.Relationship;

import daointerface.RelationshipDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class RelationshipDAOJDBC implements RelationshipDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
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
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_OID = ? ";
    
    private static final String SQL_LIST_BY_CHILD_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_CHILD_FK = ? ";
    
    private static final String SQL_LIST_BY_PARENT_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_PARENT_FK = ? ";
    
    private static final String SQL_LIST_BY_PARENT_FK_AND_CHILD_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_PARENT_FK = ? " +
        "AND REL_CHILD_FK = ? ";
        
    private static final String SQL_LIST_BY_RELATIONSHIP_TYPE_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_RELATIONSHIP_TYPE_FK = ? ";
        
    private static final String SQL_LIST_ALL =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_RELATIONSHIP " +
        "(REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_RELATIONSHIP SET " +
        "REL_RELATIONSHIP_TYPE_FK = ?, " +
        "REL_CHILD_FK = ?, " +
        "REL_PARENT_FK = ? " + 
        "WHERE REL_OID = ? ";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_RELATIONSHIP " +
        "WHERE REL_OID = ? ";

    private static final String SQL_EXIST_OID =
        "SELECT REL_OID " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_OID = ? ";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Relationship Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public RelationshipDAOJDBC() {
    	
    }

    public RelationshipDAOJDBC(DAOFactory daoFactory) {
    	
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
     * Returns the Relationship from the database matching the given OID, otherwise null.
     */
    public Relationship findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL relationships by Parent FK, otherwise null.
     */
    public List<Relationship> listByParentFK(long parentFK) throws Exception {
    	
        return list(SQL_LIST_BY_PARENT_FK, parentFK);
    }
    
    /*
     * Returns a list of ALL relationships by Child FK, otherwise null.
     */
    public List<Relationship> listByChildFK(long childFK) throws Exception {
    	
        return list(SQL_LIST_BY_CHILD_FK, childFK);
    }
    
    /*
     * Returns a list of ALL relationships by Parent FK AND Child FK, otherwise null.
     */
    public List<Relationship> listByParentFKAndChildFK(long parentFK, long childFK) throws Exception {
    	
        return list(SQL_LIST_BY_PARENT_FK_AND_CHILD_FK, parentFK, childFK);
    }
    
    /*
     * Returns a list of ALL relationships by Relationship Type FK, otherwise null.
     */
    public List<Relationship> listByRelationshipTypeFK(String relationshipTypeFK) throws Exception {
    	
        return list(SQL_LIST_BY_RELATIONSHIP_TYPE_FK, relationshipTypeFK);
    }
    
    /*
     * Returns a list of ALL relationships, otherwise null.
     */
    public List<Relationship> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given relationship OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given relationship in the database.
     * 
     *  If the Relationship OID is null, 
     *   then it will invoke "create(Relationship)", 
     *   else it will invoke "update(Relationship)".
     */
    public void save(Relationship relationship) throws Exception {
     
    	if (relationship.getOid() == null) {
    		
            create(relationship);
        }
    	else {
    		
            update(relationship);
        }
    }
    
    /*
     * Returns the relationship from the database matching the given 
     *  SQL query with the given values.
     */
    private Relationship find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Relationship relationship = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                relationship = mapRelationship(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationship;
    }

    /*
     * Returns a list of all relationships from the database. 
     *  The list is never null and is empty when the database does not contain any relationships.
     */
    public List<Relationship> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Relationship> relationships = new ArrayList<Relationship>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                relationships.add(mapRelationship(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationships;
    }
    
    /*
     * Create the given relationship in the database. 
     *  The relationship OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the relationship OID value is unknown, rather use save(Relationship).
     *   After creating, the Data Access Object will set the obtained ID in the given relationship.
     */
    public void create(Relationship relationship) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
       		relationship.getOid(),
       		relationship.getTypeFK(),
       		relationship.getChildFK(),
       		relationship.getParentFK()
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
                	
                    throw new DAOException("Creating Relationship failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_RELATIONSHIP Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given relationship in the database.
     * 
     *  The relationship OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the relationship OID value is unknown, rather use save(Relationship)}.
     */
    public void update(Relationship relationship) throws Exception {
    	
        if (relationship.getOid() == null) {
            throw new IllegalArgumentException("Relationship is not created yet, so the relationship OID cannot be null.");
        }

        Object[] values = {
       		relationship.getTypeFK(),
       		relationship.getChildFK(),
       		relationship.getParentFK(),
       		relationship.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating Relationship failed, no rows affected.");
                } 
                else {
                	
                	relationship.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_RELATIONSHIP Skipped", "***", daoFactory.getMsgLevel());
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
     * Delete the given relationship from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given relationship to null.
     */
    public void delete(Relationship relationship) throws Exception {
    	
        Object[] values = { 
        	relationship.getOid() 
        };

        if (relationship.getOid() == null) {
            throw new IllegalArgumentException("Relationship is not created yet, so the relationship OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting relationship failed, no rows affected.");
                } 
                else {
                	
                	relationship.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_RELATIONSHIP Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of Relationships for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Relationship> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
        
        List<Relationship> dataList = new ArrayList<Relationship>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapRelationship(resultSet));
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
    private static Relationship mapRelationship(ResultSet resultSet) throws SQLException {

    	return new Relationship(
      		resultSet.getLong("REL_OID"), 
       		resultSet.getString("REL_RELATIONSHIP_TYPE_FK"), 
       		resultSet.getLong("REL_CHILD_FK"), 
       		resultSet.getLong("REL_PARENT_FK")
        );
    }
}
