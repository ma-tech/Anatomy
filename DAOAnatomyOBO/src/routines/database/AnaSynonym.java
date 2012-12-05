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

import daolayer.SynonymDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.Synonym;

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

    
    // Constructors -------------------------------------------------------------------------------
    public AnaSynonym() {
    	
    }

    public AnaSynonym( String requestMsgLevel, DAOFactory daofactory) {
    	
    	try {
    		
           	this.requestMsgLevel = requestMsgLevel;

            Wrapper.printMessage("anasynonym.constructor", "LOW", this.requestMsgLevel);

            this.daofactory = daofactory;

        	this.synonymDAO = daofactory.getSynonymDAO();

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
    	
        Wrapper.printMessage("anasynonym.insertANA_SYNONYM:" + calledFrom, "LOW", this.requestMsgLevel);
        	
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

            	   throw new DatabaseException("insertANA_OBJECT for ANA_SYNONYM");
               }
               
               AnaLog analog = new AnaLog( this.requestMsgLevel,this.daofactory );
               
               //insert Synonyms to be deleted in ANA_LOG
               if ( !analog.insertANA_LOG_Synonyms( synonymCompList, "INSERT" ) ) {

               	throw new DatabaseException("insertANA_LOG_Synonyms");
               }

               for ( OBOComponent synCompie: synonymCompList ) {

                   //proceed with insertion
                   int intSYN_OID = Integer.parseInt( synCompie.getDBID() );
                   int intSYN_OBJECT_FK = Integer.parseInt( synCompie.getID() );
                   String strSYN_SYNONYM = synCompie.getName();

                   Synonym synonym = new Synonym((long) intSYN_OID, (long) intSYN_OBJECT_FK, strSYN_SYNONYM);
                   
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

        Wrapper.printMessage("anasynonym.deleteANA_SYNONYM:" + calledFrom , "LOW", this.requestMsgLevel);
        	
        try {
        	
            if ( !deleteSynComponents.isEmpty() ) {
            	
                for ( OBOComponent deletesynonymcomponent: deleteSynComponents ) {
                	
                	Synonym synonym = synonymDAO.findByOid( Long.valueOf( deletesynonymcomponent.getDBID() ) );
                	
                	synonymDAO.delete(synonym);
                }

                AnaObject anaobject = new AnaObject( this.requestMsgLevel, this.daofactory);
                
                if ( !anaobject.deleteANA_OBJECT( deleteSynComponents, "ANA_SYNONYM" ) ) {

               	   throw new DatabaseException("deleteANA_OBJECT for ANA_SYNONYM");
                }

                AnaLog analog = new AnaLog( this.requestMsgLevel, this.daofactory );
                
                //insert Synonyms to be deleted in ANA_LOG
                if ( !analog.insertANA_LOG_Synonyms( deleteSynComponents, "DELETE" ) ) {

                	throw new DatabaseException("insertANA_LOG_TimedNodes");
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

        Wrapper.printMessage("anasynonym.updateANA_SYNONYM:" + calledFrom , "LOW", this.requestMsgLevel);
    	
        try {
        	
            //insert Synonyms in ANA_SYNONYM
            if ( insertANA_SYNONYM( newSynonymsTermList, "UPDATE" ) ) {

            	throw new DatabaseException("updateANA_SYNONYM");
            }
            
            //delete Synonyms in ANA_SYNONYM
            if ( deleteANA_SYNONYM( oldSynonymsTermList, "UPDATE" ) ) {

            	throw new DatabaseException("updateANA_SYNONYM");
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
