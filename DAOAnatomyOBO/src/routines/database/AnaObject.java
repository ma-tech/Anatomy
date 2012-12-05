/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        AnaObject.java
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
* Description:  A Wrapper Class for the Table ANA_OBJECT;
*                Constructor requires a DAOFactory Object;
*                Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Methods:
*                1. insertANA_OBJECT
*                2. deleteANA_OBJECT
*                3. getMaxObjectID
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

import daolayer.DAOException;
import daolayer.DAOFactory;

import daolayer.ThingDAO;
import daolayer.NodeDAO;
import daolayer.TimedNodeDAO;

import daomodel.Thing;

import obomodel.OBOComponent;
import utility.Wrapper;

public class AnaObject {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel; 
	
    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private ThingDAO thingDAO;
    private NodeDAO nodeDAO;
    private TimedNodeDAO timednodeDAO;
    
    private int currentMaxObjectId;
    private int currentMaxPublicId;

    // Updated list of Components with new OIDs in
    private ArrayList<OBOComponent> updatedNewTermList;

    
    // Constructors -------------------------------------------------------------------------------
    public AnaObject() {
    
    }

    public AnaObject( String requestMsgLevel, DAOFactory daofactory) {
    	
    	try {

        	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("anaobject.constructor", "LOW", this.requestMsgLevel);

            this.daofactory = daofactory;
        	
        	this.thingDAO = daofactory.getThingDAO();
        	this.nodeDAO = daofactory.getNodeDAO();
        	this.timednodeDAO = daofactory.getTimedNodeDAO();

        	this.updatedNewTermList = new ArrayList<OBOComponent>(); 
        	
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
    
    // Insert new rows into ANA_OBJECT
    public boolean insertANA_OBJECT( ArrayList<OBOComponent> newTermList, String calledFromTable ) throws Exception {

    	setProcessed( true );

        Wrapper.printMessage("anaobject.insertANA_OBJECT:Inserts to Table:" + calledFromTable, "LOW", this.requestMsgLevel);
        	
        OBOComponent component = new OBOComponent();

        try {
        	
            if ( !getMaxOID() ) {

            	throw new DatabaseException("insertANA_OBJECT for getMaxOID()");
            }

            int intOBJ_OID = 0;
            String datetime = utility.MySQLDateTime.now();
            long sysadmin = 2;
            String description = "";

            if ( !newTermList.isEmpty() ) {

                for ( int i=0; i<newTermList.size(); i++ ) {
                	
                    component = newTermList.get(i);

                    //database values
                    intOBJ_OID = ++this.currentMaxObjectId;

                    //update new components with ano_oid/atn_oid
                    component.setDBID(Integer.toString(intOBJ_OID));

                    Thing thing = new Thing((long) intOBJ_OID, datetime, sysadmin, calledFromTable, description);

                    this.thingDAO.create(thing);
                    
                    this.updatedNewTermList.add( component );
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


    //  Delete rows from ANA_OBJECT
    public boolean deleteANA_OBJECT( ArrayList<OBOComponent> deleteObjects, String calledFromTable ) throws Exception {

        Wrapper.printMessage("anaobject.deleteANA_OBJECT:Deletes to ANA_OBJECT for Table:" + calledFromTable, "LOW", this.requestMsgLevel);
        	
        try {
        	
            if ( !deleteObjects.isEmpty() ) {

                for ( OBOComponent deleteObject: deleteObjects ) {
                	
                    Thing thing = thingDAO.findByOid(Long.valueOf(deleteObject.getDBID())); 

                    thingDAO.delete(thing);
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
    
    
    // getMaxObjectID - OID Surrogate Keys
    public boolean getMaxOID() throws Exception {

        Wrapper.printMessage("anaobject.getMaxObjectID()", "LOW", this.requestMsgLevel);
        	
        try {

        	//get max oid from referenced database
        	this.currentMaxObjectId = this.thingDAO.maximumOid();

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
    
    
    // getMaxPublicId - Public EMAPA/EMAP from ANA_NODE/ANA_TIMED_NODE
    public boolean getMaxPublicId() throws Exception {

        Wrapper.printMessage("anaobject.getMaxPublicId()", "LOW", this.requestMsgLevel);
        	
    	try {

        	//get max oid from referenced database
        	int currentMaxEMAPAId = this.nodeDAO.maximumEmapa();
        	int currentMaxEMAPId = this.timednodeDAO.maximumEmap();
        	
        	if ( currentMaxEMAPAId > currentMaxEMAPId ) {
        		
        		this.currentMaxPublicId = currentMaxEMAPAId;
        	}
        	else {
        		
        		this.currentMaxPublicId = currentMaxEMAPId;
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
    public ArrayList<OBOComponent> getUpdatedNewTermList() {
        return this.updatedNewTermList;
    }
    public int getCurrentMaxPublicId() {
        return this.currentMaxPublicId;
    }
    public int getCurrentMaxObjectId() {
        return this.currentMaxObjectId;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
        this.processed = processed;
    }
    public void setUpdatedNewTermList( ArrayList<OBOComponent> updatedNewTermList) {
        this.updatedNewTermList = updatedNewTermList;
    }
    public void setCurrentMaxPublicId( int currentMaxPublicId ) {
        this.currentMaxPublicId = currentMaxPublicId;
    }
    public void setCurrentMaxObjectId( int currentMaxObjectId ) {
        this.currentMaxObjectId = currentMaxObjectId;
    }
}
