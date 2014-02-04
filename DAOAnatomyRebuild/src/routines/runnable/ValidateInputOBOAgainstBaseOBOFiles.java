/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
*
* Title:        ValidateInputOBOAgainstBaseOBOFiles.java
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import obolayer.OBOComponentAccess;
import obolayer.OBOFactory;

import obomodel.OBOComponent;

import oboroutines.GenerateEditorPDF;
import oboroutines.GenerateEditorReport;
import oboroutines.MapBuilder;
import oboroutines.TreeBuilder;
import oboroutines.ValidateComponents;

import routines.aggregated.EmptyComponentsTables;
import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.aggregated.ListOBOComponentsFromOBOFile;
import routines.aggregated.LoadOBOFileIntoComponentsTables;

import daolayer.DAOFactory;

import utility.Wrapper;

public class ValidateInputOBOAgainstBaseOBOFiles {

	public static void run(String requestMsgLevel, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("validateinputoboagainstbaseobo.run", "***", requestMsgLevel);

        String baseFile = obofactory.getOBOComponentAccess().baseFile();
	    String inputFile = obofactory.getOBOComponentAccess().inputFile();
        String summaryReport = obofactory.getOBOComponentAccess().summaryReport();
        String summaryReportPdf = obofactory.getOBOComponentAccess().summaryReportPdf();

        // Get all INPUT OBO components
        ListOBOComponentsFromOBOFile inputimportcomponents = new ListOBOComponentsFromOBOFile(requestMsgLevel, obofactory, "INPUT");
        MapBuilder inputmapbuilder = new MapBuilder( requestMsgLevel, inputimportcomponents.getObocomponentList());
        TreeBuilder inputtreebuilder = new TreeBuilder( requestMsgLevel, inputmapbuilder);

        // Get all BASE OBO components
        ListOBOComponentsFromOBOFile baseimportcomponents = new ListOBOComponentsFromOBOFile(requestMsgLevel, obofactory, "BASE");
        //MapBuilder basemapbuilder = new MapBuilder( requestMsgLevel, baseimportcomponents.getTermList());
        //TreeBuilder basetreebuilder = new TreeBuilder( requestMsgLevel, basemapbuilder);

        //check for rules violation
	    ValidateComponents validatecomponents =
	            new ValidateComponents( requestMsgLevel,
	            		obofactory, 
	            		inputimportcomponents.getObocomponentList(), 
	            		baseimportcomponents.getObocomponentList(), 
	            		inputtreebuilder);
        
        String validation = "";

	    Wrapper.printMessage("validateinputoboagainstbaseobo.run : validatecomponents.getNewTermList().size()      " + validatecomponents.getNewTermList().size(), "***", requestMsgLevel);
	    Wrapper.printMessage("validateinputoboagainstbaseobo.run : validatecomponents.getModifiedTermList().size() " + validatecomponents.getModifiedTermList().size(), "***", requestMsgLevel);
	    Wrapper.printMessage("validateinputoboagainstbaseobo.run : validatecomponents.getDeletedTermList().size()  " + validatecomponents.getDeletedTermList().size(), "***", requestMsgLevel);

        ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();
        newComponents = validatecomponents.getNewTermList();
        Iterator<OBOComponent> iteratorNewTerms = newComponents.iterator();
        
     	while (iteratorNewTerms.hasNext()) {
    		
    		OBOComponent component = iteratorNewTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobo.run : NEW component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

     	ArrayList<OBOComponent> modComponents = new ArrayList<OBOComponent>();
        modComponents = validatecomponents.getModifiedTermList();
        Iterator<OBOComponent> iteratorModTerms = modComponents.iterator();
        
     	while (iteratorModTerms.hasNext()) {
    		
    		OBOComponent component = iteratorModTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobo.run : MOD component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

     	ArrayList<OBOComponent> delComponents = new ArrayList<OBOComponent>();
        delComponents = validatecomponents.getDeletedTermList();
        Iterator<OBOComponent> iteratorDelTerms = delComponents.iterator();
        
     	while (iteratorDelTerms.hasNext()) {
    		
    		OBOComponent component = iteratorDelTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobo.run : DEL component.toString() " + component.toString(), "***", requestMsgLevel);
     	}

        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	
        	validation = "PASSED VALIDATION";
        }
        else {
        	
        	validation = "FAILED VALIDATION";
        }

        Wrapper.printMessage("validateinputoboagainstbaseobo.run : Validated? " + validation, "***", requestMsgLevel);

        //generate txt summary report
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( requestMsgLevel, validatecomponents, inputFile, summaryReport);
        
        //generate pdf summary report
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( requestMsgLevel, validatecomponents, inputtreebuilder, inputFile, summaryReportPdf);
    }
}