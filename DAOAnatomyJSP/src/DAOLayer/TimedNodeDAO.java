package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.TimedNode;

/**
 * This class represents a SQL Database Access Object for the TimedNode DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the TimedNode DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class TimedNodeDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_FIND_BY_EMAPA_AND_STAGE =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, " +
        "b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE, " +
        "d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE b.ANO_PUBLIC_ID = ? " +
        "AND c.STG_SEQUENCE = ? ";
        
    private static final String SQL_EXIST_EMAPA_AND_STAGE =
        "SELECT ATN_OID " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "WHERE b.ANO_PUBLIC_ID = ? " +
        "AND c.STG_SEQUENCE = ? ";

    private static final String SQL_FIND_BY_EMAP =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, " +
        "b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE, " +
        "d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE a.ATN_PUBLIC_ID = ? ";
            
    private static final String SQL_EXIST_EMAP =
        "SELECT ATN_OID " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE " +
        "JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE " +
        "WHERE a.ATN_PUBLIC_ID = ? ";

        
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a TimedNode DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    TimedNodeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the timednode from the database matching the given EMAPA Id and Stage Sequence, 
     *  otherwise null.
     */
    public TimedNode findByEmapaAndStage(String emapaId, Long stageSeq) throws DAOException {
        return find(SQL_FIND_BY_EMAPA_AND_STAGE, emapaId, stageSeq);
    }

    /**
     * Returns the timednode from the database matching the EMAP ID, otherwise null.
     */
    public TimedNode findByEmap(String emapId) throws DAOException {
        return find(SQL_FIND_BY_EMAP, emapId);
    }

    /**
     * Returns the timednode from the database matching the given 
     *  SQL query with the given values.
     */
    private TimedNode find(String sql, Object... values) throws DAOException {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TimedNode timednode = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);

            //System.out.println("PS = " + preparedStatement.toString());
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                timednode = mapTimedNode(resultSet);
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return timednode;
    }

    /**
     * Returns true if the given timednode EMAPA ID and Stage Seq exists in the database.
     */
    public boolean existEmapaIdAndStageSeq(String emapaId, Long stageSeq) throws DAOException {
        return exist(SQL_EXIST_EMAPA_AND_STAGE, emapaId, stageSeq);
    }

    /**
     * Returns true if the given timednode EMAP ID exists in the database.
     */
    public boolean existEmapId(String emapId) throws DAOException {
        return exist(SQL_EXIST_EMAP, emapId);
    }

    /**
     * Returns true if the given SQL query with the given values returns at least one row.
     */
    private boolean exist(String sql, Object... values) throws DAOException {

    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            
            //System.out.println("PS = " + preparedStatement.toString());
            
            resultSet = preparedStatement.executeQuery();

            exist = resultSet.next();
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return exist;
    }


    // Helpers ------------------------------------------------------------------------------------
    /**
     * Map the current row of the given ResultSet to an User.
     */
    private static TimedNode mapTimedNode(ResultSet resultSet) throws SQLException {
        return new TimedNode(
      		resultSet.getLong("ATN_OID"), 
       		resultSet.getLong("ATN_NODE_FK"), 
       		resultSet.getLong("ATN_STAGE_FK"), 
       		resultSet.getString("ATN_STAGE_MODIFIER_FK"), 
       		resultSet.getString("ATN_PUBLIC_ID"), 
       		resultSet.getString("ANO_PUBLIC_ID"), 
       		resultSet.getString("STG_NAME"), 
       		resultSet.getLong("STG_SEQUENCE"), 
       		resultSet.getString("STAGE_MIN"), 
       		resultSet.getString("STAGE_MAX") 
        );
    }

}
