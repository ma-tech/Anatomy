package daolayer;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import daomodel.JsonNode;
import daomodel.Leaf;
import daomodel.TimedLeaf;

/*
 * This class represents a SQL Database Access Object for the {@link Relation} DTO. 
 *  This DAO should be used as a central point for the mapping between the 
 *  Relation DTO and a SQL database.
 */
public final class LeafDAO {

    // DEPRECATED
    private static final String SQL_LIST_ALL_NODES_BY_ROOT_NAME_OLD =
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        "FROM ANAV_LEAF_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
        "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        "FROM ANAV_GRAND_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "ORDER BY ROOT_DESC, CHILD_ID DESC, CHILD_DESC";

    // DEPRECATED
    private static final String SQL_LIST_ALL_NODES_BY_ROOT_NAME_BY_CHILD_DESC_OLD =
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        "FROM ANAV_LEAF_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
        "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        "FROM ANAV_GRAND_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "ORDER BY CHILD_ID DESC, CHILD_NAME DESC";
        //"ORDER BY ROOT_DESC, CHILD_DESC";

    // DEPRECATED
    private static final String SQL_LIST_ALL_NODES_BY_ROOT_DESC_OLD =
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        " FROM ANAV_LEAF_RELATION " +
        "  JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "  JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "  JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "  JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        " WHERE ANAV_DESC_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
        "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        " FROM ANAV_GRAND_RELATION " +
        "  JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "  JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "  JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "  JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        " WHERE ANAV_DESC_1 = ? " +
        "ORDER BY ROOT_DESC, CHILD_ID DESC, CHILD_DESC";

    // DEPRECATED
    private static final String SQL_LIST_ALL_NODES_BY_ROOT_DESC_BY_CHILD_DESC_OLD =
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        " FROM ANAV_LEAF_RELATION " +
        "  JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "  JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "  JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "  JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        " WHERE ANAV_DESC_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
        "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        " FROM ANAV_GRAND_RELATION " +
        "  JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "  JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "  JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "  JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        " WHERE ANAV_DESC_1 = ? " +
        "ORDER BY ROOT_DESC, CHILD_DESC";
    
    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL_NODES_BY_ROOT_NAME =
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        "FROM ANAV_LEAF_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
        "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        "FROM ANAV_GRAND_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "ORDER BY ROOT_DESC, CHILD_ID DESC, CHILD_DESC";

    private static final String SQL_LIST_ALL_NODES_BY_ROOT_NAME_BY_CHILD_DESC =
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        "FROM ANAV_LEAF_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
        "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        "FROM ANAV_GRAND_RELATION " +
        "JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        "WHERE ANAV_NAME_1 = ? " +
        "ORDER BY CHILD_ID DESC, CHILD_NAME DESC";
        //"ORDER BY ROOT_DESC, CHILD_DESC";

    private static final String SQL_LIST_ALL_NODES_BY_ROOT_DESC =
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        " FROM ANAV_LEAF_RELATION " +
        "  JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "  JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "  JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "  JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        " WHERE ANAV_DESC_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
        "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        " FROM ANAV_GRAND_RELATION " +
        "  JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "  JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "  JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "  JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        " WHERE ANAV_DESC_1 = ? " +
        "ORDER BY ROOT_DESC, CHILD_ID DESC, CHILD_DESC";

