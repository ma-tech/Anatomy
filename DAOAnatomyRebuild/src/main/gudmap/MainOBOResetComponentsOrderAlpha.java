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

package main.gudmap;

import daolayer.DAOFactory;

import daointerface.ThingDAO;

import utility.Wrapper;

import app.gudmap.RunOBOResetComponentsOrderAlpha;


public class MainOBOResetComponentsOrderAlpha {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {
    	
    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

        if (args.length != 1) {
			
		    Wrapper.printMessage("ERROR! There MUST be 1 Command Line Argument passed to this program!", "*", "*");
        }
        else {
        
            DAOFactory daofactory = DAOFactory.getInstance(args[0]);

            RunOBOResetComponentsOrderAlpha.run(daofactory.getDAOImpl(ThingDAO.class).getLevel(), daofactory);
        }
        
    	Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
