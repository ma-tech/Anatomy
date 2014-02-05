/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RebuildRelationshipProjectFromComponentsTables.java
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
* Description:  A Main Class that rebuilds the Relationship Project Table
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

import oboroutines.database.AnaRelationship;
import oboroutines.database.DatabaseException;

import daolayer.DAOFactory;

public class RebuildRelationshipProjectFromComponentsTables {

	public static void run( DAOFactory daofactory) throws Exception {

        // Rebuild ANA_RELATIONSHIP_PROJECT
        AnaRelationship anarelationship = new AnaRelationship( daofactory );

        if ( !anarelationship.rebuildANA_RELATIONSHIP_PROJECT()) {

     	   throw new DatabaseException("anarelationship.rebuildANA_RELATIONSHIP_PROJECT");
        }
	}
}