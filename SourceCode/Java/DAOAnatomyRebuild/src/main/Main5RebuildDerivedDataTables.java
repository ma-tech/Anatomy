/*
*----------------------------------------------------------------------------------------------
	* Project:      DAOAnatomyRebuild
*
* Title:        Main5RebuildDerivedDataTables.java
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
* Description:  A Main Class that rebuilds the Derived Data Tables in the Anatomy Database
*                - ANAD_PART_OF
*                - ANAD_PART_OF_PERSPECTIVE
*                - ANAD_RELATIONSHIP_TRANSITIVE
*
* Usage:        "main.Main5RebuildDerivedDataTables 
*                /Users/mwicks/GitMahost/Anatomy/Properties/obo.properties.input 
*                 mouse011JenkinsOBOfile 
*                  /Users/mwicks/GitMahost/Anatomy/Properties/dao.properties.input 
*                   mouse011GudmapLocalhost"
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; April 2014; Create Class
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

import routines.runnable.RebuildDerivedDataTables;


public class Main5RebuildDerivedDataTables{

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
            
            //PrintStream original = System.out;
            
            //System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(args[4]))));
            
            RebuildDerivedDataTables.run( daofactory, obofactory );
            
            //System.setOut(original);

        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
