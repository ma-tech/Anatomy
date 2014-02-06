/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ComponentOrderDAO.java
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
* Description:  This class represents a SQL Database Access Object for the ComponentOrder DTO.
*  
*               This Data Access Object should be used as a central point for the mapping between 
*                the ComponentOrder DTO and a SQL database.
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

import daomodel.JOINComponentOrderComponentComponent;

import daointerface.JOINComponentOrderComponentComponentDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import static daolayer.DAOUtil.*;

public final class JOINComponentOrderComponentComponentDAOJDBC implements JOINComponentOrderComponentComponentDAO {
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_BY_PARENT_ALPHA_ORDER =
        "SELECT ACO_OID, ACO_OBO_ALPHA_ORDER, ACO_OBO_SPECIAL_ORDER, ACO_OBO_CHILD, ACO_OBO_TYPE, ACO_OBO_PARENT, " +
        "b.AOC_OID, b.AOC_NAME, b.AOC_OBO_ID, b.AOC_DB_ID, b.AOC_NEW_ID, b.AOC_NAMESPACE, b.AOC_DEFINITION, b.AOC_GROUP, b.AOC_START, b.AOC_END, b.AOC_PRESENT, b.AOC_STATUS_CHANGE, b.AOC_STATUS_RULE, " +
        "a.AOC_OID, a.AOC_NAME, a.AOC_OBO_ID, a.AOC_DB_ID, a.AOC_NEW_ID, a.AOC_NAMESPACE, a.AOC_DEFINITION, a.AOC_GROUP, a.AOC_START, a.AOC_END, a.AOC_PRESENT, a.AOC_STATUS_CHANGE, a.AOC_STATUS_RULE " +
        "FROM ANA_OBO_COMPONENT_ORDER " +
        "JOIN ANA_OBO_COMPONENT b ON b.AOC_OBO_ID = ACO_OBO_CHILD " +
        "JOIN ANA_OBO_COMPONENT a ON a.AOC_OBO_ID = ACO_OBO_PARENT " +
        "WHERE ACO_OBO_TYPE = 'PART_OF' " +
        "AND ACO_OBO_PARENT = ? " +
        "ORDER BY a.AOC_NAME, b.AOC_NAME, b.AOC_OBO_ID ";
            

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a ComponentOrder Data Access Object for the given DAOFactory.
     *  Package private so that it can be constructed inside the Data Access Object package only.
     */
    public JOINComponentOrderComponentComponentDAOJDBC() {
    	
    }
    
    public JOINComponentOrderComponentComponentDAOJDBC(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
	public void setDAOFactory(DAOFactory daofactory) {

		this.daoFactory = daofactory;
	}
    
    /*
     * Returns the daocomponentorder from the database matching the given OID, otherwise null.
     */
    public List<JOINComponentOrderComponentComponent> listOrderByParentByAlphaOrder(String parentId) throws Exception {
    	
        return list(SQL_LIST_BY_PARENT_ALPHA_ORDER, parentId);
    }
    
    /*
     * Returns a list of all componentorders from the database. 
     *  The list is never null and is empty when the database does not contain any componentorders.
     */
    public List<JOINComponentOrderComponentComponent> list(String sql, Object... values) throws Exception {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<JOINComponentOrderComponentComponent> joincomponentordercomponentcomponents = new ArrayList<JOINComponentOrderComponentComponent>();

        try {
        	
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.getMsgLevel(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
            	
            	joincomponentordercomponentcomponents.add(mapJOINComponentOrderComponentComponent(resultSet));
            }
        } 
        catch (SQLException e) {
        	
            throw new DAOException(e);
        } 
        finally {
        	
            close(daoFactory.getMsgLevel(), connection, preparedStatement, resultSet);
        }

        return joincomponentordercomponentcomponents;
    }
    

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a ComponentOrder.
     */
    private static JOINComponentOrderComponentComponent mapJOINComponentOrderComponentComponent(ResultSet resultSet) throws SQLException {
      
    	return new JOINComponentOrderComponentComponent(
      		resultSet.getLong("ACO_OID"), 
       		resultSet.getString("ACO_OBO_CHILD"), 
       		resultSet.getString("ACO_OBO_PARENT"),
       		resultSet.getString("ACO_OBO_TYPE"),
      		resultSet.getLong("ACO_OBO_ALPHA_ORDER"),
      		resultSet.getLong("ACO_OBO_SPECIAL_ORDER"),
      		
      		resultSet.getLong("b.AOC_OID"), 
       		resultSet.getString("b.AOC_NAME"), 
       		resultSet.getString("b.AOC_OBO_ID"), 
       		resultSet.getString("b.AOC_DB_ID"),
       		resultSet.getString("b.AOC_NEW_ID"), 
       		resultSet.getString("b.AOC_NAMESPACE"),      		
       		resultSet.getString("b.AOC_DEFINITION"),      		
       		resultSet.getBoolean("b.AOC_GROUP"), 
       		resultSet.getString("b.AOC_START"), 
       		resultSet.getString("b.AOC_END"), 
       		resultSet.getBoolean("b.AOC_PRESENT"),
       		resultSet.getString("b.AOC_STATUS_CHANGE"), 
       		resultSet.getString("b.AOC_STATUS_RULE"),
       		
      		resultSet.getLong("a.AOC_OID"), 
       		resultSet.getString("a.AOC_NAME"), 
       		resultSet.getString("a.AOC_OBO_ID"), 
       		resultSet.getString("a.AOC_DB_ID"),
       		resultSet.getString("a.AOC_NEW_ID"), 
       		resultSet.getString("a.AOC_NAMESPACE"),      		
       		resultSet.getString("a.AOC_DEFINITION"),      		
       		resultSet.getBoolean("a.AOC_GROUP"), 
       		resultSet.getString("a.AOC_START"), 
       		resultSet.getString("a.AOC_END"), 
       		resultSet.getBoolean("a.AOC_PRESENT"),
       		resultSet.getString("a.AOC_STATUS_CHANGE"), 
       		resultSet.getString("a.AOC_STATUS_RULE")
        );
    }
}
