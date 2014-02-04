/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
*
* Title:        MainUpdateDatabaseFromComponentsTables.java
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
* Description:  A Main Class that Updates Anatomy Database From the Components Tables
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
package main;

import app.gudmap.RebuildRelationshipProjectFromComponentsTables;
import app.gudmap.RunOBOResetComponentsOrderAlpha;
import utility.Wrapper;
import daolayer.DAOFactory;
import daolayer.DAOProperty;
import obolayer.OBOFactory;
import obolayer.OBOProperty;
import daointerface.ThingDAO;
import routines.runnable.UpdateDatabaseFromComponentsTables;


public class MainUpdateDatabaseFromComponentsTables{

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 4) {
			
		    Wrapper.printMessage("ERROR! There MUST be 4 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);

        	DAOProperty daoproperty = new DAOProperty();
        	daoproperty.setDAOProperty(args[2], args[3]);
            DAOFactory daofactory = DAOFactory.getInstance(args[3]);

            UpdateDatabaseFromComponentsTables.run( obofactory.getMsgLevel(), daofactory, obofactory );
            
            RunOBOResetComponentsOrderAlpha.run( obofactory.getMsgLevel(), daofactory );
            
            RebuildRelationshipProjectFromComponentsTables.run( obofactory.getMsgLevel(), daofactory );
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
