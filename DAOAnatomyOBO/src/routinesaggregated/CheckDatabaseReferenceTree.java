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

package routinesaggregated;

import java.util.ArrayList;

import obomodel.OBOComponent;

import obolayer.OBOFactory;
import routinesbase.MapBuilder;
import routinesbase.TreeBuilder;
import routinesbase.ValidateComponents;
import daolayer.DAOFactory;


public class CheckDatabaseReferenceTree {
	/*
	 * run Method
	 */
    public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {
        //import database
    	ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true );

        ArrayList<OBOComponent> expTermList = importdatabase.getTermList();

        ArrayList<OBOComponent> parseOldTermList = expTermList;
        
        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder( obofactory.getComponentOBO().debug(), parseOldTermList);

        //Build tree
        TreeBuilder treebuilder = new TreeBuilder( obofactory.getComponentOBO().debug(), mapbuilder);

        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( obofactory.getComponentOBO().debug(), 
            		obofactory.getComponentOBO().species(), 
            		parseOldTermList, 
            		treebuilder);

        //if file has problems don't allow to load
        if ( validatecomponents.getProblemTermList().isEmpty() ){
        	System.out.println("ValidateComponents Class: \n" +
                    "Loading Default Reference Tree From ANATOMY008 DATABASE:\n===\n" +
        			"All Components in the Reference Tree are OK!" );
        }
        else {
        	System.out.println("ValidateComponents Class: \n" +
                "Loading Default Reference TreeFrom ANATOMY008 DATABASE:\n===\n" + 
        		"Some components in the Reference Tree contain rule violations.\n" +
                "Please load the reference under the proposed tab to fix the problem; \n" +
                "Alternatively, please select another reference." );

            //System.out.println( validatecomponents.getProblemTermList() );
            
            OBOComponent probobocomponent =
                    (OBOComponent) validatecomponents.getProblemTermList().get(0);
            
            System.out.println("no. of components with problems = " +
                    validatecomponents.getProblemTermList().size());
            System.out.println("comments = " + probobocomponent.getCheckComments());
        }
    }
}
