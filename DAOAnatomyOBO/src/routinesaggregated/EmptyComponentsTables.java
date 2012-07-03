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

package routinesaggregated;

import daolayer.ComponentDAO;
import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentCommentDAO;
import daolayer.ComponentSynonymDAO;
import daolayer.ComponentAlternativeDAO;
import daolayer.ComponentOrderDAO;

import daolayer.DAOFactory;

import obolayer.OBOFactory;


public class EmptyComponentsTables {
	/*
	 * run Method
	 */
    public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {

        // Obtain DAOs.
        ComponentDAO componentDAO = daofactory.getComponentDAO();
        ComponentRelationshipDAO componentrelationshipDAO = daofactory.getComponentRelationshipDAO();
        ComponentCommentDAO componentcommentDAO = daofactory.getComponentCommentDAO();
        ComponentSynonymDAO componentsynonymDAO = daofactory.getComponentSynonymDAO();
        ComponentAlternativeDAO componentalternativeDAO = daofactory.getComponentAlternativeDAO();
        ComponentOrderDAO componentorderDAO = daofactory.getComponentOrderDAO();

        if ( obofactory.getComponentOBO().debug() ) {
            System.out.println("---------------------------------------------------------------");
            if ( componentDAO.countAll() > 0 ) {
                System.out.println("EMPTYING ANA_OBO_COMPONENT");
           		componentDAO.empty();
            }
            else {
                System.out.println("ANA_OBO_COMPONENT IS ALREADY EMPTY!");
            }
            if ( componentrelationshipDAO.countAll() > 0 ) {
                System.out.println("EMPTYING ANA_OBO_COMPONENT_RELATIONSHIP");
           		componentrelationshipDAO.empty();
            }
            else {
                System.out.println("==> ANA_OBO_COMPONENT_RELATIONSHIP IS ALREADY EMPTY!");
            }
            if ( componentcommentDAO.countAll() > 0 ) {
                System.out.println("EMPTYING ANA_OBO_COMPONENT_COMMENT");
           		componentcommentDAO.empty();
            }
            else {
                System.out.println("==> ANA_OBO_COMPONENT_COMMENT IS ALREADY EMPTY!");
            }
            if ( componentorderDAO.countAll() > 0 ) {
                System.out.println("EMPTYING ANA_OBO_COMPONENT_ORDER");
           		componentorderDAO.empty();
            }
            else {
                System.out.println("==> ANA_OBO_COMPONENT_ORDER IS ALREADY EMPTY!");
            }
            if ( componentsynonymDAO.countAll() > 0 ) {
                System.out.println("EMPTYING ANA_OBO_COMPONENT_SYNONYM");
                componentsynonymDAO.empty();
            }
            else {
                System.out.println("==> ANA_OBO_COMPONENT_SYNONYM IS ALREADY EMPTY!");
            }
            if ( componentalternativeDAO.countAll() > 0 ) {
                System.out.println("EMPTYING ANA_OBO_COMPONENT_ALTERNATIVE");
                componentalternativeDAO.empty();
            }
            else {
                System.out.println("==> ANA_OBO_COMPONENT_ALTERNATIVE IS ALREADY EMPTY!");
            }
            System.out.println("---------------------------------------------------------------");
        }
        else {
            if ( componentDAO.countAll() > 0 ) {
           		componentDAO.empty();
            }
            if ( componentrelationshipDAO.countAll() > 0 ) {
           		componentrelationshipDAO.empty();
            }
            if ( componentcommentDAO.countAll() > 0 ) {
           		componentcommentDAO.empty();
            }
            if ( componentorderDAO.countAll() > 0 ) {
           		componentorderDAO.empty();
            }
            if ( componentsynonymDAO.countAll() > 0 ) {
                componentsynonymDAO.empty();
            }
            if ( componentalternativeDAO.countAll() > 0 ) {
                componentalternativeDAO.empty();
            }
        }

    }
}
