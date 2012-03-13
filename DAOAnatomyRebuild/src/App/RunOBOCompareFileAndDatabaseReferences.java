/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        OBOCompareFlieAndDatabaseReferences.java
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

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;

import OBOModel.ComponentFile;

import Utility.ImportFile;
import Utility.ImportDatabase;
import Utility.MapBuilder;
import Utility.TreeBuilder;
import Utility.ValidateComponents;
import Utility.GenerateEditorReport;
import Utility.GenerateEditorPDF;


public class RunOBOCompareFileAndDatabaseReferences {

	/*
	 * Main Class
	 */
    public static void run() throws Exception {

        //import Obo File from obo.properties, file.oboinfile
        ImportFile importfile = new ImportFile();
        ArrayList<ComponentFile> parseNewTermList = importfile.getTermList();


        //import Database from dao.properties, anatomy008.url
        ImportDatabase importdatabase = new ImportDatabase(true, "EMAP" );
        ArrayList<ComponentFile> parseOldTermList = importdatabase.getTermList();

        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder(parseNewTermList);
        //Build tree
        TreeBuilder treebuilder = new TreeBuilder(mapbuilder);


        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( parseNewTermList, parseOldTermList, treebuilder);
        

        //Report Success/Failure
    	System.out.println("");
    	System.out.println("===================");
    	System.out.println("VALIDATION COMPLETE");
    	System.out.println("===================");

        //if file has problems don't allow to load
        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	System.out.println("SUCCESS" );
        	System.out.println("-------" );
        	System.out.println(" All Components in the File Reference Tree are OK!" );

        }
        else {
        	System.out.println("FAILURE" );
        	System.out.println("-------" );
        	System.out.println(" Some components in the File Reference Tree contain rule violations." );
            System.out.println(" Number no. of components with problems = " +
                    validatecomponents.getProblemTermList().size());
        	System.out.println("");
        	System.out.println("   PROBLEM COMPONENTS" );
        	System.out.println("   ------- ----------" );
            
        	/*
            ArrayList<ComponentFile> problemobocomponents = validatecomponents.getProblemTermList();

          	Iterator<ComponentFile> iteratorProblemComponent = problemobocomponents.iterator();
          	int i = 0;
          	while (iteratorProblemComponent.hasNext()) {
           		ComponentFile problemobocomponent = iteratorProblemComponent.next();

           		i++;
           		System.out.println("  PROBLEM #" + i + ": " + problemobocomponent.toString());
           		
                Set<String> comments = problemobocomponent.getCheckComments();
              	Iterator<String> iteratorComment = comments.iterator();
              	int j = 0;
              	
              	while (iteratorComment.hasNext()) {
               		String comment = iteratorComment.next();
               		j++;
               		System.out.println("   Comment #" + j + " : " + comment);
               		
              	}
          	}
          	*/

        }
        
        //Generate Text Report
    	System.out.println("");
    	System.out.println("====================");
    	System.out.println("GENERATE TEXT REPORT");
    	System.out.println("====================");

        //generate txt summary report
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( validatecomponents );
        
        if ( generateeditorreport.getIsProcessed() ) {
        	System.out.println(" Summary Report " + generateeditorreport.getSummaryReportName() + " generated and saved!");
        }
        else {
        	System.out.println(" Summary Report " + generateeditorreport.getSummaryReportName() + " could NOT be saved.\n" +
                    "Check path in obo.properties file");
        }

        //Generate PDF Report
    	System.out.println("");
    	System.out.println("===================");
    	System.out.println("GENERATE PDF REPORT");
    	System.out.println("===================");

        //generate txt summary report
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( validatecomponents, treebuilder);
        
        if ( generateeditorreport.getIsProcessed() ) {
        	System.out.println(" PDF Report " + generateeditorpdf.getSummaryReportNamePdf() + " generated and saved!");
        }
        else {
        	System.out.println(" PDF Report " + generateeditorpdf.getSummaryReportNamePdf() + " could NOT be saved.\n" +
                    "Check path in obo.properties file");
        }

    }

}