    private static final String SQL_LIST_ALL_NODES_BY_ROOT_DESC_BY_CHILD_DESC =
        "SELECT" +
        "  CAST(ANAV_OID_1 as CHAR) as ROOT_OID, " +  
        "  ANAV_NAME_1 as ROOT_NAME, " +
        "  ANAV_DESC_1 as ROOT_DESC, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " +
        "  CAST(ANAV_OID_2 as CHAR) as CHILD_OID, " + 
        "  ANAV_NAME_2 as CHILD_NAME, " +
        "  'LEAF' as CHILD_ID, " +
        "  ANAV_DESC_2 as CHILD_DESC, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " + 
        "  'No Children' as GRAND_CHILD_ID, " + 
        "  'No Children' as GRAND_CHILD_NAME, " +
        "  'No Children' as GRAND_CHILD_DESC " +
        " FROM ANAV_LEAF_RELATION " +
        "  JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_MIN_1 " +
        "  JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_MAX_1 " +
        "  JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_MIN_2 " +
        "  JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_MAX_2 " +
        " WHERE ANAV_DESC_1 = ? " +
        "UNION " +
        "SELECT  " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " +
        "  ANAV_DESC_1, " +
        "  a.STG_NAME AS STG_MIN_1, " +
        "  b.STG_NAME AS STG_MAX_1, " + 
       "  CAST(ANAV_OID_2 as CHAR), " +
        "  ANAV_NAME_2, " +
        "  ANAV_ID_2, " +
        "  ANAV_DESC_2, " +
        "  c.STG_NAME AS STG_MIN_2, " +
        "  d.STG_NAME AS STG_MAX_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        " FROM ANAV_GRAND_RELATION " +
        "  JOIN ANA_STAGE a on a.STG_SEQUENCE = ANAV_STG_MIN_1 " +
        "  JOIN ANA_STAGE b on b.STG_SEQUENCE = ANAV_STG_MAX_1 " +
        "  JOIN ANA_STAGE c on c.STG_SEQUENCE = ANAV_STG_MIN_2 " +
        "  JOIN ANA_STAGE d on d.STG_SEQUENCE = ANAV_STG_MAX_2 " +
        " WHERE ANAV_DESC_1 = ? " +
        "ORDER BY ROOT_DESC, CHILD_DESC";

    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct an Leaf DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    LeafDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    
    // Actions ------------------------------------------------------------------------------------
    // LIST    ------------------------------------------------------------------------------------
    /*
     * Returns a list of All Leafs for the given Root Name, otherwise null.
     */
    public List<Leaf> listAllNodesByRootName(String rootName1, String rootName2) throws DAOException {
        return list(SQL_LIST_ALL_NODES_BY_ROOT_NAME, rootName1, rootName2);
    }
    
    /*
     * Returns a list of All Leafs for the given Root Name ordered by Child Desc, otherwise null.
     */
    public List<Leaf> listAllNodesByRootNameByChildDesc(String rootName1, String rootName2) throws DAOException {
        return list(SQL_LIST_ALL_NODES_BY_ROOT_NAME_BY_CHILD_DESC, rootName1, rootName2);
    }
    
    /*
     * Returns a list of All Leafs for the given Root Description, otherwise null.
     */
    public List<Leaf> listAllNodesByRootDesc(String rootDesc1, String rootDesc2) throws DAOException {
        return list(SQL_LIST_ALL_NODES_BY_ROOT_DESC, rootDesc1, rootDesc2);
    }
    
