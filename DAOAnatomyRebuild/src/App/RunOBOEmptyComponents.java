/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        OBOEmptyComponents.java
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
* Description:  A Main Class that empties 4 tables that contain OBO Temporary Data in the anatomy
*                database.
*
*               Required Files:
*                1. dao.properties file contains the database access attributes
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

import utility.ObjectConverter;

public class RunOBOEmptyComponents {
	/*
	 * run Method
	 */
    public static void run() throws Exception {
    	// Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        //System.out.println("DAOFactory successfully obtained: " + anatomy008);

        // Obtain DAOs.
        ComponentDAO componentDAO = anatomy008.getComponentDAO();
        ComponentRelationshipDAO componentrelationshipDAO = anatomy008.getComponentRelationshipDAO();
        ComponentCommentDAO componentcommentDAO = anatomy008.getComponentCommentDAO();
        ComponentSynonymDAO componentsynonymDAO = anatomy008.getComponentSynonymDAO();
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getComponentAlternativeDAO();

        int i = 0;

        i = componentDAO.countAll();
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

        List<Component> daocomponents = new ArrayList<Component>();
        daocomponents = componentDAO.listAll();
        
        System.out.println("====================================================");
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT              = " + ObjectConverter.convert(daocomponents.size(), String.class));

        List<ComponentRelationship> daocomponentrelationships = new ArrayList<ComponentRelationship>();
        daocomponentrelationships = componentrelationshipDAO.listAll();
        
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT_RELATIONSHIP = " + ObjectConverter.convert(daocomponentrelationships.size(), String.class));
        
        List<ComponentComment> daocomponentcomments = new ArrayList<ComponentComment>();
        daocomponentcomments = componentcommentDAO.listAll();
        
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT_COMMENTS     = " + ObjectConverter.convert(daocomponentcomments.size(), String.class));
        
        List<ComponentSynonym> daocomponentsynonym = new ArrayList<ComponentSynonym>();
        daocomponentsynonym = componentsynonymDAO.listAll();
        
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT_SYNONYM      = " + ObjectConverter.convert(daocomponentsynonym.size(), String.class));
        
        List<ComponentAlternative> daocomponentalternative = new ArrayList<ComponentAlternative>();
        daocomponentalternative = componentalternativeDAO.listAll();
        
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT_ALTERNATIVE  = " + ObjectConverter.convert(daocomponentalternative.size(), String.class));
        System.out.println("====================================================");
        
        System.out.println("");
    }
}
