/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        CheckFileReferenceTree.java
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
package routines.runnable;

import java.util.List;
import java.util.ArrayList;

import daolayer.DAOFactory;

import obolayer.OBOFactory;
import obolayer.ComponentOBO;

import obomodel.OBOComponent;
import routines.base.MapBuilder;
import routines.base.TreeBuilder;
import routines.base.ValidateComponents;

import utility.Wrapper;

public class CheckFileReferenceTree {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {

    	try {
    		
            ComponentOBO componentOBO = obofactory.getComponentOBO();

            List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
            obocomponents = componentOBO.listAll();

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
            	
        	    Wrapper.printMessage("OBOCheckFileReferenceTree.java:\n=======\nPASSED!\n=======\n" +
                        "Loading Default Reference Tree From Input OBO File:\n===\n" +
            			"All Components in the Reference Tree are OK!\n", "LOW", requestMsgLevel);
            }
            else {
            	
        	    Wrapper.printMessage("OBOCheckFileReferenceTree.java:\n=======\nERRORS!\n=======\n" +
                        "Loading Default Reference Tree From Input OBO File:\n" + 
                		"Some components in the Reference Tree contain rule violations.\n", "LOW", requestMsgLevel);
                
                OBOComponent probobocomponent =
                        (OBOComponent) validatecomponents.getProblemTermList().get(0);
                
        	    Wrapper.printMessage("no. of components with problems = " + validatecomponents.getProblemTermList().size() + "\n", "LOW", requestMsgLevel);
                
        	    Wrapper.printMessage("checkComments = " + probobocomponent.getCheckComments(), "LOW", requestMsgLevel);
        	    Wrapper.printMessage("orderComments = " + probobocomponent.getOrderComment(), "LOW", requestMsgLevel);
        	    Wrapper.printMessage("userComments = " + probobocomponent.getUserComments(), "LOW", requestMsgLevel);
                
        	    Wrapper.printMessage("probobocomponent = " + probobocomponent.toString(), "LOW", requestMsgLevel);
            }
    	}
    	catch (Exception exception) {
    		
			exception.printStackTrace();
    	}
    }
}
