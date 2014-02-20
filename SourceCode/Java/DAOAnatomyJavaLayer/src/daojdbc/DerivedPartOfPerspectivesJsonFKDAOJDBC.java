/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedPartOfPerspectivesJsonFKDAO.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version:      1
*
* Description:  This class represents a SQL Database Access Object for the DerivedPartOfPerspectivesJsonFK DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the DerivedPartOfPerspectivesJsonFK DTO and a SQL database.
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package daojdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.DerivedPartOfPerspectivesJsonFK;

import daointerface.DerivedPartOfPerspectivesJsonFKDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class DerivedPartOfPerspectivesJsonFKDAOJDBC implements DerivedPartOfPerspectivesJsonFKDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "WHERE POP_PERSPECTIVE_FK LIKE ? ";

    private static final String SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE_AND =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND a.APO_FULL_PATH LIKE ? ";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE =
        "SELECT POP_PERSPECTIVE_FK, CONCAT(a.APO_FULL_PATH_JSON_HEAD, a.APO_FULL_PATH_JSON_TAIL) AS FULL_PATH_JSON  " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_BY_PERSPECTIVE_AND =
        "SELECT POP_PERSPECTIVE_FK, CONCAT(a.APO_FULL_PATH_JSON_HEAD, a.APO_FULL_PATH_JSON_TAIL) AS FULL_PATH_JSON " +
        "FROM ANAD_PART_OF_PERSPECTIVE " +
        "JOIN ANAD_PART_OF a ON a.APO_OID = POP_APO_FK " +
        "WHERE POP_PERSPECTIVE_FK = ? " +
        "AND a.APO_FULL_PATH LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a DerivedPartOf Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public DerivedPartOfPerspectivesJsonFKDAOJDBC() {
    	
    }
    
    public DerivedPartOfPerspectivesJsonFKDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns list of DerivedPartOfs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOfPerspectivesJsonFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchPerspective)
        throws Exception {

    	String sqlSortField = "POP_PERSPECTIVE_FK";
    	
        if (sortField.equals("perspectiveFK")) {
        	sqlSortField = "POP_PERSPECTIVE_FK";      
        }
        if (sortField.equals("fullPathJson")) {
        	sqlSortField = "FULL_PATH_JSON";       
        }
        
        //String searchWithWildCards = "%" + searchTerm + "%";
        String searchWithWildCards = "%" + searchTerm;

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
            
            List<DerivedPartOfPerspectivesJsonFK> dataList = new ArrayList<DerivedPartOfPerspectivesJsonFK>();

            try {
            	
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedPartOfPerspectivesJsonFK(resultSet));
                }
                
            } 
            catch (SQLException e) {
            	
                throw new DAOException(e);
            } 
            finally {
            	
                close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
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
            
            List<DerivedPartOfPerspectivesJsonFK> dataList = new ArrayList<DerivedPartOfPerspectivesJsonFK>();

            try {
            	
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedPartOfPerspectivesJsonFK(resultSet));
                }
                
            } 
            catch (SQLException e) {
            	
                throw new DAOException(e);
            } 
            finally {
            	
                close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
            }

            return dataList;
        }
    }

    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchTerm, String searchPerspective) throws Exception {

        //String searchWithWildCards = "%" + searchTerm + "%";
        String searchWithWildCards = "%" + searchTerm;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        if (searchTerm.equals("")){
        	
            Object[] values = {
            		searchPerspective
            };

            try {
            	
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE, false, values);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                	
                    count = resultSet.getLong("VALUE");
                }
                
            } 
            catch (SQLException e) {
            	
                throw new DAOException(e);
            } 
            finally {
            	
                close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
            }
        	
        }
        else {

            Object[] values = {
            		searchPerspective,
            		searchWithWildCards
            };

        	try {
        		
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_WHERE_BY_PERSPECTIVE_AND, false, values);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                	
                    count = resultSet.getLong("VALUE");
                }
                
            } 
            catch (SQLException e) {
            	
                throw new DAOException(e);
            } 
            finally {
            	
                close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
            }
        }

        return count;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static DerivedPartOfPerspectivesJsonFK mapDerivedPartOfPerspectivesJsonFK(ResultSet resultSet) throws SQLException {
    	
        return new DerivedPartOfPerspectivesJsonFK(
       		resultSet.getString("POP_PERSPECTIVE_FK"), 
       		resultSet.getString("FULL_PATH_JSON")
        );
    }
}
