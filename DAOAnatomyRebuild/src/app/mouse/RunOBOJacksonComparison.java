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
package app.mouse;

import java.io.File;
import java.io.InputStream;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import daointerface.JOINTimedNodeNodeStageDAO;
import daointerface.ComponentDAO;

import daolayer.DAOFactory;

import daomodel.JOINTimedNodeNodeStage;
import daomodel.Component;

import utility.FileUtil;
import utility.CsvUtil;
import utility.ObjectConverter;
import utility.Wrapper;

public class RunOBOJacksonComparison {

	public static void run() throws Exception {

        Wrapper.printMessage("RunOBOJacksonComparison.run", "*", "*");

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");

        // Obtain DAOs.
        JOINTimedNodeNodeStageDAO jointimednodenodestageDAO = anatomy008.getDAOImpl(JOINTimedNodeNodeStageDAO.class);
        ComponentDAO componentDAO = anatomy008.getDAOImpl(ComponentDAO.class);
        
        File fileIn = new File("/Users/mwicks/Dropbox/Work/Anatomy/TerryHayamizu_Mouse/2012-05-09/revisedEMAPnames_2012-05-09_IN.csv");
        File fileOut = new File("/Users/mwicks/Dropbox/Work/Anatomy/TerryHayamizu_Mouse/2012-05-09/revisedEMAPnames_2012-05-09_OUT.csv");
        
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
        		rowStringOutAll.add(column);
        	}
        	
        	Iterator<String> iteratorRowStringOut1 = rowStringOut1.iterator();
        	
        	while (iteratorRowStringOut1.hasNext()) {

        		String column = iteratorRowStringOut1.next();
        		rowStringOutAll.add(column);
        	}

        	Iterator<String> iteratorRowStringOut2 = rowStringOut2.iterator();
        	
        	while (iteratorRowStringOut2.hasNext()) {

        		String column = iteratorRowStringOut2.next();
        		rowStringOutAll.add(column);
        	}
    		rowColumnListJoin.add(rowStringOutAll);

    	}

        // Format CSV.
        InputStream csvOutput = CsvUtil.formatCsv(rowColumnListJoin);

        // Save CSV.
        FileUtil.write(fileOut, csvOutput);
        
        Wrapper.printMessage("RunOBOJacksonComparison.run : " + "===========", "*", "*");
        Wrapper.printMessage("RunOBOJacksonComparison.run : " + "Error Stats", "*", "*");
        Wrapper.printMessage("RunOBOJacksonComparison.run : " + "-----------", "*", "*");
        Wrapper.printMessage("RunOBOJacksonComparison.run : " + "Cannot Find an EMAPA ID in Database = " +  ObjectConverter.convert(emapaMissing, String.class), "*", "*");
        Wrapper.printMessage("RunOBOJacksonComparison.run : ", "*", "*");
        Wrapper.printMessage("RunOBOJacksonComparison.run : " + "===========", "*", "*");
    }
}
