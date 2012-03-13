/*
*----------------------------------------------------------------------------------------------
* Project:      Anatomy
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
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; March 2012; Completely rewrite to use a standardised DAO Layer
*
*----------------------------------------------------------------------------------------------
*/

package Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import DAOLayer.DAOException;
import DAOLayer.DAOFactory;

import DAOLayer.DerivedPartOfDAO;
import DAOLayer.JOINNodeRelationshipDAO;
import DAOLayer.JOINNodeRelationshipNodeDAO;
import DAOLayer.JOINNodeRelationshipRelationshipProjectDAO;
import DAOLayer.JOINRelationshipProjectRelationshipDAO;
import DAOLayer.JOINTimedNodeNodeStageDAO;
import DAOLayer.JOINTimedNodeStageDAO;
import DAOLayer.LogDAO;
import DAOLayer.NodeDAO;
import DAOLayer.ProjectDAO;
import DAOLayer.RelationshipDAO;
import DAOLayer.RelationshipProjectDAO;
import DAOLayer.StageDAO;
import DAOLayer.StageRangeDAO;
import DAOLayer.SynonymDAO;
import DAOLayer.ThingDAO;
import DAOLayer.TimedNodeDAO;
import DAOLayer.VersionDAO;

import DAOModel.JOINNodeRelationship;
import DAOModel.JOINNodeRelationshipNode;
import DAOModel.JOINNodeRelationshipRelationshipProject;
import DAOModel.JOINRelationshipProjectRelationship;
import DAOModel.JOINTimedNodeStage;
import DAOModel.Log;
import DAOModel.Node;
import DAOModel.Relationship;
import DAOModel.RelationshipProject;
import DAOModel.Stage;
import DAOModel.Synonym;
import DAOModel.Thing;
import DAOModel.TimedNode;
import DAOModel.Version;

import OBOModel.ComponentFile;


public class GenerateSQL {
	
    // Properties ---------------------------------------------------------------------------------
    
	// A flag to print comments to System.out
	boolean debug; 
	
    //term list to be updated and term list to refer to
    private ArrayList < ComponentFile > proposedTermList; 
    
    //file properties
    private String strSpecies = "";
    private String project = "";
    
    //treebuilder object to use hashmaps to get component (getComponent)
    private TreeBuilder tree; 
    
    //term list for timed components
    private ArrayList < ComponentFile > timedCompList;

    //term list for synonym components
    private ArrayList < ComponentFile > synonymCompList;
    
    //term list for disallowed deleted components 
    // 1 - not found in db
    // 2 - is primary and have undeleted children
    private ArrayList < ComponentFile > unDeletedCompList;
    
    //term list for disallowed modified components
    // 1 - not found in db
    private ArrayList < ComponentFile > unModifiedCompList;
    
    //term list for temporary terms created for larger stage ranges from
    // modified components
    private ArrayList < ComponentFile > diffCreateTimedCompList;
    
    //term list for timed components to be deleted for smaller stage ranges
    // from modified components
    private ArrayList < ComponentFile > diffDeleteTimedCompList;
    
    //term list for relationships to be created for changed parents from
    // modifed components
    private ArrayList < ComponentFile > diffCreateRelList;
    
    //term list for relationships to be created for deleted parents from
    // modified components
    private ArrayList < ComponentFile > diffDeleteRelList;
    
    //term list for synonyms to be created for changed synonyms from modifed
    // components
    private ArrayList < ComponentFile > diffCreateSynList;
    
    //term list for synonyms to be created for deleted synonyms from modified
    // components
    private ArrayList < ComponentFile > diffDeleteSynList;
    
    //maximum public id
    private int intCurrentVersionID;
    private int intCurrentPublicID;
    private int intCurrentObjectID;
    
    //check whether was processed all the way
    private boolean isProcessed = false;
    
    //abstract class configuration
    private ComponentFile abstractClass;
    
    //Data Access Object Factory
    private DAOFactory daofactory;
    
    //Data Access Objects (DAOs)
    private DerivedPartOfDAO derivedpartofDAO;
    private LogDAO logDAO;
    private NodeDAO nodeDAO; 
    private RelationshipDAO relationshipDAO;
    private RelationshipProjectDAO relationshipprojectDAO;
    private StageDAO stageDAO;
    private SynonymDAO synonymDAO; 
    private ThingDAO thingDAO;
    private TimedNodeDAO timednodeDAO; 
    private VersionDAO versionDAO;
    private ProjectDAO projectDAO;
    private StageRangeDAO stagerangeDAO; 
    private JOINNodeRelationshipDAO joinnoderelationshipDAO;
    private JOINNodeRelationshipNodeDAO joinnoderelationshipnodeDAO; 
    private JOINNodeRelationshipRelationshipProjectDAO joinnoderelationshiprelationshipprojectDAO;
    private JOINRelationshipProjectRelationshipDAO joinrelationshipprojectrelationshipDAO;
    private JOINTimedNodeNodeStageDAO jointimednodenodestageDAO;
    private JOINTimedNodeStageDAO jointimednodestageDAO;
    
    
    
