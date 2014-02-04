/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
*
* Title:        RunDAOTestUpdate.java
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
* Description:  A runnable class that uses "update" procedures
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

import utility.Wrapper;

import daomodel.Component;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.ComponentDAO;

public class RunDAOTestUpdate {

	public static void run (DAOFactory daofactory) throws Exception {

		try {
			
	        Wrapper.printMessage("RunDAOTestUpdate.run", "*", "*");

	        // Obtain DAOs.
	        ComponentDAO componentDAO = daofactory.getDAOImpl(ComponentDAO.class);

	        Component component11 = new Component( null, "extraembryonic venous system", "EMAPA:99999", "TBD", "TBD", "abstract_anatomy", "", false, "TS13", "TS26", false, "", "UNCHECKED" );

	        Component component12 = new Component( (long) 1, "extraembryonic venous system", "EMAPA:16374", "TBD", "TBD", "abstract_anatomy", "", false, "TS13", "TS26", false, "", "UNCHECKED" );

	        //ComponentDAO
	    	System.out.println("Before CREATE");
	    	System.out.println(component11.toStringJava());
	    	componentDAO.create(component11);
	    	System.out.println("After CREATE");
	    	System.out.println(component11.toStringJava());
	    	
	    	component12 = componentDAO.findByOid((long) componentDAO.maximumOid());
	    	System.out.println("Before DELETE");
	    	System.out.println(component11.toStringJava());
	    	componentDAO.delete(component11);
	    	System.out.println("After DELETE");
	    	System.out.println(component11.toStringJava());
	    	
	    	//System.out.println(component12.toStringJava());
	    	//componentDAO.create(component12);
	    	//System.out.println(component12.toStringJava());
		}
		catch (DAOException daoe) {

			daoe.printStackTrace();
		}
	}
}
