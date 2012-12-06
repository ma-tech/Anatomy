/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
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
package routines.runnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;
import routines.base.GenerateEditorPDF;
import routines.base.GenerateEditorReport;
import routines.base.MapBuilder;
import routines.base.TreeBuilder;
import routines.base.ValidateComponents;

import daolayer.DAOFactory;
import daolayer.OBOFileDAO;

import daomodel.OBOFile;

import utility.ObjectConverter;
import utility.Wrapper;

public class LoadOBOFileIntoComponentsTablesAndValidate {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run", "***", requestMsgLevel);

	    String inputFile = obofactory.getComponentOBO().inputFile();
        String summaryReport = obofactory.getComponentOBO().summaryReport();
        String summaryReportPdf = obofactory.getComponentOBO().summaryReportPdf();
        
        // Obtain DAOs.
	    OBOFileDAO obofileDAO = daofactory.getOBOFileDAO();
        
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:Clear Out OBOFile Table", "***", requestMsgLevel);
    
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
        LoadOBOFileIntoComponentsTables.run(requestMsgLevel, daofactory, obofactory);
        
        //import Obo File from obo.properties, file.oboinfile
        ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( requestMsgLevel, daofactory, obofactory );
        MapBuilder newmapbuilder = new MapBuilder( requestMsgLevel, importcomponents.getTermList());
        TreeBuilder newtreebuilder = new TreeBuilder( requestMsgLevel, newmapbuilder);

        //import Database from dao.properties, anatomy008.url
        ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( requestMsgLevel, daofactory, obofactory, true );

        //check for rules violation
	    ValidateComponents validatecomponents =
            new ValidateComponents( requestMsgLevel,
            		obofactory, 
            		importcomponents.getTermList(), 
            		importdatabase.getTermList(), 
            		newtreebuilder);
        
        String validation = "";

	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:validatecomponents.getNewTermList().size()      " + validatecomponents.getNewTermList().size(), "***", requestMsgLevel);
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:validatecomponents.getModifiedTermList().size() " + validatecomponents.getModifiedTermList().size(), "***", requestMsgLevel);
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:validatecomponents.getDeletedTermList().size()  " + validatecomponents.getDeletedTermList().size(), "***", requestMsgLevel);

        ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();
        newComponents = validatecomponents.getNewTermList();
        Iterator<OBOComponent> iteratorNewTerms = newComponents.iterator();
        
     	while (iteratorNewTerms.hasNext()) {
    		
    		OBOComponent component = iteratorNewTerms.next();

    		Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:NEW component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

     	ArrayList<OBOComponent> modComponents = new ArrayList<OBOComponent>();
        modComponents = validatecomponents.getModifiedTermList();
        Iterator<OBOComponent> iteratorModTerms = modComponents.iterator();
        
     	while (iteratorModTerms.hasNext()) {
    		
    		OBOComponent component = iteratorModTerms.next();

    		Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:MOD component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

     	ArrayList<OBOComponent> delComponents = new ArrayList<OBOComponent>();
        delComponents = validatecomponents.getDeletedTermList();
        Iterator<OBOComponent> iteratorDelTerms = delComponents.iterator();
        
     	while (iteratorDelTerms.hasNext()) {
    		
    		OBOComponent component = iteratorDelTerms.next();

    		Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:DEL component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	
        	validation = "PASSED VALIDATION";
        }
        else {
        	
        	validation = "FAILED VALIDATION";
        }

        Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:Validated? " + validation, "***", requestMsgLevel);

        //generate txt summary report
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( requestMsgLevel, validatecomponents, inputFile, summaryReport);
        
        //generate pdf summary report
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( requestMsgLevel, validatecomponents, newtreebuilder, inputFile, summaryReportPdf);
        
        File infile = new File(inputFile);
        InputStream inputstreamin = new FileInputStream(infile);
        File txtfile = new File(summaryReport);
        InputStream inputstreamtxt = new FileInputStream(txtfile);
        File pdffile = new File(summaryReportPdf);
        InputStream inputstreampdf = new FileInputStream(pdffile);
        
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run:Write to OBOFile Table", "***", requestMsgLevel);
	    
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
    }
}