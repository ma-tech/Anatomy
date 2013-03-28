/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        SynonymDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Synonym DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Synonym DTO and a SQL database.
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
package daojdbc;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.Synonym;

import daointerface.SynonymDAO;

import utility.Wrapper;

import daolayer.DAOFactory;
import daolayer.DAOException;


public final class SynonymDAOJDBC implements SynonymDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_SYNONYM LIKE ? " +
        "AND SYN_OBJECT_FK LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_SYNONYM LIKE ? " +
        "AND SYN_OBJECT_FK LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_OID = ?";
    
    private static final String SQL_LIST_BY_OBJECT_FK_AND_SYNONYM =
        "SELECT SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_OBJECT_FK = ? " +
        "AND SYN_SYNONYM = ?";
        
    private static final String SQL_LIST_BY_OBJECT_FK =
        "SELECT SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_OBJECT_FK = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM " +
        "FROM ANA_SYNONYM ";
        
    private static final String SQL_INSERT =
        "INSERT INTO ANA_SYNONYM " +
        "(SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM) " +
        "VALUES (?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_SYNONYM SET " +
        "SYN_OBJECT_FK = ?, " +
        "SYN_SYNONYM = ? " + 
        "WHERE SYN_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_SYNONYM " +
        "WHERE SYN_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT SYN_OID " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Synonym DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    public SynonymDAOJDBC() {
    	
    }

    public SynonymDAOJDBC(DAOFactory daoFactory) {

    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
	/*
     * Returns the Synonym from the database matching the given OID, otherwise null.
     */
    public Synonym findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL synonyms by Parent FK, otherwise null.
     */
    public List<Synonym> listByObjectFKAndSynonym(long objectFK, String synonym) throws Exception {
    	
        return list(SQL_LIST_BY_OBJECT_FK_AND_SYNONYM, objectFK, synonym);
    }
    
    /*
     * Returns a list of ALL synonyms by Parent FK, otherwise null.
     */
    public List<Synonym> listByObjectFK(long objectFK) throws Exception {
    	
        return list(SQL_LIST_BY_OBJECT_FK, objectFK);
    }
    
    /*
     * Returns a list of ALL synonyms, otherwise null.
     */
    public List<Synonym> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given synonym OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Save the given synonym in the database.
     * 
     *  If the Synonym OID is null, 
     *   then it will invoke "create(Synonym)", 
     *   else it will invoke "update(Synonym)".
     */
    public void save(Synonym synonym) throws Exception {
     
    	if (synonym.getOid() == null) {
    		
            create(synonym);
        }
    	else {
    		
            update(synonym);
        }
    }
    
    /*
     * Returns the synonym from the database matching the given 
     *  SQL query with the given values.
     */
    private Synonym find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Synonym synonym = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                synonym = mapSynonym(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return synonym;
    }
    
    /*
     * Returns a list of all synonyms from the database. 
     *  The list is never null and is empty when the database does not contain any synonyms.
     */
    public List<Synonym> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Synonym> synonyms = new ArrayList<Synonym>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                synonyms.add(mapSynonym(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return synonyms;
    }
    
    /*
     * Create the given synonym in the database.
     *  
     *  The synonym OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the synonym OID value is unknown, rather use save(Synonym).
     *   After creating, the DAO will set the obtained ID in the given synonym.
     */
    public void create(Synonym synonym) throws IllegalArgumentException, Exception {
    	
    	Object[] values = {
       		synonym.getOid(),
       		synonym.getThingFK(),
       		synonym.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Creating Synonym failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_SYNONYM Skipped", "***", daoFactory.getLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, generatedKeys);
        }
    }
    
    /*
     * Update the given synonym in the database.
     * 
     *  The synonym OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the synonym OID value is unknown, rather use save(Synonym)}.
     */
    public void update(Synonym synonym) throws Exception {
    	
        if (synonym.getOid() == null) {
        	
            throw new IllegalArgumentException("Synonym is not created yet, so the synonym OID cannot be null.");
        }

    	Object[] values = {
           		synonym.getThingFK(),
           		synonym.getName(),
           		synonym.getOid()
            };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating Synonym failed, no rows affected.");
                } 
                else {
                	
                	synonym.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_SYNONYM Skipped", "***", daoFactory.getLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(),connection, preparedStatement);
        }
    }
     
    /*
     * Delete the given synonym from the database. 
     *  After deleting, the DAO will set the ID of the given synonym to null.
     */
    public void delete(Synonym synonym) throws Exception {
    	
        Object[] values = { 
        	synonym.getOid() 
        };

        if (synonym.getOid() == null) {
        	
            throw new IllegalArgumentException("Synonym is not created yet, so the synonym OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting synonym failed, no rows affected.");
                } 
                else {
                	
                	synonym.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_SYNONYM Skipped", "***", daoFactory.getLevel());
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(),connection, preparedStatement);
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
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return exist;
    }
    
    /*
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Synonym> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "SYN_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "SYN_OID";       
        }
        if (sortField.equals("thingFK")) {
        	sqlSortField = "SYN_OBJECT_FK";      
        }
        if (sortField.equals("name")) {
        	sqlSortField = "SYN_SYNONYM";         
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
        
        List<Synonym> dataList = new ArrayList<Synonym>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapSynonym(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
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
            preparedStatement = prepareStatement(daoFactory.getLevel(), daoFactory.getSqloutput(), connection, SQL_ROW_COUNT, false, values);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
                count = resultSet.getInt("VALUE");
            }
            
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getLevel(), connection, preparedStatement, resultSet);
        }

        return count;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static Synonym mapSynonym(ResultSet resultSet) throws SQLException {

    	return new Synonym(
      		resultSet.getLong("SYN_OID"), 
       		resultSet.getLong("SYN_OBJECT_FK"), 
       		resultSet.getString("SYN_SYNONYM")
        );
    }
}