    // Constructors -------------------------------------------------------------------------------
    public GenerateSQL(
    		boolean debug,
            ArrayList<ComponentFile> proposedTermList,
            TreeBuilder treebuilder,
            TreeBuilder refTreebuilder,
            String species,
            String fileName,
            ComponentFile abstractClass,
            String project  ) {


        this.debug = debug;
        
        if (this.debug) {
            System.out.println("GenerateSQL - Constructor");
        }
        
        this.proposedTermList = proposedTermList;
        
        this.timedCompList = new ArrayList<ComponentFile>();
        this.synonymCompList = new ArrayList<ComponentFile>();
        this.unDeletedCompList = new ArrayList<ComponentFile>();
        this.unModifiedCompList = new ArrayList<ComponentFile>();
        this.diffCreateTimedCompList = new ArrayList<ComponentFile>();
        this.diffDeleteTimedCompList = new ArrayList<ComponentFile>();
        this.diffCreateRelList = new ArrayList<ComponentFile>();
        this.diffDeleteRelList = new ArrayList<ComponentFile>();
        this.diffCreateSynList = new ArrayList<ComponentFile>();
        this.diffDeleteSynList = new ArrayList<ComponentFile>();

        this.tree = treebuilder;
        this.strSpecies = species;
        this.intCurrentPublicID = 0;
        this.intCurrentObjectID = 0;
        this.abstractClass = abstractClass;
        this.project = project;

        //internal termlists for data manipulation
        ArrayList<ComponentFile> newComponentFiles =
                new ArrayList < ComponentFile >();

        ArrayList<ComponentFile> deletedComponentFiles =
                new ArrayList < ComponentFile >();
        
        ArrayList<ComponentFile> validDeletedComponentFiles =
                new ArrayList < ComponentFile >();
        
        ArrayList<ComponentFile> changedComponentFiles =
                new ArrayList < ComponentFile >();
        
        ArrayList<ComponentFile> changedPropComponentFiles =
                new ArrayList < ComponentFile >();
        
        
        //construct internal arraylists
        ComponentFile component = new ComponentFile();
        for (int i = 0; i<this.proposedTermList.size(); i++){
            component = this.proposedTermList.get(i);
        
            if ( component.getStatusChange().equals("NEW") ){
                if ( component.getStatusRule().equals("FAILED") ) {
                    if (this.debug) {
                        System.out.println(
                                "--SQL queries for New ComponentFile " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!" );
                    }
                }
                newComponentFiles.add( component );
            }
            if ( component.getStatusChange().equals("DELETED") ){
                if ( component.getStatusRule().equals("FAILED") ){
                    if (this.debug) {
                        System.out.println( 
                                "--SQL queries for Deleted ComponentFile " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!");
                    }
                }
                deletedComponentFiles.add( component );
            }
            if ( component.getStatusChange().equals("CHANGED") ){
                if ( component.getStatusRule().equals("FAILED") ){
                    if (this.debug) {
                        System.out.println( 
                                "--SQL queries for Changed ComponentFile " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!");
                    }
                }
                changedComponentFiles.add( component );
            }
        }
        
        
        // Obtain DAOFactory.
        this.daofactory = DAOFactory.getInstance("daofactory");
        //System.out.println("DAOFactory successfully obtained: " + daofactory);

        // Obtain DAOs.
        this.derivedpartofDAO = daofactory.getDerivedPartOfDAO();
        this.logDAO = daofactory.getLogDAO();
        this.nodeDAO = daofactory.getNodeDAO();
        this.relationshipDAO = daofactory.getRelationshipDAO();
        this.relationshipprojectDAO = daofactory.getRelationshipProjectDAO();
        this.stageDAO = daofactory.getStageDAO();
        this.synonymDAO = daofactory.getSynonymDAO();
        this.thingDAO = daofactory.getThingDAO();
        this.timednodeDAO = daofactory.getTimedNodeDAO();
        this.versionDAO = daofactory.getVersionDAO();
        this.projectDAO = daofactory.getProjectDAO();
        this.stagerangeDAO = daofactory.getStageRangeDAO();
        this.joinnoderelationshipDAO = daofactory.getJOINNodeRelationshipDAO();
        this.joinnoderelationshipnodeDAO = daofactory.getJOINNodeRelationshipNodeDAO();
        this.joinnoderelationshiprelationshipprojectDAO = daofactory.getJOINNodeRelationshipRelationshipProjectDAO();
        this.joinrelationshipprojectrelationshipDAO = daofactory.getJOINRelationshipProjectRelationshipDAO();
        this.jointimednodenodestageDAO = daofactory.getJOINTimedNodeNodeStageDAO();
        this.jointimednodestageDAO = daofactory.getJOINTimedNodeStageDAO();


        // 01
        //set version id
        initialiseVersionID();
        
        // 02
        //set a version record in ANA_VERSION for this update
        insertANA_VERSION( this.intCurrentVersionID, newComponentFiles,
                deletedComponentFiles, changedComponentFiles );

        //new components
        //insertANA_OBJECT(newComponentFiles, "ANA_OBJECT");
        // 03
        insertANA_NODE(newComponentFiles);
        
        // 04
        insertANA_RELATIONSHIP(newComponentFiles, "NEW");
        
        // 05
        insertANA_TIMED_NODE(newComponentFiles, "NEW");
        
        // 06
        insertANA_SYNONYM(newComponentFiles, "NEW");

        /*
        delete components
         delete components, set DBIDs and get only components that have
         dbids based on emap id
        deletedComponentFiles = setDBIDs(deletedComponentFiles);
        CRITICAL DELETION VALIDATION: to disallow deletion of components that do have children in database
        1. check that term exists in database
        2. if term = primary, check that all descendants are due for deletion in obo file as well
        3. if one descendant specified in database is not found in OBO file 
           OR descendant is found but not specified for deletion, 
        4. pass on invalid term to unDeletedCompList
        pass valid terms to validDeleteComponentFiles
        validDeletedComponentFiles = this.validateDeleteTermList( deletedComponentFiles );
        insert log records for deleted components
        insertANA_LOG( validDeletedComponentFiles );
        perform deletion on valid deletion term list
        deleteComponentFileFromTables( validDeletedComponentFiles );
        reorder siblings of deleted components that have order
        reorderANA_RELATIONSHIP( validDeletedComponentFiles, this.project );
        report for invalid delete term list that have not been deleted
        reportDeletionSummary(deletedComponentFiles, validDeletedComponentFiles, this.unDeletedCompList);
        */
        
        
        //modify components, set DBIDs and get only components that have dbids based on emap id
        // 07
        changedComponentFiles = setDBIDs(changedComponentFiles);
        
        //get components whose stage ranges have changed
        // 08
        changedPropComponentFiles = this.getChangedStagesTermList( changedComponentFiles );
        
        //perform insertion and deletion for modified stage ranges
        // 09
        updateStages( changedPropComponentFiles );
        
        //get components whose names have changed
        // 10
        changedPropComponentFiles = this.getChangedNamesTermList( changedComponentFiles );
        
        //perform update for modified names
        // 11
        updateANA_NODE( changedPropComponentFiles );
        
        //get components whose synonyms have changed
        // 12
        changedPropComponentFiles = this.getChangedSynonymsTermList( changedComponentFiles );
        
        //perform insertion and deletion for modified synonyms
        // 13
        updateSynonyms( changedPropComponentFiles );
        
        //get components whose parents have changed
        // 14
        changedPropComponentFiles = this.getChangedParentsTermList( changedComponentFiles );
        
        //perform insertion and deletion for modified parent relationships
        // 15
        updateParents( changedPropComponentFiles );
        
        //get components whose primary status have changed
        changedPropComponentFiles = this.getChangedPrimaryStatusTermList( changedComponentFiles );
        //perform update for modified primary status
        // 15.5
        updateANA_NODE_primary( changedPropComponentFiles );
        
        //get components whose ordering have changed
        // 16
        changedPropComponentFiles = this.getChangedOrderTermList( changedComponentFiles );

        //perform reordering
        // 17
        updateOrder( changedPropComponentFiles );
        
        //delete components
        // delete components, set DBIDs and get only components that have dbids based on emap id
        // 07
        deletedComponentFiles = setDBIDs(deletedComponentFiles);

        //CRITICAL DELETION VALIDATION: to disallow deletion of components that do have children in database
        //1. check that term exists in database
        //2. if term = primary, check that all descendants are due for deletion in obo file as well
        //3. if one descendant specified in database is not found in OBO file
        //   OR descendant is found but not specified for deletion,
        //4. pass on invalid term to unDeletedCompList
        
        //pass valid terms to validDeleteComponentFiles
        // 18
        validDeletedComponentFiles = this.validateDeleteTermList( deletedComponentFiles );
        
        //insert log records for deleted components
        // 19
        insertANA_LOG( validDeletedComponentFiles );
        
        //perform deletion on valid deletion term list
        // 20
        deleteComponentFileFromTables( validDeletedComponentFiles );
        
        //reorder siblings of deleted components that have order
        // 21
        reorderANA_RELATIONSHIP( validDeletedComponentFiles, this.project );
        
        setIsProcessed( true );

    }
    
    
    // Getters ------------------------------------------------------------------------------------
    public boolean getIsProcessed(){
        return this.isProcessed;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setIsProcessed( boolean isProcessed ) {
        this.isProcessed = isProcessed;
    }


    // Methods ------------------------------------------------------------------------------------
    // 01
    private void initialiseVersionID(){

        if (this.debug) {
            System.out.println("01 - initialiseVersionID");
        }

        //first obj_oid in ana_object for all updates
        //obj_oid > version_fk = all records related to this update
        this.intCurrentVersionID = this.getMaxObjectID();
        this.intCurrentVersionID++;
        
    }

    // 02
    //  Insert a new row into ANA_VERSION
    private void insertANA_VERSION( int objectID, 
    		ArrayList<ComponentFile> newTermList, 
    		ArrayList<ComponentFile> delTermList, 
    		ArrayList<ComponentFile> modTermList ){
    	/*
         *  Columns:
         *   1. VER_OID      - int(10) unsigned
         *   2. VER_NUMBER   - int(10) unsigned
         *   3. VER_DATE     - datetime
         *   4. VER_COMMENTS - varchar(2000)
    	 */
        if (this.debug) {
            System.out.println("02 - insertANA_VERSION");
        }

        int intVersionEntries = 0;

        try{
           if ( !( newTermList.isEmpty() && delTermList.isEmpty() && modTermList.isEmpty() ) ){
               //insert into ANA_OBJECT
               ComponentFile vercomponent = new ComponentFile();
               ArrayList< ComponentFile > verTermList = new ArrayList<ComponentFile>();
               verTermList.add( vercomponent );
               
               insertANA_OBJECT( verTermList, "ANA_VERSION" );
               
               //find out which round of update this is to the db
        	   intVersionEntries = this.versionDAO.countAll();
        	   
               //prepare values for insertion
               int intVER_OID = this.intCurrentVersionID;
               int intVER_NUMBER = ++intVersionEntries;
               String strVER_DATE = "NOW()";
               String strVER_COMMENTS = "DB2OBO Update: Editing the ontology";

               Version version = new Version((long) intVER_OID, (long) intVER_NUMBER, strVER_DATE, strVER_COMMENTS);

               this.versionDAO.save(version);

           }
        } 
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    } 

    // 03
    //  Insert new rows into ANA_NODE
    private void insertANA_NODE( ArrayList<ComponentFile> newTermList ){
    	/*
         *  Columns:
         *   1. ANO_OID            - int(10) unsigned 
         *   2. ANO_SPECIES_FK     - varchar(20)      
         *   3. ANO_COMPONENT_NAME - varchar(255)     
         *   4. ANO_IS_PRIMARY     - tinyint(1)       
         *   5. ANO_IS_GROUP       - tinyint(1)       
         *   6. ANO_PUBLIC_ID      - varchar(20)      
         *   7. ANO_DESCRIPTION    - varchar(2000)    
    	 */
        if (this.debug) {
            System.out.println("03 - insertANA_NODE");
        }
        
        int intANO_OID = 0;
        String strANO_SPECIES_FK = this.strSpecies;
        String strANO_COMPONENT_NAME = "";
        boolean boolANO_IS_PRIMARY = true;
        boolean boolANO_IS_GROUP = false;
        String strANO_PUBLIC_ID = "";
        String strANO_DESCRIPTION = "";

        ComponentFile component;

        try {
        	//get current max public id
        	// 03-1
        	this.intCurrentPublicID = this.getMaxPublicID();

        	if ( !newTermList.isEmpty() ){

        		//insert into ANA_OBJECT first
                insertANA_OBJECT(newTermList, "ANA_NODE");
                
                for (int i = 0; i< newTermList.size(); i++){

                   component = newTermList.get(i);

                   //prepare values
                   // Column 1
                   intANO_OID = Integer.parseInt(component.getDBID());
                   // Column 2
                   strANO_SPECIES_FK = strSpecies;
                   // Column 3
                   strANO_COMPONENT_NAME = component.getName();
                   // Column 4
                   if (component.getIsPrimary() ) {
                	   boolANO_IS_PRIMARY = true;
                   }
                   else {
                	   boolANO_IS_PRIMARY = false;
                   }
                   // Column 5
                   if (component.getIsPrimary() ) {
                	   boolANO_IS_GROUP = false;
                   }
                   else {
                	   boolANO_IS_GROUP = true;
                   }
                   // Column 6
                   if (this.strSpecies.equals("mouse")) {
                	   strANO_PUBLIC_ID = "EMAPA:" + 
                               Integer.toString( ++intCurrentPublicID );
                   }
                   if (this.strSpecies.equals("chick")) {
                	   strANO_PUBLIC_ID = "ECAPA:" + 
                               Integer.toString( ++intCurrentPublicID );
                   }
                   if (this.strSpecies.equals("human")) {
                	   strANO_PUBLIC_ID = "EDHAA:" + 
                               Integer.toString( ++intCurrentPublicID );
                   }
                   // Column 7
                   strANO_DESCRIPTION = "";
                   
                   Node node = new Node((long) intANO_OID, strANO_SPECIES_FK, strANO_COMPONENT_NAME, boolANO_IS_PRIMARY, boolANO_IS_GROUP, strANO_PUBLIC_ID, strANO_DESCRIPTION);
                   
                   this.nodeDAO.save(node);
                   
                   //Update Components in Memory
                   // assign generated new EMAPA id to new components replacing temp id
                   component.setNewID(strANO_PUBLIC_ID);
                   // comment new component with generated EMAPA id
                   if (this.strSpecies.equals("mouse")) {
                	   this.tree.getComponent( component.getID()).setCheckComment(
                			   "New EMAPA:ID generated: " + strANO_PUBLIC_ID);
                   }
                   if (this.strSpecies.equals("chick")) {
                	   this.tree.getComponent( component.getID()).setCheckComment(
                			   "New ECAPA:ID generated: " + strANO_PUBLIC_ID);
                   }
                   if (this.strSpecies.equals("human")) {
                	   this.tree.getComponent( component.getID()).setCheckComment(
                			   "New EHDAA:ID generated: " + strANO_PUBLIC_ID);
                   }
                   // update new components with ano_oid
                   component.setDBID(Integer.toString(intANO_OID));
                   // update new component with generated emapa id
                   component.setID(strANO_PUBLIC_ID);

               }
           }
            
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
    
    // 04
    //  Insert into ANA_RELATIONSHIP
    private void insertANA_RELATIONSHIP( ArrayList < ComponentFile > newTermList,
            String calledFrom){
    	/*
    	 *  ANA_RELATIONSHIP
    	 *  Columns:
         *   1. REL_OID                  - int(10) unsigned 
         *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
         *   3. REL_CHILD_FK             - int(10) unsigned 
         *   4. REL_PARENT_FK            - int(10) unsigned 

    	 *  ANA_RELATIONSHIP_PROJECT 
         *  Columns:
         *   1. RLP_OID             - int(10) unsigned 
         *   2. RLP_RELATIONSHIP_FK - int(10) unsigned      
         *   3. RLP_PROJECT_FK      - char(30)
         *   4. RLP_SEQUENCE        - int(10) unsigned 
    	 */
        if (this.debug) {
            System.out.println("04 insertANA_RELATIONSHIP - called from = " + calledFrom);
        }

        ArrayList < ComponentFile > insertRelObjects = new ArrayList<ComponentFile>();
        ComponentFile component;

        String[] orders = null;
        
        int intMax_OID = 0;
        int intMAX_PK = 0;
        int intRecords = 0;
        
        boolean flagInsert;
        
        int[] batchreturn = null;
        
        String project2 = "";
        
        int intREL_OID = 0;
        String strREL_RELATIONSHIP_TYPE_FK = "";
        int intREL_CHILD_FK = 0;
        int intREL_PARENT_FK = 0;

        int intRLP_SEQUENCE = -1;

        try{
            //get max oid from referenced database
        	intMax_OID = relationshipDAO.maximumOid();

            //get max pk from referenced ana_relationship_project
        	intMAX_PK = relationshipprojectDAO.maximumOid();

        	if ( !newTermList.isEmpty() ){
          
                if ( this.project.equals("GUDMAP") ) {
                    project2 = "EMAP";
                }
                if ( this.project.equals("EMAP") ) {
                    project2 = "GUDMAP";
                }

                for ( int i = 0; i< newTermList.size(); i++){
                    component = newTermList.get(i);

                    //reset flagInsert for each new component
                    flagInsert = true;

                    //get parents + group parents
                    ArrayList < String > parents  = new ArrayList<String>();
                    ArrayList < String > parentTypes  = new ArrayList<String>();
                    parents.addAll(component.getChildOfs());
                    parentTypes.addAll(component.getChildOfTypes());

                    //check whether component has any parents, if none issue warning, no need to proceed with insert
                    if ( parents.size() == 0 ) {
                        flagInsert = false;
                    } 

                    for ( int j = 0; j< parents.size(); j++){
                        //reset insertflag for each parent

                        flagInsert = true;
                        ComponentFile parent = (ComponentFile) this.tree.getComponent( parents.get(j) );

                        String strParentType = "";
                        strParentType = parentTypes.get(j);

                        //check whether parent has been deleted from obo file, do not allow insertion
                        if ( parent == null ) {
                             flagInsert = false;
                        }
                        //UPDATED CODE: deleted components are now marked in proposed file as well and appear in the tree under its own root outside abstract anatomy
                        else if ( parent.getStatusChange().equals("DELETED") ){
                            flagInsert = false;
                        }
                        //check whether any rules broken for each parent and print warning
                        //ignore any kind of rule violation for relationship record insertion except missing parent
                        else if ( parent.getStatusRule().equals("FAILED") ){
                            flagInsert = true;
                        }
                        //if parent is root Tmp new group don't treat as relationship
                        else if ( !parent.getNamespace().equals( this.abstractClass.getNamespace() ) ){
                            flagInsert = false;
                        }
                    
                        //proceed with insertion 
                        if (flagInsert){
                            ComponentFile insertRelObject = new ComponentFile();
                            insertRelObject.setID( component.getDBID() ); 
                            
                            String strParentDBID = "";
                            //get DBID for parent 
                            if ( parent.getStatusChange().equals("NEW") ){
                                 strParentDBID = parent.getDBID();
                            }
                            //if component is not new 
                            else {
                          	   Node node = nodeDAO.findByPublicId(parent.getID());
                          	   strParentDBID = Long.toString(node.getOid());
                            }

                            //get order for child based on parent
                            orders = component.getOrderCommentOnParent( parent.getID() );
                            if ( orders!=null ){
                                String[] arrayFirstWord = orders[0].split(" ");
                                insertRelObject.setOrderComment(arrayFirstWord[0]);
                            }
                            else {
                                insertRelObject.setOrderComment("");
                            }

                            insertRelObject.addChildOf( strParentDBID ); //parent dbid
                            insertRelObject.addChildOfType( strParentType ); //parent dbid

                            insertRelObjects.add( insertRelObject );
                        }
                    }
                }
                // END OF "for ( int i = 0; i< newTermList.size(); i++)"

                //INSERT INTO ANA_RELATIONSHIP
                if ( !insertRelObjects.isEmpty() ) {
                    //INSERT INTO ANA_OBJECT and set DBIDs
                    insertANA_OBJECT( insertRelObjects, "ANA_RELATIONSHIP" );
                    
                    //INSERT INTO ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT
                    for ( ComponentFile insertRelObject : insertRelObjects ){
                        intREL_OID = Integer.parseInt( insertRelObject.getDBID() );

                        if ( insertRelObject.getChildOfTypes().get(0).equals("PART_OF")) {
                             strREL_RELATIONSHIP_TYPE_FK = "part-of";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("IS_A")) {
                            strREL_RELATIONSHIP_TYPE_FK = "is-a";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("GROUP_PART_OF")) {
                            strREL_RELATIONSHIP_TYPE_FK = "group-part-of";
                        }

                        intREL_CHILD_FK = Integer.parseInt( insertRelObject.getID() );
                        intRLP_SEQUENCE = -1;

                        if ( !insertRelObject.getOrderComment().equals("") ){
                            intRLP_SEQUENCE = Integer.parseInt( insertRelObject.getOrderComment() );
                        }

                        Relationship relationship = new Relationship((long) intREL_OID, strREL_RELATIONSHIP_TYPE_FK, (long) intREL_CHILD_FK, (long) intREL_PARENT_FK);
                        this.relationshipDAO.save(relationship);

                  		 intMAX_PK++; //get max primary key for ana_relationship_project
                        RelationshipProject relationshipproject1 = new RelationshipProject((long) intMAX_PK, (long) intREL_OID, this.project, (long) intRLP_SEQUENCE);
                        this.relationshipprojectDAO.save(relationshipproject1);
                        
                        intMAX_PK++; //get max primary key for ana_relationship_project
                        RelationshipProject relationshipproject2 = new RelationshipProject((long) intMAX_PK, (long) intREL_OID, project2, (long) intRLP_SEQUENCE);
                        this.relationshipprojectDAO.save(relationshipproject2);

                    }
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }
    
    // 05
    //  Insert into ANA_TIMED_NODE
    private void insertANA_TIMED_NODE( ArrayList<ComponentFile> newTermList, String calledFrom ){
    	/*
    	 *  ANA_TIMED_NODE - 
    	 *  
         *  Columns:
         *   1. ATN_OID               - int(10) unsigned 
         *   2. ATN_NODE_FK           - int(10) unsigned 
         *   3. ATN_STAGE_FK          - int(10) unsigned 
         *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
         *   5. ATN_PUBLIC_ID         - varchar(20)      
    	 */

    	if (this.debug) {
            System.out.println("05 - insertANA_TIMED_NODE - called from = " + calledFrom);
        }

        ComponentFile component;

        try{
        	//03-1
            this.intCurrentPublicID = this.getMaxPublicID();
            
            //create timed components in ANA_OBJECT
            // 05-1
            this.timedCompList = this.createTimeComponentFiles(newTermList, calledFrom);
                           
            if ( !timedCompList.isEmpty() ) {
                //INSERT timed component obj_oids into ANA_OBJECT
                insertANA_OBJECT(timedCompList, "ANA_TIMED_NODE");

                int intPrevNode = 0;
                int counter = 0;
                int intCompieStage = 0;

                for(int k = 0; k< timedCompList.size(); k++){
                    component = timedCompList.get(k);

                    //prepare values
                    int intATN_OID = Integer.parseInt( component.getDBID() );
                    int intATN_NODE_FK = Integer.parseInt( component.getNamespace() );
                    int intATN_STAGE_FK = 0;

                    if (intPrevNode != intATN_NODE_FK) {
                        intCompieStage = component.getStartSequence();
                    }
                    else {
                        intCompieStage++;
                    }
                    
                    Stage stage = stageDAO.findBySequence((long) intCompieStage);
                    intATN_STAGE_FK = stage.getOid().intValue();
                    
                    //String strATN_STAGE_MODIFIER_FK //default null
                    String strATN_PUBLIC_ID = component.getID();
                    
                    TimedNode timednode = new TimedNode((long) intATN_OID, (long) intATN_NODE_FK, (long) intATN_STAGE_FK, null, strATN_PUBLIC_ID);
                    timednodeDAO.save(timednode);

                    intPrevNode = intATN_NODE_FK;

                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    // 06
    //  Insert into ANA_SYNONYM
    private void insertANA_SYNONYM( ArrayList<ComponentFile> newTermList, String calledFrom ){
    	/*
    	 *  ANA_NODE - ABSTRACT Synonyms in the Anatomy DAG
    	 *              - EMAPA:.... 
         *  
         *  Columns:
         *   1. SYN_OID         - int(10) unsigned
         *   2. SYN_OBJECT_FK   - int(10) unsigned
         *   3. SYN_SYNONYM     - varchar(100)
    	 */

        if (this.debug) {
            System.out.println("06 - insertANA_SYNONYM - called from = " + calledFrom);
        }

        ComponentFile component;

        this.synonymCompList.clear();

        try{
            
            //get max oid from referenced database
           this.intCurrentObjectID = this.getMaxObjectID(); 
           
           for ( int i = 0; i< newTermList.size(); i++){
            
               component = newTermList.get(i);
               
               //get parents 
               ArrayList < String > synonyms = component.getSynonyms();
               
               for (int j = 0; j< synonyms.size(); j++){
                   
            	   ComponentFile synonymcomponent = new ComponentFile();
                   synonymcomponent.setID( component.getDBID() );
                   synonymcomponent.setName( synonyms.get(j) );
                   
                   this.synonymCompList.add( synonymcomponent );
                   
               }
           }
           
           if ( !synonymCompList.isEmpty() ) {
        	   
               insertANA_OBJECT(synonymCompList, "ANA_SYNONYM");

               for( ComponentFile synCompie: synonymCompList ){

                   //proceed with insertion
                   int intSYN_OID = Integer.parseInt( synCompie.getDBID() );
                   int intSYN_OBJECT_FK = Integer.parseInt( synCompie.getID() );
                   String strSYN_SYNONYM = synCompie.getName();

                   Synonym synonym = new Synonym((long) intSYN_OID, (long) intSYN_OBJECT_FK, strSYN_SYNONYM);
                   synonymDAO.save(synonym);

               }

           }
           
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }

    }
    
    // 07
    //  Set Database Ids into a list of components
    private ArrayList setDBIDs( ArrayList<ComponentFile> termList ){

        if (this.debug) {
            System.out.println("07 - setDBIDs");
        }
        
        int intDBID = 0;

        ComponentFile component = new ComponentFile();
        
        try{
            //for (ComponentFile component : termList){
            for (int i = 0; i<termList.size(); i++ ){
                component = termList.get(i);
                
                Node node = nodeDAO.findByPublicId(component.getID());
                
                if (node == null) {
                    if ( component.getStatusChange().equals("DELETED") ){
                    	
                        component.setCheckComment("Delete Record Warning: No " +
                            "term with the ID " + component.getID() +
                            " exists in ANA_NODE, deletion for this " +
                            "component did not proceed.");
                        termList.remove(i);
                        i--;
                        
                        this.unDeletedCompList.add(component);
                    }
                    else if ( component.getStatusChange().equals("CHANGED") ){
                        
                    	component.setCheckComment("Update Record Warning: No " +
                            "term with the ID " + component.getID() +
                            " exists in ANA_NODE, changes made by the user " +
                            "for this component were not update in the " +
                            "database.");
                        termList.remove(i);
                        i--;
                        
                        this.unModifiedCompList.add(component);
                    }
                }
                else {
                    component.setDBID( Integer.toString(intDBID) );
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
        
        return termList;
    }

    // 09
    //  update Stage
    private void updateStages( ArrayList < ComponentFile > changedStageTermList ){

        if (this.debug) {
            System.out.println("09 - updateStages");
        }

        ArrayList < ComponentFile > deleteTimedComponentFiles =
                new ArrayList < ComponentFile >();
        
        //find ranges of stages that need to be inserted/deleted, create
        // temporary components for ranges
        // 09-1
        this.createDifferenceTimedComponentFiles( changedStageTermList );
        
        //insert time components in ANA_TIMED_NODE
        insertANA_TIMED_NODE( this.diffCreateTimedCompList, "MODIFY" );
        
        //insert time components to be deleted into ANA_LOG
          //insertANA_LOG_deletedStages( this.diffDeleteTimedCompList );
          //create timed components due for deletion
        
        //deleteTimedComponentFiles = this.createTimeComponentFiles( this.diffDeleteTimedCompList, "DELETE" ); //delete obj_oids!
        // 09-2
        deleteTimedComponentFiles =
               this.insertANA_LOG_deletedStages( this.diffDeleteTimedCompList );
        
        //delete time components in ANA_TIMED_NODE
        // 09-3
        deleteANA_TIMED_NODE( deleteTimedComponentFiles );
        
        //delete object oids of timed components in ANA_OBJECT
        // 09-4
        deleteANA_OBJECT( deleteTimedComponentFiles, "ANA_TIMED_NODE" );
        
    }

    // 11
    //  Update ANA_NODE for Changed Names
    private void updateANA_NODE( ArrayList< ComponentFile > changedNameTermList ){
    	/*
    	 *  ANA_NODE - ABSTRACT Nodes in the Anatomy DAG
    	 *              - EMAPA:.... 
         *  
         *  Columns:
         *   1. ANO_OID            - int(10) unsigned 
         *   2. ANO_SPECIES_FK     - varchar(20)      
         *   3. ANO_COMPONENT_NAME - varchar(255)     
         *   4. ANO_IS_PRIMARY     - tinyint(1)       
         *   5. ANO_IS_GROUP       - tinyint(1)       
         *   6. ANO_PUBLIC_ID      - varchar(20)      
         *   7. ANO_DESCRIPTION    - varchar(2000)    
    	 */

        if (this.debug) {
            System.out.println("11 - updateANA_NODE");
        }

        try {
            
            if ( !changedNameTermList.isEmpty() ){

            	for (ComponentFile component: changedNameTermList){
                	
                	Node node = nodeDAO.findByPublicId(component.getID());
                	
                	node.setComponentName(component.getName());
                	
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
    }

    // 13
    private void updateSynonyms( ArrayList < ComponentFile > changedSynonymsTermList ){

        if (this.debug) {
            System.out.println("13 - updateSynonyms");
        }
        ArrayList < ComponentFile > deleteSynComponentFiles = new ArrayList < ComponentFile >();
        
        //find ranges of stages that need to be inserted/deleted, create
        // temporary components for ranges
        // 13-1
        this.createDifferenceSynonyms( changedSynonymsTermList );
        
        //insert relationships in ANA_SYNONYM
        // 06
        insertANA_SYNONYM( this.diffCreateSynList, "MODIFY" );
        
        //insert relationships to be deleted in ANA_LOG
        // 13-2
        deleteSynComponentFiles =
                this.insertANA_LOG_deletedSyns( this.diffDeleteSynList );
        
        //delete relationships in ANA_SYNONYM
        // 13-3
        deleteANA_SYNONYM( deleteSynComponentFiles );
        
        //delete object oids of synonym records in ANA_OBJECT
        // 09-4
        deleteANA_OBJECT( deleteSynComponentFiles, "ANA_SYNONYM" );
        
    }

    // 15
    private void updateParents( ArrayList < ComponentFile > changedParentsTermList ){

        if (this.debug) {
            System.out.println("15 - updateParents");
        }

        ArrayList < ComponentFile > deleteRelComponentFiles =
                new ArrayList < ComponentFile >();
        
        //find ranges of stages that need to be inserted/deleted, create
        // temporary components for ranges
        // 15-1
        this.createDifferenceParents( changedParentsTermList );

        //insert relationships in ANA_RELATIONSHIP
        // 04
        insertANA_RELATIONSHIP( this.diffCreateRelList, "MODIFY");
        
        //insert relationships to be deleted in ANA_LOG
        // 15-2
        deleteRelComponentFiles =
                this.insertANA_LOG_deletedRels( this.diffDeleteRelList );
        
        //delete relationships in ANA_RELATIONSHIP
        // 15-3
        deleteANA_RELATIONSHIP( deleteRelComponentFiles );
        
        //delete object oids of relationship records in ANA_OBJECT
        // 09-4
        deleteANA_OBJECT( deleteRelComponentFiles, "ANA_RELATIONSHIP" );
        
        //reorder children in deleted/added child rows
    }

    // 15.5
    private void updateANA_NODE_primary( ArrayList< ComponentFile > changedPrimaryTermList ){

        if (this.debug) {
            System.out.println("15.5 - updateANA_NODE_primary");
        }

        try {
            if ( !changedPrimaryTermList.isEmpty() ) {
                for (ComponentFile component: changedPrimaryTermList) {

                	Node node = nodeDAO.findByPublicId(component.getID());
                    
                 	node.setGroup(!component.getIsPrimary());
                	node.setPrimary(component.getIsPrimary());

                	nodeDAO.save(node);
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }

    }

    // 17
    private void updateOrder( ArrayList < ComponentFile > changedOrderTermList ){

        if (this.debug) {
            System.out.println("17 - updateOrder");
        }

        /*
         * get all parents whose children have a changed order
         *  for each parent 
         *  get the children that have an order sequence 
         *  line them up from 0 - max entry 
         *  send collection of parents-orderedchildren to querymaker
         */
        //parent-> child order 1, child2, child3
        HashMap<String, ArrayList<String>> mapOrderedChildren = new HashMap(); 
        ArrayList<String> parents = new ArrayList<String>();
        ArrayList<String> children = new ArrayList<String>();
        ArrayList<String> commentsOnParent = new ArrayList<String>();
        
        ComponentFile childCompie = new ComponentFile();
        
        String[] arrayFirstWord = null;
        String forChild = "";
        int intMaxOrder = 0;
        
        for (ComponentFile component: changedOrderTermList){
            //get all parents
            parents.addAll(component.getChildOfs());
            //parents.addAll(component.getGroupPartOf());
            //parents.add(component.getIsA());
            //check for each parent whether there is ordering
            for (String parent: parents){
                commentsOnParent.clear(); //reset for each parent
                children.clear(); //reset for each parent
                intMaxOrder = -1; //reset for each parent
                //get all children
                children.addAll( this.tree.getChildrenBasedOnParent(parent) );
                //iterate through all children of each parent and gather all order comments
                for (String child: children){
                    forChild = ""; //reset for each child
                    //get component for child
                    childCompie = this.tree.getComponent(child);
                    //get order from child component based on the parent
                    String[] arrOrderComments = childCompie.getOrderCommentOnParent(parent);
                    //if there is an order put in order vector
                    if ( arrOrderComments!=null ){
                        //find max order number for this series of siblings
                        arrayFirstWord = arrOrderComments[0].split(" ");
                        if ( Integer.parseInt(arrayFirstWord[0]) > intMaxOrder ){
                            intMaxOrder = Integer.parseInt(arrayFirstWord[0]);
                        }

                        //get first word from order comment and append child to it to make a new comment based on 'for child'
                        forChild = arrayFirstWord[0] + " for " +
                                childCompie.getID();
                        //add to order comments for this parent
                        commentsOnParent.add(forChild);

                        //should never enter here if rule properly checked in CheckComponentFiles
                        if ( arrOrderComments.length>1 ) {
                            System.out.println("--WARNING! more than one " +
                                "order comment for the same parent "
                                + parent + " detected for component " +
                                component.getID());
                        }
                    } 
                }//iterated through list of children
                //once all order comments collected from the children of one parent
                //reorder the comments and put in Array based on sequence
                ArrayList<String> arrOrdered = null;
                if (intMaxOrder!=-1){
                    String[] ordered = new String[intMaxOrder+1];
                    arrOrdered = new ArrayList<String>();
                    int intOrder = 0;
                    String strChild = "";
                    for (String comment: commentsOnParent){
                        //get sequence number from order comment
                        arrayFirstWord = comment.split(" ");
                        intOrder = Integer.parseInt(arrayFirstWord[0]); //first item in array is order no.
                        strChild = arrayFirstWord[arrayFirstWord.length-1]; //last item in array is child component id
                        //build an ordered, sorted array of comments for the parent
                        if (intOrder!=-1){
                            ordered[intOrder] = strChild;
                        }
                    }
                    //convert ordered array to arraylist, retaining order of elements
                    //put unordered elements at the end of list
                    for(int i=0; i<ordered.length; i++){
                        arrOrdered.add(ordered[i]);
                    }
                }
                //build hashmap parent-> sorted children
                //don't add entry for parents that already have entry
                if ( mapOrderedChildren.get(parent)==null ){
                    mapOrderedChildren.put(parent, arrOrdered);
                }
            }
        }
        //test
        //System.out.println(mapOrderedChildren.toString());

        //send hashmap to update_orderANA_RELATIONSHIP
        // 17-1
        update_orderANA_RELATIONSHIP(mapOrderedChildren);
    }

    // 18
    private ArrayList < ComponentFile > validateDeleteTermList(ArrayList<ComponentFile> deletedTermList){
        /*
        method to validate term list scheduled for deletion
         check that all for each component in term list, if its primary, then all
         descendants are scheduled to be deleted
         if one of its descendants cannot be found in OBO file or has not been
         scheduled for deletion
         the current component will be removed from the returned term list
         returned term list will be used for actual deletion
         and updated to ANA_LOG
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

        if (this.debug) {
            System.out.println("18 - validateDeleteTermList");
        }

        ArrayList<ComponentFile> dbTermList = new ArrayList<ComponentFile>();
        Vector<String> dependentDescendants = new Vector<String>();
        
        Boolean invalidDelete = false;
        
        try {
            //for each term in deletedTermList
            for (ComponentFile deleteCompie: deletedTermList){
                //get all dependent descendants
                dependentDescendants = recursiveGetDependentDescendants(deleteCompie.getID(), dependentDescendants, invalidDelete);
            
                if ( invalidDelete ) {
                    //disallow deletion
                    //put to unDeletedCompList for report purposes
                    deleteCompie.setCheckComment("Delete Record Warning: " + 
                        deleteCompie.getID() + " still has descendants in " +
                        "the database. Deletion not allowed.");
                
                    deleteCompie.setCheckComment("Delete Record Warning: " +
                        "Deletion of this term could potentially result in " +
                        "undesirable orphan terms or other ontology " +
                        "violations. Please generate a new OBO file from the " +
                        "database and retry deletion");
                    
                    this.unDeletedCompList.add(deleteCompie);
                }
                else {
                    
                	dbTermList.add(deleteCompie);
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        
        return dbTermList;
        
    }
    
    // 19
    private void insertANA_LOG(ArrayList<ComponentFile> recordTermList){
    	/*
    	 *  ANA_Log - A Log of all Updates to the Anatomy Database
         *  
         *  Columns:
         *   1.  LOG_OID         => int(10) unsigned
         *   2.  LOG_LOGGED_OID  => int(10) unsigned
         *   3.  LOG_VERSION_FK  => int(10) unsigned
         *   4.  LOG_COLUMN_NAME => varchar(64)
         *   5.  LOG_OLD_VALUE   => varchar(255)
         *   6.  LOG_COMMENTS    => varchar(255)
         *   
    	 */

        if (this.debug) {
            System.out.println("19 - insertANA_LOG");
        }

        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        //version_oid should be very first obj_oid created for easy tracing
        int intLOG_VERSION_FK = this.intCurrentVersionID;
        String strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components";

        //create one record
        int intLogOID = 0;
        int intLogLoggedOID = 0;

        HashMap<String, String> anoOldValues = new HashMap();
        HashMap<String, String> atnOldValues = new HashMap();
        HashMap<String, String> relOldValues = new HashMap();
        HashMap<String, String> synOldValues = new HashMap();

        //ANA_NODE columns
    	/*
         *   1. ANO_OID            - int(10) unsigned 
         *   2. ANO_SPECIES_FK     - varchar(20)      
         *   3. ANO_COMPONENT_NAME - varchar(255)     
         *   4. ANO_IS_PRIMARY     - tinyint(1)       
         *   5. ANO_IS_GROUP       - tinyint(1)       
         *   6. ANO_PUBLIC_ID      - varchar(20)      
         *   7. ANO_DESCRIPTION    - varchar(2000)    
    	 */
        Vector<String> vANOcolumns = new Vector();
        vANOcolumns.add("ANO_OID");
        vANOcolumns.add("ANO_SPECIES_FK");
        vANOcolumns.add("ANO_COMPONENT_NAME");
        vANOcolumns.add("ANO_IS_PRIMARY");
        vANOcolumns.add("ANO_IS_GROUP");
        vANOcolumns.add("ANO_PUBLIC_ID");
        vANOcolumns.add("ANO_DESCRIPTION");
        
        int intIsPrimary = 0;
        int intIsGroup = 0;

        //ANA_RELATIONSHIP columns
    	/*
         *   1. REL_OID                  - int(10) unsigned 
         *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
         *   3. REL_CHILD_FK             - int(10) unsigned 
         *   4. REL_PARENT_FK            - int(10) unsigned 
    	 */
        Vector<String> vRELcolumns = new Vector();
        vRELcolumns.add("REL_OID");
        vRELcolumns.add("REL_RELATIONSHIP_TYPE_FK");
        vRELcolumns.add("REL_PARENT_FK");
        vRELcolumns.add("REL_CHILD_FK");

        //column values for selection from ANA_RELATIONSHIP
        int intREL_OID = 0;
        String strREL_RELATIONSHIP_TYPE_FK = "";
        int intREL_PARENT_FK = 0;
        int intREL_CHILD_FK = 0;

        //ANA_TIMED_NODE columns
    	/*
         *   1. ATN_OID               - int(10) unsigned 
         *   2. ATN_NODE_FK           - int(10) unsigned 
         *   3. ATN_STAGE_FK          - int(10) unsigned 
         *   4. ATN_STAGE_MODIFIER_FK - varchar(20)      
         *   5. ATN_PUBLIC_ID         - varchar(20)      
    	 */
        Vector<String> vATNcolumns = new Vector();
        vATNcolumns.add("ATN_OID");
        vATNcolumns.add("ATN_NODE_FK");
        vATNcolumns.add("ATN_STAGE_FK");
        vATNcolumns.add("ATN_STAGE_MODIFIER_FK");
        vATNcolumns.add("ATN_PUBLIC_ID");

        //column values for selection from ANA_TIMED_NODE
        int intATN_OID = 0;
        int intATN_STAGE_FK = 0;
        String strATN_STAGE_MODIFIER_FK = "";
        String strATN_PUBLIC_ID = "";
        int intATN_NODE_FK = 0;

        //ANA_SYNONYM columns
    	/*
         *   1. SYN_OID         - int(10) unsigned
         *   2. SYN_OBJECT_FK   - int(10) unsigned
         *   3. SYN_SYNONYM     - varchar(100)
    	 */
        Vector<String> vSYNcolumns = new Vector();
        vSYNcolumns.add("SYN_OID");
        vSYNcolumns.add("SYN_OBJECT_FK");
        vSYNcolumns.add("SYN_SYNONYM");
        
        //column values for selection from ANA_SYNONYM
        int intSYN_OID = 0;
        int intSYN_OBJECT_FK = 0;
        String strSYN_SYNONYM = "";

        try{
        	//get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();
            //INSERT INTO ANA_LOG

            if ( !recordTermList.isEmpty() ) {
                //for each component to be deleted
                for (ComponentFile component: recordTermList){

                    if ( component.getIsGroup() ) {
                    	intIsGroup = 1;
                    }
                    else {
                    	intIsGroup = 0;
                    }

                    if ( component.getIsPrimary() ) {
                    	intIsPrimary = 0;
                    }
                    else {
                    	intIsPrimary = 1;
                    }
                    
                    anoOldValues.clear();
                    anoOldValues.put( "ANO_OID", component.getDBID().toString() );
                    anoOldValues.put( "ANO_SPECIES_FK", this.strSpecies );
                    anoOldValues.put( "ANO_COMPONENT_NAME", component.getName().toString() );
                    anoOldValues.put( "ANO_IS_PRIMARY", Integer.toString(intIsPrimary));
                    anoOldValues.put( "ANO_IS_GROUP", Integer.toString(intIsGroup));
                    anoOldValues.put( "ANO_PUBLIC_ID", component.getID().toString() );
                    anoOldValues.put( "ANO_DESCRIPTION", "N/A" );

                    //increment for each component
                    ++intLogLoggedOID;
                	
                    for (String columnName: vANOcolumns){	
                        intLOG_OID = ++intLogOID;
                        intLOG_LOGGED_OID = intLogLoggedOID;
                        strLOG_COLUMN_NAME = columnName;
                        strLOG_OLD_VALUE = anoOldValues.get(columnName);
                        intLOG_VERSION_FK = this.intCurrentVersionID;
                        strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components; ANA_NODE";

                        Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                        logDAO.save(log); 
                    }

                    ArrayList<TimedNode> timednodes = (ArrayList) timednodeDAO.listByNodeFK(Long.valueOf(component.getDBID()));
                    
                  	Iterator<TimedNode> iteratortimednode = timednodes.iterator();

                  	while (iteratortimednode.hasNext()) {
                    
                  		TimedNode timednode = iteratortimednode.next();

                        intATN_OID = timednode.getOid().intValue();
                        intATN_NODE_FK = timednode.getNodeFK().intValue();
                        intATN_STAGE_FK = timednode.getStageFK().intValue();
                        strATN_STAGE_MODIFIER_FK = timednode.getStageModifierFK();
                        strATN_PUBLIC_ID = timednode.getPublicId();

                        //increment for each timed component record
                        ++intLogLoggedOID;

                        //clear HashMap atnOldValues
                        atnOldValues.clear();
                        atnOldValues.put( "ATN_OID", Integer.toString( intATN_OID ) );
                        atnOldValues.put( "ATN_NODE_FK", Integer.toString( intATN_NODE_FK ) );
                        atnOldValues.put( "ATN_STAGE_FK", Integer.toString( intATN_STAGE_FK ) );
                        atnOldValues.put( "ATN_STAGE_MODIFIER_FK", strATN_STAGE_MODIFIER_FK );
                        atnOldValues.put( "ATN_PUBLIC_ID", strATN_PUBLIC_ID );

                        for (String columnName: vATNcolumns){

                            intLOG_OID = ++intLogOID;
                            intLOG_LOGGED_OID = intLogLoggedOID;
                            strLOG_COLUMN_NAME = columnName;
                            strLOG_OLD_VALUE = atnOldValues.get(columnName);
                            intLOG_VERSION_FK = this.intCurrentVersionID;
                            strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components; ANA_TIMED_NODE";

                            Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                            logDAO.save(log); 
                        }     

                  	}

                  	ArrayList<Relationship> relationships = (ArrayList) relationshipDAO.listByChildFK(Long.valueOf(component.getDBID()));
                  	
                  	Iterator<Relationship> iteratorrelationship = relationships.iterator();

                  	while (iteratorrelationship.hasNext()) {
                    
                  		Relationship relationship = iteratorrelationship.next();

                        intREL_OID = relationship.getOid().intValue();
                        strREL_RELATIONSHIP_TYPE_FK = relationship.getTypeFK();
                        intREL_PARENT_FK = relationship.getParentFK().intValue();
                        intREL_CHILD_FK = relationship.getChildFK().intValue();

                        //increment for each relationship record
                        ++intLogLoggedOID;

                        //clear HashMap relOldValues
                        relOldValues.clear();
                        relOldValues.put( "REL_OID", Integer.toString( intREL_OID ) );
                        relOldValues.put( "REL_RELATIONSHIP_TYPE_FK", strREL_RELATIONSHIP_TYPE_FK );
                        relOldValues.put( "REL_PARENT_FK", Integer.toString( intREL_PARENT_FK ) );
                        relOldValues.put( "REL_CHILD_FK", Integer.toString( intREL_CHILD_FK ) );

                        for (String columnName: vRELcolumns){
                            intLOG_OID = ++intLogOID;
                            intLOG_LOGGED_OID = intLogLoggedOID;
                            strLOG_COLUMN_NAME = columnName;
                            strLOG_OLD_VALUE = relOldValues.get(columnName);
                            intLOG_VERSION_FK = this.intCurrentVersionID;
                            strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components; ANA_RELATIONSHIP";

                            Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                            logDAO.save(log); 
                        }     
                    }
                  	
                  	ArrayList<Synonym> synonyms = (ArrayList) synonymDAO.listByObjectFK(Long.valueOf(component.getDBID()));
                  			
                  	Iterator<Synonym> iteratorsynonym = synonyms.iterator();

                  	while (iteratorsynonym.hasNext()) {
                  		Synonym synonym = iteratorsynonym.next();

                        intSYN_OID = synonym.getOid().intValue();
                        intSYN_OBJECT_FK = synonym.getThingFK().intValue();
                        strSYN_SYNONYM = synonym.getName();

                        //increment for each relationship record
                        ++intLogLoggedOID;

                        //clear HashMap synOldValues
                        synOldValues.clear();
                        synOldValues.put("SYN_OID", Integer.toString(intSYN_OID) );
                        synOldValues.put("SYN_OBJECT_FK", Integer.toString(intSYN_OBJECT_FK) );
                        synOldValues.put("SYN_SYNONYM", strSYN_SYNONYM);

                        for (String columnName: vSYNcolumns){
                            intLOG_OID = ++intLogOID;
                            intLOG_LOGGED_OID = intLogLoggedOID;
                            strLOG_COLUMN_NAME = columnName;
                            strLOG_OLD_VALUE = synOldValues.get(columnName);
                            intLOG_VERSION_FK = this.intCurrentVersionID;
                            strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components; ANA_SYNONYM";

                            Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                            logDAO.save(log); 
                        }     
                    }
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    // 20
    private void deleteComponentFileFromTables (ArrayList <ComponentFile> validDeleteTermList){

        if (this.debug) {
            System.out.println("20 - deleteComponentFileFromTables");
        }

        try {
            if ( !validDeleteTermList.isEmpty() ) {
                for (ComponentFile component: validDeleteTermList){
                	
                	// delete ANA_RELATIONSHIP rows, if any
                	ArrayList<Relationship> relationships = (ArrayList) relationshipDAO.listByChildFK(Long.valueOf(component.getDBID()));
                  	Iterator<Relationship> iteratorrelationships = relationships.iterator();

                  	while (iteratorrelationships.hasNext()) {
                  		Relationship relationship = iteratorrelationships.next();

                        //delete ANA_RELATIONSHIP_PROJECT that have foreign key constraints on ANA_RELATIONSHIP, if any
                    	ArrayList<RelationshipProject> relationshipprojects = (ArrayList) relationshipprojectDAO.listByRelationshipFK(relationship.getOid());
                      	Iterator<RelationshipProject> iteratorrelationshipprojects = relationshipprojects.iterator();

                      	while (iteratorrelationshipprojects.hasNext()) {
                      		RelationshipProject relationshipproject = iteratorrelationshipprojects.next();
                      		
                      		relationshipprojectDAO.delete(relationshipproject);
                      	}
                    	
                      	// Find associated ANA_OBJECT rows and delete them 
                      	if ( thingDAO.existOid(relationship.getOid())) {
                      		Thing thing = thingDAO.findByOid(relationship.getOid());
                      		thingDAO.delete(thing);
                      	}
                      	
                      	// Delete the ANA_RELATIONSHIP rows
                  		relationshipDAO.delete(relationship);
                  	}
                	
                  	// Delete the ANA_NODE rows, if any
                  	if ( nodeDAO.existOid(Long.valueOf(component.getDBID()))) {
                    	Node node = nodeDAO.findByOid(Long.valueOf(component.getDBID()));
                    	
                      	// Find associated ANA_OBJECT rows and delete them 
                      	if ( thingDAO.existOid(node.getOid())) {
                      		Thing thing = thingDAO.findByOid(node.getOid());
                      		
                      		thingDAO.delete(thing);
                      	}

                    	nodeDAO.delete(node);
                  	}

                  	// Delete the ANA_TIMED_NODE rows, if any
                	ArrayList<TimedNode> timednodes = (ArrayList) timednodeDAO.listByNodeFK(Long.valueOf(component.getDBID()));
                  	Iterator<TimedNode> iteratortimednodes = timednodes.iterator();

                  	while (iteratortimednodes.hasNext()) {
                  		TimedNode timednode = iteratortimednodes.next();
                  		
                      	// Find associated ANA_OBJECT rows and delete them 
                      	if ( thingDAO.existOid(timednode.getOid())) {
                      		Thing thing = thingDAO.findByOid(timednode.getOid());
                      		
                      		thingDAO.delete(thing);
                      	}

                  		timednodeDAO.delete(timednode);
                  	}

                  	// Delete the ANA_SYNONYM rows, if any
                	ArrayList<Synonym> synonyms = (ArrayList) synonymDAO.listByObjectFK(Long.valueOf(component.getDBID()));
                  	Iterator<Synonym> iteratorsynonyms = synonyms.iterator();

                  	while (iteratorsynonyms.hasNext()) {
                  		Synonym synonym = iteratorsynonyms.next();
                  		
                      	// Find associated ANA_OBJECT rows and delete them 
                      	if ( thingDAO.existOid(synonym.getOid())) {
                      		Thing thing = thingDAO.findByOid(synonym.getOid());
                      		
                      		thingDAO.delete(thing);
                      	}

                  		synonymDAO.delete(synonym);
                  	}

                  	// Delete ANA_OBJECT rows, if any 
                  	if ( thingDAO.existOid(Long.valueOf(component.getDBID())) ) {
                  		Thing thing = thingDAO.findByOid(Long.valueOf(component.getDBID()));
                  		
                  		thingDAO.delete(thing);
                  	}
                	
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        } 
    }

    // 21
    private void reorderANA_RELATIONSHIP(ArrayList <ComponentFile> validDeleteTermList, String project){
        /*
         * Reorders a collection of siblings that have order sequence entries in the ANA_RELATIONSHIP
         *   when one of the siblings have been deleted, re-ordering is based on original order and closes
         *   the sequence gap left by the deleted sibling
         * NOTE: function is obsolete if editor re-orders the remaining siblings, current rules
         * checking do not allow database to be updated if the ordering has a gap anyway
         */

        if (this.debug) {
            System.out.println("21 - reorderANA_RELATIONSHIP");
        }

        ArrayList <String> componentParents = new ArrayList<String>();

        String skipRecords = "";
        
        int parentDBID = -1;
        int childDBID = -1;
        int intSEQ = -1;
        
        try {
            if ( !validDeleteTermList.isEmpty() ) {

            	//for each component to be deleted
                for (ComponentFile component: validDeleteTermList){

                	//get all parent-child relationship entries with ordering
                    childDBID = this.getDatabaseIdentifier(component.getID());
                    
                    skipRecords = "";

                    ArrayList<JOINNodeRelationshipRelationshipProject> joinnrrps = (ArrayList) joinnoderelationshiprelationshipprojectDAO.listAllByChildFK((long) childDBID);
                  	Iterator<JOINNodeRelationshipRelationshipProject> iteratorjoinnrrps = joinnrrps.iterator();

                  	while (iteratorjoinnrrps.hasNext()) {
                  		JOINNodeRelationshipRelationshipProject joinnrrp = iteratorjoinnrrps.next();
                  		
                        if ( joinnrrp.getSequenceFK() != null ){
                        	
                            componentParents.add( joinnrrp.getPublicId() );
                            //make a list of parent-child relationship entries to be deleted
                            skipRecords = skipRecords + joinnrrp.getOidRelationshipProject().intValue() + ",";
                            
                        }
                  	}
                }

                if (!skipRecords.equals("")){
                	
                    skipRecords = skipRecords.substring(0, skipRecords.length() - 1);
                    skipRecords = "(" + skipRecords + ")";
                    
                }
                
                //check for each parent whether there is ordering
                for (String parent: componentParents){
                    //get parent dbid
                    parentDBID = this.getDatabaseIdentifier(parent);
                    //reset REL_SEQ
                    intSEQ = 0;
                    //get all children records from ANA_RELATIONSHIP_PROJECT for this parent
                    //that has an order sequence entry, order by sequence
                    //exclude entries that are scheduled for deletion
                    
                    ArrayList<JOINRelationshipProjectRelationship> joinrprs = (ArrayList) joinrelationshipprojectrelationshipDAO.listAllByParentAndProjectNotIn((long) parentDBID, project, skipRecords);
                    Iterator<JOINRelationshipProjectRelationship> iteratorjoinrprs = joinrprs.iterator();

                  	while (iteratorjoinrprs.hasNext()) {
                  		JOINRelationshipProjectRelationship joinrpr = iteratorjoinrprs.next();
                  		
                  		RelationshipProject relationshipproject = relationshipprojectDAO.findByOid(joinrpr.getOidRelationshipProject());
                  		
                  		relationshipproject.setSequence((long) intSEQ);
                  		relationshipprojectDAO.save(relationshipproject);

                        //increment rel_seq for next record
                        intSEQ++;
                  	}
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        } 
    }

    
    // SUB Methods ------------------------------------------------------------------------------------

    // 01-1
    private int getMaxObjectID(){

        if (this.debug) {
            System.out.println("1-1 getMaxObjectID");
        }

        //use old and new connection
        //select max public id from old ana_object
        //select max public id from new ana_object
        
        int intMax_OID = 0;
        int intMaxObjectID = 0;
        
        try{
            //get max oid from referenced database
        	intMax_OID = this.thingDAO.maximumOid();

            //get larger public id
        	if ( intMax_OID >= this.intCurrentObjectID ) {
        		intMaxObjectID = intMax_OID;
        	}
        	else {
        		intMaxObjectID = this.intCurrentObjectID; 
        	}

        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
            ex.printStackTrace();
        } 

    	return intMaxObjectID;
        
    }
    
    // 02-1
    // Insert a new row into ANA_OBJECT
    private void insertANA_OBJECT( ArrayList< ComponentFile > newTermList, String calledFromTable ){
    	/*
         *  Columns:
         *   1. OBJ_OID               - int(10) unsigned 
         *   2. OBJ_CREATION_DATETIME - datetime         
         *   3. OBJ_CREATOR_FK        - int(10) unsigned 
         *   4. OBJ_TABLE             - varchar(255)
         *   5. OBJ_DESCRIPTION       - varchar(255)
    	 */

        if (this.debug) {
            System.out.println("02-1 insertANA_OBJECT - for Inserts to Table " + calledFromTable);
        }
        
        ComponentFile component = new ComponentFile();

        try {
            this.intCurrentObjectID = this.getMaxObjectID();
            
            int intOBJ_OID = 0;
            String datetime = "NOW()";
            long sysadmin = 2;
            String description = "";

            if ( !newTermList.isEmpty() ){

                for ( int i=0; i<newTermList.size(); i++ ){
                    component = newTermList.get(i);

                    //database values
                    intOBJ_OID = ++this.intCurrentObjectID;
                    //update new components with ano_oid/atn_oid
                    component.setDBID(Integer.toString(intOBJ_OID));

                    Thing thing = new Thing((long) intOBJ_OID, datetime, sysadmin, calledFromTable, description);
                    this.thingDAO.save(thing);
                }
            }
        }
        catch ( DAOException dao ) {
                dao.printStackTrace();
        } 
        catch ( Exception ex ) {
            ex.printStackTrace();
        } 
    }

    // 03-1
    private int getMaxPublicID(){

        if (this.debug) {
            System.out.println("03-1 - getMaxPublicID");
        }

        //use new connection
        //select max public id from ana_node
        //select max public id from ana_timed_node
        
        int intMax_ATNpublicID = 0;
        int intMax_ANOpublicID = 0;
        int intMaxPublicID = 0;
        
        try{
            //get max emap id from updated ana_timed_node
        	intMax_ATNpublicID = timednodeDAO.maximumEmap();
        	
            //get max emapa id from updated ana_node
        	intMax_ANOpublicID = nodeDAO.maximumEmapa();

            //get larger public id
        	if ( intMax_ATNpublicID >= intMax_ANOpublicID ) {
        		intMaxPublicID = intMax_ATNpublicID;
        	}
        	else {
        		intMaxPublicID = intMax_ANOpublicID;
        	}

        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 

        //note: intCurrentPublicID should be larger than current intMaxPublicID if method has been called before
        //get larger public id
    	if ( this.intCurrentPublicID >= intMaxPublicID ) {
    		intMaxPublicID = this.intCurrentPublicID;
    	}
    	else {
    		intMaxPublicID = intMaxPublicID;
    	}
        
        return intMaxPublicID;
        
    }

    // 05-1
    private ArrayList < ComponentFile > createTimeComponentFiles(ArrayList<ComponentFile> termList, String calledFrom){

        if (this.debug) {
            System.out.println("05-1 - createTimeComponentFiles");
        }

       //create timed components in ANA_OBJECT
       ComponentFile component = new ComponentFile();
       
       boolean flagInsert;
       
       ArrayList<ComponentFile> timedComps = new ArrayList<ComponentFile>();

       for ( int i = 0; i< termList.size(); i++){
           component = termList.get(i);

           if (!( component.commentsContain("Relation: ends_at -- Missing ends at stage - ComponentFile's stage range cannot be determined.") ) &&
               !( component.commentsContain("Relation: starts_at -- Missing starts at stage - ComponentFile's stage range cannot be determined.") ) &&
               !( component.commentsContain("Relation: starts_at, ends_at -- Ends at stage earlier than starts at stage.") ) &&
               !( component.commentsContain("Relation: Starts At - More than one Start Stage!") ) &&
               !( component.commentsContain("Relation: Ends At - More than one End Stage!") ) &&
               !( component.commentsContain("Relation: starts_at, ends_at -- Stages are out of range!") ) ) 
           {
               flagInsert = false; 
           }
           else {
               flagInsert = true; 
           }

           if (flagInsert){
               //make a time component record for each stage
               for(int j = component.getStartSequence(); j <= component.getEndSequence(); j++ ){

                   String strStage = component.getStart();

                   ComponentFile timedCompie = new ComponentFile();
                   timedCompie.setNamespace( component.getDBID() ); //current component

                   timedCompie.setStart( strStage );

                   if (this.strSpecies.equals("mouse")) {
                       timedCompie.setID( "EMAP:" +
                               Integer.toString( ++this.intCurrentPublicID ) );
                   }
                   if (this.strSpecies.equals("human")) {
                       timedCompie.setID( "EHDA:" +
                               Integer.toString( ++this.intCurrentPublicID ) );
                   }
                   if (this.strSpecies.equals("chick")) {
                       timedCompie.setID( "ECAP:" +
                               Integer.toString( ++this.intCurrentPublicID ) );
                   }

                   timedComps.add(timedCompie);
                   //object_counter++;
               }
           }
       }
       return timedComps;

    }

    // 09-1
    //  method to measure difference in stage ranges between modified components and existing components in DB
    private void createDifferenceTimedComponentFiles( ArrayList<ComponentFile> diffStageTermList ){

        if (this.debug) {
            System.out.println("09-1 - createDifferenceTimedComponentFiles");
        }
        
        try {
            for (ComponentFile component: diffStageTermList){

            	//get stage range from database
            	ArrayList<JOINTimedNodeStage> jointimednodestages = (ArrayList) jointimednodestageDAO.listAllByNodeFkOrderByStageSequence(Long.valueOf(component.getDBID()));
            	
            	JOINTimedNodeStage jointimednodestagefirst = jointimednodestages.get(0);
            	JOINTimedNodeStage jointimednodestagelast = jointimednodestages.get(jointimednodestages.size() - 1);

                ComponentFile databasecomponent = new ComponentFile();

                databasecomponent.setStart( jointimednodestagefirst.getName() );
                databasecomponent.setEnd( jointimednodestagelast.getName() );
            	
                //compare stage ranges between component and databasecomponent
                // for creating new timed components
                if ( databasecomponent.getStartSequence() > component.getStartSequence() ){
                   ComponentFile createtimedcomponent = new ComponentFile();
                   createtimedcomponent.setID( component.getID() );
                   createtimedcomponent.setName( component.getName() );
                   createtimedcomponent.setDBID( component.getDBID() );
                   createtimedcomponent.setStart( component.getStart() );
                   createtimedcomponent.setEndSequence( databasecomponent.getStartSequence() - 1, this.strSpecies );
                   
                   this.diffCreateTimedCompList.add( createtimedcomponent );

                }
                if ( databasecomponent.getEndSequence() < component.getEndSequence() ){
                   ComponentFile createtimedcomponent = new ComponentFile();
                   createtimedcomponent.setID( component.getID() );
                   createtimedcomponent.setName( component.getName() );                   
                   createtimedcomponent.setDBID( component.getDBID() );
                   createtimedcomponent.setStartSequence( databasecomponent.getEndSequence() + 1, this.strSpecies );
                   createtimedcomponent.setEndSequence( component.getEndSequence(), this.strSpecies );
                   
                   this.diffCreateTimedCompList.add( createtimedcomponent );
                   
                }
                //for deleting existing timed components
                if ( databasecomponent.getStartSequence() < component.getStartSequence() ){
                   ComponentFile delTimedCompie = new ComponentFile();
                   delTimedCompie.setID( component.getID() );
                   delTimedCompie.setName( component.getName() );
                   delTimedCompie.setDBID( component.getDBID() );
                   delTimedCompie.setStartSequence( databasecomponent.getStartSequence(), this.strSpecies );
                   delTimedCompie.setEndSequence( component.getStartSequence() - 1, this.strSpecies );

                   this.diffDeleteTimedCompList.add( delTimedCompie );
                   
                }
                if ( databasecomponent.getEndSequence() > component.getEndSequence() ){
                    ComponentFile delTimedCompie = new ComponentFile();
                    delTimedCompie.setID( component.getID() );
                    delTimedCompie.setName( component.getName() );
                    delTimedCompie.setDBID( component.getDBID() );
                    delTimedCompie.setStartSequence( component.getEndSequence() + 1, this.strSpecies );
                    delTimedCompie.setEndSequence( databasecomponent.getEndSequence(), this.strSpecies );

                    this.diffDeleteTimedCompList.add( delTimedCompie );
                    
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 

    }
    
    // 09-2
    //  Insert into ANA_LOG for ANA_TIMED_NODE Deletions
    private ArrayList insertANA_LOG_deletedStages( ArrayList<ComponentFile> diffDeleteTimeComponentFiles ){
    	/*
    	 *  ANA_Log - A Log of all Updates to the Anatomy Database
         *  
         *  Columns:
         *   1.  LOG_OID         => int(10) unsigned
         *   2.  LOG_LOGGED_OID  => int(10) unsigned
         *   3.  LOG_VERSION_FK  => int(10) unsigned
         *   4.  LOG_COLUMN_NAME => varchar(64)
         *   5.  LOG_OLD_VALUE   => varchar(255)
         *   6.  LOG_COMMENTS    => varchar(255)
         *   
    	 */

        if (this.debug) {
            System.out.println("09-2 - insertANA_LOG_deletedStages");
        }
        
        ArrayList < ComponentFile > deleteTimeComponentFiles = new ArrayList < ComponentFile >();
        
        HashMap<String, String> atnOldValues = new HashMap(); 
        int intLogLoggedOID = 0;
        int intLogOID = 0;
        int intStartKey = 0;
        int intEndKey = 0;
        String stageName = "";
        int intStage = 0;
        
        //ANA_TIMED_NODE columns
        Vector<String> vATNcolumns = new Vector();
        vATNcolumns.add("ATN_OID");
        vATNcolumns.add("ATN_STAGE_FK");
        vATNcolumns.add("ATN_STAGE_MODIFIER_FK");
        vATNcolumns.add("ATN_PUBLIC_ID");
        vATNcolumns.add("ATN_NODE_FK");
        
        //column values for selection from ANA_TIMED_NODE
        int intATN_OID = 0;
        int intATN_STAGE_FK = 0;
        String strATN_STAGE_MODIFIER_FK = "";
        String strATN_PUBLIC_ID = "";
        int intATN_NODE_FK = 0;
        
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        
        //version_oid should be very first obj_oid created for easy tracing
        int intLOG_VERSION_FK = this.intCurrentVersionID; 
        String strLOG_COMMENTS = "Insert into ANA_LOG for Deleted TimedNodes";
        
        try {
        	//get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();

            if ( !diffDeleteTimeComponentFiles.isEmpty() ) {

            	for ( ComponentFile component: diffDeleteTimeComponentFiles ){

                    intStartKey = component.getStartSequence();
                    intEndKey = component.getEndSequence();

                    for ( int stage=intStartKey; stage<=intEndKey; stage++ ){

                        stageName = component.getStart();

                        ArrayList<JOINTimedNodeStage> jointimednodestages = (ArrayList) jointimednodestageDAO.listAllByNodeFkAndStageName(Long.valueOf(component.getDBID()), stageName);

                      	Iterator<JOINTimedNodeStage> iteratorjointimednodestage = jointimednodestages.iterator();

                      	while (iteratorjointimednodestage.hasNext()) {
                        
                      		JOINTimedNodeStage jointimednodestage = iteratorjointimednodestage.next();
                      		
                            ComponentFile deleteTimeComponentFile = new ComponentFile();

                            deleteTimeComponentFile.setDBID( jointimednodestage.getOidTimedNode().toString());
                            deleteTimeComponentFile.setID( jointimednodestage.getPublicTimedNodeId());
                            deleteTimeComponentFile.setNamespace( component.getDBID() ); 
                            deleteTimeComponentFile.setStart( stageName );
                            
                            deleteTimeComponentFiles.add( deleteTimeComponentFile );
                            
                            intATN_OID = jointimednodestage.getOidTimedNode().intValue();
                            intATN_STAGE_FK = jointimednodestage.getStageFK().intValue();
                            strATN_STAGE_MODIFIER_FK = jointimednodestage.getStageModifierFK();
                            strATN_PUBLIC_ID = jointimednodestage.getPublicTimedNodeId();
                            intATN_NODE_FK = jointimednodestage.getNodeFK().intValue();
                            
                            //increment for each timed component record
                            ++intLogLoggedOID;

                            //clear HashMap atnOldValues
                            atnOldValues.clear();
                            
                            atnOldValues.put( "ATN_OID", Integer.toString( intATN_OID ) );
                            atnOldValues.put( "ATN_STAGE_FK", Integer.toString( intATN_STAGE_FK ) );
                            atnOldValues.put( "ATN_STAGE_MODIFIER_FK", strATN_STAGE_MODIFIER_FK );
                            atnOldValues.put( "ATN_PUBLIC_ID", strATN_PUBLIC_ID );
                            atnOldValues.put( "ATN_NODE_FK", Integer.toString( intATN_NODE_FK ) );
                            
                            for (String columnName: vATNcolumns){
                              	
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = atnOldValues.get(columnName);

                                Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                                logDAO.save(log);
                            }
                      	}     
                    }
            	}    
            }
        }
        catch ( DAOException dao ) {
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 

        return deleteTimeComponentFiles;
          
    }

    // 09-3
    //  Delete from ANA_TIMED_NODE 
    private void deleteANA_TIMED_NODE( ArrayList<ComponentFile> deleteTimedComponentFiles ){

        if (this.debug) {
            System.out.println("deleteANA_TIMED_NODE");
        }

        try {
            if ( !deleteTimedComponentFiles.isEmpty() ) {

            	for ( ComponentFile component: deleteTimedComponentFiles ){
                
            		ArrayList<TimedNode> timednodes = (ArrayList) timednodeDAO.listByNodeFK(Long.valueOf(component.getNamespace()));
            		
                  	Iterator<TimedNode> iteratortimednode = timednodes.iterator();

                  	while (iteratortimednode.hasNext()) {
                    
                  		TimedNode timednode = iteratortimednode.next();

                  		timednodeDAO.delete(timednode);
                  	}
            	}
            }
        }
        catch ( DAOException dao ) {
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
    }

    // 09-4
    //  Delete from ANA_OBJECT
    private void deleteANA_OBJECT( ArrayList<ComponentFile> deleteObjects, String calledFromTable ){
    	/*
    	 *  ANA_OBJECT - ALL Objects in the Anatomy DAG
    	 *  
         *  Columns:
         *   1. OBJ_OID               - int(10) unsigned 
         *   2. OBJ_CREATION_DATETIME - datetime         
         *   3. OBJ_CREATOR_FK        - int(10) unsigned 
         *   4. OBJ_TABLE             - varchar(255)
         *   5. OBJ_DESCRIPTION       - varchar(255)
    	 */

        if (this.debug) {
            System.out.println("09-4 deleteANA_OBJECT - for Deletes to ANA_OBJECT for Table " + calledFromTable);
        }

        try {
            if ( !deleteObjects.isEmpty() ){

            	for ( ComponentFile deleteObject: deleteObjects ){
            		
            		Thing thing = thingDAO.findByOid(Long.valueOf(deleteObject.getDBID())); 

            		thingDAO.delete(thing);
            		
                }
            }
        }
        catch ( DAOException dao ) {
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
    }
    
    // 13-1
    private void createDifferenceSynonyms( ArrayList<ComponentFile> diffSynonymTermList ){

        if (this.debug) {
            System.out.println("13-1 - createDifferenceSynonyms");
        }

        ComponentFile databasecomponent = new ComponentFile();
        ComponentFile deletesynonymcomponent = new ComponentFile();
        ComponentFile insertsynonymcomponent = new ComponentFile();        
        
        ArrayList<String> synonyms = new ArrayList<String>();
        ArrayList<String> deleteSynonyms = new ArrayList<String>();
        ArrayList<String> insertSynonyms = new ArrayList<String>();
        
        try {
            //for each component where parents have changed
            for(ComponentFile component: diffSynonymTermList){

            	ArrayList<Synonym> synonymlist = (ArrayList) synonymDAO.listByObjectFK(Long.valueOf(component.getDBID()));
            	
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
                deleteSynonyms.clear(); //reset for each databasecomponent to component comparison
                deleteSynonyms.addAll( databasecomponent.getSynonyms() );
                deleteSynonyms.removeAll( synonyms );
                
                if ( !deleteSynonyms.isEmpty() ){
                    deletesynonymcomponent = new ComponentFile();
                
                    ArrayList < String > copyDeleteSynonyms = new ArrayList<String>();
                    
                    copyDeleteSynonyms.addAll( deleteSynonyms );
                    
                    deletesynonymcomponent.setDBID( component.getDBID() );
                    deletesynonymcomponent.setName( component.getName() );
                    deletesynonymcomponent.setSynonyms( copyDeleteSynonyms );
                    
                    this.diffDeleteSynList.add( deletesynonymcomponent );
                }
                
                //get parents to be inserted
                //parents owned by component but not by databasecomponent
                insertSynonyms.clear(); //reset 
                insertSynonyms.addAll( synonyms );
                insertSynonyms.removeAll( databasecomponent.getSynonyms() );
                
                if ( !insertSynonyms.isEmpty() ){
                    insertsynonymcomponent = new ComponentFile();
                
                    ArrayList < String > copyInsertSynonyms = new ArrayList<String>();
                    
                    copyInsertSynonyms.addAll( insertSynonyms );
                    
                    insertsynonymcomponent.setDBID( component.getDBID() );
                    insertsynonymcomponent.setName( component.getName() );
                    insertsynonymcomponent.setSynonyms( copyInsertSynonyms );
                    
                    this.diffCreateSynList.add( insertsynonymcomponent );
                }
            }
        }
        catch ( DAOException dao ) {
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
    }

    // 13-2
    //  Insert into ANA_LOG for all Deleted Synonyms
    private ArrayList insertANA_LOG_deletedSyns( ArrayList<ComponentFile> diffDeleteSyns ){
    	/*
    	 *  ANA_Log - A Log of all Updates to the Anatomy Database
         *  
         *  Columns:
         *   1.  LOG_OID         => int(10) unsigned
         *   2.  LOG_LOGGED_OID  => int(10) unsigned
         *   3.  LOG_VERSION_FK  => int(10) unsigned
         *   4.  LOG_COLUMN_NAME => varchar(64)
         *   5.  LOG_OLD_VALUE   => varchar(255)
         *   6.  LOG_COMMENTS    => varchar(255)
         *   
    	 */

        if (this.debug) {
            System.out.println("13-2 - insertANA_LOG_deletedSyns");
        }
        
        ArrayList< ComponentFile > deleteSynComponentFiles = new ArrayList<ComponentFile>(); 
        HashMap<String, String> synOldValues = new HashMap(); 
        ArrayList < String > deleteSynonyms = new ArrayList<String>();

        int intLogLoggedOID = 0;
        int intLogOID = 0;
        
        //ANA_SYNONYM columns
        Vector<String> vSYNcolumns = new Vector();
        vSYNcolumns.add("SYN_OID");
        vSYNcolumns.add("SYN_OBJECT_FK");
        vSYNcolumns.add("SYN_SYNONYM");

        //column values for selection from ANA_SYNONYM
        int intSYN_OID = 0;
        int intSYN_OBJECT_FK = 0;
        String strSYN_SYNONYM = "";
        String strLOG_COMMENTS = "Insert into ANA_LOG for Deleted Synonyms";
        
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        int intLOG_VERSION_FK = this.intCurrentVersionID; //version_oid should be very first obj_oid created for easy tracing

   
        try {
        	//get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();

            if ( !diffDeleteSyns.isEmpty() ) {

            	for ( ComponentFile component: diffDeleteSyns ){

                    deleteSynonyms = component.getSynonyms();

                    for ( String deleteSynonym: deleteSynonyms ){

                        ArrayList<Synonym> synonymlist = (ArrayList) synonymDAO.listByObjectFKAndSynonym(Long.valueOf(component.getDBID()), deleteSynonym);
                        
                        //add to temporary component
                      	Iterator<Synonym> iteratorsynonym = synonymlist.iterator();

                      	while (iteratorsynonym.hasNext()) {
                      		Synonym synonym = iteratorsynonym.next();

                            ComponentFile deleteSynComponentFile = new ComponentFile();
                            
                            deleteSynComponentFile.setName( deleteSynonym );
                            deleteSynComponentFile.setDBID( Long.toString(synonym.getOid()) );
                            deleteSynComponentFile.setID( component.getDBID() );
                            deleteSynComponentFiles.add( deleteSynComponentFile );

                            //set record values
                            intSYN_OID = synonym.getOid().intValue();
                            intSYN_OBJECT_FK = Integer.parseInt( component.getDBID() );
                            strSYN_SYNONYM = deleteSynonym;

                            //increment for each relationship record
                            ++intLogLoggedOID;

                            //clear HashMap synOldValues
                            synOldValues.clear();
                            synOldValues.put("SYN_OID", Integer.toString(intSYN_OID) );
                            synOldValues.put("SYN_OBJECT_FK", Integer.toString(intSYN_OBJECT_FK) );
                            synOldValues.put("SYN_SYNONYM", strSYN_SYNONYM);

                            for (String columnName: vSYNcolumns){
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = synOldValues.get(columnName);

                                Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                                logDAO.save(log);
                            }     

                      	}
                    }
                }
            }
        }
        catch ( DAOException dao ) {
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
        
        return deleteSynComponentFiles;
      
    }

    // 13-3
    //  Delete from ANA_SYNONYM
    private void deleteANA_SYNONYM( ArrayList<ComponentFile> deleteSynComponentFiles ){

        if (this.debug) {
            System.out.println("13-3 - deleteANA_SYNONYM");
        }
        
        try {
            if ( !deleteSynComponentFiles.isEmpty() ) {
            	
                for ( ComponentFile deletesynonymcomponent: deleteSynComponentFiles ){
                	
                	Synonym synonym = synonymDAO.findByOid(Long.valueOf(deletesynonymcomponent.getDBID()));

                	synonymDAO.delete(synonym);
                }
            } 
        }
        catch ( DAOException dao ) {
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
    }

    //method to detect difference in parents between modified components and existing components in DB
    // 15-1
    private void createDifferenceParents( ArrayList<ComponentFile> diffParentTermList ){

        if (this.debug) {
            System.out.println("15-1 - createDifferenceParents");
        }

        ComponentFile databasecomponent = new ComponentFile();
        ComponentFile deleteRelCompie = new ComponentFile();
        ComponentFile insertRelCompie = new ComponentFile();        
        
        ArrayList<String> inputParents = new ArrayList<String>();
        ArrayList<String> inputParentTypes = new ArrayList<String>();
        ArrayList<String> dbParents = new ArrayList<String>();
        ArrayList<String> dbParentTypes = new ArrayList<String>();

        ArrayList<String> deleteParents = new ArrayList<String>();
        ArrayList<String> deleteParentTypes = new ArrayList<String>();
        ArrayList<String> insertParents = new ArrayList<String>();
        ArrayList<String> insertParentTypes = new ArrayList<String>();
        
        try{
            //for each component where parents have changed
            for(ComponentFile component: diffParentTermList){

            	//reset temporary component's parents for each component
                databasecomponent.setChildOfs( new ArrayList<String>() );
                databasecomponent.setChildOfTypes( new ArrayList<String>() );
                
                ArrayList<JOINNodeRelationship> joinnoderelationships = (ArrayList) joinnoderelationshipDAO.listAllByChild(Long.valueOf(component.getDBID()));

              	Iterator<JOINNodeRelationship> iteratorjoinnoderelationship = joinnoderelationships.iterator();

              	while (iteratorjoinnoderelationship.hasNext()) {
              		JOINNodeRelationship joinnoderelationship = iteratorjoinnoderelationship.next();

                    databasecomponent.addChildOf( joinnoderelationship.getPublicId());

                    if ( joinnoderelationship.getTypeFK().equals("part-of")) {
                        databasecomponent.addChildOfType("PART_OF");
                    }
                    if ( joinnoderelationship.getTypeFK().equals("is-a")) {
                        databasecomponent.addChildOfType("IS_A");
                    }
                    if ( joinnoderelationship.getTypeFK().equals("group-part-of")) {
                        databasecomponent.addChildOfType("GROUP_PART_OF");
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

                int inputParentsSize = inputParents.size();
                int inputParentTypesSize = inputParentTypes.size();
                int dbParentsSize = dbParents.size();
                int dbParentTypesSize = dbParentTypes.size();

                inputParents.clear();
                inputParentTypes.clear();
                dbParents.clear();
                dbParentTypes.clear();
                
                if ( ip.length >= dp.length ) {
                    //System.out.println("ip.length GE dp.length");
                    // More Input Parents than in Database
                    for (int i = 0; i <= dp.length - 1; i++){
                        for (int j = 0; j <= ip.length - 1; j++){
                            if ( ip[j].equals(dp[i]) && ipt[j].equals(dpt[i]) ) {
                                ip[j] = "";
                            }
                        }
                    }
                    for (int k = 0; k <= ip.length - 1; k++){
                        if (!ip[k].equals("")) {
                            inputParents.add( (String) ip[k]);
                            inputParentTypes.add( (String) ipt[k]);
                        }
                    }
                    for (int l = 0; l <= dp.length - 1; l++){
                        dbParents.add( (String) dp[l]);
                        dbParentTypes.add( (String) dpt[l]);
                    }
                }
                else {
                    //System.out.println("ip.length LT dp.length");
                    // More Parents in Database than Input
                    for (int i = 0; i <= dp.length - 1 ; i++){
                        for (int j = 0; j <= ip.length - 1; j++){
                            if (dp[i].equals(ip[j]) && dpt[i].equals(ipt[j])) {
                                dp[i] = "";
                            }
                        }
                    }
                    for (int k = 0; k <= dp.length - 1; k++){
                        if (!dp[k].equals("")) {
                            dbParents.add( (String) dp[k]);
                            dbParentTypes.add( (String) dpt[k]);
                        }
                    }
                    for (int l = 0; l <= ip.length - 1; l++){
                        inputParents.add( (String) ip[l]);
                        inputParentTypes.add( (String) ipt[l]);
                    }
                }

                //get parents to be deleted
                //parents owned by databasecomponent but not by component
                deleteParents.clear();
                deleteParents.addAll( inputParents );
                deleteParentTypes.clear();
                deleteParents.addAll( inputParentTypes );

                if ( !deleteParents.isEmpty() ){
                    deleteRelCompie = new ComponentFile();
                    ArrayList < String > copyDeleteParents =
                            new ArrayList<String>();
                    ArrayList < String > copyDeleteParentTypes =
                            new ArrayList<String>();

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
                insertParentTypes.clear();
                insertParents.addAll( inputParents );
                insertParentTypes.addAll( inputParentTypes );

                if ( !insertParents.isEmpty() ){
                    insertRelCompie = new ComponentFile();

                    ArrayList < String > copyInsertParents =
                            new ArrayList<String>();
                    ArrayList < String > copyInsertParentTypes =
                            new ArrayList<String>();

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
        catch ( DAOException dao ) {
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
    }

    // 15-2
    //  Insert into ANA_LOG for ANA_RELATIONSHIP Deletions
    private ArrayList insertANA_LOG_deletedRels( ArrayList<ComponentFile> diffDeleteRels ){
    	/*
    	 *  ANA_Log - A Log of all Updates to the Anatomy Database
         *  
         *  Columns:
         *   1.  LOG_OID         => int(10) unsigned
         *   2.  LOG_LOGGED_OID  => int(10) unsigned
         *   3.  LOG_VERSION_FK  => int(10) unsigned
         *   4.  LOG_COLUMN_NAME => varchar(64)
         *   5.  LOG_OLD_VALUE   => varchar(255)
         *   6.  LOG_COMMENTS    => varchar(255)
         *   
    	 */

        if (this.debug) {
            System.out.println("15-2 - insertANA_LOG_deletedRels");
        }
    
        ArrayList< ComponentFile > deleteRelComponentFiles = new ArrayList<ComponentFile>(); 
        HashMap<String, String> relOldValues = new HashMap(); 
        ArrayList < String > deleteParents = new ArrayList<String>();

        int intLogLoggedOID = 0;
        int intLogOID = 0;
        int intParentDBID = 0;
        
        //ANA_RELATIONSHIP columns
        Vector<String> vRELcolumns = new Vector();
        vRELcolumns.add("REL_OID");
        vRELcolumns.add("REL_RELATIONSHIP_TYPE_FK");
        vRELcolumns.add("REL_PARENT_FK");
        vRELcolumns.add("REL_CHILD_FK");
        
        //column values for selection from ANA_RELATIONSHIP
        int intREL_OID = 0;
        String strREL_RELATIONSHIP_TYPE_FK = "";
        int intREL_PARENT_FK = 0;
        int intREL_CHILD_FK = 0;
        
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        
        //version_oid should be very first obj_oid created for easy tracing
        int intLOG_VERSION_FK = this.intCurrentVersionID; 
        String strLOG_COMMENTS = "Insert into ANA_LOG for Deleted Relationships";
   
        try {
            //get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
        	
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();
      	  
            if ( !diffDeleteRels.isEmpty() ) {
            	for ( ComponentFile component: diffDeleteRels ){
                    deleteParents = component.getChildOfs();

                    for ( String deleteParent: deleteParents ){

                        intParentDBID = this.getDatabaseIdentifier( deleteParent ); 

                        ArrayList<Relationship> relationships = (ArrayList) relationshipDAO.listByParentFKAndChildFK((long) intParentDBID, Long.valueOf(component.getDBID()));
                        
                        if (relationships.size() == 1) {
                        	Relationship relationship = relationships.get(0);
                      	  
                            ComponentFile deleteRelComponentFile = new ComponentFile();

                            deleteRelComponentFile.setDBID( Long.toString(relationship.getOid()) );
                            deleteRelComponentFile.setID( component.getDBID() );
                            deleteRelComponentFile.addChildOf( Integer.toString( intParentDBID ) );

                            if ( relationship.getTypeFK().equals("part-of") ) {
                                deleteRelComponentFile.addChildOfType( "PART_OF" );
                            }
                            if ( relationship.getTypeFK().equals("derives-from") ) {
                                deleteRelComponentFile.addChildOfType( "DERIVES_FROM" );
                            }
                            if ( relationship.getTypeFK().equals("is-a") ) {
                                deleteRelComponentFile.addChildOfType( "IS_A" );
                            }
                            if ( relationship.getTypeFK().equals("group_part_of") ) {
                                deleteRelComponentFile.addChildOfType( "GROUP_PART_OF" );
                            }

                            deleteRelComponentFiles.add( deleteRelComponentFile );

                            intREL_OID = relationship.getOid().intValue();
                            strREL_RELATIONSHIP_TYPE_FK = relationship.getTypeFK();
                            intREL_PARENT_FK = relationship.getParentFK().intValue();
                            intREL_CHILD_FK = relationship.getChildFK().intValue();

                            //increment for each relationship record
                            ++intLogLoggedOID;

                            //clear HashMap relOldValues
                            relOldValues.clear();
                            relOldValues.put( "REL_OID", Integer.toString( intREL_OID ) );
                            relOldValues.put( "REL_RELATIONSHIP_TYPE_FK", strREL_RELATIONSHIP_TYPE_FK );
                            relOldValues.put( "REL_PARENT_FK", Integer.toString( intREL_PARENT_FK ) );
                            relOldValues.put( "REL_CHILD_FK", Integer.toString( intREL_CHILD_FK ) );

                            for (String columnName: vRELcolumns){
                          	  
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = relOldValues.get(columnName);

                                Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                                logDAO.save(log);
                            }     

                        }
                        else {
                        	// Throw Exception?
                        }
                        
                    }
                }
            }

        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 

        return deleteRelComponentFiles;
      
    }

    // 15-2-1
    private int getDatabaseIdentifier( String publicId ){

        if (this.debug) {
            System.out.println("15-2-1 - getDatabaseIdentifier");
        }

        try{
        	Node node = nodeDAO.findByPublicId(publicId);
        	
            if ( node != null ) {
                return node.getOid().intValue();
            }
            else {
                return 0;
            } 
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
        
        return 0;
    }

    // 15-3
    //  Delete from ANA_RELATIONSHIP
    private void deleteANA_RELATIONSHIP( ArrayList<ComponentFile> deleteRelComponentFiles ){

        if (this.debug) {
            System.out.println("15-3 - deleteANA_RELATIONSHIP");
        }
        
        try {
            if ( !deleteRelComponentFiles.isEmpty() ) {
                for ( ComponentFile deleteRelCompie: deleteRelComponentFiles ){
                	
                	ArrayList<Relationship> relationships = (ArrayList) relationshipDAO.listByParentFKAndChildFK(Long.valueOf(deleteRelCompie.getChildOfs().get(0)), Long.valueOf(deleteRelCompie.getID()));
                  	Iterator<Relationship> iteratorrelationship = relationships.iterator();

                  	while (iteratorrelationship.hasNext()) {
                  		Relationship relationship = iteratorrelationship.next();
                  		
                  		relationshipDAO.delete(relationship);
                  	}

                  	ArrayList<RelationshipProject> relationshipprojects = (ArrayList) relationshipprojectDAO.listByRelationshipFK(Long.valueOf(deleteRelCompie.getDBID()));
                  	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                  	while (iteratorrelationship.hasNext()) {
                  		RelationshipProject relationshipproject = iteratorrelationshipproject.next();
                  		
                  		relationshipprojectDAO.delete(relationshipproject);
                  	}
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 
    }

    // 17-1 
    private void update_orderANA_RELATIONSHIP( HashMap<String, ArrayList<String>> mapParentChildren){

        if (this.debug) {
            System.out.println("17-1 - update_orderANA_RELATIONSHIP");
        }


        String parent = "";
        int parentDBID = -1;
        int childDBID = -1;
        int intREL_OID = -1;
        int intSEQ = -1;
        int intNumRecords = 0;

        ArrayList<String> orderedchildren = new ArrayList<String>();

        try {
            if ( !mapParentChildren.isEmpty() ) {
                //for each entry in the map
                for ( Iterator<String> i = mapParentChildren.keySet().iterator(); i.hasNext(); ){
                    parent = i.next();
                    //get dbid of parent
                    parentDBID = this.getDatabaseIdentifier(parent);
                    //reset order for each parent
                    intSEQ = -1; 
                    //reset number of child records for each parent
                    intNumRecords = 0; 

                    //get number of child records for each parent from database
                    ArrayList<Relationship> relationships = (ArrayList) relationshipDAO.listByParentFK(Long.valueOf(parentDBID));
                    intNumRecords = relationships.size();
                    
                    //iterate through all children for each parent
                    //if children==null, no child has an order in the proposed file
                    //update all relationship entries to sequence=null
                    if ( mapParentChildren.get(parent) == null ){
                    	
                      	Iterator<Relationship> iteratorrelationship = relationships.iterator();

                      	while (iteratorrelationship.hasNext()) {
                      		Relationship relationship = iteratorrelationship.next();

                            intREL_OID = relationship.getOid().intValue();
                            
                            ArrayList<RelationshipProject> relationshipprojects = (ArrayList) relationshipprojectDAO.listByRelationshipFK((long) intREL_OID); 

                          	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                          	while (iteratorrelationshipproject.hasNext()) {
                          		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                          		relationshipproject.setSequence((long) 0);
                          		relationshipprojectDAO.save(relationshipproject);
                          	}
                      	}
                    }
                    else {
                        for (String child: mapParentChildren.get(parent)){
                            //get dbid of child
                            childDBID = this.getDatabaseIdentifier(child);
                            //get rel_oid of this relationship
                            ArrayList<Relationship> relationshipsparentchild = (ArrayList) relationshipDAO.listByParentFKAndChildFK(Long.valueOf(parentDBID), Long.valueOf(childDBID)); 

                          	Iterator<Relationship> iteratorrelationshipparentchild = relationshipsparentchild.iterator();

                          	while (iteratorrelationshipparentchild.hasNext()) {
                          		Relationship relationship = iteratorrelationshipparentchild.next();
                          	
                          		intREL_OID = relationship.getOid().intValue();

                                //put in ordered children rows cache for later comparison
                                orderedchildren.add( Integer.toString(intREL_OID) );
                                intSEQ++;
                                
                                ArrayList<RelationshipProject> relationshipprojects = (ArrayList) relationshipprojectDAO.listByRelationshipFK((long) intREL_OID); 

                              	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                              	while (iteratorrelationshipproject.hasNext()) {
                              		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                              		relationshipproject.setSequence((long) intSEQ);
                              		relationshipprojectDAO.save(relationshipproject);
                              	}

                                
                          	}
                          	/*
                            else { //if no relationship record found, child component is a new component
                                intSEQ++; //skip this sequence number
                                //entry will be inserted into ana_relationship/project later for new components
                            }
                            */
                        }
                    }
                    
                    //if there are more child records than the number of ordered children
                    // fill up the rest with rel_sequence = null
                    if ( orderedchildren.size()<intNumRecords ){
                        //prepare string of ordered rel_oid
                        String strOrdered = "";
                        
                        for(String orderedchild: orderedchildren ){
                            strOrdered = strOrdered + orderedchild + ",";
                        }
                        //get all relationship entries that are unordered
                        ArrayList<Relationship> relationshipsunordered = (ArrayList) relationshipDAO.listByParentFK(Long.valueOf(parentDBID));
                        
                      	Iterator<Relationship> iteratorrelationship = relationshipsunordered.iterator();

                      	while (iteratorrelationship.hasNext()) {
                      		Relationship relationship = iteratorrelationship.next();
                      		
                      		intREL_OID = relationship.getOid().intValue();

                            ArrayList<RelationshipProject> relationshipprojects = (ArrayList) relationshipprojectDAO.listByRelationshipFK((long) intREL_OID); 

                          	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                          	while (iteratorrelationshipproject.hasNext()) {
                          		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                          		relationshipproject.setSequence((long) 0);
                          		relationshipprojectDAO.save(relationshipproject);
                          	}

                      	}
                    }
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 

    }

    // 18-1
    public Vector< String > recursiveGetDependentDescendants(String componentID, Vector< String > componentIDs, boolean invalidDelete){

        if (this.debug) {
            System.out.println("18-1 - recursiveGetDependentDescendants");
        }

        Vector< String > descendants = componentIDs;
        Vector< String > childrenIDs = new Vector<String>();

        boolean isPrimary = false;
        
        ComponentFile component = new ComponentFile();
        ComponentFile deletedcomponent = new ComponentFile();
        
        try {
            ArrayList<JOINNodeRelationshipNode> joinnoderelationshipnodes = (ArrayList) joinnoderelationshipnodeDAO.listAllByParentId(componentID);
          	Iterator<JOINNodeRelationshipNode> iteratorjoinnoderelationshipnode = joinnoderelationshipnodes.iterator();

          	while (iteratorjoinnoderelationshipnode.hasNext()) {
          		JOINNodeRelationshipNode joinnoderelationshipnode = iteratorjoinnoderelationshipnode.next();
          	
          		childrenIDs.add( joinnoderelationshipnode.getAPublicId());
          		
          	}

            if ( childrenIDs.isEmpty() ){
                /*
                System.out.println("No Children!");
                */
                return descendants;
            }
            else {
                for (String s: childrenIDs){
                    /*
                      check to see whether all dependent descendants have been
                       specified for deletion
                    */
                    component = this.tree.getComponent(s);
                    /*
                     System.out.println("Child Encountered: " + component.getDBID());
                       if component is not found in tree OR component is found
                         but is not scheduled for deletion
                     if ( component==null ||
                       !component.getStatusChange().equals("DELETED") ) {
                    */
                    if ( component==null ) {
                    	/*
                        System.out.println("Invalid Deletion of Children " +
                            "Encountered: " + s + ": component == null");
                        */
                        invalidDelete = true;
                        deletedcomponent = this.tree.getComponent( s );
                        
                        deletedcomponent.setCheckComment("Delete Record " +
                            "Warning: Deletion of this term results in " +
                            "orphan term " + component.getID() + " " +
                            component.getName() + ". Please generate a new OBO " +
                            "file from the database and retry deletion.");

                        this.unDeletedCompList.add(deletedcomponent);

                    }
                    else {
                        if ( !component.getStatusChange().equals("DELETED") ) {
                        	/*
                            System.out.println("Invalid Deletion of Children " +
                                "Encountered: " + component.getID() +
                                " Status != DELETED; " + " Status = " +
                                component.getStatusChange());
                           */
                            component.setStatusChange("DELETED");
                            invalidDelete = true;
                            deletedcomponent = this.tree.getComponent( s );

                            deletedcomponent.setCheckComment("Delete Record " +
                                "Warning: Deletion of this term results in " +
                                "orphan term " + component.getID() + " " +
                                component.getName() + ". Please generate a new " +
                                "OBO file from the database and retry " +
                                "deletion.");

                            this.unDeletedCompList.add(deletedcomponent);

                        }
                    }
                    //System.out.println("Add to descendants");
                    descendants.add( s );
                    //System.out.println("Recursive call of recursiveGetDependentDescendants");
                    descendants = recursiveGetDependentDescendants( s, descendants, invalidDelete );
                }
            }
        }
        catch ( DAOException dao ) {
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	ex.printStackTrace();
        } 

        return descendants;
        
    } 

    // Helpers ------------------------------------------------------------------------------------
    /*
     * method to sort through modified component list for changed stages
     */
    // 08
    private ArrayList< ComponentFile > getChangedStagesTermList( ArrayList<ComponentFile> changedTermList ){

        System.out.println("08 - getChangedStagesTermList");
        
        ArrayList < ComponentFile > termList = new ArrayList<ComponentFile>();
        
        for ( ComponentFile component: changedTermList ){
        
        	if ( component.hasDifferenceComment("Different Start Stage") || 
                 component.hasDifferenceComment("Different End Stage") )
            
        		termList.add( component );
        }
        
        return termList;
    }

    /*
     * method to sort through modified component list for changed names
     */
    // 10
    private ArrayList< ComponentFile > getChangedNamesTermList( ArrayList<ComponentFile> changedTermList ){

        System.out.println("10 - getChangedNamesTermList");
        
        ArrayList < ComponentFile > termList = new ArrayList<ComponentFile>();
        
        for ( ComponentFile component: changedTermList ){
        
        	if ( component.hasDifferenceComment("Different Name") ) {
            
        		termList.add( component );
            }
        }
        
        return termList;
    }
    
    /*
     * method to sort through modified component list for changed synonyms
     */
    // 12
    private ArrayList< ComponentFile > getChangedSynonymsTermList( ArrayList<ComponentFile> changedTermList ){

        System.out.println("12 - getChangedSynonymsTermList");
        
        ArrayList < ComponentFile > termList = new ArrayList<ComponentFile>();
        
        for ( ComponentFile component: changedTermList ){
        
        	if ( component.hasDifferenceComment("Different Synonyms") ) {
            
        		termList.add( component );
            }
        }
        
        return termList;
    }

    /*
     * method to sort through modified component list for changed parents
     */
    // 14
    private ArrayList< ComponentFile > getChangedParentsTermList( ArrayList<ComponentFile> changedTermList ){

        System.out.println("14 - getChangedParentsTermList");
        
        ArrayList < ComponentFile > termList = new ArrayList<ComponentFile>();
        
        for ( ComponentFile component: changedTermList ){
        
        	if ( component.hasDifferenceComment("Different Parents") ) {
            
        		termList.add( component );
            }
        	
            if ( component.hasDifferenceComment("Different Group Parents") ) {
            
            	termList.add( component );
            }
        }
        
        return termList;
    }

    /*
     * method to sort through modified component list for changed synonyms
     */
    // 15.25
    private ArrayList< ComponentFile > getChangedPrimaryStatusTermList( ArrayList<ComponentFile> changedTermList ){

        System.out.println("15.25 - getChangedPrimaryStatusTermList");
        
        ArrayList < ComponentFile > termList = new ArrayList<ComponentFile>();
        
        for ( ComponentFile component: changedTermList ){
        
        	if ( component.hasDifferenceComment("Different Primary Status") ) {
            
        		termList.add( component );
            }
        }
        
        return termList;
    }

    /*
     * method to sort through modified component list for changed synonyms
     */
    // 16
    private ArrayList< ComponentFile > getChangedOrderTermList( ArrayList<ComponentFile> changedTermList ){

        System.out.println("16 - getChangedOrderTermList");

        ArrayList < ComponentFile > termList = new ArrayList<ComponentFile>();
        
        for ( ComponentFile component: changedTermList ){
        
        	if ( component.hasDifferenceComment("Different Order") ) {
            
        		termList.add( component );
            }
        }
        
        return termList;
    }

}
