/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainEmptyComponentsTables.java
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
* Description:  A Main Class
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package main.archive;

import utility.Wrapper;

import routines.aggregated.EmptyComponentsTables;

import daolayer.DAOFactory;

public class MainEmptyComponentsTables {

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 1) {
			
		    Wrapper.printMessage("ERROR! There MUST be 1 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
            DAOFactory daofactory = DAOFactory.getInstance(args[0]);

            EmptyComponentsTables.run( daofactory, true, true, true, true, true, true );
        }
        
        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
