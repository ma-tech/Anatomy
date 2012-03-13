/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        OBOLoadComponents.java
#
# Date:         2012
#
# Author:       Mike Wicks
#
# Copyright:    2012
#               Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Description:  A Main Class that Reads an OBO File and populates 4 tables in the anatomy
#                database with the extracted data.
#
#               Required Files:
#                1. dao.properties file contains the database access attributes
#                2. obo.properties file contains the OBO file access attributes
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package App;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import OBOLayer.OBOFactory;
import OBOLayer.ComponentOBO;

import OBOModel.ComponentFile;

import DAOLayer.ComponentDAO;
import DAOLayer.ComponentRelationshipDAO;
import DAOLayer.ComponentCommentDAO;
import DAOLayer.ComponentSynonymDAO;
import DAOLayer.DAOFactory;

import DAOModel.Component;
import DAOModel.ComponentRelationship;
import DAOModel.ComponentComment;
import DAOModel.ComponentSynonym;


public class RunOBOLoadComponents {

	/*
	 * Main Class
	 */
    public static void run() throws Exception {

        // Obtain DAOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("file");
        //System.out.println("OBOFactory successfully obtained: " + obofactory);
        /*
        System.out.println("Create OBO-DAOs");
        System.out.println("===============");
        */

        // Obtain DAOs.
        ComponentOBO componentOBO = obofactory.getComponentOBO();
        //System.out.println("ComponentOBO successfully obtained: " + componentOBO);

        /*
        System.out.println("Extract OBO Entries");
        System.out.println("===================");
        */

        List<ComponentFile> obocomponents = new ArrayList<ComponentFile>();
        obocomponents = componentOBO.listAll();
        /*
        System.out.println("Create DAOFactory");
        System.out.println("=================");
        */
        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        //System.out.println("DAOFactory successfully obtained: " + anatomy008);

        /*
        System.out.println("Create DAOs");
        System.out.println("===========");
        */

        // Obtain DAOs.
        ComponentDAO componentDAO = anatomy008.getComponentDAO();
        //System.out.println("ComponentDAO successfully obtained: " + componentDAO);
        ComponentRelationshipDAO componentrelationshipDAO = anatomy008.getComponentRelationshipDAO();
        //System.out.println("ComponentRelationshipDAO successfully obtained: " + componentrelationshipDAO);
        ComponentCommentDAO componentcommentDAO = anatomy008.getComponentCommentDAO();
        //System.out.println("ComponentCommentDAO successfully obtained: " + componentcommentDAO);
        ComponentSynonymDAO componentsynonymDAO = anatomy008.getComponentSynonymDAO();
        //System.out.println("ComponentSynonymDAO successfully obtained: " + componentsynonymDAO);

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

        /*
        System.out.println("==========================================");
        System.out.println("LOAD OBO Components into ANA_OBO_COMPONENT");
        System.out.println("==========================================");

        System.out.println("=======================================================");
        System.out.println("LOAD OBO Components into ANA_OBO_COMPONENT_RELATIONSHIP");
        System.out.println("=======================================================");

        System.out.println("===================================================");
        System.out.println("LOAD OBO Components into ANA_OBO_COMPONENT_COMMENTS");
        System.out.println("===================================================");

        System.out.println("==================================================");
        System.out.println("LOAD OBO Components into ANA_OBO_COMPONENT_SYNONYM");
        System.out.println("==================================================");
        */

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
       		
       		List<String> obopartofs = new ArrayList<String>();
       		obopartofs = obocomponent.getChildOfs();
       		List<String> obopartoftypes = new ArrayList<String>();
       		obopartoftypes = obocomponent.getChildOfTypes();
       		
            for ( i = 0; i < obopartofs.size(); i++ ) {

           		ComponentRelationship daocomponentrelationship = new ComponentRelationship(null,
           				obocomponent.getID(),
           				obopartoftypes.get(i),
           				obopartofs.get(i));
           		
           		componentrelationshipDAO.save(daocomponentrelationship);

       		}

            List<String> usercomments = obocomponent.getUserComments();
            String usercomment = usercomments.get(0);
            
            if (usercomment.startsWith("order=")) {
            	usercomment = "None";
            }

            if (usercomments.size() > 1) {
                System.out.println("WARNING! usercomments.size() > 1, = " + Integer.toString(usercomments.size()));
            }

            if (obocomponent.getOrderComments() != null) {
                String [] ordercomments = obocomponent.getOrderComments();
                
                for ( i = 0; i < ordercomments.length; i++ ) {

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
        System.out.println("===============================================================");
        
    }

}
