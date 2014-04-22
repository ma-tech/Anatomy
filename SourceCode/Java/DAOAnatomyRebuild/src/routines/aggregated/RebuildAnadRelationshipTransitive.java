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
import daointerface.NodeDAO;
import daointerface.DerivedPartOfDAO;
import daointerface.DerivedRelationshipTransitiveDAO;
import daolayer.DAOFactory;
import daomodel.Node;
import daomodel.DerivedPartOf;
import daomodel.DerivedRelationshipTransitive;


public class RebuildAnadRelationshipTransitive {


	public static void run( DAOFactory daofactory, OBOFactory obofactory ) 
					throws Exception {

	    Wrapper.printMessage("RebuildAnadRelationshipTransitive.run", "***", daofactory.getMsgLevel());

	    // Obtain DAOs.
	    NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
	    DerivedPartOfDAO derivedpartofDAO = daofactory.getDAOImpl(DerivedPartOfDAO.class);
	    DerivedRelationshipTransitiveDAO derivedrelationshiptransitiveDAO = daofactory.getDAOImpl(DerivedRelationshipTransitiveDAO.class);

	    ArrayList<Node> nodes = (ArrayList<Node>) nodeDAO.listAllNoGroups();
	    
        Iterator<Node> iteratorNodes = nodes.iterator();
        
        long rowCount = 1;

        // For every componeont in the Part-Onomy ...
      	while (iteratorNodes.hasNext()) {
      		
      		Node node = iteratorNodes.next();
      		
      		ArrayList<DerivedPartOf> derivedpartofs = (ArrayList<DerivedPartOf>) 
      				derivedpartofDAO.listAllByNodeFK(ObjectConverter.convert(node.getOid(), String.class));
            
      		Iterator<DerivedPartOf> iteratorDerivedPartOf = derivedpartofs.iterator();

      		while (iteratorDerivedPartOf.hasNext()) {
      			
      			DerivedPartOf derivedpartof = iteratorDerivedPartOf.next();

      			if ( derivedpartof.isPrimary() && derivedpartof.isPrimaryPath()) {
      				
          			DerivedRelationshipTransitive derivedrelationshiptransitive = new DerivedRelationshipTransitive();
          			
              		String [] pathOids = derivedpartof.getFullPathOids().split("\\.");

              		//if ( pathOids.length > 1 ) {

              		for (int index = 0; index < pathOids.length; index++ ) {
              			
              			if ( !derivedrelationshiptransitiveDAO.existAncestorDescendent(ObjectConverter.convert(pathOids[index], Long.class), node.getOid()) ) {
              				
                  			derivedrelationshiptransitive.setOid(rowCount);
                  			derivedrelationshiptransitive.setDescendantFK(node.getOid());
                  			//derivedrelationshiptransitive.setAncestorFK(pathOids[pathOids.length - 2]);
                  			derivedrelationshiptransitive.setAncestorFK(pathOids[index]);
                  			derivedrelationshiptransitive.setRelTypeFK("part-of");
                
                  			derivedrelationshiptransitiveDAO.create(derivedrelationshiptransitive);
                  			
                  			rowCount++;
              			}
              		}
      			}
                
        	}
      	}
	}
}
