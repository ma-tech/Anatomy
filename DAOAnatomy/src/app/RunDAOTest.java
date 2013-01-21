/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunDAOTest.java
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

import utility.ObjectConverter;
import utility.Wrapper;

import daomodel.Node;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.NodeDAO;

public class RunDAOTest {

	public static void run (DAOFactory daofactory) throws Exception {

		try {
			
	        Wrapper.printMessage("RunDAOTest.run", "*", "*");

	        // Obtain DAOs.
	        NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
	        //NodeDAO nodeDAO = daofactory.getNodeDAOJDBC();

	        // Find Node with OID = 33
	        int oid = 33;
	        
	        if ( nodeDAO.existOid(ObjectConverter.convert(oid, Long.class)) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Node with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	Node node = nodeDAO.findByOid(ObjectConverter.convert(oid, Long.class));
		        Wrapper.printMessage("RunDAOTest.run : " + node.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Node with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
		}
		catch (DAOException daoe) {
			daoe.printStackTrace();
		}
	}
}
