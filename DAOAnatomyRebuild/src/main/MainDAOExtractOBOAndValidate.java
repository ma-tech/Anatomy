/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainDAOExtractOBOAndValidate.java
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

import app.RunDAOExtractOBOAndValidate;

public class MainDAOExtractOBOAndValidate {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	System.out.println("args[0] = " + args[0]);
    	
    	long startTime = System.currentTimeMillis();
    	
        System.out.println("=========   ----------------------------");
        System.out.println("EXECUTING - MainDAOExtractOBOAndValidate.java on " + utility.MySQLDateTime.now());
        System.out.println("=========   ----------------------------");
        System.out.println("");
        
        /*
         * MAINLINE
         */
        RunDAOExtractOBOAndValidate.run(args[0]);

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;
    	
        System.out.println("");
        System.out.println("====       ----------------------------");
        System.out.println("DONE ----- MainDAOExtractOBOAndValidate.java took " + duration / 1000 + " seconds");
        System.out.println("====       ----------------------------");
        
    }
}
