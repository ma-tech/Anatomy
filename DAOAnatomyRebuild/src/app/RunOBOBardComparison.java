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

import daolayer.JOINTimedNodeNodeStageDAO;
import daolayer.ComponentDAO;
import daolayer.ComponentAlternativeDAO;

import daolayer.DAOFactory;

import daomodel.JOINTimedNodeNodeStage;
import daomodel.Component;
import daomodel.ComponentAlternative;

import utility.FileUtil;
import utility.CsvUtil;
import utility.ObjectConverter;

public class RunOBOBardComparison {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        //System.out.println("DAOFactory successfully obtained: " + anatomy008);

        // Obtain DAOs.
        JOINTimedNodeNodeStageDAO jointimednodenodestageDAO = anatomy008.getJOINTimedNodeNodeStageDAO();
        ComponentDAO componentDAO = anatomy008.getComponentDAO();
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getComponentAlternativeDAO();
        
        File fileIn = new File("/Users/mwicks/Dropbox/Work/Anatomy/JonathanBard_Human/2012-05-17/anaOboComponent.csv.csv");
        File fileOut = new File("/Users/mwicks/Dropbox/Work/Anatomy/JonathanBard_Human/2012-05-17/anaOboComponent.csv_OUT.csv");
        
        InputStream fileStream = FileUtil.readStream(fileIn);
        
        List<List<String>> rowColumnListIn = CsvUtil.parseCsv(fileStream);
        List<List<String>> rowColumnListOut = new ArrayList<List<String>>();
        List<List<String>> rowColumnListOut2 = new ArrayList<List<String>>();
        List<List<String>> rowColumnListJoin = new ArrayList<List<String>>();
        
    	Iterator<List<String>> iteratorRow = rowColumnListIn.iterator();

    	int i = 0;
    	int emapaMissing = 0;
    	int duncanMissing = 0;
    	
    	while (iteratorRow.hasNext()) {

    		i++;
    		List<String> rowString = iteratorRow.next();

    		List<String> rowStringOut1 = new ArrayList<String>();
    		List<String> rowStringOut2 = new ArrayList<String>();
    		List<String> rowStringOutAll = new ArrayList<String>();
            
    		String emapId = rowString.get(1);
    		
    		//if ( !rowString.get(1).equals("")) {
       		if ( !"".equals(rowString.get(1)) ) {
    			JOINTimedNodeNodeStage jointimednodenodestage = jointimednodenodestageDAO.findByEmap(emapId);

    			if (jointimednodenodestage == null){
    				
    				emapaMissing++;
    				
    				if ( i == 1) {
    					rowColumnListOut.add(Arrays.asList("EMAPA ID", "EMAPA NAME"));
    					rowStringOut1.add("EMAPA ID");
    					rowStringOut1.add("EMAPA NAME");
        				rowColumnListOut2.add(Arrays.asList("DUNCAN EMAPA ID", "DUNCAN EMAPA NAME"));
    					rowStringOut2.add("DUNCAN EMAPA ID");
    					rowStringOut2.add("DUNCAN EMAPA NAME");
    				}
    				else {
        				rowColumnListOut.add(Arrays.asList("EMAP ID NOT FOUND", "EMAP ID NOT FOUND"));
    					rowStringOut1.add("EMAP ID NOT FOUND");
    					rowStringOut1.add("EMAP ID NOT FOUND");
        				rowColumnListOut2.add(Arrays.asList("DUNCAN EMAPA ID NOT FOUND", "DUNCAN EMAPA ID NOT FOUND"));
    					rowStringOut2.add("DUNCAN EMAPA ID NOT FOUND");
    					rowStringOut2.add("DUNCAN EMAPA ID NOT FOUND");
    				}
    			}
    			else {
                    rowColumnListOut.add(Arrays.asList(jointimednodenodestage.getPublicNodeId(), jointimednodenodestage.getComponentName()));
					rowStringOut1.add(jointimednodenodestage.getPublicNodeId());
					rowStringOut1.add(jointimednodenodestage.getComponentName());
                    
                    Component component = componentDAO.findByOboId(jointimednodenodestage.getPublicNodeId());
                    
        			if (component == null){
        				
        				duncanMissing++;
        				
        				rowColumnListOut2.add(Arrays.asList("DUNCAN EMAPA ID NOT FOUND", "DUNCAN EMAPA ID NOT FOUND"));
    					rowStringOut2.add("DUNCAN EMAPA ID NOT FOUND");
    					rowStringOut2.add("DUNCAN EMAPA ID NOT FOUND");
        			}
        			else {
        				rowColumnListOut2.add(Arrays.asList(component.getId(), component.getName()));
    					rowStringOut2.add(component.getId());
    					rowStringOut2.add(component.getName());
        			}
    			}
    		}
 
        	Iterator<String> iteratorRowString = rowString.iterator();
        	while (iteratorRowString.hasNext()) {

        		String column = iteratorRowString.next();
        		//System.out.println(column);
        		rowStringOutAll.add(column);
        	}
        	Iterator<String> iteratorRowStringOut1 = rowStringOut1.iterator();
        	while (iteratorRowStringOut1.hasNext()) {

        		String column = iteratorRowStringOut1.next();
        		//System.out.println(column);
        		rowStringOutAll.add(column);
        	}
        	Iterator<String> iteratorRowStringOut2 = rowStringOut2.iterator();
        	while (iteratorRowStringOut2.hasNext()) {

        		String column = iteratorRowStringOut2.next();
        		//System.out.println(column);
        		rowStringOutAll.add(column);
        	}
    		rowColumnListJoin.add(rowStringOutAll);

    	}

        // Format CSV.
        InputStream csvOutput = CsvUtil.formatCsv(rowColumnListJoin);

        // Save CSV.
        FileUtil.write(fileOut, csvOutput);
        
	    System.out.println("===========");
	    System.out.println("Error Stats");
	    System.out.println("-----------");
	    System.out.println("Cannot Find an EMAPA ID in Database = " +  ObjectConverter.convert(emapaMissing, String.class));
	    System.out.println("Cannot Find an EMAPA ID in OBO File = " +  ObjectConverter.convert(duncanMissing, String.class));
	    System.out.println("===========");
        

    }
}
