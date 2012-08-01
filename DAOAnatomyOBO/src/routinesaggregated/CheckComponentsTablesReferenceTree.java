/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        CheckComponentsTablesReferenceTree.java
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

package routinesaggregated;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import obolayer.OBOFactory;

import obomodel.OBOComponent;
import routinesbase.MapBuilder;
import routinesbase.TreeBuilder;
import routinesbase.ValidateComponents;

import daolayer.DAOFactory;



public class CheckComponentsTablesReferenceTree {
	/*
	 * run Method
	 */
    public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {

        //import database components table contents into OBOComponent format
    	ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables(daofactory, obofactory);
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importcomponents.getTermList();
        
        ArrayList<OBOComponent> parseOldTermList = (ArrayList<OBOComponent>) obocomponents;
        
        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder( obofactory.getComponentOBO().debug(), parseOldTermList);
        //Build tree
        TreeBuilder treebuilder = new TreeBuilder( obofactory.getComponentOBO().debug(), mapbuilder);

        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( obofactory, 
            		parseOldTermList, 
            		treebuilder);

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
