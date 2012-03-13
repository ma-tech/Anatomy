package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.Stage;

/**
 * This class represents a SQL Database Access Object for the Stage DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the Stage DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class StageDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "WHERE STG_SHORT_EXTRA_TEXT LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_STAGE " +
        "WHERE STG_SHORT_EXTRA_TEXT LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "WHERE STG_OID = ?";
    
    private static final String SQL_FIND_BY_NAME =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "WHERE STG_NAME = ?";
    
    private static final String SQL_FIND_BY_SEQUENCE =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "WHERE STG_SEQUENCE = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_STAGE " +
        "(STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_STAGE " +
        "SET STG_SPECIES_FK = ?, " +
        "STG_NAME = ?, " +
        "STG_SEQUENCE = ?, " + 
        "STG_DESCRIPTION = ?, " +
        "STG_SHORT_EXTRA_TEXT = ?, " +
        "STG_PUBLIC_ID = ? " + 
        "WHERE STG_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_STAGE " +
        "WHERE STG_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT STG_OID " +
        "FROM ANA_STAGE " +
        "WHERE STG_OID = ?";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a Stage DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    StageDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the stage from the database matching the given OID, otherwise null.
     */
    public Stage find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns a stage matching the given Name, otherwise null.
     */
    public Stage findByName(String name) throws DAOException {
        return find(SQL_FIND_BY_NAME, name);
    }
    
    /**
     * Returns a stage matching the given Sequence, otherwise null.
     */
    public Stage findBySequence(Long sequence) throws DAOException {
        return find(SQL_FIND_BY_SEQUENCE, sequence);
    }
    
    /**
     * Returns the stage from the database matching the given 
     *  SQL query with the given values.
     */
    private Stage find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Stage stage = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stage = mapStage(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return stage;
    }

    /**
     * Returns a list of stage matching the given Parent FK, otherwise null.
     */
    public List<Stage> listAll() throws DAOException {
        return list(SQL_LIST_ALL, null);
    }
    
    /**
     * Returns a list of all stages from the database. 
     *  The list is never null and is empty when the database does not contain any stages.
     */
    public List<Stage> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Stage> stages = new ArrayList<Stage>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stages.add(mapStage(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return stages;
    }

    /**
     * Create the given stage in the database. 
     *  The stage OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the stage OID value is unknown, rather use save(Stage).
     *  After creating, the DAO will set the obtained OID in the given stage.
     */
     
    public void create(Stage stage) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
        	stage.getOid(),
        	stage.getSpeciesFK(),
            stage.getName(),
            stage.getSequence(),
            stage.getDescription(),
            stage.getExtraText(),
            stage.getPublicId()
        };
        

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating stage failed, no rows affected.");
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
     * Update the given stage in the database.
     *  The stage OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the stage OID value is unknown, rather use save(Stage)}.
     */
    public void update(Stage stage) throws DAOException {
    	
        if (stage.getOid() == null) {
            throw new IllegalArgumentException("Stage is not created yet, so the stage OID cannot be null.");
        }

        Object[] values = {
        	stage.getSpeciesFK(),
            stage.getName(),
            stage.getSequence(),
            stage.getDescription(),
            stage.getExtraText(),
            stage.getPublicId(),
            stage.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating stage failed, no rows affected.");
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
     * Delete the given stage from the database. 
     *  After deleting, the DAO will set the ID of the given stage to null.
     */
    public void delete(Stage stage) throws DAOException {
    	
        Object[] values = { 
        	stage.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting stage failed, no rows affected.");
            } 
            else {
            	stage.setOid(null);
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
     * Returns true if the given stage OID exists in the database.
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
     * Returns list of Stages for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Stage> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws DAOException
    {
    	String sqlSortField = "STG_OID";
    	
        if (sortField.equals("oid")) {
        	sqlSortField = "STG_OID";       
        }
        if (sortField.equals("thingFK")) {
        	sqlSortField = "STG_SPECIES_FK";      
        }
        if (sortField.equals("name")) {
        	sqlSortField = "STG_NAME";         
        }
        if (sortField.equals("sequence")) {
        	sqlSortField = "STG_SEQUENCE";       
        }
        if (sortField.equals("description")) {
        	sqlSortField = "STG_DESCRIPTION";      
        }
        if (sortField.equals("extraText")) {
        	sqlSortField = "STG_SHORT_EXTRA_TEXT";         
        }
        if (sortField.equals("publicId")) {
        	sqlSortField = "STG_PUBLIC_ID";         
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
        
        List<Stage> dataList = new ArrayList<Stage>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);

            //System.out.println("PS = " + preparedStatement.toString());
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapStage(resultSet));
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
    private static Stage mapStage(ResultSet resultSet) throws SQLException {
        return new Stage(
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
