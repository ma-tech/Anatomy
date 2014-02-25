/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        EvidenceDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Evidence DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Evidence DTO and a SQL database.
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

import daomodel.Evidence;
import daointerface.EvidenceDAO;
import daolayer.DAOFactory;
import daolayer.DAOException;
import static daolayer.DAOUtil.*;

public final class EvidenceDAOJDBC implements EvidenceDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT EVI_NAME " +
        "FROM ANA_EVIDENCE " +
        "WHERE EVI_NAME LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_EVIDENCE " +
        "WHERE EVI_NAME LIKE ? ";

    private static final String SQL_FIND_BY_NAME =
        "SELECT EVI_NAME " +
        "FROM ANA_EVIDENCE " +
        "WHERE EVI_NAME = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT EVI_NAME " +
        "FROM ANA_EVIDENCE ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_EVIDENCE " +
        "(EVI_NAME) " +
        "VALUES (?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_EVIDENCE " +
        "SET EVI_NAME = ? " + 
        "WHERE EVI_NAME = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_EVIDENCE " +
        "WHERE EVI_NAME = ?";


    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Evidence Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public EvidenceDAOJDBC() {
    	
    }
    
    public EvidenceDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the evidence from the database matching the given OID, otherwise null.
     */
    public Evidence findByName(String name) throws Exception {
    	
        return find(SQL_FIND_BY_NAME, name);
    }

    /*
     * Returns the evidence from the database matching the given 
     *  SQL query with the given values.
     */
    private Evidence find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Evidence evidence = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
                evidence = mapEvidence(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return evidence;
    }

    /*
     * Returns a list of ALL evidences, otherwise null.
     */
    public List<Evidence> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of all evidences from the database. 
     *  The list is never null and is empty when the database does not contain any evidences.
     */
    public List<Evidence> list(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Evidence> evidences = new ArrayList<Evidence>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	
                evidences.add(mapEvidence(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return evidences;
    }

    /*
     * Create the given evidence in the database. 
     * The evidence OID must be null, otherwise it will throw IllegalArgumentException.
     * If the evidence OID value is unknown, rather use save(Evidence).
     * After creating, the Data Access Object will set the obtained ID in the given evidence.
     */
    public void create(Evidence evidence) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
        	evidence.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Creating evidence failed, no rows affected.");
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
     * Update the given evidence in the database.
     *  The evidence OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the evidence OID value is unknown, rather use save(Evidence)}.
     */
    public void update(Evidence evidence) throws Exception {
    	
        if (evidence.getName() == null) {
        	
            throw new IllegalArgumentException("Evidence is not created yet, so the evidence OID cannot be null.");
        }

        Object[] values = {
        	evidence.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Updating evidence failed, no rows affected.");
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
     * Delete the given evidence from the database. 
     *  After deleting, the Data Access Object will set the ID of the given evidence to null.
     */
    public void delete(Evidence evidence) throws Exception {
    	
        Object[] values = { 
        	evidence.getName() 
        };

        if (evidence.getName() == null) {
        	
            throw new IllegalArgumentException("Evidence is not created yet, so the evidence OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Deleting evidence failed, no rows affected.");
            } 
            else {
            	
            	evidence.setName(null);
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
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Evidence> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws Exception {
    	
    	String sqlSortField = "EVI_NAME";

        if (sortField.equals("name")) {
        	sqlSortField = "EVI_NAME";      
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
        
        List<Evidence> dataList = new ArrayList<Evidence>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapEvidence(resultSet));
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
    public long count(String searchTerm) throws Exception {

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

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static Evidence mapEvidence(ResultSet resultSet) throws SQLException {
    	
        return new Evidence(
       		resultSet.getString("EVI_NAME")
        );
    }
}
