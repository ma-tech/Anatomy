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
* Version:      1
*
* Description:  A Wrapper Class for the Table ANA_OBJECT;
* 
*                Constructor requires a DAOFactory Object;
*                 Pass the Class Methods: a List of OBOComponents; CalledFrom String
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
package oboroutines.database;

import java.util.ArrayList;
import java.util.Iterator;

import utility.Wrapper;
import utility.ObjectConverter;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.NodeDAO;
import daointerface.ThingDAO;
import daointerface.TimedNodeDAO;

import daomodel.Relationship;
import daomodel.Synonym;
import daomodel.Version;
import daomodel.TimedNode;
import daomodel.Node;
import daomodel.Thing;

import obomodel.OBOComponent;

public class AnaObject {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private ThingDAO thingDAO;
    private NodeDAO nodeDAO;
    private TimedNodeDAO timednodeDAO;
    
    private long longCurrentMaxObjectId;
    private long longCurrentMaxPublicId;

    // Updated list of Components with new OIDs in
    private ArrayList<OBOComponent> updatedNewTermList;

    
    // Constructors -------------------------------------------------------------------------------
    public AnaObject() {
    
    }

    public AnaObject( DAOFactory daofactory) {
    	
    	try {

            this.daofactory = daofactory;

            Wrapper.printMessage("anaobject.constructor", "***", this.daofactory.getMsgLevel());

        	this.thingDAO = daofactory.getDAOImpl(ThingDAO.class);
        	this.nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
        	this.timednodeDAO = daofactory.getDAOImpl(TimedNodeDAO.class);

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

        Wrapper.printMessage("anaobject.insertANA_OBJECT : Inserts for Table : " + calledFromTable, "***", this.daofactory.getMsgLevel());
        	
        OBOComponent component = new OBOComponent();

        try {
        	
            if ( !getMaxOID() ) {

            	throw new DatabaseException("anaobject.insertANA_OBJECT : getMaxOID()");
            }

            long longOBJ_OID = 0;
            String datetime = utility.MySQLDateTime.now();
            long sysadmin = 2;
            String description = "";

            if ( !newTermList.isEmpty() ) {

                for ( int i=0; i<newTermList.size(); i++ ) {
                	
                    component = newTermList.get(i);

                    //database values
                    longOBJ_OID = ++this.longCurrentMaxObjectId;

                    //update new components with ano_oid/atn_oid
                    component.setDBID(ObjectConverter.convert(longOBJ_OID, String.class));

                    Thing thing = new Thing(longOBJ_OID, datetime, sysadmin, description, calledFromTable);

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


    // update rows in ANA_OBJECT for New Inserts to ANA_NDOE
    public boolean updateANA_OBJECTinsertANA_NODE( ArrayList<Node> newNodeList ) throws Exception {

    	setProcessed( true );

        Wrapper.printMessage("anaobject.updateANA_OBJECTinsertANA_NODE", "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            if ( !newNodeList.isEmpty() ) {

            	Iterator<Node> iteratorNodes = newNodeList.iterator();
            	
                while ( iteratorNodes.hasNext() ) {
                	
                	Node node = iteratorNodes.next();

                    Thing thing = this.thingDAO.findByOid(node.getOid());
                    
                    if ( thing == null ) {
                    	
                    	throw new DatabaseException("ananode.updateANA_OBJECTinsertANA_NODE : Object Not Found for OID = " + node.getOid());
                    }
                    else {
                    	
                    	thing.setDescription(node.toStringThing());
                    	this.thingDAO.update(thing);
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

    // update rows in ANA_OBJECT for New Inserts to ANA_TIMED_NDOE
    public boolean updateANA_OBJECTinsertANA_TIMED_NODE( ArrayList<TimedNode> newTimedNodeList ) throws Exception {

    	setProcessed( true );

        Wrapper.printMessage("anaobject.updateANA_OBJECTinsertANA_TIMED_NODE", "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            if ( !newTimedNodeList.isEmpty() ) {

            	Iterator<TimedNode> iteratorTimedNodes = newTimedNodeList.iterator();
            	
                while ( iteratorTimedNodes.hasNext() ) {
                	
                	TimedNode timednode = iteratorTimedNodes.next();

                    Thing thing = this.thingDAO.findByOid(timednode.getOid());
                    
                    if ( thing == null ) {
                    	
                    	throw new DatabaseException("ananode.updateANA_OBJECTinsertANA_TIMED_NODE : Object Not Found for OID = " + timednode.getOid());
                    }
                    else {
                    	
                    	thing.setDescription(timednode.toStringThing());
                    	this.thingDAO.update(thing);
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


    // update rows in ANA_OBJECT for New Inserts to ANA_VERSION
    public boolean updateANA_OBJECTinsertANA_VERSION( ArrayList<Version> newVersionList ) throws Exception {

    	setProcessed( true );

        Wrapper.printMessage("anaobject.updateANA_OBJECTinsertANA_VERSION", "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            if ( !newVersionList.isEmpty() ) {

            	Iterator<Version> iteratorVersions = newVersionList.iterator();
            	
                while ( iteratorVersions.hasNext() ) {
                	
                	Version version = iteratorVersions.next();

                    Thing thing = this.thingDAO.findByOid(version.getOid());
                    
                    if ( thing == null ) {
                    	
                    	throw new DatabaseException("ananode.updateANA_OBJECTinsertANA_VERSION : Object Not Found for OID = " + version.getOid());
                    }
                    else {
                    	
                    	thing.setDescription(version.toStringThing());
                    	this.thingDAO.update(thing);
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

    // update rows in ANA_OBJECT for New Inserts to tANA_RELATIONSHIP
    public boolean updateANA_OBJECTinsertANA_RELATIONSHIP( ArrayList<Relationship> newRelationshipList ) throws Exception {

    	setProcessed( true );

        Wrapper.printMessage("anaobject.updateANA_OBJECTinsertANA_RELATIONSHIP", "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            if ( !newRelationshipList.isEmpty() ) {

            	Iterator<Relationship> iteratorRelationships = newRelationshipList.iterator();
            	
                while ( iteratorRelationships.hasNext() ) {
                	
                	Relationship relationship = iteratorRelationships.next();

                    Thing thing = this.thingDAO.findByOid(relationship.getOid());
                    
                    if ( thing == null ) {
                    	
                    	throw new DatabaseException("ananode.updateANA_OBJECTinsertANA_RELATIONSHIP : Object Not Found for OID = " + relationship.getOid());
                    }
                    else {
                    	
                    	thing.setDescription(relationship.toStringThing());
                    	this.thingDAO.update(thing);
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


    // update rows in ANA_OBJECT for New Inserts to ANA_SYNONYM
    public boolean updateANA_OBJECTinsertANA_SYNONYM( ArrayList<Synonym> newSynonymList ) throws Exception {

    	setProcessed( true );

        Wrapper.printMessage("anaobject.updateANA_OBJECTinsertANA_RELATIONSHIP", "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            if ( !newSynonymList.isEmpty() ) {

            	Iterator<Synonym> iteratorSynonyms = newSynonymList.iterator();
            	
                while ( iteratorSynonyms.hasNext() ) {
                	
                	Synonym synonym = iteratorSynonyms.next();

                    Thing thing = this.thingDAO.findByOid(synonym.getOid());
                    
                    if ( thing == null ) {
                    	
                    	throw new DatabaseException("ananode.updateANA_OBJECTinsertANA_RELATIONSHIP : Object Not Found for OID = " + synonym.getOid());
                    }
                    else {
                    	
                    	thing.setDescription(synonym.toStringThing());
                    	this.thingDAO.update(thing);
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

    //  Delete rows from ANA_OBJECT
    public boolean deleteANA_OBJECT( ArrayList<OBOComponent> deleteObjects, String calledFromTable ) throws Exception {

        Wrapper.printMessage("anaobject.deleteANA_OBJECT : Deletes to ANA_OBJECT for Table : " + calledFromTable, "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            if ( !deleteObjects.isEmpty() ) {

            	Iterator<OBOComponent> iteratorDeleteObjects = deleteObjects.iterator();
            	
                while ( iteratorDeleteObjects.hasNext() ) {
                	
                	OBOComponent deleteObject = iteratorDeleteObjects.next();

                	Thing thing = thingDAO.findByOid( ObjectConverter.convert(deleteObject.getDBID(), Long.class)); 

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

        Wrapper.printMessage("anaobject.getMaxObjectID()", "***", this.daofactory.getMsgLevel());
        	
        try {

        	//get max oid from referenced database
        	this.longCurrentMaxObjectId = this.thingDAO.maximumOid();

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

        Wrapper.printMessage("anaobject.getMaxPublicId()", "***", this.daofactory.getMsgLevel());
        	
    	try {

        	//get max oid from referenced database
        	long longCurrentMaxEMAPAId = this.nodeDAO.maximumEmapa();
        	long longCurrentMaxEMAPId = this.timednodeDAO.maximumEmap();
        	
        	if ( longCurrentMaxEMAPAId > longCurrentMaxEMAPId ) {
        		
        		this.longCurrentMaxPublicId = longCurrentMaxEMAPAId;
        	}
        	else {
        		
        		this.longCurrentMaxPublicId = longCurrentMaxEMAPId;
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
    public long getCurrentMaxPublicId() {
        return this.longCurrentMaxPublicId;
    }
    public long getCurrentMaxObjectId() {
        return this.longCurrentMaxObjectId;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
        this.processed = processed;
    }
    public void setUpdatedNewTermList( ArrayList<OBOComponent> updatedNewTermList) {
        this.updatedNewTermList = updatedNewTermList;
    }
    public void setCurrentMaxPublicId( long currentMaxPublicId ) {
        this.longCurrentMaxPublicId = currentMaxPublicId;
    }
    public void setCurrentMaxObjectId( long longCurrentMaxObjectId ) {
        this.longCurrentMaxObjectId = longCurrentMaxObjectId;
    }
}
