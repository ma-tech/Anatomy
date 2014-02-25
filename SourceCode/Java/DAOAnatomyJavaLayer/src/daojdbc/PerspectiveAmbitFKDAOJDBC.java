/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        PerspectiveAmbitFKDAO.java
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
* Description:  This class represents a SQL Database Access Object for the 
*                PerspectiveAmbitFK DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the PerspectiveAmbitFK DTO and a SQL database.
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

import daomodel.PerspectiveAmbitFK;

import daointerface.PerspectiveAmbitFKDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class PerspectiveAmbitFKDAOJDBC implements PerspectiveAmbitFKDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
    	"SELECT PAM_OID, PAM_PERSPECTIVE_FK, ANO_PUBLIC_ID, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS " +
    	"FROM ANA_PERSPECTIVE_AMBIT " +
    	"JOIN ANA_NODE ON ANO_OID = PAM_NODE_FK " +
        "WHERE PAM_PERSPECTIVE_FK LIKE ? " +
        "AND PAM_COMMENTS LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_PERSPECTIVE_AMBIT " +
        "WHERE PAM_PERSPECTIVE_FK LIKE ? " +
        "AND PAM_COMMENTS LIKE ? ";

    private static final String SQL_FIND_BY_OID =
    	"SELECT PAM_OID, PAM_PERSPECTIVE_FK, ANO_PUBLIC_ID, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS " +
    	"FROM ANA_PERSPECTIVE_AMBIT " +
    	"JOIN ANA_NODE ON ANO_OID = PAM_NODE_FK " +
        "WHERE PAM_OID = ?";
    
    private static final String SQL_LIST_ALL =
    	"SELECT PAM_OID, PAM_PERSPECTIVE_FK, ANO_PUBLIC_ID, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS " +
    	"FROM ANA_PERSPECTIVE_AMBIT " +
    	"JOIN ANA_NODE ON ANO_OID = PAM_NODE_FK " +
    	"ORDER BY PAM_OID";
    
    private static final String SQL_EXIST_OID =
        "SELECT PAM_OID " +
        "FROM ANA_PERSPECTIVE_AMBIT " +
        "WHERE PAM_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a PerspectiveAmbitFK Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public PerspectiveAmbitFKDAOJDBC() {
    	
    }

    public PerspectiveAmbitFKDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the perspectiveambitfk from the database matching the given OID, otherwise null.
     */
    public PerspectiveAmbitFK findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }

    /*
     * Returns the perspectiveambitfk from the database matching the given 
     *  SQL query with the given values.
     */
    private PerspectiveAmbitFK find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PerspectiveAmbitFK perspectiveambitfk = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
                perspectiveambitfk = mapPerspectiveAmbitFK(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return perspectiveambitfk;
    }

    /*
     * Returns a list of ALL perspectiveambitfks, otherwise null.
     */
    public List<PerspectiveAmbitFK> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns a list of all perspectiveambitfks from the database. 
     *  The list is never null and is empty when the database does not contain any perspectiveambitfks.
     */
    public List<PerspectiveAmbitFK> list(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PerspectiveAmbitFK> perspectiveambitfks = new ArrayList<PerspectiveAmbitFK>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	
                perspectiveambitfks.add(mapPerspectiveAmbitFK(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return perspectiveambitfks;
    }

    /*
     * Returns true if the given perspectiveambitfk OID exists in the database.
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
    public List<PerspectiveAmbitFK> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception {
    	
    	String sqlSortField = "PAM_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "PAM_OID";       
        }
        if (sortField.equals("perspectiveFK")) {
        	sqlSortField = "PAM_PERSPECTIVE_FK";      
        }
        if (sortField.equals("nodeFK")) {
        	sqlSortField = "PAM_NODE_FK";         
        }
        if (sortField.equals("isStart")) {
        	sqlSortField = "PAM_IS_START";         
        }
        if (sortField.equals("isStop")) {
        	sqlSortField = "PAM_IS_STOP";         
        }
        if (sortField.equals("comments")) {
        	sqlSortField = "PAM_COMMENTS";         
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
        
        List<PerspectiveAmbitFK> dataList = new ArrayList<PerspectiveAmbitFK>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapPerspectiveAmbitFK(resultSet));
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
    public long count(String searchTerm, String searchExtra) throws Exception {

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
    private static PerspectiveAmbitFK mapPerspectiveAmbitFK(ResultSet resultSet) throws SQLException {
    	
        return new PerspectiveAmbitFK(
      		resultSet.getLong("PAM_OID"), 
       		resultSet.getString("PAM_PERSPECTIVE_FK"), 
       		resultSet.getString("ANO_PUBLIC_ID"), 
       		resultSet.getString("PAM_IS_START"),
       		resultSet.getString("PAM_IS_STOP"), 
       		resultSet.getString("PAM_COMMENTS")
        );
    }
}
