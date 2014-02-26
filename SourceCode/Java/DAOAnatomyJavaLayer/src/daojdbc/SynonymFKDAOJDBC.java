/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        SynonymFKDAO.java
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
* Description:  This class represents a SQL Database Access Object for the SynonymFK DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the SynonymFK DTO and a SQL database.
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

import daomodel.SynonymFK;

import daointerface.SynonymFKDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class SynonymFKDAOJDBC implements SynonymFKDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
    	"SELECT SYN_OID, ANO_PUBLIC_ID, SYN_SYNONYM " +
    	"FROM ANA_SYNONYM " +
    	"JOIN ANA_NODE ON ANO_OID = SYN_OBJECT_FK " +
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
    	"SELECT SYN_OID, ANO_PUBLIC_ID, SYN_SYNONYM " +
    	"FROM ANA_SYNONYM " +
    	"JOIN ANA_NODE ON ANO_OID = SYN_OBJECT_FK " +
        "WHERE SYN_OID = ?";
    
    private static final String SQL_LIST_BY_OBJECT_FK_AND_SYNONYM =
    	"SELECT SYN_OID, ANO_PUBLIC_ID, SYN_SYNONYM " +
    	"FROM ANA_SYNONYM " +
    	"JOIN ANA_NODE ON ANO_OID = SYN_OBJECT_FK " +
        "WHERE SYN_OBJECT_FK = ? " +
        "AND SYN_SYNONYM = ?";
        
    private static final String SQL_LIST_BY_OBJECT_FK =
    	"SELECT SYN_OID, ANO_PUBLIC_ID, SYN_SYNONYM " +
    	"FROM ANA_SYNONYM " +
    	"JOIN ANA_NODE ON ANO_OID = SYN_OBJECT_FK " +
        "WHERE SYN_OBJECT_FK = ?";
    
    private static final String SQL_LIST_ALL =
    	"SELECT SYN_OID, ANO_PUBLIC_ID, SYN_SYNONYM " +
    	"FROM ANA_SYNONYM " +
    	"JOIN ANA_NODE ON ANO_OID = SYN_OBJECT_FK ";
        
    private static final String SQL_EXIST_OID =
        "SELECT SYN_OID " +
        "FROM ANA_SYNONYM " +
        "WHERE SYN_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a SynonymFK Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public SynonymFKDAOJDBC() {
    	
    }

    public SynonymFKDAOJDBC(DAOFactory daoFactory) {

    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
	/*
     * Returns the SynonymFK from the database matching the given OID, otherwise null.
     */
    public SynonymFK findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns a list of ALL synonymfks by Parent FK, otherwise null.
     */
    public List<SynonymFK> listByObjectFKAndSynonymFK(long objectFK, String synonymfk) throws Exception {
    	
        return list(SQL_LIST_BY_OBJECT_FK_AND_SYNONYM, objectFK, synonymfk);
    }
    
    /*
     * Returns a list of ALL synonymfks by Parent FK, otherwise null.
     */
    public List<SynonymFK> listByObjectFK(long objectFK) throws Exception {
    	
        return list(SQL_LIST_BY_OBJECT_FK, objectFK);
    }
    
    /*
     * Returns a list of ALL synonymfks, otherwise null.
     */
    public List<SynonymFK> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given synonymfk OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Returns the synonymfk from the database matching the given 
     *  SQL query with the given values.
     */
    private SynonymFK find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        SynonymFK synonymfk = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                synonymfk = mapSynonymFK(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return synonymfk;
    }
    
    /*
     * Returns a list of all synonymfks from the database. 
     *  The list is never null and is empty when the database does not contain any synonymfks.
     */
    public List<SynonymFK> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SynonymFK> synonymfks = new ArrayList<SynonymFK>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                synonymfks.add(mapSynonymFK(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return synonymfks;
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
     * Returns list of SynonymFKs for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<SynonymFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
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
        
        List<SynonymFK> dataList = new ArrayList<SynonymFK>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapSynonymFK(resultSet));
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

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static SynonymFK mapSynonymFK(ResultSet resultSet) throws SQLException {

    	return new SynonymFK(
      		resultSet.getLong("SYN_OID"), 
       		resultSet.getString("ANO_PUBLIC_ID"), 
       		resultSet.getString("SYN_SYNONYM")
        );
    }
}
