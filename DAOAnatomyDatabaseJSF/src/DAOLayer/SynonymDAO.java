package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.Synonym;

/**
 * This class represents a SQL Database Access Object for the Synonym DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the Synonym DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class SynonymDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_SYNONYM LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_SYNONYM LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_OID = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM " +
        "FROM ANA_SYNONYM ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_SYNONYM " +
        "(SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM) " +
        "VALUES (?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_SYNONYM " +
        "SET SYN_OID = ?, " +
        "SYN_OBJECT_FK = ?, " + 
        "SYN_SYNONYM = ? " + 
        "WHERE SYN_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_SYNONYM " +
        "WHERE ATN_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT SYN_OID " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_OID = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a Synonym DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    SynonymDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the synonym from the database matching the given OID, otherwise null.
     */
    public Synonym find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns the synonym from the database matching the given 
     *  SQL query with the given values.
     */
    private Synonym find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Synonym synonym = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                synonym = mapSynonym(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return synonym;
    }

    /**
     * Returns a list of ALL synonyms, otherwise null.
     */
    public List<Synonym> listAll() throws DAOException {
        return list(SQL_LIST_ALL, null);
    }
    
    /**
     * Returns a list of all synonyms from the database. 
     *  The list is never null and is empty when the database does not contain any synonyms.
     */
    public List<Synonym> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Synonym> synonyms = new ArrayList<Synonym>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                synonyms.add(mapSynonym(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return synonyms;
    }

    /**
     * Create the given synonym in the database. 
     *  The synonym OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the synonym OID value is unknown, rather use save(Synonym).
     *  After creating, the DAO will set the obtained ID in the given synonym.
     */
     
    public void create(Synonym synonym) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
        	synonym.getOid(),
    	    synonym.getThingFK(),
    	    synonym.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating synonym failed, no rows affected.");
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
     * Update the given synonym in the database.
     *  The synonym OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the synonym OID value is unknown, rather use save(Synonym)}.
     */
    public void update(Synonym synonym) throws DAOException {
    	
        if (synonym.getOid() == null) {
            throw new IllegalArgumentException("Synonym is not created yet, so the synonym OID cannot be null.");
        }

        Object[] values = {
        	synonym.getThingFK(),
        	synonym.getName(),
            synonym.getOid()
        };


        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating synonym failed, no rows affected.");
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
     * Delete the given synonym from the database. 
     *  After deleting, the DAO will set the ID of the given synonym to null.
     */
    public void delete(Synonym synonym) throws DAOException {
    	
        Object[] values = { 
        	synonym.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting synonym failed, no rows affected.");
            } 
            else {
            	synonym.setOid(null);
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
     * Returns true if the given synonym OID exists in the database.
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
    public List<Synonym> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws DAOException
    {
    	String sqlSortField = "SYN_OID";
    	
        if (sortField.equals("oid")) {
        	sqlSortField = "SYN_OID";       
        }
        if (sortField.equals("thingFK")) {
        	sqlSortField = "SYN_OBJECT_FK";      
        }
        if (sortField.equals("name")) {
        	sqlSortField = "SYN_SYNONYM";         
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
        
        List<Synonym> dataList = new ArrayList<Synonym>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);

            //System.out.println("PS = " + preparedStatement.toString());
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapSynonym(resultSet));
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
    private static Synonym mapSynonym(ResultSet resultSet) throws SQLException {
        return new Synonym(
      		resultSet.getLong("SYN_OID"), 
       		resultSet.getLong("SYN_OBJECT_FK"), 
       		resultSet.getString("SYN_SYNONYM")
        );
    }

}
