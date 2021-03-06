/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO.java
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
*                JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage DTO and a SQL database.
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

import daomodel.JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage;

import daointerface.JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAOJDBC implements
                   JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO{
	
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL =
        "SELECT " +
        "a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, a.ATN_DISPLAY_ID, " +
        "b.ANO_OID, b.ANO_SPECIES_FK, b.ANO_COMPONENT_NAME, b.ANO_IS_PRIMARY, b.ANO_IS_GROUP, b.ANO_PUBLIC_ID, b.ANO_DESCRIPTION, " +
        "c.STG_OID, c.STG_SPECIES_FK, c.STG_NAME, c.STG_SEQUENCE, c.STG_DESCRIPTION, c.STG_SHORT_EXTRA_TEXT, c.STG_PUBLIC_ID, " +
        "d.REL_OID, d.REL_RELATIONSHIP_TYPE_FK, d.REL_CHILD_FK, d.REL_PARENT_FK, " +
        "e.ATN_OID, e.ATN_NODE_FK, e.ATN_STAGE_FK, e.ATN_STAGE_MODIFIER_FK, e.ATN_PUBLIC_ID, e.ATN_DISPLAY_ID, " +
        "f.ANO_OID, f.ANO_SPECIES_FK, f.ANO_COMPONENT_NAME, f.ANO_IS_PRIMARY, f.ANO_IS_GROUP, f.ANO_PUBLIC_ID, f.ANO_DESCRIPTION, " +
        "g.STG_OID, g.STG_SPECIES_FK, g.STG_NAME, g.STG_SEQUENCE, g.STG_DESCRIPTION, g.STG_SHORT_EXTRA_TEXT, g.STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_RELATIONSHIP d ON d.REL_CHILD_FK = b.ANO_OID " +
        "JOIN ANA_NODE f ON d.REL_PARENT_FK = f.ANO_OID " +
        "JOIN ANA_TIMED_NODE e ON f.ANO_OID = e.ATN_NODE_FK " +
        "JOIN ANA_STAGE g ON g.STG_OID = e.ATN_STAGE_FK ";
            
    private static final String SQL_LIST_ALL_BY_STAGE_NAME =
        "SELECT " +
        "a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, a.ATN_DISPLAY_ID, " +
        "b.ANO_OID, b.ANO_SPECIES_FK, b.ANO_COMPONENT_NAME, b.ANO_IS_PRIMARY, b.ANO_IS_GROUP, b.ANO_PUBLIC_ID, b.ANO_DESCRIPTION, " +
        "c.STG_OID, c.STG_SPECIES_FK, c.STG_NAME, c.STG_SEQUENCE, c.STG_DESCRIPTION, c.STG_SHORT_EXTRA_TEXT, c.STG_PUBLIC_ID, " +
        "d.REL_OID, d.REL_RELATIONSHIP_TYPE_FK, d.REL_CHILD_FK, d.REL_PARENT_FK, " +
        "e.ATN_OID, e.ATN_NODE_FK, e.ATN_STAGE_FK, e.ATN_STAGE_MODIFIER_FK, e.ATN_PUBLIC_ID, e.ATN_DISPLAY_ID, " +
        "f.ANO_OID, f.ANO_SPECIES_FK, f.ANO_COMPONENT_NAME, f.ANO_IS_PRIMARY, f.ANO_IS_GROUP, f.ANO_PUBLIC_ID, f.ANO_DESCRIPTION, " +
        "g.STG_OID, g.STG_SPECIES_FK, g.STG_NAME, g.STG_SEQUENCE, g.STG_DESCRIPTION, g.STG_SHORT_EXTRA_TEXT, g.STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_RELATIONSHIP d ON d.REL_CHILD_FK = b.ANO_OID " +
        "JOIN ANA_NODE f ON d.REL_PARENT_FK = f.ANO_OID " +
        "JOIN ANA_TIMED_NODE e ON f.ANO_OID = e.ATN_NODE_FK " +
        "JOIN ANA_STAGE g ON g.STG_OID = e.ATN_STAGE_FK " +
        "WHERE c.STG_NAME = ? " +
        "AND g.STG_NAME = ? " +
        "AND a.ATN_PUBLIC_ID = ? ";
        
    private static final String SQL_LIST_ALL_BY_STAGE_NAME_BY_PARENT =
        "SELECT " +
        "a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, a.ATN_DISPLAY_ID, " +
        "b.ANO_OID, b.ANO_SPECIES_FK, b.ANO_COMPONENT_NAME, b.ANO_IS_PRIMARY, b.ANO_IS_GROUP, b.ANO_PUBLIC_ID, b.ANO_DESCRIPTION, " +
        "c.STG_OID, c.STG_SPECIES_FK, c.STG_NAME, c.STG_SEQUENCE, c.STG_DESCRIPTION, c.STG_SHORT_EXTRA_TEXT, c.STG_PUBLIC_ID, " +
        "d.REL_OID, d.REL_RELATIONSHIP_TYPE_FK, d.REL_CHILD_FK, d.REL_PARENT_FK, " +
        "e.ATN_OID, e.ATN_NODE_FK, e.ATN_STAGE_FK, e.ATN_STAGE_MODIFIER_FK, e.ATN_PUBLIC_ID, e.ATN_DISPLAY_ID, " +
        "f.ANO_OID, f.ANO_SPECIES_FK, f.ANO_COMPONENT_NAME, f.ANO_IS_PRIMARY, f.ANO_IS_GROUP, f.ANO_PUBLIC_ID, f.ANO_DESCRIPTION, " +
        "g.STG_OID, g.STG_SPECIES_FK, g.STG_NAME, g.STG_SEQUENCE, g.STG_DESCRIPTION, g.STG_SHORT_EXTRA_TEXT, g.STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_RELATIONSHIP d ON d.REL_CHILD_FK = b.ANO_OID " +
        "JOIN ANA_NODE f ON d.REL_PARENT_FK = f.ANO_OID " +
        "JOIN ANA_TIMED_NODE e ON f.ANO_OID = e.ATN_NODE_FK " +
        "JOIN ANA_STAGE g ON g.STG_OID = e.ATN_STAGE_FK " +
        "WHERE c.STG_NAME = ? " +
        "AND g.STG_NAME = ? " +
        "AND a.ATN_PUBLIC_ID = ? " +
        "ORDER BY e.ATN_PUBLIC_ID";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage Data Access Object for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAOJDBC() {
    	
    }

    public JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns a list of ALL JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage, otherwise null.
     */
    public List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> listAll() 
    		throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of ALL JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage by Node Fk, Ordered by Stage Name, otherwise null.
     */
    public List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> listAllByStageName(String stageName1, String stageName2, String parentTimedNode)
    		throws Exception {
    	
        return list(SQL_LIST_ALL_BY_STAGE_NAME, stageName1, stageName2);
    }
    
    /*
     * Returns a list of ALL JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage by Node Fk, Ordered by Stage Sequence, otherwise null.
     */
    public List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> listAllStageNameAndParent(String stageName1, String stageName2, String parentTimedNode) 
    		throws Exception {
    	
        return list(SQL_LIST_ALL_BY_STAGE_NAME_BY_PARENT, stageName1, stageName2, parentTimedNode);
    }
    
    /*
     * Returns a list of all JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage from the database. 
     *  The list is never null and is empty when the database does not contain any JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage.
     */
    public List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> list(String sql, Object... values) 
    		throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> jointimednodestages = new ArrayList<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                jointimednodestages.add(mapJOINTimedNodeNodeStageRelationshipNodeTimedNodeStage(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return jointimednodestages;
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage mapJOINTimedNodeNodeStageRelationshipNodeTimedNodeStage(ResultSet resultSet) 
    		throws SQLException {
    	
    	return new JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage(
    			resultSet.getLong("a.ATN_OID"), 
    			resultSet.getLong("a.ATN_NODE_FK"), 
    			resultSet.getLong("a.ATN_STAGE_FK"), 
    			resultSet.getString("a.ATN_STAGE_MODIFIER_FK"),
    			resultSet.getString("a.ATN_PUBLIC_ID"),
    			resultSet.getString("a.ATN_DISPLAY_ID"),
    			resultSet.getLong("b.ANO_OID"), 
    			resultSet.getString("b.ANO_SPECIES_FK"), 
    			resultSet.getString("b.ANO_COMPONENT_NAME"), 
    			resultSet.getBoolean("b.ANO_IS_PRIMARY"),
    			resultSet.getBoolean("b.ANO_IS_GROUP"),
    			resultSet.getString("b.ANO_PUBLIC_ID"), 
    			resultSet.getString("b.ANO_DESCRIPTION"), 
    			resultSet.getLong("c.STG_OID"), 
    			resultSet.getString("c.STG_SPECIES_FK"), 
    			resultSet.getString("c.STG_NAME"), 
    			resultSet.getLong("c.STG_SEQUENCE"),
    			resultSet.getString("c.STG_DESCRIPTION"),
    			resultSet.getString("c.STG_SHORT_EXTRA_TEXT"), 
    			resultSet.getString("c.STG_PUBLIC_ID"), 
    			resultSet.getLong("d.REL_OID"), 
    			resultSet.getString("d.REL_RELATIONSHIP_TYPE_FK"), 
    			resultSet.getLong("d.REL_CHILD_FK"), 
    			resultSet.getLong("d.REL_PARENT_FK"),
    			resultSet.getLong("e.ATN_OID"), 
    			resultSet.getLong("e.ATN_NODE_FK"), 
    			resultSet.getLong("e.ATN_STAGE_FK"), 
    			resultSet.getString("e.ATN_STAGE_MODIFIER_FK"),
    			resultSet.getString("e.ATN_PUBLIC_ID"),
    			resultSet.getString("e.ATN_DISPLAY_ID"),
    			resultSet.getLong("f.ANO_OID"), 
    			resultSet.getString("f.ANO_SPECIES_FK"), 
    			resultSet.getString("f.ANO_COMPONENT_NAME"), 
    			resultSet.getBoolean("f.ANO_IS_PRIMARY"),
    			resultSet.getBoolean("f.ANO_IS_GROUP"),
    			resultSet.getString("f.ANO_PUBLIC_ID"), 
    			resultSet.getString("f.ANO_DESCRIPTION"), 
    			resultSet.getLong("g.STG_OID"), 
    			resultSet.getString("g.STG_SPECIES_FK"), 
    			resultSet.getString("g.STG_NAME"), 
    			resultSet.getLong("g.STG_SEQUENCE"),
    			resultSet.getString("g.STG_DESCRIPTION"),
    			resultSet.getString("g.STG_SHORT_EXTRA_TEXT"), 
    			resultSet.getString("g.STG_PUBLIC_ID")
        );
    }
}
