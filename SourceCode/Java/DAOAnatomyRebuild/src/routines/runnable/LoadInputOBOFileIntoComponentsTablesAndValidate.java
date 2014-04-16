/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        LoadInputOBOFileIntoComponentsTablesAndValidate.java
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
* Description:  A Class that Reads an OBO File and Loads it into the Components tables in the 
*                Anatomy database;
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

import utility.Wrapper;
import utility.ObjectConverter;

import anatomy.TreeAnatomy;

import oboaccess.OBOComponentAccess;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import oboroutines.GenerateEditorPDF;
import oboroutines.GenerateEditorReport;
import oboroutines.ValidateComponents;

import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;
import routines.aggregated.LoadOBOFileIntoComponentsTables;

import daolayer.DAOFactory;

import daointerface.OBOFileDAO;

import daomodel.OBOFile;


public class LoadInputOBOFileIntoComponentsTablesAndValidate {

	public static void run( DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run", "***", daofactory.getMsgLevel());

	    String inputFile = obofactory.getOBOComponentAccess().inputFile();
        String summaryReport = obofactory.getOBOComponentAccess().summaryReport();
        String summaryReportPdf = obofactory.getOBOComponentAccess().summaryReportPdf();
        
        // Obtain DAOs.
	    OBOFileDAO obofileDAO = daofactory.getDAOImpl(OBOFileDAO.class);
        
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : Clear Out OBOFile Table", "***", daofactory.getMsgLevel());
    
	    List<OBOFile> obofiles = new ArrayList<OBOFile>();
        obofiles = obofileDAO.listAll();
        
        Iterator<OBOFile> iteratorOBOFilePre = obofiles.iterator();

        if ( obofiles.size() > 0 ) {
        	
            while (iteratorOBOFilePre.hasNext()) {
            	
            	OBOFile obofileListPre = iteratorOBOFilePre.next();
            	
            	obofileDAO.delete(obofileListPre);
          	}
        }

	    // Obtain DAOs.
        OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();

        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = obocomponentaccess.listAllInput();

	    //import Obo File from obo.properties, file.oboinfile
        LoadOBOFileIntoComponentsTables.run( daofactory, obofactory, obocomponents);
        
	    ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
	    
	    // Get all the Components in the Components Tables
	    ArrayList<OBOComponent> arraylistComponentsTables = importcomponents.getTermList();

	    // Build a Tree from all the Components in the Part-Onomy
	    TreeAnatomy treeanatomyComponentsTables = new TreeAnatomy(obofactory.getMsgLevel(), arraylistComponentsTables);

	    
	    // import Database from dao.properties
	    ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true, "" );
	    
	    // Get all the Components in the Part-Onomy
	    ArrayList<OBOComponent> arraylistOBOComponentExistingDB = importdatabase.getObocomponentAllOnomy();


        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( obofactory, 
            		arraylistComponentsTables, 
            		arraylistOBOComponentExistingDB,
            		treeanatomyComponentsTables);
        
        String validation = "";

	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : validatecomponents.getNewTermList().size()      " + validatecomponents.getNewTermList().size(), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : validatecomponents.getModifiedTermList().size() " + validatecomponents.getModifiedTermList().size(), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : validatecomponents.getDeletedTermList().size()  " + validatecomponents.getDeletedTermList().size(), "***", daofactory.getMsgLevel());

        ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();
        newComponents = validatecomponents.getNewTermList();
        Iterator<OBOComponent> iteratorNewTerms = newComponents.iterator();
        
     	while (iteratorNewTerms.hasNext()) {
    		
    		OBOComponent component = iteratorNewTerms.next();

    		//Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : NEW component.toString() " + component.toString(), "***", daofactory.getMsgLevel());
     	}

     	ArrayList<OBOComponent> modComponents = new ArrayList<OBOComponent>();
        modComponents = validatecomponents.getModifiedTermList();
        Iterator<OBOComponent> iteratorModTerms = modComponents.iterator();
        
     	while (iteratorModTerms.hasNext()) {
    		
    		OBOComponent component = iteratorModTerms.next();

    		//Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : MOD component.toString() " + component.toString(), "***", daofactory.getMsgLevel());
     	}

     	ArrayList<OBOComponent> delComponents = new ArrayList<OBOComponent>();
        delComponents = validatecomponents.getDeletedTermList();
        Iterator<OBOComponent> iteratorDelTerms = delComponents.iterator();
        
     	while (iteratorDelTerms.hasNext()) {
    		
    		OBOComponent component = iteratorDelTerms.next();

    		//Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : DEL component.toString() " + component.toString(), "***", daofactory.getMsgLevel());
     	}

        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	
        	validation = "PASSED VALIDATION";
        }
        else {
        	
        	validation = "FAILED VALIDATION";
        }

        Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : Validated? " + validation, "***", daofactory.getMsgLevel());

        //generate txt summary report
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( daofactory.getMsgLevel(), validatecomponents, inputFile, summaryReport);
        
        //generate pdf summary report
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( daofactory.getMsgLevel(), validatecomponents, treeanatomyComponentsTables, inputFile, summaryReportPdf);
        
        File infile = new File(inputFile);
        InputStream inputstreamin = new FileInputStream(infile);
        File txtfile = new File(summaryReport);
        InputStream inputstreamtxt = new FileInputStream(txtfile);
        File pdffile = new File(summaryReportPdf);
        InputStream inputstreampdf = new FileInputStream(pdffile);
        
	    Wrapper.printMessage("loadobofileintocomponentstablesandvalidate.run : Write to OBOFile Table", "***", daofactory.getMsgLevel());
	    
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