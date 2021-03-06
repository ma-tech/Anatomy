/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINNodeRelationshipRelationshipProjectDAO.java
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
* Description:  This class represents a SQL Database Access Object for the 
*                JOINNodeRelationshipRelationshipProject DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the JOINNodeRelationshipRelationshipProject DTO and a SQL database.
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

import daomodel.JOINNodeRelationshipRelationshipProject;

import daointerface.JOINNodeRelationshipRelationshipProjectDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class JOINNodeRelationshipRelationshipProjectDAOJDBC implements JOINNodeRelationshipRelationshipProjectDAO{
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL =
        "SELECT " +
        "ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK, " +
        "RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE ON REL_PARENT_FK = ANO_OID " +
        "JOIN ANA_RELATIONSHIP_PROJECT ON REL_OID = RLP_RELATIONSHIP_FK ";
    
    private static final String SQL_LIST_ALL_BY_CHILD =
        "SELECT " +
        "ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK, " +
        "RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE ON REL_PARENT_FK = ANO_OID " +
        "JOIN ANA_RELATIONSHIP_PROJECT ON REL_OID = RLP_RELATIONSHIP_FK " +
        "WHERE REL_CHILD_FK = ? ";
            
    private static final String SQL_LIST_ALL_BY_CHILD_AND_PROJECT =
        "SELECT " +
        "ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK, " +
        "RLP_OID, RLP_RELATIONSHIP_FK, RLP_PROJECT_FK, RLP_SEQUENCE " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE ON REL_PARENT_FK = ANO_OID " +
        "JOIN ANA_RELATIONSHIP_PROJECT ON REL_OID = RLP_RELATIONSHIP_FK " +
        "WHERE REL_CHILD_FK = ? " +
        "AND RLP_PROJECT_FK = ? " +
        "ORDER BY ANO_PUBLIC_ID ";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a JOINNodeRelationshipRelationshipProject Data Access Object for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public JOINNodeRelationshipRelationshipProjectDAOJDBC() {
    	
    }

    public JOINNodeRelationshipRelationshipProjectDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipRelationshipProject> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipRelationshipProject> listAllByChildFK(long childFK) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_CHILD, childFK);
    }
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipRelationshipProject> listAllByChildAndProject(long childFK, String project) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_CHILD_AND_PROJECT, childFK, project);
    }
    
    /*
     * Returns a list of all nodes from the database. 
     *  The list is never null and is empty when the database does not contain any nodes.
     */
    public List<JOINNodeRelationshipRelationshipProject> list(String sql, Object... values) throws Exception {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<JOINNodeRelationshipRelationshipProject> nodes = new ArrayList<JOINNodeRelationshipRelationshipProject>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                nodes.add(mapJOINNodeRelationshipRelationshipProject(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return nodes;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a JOINNodeRelationshipRelationshipProject.
     */
    private static JOINNodeRelationshipRelationshipProject mapJOINNodeRelationshipRelationshipProject(ResultSet resultSet) throws SQLException {

    	return new JOINNodeRelationshipRelationshipProject(
      		resultSet.getLong("ANO_OID"), 
       		resultSet.getString("ANO_SPECIES_FK"), 
       		resultSet.getString("ANO_COMPONENT_NAME"), 
       		resultSet.getBoolean("ANO_IS_PRIMARY"),
       		resultSet.getBoolean("ANO_IS_GROUP"), 
       		resultSet.getString("ANO_PUBLIC_ID"), 
       		resultSet.getString("ANO_DESCRIPTION"),
      		resultSet.getLong("REL_OID"), 
       		resultSet.getString("REL_RELATIONSHIP_TYPE_FK"), 
       		resultSet.getLong("REL_CHILD_FK"), 
       		resultSet.getLong("REL_PARENT_FK"),
       		resultSet.getLong("RLP_OID"), 
       		resultSet.getLong("RLP_RELATIONSHIP_FK"),
       		resultSet.getString("RLP_PROJECT_FK"),
       		resultSet.getLong("RLP_SEQUENCE") 
        );
    }
}
