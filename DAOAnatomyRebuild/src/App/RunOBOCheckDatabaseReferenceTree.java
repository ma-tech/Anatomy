/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOCheckDatabaseReferenceTree.java
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

package app;

import java.util.ArrayList;

import obomodel.ComponentFile;

import utility.ImportDatabase;
import utility.MapBuilder;
import utility.TreeBuilder;
import utility.ValidateComponents;

public class RunOBOCheckDatabaseReferenceTree {
	/*
	 * run Method
	 */
    public static void run() throws Exception {
        //import database
        ImportDatabase importdatabase = new ImportDatabase(true, "GUDMAP" );

        ArrayList<ComponentFile> expTermList = importdatabase.getTermList();

        ArrayList<ComponentFile> parseOldTermList = expTermList;
        
        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder(parseOldTermList);

        //Build tree
        TreeBuilder treebuilder = new TreeBuilder(mapbuilder);

        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( parseOldTermList, treebuilder);

        //if file has problems don't allow to load
        if ( validatecomponents.getProblemTermList().isEmpty() ){
        	System.out.println("ValidateComponentFiles Class: \n" +
                    "Loading Default Reference Tree From ANATOMY008 DATABASE:\n===\n" +
        			"All Components in the Reference Tree are OK!" );
        }
        else {
        	System.out.println("ValidateComponentFiles Class: \n" +
                "Loading Default Reference TreeFrom ANATOMY008 DATABASE:\n===\n" + 
        		"Some components in the Reference Tree contain rule violations.\n" +
                "Please load the reference under the proposed tab to fix the problem; \n" +
                "Alternatively, please select another reference." );

            //System.out.println( validatecomponents.getProblemTermList() );
            
            ComponentFile probobocomponent =
                    (ComponentFile) validatecomponents.getProblemTermList().get(0);
            
            System.out.println("no. of components with problems = " +
                    validatecomponents.getProblemTermList().size());
            System.out.println("comments = " + probobocomponent.getCheckComments());
        }
    }
}
