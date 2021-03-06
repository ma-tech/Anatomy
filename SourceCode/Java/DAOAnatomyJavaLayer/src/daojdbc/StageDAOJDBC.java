/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        StageDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Stage DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the Stage DTO and a SQL database.
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

import daomodel.Stage;

import daointerface.StageDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class StageDAOJDBC implements StageDAO{
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "WHERE STG_NAME LIKE ? " +
        "AND STG_SHORT_EXTRA_TEXT LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_STAGE " +
        "WHERE STG_NAME LIKE ? " +
        "AND STG_SHORT_EXTRA_TEXT LIKE ? ";

    private static final String SQL_VALUE_MIN_SEQUENCE =
        "SELECT MIN(STG_SEQUENCE) AS VALUE " +
        "FROM ANA_STAGE  ";

    private static final String SQL_VALUE_MAX_SEQUENCE =
        "SELECT MAX(STG_SEQUENCE) AS VALUE " +
        "FROM ANA_STAGE  ";

    private static final String SQL_FIND_BY_OID =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "WHERE STG_OID = ?";
    
    private static final String SQL_FIND_BY_NAME =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "WHERE STG_NAME = ?";
    
    private static final String SQL_FIND_BY_SEQUENCE =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "WHERE STG_SEQUENCE = ?";
    
    private static final String SQL_LIST_ALL =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE ";
        
    private static final String SQL_LIST_ALL_BY_SEQUENCE =
        "SELECT STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID " +
        "FROM ANA_STAGE " +
        "ORDER BY STG_SEQUENCE";
            
    private static final String SQL_INSERT =
        "INSERT INTO ANA_STAGE " +
        "(STG_OID, STG_SPECIES_FK, STG_NAME, STG_SEQUENCE, STG_DESCRIPTION, STG_SHORT_EXTRA_TEXT, STG_PUBLIC_ID) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_STAGE " +
        "SET STG_SPECIES_FK = ?, " +
        "STG_NAME = ?, " +
        "STG_SEQUENCE = ?, " + 
        "STG_DESCRIPTION = ?, " +
        "STG_SHORT_EXTRA_TEXT = ?, " +
        "STG_PUBLIC_ID = ? " + 
        "WHERE STG_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_STAGE " +
        "WHERE STG_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT STG_OID " +
        "FROM ANA_STAGE " +
        "WHERE STG_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Stage Data Access Object for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public StageDAOJDBC() {
    	
    }

    public StageDAOJDBC(DAOFactory daoFactory) {

    	this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the Stage from the database matching the given OID, otherwise null.
     */
    public Stage findByOid(long oid) throws Exception {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the Stage from the database matching the given Name, otherwise null.
     */
    public Stage findByName(String name) throws Exception {
    	
        return find(SQL_FIND_BY_NAME, name);
    }
    
    /*
     * Returns the Stage from the database matching the given Sequence Number, otherwise null.
     */
    public Stage findBySequence(long seq) throws Exception {
    	
        return find(SQL_FIND_BY_SEQUENCE, seq);
    }
    
    /*
     * Returns a list of ALL stages, ordered by Sequence otherwise null.
     */
    public List<Stage> listAllBySequence() throws Exception {
    	
        return list(SQL_LIST_ALL_BY_SEQUENCE);
    }
    
    /*
     * Returns a list of ALL stages, otherwise null.
     */
    public List<Stage> listAll() throws Exception {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given stage OID exists in the database.
     */
    public boolean existOid(long oid) throws Exception {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Returns the Maximum stage sequence in the database.
     */
    public long valueMaxSequence() throws Exception {
    	
        return value(SQL_VALUE_MAX_SEQUENCE);
    }

    /*
     * Returns the Minimum stage sequence in the database.
     */
    public long valueMinSequence() throws Exception {
    	
        return value(SQL_VALUE_MIN_SEQUENCE);
    }

    /*
     * Save the given stage in the database.
     * 
     *  If the Stage OID is null, 
     *   then it will invoke "create(Stage)", 
     *   else it will invoke "update(Stage)".
     */
    public void save(Stage stage) throws Exception {
     
    	if (stage.getOid() == null) {
    		
            create(stage);
        }
    	else {
    		
            update(stage);
        }
    }
    
    /*
     * Returns the stage from the database matching the given 
     *  SQL query with the given values.
     */
    private Stage find(String sql, Object... values) throws Exception {
    
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Stage stage = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
            	
                stage = mapStage(resultSet);
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return stage;
    }
    
    /*
     * Returns a list of all stages from the database. 
     * 
     *  The list is never null and is empty when the database does not contain any stages.
     */
    public List<Stage> list(String sql, Object... values) throws Exception {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Stage> stages = new ArrayList<Stage>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                stages.add(mapStage(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return stages;
    }
    
    /*
     * Create the given stage in the database.
     *  
     *  The stage OID must be null, otherwise it will throw IllegalArgumentException.
     *  If the stage OID value is unknown, rather use save(Stage).
     *   After creating, the Data Access Object will set the obtained ID in the given stage.
     */
    public void create(Stage stage) throws IllegalArgumentException, Exception {
    	
        Object[] values = {
       		stage.getOid(),
       		stage.getSpeciesFK(),
       		stage.getName(),
       		stage.getSequence(),
       		stage.getDescription(),
       		stage.getExtraText(),
       		stage.getPublicId()
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
                	
                    throw new DAOException("Creating Stage failed, no rows affected.");
                } 
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Create ANA_STAGE Skipped", "***", daoFactory.getMsgLevel());
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
     * Update the given stage in the database.
     * 
     *  The stage OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the stage OID value is unknown, rather use save(Stage)}.
     */
    public void update(Stage stage) throws Exception {
    	
        if (stage.getOid() == null) {
        	
            throw new IllegalArgumentException("Stage is not created yet, so the stage OID cannot be null.");
        }

        Object[] values = {
       		stage.getSpeciesFK(),
       		stage.getName(),
       		stage.getSequence(),
       		stage.getDescription(),
       		stage.getExtraText(),
       		stage.getPublicId(),
       		stage.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Updating Stage failed, no rows affected.");
                } 
                else {
                	
                	stage.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Update ANA_STAGE Skipped", "***", daoFactory.getMsgLevel());
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
     * Delete the given stage from the database. 
     * 
     *  After deleting, the Data Access Object will set the ID of the given stage to null.
     */
    public void delete(Stage stage) throws Exception {
    	
        Object[] values = { 
        	stage.getOid() 
        };

        if (stage.getOid() == null) {
        	
            throw new IllegalArgumentException("Stage is not created yet, so the stage OID cannot be null.");
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                	
                    throw new DAOException("Deleting stage failed, no rows affected.");
                } 
                else {
                	
                	stage.setOid(null);
                }
            }
            else {
            	
    		    Wrapper.printMessage("UPDATE: Delete ANA_STAGE Skipped", "***", daoFactory.getMsgLevel());
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
     * Returns list of Stages for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Stage> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws Exception {
    	
        String searchFirstWithWildCards = "";
        String searchSecondWithWildCards = "";

    	String sqlSortField = "STG_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "STG_OID";       
        }
        if (sortField.equals("speciesFK")) {
        	sqlSortField = "STG_SPECIES_FK";      
        }
        if (sortField.equals("name")) {
        	sqlSortField = "STG_NAME";         
        }
        if (sortField.equals("sequence")) {
        	sqlSortField = "STG_SEQUENCE";         
        }
        if (sortField.equals("description")) {
        	sqlSortField = "STG_DESCRIPTION";         
        }
        if (sortField.equals("extraText")) {
        	sqlSortField = "STG_SHORT_EXTRA_TEXT";         
        }
        if (sortField.equals("publicId")) {
        	sqlSortField = "STG_PUBLIC_ID";         
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
        
        List<Stage> dataList = new ArrayList<Stage>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
                dataList.add(mapStage(resultSet));
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
    public long value(String sql) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long value = 0;

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	
            	value = resultSet.getLong("VALUE");
            }
            
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return value;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to an User.
     */
    private static Stage mapStage(ResultSet resultSet) throws SQLException {

    	return new Stage(
      		resultSet.getLong("STG_OID"), 
       		resultSet.getString("STG_SPECIES_FK"), 
       		resultSet.getString("STG_NAME"), 
       		resultSet.getLong("STG_SEQUENCE"),
       		resultSet.getString("STG_DESCRIPTION"),
       		resultSet.getString("STG_SHORT_EXTRA_TEXT"),
       		resultSet.getString("STG_PUBLIC_ID")
        );
    }
}
