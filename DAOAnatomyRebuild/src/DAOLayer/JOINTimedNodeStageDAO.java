package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import DAOModel.JOINTimedNodeStage;

/*
 * This class represents a SQL Database Access Object for the JOINTimedNodeStage DTO.
 * 
 * This DAO should be used as a central point for the mapping between 
 *  the JOINTimedNodeStage DTO and a SQL database.
 *
 * http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 * 
 */
public final class JOINTimedNodeStageDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID ";
            
    private static final String SQL_LIST_ALL_BY_NODE_FK_ORDER_BY_STAGE_NAME =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? " +
        "ORDER BY STG_NAME";
        
    private static final String SQL_LIST_ALL_BY_NODE_FK_ORDER_BY_STAGE_SEQUENCE =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? " +
        "ORDER BY STG_SEQUENCE";
            
    private static final String SQL_LIST_ALL_BY_NODE_FK_AND_STAGE_NAME =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, " +
        "STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? " +
        "AND STG_NAME = ?";
            
    private static final String SQL_COUNT_ALL_BY_NODE_FK =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_TIMED_NODE " +
        "JOIN ANA_STAGE ON ATN_STAGE_FK = STG_OID " +
        "WHERE ATN_NODE_FK = ? " +
        "ORDER BY STG_NAME";
            
    private static final String SQL_LIST_ALL_BY_NODE_FK_AND_SEQUENCE =
        "SELECT " +
        "ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID, " +
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
     * Construct a JOINTimedNodeStage DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    JOINTimedNodeStageDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<JOINTimedNodeStage> listAll() throws DAOException {
    	
        return list(SQL_LIST_ALL);
        
    }
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkOrderByStageName(String nodeFk) throws DAOException {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_ORDER_BY_STAGE_NAME, nodeFk);
        
    }
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkOrderByStageSequence(String nodeFk) throws DAOException {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_ORDER_BY_STAGE_SEQUENCE, nodeFk);
        
    }
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkAndStageName(String nodeFk, String stageName) throws DAOException {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_AND_STAGE_NAME, nodeFk, stageName);
        
    }
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public int countAllByNodeFk(String nodeFk) throws DAOException {
    	
        return count(SQL_COUNT_ALL_BY_NODE_FK, nodeFk);
        
    }
    
    /*
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<JOINTimedNodeStage> listAllByNodeFkAndSequence(String nodeFk, String sequence) throws DAOException {
    	
        return list(SQL_LIST_ALL_BY_NODE_FK_AND_SEQUENCE, nodeFk, sequence);
        
    }
    
    /*
     * Returns a list of all timednodes from the database. 
     *  The list is never null and is empty when the database does not contain any timednodes.
     */
    public List<JOINTimedNodeStage> list(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<JOINTimedNodeStage> timednodes = new ArrayList<JOINTimedNodeStage>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                timednodes.add(mapJOINTimedNodeStage(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return timednodes;
        
    }
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String sql, String key) throws DAOException {

        Object[] values = {
        		key
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("VALUE");
            }
            
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
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
