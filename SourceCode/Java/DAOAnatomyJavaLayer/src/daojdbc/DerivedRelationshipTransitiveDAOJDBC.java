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

import utility.Wrapper;
import daomodel.DerivedPartOf;
import daomodel.DerivedRelationshipTransitive;
import daointerface.DerivedRelationshipTransitiveDAO;
import daolayer.DAOFactory;
import daolayer.DAOException;
import static daolayer.DAOUtil.*;

public final class DerivedRelationshipTransitiveDAOJDBC implements DerivedRelationshipTransitiveDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_INSERT =
        "INSERT INTO ANAD_RELATIONSHIP_TRANSITIVE " +
        "( RTR_OID, RTR_RELATIONSHIP_TYPE_FK, RTR_ANCESTOR_FK, RTR_DESCENDENT_FK ) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANAD_RELATIONSHIP_TRANSITIVE SET " +
        "RTR_RELATIONSHIP_TYPE_FK = ?, " +
        "RTR_ANCESTOR_FK = ?, " + 
        "RTR_DESCENDENT_FK = ? " + 
        "WHERE RTR_OID = ?";
        
    private static final String SQL_DELETE =
    	"DELETE FROM ANAD_RELATIONSHIP_TRANSITIVE " +
    	"WHERE RTR_OID = ?";

    private static final String SQL_EXIST_OID =
    	"SELECT RTR_OID " +
    	"FROM ANAD_RELATIONSHIP_TRANSITIVE " +
    	"WHERE RTR_OID = ?";

    private static final String SQL_LIST_ALL =
	    "SELECT RTR_OID, RTR_RELATIONSHIP_TYPE_FK, RTR_ANCESTOR_FK, RTR_DESCENDENT_FK " +
	    "FROM ANAD_RELATIONSHIP_TRANSITIVE ";

    private static final String SQL_FIND_BY_OID =
        "SELECT RTR_OID, RTR_RELATIONSHIP_TYPE_FK, RTR_ANCESTOR_FK, RTR_DESCENDENT_FK " +
        "FROM ANAD_RELATIONSHIP_TRANSITIVE " +
        "WHERE RTR_OID = ?";

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

    private static final String SQL_EMPTY =
        "DELETE FROM ANAD_RELATIONSHIP_TRANSITIVE";


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
     *  Empty the ANAD_RELATIONSHIP_TRANSITIVE Table from the database. 
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
                	
                    throw new DAOException("Deleting ALL ANAD_RELATIONSHIP_TRANSITIVE failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANAD_RELATIONSHIP_TRANSITIVE Skipped", "***", daoFactory.getMsgLevel());
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
    private static DerivedRelationshipTransitive mapDerivedRelationshipTransitive(ResultSet resultSet) throws SQLException {
    	
        return new DerivedRelationshipTransitive(
      		resultSet.getLong("RTR_OID"), 
       		resultSet.getString("RTR_RELATIONSHIP_TYPE_FK"), 
       		resultSet.getLong("RTR_DESCENDENT_FK"), 
       		resultSet.getLong("RTR_ANCESTOR_FK") 
        );
    }

    /*
     * Returns the DerivedRelationshipTransitive from the database matching the given OID, otherwise null.
     */
    public DerivedRelationshipTransitive findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL DerivedRelationshipTransitive, otherwise null.
     */
    public List<DerivedRelationshipTransitive> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given DerivedRelationshipTransitive OID exists in the database.
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
     * Save the given derivedpartof in the database.
     * 
     *  If the DerivedPartOf OID is null, 
     *   then it will invoke "create(DerivedPartOf)", 
     *   else it will invoke "update(DerivedPartOf)".
     */
    public void save(DerivedRelationshipTransitive derivedrelationshiptransitive) throws Exception {
     
    	if (derivedrelationshiptransitive.getOid() == null) {
    		
            create(derivedrelationshiptransitive);
        }
    	else {
    		
            update(derivedrelationshiptransitive);
        }
    }
    
    /*
     * Returns the derivedrelationshiptransitive from the database matching the given 
     *  SQL query with the given values.
     */
    private DerivedRelationshipTransitive find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DerivedRelationshipTransitive derivedrelationshiptransitive = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
            	derivedrelationshiptransitive = mapDerivedRelationshipTransitive(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return derivedrelationshiptransitive;
    }
    

    /*
     * Create the given derivedrelationshiptransitive in the database. 
     * 
     *  The derivedrelationshiptransitive OID must be null, otherwise it will throw IllegalArgumentException.
     *   If the derivedrelationshiptransitive OID value is unknown, rather use save(derivedrelationshiptransitive).
     *    After creating, the Data Access Object will set the obtained ID in the given derivedrelationshiptransitive.
     */
    public void create(DerivedRelationshipTransitive derivedrelationshiptransitive) throws IllegalArgumentException, Exception {
    	
    	Object[] values = {
    		derivedrelationshiptransitive.getOid(),
    		derivedrelationshiptransitive.getRelTypeFK(),
    		derivedrelationshiptransitive.getAncestorFK(),
    		derivedrelationshiptransitive.getDescendantFK()
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
                	
                    throw new DAOException("Creating DerivedRelationshipTransitive failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANAD_RELATIONSHIP_TRANSITIVE Skipped", "***", daoFactory.getMsgLevel());
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
    public void update(DerivedRelationshipTransitive derivedrelationshiptransitive) throws Exception {
    	
        if (derivedrelationshiptransitive.getOid() == null) {
        	
            throw new IllegalArgumentException("DerivedPartOf is not created yet, so the derivedpartof OID cannot be null.");
        }

    	Object[] values = {
        		derivedrelationshiptransitive.getRelTypeFK(),
        		derivedrelationshiptransitive.getAncestorFK(),
        		derivedrelationshiptransitive.getDescendantFK(),
        		derivedrelationshiptransitive.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating derivedrelationshiptransitive failed, no rows affected.");
                } 
                else {
                	
                	derivedrelationshiptransitive.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANAD_RELATIONSHIP_TRANSITIVE Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of DerivedRelationshipTransitive for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
	public List<DerivedRelationshipTransitive> display(int firstRow,
			int rowCount, String sortField, boolean sortAscending,
			String searchFirst, String searchSecond) throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        String sqlSortField = "RTR_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "RTR_OID";       
        }
        if (sortField.equals("speciesFK")) {
        	sqlSortField = "RTR_RELATIONSHIP_TYPE_FK";      
        }
        if (sortField.equals("nodeStartFK")) {
        	sqlSortField = "RTR_ANCESTOR_FK";         
        }
        if (sortField.equals("nodeStopFK")) {
        	sqlSortField = "RTR_DESCENDENT_FK";      
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
                	searchFirstWithWildCards, 
                	searchSecondWithWildCards,
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
     * Delete the given derivedpartof from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given derivedpartof to null.
     */
	public void delete(
			DerivedRelationshipTransitive derivedrelationshiptransitive)
			throws Exception {
    	
        Object[] values = { 
        		derivedrelationshiptransitive.getOid() 
        };

        if (derivedrelationshiptransitive.getOid() == null) {
        	
            throw new IllegalArgumentException("DerivedRelationshipTransitive is not created yet, so the derivedrelationshiptransitive OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting derivedrelationshiptransitive failed, no rows affected.");
                } 
                else {
                	
                	derivedrelationshiptransitive.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANAD_RELATIONSHIP_TRANSITIVE Skipped", "***", daoFactory.getMsgLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(),connection, preparedStatement);
        }
    }
    
}
