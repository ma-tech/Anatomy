package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import DAOModel.DerivedPartOfPerspectives;

/**
 * This class represents a SQL Database Access Object for the DerivedPartOfPerspectives DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the DerivedPartOfPerspectives DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class DerivedPartOfPerspectivesDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_ROW_COUNT_ALL =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE";

    private static final String SQL_ROW_COUNT_BY_PERSPECTIVE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_PERSPECTIVE_FK = ? ";

    private static final String SQL_ROW_COUNT_BY_PERSPECTIVE_AND =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND POP_NODE_FK = ? ";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE =
        "SELECT POP_PERSPECTIVE_FK, POP_APO_FK, POP_IS_ANCESTOR, POP_NODE_FK " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";
        
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND =
        "SELECT POP_PERSPECTIVE_FK, POP_APO_FK, POP_IS_ANCESTOR, POP_NODE_FK " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND POP_NODE_FK = ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a DerivedPartOfPerspectives DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    DerivedPartOfPerspectivesDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns list of DerivedPartOfPerspectivess for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOfPerspectives> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchPerspective)
        throws DAOException
    {

    	String sqlSortField = "POP_PERSPECTIVE_FK";
    	
        if (sortField.equals("perspectiveFK")) {
        	sqlSortField = "POP_PERSPECTIVE_FK";      
        }
        if (sortField.equals("nodeFK")) {
        	sqlSortField = "POP_NODE_FK";      
        }
        if (sortField.equals("partOfFK")) {
        	sqlSortField = "POP_APO_FK";         
        }
        if (sortField.equals("ancestor")) {
        	sqlSortField = "POP_IS_ANCESTOR";         
        }
        
        String searchWithWildCards = searchTerm;

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = "";

        if (searchTerm.equals("")){

        	sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE, sqlSortField, sortDirection);

        	Object[] values = {
            		searchPerspective,
                    firstRow, 
                    rowCount
            };

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<DerivedPartOfPerspectives> dataList = new ArrayList<DerivedPartOfPerspectives>();

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedPartOfPerspectives(resultSet));
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
            sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND, sqlSortField, sortDirection);

            Object[] values = {
            		searchPerspective,
            		searchWithWildCards,
                    firstRow, 
                    rowCount
            };

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<DerivedPartOfPerspectives> dataList = new ArrayList<DerivedPartOfPerspectives>();

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedPartOfPerspectives(resultSet));
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
    public int count(String searchTerm, String searchPerspective) throws DAOException {

        String searchWithWildCards = searchTerm;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        if (searchTerm.equals("")){
            try {

            	Object[] values = {
            			searchPerspective
                };

                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_BY_PERSPECTIVE, false, values);

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
        }
        else {
            try {

            	Object[] values = {
            			searchPerspective,
                		searchWithWildCards
                };

                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_BY_PERSPECTIVE_AND, false, values);

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
        }

        return count;
    }


    // Helpers ------------------------------------------------------------------------------------
    /**
     * Map the current row of the given ResultSet to an User.
     */
    private static DerivedPartOfPerspectives mapDerivedPartOfPerspectives(ResultSet resultSet) throws SQLException {
        return new DerivedPartOfPerspectives(
       		resultSet.getString("POP_PERSPECTIVE_FK"), 
       		resultSet.getLong("POP_APO_FK"), 
       		resultSet.getInt("POP_IS_ANCESTOR"), 
       		resultSet.getLong("POP_NODE_FK")
        );
    }

}