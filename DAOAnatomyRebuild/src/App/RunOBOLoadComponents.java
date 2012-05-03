/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOLoadComponents.java
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
* Description:  A Main Class that Reads an OBO File and populates 4 tables in the anatomy
*                database with the extracted data.
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

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import obolayer.OBOFactory;
import obolayer.ComponentOBO;

import obomodel.ComponentFile;

import daolayer.ComponentDAO;
import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentCommentDAO;
import daolayer.ComponentSynonymDAO;
import daolayer.ComponentAlternativeDAO;
import daolayer.DAOFactory;

import daomodel.Component;
import daomodel.ComponentRelationship;
import daomodel.ComponentComment;
import daomodel.ComponentSynonym;
import daomodel.ComponentAlternative;

public class RunOBOLoadComponents {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

        // Obtain OBOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("file");
        //System.out.println("OBOFactory successfully obtained: " + obofactory);

        // Obtain DAOs.
        ComponentOBO componentOBO = obofactory.getComponentOBO();
        //System.out.println("ComponentOBO successfully obtained: " + componentOBO);

        List<ComponentFile> obocomponents = new ArrayList<ComponentFile>();
        obocomponents = componentOBO.listAll();

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        //System.out.println("DAOFactory successfully obtained: " + anatomy008);

        // Obtain DAOs.
        ComponentDAO componentDAO = anatomy008.getComponentDAO();
        ComponentRelationshipDAO componentrelationshipDAO = anatomy008.getComponentRelationshipDAO();
        ComponentCommentDAO componentcommentDAO = anatomy008.getComponentCommentDAO();
        ComponentSynonymDAO componentsynonymDAO = anatomy008.getComponentSynonymDAO();
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getComponentAlternativeDAO();

        int i = componentDAO.countAll();
        if ( i > 0 ) {
            System.out.println("EMPTY ANA_OBO_COMPONENT");

       		componentDAO.empty();
        }

        i = componentrelationshipDAO.countAll();
        if ( i > 0 ) {
            System.out.println("EMPTY ANA_OBO_COMPONENT_RELATIONSHIP");

       		componentrelationshipDAO.empty();
        }

        i = componentcommentDAO.countAll();
        if ( i > 0 ) {
            System.out.println("EMPTY ANA_OBO_COMPONENT_COMMENT");

       		componentcommentDAO.empty();
        }

        i = componentsynonymDAO.countAll();
        if ( i > 0 ) {
            System.out.println("EMPTY ANA_OBO_COMPONENT_SYNONYM");

            componentsynonymDAO.empty();
        }

        i = componentalternativeDAO.countAll();
        if ( i > 0 ) {
            System.out.println("EMPTY ANA_OBO_COMPONENT_ALTERNATIVE");

            componentalternativeDAO.empty();
        }

      	Iterator<ComponentFile> iteratorComponent = obocomponents.iterator();
      	while (iteratorComponent.hasNext()) {
       		ComponentFile obocomponent = iteratorComponent.next();

       		Component daocomponent = new Component(null,
       				obocomponent.getName(),
       				obocomponent.getID(),
       				obocomponent.getDBID(),
       				obocomponent.getNewID(),
       				obocomponent.getNamespace(),
       				0,
       				obocomponent.getStart(),
       				obocomponent.getEnd(),
       				obocomponent.getPresent(),
       				obocomponent.getStatusChange(),
       				obocomponent.getStatusRule());
       		
       		componentDAO.save(daocomponent);
       		
       		List<String> oboalternativeids = new ArrayList<String>();
       		oboalternativeids = obocomponent.getAlternativeIds();
       		
       		List<String> obopartofs = new ArrayList<String>();
       		obopartofs = obocomponent.getChildOfs();
       		
       		List<String> obopartoftypes = new ArrayList<String>();
       		obopartoftypes = obocomponent.getChildOfTypes();
       		
            for ( i = 0; i < oboalternativeids.size(); i++ ) {

           		ComponentAlternative daocomponentalternative = new ComponentAlternative(null,
           				obocomponent.getID(),
           				oboalternativeids.get(i));
           		
           		componentalternativeDAO.save(daocomponentalternative);

       		}

            for ( i = 0; i < obopartofs.size(); i++ ) {

           		ComponentRelationship daocomponentrelationship = new ComponentRelationship(null,
           				obocomponent.getID(),
           				obopartoftypes.get(i),
           				obopartofs.get(i));
           		
           		componentrelationshipDAO.save(daocomponentrelationship);

       		}

            List<String> usercomments = obocomponent.getUserComments();
            //System.out.println("WARNING! usercomments.size, = " + Integer.toString(usercomments.size()));

            /*
            
            if (usercomment.startsWith("order=")) {
            	usercomment = "None";
            }
            */

            if (usercomments.size() > 1) {
                System.out.println("WARNING! usercomments.size() > 1, = " + Integer.toString(usercomments.size()));
            }

            if (obocomponent.getOrderComments() != null) {
                String [] ordercomments = obocomponent.getOrderComments();
                
                for ( i = 0; i < ordercomments.length; i++ ) {

                    String usercomment = usercomments.get(0);

                	ComponentComment daocomponentcomment = new ComponentComment(null,
               				obocomponent.getID(),
               				"None",
               				usercomment,
               				ordercomments[i]);
               		
               		componentcommentDAO.save(daocomponentcomment);

           		}
            }

            List<String> synonyms = obocomponent.getSynonyms();
            
            if (synonyms.size() > 1) {
                for ( i = 0; i < synonyms.size(); i++ ) {

               		ComponentSynonym daocomponentsynonym = new ComponentSynonym(null,
               				obocomponent.getID(),
               				synonyms.get(i));
               		
               		componentsynonymDAO.save(daocomponentsynonym);

           		}
            }
      	}
      	
        List<Component> daocomponents = new ArrayList<Component>();
        daocomponents = componentDAO.listAll();
        
        System.out.println("===============================================================");
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT              = " + Integer.toString(daocomponents.size()));

        List<ComponentRelationship> daocomponentrelationships = new ArrayList<ComponentRelationship>();
        daocomponentrelationships = componentrelationshipDAO.listAll();
        
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_RELATIONSHIP = " + Integer.toString(daocomponentrelationships.size()));
        
        List<ComponentComment> daocomponentcomments = new ArrayList<ComponentComment>();
        daocomponentcomments = componentcommentDAO.listAll();
        
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_COMMENTS     = " + Integer.toString(daocomponentcomments.size()));
        
        List<ComponentSynonym> daocomponentsynonym = new ArrayList<ComponentSynonym>();
        daocomponentsynonym = componentsynonymDAO.listAll();
        
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_SYNONYM      = " + Integer.toString(daocomponentsynonym.size()));

        List<ComponentAlternative> daocomponentalternative = new ArrayList<ComponentAlternative>();
        daocomponentalternative = componentalternativeDAO.listAll();
        
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_ALTERNATIVE  = " + Integer.toString(daocomponentalternative.size()));
        System.out.println("===============================================================");
    }
}
