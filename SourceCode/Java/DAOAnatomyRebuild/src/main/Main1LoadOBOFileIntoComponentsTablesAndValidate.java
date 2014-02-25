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
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

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

		if (args.length != 5) {
			
		    Wrapper.printMessage("ERROR! There MUST be 5 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);
        	
        	DAOProperty daoproperty = new DAOProperty();
        	daoproperty.setDAOProperty(args[2], args[3]);
            DAOFactory daofactory = DAOFactory.getInstance(args[3]);
            	
            EmptyComponentsTables.run( daofactory );
            
            LoadInputOBOFileIntoComponentsTablesAndValidate.run( daofactory, obofactory );
            
            //PrintStream original = System.out;
            
            //System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(args[4]))));
            
            RunOBOCheckComponentsOrdering.run( daofactory );
            
            RunOBOValidateComponentsOrder.run( daofactory );

            //System.setOut(original);
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
