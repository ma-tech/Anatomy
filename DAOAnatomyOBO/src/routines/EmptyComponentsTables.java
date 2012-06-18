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

package routines;

import daolayer.ComponentDAO;
import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentCommentDAO;
import daolayer.ComponentSynonymDAO;
import daolayer.ComponentAlternativeDAO;
import daolayer.ComponentOrderDAO;

import daolayer.DAOFactory;


public class EmptyComponentsTables {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

    	// Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        // Obtain DAOs.
        ComponentDAO componentDAO = anatomy008.getComponentDAO();
        ComponentRelationshipDAO componentrelationshipDAO = anatomy008.getComponentRelationshipDAO();
        ComponentCommentDAO componentcommentDAO = anatomy008.getComponentCommentDAO();
        ComponentSynonymDAO componentsynonymDAO = anatomy008.getComponentSynonymDAO();
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getComponentAlternativeDAO();
        ComponentOrderDAO componentorderDAO = anatomy008.getComponentOrderDAO();

        int i = 0;

        i = componentDAO.countAll();
        if ( i > 0 ) {
       		componentDAO.empty();
        }

        i = componentrelationshipDAO.countAll();
        if ( i > 0 ) {
       		componentrelationshipDAO.empty();
        }

        i = componentcommentDAO.countAll();
        if ( i > 0 ) {
       		componentcommentDAO.empty();
        }

        i = componentsynonymDAO.countAll();
        if ( i > 0 ) {
            componentsynonymDAO.empty();
        }

        i = componentalternativeDAO.countAll();
        if ( i > 0 ) {
            componentalternativeDAO.empty();
        }

        i = componentorderDAO.countAll();
        if ( i > 0 ) {
            componentorderDAO.empty();
        }

    }
}
