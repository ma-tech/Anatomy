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
*                   mouse011GudmapLocalhost"
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package main.support;

import utility.Wrapper;
import obolayer.OBOFactory;
import obolayer.OBOProperty;
import routines.runnable.FindNodeInPerspective;

public class MainFindNodeInPerspective{

	public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

		if (args.length != 3 ) {
			
		    Wrapper.printMessage("ERROR! There MUST be 3 Command Line Arguments passed to this program!", "*", "*");
        }
        else {
        
        	OBOProperty oboproperty = new OBOProperty();
        	oboproperty.setOBOProperty(args[0], args[1]);
        	OBOFactory obofactory = OBOFactory.getInstance(args[1]);

            char separator = ',';
            String searchType = "ComponentName";
            String searchTerm = "adventitia of seminal vesicle";

            FindNodeInPerspective.run( obofactory, "/Users/mwicks/Desktop/Ontology/urogenitalTerms.csv", separator, searchType, searchTerm);

            searchType = "PublicId";
            searchTerm = "EMAPA:16039";

            FindNodeInPerspective.run( obofactory, "/Users/mwicks/Desktop/Ontology/renalTerms.csv", separator, searchType, searchTerm);

            searchTerm = "EMAPA:29794";
        	
            FindNodeInPerspective.run( obofactory, "/Users/mwicks/Desktop/Ontology/adultKidneyGenepaintTerms.csv", separator, searchType, searchTerm);
        
            FindNodeInPerspective.run( obofactory, "/Users/mwicks/Desktop/Ontology/wholeMouseTerms.csv", separator, searchType, searchTerm);
        }

        Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
