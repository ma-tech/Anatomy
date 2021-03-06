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

import utility.Wrapper;

import daomodel.DerivedPartOf;

import daointerface.DerivedPartOfDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;


public final class DerivedPartOfDAOJDBC implements DerivedPartOfDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_NODE_FK LIKE ? " +
        "AND APO_FULL_PATH LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF ";

     private static final String SQL_ROW_COUNT_WHERE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_NODE_FK LIKE ? " +
        "AND APO_FULL_PATH LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_OID = ?";
    
    private static final String SQL_FIND_BY_OID_PATH =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_FULL_PATH_OIDS = ? ";
        
    private static final String SQL_LIST_ALL_BY_OID_IN_PATH =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_FULL_PATH_OIDS LIKE ? " +
        "ORDER BY APO_FULL_PATH_OIDS ";

    private static final String SQL_LIST_ALL_BY_STAGE_SEQUENCE =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "JOIN ANA_STAGE a ON APO_NODE_START_STAGE_FK = a.STG_OID " +
        "JOIN ANA_STAGE b ON APO_NODE_END_STAGE_FK = b.STG_OID " +
        "WHERE a.STG_SEQUENCE <= ? " +
        "AND b.STG_SEQUENCE >= ? " +
        "ORDER BY APO_FULL_PATH ";

    private static final String SQL_FIND_BY_NODE_FK_IN_PRIMARY_PATH =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_NODE_FK = ? " +
        "AND APO_IS_PRIMARY_PATH IS TRUE ";

    private static final String SQL_FIND_BY_NODE_FK =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_NODE_FK = ? ";

    private static final String SQL_LIST_ALL =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANAD_PART_OF " +
        "(APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK ) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANAD_PART_OF SET " +
        "APO_SPECIES_FK = ?, " +
        "APO_NODE_START_STAGE_FK = ?, " + 
        "APO_NODE_END_STAGE_FK = ?, " +
        "APO_PATH_START_STAGE_FK = ?, " + 
        "APO_PATH_END_STAGE_FK = ?, " +
        "APO_NODE_FK = ?, " + 
        "APO_SEQUENCE = ?, " +
        "APO_DEPTH = ?, " + 
        "APO_FULL_PATH = ?, " +
        "APO_FULL_PATH_OIDS = ?, " + 
        "APO_FULL_PATH_EMAPAS = ?, " + 
        "APO_FULL_PATH_JSON_HEAD = ?, " +
        "APO_FULL_PATH_JSON_TAIL = ?, " + 
        "APO_IS_PRIMARY = ?, " + 
        "APO_IS_PRIMARY_PATH = ?, " +
        "APO_PARENT_APO_FK = ? " + 
        "WHERE APO_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANAD_PART_OF " +
        "WHERE APO_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT APO_OID " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_OID = ?";

    private static final String SQL_LIST_BY_NODE_FK =
        "SELECT APO_OID, APO_SPECIES_FK, APO_NODE_START_STAGE_FK, APO_NODE_END_STAGE_FK, APO_PATH_START_STAGE_FK, APO_PATH_END_STAGE_FK, APO_NODE_FK, APO_SEQUENCE, APO_DEPTH, APO_FULL_PATH, APO_FULL_PATH_OIDS, APO_FULL_PATH_EMAPAS, APO_FULL_PATH_JSON_HEAD, APO_FULL_PATH_JSON_TAIL, APO_IS_PRIMARY, APO_IS_PRIMARY_PATH, APO_PARENT_APO_FK  " +
        "FROM ANAD_PART_OF " +
        "WHERE APO_NODE_FK = ?";
        
    private static final String SQL_EMPTY =
        "DELETE FROM ANAD_PART_OF";


    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a DerivedPartOf Data Access Object for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public DerivedPartOfDAOJDBC() {
    	
    }
    
    public DerivedPartOfDAOJDBC(DAOFactory daoFactory) {
       
    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the DerivedPartOf from the database matching the given OID, otherwise null.
     */
    public DerivedPartOf findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    
    /*
     * Returns the DerivedPartOf from the database matching the given OID, otherwise null.
     */
    public DerivedPartOf findByNodeFKInPrimaryPath(long nodeFK) throws Exception {
    	
        return find(SQL_FIND_BY_NODE_FK_IN_PRIMARY_PATH, nodeFK);
    }
    
    
    /*
     * Returns the DerivedPartOf from the database matching the given OID, otherwise null.
     */
    public DerivedPartOf findByNodeFK(long nodeFK) throws Exception {
    	
        return find(SQL_FIND_BY_NODE_FK, nodeFK);
    }
    
    
    /*
     * Returns the DerivedPartOf from the database matching the given Pathway , otherwise null.
     */
    public DerivedPartOf findByOidPath(String path) throws Exception {
    	
        return find(SQL_FIND_BY_OID_PATH, path);
    }
    

    /*
     * Returns a list of ALL derivedpartofs, otherwise null.
     */
    public List<DerivedPartOf> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given derivedpartof OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given derivedpartof in the database.
     * 
     *  If the DerivedPartOf OID is null, 
     *   then it will invoke "create(DerivedPartOf)", 
     *   else it will invoke "update(DerivedPartOf)".
     */
    public void save(DerivedPartOf derivedpartof) throws Exception {
     
    	if (derivedpartof.getOid() == null) {
    		
            create(derivedpartof);
        }
    	else {
    		
            update(derivedpartof);
        }
    }
    
    /*
     * Returns the derivedpartof from the database matching the given 
     *  SQL query with the given values.
     */
    private DerivedPartOf find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DerivedPartOf derivedpartof = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                derivedpartof = mapDerivedPartOf(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return derivedpartof;
    }
    
    /*
     * Returns a list of relationship matching the given Node FK, otherwise null.
     */
    public List<DerivedPartOf> listAllByNodeFK(String nodeFK) throws Exception {
    	
        return list(SQL_LIST_BY_NODE_FK, nodeFK);
    }
    
    /*
     * Returns a list of all derivedpartofs from the database. 
     *  The list is never null and is empty when the database does not contain any derivedpartofs.
     */
    public List<DerivedPartOf> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<DerivedPartOf> derivedpartofs = new ArrayList<DerivedPartOf>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                derivedpartofs.add(mapDerivedPartOf(resultSet));
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
     * Create the given derivedpartof in the database. 
     * 
     *  The derivedpartof OID must be null, otherwise it will throw IllegalArgumentException.
     *   If the derivedpartof OID value is unknown, rather use save(DerivedPartOf).
     *    After creating, the Data Access Object will set the obtained ID in the given derivedpartof.
     */
    public void create(DerivedPartOf derivedpartof) throws IllegalArgumentException, Exception {
    	
    	Object[] values = {
    		derivedpartof.getOid(),
    		derivedpartof.getSpeciesFK(),
    		derivedpartof.getNodeStartFK(),
   			derivedpartof.getNodeStopFK(),
   			derivedpartof.getPathStartFK(),
   			derivedpartof.getPathStopFK(),
   			derivedpartof.getNodeFK(),
    		derivedpartof.getSequence(),
    		derivedpartof.getDepth(),
    		derivedpartof.getFullPath(),
   			derivedpartof.getFullPathOids(),
   			derivedpartof.getFullPathEmapas(),
   			derivedpartof.getFullPathJsonHead(),
    		derivedpartof.getFullPathJsonTail(),
    		derivedpartof.isPrimary(),
    		derivedpartof.isPrimaryPath(),
    		derivedpartof.getParentFK()
    	};

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Creating DerivedPartOf failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANAD_PART_OF Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, generatedKeys);
        }
    }
    
    /*
     * Update the given derivedpartof in the database.
     * 
     *  The derivedpartof OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the derivedpartof OID value is unknown, rather use save(DerivedPartOf)}.
     */
    public void update(DerivedPartOf derivedpartof) throws Exception {
    	
        if (derivedpartof.getOid() == null) {
        	
            throw new IllegalArgumentException("DerivedPartOf is not created yet, so the derivedpartof OID cannot be null.");
        }

    	Object[] values = {
        	derivedpartof.getSpeciesFK(),
       		derivedpartof.getNodeStartFK(),
      		derivedpartof.getNodeStopFK(),
       		derivedpartof.getPathStartFK(),
       		derivedpartof.getPathStopFK(),
      		derivedpartof.getNodeFK(),
       		derivedpartof.getSequence(),
       		derivedpartof.getDepth(),
       		derivedpartof.getFullPath(),
       		derivedpartof.getFullPathOids(),
       		derivedpartof.getFullPathEmapas(),
      		derivedpartof.getFullPathJsonHead(),
       		derivedpartof.getFullPathJsonTail(),
       		derivedpartof.isPrimary(),
       		derivedpartof.isPrimaryPath(),
        	derivedpartof.getParentFK(),
        	derivedpartof.getOid(),
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating DerivedPartOf failed, no rows affected.");
                } 
                else {
                	
                	derivedpartof.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANAD_PART_OF Skipped", "***", daoFactory.getMsgLevel());
            }
            
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(),connection, preparedStatement);
        }
    }
     
    /*
     * Delete the given derivedpartof from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given derivedpartof to null.
     */
    public void delete(DerivedPartOf derivedpartof) throws Exception {
    	
        Object[] values = { 
        	derivedpartof.getOid() 
        };

        if (derivedpartof.getOid() == null) {
        	
            throw new IllegalArgumentException("DerivedPartOf is not created yet, so the derivedpartof OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting derivedpartof failed, no rows affected.");
                } 
                else {
                	
                	derivedpartof.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANAD_PART_OF Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(),connection, preparedStatement);
        }
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
    public List<DerivedPartOf> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

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
    	if (sortField.equals("fullPathOids")) {
        	sqlSortField = "APO_FULL_PATH_OIDS";       
        }
    	if (sortField.equals("fullPathEmapas")) {
        	sqlSortField = "APO_FULL_PATH_EMAPAS";       
        }
        if (sortField.equals("fullPathJsonHead")) {
        	sqlSortField = "APO_FULL_PATH_JSON_HEAD";      
        }
        if (sortField.equals("fullPathJsonTail")) {
        	sqlSortField = "APO_FULL_PATH_JSON_TAIL";         
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
        
        if (searchFirst.equals("")) {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
    	}
        else {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
        }

        if (searchSecond.equals("")) {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
    	}
        else {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
        }
        
        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        if (searchFirst.equals("")){

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

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedPartOf(resultSet));
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
                	searchFirstWithWildCards, 
                	searchSecondWithWildCards,
                    firstRow, 
                    rowCount
                };

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<DerivedPartOf> dataList = new ArrayList<DerivedPartOf>();

            try {
            	
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                	
                    dataList.add(mapDerivedPartOf(resultSet));
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
     * Returns list of DerivedPartOfs for a given OID in the Pathway
     */
    public List<DerivedPartOf> listByOidInPath(long oid)
        throws Exception {
    	
        String searchPath = "%" + oid + ".%";

        return list(SQL_LIST_ALL_BY_OID_IN_PATH, searchPath);
    }

    
    /*
     * Returns list of DerivedPartOfs for a range of Stage Sequences
     */
    public List<DerivedPartOf> listAllByStageSequence(long stageStart, long stageEnd)
        throws Exception {
    	
        return list(SQL_LIST_ALL_BY_STAGE_SEQUENCE, stageStart, stageEnd);
    }

    
    /*
     * Returns total amount of rows in table.
     */
    public long count(String searchFirst, String searchSecond) throws Exception {

        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        if (searchFirst.equals("")) {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
    	}
        else {
        	searchFirstWithWildCards = "%" + searchFirst + "%";
        }

        if (searchSecond.equals("")) {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
    	}
        else {
        	searchSecondWithWildCards = "%" + searchSecond + "%";
        }
        
        Object[] values = {
        	searchFirstWithWildCards, 
        	searchSecondWithWildCards
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            
            if (searchFirst.equals("")){
            	
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

    
    /*
     * Returns total amount of rows in table.
     */
    public long countAll() throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false);

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

    
    /*
     *  Empty the ANAD_PART_OF Table from the database. 
     */
    public void empty() throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_EMPTY, false);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting ALL ANAD_PART_OF failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANAD_PART_OF Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(),connection, preparedStatement);
        }
    }
    

    // Helpers ------------------------------------------------------------------------------------
    /*
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
      		resultSet.getString("APO_FULL_PATH_EMAPAS"), 
       		resultSet.getString("APO_FULL_PATH_JSON_HEAD"), 
       		resultSet.getString("APO_FULL_PATH_JSON_TAIL"), 
       		resultSet.getBoolean("APO_IS_PRIMARY"),
       		resultSet.getBoolean("APO_IS_PRIMARY_PATH"),
       		resultSet.getLong("APO_PARENT_APO_FK")
        );
    }
}