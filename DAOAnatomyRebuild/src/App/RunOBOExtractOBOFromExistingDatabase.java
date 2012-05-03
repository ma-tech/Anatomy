/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        OBOExtractOBOFromExistingDatabase.java
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
* Description:  A Main Class that Reads an Anatomy Database and Writes out the data in OBO
*                Format
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

package app;

import java.util.ArrayList;
import java.util.List;

import obomodel.ComponentFile;

import obolayer.OBOFactory;
import obolayer.ComponentOBO;

import utility.ImportDatabase;

public class RunOBOExtractOBOFromExistingDatabase {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

        //import database
        ImportDatabase importdatabase = new ImportDatabase(true, "GUDMAP" );
        
        // Obtain OBOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("file");
        
        // Obtain DAOs.
        ComponentOBO componentOBO = obofactory.getComponentOBO();
        
        // Export Database Components to OBO File.
        List<ComponentFile> obocomponents = new ArrayList<ComponentFile>();
        obocomponents = importdatabase.getTermList();
        
        // Write out Obo File
        componentOBO.setComponentFileList((ArrayList) obocomponents);
        componentOBO.createTemplateRelationList();
        
        Boolean isProcessed = componentOBO.writeAll();

        if (isProcessed) {
            System.out.println("Obo File SUCCESSFULLY written to " + componentOBO.outputFile());
        }
        else {
            System.out.println("Obo File FAILED to write to " + componentOBO.outputFile());
        }
    }
}
