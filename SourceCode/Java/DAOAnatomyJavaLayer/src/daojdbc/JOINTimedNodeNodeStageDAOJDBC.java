/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINTimedNodeNodeStageDAO.java
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
*                JOINTimedNodeNodeStage DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the JOINTimedNodeNodeStage DTO and a SQL database.
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

import daomodel.JOINTimedNodeNodeStage;

import daointerface.JOINTimedNodeNodeStageDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class JOINTimedNodeNodeStageDAOJDBC implements JOINTimedNodeNodeStageDAO{
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_NODE ON ANO_OID = ATN_NODE_FK " +
        "JOIN ANA_STAGE ON STG_OID = ATN_STAGE_FK";
        
    private static final String SQL_LIST_ALL_BY_STAGE_NAME =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_NODE ON ANO_OID = ATN_NODE_FK " +
        "JOIN ANA_STAGE ON STG_OID = ATN_STAGE_FK " +
        "WHERE STG_NAME = ? " +
        "ORDER BY ATN_OID ";
            
    private static final String SQL_FIND_BY_EMAP =
    	"SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_NODE ON ANO_OID = ATN_NODE_FK " +
        "JOIN ANA_STAGE ON STG_OID = ATN_STAGE_FK " +
        "WHERE ATN_PUBLIC_ID = ? ";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a JOINTimedNodeNodeStage Data Access Object for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public JOINTimedNodeNodeStageDAOJDBC() {
    	
    }

    public JOINTimedNodeNodeStageDAOJDBC(DAOFactory daoFactory) {
       
    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the jointimednodenodestage from the database matching the EMAP ID, otherwise null.
     */
    public JOINTimedNodeNodeStage findByEmap(String emapId) throws Exception {
    	
        return find(SQL_FIND_BY_EMAP, emapId);
    }

    /*
     * Returns the jointimednodenodestage from the database matching the given 
     *  SQL query with the given values.
     */
    private JOINTimedNodeNodeStage find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JOINTimedNodeNodeStage jointimednodenodestage = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
            	jointimednodenodestage = mapJOINTimedNodeNodeStage(resultSet);
            }
        }
        catch (SQLException e) {
        	
            throw new DAOException(e);
        }
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return jointimednodenodestage;
    }

    /*
     * Returns a list of ALL jointimednodenodestages, otherwise null.
     */
    public List<JOINTimedNodeNodeStage> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of ALL jointimednodenodestages, otherwise null.
     */
    public List<JOINTimedNodeNodeStage> listAllByStageName( String stage ) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_STAGE_NAME, stage);
    }
    
    /*
     * Returns a list of all jointimednodenodestages from the database. 
     *  The list is never null and is empty when the database does not contain any jointimednodenodestages.
     */
    public List<JOINTimedNodeNodeStage> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<JOINTimedNodeNodeStage> jointimednodenodestages = new ArrayList<JOINTimedNodeNodeStage>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
            	jointimednodenodestages.add(mapJOINTimedNodeNodeStage(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return jointimednodenodestages;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static JOINTimedNodeNodeStage mapJOINTimedNodeNodeStage(ResultSet resultSet) throws SQLException {
    	
    	return new JOINTimedNodeNodeStage(
      		resultSet.getLong("ATN_OID"), 
       		resultSet.getLong("ATN_NODE_FK"), 
       		resultSet.getLong("ATN_STAGE_FK"), 
       		resultSet.getString("ATN_STAGE_MODIFIER_FK"),
       		resultSet.getString("ATN_PUBLIC_ID"),
       		resultSet.getString("ATN_DISPLAY_ID"),
       		resultSet.getLong("ANO_OID"), 
       		resultSet.getString("ANO_SPECIES_FK"), 
       		resultSet.getString("ANO_COMPONENT_NAME"), 
       		resultSet.getBoolean("ANO_IS_PRIMARY"),
       		resultSet.getBoolean("ANO_IS_GROUP"), 
       		resultSet.getString("ANO_PUBLIC_ID"), 
       		resultSet.getString("ANO_DESCRIPTION"),
      		resultSet.getLong("STG_OID"), 
       		resultSet.getString("STG_SPECIES_FK"), 
       		resultSet.getString("STG_NAME"), 
       		resultSet.getLong("STG_SEQUENCE"),
       		resultSet.getString("STG_DESCRIPTION"),
       		resultSet.getString("STG_SHORT_EXTRA_TEXT"),
       		resultSet.getString("STG_PUBLIC_ID")
        );
    }
}
