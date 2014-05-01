/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        Main7GenerateTreeReports.java
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
* Description:  A Main Class that prints the Tree Reports
*
* Usage:       "main.Main7GenerateTreeReports
*                /Users/mwicks/GitMahost/Anatomy/SourceCode/Properties/obo.properties.input 
*                 mouse012OBOfile 
*                  /Users/mwicks/GitMahost/Anatomy/SourceCode/Properties/dao.properties.input 
*                   mouse012Localhost 
*                    Whole-mouse 
*                     /Users/mwicks/GitMahost/Anatomy/Database/Versions/Mouse012/Formats/Trees/Text"
*                     
* Parameters:  1. The Location of the OBO Properties file
*              2. The Key to the set selected OBO Properties 
*              3. The Location of the DAO Properties file
*              4. The Key to the set selected DAO Properties
*              5. The Perspective you require: A. "Adult-Kidney-(GenePaint)", 
*                                              B. "Renal", 
*                                              C. "Urogenital", 
*                                              D. "Whole-mouse"
*              6. The Location of the Generated Reports
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

import routines.aggregated.ReportAbstractGroupsEmbedded;
import routines.aggregated.ReportAbstractGroupsTrailing;
import routines.aggregated.ReportStagedGroupsEmbedded;
import routines.aggregated.ReportStagedGroupsTrailing;


public class Main7GenerateTreeReports {

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 6 ) {
			
		    Wrapper.printMessage("ERROR! There MUST be 6 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);

        	DAOProperty daoproperty = new DAOProperty();
        	daoproperty.setDAOProperty(args[2], args[3]);
            DAOFactory daofactory = DAOFactory.getInstance(args[3]);

            String argPersepctive = args[4].replaceAll("-", " ");
            
            ReportAbstractGroupsEmbedded.run( obofactory, daofactory, argPersepctive, args[5]);
            
            ReportAbstractGroupsTrailing.run( obofactory, daofactory, argPersepctive, args[5]);

            ReportStagedGroupsEmbedded.run( obofactory, daofactory, argPersepctive, args[5]);

            ReportStagedGroupsTrailing.run( obofactory, daofactory, argPersepctive, args[5]);
            
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
