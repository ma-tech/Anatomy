/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOLoadFileIntoDatabase.java
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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import daolayer.DAOFactory;
import daolayer.OBOFileDAO;
import daomodel.OBOFile;

import obomodel.OBOComponent;
import utility.ExecuteCommand;
import utility.ImportFile;
import utility.ImportDatabase;
import utility.MapBuilder;
import utility.TreeBuilder;
import utility.GenerateSQL;
import utility.ValidateComponents;

public class RunOBOLoadFileIntoDatabase {
	/*
	 * run Method
	 */
    public static void run(String Oid) throws Exception {
    	
	    // Obtain DAOFactory.
	    DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
	    // Obtain DAOs.
	    OBOFileDAO obofileDAO = anatomy008.getOBOFileDAO();
	    // Find a OBOFile
	    OBOFile obofile = obofileDAO.findWithBinary(Long.parseLong(Oid));
	    
        // Open streams.
        OutputStream output = null;
        InputStream input = null;
        InputStream textinput = null;
        InputStream pdfinput = null;
        File file = null;
        
        String[] nameArray = obofile.getName().split("\\.");
        String fileName = nameArray[0] + "." + nameArray[1];
        String filePath = "/tmp/" + fileName;
        String command1 = "rm " + filePath;

        //System.out.println("-- command1 " + command1 + " --");

        ExecuteCommand.execute(command1);

        file = new File(filePath);
        output = new FileOutputStream(file);
        
        input = obofile.getContent();
        textinput = obofile.getTextreport();
        pdfinput = obofile.getPdfreport();
        
        IOUtils.copy(input, output);
        
        //import Obo File from obo.properties, file.oboinfile
        ImportFile importfile = new ImportFile(filePath);
        ArrayList<OBOComponent> parseNewTermList = importfile.getTermList();
        //Build hashmap of NEW components
        MapBuilder newmapbuilder = new MapBuilder(parseNewTermList);
        //Build tree
        TreeBuilder newtreebuilder = new TreeBuilder(newmapbuilder);

        //System.out.println("-- parseNewTermList.size() " + parseNewTermList.size() + " --");

        //import Database from dao.properties, anatomy008.url
        ImportDatabase importdatabase = new ImportDatabase(true, "EMAP" );
        ArrayList<OBOComponent> parseOldTermList = importdatabase.getTermList();
        //Build hashmap of OLD components
        MapBuilder oldmapbuilder = new MapBuilder(parseOldTermList);
        //Build tree
        TreeBuilder oldtreebuilder = new TreeBuilder(oldmapbuilder);

        //check for rules violation
        ValidateComponents validatecomponents =
            new ValidateComponents( parseNewTermList, parseOldTermList, newtreebuilder);
        
        if ( validatecomponents.getProblemTermList().isEmpty() ) {
        	System.out.println("PASSED VALIDATION");

        	// Update the Database
            GenerateSQL generatesql = new GenerateSQL( true, parseNewTermList, newtreebuilder, oldtreebuilder, "mouse", "EMAP" );
            
            if ( generatesql.isProcessed()) {
                System.out.println("======================");
                System.out.println("GenerateSQL - SUCCESS!");
                System.out.println("======================");

            	obofile.setValidation("UPDATED");

                obofileDAO.save(obofile);
            }
            else {
                System.out.println("======================");
            	System.out.println("GenerateSQL - FAILED!");
                System.out.println("======================");

                obofile.setValidation("FAILED UPDATE");
            }
            
        }
        else {
        	System.out.println("FAILED VALIDATION");
        	obofile.setValidation("FAILED VALIDATION");
        }

        obofile.setContent(input);
        obofile.setTextreport(textinput);
        obofile.setPdfreport(pdfinput);

        obofileDAO.save(obofile);

    }
}
