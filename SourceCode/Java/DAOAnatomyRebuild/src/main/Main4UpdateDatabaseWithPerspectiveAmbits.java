/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        MainUpdateDatabaseWithPerspectiveAmbits.java
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
* Description:  A Main Class that Updates Anatomy Database with the Perspecive Ambits from File
*
* Usage:       "main.MainUpdateDatabaseFromComponentsTables
*                /Users/mwicks/GitMahost/Anatomy/Properties/obo.properties.input 
*                 mouse011JenkinsOBOfile 
*                  /Users/mwicks/GitMahost/Anatomy/Properties/dao.properties.input 
*                   mouse011GudmapLocalhost
*                    /Users/mwicks/GitMahost/Anatomy/Database/Tasks/CreateMouse012/Work/CSV/mousePerspectiveAmbits.csv"
* 
* Parameters:  1. The Location of the OBO Properties file
*              2. The Key to the set selected OBO Properties 
*              3. The Location of the DAO Properties file
*              4. The Key to the set selected DAO Properties
*              5. The Location of the CSV File containing the Perspective Ambit rows
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

import utility.Wrapper;
import daolayer.DAOFactory;
import daolayer.DAOProperty;
import obolayer.OBOFactory;
import obolayer.OBOProperty;
import routines.runnable.UpdateDatabaseWithBaseData;
import routines.runnable.UpdateDatabaseWithPerspectiveAmbits;

public class Main4UpdateDatabaseWithPerspectiveAmbits{

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 5 ) {
			
		    Wrapper.printMessage("ERROR! There MUST be 5 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);

        	DAOProperty daoproperty = new DAOProperty();
        	daoproperty.setDAOProperty(args[2], args[3]);
            DAOFactory daofactory = DAOFactory.getInstance(args[3]);

            char separator = ';';

            UpdateDatabaseWithBaseData.run( daofactory, obofactory, args[4], separator );
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
