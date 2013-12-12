/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainLoadOBOFileIntoComponentsTablesAndValidate.java
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
* Description:  A Main Class that Loads an OBOFile Into the Components Tables And Validate
*                against the existing anatomy database
*
*               Required Files:
*                1. dao.properties file contains the database access attributes
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
package main;

import utility.Wrapper;

import daolayer.DAOFactory;

import obolayer.OBOFactory;

import daointerface.ThingDAO;

import routines.aggregated.EmptyComponentsTables;
import routines.runnable.LoadInputOBOFileIntoComponentsTablesAndValidate;
import routines.runnable.RunOBOCheckComponentsOrdering;

import app.gudmap.RunOBOValidateComponentsOrder; 


public class MainLoadOBOFileIntoComponentsTablesAndValidate{

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 2) {
			
		    Wrapper.printMessage("ERROR! There MUST be 2 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);
            DAOFactory daofactory = DAOFactory.getInstance(args[0]);
            
            EmptyComponentsTables.run( obofactory.getMsgLevel(), daofactory, obofactory );
            LoadInputOBOFileIntoComponentsTablesAndValidate.run( obofactory.getMsgLevel(), daofactory, obofactory );
            RunOBOCheckComponentsOrdering.run( obofactory.getMsgLevel(), daofactory, obofactory );
            RunOBOValidateComponentsOrder.run( obofactory.getMsgLevel(), daofactory, obofactory) ;
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
