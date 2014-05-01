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
import java.util.Enumeration;
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
            		"TS25", "TS26", "TS27", "TS28", "EMAPA:35577", "EMAPA:25765"}
            ));


	public static void run( DAOFactory daofactory, OBOFactory obofactory, 
			TreeAnatomy treeanatomyPartOnomy, ArrayList<OBOComponent> arraylistOBOComponent ) 
					throws Exception {

	    Wrapper.printMessage("RebuildAnadPartOf.run", "***", daofactory.getMsgLevel());

	    // Obtain DAOs.
	    DerivedPartOfDAO derivedpartofDAO = daofactory.getDAOImpl(DerivedPartOfDAO.class);

	    StageDAO stageDAO = daofactory.getDAOImpl(StageDAO.class);
	    NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
	    
	    Long longMinOid = nodeDAO.minimumOID();
	    
	    Node node = nodeDAO.findByOid(longMinOid);
	    
	    String strRootOID = ObjectConverter.convert(node.getOid(), String.class);
	    String strRootID = node.getPublicId();
	    String strRootName = node.getComponentName();

        long longRowCount = 0;

	    DerivedPartOf derivedpartof = new DerivedPartOf();

	    derivedpartof.setOid(longRowCount);
	    derivedpartof.setSpeciesFK(obofactory.getOBOComponentAccess().species());
	    
	    Stage stageStart = stageDAO.findBySequence(0); 
	    derivedpartof.setNodeStartFK(stageStart.getOid());
	    Stage stageEnd = stageDAO.findBySequence(27); 
	    derivedpartof.setNodeStopFK(stageEnd.getOid());
	    Stage stageStartPath = stageDAO.findBySequence(0); 
	    derivedpartof.setPathStartFK(stageStartPath.getOid());
	    Stage stageEndPath = stageDAO.findBySequence(27); 
	    derivedpartof.setPathStopFK(stageEndPath.getOid());
	    
	    derivedpartof.setPrimaryPath(true);
	    derivedpartof.setNodeFK(node.getOid());
	    derivedpartof.setSequence(0);
	    derivedpartof.setDepth(1);
	    derivedpartof.setFullPath(node.getComponentName());
	    derivedpartof.setFullPathOids(ObjectConverter.convert(node.getOid(), String.class));
	    derivedpartof.setFullPathEmapas(node.getPublicId());
	    /*
	    derivedpartof.setFullPathJsonHead(String fullPathJsonHead);
	    derivedpartof.setFullPathJsonTail(String fullPathJsonTail);
	    */
	    derivedpartof.setPrimary(true);
	    derivedpartof.setParentFK(0);
	    
	    derivedpartofDAO.create(derivedpartof);
	    
	    longRowCount++;
	    
	    List<String> publicIDs = new ArrayList<String>();
	    
	    publicIDs.add(node.getPublicId());
	    
	    DefaultMutableTreeNode motherNode = treeanatomyPartOnomy.getDefaultmutabletreenodeMother();
	  	  
	    //System.out.println("preorderEnumeration()");
	    //System.out.println("-----------------------");
	    //System.out.println();
  	  
	    Enumeration en = motherNode.preorderEnumeration();
	    
	    while (en.hasMoreElements()) {

	    	// Unfortunately the enumeration isn't genericised so we need to downcast
	    	// when calling nextElement():
	    	DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) en.nextElement();

	    	Object nodeInfo = treeNode.getUserObject(); 
          
	    	if (nodeInfo instanceof OBOComponent){ 
        	
	    		OBOComponent obocomponent = (OBOComponent) nodeInfo;

	    		if ( !publicIDs.contains(obocomponent.getID())) {
	    			
		    		if ( !VALID_VALUES.contains( obocomponent.getID() ) ) {
		    			
                		//System.out.println("Node ID = " + obocomponent.getID());
		        		
		            	// Find the All the PART-OF Paths for each component in the Part-Onomy
		                Vector<DefaultMutableTreeNode[]> vectorNodes = treeanatomyPartOnomy.getTreePaths(obocomponent.getID());
		          		
		                Iterator<DefaultMutableTreeNode[]> iteratorDefaultmutabletreenodesvector = vectorNodes.iterator();
		                
		                // For Each Path, build a string of Ids, Period separated
		              	while (iteratorDefaultmutabletreenodesvector.hasNext()) {
		              		
		              		DefaultMutableTreeNode [] defaultmutabletreenodearray = iteratorDefaultmutabletreenodesvector.next();
		              		
		              		String strPathwayOID = "";
		              		String strPathwayID = "";
		              		String strPathwayName = "";
		              		
		                	strPathwayOID = strRootOID + ".";
		                	strPathwayID = strRootID + ".";
		                	strPathwayName = strRootName + ".";

		              		long longNodeStart = 0;
		              		long longNodeEnd = 0;

		              		long longCount = 1;
		              		long longNodeFk = 0;
		              		long longNodeFkPrev = 0;
		              		
		              		long longDepth = defaultmutabletreenodearray.length - 2;
		              		
		              		boolean boolPrimary = true;
		              		
		              		List<Long> startSequences = new ArrayList<Long>();
		              		List<Long> endSequences = new ArrayList<Long>();

		              		List<Boolean> groupPath = new ArrayList<Boolean>();

		              		for (DefaultMutableTreeNode defaultmutabletreenode: defaultmutabletreenodearray){

		                  		Object newNodeInfo = defaultmutabletreenode.getUserObject(); 
		                        
		                        if (newNodeInfo instanceof OBOComponent){
		                        	
		                        	longNodeFkPrev = longNodeFk;
		                        	
		                        	OBOComponent obocomponentInstance = (OBOComponent) newNodeInfo;
		                        	
		                        	if ( !VALID_VALUES.contains(obocomponentInstance.getID() ) ) {
		                        		
		                        		//System.out.println("  Pathway ID = " + obocomponentInstance.getID());
		                        		
		                            	strPathwayOID = strPathwayOID + obocomponentInstance.getDBID();
		                            	strPathwayID = strPathwayID + obocomponentInstance.getID();
		                            	strPathwayName = strPathwayName + obocomponentInstance.getName();
		                                	
		                            	longNodeFk = ObjectConverter.convert(obocomponentInstance.getDBID(), Long.class);
		                            	longNodeStart = obocomponentInstance.getStartSequence();
		                            	longNodeEnd = obocomponentInstance.getEndSequence();
		                                
		                            	boolPrimary = obocomponentInstance.isPrimary();
		                                
		                            	startSequences.add(longNodeStart);
		                            	endSequences.add(longNodeEnd);
		                            	
		                            	groupPath.add(boolPrimary);
		                            	
		                            	if ( longCount < defaultmutabletreenodearray.length) {
		                            		
		                                	strPathwayOID = strPathwayOID + ".";
		                                	strPathwayID = strPathwayID + ".";
		                                	strPathwayName = strPathwayName + ".";
		                            	}
		                        	}
		                        }
		                        
		                        longCount++;
		                    }
		                    
		                    derivedpartof = new DerivedPartOf();
		                    
		                    derivedpartof.setOid(longRowCount);
		                    derivedpartof.setSpeciesFK(obofactory.getOBOComponentAccess().species());

		                    stageStart = stageDAO.findBySequence(longNodeStart); 
		                    derivedpartof.setNodeStartFK(stageStart.getOid());
		                    stageEnd = stageDAO.findBySequence(longNodeEnd); 
		                    derivedpartof.setNodeStopFK(stageEnd.getOid());
		                    stageStartPath = stageDAO.findBySequence(Collections.min(startSequences)); 
		                    derivedpartof.setPathStartFK(stageStartPath.getOid());
		                    stageEndPath = stageDAO.findBySequence(Collections.max(endSequences)); 
		                    derivedpartof.setPathStopFK(stageEndPath.getOid());


		                    if (groupPath.contains(false)) {
		                    
		                    	derivedpartof.setPrimaryPath(false);
		                    }
		                    else {
		                    
		                    	derivedpartof.setPrimaryPath(true);
		                    }

		                    derivedpartof.setNodeFK(longNodeFk);
		                    derivedpartof.setSequence( longRowCount );
		                    derivedpartof.setDepth(longDepth);
		                    derivedpartof.setFullPath(strPathwayName);
		                    derivedpartof.setFullPathOids(strPathwayOID);
		                    derivedpartof.setFullPathEmapas(strPathwayID);

		                    //derivedpartof.setFullPathJsonHead(String fullPathJsonHead);
		                    //derivedpartof.setFullPathJsonTail(String fullPathJsonTail);

		                    derivedpartof.setPrimary(boolPrimary);

		                    derivedpartof.setParentFK(longNodeFkPrev);

		                    derivedpartofDAO.create(derivedpartof);
		                    
		                	longRowCount++;
		                }
	              	

		              	publicIDs.add(obocomponent.getID());
		    		}
	        	}
	    	}
	    }

	    //System.out.println("Total Number of Nodes = " + longRowCount);

        // For every row in ANAD_PART_OF we need to update the Parent Part Of column ...
      	ArrayList<DerivedPartOf> arraylistDerivedPartOf = (ArrayList<DerivedPartOf>) derivedpartofDAO.listAll();

        Iterator<DerivedPartOf> iteratorArraylistDerivedPartOf = arraylistDerivedPartOf.iterator();
        
      	while (iteratorArraylistDerivedPartOf.hasNext()) {
      		
      		DerivedPartOf derivedpartofUpdate = iteratorArraylistDerivedPartOf.next();
      			
      		if ( derivedpartofUpdate.getFullPathOids().lastIndexOf('.') != -1 ) {
      			
          		String parentPath = derivedpartofUpdate.getFullPathOids().substring(0, derivedpartofUpdate.getFullPathOids().lastIndexOf('.'));
          		
          		DerivedPartOf derivedpartofParent = derivedpartofDAO.findByOidPath(parentPath);
          		
          		derivedpartofUpdate.setParentFK(derivedpartofParent.getOid());
          		
          		derivedpartofDAO.save(derivedpartofUpdate);
      		}
      	}
	}

}
