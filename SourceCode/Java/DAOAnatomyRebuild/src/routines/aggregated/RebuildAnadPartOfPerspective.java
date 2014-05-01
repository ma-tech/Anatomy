/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RebuildAnadRelationshipTransitive.java
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
* Description:  A Main Class that rebuilds the ANAD_RELATIONSHIP_TRANSITIVE Derived Data Table
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; April 2014; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.aggregated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import utility.ObjectConverter;
import utility.Wrapper;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import daointerface.PerspectiveAmbitDAO;
import daointerface.DerivedPartOfDAO;
import daointerface.DerivedRelationshipTransitiveDAO;
import daointerface.DerivedPartOfPerspectivesDAO;

import daolayer.DAOFactory;

import daomodel.PerspectiveAmbit;
import daomodel.DerivedPartOf;
import daomodel.DerivedRelationshipTransitive;
import daomodel.DerivedPartOfPerspectives;


public class RebuildAnadPartOfPerspective {


	public static void run( DAOFactory daofactory, OBOFactory obofactory ) 
					throws Exception {

	    Wrapper.printMessage("RebuildAnadPartOfPerspective.run", "***", daofactory.getMsgLevel());

	    // Obtain DAOs.
	    PerspectiveAmbitDAO perspectiveambitDAO = daofactory.getDAOImpl(PerspectiveAmbitDAO.class);
	    DerivedPartOfDAO derivedpartofDAO = daofactory.getDAOImpl(DerivedPartOfDAO.class);
	    DerivedRelationshipTransitiveDAO derivedrelationshiptransitiveDAO = daofactory.getDAOImpl(DerivedRelationshipTransitiveDAO.class);
	    DerivedPartOfPerspectivesDAO derivedpartofperspectivesDAO = daofactory.getDAOImpl(DerivedPartOfPerspectivesDAO.class);

	    ArrayList<PerspectiveAmbit> perspectiveambits = (ArrayList<PerspectiveAmbit>) perspectiveambitDAO.listAll();
	    
        Iterator<PerspectiveAmbit> iteratorPerspectiveAmbits = perspectiveambits.iterator();
        
  		//System.out.println("START Nodes ONLY");

        while (iteratorPerspectiveAmbits.hasNext()) {
      		
      		PerspectiveAmbit perspectiveambit = iteratorPerspectiveAmbits.next();
      		
      		if ( perspectiveambit.isStart() && !perspectiveambit.isStop() ) {
      			
          		//System.out.println(perspectiveambit.toString());
          		
          		ArrayList<DerivedRelationshipTransitive> derivedrelationshiptransitives = (ArrayList<DerivedRelationshipTransitive>) 
          				derivedrelationshiptransitiveDAO.listAllByAncestor( perspectiveambit.getNodeFK() );
                
          		Iterator<DerivedRelationshipTransitive> iteratorDerivedRelationshipTransitives = derivedrelationshiptransitives.iterator();

          		while (iteratorDerivedRelationshipTransitives.hasNext()) {
          			
          			DerivedRelationshipTransitive derivedrelationshiptransitive = iteratorDerivedRelationshipTransitives.next();

          			DerivedPartOfPerspectives derivedpartofperspectives = new DerivedPartOfPerspectives();
              			
          			
          			derivedpartofperspectives.setPerspectiveFK(perspectiveambit.getPerspectiveFK());
          			
          			DerivedPartOf derivedpartof = derivedpartofDAO.findByNodeFK( derivedrelationshiptransitive.getDescendantFK() );  
          					
          			derivedpartofperspectives.setPartOfFK(derivedpartof.getOid());
          			
          			
          			if (  perspectiveambit.getNodeFK() == derivedrelationshiptransitive.getDescendantFK() ) {
          				
          				derivedpartofperspectives.setAncestor(1);
          			}
          			else {
          				
          				derivedpartofperspectives.setAncestor(0);
          			}

          			
          			derivedpartofperspectives.setNodeFK(derivedrelationshiptransitive.getDescendantFK());
          			
          			derivedpartofperspectivesDAO.create(derivedpartofperspectives);
            	}
      		}
      	}
      	
      		
  		//System.out.println("STOP Nodes ONLY");

        iteratorPerspectiveAmbits = perspectiveambits.iterator();
        
      	while (iteratorPerspectiveAmbits.hasNext()) {

      		PerspectiveAmbit perspectiveambit = iteratorPerspectiveAmbits.next();

      		if ( !perspectiveambit.isStart() && perspectiveambit.isStop() ) {
      			
          		//System.out.println(perspectiveambit.toString());
          		
          		ArrayList<DerivedRelationshipTransitive> derivedrelationshiptransitives = (ArrayList<DerivedRelationshipTransitive>) 
          				derivedrelationshiptransitiveDAO.listAllByAncestor( perspectiveambit.getNodeFK() );
                
          		Iterator<DerivedRelationshipTransitive> iteratorDerivedRelationshipTransitives = derivedrelationshiptransitives.iterator();

          		while (iteratorDerivedRelationshipTransitives.hasNext()) {
          			
          			DerivedRelationshipTransitive derivedrelationshiptransitive = iteratorDerivedRelationshipTransitives.next();

          			DerivedPartOfPerspectives derivedpartofperspectives = 
          					derivedpartofperspectivesDAO.findByNodeFKAndPerspective(derivedrelationshiptransitive.getDescendantFK(), 
          							perspectiveambit.getPerspectiveFK());

          			if ( derivedpartofperspectives != null ) {
              			
              			//System.out.println("derivedrelationshiptransitive.toString() = " + derivedrelationshiptransitive.toString());
              			//System.out.println("derivedpartofperspectives.toString() = " + derivedpartofperspectives.toString());
              			
              			derivedpartofperspectivesDAO.delete(derivedpartofperspectives);
          			}
            	}
      		}
      	}
      	
      	
  		//System.out.println("START AND STOP Nodes");

  		iteratorPerspectiveAmbits = perspectiveambits.iterator();
        
      	while (iteratorPerspectiveAmbits.hasNext()) {

      		PerspectiveAmbit perspectiveambit = iteratorPerspectiveAmbits.next();

      		if ( perspectiveambit.isStart() && perspectiveambit.isStop() ) {
      			
          		//System.out.println(perspectiveambit.toString());
          		
          		DerivedPartOfPerspectives derivedpartofperspectives = new DerivedPartOfPerspectives();
          		
          		DerivedPartOf derivedpartof = derivedpartofDAO.findByNodeFK( perspectiveambit.getNodeFK() );
          		
          		if ( derivedpartof != null ) {
          			
          			//System.out.println("derivedpartof.toString() = " + derivedpartof.toString());
          			//System.out.println("derivedpartofperspectives.toString() = " + derivedpartofperspectives.toString());
          			
              		derivedpartofperspectives.setPerspectiveFK(perspectiveambit.getPerspectiveFK());
              		derivedpartofperspectives.setPartOfFK(derivedpartof.getOid());
              		derivedpartofperspectives.setAncestor(0);
              		derivedpartofperspectives.setNodeFK( perspectiveambit.getNodeFK() );
              		
          			//System.out.println("Create A START and STOP node D-POP row" + derivedpartofperspectives.toString());
          			
              		derivedpartofperspectivesDAO.create(derivedpartofperspectives);
          		}
      		}
      	}
      	
	}
}
