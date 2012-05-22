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

public class RunOBOBardUpdateRelationship {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        //System.out.println("DAOFactory successfully obtained: " + anatomy008);

        // Obtain DAOs.
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getComponentAlternativeDAO();
        
        File fileIn = new File("/Users/mwicks/Desktop/anaOboComponentRelationship_IN.csv");
        File fileOut = new File("/Users/mwicks/Desktop/anaOboComponentRelationship_OUT.csv");
        
        InputStream fileStream = FileUtil.readStream(fileIn);
        
        List<List<String>> rowColumnListIn = CsvUtil.parseCsv(fileStream);
        List<List<String>> rowColumnListOut = new ArrayList<List<String>>();
        
    	Iterator<List<String>> iteratorRow = rowColumnListIn.iterator();

    	while (iteratorRow.hasNext()) {

    		List<String> rowString = iteratorRow.next();
    		List<String> rowStringOut = new ArrayList<String>();
            
    		String acrOid = rowString.get(0);
    		String acrOboChild = rowString.get(1);
    		String acrOboChildStart = rowString.get(2);
    		String acrOboChildStop = rowString.get(3);
    		String acrOboType = rowString.get(4);
    		String acrOboParent = rowString.get(5);
    		
    		String acrOboChildAlt = "";
    		String acrOboParentAlt = "";

       		if ( !"".equals(acrOboChild) ) {
       			ComponentAlternative componentalternativeCARO = componentalternativeDAO.findByOboIdCARO(acrOboChild);

    			if (componentalternativeCARO == null){

           			ComponentAlternative componentalternativeAEO = componentalternativeDAO.findByOboIdAEO(acrOboChild);

        			if (componentalternativeAEO == null){

        				acrOboChildAlt = acrOboChild;
        			}
        			else { 
        				acrOboChildAlt = componentalternativeAEO.getAltId();
        			}
    			}
    			else {
    				acrOboChildAlt = componentalternativeCARO.getAltId();
    			}
       		}
    			
       		if ( !"".equals(acrOboParent) ) {
       			ComponentAlternative componentalternativeCARO = componentalternativeDAO.findByOboIdCARO(acrOboParent);

    			if (componentalternativeCARO == null){

           			ComponentAlternative componentalternativeAEO = componentalternativeDAO.findByOboIdAEO(acrOboParent);

        			if (componentalternativeAEO == null){

        				acrOboParentAlt = acrOboParent;
        			}
        			else { 
        				acrOboParentAlt = componentalternativeAEO.getAltId();
        			}
    			}
    			else {
    				acrOboParentAlt = componentalternativeCARO.getAltId();
    			}
       		}
       		
       		rowStringOut.add(acrOid);
       		rowStringOut.add(acrOboChildAlt);
       		rowStringOut.add(acrOboChildStart);
       		rowStringOut.add(acrOboChildStop);
       		rowStringOut.add(acrOboType);
       		rowStringOut.add(acrOboParentAlt);
 
       		rowColumnListOut.add(rowStringOut);
       		
    	}

        // Format CSV.
        InputStream csvOutput = CsvUtil.formatCsv(rowColumnListOut);

        // Save CSV.
        FileUtil.write(fileOut, csvOutput);
        
    }
}
