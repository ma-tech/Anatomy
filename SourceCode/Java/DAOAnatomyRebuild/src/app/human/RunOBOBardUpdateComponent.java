/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOBardUpdateComponent.java
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

public class RunOBOBardUpdateComponent {

	public static void run() throws Exception {

        Wrapper.printMessage("RunOBOBardUpdateComponent.run", "*", "*");

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");

        // Obtain DAOs.
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getDAOImpl(ComponentAlternativeDAO.class);
        
        File fileIn = new File("/Users/mwicks/Dropbox/Work/Anatomy/JonathanBard_Human/2012-05-23/anaOboComponent_IN.csv");
        File fileOut = new File("/Users/mwicks/Dropbox/Work/Anatomy/JonathanBard_Human/2012-05-23/anaOboComponent_OUT.csv");
        
        InputStream fileStream = FileUtil.readStream(fileIn);
        
        List<List<String>> rowColumnListIn = CsvUtil.parseCsv(fileStream);
        List<List<String>> rowColumnListOut = new ArrayList<List<String>>();
        
    	Iterator<List<String>> iteratorRow = rowColumnListIn.iterator();

    	while (iteratorRow.hasNext()) {

    		List<String> rowString = iteratorRow.next();
    		List<String> rowStringOut = new ArrayList<String>();
            
    		String aocOid = rowString.get(0).replace(',', ' ');
    		String aocName = rowString.get(1).replace(',', ' ');
    		String aocOboId = rowString.get(2).replace(',', ' ');
    		String aocOboDbId = rowString.get(3).replace(',', ' ');
    		String aocNewId = rowString.get(4).replace(',', ' ');
    		String aocNamespace = rowString.get(5).replace(',', ' ');
    		String aocDefinition = rowString.get(6).replace(',', ' ');
    		String aocGroup = rowString.get(7).replace(',', ' ');
    		String aocStart = rowString.get(8).replace(',', ' ');
    		String aocEnd = rowString.get(9).replace(',', ' ');
    		String aocPresent = rowString.get(10).replace(',', ' ');
    		String aocStatusChange = rowString.get(11).replace(',', ' ');
    		String aocStatusRule = rowString.get(12).replace(',', ' ');
    		
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
