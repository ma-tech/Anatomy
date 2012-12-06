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

import utility.Wrapper;

import obolayer.OBOFactory;

import daolayer.DAOFactory;

import app.RunOBOTest;

public class MainOBOTest {

	public static void main(String[] args) throws Exception {

    	String requestMsgLevel = args[0]; 

    	long startTime = Wrapper.printPrologue(requestMsgLevel, Wrapper.getExecutingClass());

        if (args.length != 3) {

		    Wrapper.printMessage(" ERROR! There MUST be 3 Command Line Arguments passed to this program", "HIGH", requestMsgLevel);
        }
        else {
            // Obtain OBOFactory.
            OBOFactory obofactory = OBOFactory.getInstance(args[2]);
            DAOFactory daofactory = DAOFactory.getInstance(args[1]);

            RunOBOTest.run( requestMsgLevel, obofactory, daofactory );
        }

    	Wrapper.printEpilogue(requestMsgLevel, Wrapper.getExecutingClass(), startTime);
    }
}
