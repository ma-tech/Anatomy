/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        SourceFormatDAO.java
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
* Description:  This class represents a SQL Database Access Object for the SourceFormat DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the SourceFormat DTO and a SQL database.
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

import daomodel.SourceFormat;

import daointerface.SourceFormatDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

public final class SourceFormatDAOJDBC implements SourceFormatDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT SFM_NAME " +
        "FROM ANA_SOURCE_FORMAT " +
        "WHERE SFM_NAME LIKE ? " +
        "AND SFM_NAME LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_SOURCE_FORMAT " +
        "WHERE SFM_NAME LIKE ? " +
        "AND SFM_NAME LIKE ? ";

    private static final String SQL_FIND_BY_NAME =
        "SELECT SFM_NAME " +
        "FROM ANA_SOURCE_FORMAT " +
        "WHERE SFM_NAME = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT SFM_NAME " +
        "FROM ANA_SOURCE_FORMAT ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_SOURCE_FORMAT " +
        "(SFM_NAME) " +
        "VALUES (?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_SOURCE_FORMAT " +
        "SET SFM_NAME = ? " + 
        "WHERE SFM_NAME = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_SOURCE_FORMAT " +
        "WHERE SFM_NAME = ?";

    private static final String SQL_EXIST_NAME =
        "SELECT SFM_NAME " +
        "FROM ANA_SOURCE_FORMAT " +
        "WHERE SFM_NAME = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a SourceFormat DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public SourceFormatDAOJDBC() {
    	
    }

    public SourceFormatDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the sourceformat from the database matching the given OID, otherwise null.
     */
    public SourceFormat findByName(String name) throws Exception {
    	
        return find(SQL_FIND_BY_NAME, name);
    }

    /*
     * Returns the sourceformat from the database matching the given 
     *  SQL query with the given values.
     */
    private SourceFormat find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        SourceFormat sourceformat = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
                sourceformat = mapSourceFormat(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return sourceformat;
    }

    /*
     * Returns a list of ALL sourceformats, otherwise null.
     */
    public List<SourceFormat> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL, (Object[]) null);
    }
    
    /*
     * Returns a list of all sourceformats from the database. 
     *  The list is never null and is empty when the database does not contain any sourceformats.
     */
    public List<SourceFormat> list(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SourceFormat> sourceformats = new ArrayList<SourceFormat>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	
                sourceformats.add(mapSourceFormat(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return sourceformats;
    }

    /*
     * Create the given sourceformat in the database. 
     * The sourceformat OID must be null, otherwise it will throw IllegalArgumentException.
     * If the sourceformat OID value is unknown, rather use save(SourceFormat).
     * After creating, the DAO will set the obtained ID in the given sourceformat.
     */    
    public void create(SourceFormat sourceformat) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
    	    sourceformat.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Creating sourceformat failed, no rows affected.");
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
     * Update the given sourceformat in the database.
     *  The sourceformat OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the sourceformat OID value is unknown, rather use save(SourceFormat)}.
     */
    public void update(SourceFormat sourceformat) throws Exception {
    	
        if (sourceformat.getName() == null) {
        	
            throw new IllegalArgumentException("SourceFormat is not created yet, so the sourceformat OID cannot be null.");
        }

        Object[] values = {
      	    sourceformat.getName(),
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Updating sourceformat failed, no rows affected.");
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
     * Delete the given sourceformat from the database. 
     *  After deleting, the DAO will set the ID of the given sourceformat to null.
     */
    public void delete(SourceFormat sourceformat) throws Exception {
    	
        Object[] values = { 
        	sourceformat.getName() 
        };

        if (sourceformat.getName() == null) {
        	
            throw new IllegalArgumentException("SourceFormat is not created yet, so the sourceformat OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Deleting sourceformat failed, no rows affected.");
            } 
            else {
            	
            	sourceformat.setName(null);
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
     * Returns true if the given sourceformat OID exists in the database.
     */
    public boolean existName(String name) throws Exception {
    	
        return exist(SQL_EXIST_NAME, name);
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
    public List<SourceFormat> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception {
    	
    	String sqlSortField = "SFM_NAME";

        if (sortField.equals("name")) {
        	sqlSortField = "SFM_NAME";      
        }
        
        String searchWithWildCards = "";
        String extraWithWildCards = "";

        if (searchTerm.equals("")) {
        	//searchWithWildCards = "";
        	searchWithWildCards = "%" + searchTerm + "%";
    	}
        else {
        	searchWithWildCards = "%" + searchTerm + "%";
        }

        if (searchExtra.equals("")) {
        	//extraWithWildCards = "";
        	extraWithWildCards = "%" + searchExtra + "%";
    	}
        else {
        	extraWithWildCards = "%" + searchExtra + "%";
        }
        
         Object[] values = {
        		searchWithWildCards, 
        		extraWithWildCards,
                firstRow, 
                rowCount
        };

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<SourceFormat> dataList = new ArrayList<SourceFormat>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapSourceFormat(resultSet));
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
    public int count(String searchTerm, String searchExtra) throws Exception {

        String searchWithWildCards = "";
        String extraWithWildCards = "";

        if (searchTerm.equals("")) {
        	//searchWithWildCards = "";
        	searchWithWildCards = "%" + searchTerm + "%";
    	}
        else {
        	searchWithWildCards = "%" + searchTerm + "%";
        }

        if (searchExtra.equals("")) {
        	//extraWithWildCards = "";
        	extraWithWildCards = "%" + searchExtra + "%";
    	}
        else {
        	extraWithWildCards = "%" + searchExtra + "%";
        }
        
        Object[] values = {
        		searchWithWildCards, 
        		extraWithWildCards
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
    private static SourceFormat mapSourceFormat(ResultSet resultSet) throws SQLException {
    	
        return new SourceFormat(
        		resultSet.getString("SFM_NAME")
        		);
    }
}
