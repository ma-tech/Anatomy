package daolayer;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import utility.WhatIsThisString;

import daomodel.DerivedPartOfPerspectivesFK;

/**
 * This class represents a SQL Database Access Object for the DerivedPartOf DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the DerivedPartOf DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class DerivedPartOfPerspectivesFKDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE ";

    private static final String SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = POP_NODE_FK " +
        "JOIN ANA_TIMED_NODE c ON c.ATN_NODE_FK = b.ANO_OID " + 
        "JOIN ANA_STAGE d ON d.STG_OID = c.ATN_STAGE_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND d.STG_SEQUENCE = ? ";

    private static final String SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE_AND_PATH =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = POP_NODE_FK " +
        "JOIN ANA_TIMED_NODE c ON c.ATN_NODE_FK = b.ANO_OID " + 
        "JOIN ANA_STAGE d ON d.STG_OID = c.ATN_STAGE_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND a.APO_FULL_PATH LIKE ? " +
        "AND d.STG_SEQUENCE = ? ";

    private static final String SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE_AND_ID =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = POP_NODE_FK " +
        "JOIN ANA_TIMED_NODE c ON c.ATN_NODE_FK = b.ANO_OID " + 
        "JOIN ANA_STAGE d ON d.STG_OID = c.ATN_STAGE_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND ( b.ANO_PUBLIC_ID LIKE ? " +
        "OR c.ATN_PUBLIC_ID LIKE ? ) " +
        "AND d.STG_SEQUENCE = ? ";

    private static final String SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE_AND_PATH_AND_ID =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = POP_NODE_FK " +
        "JOIN ANA_TIMED_NODE c ON c.ATN_NODE_FK = b.ANO_OID " + 
        "JOIN ANA_STAGE d ON d.STG_OID = c.ATN_STAGE_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND ( a.APO_FULL_PATH LIKE ? " +
        "OR b.ANO_PUBLIC_ID LIKE ? " +
        "OR c.ATN_PUBLIC_ID LIKE ? ) " +
        "AND d.STG_SEQUENCE = ? " + 
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE =
        "SELECT POP_PERSPECTIVE_FK, a.APO_FULL_PATH AS FULL_PATH, CONCAT(a.APO_FULL_PATH_JSON_HEAD, a.APO_FULL_PATH_JSON_TAIL) AS FULL_PATH_JSON, POP_IS_ANCESTOR, b.ANO_PUBLIC_ID AS EMAPA_PUBLIC_ID, c.ATN_PUBLIC_ID AS EMAP_PUBLIC_ID " +
  		"FROM ANAD_PART_OF_PERSPECTIVE " +
   		"JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
   		"JOIN ANA_NODE b ON b.ANO_OID = POP_NODE_FK " +
   		"JOIN ANA_TIMED_NODE c ON c.ATN_NODE_FK = b.ANO_OID " + 
   		"JOIN ANA_STAGE d ON d.STG_OID = c.ATN_STAGE_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND d.STG_SEQUENCE = ? " + 
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND_PATH =
        "SELECT POP_PERSPECTIVE_FK, a.APO_FULL_PATH AS FULL_PATH, CONCAT(a.APO_FULL_PATH_JSON_HEAD, a.APO_FULL_PATH_JSON_TAIL) AS FULL_PATH_JSON, POP_IS_ANCESTOR, b.ANO_PUBLIC_ID AS EMAPA_PUBLIC_ID, c.ATN_PUBLIC_ID AS EMAP_PUBLIC_ID " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = POP_NODE_FK " +
        "JOIN ANA_TIMED_NODE c ON c.ATN_NODE_FK = b.ANO_OID " + 
        "JOIN ANA_STAGE d ON d.STG_OID = c.ATN_STAGE_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND a.APO_FULL_PATH LIKE ? " +
        "AND d.STG_SEQUENCE = ? " + 
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND_ID =
        "SELECT POP_PERSPECTIVE_FK, a.APO_FULL_PATH AS FULL_PATH, CONCAT(a.APO_FULL_PATH_JSON_HEAD, a.APO_FULL_PATH_JSON_TAIL) AS FULL_PATH_JSON, POP_IS_ANCESTOR, b.ANO_PUBLIC_ID AS EMAPA_PUBLIC_ID, c.ATN_PUBLIC_ID AS EMAP_PUBLIC_ID " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = POP_NODE_FK " +
        "JOIN ANA_TIMED_NODE c ON c.ATN_NODE_FK = b.ANO_OID " + 
        "JOIN ANA_STAGE d ON d.STG_OID = c.ATN_STAGE_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND (b.ANO_PUBLIC_ID LIKE ? " +
        "OR c.ATN_PUBLIC_ID LIKE ? ) " +
        "AND d.STG_SEQUENCE = ? " + 
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND_PATH_AND_ID =
        "SELECT POP_PERSPECTIVE_FK, a.APO_FULL_PATH AS FULL_PATH, CONCAT(a.APO_FULL_PATH_JSON_HEAD, a.APO_FULL_PATH_JSON_TAIL) AS FULL_PATH_JSON, POP_IS_ANCESTOR, b.ANO_PUBLIC_ID AS EMAPA_PUBLIC_ID, c.ATN_PUBLIC_ID AS EMAP_PUBLIC_ID " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "JOIN ANA_NODE b ON b.ANO_OID = POP_NODE_FK " +
        "JOIN ANA_TIMED_NODE c ON c.ATN_NODE_FK = b.ANO_OID " + 
        "JOIN ANA_STAGE d ON d.STG_OID = c.ATN_STAGE_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND ( a.APO_FULL_PATH LIKE ? " +
        "OR b.ANO_PUBLIC_ID LIKE ? " +
        "OR c.ATN_PUBLIC_ID LIKE ? ) " +
        "AND d.STG_SEQUENCE = ? " + 
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a DerivedPartOf DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    DerivedPartOfPerspectivesFKDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns list of DerivedPartOfs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOfPerspectivesFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchId, String searchDirection, String searchStartStage, String searchEndStage, String searchPerspective)
        throws DAOException
    {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<DerivedPartOfPerspectivesFK> dataList = new ArrayList<DerivedPartOfPerspectivesFK>();

    	String sqlSortField = "POP_PERSPECTIVE_FK";
    	
        if (sortField.equals("perspectiveFK")) {
        	sqlSortField = "POP_PERSPECTIVE_FK";      
        }
        if (sortField.equals("nodeEmapa")) {
        	sqlSortField = "EMAPA_PUBLIC_ID";      
        }
        if (sortField.equals("nodeEmap")) {
        	sqlSortField = "EMAP_PUBLIC_ID";      
        }
        if (sortField.equals("fullPath")) {
        	sqlSortField = "FULL_PATH";       
        }
        if (sortField.equals("fullPathJson")) {
        	sqlSortField = "FULL_PATH_JSON";       
        }
        if (sortField.equals("ancestor")) {
        	sqlSortField = "POP_IS_ANCESTOR";      
        }
        
        String searchTermWithWildCards = "";
        String searchEMAPAIdWithWildCards = "";
        String searchEMAPIdWithWildCards = "";

        if (WhatIsThisString.isItNumeric(searchTerm)){
        	searchId = "%" + searchTerm + "%";
        	searchTermWithWildCards = "";
        	searchTerm = "";
        }
        
        if (searchDirection.equals("UP")) {
            searchTermWithWildCards = "%." + searchTerm;
        }

        if (searchDirection.equals("ALL")) {
            searchTermWithWildCards = "%" + searchTerm + "%";
        	//searchTermWithWildCards = "%." + searchTerm + ".%";
        }

        int start = 0;
        
        if (WhatIsThisString.isItNumeric(searchStartStage)){
            start = Integer.parseInt(searchStartStage);
        }

		searchStartStage = Integer.toString(start);

        searchEMAPAIdWithWildCards = "EMAPA:" + searchId;
        searchEMAPIdWithWildCards = "EMAP:" + searchId;

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = "";

        if (searchTerm.equals("") && searchId.equals("")){

        	sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE, sqlSortField, sortDirection);

        	Object[] values = {
            		searchPerspective,
            		searchStartStage,
                    firstRow, 
                    rowCount
            };
            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedPartOfPerspectivesFK(resultSet));
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
        
        if (searchTerm.equals("") && !searchId.equals("")){

        	sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND_ID, sqlSortField, sortDirection);
            
        	Object[] values = {
            		searchPerspective,
            		searchEMAPAIdWithWildCards,
            		searchEMAPIdWithWildCards,
            		searchStartStage,
                    firstRow, 
                    rowCount
            };

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedPartOfPerspectivesFK(resultSet));
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

        if (!searchTerm.equals("") && searchId.equals("")){

        	sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND_PATH, sqlSortField, sortDirection);

        	Object[] values = {
            		searchPerspective,
            		searchTermWithWildCards,
            		searchStartStage,
                    firstRow, 
                    rowCount
            };

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedPartOfPerspectivesFK(resultSet));
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
        
        if (!searchTerm.equals("") && !searchId.equals("")){

        	sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND_PATH_AND_ID, sqlSortField, sortDirection);
            Object[] values = {
            		searchPerspective,
            		searchTermWithWildCards,
            		searchEMAPAIdWithWildCards,
            		searchEMAPIdWithWildCards,
            		searchStartStage,
                    firstRow, 
                    rowCount
            };

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapDerivedPartOfPerspectivesFK(resultSet));
                }
                
            } 
            catch (SQLException e) {
                throw new DAOException(e);
            } 
            finally {
                close(connection, preparedStatement, resultSet);
            }


        }

        return dataList;

    }

    /**
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm, String searchId, String searchDirection, String searchStartStage, String searchEndStage, String searchPerspective) throws DAOException {

        String searchTermWithWildCards = "";
        String searchEMAPAIdWithWildCards = "";
        String searchEMAPIdWithWildCards = "";

        if (WhatIsThisString.isItNumeric(searchTerm)){
        	searchId = "%" + searchTerm + "%";
        	searchTermWithWildCards = "";
        	searchTerm = "";
        }
        
        if (searchDirection.equals("UP")) {
            searchTermWithWildCards = "%." + searchTerm;
        }

        if (searchDirection.equals("ALL")) {
        	//searchTermWithWildCards = "%." + searchTerm + ".%";
            searchTermWithWildCards = "%" + searchTerm + "%";
        }

        int start = 0;
        
        if (WhatIsThisString.isItNumeric(searchStartStage)){
            start = Integer.parseInt(searchStartStage);
        }

		searchStartStage = Integer.toString(start);

        searchEMAPAIdWithWildCards = "EMAPA:" + searchId;
        searchEMAPIdWithWildCards = "EMAP:" + searchId;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        if (searchTerm.equals("") && searchId.equals("")){
        	
            Object[] values = {
            		searchPerspective,
            		searchStartStage
            };

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE, false, values);

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
        
        if (searchTerm.equals("") && !searchId.equals("")) {

            Object[] values = {
            		searchPerspective,
            		searchEMAPAIdWithWildCards,
               		searchEMAPIdWithWildCards,
               	 	searchStartStage
            };

        	try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE_AND_ID, false, values);

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
        	
        if (!searchTerm.equals("") && searchId.equals("")){
        	
            Object[] values = {
            		searchPerspective,
            		searchTermWithWildCards,
            		searchStartStage
            };

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE_AND_PATH, false, values);

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
        
        if (!searchTerm.equals("") && !searchId.equals("")) {

            Object[] values = {
            		searchPerspective,
            		searchTermWithWildCards,
            		searchEMAPAIdWithWildCards,
               		searchEMAPIdWithWildCards,
            		searchStartStage
            };

        	try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE_AND_PATH_AND_ID, false, values);

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
    private static DerivedPartOfPerspectivesFK mapDerivedPartOfPerspectivesFK(ResultSet resultSet) throws SQLException {
        return new DerivedPartOfPerspectivesFK(
       		resultSet.getString("POP_PERSPECTIVE_FK"), 
       		resultSet.getString("FULL_PATH"), 
       		resultSet.getString("FULL_PATH_JSON"), 
       		resultSet.getInt("POP_IS_ANCESTOR"), 
       		resultSet.getString("EMAPA_PUBLIC_ID"), 
       		resultSet.getString("EMAP_PUBLIC_ID")
        );
    }

}