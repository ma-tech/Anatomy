/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainCheckFileReferenceTree.java
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
* Description:  A Main Class that Reads an OBO File and populates 4 tables in the anatomy
*                database with the extracted data.
*
*               Required Files:
*                1. dao.properties file contains the database access attributes
*                2. obo.properties file contains the OBO file access attributes
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

import routines.runnable.archive.CheckFileReferenceTree;

import obolayer.OBOFactory;

import daolayer.DAOFactory;

import daointerface.ThingDAO;

public class MainCheckFileReferenceTree {

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 2) {
			
		    Wrapper.printMessage("ERROR! There MUST be 2 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);
            DAOFactory daofactory = DAOFactory.getInstance(args[0]);

            CheckFileReferenceTree.run( daofactory.getDAOImpl(ThingDAO.class).getLevel(), daofactory, obofactory );
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}