/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainUpdateDatabaseFromComponentsTables.java
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
* Description:  A Main Class that Updates Anatomy Database From the Components Tables
*
* Usage:       "main.MainUpdateDatabaseFromComponentsTables
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
import app.gudmap.RebuildRelationshipProjectFromComponentsTables;
import app.gudmap.RunOBOResetComponentsOrderAlpha;
import daolayer.DAOFactory;
import daolayer.DAOProperty;
import obolayer.OBOFactory;
import obolayer.OBOProperty;
import routines.runnable.UpdateDatabaseFromComponentsTables;
import routines.runnable.UpdateDatabaseWithPerspectiveAmbits;

public class Main2UpdateDatabaseFromComponentsTables{

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 6) {
			
		    Wrapper.printMessage("ERROR! There MUST be 6 Command Line Arguments passed to this program!", "*", "*");
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
            
            UpdateDatabaseFromComponentsTables.run( daofactory, obofactory );
            
            RunOBOResetComponentsOrderAlpha.run( daofactory );
            
            RebuildRelationshipProjectFromComponentsTables.run( daofactory );
            
            UpdateDatabaseWithPerspectiveAmbits.run( daofactory, obofactory, args[4] );

            //System.setOut(original);
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
