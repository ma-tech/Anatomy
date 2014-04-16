/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        ValidateInputOBOAgainstBaseOBODatabase.java
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
*                an Anatomy database
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

import utility.Wrapper;

import anatomy.TreeAnatomy;

import oboaccess.OBOComponentAccess;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import oboroutines.GenerateEditorPDF;
import oboroutines.GenerateEditorReport;
import oboroutines.ValidateComponents;

import routines.aggregated.EmptyComponentsTables;
import routines.aggregated.ListOBOComponentsFromComponentsTables;
import routines.aggregated.LoadOBOFileIntoComponentsTables;

import daolayer.DAOFactory;


public class ValidateInputOBOAgainstBaseOBODatabase {

	public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("validateinputoboagainstbaseobodatabase.run", "***", obofactory.getMsgLevel());

	    // Obtain DAOs.
        OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();

        String baseFile = obofactory.getOBOComponentAccess().baseFile();
	    String inputFile = obofactory.getOBOComponentAccess().inputFile();
        String summaryReport = obofactory.getOBOComponentAccess().summaryReport();
        String summaryReportPdf = obofactory.getOBOComponentAccess().summaryReportPdf();

        // Get all INPUT OBO components
        List<OBOComponent> inputobocomponents = new ArrayList<OBOComponent>();
        inputobocomponents = obocomponentaccess.listAllInput();
        
        EmptyComponentsTables.run( daofactory );
        
        LoadOBOFileIntoComponentsTables.run( daofactory, obofactory, inputobocomponents) ;
        
        ListOBOComponentsFromComponentsTables listobocomponentsfromcomponentstablesIN = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
	    
        // Get all the Components in the Components Tables
	    ArrayList<OBOComponent> arraylistComponentsTables = listobocomponentsfromcomponentstablesIN.getTermList();

	    // Build a Tree from all the Components in the Part-Onomy
	    TreeAnatomy treeanatomyComponentsTables = new TreeAnatomy(obofactory.getMsgLevel(), arraylistComponentsTables);

        // Get all BASE OBO components
        List<OBOComponent> baseobocomponents = new ArrayList<OBOComponent>();
        baseobocomponents = obocomponentaccess.listAllBase();
        
        EmptyComponentsTables.run( daofactory );
        
        LoadOBOFileIntoComponentsTables.run( daofactory, obofactory, baseobocomponents );
        
        ListOBOComponentsFromComponentsTables listobocomponentsfromcomponentstablesBASE = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );

        // Get all the Components in the Components Tables
	    ArrayList<OBOComponent> arraylistComponentsTablesBASE = listobocomponentsfromcomponentstablesBASE.getTermList();


	    //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( obofactory, 
            		arraylistComponentsTables, 
            		arraylistComponentsTablesBASE,
            		treeanatomyComponentsTables);

	    String validation = "";

	    Wrapper.printMessage("validateinputoboagainstbaseobodatabase.run : validatecomponents.getNewTermList().size()      " + validatecomponents.getNewTermList().size(), "***", obofactory.getMsgLevel());
	    Wrapper.printMessage("validateinputoboagainstbaseobodatabase.run : validatecomponents.getModifiedTermList().size() " + validatecomponents.getModifiedTermList().size(), "***", obofactory.getMsgLevel());
	    Wrapper.printMessage("validateinputoboagainstbaseobodatabase.run : validatecomponents.getDeletedTermList().size()  " + validatecomponents.getDeletedTermList().size(), "***", obofactory.getMsgLevel());

        ArrayList<OBOComponent> newComponents = new ArrayList<OBOComponent>();
        newComponents = validatecomponents.getNewTermList();
        Iterator<OBOComponent> iteratorNewTerms = newComponents.iterator();
        
     	while (iteratorNewTerms.hasNext()) {
    		
    		OBOComponent component = iteratorNewTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobodatabase.run : NEW component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

     	ArrayList<OBOComponent> modComponents = new ArrayList<OBOComponent>();
        modComponents = validatecomponents.getModifiedTermList();
        Iterator<OBOComponent> iteratorModTerms = modComponents.iterator();
        
     	while (iteratorModTerms.hasNext()) {
    		
    		OBOComponent component = iteratorModTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobodatabase.run : MOD component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

     	ArrayList<OBOComponent> delComponents = new ArrayList<OBOComponent>();
        delComponents = validatecomponents.getDeletedTermList();
        Iterator<OBOComponent> iteratorDelTerms = delComponents.iterator();
        
     	while (iteratorDelTerms.hasNext()) {
    		
    		OBOComponent component = iteratorDelTerms.next();

    		Wrapper.printMessage("validateinputoboagainstbaseobodatabase.run : DEL component.toString() " + component.toString(), "***", obofactory.getMsgLevel());
     	}

        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	
        	validation = "PASSED VALIDATION";
        }
        else {
        	
        	validation = "FAILED VALIDATION";
        }

        Wrapper.printMessage("validateinputoboagainstbaseobodatabase.run : Validated? " + validation, "***", obofactory.getMsgLevel());

        //generate txt summary report
        GenerateEditorReport generateeditorreport = new GenerateEditorReport( obofactory.getMsgLevel(), validatecomponents, inputFile, summaryReport);
        
        //generate pdf summary report
        GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( obofactory.getMsgLevel(), validatecomponents, treeanatomyComponentsTables, inputFile, summaryReportPdf);
    }
}