/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
*
* Title:        MainOBOBardUpdateRelationship.java
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

package main.human;

import utility.Wrapper;

import app.human.RunOBOBardUpdateRelationship;

public class MainOBOBardUpdateRelationship {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

        /*
         * MAINLINE
         */
        RunOBOBardUpdateRelationship.run();
        
    	Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
