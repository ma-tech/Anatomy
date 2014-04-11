/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunExtractOBOAndValidate.java
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
* Description:  A Main Class that accesses an Anatomy Database via a Data Access Object Layer;
*                Finds are performed using each Data Access Object 
*
*               Required Files:
*                1. dao.properties file contains the OBO file access attributes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; March 2013; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package app;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import utility.ExecuteCommand;
import obomodel.OBOComponent;
import oboroutines.GenerateEditorPDF;
import oboroutines.GenerateEditorReport;
import oboroutines.Parser;
import oboroutines.ValidateComponents;
import oboroutines.archive.MapBuilder;
import oboroutines.archive.TreeBuilder;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;
import routines.aggregated.LoadOBOFileIntoComponentsTables;
import daolayer.DAOException;
import daolayer.DAOFactory;
import daointerface.OBOFileDAO;
import daomodel.OBOFile;
import obolayer.OBOFactory;

public class RunExtractOBOAndValidate {
	/*
	 * run Method
	 */
	public static void run (long Oid, DAOFactory daofactory, OBOFactory obofactory) 
			throws NumberFormatException, Exception {

		try {
		    // Obtain DAOs.
		    OBOFileDAO obofileDAO = daofactory.getDAOImpl(OBOFileDAO.class);
		    
		    // Find a OBOFile
		    OBOFile obofile = obofileDAO.findWithBinary(Oid);
		    
            // Open streams.
	        OutputStream output = null;
	        InputStream input = null;
	        File file = null;
	        
	        String[] nameArray = obofile.getName().split("\\.");
	        String fileName = nameArray[0] + "." + nameArray[1];
	        String filePath = "/tmp/" + fileName;
	        String command1 = "rm " + filePath;
	        String fileTextReport = nameArray[0] + "_ValidationReport.txt";
	        String filePathTextReport = "/tmp/" + fileTextReport;
	        String command2 = "rm " + filePathTextReport;
	        String filePdfReport = nameArray[0] + "_ValidationReport.pdf";
	        String filePathPdfReport = "/tmp/" + filePdfReport;
	        String command3 = "rm " + filePathPdfReport;

	        ExecuteCommand.execute(command1);
	        ExecuteCommand.execute(command2);
	        ExecuteCommand.execute(command3);

	        file = new File(filePath);
            output = new FileOutputStream(file);
            
            input = obofile.getContent();
            
            IOUtils.copy(input, output);
            
            //import Obo File from obo.properties, file.oboinfile
        	ArrayList<OBOComponent> componentList = new ArrayList<OBOComponent>();
        	
        	Parser parser = new Parser(
        			daofactory.getMsgLevel(),
        			filePath,
        			false,
        			"mouse");
        	
        	componentList = parser.getComponents();
        	        	
        	//Wrapper.printMessage("componentList.size()   = " + componentList.size(), "*", "*");

    	    //import Obo File from obo.properties, file.oboinfile
            LoadOBOFileIntoComponentsTables.run( daofactory, obofactory, componentList);
            

            //import Database from dao.properties, anatomy008.url
            ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true );
            ArrayList<OBOComponent> parseOldTermList = importdatabase.getTermList();

            //Build hashmap of components
            MapBuilder mapbuilder = new MapBuilder( daofactory.getMsgLevel(), componentList );
            //Build tree
            TreeBuilder treebuilder = new TreeBuilder( daofactory.getMsgLevel(), mapbuilder );

            //check for rules violation
            ValidateComponents validatecomponents = new ValidateComponents( obofactory, componentList, parseOldTermList, treebuilder);
            
            if ( validatecomponents.getProblemTermList().isEmpty() ) {
            	obofile.setValidation("VALIDATED");
            }
            else {
            	obofile.setValidation("FAILED");
            }
            
            //generate txt summary report
            GenerateEditorReport generateeditorreport = new GenerateEditorReport( daofactory.getMsgLevel(), validatecomponents, filePath, filePathTextReport );
            //generate txt summary report
            GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( daofactory.getMsgLevel(), validatecomponents, treebuilder, filePath, filePathPdfReport );

            file = new File(filePath);
            input = new FileInputStream(file);

            obofile.setContent(input);

            file = null;
	        file = new File(filePathTextReport);
            input = new FileInputStream(file);

            obofile.setTextreport(input);
            obofile.setTextreportname(fileTextReport);
            obofile.setTextreportdate(utility.MySQLDateTime.now());
            obofile.setTextreportlength(file.length());
            obofile.setTextreporttype(obofile.getContenttype());
            
            file = null;
	        file = new File(filePathPdfReport);
            input = new FileInputStream(file);

            obofile.setPdfreport(input);
            obofile.setPdfreportname(filePdfReport);
            obofile.setPdfreportdate(utility.MySQLDateTime.now());
            obofile.setPdfreportlength(file.length());
            obofile.setPdfreporttype(obofile.getContenttype());
            
            obofileDAO.save(obofile);
            
        	ExecuteCommand.execute(command1);
	        ExecuteCommand.execute(command2);
	        ExecuteCommand.execute(command3);

		}
        catch (IOException ioexception) {
        	ioexception.printStackTrace();
        }
		catch (DAOException daoexception) {
			daoexception.printStackTrace();
		}

	}
    
}
