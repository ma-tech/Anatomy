/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainExtractAndWriteOBOFromExistingDatabase.java
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
* Description:  A Main Executable Class that Extracts And Write OBOComponents From an 
*                Existing Anatomy Database
* 
* Usage:        "main.MainExtractAndWriteOBOFromExistingDatabase 
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

import utility.Wrapper;

import obolayer.OBOFactory;
import obolayer.OBOProperty;

import daolayer.DAOFactory;
import daolayer.DAOProperty;

import routines.runnable.ExtractAndWriteOBOFromExistingDatabase;

public class Main5ExtractAndWriteOBOFromExistingDatabase {

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 5) {
			
		    Wrapper.printMessage("ERROR! There MUST be 4 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);
        		
        	DAOProperty daoproperty = new DAOProperty();
        	daoproperty.setDAOProperty(args[2], args[3]);
            DAOFactory daofactory = DAOFactory.getInstance(args[3]);

            ExtractAndWriteOBOFromExistingDatabase.run( daofactory, obofactory );
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
