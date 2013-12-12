/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        SpeciesDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Species DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Species DTO and a SQL database.
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

import daomodel.Species;

import daointerface.SpeciesDAO;

import utility.Wrapper;

import daolayer.DAOFactory;
import daolayer.DAOException;


public final class SpeciesDAOJDBC implements SpeciesDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT RSP_NAME, RSP_LATIN_NAME, RSP_TIMED_NODE_ID_PREFIX, RSP_NODE_ID_PREFIX " +
        "FROM REF_SPECIES " +
        "WHERE RSP_TIMED_NODE_ID_PREFIX LIKE ? " +
        "AND RSP_NODE_ID_PREFIX LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM REF_SPECIES " +
        "WHERE RSP_TIMED_NODE_ID_PREFIX LIKE ? " +
        "AND RSP_NODE_ID_PREFIX LIKE ? ";

    private static final String SQL_FIND_BY_NAME =
        "SELECT RSP_NAME, RSP_LATIN_NAME, RSP_TIMED_NODE_ID_PREFIX, RSP_NODE_ID_PREFIX " +
        "FROM REF_SPECIES " +
        "WHERE RSP_NAME = ? ";
    
    private static final String SQL_LIST_ALL =
        "SELECT RSP_NAME, RSP_LATIN_NAME, RSP_TIMED_NODE_ID_PREFIX, RSP_NODE_ID_PREFIX " +
        "FROM REF_SPECIES ";
        
    private static final String SQL_INSERT =
        "INSERT INTO REF_SPECIES " +
        "(RSP_NAME, RSP_LATIN_NAME, RSP_TIMED_NODE_ID_PREFIX, RSP_NODE_ID_PREFIX) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE REF_SPECIES SET " +
        "RSP_LATIN_NAME = ?, " +
        "RSP_TIMED_NODE_ID_PREFIX = ?, " +
        "RSP_NODE_ID_PREFIX = ?, " +
        "WHERE RSP_NAME = ? ";
    
    private static final String SQL_DELETE =
        "DELETE FROM REF_SPECIES " +
        "WHERE RSP_NAME = ? ";

    private static final String SQL_EXIST_NAME =
        "SELECT RSP_NAME " +
        "FROM REF_SPECIES " +
        "WHERE RSP_NAME = ? ";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Species DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public SpeciesDAOJDBC() {
    	
    }

    public SpeciesDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the Species from the database matching the given OID, otherwise null.
     */
    public Species findByName(String name) throws Exception {
    	
        return find(SQL_FIND_BY_NAME, name);
    }
    
    /*
     * Returns a list of ALL speciess, otherwise null.
     */
    public List<Species> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given species OID exists in the database.
     */
    public boolean existName(String name) throws Exception {
    	
        return exist(SQL_EXIST_NAME, name);
    }

    /*
     * Save the given species in the database.
     * 
     *  If the Species OID is null, 
     *   then it will invoke "create(Species)", 
     *   else it will invoke "update(Species)".
     */
    public void save(Species species) throws Exception {
     
    	if (species.getName() == null) {
    		
            create(species);
        }
    	else {
    		
            update(species);
        }
    }
    
    /*
     * Returns the species from the database matching the given 
     *  SQL query with the given values.
     */
    private Species find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Species species = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                species = mapSpecies(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return species;
    }

    /*
     * Returns a list of all speciess from the database. 
     *  The list is never null and is empty when the database does not contain any speciess.
     */
    public List<Species> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Species> speciess = new ArrayList<Species>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                speciess.add(mapSpecies(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return speciess;
    }
    
    /*
     * Create the given species in the database. 
     *  The species OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the species OID value is unknown, rather use save(Species).
     *   After creating, the DAO will set the obtained ID in the given species.
     */
    public void create(Species species) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
       		species.getName(),
       		species.getLatinName(),
       		species.getTimedPrefix(),
       		species.getAbstractPrefix()
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
                	
                    throw new DAOException("Creating Species failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create REF_SPECIES Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given species in the database.
     * 
     *  The species OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the species OID value is unknown, rather use save(Species)}.
     */
    public void update(Species species) throws Exception {
    	
        if (species.getName() == null) {
            throw new IllegalArgumentException("Species is not created yet, so the species OID cannot be null.");
        }

        Object[] values = {
       		species.getLatinName(),
       		species.getTimedPrefix(),
       		species.getAbstractPrefix(),
       		species.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating Species failed, no rows affected.");
                } 
                else {
                	
                	species.setName(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update REF_SPECIES Skipped", "***", daoFactory.getMsgLevel());
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
     * Delete the given species from the database. 
     * 
     *  After deleting, the DAO will set the ID of the given species to null.
     */
    public void delete(Species species) throws Exception {
    	
        Object[] values = { 
        	species.getName() 
        };

        if (species.getName() == null) {
            throw new IllegalArgumentException("Species is not created yet, so the species OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting species failed, no rows affected.");
                } 
                else {
                	
                	species.setName(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete REF_SPECIES Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of Speciess for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Species> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "RSP_NAME";

    	if (sortField.equals("name")) {
        	sqlSortField = "RSP_NAME";       
        }
        if (sortField.equals("latinName")) {
        	sqlSortField = "RSP_LATIN_NAME";      
        }
        if (sortField.equals("timedPrefix")) {
        	sqlSortField = "RSP_TIMED_NODE_ID_PREFIX";      
        }
        if (sortField.equals("abstractPrefix")) {
        	sqlSortField = "RSP_NODE_ID_PREFIX";         
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
        
        List<Species> dataList = new ArrayList<Species>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapSpecies(resultSet));
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
    public int count(String searchFirst, String searchSecond) throws Exception {

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
    private static Species mapSpecies(ResultSet resultSet) throws SQLException {

    	return new Species(
      		resultSet.getString("RSP_NAME"), 
      		resultSet.getString("RSP_LATIN_NAME"), 
       		resultSet.getString("RSP_TIMED_NODE_ID_PREFIX"), 
       		resultSet.getString("RSP_NODE_ID_PREFIX")
        );
    }
}
