package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DAOModel.Leaf;

/*
 * This class represents a SQL Database Access Object for the {@link Relation} DTO. 
 *  This DAO should be used as a central point for the mapping between the 
 *  Relation DTO and a SQL database.
 */
public final class LeafDAO {

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
        "ORDER BY ROOT_DESC, CHILD_DESC";

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
     * Convert the Leaf ResultSet to JSON.
     */
    public String convertLeafListToStringJson(List<Leaf> leafs) {

	    String returnString = "";

        int rowCount = 0;
        int grandChildCount = 1;
        
        boolean lastLeaf = false;

        Leaf savedLeaf = new Leaf();

        if (leafs.size() > 0) {
        	Iterator<Leaf> iterator = leafs.iterator();
        	while (iterator.hasNext()) {
        		Leaf leaf = iterator.next();
  		        if ( rowCount == 0) {
  		        	returnString = returnString + 
  		        		"\"children\": [\n{\n\"attr\": {\n\"ext_id\": \"" +
                        leaf.getRootName() + 
                        "\", \"id\": \"li_node_ROOT_Abstract_id" +
                        leaf.getRootOid() +
                        "\", \"name\": \"" +
                        leaf.getRootDescription() +
                        "\", \"start\": \"" +
                        leaf.getRootStart() +
                        "\", \"end\": \"" +
                        leaf.getRootEnd() +
                        "\"},\n\"children\": [\n";
  		        }
  		        if ( leaf.getChildId().equals("LEAF")) {
  		        	if ( rowCount == 1 ){
  	        	        returnString = returnString + 
  	                          ",";
  		        	}
        	        returnString = returnString + 
                        "{\n\"attr\": {\n\"ext_id\": \"" +
  		                leaf.getChildName() + 
        	            "\",\n\"id\": \"li_node_ROOT_Abstract_id" + 
 	    	            leaf.getChildOid() +
 	    	            "\",\n\"name\": \"" + 
 	    	            leaf.getChildDescription() + 
 	    	            "\",\n\"start\": \"" + 
 	    	            leaf.getChildStart() + 
 	    	            "\",\n\"end\": \"" + 
 	    	            leaf.getChildEnd() + 
 	    	            "\"\n},\n\"data\": \"" +
                        leaf.getChildDescription() + 
                        "\"\n},";
        	            lastLeaf = true;
  		        }
  		        else {
  		        	if (leaf.getChildName().equals(savedLeaf.getChildName())) {
  		        		grandChildCount = grandChildCount + 1;
  		        	}
  		        	else {
  		        		if (lastLeaf == false){
  		  		  	        returnString = returnString + 
   	                            ",\n{\n\"attr\": {\n\"ext_id\": \"" +
  		  		                savedLeaf.getChildName() + 
  		        	            "\",\n\"id\": \"li_node_ROOT_Abstract_id" + 
  		 	    	            savedLeaf.getChildId() +
  		 	    	            "\",\n\"name\": \"" + 
  		 	    	            savedLeaf.getChildDescription() + 
  		 	    	            "\",\n\"start\": \"" + 
  		 	    	            savedLeaf.getChildStart() + 
  		 	    	            "\",\n\"end\": \"" + 
  		 	    	            savedLeaf.getChildEnd() + 
  		 	    	            "\"\n},\n\"data\": \"" +
  		                        savedLeaf.getChildDescription() + 
  		                        "(" + 
  		                        Integer.toString(grandChildCount) + 
  		                        ")\"\n},";
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
                ",\n{\n\"attr\": {\n\"ext_id\": \"" +
	  		    savedLeaf.getChildName() + 
	        	"\",\n\"id\": \"li_node_ROOT_Abstract_id" + 
	 	    	savedLeaf.getChildId() +
	 	    	"\",\n\"name\": \"" + 
	 	    	savedLeaf.getChildDescription() + 
	 	    	"\",\n\"start\": \"" + 
	 	    	savedLeaf.getChildStart() + 
	 	    	"\",\n\"end\": \"" + 
	 	    	savedLeaf.getChildEnd() + 
	 	    	"\"\n},\n\"data\": \"" +
	            savedLeaf.getChildDescription() + 
	            "(" + 
	            Integer.toString(grandChildCount) + 
	            ")\"\n}\n],\n\"data\": \"" +
                savedLeaf.getRootDescription() +
                "\"\n}\n],";
	        }
	        else {
                returnString = returnString + 
       	            "\n],\n\"data\": \"" +
                    savedLeaf.getRootDescription() +
                    "\"\n}\n],";
	        }
        }
        
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
}