    /*
     * Returns a list of All Leafs for the given Root Description, otherwise null.
     */
    public List<Leaf> listAllNodesByRootDescByChildDesc(String rootDesc1, String rootDesc2) throws DAOException {
        return list(SQL_LIST_ALL_NODES_BY_ROOT_DESC_BY_CHILD_DESC, rootDesc1, rootDesc2);
    }
    
    
    /*
     * Returns a list of Leafs from the database.
     *  The list is never null and is empty when the database does not contain any 
     *  Leafs.
     */
    public List<Leaf> list(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Leaf> leafs = new ArrayList<Leaf>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                leafs.add(mapLeaf(resultSet));
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return leafs;
    }

    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a Leaf.
     */
    private static Leaf mapLeaf(ResultSet resultSet) throws SQLException {
        return new Leaf(
      		resultSet.getString("ROOT_OID"), 
      		resultSet.getString("ROOT_NAME"),
      		resultSet.getString("ROOT_DESC"), 
      		resultSet.getString("STG_MIN_1"),
      		resultSet.getString("STG_MAX_1"),
      		resultSet.getString("CHILD_OID"), 
      		resultSet.getString("CHILD_ID"), 
      		resultSet.getString("CHILD_NAME"),      		
      		resultSet.getString("CHILD_DESC"), 
      		resultSet.getString("STG_MIN_2"),
      		resultSet.getString("STG_MAX_2"),
      		resultSet.getString("GRAND_CHILD_ID"), 
      		resultSet.getString("GRAND_CHILD_NAME"),
      		resultSet.getString("GRAND_CHILD_DESC"));
    }

    
    /*
     * Convert the Leaf ResultSet to JSON for Ajax calls
     */
    public String convertLeafListToStringJsonChildren(List<Leaf> leafs) {

	    String returnString = "[";

    	Leaf leaf = new Leaf();
    	Leaf prevleaf = new Leaf();
    	Leaf rootLeaf = new Leaf();

		int rowCount = 0;

        Iterator<Leaf> iteratorleaf = leafs.iterator();

        String prevChildName = "";
        String leafType = "ROOT";
        int countChildNames = 0;
        
        while (iteratorleaf.hasNext()) {

        	prevleaf = leaf;
  			leaf = iteratorleaf.next();

  			rowCount++;

  			if (rowCount == 1 ) {
  				
  				rootLeaf = leaf;
  				
  	  			returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
  	  				rootLeaf.getRootName() + 
  			        	    "\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
  			        	  rootLeaf.getRootOid() +
  			        	    "\",\n\"start\": \"" + 
  			        	  rootLeaf.getRootStart() + 
  			        	    "\",\n\"end\": \"" + 
  			        	  rootLeaf.getRootEnd() + 
  			 	    	    "\",\"name\": \"" + 
  			 	    	 rootLeaf.getRootDescription() + 
  			 	    	    "\"},\"children\": [";
  			}
  			
  			if (!leaf.getChildName().equals(prevChildName) && !prevChildName.equals("")) {
       			if ( leaf.getChildId().equals("LEAF")) {
   					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              				prevleaf.getChildName() + 
      		        	    "\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
      		        	    prevleaf.getChildOid() +
      		        	    "\",\n\"start\": \"" + 
      		        	    prevleaf.getChildStart() + 
      		        	    "\",\n\"end\": \"" + 
                            prevleaf.getChildEnd() + 
      		 	    	    "\",\"name\": \"" + 
      		 	    	    prevleaf.getChildDescription() + 
      		 	    	    "\"},\"data\": \"" +
      		 	    	    prevleaf.getChildDescription() + 
      		                "\"},";
       			}
       			else {
   					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              				prevleaf.getChildName() + 
      		        	    "\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
      		        	    prevleaf.getChildId() +
 	    	                "\",\n\"start\": \"" + 
 	    	                prevleaf.getChildStart() + 
 	    	                "\",\n\"end\": \"" + 
 	    	                prevleaf.getChildEnd() + 
      		 	    	    "\",\"name\": \"" + 
      		 	    	    prevleaf.getChildDescription() + 
      		 	    	    "\"},\"data\": \"" +
      		 	    	    prevleaf.getChildDescription() + 
      		                "\"},";
      			}
       			countChildNames = 0;
       		}
       		prevChildName = leaf.getChildName();
   			countChildNames++;
   			
      	}

		if ( prevleaf.getChildId().equals("LEAF")) {
			returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
   				leaf.getChildName() + 
	        	"\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
	        	leaf.getChildOid() +
    	            "\",\n\"start\": \"" + 
    	            leaf.getChildStart() + 
    	            "\",\n\"end\": \"" + 
    	            leaf.getChildEnd() + 
	 	    	"\",\"name\": \"" + 
	 	    	leaf.getChildDescription() + 
	 	    	"\"},\"data\": \"" +
	 	    	leaf.getChildDescription() + 
	            "\"}";
		}
		else {
			returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
   				leaf.getChildName() + 
	        	"\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
	        	leaf.getChildOid() +
    	            "\",\n\"start\": \"" + 
    	            leaf.getChildStart() + 
    	            "\",\n\"end\": \"" + 
    	            leaf.getChildEnd() + 
	 	    	"\",\"name\": \"" + 
	 	    	leaf.getChildDescription() + 
	 	    	"\"},\"data\": \"" +
	 	    	leaf.getChildDescription() + 
	            "(" + 
	            countChildNames + 
	            ")\"}";
		}
		
		if ( rowCount == leafs.size()) {
			returnString = returnString + "]";
		}
		else {
			returnString = returnString + ",";
		}
        
		returnString = returnString + ", \"data\": \"" +
		 	    	 rootLeaf.getRootDescription() + 
		                "\"}]";
        
        return returnString;
    }

    
    /*
     * Convert the Leaf ResultSet to JSON for Ajax calls
     */
    public String convertLeafListToStringJsonLines(List<Leaf> leafs) {

	    String returnString = "";

        int rowCount = 0;
        int grandChildCount = 1;
        
        boolean lastLeaf = false;

        Leaf savedLeaf = new Leaf();

        if (leafs.size() > 0) {
        	
        	Iterator<Leaf> iterator = leafs.iterator();
        	
        	while (iterator.hasNext()) {
        	
        		Leaf leaf = iterator.next();
  		        
        		if ( leaf.getChildId().equals("LEAF")) {
        	        returnString = returnString + 
                        "{\"attr\": {\"ext_id\": \"" +
  		                leaf.getChildName() + 
        	            "\",\"id\": \"li_node_ROOT_Abstract_id" + 
 	    	            leaf.getChildOid() +
 	    	            "\",\"name\": \"" + 
 	    	            leaf.getChildDescription() + 
 	    	            "\",\"start\": \"" + 
 	    	            leaf.getChildStart() + 
 	    	            "\",\"end\": \"" + 
 	    	            leaf.getChildEnd() + 
 	    	            "\"},\"data\": \"" +
                        leaf.getChildDescription() + 
                        "\"};";
        	            lastLeaf = true;
  		        }
  		        else {
  		        
  		        	if (leaf.getChildName().equals(savedLeaf.getChildName())) {
  		        	
  		        		grandChildCount = grandChildCount + 1;
  		        	}
  		        	else {
  		        		
  		        		if (lastLeaf == false){
  		  		  	    
  		        			returnString = returnString + 
   	                            "{\"attr\": {\"ext_id\": \"" +
  		  		                savedLeaf.getChildName() + 
  		        	            "\",\"id\": \"li_node_ROOT_Abstract_id" + 
  		 	    	            savedLeaf.getChildId() +
  		 	    	            "\",\"name\": \"" + 
  		 	    	            savedLeaf.getChildDescription() + 
  		 	    	            "\",\"start\": \"" + 
  		 	    	            savedLeaf.getChildStart() + 
  		 	    	            "\",\"end\": \"" + 
  		 	    	            savedLeaf.getChildEnd() + 
  		 	    	            "\"},\"data\": \"" +
  		                        savedLeaf.getChildDescription() + 
  		                        "(" + 
  		                        Integer.toString(grandChildCount) + 
  		                        ")\"};";
  		        		}
  		        		else {
  		        		
  		        			lastLeaf = false;
  		        		}
  		        		
 	  	                grandChildCount = 1;
  		        	}
  		        }
        		
  		        rowCount = rowCount + 1;
 		        savedLeaf = leaf;
        	}
        	
	        if ( !savedLeaf.getChildId().equals("LEAF")) {
    	  	
	        	returnString = returnString + 
                "{\"attr\": {\"ext_id\": \"" +
	  		    savedLeaf.getChildName() + 
	        	"\",\"id\": \"li_node_ROOT_Abstract_id" + 
	 	    	savedLeaf.getChildId() +
	 	    	"\",\"name\": \"" + 
	 	    	savedLeaf.getChildDescription() + 
	 	    	"\",\"start\": \"" + 
	 	    	savedLeaf.getChildStart() + 
	 	    	"\",\"end\": \"" + 
	 	    	savedLeaf.getChildEnd() + 
	 	    	"\"},\"data\": \"" +
	            savedLeaf.getChildDescription() + 
	            "(" + 
	            Integer.toString(grandChildCount) + 
	            ")\"};";
	        }
        }
        
        return returnString;
    }

