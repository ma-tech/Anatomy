/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        VersionDAO.java
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
* Description:  This class represents a SQL Database Access Object for the Node DTO.
*  
*               This DAO should be used as a central point for the mapping between 
*                the Node DTO and a SQL database.
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

package daolayer;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import daomodel.Node;

public final class NodeDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_MAX_EMAPA =
        "SELECT MAX(CAST( SUBSTRING(ANO_PUBLIC_ID, 7) AS SIGNED )) AS MAXIMUM " +
        "FROM ANA_NODE";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_PUBLIC_ID LIKE ? " +
        "AND ANO_DESCRIPTION LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT_WHERE =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_NODE " +
        "WHERE ANO_PUBLIC_ID LIKE ? " +
        "AND ANO_DESCRIPTION LIKE ? ";

    private static final String SQL_ROW_COUNT =
        "SELECT COUNT(*) AS VALUE " +
        "FROM ANA_NODE ";

    private static final String SQL_FIND_BY_OID =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_OID = ? ";
    
    private static final String SQL_FIND_BY_PUBLIC_ID =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_PUBLIC_ID = ? ";
    
    private static final String SQL_FIND_BY_COMPONENT_NAME =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_COMPONENT_NAME = ? ";
        
    private static final String SQL_LIST_ALL =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE ";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_NODE " +
        "(ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_NODE SET " +
        "ANO_SPECIES_FK = ?, " +
        "ANO_COMPONENT_NAME = ?, " + 
        "ANO_IS_PRIMARY = ?, " +
        "ANO_IS_GROUP = ?, " +
        "ANO_PUBLIC_ID = ?, " +
        "ANO_DESCRIPTION = ? " + 
        "WHERE ANO_OID = ?";
    
    private static final String SQL_UPDATE_BY_PUBLIC_ID =
        "UPDATE ANA_NODE SET " +
        "ANO_SPECIES_FK = ?, " +
        "ANO_COMPONENT_NAME = ?, " + 
        "ANO_IS_PRIMARY = ?, " +
        "ANO_IS_GROUP = ?, " +
        "ANO_PUBLIC_ID = ?, " +
        "ANO_DESCRIPTION = ? " + 
        "WHERE ANO_PUBLIC_ID = ?";
        
    private static final String SQL_DELETE =
        "DELETE FROM ANA_NODE " +
        "WHERE ANO_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT ANO_OID " +
        "FROM ANA_NODE " +
        "WHERE ANO_OID = ?";

    private static final String SQL_EXIST_BY_PUBLIC_ID =
        "SELECT ANO_OID " +
        "FROM ANA_NODE " +
        "WHERE ANO_PUBLIC_ID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Node DAO for the given DAOFactory.
     * 
     *  Package private so that it can be constructed inside the DAO package only.
     */
    NodeDAO(DAOFactory daoFactory) {
    	
        this.daoFactory = daoFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the maximum EMAPA id.
     */
    public int maximumEmapa() throws DAOException {
    	
        return maximum(SQL_MAX_EMAPA);
    }
    
    /*
     * Returns the node from the database matching the given OID, otherwise null.
     */
    public Node findByOid(Long oid) throws DAOException {
    	
        return find(SQL_FIND_BY_OID, oid);
    }
    
    /*
     * Returns the node from the database matching the given publicId, otherwise null.
     */
    public Node findByPublicId(String publicId) throws DAOException {
    	
        return find(SQL_FIND_BY_PUBLIC_ID, publicId);
    }

    /*
     * Returns the node from the database matching the given componentName, otherwise null.
     */
    public Node findByComponentName(String componentName) throws DAOException {
    	
        return find(SQL_FIND_BY_COMPONENT_NAME, componentName);
    }

    /*
     * Returns a list of ALL nodes, otherwise null.
     */
    public List<Node> listAll() throws DAOException {
    	
        return list(SQL_LIST_ALL);
    }
    
    /*
     * Returns true if the given node OID exists in the database.
     */
    public boolean existOid(Long oid) throws DAOException {
    	
        return exist(SQL_EXIST_OID, oid);
    }

    /*
     * Returns true if the given node OID exists in the database.
     */
    public boolean existPublicId(String publicId) throws DAOException {
    	
        return exist(SQL_EXIST_BY_PUBLIC_ID, publicId);
    }

    /*
     * Save the given node in the database.
     * 
     *  If the Node OID is null, 
     *   then it will invoke "create(Node)", 
     *   else it will invoke "update(Node)".
     */
    public void save(Node node) throws DAOException {
     
    	if (node.getOid() == null) {
            create(node);
        }
    	else {
            update(node);
        }
    }

    /*
     * Returns the node from the database matching the given 
     *  SQL query with the given values.
     */
    private Node find(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Node node = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
                node = mapNode(resultSet);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return node;
    }
    
    /*
     * Returns a list of all nodes from the database. 
     *  The list is never null and is empty when the database does not contain any nodes.
     */
    public List<Node> list(String sql, Object... values) throws DAOException {
      
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Node> nodes = new ArrayList<Node>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                nodes.add(mapNode(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return nodes;
    }
    
    /*
     * Create the given node in the database.
     * 
     *  The node OID must be null, otherwise it will throw IllegalArgumentException.
     *   If the node OID value is unknown, rather use save(Node).
     *    After creating, the DAO will set the obtained ID in the given node.
     */
    public void create(Node node) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
            node.getOid(),
        	node.getSpeciesFK(),
        	node.getComponentName(),
        	node.isPrimary(),
        	node.isGroup(),
        	node.getPublicId(),
        	node.getDescription()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_INSERT, true, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Creating Node failed, no rows affected.");
                } 
            }
            else {
            	System.out.println("UPDATE: Create ANA_NODE Skipped");
            }

        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }
    
    /*
     * Update the given node in the database.
     * 
     *  The node OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the node OID value is unknown, rather use save(Node)}.
     */
    public void update(Node node) throws DAOException {
    	
        if (node.getOid() == null) {
            throw new IllegalArgumentException("Node is not created yet, so the node OID cannot be null.");
        }

        Object[] values = {
           	node.getSpeciesFK(),
           	node.getComponentName(),
           	node.isPrimary(),
           	node.isGroup(),
           	node.getPublicId(),
           	node.getDescription(),
            node.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_UPDATE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Updating Node failed, no rows affected.");
                } 
                else {
                	node.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Update ANA_NODE Skipped");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
     
    /*
     *  Delete the given node from the database. 
     *  
     *  After deleting, the DAO will set the ID of the given node to null.
     */
    public void delete(Node node) throws DAOException {
    	
        Object[] values = { 
        	node.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, SQL_DELETE, false, values);

            if ( daoFactory.isUpdate() ) {

            	int affectedRows = preparedStatement.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DAOException("Deleting Node failed, no rows affected.");
                } 
                else {
                	node.setOid(null);
                }
            }
            else {
            	System.out.println("UPDATE: Delete ANA_NODE Skipped");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
    
    /*
     * Returns true if the given SQL query with the given values returns at least one row.
     */
    private boolean exist(String sql, Object... values) throws DAOException {
     
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return exist;
    }
    
    /*
     * Returns list of Nodes for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Node> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchFirst, String searchSecond)
        throws DAOException {
    	
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

    	String sqlSortField = "ANO_OID";

    	if (sortField.equals("oid")) {
        	sqlSortField = "ANO_OID";       
        }
        if (sortField.equals("speciesFK")) {
        	sqlSortField = "ANO_SPECIES_FK";      
        }
        if (sortField.equals("componentName")) {
        	sqlSortField = "ANO_COMPONENT_NAME";         
        }
        if (sortField.equals("primary")) {
        	sqlSortField = "ANO_IS_PRIMARY";         
        }
        if (sortField.equals("group")) {
        	sqlSortField = "ANO_IS_GROUP";         
        }
        if (sortField.equals("publicId")) {
        	sqlSortField = "ANO_PUBLIC_ID";         
        }
        if (sortField.equals("description")) {
        	sqlSortField = "ANO_DESCRIPTION";         
        }
        
        String sortDirection = sortAscending ? "ASC" : "DESC";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<Node> dataList = new ArrayList<Node>();

        try {
            connection = daoFactory.getConnection();
            
            if ( searchFirst.equals("")) {
                Object[] values = {
                        firstRow, 
                        rowCount
                    };
                String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);

            }
            else {
                Object[] values = {
                    	searchFirstWithWildCards, 
                    	searchSecondWithWildCards,
                        firstRow, 
                        rowCount
                    };
                String sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE, sqlSortField, sortDirection);
                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            }

            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                dataList.add(mapNode(resultSet));
            }
            
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return dataList;
    }
    
    /*
     * Returns total amount of rows in table.
     */
    public int count(String searchFirst, String searchSecond) throws DAOException {

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

            if ( searchFirst.equals("")) {
                String sql = SQL_ROW_COUNT;
                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false);

            }
            else {
                String sql = SQL_ROW_COUNT_WHERE;
                preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            }

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("VALUE");
            }
            
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return count;
    }

    /*
     * Returns total amount of rows in table.
     */
    public int maximum(String sql) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int maximum = 0;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	maximum = resultSet.getInt("MAXIMUM");
            }
            
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return maximum;
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a Node.
     */
    private static Node mapNode(ResultSet resultSet) throws SQLException {
      
    	return new Node(
      		resultSet.getLong("ANO_OID"), 
       		resultSet.getString("ANO_SPECIES_FK"), 
       		resultSet.getString("ANO_COMPONENT_NAME"), 
       		resultSet.getBoolean("ANO_IS_PRIMARY"),
       		resultSet.getBoolean("ANO_IS_GROUP"), 
       		resultSet.getString("ANO_PUBLIC_ID"), 
       		resultSet.getString("ANO_DESCRIPTION")
        );
    }
}