/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
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
package routines.runnable;

import daolayer.ComponentDAO;
import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentCommentDAO;
import daolayer.ComponentSynonymDAO;
import daolayer.ComponentAlternativeDAO;
import daolayer.ComponentOrderDAO;

import daolayer.DAOFactory;

import obolayer.OBOFactory;

import utility.Wrapper;

public class EmptyComponentsTables {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {

        // Obtain DAOs.
        ComponentDAO componentDAO = daofactory.getComponentDAO();
        ComponentRelationshipDAO componentrelationshipDAO = daofactory.getComponentRelationshipDAO();
        ComponentCommentDAO componentcommentDAO = daofactory.getComponentCommentDAO();
        ComponentSynonymDAO componentsynonymDAO = daofactory.getComponentSynonymDAO();
        ComponentAlternativeDAO componentalternativeDAO = daofactory.getComponentAlternativeDAO();
        ComponentOrderDAO componentorderDAO = daofactory.getComponentOrderDAO();

        Wrapper.printMessage("---------------------------------------------------------------", "LOW", requestMsgLevel);

        if ( componentDAO.countAll() > 0 ) {

        	Wrapper.printMessage("EMPTYING ANA_OBO_COMPONENT", "LOW", requestMsgLevel);
        	componentDAO.empty();    
        }
        else {
        
        	Wrapper.printMessage("ANA_OBO_COMPONENT IS ALREADY EMPTY!", "LOW", requestMsgLevel);
        }
        if ( componentrelationshipDAO.countAll() > 0 ) {

        	Wrapper.printMessage("EMPTYING ANA_OBO_COMPONENT_RELATIONSHIP", "LOW", requestMsgLevel);
        	componentrelationshipDAO.empty();
        }
        else {

        	Wrapper.printMessage("==> ANA_OBO_COMPONENT_RELATIONSHIP IS ALREADY EMPTY!", "LOW", requestMsgLevel);
        }
        if ( componentcommentDAO.countAll() > 0 ) {

        	Wrapper.printMessage("EMPTYING ANA_OBO_COMPONENT_COMMENT", "LOW", requestMsgLevel);
        	componentcommentDAO.empty();
        }
        else {

        	Wrapper.printMessage("==> ANA_OBO_COMPONENT_COMMENT IS ALREADY EMPTY!", "LOW", requestMsgLevel);
        }
        if ( componentorderDAO.countAll() > 0 ) {
        
        	Wrapper.printMessage("EMPTYING ANA_OBO_COMPONENT_ORDER", "LOW", requestMsgLevel);
        	componentorderDAO.empty();
        }
        else {
        
        	Wrapper.printMessage("==> ANA_OBO_COMPONENT_ORDER IS ALREADY EMPTY!", "LOW", requestMsgLevel);
        }
        if ( componentsynonymDAO.countAll() > 0 ) {

        	Wrapper.printMessage("EMPTYING ANA_OBO_COMPONENT_SYNONYM", "LOW", requestMsgLevel);
        	componentsynonymDAO.empty();
        }
        else {
        
        	Wrapper.printMessage("==> ANA_OBO_COMPONENT_SYNONYM IS ALREADY EMPTY!", "LOW", requestMsgLevel);
        }
        if ( componentalternativeDAO.countAll() > 0 ) {
        
        	Wrapper.printMessage("EMPTYING ANA_OBO_COMPONENT_ALTERNATIVE", "LOW", requestMsgLevel);
        	componentalternativeDAO.empty();
        }
        else {

        	Wrapper.printMessage("==> ANA_OBO_COMPONENT_ALTERNATIVE IS ALREADY EMPTY!", "LOW", requestMsgLevel);
        }
        
        Wrapper.printMessage("---------------------------------------------------------------", "LOW", requestMsgLevel);
    }
}