/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RebuildDerivedDataTables.java
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
* Description:  A Class that rebuilds the Derived Data Tables in the Anatomy Database;
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; April 2014; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.runnable;

import java.util.ArrayList;

import utility.Wrapper;

import anatomy.TreeAnatomy;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import daolayer.DAOFactory;

import routines.aggregated.ListOBOComponentsFromExistingDatabase;

import routines.aggregated.EmptyDerivedTables;
import routines.aggregated.RebuildAnadPartOf;
import routines.aggregated.RebuildAnadRelationshipTransitive;
import routines.aggregated.RebuildAnadPartOfPerspective;


public class RebuildDerivedDataTables {
	

	public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("RebuildAnadPartOf.run", "***", obofactory.getMsgLevel());
	    
        // import Database from dao.properties
	    ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true, "" );
	    
	    // Get all the Components in the Part-Onomy
	    ArrayList<OBOComponent> arraylistOBOComponent = importdatabase.getObocomponentPartOnomy();

	    // Build a Tree from all the Components in the Part-Onomy
	    TreeAnatomy treeanatomyPartOnomy = new TreeAnatomy(obofactory.getMsgLevel(), arraylistOBOComponent);
	    
	    // rebuild ANAD_PART_OF
	    EmptyDerivedTables.run(daofactory, true, false, false);
	    RebuildAnadPartOf.run(daofactory, obofactory, treeanatomyPartOnomy, arraylistOBOComponent);

	    // rebuild ANAD_RELATIONSHIP_TRANSITIVE
	    EmptyDerivedTables.run(daofactory, false, true, false);
	    RebuildAnadRelationshipTransitive.run(daofactory, obofactory);

	    // rebuild ANAD_PART_OF_PERSPECTIVE
	    EmptyDerivedTables.run(daofactory, false, false, true);
	    RebuildAnadPartOfPerspective.run(daofactory, obofactory);

	}
}