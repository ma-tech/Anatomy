package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.Thing;

/**
 * This class represents a SQL Database Access Object for the Thing DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the Thing DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class ThingDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK, OBJ_TABLE, OBJ_DESCRIPTION " +
        "FROM ANA_OBJECT " +
        "WHERE OBJ_DESCRIPTION LIKE ? " +
        "AND OBJ_TABLE LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_OBJECT " +
        "WHERE OBJ_DESCRIPTION LIKE ? " +
        "AND OBJ_TABLE LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK, OBJ_TABLE, OBJ_DESCRIPTION " +
        "FROM ANA_OBJECT " +
        "WHERE OBJ_OID = ?";
    
    private static final String SQL_GET_MAX_OID =
        "SELECT MAX(OBJ_OID) AS VALUE" +
        "FROM ANA_OBJECT ";
    
    private static final String SQL_LIST_ALL =
        "SELECT OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK, OBJ_TABLE, OBJ_DESCRIPTION " +
        "FROM ANA_OBJECT ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBJECT " +
        "(OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK, OBJ_TABLE, OBJ_DESCRIPTION) " +
        "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBJECT " +
        "SET OBJ_CREATION_DATETIME = ?, " +
        "OBJ_CREATOR_FK = ?, " + 
        "OBJ_TABLE = ? " + 
        "OBJ_DESCRIPTION = ? " + 
        "WHERE OBJ_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBJECT " +
        "WHERE OBJ_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT OBJ_OID " +
        "FROM ANA_OBJECT " +
        "WHERE OBJ_OID = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a Thing DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    ThingDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the thing from the database matching the given OID, otherwise null.
     */
    public Thing find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns the thing from the database matching the given 
     *  SQL query with the given values.
     */
    private Thing find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Thing thing = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                thing = mapThing(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return thing;
    }

    /**
     * Returns a list of ALL things, otherwise null.
     */
    public List<Thing> listAll() throws DAOException {
        return list(SQL_LIST_ALL, null);
    }
    
    /**
     * Returns a list of all things from the database. 
     *  The list is never null and is empty when the database does not contain any things.
     */
    public List<Thing> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Thing> things = new ArrayList<Thing>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                things.add(mapThing(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return things;
    }

    /**
     * Create the given user in the database. 
     * The user ID must be null, otherwise it will throw
     * IllegalArgumentException. If the user ID value is unknown, rather use {@link #save(User)}.
     * After creating, the DAO will set the obtained ID in the given user.
     */
     
    public void create(Thing thing) throws IllegalArgumentException, DAOException {
    	
        if (thing.getOid() != null) {
            throw new IllegalArgumentException("Thing has already been created, as the thing OID is not null.");
        }
        
        Long maxOid = findMaxOid();
        maxOid++;
        thing.setOid(maxOid);

        Object[] values = {
        	thing.getOid(),
            thing.getCreationDateTime(),
            thing.getCreatorFK(),
            thing.getTable(),
            thing.getDescription()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating thing failed, no rows affected.");
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
     * Update the given thing in the database.
     *  The thing OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the thing OID value is unknown, rather use save(Thing)}.
     */
    public void update(Thing thing) throws DAOException {
    	
        if (thing.getOid() == null) {
            throw new IllegalArgumentException("Thing is not created yet, so the thing OID cannot be null.");
        }

        Object[] values = {
            thing.getCreationDateTime(),
            thing.getCreatorFK(),
            thing.getTable(),
            thing.getDescription(),
            thing.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating thing failed, no rows affected.");
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
     * Save the given thing in the database.
     *  If the thing OID is null, then 
     *   it will invoke create(Thing), else 
     *   it will invoke update(Thing).
     */
    public void save(Thing thing) throws DAOException {
    	
        if (thing.getOid() == null) {
            create(thing);
        } 
        else {
            update(thing);
        }
    }
    

    /**
     * Delete the given thing from the database. 
     *  After deleting, the DAO will set the ID of the given thing to null.
     */
    public void delete(Thing thing) throws DAOException {
    	
        Object[] values = { 
        	thing.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting thing failed, no rows affected.");
            } 
            else {
            	thing.setOid(null);
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
     * Returns true if the given thing OID exists in the database.
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
     * Returns the thing from the database matching the given OID, otherwise null.
     */
    public Long findMaxOid() throws DAOException {
        return value(SQL_GET_MAX_OID);
    }

    /**
     * Returns a value if the given SQL query with the given values returns at least one row.
     */
    private Long value(String sql) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long value;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false);
            resultSet = preparedStatement.executeQuery();
            value = resultSet.getLong("VALUE");
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return value;
    }

    /**
     * Returns list of Things for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */

    public List<Thing> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchTable)
        throws DAOException
    {
    	String sqlSortField = "OBJ_OID";

        if (sortField.equals("oid")) {
        	sqlSortField = "OBJ_OID";       
        }
        if (sortField.equals("creationDateTime")) {
        	sqlSortField = "OBJ_CREATION_DATETIME";      
        }
        if (sortField.equals("creatorFK")) {
        	sqlSortField = "OBJ_CREATOR_FK";         
        }
        if (sortField.equals("table")) {
        	sqlSortField = "OBJ_TABLE";         
        }
        if (sortField.equals("description")) {
        	sqlSortField = "OBJ_DESCRIPTION";         
        }
        
        String searchWithWildCards = "%" + searchTerm + "%";
        String tableWithWildCards = "%" + searchTable + "%";

        Object[] values = {
        		searchWithWildCards,
        		tableWithWildCards,
                firstRow, 
                rowCount
        };

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<Thing> dataList = new ArrayList<Thing>();
        
        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);

            //System.out.println("PS = " + preparedStatement.toString());
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapThing(resultSet));
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
    public int count(String searchTerm, String searchTable) throws DAOException {

        String searchWithWildCards = "%" + searchTerm + "%";
        String tableWithWildCards = "%" + searchTable + "%";

        Object[] values = {
        		searchWithWildCards,
        		tableWithWildCards
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
    private static Thing mapThing(ResultSet resultSet) throws SQLException {
        return new Thing(
      		resultSet.getLong("OBJ_OID"), 
       		resultSet.getString("OBJ_CREATION_DATETIME"), 
       		resultSet.getLong("OBJ_CREATOR_FK"),
       		resultSet.getString("OBJ_TABLE"),
       		resultSet.getString("OBJ_DESCRIPTION")
        );
    }

}
