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
* Version:      1
*
* Description:  A Main Class that empties 4 Components tables that contain OBO Temporary Data 
*                in the anatomy database.
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.aggregated;

import utility.Wrapper;

import daointerface.ComponentAlternativeDAO;
import daointerface.ComponentCommentDAO;
import daointerface.ComponentDAO;
import daointerface.ComponentOrderDAO;
import daointerface.ComponentRelationshipDAO;
import daointerface.ComponentSynonymDAO;

import daolayer.DAOFactory;

public class EmptyComponentsTables {

	public static void run( DAOFactory daofactory,
			boolean boolAnaOboComponent, 
			boolean boolAnaOboComponentRelationship, 
			boolean boolAnaOboComponentComment,
			boolean boolAnaOboComponentOrder, 
			boolean boolAnaOboComponentSynonym, 
			boolean boolAnaOboComponentAlternative ) throws Exception {

	    Wrapper.printMessage("emptycomponentstables.run", "***", daofactory.getMsgLevel());

	    // Obtain DAOs.
	    //daofactory.getDAOImpl(ThingDAO.class).getLevel()
        ComponentDAO componentDAO = daofactory.getDAOImpl(ComponentDAO.class);
        ComponentRelationshipDAO componentrelationshipDAO = daofactory.getDAOImpl(ComponentRelationshipDAO.class);
        ComponentCommentDAO componentcommentDAO = daofactory.getDAOImpl(ComponentCommentDAO.class);
        ComponentSynonymDAO componentsynonymDAO = daofactory.getDAOImpl(ComponentSynonymDAO.class);
        ComponentAlternativeDAO componentalternativeDAO = daofactory.getDAOImpl(ComponentAlternativeDAO.class);
        ComponentOrderDAO componentorderDAO = daofactory.getDAOImpl(ComponentOrderDAO.class);

        Wrapper.printMessage("emptycomponentstables.run : ---------------------------------------------------------------", "***", daofactory.getMsgLevel());

        if ( boolAnaOboComponent ) {
        	
            if ( componentDAO.countAll() > 0 ) {

            	Wrapper.printMessage("emptycomponentstables.run : EMPTYING ANA_OBO_COMPONENT", "***", daofactory.getMsgLevel());
            	componentDAO.empty();    
            }
            else {
            
            	Wrapper.printMessage("emptycomponentstables.run : ANA_OBO_COMPONENT IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }

        if (boolAnaOboComponentRelationship) {
        	
            if ( componentrelationshipDAO.countAll() > 0 ) {

            	Wrapper.printMessage("emptycomponentstables.run : EMPTYING ANA_OBO_COMPONENT_RELATIONSHIP", "***", daofactory.getMsgLevel());
            	componentrelationshipDAO.empty();
            }
            else {

            	Wrapper.printMessage("emptycomponentstables.run : ==> ANA_OBO_COMPONENT_RELATIONSHIP IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }
        
        if ( boolAnaOboComponentComment ) {
        	
            if ( componentcommentDAO.countAll() > 0 ) {

            	Wrapper.printMessage("emptycomponentstables.run : EMPTYING ANA_OBO_COMPONENT_COMMENT", "***", daofactory.getMsgLevel());
            	componentcommentDAO.empty();
            }
            else {

            	Wrapper.printMessage("emptycomponentstables.run : ==> ANA_OBO_COMPONENT_COMMENT IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }
        
        if ( boolAnaOboComponentOrder ) {
        	
            if ( componentorderDAO.countAll() > 0 ) {
                
            	Wrapper.printMessage("emptycomponentstables.run : EMPTYING ANA_OBO_COMPONENT_ORDER", "***", daofactory.getMsgLevel());
            	componentorderDAO.empty();
            }
            else {
            
            	Wrapper.printMessage("emptycomponentstables.run : ==> ANA_OBO_COMPONENT_ORDER IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }
        
        if ( boolAnaOboComponentSynonym ) {
        	
            if ( componentsynonymDAO.countAll() > 0 ) {

            	Wrapper.printMessage("emptycomponentstables.run : EMPTYING ANA_OBO_COMPONENT_SYNONYM", "***", daofactory.getMsgLevel());
            	componentsynonymDAO.empty();
            }
            else {
            
            	Wrapper.printMessage("emptycomponentstables.run : ==> ANA_OBO_COMPONENT_SYNONYM IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }
        
        if ( boolAnaOboComponentAlternative ) {
        	
            if ( componentalternativeDAO.countAll() > 0 ) {
                
            	Wrapper.printMessage("emptycomponentstables.run : EMPTYING ANA_OBO_COMPONENT_ALTERNATIVE", "***", daofactory.getMsgLevel());
            	componentalternativeDAO.empty();
            }
            else {

            	Wrapper.printMessage("emptycomponentstables.run : ==> ANA_OBO_COMPONENT_ALTERNATIVE IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }
        
        Wrapper.printMessage("emptycomponentstables.run : ---------------------------------------------------------------", "***", daofactory.getMsgLevel());
    }
}
