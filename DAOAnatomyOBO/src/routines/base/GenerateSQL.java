/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        GenerateSQL.java
*
* Date:         2008
*
* Author:       MeiSze Lam and Attila Gyenesi
*
* Copyright:    2009 Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  Produce a tree structure from an ArrayList of OBO Components
*               
*               Methods:
*                1.  inserts
*                2.  deletes
*                3.  updates
*
*               Sub-Methods:
*                1.  validateDeleteTermList
*                2.  deleteComponentFromAllTables
*                3.  updateOrder
*                4.  createDifferenceTimedComponents
*                5.  createDifferenceSynonyms
*                6.  createDifferenceParents
*                7. getChangedStagesTermList
*                8. getChangedNamesTermList
*                9. getChangedSynonymsTermList
*                10. getChangedParentsTermList
*                11. getChangedPrimaryStatusTermList
*                12. getNewParentsTermList
*                13. getChangedOrderTermList
*                14. recursiveGetDependentDescendants
*
*               Helper Methods:
*                1.  getChangedStagesTermList
*                2.  getChangedNamesTermList
*                3.  getChangedSynonymsTermList
*                4.  getChangedParentsTermList
*                5.  getChangedPrimaryStatusTermList
*                6.  getNewParentsTermList
*                7.  getChangedOrderTermList
*                
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; March 2012; Completely rewrite to use a standardised DAO Layer
* Mike Wicks; Nov 2012; Refactor, move All Database access to wrapper objects around Tables
*
*----------------------------------------------------------------------------------------------
*/
package routines.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daolayer.SynonymDAO;
import daolayer.JOINNodeRelationshipDAO;
import daolayer.JOINNodeRelationshipNodeDAO;
import daolayer.JOINTimedNodeStageDAO;

import daomodel.Synonym;
import daomodel.JOINNodeRelationship;
import daomodel.JOINNodeRelationshipNode;

import obolayer.OBOException;
import obolayer.OBOFactory;

import obomodel.OBOComponent;

import routines.database.AnaSynonym;
import routines.database.AnaTimedNode;
import routines.database.AnaRelationship;
import routines.database.AnaNode;
import routines.database.AnaVersion;
import routines.database.DatabaseException;

import utility.Wrapper;

public class GenerateSQL {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel;
	
    //file properties
    private String strSpecies;
    private String project;
    
    //treebuilder object to use hashmaps to get component (getComponent)
    private TreeBuilder tree; 
    
    //term list for relationships to be created for changed parents from modifed components
    private ArrayList<OBOComponent> diffCreateRelList;
    
    //term list for relationships to be created for deleted parents from modified components
    private ArrayList<OBOComponent> diffDeleteRelList;
    
    //term list for synonyms to be created for changed synonyms from modified components
    private ArrayList<OBOComponent> diffCreateSynList;
    
    //term list for synonyms to be created for deleted synonyms from modified components
    private ArrayList<OBOComponent> diffDeleteSynList;
    
    //check whether was processed all the way
    private boolean processed;
    
    //abstract class configuration
    private OBOComponent abstractclassobocomponent; 
    private OBOComponent stageclassobocomponent; 
    private OBOComponent groupclassobocomponent; 
    private OBOComponent grouptermclassobocomponent; 
    
    //Data Access Objects (DAOs)
    private SynonymDAO synonymDAO; 
    private JOINNodeRelationshipDAO joinnoderelationshipDAO;
    private JOINNodeRelationshipNodeDAO joinnoderelationshipnodeDAO; 
    private JOINTimedNodeStageDAO jointimednodestageDAO;
    
