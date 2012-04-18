/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOLoadFileIntoDatabase.java
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

package App;

import java.util.ArrayList;

import OBOModel.ComponentFile;

import Utility.ImportFile;
import Utility.ImportDatabase;
import Utility.MapBuilder;
import Utility.TreeBuilder;
import Utility.GenerateSQL;

public class RunOBOLoadFileIntoDatabase {
	/*
	 * run Method
	 */
    public static void run() throws Exception {
        //import Obo File from obo.properties, file.oboinfile
        ImportFile importfile = new ImportFile();
        ArrayList<ComponentFile> parseNewTermList = importfile.getTermList();

        //import Database from dao.properties, anatomy008.url
        ImportDatabase importdatabase = new ImportDatabase(true, "EMAP" );
        ArrayList<ComponentFile> parseOldTermList = importdatabase.getTermList();

        //Build hashmap of NEW components
        MapBuilder newmapbuilder = new MapBuilder(parseNewTermList);
        //Build tree
        TreeBuilder newtreebuilder = new TreeBuilder(newmapbuilder);

        //Build hashmap of OLD components
        MapBuilder oldmapbuilder = new MapBuilder(parseOldTermList);
        //Build tree
        TreeBuilder oldtreebuilder = new TreeBuilder(oldmapbuilder);

        //check for rules violation
        GenerateSQL generatesql = new GenerateSQL( true, parseNewTermList, newtreebuilder, oldtreebuilder, "mouse", "EMAP" );

    }
}