    /*
     * Convert the Leaf ResultSet to JSON for Ajax calls
     */
    public String convertLeafListToStringJsonAggregateOld(List<Leaf> leafs) {

	    String returnString = "[";

    	Leaf leaf = new Leaf();
    	Leaf prevleaf = new Leaf();

		int rowCount = 0;

        Iterator<Leaf> iteratorleaf = leafs.iterator();

        String prevChildName = "";
        String leafType = "";
        int countChildNames = 0;
        
        while (iteratorleaf.hasNext()) {

        	prevleaf = leaf;
  			leaf = iteratorleaf.next();

  			rowCount++;
       		
       		if (!leaf.getChildName().equals(prevChildName) && !prevChildName.equals("")) {
       			if ( leaf.getChildId().equals("LEAF")) {
       				if ( prevleaf.getChildId().equals("LEAF")) {
       					leafType = "LEAF";
       					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              				prevleaf.getChildName() + 
      		        	    "\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
      		        	    prevleaf.getChildOid() +
      		        	    "\",\n\"start\": \"" + 
      		        	    prevleaf.getChildStart() + 
      		        	    "\",\n\"end\": \"" + 
                            prevleaf.getChildEnd() + 
      		 	    	    "\",\"name\": \"" + 
      		 	    	    prevleaf.getChildDescription() + 
      		 	    	    "\"},\"data\": \"" +
      		 	    	    prevleaf.getChildDescription() + 
      		                "\",\"state\": \"closed\"},";
       				}
       				else {
       					leafType = "BRANCH";
       					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              				prevleaf.getChildName() + 
      		        	    "\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
      		        	    prevleaf.getChildOid() +
 	    	                "\",\n\"start\": \"" + 
 	    	                prevleaf.getChildStart() + 
 	    	                "\",\n\"end\": \"" + 
 	    	                prevleaf.getChildEnd() + 
      		 	    	    "\",\"name\": \"" + 
      		 	    	    prevleaf.getChildDescription() + 
      		 	    	    "\"},\"data\": \"" +
      		 	    	    prevleaf.getChildDescription() + 
      		                "(" + 
      		                countChildNames + 
      		                ")\",\"state\": \"closed\"},";
       				}
       			}
       			else {
       				if ( prevleaf.getChildId().equals("LEAF")) {
       					leafType = "LEAF";
       					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              				prevleaf.getChildName() + 
      		        	    "\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
      		        	    prevleaf.getChildId() +
 	    	                "\",\n\"start\": \"" + 
 	    	                prevleaf.getChildStart() + 
 	    	                "\",\n\"end\": \"" + 
 	    	                prevleaf.getChildEnd() + 
      		 	    	    "\",\"name\": \"" + 
      		 	    	    prevleaf.getChildDescription() + 
      		 	    	    "\"},\"data\": \"" +
      		 	    	    prevleaf.getChildDescription() + 
      		                "\",\"state\": \"closed\"},";
       				}
       				else {
       					leafType = "BRANCH";
       					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              				prevleaf.getChildName() + 
      		        	    "\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
      		        	    prevleaf.getChildId() +
 	    	                "\",\n\"start\": \"" + 
 	    	                prevleaf.getChildStart() + 
 	    	                "\",\n\"end\": \"" + 
 	    	                prevleaf.getChildEnd() + 
      		 	    	    "\",\"name\": \"" + 
      		 	    	    prevleaf.getChildDescription() + 
      		 	    	    "\"},\"data\": \"" +
      		 	    	    prevleaf.getChildDescription() + 
      		                "(" + 
      		                countChildNames + 
      		                ")\",\"state\": \"closed\"},";
       				}
      			}
       			countChildNames = 0;
       		}
       		prevChildName = leaf.getChildName();
   			countChildNames++;
   			
      	}

		if ( prevleaf.getChildId().equals("LEAF")) {
			leafType = "LEAF";
			returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
   				leaf.getChildName() + 
	        	"\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
	        	leaf.getChildOid() +
    	            "\",\n\"start\": \"" + 
    	            leaf.getChildStart() + 
    	            "\",\n\"end\": \"" + 
    	            leaf.getChildEnd() + 
	 	    	"\",\"name\": \"" + 
	 	    	leaf.getChildDescription() + 
	 	    	"\"},\"data\": \"" +
	 	    	leaf.getChildDescription() + 
	            "\",\"state\": \"closed\"}";
		}
		else {
			leafType = "BRANCH";
			returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
   				leaf.getChildName() + 
	        	"\",\"id\": \"li_node_" + leafType + "_Abstract_id" + 
	        	leaf.getChildOid() +
    	            "\",\n\"start\": \"" + 
    	            leaf.getChildStart() + 
    	            "\",\n\"end\": \"" + 
    	            leaf.getChildEnd() + 
	 	    	"\",\"name\": \"" + 
	 	    	leaf.getChildDescription() + 
	 	    	"\"},\"data\": \"" +
	 	    	leaf.getChildDescription() + 
	            "(" + 
	            countChildNames + 
	            ")\",\"state\": \"closed\"}";
		}
		
		if ( rowCount == leafs.size()) {
			returnString = returnString + "]";
		}
		else {
			returnString = returnString + ",";
		}
    
        return returnString;
    }
	/*
	 * Convert the Leaf ResultSet to JSON for Ajax calls
	 */
	public String convertLeafListToStringJsonAggregate(List<Leaf> leafs) {

		Iterator<Leaf> iteratorleaf = leafs.iterator();

		Leaf leaf = new Leaf();
		LinkedHashMap <String,JsonNode> jsonNodes = new LinkedHashMap<String,JsonNode> ();

		String returnString = "[";

		while (iteratorleaf.hasNext()) {

			leaf = iteratorleaf.next();

			/*
			System.out.println("##PS## LeafDAO leaf.getRootOid() " + leaf.getRootOid());
			System.out.println("##PS## LeafDAO leaf.getRootName() " + leaf.getRootName());
			System.out.println("##PS## LeafDAO leaf.getRootDescription() " + leaf.getRootDescription());
			System.out.println("##PS## LeafDAO leaf.leaf.getChildStart() " + leaf.getChildStart());
			System.out.println("##PS## LeafDAO leaf.leaf.getChildEnd() " + leaf.getChildEnd());
			System.out.println("##PS## LeafDAO leaf.leaf.getChildOid() " + leaf.getChildOid());
			System.out.println("##PS## LeafDAO leaf.leaf.getChildId() " + leaf.getChildId());
			System.out.println("##PS## LeafDAO leaf.leaf.getChildName() " + leaf.getChildName());
			System.out.println("##PS## LeafDAO leaf.leaf.getChildDescription() " + leaf.getChildDescription());
			System.out.println("##PS## LeafDAO leaf.leaf.getGrandChildId() " + leaf.getGrandChildId());
			System.out.println("##PS## LeafDAO leaf.leaf.getGrandChildName() " + leaf.getGrandChildName());
			System.out.println("##PS## LeafDAO leaf.leaf.getGrandChildDescription() " + leaf.getGrandChildDescription());			
			System.out.println(" ");	
			*/	
						
			String extID = leaf.getChildName();
			
			//Seen this node before - increment its child count
			if (jsonNodes.containsKey(extID)) {
				jsonNodes.get(extID).setChildCount(jsonNodes.get(extID).getChildCount()+1);
			}
			//not seen this node before - make a new one
			else {
				String jsonID;
				int childCount;
				if (leaf.getChildId().equals("LEAF") ) {
					if ( !leaf.getGrandChildName().equals("No Children")) {
						//LEAFs should have no children!
						System.out.println("##PS## WARNING LEAF CONFLICT! ");
					}
					//jsonID = "li_node_LEAF_Timed_id" + leaf.getChildOid();
					jsonID = "li_node_id_" + leaf.getChildOid();
					childCount = 0;
				}
				else {
					//jsonID = "li_node_BRANCH_Timed_id" + leaf.getChildOid();
					jsonID = "li_node_id_" + leaf.getChildId();
					childCount = 1;
				}
				String startStage = leaf.getChildStart();
				String endStage = leaf.getChildEnd();
				String name = leaf.getChildDescription();	
				jsonNodes.put(extID, new JsonNode(extID,jsonID,extID,startStage,endStage,name,childCount));
			}
		}
		
		Iterator<String> iteratorJsonNodes = jsonNodes.keySet().iterator();
		while (iteratorJsonNodes.hasNext()) {
			String extID = iteratorJsonNodes.next();
			returnString = returnString + jsonNodes.get(extID).printJsonNodeAbstract();
		}

		//knock off the last "," and replace with "]" to make nice JSON
		returnString = returnString.substring(0, returnString.lastIndexOf(","));
		returnString = returnString + "]";
		
		//System.out.println("##PS## return " + returnString);
		
		return returnString;
	}
}
