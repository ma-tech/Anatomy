/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        CheckDatabaseReferenceTree.java
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
* Description:  A Main Class that Reads an Anatomy Database and validates it against itself
*
*               Required Files:
*                1. dao.properties file contains the database access attributes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.runnable.archive;

import java.util.ArrayList;

import obomodel.OBOComponent;
import oboroutines.MapBuilder;
import oboroutines.TreeBuilder;
import oboroutines.ValidateComponents;

import obolayer.OBOFactory;

import routines.aggregated.ListOBOComponentsFromExistingDatabase;

import daolayer.DAOFactory;

import utility.Wrapper;

public class CheckDatabaseReferenceTree {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    
	    Wrapper.printMessage("checkdatabasereferencetree.run", "***", requestMsgLevel);

	    //import database
    	ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( requestMsgLevel, daofactory, obofactory, true );

        ArrayList<OBOComponent> expTermList = importdatabase.getTermList();
        ArrayList<OBOComponent> parseOldTermList = expTermList;
        
        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder( requestMsgLevel, parseOldTermList);
        //Build tree
        TreeBuilder treebuilder = new TreeBuilder( requestMsgLevel, mapbuilder);

        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( requestMsgLevel,
            		obofactory, 
            		parseOldTermList, 
            		treebuilder);

        //if file has problems don't allow to load
        if ( validatecomponents.getProblemTermList().isEmpty() ){

    	    Wrapper.printMessage("checkdatabasereferencetree.run :  \n" +
                    "Loading Default Reference Tree From ANATOMY DATABASE:\n===\n" +
        			"All Components in the Reference Tree are OK!", "***", requestMsgLevel);
        }
        else {
        	
    	    Wrapper.printMessage("checkdatabasereferencetree.run :  \n" +
                    "Loading Default Reference TreeFrom ANATOMY DATABASE:\n===\n" + 
            		"Some components in the Reference Tree contain rule violations.\n" +
                    "Please load the reference under the proposed tab to fix the problem; \n" +
                    "Alternatively, please select another reference.", "***", requestMsgLevel);

            OBOComponent probobocomponent =
                    (OBOComponent) validatecomponents.getProblemTermList().get(0);
            
    	    Wrapper.printMessage("checkdatabasereferencetree.run : no. of components with problems = " + validatecomponents.getProblemTermList().size(), "***", requestMsgLevel);
    	    Wrapper.printMessage("checkdatabasereferencetree.run : comments = " + probobocomponent.getCheckComments(), "***", requestMsgLevel);
        }
    }
}