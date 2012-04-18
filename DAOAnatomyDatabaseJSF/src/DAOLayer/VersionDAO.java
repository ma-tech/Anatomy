package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.Version;

/**
 * This class represents a SQL Database Access Object for the Version DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the Version DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class VersionDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT VER_OID, VER_NUMBER, VER_DATE, VER_COMMENTS " +
        "FROM ANA_VERSION " +
        "WHERE VER_DATE LIKE ? " +
        "AND VER_COMMENTS LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_VERSION " +
        "WHERE VER_DATE LIKE ? " +
        "AND VER_COMMENTS LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT VER_OID, VER_NUMBER, VER_DATE, VER_COMMENTS " +
        "FROM ANA_VERSION " +
        "WHERE VER_OID = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT VER_OID, VER_NUMBER, VER_DATE, VER_COMMENTS " +
        "FROM ANA_VERSION ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_VERSION " +
        "(VER_OID, VER_NUMBER, VER_DATE, VER_COMMENTS) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_VERSION " +
        "SET VER_NUMBER = ?, " +
        "VER_DATE = ?, " +
        "VER_COMMENTS = ? " + 
        "WHERE VER_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_VERSION " +
        "WHERE VER_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT VER_OID " +
        "FROM ANA_VERSION " +
        "WHERE VER_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a Version DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    VersionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the version from the database matching the given OID, otherwise null.
     */
    public Version find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns the version from the database matching the given 
     *  SQL query with the given values.
     */
    private Version find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Version version = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                version = mapVersion(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return version;
    }

    /**
     * Returns a list of ALL versions, otherwise null.
     */
    public List<Version> listAll() throws DAOException {
        return list(SQL_LIST_ALL, null);
    }
    
    /**
     * Returns a list of all versions from the database. 
     *  The list is never null and is empty when the database does not contain any versions.
     */
    public List<Version> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Version> versions = new ArrayList<Version>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                versions.add(mapVersion(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return versions;
    }

    /**
     * Create the given version in the database. 
     * The version OID must be null, otherwise it will throw IllegalArgumentException.
     * If the version OID value is unknown, rather use save(Version).
     * After creating, the DAO will set the obtained ID in the given version.
     */
     
    public void create(Version version) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
        	version.getOid(),
        	version.getNumber(),
        	version.getDate(),
        	version.getComments()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating version failed, no rows affected.");
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
     * Update the given version in the database.
     *  The version OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the version OID value is unknown, rather use save(Version)}.
     */
    public void update(Version version) throws DAOException {
    	
        if (version.getOid() == null) {
            throw new IllegalArgumentException("Version is not created yet, so the version OID cannot be null.");
        }

        Object[] values = {
         	version.getNumber(),
           	version.getDate(),
           	version.getComments(),
           	version.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating version failed, no rows affected.");
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
     * Delete the given version from the database. 
     *  After deleting, the DAO will set the ID of the given version to null.
     */
    public void delete(Version version) throws DAOException {
    	
        Object[] values = { 
        	version.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting version failed, no rows affected.");
            } 
            else {
            	version.setOid(null);
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
     * Returns true if the given version OID exists in the database.
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
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Version> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws DAOException
    {
    	String sqlSortField = "VER_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "VER_OID";       
        }
        if (sortField.equals("number")) {
        	sqlSortField = "VER_NUMBER";      
        }
        if (sortField.equals("date")) {
        	sqlSortField = "VER_DATE";         
        }
        if (sortField.equals("comments")) {
        	sqlSortField = "VER_COMMENTS";         
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
        
        List<Version> dataList = new ArrayList<Version>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);

            //System.out.println("PS = " + preparedStatement.toString());
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapVersion(resultSet));
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
    private static Version mapVersion(ResultSet resultSet) throws SQLException {
        return new Version(
      		resultSet.getLong("VER_OID"), 
       		resultSet.getLong("VER_NUMBER"), 
       		resultSet.getString("VER_DATE"), 
       		resultSet.getString("VER_COMMENTS")
        );
    }

}
