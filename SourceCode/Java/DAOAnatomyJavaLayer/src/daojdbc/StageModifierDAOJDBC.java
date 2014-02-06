/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        StageModifierDAO.java
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
* Description:  This class represents a SQL Database Access Object for the StageModifier DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the StageModifier DTO and a SQL database.
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

import daomodel.StageModifier;

import daointerface.StageModifierDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class StageModifierDAOJDBC implements StageModifierDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT SMO_NAME " +
        "FROM ANA_STAGE_MODIFIER " +
        "WHERE SMO_NAME LIKE ? " +
        "AND SMO_NAME LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_STAGE_MODIFIER " +
        "WHERE SMO_NAME LIKE ? " +
        "AND SMO_NAME LIKE ? ";

    private static final String SQL_FIND_BY_NAME =
        "SELECT SMO_NAME " +
        "FROM ANA_STAGE_MODIFIER " +
        "WHERE SMO_NAME = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT SMO_NAME " +
        "FROM ANA_STAGE_MODIFIER ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_STAGE_MODIFIER " +
        "(SMO_NAME) " +
        "VALUES (?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_STAGE_MODIFIER " +
        "SET SMO_NAME = ? " + 
        "WHERE SMO_NAME = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_STAGE_MODIFIER " +
        "WHERE SMO_NAME = ?";

    private static final String SQL_EXIST_NAME =
        "SELECT SMO_NAME " +
        "FROM ANA_STAGE_MODIFIER " +
        "WHERE SMO_NAME = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a StageModifier Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public StageModifierDAOJDBC() {
    	
    }

    public StageModifierDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the stagemodifier from the database matching the given OID, otherwise null.
     */
    public StageModifier findByName(String name) throws Exception {
    	
        return find(SQL_FIND_BY_NAME, name);
    }

    /*
     * Returns the stagemodifier from the database matching the given 
     *  SQL query with the given values.
     */
    private StageModifier find(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StageModifier stagemodifier = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	
                stagemodifier = mapStageModifier(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return stagemodifier;
    }

    /*
     * Returns a list of ALL stagemodifiers, otherwise null.
     */
    public List<StageModifier> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL, (Object[]) null);
    }
    
    /*
     * Returns a list of all stagemodifiers from the database. 
     *  The list is never null and is empty when the database does not contain any stagemodifiers.
     */
    public List<StageModifier> list(String sql, Object... values) throws Exception {
    	
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<StageModifier> stagemodifiers = new ArrayList<StageModifier>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	
                stagemodifiers.add(mapStageModifier(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return stagemodifiers;
    }

    /*
     * Create the given stagemodifier in the database. 
     * The stagemodifier OID must be null, otherwise it will throw IllegalArgumentException.
     * If the stagemodifier OID value is unknown, rather use save(StageModifier).
     * After creating, the Data Access Object will set the obtained ID in the given stagemodifier.
     */    
    public void create(StageModifier stagemodifier) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
    	    stagemodifier.getName()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Creating stagemodifier failed, no rows affected.");
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
     * Update the given stagemodifier in the database.
     *  The stagemodifier OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the stagemodifier OID value is unknown, rather use save(StageModifier)}.
     */
    public void update(StageModifier stagemodifier) throws Exception {
    	
        if (stagemodifier.getName() == null) {
        	
            throw new IllegalArgumentException("StageModifier is not created yet, so the stagemodifier OID cannot be null.");
        }

        Object[] values = {
      	    stagemodifier.getName(),
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Updating stagemodifier failed, no rows affected.");
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
     * Delete the given stagemodifier from the database. 
     *  After deleting, the Data Access Object will set the ID of the given stagemodifier to null.
     */
    public void delete(StageModifier stagemodifier) throws Exception {
    	
        Object[] values = { 
        	stagemodifier.getName() 
        };

        if (stagemodifier.getName() == null) {
        	
            throw new IllegalArgumentException("StageModifier is not created yet, so the stagemodifier OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
            	
                throw new DAOException("Deleting stagemodifier failed, no rows affected.");
            } 
            else {
            	
            	stagemodifier.setName(null);
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
     * Returns true if the given stagemodifier OID exists in the database.
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
    public List<StageModifier> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws Exception {
    	
    	String sqlSortField = "SMO_NAME";

        if (sortField.equals("name")) {
        	sqlSortField = "SMO_NAME";      
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
        
        List<StageModifier> dataList = new ArrayList<StageModifier>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapStageModifier(resultSet));
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
    private static StageModifier mapStageModifier(ResultSet resultSet) throws SQLException {
    	
        return new StageModifier(
        		resultSet.getString("SMO_NAME")
        		);
    }
}
