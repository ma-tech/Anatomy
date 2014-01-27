/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        EditorDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Editor DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Editor DTO and a SQL database.
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

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.Editor;

import daointerface.EditorDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

public final class EditorDAOJDBC implements EditorDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT EDI_OID, EDI_NAME " +
        "FROM ANA_EDITOR " +
        "WHERE EDI_NAME LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_EDITOR " +
        "WHERE EDI_NAME LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT EDI_OID, EDI_NAME " +
        "FROM ANA_EDITOR " +
        "WHERE EDI_OID = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT EDI_OID, EDI_NAME " +
        "FROM ANA_EDITOR ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_EDITOR " +
        "(EDI_OID, EDI_NAME) " +
        "VALUES (?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_EDITOR " +
        "SET EDI_NAME = ? " + 
        "WHERE EDI_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_EDITOR " +
        "WHERE EDI_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT EDI_OID " +
        "FROM ANA_EDITOR " +
        "WHERE EDI_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Editor DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public EditorDAOJDBC() {
    	
    }
    
    public EditorDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the editor from the database matching the given OID, otherwise null.
     */
    public Editor findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }

    /*
     * Returns the editor from the database matching the given 
     *  SQL query with the given values.
     */
    private Editor find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Editor editor = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
                editor = mapEditor(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return editor;
    }

    /*
     * Returns a list of ALL editors, otherwise null.
     */
    public List<Editor> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL, (Object[]) null);
    }
    
    /*
     * Returns a list of all editors from the database. 
     *  The list is never null and is empty when the database does not contain any editors.
     */
    public List<Editor> list(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Editor> editors = new ArrayList<Editor>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	
                editors.add(mapEditor(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return editors;
    }

    /*
     * Create the given editor in the database. 
     * The editor OID must be null, otherwise it will throw IllegalArgumentException.
     * If the editor OID value is unknown, rather use save(Editor).
     * After creating, the DAO will set the obtained ID in the given editor.
     */
    public void create(Editor editor) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
        	editor.getOid(),
        	editor.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Creating editor failed, no rows affected.");
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
     * Update the given editor in the database.
     *  The editor OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the editor OID value is unknown, rather use save(Editor)}.
     */
    public void update(Editor editor) throws Exception {
    	
        if (editor.getOid() == null) {
        	
            throw new IllegalArgumentException("Editor is not created yet, so the editor OID cannot be null.");
        }

        Object[] values = {
        	editor.getName(),
            editor.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Updating editor failed, no rows affected.");
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
     * Delete the given editor from the database. 
     *  After deleting, the DAO will set the ID of the given editor to null.
     */
    public void delete(Editor editor) throws Exception {
    	
        Object[] values = { 
        	editor.getOid() 
        };

        if (editor.getOid() == null) {
        	
            throw new IllegalArgumentException("Editor is not created yet, so the editor OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Deleting editor failed, no rows affected.");
            } 
            else {
            	
            	editor.setOid(null);
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
     * Returns true if the given editor OID exists in the database.
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
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Editor> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception {
    	
    	String sqlSortField = "EDI_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "EDI_OID";       
        }
        if (sortField.equals("name")) {
        	sqlSortField = "EDI_NAME";      
        }
        
        String searchWithWildCards = "";

        if (searchTerm.equals("")) {
        	//searchWithWildCards = "";
        	searchWithWildCards = "%" + searchTerm + "%";
    	}
        else {
        	searchWithWildCards = "%" + searchTerm + "%";
        }

         Object[] values = {
        		searchWithWildCards, 
                firstRow, 
                rowCount
        };

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<Editor> dataList = new ArrayList<Editor>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapEditor(resultSet));
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
    public int count(String searchTerm) throws Exception {

        String searchWithWildCards = "";

        if (searchTerm.equals("")) {
        	//searchWithWildCards = "";
        	searchWithWildCards = "%" + searchTerm + "%";
    	}
        else {
        	searchWithWildCards = "%" + searchTerm + "%";
        }

        Object[] values = {
        		searchWithWildCards, 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
                count = resultSet.getInt("VALUE");
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
    private static Editor mapEditor(ResultSet resultSet) throws SQLException {
    	
        return new Editor(
      		resultSet.getLong("EDI_OID"), 
       		resultSet.getString("EDI_NAME")
        );
    }
}
