/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOCheckComponentsReferenceTree.java
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
* Description:  A Main Class that Reads an OBO File and validates it against itself.
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

package app;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import obomodel.OBOComponent;

import routines.ListOBOComponentsFromComponentsTables;
import routines.MapBuilder;
import routines.TreeBuilder;
import routines.ValidateComponents;

public class RunOBOCheckComponentsReferenceTree {
	/*
	 * run Method
	 */
    public static void run(String species) throws IOException {

        //import database components table contents into OBOComponent format
    	ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables();
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importcomponents.getTermList();
        
        ArrayList<OBOComponent> parseOldTermList = (ArrayList<OBOComponent>) obocomponents;
        
        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder(parseOldTermList);
        //Build tree
        TreeBuilder treebuilder = new TreeBuilder(mapbuilder);

        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( species, parseOldTermList, treebuilder);

        //if file has problems don't allow to load
        if ( validatecomponents.getProblemTermList().isEmpty() ){
        	
        	System.out.println("=======\nPASSED!\n=======\n" +
                "Loading Default Reference Tree From Database Components Tables:\n" +
        		"All Components in the Reference Tree are OK!" );
        }
        else {
        	
        	System.out.println("=======\nFAILED!\n=======\n" +
                "Loading Default Reference Tree From Database Components Tables:\n" +
        		"Some components in the Reference Tree contain rule violations." );

        	ArrayList<OBOComponent> problemTermList = validatecomponents.getProblemTermList();
            
        	System.out.println("\nThere are " + problemTermList.size() + " problems!\n");

          	Iterator<OBOComponent> iteratorComponent = problemTermList.iterator();

          	int i = 0;
          	
          	while (iteratorComponent.hasNext()) {
          		
          		i++;
          		OBOComponent obocomponent = iteratorComponent.next();
          		System.out.println("Problem Component #" + i + ": " + obocomponent.toString());
          	}
        }
    }
}
