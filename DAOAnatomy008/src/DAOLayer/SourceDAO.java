package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import DAOModel.Source;

/**
 * This class represents a SQL Database Access Object for the Source DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the Source DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class SourceDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT SRC_OID, SRC_NAME, SRC_AUTHORS, SRC_FORMAT_FK, SRC_YEAR " +
        "FROM ANA_SOURCE " +
        "WHERE SRC_NAME LIKE ? " +
        "AND SRC_AUTHORS LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_SOURCE " +
        "WHERE SRC_NAME LIKE ? " +
        "AND SRC_AUTHORS LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT SRC_OID, SRC_NAME, SRC_AUTHORS, SRC_FORMAT_FK, SRC_YEAR " +
        "FROM ANA_SOURCE " +
        "WHERE ANO_OID = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT SRC_OID, SRC_NAME, SRC_AUTHORS, SRC_FORMAT_FK, SRC_YEAR " +
        "FROM ANA_SOURCE ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_SOURCE " +
        "(SRC_OID, SRC_NAME, SRC_AUTHORS, SRC_FORMAT_FK, SRC_YEAR) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_SOURCE " +
        "SET SRC_NAME = ?, " +
        "SRC_AUTHORS = ?, " +
        "SRC_FORMAT_FK = ?, " + 
        "SRC_YEAR = ? " + 
        "WHERE SRC_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_SOURCE " +
        "WHERE SRC_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT SRC_OID " +
        "FROM ANA_SOURCE " +
        "WHERE SRC_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a Source DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    SourceDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the source from the database matching the given OID, otherwise null.
     */
    public Source find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns the source from the database matching the given 
     *  SQL query with the given values.
     */
    private Source find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Source source = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                source = mapSource(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return source;
    }

    /**
     * Returns a list of ALL sources, otherwise null.
     */
    public List<Source> listAll() throws DAOException {
        return list(SQL_LIST_ALL, null);
    }
    
    /**
     * Returns a list of all sources from the database. 
     *  The list is never null and is empty when the database does not contain any sources.
     */
    public List<Source> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Source> sources = new ArrayList<Source>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sources.add(mapSource(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return sources;
    }

    /**
     * Create the given source in the database. 
     * The source OID must be null, otherwise it will throw IllegalArgumentException.
     * If the source OID value is unknown, rather use save(Source).
     * After creating, the DAO will set the obtained ID in the given source.
     */
     
    public void create(Source source) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
    	    source.getOid(),
    	    source.getName(),
    	    source.getAuthors(),
    	    source.getFormatFK(),
    	    source.getYear()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating source failed, no rows affected.");
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
     * Update the given source in the database.
     *  The source OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the source OID value is unknown, rather use save(Source)}.
     */
    public void update(Source source) throws DAOException {
    	
        if (source.getOid() == null) {
            throw new IllegalArgumentException("Source is not created yet, so the source OID cannot be null.");
        }

        Object[] values = {
      	    source.getName(),
       	    source.getAuthors(),
       	    source.getFormatFK(),
       	    source.getYear(),
            source.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating source failed, no rows affected.");
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
     * Delete the given source from the database. 
     *  After deleting, the DAO will set the ID of the given source to null.
     */
    public void delete(Source source) throws DAOException {
    	
        Object[] values = { 
        	source.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting source failed, no rows affected.");
            } 
            else {
            	source.setOid(null);
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
     * Returns true if the given source OID exists in the database.
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
    public List<Source> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws DAOException
    {
    	String sqlSortField = "SRC_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "SRC_OID";       
        }
        if (sortField.equals("name")) {
        	sqlSortField = "SRC_NAME";      
        }
        if (sortField.equals("authors")) {
        	sqlSortField = "SRC_AUTHORS";         
        }
        if (sortField.equals("formatFK")) {
        	sqlSortField = "SRC_FORMAT_FK";         
        }
        if (sortField.equals("year")) {
        	sqlSortField = "SRC_YEAR";         
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
        
        List<Source> dataList = new ArrayList<Source>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapSource(resultSet));
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
    private static Source mapSource(ResultSet resultSet) throws SQLException {
        return new Source(
        		resultSet.getLong("SRC_OID"),
        		resultSet.getString("SRC_NAME"),
        		resultSet.getString("SRC_AUTHORS"),
        		resultSet.getString("SRC_FORMAT_FK"),
        		resultSet.getLong("SRC_YEAR")
        		);
    }

}