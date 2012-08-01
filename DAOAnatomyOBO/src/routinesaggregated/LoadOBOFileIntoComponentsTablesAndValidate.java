/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        LoadOBOFileIntoComponentsTablesAndValidate.java
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

package routinesaggregated;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import obolayer.OBOFactory;
import routinesbase.GenerateEditorPDF;
import routinesbase.GenerateEditorReport;
import routinesbase.MapBuilder;
import routinesbase.TreeBuilder;
import routinesbase.ValidateComponents;

import daolayer.DAOFactory;
import daolayer.OBOFileDAO;

import daomodel.OBOFile;

import utility.ObjectConverter;


public class LoadOBOFileIntoComponentsTablesAndValidate {
	/*
	 * run Method
	 */
    public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
        String inputFile = obofactory.getComponentOBO().inputFile();
        String summaryReport = obofactory.getComponentOBO().summaryReport();
        String summaryReportPdf = obofactory.getComponentOBO().summaryReportPdf();
        
        // Obtain DAOs.
	    OBOFileDAO obofileDAO = daofactory.getOBOFileDAO();
        
	    if ( obofactory.getComponentOBO().debug() ) {
	        System.out.println("Clear Out OBOFile Table");
	    }
	    List<OBOFile> obofiles = new ArrayList<OBOFile>();
        obofiles = obofileDAO.listAll();
        
        Iterator<OBOFile> iteratorOBOFilePre = obofiles.iterator();

        if ( obofiles.size() > 0 ) {
            while (iteratorOBOFilePre.hasNext()) {
            	OBOFile obofileListPre = iteratorOBOFilePre.next();
            	
            	obofileDAO.delete(obofileListPre);
          	}
        }

	    //import Obo File from obo.properties, file.oboinfile
	    if ( obofactory.getComponentOBO().debug() ) {
	        System.out.println("LoadOBOFileIntoComponentsTables");
	    }
        LoadOBOFileIntoComponentsTables.run(daofactory, obofactory);
        
        //import Obo File from obo.properties, file.oboinfile
	    if ( obofactory.getComponentOBO().debug() ) {
	        System.out.println("ListOBOComponentsFromComponentsTables");
	    }
        ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
        MapBuilder newmapbuilder = new MapBuilder(obofactory.getComponentOBO().debug(), importcomponents.getTermList());
        TreeBuilder newtreebuilder = new TreeBuilder(obofactory.getComponentOBO().debug(), newmapbuilder);

        //import Database from dao.properties, anatomy008.url
	    if ( obofactory.getComponentOBO().debug() ) {
	        System.out.println("ListOBOComponentsFromExistingDatabase");
	    }
        ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true );
        //MapBuilder oldmapbuilder = new MapBuilder(importdatabase.getTermList());
        //TreeBuilder oldtreebuilder = new TreeBuilder(oldmapbuilder);

        //check for rules violation
	    if ( obofactory.getComponentOBO().debug() ) {
	        System.out.println("ValidateComponents");
	    }
        ValidateComponents validatecomponents =
            new ValidateComponents( obofactory, 
            		importcomponents.getTermList(), 
            		importdatabase.getTermList(), 
            		newtreebuilder);
        
        String validation = "";

        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	validation = "PASSED VALIDATION";
        }
        else {
        	validation = "FAILED VALIDATION";
        }
	    if ( obofactory.getComponentOBO().debug() ) {

	    	System.out.println("Validated? " + validation);
	    }

        //generate txt summary report
	    if ( obofactory.getComponentOBO().debug() ) {
	        System.out.println("GenerateEditorReport");
	    }
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( obofactory.getComponentOBO().debug(), validatecomponents, inputFile, summaryReport);
        
        //generate pdf summary report
	    if ( obofactory.getComponentOBO().debug() ) {
	        System.out.println("GenerateEditorPDF");
	    }
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( obofactory.getComponentOBO().debug(), validatecomponents, newtreebuilder, inputFile, summaryReportPdf);
        
        File infile = new File(inputFile);
        InputStream inputstreamin = new FileInputStream(infile);
        File txtfile = new File(summaryReport);
        InputStream inputstreamtxt = new FileInputStream(txtfile);
        File pdffile = new File(summaryReportPdf);
        InputStream inputstreampdf = new FileInputStream(pdffile);
        
	    if ( obofactory.getComponentOBO().debug() ) {
	        System.out.println("Write to OBOFile Table");
	    }
	    // Create an OBOFile Object
	    OBOFile obofile = new OBOFile(null,
	    		// Input OBO File
	    		inputFile, 
	    		inputstreamin,
	    		//null,
	    		"UTF-8",
	    		ObjectConverter.convert(inputstreamin.available(), Long.class),
	    		utility.MySQLDateTime.now(),
	    		// OK?
	    		validation,
	    		"SYSTEM", 
	    		// Output Text Report
	    		summaryReport, 
	    		inputstreamtxt,
	    		//null,
	    		"UTF-8",
	    		ObjectConverter.convert(inputstreamtxt.available(), Long.class),
	    		utility.MySQLDateTime.now(), 
	    		// Output PDF Report
	    		summaryReportPdf, 
	    		inputstreampdf,
	    		//null,
	    		"UTF-8",
	    		ObjectConverter.convert(inputstreampdf.available(), Long.class),
	    		utility.MySQLDateTime.now());
        
        obofileDAO.save(obofile);
        
        obofiles = obofileDAO.listAll();
        
        Iterator<OBOFile> iteratorOBOFilePost = obofiles.iterator();

        int i = 0;

	    if ( obofactory.getComponentOBO().debug() ) {
	        if ( obofiles.size() > 0 ) {
	            System.out.println("List ALL OBOFile Rows");
	            System.out.println("");
	            System.out.println("\t=============================================");
	            System.out.println("\tA List of the Uploaded and Validated OBOFiles = " + Integer.toString(obofiles.size()));
	            System.out.println("\t=============================================");

	            while (iteratorOBOFilePost.hasNext()) {
	            	OBOFile obofileListed = iteratorOBOFilePost.next();
	           		i++;
	           		System.out.println(Integer.toString(i) + obofileListed.toString());
	          	}
	        }
	    }
    }
}
