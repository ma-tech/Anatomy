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
package main.gudmap;

import utility.Wrapper;

import daolayer.DAOFactory;

import daointerface.ThingDAO;

import app.gudmap.RunInsertSynonymsFromCSVFile;


public class MainInsertSynonymsFromCSVFile{

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 1) {
			
		    Wrapper.printMessage("ERROR! There MUST be 1 Command Line Argument passed to this program!", "*", "*");
        }
        else {
        
            DAOFactory daofactory = DAOFactory.getInstance(args[0]);

            RunInsertSynonymsFromCSVFile.run( daofactory.getMsgLevel(), daofactory );
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
