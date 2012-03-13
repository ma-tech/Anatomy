/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOCheckFileReferenceTree.java
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

package App;

import java.util.List;
import java.util.ArrayList;

import DAOLayer.ComponentCommentDAO;
import DAOLayer.ComponentDAO;
import DAOLayer.ComponentRelationshipDAO;
import DAOLayer.ComponentSynonymDAO;
import DAOLayer.DAOFactory;

import OBOLayer.OBOFactory;
import OBOLayer.OBOException;
import OBOLayer.ComponentOBO;

import OBOModel.ComponentFile;

import Utility.MapBuilder;
import Utility.TreeBuilder;
import Utility.ValidateComponents;

public class RunOBOCheckFileReferenceTree {
	/*
	 * run Method
	 */
    public static void run() {

    	try {
            // Obtain DAOFactory.
            DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
            //System.out.println("DAOFactory successfully obtained: " + anatomy008);
            
            // Obtain DAOs.
            ComponentDAO componentDAO = anatomy008.getComponentDAO();
            //System.out.println("ComponentDAO successfully obtained: " + componentDAO);
            ComponentRelationshipDAO componentrelationshipDAO = anatomy008.getComponentRelationshipDAO();
            //System.out.println("ComponentRelationshipDAO successfully obtained: " + componentrelationshipDAO);
            ComponentCommentDAO componentcommentDAO = anatomy008.getComponentCommentDAO();
            //System.out.println("ComponentCommentDAO successfully obtained: " + componentcommentDAO);
            ComponentSynonymDAO componentsynonymDAO = anatomy008.getComponentSynonymDAO();
            //System.out.println("ComponentSynonymDAO successfully obtained: " + componentsynonymDAO);

            OBOFactory obofactory = OBOFactory.getInstance("file");
            //System.out.println("OBOFactory successfully obtained: " + obofactory);

            ComponentOBO componentOBO = obofactory.getComponentOBO();
            //System.out.println("ComponentOBO successfully obtained: " + componentOBO);

            List<ComponentFile> obocomponents = new ArrayList<ComponentFile>();
            obocomponents = componentOBO.listAll();

            ArrayList<ComponentFile> parseOldTermList = (ArrayList) obocomponents;
            
            //Build hashmap of components
            MapBuilder mapbuilder = new MapBuilder(parseOldTermList);

            //Build tree
            TreeBuilder treebuilder = new TreeBuilder(mapbuilder);

            //check for rules violation
            ValidateComponents validatecomponents =
                new ValidateComponents( parseOldTermList, treebuilder);

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
                
                ComponentFile probobocomponent =
                        (ComponentFile) validatecomponents.getProblemTermList().get(0);
                
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
