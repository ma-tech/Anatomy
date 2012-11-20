/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainOBOValidateComponents.java
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

import app.RunExportToXML;

public class MainExportToXML {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {
    	
    	long startTime = System.currentTimeMillis();
    	Date startDate = new Date();
    	String dateString = startDate.toString();
    	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    	Date parsed = format.parse(dateString);
        System.out.println("=========   ---------------");
        System.out.println("EXECUTING - MainExportToXML.java on " + parsed.toString());
        System.out.println("=========   ---------------");
        System.out.println("");

        /*
         * MAINLINE
         */
        if (args.length != 2) {
        	System.out.println(" ERROR - There MUST be 2 arguments passed to this program!\n ERROR - Try Again!");
        }
        else {
            // Obtain OBOFactory.
            OBOFactory obofactory = OBOFactory.getInstance(args[1]);
            DAOFactory daofactory = DAOFactory.getInstance(args[0]);

            RunExportToXML.run(obofactory, daofactory);
        }
        
        System.out.println("");
    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;
        System.out.println("====        ---------------");
        System.out.println("DONE ------ MainExportToXML.java took " + duration / 1000 + " seconds");
        System.out.println("====        ---------------");
    }
}
