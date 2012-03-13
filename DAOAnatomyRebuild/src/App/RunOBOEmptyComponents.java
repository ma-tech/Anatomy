/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        OBOEmptyComponents.java
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
# Description:  A Main Class that empties 4 tables that contain OBO Temporary Data in the anatomy
#                database.
#
#               Required Files:
#                1. dao.properties file contains the database access attributes
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

import DAOLayer.ComponentDAO;
import DAOLayer.ComponentRelationshipDAO;
import DAOLayer.ComponentCommentDAO;
import DAOLayer.ComponentSynonymDAO;
import DAOLayer.DAOFactory;

import DAOModel.Component;
import DAOModel.ComponentRelationship;
import DAOModel.ComponentComment;
import DAOModel.ComponentSynonym;


public class RunOBOEmptyComponents {

	/*
	 * Main Class
	 */
    public static void run() throws Exception {


        /*
        System.out.println("==================================");
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

        List<Component> daocomponents = new ArrayList<Component>();
        daocomponents = componentDAO.listAll();
        
        System.out.println("====================================================");
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT              = " + Integer.toString(daocomponents.size()));

        List<ComponentRelationship> daocomponentrelationships = new ArrayList<ComponentRelationship>();
        daocomponentrelationships = componentrelationshipDAO.listAll();
        
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT_RELATIONSHIP = " + Integer.toString(daocomponentrelationships.size()));
        
        List<ComponentComment> daocomponentcomments = new ArrayList<ComponentComment>();
        daocomponentcomments = componentcommentDAO.listAll();
        
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT_COMMENTS     = " + Integer.toString(daocomponentcomments.size()));
        
        List<ComponentSynonym> daocomponentsynonym = new ArrayList<ComponentSynonym>();
        daocomponentsynonym = componentsynonymDAO.listAll();
        
        System.out.println("The Number of Rows in ANA_OBO_COMPONENT_SYNONYM      = " + Integer.toString(daocomponentsynonym.size()));
        System.out.println("====================================================");
        
        System.out.println("");

    }

}
