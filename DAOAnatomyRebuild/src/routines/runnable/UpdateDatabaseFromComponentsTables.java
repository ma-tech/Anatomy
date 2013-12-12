/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
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
* Version:      1
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
import oboroutines.GenerateSQL;
import oboroutines.MapBuilder;
import oboroutines.TreeBuilder;
import oboroutines.ValidateComponents;

import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;


import daolayer.DAOFactory;

import utility.Wrapper;

public class UpdateDatabaseFromComponentsTables {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("updatedatabasefromcomponentstables.run", "***", requestMsgLevel);

	    ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( requestMsgLevel, daofactory, obofactory );
    	MapBuilder newmapbuilder = new MapBuilder( requestMsgLevel, importcomponents.getTermList());
        TreeBuilder newtreebuilder = new TreeBuilder( requestMsgLevel, newmapbuilder);

        //import Database from dao.properties, anatomy008.url
	    ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( requestMsgLevel, daofactory, obofactory, true );
        MapBuilder oldmapbuilder = new MapBuilder( requestMsgLevel, importdatabase.getTermList());
        TreeBuilder oldtreebuilder = new TreeBuilder( requestMsgLevel, oldmapbuilder);

        //check for rules violation
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
    	    
    		Wrapper.printMessage("updatedatabasefromcomponentstables.run : NEW component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

        Iterator<OBOComponent> iteratorModTerms = modComponents.iterator();
        
     	while (iteratorModTerms.hasNext()) {
    		
    		OBOComponent component = iteratorModTerms.next();

    		Wrapper.printMessage("updatedatabasefromcomponentstables.run : MOD component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

        Iterator<OBOComponent> iteratorDelTerms = delComponents.iterator();
        
     	while (iteratorDelTerms.hasNext()) {
    		
    		OBOComponent component = iteratorDelTerms.next();

    		Wrapper.printMessage("updatedatabasefromcomponentstables.run : DEL component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

        if ( newComponents.size() > 0 ) {
        	
            // Update the Database for NEW Components
        	GenerateSQL generatesql = new GenerateSQL( requestMsgLevel, daofactory, obofactory, newComponents, newtreebuilder, oldtreebuilder );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - NEW - SUCCESS!", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
            }
            else {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - NEW - FAILURE!", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
            }
        }
        
        if ( modComponents.size() > 0 ) {
        	
            // Update the Database for Modified Components
        	GenerateSQL generatesql = new GenerateSQL( requestMsgLevel, daofactory, obofactory, modComponents, newtreebuilder, oldtreebuilder );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - MOD - SUCCESS!", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
            }
            else {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - MOD - FAILURE!", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
            }
        }

        if ( delComponents.size() > 0 ) {
        	
            // Update the Database for Deleted Components
        	GenerateSQL generatesql = new GenerateSQL( requestMsgLevel, daofactory, obofactory, delComponents, newtreebuilder, oldtreebuilder );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - DEL - SUCCESS!", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
            }
            else {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - DEL - FAILURE!", "***", requestMsgLevel);
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", requestMsgLevel);
            }
        }
    }
}