/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
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
package routines.runnable.archive;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.base.MapBuilder;
import routines.base.TreeBuilder;
import routines.base.ValidateComponents;

import daolayer.DAOFactory;

import utility.Wrapper;

public class CheckComponentsTablesReferenceTree {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    Wrapper.printMessage("checkcomponentstablesreferencetree.run", "***", requestMsgLevel);

	    //import database components table contents into OBOComponent format
    	ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( requestMsgLevel, daofactory, obofactory );
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importcomponents.getTermList();
        
        ArrayList<OBOComponent> parseOldTermList = (ArrayList<OBOComponent>) obocomponents;
        
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
        	
    	    Wrapper.printMessage("checkcomponentstablesreferencetree.run : =======\nPASSED!\n=======\n" +
                    "Loading Default Reference Tree From Database Components Tables:\n" +
            		"All Components in the Reference Tree are OK!", "***", requestMsgLevel);
        }
        else {
        	
    	    Wrapper.printMessage("checkcomponentstablesreferencetree.run : =======\nFAILED!\n=======\n" +
                    "Loading Default Reference Tree From Database Components Tables:\n" +
            		"Some components in the Reference Tree contain rule violations.", "***", requestMsgLevel);

    	    ArrayList<OBOComponent> problemTermList = validatecomponents.getProblemTermList();
            
    	    Wrapper.printMessage("checkcomponentstablesreferencetree.run : \nThere are " + problemTermList.size() + " problems!\n", "***", requestMsgLevel);
    	    
    	    Iterator<OBOComponent> iteratorComponent = problemTermList.iterator();

          	int i = 0;
          	
          	while (iteratorComponent.hasNext()) {
          		
          		i++;
          		OBOComponent obocomponent = iteratorComponent.next();

        	    Wrapper.printMessage("checkcomponentstablesreferencetree.run : Problem Component #" + i + ": " + obocomponent.toString(), "***", requestMsgLevel);
          	}
        }
    }
}
