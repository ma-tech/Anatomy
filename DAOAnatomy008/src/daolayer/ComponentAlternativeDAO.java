/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        ComponentAlternativeDAO.java
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
* Version: 1
*
* Description:  This class represents a SQL Database Access Object for the ComponentAlternative DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the ComponentAlternative DTO and a SQL database.
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package daolayer;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.ComponentAlternative;

public final class ComponentAlternativeDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ACA_OID, ACA_OBO_ID, ACA_OBO_ALT_ID  " +
        "FROM ANA_OBO_COMPONENT_ALTERNATIVE " +
        "WHERE ACA_OBO_ALT_ID LIKE ? " +
        "AND ACA_OBO_ID LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_ALTERNATIVE " +
        "WHERE ACA_OBO_ALT_ID LIKE ? " +
        "AND ACA_OBO_ID LIKE ? ";

    private static final String SQL_ROW_COUNT_ALL =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_ALTERNATIVE ";

    private static final String SQL_FIND_BY_OID =
        "SELECT ACA_OID, ACA_OBO_ID, ACA_OBO_ALT_ID  " +
        "FROM ANA_OBO_COMPONENT_ALTERNATIVE " +
        "WHERE ACA_OID = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT ACA_OID, ACA_OBO_ID, ACA_OBO_ALT_ID " +
        "FROM ANA_OBO_COMPONENT_ALTERNATIVE ";
    
    private static final String SQL_LIST_ALL_BY_OBO_ID =
        "SELECT ACA_OID, ACA_OBO_ID, ACA_OBO_ALT_ID " +
        "FROM ANA_OBO_COMPONENT_ALTERNATIVE " +
        "WHERE ACA_OBO_ID = ? ";
            
    private static final String SQL_LIST_ALL_BY_ALT_ID =
        "SELECT ACA_OID, ACA_OBO_ID, ACA_OBO_ALT_ID " +
        "FROM ANA_OBO_COMPONENT_ALTERNATIVE " +
        "WHERE ACA_OBO_ALT_ID = ? ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBO_COMPONENT_ALTERNATIVE " +
        "(ACA_OBO_ID, ACA_OBO_ALT_ID) " +
        "VALUES (?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBO_COMPONENT_ALTERNATIVE SET " +
        "ACA_OBO_ID = ?, " +
        "ACA_OBO_ALT_ID = ? " + 
        "WHERE ACA_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBO_COMPONENT_ALTERNATIVE " +
        "WHERE ACA_OID = ?";

    private static final String SQL_EMPTY =
        "DELETE FROM ANA_OBO_COMPONENT_ALTERNATIVE";

    private static final String SQL_EXIST_OID =
        "SELECT ACA_OID " +
        "FROM ANA_OBO_COMPONENT_ALTERNATIVE " +
        "WHERE ACA_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a ComponentAlternative DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    ComponentAlternativeDAO(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the daocomponentsynonym from the database matching the given OID, otherwise null.
     */
    public ComponentAlternative findByOid(Long oid) throws DAOException {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the daocomponentsynonyms from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentAlternative> listByOboId(String oboid) throws DAOException {
    	
        return list(SQL_LIST_ALL_BY_OBO_ID, oboid);
    }
    
    /*
     * Returns the daocomponentsynonyms from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentAlternative> listByAltId(String text) throws DAOException {
    	
        return list(SQL_LIST_ALL_BY_ALT_ID, text);
    }
    
    /*
     * Returns a list of ALL componentsynonyms, otherwise null.
     */
    public List<ComponentAlternative> listAll() throws DAOException {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given daocomponentsynonym OID exists in the database.
     */
    public boolean existOid(String oid) throws DAOException {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given daocomponentsynonym in the database.
     * 
     *  If the ComponentAlternative OID is null, 
     *   then it will invoke "create(ComponentAlternative)", 
     *   else it will invoke "update(ComponentAlternative)".
     */
    public void save(ComponentAlternative daocomponentsynonym) throws DAOException {
     
    	if (daocomponentsynonym.getOid() == null) {
            create(daocomponentsynonym);
        }
    	else {
            update(daocomponentsynonym);
        }
    }

    /*
     * Returns the daocomponentsynonym from the database matching the given 
     *  SQL query with the given values.
     */
    private ComponentAlternative find(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ComponentAlternative daocomponentsynonym = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
                daocomponentsynonym = mapComponentAlternative(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return daocomponentsynonym;
    }
    
    /*
     * Returns a list of all componentsynonyms from the database. 
     *  The list is never null and is empty when the database does not contain any componentsynonyms.
     */
    public List<ComponentAlternative> list(String sql, Object... values) throws DAOException {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ComponentAlternative> componentsynonyms = new ArrayList<ComponentAlternative>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                componentsynonyms.add(mapComponentAlternative(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return componentsynonyms;
    }
    
    /*
     * Create the given daocomponentsynonym in the database. 
     * 
     *  The daocomponentsynonym OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentsynonym OID value is unknown, rather use save(ComponentAlternative).
     *   After creating, the DAO will set the obtained ID in the given daocomponentsynonym.
     */
    public void create(ComponentAlternative daocomponentsynonym) throws IllegalArgumentException, DAOException {

    	Object[] values = {
        	daocomponentsynonym.getId(),
            daocomponentsynonym.getAltId()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            
            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Creating ComponentAlternative failed, no rows affected.");
                } 
            }
            else {
            	System.out.println("UPDATE: Create ANA_OBO_COMPONENT_ALTERNATIVE Skipped");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }
    
    /*
     * Update the given daocomponentsynonym in the database.
     *  The daocomponentsynonym OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentsynonym OID value is unknown, rather use save(ComponentAlternative)}.
     */
    public void update(ComponentAlternative daocomponentsynonym) throws DAOException {
    	
        if (daocomponentsynonym.getOid() == null) {
            throw new IllegalArgumentException("ComponentAlternative is not created yet, so the daocomponentsynonym OID cannot be null.");
        }

        Object[] values = {
            daocomponentsynonym.getId(),
            daocomponentsynonym.getAltId(),
           	daocomponentsynonym.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Updating ComponentAlternative failed, no rows affected.");
                } 
                else {
                	daocomponentsynonym.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Update ANA_OBO_COMPONENT_ALTERNATIVE Skipped");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
     
    /*
     *  Delete the given daocomponentsynonym from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentsynonym to null.
     */
    public void delete(ComponentAlternative daocomponentsynonym) throws DAOException {
    	
        Object[] values = { 
        	daocomponentsynonym.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Deleting ComponentAlternative failed, no rows affected.");
                } 
                else {
                	daocomponentsynonym.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Delete ANA_OBO_COMPONENT_ALTERNATIVE Skipped");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
    
    /*
     *  Delete the given daocomponentsynonym from the database. 
     *  After deleting, the DAO will set the ID of the given daocomponentsynonym to null.
     */
    public void empty() throws DAOException {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_EMPTY, false);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Deleting ALL ComponentAlternatives failed, no rows affected.");
                } 
            }
            else {
            	System.out.println("UPDATE: Delete ALL ANA_OBO_COMPONENT_ALTERNATIVE Skipped");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
    
    /*
     * Returns true if the given SQL query with the given values returns at least one row.
     */
    private boolean exist(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
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

    /*
     * Returns list of ComponentAlternatives for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentAlternative> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws DAOException {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        String sqlSortField = "ACA_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "ACA_OID";       
        }
        if (sortField.equals("id")) {
        	sqlSortField = "ACA_OBO_ID";         
        }
        if (sortField.equals("text")) {
        	sqlSortField = "ACA_OBO_ALT_ID";         
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
        
        Object[] values = {
       		searchFirstWithWildCards, 
       		searchSecondWithWildCards,
            firstRow, 
            rowCount
        };

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<ComponentAlternative> dataList = new ArrayList<ComponentAlternative>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapComponentAlternative(resultSet));
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
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws DAOException {

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
        int count = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

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

    /*
     * Returns total amount of rows in table.
     */
    public int countAll() throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_ALL, false);

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
    /*
     * Map the current row of the given ResultSet to a ComponentAlternative.
     */
    private static ComponentAlternative mapComponentAlternative(ResultSet resultSet) throws SQLException {

    	return new ComponentAlternative(
      		resultSet.getLong("ACA_OID"), 
       		resultSet.getString("ACA_OBO_ID"), 
       		resultSet.getString("ACA_OBO_ALT_ID")
       	);
    }
}
