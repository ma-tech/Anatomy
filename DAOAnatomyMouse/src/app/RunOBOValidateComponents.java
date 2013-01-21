/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        OBOValidateComponents.java
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
* Description:  A Main Class that Validates OBO data previously loaded into ANA_COMPONENT... 
*                tables in the Anatomy database .
*
*               Required Files:
*                1. dao.properties file contains the database access attributes
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

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import utility.Wrapper;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.StageRangeDAO;

import daomodel.StageRange;

public class RunOBOValidateComponents {

	public static void run() throws Exception {
    	
    	try {
    		
	        Wrapper.printMessage("RunOBOValidateComponents.run", "*", "*");

            // Obtain DAOFactory.
            DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");

            // Obtain DAOs.
            StageRangeDAO stagerangeDAO = anatomy008.getDAOImpl(StageRangeDAO.class);

            int i = 0;
            
            List<StageRange> daostageranges = new ArrayList<StageRange>();
            daostageranges = stagerangeDAO.listByExistingChildPartOfExistingParent();

	        Wrapper.printMessage("RunOBOValidateComponents.run : " + "Test 1", "*", "*");
	        Wrapper.printMessage("RunOBOValidateComponents.run : " + "\t========================================================================", "*", "*");
	        Wrapper.printMessage("RunOBOValidateComponents.run : " + "\tThe Number of EXISTING Child Nodes OUTSIDE EXISTING Parent Stage Range = " + Integer.toString(daostageranges.size()), "*", "*");
	        Wrapper.printMessage("RunOBOValidateComponents.run : " + "\t========================================================================", "*", "*");

            String childId = "";
            String childName = "";
            String childStart = "";
            String childEnd = "";
            String parentId = "";
            String parentName = "";
            String parentStart = "";
            String parentEnd = "";
            
	        Wrapper.printMessage("RunOBOValidateComponents.run : " + String.format("\t%14s\t%75s\t%10s\t%10s\t%14s\t%75s\t%10s\t%10s", 
            		childId, childName, childStart, childEnd, parentId, parentName, parentStart, parentEnd), "*", "*");
            
            childId = "childId";
            childName = "childName";
            childStart = "childStart";
            childEnd = "childEnd";
            parentId = "parentId";
            parentName = "parentName";
            parentStart = "parentStart";
            parentEnd = "parentEnd";
            
	        Wrapper.printMessage("RunOBOValidateComponents.run : " + String.format("\t%14s\t%75s\t%10s\t%10s\t%14s\t%75s\t%10s\t%10s", 
            		childId, childName, childStart, childEnd, parentId, parentName, parentStart, parentEnd), "*", "*");

            childId = "--------------";
            childName = "---------------------------------------------------------------------------";
            childStart = "----------";
            childEnd = "----------";
            parentId = "--------------";
            parentName = "---------------------------------------------------------------------------";
            parentStart = "----------";
            parentEnd = "----------";
            
	        Wrapper.printMessage("RunOBOValidateComponents.run : " + String.format("\t%14s\t%75s\t%10s\t%10s\t%14s\t%75s\t%10s\t%10s", 
            		childId, childName, childStart, childEnd, parentId, parentName, parentStart, parentEnd), "*", "*");

	        Iterator<StageRange> iteratorStageRange = daostageranges.iterator();
          	
            while (iteratorStageRange.hasNext()) {
           		
            	StageRange stagerange = iteratorStageRange.next();
           		i++;
           		
    	        Wrapper.printMessage("RunOBOValidateComponents.run : " + Integer.toString(i) + stagerange.reportStageRange(), "*", "*");
          	}

    	}
		catch (DAOException daoexception) {

			daoexception.printStackTrace();
		}
    }
}
