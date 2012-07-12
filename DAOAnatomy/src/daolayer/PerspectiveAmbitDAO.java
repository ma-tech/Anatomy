package daolayer;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.PerspectiveAmbit;

/**
 * This class represents a SQL Database Access Object for the PerspectiveAmbit DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the PerspectiveAmbit DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class PerspectiveAmbitDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT PAM_OID, PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS " +
        "FROM ANA_PERSPECTIVE_AMBIT " +
        "WHERE PAM_PERSPECTIVE_FK LIKE ? " +
        "AND PAM_COMMENTS LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_PERSPECTIVE_AMBIT " +
        "WHERE PAM_PERSPECTIVE_FK LIKE ? " +
        "AND PAM_COMMENTS LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT PAM_OID, PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS " +
        "FROM ANA_PERSPECTIVE_AMBIT " +
        "WHERE PAM_OID = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT PAM_OID, PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS " +
        "FROM ANA_PERSPECTIVE_AMBIT ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_PERSPECTIVE_AMBIT " +
        "(PAM_OID, PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_PERSPECTIVE_AMBIT " +
        "SET PAM_PERSPECTIVE_FK = ?, " +
        "PAM_NODE_FK = ?, " +
        "PAM_IS_START = ?, " + 
        "PAM_IS_STOP = ?, " +
        "PAM_COMMENTS = ? " + 
        "WHERE PAM_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_PERSPECTIVE_AMBIT " +
        "WHERE PAM_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT PAM_OID " +
        "FROM ANA_PERSPECTIVE_AMBIT " +
        "WHERE PAM_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a PerspectiveAmbit DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    PerspectiveAmbitDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the perspectiveAmbit from the database matching the given OID, otherwise null.
     */
    public PerspectiveAmbit find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns the perspectiveAmbit from the database matching the given 
     *  SQL query with the given values.
     */
    private PerspectiveAmbit find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PerspectiveAmbit perspectiveAmbit = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                perspectiveAmbit = mapPerspectiveAmbit(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return perspectiveAmbit;
    }

    /**
     * Returns a list of ALL perspectiveAmbits, otherwise null.
     */
    public List<PerspectiveAmbit> listAll() throws DAOException {
        return list(SQL_LIST_ALL, (Object[]) null);
    }
    
    /**
     * Returns a list of all perspectiveAmbits from the database. 
     *  The list is never null and is empty when the database does not contain any perspectiveAmbits.
     */
    public List<PerspectiveAmbit> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PerspectiveAmbit> perspectiveAmbits = new ArrayList<PerspectiveAmbit>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                perspectiveAmbits.add(mapPerspectiveAmbit(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return perspectiveAmbits;
    }

    /**
     * Create the given perspectiveAmbit in the database. 
     * The perspectiveAmbit OID must be null, otherwise it will throw IllegalArgumentException.
     * If the perspectiveAmbit OID value is unknown, rather use save(PerspectiveAmbit).
     * After creating, the DAO will set the obtained ID in the given perspectiveAmbit.
     */
     
    public void create(PerspectiveAmbit perspectiveAmbit) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
        	perspectiveAmbit.getOid(),
        	perspectiveAmbit.getPerspectiveFK(),
        	perspectiveAmbit.getNodeFK(),
        	perspectiveAmbit.getIsStart(),
        	perspectiveAmbit.getIsStop(),
        	perspectiveAmbit.getComments()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating perspectiveAmbit failed, no rows affected.");
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
     * Update the given perspectiveAmbit in the database.
     *  The perspectiveAmbit OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the perspectiveAmbit OID value is unknown, rather use save(PerspectiveAmbit)}.
     */
    public void update(PerspectiveAmbit perspectiveAmbit) throws DAOException {
    	
        if (perspectiveAmbit.getOid() == null) {
            throw new IllegalArgumentException("PerspectiveAmbit is not created yet, so the perspectiveAmbit OID cannot be null.");
        }

        Object[] values = {
            perspectiveAmbit.getPerspectiveFK(),
            perspectiveAmbit.getNodeFK(),
            perspectiveAmbit.getIsStart(),
            perspectiveAmbit.getIsStop(),
            perspectiveAmbit.getComments(),
            perspectiveAmbit.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating perspectiveAmbit failed, no rows affected.");
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
     * Delete the given perspectiveAmbit from the database. 
     *  After deleting, the DAO will set the ID of the given perspectiveAmbit to null.
     */
    public void delete(PerspectiveAmbit perspectiveAmbit) throws DAOException {
    	
        Object[] values = { 
        	perspectiveAmbit.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting perspectiveAmbit failed, no rows affected.");
            } 
            else {
            	perspectiveAmbit.setOid(null);
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
     * Returns true if the given perspectiveAmbit OID exists in the database.
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
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
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
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<PerspectiveAmbit> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws DAOException
    {
    	String sqlSortField = "PAM_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "PAM_OID";       
        }
        if (sortField.equals("perspectiveFK")) {
        	sqlSortField = "PAM_PERSPECTIVE_FK";      
        }
        if (sortField.equals("nodeFK")) {
        	sqlSortField = "PAM_NODE_FK";         
        }
        if (sortField.equals("isStart")) {
        	sqlSortField = "PAM_IS_START";         
        }
        if (sortField.equals("isStop")) {
        	sqlSortField = "PAM_IS_STOP";         
        }
        if (sortField.equals("comments")) {
        	sqlSortField = "PAM_COMMENTS";         
        }
        
        String searchWithWildCards = "";
        String extraWithWildCards = "";

        if (searchTerm.equals("")) {
        	//searchWithWildCards = "";
        	searchWithWildCards = "%" + searchTerm + "%";
    	}
        else {
        	searchWithWildCards = "%" + searchTerm + "%";
        }

        if (searchExtra.equals("")) {
        	//extraWithWildCards = "";
        	extraWithWildCards = "%" + searchExtra + "%";
    	}
        else {
        	extraWithWildCards = "%" + searchExtra + "%";
        }
        
         Object[] values = {
        		searchWithWildCards, 
        		extraWithWildCards,
                firstRow, 
                rowCount
        };

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<PerspectiveAmbit> dataList = new ArrayList<PerspectiveAmbit>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapPerspectiveAmbit(resultSet));
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
    public int count(String searchTerm, String searchExtra) throws DAOException {

        String searchWithWildCards = "";
        String extraWithWildCards = "";

        if (searchTerm.equals("")) {
        	//searchWithWildCards = "";
        	searchWithWildCards = "%" + searchTerm + "%";
    	}
        else {
        	searchWithWildCards = "%" + searchTerm + "%";
        }

        if (searchExtra.equals("")) {
        	//extraWithWildCards = "";
        	extraWithWildCards = "%" + searchExtra + "%";
    	}
        else {
        	extraWithWildCards = "%" + searchExtra + "%";
        }
        
        Object[] values = {
        		searchWithWildCards, 
        		extraWithWildCards
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

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
    private static PerspectiveAmbit mapPerspectiveAmbit(ResultSet resultSet) throws SQLException {
        return new PerspectiveAmbit(
      		resultSet.getLong("PAM_OID"), 
       		resultSet.getString("PAM_PERSPECTIVE_FK"), 
       		resultSet.getLong("PAM_NODE_FK"), 
       		resultSet.getInt("PAM_IS_START"),
       		resultSet.getInt("PAM_IS_STOP"), 
       		resultSet.getString("PAM_COMMENTS")
        );
    }

}
