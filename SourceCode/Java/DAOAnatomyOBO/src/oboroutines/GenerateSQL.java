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
* Version:      1
*
* Description:  The MAIN Anatomy Database Update Class
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
*                7.  getChangedStagesTermList
*                8.  getChangedNamesTermList
*                9.  getChangedSynonymsTermList
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
* Mike Wicks; March 2012; Completely rewrite to use a standardised Data Access Object Layer
* Mike Wicks; Nov 2012; Refactor, move All Database access to wrapper objects around Tables
*
*----------------------------------------------------------------------------------------------
*/
package oboroutines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import utility.ObjectConverter;
import utility.Wrapper;
import daolayer.DAOException;
import daolayer.DAOFactory;
import daointerface.SynonymDAO;
import daointerface.JOINNodeRelationshipDAO;
import daointerface.JOINNodeRelationshipNodeDAO;
import daointerface.JOINTimedNodeStageDAO;
import daomodel.Synonym;
import daomodel.JOINNodeRelationship;
import daomodel.JOINNodeRelationshipNode;
import obolayer.OBOException;
import obolayer.OBOFactory;
import obomodel.OBOComponent;
import oboroutines.database.AnaNode;
import oboroutines.database.AnaRelationship;
import oboroutines.database.AnaSynonym;
import oboroutines.database.AnaTimedNode;
import oboroutines.database.AnaVersion;
import oboroutines.database.DatabaseException;

