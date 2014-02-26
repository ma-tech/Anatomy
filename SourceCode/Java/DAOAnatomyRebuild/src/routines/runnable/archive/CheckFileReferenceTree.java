/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
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

import java.util.List;
import java.util.ArrayList;

import utility.Wrapper;

import daolayer.DAOFactory;

import obolayer.OBOFactory;

import oboaccess.OBOComponentAccess;

import obomodel.OBOComponent;

import oboroutines.MapBuilder;
import oboroutines.TreeBuilder;
import oboroutines.ValidateComponents;

public class CheckFileReferenceTree {

	public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {

    	try {
    		
    	    Wrapper.printMessage("checkfilereferencetree.run", "***", daofactory.getMsgLevel());

    	    OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();

            List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
            obocomponents = obocomponentaccess.listAllInput();

            ArrayList<OBOComponent> parseOldTermList = (ArrayList<OBOComponent>) obocomponents;
            
            //Build hashmap of components
            MapBuilder mapbuilder = new MapBuilder( daofactory.getMsgLevel(), parseOldTermList);
            //Build tree
            TreeBuilder treebuilder = new TreeBuilder( daofactory.getMsgLevel(), mapbuilder);

            //check for rules violation
            ValidateComponents validatecomponents =
                new ValidateComponents( obofactory, 
                		parseOldTermList, 
                		treebuilder);

            //if file has problems don't allow to load
            if ( validatecomponents.getProblemTermList().isEmpty() ){
            	
        	    Wrapper.printMessage("checkfilereferencetree.run : \n=======\nPASSED!\n=======\n" +
                        "Loading Default Reference Tree From Input OBO File:\n===\n" +
            			"All Components in the Reference Tree are OK!\n", "***", daofactory.getMsgLevel());
            }
            else {
            	
        	    Wrapper.printMessage("checkfilereferencetree.run : \n=======\nERRORS!\n=======\n" +
                        "Loading Default Reference Tree From Input OBO File:\n" + 
                		"Some components in the Reference Tree contain rule violations.\n", "***", daofactory.getMsgLevel());
                
                OBOComponent probobocomponent =
                        (OBOComponent) validatecomponents.getProblemTermList().get(0);
                
        	    Wrapper.printMessage("checkfilereferencetree.run : no. of components with problems = " + validatecomponents.getProblemTermList().size() + "\n", "***", daofactory.getMsgLevel());
                
        	    Wrapper.printMessage("checkfilereferencetree.run : checkComments = " + probobocomponent.getCheckComments(), "***", daofactory.getMsgLevel());
        	    Wrapper.printMessage("checkfilereferencetree.run : orderComments = " + probobocomponent.getOrderComment(), "***", daofactory.getMsgLevel());
        	    Wrapper.printMessage("checkfilereferencetree.run : userComments = " + probobocomponent.getUserComments(), "***", daofactory.getMsgLevel());
                
        	    Wrapper.printMessage("checkfilereferencetree.run : probobocomponent = " + probobocomponent.toString(), "***", daofactory.getMsgLevel());
            }
    	}
    	catch (Exception exception) {
    		
			exception.printStackTrace();
    	}
    }
}
