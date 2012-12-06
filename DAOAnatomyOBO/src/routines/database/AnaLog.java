/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        AnaLog.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2009 Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Log: 1
*
* Description:  A Wrapper Class for the Table ANA_LOG;
*                Constructor requires a DAOFactory Object;
*                Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Methods:
*                1. insertANA_LOG_TimedNodes
*                2. insertANA_LOG_Relationships
*                3. insertANA_LOG_Synonyms
*                4. insertANA_LOG_Nodes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; September 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daolayer.LogDAO;
import daolayer.NodeDAO;
import daolayer.RelationshipDAO;
import daolayer.SynonymDAO;
import daolayer.JOINTimedNodeStageDAO;

import daomodel.Log;
import daomodel.Node;
import daomodel.Relationship;
import daomodel.Synonym;
import daomodel.JOINTimedNodeStage;

import obomodel.OBOComponent;
import utility.Wrapper;

public class AnaLog {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel; 
	
    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private LogDAO logDAO;
    private NodeDAO nodeDAO;
    private RelationshipDAO relationshipDAO;
    private SynonymDAO synonymDAO;
    private JOINTimedNodeStageDAO jointimednodestageDAO;
    
    private int intCurrentLogID;

    
    // Constructors -------------------------------------------------------------------------------
    public AnaLog() {
    	
    }

