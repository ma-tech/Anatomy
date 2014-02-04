/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        AnaSynonym.java
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
* Description:  A Wrapper Class for the Table ANA_SYNONYM;
* 
*                Constructor requires a DAOFactory Object;
*                 Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Methods:
*                1. insertANA_SYNONYM
*                2. updateANA_SYNONYM
*                3. deleteANA_SYNONYM
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import utility.Wrapper;
import utility.MySQLDateTime;

import daointerface.LogDAO;
import daointerface.SynonymDAO;
import daointerface.ThingDAO;
import daointerface.VersionDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.Log;
import daomodel.Synonym;
import daomodel.Thing;
import daomodel.Version;

import obomodel.OBOComponent;

public class AnaSynonym {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel; 
	
    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private SynonymDAO synonymDAO;
    private ThingDAO thingDAO;
    private LogDAO logDAO;
    private VersionDAO versionDAO;
    
    private long longLOG_VERSION_FK;

    
    // Constructors -------------------------------------------------------------------------------
    public AnaSynonym() {
    	
    }

    public AnaSynonym( String requestMsgLevel, DAOFactory daofactory) {
    	
    	try {
    		
           	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("anasynonym.constructor", "***", this.requestMsgLevel);

            this.daofactory = daofactory;

        	this.synonymDAO = daofactory.getDAOImpl(SynonymDAO.class);
        	this.thingDAO = daofactory.getDAOImpl(ThingDAO.class);
        	this.versionDAO = daofactory.getDAOImpl(VersionDAO.class);
        	this.logDAO = daofactory.getDAOImpl(LogDAO.class);

        	Version version = versionDAO.findMostRecent();
            this.longLOG_VERSION_FK = version.getOid();

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
    
    
    //  Insert new rows into ANA_SYNONYM
    public boolean insertANA_SYNONYM( ArrayList<OBOComponent> newTermList, String calledFrom ) throws Exception {
    	
        Wrapper.printMessage("anasynonym.insertANA_SYNONYM : " + calledFrom, "***", this.requestMsgLevel);
        	
        setProcessed( true );
        
        OBOComponent component;

        ArrayList<OBOComponent> synonymCompList = new ArrayList<OBOComponent>();

        try {
            
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
        	   
               AnaObject anaobject = new AnaObject(this.requestMsgLevel, this.daofactory);
               
               if ( !anaobject.insertANA_OBJECT(synonymCompList, "ANA_SYNONYM") ) {

            	   throw new DatabaseException("anasynonym.insertANA_SYNONYM : insertANA_OBJECT");
               }
               
               for ( OBOComponent synComponent: synonymCompList ) {

                   //proceed with insertion
                   int intSYN_OID = Integer.parseInt( synComponent.getDBID() );
                   int intSYN_OBJECT_FK = Integer.parseInt( synComponent.getID() );
                   String strSYN_SYNONYM = synComponent.getName();

                   Synonym synonym = new Synonym((long) intSYN_OID, (long) intSYN_OBJECT_FK, strSYN_SYNONYM);

                  	if ( !logANA_SYNONYM( synonym, calledFrom) ) {
                		
                    	throw new DatabaseException("anasynonym.insertANA_SYNONYM : logANA_SYNONYM");
                	}

                   this.synonymDAO.create(synonym);
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

    
    //  Delete rows from ANA_SYNONYM
    public boolean deleteANA_SYNONYM( ArrayList<OBOComponent> deleteSynComponents, String calledFrom ) throws Exception {

        Wrapper.printMessage("anasynonym.deleteANA_SYNONYM : " + calledFrom , "***", this.requestMsgLevel);
        	
        try {
        	
        	ArrayList<Synonym> synonyms = new ArrayList<Synonym>();
        	
        	if ( !deleteSynComponents.isEmpty() ) {
            	
                for ( OBOComponent deletesynonymcomponent: deleteSynComponents ) {
                	
                	synonyms = (ArrayList<Synonym>) synonymDAO.listByObjectFK(Long.valueOf( deletesynonymcomponent.getDBID() ));
                			
                    Iterator<Synonym> iteratorSynonyms = synonyms.iterator();
                    
                    while ( iteratorSynonyms.hasNext() ) {
                    	
                    	Synonym synonym = iteratorSynonyms.next();

                    	Thing thing = thingDAO.findByOid(synonym.getOid()); 
                    	
                       	if ( !logANA_SYNONYM( synonym, calledFrom) ) {
                    		
                        	throw new DatabaseException("anasynonym.deleteANA_SYNONYM : logANA_SYNONYM");
                    	}

                        thingDAO.delete(thing);

                    	synonymDAO.delete(synonym);
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

    
    // update existing rows in ANA_SYNONYM
	public boolean updateANA_SYNONYM( ArrayList<OBOComponent> newSynonymsTermList, 
			ArrayList<OBOComponent> oldSynonymsTermList, 
			String calledFrom ) throws Exception {

        Wrapper.printMessage("anasynonym.updateANA_SYNONYM : " + calledFrom , "***", this.requestMsgLevel);
    	
        try {
        	
        	if ( newSynonymsTermList.isEmpty() ) {

        		//System.out.println("newSynonymsTermList.isEmpty() = " + newSynonymsTermList.isEmpty());
        	}
        	else {
        		
        		//System.out.println("newSynonymsTermList.isEmpty() = " + newSynonymsTermList.isEmpty());

        		//insert Synonyms in ANA_SYNONYM
                if ( !insertANA_SYNONYM( newSynonymsTermList, "UPDATE" ) ) {

                	throw new DatabaseException("anasynonym.updateANA_SYNONYM : insertANA_SYNONYM");
                }
        	}
        	
        	if ( oldSynonymsTermList.isEmpty() ) {

        		//System.out.println("oldSynonymsTermList.isEmpty() = " + oldSynonymsTermList.isEmpty());
        	}
        	else {
        		
                //delete Synonyms in ANA_SYNONYM
                if ( !deleteANA_SYNONYM( oldSynonymsTermList, "UPDATE" ) ) {

                	throw new DatabaseException("anasynonym.updateANA_SYNONYM : deleteANA_SYNONYM");
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
        
        return isProcessed();
    }

      
    //  Insert into ANA_LOG for ANA_SYNONYM Insertions or Deletions
    public boolean logANA_SYNONYM( Synonym synonym, String calledFrom ) throws Exception {

        Wrapper.printMessage("anasynonym.logANA_SYNONYM : " + calledFrom, "***", this.requestMsgLevel);
        	
        long longLogLoggedOID = 0;
        long longLogOID = 0;
        
        HashMap<String, String> synOldValues = new HashMap<String, String>(); 

        //ANA_SYNONYM columns
        Vector<String> vSYNcolumns = new Vector<String>();
        vSYNcolumns.add("SYN_OID");
        vSYNcolumns.add("SYN_OBJECT_FK");
        vSYNcolumns.add("SYN_SYNONYM");

        //column values for selection from ANA_SYNONYM
        //int intSYN_OID = 0;

        String strLOG_COMMENTS = "";
        String strLOG_DATETIME = MySQLDateTime.now();

        if ( calledFrom.equals("INSERT") ) {
        	
        	strLOG_COMMENTS = "INSERT Synonyms";
        }
        else if ( calledFrom.equals("DELETE") ) {
        	
        	strLOG_COMMENTS = "DELETE Synonyms";
        }
        else if ( calledFrom.equals("UPDATE") ) {
        	
        	strLOG_COMMENTS = "UDPATE Synonyms";
        }
        else {
        	
        	strLOG_COMMENTS = "UNKNOWN REASON for Synonym INSERT into ANA_LOG";
        }
        
        //column values for insertion into ANA_LOG
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
   
        try {

        	//get max log_oid from new database
        	longLogOID = utility.ObjectConverter.convert(logDAO.maximumOid(), Long.class);

            //clear HashMap synOldValues
            synOldValues.clear();
            synOldValues.put("SYN_OID", utility.ObjectConverter.convert(synonym.getOid(), String.class ) );
            synOldValues.put("SYN_OBJECT_FK", utility.ObjectConverter.convert(synonym.getThingFK(), String.class ) );
            synOldValues.put("SYN_SYNONYM", synonym.getName());

            longLogLoggedOID = synonym.getOid();
            
            for (String columnName: vSYNcolumns) {
            	
            	longLogOID++;
                strLOG_COLUMN_NAME = columnName;
                strLOG_OLD_VALUE = synOldValues.get(columnName);

                Log log = new Log(longLogOID, longLogLoggedOID, this.longLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS, strLOG_DATETIME, "ANA_SYNONYM");
                
                logDAO.create(log);
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
