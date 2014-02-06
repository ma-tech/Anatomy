/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        MainOBOTest.java
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
* Usage:        "main.MainOBOTest 
*                /Users/mwicks/GitMahost/Anatomy/Properties/obo.properties.input 
*                 mouse011JenkinsOBOfile" 
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

import utility.Wrapper;

import obolayer.OBOFactory;
import obolayer.OBOProperty;

import app.RunOBOTest;

public class MainOBOTest {

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

        if (args.length != 2 ) {

		    Wrapper.printMessage("ERROR! There MUST be 2 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        	
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);

            RunOBOTest.run( obofactory );
        }

    	Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
