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

import daointerface.DerivedPartOfDAO;
import daointerface.DerivedPartOfPerspectivesDAO;
import daointerface.DerivedRelationshipTransitiveDAO;

import daolayer.DAOFactory;

public class EmptyDerivedTables {

	public static void run( DAOFactory daofactory, 
			boolean boolAnadPartOf, 
			boolean boolAnadRelationshipTransitive, 
			boolean boolAnadPartOfPerspective ) throws Exception {

	    Wrapper.printMessage("EmptyDerivedTables.run", "***", daofactory.getMsgLevel());

	    // Obtain DAOs.
	    DerivedPartOfDAO derivedpartofDAO = daofactory.getDAOImpl(DerivedPartOfDAO.class);
	    DerivedPartOfPerspectivesDAO derivedpartofperspectivesDAO = daofactory.getDAOImpl(DerivedPartOfPerspectivesDAO.class);
	    DerivedRelationshipTransitiveDAO derivedrelationshiptransitiveDAO = daofactory.getDAOImpl(DerivedRelationshipTransitiveDAO.class);

        Wrapper.printMessage("EmptyDerivedTables.run : ---------------------------------------------------------------", "***", daofactory.getMsgLevel());

        if ( boolAnadPartOf ) {
        	
            if ( derivedpartofDAO.countAll() > 0 ) {

            	Wrapper.printMessage("EmptyDerivedTables.run : EMPTYING ANAD_PART_OF", "***", daofactory.getMsgLevel());
            	derivedpartofDAO.empty();    
            }
            else {
            
            	Wrapper.printMessage("EmptyDerivedTables.run : ANAD_PART_OF IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }

        if ( boolAnadRelationshipTransitive ) {
        	
            if ( derivedrelationshiptransitiveDAO.countAll() > 0 ) {

            	Wrapper.printMessage("EmptyDerivedTables.run : EMPTYING ANAD_RELATIONSHIP_TRANSITIVE", "***", daofactory.getMsgLevel());
            	derivedrelationshiptransitiveDAO.empty();
            }
            else {

            	Wrapper.printMessage("EmptyDerivedTables.run : ==> ANAD_RELATIONSHIP_TRANSITIVE IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }

        if ( boolAnadPartOfPerspective ) {
        	
            if ( derivedpartofperspectivesDAO.countAll() > 0 ) {

            	Wrapper.printMessage("EmptyDerivedTables.run : EMPTYING ANAD_PART_OF_PRESPECTIVE", "***", daofactory.getMsgLevel());
            	derivedpartofperspectivesDAO.empty();
            }
            else {

            	Wrapper.printMessage("EmptyDerivedTables.run : ==> ANAD_PART_OF_PRESPECTIVE IS ALREADY EMPTY!", "***", daofactory.getMsgLevel());
            }
        }
        
        Wrapper.printMessage("EmptyDerivedTables.run : ---------------------------------------------------------------", "***", daofactory.getMsgLevel());
    }
}
