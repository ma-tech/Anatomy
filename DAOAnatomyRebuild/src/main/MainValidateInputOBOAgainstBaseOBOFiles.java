/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainValidateInputOBOAgainstBaseOBOFiles.java
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
* Description:  A Main Class that Loads 2 OBOFiles into memory, and validates INPUT against BASE.
*
*               Required Files:
*                2. obo.properties file contains the OBO file access attributes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; December 2013; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package main;

import utility.Wrapper;

import obolayer.OBOFactory;

import routines.runnable.ValidateInputOBOAgainstBaseOBOFiles;


public class MainValidateInputOBOAgainstBaseOBOFiles{

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 1) {
			
		    Wrapper.printMessage("ERROR! There MUST be 1 Command Line Argument passed to this program!", "*", "*");
        }
        else {
        
        	OBOFactory obofactory = OBOFactory.getInstance(args[0]);
            
            ValidateInputOBOAgainstBaseOBOFiles.run( obofactory.getMsgLevel(), obofactory );
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
