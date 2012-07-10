/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunDAOTestTimedLeafs.java
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
* Description:  A Main Class that reads in a OBO file and Writes it out again 
*
* Required Files:
*                2. obo.properties file contains the OBO file access attributes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daomodel.TimedLeaf;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daolayer.TimedLeafDAO;


public class RunDAOTestTimedLeafs {
	/*
	 * run Method
	 */
	public static void run (DAOFactory daofactory, String emapId, String stage) throws IOException {

		try {
	        // Obtain DAOs.
			TimedLeafDAO timedleafDAO = daofactory.getTimedLeafDAO();

			int rowCount = 0;
	        String timedleafRootName = emapId;
	        String timedleafStage = stage;
	        
	    	List<TimedLeaf> timedleafs = new ArrayList<TimedLeaf>();
	    	TimedLeaf timedleaf = new TimedLeaf();
	    	TimedLeaf prevleaf = new TimedLeaf();

        	timedleafs = timedleafDAO.listAllTimedNodesByRootNameByChildDesc( timedleafRootName, timedleafStage, timedleafRootName, timedleafStage );

            Iterator<TimedLeaf> iteratortimedleaf = timedleafs.iterator();

            String prevChildName = "";
            String leafType = "";
            int countChildNames = 0;
            
  			System.out.println("==========================");
  			System.out.println(emapId + " ; Stage : " + stage);
  			System.out.println("--------------------------");

  			System.out.println("[");

            while (iteratortimedleaf.hasNext()) {

            	prevleaf = timedleaf;
      			timedleaf = iteratortimedleaf.next();

      			rowCount++;
           		
      			//System.out.println("LEAF NO. : " + rowCount);
           		
           		if (!timedleaf.getChildName().equals(prevChildName) && !prevChildName.equals("")) {
           			
           			//System.out.println("prevChildName : " + prevChildName + "; countChildIds : " + countChildNames);
           			
           			if ( timedleaf.getChildId().equals("LEAF")) {
           				if ( prevleaf.getChildId().equals("LEAF")) {
           					leafType = "LEAF";
                  			System.out.println("{\"attr\": {\"ext_id\": \"" +
                  				prevleaf.getChildName() + 
          		        	    "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
          		        	    prevleaf.getChildOid() +
                	    	    "\",\"stage\": \"" + 
                	    	    prevleaf.getStage() +
          		 	    	    "\",\"name\": \"" + 
          		 	    	    prevleaf.getChildDescription() + 
          		 	    	    "\"},\"data\": \"" +
          		 	    	    prevleaf.getChildDescription() + 
          		                "\",\"state\": \"closed\"},");
           				}
           				else {
           					leafType = "BRANCH";
                  			System.out.println("{\"attr\": {\"ext_id\": \"" +
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
          		                ")\",\"state\": \"closed\"},");
           				}
           			}
           			else {
           				if ( prevleaf.getChildId().equals("LEAF")) {
           					leafType = "LEAF";
                  			System.out.println("{\"attr\": {\"ext_id\": \"" +
                  				prevleaf.getChildName() + 
          		        	    "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
          		        	    prevleaf.getChildId() +
                	    	    "\",\"stage\": \"" + 
                	    	    prevleaf.getStage() +
          		 	    	    "\",\"name\": \"" + 
          		 	    	    prevleaf.getChildDescription() + 
          		 	    	    "\"},\"data\": \"" +
          		 	    	    prevleaf.getChildDescription() + 
          		                "\",\"state\": \"closed\"},");
           				}
           				else {
           					leafType = "BRANCH";
                  			System.out.println("{\"attr\": {\"ext_id\": \"" +
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
          		                ")\",\"state\": \"closed\"},");
           				}
          			}

           			countChildNames = 0;
           		}
           		
           		prevChildName = timedleaf.getChildName();
       			countChildNames++;
          	}

			if ( prevleaf.getChildId().equals("LEAF")) {
				leafType = "LEAF";
	      		System.out.println("{\"attr\": {\"ext_id\": \"" +
	       			timedleaf.getChildName() + 
			        "\",\"id\": \"li_node_" + leafType + "_Timed_id" + 
			        timedleaf.getChildOid() +
	    	    	"\",\"stage\": \"" + 
	    	    	timedleaf.getStage() +
			 	    "\",\"name\": \"" + 
			 	    timedleaf.getChildDescription() + 
			 	    "\"},\"data\": \"" +
			 	    timedleaf.getChildDescription() + 
			        "\",\"state\": \"closed\"}");
			}
			else {
				leafType = "BRANCH";
      			System.out.println("{\"attr\": {\"ext_id\": \"" +
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
			        ")\",\"state\": \"closed\"}");
			}

			if ( rowCount == timedleafs.size()) {
       			System.out.println("]");
   			}
   			else {
       			System.out.println(",");
   			}

       		/*
       		leafTree = timedleafDAO.convertLeafListToStringJson(timedleafs);

       		leafTree = timedleafDAO.convertLeafListToStringJsonLines(timedleafs);
       		*/
		}
		catch (DAOException daoe) {
			daoe.printStackTrace();
		}
	}
}
