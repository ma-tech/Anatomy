package DAOLayer;

import static DAOLayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DAOModel.TimedLeaf;

/*
 * This class represents a SQL Database Access Object for the {@link Relation} DTO. 
 *  This DAO should be used as a central point for the mapping between the 
 *  Relation DTO and a SQL database.
 */
public final class TimedLeafDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_LIST_ALL_NODES_BY_ROOT_NAME =
        "SELECT  " +
        "  ANAV_STAGE AS STAGE, " +
        "  CAST(ANAV_OID_1 AS CHAR) AS ROOT_OID, " + 
        "  ANAV_NAME_1 AS ROOT_NAME, " +
        "  ANAV_DESC_1 AS ROOT_DESC, " +
        "  CAST(ANAV_OID_2 AS CHAR) AS CHILD_OID, " + 
        "  'LEAF' AS CHILD_ID, " +
        "  ANAV_NAME_2 AS CHILD_NAME, " + 
        "  ANAV_DESC_2 AS CHILD_DESC, " + 
        "  'No Children' AS GRAND_CHILD_ID, " + 
        "  'No Children' AS GRAND_CHILD_NAME, " +
        "  'No Children' AS GRAND_CHILD_DESC " +
        " FROM ANAV_TIMED_LEAF_RELATION " +
        " WHERE ANAV_NAME_1 = ?  " +
        " AND ANAV_STAGE = ?  " +
        "UNION " +
        "SELECT " +
        "  STG_NAME AS STAGE, " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " + 
        "  ANAV_DESC_1, " +
        "  CAST(ANAV_OID_2 AS CHAR), " + 
        "  ANAV_ID_2, " +
        "  ANAV_NAME_2, " +
        "  ANAV_DESC_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        " FROM ANAV_TIMED_GRAND_RELATION " +
        " JOIN ANA_STAGE ON STG_OID = ANAV_STAGE " +
        " WHERE ANAV_NAME_1 = ? " +
        " AND STG_NAME = ? " +
        " ORDER BY CHILD_ID DESC, CHILD_NAME DESC ";

    private static final String SQL_LIST_ALL_NODES_BY_ROOT_DESC =
        "SELECT  " +
        "  ANAV_STAGE AS STAGE, " +
        "  CAST(ANAV_OID_1 AS CHAR) AS ROOT_OID, " + 
        "  ANAV_NAME_1 AS ROOT_NAME, " +
        "  ANAV_DESC_1 AS ROOT_DESC, " +
        "  CAST(ANAV_OID_2 AS CHAR) AS CHILD_OID, " + 
        "  'LEAF' AS CHILD_ID, " +
        "  ANAV_NAME_2 AS CHILD_NAME, " + 
        "  ANAV_DESC_2 AS CHILD_DESC, " + 
        "  'No Children' AS GRAND_CHILD_ID, " + 
        "  'No Children' AS GRAND_CHILD_NAME, " +
        "  'No Children' AS GRAND_CHILD_DESC " +
        " FROM ANAV_TIMED_LEAF_RELATION " +
        " WHERE ANAV_DESC_1 = ?  " +
        " AND ANAV_STAGE = ?  " +
        "UNION " +
        "SELECT " +
        "  STG_NAME AS STAGE, " +
        "  ANAV_ID_1, " +
        "  ANAV_NAME_1, " + 
        "  ANAV_DESC_1, " +
        "  CAST(ANAV_OID_2 AS CHAR), " + 
        "  ANAV_ID_2, " +
        "  ANAV_NAME_2, " +
        "  ANAV_DESC_2, " +
        "  ANAV_ID_3, " +
        "  ANAV_NAME_3, " +
        "  ANAV_DESC_3 " +
        " FROM ANAV_TIMED_GRAND_RELATION " +
        " JOIN ANA_STAGE ON STG_OID = ANAV_STAGE " +
        " WHERE ANAV_DESC_1 = ? " +
        " AND STG_NAME = ? " +
        " ORDER BY CHILD_ID DESC, CHILD_NAME DESC ";
    
    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct an Leaf DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    TimedLeafDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    
    // Actions ------------------------------------------------------------------------------------
    // LIST    ------------------------------------------------------------------------------------
    /*
     * Returns a list of All Leafs for the given Root Name, otherwise null.
     */
    public List<TimedLeaf> listAllTimedNodesByRootName(String rootName1, String stage1, String rootName2, String stage2) 
    	throws DAOException {
        return list(SQL_LIST_ALL_NODES_BY_ROOT_NAME, rootName1, stage1, rootName2, stage2);
    }
    
    /*
     * Returns a list of All Leafs for the given Root Description, otherwise null.
     */
    public List<TimedLeaf> listAllTimedNodesByRootDesc(String rootDesc1, String stage1, String rootDesc2, String stage2) 
    	throws DAOException {
        return list(SQL_LIST_ALL_NODES_BY_ROOT_DESC, rootDesc1, stage1, rootDesc2, stage2);
    }
    
    
    /*
     * Returns a list of Leafs from the database.
     *  The list is never null and is empty when the database does not contain any 
     *  Leafs.
     */
    public List<TimedLeaf> list(String sql, Object... values) 
    	throws DAOException {
        
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TimedLeaf> timedleafs = new ArrayList<TimedLeaf>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(daoFactory.isDebug(), daoFactory.getSqloutput(), connection, sql, false, values);
            
            resultSet = preparedStatement.executeQuery();
        
            while (resultSet.next()) {
                timedleafs.add(mapTimedLeaf(resultSet));
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
        finally {
            close(connection, preparedStatement, resultSet);
        }

        return timedleafs;
    }

    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Map the current row of the given ResultSet to a Leaf.
     */
    private static TimedLeaf mapTimedLeaf(ResultSet resultSet) 
    	throws SQLException {
        
    	return new TimedLeaf(
       		resultSet.getString("STAGE"), 
      		resultSet.getString("ROOT_OID"), 
      		resultSet.getString("ROOT_NAME"),
      		resultSet.getString("ROOT_DESC"), 
      		resultSet.getString("CHILD_OID"), 
      		resultSet.getString("CHILD_ID"), 
      		resultSet.getString("CHILD_NAME"),      		
      		resultSet.getString("CHILD_DESC"), 
      		resultSet.getString("GRAND_CHILD_ID"), 
      		resultSet.getString("GRAND_CHILD_NAME"),
      		resultSet.getString("GRAND_CHILD_DESC"));
    }

    
    /*
     * Convert the Leaf ResultSet to JSON.
     */
    public String convertLeafListToStringJson(List<TimedLeaf> timedleafs) {

	    String returnString = "";

        int rowCount = 0;
        int grandChildCount = 1;
        
        boolean lastLeaf = false;

        TimedLeaf savedLeaf = new TimedLeaf();

        if (timedleafs.size() > 0) {
        	Iterator<TimedLeaf> iterator = timedleafs.iterator();
        	while (iterator.hasNext()) {
        		TimedLeaf timedleaf = iterator.next();
  		        if ( rowCount == 0) {
  		        	returnString = returnString + 
  		        		"\"children\": [\n{\n\"attr\": {\n\"ext_id\": \"" +
  		        		timedleaf.getRootName() + 
                        "\", \"id\": \"li_node_ROOT_Timed_id" +
                        timedleaf.getRootOid() +
 	    	            "\",\"stage\": \"" + 
        	            timedleaf.getStage() +
                        "\", \"name\": \"" +
                        timedleaf.getRootDescription() +
                        "\"},\n\"children\": [\n";
  		        }
  		        if ( timedleaf.getChildId().equals("LEAF")) {
  		        	if ( rowCount == 1 ){
  	        	        returnString = returnString + 
  	                          ",";
  		        	}
        	        returnString = returnString + 
                        "{\n\"attr\": {\n\"ext_id\": \"" +
                        timedleaf.getChildName() + 
        	            "\",\n\"id\": \"li_node_LEAF_Timed_id" + 
        	            timedleaf.getChildOid() +
 	    	            "\",\"stage\": \"" + 
        	            timedleaf.getStage() +
 	    	            "\",\n\"name\": \"" + 
 	    	            timedleaf.getChildDescription() + 
 	    	            "\"\n},\n\"data\": \"" +
 	    	            timedleaf.getChildDescription() + 
                        "\"\n},";
        	            lastLeaf = true;
  		        }
  		        else {
  		        	if (timedleaf.getChildName().equals(savedLeaf.getChildName())) {
  		        		grandChildCount = grandChildCount + 1;
  		        	}
  		        	else {
  		        		if (lastLeaf == false){
  		  		  	        returnString = returnString + 
   	                            ",\n{\n\"attr\": {\n\"ext_id\": \"" +
  		  		                savedLeaf.getChildName() + 
  		        	            "\",\n\"id\": \"li_node_BRANCH_Timed_id" + 
  		 	    	            savedLeaf.getChildId() +
        	    	            "\",\"stage\": \"" + 
                	            savedLeaf.getStage() +
  		 	    	            "\",\n\"name\": \"" + 
  		 	    	            savedLeaf.getChildDescription() + 
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
 		        savedLeaf = timedleaf;
        	}
	        if ( !savedLeaf.getChildId().equals("LEAF")) {
                returnString = returnString + 
                ",\n{\n\"attr\": {\n\"ext_id\": \"" +
	  		    savedLeaf.getChildName() + 
	        	"\",\n\"id\": \"li_node_BRANCH_Timed_id" + 
	 	    	savedLeaf.getChildId() +
 	    	    "\",\"stage\": \"" + 
        	    savedLeaf.getStage() +
	 	    	"\",\n\"name\": \"" + 
	 	    	savedLeaf.getChildDescription() + 
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
    public String convertLeafListToStringJsonLines(List<TimedLeaf> timedleafs) {

	    String returnString = "";

        int rowCount = 0;
        int grandChildCount = 1;
        
        boolean lastLeaf = false;

        TimedLeaf savedLeaf = new TimedLeaf();

        if (timedleafs.size() > 0) {
        	Iterator<TimedLeaf> iterator = timedleafs.iterator();
        	while (iterator.hasNext()) {
        		TimedLeaf timedleaf = iterator.next();
  		        if ( timedleaf.getChildId().equals("LEAF")) {
        	        returnString = returnString + 
                        "{\"attr\": {\"ext_id\": \"" +
                        timedleaf.getChildName() + 
        	            "\",\"id\": \"li_node_LEAF_Timed_id" + 
        	            timedleaf.getChildOid() +
 	    	            "\",\"stage\": \"" + 
        	            timedleaf.getStage() +
 	    	            "\",\"name\": \"" + 
   	    	            timedleaf.getChildDescription() + 
 	    	            "\"},\"data\": \"" +
 	    	            timedleaf.getChildDescription() + 
                        "\"};";
        	            lastLeaf = true;
  		        }
  		        else {
  		        	if (timedleaf.getChildName().equals(savedLeaf.getChildName())) {
  		        		grandChildCount = grandChildCount + 1;
  		        	}
  		        	else {
  		        		if (lastLeaf == false){
  		  		  	        returnString = returnString + 
   	                            "{\"attr\": {\"ext_id\": \"" +
  		  		                savedLeaf.getChildName() + 
  		        	            "\",\"id\": \"li_node_BRANCH_Timed_id" + 
  		 	    	            savedLeaf.getChildId() +
        	    	            "\",\"stage\": \"" + 
                	            savedLeaf.getStage() +
  		 	    	            "\",\"name\": \"" + 
  		 	    	            savedLeaf.getChildDescription() + 
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
 		        savedLeaf = timedleaf;
        	}
	        if ( !savedLeaf.getChildId().equals("LEAF")) {
    	  		returnString = returnString + 
                "{\"attr\": {\"ext_id\": \"" +
	  		    savedLeaf.getChildName() + 
	        	"\",\"id\": \"li_node_BRANCH_Timed_id" + 
	 	    	savedLeaf.getChildId() +
   	            "\",\"stage\": \"" + 
   	            savedLeaf.getStage() +
	 	    	"\",\"name\": \"" + 
	 	    	savedLeaf.getChildDescription() + 
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
    public String convertLeafListToStringJsonAggregate(List<TimedLeaf> timedleafs) {

	    String returnString = "[";

        int rowCount = 0;
        int grandChildCount = 1;
        
        boolean lastLeaf = false;

        TimedLeaf savedLeaf = new TimedLeaf();

        if (timedleafs.size() > 0) {
        	Iterator<TimedLeaf> iterator = timedleafs.iterator();
        	
        	while (iterator.hasNext()) {
        		TimedLeaf timedleaf = iterator.next();
  		        rowCount = rowCount + 1;

  		        if ( timedleaf.getChildId().equals("LEAF") ) {
        	        returnString = returnString + 
                        "{\"attr\": {\"ext_id\": \"" +
                        timedleaf.getChildName() + 
        	            "\",\"id\": \"li_node_LEAF_Timed_id" + 
        	            timedleaf.getChildOid() +
 	    	            "\",\"stage\": \"" + 
        	            timedleaf.getStage() +
 	    	            "\",\"name\": \"" + 
   	    	            timedleaf.getChildDescription() + 
 	    	            "\"},\"data\": \"" +
 	    	            timedleaf.getChildDescription() + 
                        "\"}";
        	            if ( timedleafs.size() != rowCount ) {
  	        	            returnString = returnString + ",";
  		        	    }
        	            lastLeaf = true;
  		        }
  		        
  		        if ( !timedleaf.getChildId().equals("LEAF") ) {
  		        	if (timedleaf.getChildName().equals(savedLeaf.getChildName())) {
  		        		grandChildCount = grandChildCount + 1;
  		        	}
  		        	else {
  		        		if (lastLeaf == false && rowCount > 1){
  		  		  	        returnString = returnString + 
   	                            "{\"attr\": {\"ext_id\": \"" +
  		  		                savedLeaf.getChildName() + 
  		        	            "\",\"id\": \"li_node_BRANCH_Timed_id" + 
  		 	    	            savedLeaf.getChildId() +
        	    	            "\",\"stage\": \"" + 
                	            savedLeaf.getStage() +
  		 	    	            "\",\"name\": \"" + 
  		 	    	            savedLeaf.getChildDescription() + 
  		 	    	            "\"},\"data\": \"" +
  		                        savedLeaf.getChildDescription() + 
  		                        "(" + 
  		                        Integer.toString(grandChildCount) + 
  		                        ")\",\"state\": \"closed\"},";
  		        		}
  		        		else {
  		        			lastLeaf = false;
  		        		}
 	  	                grandChildCount = 1;
  		        	}
  	 		        savedLeaf = timedleaf;

    		        if ( rowCount == timedleafs.size()) {
        	  		    returnString = returnString + 
                        "{\"attr\": {\"ext_id\": \"" +
	  		            savedLeaf.getChildName() + 
	        	        "\",\"id\": \"li_node_BRANCH_Timed_id" + 
	 	    	        savedLeaf.getChildId() +
      	                "\",\"stage\": \"" + 
      	                savedLeaf.getStage() +
	    	    	    "\",\"name\": \"" + 
	 	         	    savedLeaf.getChildDescription() + 
	 	    	        "\"},\"data\": \"" +
	                    savedLeaf.getChildDescription() + 
    	                "(" + 
	                    Integer.toString(grandChildCount) + 
  		                ")\",\"state\": \"closed\"}";
  		            }

 		        }
        	}
        }
        
        return returnString + "]";
    
    }
}
