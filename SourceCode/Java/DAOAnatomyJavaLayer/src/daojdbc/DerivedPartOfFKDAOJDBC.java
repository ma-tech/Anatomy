/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        DerivedPartOfDAO.java
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
* Description:  This class represents a SQL Database Access Object for the DerivedPartOf DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the DerivedPartOf DTO and a SQL database.
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

import daomodel.DerivedPartOfFK;

import daointerface.DerivedPartOfFKDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class DerivedPartOfFKDAOJDBC implements DerivedPartOfFKDAO{
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT APO_OID, APO_SPECIES_FK, " +
        "a.STG_NAME AS NODE_START_STAGE, b.STG_NAME AS NODE_END_STAGE, " +
        "c.STG_NAME AS PATH_START_STAGE, d.STG_NAME AS PATH_END_STAGE, " +
        "e.ANO_PUBLIC_ID AS NODE_ID, " +
        "APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_FULL_PATH_OIDS, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK " +
        "FROM ANAD_PART_OF " +
        "JOIN ANA_STAGE a on a.STG_OID = APO_NODE_START_STAGE_FK " +
        "JOIN ANA_STAGE b on b.STG_OID = APO_NODE_END_STAGE_FK " +
        "JOIN ANA_STAGE c on c.STG_OID = APO_PATH_START_STAGE_FK " +
        "JOIN ANA_STAGE d on d.STG_OID = APO_PATH_END_STAGE_FK " +
        "JOIN ANA_NODE e on e.ANO_OID = APO_NODE_FK " +
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
        "SELECT APO_OID, APO_SPECIES_FK, " +
        "a.STG_NAME AS NODE_START_STAGE, b.STG_NAME AS NODE_END_STAGE, " +
        "c.STG_NAME AS PATH_START_STAGE, d.STG_NAME AS PATH_END_STAGE, " +
        "e.ANO_PUBLIC_ID AS NODE_ID, " +
        "APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_FULL_PATH_OIDS, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK " +
        "FROM ANAD_PART_OF " +
        "JOIN ANA_STAGE a on a.STG_OID = APO_NODE_START_STAGE_FK " +
        "JOIN ANA_STAGE b on b.STG_OID = APO_NODE_END_STAGE_FK " +
        "JOIN ANA_STAGE c on c.STG_OID = APO_PATH_START_STAGE_FK " +
        "JOIN ANA_STAGE d on d.STG_OID = APO_PATH_END_STAGE_FK " +
        "JOIN ANA_NODE e on e.ANO_OID = APO_NODE_FK " +
        "WHERE APO_FULL_PATH LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_FIND_BY_OID =
        "SELECT APO_OID, APO_SPECIES_FK, " +
        "a.STG_NAME AS NODE_START_STAGE, b.STG_NAME AS NODE_END_STAGE, " +
        "c.STG_NAME AS PATH_START_STAGE, d.STG_NAME AS PATH_END_STAGE, " +
        "e.ANO_PUBLIC_ID AS NODE_ID, " +
        "APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_FULL_PATH_OIDS, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK " +
        "FROM ANAD_PART_OF " +
        "JOIN ANA_STAGE a on a.STG_OID = APO_NODE_START_STAGE_FK " +
        "JOIN ANA_STAGE b on b.STG_OID = APO_NODE_END_STAGE_FK " +
        "JOIN ANA_STAGE c on c.STG_OID = APO_PATH_START_STAGE_FK " +
        "JOIN ANA_STAGE d on d.STG_OID = APO_PATH_END_STAGE_FK " +
        "JOIN ANA_NODE e on e.ANO_OID = APO_NODE_FK " +
        "WHERE APO_OID = ?";
    
    private static final String SQL_LIST_BY_NODE_FK =
    	"SELECT APO_OID, APO_SPECIES_FK, " +
        "a.STG_NAME AS NODE_START_STAGE, b.STG_NAME AS NODE_END_STAGE, " +
        "c.STG_NAME AS PATH_START_STAGE, d.STG_NAME AS PATH_END_STAGE, " +
        "e.ANO_PUBLIC_ID AS NODE_ID, " +
        "APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_FULL_PATH_OIDS, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK " +
        "FROM ANAD_PART_OF " +
        "JOIN ANA_STAGE a on a.STG_OID = APO_NODE_START_STAGE_FK " +
        "JOIN ANA_STAGE b on b.STG_OID = APO_NODE_END_STAGE_FK " +
        "JOIN ANA_STAGE c on c.STG_OID = APO_PATH_START_STAGE_FK " +
        "JOIN ANA_STAGE d on d.STG_OID = APO_PATH_END_STAGE_FK " +
        "JOIN ANA_NODE e on e.ANO_OID = APO_NODE_FK " +
        "WHERE APO_NODE_FK = ?";
    
    private static final String SQL_EXIST_OID =
        "SELECT APO_OID " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_OID = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a DerivedPartOf Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public DerivedPartOfFKDAOJDBC() {
    	
    }
    
    public DerivedPartOfFKDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the relationship from the database matching the given OID, otherwise null.
     */
    public DerivedPartOfFK findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }

    /*
     * Returns the relationship from the database matching the given 
     *  SQL query with the given values.
     */
    private DerivedPartOfFK find(String sql, Object... values) throws Exception {

    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DerivedPartOfFK relationship = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
                relationship = mapDerivedPartOfFK(resultSet);
                
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return relationship;
    }

    /*
     * Returns a list of relationship matching the given Node FK, otherwise null.
     */
    public List<DerivedPartOfFK> listAllByNodeFK(String nodeFK) throws Exception {
    	
        return list(SQL_LIST_BY_NODE_FK, nodeFK);
    }
    
    /*
     * Returns a list of all derivedpartofs from the database. 
     *  The list is never null and is empty when the database does not contain any derivedpartofs.
     */
    public List<DerivedPartOfFK> list(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<DerivedPartOfFK> derivedpartofs = new ArrayList<DerivedPartOfFK>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	
                derivedpartofs.add(mapDerivedPartOfFK(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return derivedpartofs;
    }

    
    /*
     * Returns true if the given relationship OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Returns true if the given SQL query with the given values returns at least one row.
     */
    private boolean exist(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return exist;
    }

    /*
     * Returns list of DerivedPartOfs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<DerivedPartOfFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception {

    	String sqlSortField = "APO_OID";
    	
        if (sortField.equals("oid")) {
        	sqlSortField = "APO_OID";       
        }
        if (sortField.equals("speciesFK")) {
        	sqlSortField = "APO_SPECIES_FK";      
        }
        if (sortField.equals("nodeStart")) {
        	sqlSortField = "NODE_START_STAGE";         
        }
        if (sortField.equals("nodeStop")) {
        	sqlSortField = "NODE_END_STAGE";         
        }
        if (sortField.equals("pathStart")) {
        	sqlSortField = "PATH_START_STAGE";       
        }
        if (sortField.equals("pathStop")) {
        	sqlSortField = "PATH_END_STAGE";       
        }
        if (sortField.equals("node")) {
        	sqlSortField = "NODE_ID";      
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
        
        //String searchWithWildCards = "%" + searchTerm + "%";
        String searchWithWildCards = "%" + searchTerm;

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
            
            List<DerivedPartOfFK> dataList = new ArrayList<DerivedPartOfFK>();

            try {
            	
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedPartOfFK(resultSet));
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
            
            List<DerivedPartOfFK> dataList = new ArrayList<DerivedPartOfFK>();

            try {
            	
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedPartOfFK(resultSet));
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

        //String searchWithWildCards = "%" + searchTerm + "%";
        String searchWithWildCards = "%" + searchTerm;

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
    private static DerivedPartOfFK mapDerivedPartOfFK(ResultSet resultSet) throws SQLException {
    	
        return new DerivedPartOfFK(
      		resultSet.getLong("APO_OID"), 
       		resultSet.getString("APO_SPECIES_FK"), 
       		resultSet.getString("NODE_START_STAGE"), 
       		resultSet.getString("NODE_END_STAGE"), 
       		resultSet.getString("PATH_START_STAGE"), 
       		resultSet.getString("PATH_END_STAGE"), 
       		resultSet.getString("NODE_ID"), 
       		resultSet.getLong("APO_SEQUENCE"), 
       		resultSet.getLong("APO_DEPTH"), 
       		resultSet.getString("APO_FULL_PATH"), 
       		resultSet.getString("APO_FULL_PATH_OIDS"), 
       		resultSet.getString("APO_FULL_PATH_JSON_HEAD"), 
       		resultSet.getString("APO_FULL_PATH_JSON_TAIL"), 
       		resultSet.getBoolean("APO_IS_PRIMARY"), 
       		resultSet.getBoolean("APO_IS_PRIMARY_PATH"), 
       		resultSet.getLong("APO_PARENT_APO_FK")
        );
    }
}
