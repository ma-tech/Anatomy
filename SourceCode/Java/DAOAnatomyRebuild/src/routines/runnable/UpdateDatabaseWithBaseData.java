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
import java.util.Iterator;
import java.util.List;

import csvmodel.Anatomy;
import utility.CsvUtil;
import utility.FileUtil;
import utility.Wrapper;
import obolayer.OBOFactory;
import daolayer.DAOException;
import daolayer.DAOFactory;

public class UpdateDatabaseWithBaseData {
	
	public static void run(DAOFactory daofactory, OBOFactory obofactory, String fileName, char separator ) throws Exception {
		
		Wrapper.printMessage("UpdateDatabaseWithBaseData.run", "***", obofactory.getMsgLevel());
	    
        try {
        	
            // Format InputStream for Anatomy.
            InputStream csvInput = FileUtil.readStream(new File( fileName ));
            
            // Create Anatomy List
        	Anatomy anatomy = new Anatomy( daofactory, CsvUtil.parseCsv(csvInput, ';') );
        	
        	// Update the Database with the Anatomy Data
        	anatomy.updateDatabase();
        	
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}
}