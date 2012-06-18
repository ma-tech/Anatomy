/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOLoadFileIntoComponentsAndValidate.java
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

package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daolayer.DAOFactory;
import daolayer.OBOFileDAO;

import daomodel.OBOFile;

import utility.ObjectConverter;
import utility.StringStreamConverter;

import routines.GenerateEditorPDF;
import routines.GenerateEditorReport;
import routines.ValidateComponents;
import routines.MapBuilder;
import routines.TreeBuilder;

import routines.LoadComponentsTablesFromOBOFile;
import routines.ListOBOComponentsFromComponentsTables;
import routines.ListOBOComponentsFromOBOFile;
import routines.ListOBOComponentsFromExistingDatabase;

public class RunOBOLoadFileIntoComponentsAndValidate {
	/*
	 * run Method
	 */
    public static void run() throws Exception {
    	
        System.out.println("Obtain DAO");
	    // Obtain DAOFactory.
	    DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
	    // Obtain DAOs.
	    OBOFileDAO obofileDAO = anatomy008.getOBOFileDAO();
        
        System.out.println("Clear Out OBOFile Table");
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
        System.out.println("LoadComponentsTablesFromOBOFile");
        LoadComponentsTablesFromOBOFile importfile = new LoadComponentsTablesFromOBOFile();
        System.out.println("ListOBOComponentsFromOBOFile");
        ListOBOComponentsFromOBOFile listfile = new ListOBOComponentsFromOBOFile();
        
        System.out.println("listfile.getInputFileName()       " + listfile.getInputFileName());
        System.out.println("listfile.getOutputReportName()    " + listfile.getOutputReportName());
        System.out.println("listfile.getOutputReportPDFName() " + listfile.getOutputReportPDFName());
        
        //import Obo File from obo.properties, file.oboinfile
        System.out.println("ListOBOComponentsFromComponentsTables");
        ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables();
        MapBuilder newmapbuilder = new MapBuilder(importcomponents.getTermList());
        TreeBuilder newtreebuilder = new TreeBuilder(newmapbuilder);

        //import Database from dao.properties, anatomy008.url
        System.out.println("ListOBOComponentsFromExistingDatabase");
        ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase(true, "EMAP" );
        //MapBuilder oldmapbuilder = new MapBuilder(importdatabase.getTermList());
        //TreeBuilder oldtreebuilder = new TreeBuilder(oldmapbuilder);

        //check for rules violation
        System.out.println("ValidateComponents");
        ValidateComponents validatecomponents =
            new ValidateComponents( "mouse", importcomponents.getTermList(), importdatabase.getTermList(), newtreebuilder);
        
        String validation = "";

        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	validation = "PASSED VALIDATION";
        }
        else {
        	validation = "FAILED VALIDATION";
        }
        System.out.println("Validated? " + validation);

        //generate txt summary report
        System.out.println("GenerateEditorReport");
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( validatecomponents, 
        		listfile.getInputFileName(),
        		listfile.getOutputReportName());
        
        //generate pdf summary report
        System.out.println("GenerateEditorPDF");
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( validatecomponents, 
        		newtreebuilder, 
        		listfile.getInputFileName(),
        		listfile.getOutputReportPDFName());

        File infile = new File(listfile.getInputFileName());
        InputStream inputstreamin = new FileInputStream(infile);
        File txtfile = new File(listfile.getOutputReportName());
        InputStream inputstreamtxt = new FileInputStream(txtfile);
        File pdffile = new File(listfile.getOutputReportPDFName());
        InputStream inputstreampdf = new FileInputStream(pdffile);
        
        System.out.println("Write to OBOFile");
	    // Create an OBOFile Object
	    OBOFile obofile = new OBOFile(null,
	    		// Input OBO File
	    		listfile.getInputFileName(), 
	    		inputstreamin,
	    		//null,
	    		"UTF-8",
	    		ObjectConverter.convert(inputstreamin.available(), Long.class),
	    		utility.MySQLDateTime.now(),
	    		// OK?
	    		validation,
	    		"SYSTEM", 
	    		// Output Text Report
	    		listfile.getOutputReportName(), 
	    		inputstreamtxt,
	    		//null,
	    		"UTF-8",
	    		ObjectConverter.convert(inputstreamtxt.available(), Long.class),
	    		utility.MySQLDateTime.now(), 
	    		// Output PDF Report
	    		listfile.getOutputReportPDFName(), 
	    		inputstreampdf,
	    		//null,
	    		"UTF-8",
	    		ObjectConverter.convert(inputstreampdf.available(), Long.class),
	    		utility.MySQLDateTime.now());
        
        obofileDAO.save(obofile);
        
        obofiles = obofileDAO.listAll();
        
        Iterator<OBOFile> iteratorOBOFilePost = obofiles.iterator();

        int i = 0;

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
