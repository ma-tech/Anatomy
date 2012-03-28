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
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;

import Utility.ExecuteCommand;

import DAOLayer.DAOException;
import DAOLayer.DAOFactory;
import DAOLayer.OBOFileDAO;

import DAOModel.OBOFile;

public class RunDAOExtractOBO {
	
	/*
	 * run Method
	 */
	public static void run () {

		try {
		    // Obtain DAOFactory.
		    DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
		    System.out.println("DAOFactory successfully obtained: " + anatomy008);

		    // Obtain DAOs.
		    OBOFileDAO obofileDAO = anatomy008.getOBOFileDAO();
		    System.out.println("DerivedPartOfDAO successfully obtained: " + obofileDAO);
		    
		    // Find a OBOFile
		    String obofileOid = "1"; 
		    OBOFile obofile = obofileDAO.find(Long.parseLong(obofileOid));
		    System.out.println("obofileDAO.find(\"1\")");
		    System.out.println("Retrieved OBOFile: " + obofile);
		    
            // Open streams.
	        File file = null;
	        OutputStream output = null;
	        
	        String[] name = obofile.getName().split("\\.");

	        String command = "rm /tmp/" + name[0] + "." + name[1];
		    ExecuteCommand.execute(command);

	        //file = File.createTempFile(name[0], "." + name[1], new File("/tmp"));
	        file = new File("/tmp/" + name[0] + "." + name[1]);
		    System.out.println("Temp File: " + file.toString());
		    
            output = new FileOutputStream(file);
            IOUtils.copy(obofile.getContent(), output);
            
		}
        catch (IOException ioexception) {
        	ioexception.printStackTrace();
        }
		catch (DAOException daoexception) {
			daoexception.printStackTrace();
		}

	}
    
}
