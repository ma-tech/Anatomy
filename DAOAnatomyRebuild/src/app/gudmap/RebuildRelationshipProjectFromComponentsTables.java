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
package app.gudmap;

import daolayer.DAOFactory;

import routines.database.AnaRelationship;
import routines.database.DatabaseException;


public class RebuildRelationshipProjectFromComponentsTables {

	public static void run(String requestMsgLevel, DAOFactory daofactory) throws Exception {

        // Rebuild ANA_RELATIONSHIP_PROJECT
        AnaRelationship anarelationship = new AnaRelationship( requestMsgLevel, daofactory );

        if ( !anarelationship.rebuildANA_RELATIONSHIP_PROJECT()) {

     	   throw new DatabaseException("anarelationship.rebuildANA_RELATIONSHIP_PROJECT");
        }
	}
}