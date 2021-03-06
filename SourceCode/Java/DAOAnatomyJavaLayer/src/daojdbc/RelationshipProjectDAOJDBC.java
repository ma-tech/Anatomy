/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        RelationshipProjectDAO.java
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

import utility.Wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.RelationshipProject;

import daointerface.RelationshipProjectDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class RelationshipProjectDAOJDBC implements RelationshipProjectDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP_PROJECT " +
        "WHERE RLP_RELATIONSHIP_FK LIKE ? " +
        "AND RLP_PROJECT_FK LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_RELATIONSHIP_PROJECT " +
        "WHERE RLP_RELATIONSHIP_FK LIKE ? " +
        "AND RLP_PROJECT_FK LIKE ? ";

    private static final String SQL_MAX_OID =
        "SELECT MAX(RLP_OID) AS MAXIMUM " +
        "FROM ANA_RELATIONSHIP_PROJECT";

    private static final String SQL_FIND_BY_OID =
        "SELECT RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP_PROJECT " +
        "WHERE RLP_OID = ?";
    
    private static final String SQL_LIST_BY_PROJECT_FK =
        "SELECT RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP_PROJECT " +
        "WHERE RLP_PROJECT_FK = ?";
    
    private static final String SQL_LIST_BY_SEQUENCE =
        "SELECT RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP_PROJECT " +
        "WHERE RLP_SEQUENCE = ?";
    
    private static final String SQL_LIST_BY_RELATIONSHIP_FK =
        "SELECT RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP_PROJECT " +
        "WHERE RLP_RELATIONSHIP_FK = ?";
        
    private static final String SQL_LIST_ALL =
        "SELECT RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP_PROJECT ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_RELATIONSHIP_PROJECT " +
        "(RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_RELATIONSHIP_PROJECT SET " +
        "RLP_RELATIONSHIP_FK = ?, " +
        "RLP_PROJECT_FK = ?, " +
        "RLP_SEQUENCE = ? " + 
        "WHERE RLP_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_RELATIONSHIP_PROJECT " +
        "WHERE RLP_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT RLP_OID " +
        "FROM ANA_RELATIONSHIP_PROJECT " +
        "WHERE RLP_OID = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a RelationshipProject Data Access Object for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public RelationshipProjectDAOJDBC() {
    	
    }

    public RelationshipProjectDAOJDBC(DAOFactory daoFactory) {
    
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
     * Returns the relationshipproject from the database matching the given OID, otherwise null.
     */
    public RelationshipProject findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL relationshipprojects by Parent FK, otherwise null.
     */
    public List<RelationshipProject> listByProjectFK(long projectFK) throws Exception {
    	
        return list(SQL_LIST_BY_PROJECT_FK, projectFK);
    }
    
    /*
     * Returns a list of ALL relationshipprojects by Sequence Number, otherwise null.
     */
    public List<RelationshipProject> listBySequence(long sequence) throws Exception {
    	
        return list(SQL_LIST_BY_SEQUENCE, sequence);
    }
    
    /*
     * Returns a list of ALL relationshipprojects by Relationship FK, otherwise null.
     */
    public List<RelationshipProject> listByRelationshipFK(long relationshipFK) throws Exception {
    	
        return list(SQL_LIST_BY_RELATIONSHIP_FK, relationshipFK);
    }
    
    /*
     * Returns a list of ALL relationshipprojects, otherwise null.
     */
    public List<RelationshipProject> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given relationshipproject OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given relationshipproject in the database.
     * 
     *  If the RelationshipProject OID is null, 
     *   then it will invoke "create(RelationshipProject)", 
     *   else it will invoke "update(RelationshipProject)".
     */
    public void save(RelationshipProject relationshipproject) throws Exception {
     
    	if (relationshipproject.getOid() == null) {
    		
            create(relationshipproject);
        }
    	else {
    		
            update(relationshipproject);
        }
    }
    
    /*
     * Returns the relationshipproject from the database matching the given 
     *  SQL query with the given values.
     */
    private RelationshipProject find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RelationshipProject relationshipproject = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                relationshipproject = mapRelationshipProject(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationshipproject;
    }
    
    /*
     * Returns a list of all relationshipprojects from the database. 
     *  The list is never null and is empty when the database does not contain any relationshipprojects.
     */
    public List<RelationshipProject> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<RelationshipProject> relationshipprojects = new ArrayList<RelationshipProject>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                relationshipprojects.add(mapRelationshipProject(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationshipprojects;
    }
    
    /*
     * Create the given relationshipproject in the database.
     *  
     *  The relationshipproject OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the relationshipproject OID value is unknown, rather use save(RelationshipProject).
     *   After creating, the Data Access Object will set the obtained ID in the given relationshipproject.
     */
    public void create(RelationshipProject relationshipproject) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
       		relationshipproject.getOid(),
       		relationshipproject.getRelationshipFK(),
       		relationshipproject.getProjectFK(),
       		relationshipproject.getSequence()
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
                	
                    throw new DAOException("Creating RelationshipProject failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_RELATIONSHIP_PROJECT Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given relationshipproject in the database.
     * 
     *  The relationshipproject OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the relationshipproject OID value is unknown, rather use save(RelationshipProject).
     */
    public void update(RelationshipProject relationshipproject) throws Exception {
    	
        if (relationshipproject.getOid() == null) {
        	
            throw new IllegalArgumentException("RelationshipProject is not created yet, so the relationshipproject OID cannot be null.");
        }

        Object[] values = {
       		relationshipproject.getRelationshipFK(),
       		relationshipproject.getProjectFK(),
       		relationshipproject.getSequence(),
       		relationshipproject.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating RelationshipProject failed, no rows affected.");
                } 
                else {
                	
                	relationshipproject.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_RELATIONSHIP_PROJECT Skipped", "***", daoFactory.getMsgLevel());
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
     * Delete the given relationshipproject from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given relationshipproject to null.
     */
    public void delete(RelationshipProject relationshipproject) throws Exception {
    	
        Object[] values = { 
        	relationshipproject.getOid() 
        };

        if (relationshipproject.getOid() == null) {
        	
            throw new IllegalArgumentException("RelationshipProject is not created yet, so the relationshipproject OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting relationshipproject failed, no rows affected.");
                } 
                else {
                	
                	relationshipproject.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_RELATIONSHIP_PROJECT Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of RelationshipProjects for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<RelationshipProject> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "RLP_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "RLP_OID";       
        }
        if (sortField.equals("typeFK")) {
        	sqlSortField = "RLP_RELATIONSHIP_FK";      
        }
        if (sortField.equals("childFK")) {
        	sqlSortField = "RLP_PROJECT_FK";         
        }
        if (sortField.equals("parentFK")) {
        	sqlSortField = "RLP_SEQUENCE";         
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
        
        List<RelationshipProject> dataList = new ArrayList<RelationshipProject>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapRelationshipProject(resultSet));
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
    private static RelationshipProject mapRelationshipProject(ResultSet resultSet) throws SQLException {

    	return new RelationshipProject(
      		resultSet.getLong("RLP_OID"), 
       		resultSet.getLong("RLP_RELATIONSHIP_FK"), 
       		resultSet.getString("RLP_PROJECT_FK"), 
       		resultSet.getLong("RLP_SEQUENCE")
        );
    }
}
