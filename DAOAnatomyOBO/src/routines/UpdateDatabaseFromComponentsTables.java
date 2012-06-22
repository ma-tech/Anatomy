/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        UpdateDatabaseFromComponentsTables.java
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
* Description:  A Main Class that Reads an OBO File and Loads it into an existing 
*                Anatomy database;
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

package routines;

import obolayer.OBOFactory;

import daolayer.DAOFactory;


public class UpdateDatabaseFromComponentsTables {
	/*
	 * run Method
	 */
    public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
        if ( daofactory.getThingDAO().debug() ) {
        	System.out.println("ListOBOComponentsFromComponentsTables");
        }
        ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
        MapBuilder newmapbuilder = new MapBuilder(importcomponents.getTermList());
        TreeBuilder newtreebuilder = new TreeBuilder(newmapbuilder);

        //import Database from dao.properties, anatomy008.url
        if ( daofactory.getThingDAO().debug() ) {
        	System.out.println("ListOBOComponentsFromExistingDatabase");
        }
        ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true );
        MapBuilder oldmapbuilder = new MapBuilder(importdatabase.getTermList());
        TreeBuilder oldtreebuilder = new TreeBuilder(oldmapbuilder);

        //check for rules violation
        if ( daofactory.getThingDAO().debug() ) {
        	System.out.println("ValidateComponents");
        }
        ValidateComponents validatecomponents =
            new ValidateComponents( obofactory.getComponentOBO().species(), importcomponents.getTermList(), importdatabase.getTermList(), newtreebuilder);

        // Update the Database
        if ( daofactory.getThingDAO().debug() ) {
        	System.out.println("GenerateSQL");
        }
        GenerateSQL generatesql = new GenerateSQL( daofactory, obofactory, validatecomponents.getProposedTermList(), newtreebuilder, oldtreebuilder );

        if ( generatesql.isProcessed()) {
            System.out.println("----------------------");
            System.out.println("GenerateSQL - SUCCESS!");
            System.out.println("----------------------");
        }
        else {
            System.out.println("----------------------");
        	System.out.println("GenerateSQL - FAILED!");
            System.out.println("----------------------");
        }
    }
}
