/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainDAOTest.java
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

import daolayer.DAOFactory;

import app.RunDAOTest;

public class MainDAOTest {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);

        System.out.println("=========  ------------");
        System.out.println("EXECUTING - MainDAOTest.java on " + parsed.toString());
        System.out.println("=========  ------------");
        System.out.println("");
        
        /*
         * MAINLINE
         */
        // Obtain DAOFactory.
        DAOFactory daofactory = DAOFactory.getInstance("mouseAnatomy008LocalhostDebug");

        RunDAOTest.run(daofactory);

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;

        System.out.println("");
        System.out.println("====       ------------");
        System.out.println("DONE ------ MainDAOTest.java took " + duration / 1000 + " seconds");
        System.out.println("====       ------------");
    }
}
