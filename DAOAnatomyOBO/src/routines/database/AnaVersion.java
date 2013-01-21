/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
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
* Version: 1
*
* Description:  A Wrapper Class for the Table ANA_VERSION;
*                Constructor requires a DAOFactory Object;
*                Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Methods:
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
package routines.database;

import java.util.ArrayList;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.VersionDAO;

import daomodel.Version;

import obomodel.OBOComponent;

import utility.Wrapper;

public class AnaVersion {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel; 
	
    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private VersionDAO versionDAO;
    
    
    // Constructors -------------------------------------------------------------------------------
    public AnaVersion() {
    	
    }

    public AnaVersion( String requestMsgLevel, DAOFactory daofactory) {

    	try {
    		
           	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("anaversion.constructor", "***", this.requestMsgLevel);

            this.daofactory = daofactory;
        	
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

        Wrapper.printMessage("anaversion.insertANA_VERSION", "***", this.requestMsgLevel);
        	
        int intVersionEntries = 0;

        try {
        	
      	    //insert into ANA_OBJECT
            OBOComponent vercomponent = new OBOComponent();
            ArrayList< OBOComponent > verTermList = new ArrayList<OBOComponent>();
            verTermList.add( vercomponent );
            
            AnaObject anaobject = new AnaObject(this.requestMsgLevel, this.daofactory);
            
            if ( !anaobject.insertANA_OBJECT( verTermList, "ANA_VERSION" ) ) {

         	   throw new DatabaseException("anaversion.insertANA_VERSION : insertANA_OBJECT:ANA_VERSION");
            }
            
            //find out which round of update this is to the db
     	    intVersionEntries = this.versionDAO.countAll();
     	   
            if ( !anaobject.getMaxOID() ) {

            	throw new DatabaseException("anaversion.insertANA_VERSION : anaobject.getMaxOID");
            }

            //prepare values for insertion
            int intVER_OID = anaobject.getCurrentMaxObjectId();

            int intVER_NUMBER = ++intVersionEntries;
            String strVER_DATE = utility.MySQLDateTime.now();
            String strVER_COMMENTS = "DB2OBO Update - Editing the ontology";

            Version version = new Version((long) intVER_OID, (long) intVER_NUMBER, strVER_DATE, strVER_COMMENTS);

            this.versionDAO.create(version);

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
