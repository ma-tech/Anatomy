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
* Version: 1
*
* Description:  A Wrapper Class for the Table ANA_SYNONYM;
*                Constructor requires a DAOFactory Object;
*                Pass the Class Methods: a List of OBOComponents; CalledFrom String
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
package routines.database;

import java.util.ArrayList;
import java.util.Iterator;

import daolayer.SynonymDAO;
import daolayer.ThingDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.Synonym;
import daomodel.Thing;

import obomodel.OBOComponent;

import utility.Wrapper;

public class AnaSynonym {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    private String requestMsgLevel; 
	
    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private SynonymDAO synonymDAO;
    private ThingDAO thingDAO;

    
    // Constructors -------------------------------------------------------------------------------
    public AnaSynonym() {
    	
    }

    public AnaSynonym( String requestMsgLevel, DAOFactory daofactory) {
    	
    	try {
    		
           	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("anasynonym.constructor", "***", this.requestMsgLevel);

            this.daofactory = daofactory;

        	this.synonymDAO = daofactory.getSynonymDAO();
        	this.thingDAO = daofactory.getThingDAO();

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
               
               for ( OBOComponent synCompie: synonymCompList ) {

                   //proceed with insertion
                   int intSYN_OID = Integer.parseInt( synCompie.getDBID() );
                   int intSYN_OBJECT_FK = Integer.parseInt( synCompie.getID() );
                   String strSYN_SYNONYM = synCompie.getName();

                   Synonym synonym = new Synonym((long) intSYN_OID, (long) intSYN_OBJECT_FK, strSYN_SYNONYM);
                   
                   this.synonymDAO.create(synonym);
               }
               
               AnaLog analog = new AnaLog( this.requestMsgLevel,this.daofactory );
               
               //insert Synonyms to be deleted in ANA_LOG
               if ( !analog.insertANA_LOG_Synonyms( synonymCompList, "INSERT" ) ) {

               	throw new DatabaseException("anasynonym.insertANA_SYNONYM : insertANA_LOG_Synonyms");
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

                        thingDAO.delete(thing);

                    	synonymDAO.delete(synonym);
                    }
                }
                
                if ( !synonyms.isEmpty() ) {
                	
                    AnaLog analog = new AnaLog( this.requestMsgLevel, this.daofactory );
                    
                    //insert Synonyms to be deleted in ANA_LOG
                    if ( !analog.insertANA_LOG_Synonyms( deleteSynComponents, "DELETED" ) ) {

                    	throw new DatabaseException("anasynonym.deleteANA_SYNONYM : insertANA_LOG_Synonyms");
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
                if ( insertANA_SYNONYM( newSynonymsTermList, "UPDATE" ) ) {

                	throw new DatabaseException("anasynonym.updateANA_SYNONYM : insertANA_SYNONYM");
                }
        	}
        	
        	if ( oldSynonymsTermList.isEmpty() ) {

        		//System.out.println("oldSynonymsTermList.isEmpty() = " + oldSynonymsTermList.isEmpty());
        	}
        	else {
        		
                //delete Synonyms in ANA_SYNONYM
                if ( deleteANA_SYNONYM( oldSynonymsTermList, "UPDATE" ) ) {

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

      
    // Getters ------------------------------------------------------------------------------------
    public boolean isProcessed() {
        return this.processed;
    }
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
        this.processed = processed;
    }
}
