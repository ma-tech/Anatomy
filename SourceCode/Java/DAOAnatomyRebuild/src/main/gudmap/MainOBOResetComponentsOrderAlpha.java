/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainOBOResetComponentsOrderAlpha.java
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
* Description:  A Main Executable Class that resets the Ordering the Components Order table
*                into Alphabetic ordering
* 
* Usage:        "main.MainOBOResetComponentsOrderAlpha 
*                  /Users/mwicks/GitMahost/Anatomy/Properties/dao.properties.input 
*                   mouse011GudmapLocalhost"
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
import daolayer.DAOProperty;

import app.gudmap.RunOBOResetComponentsOrderAlpha;

public class MainOBOResetComponentsOrderAlpha {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {
    	
    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

        if (args.length != 2) {
			
		    Wrapper.printMessage("ERROR! There MUST be 2 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	DAOProperty daoproperty = new DAOProperty();
        	daoproperty.setDAOProperty(args[0], args[1]);
            DAOFactory daofactory = DAOFactory.getInstance(args[1]);

            RunOBOResetComponentsOrderAlpha.run( daofactory );
        }
        
    	Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
