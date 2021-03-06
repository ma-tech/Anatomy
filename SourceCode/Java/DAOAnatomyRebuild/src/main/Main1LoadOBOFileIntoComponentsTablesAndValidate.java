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
* Usage:        "main.MainLoadOBOFileIntoComponentsTablesAndValidate 
*                /Users/mwicks/GitMahost/Anatomy/Properties/obo.properties.input 
*                 mouse011JenkinsOBOfile 
*                  /Users/mwicks/GitMahost/Anatomy/Properties/dao.properties.input 
*                   mouse011GudmapLocalhost"
* 
* Parameters:  1. The Location of the OBO Properties file
*              2. The Key to the set selected OBO Properties 
*              3. The Location of the DAO Properties file
*              4. The Key to the set selected DAO Properties
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
import daolayer.DAOProperty;

import obolayer.OBOFactory;
import obolayer.OBOProperty;

import routines.aggregated.EmptyComponentsTables;

import routines.runnable.LoadInputOBOFileIntoComponentsTablesAndValidate;
import routines.runnable.RunOBOCheckComponentsOrdering;

import app.gudmap.RunOBOValidateComponentsOrder; 


public class Main1LoadOBOFileIntoComponentsTablesAndValidate{

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 4) {
			
		    Wrapper.printMessage("ERROR! There MUST be 4 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);
        	
        	DAOProperty daoproperty = new DAOProperty();
        	daoproperty.setDAOProperty(args[2], args[3]);
            DAOFactory daofactory = DAOFactory.getInstance(args[3]);
            	
            EmptyComponentsTables.run( daofactory, true, true, true, true, true, true );
            
            LoadInputOBOFileIntoComponentsTablesAndValidate.run( daofactory, obofactory );
            
            RunOBOCheckComponentsOrdering.run( daofactory );
            
            RunOBOValidateComponentsOrder.run( daofactory );
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
