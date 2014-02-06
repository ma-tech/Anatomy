/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
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
* Version:      1
*
* Description:  A runnable class that accesses "Leafs" by EMAPA Id
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

public class RunDAOTestAbstractLeafsSubRoutine3 {

	public static void run (DAOFactory daofactory, String emapaId ) throws Exception {

		try {
			
	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine3.run", "*", "*");

	        String leafRootName = emapaId;

	        // Obtain DAOs.
	        LeafDAO leafDAO = daofactory.getDAOImpl(LeafDAO.class);

	    	List<Leaf> leafs = new ArrayList<Leaf>();
        	leafs = leafDAO.listAllNodesByRootNameByChildDesc( leafRootName, leafRootName );

	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine3.run : ===========", "*", "*");
	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine3.run : " + emapaId + " - convertLeafListToStringJsonChildren", "*", "*");
	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine3.run : -----------", "*", "*");
	        Wrapper.printMessage("RunDAOTestAbstractLeafsSubRoutine3.run : " + leafDAO.convertLeafListToStringJsonChildren(leafs), "*", "*");
		}
		catch (DAOException daoe) {

			daoe.printStackTrace();
		}
	}
}
