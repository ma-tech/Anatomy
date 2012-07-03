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

import java.text.SimpleDateFormat;

import java.util.Date;

import obolayer.OBOFactory;
import daolayer.DAOFactory;

import routinesaggregated.ExtractAndWriteOBOFromExistingDatabase;


public class MainExtractAndWriteOBOFromExistingDatabase {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	long startTime = System.currentTimeMillis();
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);
        System.out.println("=========   ------------------------------------------");
        System.out.println("EXECUTING - MainExtractAndWriteOBOFromExistingDatabase.java on " + parsed.toString());
        System.out.println("=========   ------------------------------------------");
        System.out.println("");

        // Obtain DAOFactory.
        DAOFactory daofactory = DAOFactory.getInstance("mouseAnatomy008LocalhostDebug");
        // Obtain OBOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("mouseOBOfileDebug");

        ExtractAndWriteOBOFromExistingDatabase.run(daofactory, obofactory);

        System.out.println("");
    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;
        System.out.println("=========   ------------------------------------------");
        System.out.println("DONE      - MainExtractAndWriteOBOFromExistingDatabase.java took " + duration / 1000 + " seconds");
        System.out.println("=========   ------------------------------------------");
    }
}
