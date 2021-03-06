/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINNodeRelationshipDAO.java
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
* Description:  This class represents a SQL Database Access Object for the JOINNodeRelationship DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the JOINNodeRelationship DTO and a SQL database.
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

import daomodel.JOINNodeRelationship;

import daointerface.JOINNodeRelationshipDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class JOINNodeRelationshipDAOJDBC implements JOINNodeRelationshipDAO{
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL =
        "SELECT " +
        "ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE ON ANO_OID = REL_PARENT_FK ";
    
    private static final String SQL_LIST_ALL_BY_CHILD =
        "SELECT " +
        "ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION, " +
        "REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_NODE ON ANO_OID = REL_PARENT_FK " +
        "WHERE REL_CHILD_FK = ? ";        

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a JOINNodeRelationship Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public JOINNodeRelationshipDAOJDBC() {
    	
    }
    
    public JOINNodeRelationshipDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationship> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of ALL rows, otherwise null.
     */
    public List<JOINNodeRelationship> listAllByChild(long childFK) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_CHILD, childFK);
    }
    
    /*
     * Returns a list of all nodes from the database. 
     *  The list is never null and is empty when the database does not contain any nodes.
     */
    public List<JOINNodeRelationship> list(String sql, Object... values) throws Exception {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<JOINNodeRelationship> nodes = new ArrayList<JOINNodeRelationship>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                nodes.add(mapJOINNodeRelationship(resultSet));
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
     * Map the current row of the given ResultSet to a JOINNodeRelationship.
     */
    private static JOINNodeRelationship mapJOINNodeRelationship(ResultSet resultSet) throws SQLException {

    	return new JOINNodeRelationship(
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
       		resultSet.getLong("REL_PARENT_FK")
        );
    }
}
