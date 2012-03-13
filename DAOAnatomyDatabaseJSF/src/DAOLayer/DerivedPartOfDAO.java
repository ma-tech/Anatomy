package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.DerivedPartOf;

/**
 * This class represents a SQL Database Access Object for the DerivedPartOf DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the DerivedPartOf DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class DerivedPartOfDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_FULL_PATH_OIDS, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK " +
        "FROM ANAD_PART_OF " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF ";

    private static final String SQL_ROW_COUNT_WHERE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_FULL_PATH LIKE ? ";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_FULL_PATH_OIDS, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_FULL_PATH LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_FIND_BY_OID =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_FULL_PATH_OIDS, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_OID = ?";
    
    private static final String SQL_LIST_BY_NODE_FK =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_FULL_PATH_OIDS, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_NODE_FK = ?";
    
    private static final String SQL_EXIST_OID =
        "SELECT APO_OID " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_OID = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a DerivedPartOf DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    DerivedPartOfDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the relationship from the database matching the given OID, otherwise null.
     */
    public DerivedPartOf find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns the relationship from the database matching the given 
     *  SQL query with the given values.
     */
    private DerivedPartOf find(String sql, Object... values) throws DAOException {

    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DerivedPartOf relationship = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                relationship = mapDerivedPartOf(resultSet);
                
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return relationship;
    }

    /**
     * Returns a list of relationship matching the given Node FK, otherwise null.
     */
    public List<DerivedPartOf> listAllByNodeFK(String nodeFK) throws DAOException {
        return list(SQL_LIST_BY_NODE_FK, nodeFK);
    }
    
    /**
     * Returns a list of all derivedpartofs from the database. 
     *  The list is never null and is empty when the database does not contain any derivedpartofs.
     */
    public List<DerivedPartOf> list(String sql, Object... values) throws DAOException {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<DerivedPartOf> derivedpartofs = new ArrayList<DerivedPartOf>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                derivedpartofs.add(mapDerivedPartOf(resultSet));
                
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return derivedpartofs;
    }

    
    /**
     * Returns true if the given relationship OID exists in the database.
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
     * Returns list of DerivedPartOfs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOf> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws DAOException
    {

    	String sqlSortField = "APO_OID";
    	
        if (sortField.equals("oid")) {
        	sqlSortField = "APO_OID";       
        }
        if (sortField.equals("speciesFK")) {
        	sqlSortField = "APO_SPECIES_FK";      
        }
        if (sortField.equals("nodeStartFK")) {
        	sqlSortField = "APO_NODE_START_STAGE_FK";         
        }
        if (sortField.equals("nodeStopFK")) {
        	sqlSortField = "APO_NODE_END_STAGE_FK";         
        }
        if (sortField.equals("pathStartFK")) {
        	sqlSortField = "APO_PATH_START_STAGE_FK";       
        }
        if (sortField.equals("pathStopFK")) {
        	sqlSortField = "APO_PATH_END_STAGE_FK";       
        }
        if (sortField.equals("nodeFK")) {
        	sqlSortField = "APO_NODE_FK";      
        }
        if (sortField.equals("sequence")) {
        	sqlSortField = "APO_SEQUENCE";         
        }
        if (sortField.equals("depth")) {
        	sqlSortField = "APO_DEPTH";         
        }
        if (sortField.equals("fullPath")) {
        	sqlSortField = "APO_FULL_PATH";       
        }
        if (sortField.equals("primary")) {
        	sqlSortField = "APO_IS_PRIMARY";      
        }
        if (sortField.equals("primaryPath")) {
        	sqlSortField = "APO_IS_PRIMARY_PATH";         
        }
        if (sortField.equals("parentFK")) {
        	sqlSortField = "APO_PARENT_APO_FK";         
        }
        
        String searchWithWildCards = "%" + searchTerm + "%";

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = "";

        if (searchTerm.equals("")){

        	sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
            Object[] values = {
                    firstRow, 
                    rowCount
            };
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<DerivedPartOf> dataList = new ArrayList<DerivedPartOf>();

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(connection, sql, false, values);

                //System.out.println("PS = " + preparedStatement.toString());
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedPartOf(resultSet));
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
        else {
            sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE, sqlSortField, sortDirection);
            Object[] values = {
            		searchWithWildCards,
                    firstRow, 
                    rowCount
            };
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<DerivedPartOf> dataList = new ArrayList<DerivedPartOf>();

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(connection, sql, false, values);

                //System.out.println("PS = " + preparedStatement.toString());
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedPartOf(resultSet));
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

            if (searchTerm.equals("")){
                preparedStatement = prepareStatement(connection, SQL_ROW_COUNT, false);
            }
            else {
                preparedStatement = prepareStatement(connection, SQL_ROW_COUNT_WHERE, false, values);
            }

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
    private static DerivedPartOf mapDerivedPartOf(ResultSet resultSet) throws SQLException {
        return new DerivedPartOf(
      		resultSet.getLong("APO_OID"), 
       		resultSet.getString("APO_SPECIES_FK"), 
       		resultSet.getLong("APO_NODE_START_STAGE_FK"), 
       		resultSet.getLong("APO_NODE_END_STAGE_FK"), 
       		resultSet.getLong("APO_PATH_START_STAGE_FK"), 
       		resultSet.getLong("APO_PATH_END_STAGE_FK"), 
       		resultSet.getLong("APO_NODE_FK"), 
       		resultSet.getLong("APO_SEQUENCE"), 
       		resultSet.getLong("APO_DEPTH"), 
       		resultSet.getString("APO_FULL_PATH"), 
       		resultSet.getString("APO_FULL_PATH_OIDS"), 
       		resultSet.getString("APO_FULL_PATH_JSON_HEAD"), 
       		resultSet.getString("APO_FULL_PATH_JSON_TAIL"), 
       		resultSet.getInt("APO_IS_PRIMARY"), 
       		resultSet.getInt("APO_IS_PRIMARY_PATH"), 
       		resultSet.getLong("APO_PARENT_APO_FK")
        );
    }

}
