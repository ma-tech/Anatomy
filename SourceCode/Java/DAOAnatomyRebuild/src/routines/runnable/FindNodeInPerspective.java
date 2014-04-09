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

import utility.CsvUtil;
import utility.FileUtil;
import utility.Wrapper;
import csvmodel.AnatomyInPerspective;
import obolayer.OBOFactory;

public class FindNodeInPerspective {
	
	public static void run(OBOFactory obofactory, String fileName, char separator, String searchType, String searchTerm ) throws Exception {
		
		Wrapper.printMessage("FindNodeInPerspective.run", "***", obofactory.getMsgLevel());
	    
        try {
        	
            // Format InputStream for Anatomy.
            InputStream csvInput = FileUtil.readStream(new File( fileName ));
            
            // Create Anatomy List
            AnatomyInPerspective anatomyinperspective = new AnatomyInPerspective( CsvUtil.parseCsv(csvInput, separator) );
        	
            anatomyinperspective.importCSVFile();
            
            if ( searchType.equals("ComponentName")) {
            	
                if ( anatomyinperspective.containsComponentName(searchTerm)) {
                	System.out.println(searchTerm + " IN file " + fileName);
                }
                else {
                	System.out.println(searchTerm + " NOT IN file " + fileName);
                }
            }
            else if ( searchType.equals("PublicId")) {
            	
                if ( anatomyinperspective.containsPublicId(searchTerm)) {
                	System.out.println(searchTerm + " IN file " + fileName);
                }
                else {
                	System.out.println(searchTerm + " NOT IN file " + fileName);
                }
            }
            else {
            	
            	System.out.println("Unknown Search Method = " + searchType + " for Search Term = " + searchTerm);
            }


            
        }
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}
}