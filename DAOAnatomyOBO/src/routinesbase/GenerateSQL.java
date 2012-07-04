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

package routinesbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daolayer.JOINNodeRelationshipDAO;
import daolayer.JOINNodeRelationshipNodeDAO;
//import daolayer.JOINNodeRelationshipRelationshipProjectDAO;
//import daolayer.JOINRelationshipProjectRelationshipDAO;
import daolayer.JOINTimedNodeStageDAO;
import daolayer.LogDAO;
import daolayer.NodeDAO;
import daolayer.RelationshipDAO;
import daolayer.RelationshipProjectDAO;
import daolayer.StageDAO;
import daolayer.SynonymDAO;
import daolayer.ThingDAO;
import daolayer.TimedNodeDAO;
import daolayer.VersionDAO;
import daolayer.ComponentDAO;
import daolayer.ComponentOrderDAO;
import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentSynonymDAO;
import daolayer.ComponentAlternativeDAO;
import daolayer.ComponentCommentDAO;

import daomodel.Component;
import daomodel.ComponentOrder;
import daomodel.ComponentRelationship;
import daomodel.ComponentSynonym;
import daomodel.ComponentAlternative;
import daomodel.ComponentComment;
import daomodel.JOINNodeRelationship;
import daomodel.JOINNodeRelationshipNode;
//import daomodel.JOINNodeRelationshipRelationshipProject;
//import daomodel.JOINRelationshipProjectRelationship;
import daomodel.JOINTimedNodeStage;
import daomodel.Log;
import daomodel.Node;
import daomodel.Relationship;
import daomodel.RelationshipProject;
import daomodel.Stage;
import daomodel.Synonym;
import daomodel.Thing;
import daomodel.TimedNode;
import daomodel.Version;

import obolayer.OBOFactory;

import obomodel.OBOComponent;


public class GenerateSQL {
	// Properties ---------------------------------------------------------------------------------
    
	// A flag to print comments to System.out
	boolean debug; 
	
    //term list to be updated and term list to refer to
    //private ArrayList<OBOComponent> proposedTermList; 
    
    //file properties
    private String strSpecies = "";
    private String project = "";
    
    //treebuilder object to use hashmaps to get component (getComponent)
    private TreeBuilder tree; 
    
    //term list for timed components
    private ArrayList<OBOComponent> timedCompList;

    //term list for synonym components
    private ArrayList<OBOComponent> synonymCompList;
    
    //term list for disallowed deleted components 
    // 1 - not found in db
    // 2 - is primary and have undeleted children
    private ArrayList<OBOComponent> unDeletedCompList;
    
    //term list for disallowed modified components
    // 1 - not found in db
    private ArrayList<OBOComponent> unModifiedCompList;
    
    //term list for temporary terms created for larger stage ranges from
    // modified components
    private ArrayList<OBOComponent> diffCreateTimedCompList;
    
    //term list for timed components to be deleted for smaller stage ranges
    // from modified components
    private ArrayList<OBOComponent> diffDeleteTimedCompList;
    
    //term list for relationships to be created for changed parents from
    // modifed components
    private ArrayList<OBOComponent> diffCreateRelList;
    
    //term list for relationships to be created for deleted parents from
    // modified components
    private ArrayList<OBOComponent> diffDeleteRelList;
    
    //term list for synonyms to be created for changed synonyms from modifed
    // components
    private ArrayList<OBOComponent> diffCreateSynList;
    
    //term list for synonyms to be created for deleted synonyms from modified
    // components
    private ArrayList<OBOComponent> diffDeleteSynList;
    
    //maximum public id
    private int intCurrentVersionID;
    private int intCurrentPublicID;
    private int intCurrentObjectID;
    
    //check whether was processed all the way
    private boolean processed;
    
    //abstract class configuration
    private OBOComponent abstractclassobocomponent;
    
    //Data Access Object Factory
    //private DAOFactory daofactory;
    
    //Data Access Objects (DAOs)
    private LogDAO logDAO;
    private NodeDAO nodeDAO; 
    private RelationshipDAO relationshipDAO;
    private RelationshipProjectDAO relationshipprojectDAO;
    private StageDAO stageDAO;
    private SynonymDAO synonymDAO; 
    private ThingDAO thingDAO;
    private TimedNodeDAO timednodeDAO; 
    private VersionDAO versionDAO;
    private JOINNodeRelationshipDAO joinnoderelationshipDAO;
    private JOINNodeRelationshipNodeDAO joinnoderelationshipnodeDAO; 
    //private JOINNodeRelationshipRelationshipProjectDAO joinnoderelationshiprelationshipprojectDAO;
    //private JOINRelationshipProjectRelationshipDAO joinrelationshipprojectrelationshipDAO;
    private JOINTimedNodeStageDAO jointimednodestageDAO;
    private ComponentDAO componentDAO;
    private ComponentAlternativeDAO componentalternativeDAO;
    private ComponentRelationshipDAO componentrelationshipDAO;
    private ComponentOrderDAO componentorderDAO;
    private ComponentSynonymDAO componentsynonymDAO;
    private ComponentCommentDAO componentcommentDAO;
    
