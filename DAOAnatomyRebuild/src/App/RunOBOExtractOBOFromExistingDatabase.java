/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        OBOExtractOBOFromExistingDatabase.java
#
# Date:         2012
#
# Author:       Mike Wicks
#
# Copyright:    2012
#               Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Description:  A Main Class that Reads an OBO File and populates 4 tables in the anatomy
#                database with the extracted data.
#
#               Required Files:
#                1. dao.properties file contains the database access attributes
#                2. obo.properties file contains the OBO file access attributes
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package App;

import java.util.ArrayList;
import java.util.List;

import OBOModel.ComponentFile;

import OBOLayer.OBOFactory;
import OBOLayer.ComponentOBO;

import Utility.ImportDatabase;


public class RunOBOExtractOBOFromExistingDatabase {

	/*
	 * Main Class
	 */
    public static void run() throws Exception {

        //import database
        ImportDatabase importdatabase = new ImportDatabase(true, "GUDMAP" );
        
        // Obtain OBOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("file");
        
        // Obtain DAOs.
        ComponentOBO componentOBO = obofactory.getComponentOBO();
        
        // Export Database Components to OBO File.
        //importdatabase.saveOBOFile(componentOBO.outputFile());
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
