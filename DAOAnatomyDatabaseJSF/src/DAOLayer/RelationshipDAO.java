package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Model.Relationship;

/**
 * This class represents a SQL Database Access Object for the Relationship DTO.
 * This DAO should be used as a central point for the mapping between 
 *  the Relationship DTO and a SQL database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public final class RelationshipDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_RELATIONSHIP ";

    private static final String SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_PARENT_FK = ? " +
        "ORDER BY %s %s "+
        "LIMIT ?, ?";

    private static final String SQL_ROW_COUNT_WHERE =
        "SELECT count(*) AS VALUE " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_PARENT_FK = ? ";

    private static final String SQL_FIND_BY_OID =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_OID = ?";
    
    private static final String SQL_LIST_BY_CHILD_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_CHILD_FK = ?";
    
    private static final String SQL_LIST_BY_PARENT_FK =
        "SELECT REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_PARENT_FK = ?";
    
    private static final String SQL_INSERT =
        "INSERT INTO ANA_RELATIONSHIP " +
        "(REL_OID, REL_RELATIONSHIP_TYPE_FK, REL_CHILD_FK, REL_PARENT_FK) " +
        "VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE ANA_RELATIONSHIP " +
        "SET REL_RELATIONSHIP_TYPE_FK = ?, " +
        "REL_CHILD_FK = ?, " +
        "REL_PARENT_FK = ? " + 
        "WHERE REL_OID = ?";
    
    private static final String SQL_DELETE =
        "DELETE FROM ANA_RELATIONSHIP " +
        "WHERE REL_OID = ?";

    private static final String SQL_EXIST_OID =
        "SELECT REL_OID " +
        "FROM ANA_RELATIONSHIP " +
        "WHERE REL_OID = ?";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a Relationship DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    RelationshipDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Actions ------------------------------------------------------------------------------------
    /**
     * Returns the relationship from the database matching the given OID, otherwise null.
     */
    public Relationship find(Long oid) throws DAOException {
        return find(SQL_FIND_BY_OID, oid);
    }

    /**
     * Returns the relationship from the database matching the given 
     *  SQL query with the given values.
     */
    private Relationship find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Relationship relationship = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                relationship = mapRelationship(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return relationship;
    }

    /**
     * Returns a list of relationship matching the given Child FK, otherwise null.
     */
    public List<Relationship> listAllByChildFK(String childFK) throws DAOException {
        return list(SQL_LIST_BY_CHILD_FK, childFK);
    }
    
    /**
     * Returns a list of relationship matching the given Parent FK, otherwise null.
     */
    public List<Relationship> listAllByParentFK(String parentFK) throws DAOException {
        return list(SQL_LIST_BY_PARENT_FK, parentFK);
    }
    
    /**
     * Returns a list of all relationships from the database. 
     *  The list is never null and is empty when the database does not contain any relationships.
     */
    public List<Relationship> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Relationship> relationships = new ArrayList<Relationship>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                relationships.add(mapRelationship(resultSet));
            }
        } 
        catch (SQLException e) {
            throw new DAOException(e);
        } 
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return relationships;
    }

    /**
     * Create the given user in the database. 

     */
     
    public void create(Relationship relationship) throws IllegalArgumentException, DAOException {
    	
        Object[] values = {
        	relationship.getOid(),
            relationship.getChildFK(),
            relationship.getParentFK(),
            relationship.getTypeFK()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Creating relationship failed, no rows affected.");
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
     * Update the given relationship in the database.
     *  The relationship OID must not be null, otherwise it will throw IllegalArgumentException. 
     *  If the relationship OID value is unknown, rather use save(Relationship)}.
     */
    public void update(Relationship relationship) throws DAOException {
    	
        if (relationship.getOid() == null) {
            throw new IllegalArgumentException("Relationship has already een created, so the relationship OID cannot be null.");
        }

        Object[] values = {
            relationship.getChildFK(),
            relationship.getParentFK(),
            relationship.getTypeFK(),
            relationship.getOid()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Updating relationship failed, no rows affected.");
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
     * Delete the given relationship from the database. 
     *  After deleting, the DAO will set the ID of the given relationship to null.
     */
    public void delete(Relationship relationship) throws DAOException {
    	
        Object[] values = { 
        	relationship.getOid() 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Deleting relationship failed, no rows affected.");
            } 
            else {
            	relationship.setOid(null);
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
     * Returns true if the given relationship OID exists in the database.
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
     * Returns list of Relationships for Display purposes
     *  starting at the given first index with the given row count,
     *  sorted by the given sort field and sort order.
     */
    public List<Relationship> display(int firstRow, int rowCount, String sortField, boolean sortAscending, String searchTerm)
        throws DAOException
    {
    	String sqlSortField = "REL_OID";
    	
        if (sortField.equals("oid")) {
        	sqlSortField = "REL_OID";       
        }
        if (sortField.equals("typeFK")) {
        	sqlSortField = "REL_RELATIONSHIP_TYPE_FK";      
        }
        if (sortField.equals("childFK")) {
        	sqlSortField = "REL_CHILD_FK";         
        }
        if (sortField.equals("parentFK")) {
        	sqlSortField = "REL_PARENT_FK";         
        }
        
        String searchWithWildCards = searchTerm;

        String sortDirection = sortAscending ? "ASC" : "DESC";
        String sql = "";

        if (searchTerm.equals("")){
            sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT, sqlSortField, sortDirection);
            Object[] values = {
                    firstRow, 
                    rowCount
            };
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<Relationship> dataList = new ArrayList<Relationship>();

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(connection, sql, false, values);

                //System.out.println("PS = " + preparedStatement.toString());
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapRelationship(resultSet));
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
        else {
            sql = String.format(SQL_DISPLAY_BY_ORDER_AND_LIMIT_WHERE, sqlSortField, sortDirection);
            Object[] values = {
            		searchWithWildCards,
                    firstRow, 
                    rowCount
            };
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            
            List<Relationship> dataList = new ArrayList<Relationship>();

            try {
                connection = daoFactory.getConnection();

                preparedStatement = prepareStatement(connection, sql, false, values);

                //System.out.println("PS = " + preparedStatement.toString());
                
                resultSet = preparedStatement.executeQuery();
            
                while (resultSet.next()) {
                    dataList.add(mapRelationship(resultSet));
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


    }

    /**
     * Returns total amount of rows in table.
     */
    public int count(String searchTerm) throws DAOException {

        String searchWithWildCards = searchTerm;

        Object[] values = {
        		searchWithWildCards
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            connection = daoFactory.getConnection();

            if (searchTerm.equals("")){
                preparedStatement = prepareStatement(connection, SQL_ROW_COUNT, false);
            }
            else {
                preparedStatement = prepareStatement(connection, SQL_ROW_COUNT_WHERE, false, values);
            }

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
    private static Relationship mapRelationship(ResultSet resultSet) throws SQLException {
        return new Relationship(
      		resultSet.getLong("REL_OID"), 
       		resultSet.getString("REL_RELATIONSHIP_TYPE_FK"), 
       		resultSet.getLong("REL_CHILD_FK"), 
       		resultSet.getLong("REL_PARENT_FK")
        );
    }

}
