/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerOBO
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

import daolayer.DAOFactory;
import daolayer.DAOProperty;

import daointerface.ThingDAO;

import app.RunOBOTest;

public class MainOBOTest {

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

        if (args.length != 4) {

		    Wrapper.printMessage("ERROR! There MUST be 4 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        	
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	
        	DAOProperty daoproperty = new DAOProperty();
        	daoproperty.setDAOProperty(args[2], args[3]);

        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);
            DAOFactory daofactory = DAOFactory.getInstance(args[3]);

            RunOBOTest.run( daofactory.getDAOImpl(ThingDAO.class).getLevel(), obofactory, daofactory );
        }

    	Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
