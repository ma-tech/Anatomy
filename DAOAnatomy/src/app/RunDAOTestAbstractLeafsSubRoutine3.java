/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunDAOTestAbstractLeafsSubRoutine3.java
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

import daomodel.Leaf;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daolayer.LeafDAO;


public class RunDAOTestAbstractLeafsSubRoutine3 {
	/*
	 * run Method
	 */
	public static void run (DAOFactory daofactory, String emapaId ) throws Exception {

		try {
	        String leafRootName = emapaId;

	        // Obtain DAOs.
			LeafDAO leafDAO = daofactory.getLeafDAO();

	    	List<Leaf> leafs = new ArrayList<Leaf>();
        	leafs = leafDAO.listAllNodesByRootNameByChildDesc( leafRootName, leafRootName );

			System.out.println("===========");
			System.out.println(emapaId + " - convertLeafListToStringJsonChildren");
			System.out.println("-----------");

 			System.out.println( leafDAO.convertLeafListToStringJsonChildren(leafs) );
		}
		catch (DAOException daoe) {
			daoe.printStackTrace();
		}
	}
}
