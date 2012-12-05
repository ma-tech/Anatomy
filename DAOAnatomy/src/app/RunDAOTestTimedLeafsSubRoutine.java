/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunDAOTestTimedLeafsSubRoutine.java
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


public class RunDAOTestTimedLeafsSubRoutine {
	/*
	 * run Method
	 */
	public static void run (DAOFactory daofactory, String emapId, String stage) throws Exception {

		try {
			TimedLeafDAO timedleafDAO = daofactory.getTimedLeafDAO();

	    	List<TimedLeaf> timedleafs = new ArrayList<TimedLeaf>();

        	timedleafs = timedleafDAO.listAllTimedNodesByRootNameByChildDesc( emapId, stage, emapId, stage );

  			System.out.println("==========================");
  			System.out.println(emapId + " ; Stage : " + stage + " - convertLeafListToStringJsonAggregate");
  			System.out.println("--------------------------");

  			System.out.println( timedleafDAO.convertLeafListToStringJsonAggregate(timedleafs) );
		}
		catch (DAOException daoe) {
			daoe.printStackTrace();
		}
	}
}
