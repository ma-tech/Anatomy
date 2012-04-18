/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunDAOExtractOBO.java
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
* Description:  A Main Class that accesses an Anatomy Database via a DAO Layer;
*                Finds are performed using each Data Access Object 
*
*               Required Files:
*                1. dao.properties file contains the OBO file access attributes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package App;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import OBOModel.ComponentFile;
import Utility.ExecuteCommand;
import Utility.GenerateEditorPDF;
import Utility.GenerateEditorReport;
import Utility.ImportDatabase;
import Utility.ImportFile;
import Utility.MapBuilder;
import Utility.TreeBuilder;
import Utility.ValidateComponents;

import DAOLayer.DAOException;
import DAOLayer.DAOFactory;
import DAOLayer.OBOFileDAO;

import DAOModel.OBOFile;

public class RunDAOExtractOBOAndValidate {
	
	/*
	 * run Method
	 */
	public static void run (String Oid) {

		try {
		    // Obtain DAOFactory.
		    DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");

		    // Obtain DAOs.
		    OBOFileDAO obofileDAO = anatomy008.getOBOFileDAO();
		    
		    // Find a OBOFile
		    OBOFile obofile = obofileDAO.findWithBinary(Long.parseLong(Oid));
		    
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
            ImportFile importfile = new ImportFile(filePath);
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
            
            if ( validatecomponents.getProblemTermList().isEmpty() ) {
            	obofile.setValidation("VALIDATED");
            }
            else {
            	obofile.setValidation("FAILED VALIDATION");
            }
            
            //generate txt summary report
            GenerateEditorReport generateeditorreport = new GenerateEditorReport( validatecomponents, filePath, filePathTextReport );
            //generate txt summary report
            GenerateEditorPDF generateeditorpdf = new GenerateEditorPDF( validatecomponents, treebuilder, filePath, filePathPdfReport );

            file = new File(filePath);
            input = new FileInputStream(file);

            obofile.setContent(input);

            file = null;
	        file = new File(filePathTextReport);
            input = new FileInputStream(file);

            obofile.setTextreport(input);
            obofile.setTextreportname(fileTextReport);
            obofile.setTextreportdate(Utility.MySQLDateTime.now());
            obofile.setTextreportlength(file.length());
            obofile.setTextreporttype(obofile.getContenttype());
            
            file = null;
	        file = new File(filePathPdfReport);
            input = new FileInputStream(file);

            obofile.setPdfreport(input);
            obofile.setPdfreportname(filePdfReport);
            obofile.setPdfreportdate(Utility.MySQLDateTime.now());
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