public class GenerateSQL {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    //file properties
    private String strSpecies;
    private String project;
    private boolean boolGenerateIdentifiers;
    
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
    		DAOFactory daofactory, 
    		OBOFactory obofactory, 
            ArrayList<OBOComponent> proposedTermList,
            TreeBuilder treebuilder,
            TreeBuilder refTreebuilder ) throws Exception {

    	setProcessed( true );

    	try {
    		
        	this.daofactory = daofactory;

            Wrapper.printMessage("generatesql.constructor", "***", this.daofactory.getMsgLevel());
            
            this.project = obofactory.getOBOComponentAccess().project();
            this.strSpecies = obofactory.getOBOComponentAccess().species();
            
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

            this.abstractclassobocomponent.setName( obofactory.getOBOComponentAccess().abstractClassName() );
            this.abstractclassobocomponent.setID( obofactory.getOBOComponentAccess().abstractClassId() );
            this.abstractclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().abstractClassNamespace() );

            this.stageclassobocomponent.setName( obofactory.getOBOComponentAccess().stageClassName() );
            this.stageclassobocomponent.setID( obofactory.getOBOComponentAccess().stageClassId() );
            this.stageclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().stageClassNamespace() );

            this.groupclassobocomponent.setName( obofactory.getOBOComponentAccess().groupClassName() );
            this.groupclassobocomponent.setID( obofactory.getOBOComponentAccess().groupClassId() );
            this.groupclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().groupClassNamespace() );

            this.grouptermclassobocomponent.setName( obofactory.getOBOComponentAccess().groupTermClassName() );
            this.grouptermclassobocomponent.setID( obofactory.getOBOComponentAccess().groupTermClassId() );
            this.grouptermclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().groupTermClassNamespace() );
            
            this.boolGenerateIdentifiers = obofactory.getOBOComponentAccess().generateIdentifiers();

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
                if ( component.getStatusChange().equals("INSERT") ) {
                	
                    if ( component.getStatusRule().equals("FAILED") ) {

                    	Wrapper.printMessage("generatesql.constructor:SQL queries for New OBOComponent " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!", "*", this.daofactory.getMsgLevel());
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
                
                // DELETE Terms
                if ( component.getStatusChange().equals("DELETE") ) {
                	
                	if ( component.getStatusRule().equals("FAILED") ) {

                		Wrapper.printMessage("generatesql.constructor:SQL queries for Deleted OBOComponent " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!", "*", this.daofactory.getMsgLevel());
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
                if ( component.getStatusChange().equals("UPDATE") ) {
                	
                    if ( component.getStatusRule().equals("FAILED") ) {
                        
                    	Wrapper.printMessage("generatesql.constructor:SQL queries for Changed OBOComponent " +
                                    component.getID() + " " + component.getName() +
                                    " with rule violation have been generated!", "*", this.daofactory.getMsgLevel());
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
                
                if ( !component.getStatusChange().equals("INSERT") || 
                	!component.getStatusChange().equals("DELETE") || 
                	!component.getStatusChange().equals("UPDATE") ) {
                	
                	if (VALID_VALUES.contains( component.getID() )) {

                		setProcessed( true );
                	}
                }
                
                // Terms that are neither NEW, MODIFIED or DELETE - ERROR!!!
                else {

                    setProcessed( false );
            	}
            }

            // Are we good to go?
            //  Yes:
            if ( isProcessed() ) {
            	
                // Obtain DAOs.
                this.synonymDAO = daofactory.getDAOImpl(SynonymDAO.class);
                this.joinnoderelationshipDAO = daofactory.getDAOImpl(JOINNodeRelationshipDAO.class);
                this.joinnoderelationshipnodeDAO = daofactory.getDAOImpl(JOINNodeRelationshipNodeDAO.class);
                this.jointimednodestageDAO = daofactory.getDAOImpl(JOINTimedNodeStageDAO.class);

                //  set a version record in ANA_VERSION for this update
                if ( !( newComponents.isEmpty() && 
                		deletedComponents.isEmpty() && 
                		changedComponents.isEmpty() ) ) {

                	AnaVersion anaversion = new AnaVersion( this.daofactory);

                	if ( !anaversion.insertANA_VERSION() ) {

                		throw new DatabaseException("anaversion.insertANA_VERSION");
                	}
                	
                    // Do inserts of NEW components
                    inserts( newComponents );

                    // Do updates of MODIFIED components
                    updates( changedComponents );
                    
                    // Do deletes of DELETE components
                    deletes( deletedComponents );

                    // Rebuild ANA_RELATIONSHIP_PROJECT
                    AnaRelationship anarelationship = new AnaRelationship( this.daofactory );

                    if ( !anarelationship.rebuildANA_RELATIONSHIP_PROJECT()) {

                 	   throw new DatabaseException("anarelationship.rebuildANA_RELATIONSHIP_PROJECT");
                    }

                    setProcessed( true );
                }
            }
            // NOT Good to Go
            else {

            	Wrapper.printMessage("generatesql.constructor:No record inserted: Database Update " +
                        "did not occur because SYSTEM failed to detect any " +
                        "changes in the OBO File!", "*", this.daofactory.getMsgLevel());
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

        Wrapper.printMessage("generatesql.inserts", "***", this.daofactory.getMsgLevel());
    		
    	try {
    		
            if (newComponents.size() > 0) {
            	
                // INSERTS into ANA_NODE
                AnaNode ananode = new AnaNode( this.daofactory );
                
                if ( !ananode.insertANA_NODE( newComponents, 
                		this.boolGenerateIdentifiers,
                		"INSERT", 
                		this.strSpecies, 
                		this.tree ) ) {

             	   throw new DatabaseException("ananode.insertANA_NODE");
                }
                
                // INSERTS into ANA_RELATIONSHIP
                AnaRelationship anarelationship = new AnaRelationship( this.daofactory );
                
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
                AnaTimedNode anatimednode = new AnaTimedNode( this.daofactory );
                
                if ( !anatimednode.insertANA_TIMED_NODE( ananode.getUpdatedComponentList(),
                		this.boolGenerateIdentifiers,
                		"INSERT", 
                		this.strSpecies) ) {

                	throw new DatabaseException("anatimednode.insertANA_TIMED_NODE");
                }
                
                // INSERTS into ANA_SYNONYM
                AnaSynonym anasynonym = new AnaSynonym( this.daofactory.getMsgLevel(), this.daofactory );
                
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
    	
        Wrapper.printMessage("generatesql.deletes", "***", this.daofactory.getMsgLevel());
    		
    	try {
    		
            if ( deletedComponentsIn.size() > 0 ) {

            	// delete components, set DBIDs and get only components that have dbids based on emap id
                AnaNode ananode = new AnaNode( this.daofactory );

                if ( !ananode.setDatabaseOIDs(deletedComponentsIn, 
                		"INSERT") ){

                	throw new DatabaseException("ananode.setDatabaseOIDs");
                }
                
                //pass valid terms to validDeleteComponents

                //perform deletion on valid deletion term list
                deleteComponentFromAllTables( validateDeleteTermList( ananode.getUpdatedComponentList() ) );
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
    	
        Wrapper.printMessage("generatesql.updates", "***", this.daofactory.getMsgLevel());
    		
    	try {
    		
            if (changedComponentsIn.size() > 0) {
                
                //System.out.println("changedComponentsIn.size() = " + changedComponentsIn.size());

                //modify components, set DBIDs and get only components that have dbids based on emap id
                AnaNode ananode = new AnaNode( this.daofactory );

                if ( !ananode.setDatabaseOIDs(changedComponentsIn, 
                		"INSERT") ){
                		
                	throw new DatabaseException("ananode.setDatabaseOIDs");
                }

                //System.out.println("ananode.getStartingComponentList.size() = " + ananode.getStartingComponentList().size());
                
                //get components whose stage ranges have changed
                //find ranges of stages that need to be inserted/deleted, create
                // temporary components for ranges and perform insertion and deletion for modified stage ranges
                AnaTimedNode anatimednode = new AnaTimedNode( this.daofactory );

                if ( !anatimednode.updateANA_TIMED_NODE( createDifferenceTimedComponents( getChangedStagesTermList( ananode.getStartingComponentList() ) ),
                		this.boolGenerateIdentifiers,
                		"INSERT", 
                		this.strSpecies) ) {

                	throw new DatabaseException("anatimednode.updateANA_TIMED_NODE");
                }
                
                //get components whose names have changed
                //perform update for modified names
                if ( !ananode.updateANA_NODE_name( getChangedNamesTermList( ananode.getStartingComponentList() ), 
                		"UPDATE", this.strSpecies) ) {

             	   throw new DatabaseException("ananode.updateANA_NODE_name");
                }
                
                //get components whose synonyms have changed
                createDifferenceSynonyms( getChangedSynonymsTermList( ananode.getStartingComponentList() ) );
                

                if ( !(this.diffCreateSynList.isEmpty() &&  this.diffDeleteSynList.isEmpty()) ) {
                	
                    //find ranges of stages that need to be inserted/deleted, create
                    // temporary components for ranges
                    //perform insertion and deletion for modified synonyms
                    AnaSynonym anasynonym = new AnaSynonym( this.daofactory.getMsgLevel(), this.daofactory );

                    if ( !anasynonym.updateANA_SYNONYM( this.diffCreateSynList, 
                    		this.diffDeleteSynList, 
                    		"DELETE") ) {

                 	   throw new DatabaseException("anasynonym.updateANA_SYNONYM");
                    }
                    
                }

                //get components whose parents have changed
                createDifferenceParents( getChangedParentsTermList( ananode.getStartingComponentList() ) );
                		
                //find ranges of stages that need to be inserted/deleted, create temporary components for ranges
                //perform insertion and deletion for modified parent relationships
                AnaRelationship anarelationship = new AnaRelationship( this.daofactory );

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
                		"UPDATE", this.strSpecies) ) {

                	throw new DatabaseException("ananode.updateANA_NODE_primary");
                }
                
                //get components whose ordering have changed and perform reordering
                //updateOrder( getChangedOrderTermList( ananode.getStartingComponentList() ) );

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

        Wrapper.printMessage("generatesql.validateDeleteTermList", "***", this.daofactory.getMsgLevel());
        	
        ArrayList<OBOComponent> dbTermList = new ArrayList<OBOComponent>();
        Vector<String> dependentDescendants = new Vector<String>();
        
        boolean invalidDelete = false;
        
        try {
        	
            //for each term in deletedTermList
            for (OBOComponent deleteComponent: deletedTermList) {
            	
                //get all dependent descendants
                dependentDescendants = recursiveGetDependentDescendants(deleteComponent.getID(), dependentDescendants, invalidDelete);
            
                if ( invalidDelete ) {
                	
                    //disallow deletion
                    deleteComponent.setCheckComment("Delete Record Warning: " + 
                        deleteComponent.getID() + " still has descendants in " +
                        "the database. Deletion not allowed.");
                
                    deleteComponent.setCheckComment("Delete Record Warning: " +
                        "Deletion of this term could potentially result in " +
                        "undesirable orphan terms or other ontology " +
                        "violations. Please generate a new OBO file from the " +
                        "database and retry deletion");
                }
                else {
                    
                	dbTermList.add(deleteComponent);
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

        Wrapper.printMessage("generatesql.deleteComponentFromAllTables", "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            if ( !validDeleteTermList.isEmpty() ) {

                AnaTimedNode anatimednode = new AnaTimedNode( this.daofactory );

                if ( !anatimednode.deleteANA_TIMED_NODE(validDeleteTermList, "DELETE") ) {

                    throw new DatabaseException("anatimednode.deleteANA_TIMED_NODE for DELETE");
                }

                AnaNode ananode = new AnaNode( this.daofactory );
                
                if ( !ananode.deleteANA_NODE(validDeleteTermList, this.strSpecies, "DELETE") ) {

                	throw new DatabaseException("ananode.deleteANA_NODE for DELETE");
                }

                AnaSynonym anasynonym = new AnaSynonym( this.daofactory.getMsgLevel(), this.daofactory );
                
                if ( !anasynonym.deleteANA_SYNONYM(validDeleteTermList, "DELETE") ) {
             	
                	throw new DatabaseException("anasynonym.deleteANA_SYNONYM for DELETE");
                }
              
                AnaRelationship anarelationship = new AnaRelationship( this.daofactory );
                
                if ( !anarelationship.deleteANA_RELATIONSHIP(validDeleteTermList, "DELETE") ) {
             	
                	throw new DatabaseException("anarelationship.deleteANA_RELATIONSHIP for DELETE");
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

        Wrapper.printMessage("generatesql.recursiveGetDependentDescendants", "***", this.daofactory.getMsgLevel());
        	
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
                    else if ( !component.getStatusChange().equals("DELETE") ) {

                    	component.setStatusChange("DELETE");
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

        Wrapper.printMessage("generatesql.createDifferenceTimedComponents", "***", this.daofactory.getMsgLevel());
        	
        ArrayList<OBOComponent> diffCreateTimedCompList = new ArrayList<OBOComponent>();
        
        try {
        	
            for (OBOComponent component: diffStageTermList) {

                long longStartSequence = this.jointimednodestageDAO.minSequenceByNodeFk( ObjectConverter.convert(component.getDBID(), Long.class));
                
                long longEndSequence = this.jointimednodestageDAO.maxSequenceByNodeFk( ObjectConverter.convert(component.getDBID(), Long.class) );

                //System.out.println("longStartSequence                = " + longStartSequence);
                //System.out.println("longEndSequence                  = " + longEndSequence);
                //System.out.println("component.getStartSequence() = " + component.getStartSequence());
                //System.out.println("component.getEndSequence()   = " + component.getEndSequence());
                
                //compare stage ranges between component and databasecomponent
                // for creating new timed components
                if ( longStartSequence > component.getStartSequence() ) {
                   
                    //System.out.println("HERE AAAAA");

                    OBOComponent createtimedcomponent = new OBOComponent();
                   
                	createtimedcomponent.setID( component.getID() );
                	createtimedcomponent.setName( component.getName() );
                	createtimedcomponent.setDBID( component.getDBID() );
                	createtimedcomponent.setStart( component.getStart() );
                	createtimedcomponent.setEndSequence( longStartSequence - 1, this.strSpecies );
                	createtimedcomponent.setStatusChange("INSERT");
                	createtimedcomponent.setStatusRule("PASSED");
                	
                    Set<String> copyComments = component.getCheckComments();       
                    
                    for (String s: copyComments){
                    	
                        //System.out.println("component comment = " + s);

                    	createtimedcomponent.setCheckComment(s);
                    }

                	diffCreateTimedCompList.add( createtimedcomponent );
                }
                
                if ( longEndSequence < component.getEndSequence() ) {
                   
                    //System.out.println("HERE BBBBB");

                    OBOComponent createtimedcomponent = new OBOComponent();
                   
                	createtimedcomponent.setID( component.getID() );
                	createtimedcomponent.setName( component.getName() );                   
                	createtimedcomponent.setDBID( component.getDBID() );
                	createtimedcomponent.setStartSequence( longEndSequence + 1, this.strSpecies );
                	createtimedcomponent.setEndSequence( component.getEndSequence(), this.strSpecies );
                	createtimedcomponent.setStatusChange("INSERT");
                	createtimedcomponent.setStatusRule("PASSED");
                   
                    Set<String> copyComments = component.getCheckComments();       
                    
                    for (String s: copyComments){
                    	
                        //System.out.println("component comment = " + s);

                    	createtimedcomponent.setCheckComment(s);
                    }

                	diffCreateTimedCompList.add( createtimedcomponent );
                }
                
                //for deleting existing timed components
                if ( longStartSequence < component.getStartSequence() ) {
                   
                    //System.out.println("HERE CCCCC");

                    OBOComponent delTimedComponent = new OBOComponent();
                   
                	delTimedComponent.setID( component.getID() );
                	delTimedComponent.setName( component.getName() );
                	delTimedComponent.setDBID( component.getDBID() );
                	delTimedComponent.setStartSequence( longStartSequence, this.strSpecies );
                	delTimedComponent.setEndSequence( component.getStartSequence() - 1, this.strSpecies );
                	delTimedComponent.setStatusChange("DELETE");
                	delTimedComponent.setStatusRule("PASSED");

                    Set<String> copyComments = component.getCheckComments();       
                    
                    for (String s: copyComments){
                    	
                        //System.out.println("component comment = " + s);

                    	delTimedComponent.setCheckComment(s);
                    }

                	diffCreateTimedCompList.add( delTimedComponent );
                }
                
                if ( longEndSequence > component.getEndSequence() ) {
                    
                    //System.out.println("HERE DDDDD");

                    OBOComponent delTimedComponent = new OBOComponent();
                    
                	delTimedComponent.setID( component.getID() );
                    delTimedComponent.setName( component.getName() );
                    delTimedComponent.setDBID( component.getDBID() );
                    delTimedComponent.setStartSequence( component.getEndSequence() + 1, this.strSpecies );
                    delTimedComponent.setEndSequence( longEndSequence, this.strSpecies );
                    delTimedComponent.setStatusChange("DELETE");
                    delTimedComponent.setStatusRule("PASSED");

                    Set<String> copyComments = component.getCheckComments();       
                    
                    for (String s: copyComments){
                    	
                        //System.out.println("component comment = " + s);

                    	delTimedComponent.setCheckComment(s);
                    }

                    diffCreateTimedCompList.add( delTimedComponent );
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

        Wrapper.printMessage("generatesql.createDifferenceSynonyms", "***", this.daofactory.getMsgLevel());
        	
        OBOComponent databasecomponent = new OBOComponent();
        OBOComponent deletesynonymcomponent = new OBOComponent();
        OBOComponent insertsynonymcomponent = new OBOComponent();        
        
        ArrayList<String> synonyms = new ArrayList<String>();
        ArrayList<String> deleteSynonyms = new ArrayList<String>();
        ArrayList<String> insertSynonyms = new ArrayList<String>();
        
        try {
        	
        	//System.out.println("diffSynonymTermList.size() = " + diffSynonymTermList.size());
        	//System.out.println("this.diffDeleteSynList.size() = " + this.diffDeleteSynList.size()); 
        	//System.out.println("this.diffCreateSynList.size() = " + this.diffCreateSynList.size()); 
        	
            //for each component where parents have changed
            for (OBOComponent component: diffSynonymTermList) {

            	ArrayList<Synonym> synonymlist = (ArrayList<Synonym>) this.synonymDAO.listByObjectFK( ObjectConverter.convert(component.getDBID(), Long.class));
            	
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

        Wrapper.printMessage("generatesql.createDifferenceParents", "***", this.daofactory.getMsgLevel());
        	
        OBOComponent databasecomponent = new OBOComponent();
        OBOComponent deleteRelComponent = new OBOComponent();
        OBOComponent insertRelComponent = new OBOComponent();        
        
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

                ArrayList<JOINNodeRelationship> joinnoderelationships = (ArrayList<JOINNodeRelationship>) this.joinnoderelationshipDAO.listAllByChild( ObjectConverter.convert(component.getDBID(), Long.class));

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
                        Wrapper.printMessage("generatesql.createDifferenceParents:UNKNOWN Relationship Type = " + joinnoderelationship.getTypeFK() +"!", "*", this.daofactory.getMsgLevel());
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
                	
                    deleteRelComponent = new OBOComponent();
                    ArrayList < String > copyDeleteParents = new ArrayList<String>();
                    ArrayList < String > copyDeleteParentTypes = new ArrayList<String>();

                    copyDeleteParents.addAll( deleteParents );
                    copyDeleteParentTypes.addAll( deleteParentTypes );

                    deleteRelComponent.setDBID( component.getDBID() );
                    deleteRelComponent.setID( component.getID() );
                    deleteRelComponent.setName( component.getName() );
                    deleteRelComponent.setChildOfs( copyDeleteParents );
                    deleteRelComponent.setChildOfTypes( copyDeleteParentTypes );
                    
                    this.diffDeleteRelList.add( deleteRelComponent );
                }

                insertParents.clear(); 
                insertParents.addAll( inputParents );
                insertParentTypes.clear();
                insertParentTypes.addAll( inputParentTypes );

                if ( !insertParents.isEmpty() ) {
                	
                    insertRelComponent = new OBOComponent();

                    ArrayList < String > copyInsertParents = new ArrayList<String>();
                    ArrayList < String > copyInsertParentTypes = new ArrayList<String>();

                    copyInsertParents.addAll( insertParents );
                    copyInsertParentTypes.addAll( insertParentTypes );

                    insertRelComponent.setDBID( component.getDBID() );
                    insertRelComponent.setID( component.getID() );
                    insertRelComponent.setName( component.getName() );
                    insertRelComponent.setChildOfs( copyInsertParents );
                    insertRelComponent.setChildOfTypes( copyInsertParentTypes );

                    this.diffCreateRelList.add( insertRelComponent );
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

        Wrapper.printMessage("generatesql.getChangedStagesTermList", "***", this.daofactory.getMsgLevel());

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

        Wrapper.printMessage("generatesql.getChangedNamesTermList", "***", this.daofactory.getMsgLevel());
        
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

        Wrapper.printMessage("generatesql.getChangedSynonymsTermList", "***", this.daofactory.getMsgLevel());

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	//System.out.println("component.toString(): " + component.toString());
        	//System.out.println("component.getCheckComments(): " + component.getCheckComments());
        	
        	if ( component.hasDifferenceComment("Different Synonyms") ) {

            	//System.out.println("Different Synonym!!!");

        		termList.add( component );
            }
        }
        
        return termList;
    }

    
    // method to sort through modified component list for changed parents
    private ArrayList<OBOComponent> getChangedParentsTermList( ArrayList<OBOComponent> changedTermList ) throws Exception {

        Wrapper.printMessage("generatesql.getChangedParentsTermList", "***", this.daofactory.getMsgLevel());

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

        Wrapper.printMessage("generatesql.getChangedPrimaryStatusTermList", "***", this.daofactory.getMsgLevel());

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

        Wrapper.printMessage("generatesql.getNewParentsTermList", "***", this.daofactory.getMsgLevel());

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("New Parents") ) {
        		
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
