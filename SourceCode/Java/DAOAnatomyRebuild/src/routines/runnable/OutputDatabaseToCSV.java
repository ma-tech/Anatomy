/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        UpdateDatabaseWithPerspectiveAmbits.java
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
* Description:  A Class that Reads a CSV File of Perspective Ambits with Foreign Keys added
*                and Loads it into an existing Anatomy database;
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
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utility.CsvUtil;
import utility.FileUtil;
import utility.Wrapper;

import anatomy.Anatomy;

import obolayer.OBOFactory;

import daolayer.DAOException;
import daolayer.DAOFactory;

public class OutputDatabaseToCSV {
	
	public static void run(DAOFactory daofactory, OBOFactory obofactory, String fileName, char separator ) throws Exception {
		
		Wrapper.printMessage("OutputDatabaseToCSV.run", "***", obofactory.getMsgLevel());
	    
        try {
        	
            // Create Empty Anatomy Object
        	Anatomy anatomy = new Anatomy( daofactory );
        	
        	// Populate the Anatomy Array with Data from 16 Tables 
        	anatomy.createCSV2DArrayFromDatabase();
        	//anatomy.createCSV2DArrayFromDatabaseBaseDataOnly();
        	
         	// Format Anatomy into an InputStream
            InputStream csvInput = CsvUtil.formatCsv(anatomy.getCsv2DStringArray(), separator);

            // Save Anatomy to file
            FileUtil.write(new File( fileName ), csvInput);
            
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}
}