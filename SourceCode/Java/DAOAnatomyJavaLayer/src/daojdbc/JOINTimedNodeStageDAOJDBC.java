/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        JOINTimedNodeStageDAO.java
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
*                JOINTimedNodeStage DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the JOINTimedNodeStage DTO and a SQL database.
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

import daomodel.JOINTimedNodeStage;

import daointerface.JOINTimedNodeStageDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class JOINTimedNodeStageDAOJDBC implements JOINTimedNodeStageDAO{
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID ";
            
    private static final String SQL_LIST_ALL_BY_NODE_FK_ORDER_BY_STAGE_NAME =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? " +
        "ORDER BY STG_NAME";
        
    private static final String SQL_LIST_ALL_BY_NODE_FK_ORDER_BY_STAGE_SEQUENCE =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? " +
        "ORDER BY STG_SEQUENCE";
            
    private static final String SQL_LIST_ALL_BY_NODE_FK_AND_STAGE_NAME =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? " +
        "AND STG_NAME = ?";
            
    private static final String SQL_LIST_ALL_BY_NODE_FK_AND_STAGE_SEQUENCE =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? " +
        "AND STG_SEQUENCE = ?";
                
    private static final String SQL_COUNT_ALL_BY_NODE_FK =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? ";
            
    private static final String SQL_MAX_SEQUENCE_BY_NODE_FK =
        "SELECT MAX(STG_SEQUENCE) AS VALUE " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? ";
 
    private static final String SQL_MIN_SEQUENCE_BY_NODE_FK =
        "SELECT MIN(STG_SEQUENCE) AS VALUE " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? ";
     
    private static final String SQL_LIST_ALL_BY_NODE_FK_AND_SEQUENCE =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, ATN_DISPLAY_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK IN (" +
        "SELECT REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "JOIN ANA_TIMED_NODE ON ATN_NODE_FK = REL_PARENT_FK " +
        "JOIN ANA_STAGE AND STG_OID = ATN_STAGE_FK " +
        "WHERE ATN_NODE_FK = ? " +
        "AND STG_SEQUENCE = ? " +
        ") " +
        "AND STG_SEQUENCE = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a JOINTimedNodeStage Data Access Object for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public JOINTimedNodeStageDAOJDBC() {
    	
    }

    public JOINTimedNodeStageDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns a list of ALL jointimednodestages, otherwise null.
     */
    public List<JOINTimedNodeStage> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of ALL jointimednodestages by Node Fk, Ordered by Stage Name, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkOrderByStageName(long nodeFk) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_ORDER_BY_STAGE_NAME, nodeFk);
    }
    
    /*
     * Returns a list of ALL jointimednodestages by Node Fk, Ordered by Stage Sequence, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkOrderByStageSequence(long nodeFk) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_ORDER_BY_STAGE_SEQUENCE, nodeFk);
    }
    
    /*
     * Returns a list of ALL jointimednodestages by Node Fk and Stage Name, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkAndStageName(long nodeFk, String stageName) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_AND_STAGE_NAME, nodeFk, stageName);
    }
    
    /*
     * Returns a list of ALL jointimednodestages by Node Fk and Stage Name, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkAndStageSequence(long nodeFk, long stageSequence) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_AND_STAGE_SEQUENCE, nodeFk, stageSequence);
    }
    
    /*
     * Returns a list of ALL jointimednodestages by node Fk and Sequence, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkAndSequence(long nodeFk, String sequence) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_AND_SEQUENCE, nodeFk, sequence);
    }
    
    /*
     * Returns a count of ALL jointimednodestages by node FK, otherwise null.
     */
    public long countAllByNodeFk(long nodeFk) throws Exception {
    	
        return count(SQL_COUNT_ALL_BY_NODE_FK, nodeFk);
    }
    
    /*
     * Returns a count of ALL jointimednodestages by node FK, otherwise null.
     */
    public long maxSequenceByNodeFk(long nodeFk) throws Exception {
    	
        return maxMin(SQL_MAX_SEQUENCE_BY_NODE_FK, nodeFk);
    }
    
    /*
     * Returns a count of ALL jointimednodestages by node FK, otherwise null.
     */
    public long minSequenceByNodeFk(long nodeFk) throws Exception {
    	
        return maxMin(SQL_MIN_SEQUENCE_BY_NODE_FK, nodeFk);
    }
    
    /*
     * Returns a list of all jointimednodestages from the database. 
     *  The list is never null and is empty when the database does not contain any jointimednodestages.
     */
    public List<JOINTimedNodeStage> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<JOINTimedNodeStage> jointimednodestages = new ArrayList<JOINTimedNodeStage>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                jointimednodestages.add(mapJOINTimedNodeStage(resultSet));
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
    
    /*
     * Returns total amount of rows in table.
     */
    public long count(String sql, long key) throws Exception {

        Object[] values = {
        		key
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

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
    public long maxMin(String sql, long key) throws Exception {

        Object[] values = {
        		key
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

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

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static JOINTimedNodeStage mapJOINTimedNodeStage(ResultSet resultSet) throws SQLException {
    	
    	return new JOINTimedNodeStage(
      		resultSet.getLong("ATN_OID"), 
       		resultSet.getLong("ATN_NODE_FK"), 
       		resultSet.getLong("ATN_STAGE_FK"), 
       		resultSet.getString("ATN_STAGE_MODIFIER_FK"),
       		resultSet.getString("ATN_PUBLIC_ID"),
       		resultSet.getString("ATN_DISPLAY_ID"),
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
