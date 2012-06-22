/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainOBOLoadFileIntoDatabase.java
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
* Description:  A Main Class that Reads an OBO File and Loads the Data into the Anatomy Database
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

import java.text.SimpleDateFormat;

import java.util.Date;

import obolayer.OBOFactory;

import daolayer.DAOFactory;


import routines.LoadOBOFileIntoComponentsTablesAndValidate;
import routines.RunOBOCheckComponentsOrdering;


public class MainLoadOBOFileIntoComponentsTablesAndValidate{
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	long startTime = System.currentTimeMillis();
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);
        System.out.println("=========   ----------------------------------------------");
        System.out.println("EXECUTING - MainLoadOBOFileIntoComponentsTablesAndValidate.java on " + parsed.toString());
        System.out.println("=========   ----------------------------------------------");
        System.out.println("");
        /*
         * MAINLINE
         */
        // Obtain DAOFactory.
        DAOFactory daofactory = DAOFactory.getInstance("anatomy008");
        // Obtain OBOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("file");

        LoadOBOFileIntoComponentsTablesAndValidate.run(daofactory, obofactory);
        
        RunOBOCheckComponentsOrdering.run(daofactory, obofactory);

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;
        System.out.println("");
        System.out.println("=========   ----------------------------------------------");
        System.out.println("DONE      - MainLoadOBOFileIntoComponentsTablesAndValidate.java took " + duration / 1000 + " seconds");
        System.out.println("=========   ----------------------------------------------");
    }
}
