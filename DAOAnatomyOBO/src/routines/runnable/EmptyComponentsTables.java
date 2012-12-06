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

	    Wrapper.printMessage("emptycomponentstables.run", "***", requestMsgLevel);

	    // Obtain DAOs.
        ComponentDAO componentDAO = daofactory.getComponentDAO();
        ComponentRelationshipDAO componentrelationshipDAO = daofactory.getComponentRelationshipDAO();
        ComponentCommentDAO componentcommentDAO = daofactory.getComponentCommentDAO();
        ComponentSynonymDAO componentsynonymDAO = daofactory.getComponentSynonymDAO();
        ComponentAlternativeDAO componentalternativeDAO = daofactory.getComponentAlternativeDAO();
        ComponentOrderDAO componentorderDAO = daofactory.getComponentOrderDAO();

        Wrapper.printMessage("emptycomponentstables.run:---------------------------------------------------------------", "***", requestMsgLevel);

        if ( componentDAO.countAll() > 0 ) {

        	Wrapper.printMessage("emptycomponentstables.run:EMPTYING ANA_OBO_COMPONENT", "***", requestMsgLevel);
        	componentDAO.empty();    
        }
        else {
        
        	Wrapper.printMessage("emptycomponentstables.run:ANA_OBO_COMPONENT IS ALREADY EMPTY!", "***", requestMsgLevel);
        }
        if ( componentrelationshipDAO.countAll() > 0 ) {

        	Wrapper.printMessage("emptycomponentstables.run:EMPTYING ANA_OBO_COMPONENT_RELATIONSHIP", "***", requestMsgLevel);
        	componentrelationshipDAO.empty();
        }
        else {

        	Wrapper.printMessage("emptycomponentstables.run:==> ANA_OBO_COMPONENT_RELATIONSHIP IS ALREADY EMPTY!", "***", requestMsgLevel);
        }
        if ( componentcommentDAO.countAll() > 0 ) {

        	Wrapper.printMessage("emptycomponentstables.run:EMPTYING ANA_OBO_COMPONENT_COMMENT", "***", requestMsgLevel);
        	componentcommentDAO.empty();
        }
        else {

        	Wrapper.printMessage("emptycomponentstables.run:==> ANA_OBO_COMPONENT_COMMENT IS ALREADY EMPTY!", "***", requestMsgLevel);
        }
        if ( componentorderDAO.countAll() > 0 ) {
        
        	Wrapper.printMessage("emptycomponentstables.run:EMPTYING ANA_OBO_COMPONENT_ORDER", "***", requestMsgLevel);
        	componentorderDAO.empty();
        }
        else {
        
        	Wrapper.printMessage("emptycomponentstables.run:==> ANA_OBO_COMPONENT_ORDER IS ALREADY EMPTY!", "***", requestMsgLevel);
        }
        if ( componentsynonymDAO.countAll() > 0 ) {

        	Wrapper.printMessage("emptycomponentstables.run:EMPTYING ANA_OBO_COMPONENT_SYNONYM", "***", requestMsgLevel);
        	componentsynonymDAO.empty();
        }
        else {
        
        	Wrapper.printMessage("emptycomponentstables.run:==> ANA_OBO_COMPONENT_SYNONYM IS ALREADY EMPTY!", "***", requestMsgLevel);
        }
        if ( componentalternativeDAO.countAll() > 0 ) {
        
        	Wrapper.printMessage("emptycomponentstables.run:EMPTYING ANA_OBO_COMPONENT_ALTERNATIVE", "***", requestMsgLevel);
        	componentalternativeDAO.empty();
        }
        else {

        	Wrapper.printMessage("emptycomponentstables.run:==> ANA_OBO_COMPONENT_ALTERNATIVE IS ALREADY EMPTY!", "***", requestMsgLevel);
        }
        
        Wrapper.printMessage("emptycomponentstables.run:---------------------------------------------------------------", "***", requestMsgLevel);
    }
}
