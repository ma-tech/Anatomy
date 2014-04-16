/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RebuildAnadPartOf.java
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
* Description:  A Main Class that rebuilds the ANAD_PART_OF Derived Data Table
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

import anatomy.TreeAnatomy;

import daointerface.DerivedPartOfDAO;
import daointerface.NodeDAO;
import daointerface.StageDAO;

import daolayer.DAOFactory;

import daomodel.DerivedPartOf;
import daomodel.Node;
import daomodel.Stage;


public class RebuildAnadPartOf {

    private static final Set<String> VALID_VALUES = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"EMAPA:0", "group_term", "Tmp_new_group", "TS:0", "TS01", "TS02", "TS03", "TS04", "TS05", "TS06", "TS07", "TS08", "TS09", 
            		"TS10", "TS11", "TS12", "TS13", "TS14", "TS15", "TS16", "TS17", "TS18", "TS19", "TS20", "TS21", "TS22", "TS23", "TS24", 
            		"TS25", "TS26", "TS27", "TS28", "EMAPA:35577"}
            ));


	public static void run( DAOFactory daofactory, OBOFactory obofactory, 
			TreeAnatomy treeanatomyPartOnomy, ArrayList<OBOComponent> arraylistOBOComponent ) 
					throws Exception {

	    Wrapper.printMessage("RebuildAnadPartOf.run", "***", daofactory.getMsgLevel());

	    // Obtain DAOs.
	    DerivedPartOfDAO derivedpartofDAO = daofactory.getDAOImpl(DerivedPartOfDAO.class);

	    StageDAO stageDAO = daofactory.getDAOImpl(StageDAO.class);
	    NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
	    
        Iterator<OBOComponent> iteratorArraylistOBOComponent = arraylistOBOComponent.iterator();

        long longRowCount = 1;
        
        // For every componeont in the Part-Onomy ...
      	while (iteratorArraylistOBOComponent.hasNext()) {
      		
      		OBOComponent obocomponent = iteratorArraylistOBOComponent.next();
      		
        	if ( !VALID_VALUES.contains( obocomponent.getID() ) ) {
        		
            	// Find the All the PART-OF Paths for each component in the Part-Onomy
                Vector<DefaultMutableTreeNode[]> vectorNodes = treeanatomyPartOnomy.getTreePaths(obocomponent.getID());
          		
                Iterator<DefaultMutableTreeNode[]> iteratorDefaultmutabletreenodesvector = vectorNodes.iterator();
                
                // For Each Path, build a string of Ids, Period separated
              	while (iteratorDefaultmutabletreenodesvector.hasNext()) {
              		
              		DefaultMutableTreeNode [] defaultmutabletreenodearray = iteratorDefaultmutabletreenodesvector.next();
              		
              		String strPathwayOID = "";
              		String strPathwayID = "";
              		String strPathwayName = "";
              		
            		Node node = nodeDAO.findByPublicId("EMAPA:25765");

                	strPathwayOID = ObjectConverter.convert(node.getOid(), String.class);

              		long longNodeStart = 0;
              		long longNodeEnd = 0;

              		long longCount = 1;
              		long longNodeFk = 0;
              		long longNodeFkPrev = 0;
              		
              		long longDepth = defaultmutabletreenodearray.length - 2;
              		
              		boolean boolPrimary = true;
              		boolean boolPrimaryPath = true;
              		
              		List<Long> startSequences = new ArrayList<Long>();
              		List<Long> endSequences = new ArrayList<Long>();

              		for (DefaultMutableTreeNode defaultmutabletreenode: defaultmutabletreenodearray){

                  		Object nodeInfo = defaultmutabletreenode.getUserObject(); 
                        
                        if (nodeInfo instanceof OBOComponent){
                        	
                        	longNodeFkPrev = longNodeFk;
                        	
                        	OBOComponent obocomponentInstance = (OBOComponent) nodeInfo;
                        	
                        	//strPathway = strPathway + obocomponent.getID() + "(" + obocomponent.getName() + ")";
                        	
                        	if ( !VALID_VALUES.contains(obocomponentInstance.getID() ) ) {
                        		
                            	strPathwayOID = strPathwayOID + obocomponentInstance.getDBID();
                            	strPathwayID = strPathwayID + obocomponentInstance.getID();
                            	strPathwayName = strPathwayName + obocomponentInstance.getName();
                            	
                            	if ( obocomponentInstance.getID().equals("EMAPA:25765") ) {
                            		
                                	longNodeFk = node.getOid();
                                	longNodeStart = 27;
                                	longNodeEnd = 0;
                            	}
                            	else {
                            		
                                	longNodeFk = ObjectConverter.convert(obocomponentInstance.getDBID(), Long.class);
                                	longNodeStart = obocomponentInstance.getStartSequence();
                                	longNodeEnd = obocomponentInstance.getEndSequence();
                            	}
                            	
                            	startSequences.add(longNodeStart);
                            	endSequences.add(longNodeEnd);
                            	
                            	boolPrimary = obocomponentInstance.isPrimary();
                            	
                            	if ( boolPrimary != boolPrimaryPath) {
                            		
                            		boolPrimaryPath = boolPrimary;
                            	}
                            	
                            	if ( longCount < defaultmutabletreenodearray.length) {
                            		
                                	strPathwayOID = strPathwayOID + ".";
                                	strPathwayID = strPathwayID + ".";
                                	strPathwayName = strPathwayName + ".";
                            	}
                        	}
                        }
                        
                        longCount++;
                    }
                    
                    DerivedPartOf derivedpartof = new DerivedPartOf();
                    
                    derivedpartof.setOid(longRowCount);
                    derivedpartof.setSpeciesFK(obofactory.getOBOComponentAccess().species());

                    if ( strPathwayID.equals("EMAPA:25765")) {
                    	
                        Stage stageStart = stageDAO.findBySequence(0); 
                        derivedpartof.setNodeStartFK(stageStart.getOid());
                        
                        Stage stageEnd = stageDAO.findBySequence(27); 
                        derivedpartof.setNodeStopFK(stageEnd.getOid());
                        
                        Stage stageStartPath = stageDAO.findBySequence(0); 
                        derivedpartof.setPathStartFK(stageStartPath.getOid());
                        
                        Stage stageEndPath = stageDAO.findBySequence(27); 
                        derivedpartof.setPathStopFK(stageEndPath.getOid());
                    }
                    else {
                    	
                        Stage stageStart = stageDAO.findBySequence(longNodeStart); 
                        derivedpartof.setNodeStartFK(stageStart.getOid());
                        
                        Stage stageEnd = stageDAO.findBySequence(longNodeEnd); 
                        derivedpartof.setNodeStopFK(stageEnd.getOid());
                        
                        Stage stageStartPath = stageDAO.findBySequence(Collections.min(startSequences)); 
                        derivedpartof.setPathStartFK(stageStartPath.getOid());
                        
                        Stage stageEndPath = stageDAO.findBySequence(Collections.max(endSequences)); 
                        derivedpartof.setPathStopFK(stageEndPath.getOid());
                    }

                    derivedpartof.setNodeFK(longNodeFk);
                    derivedpartof.setSequence(longRowCount);
                    derivedpartof.setDepth(longDepth);
                    derivedpartof.setFullPath(strPathwayName);
                    derivedpartof.setFullPathOids(strPathwayOID);
                    derivedpartof.setFullPathEmapas(strPathwayID);
                    /*
                    derivedpartof.setFullPathJsonHead(String fullPathJsonHead);
                    derivedpartof.setFullPathJsonTail(String fullPathJsonTail);
                    */
                    derivedpartof.setPrimary(boolPrimary);
                    derivedpartof.setPrimaryPath(boolPrimaryPath);

                    derivedpartof.setParentFK(longNodeFkPrev);

                    derivedpartofDAO.create(derivedpartof);
                    
                    //System.out.println(derivedpartof.toString());
                	
                	longRowCount++;
                	/*
                	if ( longRowCount == 100) {
                		
                    	System.exit(0);
                	}
                	*/
                }
        	}
      	}
	}
}
