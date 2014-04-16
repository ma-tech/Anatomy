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
* Description:  A Class that Reads an OBO File and Loads it into an existing Anatomy database;
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

import utility.Wrapper;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import oboroutines.GenerateSQL;
import oboroutines.ValidateComponents;

import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;

import daolayer.DAOFactory;

import anatomy.TreeAnatomy;


public class UpdateDatabaseFromComponentsTables {

	public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("updatedatabasefromcomponentstables.run", "***", obofactory.getMsgLevel());

	    ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
	    
	    // Get all the Components in the Components Tables
	    ArrayList<OBOComponent> arraylistComponentsTables = importcomponents.getTermList();

	    // Build a Tree from all the Components in the Part-Onomy
	    TreeAnatomy treeanatomyComponentsTables = new TreeAnatomy(obofactory.getMsgLevel(), arraylistComponentsTables);

	    
	    // import Database from dao.properties
	    ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true, "" );
	    
	    // Get all the Components in the Part-Onomy
	    ArrayList<OBOComponent> arraylistOBOComponentExistingDB = importdatabase.getObocomponentAllOnomy();


        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( obofactory, 
            		arraylistComponentsTables, 
            		arraylistOBOComponentExistingDB,
            		treeanatomyComponentsTables);

        
        ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();
        newComponents = validatecomponents.getNewTermList();
        
        ArrayList<OBOComponent> modComponents = new ArrayList<OBOComponent>();
        modComponents = validatecomponents.getModifiedTermList();
        
        ArrayList<OBOComponent> delComponents = new ArrayList<OBOComponent>();
        delComponents = validatecomponents.getDeletedTermList();
        
        Iterator<OBOComponent> iteratorNewTerms = newComponents.iterator();
        
     	while (iteratorNewTerms.hasNext()) {
    		
    		OBOComponent component = iteratorNewTerms.next();
    	    
    		Wrapper.printMessage("updatedatabasefromcomponentstables.run : NEW component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

        Iterator<OBOComponent> iteratorModTerms = modComponents.iterator();
        
     	while (iteratorModTerms.hasNext()) {
    		
    		OBOComponent component = iteratorModTerms.next();

    		Wrapper.printMessage("updatedatabasefromcomponentstables.run : MOD component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

        Iterator<OBOComponent> iteratorDelTerms = delComponents.iterator();
        
     	while (iteratorDelTerms.hasNext()) {
    		
    		OBOComponent component = iteratorDelTerms.next();

    		Wrapper.printMessage("updatedatabasefromcomponentstables.run : DEL component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

        if ( newComponents.size() > 0 ) {
        	
            // Update the Database for NEW Components
        	GenerateSQL generatesql = new GenerateSQL( daofactory, obofactory, newComponents, treeanatomyComponentsTables );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - NEW - SUCCESS!", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
            }
            else {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - NEW - FAILURE!", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
            }
        }
        
        if ( modComponents.size() > 0 ) {
        	
            // Update the Database for Modified Components
        	GenerateSQL generatesql = new GenerateSQL( daofactory, obofactory, modComponents, treeanatomyComponentsTables );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - MOD - SUCCESS!", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
            }
            else {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - MOD - FAILURE!", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
            }
        }

        if ( delComponents.size() > 0 ) {
        	
            // Update the Database for Deleted Components
        	GenerateSQL generatesql = new GenerateSQL( daofactory, obofactory, delComponents,treeanatomyComponentsTables );

            if ( generatesql.isProcessed()) {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - DEL - SUCCESS!", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
            }
            else {
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : GenerateSQL - DEL - FAILURE!", "***", obofactory.getMsgLevel());
        	    Wrapper.printMessage("updatedatabasefromcomponentstables.run : ===========   ---   --------", "***", obofactory.getMsgLevel());
            }
        }
    }
}