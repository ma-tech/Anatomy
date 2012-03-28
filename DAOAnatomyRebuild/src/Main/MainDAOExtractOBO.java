/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainDAOExtractOBO.java
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

package Main;

import App.RunDAOExtractOBO;

public class MainDAOExtractOBO {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	long startTime = System.currentTimeMillis();
    	
        System.out.println("=========   -----------------");
        System.out.println("EXECUTING - MainDAOExtractOBO.java on " + Utility.MySQLDateTime.now());
        System.out.println("=========   -----------------");
        System.out.println("");

        /*
         * MAINLINE
         */
        RunDAOExtractOBO.run();

    	long endTime = System.currentTimeMillis();
    	long duration = endTime - startTime;

        System.out.println("");
        System.out.println("====       -----------------");
        System.out.println("DONE ----- MainDAOExtractOBO.java took " + duration / 1000 + " seconds");
        System.out.println("====       -----------------");
    }
}
