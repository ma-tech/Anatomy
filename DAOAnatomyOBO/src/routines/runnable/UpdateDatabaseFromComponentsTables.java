/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        UpdateDatabaseFromComponentsTables.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  A Main Class that Reads an OBO File and Loads it into an existing 
*                Anatomy database;
*
*               Required Files:
*                1. dao.properties file contains the database access attributes
*                2. obo.properties file contains the OBO file access attributes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.runnable;

import java.util.ArrayList;
import java.util.Iterator;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;
import routines.base.GenerateSQL;
import routines.base.MapBuilder;
import routines.base.TreeBuilder;
import routines.base.ValidateComponents;

import daolayer.DAOFactory;

import utility.Wrapper;

public class UpdateDatabaseFromComponentsTables {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("ListOBOComponentsFromComponentsTables", "LOW", requestMsgLevel);
    	ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( requestMsgLevel, daofactory, obofactory );
    	MapBuilder newmapbuilder = new MapBuilder( requestMsgLevel, importcomponents.getTermList());
        TreeBuilder newtreebuilder = new TreeBuilder( requestMsgLevel, newmapbuilder);

        //import Database from dao.properties, anatomy008.url
	    Wrapper.printMessage("ListOBOComponentsFromExistingDatabase", "LOW", requestMsgLevel);
	    ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( requestMsgLevel, daofactory, obofactory, true );
        MapBuilder oldmapbuilder = new MapBuilder( requestMsgLevel, importdatabase.getTermList());
        TreeBuilder oldtreebuilder = new TreeBuilder( requestMsgLevel, oldmapbuilder);

        //check for rules violation
	    Wrapper.printMessage("ValidateComponents", "LOW", requestMsgLevel);
        
        ValidateComponents validatecomponents =
            new ValidateComponents( requestMsgLevel,
            		obofactory, 
            		importcomponents.getTermList(), 
            		importdatabase.getTermList(), 
            		newtreebuilder);

        ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();
        newComponents = validatecomponents.getNewTermList();
        
        ArrayList<OBOComponent> modComponents = new ArrayList<OBOComponent>();
        modComponents = validatecomponents.getModifiedTermList();
        
        ArrayList<OBOComponent> delComponents = new ArrayList<OBOComponent>();
        delComponents = validatecomponents.getDeletedTermList();
        
        Iterator<OBOComponent> iteratorNewTerms = newComponents.iterator();
        
     	while (iteratorNewTerms.hasNext()) {
    		
    		OBOComponent component = iteratorNewTerms.next();
    	    
    		Wrapper.printMessage("NEW component.toString() " + component.toString(), "LOW", requestMsgLevel);
     	}

        Iterator<OBOComponent> iteratorModTerms = modComponents.iterator();
        
     	while (iteratorModTerms.hasNext()) {
    		
    		OBOComponent component = iteratorModTerms.next();

    		Wrapper.printMessage("MOD component.toString() " + component.toString(), "LOW", requestMsgLevel);
     	}

        Iterator<OBOComponent> iteratorDelTerms = delComponents.iterator();
        
     	while (iteratorDelTerms.hasNext()) {
    		
    		OBOComponent component = iteratorDelTerms.next();

    		Wrapper.printMessage("DEL component.toString() " + component.toString(), "LOW", requestMsgLevel);
     	}

        if ( newComponents.size() > 0 ) {
        	
            // Update the Database for NEW Components
    	    Wrapper.printMessage("GenerateSQL - NEW", "LOW", requestMsgLevel);

        	GenerateSQL generatesql = new GenerateSQL( requestMsgLevel, daofactory, obofactory, newComponents, newtreebuilder, oldtreebuilder );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("GenerateSQL - NEW - SUCCESS!", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
            }
            else {
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("GenerateSQL - NEW - FAILURE!", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
            }
        }
        
        if ( modComponents.size() > 0 ) {
        	
            // Update the Database for Modified Components
    	    Wrapper.printMessage("GenerateSQL - MOD", "LOW", requestMsgLevel);

        	GenerateSQL generatesql = new GenerateSQL( requestMsgLevel, daofactory, obofactory, modComponents, newtreebuilder, oldtreebuilder );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("GenerateSQL - MOD - SUCCESS!", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
            }
            else {
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("GenerateSQL - MOD - FAILURE!", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
            }
        }

        if ( delComponents.size() > 0 ) {
        	
            // Update the Database for Deleted Components
    	    Wrapper.printMessage("GenerateSQL - DEL", "LOW", requestMsgLevel);

        	GenerateSQL generatesql = new GenerateSQL( requestMsgLevel, daofactory, obofactory, delComponents, newtreebuilder, oldtreebuilder );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("GenerateSQL - DEL - SUCCESS!", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
            }
            else {
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("GenerateSQL - DEL - FAILURE!", "MEDIUM", requestMsgLevel);
        	    Wrapper.printMessage("===========   ---   --------", "MEDIUM", requestMsgLevel);
            }
        }
    }
}