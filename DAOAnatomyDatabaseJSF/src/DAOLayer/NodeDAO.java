package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.Node;

/**
 * This class represents a SQL Database Access Object for the Node DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the Node DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class NodeDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_PUBLIC_ID LIKE ? " +
        "AND ANO_COMPONENT_NAME LIKE ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_NODE " +
        "WHERE ANO_PUBLIC_ID LIKE ? " +
        "AND ANO_COMPONENT_NAME LIKE ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_OID = ?";
    
    private static final String SQL_FIND_BY_PUBLIC_ID =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_PUBLIC_ID = ?";
    
    private static final String SQL_FIND_MAX_PUBLIC_ID =
        "SELECT MAX(ANO_PUBLIC_ID) " +
        "FROM ANA_NODE ";
    
    private static final String SQL_LIST_ALL =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE ";
    
    private static final String SQL_LIST_ALL_BY_PRIMARY =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_IS_PRIMARY = ?";
    
    private static final String SQL_LIST_ALL_BY_GROUP =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "WHERE ANO_IS_GROUP = ?";
    
    private static final String SQL_LIST_ALL_BY_NAME_LIKENESS =
        "SELECT ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION " +
        "FROM ANA_NODE " +
        "where ANO_COMPONENT_NAME LIKE \"%?%\"";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_NODE " +
        "(ANO_OID, ANO_SPECIES_FK, ANO_COMPONENT_NAME, ANO_IS_PRIMARY, ANO_IS_GROUP, ANO_PUBLIC_ID, ANO_DESCRIPTION) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_NODE " +
        "SET ANO_SPECIES_FK = ?, " +
        "ANO_COMPONENT_NAME = ?, " +
        "ANO_IS_PRIMARY = ?, " + 
        "ANO_IS_GROUP = ?, " +
        "ANO_PUBLIC_ID = ?, " +
        "ANO_DESCRIPTION = ? " + 
        "WHERE ANO_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_NODE " +
        "WHERE STG_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT ANO_OID " +
        "FROM ANA_NODE " +
        "WHERE ANO_OID = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a Node DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    NodeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the node from the database matching the given OID, otherwise null.
     */
    public Node find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns the node from the database matching the given Public EMAPA ID, otherwise null.
     */
    public Node find(String publicId) throws DAOException {
        return find(SQL_FIND_BY_PUBLIC_ID, publicId);
    }

    /**
     * Returns the max public ID (EMAPA) from the database, otherwise null.
     */
    public Node find() throws DAOException {
        return find(SQL_FIND_MAX_PUBLIC_ID, null);
    }

    /**
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
            preparedStatement = prepareStatement(connection, sql, false, values);
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

    /**
     * Returns a list of ALL nodes, otherwise null.
     */
    public List<Node> listAll() throws DAOException {
        return list(SQL_LIST_ALL, null);
    }
    
    /**
     * Returns a list of all Primary nodes, otherwise null.
     */
    public List<Node> listAllByPrimary(Integer primary) throws DAOException {
        return list(SQL_LIST_ALL_BY_PRIMARY, primary);
    }
    
    /**
     * Returns a list of ALL Group nodes, otherwise null.
     */
    public List<Node> listAllByGroup(Integer group) throws DAOException {
        return list(SQL_LIST_ALL_BY_GROUP, group);
    }
    
    /**
     * Returns a list of All nodes that contain the name extract, otherwise null.
     */
    public List<Node> listAllByNameLikeness(String name) throws DAOException {
        return list(SQL_LIST_ALL_BY_NAME_LIKENESS, name);
    }
    
    /**
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
            preparedStatement = prepareStatement(connection, sql, false, values);
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

    /**
     * Create the given node in the database. 
     * The node OID must be null, otherwise it will throw IllegalArgumentException.
     * If the node OID value is unknown, rather use save(Node).
     * After creating, the DAO will set the obtained ID in the given node.
     */
     
    public void create(Node node) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
        	node.getOid(),
    	    node.getSpeciesFK(),
    	    node.getComponentName(), 
    	    node.getPrimary(),
    	    node.getGroup(),
    	    node.getPublicId(),
    	    node.getDescription()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating node failed, no rows affected.");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }
    
    /**
     * Update the given node in the database.
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
        	node.getPrimary(),
        	node.getGroup(),
        	node.getPublicId(),
        	node.getDescription(),
            node.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating node failed, no rows affected.");
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
    
     
    /**
     * Delete the given node from the database. 
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
            preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting node failed, no rows affected.");
            } 
            else {
            	node.setOid(null);
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement);
        }
    }
    
    
    /**
     * Returns true if the given node OID exists in the database.
     */
    public boolean existOid(String oid) throws DAOException {
        return exist(SQL_EXIST_OID, oid);
    }

    /**
     * Returns true if the given SQL query with the given values returns at least one row.
     */
    private boolean exist(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
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

    /**
     * Returns list of Synonyms for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Node> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm, String searchExtra)
        throws DAOException
    {
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
        
        List<Node> dataList = new ArrayList<Node>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);

            //System.out.println("PS = " + preparedStatement.toString());
            
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

    /**
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm, String searchExtra) throws DAOException {

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
            preparedStatement = prepareStatement(connection, SQL_ROW_COUNT, false, values);

            //System.out.println("PS = " + preparedStatement.toString());

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


    // Helpers ------------------------------------------------------------------------------------
    /**
     * Map the current row of the given ResultSet to an User.
     */
    private static Node mapNode(ResultSet resultSet) throws SQLException {
        return new Node(
      		resultSet.getLong("ANO_OID"), 
       		resultSet.getString("ANO_SPECIES_FK"), 
       		resultSet.getString("ANO_COMPONENT_NAME"), 
       		resultSet.getInt("ANO_IS_PRIMARY"),
       		resultSet.getInt("ANO_IS_GROUP"), 
       		resultSet.getString("ANO_PUBLIC_ID"), 
       		resultSet.getString("ANO_DESCRIPTION")
        );
    }

}
