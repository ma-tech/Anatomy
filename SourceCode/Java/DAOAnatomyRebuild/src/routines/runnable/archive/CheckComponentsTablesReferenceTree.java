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
* Version:      1
*
* Description:  
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
import java.util.ArrayList;

import utility.Wrapper;

import anatomy.TreeAnatomy;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import oboroutines.ValidateComponents;

import routines.aggregated.ListOBOComponentsFromComponentsTables;

import daolayer.DAOFactory;


public class CheckComponentsTablesReferenceTree {

	public static void run( DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    Wrapper.printMessage("checkcomponentstablesreferencetree.run", "***", daofactory.getMsgLevel());

	    //import database components table contents into OBOComponent format
    	ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
        
        ArrayList<OBOComponent> obocomponents = importcomponents.getTermList();
        
	    // Build a Tree from all the Components in the Part-Onomy
	    TreeAnatomy treeanatomy = new TreeAnatomy(obofactory.getMsgLevel(), obocomponents);

        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( obofactory, 
            		obocomponents, 
            		treeanatomy);

        //if file has problems don't allow to load
        if ( validatecomponents.getProblemTermList().isEmpty() ){
        	
    	    Wrapper.printMessage("checkcomponentstablesreferencetree.run : =======\nPASSED!\n=======\n" +
                    "Loading Default Reference Tree From Database Components Tables:\n" +
            		"All Components in the Reference Tree are OK!", "***", daofactory.getMsgLevel());
        }
        else {
        	
    	    Wrapper.printMessage("checkcomponentstablesreferencetree.run : =======\nFAILED!\n=======\n" +
                    "Loading Default Reference Tree From Database Components Tables:\n" +
            		"Some components in the Reference Tree contain rule violations.", "***", daofactory.getMsgLevel());

    	    ArrayList<OBOComponent> problemTermList = validatecomponents.getProblemTermList();
            
    	    Wrapper.printMessage("checkcomponentstablesreferencetree.run : \nThere are " + problemTermList.size() + " problems!\n", "***", daofactory.getMsgLevel());
    	    
    	    Iterator<OBOComponent> iteratorComponent = problemTermList.iterator();

          	int i = 0;
          	
          	while (iteratorComponent.hasNext()) {
          		
          		i++;
          		OBOComponent obocomponent = iteratorComponent.next();

        	    Wrapper.printMessage("checkcomponentstablesreferencetree.run : Problem Component #" + i + ": " + obocomponent.toString(), "***", daofactory.getMsgLevel());
          	}
        }
    }
}
