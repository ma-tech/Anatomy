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
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "WHERE ATN_PUBLIC_ID LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_TIMED_NODE " + 
        "WHERE ATN_PUBLIC_ID LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "WHERE ATN_OID = ? ";
    
    private static final String SQL_FIND_MAX_OID =
        "SELECT MAX(ATN_PUBLIC_ID) " +
        "FROM ANA_TIMED_NODE ";
    
    private static final String SQL_LIST_ALL =
        "SELECT a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE " +
        "FROM ANA_TIMED_NODE a " +
        "JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK " +
        "JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK " +
        "FROM ANA_TIMED_NODE ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_TIMED_NODE " +
        "(ATN_OID, ATN_NODE_FK, ATN_STAGE_FK, ATN_STAGE_MODIFIER_FK, ATN_PUBLIC_ID) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_TIMED_NODE " +
        "SET ATN_NODE_FK = ?, " +
        "ATN_STAGE_FK = ?, " + 
        "ATN_STAGE_MODIFIER_FK = ? ," + 
        "ATN_PUBLIC_ID = ? " + 
        "WHERE ATN_OID = ? ";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_TIMED_NODE " +
        "WHERE ATN_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT ATN_OID " +
        "FROM ANA_TIMED_NODE " +
        "WHERE ATN_OID = ? ";
    
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
     * Returns the timednode from the database matching the given OID, otherwise null.
     */
    public TimedNode find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
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
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                timednode = mapTimedNode(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return timednode;
    }

    /**
     * Returns a list of ALL timednodes, otherwise null.
     */
    public List<TimedNode> listAll() throws DAOException {
        return list(SQL_LIST_ALL, null);
    }
    
    /**
     * Returns a list of all timednodes from the database. 
     *  The list is never null and is empty when the database does not contain any timednodes.
     */
    public List<TimedNode> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TimedNode> timednodes = new ArrayList<TimedNode>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                timednodes.add(mapTimedNode(resultSet));
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

    /**
     * Create the given user in the database. 
     * After creating, the DAO will set the obtained ID in the given user.
     */
     
    public void create(TimedNode timednode) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
        	timednode.getOid(),
            timednode.getNodeFK(), 
            timednode.getStageFK(),
            timednode.getStageModifierFK(),
            timednode.getPublicId()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating timednode failed, no rows affected.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                timednode.setOid(generatedKeys.getLong(1));
            } 
            else {
                throw new DAOException("Creating timednode failed, no generated key obtained.");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }
    
    /**
     * Update the given timednode in the database.
     *  The timednode OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the timednode OID value is unknown, rather use save(TimedNode)}.
     */
    public void update(TimedNode timednode) throws DAOException {
    	
        if (timednode.getOid() == null) {
            throw new IllegalArgumentException("TimedNode is not created yet, so the timednode OID cannot be null.");
        }

        Object[] values = {
            timednode.getNodeFK(), 
            timednode.getStageFK(),
            timednode.getStageModifierFK(),
            timednode.getPublicId(),
            timednode.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating timednode failed, no rows affected.");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
    
     
    /**
     * Delete the given timednode from the database. 
     *  After deleting, the DAO will set the ID of the given timednode to null.
     */
    public void delete(TimedNode timednode) throws DAOException {
    	
        Object[] values = { 
        	timednode.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting timednode failed, no rows affected.");
            } 
            else {
            	timednode.setOid(null);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
    
    
    /**
     * Returns true if the given timednode OID exists in the database.
     */
    public boolean existOid(String oid) throws DAOException {
        return exist(SQL_EXIST_OID, oid);
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

    /**
     * Returns list of TimedNodes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<TimedNode> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws DAOException
    {
    	String sqlSortField = "ATN_OID";

        if (sortField.equals("oid")) {
        	sqlSortField = "ATN_OID";       
        }
        if (sortField.equals("nodeFK")) {
        	sqlSortField = "ATN_NODE_FK";      
        }
        if (sortField.equals("stageFK")) {
        	sqlSortField = "ATN_STAGE_FK";         
        }
        if (sortField.equals("stageModifierFK")) {
        	sqlSortField = "ATN_STAGE_MODIFIER_FK";         
        }
        if (sortField.equals("publicId")) {
        	sqlSortField = "ATN_PUBLIC_ID";         
        }
        if (sortField.equals("publicEmapaId")) {
        	sqlSortField = "ANO_PUBLIC_ID";         
        }
        if (sortField.equals("stageName")) {
        	sqlSortField = "STG_NAME";         
        }
        if (sortField.equals("stageSeq")) {
        	sqlSortField = "STG_SEQUENCE";         
        }
        
        String searchWithWildCards = "%" + searchTerm + "%";

        Object[] values = {
        		searchWithWildCards,
                firstRow, 
                rowCount
        };

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<TimedNode> dataList = new ArrayList<TimedNode>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);

            //System.out.println("PS = " + preparedStatement.toString());
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapTimedNode(resultSet));
            }
            
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return dataList;
    }

    /**
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm) throws DAOException {

        String searchWithWildCards = "%" + searchTerm + "%";

        Object[] values = {
        		searchWithWildCards
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_ROW_COUNT, false, values);

            //System.out.println("PS = " + preparedStatement.toString());

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
       		resultSet.getString("STG_SEQUENCE")
        );
    }

}
