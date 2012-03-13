/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        OBOEmptyComponents.java
#
# Date:         2012
#
# Author:       Mike Wicks
#
# Copyright:    2012
#               Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Description:  A Main Class that empties 4 tables that contain OBO Temporary Data in the anatomy
#                database.
#
#               Required Files:
#                1. dao.properties file contains the database access attributes
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package Main;

import java.text.SimpleDateFormat;
import java.util.Date;

import App.RunOBOEmptyComponents;

public class MainOBOEmptyComponents {

	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	long startTime = System.currentTimeMillis();
    	
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);

        System.out.println("=========   ======================");
        System.out.println("EXECUTING - MainOBOEmptyComponents.java on " + parsed.toString());
        System.out.println("=========   ======================");
        System.out.println("");

        RunOBOEmptyComponents.run();
        
        System.out.println("");

    	long endTime = System.currentTimeMillis();
    	
    	long duration = endTime - startTime;

        System.out.println("====        ======================");
        System.out.println("DONE ------ MainOBOEmptyComponents.java took " + duration / 1000 + " seconds");
        System.out.println("====        ======================");
        

    }

}
