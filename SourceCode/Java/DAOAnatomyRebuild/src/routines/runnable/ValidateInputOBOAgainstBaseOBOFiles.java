/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
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
* Description:  A Main Class that Reads an OBO File (INPUT), compares and validates it against
*                another OBO file (BASE)
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

import utility.Wrapper;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import oboroutines.GenerateEditorPDF;
import oboroutines.GenerateEditorReport;
import oboroutines.MapBuilder;
import oboroutines.TreeBuilder;
import oboroutines.ValidateComponents;

import routines.aggregated.ListOBOComponentsFromOBOFile;

public class ValidateInputOBOAgainstBaseOBOFiles {

	public static void run(OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("validateinputoboagainstbaseobo.run", "***", obofactory.getMsgLevel());

        String baseFile = obofactory.getOBOComponentAccess().baseFile();
	    String inputFile = obofactory.getOBOComponentAccess().inputFile();
        String summaryReport = obofactory.getOBOComponentAccess().summaryReport();
        String summaryReportPdf = obofactory.getOBOComponentAccess().summaryReportPdf();

        // Get all INPUT OBO components
        ListOBOComponentsFromOBOFile inputimportcomponents = new ListOBOComponentsFromOBOFile( obofactory, "INPUT" );
        MapBuilder inputmapbuilder = new MapBuilder( obofactory.getMsgLevel(), inputimportcomponents.getObocomponentList());
        TreeBuilder inputtreebuilder = new TreeBuilder( obofactory.getMsgLevel(), inputmapbuilder);

        // Get all BASE OBO components
        ListOBOComponentsFromOBOFile baseimportcomponents = new ListOBOComponentsFromOBOFile( obofactory, "BASE" );
        //MapBuilder basemapbuilder = new MapBuilder( obofactory.getMsgLevel(), baseimportcomponents.getTermList());
        //TreeBuilder basetreebuilder = new TreeBuilder( obofactory.getMsgLevel(), basemapbuilder);

        //check for rules violation
	    ValidateComponents validatecomponents =
	            new ValidateComponents( obofactory, 
	            		inputimportcomponents.getObocomponentList(), 
	            		baseimportcomponents.getObocomponentList(), 
	            		inputtreebuilder);
        
        String validation = "";

	    Wrapper.printMessage("validateinputoboagainstbaseobo.run : validatecomponents.getNewTermList().size()      " + validatecomponents.getNewTermList().size(), "***", obofactory.getMsgLevel());
	    Wrapper.printMessage("validateinputoboagainstbaseobo.run : validatecomponents.getModifiedTermList().size() " + validatecomponents.getModifiedTermList().size(), "***", obofactory.getMsgLevel());
	    Wrapper.printMessage("validateinputoboagainstbaseobo.run : validatecomponents.getDeletedTermList().size()  " + validatecomponents.getDeletedTermList().size(), "***", obofactory.getMsgLevel());

        ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();
        newComponents = validatecomponents.getNewTermList();
        Iterator<OBOComponent> iteratorNewTerms = newComponents.iterator();
        
     	while (iteratorNewTerms.hasNext()) {
    		
    		OBOComponent component = iteratorNewTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobo.run : NEW component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

     	ArrayList<OBOComponent> modComponents = new ArrayList<OBOComponent>();
        modComponents = validatecomponents.getModifiedTermList();
        Iterator<OBOComponent> iteratorModTerms = modComponents.iterator();
        
     	while (iteratorModTerms.hasNext()) {
    		
    		OBOComponent component = iteratorModTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobo.run : MOD component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

     	ArrayList<OBOComponent> delComponents = new ArrayList<OBOComponent>();
        delComponents = validatecomponents.getDeletedTermList();
        Iterator<OBOComponent> iteratorDelTerms = delComponents.iterator();
        
     	while (iteratorDelTerms.hasNext()) {
    		
    		OBOComponent component = iteratorDelTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobo.run : DEL component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	
        	validation = "PASSED VALIDATION";
        }
        else {
        	
        	validation = "FAILED VALIDATION";
        }

        Wrapper.printMessage("validateinputoboagainstbaseobo.run : Validated? " + validation, "***", obofactory.getMsgLevel());

        //generate txt summary report
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( obofactory.getMsgLevel(), validatecomponents, inputFile, summaryReport);
        
        //generate pdf summary report
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( obofactory.getMsgLevel(), validatecomponents, inputtreebuilder, inputFile, summaryReportPdf);
    }
}