    // Constructors -------------------------------------------------------------------------------
    public GenerateSQL(DAOFactory daofactory, 
    		OBOFactory obofactory, 
            ArrayList<OBOComponent> proposedTermList,
            TreeBuilder treebuilder,
            TreeBuilder refTreebuilder ) throws Exception {

    	processed = true;
    	//this.daofactory = daofactory;
        debug = daofactory.getThingDAO().debug();
        
        if (debug) {
        	
            System.out.println("===========");
            System.out.println("GenerateSQL - Constructor");
            System.out.println("===========");
        }
        
        //this.proposedTermList = proposedTermList;
        
        //System.out.println("-- proposedTermList.size() " + proposedTermList.size() + " --");

        timedCompList = new ArrayList<OBOComponent>();
        synonymCompList = new ArrayList<OBOComponent>();
        unDeletedCompList = new ArrayList<OBOComponent>();
        unModifiedCompList = new ArrayList<OBOComponent>();
        diffCreateTimedCompList = new ArrayList<OBOComponent>();
        diffDeleteTimedCompList = new ArrayList<OBOComponent>();
        diffCreateRelList = new ArrayList<OBOComponent>();
        diffDeleteRelList = new ArrayList<OBOComponent>();
        diffCreateSynList = new ArrayList<OBOComponent>();
        diffDeleteSynList = new ArrayList<OBOComponent>();

        tree = treebuilder;
        strSpecies = obofactory.getComponentOBO().species();
        intCurrentPublicID = 0;
        intCurrentObjectID = 0;
        
    	// set abstract class parameters
        abstractclassobocomponent = new OBOComponent();

        // 1: set abstract class parameters
        abstractclassobocomponent.setName( "Abstract anatomy" );
        abstractclassobocomponent.setID( "EMAPA:0" );
        abstractclassobocomponent.setNamespace( "abstract_anatomy" );
        project = obofactory.getComponentOBO().project();

        //internal termlists for data manipulation
        ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();

        ArrayList<OBOComponent> deletedComponents = new ArrayList<OBOComponent>();
        
        ArrayList<OBOComponent> changedComponents = new ArrayList<OBOComponent>();
        
        //construct internal arraylists
        OBOComponent component = new OBOComponent();
        
        for (int i = 0; i<proposedTermList.size(); i++) {

        	component = proposedTermList.get(i);

            if ( component.getStatusChange().equals("NEW") ) {
            	
                if ( component.getStatusRule().equals("FAILED") ) {
                	
                    if (debug) {
                    	
                        System.out.println(
                                "--SQL queries for New OBOComponent " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!" );
                    }
                    
                    setProcessed(false);
                }
                else if ( component.getStatusRule().equals("PASSED") ) {
                	
                    setProcessed(true);
                }
                else {
                	
                    System.out.println("UNKNOWN Component getStatusRule Value = " + component.getStatusRule());
                    setProcessed(false);
                }
                
                newComponents.add( component );
            }
            else if ( component.getStatusChange().equals("DELETED") ) {
            	
            	if ( component.getStatusRule().equals("FAILED") ) {
            		
                    if (debug) {
                    	
                        System.out.println( 
                                "--SQL queries for Deleted OBOComponent " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!");
                    }
                    
                    setProcessed(false);
                }
                else if ( component.getStatusRule().equals("PASSED") ) {
                	
                    setProcessed(true);
                }
                else {
                	
                    System.out.println("UNKNOWN Component getStatusRule Value = " + component.getStatusRule());
                    setProcessed(false);
                }
            	
                deletedComponents.add( component );
            }
            else if ( component.getStatusChange().equals("CHANGED") ) {
            	
                if ( component.getStatusRule().equals("FAILED") ) {
                	
                    if (debug) {
                    	
                        System.out.println( 
                                "--SQL queries for Changed OBOComponent " +
                                component.getID() + " " + component.getName() +
                                " with rule violation have been generated!");
                    }
                    
                    setProcessed(false);
                }
                else if ( component.getStatusRule().equals("PASSED") ) {
                	
                	/*
                	if (component.getID().equals("EMAPA:18305")) {
                        System.out.println("GenerateSQL.java");
                        System.out.println("----------------");
                        System.out.println("Changed component detected: " + component.getID());
                        System.out.println("component");
                        System.out.println("component.toString() " + component.toString());
                        System.out.println("component.getCheckComments() " + component.getCheckComments());
                        System.out.println("----------------");
                	}
                	*/
                    setProcessed(true);
                }
                else {
                	
                    System.out.println("UNKNOWN Component getStatusRule Value = " + component.getStatusRule());
                    setProcessed(false);
                }
 
                changedComponents.add( component );
            }
            else if ( !component.getStatusChange().equals("UNCHANGED") ) {

            	if ( component.getID().equals("group_term")) {
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("Tmp_new_group") ) { 
                    setProcessed(true);
            	}
            	else if (component.getID().equals("EMAPA:0") ) {
                    setProcessed(true);
            	}
            	else if (component.getID().equals("EMAPA:25765") ) {
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS:0") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS01") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS02") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS03") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS04") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS05") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS06") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS07") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS08") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS09") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS10") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS11") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS12") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS13") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS14") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS15") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS16") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS17") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS18") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS19") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS20") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS21") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS22") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS23") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS24") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS25") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS26") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS27") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("TS28") ) { 
                    setProcessed(true);
            	}
            	else if ( component.getID().equals("CS01")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS02")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS03")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS04")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS05a")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS05b")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS05c")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS06a")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS06b")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS07")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS08")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS09")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS10")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS11")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS12")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS13")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS14")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS15")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS16")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS17")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS18")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS19")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS20")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS21")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS22")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("CS23")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-I")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-II")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-III")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-IV")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-V")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-VI")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-VII")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-VIII")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-IX")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-X")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-XI")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-XII")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-XIII")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("EGK-XIV")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH02")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH03")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH04")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH05")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH06")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH07")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH08")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH09")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH10")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH11")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH12")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH13")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH14")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH15")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH16")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH17")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH18")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH19")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH20")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH21")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH22")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH23")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH24")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH25")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH26")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH27")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH28")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH29")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH30")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH31")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH32")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH33")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH34")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH35")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH36")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH37")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH38")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH39")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH40")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH41")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH42")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH43")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH44")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH45")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH46")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH47")) {
            		setProcessed(true);
            	}
            	else if ( component.getID().equals("HH48")) {
            		setProcessed(true);
            	}
            	else {
                	System.out.println("UNKNOWN Component StatusChange Value = " + component.getStatusChange());
                    System.out.println("component.toString() = " + component.toString());
                    setProcessed(false);
            	}
            }
        }

        if (processed) {
        	
        	// Obtain DAOFactory.
        	//this.daofactory = daofactory;

            // Obtain DAOs.
            logDAO = 
            		daofactory.getLogDAO();
            nodeDAO = 
            		daofactory.getNodeDAO();
            relationshipDAO = 
            		daofactory.getRelationshipDAO();
            relationshipprojectDAO = 
            		daofactory.getRelationshipProjectDAO();
            stageDAO = 
            		daofactory.getStageDAO();
            synonymDAO = 
            		daofactory.getSynonymDAO();
            thingDAO = 
            		daofactory.getThingDAO();
            timednodeDAO = 
            		daofactory.getTimedNodeDAO();
            versionDAO = 
            		daofactory.getVersionDAO();
            joinnoderelationshipDAO = 
            		daofactory.getJOINNodeRelationshipDAO();
            joinnoderelationshipnodeDAO = 
            		daofactory.getJOINNodeRelationshipNodeDAO();
            /*
            joinnoderelationshiprelationshipprojectDAO = 
            		daofactory.getJOINNodeRelationshipRelationshipProjectDAO();
            joinrelationshipprojectrelationshipDAO = 
            		daofactory.getJOINRelationshipProjectRelationshipDAO();
            */
            jointimednodestageDAO = 
            		daofactory.getJOINTimedNodeStageDAO();
            componentDAO = 
            		daofactory.getComponentDAO();
            componentalternativeDAO =
            		daofactory.getComponentAlternativeDAO();
            componentrelationshipDAO = 
            		daofactory.getComponentRelationshipDAO();
            componentorderDAO = 
            		daofactory.getComponentOrderDAO();
            componentsynonymDAO = 
            		daofactory.getComponentSynonymDAO();
            componentcommentDAO = 
            		daofactory.getComponentCommentDAO();

            // 01
            //set version id
            initialiseVersionID();
            
            // 02
            //set a version record in ANA_VERSION for this update
            insertANA_VERSION( intCurrentVersionID, newComponents,
                    deletedComponents, changedComponents );

            // AA
            //  INSERT components
            inserts( newComponents );

            // BB
            //  AMENDED components
            updates( changedComponents );
            
            // CC
            //  DELETED components
            deletes( deletedComponents );

            // DD
            //  rebuild ANA_RELATIONSHIP_PROJECT
            rebuildANA_RELATIONSHIP_PROJECT();

            setProcessed( true );
        }
    }
    
    
    // Getters ------------------------------------------------------------------------------------
    public boolean isProcessed() {
    	
        return processed;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
    	
        this.processed = processed;
    }


    // Methods ------------------------------------------------------------------------------------
    // 01
    private void initialiseVersionID() {

        if (debug) {
        	
            System.out.println("01 - initialiseVersionID");
        }

        //first obj_oid in ana_object for all updates
        //obj_oid > version_fk = all records related to this update
        intCurrentVersionID = getMaxObjectID();
        intCurrentVersionID++;
        
    }

    // 02
    //  Insert a new row into ANA_VERSION
    private void insertANA_VERSION( int objectID, 
    		ArrayList<OBOComponent> newTermList, 
    		ArrayList<OBOComponent> delTermList, 
    		ArrayList<OBOComponent> modTermList ) {
    	/*
         *  Columns:
         *   1. VER_OID      - int(10) unsigned
         *   2. VER_NUMBER   - int(10) unsigned
         *   3. VER_DATE     - datetime
         *   4. VER_COMMENTS - varchar(2000)
    	 */
        if (debug) {
        	
            System.out.println("02 - insertANA_VERSION");
        }

        int intVersionEntries = 0;

        try {
        	
           if ( !( newTermList.isEmpty() && delTermList.isEmpty() && modTermList.isEmpty() ) ) {
               
        	   //insert into ANA_OBJECT
               OBOComponent vercomponent = new OBOComponent();
               ArrayList< OBOComponent > verTermList = new ArrayList<OBOComponent>();
               verTermList.add( vercomponent );
               
               insertANA_OBJECT( verTermList, "ANA_VERSION" );
               
               //find out which round of update this is to the db
        	   intVersionEntries = versionDAO.countAll();
        	   
               //prepare values for insertion
               int intVER_OID = intCurrentVersionID;
               int intVER_NUMBER = ++intVersionEntries;
               String strVER_DATE = utility.MySQLDateTime.now();
               String strVER_COMMENTS = "DB2OBO Update - Editing the ontology";

               Version version = new Version((long) intVER_OID, (long) intVER_NUMBER, strVER_DATE, strVER_COMMENTS);

               versionDAO.create(version);
           }
           else {
        	   
        	   if (debug) {
            	
        		   System.out.println("-- No record inserted: Database Update " +
                           "did not occur because DB2OBO failed to detect any " +
                           "changes in the OBO File --");
        	   }

        	   setProcessed(false);
           }
        } 
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    } 

    // AA
    //  Wrapper Routine for ALL INSERTS to the database
    private void inserts( ArrayList<OBOComponent> newComponents ) {

    	if (debug) {
    		
            System.out.println("== - -------");
            System.out.println("AA - inserts");
            System.out.println("== - -------");
        }
        
        //System.out.println("newComponents.size() " + newComponents.size());

        if (newComponents.size() > 0) {
            // 03
            insertANA_NODE(newComponents);
            
            // 04
            insertANA_RELATIONSHIP(newComponents, "NEW");
            
            // 05
            insertANA_TIMED_NODE(newComponents, "NEW");
            
            // 06
            insertANA_SYNONYM(newComponents, "NEW");
        }
    }
    
    // BB
    //  Wrapper Routine for ALL UPDATES to the database
    private void updates( ArrayList<OBOComponent> changedComponentsIn ) {
    	
    	if (debug) {
    		
            System.out.println("== - -------");
            System.out.println("BB - updates");
            System.out.println("== - -------");
        }
    	
        //System.out.println("changedComponentsIn.size() " + changedComponentsIn.size());

        if (changedComponentsIn.size() > 0) {
            //modify components, set DBIDs and get only components that have dbids based on emap id
            // 07
        	ArrayList<OBOComponent> changedComponents = (ArrayList<OBOComponent>) setDBIDs(changedComponentsIn);
            
            //get components whose stage ranges have changed
            // 08
        	ArrayList<OBOComponent> changedPropComponents = getChangedStagesTermList( changedComponents );
            
            //perform insertion and deletion for modified stage ranges
            // 09
            updateStages( changedPropComponents );
            
            //get components whose names have changed
            // 10
            changedPropComponents = getChangedNamesTermList( changedComponents );
            
            //perform update for modified names
            // 11
            updateANA_NODE( changedPropComponents );
            
            //get components whose synonyms have changed
            // 12
            changedPropComponents = getChangedSynonymsTermList( changedComponents );
            
            //perform insertion and deletion for modified synonyms
            // 13
            updateSynonyms( changedPropComponents );
            
            //get components whose parents have changed
            // 14
            changedPropComponents = getChangedParentsTermList( changedComponents );
            
            //perform insertion and deletion for modified parent relationships
            // 15
            updateParents( changedPropComponents );
            
            //get components whose primary status have changed
            changedPropComponents = getChangedPrimaryStatusTermList( changedComponents );
            //perform update for modified primary status
            // 15.5
            updateANA_NODE_primary( changedPropComponents );
            
            //get components whose ordering have changed
            // 16
            //changedPropComponents = getChangedOrderTermList( changedComponents );

            //perform reordering
            // 17
            //updateOrder( changedPropComponents );

            //get components whose have NEW parents 
            // 18
            ArrayList<OBOComponent> newPropComponents = getNewParentsTermList( changedComponents );
            
            //System.out.println("newPropComponents.size() = " + newPropComponents.size());
            // 04
            insertANA_RELATIONSHIP(newPropComponents, "OLD");
            
        }
    }
    
    // CC
    //  Wrapper Routine for ALL DELETES to the database
    private void deletes( ArrayList<OBOComponent> deletedComponentsIn ) {
    	
    	if (debug) {
    		
            System.out.println("== - -------");
            System.out.println("CC - deletes");
            System.out.println("== - -------");
        }
        
        //System.out.println("deletedComponentsIn.size() " + deletedComponentsIn.size());

        if (deletedComponentsIn.size() > 0) {
            /*
            delete components
             delete components, set DBIDs and get only components that have
             dbids based on emap id
            deletedComponents = setDBIDs(deletedComponents);
            CRITICAL DELETION VALIDATION: to disallow deletion of components that do have children in database
            1. check that term exists in database
            2. if term = primary, check that all descendants are due for deletion in obo file as well
            3. if one descendant specified in database is not found in OBO file 
               OR descendant is found but not specified for deletion, 
            4. pass on invalid term to unDeletedCompList
            pass valid terms to validDeleteComponents
            validDeletedComponents = validateDeleteTermList( deletedComponents );
            insert log records for deleted components
            insertANA_LOG( validDeletedComponents );
            perform deletion on valid deletion term list
            deleteComponentFromTables( validDeletedComponents );
            reorder siblings of deleted components that have order
            reorderANA_RELATIONSHIP( validDeletedComponents, project );
            report for invalid delete term list that have not been deleted
            reportDeletionSummary(deletedComponents, validDeletedComponents, unDeletedCompList);
            */

            // delete components, set DBIDs and get only components that have dbids based on emap id
            // 07
        	ArrayList<OBOComponent> deletedComponents = (ArrayList<OBOComponent>) setDBIDs(deletedComponentsIn);

            //CRITICAL DELETION VALIDATION: to disallow deletion of components that do have children in database
            //1. check that term exists in database
            //2. if term = primary, check that all descendants are due for deletion in obo file as well
            //3. if one descendant specified in database is not found in OBO file
            //   OR descendant is found but not specified for deletion,
            //4. pass on invalid term to unDeletedCompList
            
            //pass valid terms to validDeleteComponents
            // 18
        	ArrayList<OBOComponent> validDeletedComponents = validateDeleteTermList( deletedComponents );
            
            //insert log records for deleted components
            // 19
            insertANA_LOG( validDeletedComponents );
            
            //perform deletion on valid deletion term list
            // 20
            deleteComponentFromTables( validDeletedComponents );
            
            //reorder siblings of deleted components that have order
            // 21
            //reorderANA_RELATIONSHIP( validDeletedComponents, project );
    	}
    }
    
    
    // 03
    //  Insert new rows into ANA_NODE
    private void insertANA_NODE( ArrayList<OBOComponent> newTermList ) {
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
        if (debug) {
        	
            System.out.println("03 - insertANA_NODE");
        }
        
        int intANO_OID = 0;
        String strANO_SPECIES_FK = strSpecies;
        String strANO_COMPONENT_NAME = "";
        boolean boolANO_IS_PRIMARY = true;
        boolean boolANO_IS_GROUP = false;
        String strANO_PUBLIC_ID = "";
        String strANO_DESCRIPTION = "";

        OBOComponent component;

        try {
        	
        	//get current max public id
        	// 03-1
        	intCurrentPublicID = getMaxPublicID();

        	if ( !newTermList.isEmpty() ) {

        		//insert into ANA_OBJECT first
                insertANA_OBJECT(newTermList, "ANA_NODE");
                
                for (int i = 0; i< newTermList.size(); i++) {

                   component = newTermList.get(i);

                   //prepare values
                   // Column 1
                   intANO_OID = Integer.parseInt(component.getDBID());
                   // Column 2
                   strANO_SPECIES_FK = strSpecies;
                   // Column 3
                   strANO_COMPONENT_NAME = component.getName();
                 
                   // Column 4 && Column 5
                   if (component.getIsPrimary() ) {
                	   
                	   boolANO_IS_PRIMARY = true;
                	   boolANO_IS_GROUP = false;
                   }
                   else {
                	   
                	   boolANO_IS_PRIMARY = false;
                	   boolANO_IS_GROUP = true;
                   }
                   
                   // Column 6
                   if (strSpecies.equals("mouse")) {
                	   
                	   strANO_PUBLIC_ID = "EMAPA:" + 
                               Integer.toString( ++intCurrentPublicID );
                   }
                   else if (strSpecies.equals("chick")) {
                	   
                	   strANO_PUBLIC_ID = "ECAPA:" + 
                               Integer.toString( ++intCurrentPublicID );
                   }
                   else if (strSpecies.equals("human")) {
                	   
                	   strANO_PUBLIC_ID = "EDHAA:" + 
                               Integer.toString( ++intCurrentPublicID );
                   }
                   else {
                	   
                       System.out.println("UNKNOWN Species Value = " + strSpecies);
                   }
                   // Column 7
                   strANO_DESCRIPTION = "";
                   
                   Node node = new Node((long) intANO_OID, strANO_SPECIES_FK, strANO_COMPONENT_NAME, boolANO_IS_PRIMARY, boolANO_IS_GROUP, strANO_PUBLIC_ID, strANO_DESCRIPTION);
                   
                   nodeDAO.create(node);
                   
                   //Update Components in Memory
                   // assign generated new EMAPA id to new components replacing temp id
                   component.setNewID(strANO_PUBLIC_ID);
                   
                   // comment new component with generated EMAPA id
                   if (strSpecies.equals("mouse")) {
                	   
                	   tree.getComponent( component.getID()).setCheckComment(
                			   "New EMAPA:ID generated: " + strANO_PUBLIC_ID);
                   }
                   else if (strSpecies.equals("chick")) {
                	   
                	   tree.getComponent( component.getID()).setCheckComment(
                			   "New ECAPA:ID generated: " + strANO_PUBLIC_ID);
                   }
                   else if (strSpecies.equals("human")) {
                	   
                	   tree.getComponent( component.getID()).setCheckComment(
                			   "New EHDAA:ID generated: " + strANO_PUBLIC_ID);
                   }
                   else {
                	   
                       System.out.println("UNKNOWN Species Value = " + strSpecies);
                   }

                   // Update the ANA_OBO_COMPONENT tables ...
                   insertANA_OBO_COMPONENT_ALTERNATIVE( component.getID(), strANO_PUBLIC_ID);
                   
                   // update new components with ano_oid
                   component.setDBID(Integer.toString(intANO_OID));
                   
                   // update new component with generated emapa id
                   component.setID(strANO_PUBLIC_ID);
               }
           }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 03-05
    //  Insert new rows into ANA_OBO_COMPONENT_ALTERNATIVE
    private void insertANA_OBO_COMPONENT_ALTERNATIVE( String oldPublicId, String newPublicId ) {
   	/*
     *  Columns:
     *   ACS_OID bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACS_OBO_ID varchar(25) NOT NULL,
     *   ACS_OBO_ALT_ID varchar(25) NOT NULL,
  	 */
        if (debug) {
        	
            System.out.println("03-05 - insertANA_OBO_COMPONENT_ALTERNATIVE");
        }
        
        try {
        	
        	ComponentAlternative componentalternative = new ComponentAlternative(null,
        			newPublicId,
        			oldPublicId);
        	
        	componentalternativeDAO.create(componentalternative);
        	
        	updateANA_OBO_COMPONENT( oldPublicId, newPublicId );
        	updateANA_OBO_COMPONENT_SYNONYM( oldPublicId, newPublicId );
        	updateANA_OBO_COMPONENT_COMMENT( oldPublicId, newPublicId );
        	updateANA_OBO_COMPONENT_RELATIONSHIP( oldPublicId, newPublicId );
        	updateANA_OBO_COMPONENT_ORDER( oldPublicId, newPublicId );
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 03-10
    //  Update existing rows in ANA_OBO_COMPONENT
    private void updateANA_OBO_COMPONENT( String oldPublicId, String newPublicId ) {
   	/*
     *  Columns:
     *   AOC_OID bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   AOC_NAME varchar(255) NOT NULL,
     *   AOC_OBO_ID varchar(25) NOT NULL,
     *   AOC_DB_ID varchar(25) NOT NULL,
     *   AOC_NEW_ID varchar(25) NOT NULL,
     *   AOC_NAMESPACE varchar(50) NOT NULL,
     *   AOC_DEFINITION varchar(510) NOT NULL,
     *   AOC_GROUP tinyint(1) NOT NULL,
     *   AOC_START varchar(10) NOT NULL,
     *   AOC_END varchar(10) NOT NULL,
     *   AOC_PRESENT varchar(10) NOT NULL,
     *   AOC_STATUS_CHANGE varchar(10) NOT NULL COMMENT 'UNCHANGED NEW CHANGED DELETED',
     *   AOC_STATUS_RULE varchar(10) NOT NULL COMMENT 'UNCHECKED PASSED FAILED',
  	 */
        if (debug) {
        	
            System.out.println("03-10 - updateANA_OBO_COMPONENT");
        }
        
        try {
        	
    		Component component = componentDAO.findByOboId(oldPublicId);
    		
    		component.setId(newPublicId);
    		
    		componentDAO.save(component);
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 03-15
    //  Update existing rows in ANA_OBO_COMPONENT_SYNONYM
    private void updateANA_OBO_COMPONENT_SYNONYM( String oldPublicId, String newPublicId ) {
   	/*
     *  Columns:
     *   ACS_OID bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACS_OBO_ID varchar(25) NOT NULL,
     *   ACS_OBO_TEXT varchar(1000) NOT NULL,
  	 */
        if (debug) {
        	
            System.out.println("03-15 - updateANA_OBO_COMPONENT_SYNONYM");
        }
        
        try {
        	
    		List<ComponentSynonym> componentsynonyms = componentsynonymDAO.listByOboId(oldPublicId);
        	Iterator<ComponentSynonym> iteratorComponentSynonym = componentsynonyms.iterator();

        	while (iteratorComponentSynonym.hasNext()) {
        		
        		ComponentSynonym componentsynonym = iteratorComponentSynonym.next();
        		
        		componentsynonym.setId(newPublicId);
        		
        		componentsynonymDAO.save(componentsynonym);
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 03-20
    //  Update existing rows in ANA_OBO_COMPONENT_COMMENT
    private void updateANA_OBO_COMPONENT_COMMENT( String oldPublicId, String newPublicId ) {
   	/*
     *  Columns:
     *   ACC_OID bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACC_OBO_ID varchar(25) NOT NULL,
     *   ACC_OBO_GENERAL_COMMENT varchar(1000) NOT NULL,
     *   ACC_OBO_USER_COMMENT varchar(1000) NOT NULL,
     *   ACC_OBO_ORDER_COMMENT varchar(1000) NOT NULL,
  	 */
        if (debug) {
        	
            System.out.println("03-20 - updateANA_OBO_COMPONENT_COMMENT");
        }
        
        try {
        	
    		List<ComponentComment> componentcomments = componentcommentDAO.listByOboId(oldPublicId);
        	Iterator<ComponentComment> iteratorComponentComment = componentcomments.iterator();

        	while (iteratorComponentComment.hasNext()) {
        		
        		ComponentComment componentcomment = iteratorComponentComment.next();
        		
        		componentcomment.setId(newPublicId);
        		
        		componentcommentDAO.save(componentcomment);
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 03-25
    //  Update existing rows in ANA_OBO_COMPONENT_RELATIONSHIP
    private void updateANA_OBO_COMPONENT_RELATIONSHIP( String oldPublicId, String newPublicId ) {
   	/*
     *  Columns:
     *   ACR_OID bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACR_OBO_ID varchar(25) NOT NULL,
     *   ACR_OBO_CHILD_START int NOT NULL,
     *   ACR_OBO_CHILD_STOP int NOT NULL,
     *   ACR_OBO_TYPE varchar(25) NOT NULL,
     *   ACR_OBO_PARENT varchar(25) NOT NULL,
  	 */
        if (debug) {
        	
            System.out.println("03-25 - updateANA_OBO_COMPONENT_RELATIONSHIP");
        }
        
        try {
        	
    		List<ComponentRelationship> componentrelationshipchilds = componentrelationshipDAO.listByChild(oldPublicId);
        	Iterator<ComponentRelationship> iteratorComponentRelationshipChild = componentrelationshipchilds.iterator();

        	while (iteratorComponentRelationshipChild.hasNext()) {
        		
        		ComponentRelationship componentrelationship = iteratorComponentRelationshipChild.next();
        		
        		componentrelationship.setChild(newPublicId);
        		
        		componentrelationshipDAO.save(componentrelationship);
        	}

        	List<ComponentRelationship> componentrelationshipparents = componentrelationshipDAO.listByParent(oldPublicId);
        	Iterator<ComponentRelationship> iteratorComponentRelationshipParent = componentrelationshipparents.iterator();

        	while (iteratorComponentRelationshipParent.hasNext()) {
        		
        		ComponentRelationship componentrelationship = iteratorComponentRelationshipParent.next();
        		
        		componentrelationship.setParent(newPublicId);
        		
        		componentrelationshipDAO.save(componentrelationship);
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 03-30
    //  Update existing rows in ANA_OBO_COMPONENT_ORDER
    private void updateANA_OBO_COMPONENT_ORDER( String oldPublicId, String newPublicId ) {
   	/*
     *  Columns:
     *   ACO_OID bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACO_OBO_ID varchar(25) NOT NULL,
     *   ACO_OBO_PARENT varchar(25) NOT NULL,
     *   ACO_OBO_TYPE varchar(25) NOT NULL,
     *   ACO_OBO_ALPHA_ORDER int(20) unsigned NULL,
     *   ACO_OBO_SPECIAL_ORDER int(20) unsigned NULL,
  	 */
        if (debug) {
        	
            System.out.println("03-30 - updateANA_OBO_COMPONENT_ORDER");
        }
        
        try {
        	
    		List<ComponentOrder> componentorderchilds = componentorderDAO.listByChild(oldPublicId);
        	Iterator<ComponentOrder> iteratorComponentOrderChild = componentorderchilds.iterator();

        	while (iteratorComponentOrderChild.hasNext()) {
        		
        		ComponentOrder componentorder = iteratorComponentOrderChild.next();
        		
        		componentorder.setChild(newPublicId);
        		
        		componentorderDAO.save(componentorder);
        	}

        	List<ComponentOrder> componentorderparents = componentorderDAO.listByParent(oldPublicId);
        	Iterator<ComponentOrder> iteratorComponentOrderParent = componentorderparents.iterator();

        	while (iteratorComponentOrderParent.hasNext()) {
        		
        		ComponentOrder componentorder = iteratorComponentOrderParent.next();
        		
        		componentorder.setParent(newPublicId);
        		
        		componentorderDAO.save(componentorder);
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 04
    //  Insert into ANA_RELATIONSHIP
    private void insertANA_RELATIONSHIP( ArrayList<OBOComponent> newTermList,
            String calledFrom) {
    	/*
    	 *  ANA_RELATIONSHIP
    	 *  Columns:
         *   1. REL_OID                  - int(10) unsigned 
         *   2. REL_RELATIONSHIP_TYPE_FK - varchar(20)      
         *   3. REL_CHILD_FK             - int(10) unsigned 
         *   4. REL_PARENT_FK            - int(10) unsigned 
    	 */
        if (debug) {
        	
            System.out.println("04 - insertANA_RELATIONSHIP - called from = " + calledFrom);
        }

        ArrayList<OBOComponent> insertRelObjects = new ArrayList<OBOComponent>();
        OBOComponent component;

        String[] orders = null;
        
        int intMAX_PK = 0;
        
        boolean flagInsert;
        
        String project2 = "";
        
        int intREL_OID = 0;
        String strREL_RELATIONSHIP_TYPE_FK = "";
        int intREL_CHILD_FK = 0;
        int intREL_PARENT_FK = 0;

        int intRLP_SEQUENCE = -1;

        try {
        	
            //get max pk from referenced ana_relationship_project
        	//intMAX_PK = relationshipprojectDAO.maximumOid();

        	if ( !newTermList.isEmpty() ) {
          
                if ( project.equals("GUDMAP") ) {
                	
                    project2 = "EMAP";
                }
                else if ( project.equals("EMAP") ) {
                	
                    project2 = "GUDMAP";
                }
                else {
                	
                    System.out.println("UNKNOWN Project Value = " + project);
                }

                for ( int i = 0; i< newTermList.size(); i++) {
                	
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

                    for ( int j = 0; j< parents.size(); j++) {
                    	
                        //reset insertflag for each parent
                        flagInsert = true;
                        OBOComponent parent = (OBOComponent) tree.getComponent( parents.get(j) );

                        String strParentType = "";
                        strParentType = parentTypes.get(j);

                        //check whether parent has been deleted from obo file, do not allow insertion
                        if ( parent == null ) {
                        	
                             flagInsert = false;
                        }
                        else {
                        	
                        	ArrayList<JOINNodeRelationshipNode> joinnoderelationshipnodes = 
                        			(ArrayList<JOINNodeRelationshipNode>) joinnoderelationshipnodeDAO.listAllByChildIdAndParentId(component.getID(), parent.getID());

                        	if (joinnoderelationshipnodes.size() == 0) {
                            
                        		flagInsert = true;
                        	}
                        	else {
                                
                        		flagInsert = false;
                        	}
                        }
                        
                        //UPDATED CODE: deleted components are now marked in proposed file as well and appear in the tree under its own root outside abstract anatomy
                        if ( parent.getStatusChange().equals("DELETED") ) {
                            
                        	flagInsert = false;
                        }

                        //check whether any rules broken for each parent and print warning
                        //ignore any kind of rule violation for relationship record insertion except missing parent
                        else if ( parent.getStatusRule().equals("FAILED") ) {
                            
                        	flagInsert = true;
                        }
                        
                        //if parent is root Tmp new group don't treat as relationship
                        else if ( !parent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) ) {
                            
                        	flagInsert = false;
                        }
                    
                        //proceed with insertion 
                        if (flagInsert) {
                            
                        	OBOComponent insertRelObject = new OBOComponent();
                            
                            insertRelObject.setID( component.getDBID() ); 
                            
                            String strParentDBID = "";
                            
                            //get DBID for parent 
                            if ( parent.getStatusChange().equals("NEW") ) {
                            	
                                 strParentDBID = parent.getDBID();
                            }
                            //if component is not new 
                            else {
                            	
                            	Node node = null;
                                
                            	if ( nodeDAO.existPublicId(parent.getID()) ) {

                            		node = nodeDAO.findByPublicId(parent.getID());
                                	strParentDBID = Long.toString(node.getOid());
                            	}
                            	else {
                                	
                            		node = null;
                                	strParentDBID = "0";
                            	}
                            }

                            //get order for child based on parent
                            orders = component.getOrderCommentOnParent( parent.getID() );
                            
                            if ( orders!=null ) {
                            	
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
                    for ( OBOComponent insertRelObject : insertRelObjects ) {
                    	
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
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("DERIVES_FROM")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "derives-from";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("DEVELOPS_FROM")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "develops-from";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("LOCATED_IN")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "located-in";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("DEVELOPS_IN")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "develops-in";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("DISJOINT_FROM")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "disjoint-from";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("SURROUNDS")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "surrounds";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("ATTACHED_TO")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "attached-to";
                        }
                        else if ( insertRelObject.getChildOfTypes().get(0).equals("HAS_PART")) {
                        	
                            strREL_RELATIONSHIP_TYPE_FK = "has-part";
                        }
                        else {
                            System.out.println("UNKNOWN Relationship Type = " + insertRelObject.getChildOfTypes().get(0));
                        }

                        intREL_CHILD_FK = Integer.parseInt( insertRelObject.getID() );
                        //intRLP_SEQUENCE = -1;
                        intRLP_SEQUENCE = 0;
                        
                        if ( !insertRelObject.getOrderComment().equals("") ) {
                        	
                            intRLP_SEQUENCE = Integer.parseInt( insertRelObject.getOrderComment() );
                        }

                        try {
                        	
                            int intTryREL_PARENT_FK = 0;
                            intTryREL_PARENT_FK = Integer.parseInt( insertRelObject.getChildOfs().get(0) );
                            intREL_PARENT_FK = intTryREL_PARENT_FK;
                        }
                        catch(Exception e) {
                        	
                            System.out.println("Exception caught for child " + 
                                insertRelObject.getID() + " parent " +
                                insertRelObject.getChildOfs().toString() );
                            e.printStackTrace();
                        }
                    	
                        if ( !insertRelObject.getOrderComment().equals("") ) {
                        	
                            intRLP_SEQUENCE = Integer.parseInt( insertRelObject.getOrderComment() );
                        }

                        Relationship relationship = new Relationship((long) intREL_OID, strREL_RELATIONSHIP_TYPE_FK, (long) intREL_CHILD_FK, (long) intREL_PARENT_FK);
                
                        relationshipDAO.create(relationship);
                        
                        //insertANA_RELATIONSHIP_PROJECT( insertRelObject, intREL_OID, calledFrom );
                    }
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 04-5
    //  Insert into ANA_RELATIONSHIP
    private void insertANA_RELATIONSHIP_PROJECT( 
    		OBOComponent newComponent,
    		int relationshipOid,
            String calledFrom) {
    	/*
    	 *  ANA_RELATIONSHIP_PROJECT 
         *  Columns:
         *   1. RLP_OID             - int(10) unsigned 
         *   2. RLP_RELATIONSHIP_FK - int(10) unsigned      
         *   3. RLP_PROJECT_FK      - char(30)
         *   4. RLP_SEQUENCE        - int(10) unsigned 
    	 */
        if (debug) {
        	
            System.out.println("04-5 - insertANA_RELATIONSHIP_PROJECT - called from = " + calledFrom);
        }

        ArrayList<String> orderParents = new ArrayList<String>();
        ArrayList<ComponentOrder> componentorders = new ArrayList<ComponentOrder>();
        
        try {
            orderParents = newComponent.getChildOfs();
            
            for ( String orderParent: orderParents ) {
            	
            	componentorders = (ArrayList) componentorderDAO.listByChildIdAndParentID( newComponent.getID(), orderParent );

            	if ( componentorders.size() == 1) {
            		
            		ComponentOrder componentorder = componentorders.get(0);
            		
                    int intMAX_PK = relationshipprojectDAO.maximumOid();

                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject1 = new RelationshipProject((long) intMAX_PK, (long) relationshipOid, "EMAP", componentorder.getAlphaorder());
            
                    relationshipprojectDAO.create(relationshipproject1);
                    
                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject2 = new RelationshipProject((long) intMAX_PK, (long) relationshipOid, "GUDMAP", componentorder.getSpecialorder());
                  
                    relationshipprojectDAO.create(relationshipproject2);
            	}
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // DD
    //  Insert into ANA_RELATIONSHIP
    private void rebuildANA_RELATIONSHIP_PROJECT() {
    	/*
    	 *  ANA_RELATIONSHIP_PROJECT 
         *  Columns:
         *   1. RLP_OID             - int(10) unsigned 
         *   2. RLP_RELATIONSHIP_FK - int(10) unsigned      
         *   3. RLP_PROJECT_FK      - char(30)
         *   4. RLP_SEQUENCE        - int(10) unsigned 
    	 */
        if (debug) {
        	
            System.out.println("DD - rebuildANA_RELATIONSHIP_PROJECT");
        }

        ArrayList<ComponentOrder> componentorders = new ArrayList<ComponentOrder>();
        ArrayList<RelationshipProject> relationshipprojects = new ArrayList<RelationshipProject>(); 
        int intMAX_PK = 0;
        
        try {
            relationshipprojects = (ArrayList) relationshipprojectDAO.listAll();
        	
        	Iterator<RelationshipProject> iteratorRelationshipProject = relationshipprojects.iterator();

        	while (iteratorRelationshipProject.hasNext()) {
        		
        		RelationshipProject relationshipproject = iteratorRelationshipProject.next();
        		
        		relationshipprojectDAO.delete(relationshipproject);
        	}

            ArrayList<JOINNodeRelationshipNode> joinnoderelationships = new ArrayList<JOINNodeRelationshipNode>(); 
            joinnoderelationships = (ArrayList) joinnoderelationshipnodeDAO.listAll();
            
        	Iterator<JOINNodeRelationshipNode> iteratorJOINNodeRelationshipNode = joinnoderelationships.iterator();

        	while (iteratorJOINNodeRelationshipNode.hasNext()) {
        		
        		JOINNodeRelationshipNode joinnoderelationship = iteratorJOINNodeRelationshipNode.next();

            	componentorders = (ArrayList) componentorderDAO.listByChildIdAndParentID( joinnoderelationship.getAPublicId(), joinnoderelationship.getBPublicId() );

            	if ( componentorders.size() == 1) {
            		
            		ComponentOrder componentorder = componentorders.get(0);
            		
                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject1 = new RelationshipProject((long) intMAX_PK, 
                    		joinnoderelationship.getOidRelationship(), 
                    		"EMAP", 
                    		componentorder.getAlphaorder());
            
                    relationshipprojectDAO.create(relationshipproject1);
                    
                    //get max primary key for ana_relationship_project
                    intMAX_PK++; 
                    RelationshipProject relationshipproject2 = new RelationshipProject((long) intMAX_PK, 
                    		joinnoderelationship.getOidRelationship(), 
                    		"GUDMAP", 
                    		componentorder.getSpecialorder());
                  
                    relationshipprojectDAO.create(relationshipproject2);
            	}
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 05
    //  Insert into ANA_TIMED_NODE
    private void insertANA_TIMED_NODE( ArrayList<OBOComponent> newTermList, String calledFrom ) {
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

    	if (debug) {
    		
            System.out.println("05 - insertANA_TIMED_NODE - called from = " + calledFrom);
        }

        OBOComponent component;

        try {
        	
        	//03-1
            intCurrentPublicID = getMaxPublicID();
            
            //create timed components in ANA_OBJECT
            // 05-1
            timedCompList = createTimeComponents(newTermList, calledFrom);
                           
            if ( !timedCompList.isEmpty() ) {
            	
                //INSERT timed component obj_oids into ANA_OBJECT
                insertANA_OBJECT(timedCompList, "ANA_TIMED_NODE");

                int intPrevNode = 0;
                int intCompieStage = 0;

                for (int k = 0; k< timedCompList.size(); k++) {
                	
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

                    timednodeDAO.create(timednode);

                    intPrevNode = intATN_NODE_FK;
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }

    // 06
    //  Insert into ANA_SYNONYM
    private void insertANA_SYNONYM( ArrayList<OBOComponent> newTermList, String calledFrom ) {
    	/*
    	 *  ANA_NODE - ABSTRACT Synonyms in the Anatomy DAG
    	 *              - EMAPA:.... 
         *  
         *  Columns:
         *   1. SYN_OID         - int(10) unsigned
         *   2. SYN_OBJECT_FK   - int(10) unsigned
         *   3. SYN_SYNONYM     - varchar(100)
    	 */

        if (debug) {
        	
            System.out.println("06 - insertANA_SYNONYM - called from = " + calledFrom);
        }

        OBOComponent component;

        synonymCompList.clear();

        try {
            
            //get max oid from referenced database
           intCurrentObjectID = getMaxObjectID(); 
           
           for ( int i = 0; i< newTermList.size(); i++) {
            
               component = newTermList.get(i);
               
               //get parents 
               ArrayList < String > synonyms = component.getSynonyms();
               
               for (int j = 0; j< synonyms.size(); j++) {
                   
            	   OBOComponent synonymcomponent = new OBOComponent();
                   synonymcomponent.setID( component.getDBID() );
                   synonymcomponent.setName( synonyms.get(j) );
                   
                   synonymCompList.add( synonymcomponent );
               }
           }
           
           if ( !synonymCompList.isEmpty() ) {
        	   
               insertANA_OBJECT(synonymCompList, "ANA_SYNONYM");

               for ( OBOComponent synCompie: synonymCompList ) {

                   //proceed with insertion
                   int intSYN_OID = Integer.parseInt( synCompie.getDBID() );
                   int intSYN_OBJECT_FK = Integer.parseInt( synCompie.getID() );
                   String strSYN_SYNONYM = synCompie.getName();

                   Synonym synonym = new Synonym((long) intSYN_OID, (long) intSYN_OBJECT_FK, strSYN_SYNONYM);
                   
                   synonymDAO.create(synonym);
               }
           }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }
    
    // 07
    //  Set Database Ids into a list of components
    private ArrayList<OBOComponent> setDBIDs( ArrayList<OBOComponent> termList ) {

    	/*
        if (debug) {
        	
            System.out.println("07 - setDBIDs");
        }
        */
        
        OBOComponent component = new OBOComponent();
        
        try {
        	
            //for (OBOComponent component : termList) {
            for (int i = 0; i<termList.size(); i++ ) {
            	
                component = termList.get(i);
                
                Node node = nodeDAO.findByPublicId(component.getID());
                
                if (node == null) {
                	
                    if ( component.getStatusChange().equals("DELETED") ) {
                    	
                        component.setCheckComment("Delete Record Warning: No " +
                            "term with the ID " + component.getID() +
                            " exists in ANA_NODE, deletion for this " +
                            "component did not proceed.");
                        termList.remove(i);
                        i--;
                        
                        unDeletedCompList.add(component);
                    }
                    else if ( component.getStatusChange().equals("CHANGED") ) {
                        
                    	component.setCheckComment("Update Record Warning: No " +
                            "term with the ID " + component.getID() +
                            " exists in ANA_NODE, changes made by the user " +
                            "for this component were not update in the " +
                            "database.");
                        termList.remove(i);
                        i--;
                        
                        unModifiedCompList.add(component);
                    }
                }
                else {
                	
                    component.setDBID( node.getOid().toString() );
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
        
        return termList;
    }

    // 09
    //  update Stage
	private void updateStages( ArrayList<OBOComponent> changedStageTermList ) {

        if (debug) {
        	
            System.out.println("09 - updateStages");
        }

        ArrayList<OBOComponent> deleteTimedComponents =
                new ArrayList<OBOComponent>();
        
        //find ranges of stages that need to be inserted/deleted, create
        // temporary components for ranges
        // 09-1
        createDifferenceTimedComponents( changedStageTermList );
        
        //insert time components in ANA_TIMED_NODE
        insertANA_TIMED_NODE( diffCreateTimedCompList, "MODIFY" );
        
        //insert time components to be deleted into ANA_LOG
          //insertANA_LOG_deletedStages( diffDeleteTimedCompList );
          //create timed components due for deletion
        
        //deleteTimedComponents = createTimeComponents( diffDeleteTimedCompList, "DELETE" ); //delete obj_oids!
        // 09-2
        deleteTimedComponents =
               (ArrayList<OBOComponent>) insertANA_LOG_deletedStages( diffDeleteTimedCompList );
        
        //delete time components in ANA_TIMED_NODE
        // 09-3
        deleteANA_TIMED_NODE( deleteTimedComponents );
        
        //delete object oids of timed components in ANA_OBJECT
        // 09-4
        deleteANA_OBJECT( deleteTimedComponents, "ANA_TIMED_NODE" );
    }

    // 11
    //  Update ANA_NODE for Changed Names
    private void updateANA_NODE( ArrayList<OBOComponent> changedNameTermList ) {
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

        if (debug) {
        	
            System.out.println("11 - updateANA_NODE");
        }

        try {
            
            if ( !changedNameTermList.isEmpty() ) {

            	for (OBOComponent component: changedNameTermList) {
                	
                	Node node = nodeDAO.findByPublicId(component.getID());
                	
                	node.setComponentName(component.getName());
                	
                	nodeDAO.save(node);
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }

    // 13
	private void updateSynonyms( ArrayList<OBOComponent> changedSynonymsTermList ) {

        if (debug) {
        	
            System.out.println("13 - updateSynonyms");
        }
        
        ArrayList<OBOComponent> deleteSynComponents = new ArrayList<OBOComponent>();
        
        //find ranges of stages that need to be inserted/deleted, create
        // temporary components for ranges
        // 13-1
        createDifferenceSynonyms( changedSynonymsTermList );
        
        //insert relationships in ANA_SYNONYM
        // 06
        insertANA_SYNONYM( diffCreateSynList, "MODIFY" );
        
        //insert relationships to be deleted in ANA_LOG
        // 13-2
        deleteSynComponents = 
        		(ArrayList<OBOComponent>) insertANA_LOG_deletedSyns( diffDeleteSynList );
        
        //delete relationships in ANA_SYNONYM
        // 13-3
        deleteANA_SYNONYM( deleteSynComponents );
        
        //delete object oids of synonym records in ANA_OBJECT
        // 09-4
        deleteANA_OBJECT( deleteSynComponents, "ANA_SYNONYM" );
    }

    // 15
	private void updateParents( ArrayList<OBOComponent> changedParentsTermList ) {

        if (debug) {
        	
            System.out.println("15 - updateParents");
        }

        ArrayList<OBOComponent> deleteRelComponents =
                new ArrayList<OBOComponent>();
        
        //find ranges of stages that need to be inserted/deleted, create
        // temporary components for ranges
        // 15-1
        createDifferenceParents( changedParentsTermList );

        //insert relationships in ANA_RELATIONSHIP
        // 04
        insertANA_RELATIONSHIP( diffCreateRelList, "MODIFY");
        
        //insert relationships to be deleted in ANA_LOG
        // 15-2
        deleteRelComponents =
        		(ArrayList<OBOComponent>) insertANA_LOG_deletedRels( diffDeleteRelList );
        
        //delete relationships in ANA_RELATIONSHIP
        // 15-3
        deleteANA_RELATIONSHIP( deleteRelComponents );
        
        //delete object oids of relationship records in ANA_OBJECT
        // 09-4
        deleteANA_OBJECT( deleteRelComponents, "ANA_RELATIONSHIP" );
        
        //reorder children in deleted/added child rows
    }

    // 15.5
    private void updateANA_NODE_primary( ArrayList< OBOComponent > changedPrimaryTermList ) {

        if (debug) {
        	
            System.out.println("15.5 - updateANA_NODE_primary");
        }

        try {
        	
            if ( !changedPrimaryTermList.isEmpty() ) {
            	
                for (OBOComponent component: changedPrimaryTermList) {

                	Node node = nodeDAO.findByPublicId(component.getID());
                    
                 	node.setGroup(!component.getIsPrimary());
                	node.setPrimary(component.getIsPrimary());

                	nodeDAO.save(node);
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }

    /*
    // 17
    private void updateOrder( ArrayList<OBOComponent> changedOrderTermList ) {

        if (debug) {
            System.out.println("17 - updateOrder");
        }

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
        
        OBOComponent childCompie = new OBOComponent();
        
        String[] arrayFirstWord = null;
        String forChild = "";
        int intMaxOrder = 0;
        
        for (OBOComponent component: changedOrderTermList) {
            //get all parents
            parents.addAll(component.getChildOfs());
            //parents.addAll(component.getGroupPartOf());
            //parents.add(component.getIsA());
            //check for each parent whether there is ordering
            for (String parent: parents) {
                commentsOnParent.clear(); //reset for each parent
                children.clear(); //reset for each parent
                intMaxOrder = -1; //reset for each parent
                //get all children
                children.addAll( tree.getChildrenBasedOnParent(parent) );
                //iterate through all children of each parent and gather all order comments
                for (String child: children) {
                    forChild = ""; //reset for each child
                    //get component for child
                    childCompie = tree.getComponent(child);
                    //get order from child component based on the parent
                    String[] arrOrderComments = childCompie.getOrderCommentOnParent(parent);
                    //if there is an order put in order vector
                    if ( arrOrderComments!=null ) {
                        //find max order number for this series of siblings
                        arrayFirstWord = arrOrderComments[0].split(" ");
                        if ( Integer.parseInt(arrayFirstWord[0]) > intMaxOrder ) {
                            intMaxOrder = Integer.parseInt(arrayFirstWord[0]);
                        }

                        //get first word from order comment and append child to it to make a new comment based on 'for child'
                        forChild = arrayFirstWord[0] + " for " +
                                childCompie.getID();
                        //add to order comments for this parent
                        commentsOnParent.add(forChild);

                        //should never enter here if rule properly checked in CheckComponents
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
        //test
        //System.out.println(mapOrderedChildren.toString());

        //send hashmap to update_orderANA_RELATIONSHIP
        // 17-1
        update_orderANA_RELATIONSHIP(mapOrderedChildren);
    }
    */

    // 18
    private ArrayList<OBOComponent> validateDeleteTermList(ArrayList<OBOComponent> deletedTermList) {
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

        if (debug) {
        	
            System.out.println("18 - validateDeleteTermList");
        }

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
                    //put to unDeletedCompList for report purposes
                    deleteCompie.setCheckComment("Delete Record Warning: " + 
                        deleteCompie.getID() + " still has descendants in " +
                        "the database. Deletion not allowed.");
                
                    deleteCompie.setCheckComment("Delete Record Warning: " +
                        "Deletion of this term could potentially result in " +
                        "undesirable orphan terms or other ontology " +
                        "violations. Please generate a new OBO file from the " +
                        "database and retry deletion");
                    
                    unDeletedCompList.add(deleteCompie);
                }
                else {
                    
                	dbTermList.add(deleteCompie);
                }
            }
        }
        catch (Exception ex) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
        
        return dbTermList;
    }
    
    // 19
    private void insertANA_LOG(ArrayList<OBOComponent> recordTermList) {
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

        if (debug) {
        	
            System.out.println("19 - insertANA_LOG");
        }

        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        //version_oid should be very first obj_oid created for easy tracing
        int intLOG_VERSION_FK = intCurrentVersionID;
        String strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components";

        //create one record
        int intLogOID = 0;
        int intLogLoggedOID = 0;

        HashMap<String, String> anoOldValues = new HashMap<String, String>();
        HashMap<String, String> atnOldValues = new HashMap<String, String>();
        HashMap<String, String> relOldValues = new HashMap<String, String>();
        HashMap<String, String> synOldValues = new HashMap<String, String>();

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
        Vector<String> vANOcolumns = new Vector<String>();
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
        Vector<String> vRELcolumns = new Vector<String>();
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
        Vector<String> vATNcolumns = new Vector<String>();
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
        Vector<String> vSYNcolumns = new Vector<String>();
        vSYNcolumns.add("SYN_OID");
        vSYNcolumns.add("SYN_OBJECT_FK");
        vSYNcolumns.add("SYN_SYNONYM");
        
        //column values for selection from ANA_SYNONYM
        int intSYN_OID = 0;
        int intSYN_OBJECT_FK = 0;
        String strSYN_SYNONYM = "";

        try {
        	
        	//get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();
            //INSERT INTO ANA_LOG

            if ( !recordTermList.isEmpty() ) {
            	
                //for each component to be deleted
                for (OBOComponent component: recordTermList) {

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
                    anoOldValues.put( "ANO_SPECIES_FK", strSpecies );
                    anoOldValues.put( "ANO_COMPONENT_NAME", component.getName().toString() );
                    anoOldValues.put( "ANO_IS_PRIMARY", Integer.toString(intIsPrimary));
                    anoOldValues.put( "ANO_IS_GROUP", Integer.toString(intIsGroup));
                    anoOldValues.put( "ANO_PUBLIC_ID", component.getID().toString() );
                    anoOldValues.put( "ANO_DESCRIPTION", "N/A" );

                    //increment for each component
                    ++intLogLoggedOID;
                	
                    for (String columnName: vANOcolumns) {	
                    	
                        intLOG_OID = ++intLogOID;
                        intLOG_LOGGED_OID = intLogLoggedOID;
                        strLOG_COLUMN_NAME = columnName;
                        strLOG_OLD_VALUE = anoOldValues.get(columnName);
                        intLOG_VERSION_FK = intCurrentVersionID;
                        strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components; ANA_NODE";

                        Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                        
                        logDAO.create(log); 
                    }

                    ArrayList<TimedNode> timednodes = (ArrayList<TimedNode>) timednodeDAO.listByNodeFK(Long.valueOf(component.getDBID()));
                    
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

                        for (String columnName: vATNcolumns) {

                            intLOG_OID = ++intLogOID;
                            intLOG_LOGGED_OID = intLogLoggedOID;
                            strLOG_COLUMN_NAME = columnName;
                            strLOG_OLD_VALUE = atnOldValues.get(columnName);
                            intLOG_VERSION_FK = intCurrentVersionID;
                            strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components; ANA_TIMED_NODE";

                            Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                            
                            logDAO.create(log); 
                        }     
                  	}

                  	ArrayList<Relationship> relationships = (ArrayList<Relationship>) relationshipDAO.listByChildFK(Long.valueOf(component.getDBID()));
                  	
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

                        for (String columnName: vRELcolumns) {
                        	
                            intLOG_OID = ++intLogOID;
                            intLOG_LOGGED_OID = intLogLoggedOID;
                            strLOG_COLUMN_NAME = columnName;
                            strLOG_OLD_VALUE = relOldValues.get(columnName);
                            intLOG_VERSION_FK = intCurrentVersionID;
                            strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components; ANA_RELATIONSHIP";

                            Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                            
                            logDAO.create(log); 
                        }     
                    }
                  	
                  	ArrayList<Synonym> synonyms = (ArrayList<Synonym>) synonymDAO.listByObjectFK(Long.valueOf(component.getDBID()));
                  			
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

                        for (String columnName: vSYNcolumns) {
                        	
                            intLOG_OID = ++intLogOID;
                            intLOG_LOGGED_OID = intLogLoggedOID;
                            strLOG_COLUMN_NAME = columnName;
                            strLOG_OLD_VALUE = synOldValues.get(columnName);
                            intLOG_VERSION_FK = intCurrentVersionID;
                            strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components; ANA_SYNONYM";

                            Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                            
                            logDAO.create(log); 
                        }     
                    }
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        }
    }

    // 20
    private void deleteComponentFromTables (ArrayList <OBOComponent> validDeleteTermList) {

        if (debug) {
        	
            System.out.println("20 - deleteComponentFromTables");
        }

        try {
        	
            if ( !validDeleteTermList.isEmpty() ) {
            	
                for (OBOComponent component: validDeleteTermList) {
                	
                	// delete ANA_RELATIONSHIP rows, if any
                	ArrayList<Relationship> relationships = (ArrayList<Relationship>) relationshipDAO.listByChildFK(Long.valueOf(component.getDBID()));
                  	Iterator<Relationship> iteratorrelationships = relationships.iterator();

                  	while (iteratorrelationships.hasNext()) {
                  		
                  		Relationship relationship = iteratorrelationships.next();

                        //delete ANA_RELATIONSHIP_PROJECT that have foreign key constraints on ANA_RELATIONSHIP, if any
                  		/*
                    	ArrayList<RelationshipProject> relationshipprojects = (ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK(relationship.getOid());
                      	Iterator<RelationshipProject> iteratorrelationshipprojects = relationshipprojects.iterator();

                      	while (iteratorrelationshipprojects.hasNext()) {
                      		
                      		RelationshipProject relationshipproject = iteratorrelationshipprojects.next();
                      		
                      		relationshipprojectDAO.delete(relationshipproject);
                      	}
                      	*/
                    	
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
                	ArrayList<TimedNode> timednodes = (ArrayList<TimedNode>) timednodeDAO.listByNodeFK(Long.valueOf(component.getDBID()));
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
                	ArrayList<Synonym> synonyms = (ArrayList<Synonym>) synonymDAO.listByObjectFK(Long.valueOf(component.getDBID()));
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
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        } 
    }

    /*
    // 21
    private void reorderANA_RELATIONSHIP(ArrayList <OBOComponent> validDeleteTermList, String project) {
         
         // Reorders a collection of siblings that have order sequence entries in the ANA_RELATIONSHIP
         //   when one of the siblings have been deleted, re-ordering is based on original order and closes
         //   the sequence gap left by the deleted sibling
         // NOTE: function is obsolete if editor re-orders the remaining siblings, current rules
         // checking do not allow database to be updated if the ordering has a gap anyway

        if (debug) {
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
                for (OBOComponent component: validDeleteTermList) {

                	//get all parent-child relationship entries with ordering
                    childDBID = getDatabaseIdentifier(component.getID());
                    
                    skipRecords = "";

                    ArrayList<JOINNodeRelationshipRelationshipProject> joinnrrps = 
                    		(ArrayList<JOINNodeRelationshipRelationshipProject>) joinnoderelationshiprelationshipprojectDAO.listAllByChildFK((long) childDBID);
                  	Iterator<JOINNodeRelationshipRelationshipProject> iteratorjoinnrrps = joinnrrps.iterator();

                  	while (iteratorjoinnrrps.hasNext()) {
                  		JOINNodeRelationshipRelationshipProject joinnrrp = iteratorjoinnrrps.next();
                  		
                        if ( joinnrrp.getSequenceFK() != null ) {
                        	
                            componentParents.add( joinnrrp.getPublicId() );
                            //make a list of parent-child relationship entries to be deleted
                            skipRecords = skipRecords + joinnrrp.getOidRelationshipProject().intValue() + ",";
                            
                        }
                  	}
                }

                if (!skipRecords.equals("")) {
                	
                    skipRecords = skipRecords.substring(0, skipRecords.length() - 1);
                    skipRecords = "(" + skipRecords + ")";
                    
                }
                
                //check for each parent whether there is ordering
                for (String parent: componentParents) {
                    //get parent dbid
                    parentDBID = getDatabaseIdentifier(parent);
                    //reset REL_SEQ
                    intSEQ = 0;
                    //get all children records from ANA_RELATIONSHIP_PROJECT for this parent
                    //that has an order sequence entry, order by sequence
                    //exclude entries that are scheduled for deletion
                    
                    ArrayList<JOINRelationshipProjectRelationship> joinrprs = 
                    		(ArrayList<JOINRelationshipProjectRelationship>) joinrelationshipprojectrelationshipDAO.listAllByParentAndProjectNotIn((long) parentDBID, project, skipRecords);
                    Iterator<JOINRelationshipProjectRelationship> iteratorjoinrprs = joinrprs.iterator();

                  	while (iteratorjoinrprs.hasNext()) {
                  		JOINRelationshipProjectRelationship joinrpr = iteratorjoinrprs.next();
                  		
                  		RelationshipProject relationshipproject = relationshipprojectDAO.findByOid(joinrpr.getOidRelationshipProject());
                  		
                  		relationshipproject.setSequence((long) intSEQ);
                  		relationshipprojectDAO.create(relationshipproject);

                        //increment rel_seq for next record
                        intSEQ++;
                  	}
                }
            }
        }
        catch ( DAOException dao ) {
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	setProcessed(false);
            ex.printStackTrace();
        } 
    }
    */
    
    // SUB Methods ------------------------------------------------------------------------------------

    // 01-1
    private int getMaxObjectID() {

        if (debug) {
        	
            System.out.println("1-1 - getMaxObjectID");
        }

        //use old and new connection
        //select max public id from old ana_object
        //select max public id from new ana_object
        
        int intMax_OID = 0;
        int intMaxObjectID = 0;
        
        try {
        	
            //get max oid from referenced database
        	intMax_OID = thingDAO.maximumOid();

            //get larger public id
        	if ( intMax_OID >= intCurrentObjectID ) {
        		
        		intMaxObjectID = intMax_OID;
        	}
        	else {
        		
        		intMaxObjectID = intCurrentObjectID; 
        	}
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        } 

    	return intMaxObjectID;
    }
    
    // 02-1
    // Insert a new row into ANA_OBJECT
    private void insertANA_OBJECT( ArrayList< OBOComponent > newTermList, String calledFromTable ) {
    	/*
         *  Columns:
         *   1. OBJ_OID               - int(10) unsigned 
         *   2. OBJ_CREATION_DATETIME - datetime         
         *   3. OBJ_CREATOR_FK        - int(10) unsigned 
         *   4. OBJ_TABLE             - varchar(255)
         *   5. OBJ_DESCRIPTION       - varchar(255)
    	 */

        if (debug) {
        	
            System.out.println("02-1 - insertANA_OBJECT - for Inserts to Table " + calledFromTable);
        }
        
        OBOComponent component = new OBOComponent();

        try {
        	
            intCurrentObjectID = getMaxObjectID();
            
            int intOBJ_OID = 0;
            String datetime = utility.MySQLDateTime.now();
            long sysadmin = 2;
            String description = "";

            if ( !newTermList.isEmpty() ) {

                for ( int i=0; i<newTermList.size(); i++ ) {
                	
                    component = newTermList.get(i);

                    //database values
                    intOBJ_OID = ++intCurrentObjectID;
                    //update new components with ano_oid/atn_oid
                    component.setDBID(Integer.toString(intOBJ_OID));

                    Thing thing = new Thing((long) intOBJ_OID, datetime, sysadmin, calledFromTable, description);

                    thingDAO.create(thing);
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
            ex.printStackTrace();
        } 
    }

    // 03-1
    private int getMaxPublicID() {

        if (debug) {
        	
            System.out.println("03-1 - getMaxPublicID");
        }

        //use new connection
        //select max public id from ana_node
        //select max public id from ana_timed_node
        
        int intMax_ATNpublicID = 0;
        int intMax_ANOpublicID = 0;
        int intMaxPublicID = 0;
        
        try {
        	
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
        	
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 

        //note: intCurrentPublicID should be larger than current intMaxPublicID if method has been called before
        //get larger public id
    	if ( intCurrentPublicID >= intMaxPublicID ) {
    		
    		intMaxPublicID = intCurrentPublicID;
    	}
        
        return intMaxPublicID;
    }

    // 05-1
    private ArrayList<OBOComponent> createTimeComponents(ArrayList<OBOComponent> termList, String calledFrom) {

        if (debug) {
        	
            System.out.println("05-1 - createTimeComponents");
        }

        //create timed components in ANA_OBJECT
        OBOComponent component = new OBOComponent();
        
        boolean flagInsert = false;
        
        ArrayList<OBOComponent> timedComps = new ArrayList<OBOComponent>();
        
        for ( int i = 0; i< termList.size(); i++) {
     	   
            component = termList.get(i);

            if ( ( component.commentsContain("Relation: ends_at -- Missing ends at stage - OBOComponent's stage range cannot be determined.") ) ||
                 ( component.commentsContain("Relation: starts_at -- Missing starts at stage - OBOComponent's stage range cannot be determined.") ) ||
                 ( component.commentsContain("Relation: starts_at, ends_at -- Ends at stage earlier than starts at stage.") ) ||
                 ( component.commentsContain("Relation: Starts At - More than one Start Stage!") ) ||
                 ( component.commentsContain("Relation: Ends At - More than one End Stage!") ) ||
                 ( component.commentsContain("Relation: starts_at, ends_at -- Stages are out of range!") ) ) {
         	   
                flagInsert = false; 
            }
            else {
         	   
                flagInsert = true; 
            }

            if (flagInsert) {
         	   
                //make a time component record for each stage
                for (int j = component.getStartSequence(); j <= component.getEndSequence(); j++ ) {

                    String strStage = component.getStart();

                    OBOComponent timedCompie = new OBOComponent();
                    timedCompie.setNamespace( component.getDBID() ); //current component

                    timedCompie.setStart( strStage );

                    if (strSpecies.equals("mouse")) {
                 	   
                        timedCompie.setID( "EMAP:" +
                                Integer.toString( ++intCurrentPublicID ) );
                    }
                    
                    if (strSpecies.equals("human")) {
                 	   
                        timedCompie.setID( "EHDA:" +
                                Integer.toString( ++intCurrentPublicID ) );
                    }
                    
                    if (strSpecies.equals("chick")) {
                 	   
                        timedCompie.setID( "ECAP:" +
                                Integer.toString( ++intCurrentPublicID ) );
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
    private void createDifferenceTimedComponents( ArrayList<OBOComponent> diffStageTermList ) {

        if (debug) {
        	
            System.out.println("09-1 - createDifferenceTimedComponents");
        }
        
        try {
        	
            for (OBOComponent component: diffStageTermList) {

                int startSequence = jointimednodestageDAO.minSequenceByNodeFk(Long.valueOf(component.getDBID()));
                
                int endSequence = jointimednodestageDAO.maxSequenceByNodeFk(Long.valueOf(component.getDBID()));

                //compare stage ranges between component and databasecomponent
                // for creating new timed components
                if ( startSequence > component.getStartSequence() ) {
                   
                	OBOComponent createtimedcomponent = new OBOComponent();
                   
                	createtimedcomponent.setID( component.getID() );
                	createtimedcomponent.setName( component.getName() );
                	createtimedcomponent.setDBID( component.getDBID() );
                	createtimedcomponent.setStart( component.getStart() );
                	createtimedcomponent.setEndSequence( startSequence - 1, strSpecies );

                	diffCreateTimedCompList.add( createtimedcomponent );
                }
                else if ( endSequence < component.getEndSequence() ) {
                   
                	OBOComponent createtimedcomponent = new OBOComponent();
                   
                	createtimedcomponent.setID( component.getID() );
                	createtimedcomponent.setName( component.getName() );                   
                	createtimedcomponent.setDBID( component.getDBID() );
                	createtimedcomponent.setStartSequence( endSequence + 1, strSpecies );
                	createtimedcomponent.setEndSequence( component.getEndSequence(), strSpecies );
                   
                	diffCreateTimedCompList.add( createtimedcomponent );
                }
                //for deleting existing timed components
                else if ( startSequence < component.getStartSequence() ) {
                   
                	OBOComponent delTimedCompie = new OBOComponent();
                   
                	delTimedCompie.setID( component.getID() );
                	delTimedCompie.setName( component.getName() );
                	delTimedCompie.setDBID( component.getDBID() );
                	delTimedCompie.setStartSequence( startSequence, strSpecies );
                	delTimedCompie.setEndSequence( component.getStartSequence() - 1, strSpecies );

                	diffDeleteTimedCompList.add( delTimedCompie );
                }
                else if ( endSequence > component.getEndSequence() ) {
                    
                	OBOComponent delTimedCompie = new OBOComponent();
                    
                	delTimedCompie.setID( component.getID() );
                    delTimedCompie.setName( component.getName() );
                    delTimedCompie.setDBID( component.getDBID() );
                    delTimedCompie.setStartSequence( component.getEndSequence() + 1, strSpecies );
                    delTimedCompie.setEndSequence( endSequence, strSpecies );

                    diffDeleteTimedCompList.add( delTimedCompie );
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }
    
    // 09-2
    //  Insert into ANA_LOG for ANA_TIMED_NODE Deletions
    private ArrayList<OBOComponent> insertANA_LOG_deletedStages( ArrayList<OBOComponent> diffDeleteTimeComponents ) {
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

        if (debug) {
        	
            System.out.println("09-2 - insertANA_LOG_deletedStages");
        }
        
        ArrayList<OBOComponent> deleteTimeComponents = new ArrayList<OBOComponent>();
        
        HashMap<String, String> atnOldValues = new HashMap<String, String>(); 
        int intLogLoggedOID = 0;
        int intLogOID = 0;
        int intStartKey = 0;
        int intEndKey = 0;
        String stageName = "";
        
        //ANA_TIMED_NODE columns
        Vector<String> vATNcolumns = new Vector<String>();
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
        int intLOG_VERSION_FK = intCurrentVersionID; 
        String strLOG_COMMENTS = "Insert into ANA_LOG for Deleted TimedNodes";
        
        try {
        	
        	//get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();

            if ( !diffDeleteTimeComponents.isEmpty() ) {

            	for ( OBOComponent component: diffDeleteTimeComponents ) {

                    intStartKey = component.getStartSequence();
                    intEndKey = component.getEndSequence();

                    for ( int stage=intStartKey; stage<=intEndKey; stage++ ) {

                        ArrayList<JOINTimedNodeStage> jointimednodestages = 
                        		(ArrayList<JOINTimedNodeStage>) jointimednodestageDAO.listAllByNodeFkAndStageSequence(Long.valueOf(component.getDBID()), (long) stage);

                        Iterator<JOINTimedNodeStage> iteratorjointimednodestage = jointimednodestages.iterator();

                      	while (iteratorjointimednodestage.hasNext()) {
                        
                      		JOINTimedNodeStage jointimednodestage = iteratorjointimednodestage.next();
                      		
                            OBOComponent deleteTimeComponent = new OBOComponent();

                            deleteTimeComponent.setDBID( jointimednodestage.getOidTimedNode().toString());
                            deleteTimeComponent.setID( jointimednodestage.getPublicTimedNodeId());
                            deleteTimeComponent.setNamespace( component.getDBID() ); 
                            deleteTimeComponent.setStart( stageName );
                            
                            deleteTimeComponents.add( deleteTimeComponent );
                            
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
                            
                            for (String columnName: vATNcolumns) {
                              	
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = atnOldValues.get(columnName);

                                Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                               
                                logDAO.create(log);
                            }
                      	}     
                    }
            	}    
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 

        return deleteTimeComponents;
    }

    // 09-3
    //  Delete from ANA_TIMED_NODE 
    private void deleteANA_TIMED_NODE( ArrayList<OBOComponent> deleteTimedComponents ) {

        if (debug) {
        	
            System.out.println("09-3 - deleteANA_TIMED_NODE");
        }

        try {
        	
            if ( !deleteTimedComponents.isEmpty() ) {

            	for ( OBOComponent component: deleteTimedComponents ) {

            		TimedNode timednode = timednodeDAO.findByOid(Long.valueOf(component.getDBID()));

            		timednodeDAO.delete(timednode);
            	}
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }

    // 09-4
    //  Delete from ANA_OBJECT
    private void deleteANA_OBJECT( ArrayList<OBOComponent> deleteObjects, String calledFromTable ) {
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

        if (debug) {
        	
            System.out.println("09-4 - deleteANA_OBJECT - for Deletes to ANA_OBJECT for Table " + calledFromTable);
        }

        try {
        	
            if ( !deleteObjects.isEmpty() ) {

                for ( OBOComponent deleteObject: deleteObjects ) {
                	
                    Thing thing = thingDAO.findByOid(Long.valueOf(deleteObject.getDBID())); 

                    thingDAO.delete(thing);
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }
    
    // 13-1
    private void createDifferenceSynonyms( ArrayList<OBOComponent> diffSynonymTermList ) {

        if (debug) {
        	
            System.out.println("13-1 - createDifferenceSynonyms");
        }

        OBOComponent databasecomponent = new OBOComponent();
        OBOComponent deletesynonymcomponent = new OBOComponent();
        OBOComponent insertsynonymcomponent = new OBOComponent();        
        
        ArrayList<String> synonyms = new ArrayList<String>();
        ArrayList<String> deleteSynonyms = new ArrayList<String>();
        ArrayList<String> insertSynonyms = new ArrayList<String>();
        
        try {
        	
            //for each component where parents have changed
            for (OBOComponent component: diffSynonymTermList) {

            	ArrayList<Synonym> synonymlist = (ArrayList<Synonym>) synonymDAO.listByObjectFK(Long.valueOf(component.getDBID()));
            	
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
                
                if ( !deleteSynonyms.isEmpty() ) {
                	
                    deletesynonymcomponent = new OBOComponent();
                
                    ArrayList < String > copyDeleteSynonyms = new ArrayList<String>();
                    
                    copyDeleteSynonyms.addAll( deleteSynonyms );
                    
                    deletesynonymcomponent.setDBID( component.getDBID() );
                    deletesynonymcomponent.setName( component.getName() );
                    deletesynonymcomponent.setSynonyms( copyDeleteSynonyms );
                    
                    diffDeleteSynList.add( deletesynonymcomponent );
                }
                
                //get parents to be inserted
                //parents owned by component but not by databasecomponent
                insertSynonyms.clear(); //reset 
                insertSynonyms.addAll( synonyms );
                insertSynonyms.removeAll( databasecomponent.getSynonyms() );
                
                if ( !insertSynonyms.isEmpty() ) {
                	
                    insertsynonymcomponent = new OBOComponent();
                
                    ArrayList < String > copyInsertSynonyms = new ArrayList<String>();
                    
                    copyInsertSynonyms.addAll( insertSynonyms );
                    
                    insertsynonymcomponent.setDBID( component.getDBID() );
                    insertsynonymcomponent.setName( component.getName() );
                    insertsynonymcomponent.setSynonyms( copyInsertSynonyms );
                    
                    diffCreateSynList.add( insertsynonymcomponent );
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }

    // 13-2
    //  Insert into ANA_LOG for all Deleted Synonyms
    private ArrayList<OBOComponent> insertANA_LOG_deletedSyns( ArrayList<OBOComponent> diffDeleteSyns ) {
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

        if (debug) {
        	
            System.out.println("13-2 - insertANA_LOG_deletedSyns");
        }
        
        ArrayList<OBOComponent> deleteSynComponents = new ArrayList<OBOComponent>(); 
        HashMap<String, String> synOldValues = new HashMap<String, String>(); 
        ArrayList<String> deleteSynonyms = new ArrayList<String>();

        int intLogLoggedOID = 0;
        int intLogOID = 0;
        
        //ANA_SYNONYM columns
        Vector<String> vSYNcolumns = new Vector<String>();
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
        int intLOG_VERSION_FK = intCurrentVersionID; //version_oid should be very first obj_oid created for easy tracing

   
        try {
        	
        	//get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();

            if ( !diffDeleteSyns.isEmpty() ) {

            	for ( OBOComponent component: diffDeleteSyns ) {

                    deleteSynonyms = component.getSynonyms();

                    for ( String deleteSynonym: deleteSynonyms ) {

                        ArrayList<Synonym> synonymlist = (ArrayList<Synonym>) synonymDAO.listByObjectFKAndSynonym(Long.valueOf(component.getDBID()), deleteSynonym);
                        
                        //add to temporary component
                      	Iterator<Synonym> iteratorsynonym = synonymlist.iterator();

                      	while (iteratorsynonym.hasNext()) {
                      		
                      		Synonym synonym = iteratorsynonym.next();

                            OBOComponent deleteSynComponent = new OBOComponent();
                            
                            deleteSynComponent.setName( deleteSynonym );
                            deleteSynComponent.setDBID( Long.toString(synonym.getOid()) );
                            deleteSynComponent.setID( component.getDBID() );
                            deleteSynComponents.add( deleteSynComponent );

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

                            for (String columnName: vSYNcolumns) {
                            	
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = synOldValues.get(columnName);

                                Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                                
                                logDAO.create(log);
                            }     
                      	}
                    }
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
        
        return deleteSynComponents;
    }

    // 13-3
    //  Delete from ANA_SYNONYM
    private void deleteANA_SYNONYM( ArrayList<OBOComponent> deleteSynComponents ) {

        if (debug) {
        	
            System.out.println("13-3 - deleteANA_SYNONYM");
        }
        
        try {
        	
            if ( !deleteSynComponents.isEmpty() ) {
            	
                for ( OBOComponent deletesynonymcomponent: deleteSynComponents ) {
                	
                	Synonym synonym = synonymDAO.findByOid(Long.valueOf(deletesynonymcomponent.getDBID()));
                	
                	synonymDAO.delete(synonym);
                }
            } 
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }

    //method to detect difference in parents between modified components and existing components in DB
    // 15-1
    private void createDifferenceParents( ArrayList<OBOComponent> diffParentTermList ) {

        if (debug) {
        	
            System.out.println("15-1 - createDifferenceParents");
        }

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

                ArrayList<JOINNodeRelationship> joinnoderelationships = (ArrayList<JOINNodeRelationship>) joinnoderelationshipDAO.listAllByChild(Long.valueOf(component.getDBID()));

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
                    else if ( joinnoderelationship.getTypeFK().equals("SURROUNDS")) {
                   	
                    	databasecomponent.addChildOfType("surrounds");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("ATTACHED_TO")) {
                   	
                    	databasecomponent.addChildOfType("attached-to");
                    }
                    else if ( joinnoderelationship.getTypeFK().equals("HAS_PART")) {
                   	
                    	databasecomponent.addChildOfType("has-part");
                    }
                    else {
                        System.out.println("UNKNOWN Relationship Type = " + joinnoderelationship.getTypeFK());
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
                    
                    diffDeleteRelList.add( deleteRelCompie );
                }

                insertParents.clear(); 
                insertParents.addAll( inputParents );
                insertParentTypes.clear();
                insertParentTypes.addAll( inputParentTypes );

                if ( !insertParents.isEmpty() ) {
                	
                    insertRelCompie = new OBOComponent();

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

                    diffCreateRelList.add( insertRelCompie );
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }

    //method to detect difference in parents between modified components and existing components in DB
    // 15-1
    private void createNewParents( ArrayList<OBOComponent> newParentTermList ) {

        if (debug) {
        	
            System.out.println("18-5 - createNewParents");
        }

        try {
            //for each component where parents have changed
            for (OBOComponent component: newParentTermList) {

            	Set<String> comments = component.getNewComments();
            	
            	if ( !comments.isEmpty() ) {
            		
                    for (String string: comments){

                    	String [] splitString = string.split(" ");
                    	String targetId = splitString[9];
                    	
                	    int indexOfChildOf = component.getChildOfIndex(targetId);
                	    
                	    String targetType = component.getChildOfTypes().get(indexOfChildOf);
                	    
                	    
                    }
            	}
            }
        }
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }

    // 15-2
    //  Insert into ANA_LOG for ANA_RELATIONSHIP Deletions
    private ArrayList<OBOComponent> insertANA_LOG_deletedRels( ArrayList<OBOComponent> diffDeleteRels ) {
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

        if (debug) {
        	
            System.out.println("15-2 - insertANA_LOG_deletedRels");
        }
    
        ArrayList<OBOComponent> deleteRelComponents = new ArrayList<OBOComponent>(); 
        HashMap<String, String> relOldValues = new HashMap<String, String>(); 
        ArrayList<String> deleteParents = new ArrayList<String>();

        int intLogLoggedOID = 0;
        int intLogOID = 0;
        int intParentDBID = 0;
        
        //ANA_RELATIONSHIP columns
        Vector<String> vRELcolumns = new Vector<String>();
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
        int intLOG_VERSION_FK = intCurrentVersionID; 
        String strLOG_COMMENTS = "Insert into ANA_LOG for Deleted Relationships";
   
        try {
        	
            //get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
        	
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();
      	  
            if ( !diffDeleteRels.isEmpty() ) {
            	
            	for ( OBOComponent component: diffDeleteRels ) {
            		
                    //deleteParents = component.getChildOfs();
                    deleteParents = component.getChildOfs();

                    for ( String deleteParent: deleteParents ) {

                        intParentDBID = getDatabaseIdentifier( deleteParent ); 
                        
                        ArrayList<Relationship> relationships = (ArrayList<Relationship>) relationshipDAO.listByParentFKAndChildFK((long) intParentDBID, Long.valueOf(component.getDBID()));

                        if (relationships.size() == 1) {
                        	
                        	Relationship relationship = relationships.get(0);
                      	  
                            OBOComponent deleteRelComponent = new OBOComponent();

                            deleteRelComponent.setDBID( Long.toString(relationship.getOid()) );
                            deleteRelComponent.setID( component.getDBID() );
                            deleteRelComponent.addChildOf( Integer.toString( intParentDBID ) );

                            if ( relationship.getTypeFK().equals("part-of") ) {
                            	
                                deleteRelComponent.addChildOfType( "PART_OF" );
                            }
                            else if ( relationship.getTypeFK().equals("is-a") ) {
                            	
                                deleteRelComponent.addChildOfType( "IS_A" );
                            }
                            else if ( relationship.getTypeFK().equals("group-part-of") ) {
                            	
                                deleteRelComponent.addChildOfType( "GROUP_PART_OF" );
                            }
                            else if ( relationship.getTypeFK().equals("derives-from") ) {
                            	
                                deleteRelComponent.addChildOfType( "DERIVES_FROM" );
                            }
                            else if ( relationship.getTypeFK().equals("develops-from")) {
                               	
                            	deleteRelComponent.addChildOfType("DEVELOPS_FROM");
                            }
                            else if ( relationship.getTypeFK().equals("located-in")) {
                           	
                            	deleteRelComponent.addChildOfType("LOCATED_IN");
                            }
                            else if ( relationship.getTypeFK().equals("develops-in")) {
                           	
                            	deleteRelComponent.addChildOfType("DEVELOPS_IN");
                            }
                            else if ( relationship.getTypeFK().equals("disjoint-from")) {
                           	
                            	deleteRelComponent.addChildOfType("DISJOINT_FROM");
                            }
                            else if ( relationship.getTypeFK().equals("surrounds")) {
                           	
                            	deleteRelComponent.addChildOfType("SURROUNDS");
                            }
                            else if ( relationship.getTypeFK().equals("attached-to")) {
                           	
                            	deleteRelComponent.addChildOfType("ATTACHED_TO");
                            }
                            else if ( relationship.getTypeFK().equals("has-part")) {
                           	
                            	deleteRelComponent.addChildOfType("HAS_PART");
                            }
                            else {
                                System.out.println("UNKNOWN Relationship Type = " + relationship.getTypeFK());
                            }

                            deleteRelComponents.add( deleteRelComponent );

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

                            for (String columnName: vRELcolumns) {
                          	  
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = relOldValues.get(columnName);

                                Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                                
                                logDAO.create(log);
                            }     
                        }
                    }
                }
            }

        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 

        return deleteRelComponents;
    }

    // 15-2-1
    private int getDatabaseIdentifier( String publicId ) {

        if (debug) {
        	
            System.out.println("15-2-1 - getDatabaseIdentifier");
        }

        try {
        	
        	Node node = nodeDAO.findByPublicId(publicId);
        	
            if ( node != null ) {
            	
                return node.getOid().intValue();
            }
            else {
            	
                return 0;
            } 
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
        
        return 0;
    }

    // 15-3
    //  Delete from ANA_RELATIONSHIP
    private void deleteANA_RELATIONSHIP( ArrayList<OBOComponent> deleteRelComponents ) {

        if (debug) {
        	
            System.out.println("15-3 - deleteANA_RELATIONSHIP");
        }
        
        try {
        	
            if ( !deleteRelComponents.isEmpty() ) {
            	
                for ( OBOComponent deleteRelCompie: deleteRelComponents ) {
                	
                	ArrayList<Relationship> relationships = 
                			(ArrayList<Relationship>) relationshipDAO.listByParentFKAndChildFK(Long.valueOf(deleteRelCompie.getChildOfs().get(0)), Long.valueOf(deleteRelCompie.getID()));

                	Iterator<Relationship> iteratorrelationship = relationships.iterator();

                  	while (iteratorrelationship.hasNext()) {
                  		
                  		Relationship relationship = iteratorrelationship.next();
                  		
                  		relationshipDAO.delete(relationship);
                  	}

                  	/*
                  	ArrayList<RelationshipProject> relationshipprojects = 
                  			(ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK(Long.valueOf(deleteRelCompie.getDBID()));
                  	
                  	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                  	while (iteratorrelationship.hasNext()) {

                  		RelationshipProject relationshipproject = iteratorrelationshipproject.next();
                  		
                  		relationshipprojectDAO.delete(relationshipproject);
                  	}
                  	*/
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }

    /*
    // 17-1 
	private void update_orderANA_RELATIONSHIP( HashMap<String, ArrayList<String>> mapParentChildren) {

        if (debug) {
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
                for ( Iterator<String> i = mapParentChildren.keySet().iterator(); i.hasNext(); ) {
                    parent = i.next();
                    //get dbid of parent
                    parentDBID = getDatabaseIdentifier(parent);
                    //reset order for each parent
                    intSEQ = -1; 
                    //reset number of child records for each parent
                    intNumRecords = 0; 

                    //get number of child records for each parent from database
                    ArrayList<Relationship> relationships = 
                    		(ArrayList<Relationship>) relationshipDAO.listByParentFK(Long.valueOf(parentDBID));
                    intNumRecords = relationships.size();
                    
                    //iterate through all children for each parent
                    //if children==null, no child has an order in the proposed file
                    //update all relationship entries to sequence=null
                    if ( mapParentChildren.get(parent) == null ) {
                    	
                      	Iterator<Relationship> iteratorrelationship = relationships.iterator();

                      	while (iteratorrelationship.hasNext()) {
                      		Relationship relationship = iteratorrelationship.next();

                            intREL_OID = relationship.getOid().intValue();
                            
                            ArrayList<RelationshipProject> relationshipprojects = 
                            		(ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK((long) intREL_OID); 

                          	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                          	while (iteratorrelationshipproject.hasNext()) {
                          		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                          		relationshipproject.setSequence((long) 0);
                          		relationshipprojectDAO.create(relationshipproject);
                          	}
                      	}
                    }
                    else {
                        for (String child: mapParentChildren.get(parent)) {
                            //get dbid of child
                            childDBID = getDatabaseIdentifier(child);
                            //get rel_oid of this relationship
                            ArrayList<Relationship> relationshipsparentchild = 
                            		(ArrayList<Relationship>) relationshipDAO.listByParentFKAndChildFK(Long.valueOf(parentDBID), Long.valueOf(childDBID)); 

                          	Iterator<Relationship> iteratorrelationshipparentchild = relationshipsparentchild.iterator();

                          	while (iteratorrelationshipparentchild.hasNext()) {
                          		Relationship relationship = iteratorrelationshipparentchild.next();
                          	
                          		intREL_OID = relationship.getOid().intValue();

                                //put in ordered children rows cache for later comparison
                                orderedchildren.add( Integer.toString(intREL_OID) );
                                intSEQ++;
                                
                                ArrayList<RelationshipProject> relationshipprojects = 
                                		(ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK((long) intREL_OID); 

                              	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                              	while (iteratorrelationshipproject.hasNext()) {
                              		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                              		relationshipproject.setSequence((long) intSEQ);
                              		relationshipprojectDAO.create(relationshipproject);
                              	}

                                
                          	}
                        }
                    }
                    
                    //if there are more child records than the number of ordered children
                    // fill up the rest with rel_sequence = null
                    if ( orderedchildren.size()<intNumRecords ) {
                        //prepare string of ordered rel_oid
                        String strOrdered = "";
                        
                        for(String orderedchild: orderedchildren ) {
                            strOrdered = strOrdered + orderedchild + ",";
                        }
                        //get all relationship entries that are unordered
                        ArrayList<Relationship> relationshipsunordered = 
                        		(ArrayList<Relationship>) relationshipDAO.listByParentFK(Long.valueOf(parentDBID));
                        
                      	Iterator<Relationship> iteratorrelationship = relationshipsunordered.iterator();

                      	while (iteratorrelationship.hasNext()) {
                      		Relationship relationship = iteratorrelationship.next();
                      		
                      		intREL_OID = relationship.getOid().intValue();

                            ArrayList<RelationshipProject> relationshipprojects = 
                            		(ArrayList<RelationshipProject>) relationshipprojectDAO.listByRelationshipFK((long) intREL_OID); 

                          	Iterator<RelationshipProject> iteratorrelationshipproject = relationshipprojects.iterator();

                          	while (iteratorrelationshipproject.hasNext()) {
                          		RelationshipProject relationshipproject = iteratorrelationshipproject.next();

                          		relationshipproject.setSequence((long) 0);
                          		relationshipprojectDAO.create(relationshipproject);
                          	}

                      	}
                    }
                }
            }
        }
        catch ( DAOException dao ) {
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	setProcessed(false);
        	ex.printStackTrace();
        } 
    }
    */

    // 18-1
    @SuppressWarnings("null")
	public Vector< String > recursiveGetDependentDescendants(String componentID, Vector< String > componentIDs, boolean invalidDelete) {

        if (debug) {
        	
            System.out.println("18-1 - recursiveGetDependentDescendants");
        }

        Vector< String > descendants = componentIDs;
        Vector< String > childrenIDs = new Vector<String>();

        OBOComponent component = new OBOComponent();
        OBOComponent deletedcomponent = new OBOComponent();
        
        try {
        	
            ArrayList<JOINNodeRelationshipNode> joinnoderelationshipnodes = 
            		(ArrayList<JOINNodeRelationshipNode>) joinnoderelationshipnodeDAO.listAllByParentId(componentID);
            
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
                    component = tree.getComponent(s);

                    if ( component==null ) {
                
                    	invalidDelete = true;
                        deletedcomponent = tree.getComponent( s );
                        
                        deletedcomponent.setCheckComment("Delete Record " +
                            "Warning: Deletion of this term results in " +
                            "orphan term " + component.getID() + " " +
                            component.getName() + ". Please generate a new OBO " +
                            "file from the database and retry deletion.");

                        unDeletedCompList.add(deletedcomponent);
                    }
                    else if ( !component.getStatusChange().equals("DELETED") ) {

                    	component.setStatusChange("DELETED");
                        invalidDelete = true;
                        deletedcomponent = tree.getComponent( s );

                        deletedcomponent.setCheckComment("Delete Record " +
                            "Warning: Deletion of this term results in " +
                            "orphan term " + component.getID() + " " +
                            component.getName() + ". Please generate a new " +
                            "OBO file from the database and retry " +
                            "deletion.");

                        unDeletedCompList.add(deletedcomponent);
                    }
                    
                    descendants.add( s );
                    descendants = recursiveGetDependentDescendants( s, descendants, invalidDelete );
                }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed(false);
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed(false);
        	ex.printStackTrace();
        } 

        return descendants;
    } 

    // Helpers ------------------------------------------------------------------------------------
    /*
     * method to sort through modified component list for changed stages
     */
    // 08
    private ArrayList< OBOComponent > getChangedStagesTermList( ArrayList<OBOComponent> changedTermList ) {

        if (debug) {

        	System.out.println("08 - getChangedStagesTermList");
        }
        
        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        
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
    private ArrayList< OBOComponent > getChangedNamesTermList( ArrayList<OBOComponent> changedTermList ) {

        if (debug) {

            System.out.println("10 - getChangedNamesTermList");
        }
        
        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
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
    private ArrayList< OBOComponent > getChangedSynonymsTermList( ArrayList<OBOComponent> changedTermList ) {

        if (debug) {

            System.out.println("12 - getChangedSynonymsTermList");
        }

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
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
    private ArrayList< OBOComponent > getChangedParentsTermList( ArrayList<OBOComponent> changedTermList ) {

        if (debug) {

        	System.out.println("14 - getChangedParentsTermList");
        }

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

    /*
     * method to sort through modified component list for changed synonyms
     */
    // 15.25
    private ArrayList< OBOComponent > getChangedPrimaryStatusTermList( ArrayList<OBOComponent> changedTermList ) {

        if (debug) {

            System.out.println("15.25 - getChangedPrimaryStatusTermList");
        }
        
        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("Different Primary Status") ) {
        		
        		termList.add( component );
            }
        }
        
        return termList;
    }

    /*
     * method to sort through modified component list for changed parents
     */
    // 18
    private ArrayList< OBOComponent > getNewParentsTermList( ArrayList<OBOComponent> changedTermList ) {

        if (debug) {

        	System.out.println("18 - getNewParentsTermList");
        }

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	
        	if ( component.hasDifferenceComment("New Parents") ) {
        		
        		termList.add( component );

        		/*
            	if (component.getID().equals("EMAPA:18305")) {
                    System.out.println("GenerateSQL.java");
                    System.out.println("----------------");
                    System.out.println("Changed getNewParentsTermList: " + component.getID());
                    System.out.println("component");
                    System.out.println("component.toString() " + component.toString());
                    System.out.println("component.getCheckComments() " + component.getCheckComments());
                    System.out.println("component.getChildOfs().toString()" + component.getChildOfs().toString());
                    System.out.println("component.getChildOfTypes().toString()" + component.getChildOfTypes().toString());
                    System.out.println("----------------");
            	}
            	*/
            }
        }
        
        return termList;
    }

    /*
     * method to sort through modified component list for changed synonyms
     */
    /*
    // 16
    private ArrayList< OBOComponent > getChangedOrderTermList( ArrayList<OBOComponent> changedTermList ) {

        System.out.println("16 - getChangedOrderTermList");

        ArrayList<OBOComponent> termList = new ArrayList<OBOComponent>();
        
        for ( OBOComponent component: changedTermList ) {
        	if ( component.hasDifferenceComment("Different Order") ) {
        		termList.add( component );
            }
        }
        
        return termList;
    }
    */

}
