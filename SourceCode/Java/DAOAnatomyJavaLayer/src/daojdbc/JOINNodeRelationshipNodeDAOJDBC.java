/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINNodeRelationshipNodeDAO.java
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
* Description:  This class represents a SQL Database Access Object for the JOINNodeRelationshipNode DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the JOINNodeRelationshipNode DTO and a SQL database.
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

import daomodel.JOINNodeRelationshipNode;

import daointerface.JOINNodeRelationshipNodeDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class JOINNodeRelationshipNodeDAOJDBC implements JOINNodeRelationshipNodeDAO{
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL =
        "SELECT " +
        "a.ANO_OID, a.ANO_SPECIES_FK, a.ANO_COMPONENT_NAME, a.ANO_IS_PRIMARY, a.ANO_IS_GROUP, a.ANO_PUBLIC_ID, a.ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK, " +
        "b.ANO_OID, b.ANO_SPECIES_FK, b.ANO_COMPONENT_NAME, b.ANO_IS_PRIMARY, b.ANO_IS_GROUP, b.ANO_PUBLIC_ID, b.ANO_DESCRIPTION " +
        "FROM ANA_NODE a " +
        "JOIN ANA_RELATIONSHIP ON REL_CHILD_FK = a.ANO_OID " +
        "JOIN ANA_NODE b ON REL_PARENT_FK = b.ANO_OID ";
    
    private static final String SQL_LIST_ALL_PART_OFS =
        "SELECT " +
        "a.ANO_OID, a.ANO_SPECIES_FK, a.ANO_COMPONENT_NAME, a.ANO_IS_PRIMARY, a.ANO_IS_GROUP, a.ANO_PUBLIC_ID, a.ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK, " +
        "b.ANO_OID, b.ANO_SPECIES_FK, b.ANO_COMPONENT_NAME, b.ANO_IS_PRIMARY, b.ANO_IS_GROUP, b.ANO_PUBLIC_ID, b.ANO_DESCRIPTION " +
        "FROM ANA_NODE a " +
        "JOIN ANA_RELATIONSHIP ON REL_CHILD_FK = a.ANO_OID " +
        "JOIN ANA_NODE b ON REL_PARENT_FK = b.ANO_OID " +
        "WHERE REL_RELATIONSHIP_TYPE_FK = 'part-of' ";
        
    private static final String SQL_LIST_ALL_ISAS_BY_CHILD =
    	"SELECT " +
    	"a.ANO_OID, a.ANO_SPECIES_FK, a.ANO_COMPONENT_NAME, a.ANO_IS_PRIMARY, a.ANO_IS_GROUP, a.ANO_PUBLIC_ID, a.ANO_DESCRIPTION, " +
    	"REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK, " +
    	"b.ANO_OID, b.ANO_SPECIES_FK, b.ANO_COMPONENT_NAME, b.ANO_IS_PRIMARY, b.ANO_IS_GROUP, b.ANO_PUBLIC_ID, b.ANO_DESCRIPTION " +
    	"FROM ANA_NODE a " +
    	"JOIN ANA_RELATIONSHIP ON REL_CHILD_FK = a.ANO_OID " +
    	"JOIN ANA_NODE b ON REL_PARENT_FK = b.ANO_OID " +
    	"WHERE REL_RELATIONSHIP_TYPE_FK = 'is-a' " +
    	"AND a.ANO_OID = ? " +
    	"ORDER BY b.ANO_PUBLIC_ID";
    
    private static final String SQL_LIST_ALL_BY_PARENT_ID =
        "SELECT " +
        "a.ANO_OID, a.ANO_SPECIES_FK, a.ANO_COMPONENT_NAME, a.ANO_IS_PRIMARY, a.ANO_IS_GROUP, a.ANO_PUBLIC_ID, a.ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK, " +
        "b.ANO_OID, b.ANO_SPECIES_FK, b.ANO_COMPONENT_NAME, b.ANO_IS_PRIMARY, b.ANO_IS_GROUP, b.ANO_PUBLIC_ID, b.ANO_DESCRIPTION " +
        "FROM ANA_NODE a " +
        "JOIN ANA_RELATIONSHIP ON REL_CHILD_FK = a.ANO_OID " +
        "JOIN ANA_NODE b ON REL_PARENT_FK = b.ANO_OID " +
        "WHERE b.ANO_PUBLIC_ID = ? ";        

    private static final String SQL_LIST_ALL_BY_CHILD_ID_AND_PARENT_ID =
        "SELECT " +
        "a.ANO_OID, a.ANO_SPECIES_FK, a.ANO_COMPONENT_NAME, a.ANO_IS_PRIMARY, a.ANO_IS_GROUP, a.ANO_PUBLIC_ID, a.ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK, " +
        "b.ANO_OID, b.ANO_SPECIES_FK, b.ANO_COMPONENT_NAME, b.ANO_IS_PRIMARY, b.ANO_IS_GROUP, b.ANO_PUBLIC_ID, b.ANO_DESCRIPTION " +
        "FROM ANA_NODE a " +
        "JOIN ANA_RELATIONSHIP ON REL_CHILD_FK = a.ANO_OID " +
        "JOIN ANA_NODE b ON REL_PARENT_FK = b.ANO_OID " +
        "WHERE a.ANO_PUBLIC_ID = ? " +        
        "AND b.ANO_PUBLIC_ID = ? ";        

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a JOINNodeRelationshipNode Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public JOINNodeRelationshipNodeDAOJDBC() {
    	
    }
    
    public JOINNodeRelationshipNodeDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }

    /*
     * Returns a list of ALL PART_OF rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllPartOfs() throws Exception {
    	
        return list(SQL_LIST_ALL_PART_OFS);
    }

    /*
     * Returns a list of ALL PART_OF rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllIsAsByChild(String childId) throws Exception {
    	
        return list(SQL_LIST_ALL_ISAS_BY_CHILD, childId);
    }

    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllByParentId(String parentId) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_PARENT_ID, parentId);
    }
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationshipNode> listAllByChildIdAndParentId(String childId, String parentId) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_CHILD_ID_AND_PARENT_ID, childId, parentId);
    }
    
    /*
     * Returns a list of all noderelationshipnodes from the database. 
     *  The list is never null and is empty when the database does not contain any noderelationshipnodes.
     */
    public List<JOINNodeRelationshipNode> list(String sql, Object... values) throws Exception {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<JOINNodeRelationshipNode> noderelationshipnodes = new ArrayList<JOINNodeRelationshipNode>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                noderelationshipnodes.add(mapJOINNodeRelationshipNode(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return noderelationshipnodes;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a JOINNodeRelationshipNode.
     */
    private static JOINNodeRelationshipNode mapJOINNodeRelationshipNode(ResultSet resultSet) throws SQLException {

    	return new JOINNodeRelationshipNode(
      		resultSet.getLong("a.ANO_OID"), 
       		resultSet.getString("a.ANO_SPECIES_FK"), 
       		resultSet.getString("a.ANO_COMPONENT_NAME"), 
       		resultSet.getBoolean("a.ANO_IS_PRIMARY"),
       		resultSet.getBoolean("a.ANO_IS_GROUP"), 
       		resultSet.getString("a.ANO_PUBLIC_ID"), 
       		resultSet.getString("a.ANO_DESCRIPTION"),
      		resultSet.getLong("REL_OID"), 
       		resultSet.getString("REL_RELATIONSHIP_TYPE_FK"), 
       		resultSet.getLong("REL_CHILD_FK"), 
       		resultSet.getLong("REL_PARENT_FK"),
      		resultSet.getLong("b.ANO_OID"), 
       		resultSet.getString("b.ANO_SPECIES_FK"), 
       		resultSet.getString("b.ANO_COMPONENT_NAME"), 
       		resultSet.getBoolean("b.ANO_IS_PRIMARY"),
       		resultSet.getBoolean("b.ANO_IS_GROUP"), 
       		resultSet.getString("b.ANO_PUBLIC_ID"), 
       		resultSet.getString("b.ANO_DESCRIPTION")
        );
    }
}
