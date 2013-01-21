/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunDAOTestAbstractLeafsSubRoutine.java
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

import java.util.ArrayList;
import java.util.List;

import utility.Wrapper;

import daomodel.Leaf;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.LeafDAO;

public class RunDAOTestAbstractLeafsSubRoutine {

	public static void run (DAOFactory daofactory, String emapaId ) throws Exception {

		try {
		
	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine.run", "*", "*");

	        String leafRootName = emapaId;

	        // Obtain DAOs.
	        LeafDAO leafDAO = daofactory.getDAOImpl(LeafDAO.class);

	    	List<Leaf> leafs = new ArrayList<Leaf>();
        	leafs = leafDAO.listAllNodesByRootNameByChildDesc( leafRootName, leafRootName );

	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine.run : " + "===========", "*", "*");
	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine.run : " + emapaId + " - convertLeafListToStringJsonAggregate", "*", "*");
	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine.run : " + "-----------", "*", "*");
	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine.run : " + leafDAO.convertLeafListToStringJsonAggregate(leafs), "*", "*");
		}
		catch (DAOException daoe) {

			daoe.printStackTrace();
		}
	}
}
