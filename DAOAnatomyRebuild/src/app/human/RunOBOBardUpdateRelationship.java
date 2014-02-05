/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOBardUpdateRelationship.java
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
* Description:  A Main Class that Reads an OBO File and populates 4 tables in the anatomy
*                database with the extracted data.
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package app.human;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import utility.Wrapper;
import utility.FileUtil;
import utility.CsvUtil;

import daointerface.ComponentAlternativeDAO;

import daolayer.DAOFactory;

import daomodel.ComponentAlternative;

public class RunOBOBardUpdateRelationship {

	public static void run() throws Exception {

        Wrapper.printMessage("RunOBOBardUpdateRelationship.run", "*", "*");

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");

        // Obtain DAOs.
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getDAOImpl(ComponentAlternativeDAO.class);
        
        File fileIn = new File("/Users/mwicks/Dropbox/Work/Anatomy/JonathanBard_Human/2012-05-23/anaOboComponentRelationship_IN.csv");
        File fileOut = new File("/Users/mwicks/Dropbox/Work/Anatomy/JonathanBard_Human/2012-05-23/anaOboComponentRelationship_OUT.csv");
        
        InputStream fileStream = FileUtil.readStream(fileIn);
        
        List<List<String>> rowColumnListIn = CsvUtil.parseCsv(fileStream);
        List<List<String>> rowColumnListOut = new ArrayList<List<String>>();
        
    	Iterator<List<String>> iteratorRow = rowColumnListIn.iterator();

    	while (iteratorRow.hasNext()) {

    		List<String> rowString = iteratorRow.next();
    		List<String> rowStringOut = new ArrayList<String>();
            
    		String acrOid = rowString.get(0).replace(',', ' ');
    		String acrOboChild = rowString.get(1).replace(',', ' ');
    		String acrOboChildStart = rowString.get(2).replace(',', ' ');
    		String acrOboChildStop = rowString.get(3).replace(',', ' ');
    		String acrOboType = rowString.get(4).replace(',', ' ');
    		String acrOboParent = rowString.get(5).replace(',', ' ');
    		
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
