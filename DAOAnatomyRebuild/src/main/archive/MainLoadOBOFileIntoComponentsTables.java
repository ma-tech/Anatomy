/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
*
* Title:        MainLoadOBOFileIntoComponentsTables.java
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
* Description:  A Main Executable Class that Loads an OBOFile Into Components Tables in the 
*                Anatomy Database 
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package main.archive;

import utility.Wrapper;

import java.util.List;
import java.util.ArrayList;

import obolayer.OBOComponentAccess;
import obolayer.OBOFactory;

import obomodel.OBOComponent;

import daolayer.DAOFactory;

import daointerface.ThingDAO;

import routines.aggregated.LoadOBOFileIntoComponentsTables;


public class MainLoadOBOFileIntoComponentsTables {

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 2) {
			
		    Wrapper.printMessage("ERROR! There MUST be 2 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);
            DAOFactory daofactory = DAOFactory.getInstance(args[0]);

    	    // Obtain DAOs.
            OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();

            List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
            obocomponents = obocomponentaccess.listAllInput();


            LoadOBOFileIntoComponentsTables.run( obofactory.getMsgLevel(), daofactory, obofactory, obocomponents );
        }
        
        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
