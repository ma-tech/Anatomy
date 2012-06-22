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

package routines;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import daolayer.DAOFactory;

import obolayer.OBOFactory;
import obolayer.OBOException;
import obolayer.ComponentOBO;

import obomodel.OBOComponent;


public class CheckFileReferenceTree {
	/*
	 * run Method
	 */
    public static void run(DAOFactory daofactory, OBOFactory obofactory) throws IOException {

    	try {
            ComponentOBO componentOBO = obofactory.getComponentOBO();

            List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
            obocomponents = componentOBO.listAll();

            ArrayList<OBOComponent> parseOldTermList = (ArrayList<OBOComponent>) obocomponents;
            
            //Build hashmap of components
            MapBuilder mapbuilder = new MapBuilder(parseOldTermList);
            //Build tree
            TreeBuilder treebuilder = new TreeBuilder(mapbuilder);

            //check for rules violation
            ValidateComponents validatecomponents =
                new ValidateComponents( "mouse", parseOldTermList, treebuilder);

            //if file has problems don't allow to load
            if ( validatecomponents.getProblemTermList().isEmpty() ){
            	System.out.println("OBOCheckFileReferenceTree.java:\n=======\nPASSED!\n=======\n" +
                        "Loading Default Reference Tree From Input OBO File:\n===\n" +
            			"All Components in the Reference Tree are OK!\n" );
            }
            else {
            	System.out.println("OBOCheckFileReferenceTree.java:\n=======\nERRORS!\n=======\n" +
                    "Loading Default Reference Tree From Input OBO File:\n" + 
            		"Some components in the Reference Tree contain rule violations.\n" );

                //System.out.println( validatecomponents.getProblemTermList() );
                
                OBOComponent probobocomponent =
                        (OBOComponent) validatecomponents.getProblemTermList().get(0);
                
                System.out.println("no. of components with problems = " +
                        validatecomponents.getProblemTermList().size() + "\n");
                
                System.out.println("checkComments = " + probobocomponent.getCheckComments());
                System.out.println("orderComments = " + probobocomponent.getOrderComment());
                System.out.println("userComments = " + probobocomponent.getUserComments());
                
                System.out.println("probobocomponent = " + probobocomponent.toString());
            }
    	}
    	catch (OBOException oboexception) {
			oboexception.printStackTrace();
    	}
    }
}