    public AnaLog( String requestMsgLevel, DAOFactory daofactory) {

    	try {
    		
        	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("analog.constructor", "***", this.requestMsgLevel);

            this.daofactory = daofactory;

        	this.logDAO = daofactory.getLogDAO();
        	this.nodeDAO = daofactory.getNodeDAO();
        	this.relationshipDAO = daofactory.getRelationshipDAO();
        	this.synonymDAO = daofactory.getSynonymDAO();
        	this.jointimednodestageDAO = daofactory.getJOINTimedNodeStageDAO();

        	setProcessed( true );
    	}

        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
    }
    
    
    //  Insert into ANA_LOG for ANA_TIMED_NODE Insertions or Deletions
    public boolean insertANA_LOG_Nodes( ArrayList<OBOComponent> recordTermList, String strSpecies, String calledFrom ) throws Exception {

        Wrapper.printMessage("analog.insertANA_LOG_Nodes:" + calledFrom , "***", this.requestMsgLevel);
        	
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        
        //version_oid should be very first obj_oid created for easy tracing
        int intLOG_VERSION_FK = intCurrentLogID;
        String strLOG_COMMENTS = "";
        
        if ( calledFrom.equals("INSERT") ) {
        	
        	strLOG_COMMENTS = "Insert into ANA_LOG for INSERTED Nodes";
        }
        else if ( calledFrom.equals("DELETE") ) {
        	
        	strLOG_COMMENTS = "Insert into ANA_LOG for DELETED Nodes";
        }
        else {
        	
        	strLOG_COMMENTS = "UNKNOWN REASON for Nodes INSERT into ANA_LOG";
        }

        //create one record
        int intLogOID = 0;
        int intLogLoggedOID = 0;

        HashMap<String, String> anoOldValues = new HashMap<String, String>();
        
        //ANA_NODE columns
        Vector<String> vANOcolumns = new Vector<String>();
        vANOcolumns.add("ANO_OID");
        vANOcolumns.add("ANO_SPECIES_FK");
        vANOcolumns.add("ANO_COMPONENT_NAME");
        vANOcolumns.add("ANO_IS_PRIMARY");
        vANOcolumns.add("ANO_IS_GROUP");
        vANOcolumns.add("ANO_PUBLIC_ID");
        vANOcolumns.add("ANO_DESCRIPTION");
        vANOcolumns.add("ANO_DISPLAY_ID");
        
        int intIsPrimary = 0;
        int intIsGroup = 0;

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
                    anoOldValues.put( "ANO_PUBLIC_ID", component.getID() );
                    anoOldValues.put( "ANO_DESCRIPTION", "N/A" );
                    anoOldValues.put( "ANO_DISPLAY_ID", component.getDisplayId() );

                    //increment for each component
                    ++intLogLoggedOID;
                	
                    for (String columnName: vANOcolumns) {	
                    	
                        intLOG_OID = ++intLogOID;
                        intLOG_LOGGED_OID = intLogLoggedOID;
                        strLOG_COLUMN_NAME = columnName;
                        strLOG_OLD_VALUE = anoOldValues.get(columnName);
                        intLOG_VERSION_FK = intCurrentLogID;
                        strLOG_COMMENTS = "Insert into ANA_LOG for Valid Deleted Components from ANA_NODE";

                        Log log = new Log((long) intLOG_OID, (long) intLOG_LOGGED_OID, (long) intLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS);
                        
                        logDAO.create(log); 
                    }
                }
            }
        }

        catch ( DAOException dao ) {
        	
        	setProcessed( false );
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 

        return isProcessed();
    }
    
    
    //  Insert into ANA_LOG for ANA_TIMED_NODE Insertions or Deletions
    public boolean insertANA_LOG_TimedNodes( ArrayList<OBOComponent> recordTermList, String calledFrom ) throws Exception {

        Wrapper.printMessage("analog.insertANA_LOG_TimedNodes:" + calledFrom, "***", this.requestMsgLevel);
        	
        HashMap<String, String> atnOldValues = new HashMap<String, String>(); 
        int intLogLoggedOID = 0;
        int intLogOID = 0;
        int intStartKey = 0;
        int intEndKey = 0;
        
        //ANA_TIMED_NODE columns
        Vector<String> vATNcolumns = new Vector<String>();
        vATNcolumns.add("ATN_OID");
        vATNcolumns.add("ATN_STAGE_FK");
        vATNcolumns.add("ATN_STAGE_MODIFIER_FK");
        vATNcolumns.add("ATN_PUBLIC_ID");
        vATNcolumns.add("ATN_NODE_FK");
        vATNcolumns.add("ATN_DISPLAY_ID");
        
        //column values for selection from ANA_TIMED_NODE
        int intATN_OID = 0;
        int intATN_STAGE_FK = 0;
        String strATN_STAGE_MODIFIER_FK = "";
        String strATN_PUBLIC_ID = "";
        int intATN_NODE_FK = 0;
        String strATN_DISPLAY_ID = "";
        
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        
        //version_oid should be very first obj_oid created for easy tracing
        int intLOG_VERSION_FK = 0;
        String strLOG_COMMENTS = "";

        if ( calledFrom.equals("INSERT") ) {
        	
        	strLOG_COMMENTS = "Insert into ANA_LOG for INSERTED TimedNodes";
        }
        else if ( calledFrom.equals("DELETE") ) {
        	
        	strLOG_COMMENTS = "Insert into ANA_LOG for DELETED TimedNodes";
        }
        else {
        	
        	strLOG_COMMENTS = "UNKNOWN REASON for TimedNodes INSERT into ANA_LOG";
        }
        
        try {
        	
            AnaObject anaobject = new AnaObject( requestMsgLevel, this.daofactory );
            
            if ( !anaobject.getMaxOID() ) {

            	throw new DatabaseException("getMaxOID");
            }

            intLOG_VERSION_FK = anaobject.getCurrentMaxObjectId();

        	//get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
        	
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();

            if ( !recordTermList.isEmpty() ) {

            	for ( OBOComponent component: recordTermList ) {

                    intStartKey = component.getStartSequence();
                    intEndKey = component.getEndSequence();

                    for ( int stage=intStartKey; stage<=intEndKey; stage++ ) {

                        ArrayList<JOINTimedNodeStage> jointimednodestages = 
                        		(ArrayList<JOINTimedNodeStage>) jointimednodestageDAO.listAllByNodeFkAndStageSequence(Long.valueOf(component.getDBID()), (long) stage);

                        Iterator<JOINTimedNodeStage> iteratorjointimednodestage = jointimednodestages.iterator();

                      	while (iteratorjointimednodestage.hasNext()) {
                        
                      		JOINTimedNodeStage jointimednodestage = iteratorjointimednodestage.next();
                      		
                            intATN_OID = jointimednodestage.getOidTimedNode().intValue();
                            intATN_STAGE_FK = jointimednodestage.getStageFK().intValue();
                            strATN_STAGE_MODIFIER_FK = jointimednodestage.getStageModifierFK();
                            strATN_PUBLIC_ID = jointimednodestage.getPublicTimedNodeId();
                            intATN_NODE_FK = jointimednodestage.getNodeFK().intValue();
                            strATN_DISPLAY_ID = jointimednodestage.getDisplayTimedNodeId();
                            
                            //increment for each timed component record
                            ++intLogLoggedOID;

                            //clear HashMap atnOldValues
                            atnOldValues.clear();
                            
                            atnOldValues.put( "ATN_OID", Integer.toString( intATN_OID ) );
                            atnOldValues.put( "ATN_STAGE_FK", Integer.toString( intATN_STAGE_FK ) );
                            atnOldValues.put( "ATN_STAGE_MODIFIER_FK", strATN_STAGE_MODIFIER_FK );
                            atnOldValues.put( "ATN_PUBLIC_ID", strATN_PUBLIC_ID );
                            atnOldValues.put( "ATN_NODE_FK", Integer.toString( intATN_NODE_FK ) );
                            atnOldValues.put( "ATN_DISPLAY_ID", strATN_DISPLAY_ID );
                            
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
        	
        	setProcessed( false );
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 

        return isProcessed();
    }
    

    //  Insert into ANA_LOG for ANA_RELATIONSHIP Insertions or Deletions
    public boolean insertANA_LOG_Relationships( ArrayList<OBOComponent> recordTermList, String calledFrom ) throws Exception {

        Wrapper.printMessage("analog.insertANA_LOG_Relationships:" + calledFrom, "***", this.requestMsgLevel);
        	
        try {
        	
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
            int intLOG_VERSION_FK = 0; 
            String strLOG_COMMENTS = "";

            if ( calledFrom.equals("INSERT") ) {
            	
            	strLOG_COMMENTS = "Insert into ANA_LOG for INSERTED Relationships";
            }
            else if ( calledFrom.equals("DELETE") ) {
            	
            	strLOG_COMMENTS = "Insert into ANA_LOG for DELETED Relationships";
            }
            else {
            	
            	strLOG_COMMENTS = "UNKNOWN REASON for Relationship INSERT into ANA_LOG";
            }
            
            AnaObject anaobject = new AnaObject( requestMsgLevel, this.daofactory);

            if ( !anaobject.getMaxOID() ) {
           	   
            	throw new DatabaseException("getMaxOID");
            }

            intLOG_VERSION_FK = anaobject.getCurrentMaxObjectId();
        	
            //get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
        	
            //get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();
      	  
            if ( !recordTermList.isEmpty() ) {
            	
            	for ( OBOComponent component: recordTermList ) {
            		
                    //deleteParents = component.getChildOfs();
                    deleteParents = component.getChildOfs();

                    for ( String deleteParent: deleteParents ) {

                        //intParentDBID = getDatabaseIdentifier( deleteParent ); 
                    	Node node = nodeDAO.findByPublicId(deleteParent);
                    	
                        if ( node != null ) {
                        	
                        	intParentDBID = node.getOid().intValue();
                        }

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
                            else if ( relationship.getTypeFK().equals("attached-to")) {
                           	
                            	deleteRelComponent.addChildOfType("ATTACHED_TO");
                            }
                            else if ( relationship.getTypeFK().equals("has-part")) {
                           	
                            	deleteRelComponent.addChildOfType("HAS_PART");
                            }
                            else if ( relationship.getTypeFK().equals("connected-to")) {
                               	
                            	deleteRelComponent.addChildOfType("CONNECTED_TO");
                            }
                            else {
                            
                                Wrapper.printMessage("analog.insertANA_LOG_Relationships:" + calledFrom + ";" + "UNKNOWN Relationship Type = " + relationship.getTypeFK(), "*", this.requestMsgLevel);
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
        	
        	setProcessed( false );
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 

        return isProcessed();
    }

    
    //  Insert into ANA_LOG for ANA_SYNONYM Insertions or Deletions
    public boolean insertANA_LOG_Synonyms( ArrayList<OBOComponent> recordTermList, String calledFrom ) throws Exception {

        Wrapper.printMessage("analog.insertANA_LOG_Synonyms:" + calledFrom, "***", this.requestMsgLevel);
        	
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
        String strLOG_COMMENTS = "";

        if ( calledFrom.equals("INSERT") ) {
        	
        	strLOG_COMMENTS = "Insert into ANA_LOG for INSERTED Synonyms";
        }
        else if ( calledFrom.equals("DELETE") ) {
        	
        	strLOG_COMMENTS = "Insert into ANA_LOG for DELETED Synonyms";
        }
        else {
        	
        	strLOG_COMMENTS = "UNKNOWN REASON for Synonym INSERT into ANA_LOG";
        }
        
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        int intLOG_VERSION_FK = 0; 
   
        try {

        	AnaObject anaobject = new AnaObject( requestMsgLevel, this.daofactory);

            if ( !anaobject.getMaxOID() ) {

            	throw new DatabaseException("getMaxOID");
            }

            intLOG_VERSION_FK = anaobject.getCurrentMaxObjectId();
        	
        	//get max log_oid from new database
        	intLogOID = logDAO.maximumOid();
            
        	//get max log_logged_oid from new database
        	intLogLoggedOID = logDAO.maximumLoggedOid();

            if ( !recordTermList.isEmpty() ) {

            	for ( OBOComponent component: recordTermList ) {

                    deleteSynonyms = component.getSynonyms();

                    for ( String deleteSynonym: deleteSynonyms ) {

                        ArrayList<Synonym> synonymlist = (ArrayList<Synonym>) synonymDAO.listByObjectFKAndSynonym(Long.valueOf(component.getDBID()), deleteSynonym);
                        
                        //add to temporary component
                      	Iterator<Synonym> iteratorsynonym = synonymlist.iterator();

                      	while (iteratorsynonym.hasNext()) {
                      		
                      		Synonym synonym = iteratorsynonym.next();

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
        	
        	setProcessed( false );
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 
        
        return isProcessed();
    }    
    
    
    // Getters ------------------------------------------------------------------------------------
    public boolean isProcessed() {
        return this.processed;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
        this.processed = processed;
    }
}
