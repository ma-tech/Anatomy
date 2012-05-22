/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOLoadComponents.java
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
* Description:  A Main Class that Reads an OBO File and populates 4 tables in the anatomy
*                database with the extracted data.
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
import java.io.InputStream;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import daolayer.ComponentAlternativeDAO;

import daolayer.DAOFactory;

import daomodel.ComponentAlternative;

import utility.FileUtil;
import utility.CsvUtil;
import utility.ObjectConverter;

public class RunOBOBardUpdateComponent {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        //System.out.println("DAOFactory successfully obtained: " + anatomy008);

        // Obtain DAOs.
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getComponentAlternativeDAO();
        
        File fileIn = new File("/Users/mwicks/Desktop/anaOboComponent_IN.csv");
        File fileOut = new File("/Users/mwicks/Desktop/anaOboComponent_OUT.csv");
        
        InputStream fileStream = FileUtil.readStream(fileIn);
        
        List<List<String>> rowColumnListIn = CsvUtil.parseCsv(fileStream);
        List<List<String>> rowColumnListOut = new ArrayList<List<String>>();
        
    	Iterator<List<String>> iteratorRow = rowColumnListIn.iterator();

    	while (iteratorRow.hasNext()) {

    		List<String> rowString = iteratorRow.next();
    		List<String> rowStringOut = new ArrayList<String>();
    		/*
    		 * AOC_OID	
    		 * AOC_NAME	
    		 * AOC_OBO_ID	
    		 * AOC_DB_ID	
    		 * AOC_NEW_ID	
    		 * AOC_NAMESPACE	
    		 * AOC_DEFINITION	
    		 * AOC_GROUP	
    		 * AOC_START	
    		 * AOC_END	
    		 * AOC_PRESENT	
    		 * AOC_STATUS_CHANGE	
    		 * AOC_STATUS_RULE
    		 */
            
    		String aocOid = rowString.get(0);
    		String aocName = rowString.get(1);
    		String aocOboId = rowString.get(2);
    		String aocOboDbId = rowString.get(3);
    		String aocNewId = rowString.get(4);
    		String aocNamespace = rowString.get(5);
    		String aocDefinition = rowString.get(6);
    		String aocGroup = rowString.get(7);
    		String aocStart = rowString.get(8);
    		String aocEnd = rowString.get(9);
    		String aocPresent = rowString.get(10);
    		String aocStatusChange = rowString.get(11);
    		String aocStatusRule = rowString.get(12);
    		
    		String aocOboAltId = "";

       		if ( !"".equals(aocOboId) ) {
       			ComponentAlternative componentalternativeCARO = componentalternativeDAO.findByOboIdCARO(aocOboId);

    			if (componentalternativeCARO == null){

           			ComponentAlternative componentalternativeAEO = componentalternativeDAO.findByOboIdAEO(aocOboId);

        			if (componentalternativeAEO == null){

        				aocOboAltId = aocOboId;
        			}
        			else { 
        				aocOboAltId = componentalternativeAEO.getAltId();
        			}
    			}
    			else {
    				aocOboAltId = componentalternativeCARO.getAltId();
    			}
       		}
    			
       		rowStringOut.add(aocOid);
       		rowStringOut.add(aocName);
       		rowStringOut.add(aocOboAltId);
       		rowStringOut.add(aocOboDbId);
       		rowStringOut.add(aocNewId);
       		rowStringOut.add(aocNamespace);
       		rowStringOut.add(aocDefinition);
       		rowStringOut.add(aocGroup);
       		rowStringOut.add(aocStart);
       		rowStringOut.add(aocEnd);
       		rowStringOut.add(aocPresent);
       		rowStringOut.add(aocStatusChange);
       		rowStringOut.add(aocStatusRule);
 
       		rowColumnListOut.add(rowStringOut);
       		
    	}

        // Format CSV.
        InputStream csvOutput = CsvUtil.formatCsv(rowColumnListOut);

        // Save CSV.
        FileUtil.write(fileOut, csvOutput);
        
    }
}
