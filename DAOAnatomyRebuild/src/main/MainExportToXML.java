/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainExportToXML.java
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
* Description:  A Main Executable Class 
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package main;

import utility.Wrapper;

import obolayer.OBOFactory;

import daolayer.DAOFactory;

import app.RunExportToXML;

public class MainExportToXML {

	public static void main(String[] args) throws Exception {
    	
    	long startTime = Wrapper.printPrologue("HIGH", Wrapper.getExecutingClass());

		if (args.length != 2) {
			
		    Wrapper.printMessage(" ERROR! There MUST be 2 Command Line Arguments passed to this program", "HIGH", "HIGH");
        }
        else {
        
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);
            DAOFactory daofactory = DAOFactory.getInstance(args[0]);

            RunExportToXML.run( daofactory.getThingDAO().getLevel(), obofactory, daofactory );
        }
        
        Wrapper.printEpilogue("HIGH", Wrapper.getExecutingClass(), startTime);
    }
}
