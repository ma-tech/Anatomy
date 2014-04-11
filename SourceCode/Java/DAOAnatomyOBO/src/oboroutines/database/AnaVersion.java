/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerOBO
*
* Title:        AnaVersion.java
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
* Description:  A Wrapper Class for the Table ANA_VERSION;
* 
*                Constructor requires a DAOFactory Object;
*                 Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Main Methods:
*                1. insertANA_VERSION
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

import utility.Wrapper;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.VersionDAO;

import daomodel.Version;

import obomodel.OBOComponent;


public class AnaVersion {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private VersionDAO versionDAO;
    
    //input Version list 
    private ArrayList<Version> versionList;
    
    
    // Constructors -------------------------------------------------------------------------------
    public AnaVersion() {
    	
    }

    public AnaVersion( DAOFactory daofactory) {

    	try {
    		
            this.daofactory = daofactory;        	

            Wrapper.printMessage("anaversion.constructor", "***", this.daofactory.getMsgLevel());
            
            this.versionList = new ArrayList<Version>();

        	this.versionDAO = daofactory.getDAOImpl(VersionDAO.class);

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
    
    
    //  Insert a new row into ANA_VERSION
    public boolean insertANA_VERSION() throws Exception {

        Wrapper.printMessage("anaversion.insertANA_VERSION", "***", this.daofactory.getMsgLevel());
        	
        long longVersionEntries = 0;

        this.versionList.clear();
        
        try {
        	
      	    //insert into ANA_OBJECT
            OBOComponent vercomponent = new OBOComponent();
            ArrayList< OBOComponent > verTermList = new ArrayList<OBOComponent>();
            verTermList.add( vercomponent );
            
            AnaObject anaobject = new AnaObject( this.daofactory );
            
            if ( !anaobject.insertANA_OBJECT( verTermList, "ANA_VERSION" ) ) {

         	   throw new DatabaseException("anaversion.insertANA_VERSION : insertANA_OBJECT:ANA_VERSION");
            }
            
            //find out which round of update this is to the db
     	    longVersionEntries = this.versionDAO.countAll();
     	   
            if ( !anaobject.getMaxOID() ) {

            	throw new DatabaseException("anaversion.insertANA_VERSION : anaobject.getMaxOID");
            }

            //prepare values for insertion
            long longVER_OID = anaobject.getCurrentMaxObjectId();

            long longVER_NUMBER = ++longVersionEntries;
            String strVER_DATE = utility.MySQLDateTime.now();
            String strVER_COMMENTS = "DB2OBO Update - Editing the ontology";

            Version version = new Version(longVER_OID, longVER_NUMBER, strVER_DATE, strVER_COMMENTS);

        	this.versionList.add(version);

            this.versionDAO.create(version);
            
           	// Update ANA_OBJECT
            if ( !anaobject.updateANA_OBJECTinsertANA_VERSION(this.versionList) ) {

          	   throw new DatabaseException("ananode.insertVERSION : updateANA_OBJECTinsertANA_VERSION");
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
