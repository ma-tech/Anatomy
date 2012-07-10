package daolayer;

import static daolayer.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daomodel.Leaf;
import daomodel.TimedLeaf;

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

    private static final String SQL_LIST_ALL_NODES_BY_ROOT_NAME_BY_CHILD_DESC =
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
        "ORDER BY CHILD_ID DESC, CHILD_NAME DESC ";
        //" ORDER BY CHILD_ID ";
        //" ORDER BY CHILD_DESC ";

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
    
    private static final String SQL_LIST_ALL_NODES_BY_ROOT_DESC_BY_CHILD_DESC =
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
        " ORDER BY CHILD_DESC ";

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
     * Returns a list of All Leafs for the given Root Name, otherwise null.
     */
    public List<TimedLeaf> listAllTimedNodesByRootNameByChildDesc(String rootName1, String stage1, String rootName2, String stage2) 
    	throws DAOException {
        return list(SQL_LIST_ALL_NODES_BY_ROOT_NAME_BY_CHILD_DESC, rootName1, stage1, rootName2, stage2);
    }
    
    /*
     * Returns a list of All Leafs for the given Root Description, otherwise null.
     */
    public List<TimedLeaf> listAllTimedNodesByRootDescByChildDesc(String rootDesc1, String stage1, String rootDesc2, String stage2) 
    	throws DAOException {
        return list(SQL_LIST_ALL_NODES_BY_ROOT_DESC_BY_CHILD_DESC, rootDesc1, stage1, rootDesc2, stage2);
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
     * Convert the Leaf ResultSet to JSON for Ajax calls
     */
    public String convertLeafListToStringJsonChildren(List<TimedLeaf> timedleafs) {

	    String returnString = "[";

	    TimedLeaf leaf = new TimedLeaf();
	    TimedLeaf prevleaf = new TimedLeaf();
	    TimedLeaf rootLeaf = new TimedLeaf();

		int rowCount = 0;

        Iterator<TimedLeaf> iteratorleaf = timedleafs.iterator();

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
  			        "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
  			        rootLeaf.getRootOid() +
 	    	        "\",\"stage\": \"" + 
 	    	        rootLeaf.getStage() +
  			 	    "\",\"name\": \"" + 
  			 	    rootLeaf.getRootDescription() + 
  			 	    "\"},\"children\": [";
  			}
  			
  			if (!leaf.getChildName().equals(prevChildName) && !prevChildName.equals("")) {
       			if ( leaf.getChildId().equals("LEAF")) {
   					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              			prevleaf.getChildName() + 
      		        	"\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
      		        	prevleaf.getChildOid() +
 	    	            "\",\"stage\": \"" + 
 	    	            prevleaf.getStage() +
      		 	    	"\",\"name\": \"" + 
      		 	    	prevleaf.getChildDescription() + 
      		 	    	"\"},\"data\": \"" +
      		 	    	prevleaf.getChildDescription() + 
      		            "\"},";
       			}
       			else {
   					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              			prevleaf.getChildName() + 
      		        	"\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
      		        	prevleaf.getChildId() +
 	    	            "\",\"stage\": \"" + 
 	    	            prevleaf.getStage() +
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
	        	"\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
	        	leaf.getChildOid() +
 	    	    "\",\"stage\": \"" + 
 	    	    leaf.getStage() +
	 	    	"\",\"name\": \"" + 
	 	    	leaf.getChildDescription() + 
	 	    	"\"},\"data\": \"" +
	 	    	leaf.getChildDescription() + 
	            "\"}";
		}
		else {
			returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
   				leaf.getChildName() + 
	        	"\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
	        	leaf.getChildOid() +
 	    	    "\",\"stage\": \"" + 
 	    	    leaf.getStage() +
	 	    	"\",\"name\": \"" + 
	 	    	leaf.getChildDescription() + 
	 	    	"\"},\"data\": \"" +
	 	    	leaf.getChildDescription() + 
	            "(" + 
	            countChildNames + 
	            ")\"}";
		}
		
		if ( rowCount == timedleafs.size()) {
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
    public String convertLeafListToStringJsonLines(List<TimedLeaf> timedleafs) {

	    String returnString = "";

        int rowCount = 0;
        int grandChildCount = 1;
        
        boolean lastLeaf = false;

        TimedLeaf savedLeaf = new TimedLeaf();

        if (timedleafs.size() > 0) {
        	
        	Iterator<TimedLeaf> iterator = timedleafs.iterator();
        	
        	while (iterator.hasNext()) {
        	
        		TimedLeaf leaf = iterator.next();
  		        
        		if ( leaf.getChildId().equals("LEAF")) {
        	        returnString = returnString + 
                        "{\"attr\": {\"ext_id\": \"" +
  		                leaf.getChildName() + 
        	            "\",\"id\": \"li_node_ROOT_Timed_id" + 
 	    	            leaf.getChildOid() +
 	    	            "\",\"name\": \"" + 
 	    	            leaf.getChildDescription() + 
            	    	"\",\"stage\": \"" + 
            	    	leaf.getStage() +
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
  		        	            "\",\"id\": \"li_node_ROOT_Timed_id" + 
  		 	    	            savedLeaf.getChildId() +
  		 	    	            "\",\"name\": \"" + 
  		 	    	            savedLeaf.getChildDescription() + 
            	    	        "\",\"stage\": \"" + 
            	    	        savedLeaf.getStage() +
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
	        	"\",\"id\": \"li_node_ROOT_Timed_id" + 
	 	    	savedLeaf.getChildId() +
	 	    	"\",\"name\": \"" + 
	 	    	savedLeaf.getChildDescription() + 
            	"\",\"stage\": \"" + 
            	savedLeaf.getStage() +
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

        Iterator<TimedLeaf> iteratortimedleaf = timedleafs.iterator();

		TimedLeaf timedleaf = new TimedLeaf();
    	TimedLeaf prevleaf = new TimedLeaf();

		int rowCount = 0;
        int countChildNames = 0;

        String prevChildName = "";
        String leafType = "";
	    String returnString = "[";

        while (iteratortimedleaf.hasNext()) {

        	prevleaf = timedleaf;
  			timedleaf = iteratortimedleaf.next();

  			rowCount++;
       		
       		if (!timedleaf.getChildName().equals(prevChildName) && !prevChildName.equals("")) {
       			
       			if ( timedleaf.getChildId().equals("LEAF")) {
       				if ( prevleaf.getChildId().equals("LEAF")) {
       					leafType = "LEAF";

       					returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
              					prevleaf.getChildName() + 
      		        	            "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
      		        	          prevleaf.getChildOid() +
            	    	            "\",\"stage\": \"" + 
            	    	            prevleaf.getStage() +
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
      		        	            "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
      		        	          prevleaf.getChildOid() +
            	    	            "\",\"stage\": \"" + 
            	    	            prevleaf.getStage() +
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
      		        	            "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
      		        	          prevleaf.getChildId() +
            	    	            "\",\"stage\": \"" + 
            	    	            prevleaf.getStage() +
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
      		        	            "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
      		        	          prevleaf.getChildId() +
            	    	            "\",\"stage\": \"" + 
            	    	            prevleaf.getStage() +
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
       		prevChildName = timedleaf.getChildName();
   			countChildNames++;
      	}

		if ( prevleaf.getChildId().equals("LEAF")) {
			leafType = "LEAF";
			
			returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
   					timedleaf.getChildName() + 
	        	            "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
	        	          timedleaf.getChildOid() +
	    	            "\",\"stage\": \"" + 
	    	            timedleaf.getStage() +
	 	    	            "\",\"name\": \"" + 
	 	    	         timedleaf.getChildDescription() + 
	 	    	            "\"},\"data\": \"" +
	 	    	         timedleaf.getChildDescription() + 
	                        "\",\"state\": \"closed\"}";
		}
		else {
			leafType = "BRANCH";
			
			returnString = returnString + "{\"attr\": {\"ext_id\": \"" +
   					timedleaf.getChildName() + 
	        	            "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
	        	          timedleaf.getChildOid() +
	    	            "\",\"stage\": \"" + 
	    	            timedleaf.getStage() +
	 	    	            "\",\"name\": \"" + 
	 	    	         timedleaf.getChildDescription() + 
	 	    	            "\"},\"data\": \"" +
	 	    	         timedleaf.getChildDescription() + 
	                        "(" + 
	                        countChildNames + 
	                        ")\",\"state\": \"closed\"}";
		}

			if ( rowCount == timedleafs.size()) {
				returnString = returnString + "]";
			}
			else {
				returnString = returnString + ",";
			}
		
		return returnString;
    }
}
