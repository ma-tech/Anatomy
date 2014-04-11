/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        ValidateComponentsTablesAgainstExistingDatabase.java
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
* Description:  
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.runnable.archive;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utility.Wrapper;
import utility.ObjectConverter;
import obolayer.OBOFactory;
import oboroutines.GenerateEditorPDF;
import oboroutines.GenerateEditorReport;
import oboroutines.ValidateComponents;
import oboroutines.archive.MapBuilder;
import oboroutines.archive.TreeBuilder;
import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;
import daolayer.DAOFactory;
import daointerface.OBOFileDAO;
import daomodel.OBOFile;

public class ValidateComponentsTablesAgainstExistingDatabase {

	public static void run( DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    Wrapper.printMessage("validatecomponentstablesagainstexistingdatabase.run", "***", daofactory.getMsgLevel());

        String inputFile = obofactory.getOBOComponentAccess().inputFile();
        String summaryReport = obofactory.getOBOComponentAccess().summaryReport();
        String summaryReportPdf = obofactory.getOBOComponentAccess().summaryReportPdf();
        
        // Obtain DAOs.
	    OBOFileDAO obofileDAO = daofactory.getDAOImpl(OBOFileDAO.class);
        
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
	    ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
        
        MapBuilder newmapbuilder = new MapBuilder( daofactory.getMsgLevel(), importcomponents.getTermList());
        TreeBuilder newtreebuilder = new TreeBuilder( daofactory.getMsgLevel(), newmapbuilder);

        //import Database from dao.properties, anatomy008.url
	    ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true );

        //check for rules violation
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

        Wrapper.printMessage("validatecomponentstablesagainstexistingdatabase.run : Validated? " + validation, "***", daofactory.getMsgLevel());

        //generate txt summary report
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( daofactory.getMsgLevel(), validatecomponents, inputFile, summaryReport);
        
        //generate pdf summary report
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( daofactory.getMsgLevel(), validatecomponents, newtreebuilder, inputFile, summaryReportPdf);
        
        File infile = new File(inputFile);
        InputStream inputstreamin = new FileInputStream(infile);
        
        File txtfile = new File(summaryReport);
        InputStream inputstreamtxt = new FileInputStream(txtfile);
        
        File pdffile = new File(summaryReportPdf);
        InputStream inputstreampdf = new FileInputStream(pdffile);

        Wrapper.printMessage("validatecomponentstablesagainstexistingdatabase.run : Write to OBOFile Table", "***", daofactory.getMsgLevel());

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

        if ( obofiles.size() > 0 ) {
	        
    	    Wrapper.printMessage("validatecomponentstablesagainstexistingdatabase.run : List ALL OBOFile Rows", "***", daofactory.getMsgLevel());
    	    Wrapper.printMessage("validatecomponentstablesagainstexistingdatabase.run : ", "***", daofactory.getMsgLevel());
    	    Wrapper.printMessage("validatecomponentstablesagainstexistingdatabase.run : \t=============================================", "***", daofactory.getMsgLevel());
    	    Wrapper.printMessage("validatecomponentstablesagainstexistingdatabase.run : \tA List of the Uploaded and Validated OBOFiles = " + obofiles.size(), "***", daofactory.getMsgLevel());
    	    Wrapper.printMessage("validatecomponentstablesagainstexistingdatabase.run : \t=============================================", "***", daofactory.getMsgLevel());

            while (iteratorOBOFilePost.hasNext()) {
            
            	OBOFile obofileListed = iteratorOBOFilePost.next();
           		i++;
           		
        	    Wrapper.printMessage(i + obofileListed.toString(), "***", daofactory.getMsgLevel());
          	}
        }
    }
}
