package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.DerivedRelationshipTransitive;

/**
 * This class represents a SQL Database Access Object for the DerivedRelationshipTransitive DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the DerivedRelationshipTransitive DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class DerivedRelationshipTransitiveDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT RTR_OID, RTR_RELATIONSHIP_TYPE_FK, RTR_DESCENDENT_FK, RTR_ANCESTOR_FK " +
        "FROM ANAD_RELATIONSHIP_TRANSITIVE " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE =
        "SELECT RTR_OID, RTR_RELATIONSHIP_TYPE_FK, RTR_DESCENDENT_FK, RTR_ANCESTOR_FK " +
        "FROM ANAD_RELATIONSHIP_TRANSITIVE " +
        "WHERE RTR_DESCENDENT_FK LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_RELATIONSHIP_TRANSITIVE ";

    private static final String SQL_ROW_COUNT_WHERE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_RELATIONSHIP_TRANSITIVE " +
        "WHERE RTR_DESCENDENT_FK LIKE ? ";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a DerivedRelationshipTransitive DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    DerivedRelationshipTransitiveDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns a list of relationship matching the given Node FK, otherwise null.
     */
    public List<DerivedRelationshipTransitive> listAllByDescendantFK(String descendantFK) throws DAOException {
        return list(SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE, descendantFK);
    }
    
    /**
     * Returns a list of all derivedrelationshiptransitives from the database. 
     *  The list is never null and is empty when the database does not contain any derivedrelationshiptransitives.
     */
    public List<DerivedRelationshipTransitive> list(String sql, Object... values) throws DAOException {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<DerivedRelationshipTransitive> derivedrelationshiptransitives = new ArrayList<DerivedRelationshipTransitive>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);

            //System.out.println("PS = " + preparedStatement.toString());
            
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                derivedrelationshiptransitives.add(mapDerivedRelationshipTransitive(resultSet));
                
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return derivedrelationshiptransitives;
    }

    /*
     * Returns list of DerivedRelationshipTransitives for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedRelationshipTransitive> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws DAOException
    {

    	String sqlSortField = "RTR_OID";
    	
        if (sortField.equals("oid")) {
        	sqlSortField = "RTR_OID";       
        }
        if (sortField.equals("speciesFK")) {
        	sqlSortField = "RTR_RELATIONSHIP_TYPE_FK";      
        }
        if (sortField.equals("nodeStartFK")) {
        	sqlSortField = "RTR_DESCENDENT_FK";         
        }
        if (sortField.equals("nodeStopFK")) {
        	sqlSortField = "RTR_ANCESTOR_FK";         
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
            
            List<DerivedRelationshipTransitive> dataList = new ArrayList<DerivedRelationshipTransitive>();

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(connection, sql, false, values);

                //System.out.println("PS = " + preparedStatement.toString());
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedRelationshipTransitive(resultSet));
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
            
            List<DerivedRelationshipTransitive> dataList = new ArrayList<DerivedRelationshipTransitive>();

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(connection, sql, false, values);

                //System.out.println("PS = " + preparedStatement.toString());
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedRelationshipTransitive(resultSet));
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
    private static DerivedRelationshipTransitive mapDerivedRelationshipTransitive(ResultSet resultSet) throws SQLException {
        return new DerivedRelationshipTransitive(
      		resultSet.getLong("RTR_OID"), 
       		resultSet.getString("RTR_RELATIONSHIP_TYPE_FK"), 
       		resultSet.getLong("RTR_DESCENDENT_FK"), 
       		resultSet.getLong("RTR_ANCESTOR_FK") 
        );
    }

}