    // A List of Valid Values for OBO Terms that are NOT Terms in the Tree/DAG
    private static final Set<String> VALID_VALUES = new HashSet<String>(Arrays.asList(
        new String[] 
    	    {"group_term","Tmp_new_group","EMAPA:0","EMAPA:25765","EHDAA:0","EHDAA:1","ECAPA:0","ECAPA:1",
    	     "TS:0","TS01","TS02","TS03","TS04","TS05","TS06","TS07","TS08","TS09","TS10","TS11","TS12","TS13","TS14","TS15","TS16","TS17","TS18","TS19","TS20","TS21","TS22","TS23","TS24","TS25","TS26","TS27","TS28",
    	     "CS:0","CS01","CS02","CS03","CS04","CS05a","CS05b","CS05c","CS06a","CS06b","CS07","CS08","CS09","CS10","CS11","CS12","CS13","CS14","CS15","CS16","CS17","CS18","CS19","CS20","CS21","CS22","CS23",
    	     "HH:0","EGK-I","EGK-II","EGK-III","EGK-IV","EGK-V","EGK-VI","EGK-VII","EGK-VIII","EGK-IX","EGK-X","EGK-XI","EGK-XII","EGK-XIII","EGK-XIV","HH01","HH02","HH03","HH04","HH05","HH06","HH07","HH08","HH09","HH10","HH11","HH12","HH13","HH14","HH15","HH16","HH17","HH18","HH19","HH20","HH21","HH22","HH23","HH24","HH25","HH26","HH27","HH28","HH29","HH30","HH31","HH32","HH33","HH34","HH35","HH36","HH37","HH38","HH39","HH40","HH41","HH42","HH43","HH44","HH45","HH46","HH47","HH48"
            }
        ));

    
    // Constructors -------------------------------------------------------------------------------
    public GenerateSQL(
    		String requestMsgLevel,
    		DAOFactory daofactory, 
    		OBOFactory obofactory, 
            ArrayList<OBOComponent> proposedTermList,
            TreeBuilder treebuilder,
            TreeBuilder refTreebuilder ) throws Exception {

    	setProcessed( true );

    	try {
    		
        	this.daofactory = daofactory;

        	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("generatesql.constructor", "***", this.requestMsgLevel);
            
            this.project = obofactory.getComponentOBO().project();
            this.strSpecies = obofactory.getComponentOBO().species();
            
            this.tree = treebuilder;

            this.diffCreateRelList = new ArrayList<OBOComponent>();
            this.diffDeleteRelList = new ArrayList<OBOComponent>();
            
            this.diffDeleteSynList = new ArrayList<OBOComponent>();
            this.diffCreateSynList = new ArrayList<OBOComponent>();

        	// 1: set abstract class parameters
            // 2: set stage class parameters
            // 3: temporary new group class parameters
            // 4: group term class parameters
            this.abstractclassobocomponent = new OBOComponent();
            this.stageclassobocomponent = new OBOComponent();
            this.groupclassobocomponent = new OBOComponent();
            this.grouptermclassobocomponent = new OBOComponent();

            this.abstractclassobocomponent.setName( obofactory.getComponentOBO().abstractClassName() );
            this.abstractclassobocomponent.setID( obofactory.getComponentOBO().abstractClassId() );
            this.abstractclassobocomponent.setNamespace( obofactory.getComponentOBO().abstractClassNamespace() );

            this.stageclassobocomponent.setName( obofactory.getComponentOBO().stageClassName() );
            this.stageclassobocomponent.setID( obofactory.getComponentOBO().stageClassId() );
            this.stageclassobocomponent.setNamespace( obofactory.getComponentOBO().stageClassNamespace() );

            this.groupclassobocomponent.setName( obofactory.getComponentOBO().groupClassName() );
            this.groupclassobocomponent.setID( obofactory.getComponentOBO().groupClassId() );
            this.groupclassobocomponent.setNamespace( obofactory.getComponentOBO().groupClassNamespace() );

            this.grouptermclassobocomponent.setName( obofactory.getComponentOBO().groupTermClassName() );
            this.grouptermclassobocomponent.setID( obofactory.getComponentOBO().groupTermClassId() );
            this.grouptermclassobocomponent.setNamespace( obofactory.getComponentOBO().groupTermClassNamespace() );
            
            //internal termlists for data manipulation
            ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();
            ArrayList<OBOComponent> deletedComponents = new ArrayList<OBOComponent>();
            ArrayList<OBOComponent> changedComponents = new ArrayList<OBOComponent>();
            
            //construct internal arraylists
            OBOComponent component = new OBOComponent();

            // Go through all the proposed terms and validate (again)
            for (int i = 0; i<proposedTermList.size(); i++) {

            	component = proposedTermList.get(i);

            	// NEW Terms
                if ( component.getStatusChange().equals("NEW") ) {
                	
                    if ( component.getStatusRule().equals("FAILED") ) {

                    	Wrapper.printMessage("generatesql.constructor:SQL queries for New OBOComponent " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!", "*", this.requestMsgLevel);
                        setProcessed( false );
                    }
                    else if ( component.getStatusRule().equals("PASSED") ) {
                    	
                        setProcessed( true );
                    }
                    else {
                    	
                        setProcessed( false );
                    }
                    
                    newComponents.add( component );
                }
                
                // DELETED Terms
                if ( component.getStatusChange().equals("DELETED") ) {
                	
                	if ( component.getStatusRule().equals("FAILED") ) {

                		Wrapper.printMessage("generatesql.constructor:SQL queries for Deleted OBOComponent " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!", "*", this.requestMsgLevel);
                        setProcessed( false );
                    }
                    else if ( component.getStatusRule().equals("PASSED") ) {
                    	
                        setProcessed( true );
                    }
                    else {
                    	
                        setProcessed( false );
                    }
                	
                    deletedComponents.add( component );
                }
                
                // MODIFIED Terms
                if ( component.getStatusChange().equals("CHANGED") ) {
                	
                    if ( component.getStatusRule().equals("FAILED") ) {
                        
                    	Wrapper.printMessage("generatesql.constructor:SQL queries for Changed OBOComponent " +
                                    component.getID() + " " + component.getName() +
                                    " with rule violation have been generated!", "*", this.requestMsgLevel);
                        setProcessed( false );
                    }
                    else if ( component.getStatusRule().equals("PASSED") ) {
                    	
                        setProcessed( true );
                    }
                    else {
                    	
                        setProcessed( false );
                    }
     
                    changedComponents.add( component );
                }
                
                if ( !component.getStatusChange().equals("NEW") || 
                	!component.getStatusChange().equals("DELETED") || 
                	!component.getStatusChange().equals("CHANGED") ) {
                	
                	if (VALID_VALUES.contains( component.getID() )) {

                		setProcessed( true );
                	}
                }
                
                // Terms that are neither NEW, MODIFIED or DELETED - ERROR!!!
                else {

                    setProcessed( false );
            	}
            }

            // Are we good to go?
            //  Yes:
            if ( isProcessed() ) {
            	
                // Obtain DAOs.
                this.synonymDAO = daofactory.getSynonymDAO();
                this.joinnoderelationshipDAO = daofactory.getJOINNodeRelationshipDAO();
                this.joinnoderelationshipnodeDAO = daofactory.getJOINNodeRelationshipNodeDAO();
                this.jointimednodestageDAO = daofactory.getJOINTimedNodeStageDAO();

                //  set a version record in ANA_VERSION for this update
                if ( !( newComponents.isEmpty() && 
                		deletedComponents.isEmpty() && 
                		changedComponents.isEmpty() ) ) {

                	AnaVersion anaversion = new AnaVersion( this.requestMsgLevel, this.daofactory);

                	if ( !anaversion.insertANA_VERSION() ) {

                		throw new DatabaseException("anaversion.insertANA_VERSION");
                	}
                	
                    // Do INSERTs of NEW components
                    inserts( newComponents );

                    // Do UPDATEs of MODIFIED components
                    updates( changedComponents );
                    
                    // Do DELETES of DELETED components
                    deletes( deletedComponents );

                    // Rebuild ANA_RELATIONSHIP_PROJECT
                    AnaRelationship anarelationship = new AnaRelationship( this.requestMsgLevel, this.daofactory );

                    if ( !anarelationship.rebuildANA_RELATIONSHIP_PROJECT()) {

                 	   throw new DatabaseException("anarelationship.insertANA_RELATIONSHIP");
                    }

                    setProcessed( true );
                }
            }
            // NOT Good to Go
            else {

            	Wrapper.printMessage("generatesql.constructor:No record inserted: Database Update " +
                        "did not occur because SYSTEM failed to detect any " +
                        "changes in the OBO File!", "*", this.requestMsgLevel);
            	setProcessed( false );
            }
    	}
        catch ( DatabaseException dbex ) {
        	
            setProcessed( false );
            dbex.printStackTrace();
        }
        catch ( OBOException oboex ) {
        	
            setProcessed( false );
            oboex.printStackTrace();
        }
        catch ( DAOException daoex ) {
        	
            setProcessed( false );
            daoex.printStackTrace();
        }
        catch ( Exception ex ) {
        	
            setProcessed( false );
            ex.printStackTrace();
        }
    }
    
    // Methods ------------------------------------------------------------------------------------

    //  Wrapper Routine for ALL INSERTS to the database
    private void inserts( ArrayList<OBOComponent> newComponents ) throws Exception {

        Wrapper.printMessage("generatesql.inserts", "***", this.requestMsgLevel);
    		
    	try {
    		
            if (newComponents.size() > 0) {
            	
                // INSERTS into ANA_NODE
                AnaNode ananode = new AnaNode( this.requestMsgLevel, this.daofactory );
                
                if ( !ananode.insertANA_NODE( newComponents, 
                		"INSERT", 
                		this.strSpecies, 
                		this.tree ) ) {

             	   throw new DatabaseException("ananode.insertANA_NODE");
                }
                
                // INSERTS into ANA_RELATIONSHIP
                AnaRelationship anarelationship = new AnaRelationship( this.requestMsgLevel, this.daofactory );
                
                if ( !anarelationship.insertANA_RELATIONSHIP( ananode.getUpdatedComponentList(),
                		"INSERT",
                		this.project,
                		this.strSpecies, 
                		this.tree,
                		this.abstractclassobocomponent, 
                		this.stageclassobocomponent, 
                		this.groupclassobocomponent, 
                		this.grouptermclassobocomponent) ) {

             	   throw new DatabaseException("anarelationship.insertANA_RELATIONSHIP");
                }
                
                // INSERTS into ANA_TIMED_NODE
                AnaTimedNode anatimednode = new AnaTimedNode( this.requestMsgLevel, this.daofactory );
                
                if ( !anatimednode.insertANA_TIMED_NODE( ananode.getUpdatedComponentList(),
                		"INSERT", 
                		this.strSpecies) ) {

                	throw new DatabaseException("anatimednode.insertANA_TIMED_NODE");
                }
                
                // INSERTS into ANA_SYNONYM
                AnaSynonym anasynonym = new AnaSynonym( this.requestMsgLevel, this.daofactory );
                
                if ( !anasynonym.insertANA_SYNONYM( newComponents, 
                		"INSERT" ) ) {

                	throw new DatabaseException("anasynonym.insertANA_SYNONYM");
                }
            }
    	}
        catch ( DatabaseException dbex ) {
        	
        	setProcessed( false );
        	dbex.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
    }
    

    //  Wrapper Routine for ALL DELETES to the database
    /*
     delete components, set DBIDs and get only components that have dbids based on emap id

    CRITICAL DELETION VALIDATION: 
     to disallow deletion of components that do have children in database:
      1. check that term exists in database 
      2. if term = primary, check that all descendants are due for deletion in obo file as well
      3. if one descendant specified in database is not found in OBO file 
          OR descendant is found but not specified for deletion, 
      4. pass on invalid term to unDeletedCompList
    
    pass valid terms to validDeleteComponents

    insert log records for deleted components

    perform deletion on valid deletion term list

    reorder siblings of deleted components that have order
    */
    private void deletes( ArrayList<OBOComponent> deletedComponentsIn ) throws Exception {
    	
        Wrapper.printMessage("generatesql.deletes", "***", this.requestMsgLevel);
    		
    	try {
    		
            if ( deletedComponentsIn.size() > 0 ) {

            	// delete components, set DBIDs and get only components that have dbids based on emap id
                AnaNode ananode = new AnaNode( this.requestMsgLevel, this.daofactory );

                if ( !ananode.setDatabaseOIDs(deletedComponentsIn, 
                		"INSERT") ){

                	throw new DatabaseException("ananode.setDatabaseOIDs");
                }
                
                //pass valid terms to validDeleteComponents

                //perform deletion on valid deletion term list
                deleteComponentFromAllTables( validateDeleteTermList( ananode.getUpdatedComponentList() ) );
                
                //reorder siblings of deleted components that have order
                AnaRelationship anarelationship = new AnaRelationship( this.requestMsgLevel, this.daofactory );

                if ( !anarelationship.reorderANA_RELATIONSHIP( validateDeleteTermList( ananode.getUpdatedComponentList() ),
                		this.strSpecies, 
                		"deletes") ) {
             	   
                	throw new DatabaseException("anarelationship.reorderANA_RELATIONSHIP");
                }
        	}
    	}
        catch ( DatabaseException dbex ) {
        	
        	setProcessed( false );
        	dbex.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 
    }
 
    
    //  Wrapper Routine for ALL UPDATES to the database
    private void updates( ArrayList<OBOComponent> changedComponentsIn ) throws Exception {
    	
        Wrapper.printMessage("generatesql.updates", "***", this.requestMsgLevel);
    		
    	try {
    		
            if (changedComponentsIn.size() > 0) {
                
            	//modify components, set DBIDs and get only components that have dbids based on emap id
                AnaNode ananode = new AnaNode( this.requestMsgLevel, this.daofactory );

                if ( !ananode.setDatabaseOIDs(changedComponentsIn, 
                		"INSERT") ){
                		
                	throw new DatabaseException("ananode.setDatabaseOIDs");
                }

                //get components whose stage ranges have changed
                //find ranges of stages that need to be inserted/deleted, create
                // temporary components for ranges and perform insertion and deletion for modified stage ranges
                AnaTimedNode anatimednode = new AnaTimedNode( this.requestMsgLevel, this.daofactory );

                if ( !anatimednode.updateANA_TIMED_NODE( createDifferenceTimedComponents( getChangedStagesTermList( ananode.getStartingComponentList() ) ),
                		"INSERT", 
                		this.strSpecies) ) {

                	throw new DatabaseException("anatimednode.updateANA_TIMED_NODE");
                }
                
                //get components whose names have changed
                //perform update for modified names
                if ( !ananode.updateANA_NODE_name( getChangedNamesTermList( ananode.getStartingComponentList() ), 
                		"UPDATE") ) {

             	   throw new DatabaseException("ananode.updateANA_NODE_name");
                }
                
                //get components whose synonyms have changed
                createDifferenceSynonyms( getChangedSynonymsTermList( ananode.getStartingComponentList() ) );
                
                //find ranges of stages that need to be inserted/deleted, create
                // temporary components for ranges
                //perform insertion and deletion for modified synonyms
                AnaSynonym anasynonym = new AnaSynonym( this.requestMsgLevel, this.daofactory );

                if ( !anasynonym.updateANA_SYNONYM( this.diffCreateSynList, 
                		this.diffDeleteSynList, 
                		"DELETE") ) {

             	   throw new DatabaseException("anasynonym.updateANA_SYNONYM");
                }
                
                //get components whose parents have changed
                createDifferenceParents( getChangedParentsTermList( ananode.getStartingComponentList() ) );
                		
                //find ranges of stages that need to be inserted/deleted, create temporary components for ranges
                //perform insertion and deletion for modified parent relationships
                AnaRelationship anarelationship = new AnaRelationship( this.requestMsgLevel, this.daofactory );

                if ( !anarelationship.insertANA_RELATIONSHIP( this.diffCreateRelList, 
                		"INSERT",
                		this.project,
                		this.strSpecies, 
                		this.tree,
                		this.abstractclassobocomponent, 
                		this.stageclassobocomponent, 
                		this.groupclassobocomponent, 
                		this.grouptermclassobocomponent) ) {
             	   
                	throw new DatabaseException("anarelationship.insertANA_RELATIONSHIP_UpdateParents");
                }
                
                if ( !anarelationship.deleteANA_RELATIONSHIP(this.diffDeleteRelList, 
                		"DELETE") ) {

                	throw new DatabaseException("anarelationship.deleteANA_RELATIONSHIP_UpdateParents");
                }
                
                //get components whose primary status have changed
                //perform update for modified primary status
                if ( !ananode.updateANA_NODE_primary( getChangedPrimaryStatusTermList( ananode.getStartingComponentList() ), 
                		"UPDATE") ) {

                	throw new DatabaseException("ananode.updateANA_NODE_primary");
                }
                
                //get components whose ordering have changed and perform reordering
                updateOrder( getChangedOrderTermList( ananode.getStartingComponentList() ) );

                //get components whose have NEW parents 
                if ( !anarelationship.insertANA_RELATIONSHIP( getNewParentsTermList( ananode.getStartingComponentList() ),
                		"INSERT",
                		this.project,
                		this.strSpecies, 
                		this.tree,
                		this.abstractclassobocomponent, 
                		this.stageclassobocomponent, 
                		this.groupclassobocomponent, 
                		this.grouptermclassobocomponent) ) {
             	   
                	throw new DatabaseException("anarelationship.insertANA_RELATIONSHIP_NewParents");
                }
            }
    	}
        catch ( DatabaseException dbex ) {
        	
        	setProcessed( false );
        	dbex.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 
    }

    
    // SUB Methods ------------------------------------------------------------------------------------
    
    // updateOrder
    private void updateOrder( ArrayList<OBOComponent> changedOrderTermList ) throws Exception {

        Wrapper.printMessage("generatesql.updateOrder", "***", this.requestMsgLevel);
        
        try {
        	
            // get all parents whose children have a changed order
            //  for each parent 
            //  get the children that have an order sequence 
            //  line them up from 0 - max entry 
            //  send collection of parents-orderedchildren to querymaker
            //parent-> child order 1, child2, child3
            HashMap<String, ArrayList<String>> mapOrderedChildren = new HashMap<String, ArrayList<String>>(); 
            ArrayList<String> parents = new ArrayList<String>();
            ArrayList<String> children = new ArrayList<String>();
            ArrayList<String> commentsOnParent = new ArrayList<String>();
            
            OBOComponent childComponent = new OBOComponent();
            
            String[] arrayFirstWord = null;
            String forChild = "";
            int intMaxOrder = 0;
            
            for (OBOComponent component: changedOrderTermList) {
            	
                //get all parents
                parents.addAll(component.getChildOfs());

                //check for each parent whether there is ordering
                for (String parent: parents) {
                
                	commentsOnParent.clear(); //reset for each parent
                    children.clear(); //reset for each parent
                    intMaxOrder = -1; //reset for each parent
                    
                    //get all children
                    children.addAll( tree.getChildrenBasedOnParent(parent) );
                    
                    //iterate through all children of each parent and gather all order comments
                    for (String child: children) {

                    	//reset for each child
                    	forChild = ""; 
                    
                        //get component for child
                        childComponent = tree.getComponent(child);
                        
                        //get order from child component based on the parent
                        String[] arrOrderComments = childComponent.getOrderCommentOnParent(parent);
                        
                        //if there is an order put in order vector
                        if ( arrOrderComments!=null ) {
                        
                        	//find max order number for this series of siblings
                            arrayFirstWord = arrOrderComments[0].split(" ");
                            
                            if ( Integer.parseInt(arrayFirstWord[0]) > intMaxOrder ) {
                            	
                                intMaxOrder = Integer.parseInt(arrayFirstWord[0]);
                            }

                            //get first word from order comment and append child to it to make a new comment based on 'for child'
                            forChild = arrayFirstWord[0] + " for " + childComponent.getID();
                            
                            //add to order comments for this parent
                            commentsOnParent.add(forChild);

                            //should never enter here if rule properly checked in CheckComponents
                            if ( arrOrderComments.length>1 ) {
                            
                                Wrapper.printMessage("generatesql.updateOrder:WARNING! more than one " +
                                        "order comment for the same parent "
                                        + parent + " detected for component " +
                                        component.getID() + "!", "*", this.requestMsgLevel);
                            }
                        } 
                    }
                    
                    //iterated through list of children
                    //once all order comments collected from the children of one parent
                    //reorder the comments and put in Array based on sequence
                    ArrayList<String> arrOrdered = null;
                    
                    if (intMaxOrder!=-1) {
                    
                    	String[] ordered = new String[intMaxOrder+1];
                        arrOrdered = new ArrayList<String>();
                        int intOrder = 0;
                        String strChild = "";
                        
                        for (String comment: commentsOnParent) {
                        
                        	//get sequence number from order comment
                            arrayFirstWord = comment.split(" ");
                            intOrder = Integer.parseInt(arrayFirstWord[0]); //first item in array is order no.
                            strChild = arrayFirstWord[arrayFirstWord.length-1]; //last item in array is child component id
                            
                            //build an ordered, sorted array of comments for the parent
                            if (intOrder!=-1) {
                            	
                                ordered[intOrder] = strChild;
                            }
                        }
                        
                        //convert ordered array to arraylist, retaining order of elements
                        //put unordered elements at the end of list
                        for(int i=0; i<ordered.length; i++) {
                        	
                            arrOrdered.add(ordered[i]);
                        }
                    }
                    
                    //build hashmap parent-> sorted children
                    //don't add entry for parents that already have entry
                    if ( mapOrderedChildren.get(parent)==null ) {
                    	
                        mapOrderedChildren.put(parent, arrOrdered);
                    }
                }
            }
            
            //send hashmap to update_orderANA_RELATIONSHIP
            AnaRelationship anarelationship = new AnaRelationship( this.requestMsgLevel, this.daofactory );
            
            if ( !anarelationship.update_orderANA_RELATIONSHIP( mapOrderedChildren, "updateOrder") ) {

            	throw new DatabaseException("anarelationship.update_orderANA_RELATIONSHIP");
            }
        }
        catch ( DatabaseException dbex ) {
        	
        	setProcessed( false );
        	dbex.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        }
    }

    
    /*
    method to validate term list scheduled for deletion

     check that all for each component in term list, if its primary, then all
     descendants are scheduled to be deleted
     
     if one of its descendants cannot be found in OBO file or has not been
     scheduled for deletion the current component will be removed from the returned term list
     returned term list will be used for actual deletion and updated to ANA_LOG
     
     IMPORTANT: deletion is ONLY ALLOWED if the terms to be deleted by the
                user does not contain any
                child-parent relationships in ANA_RELATIONSHIP where the
                component to be deleted is the parent
                and the child is not due for deletion ie. not in the
                deletedTermList
     
     JUSTIFICATION: if term has a child term in the DB term list but not in
                    proposed term list,
                    then deleting the term will cause undesirable orphan terms
   */
    private ArrayList<OBOComponent> validateDeleteTermList(ArrayList<OBOComponent> deletedTermList) throws Exception {

        Wrapper.printMessage("generatesql.validateDeleteTermList", "***", this.requestMsgLevel);
        	
        ArrayList<OBOComponent> dbTermList = new ArrayList<OBOComponent>();
        Vector<String> dependentDescendants = new Vector<String>();
        
        Boolean invalidDelete = false;
        
        try {
        	
            //for each term in deletedTermList
            for (OBOComponent deleteCompie: deletedTermList) {
            	
                //get all dependent descendants
                dependentDescendants = recursiveGetDependentDescendants(deleteCompie.getID(), dependentDescendants, invalidDelete);
            
                if ( invalidDelete ) {
                	
                    //disallow deletion
                    deleteCompie.setCheckComment("Delete Record Warning: " + 
                        deleteCompie.getID() + " still has descendants in " +
                        "the database. Deletion not allowed.");
                
                    deleteCompie.setCheckComment("Delete Record Warning: " +
                        "Deletion of this term could potentially result in " +
                        "undesirable orphan terms or other ontology " +
                        "violations. Please generate a new OBO file from the " +
                        "database and retry deletion");
                }
                else {
                    
                	dbTermList.add(deleteCompie);
                }
            }
        }
        catch (Exception ex) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
        
        return dbTermList;
    }

    // deleteComponentFromAllTables
    private void deleteComponentFromAllTables (ArrayList <OBOComponent> validDeleteTermList) throws Exception {

        Wrapper.printMessage("generatesql.deleteComponentFromAllTables", "***", this.requestMsgLevel);
        	
        try {
        	
            if ( !validDeleteTermList.isEmpty() ) {
            	
                AnaTimedNode anatimednode = new AnaTimedNode( this.requestMsgLevel, this.daofactory );

                if ( !anatimednode.deleteANA_TIMED_NODE(validDeleteTermList, 
                		"deleteComponentFromTables") ) {

                    throw new DatabaseException("anatimednode.deleteANA_TIMED_NODE for deleteComponentFromTables");
                }

                AnaNode ananode = new AnaNode( this.requestMsgLevel, this.daofactory );
                
                if ( !ananode.deleteANA_NODE(validDeleteTermList, 
                		this.strSpecies, 
                		"deleteComponentFromTables") ) {

                	throw new DatabaseException("ananode.deleteANA_NODE for deleteComponentFromTables");
                }

                AnaSynonym anasynonym = new AnaSynonym( this.requestMsgLevel, this.daofactory );
                
                if ( !anasynonym.deleteANA_SYNONYM(validDeleteTermList, 
                		"deleteComponentFromTables") ) {
             	
                	throw new DatabaseException("anasynonym.deleteANA_SYNONYM for deleteComponentFromTables");
                }
              
                AnaRelationship anarelationship = new AnaRelationship( this.requestMsgLevel, this.daofactory );
                
                if ( !anarelationship.deleteANA_RELATIONSHIP(validDeleteTermList, "" +
                		"deleteComponentFromTables") ) {
             	
                	throw new DatabaseException("anarelationship.deleteANA_RELATIONSHIP for deleteComponentFromTables");
                }
            }
        }
        catch ( DatabaseException dbex ) {
        	
        	setProcessed( false );
        	dbex.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        } 
    }

    
    // recursiveGetDependentDescendants 
	private Vector<String> recursiveGetDependentDescendants(String componentID, Vector< String > componentIDs, boolean invalidDelete) throws Exception {

        Wrapper.printMessage("generatesql.recursiveGetDependentDescendants", "***", this.requestMsgLevel);
        	
        Vector< String > descendants = componentIDs;
        Vector< String > childrenIDs = new Vector<String>();

        OBOComponent component = new OBOComponent();
        OBOComponent deletedcomponent = new OBOComponent();
        
        try {
        	
            ArrayList<JOINNodeRelationshipNode> joinnoderelationshipnodes = 
            		(ArrayList<JOINNodeRelationshipNode>) this.joinnoderelationshipnodeDAO.listAllByParentId(componentID);
            
          	Iterator<JOINNodeRelationshipNode> iteratorjoinnoderelationshipnode = joinnoderelationshipnodes.iterator();

          	while (iteratorjoinnoderelationshipnode.hasNext()) {

          		JOINNodeRelationshipNode joinnoderelationshipnode = iteratorjoinnoderelationshipnode.next();
          	
          		childrenIDs.add( joinnoderelationshipnode.getAPublicId());
          	}

            if ( childrenIDs.isEmpty() ) {
                
            	return descendants;
            }
            else {
            	
                for (String s: childrenIDs) {
                    /*
                      check to see whether all dependent descendants have been
                       specified for deletion
                    */
                    component = this.tree.getComponent(s);

                    if ( component==null ) {
                
                    	invalidDelete = true;
                        deletedcomponent = this.tree.getComponent( s );
                        
                        deletedcomponent.setCheckComment("Delete Record " +
                            "Warning: Deletion of this term results in " +
                            "orphan term " + component.getID() + " " +
                            component.getName() + ". Please generate a new OBO " +
                            "file from the database and retry deletion.");
                    }
                    else if ( !component.getStatusChange().equals("DELETED") ) {

                    	component.setStatusChange("DELETED");
                        invalidDelete = true;
                        deletedcomponent = this.tree.getComponent( s );

                        deletedcomponent.setCheckComment("Delete Record " +
                            "Warning: Deletion of this term results in " +
                            "orphan term " + component.getID() + " " +
                            component.getName() + ". Please generate a new " +
                            "OBO file from the database and retry " +
                            "deletion.");
                    }
                    
                    descendants.add( s );
                    descendants = recursiveGetDependentDescendants( s, descendants, invalidDelete );
                }
            }
        }
        catch ( DAOException daoex ) {
        	
        	setProcessed( false );
        	daoex.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 

        return descendants;
    } 

	
    //  method to measure difference in stage ranges between modified components and existing components in DB
    private ArrayList<OBOComponent> createDifferenceTimedComponents( ArrayList<OBOComponent> diffStageTermList ) throws Exception {

        Wrapper.printMessage("generatesql.createDifferenceTimedComponents", "***", this.requestMsgLevel);
        	
        ArrayList<OBOComponent> diffCreateTimedCompList = new ArrayList<OBOComponent>();
        
        try {
        	
            for (OBOComponent component: diffStageTermList) {

                int startSequence = this.jointimednodestageDAO.minSequenceByNodeFk(Long.valueOf(component.getDBID()));
                
                int endSequence = this.jointimednodestageDAO.maxSequenceByNodeFk(Long.valueOf(component.getDBID()));

                //compare stage ranges between component and databasecomponent
                // for creating new timed components
                if ( startSequence > component.getStartSequence() ) {
                   
                	OBOComponent createtimedcomponent = new OBOComponent();
                   
                	createtimedcomponent.setID( component.getID() );
                	createtimedcomponent.setName( component.getName() );
                	createtimedcomponent.setDBID( component.getDBID() );
                	createtimedcomponent.setStart( component.getStart() );
                	createtimedcomponent.setEndSequence( startSequence - 1, this.strSpecies );

                	diffCreateTimedCompList.add( createtimedcomponent );
                }
                else if ( endSequence < component.getEndSequence() ) {
                   
                	OBOComponent createtimedcomponent = new OBOComponent();
                   
                	createtimedcomponent.setID( component.getID() );
                	createtimedcomponent.setName( component.getName() );                   
                	createtimedcomponent.setDBID( component.getDBID() );
                	createtimedcomponent.setStartSequence( endSequence + 1, this.strSpecies );
                	createtimedcomponent.setEndSequence( component.getEndSequence(), this.strSpecies );
                   
                	diffCreateTimedCompList.add( createtimedcomponent );
                }
                //for deleting existing timed components
                else if ( startSequence < component.getStartSequence() ) {
                   
                	OBOComponent delTimedCompie = new OBOComponent();
                   
                	delTimedCompie.setID( component.getID() );
                	delTimedCompie.setName( component.getName() );
                	delTimedCompie.setDBID( component.getDBID() );
                	delTimedCompie.setStartSequence( startSequence, this.strSpecies );
                	delTimedCompie.setEndSequence( component.getStartSequence() - 1, this.strSpecies );

                	diffCreateTimedCompList.add( delTimedCompie );
                }
                else if ( endSequence > component.getEndSequence() ) {
                    
                	OBOComponent delTimedCompie = new OBOComponent();
                    
                	delTimedCompie.setID( component.getID() );
                    delTimedCompie.setName( component.getName() );
                    delTimedCompie.setDBID( component.getDBID() );
                    delTimedCompie.setStartSequence( component.getEndSequence() + 1, this.strSpecies );
                    delTimedCompie.setEndSequence( endSequence, this.strSpecies );

                    diffCreateTimedCompList.add( delTimedCompie );
                }
            }
        }
        catch ( DAOException daoex ) {
        	
        	setProcessed( false );
        	daoex.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 
        
        return diffCreateTimedCompList;
    }


    // createDifferenceSynonyms
    private void createDifferenceSynonyms( ArrayList<OBOComponent> diffSynonymTermList ) throws Exception {

        Wrapper.printMessage("generatesql.createDifferenceSynonyms", "***", this.requestMsgLevel);
        	
        OBOComponent databasecomponent = new OBOComponent();
        OBOComponent deletesynonymcomponent = new OBOComponent();
        OBOComponent insertsynonymcomponent = new OBOComponent();        
        
        ArrayList<String> synonyms = new ArrayList<String>();
        ArrayList<String> deleteSynonyms = new ArrayList<String>();
        ArrayList<String> insertSynonyms = new ArrayList<String>();
        
        try {
        	
            //for each component where parents have changed
            for (OBOComponent component: diffSynonymTermList) {

            	ArrayList<Synonym> synonymlist = (ArrayList<Synonym>) this.synonymDAO.listByObjectFK(Long.valueOf(component.getDBID()));
            	
                //reset temporary component's parents for each component
                databasecomponent.setSynonyms( new ArrayList<String>() );
                
                //add to temporary component
              	Iterator<Synonym> iteratorsynonym = synonymlist.iterator();

              	while (iteratorsynonym.hasNext()) {
              		
              		Synonym synonym = iteratorsynonym.next();

                    databasecomponent.addSynonym( synonym.getName());
              	}

                //make 2 arraylists to compare with component's parents and group parents
                synonyms.clear();
                synonyms.addAll( component.getSynonyms() );
                
                //get parents to be deleted
                //parents owned by databasecomponent but not by component
                deleteSynonyms.clear(); 
                //reset for each databasecomponent to component comparison
                deleteSynonyms.addAll( databasecomponent.getSynonyms() );
                deleteSynonyms.removeAll( synonyms );
                
                if ( !deleteSynonyms.isEmpty() ) {
                	
                    deletesynonymcomponent = new OBOComponent();
                
                    ArrayList < String > copyDeleteSynonyms = new ArrayList<String>();
                    
                    copyDeleteSynonyms.addAll( deleteSynonyms );
                    
                    deletesynonymcomponent.setDBID( component.getDBID() );
                    deletesynonymcomponent.setName( component.getName() );
                    deletesynonymcomponent.setSynonyms( copyDeleteSynonyms );
                    
                    this.diffDeleteSynList.add( deletesynonymcomponent );
                }
                
                //get parents to be inserted
                //parents owned by component but not by databasecomponent
                insertSynonyms.clear(); 
                //reset 
                insertSynonyms.addAll( synonyms );
                insertSynonyms.removeAll( databasecomponent.getSynonyms() );
                
                if ( !insertSynonyms.isEmpty() ) {
                	
                    insertsynonymcomponent = new OBOComponent();
                
                    ArrayList < String > copyInsertSynonyms = new ArrayList<String>();
                    
                    copyInsertSynonyms.addAll( insertSynonyms );
                    
                    insertsynonymcomponent.setDBID( component.getDBID() );
                    insertsynonymcomponent.setName( component.getName() );
                    insertsynonymcomponent.setSynonyms( copyInsertSynonyms );
                    
                    this.diffCreateSynList.add( insertsynonymcomponent );
                }
            }
        }
        catch ( DAOException daoex ) {
        	
        	setProcessed( false );
        	daoex.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        }
    }

    
    //method to detect difference in parents between modified components and existing components in DB
    private void createDifferenceParents( ArrayList<OBOComponent> diffParentTermList ) throws Exception {

        Wrapper.printMessage("generatesql.createDifferenceParents", "***", this.requestMsgLevel);
        	
        OBOComponent databasecomponent = new OBOComponent();
        OBOComponent deleteRelCompie = new OBOComponent();
        OBOComponent insertRelCompie = new OBOComponent();        
        
        ArrayList<String> inputParents = new ArrayList<String>();
        ArrayList<String> inputParentTypes = new ArrayList<String>();
        ArrayList<String> dbParents = new ArrayList<String>();
        ArrayList<String> dbParentTypes = new ArrayList<String>();

        ArrayList<String> deleteParents = new ArrayList<String>();
        ArrayList<String> deleteParentTypes = new ArrayList<String>();
        ArrayList<String> insertParents = new ArrayList<String>();
        ArrayList<String> insertParentTypes = new ArrayList<String>();
        
        try {
        	
            //for each component where parents have changed
            for (OBOComponent component: diffParentTermList) {

            	//reset temporary component's parents for each component
                databasecomponent.setChildOfs( new ArrayList<String>() );
                databasecomponent.setChildOfTypes( new ArrayList<String>() );

                ArrayList<JOINNodeRelationship> joinnoderelationships = (ArrayList<JOINNodeRelationship>) this.joinnoderelationshipDAO.listAllByChild(Long.valueOf(component.getDBID()));

              	Iterator<JOINNodeRelationship> iteratorjoinnoderelationship = joinnoderelationships.iterator();

              	while (iteratorjoinnoderelationship.hasNext()) {

              		JOINNodeRelationship joinnoderelationship = iteratorjoinnoderelationship.next();

              		databasecomponent.addChildOf( joinnoderelationship.getPublicId());

                    if ( joinnoderelationship.getTypeFK().equals("part-of")) {
                    
                    	databasecomponent.addChildOfType("PART_OF");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("is-a")) {
                        
                    	databasecomponent.addChildOfType("IS_A");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("group-part-of")) {
                        
                    	databasecomponent.addChildOfType("GROUP_PART_OF");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("DERIVES_FROM")) {
                   	
                    	databasecomponent.addChildOfType("derives-from");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("DEVELOPS_FROM")) {
                       	
                    	databasecomponent.addChildOfType("develops-from");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("LOCATED_IN")) {
                   	
                    	databasecomponent.addChildOfType("located-in");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("DEVELOPS_IN")) {
                   	
                    	databasecomponent.addChildOfType("develops-in");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("DISJOINT_FROM")) {
                   	
                    	databasecomponent.addChildOfType("disjoint-from");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("ATTACHED_TO")) {
                   	
                    	databasecomponent.addChildOfType("attached-to");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("HAS_PART")) {
                   	
                    	databasecomponent.addChildOfType("has-part");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("CONNECTED_TO")) {
                    	
                    	databasecomponent.addChildOfType("connected-to");
                    }
                    else {
                        Wrapper.printMessage("generatesql.createDifferenceParents:UNKNOWN Relationship Type = " + joinnoderelationship.getTypeFK() +"!", "*", this.requestMsgLevel);
                    }
              	}
                
                /*
                * We need to check the parents in the supplied list against the
                *  parents in the current database
                *
                * If the supplied list is greater than the database list then
                *  we need to remove the ones from the list that are already in
                *  the database.
                *
                */
                //compare with component's parents and group parents
                inputParents.clear();
                inputParents.addAll( component.getChildOfs() );
                inputParentTypes.clear();
                inputParentTypes.addAll( component.getChildOfTypes() );
                
                dbParents.clear();
                dbParents.addAll( databasecomponent.getChildOfs() );
                dbParentTypes.clear();
                dbParentTypes.addAll( databasecomponent.getChildOfTypes() );

                Object ip[] = inputParents.toArray();
                Object ipt[] = inputParentTypes.toArray();
                Object dp[] = dbParents.toArray();
                Object dpt[] = dbParentTypes.toArray();

                inputParents.clear();
                inputParentTypes.clear();
                dbParents.clear();
                dbParentTypes.clear();
                
                if ( ip.length >= dp.length ) {
                	
                    // More Input Parents than in Database
                    for (int i = 0; i <= dp.length - 1; i++) {
                    	
                        for (int j = 0; j <= ip.length - 1; j++) {
                        	
                            if ( ip[j].equals(dp[i]) && ipt[j].equals(dpt[i]) ) {
                            	
                                ip[j] = "";
                            }
                        }
                    }
                    
                    for (int k = 0; k <= ip.length - 1; k++) {
                    	
                        if (!ip[k].equals("")) {
                        	
                            inputParents.add( (String) ip[k]);
                            inputParentTypes.add( (String) ipt[k]);
                        }
                    }
                    
                    for (int l = 0; l <= dp.length - 1; l++) {
                    	
                        dbParents.add( (String) dp[l]);
                        dbParentTypes.add( (String) dpt[l]);
                    }
                }
                else {
                    // More Parents in Database than Input
                    for (int i = 0; i <= dp.length - 1 ; i++) {
                    	
                        for (int j = 0; j <= ip.length - 1; j++) {
                        	
                            if (dp[i].equals(ip[j]) && dpt[i].equals(ipt[j])) {
                            	
                                dp[i] = "";
                            }
                        }
                    }
                    
                    for (int k = 0; k <= dp.length - 1; k++) {
                    	
                        if (!dp[k].equals("")) {
                        	
                            dbParents.add( (String) dp[k]);
                            dbParentTypes.add( (String) dpt[k]);
                        }
                    }
                    
                    for (int l = 0; l <= ip.length - 1; l++) {
                    	
                        inputParents.add( (String) ip[l]);
                        inputParentTypes.add( (String) ipt[l]);
                    }
                }

                //get parents to be deleted
                //parents owned by databasecomponent but not by component
                deleteParents.clear();
                deleteParents.addAll( dbParents );
                deleteParentTypes.clear();
                deleteParentTypes.addAll( dbParentTypes );
                
                if ( !deleteParents.isEmpty() ) {
                	
                    deleteRelCompie = new OBOComponent();
                    ArrayList < String > copyDeleteParents = new ArrayList<String>();
                    ArrayList < String > copyDeleteParentTypes = new ArrayList<String>();

                    copyDeleteParents.addAll( deleteParents );
                    copyDeleteParentTypes.addAll( deleteParentTypes );

                    deleteRelCompie.setDBID( component.getDBID() );
                    deleteRelCompie.setID( component.getID() );
                    deleteRelCompie.setName( component.getName() );
                    deleteRelCompie.setChildOfs( copyDeleteParents );
                    deleteRelCompie.setChildOfTypes( copyDeleteParentTypes );
                    
                    this.diffDeleteRelList.add( deleteRelCompie );
                }

                insertParents.clear(); 
                insertParents.addAll( inputParents );
                insertParentTypes.clear();
                insertParentTypes.addAll( inputParentTypes );

                if ( !insertParents.isEmpty() ) {
                	
                    insertRelCompie = new OBOComponent();

                    ArrayList < String > copyInsertParents = new ArrayList<String>();
                    ArrayList < String > copyInsertParentTypes = new ArrayList<String>();

                    copyInsertParents.addAll( insertParents );
                    copyInsertParentTypes.addAll( insertParentTypes );

                    insertRelCompie.setDBID( component.getDBID() );
                    insertRelCompie.setID( component.getID() );
                    insertRelCompie.setName( component.getName() );
                    insertRelCompie.setChildOfs( copyInsertParents );
                    insertRelCompie.setChildOfTypes( copyInsertParentTypes );

                    this.diffCreateRelList.add( insertRelCompie );
                }
            }
        }
        catch ( DAOException daoex ) {
        	
        	setProcessed( false );
        	daoex.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 
    }


    // Helpers ------------------------------------------------------------------------------------

    // method to sort through modified component list for changed stages
    private ArrayList<OBOComponent> getChangedStagesTermList( ArrayList<OBOComponent> changedTermList ) throws Exception {

        Wrapper.printMessage("generatesql.getChangedStagesTermList", "***", this.requestMsgLevel);

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        
        	if ( component.hasDifferenceComment("Different Start Stage") || 
                 component.hasDifferenceComment("Different End Stage") )
            
        		termList.add( component );
        }
        
        return termList;
    }
    

    // method to sort through modified component list for changed names
    private ArrayList<OBOComponent> getChangedNamesTermList( ArrayList<OBOComponent> changedTermList ) throws Exception {

        Wrapper.printMessage("generatesql.getChangedNamesTermList", "***", this.requestMsgLevel);
        
        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("Different Name") ) {
        		
        		termList.add( component );
            }
        }
        
        return termList;
    }
    
    
    // method to sort through modified component list for changed synonyms
    private ArrayList<OBOComponent> getChangedSynonymsTermList( ArrayList<OBOComponent> changedTermList ) throws Exception {

        Wrapper.printMessage("generatesql.getChangedSynonymsTermList", "***", this.requestMsgLevel);

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("Different Synonyms") ) {
        		
        		termList.add( component );
            }
        }
        
        return termList;
    }

    
    // method to sort through modified component list for changed parents
    private ArrayList<OBOComponent> getChangedParentsTermList( ArrayList<OBOComponent> changedTermList ) throws Exception {

        Wrapper.printMessage("generatesql.getChangedParentsTermList", "***", this.requestMsgLevel);

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("Different Parents") ) {
        		
        		termList.add( component );
            }
        	
            if ( component.hasDifferenceComment("Different Group Parents") ) {
            	
            	termList.add( component );
            }
        }
        
        return termList;
    }

    
    // method to sort through modified component list for changed synonyms
    private ArrayList<OBOComponent> getChangedPrimaryStatusTermList( ArrayList<OBOComponent> changedTermList ) throws Exception {

        Wrapper.printMessage("generatesql.getChangedPrimaryStatusTermList", "***", this.requestMsgLevel);

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("Different Primary Status") ) {
        		
        		termList.add( component );
            }
        }
        
        return termList;
    }


    // method to sort through modified component list for changed parents
    private ArrayList<OBOComponent> getNewParentsTermList( ArrayList<OBOComponent> changedTermList ) throws Exception {

        Wrapper.printMessage("generatesql.getNewParentsTermList", "***", this.requestMsgLevel);

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("New Parents") ) {
        		
        		termList.add( component );
            }
        }
        
        return termList;
    }


    // method to sort through modified component list for changed synonyms
    private ArrayList<OBOComponent> getChangedOrderTermList( ArrayList<OBOComponent> changedTermList ) throws Exception {

        Wrapper.printMessage("generatesql.getChangedOrderTermList", "***", this.requestMsgLevel);

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("Different Order") ) {
        		
        		termList.add( component );
            }
        }
        
        return termList;
    }
    
    
    // Getters ------------------------------------------------------------------------------------
    public boolean isProcessed() {
        return processed;
    }
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
        this.processed = processed;
    }
}
