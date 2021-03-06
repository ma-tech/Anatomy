/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ComponentSynonymDAO.java
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
* Description:  This class represents a SQL Database Access Object for the ComponentSynonym DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the ComponentSynonym DTO and a SQL database.
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

import daomodel.ComponentSynonym;

import daointerface.ComponentSynonymDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class ComponentSynonymDAOJDBC implements ComponentSynonymDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ACS_OID, ACS_OBO_ID, ACS_OBO_TEXT  " +
        "FROM ANA_OBO_COMPONENT_SYNONYM " +
        "WHERE ACS_OBO_TEXT LIKE ? " +
        "AND ACS_OBO_ID LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_SYNONYM " +
        "WHERE ACS_OBO_TEXT LIKE ? " +
        "AND ACS_OBO_ID LIKE ? ";

    private static final String SQL_ROW_COUNT_ALL =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_OBO_COMPONENT_SYNONYM ";

    private static final String SQL_FIND_BY_OID =
        "SELECT ACS_OID, ACS_OBO_ID, ACS_OBO_TEXT  " +
        "FROM ANA_OBO_COMPONENT_SYNONYM " +
        "WHERE ACS_OID = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT ACS_OID, ACS_OBO_ID, ACS_OBO_TEXT " +
        "FROM ANA_OBO_COMPONENT_SYNONYM ";
    
    private static final String SQL_LIST_ALL_BY_OBO_ID =
        "SELECT ACS_OID, ACS_OBO_ID, ACS_OBO_TEXT " +
        "FROM ANA_OBO_COMPONENT_SYNONYM " +
        "WHERE ACS_OBO_ID = ? ";
            
    private static final String SQL_LIST_ALL_BY_TEXT =
        "SELECT ACS_OID, ACS_OBO_ID, ACS_OBO_TEXT " +
        "FROM ANA_OBO_COMPONENT_SYNONYM " +
        "WHERE ACS_OBO_TEXT = ? ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_OBO_COMPONENT_SYNONYM " +
        "(ACS_OBO_ID, ACS_OBO_TEXT) " +
        "VALUES (?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_OBO_COMPONENT_SYNONYM SET " +
        "ACS_OBO_ID = ?, " +
        "ACS_OBO_TEXT = ? " + 
        "WHERE ACS_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_OBO_COMPONENT_SYNONYM " +
        "WHERE ACS_OID = ?";

    private static final String SQL_EMPTY =
        "DELETE FROM ANA_OBO_COMPONENT_SYNONYM";

    private static final String SQL_EXIST_OID =
        "SELECT ACS_OID " +
        "FROM ANA_OBO_COMPONENT_SYNONYM " +
        "WHERE ACS_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a ComponentSynonym Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public ComponentSynonymDAOJDBC() {
    	
    }
    
    public ComponentSynonymDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the daocomponentsynonym from the database matching the given OID, otherwise null.
     */
    public ComponentSynonym findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the daocomponentsynonyms from the database matching the given OBO ID, otherwise null.
     */
    public List<ComponentSynonym> listByOboId(String oboid) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_OBO_ID, oboid);
    }
    
    /*
     * Returns the daocomponentsynonyms from the database matching the given OBI Name, otherwise null.
     */
    public List<ComponentSynonym> listByText(String text) throws Exception {
    	
        return list(SQL_LIST_ALL_BY_TEXT, text);
    }
    
    /*
     * Returns a list of ALL componentsynonyms, otherwise null.
     */
    public List<ComponentSynonym> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given daocomponentsynonym OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given daocomponentsynonym in the database.
     * 
     *  If the ComponentSynonym OID is null, 
     *   then it will invoke "create(ComponentSynonym)", 
     *   else it will invoke "update(ComponentSynonym)".
     */
    public void save(ComponentSynonym daocomponentsynonym) throws Exception {
     
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
    private ComponentSynonym find(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ComponentSynonym daocomponentsynonym = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                daocomponentsynonym = mapComponentSynonym(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return daocomponentsynonym;
    }
    
    /*
     * Returns a list of all componentsynonyms from the database. 
     *  The list is never null and is empty when the database does not contain any componentsynonyms.
     */
    public List<ComponentSynonym> list(String sql, Object... values) throws Exception {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ComponentSynonym> componentsynonyms = new ArrayList<ComponentSynonym>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                componentsynonyms.add(mapComponentSynonym(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return componentsynonyms;
    }
    
    /*
     * Create the given daocomponentsynonym in the database. 
     * 
     *  The daocomponentsynonym OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the daocomponentsynonym OID value is unknown, rather use save(ComponentSynonym).
     *   After creating, the Data Access Object will set the obtained ID in the given daocomponentsynonym.
     */
    public void create(ComponentSynonym daocomponentsynonym) throws IllegalArgumentException, Exception {

    	Object[] values = {
        	daocomponentsynonym.getId(),
            daocomponentsynonym.getText()
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
                	
                    throw new DAOException("Creating ComponentSynonym failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_OBO_COMPONENT_SYNONYM Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given daocomponentsynonym in the database.
     *  The daocomponentsynonym OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the daocomponentsynonym OID value is unknown, rather use save(ComponentSynonym)}.
     */
    public void update(ComponentSynonym daocomponentsynonym) throws Exception {
    	
        if (daocomponentsynonym.getOid() == null) {
        	
            throw new IllegalArgumentException("ComponentSynonym is not created yet, so the daocomponentsynonym OID cannot be null.");
        }

        Object[] values = {
            daocomponentsynonym.getId(),
            daocomponentsynonym.getText(),
           	daocomponentsynonym.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating ComponentSynonym failed, no rows affected.");
                } 
                else {
                	
                	daocomponentsynonym.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_OBO_COMPONENT_SYNONYM Skipped", "***", daoFactory.getMsgLevel());
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
     *  Delete the given daocomponentsynonym from the database. 
     *  After deleting, the Data Access Object will set the ID of the given daocomponentsynonym to null.
     */
    public void delete(ComponentSynonym daocomponentsynonym) throws Exception {
    	
        Object[] values = { 
        	daocomponentsynonym.getOid() 
        };

        if (daocomponentsynonym.getOid() == null) {
        	
            throw new IllegalArgumentException("ComponentSynonym is not created yet, so the daocomponentsynonym OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting ComponentSynonym failed, no rows affected.");
                } 
                else {
                	
                	daocomponentsynonym.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_OBO_COMPONENT_SYNONYM Skipped", "***", daoFactory.getMsgLevel());
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
     *  Delete the given daocomponentsynonym from the database. 
     *  After deleting, the Data Access Object will set the ID of the given daocomponentsynonym to null.
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
                	
                    throw new DAOException("Deleting ALL ComponentSynonyms failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_OBO_COMPONENT_SYNONYM Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of ComponentSynonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<ComponentSynonym> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

        String sqlSortField = "ACS_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "ACS_OID";       
        }
        if (sortField.equals("id")) {
        	sqlSortField = "ACS_OBO_ID";         
        }
        if (sortField.equals("text")) {
        	sqlSortField = "ACS_OBO_TEXT";         
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
        
        List<ComponentSynonym> dataList = new ArrayList<ComponentSynonym>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapComponentSynonym(resultSet));
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
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

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
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT_ALL, false);

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
     * Map the current row of the given ResultSet to a ComponentSynonym.
     */
    private static ComponentSynonym mapComponentSynonym(ResultSet resultSet) throws SQLException {

    	return new ComponentSynonym(
      		resultSet.getLong("ACS_OID"), 
       		resultSet.getString("ACS_OBO_ID"), 
       		resultSet.getString("ACS_OBO_TEXT")
       	);
    }
}
