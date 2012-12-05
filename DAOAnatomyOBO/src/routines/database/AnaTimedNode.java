/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        AnaTimedNode.java
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
* Version: 1
*
* Description:  A Wrapper Class for the Table ANA_TIMED_NODE;
*                Constructor requires a DAOFactory Object;
*                Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Methods:
*                1. insertANA_TIMED_NODE
*                2. createTimeComponents
*                3. deleteANA_TIMED_NODE
*                4. updateANA_TIMED_NODE
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; November 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.database;

import java.util.ArrayList;

import daolayer.TimedNodeDAO;
import daolayer.StageDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.TimedNode;
import daomodel.Stage;

import obomodel.OBOComponent;

import routines.database.AnaObject;

import utility.Wrapper;

public class AnaTimedNode {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel; 
	
    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private TimedNodeDAO timednodeDAO;
    private StageDAO stageDAO;

    // Constructors -------------------------------------------------------------------------------
    public AnaTimedNode() {
    	
    }

    public AnaTimedNode( String requestMsgLevel, DAOFactory daofactory) {
    	
    	try {
    		
           	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("anatimednode.constructor", "LOW", this.requestMsgLevel);

            this.daofactory = daofactory;

        	this.timednodeDAO = daofactory.getTimedNodeDAO();
        	this.stageDAO = daofactory.getStageDAO();

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
    
    
    //  Insert new rows into ANA_TIMED_NODE
    public boolean insertANA_TIMED_NODE( ArrayList<OBOComponent> newTermList, String calledFrom, String strSpecies) throws Exception {

        Wrapper.printMessage("anatimednode.insertANA_TIMED_NODE:" + calledFrom, "LOW", this.requestMsgLevel);
    		
        OBOComponent component;
        boolean flagInsert = false;

        try {
        	
            //create timed components in ANA_OBJECT
            ArrayList<OBOComponent> timedComps = new ArrayList<OBOComponent>();
            
            for ( int i = 0; i< newTermList.size(); i++) {
         	   
                component = newTermList.get(i);

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

                //INSERT timed component obj_oids into ANA_OBJECT
                AnaObject anaobject = new AnaObject( this.requestMsgLevel, this.daofactory);
                
                if ( !anaobject.getMaxPublicId() ) {

              	   throw new DatabaseException("insertANA_OBJECT for getMaxPublicId");
                 }

                int intCurrentPublicID = anaobject.getCurrentMaxPublicId() + 1;
                
                if (flagInsert) {
             	   
                    //make a time component record for each stage
                    for (int j = component.getStartSequence(); j <= component.getEndSequence(); j++ ) {

                        OBOComponent timedComponent = new OBOComponent();
                        		
                        timedComponent.setNamespace( component.getDBID() ); 
                        //current component
                        timedComponent.setStartSequence(j, strSpecies);
                        
                        char padChar = '0';
                        
                        intCurrentPublicID = intCurrentPublicID + 1;

                        if (strSpecies.equals("mouse")) {
                     	   
                            timedComponent.setID( "EMAP:" + Integer.toString( intCurrentPublicID ) );
                            timedComponent.setDisplayId( "EMAP:" + utility.StringPad.pad(intCurrentPublicID, 7, padChar) );
                        }
                        else if (strSpecies.equals("human")) {
                     	   
                            timedComponent.setID( "EHDA:" + Integer.toString( intCurrentPublicID ) );
                            timedComponent.setDisplayId( "EHDA:" + utility.StringPad.pad(intCurrentPublicID, 7, padChar) );
                        }
                        else if (strSpecies.equals("chick")) {
                     	   
                            timedComponent.setID( "ECAP:" + Integer.toString( intCurrentPublicID ) );
                            timedComponent.setDisplayId( "ECAP:" + utility.StringPad.pad(intCurrentPublicID, 7, padChar) );
                        }
                        else {
                     	   
                            Wrapper.printMessage("anatimednode.insertANA_TIMED_NODE:" + calledFrom + ";" + "UNKNOWN Species Value = " + strSpecies, "HIGH", this.requestMsgLevel);
                        }

                        timedComps.add(timedComponent);
                    }
                }
            }

        
            if ( !timedComps.isEmpty() ) {
            	
                //INSERT timed component obj_oids into ANA_OBJECT
                AnaObject anaobject = new AnaObject( this.requestMsgLevel, this.daofactory);
                
                if ( !anaobject.insertANA_OBJECT(timedComps, "ANA_TIMED_NODE") ) {

               	   throw new DatabaseException("insertANA_OBJECT:ANA_NODE");
                }

                int intPrevNode = 0;
                int intCompieStage = 0;

                AnaLog analog = new AnaLog(  this.requestMsgLevel, this.daofactory );
                
                //insert TimedNodes to be deleted in ANA_LOG
                if ( !analog.insertANA_LOG_TimedNodes( timedComps, "INSERT" ) ) {

                	throw new DatabaseException("insertANA_LOG_TimedNodes");
                }

                for (int k = 0; k< timedComps.size(); k++) {
                	
                    component = timedComps.get(k);

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
                    
                    Stage stage = this.stageDAO.findBySequence((long) intCompieStage);
                    intATN_STAGE_FK = stage.getOid().intValue();
                    
                    //String strATN_STAGE_MODIFIER_FK == null
                    String strATN_PUBLIC_ID = component.getID();
                    String strATN_DISPLAY_ID = component.getDisplayId();
                    
                    TimedNode timednode = new TimedNode((long) intATN_OID, (long) intATN_NODE_FK, (long) intATN_STAGE_FK, null, strATN_PUBLIC_ID, strATN_DISPLAY_ID);
                    
                    this.timednodeDAO.create(timednode);

                    intPrevNode = intATN_NODE_FK;
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
    
    
    //  Delete rows from ANA_TIMED_NODE 
    public boolean deleteANA_TIMED_NODE( ArrayList<OBOComponent> deleteTimedComponents, String calledFrom ) throws Exception {

        Wrapper.printMessage("anatimednode.deleteANA_TIMED_NODE:" + calledFrom, "LOW", this.requestMsgLevel);
        	
        try {
        	
            if ( !deleteTimedComponents.isEmpty() ) {

            	for ( OBOComponent component: deleteTimedComponents ) {

            		TimedNode timednode = timednodeDAO.findByOid(Long.valueOf(component.getDBID()));

            		timednodeDAO.delete(timednode);
            	}
            }
            
            AnaObject anaobject = new AnaObject(this.requestMsgLevel, this.daofactory);
            
            if ( !anaobject.insertANA_OBJECT(deleteTimedComponents, "ANA_TIMED_NODE") ) {

           	   throw new DatabaseException("deleteANA_OBJECT:ANA_TIMED_NODE");
            }
            
            AnaLog analog = new AnaLog( this.requestMsgLevel, this.daofactory );
            
            //insert TimedNodes to be deleted in ANA_LOG
            if ( !analog.insertANA_LOG_TimedNodes( deleteTimedComponents, "DELETE" ) ) {

            	throw new DatabaseException("insertANA_LOG_TimedNodes");
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
    
    
    //  update rows in ANA_TIMED_NODE
	public boolean updateANA_TIMED_NODE( ArrayList<OBOComponent> changedTimedTermList, String calledFrom, String strSpecies) throws Exception {

        Wrapper.printMessage("anatimednode.updateANA_TIMED_NODE:" + calledFrom, "LOW", this.requestMsgLevel);
        	
        try {
        	
            //insert time components in ANA_TIMED_NODE
            if ( !insertANA_TIMED_NODE( changedTimedTermList, "MODIFY", strSpecies) ) {

            	throw new DatabaseException("insertANA_TIMED_NODE");
            }
            
            //delete time components in ANA_TIMED_NODE
            if ( !deleteANA_TIMED_NODE( changedTimedTermList, "UPDATE" ) ) {

            	throw new DatabaseException("insertANA_TIMED_NODE");
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
