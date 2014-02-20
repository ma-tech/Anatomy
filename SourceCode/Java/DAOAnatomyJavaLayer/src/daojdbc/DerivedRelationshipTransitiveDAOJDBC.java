/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedRelationshipTransitiveDAO.java
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
* Description:  This class represents a SQL Database Access Object for the DerivedRelationshipTransitive DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the DerivedRelationshipTransitive DTO and a SQL database.
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

import daomodel.DerivedRelationshipTransitive;

import daointerface.DerivedRelationshipTransitiveDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class DerivedRelationshipTransitiveDAOJDBC implements DerivedRelationshipTransitiveDAO {
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
    /*
     * Construct a DerivedRelationshipTransitive Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public DerivedRelationshipTransitiveDAOJDBC() {
    	
    }
    
    public DerivedRelationshipTransitiveDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns a list of relationship matching the given Node FK, otherwise null.
     */
    public List<DerivedRelationshipTransitive> listAllByDescendantFK(String descendantFK) throws Exception {
    	
        return list(SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE, descendantFK);
    }
    
    /*
     * Returns a list of all derivedrelationshiptransitives from the database. 
     *  The list is never null and is empty when the database does not contain any derivedrelationshiptransitives.
     */
    public List<DerivedRelationshipTransitive> list(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<DerivedRelationshipTransitive> derivedrelationshiptransitives = new ArrayList<DerivedRelationshipTransitive>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	
                derivedrelationshiptransitives.add(mapDerivedRelationshipTransitive(resultSet));
                
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return derivedrelationshiptransitives;
    }

    /*
     * Returns list of DerivedRelationshipTransitives for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedRelationshipTransitive> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception {

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

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedRelationshipTransitive(resultSet));
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

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedRelationshipTransitive(resultSet));
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
    public long count(String searchTerm) throws Exception {

        String searchWithWildCards = "%" + searchTerm + "%";

        Object[] values = {
        		searchWithWildCards
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        try {
        	
            connection = daoFactory.getConnection();

            if (searchTerm.equals("")){
            	
                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false);
            }
            else {
            	
                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_WHERE, false, values);
            }

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

        return count;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
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
