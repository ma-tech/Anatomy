/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOTest.java
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
* Description:  A Main Class that reads in a OBO file and Writes it out again 
*
* Required Files:
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

import java.util.ArrayList;
import java.util.List;

import obomodel.OBOComponent;

import obolayer.OBOFactory;
import obolayer.OBOException;

import obolayer.ComponentOBO;

public class RunOBOTest {
	/*
	 * run Method
	 */
	public static void run () {

		try {
	        // Obtain OBOFactory.
	        OBOFactory obofactory = OBOFactory.getInstance("file");
	        //System.out.println("OBOFactory successfully obtained: " + obofactory);

	        // Obtain DAOs.
	        ComponentOBO componentOBO = obofactory.getComponentOBO();
	        //System.out.println("ComponentOBO successfully obtained: " + componentOBO);

	        // Read in OBO File
	        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
	        obocomponents = componentOBO.listAll();
	        
	        /*
	        if (componentOBO.debug()) {
	        	Iterator<OBOComponent> iterator = obocomponents.iterator();

	        	while (iterator.hasNext()) {
	        		OBOComponent obocomponent = iterator.next();
	                System.out.println(obocomponent.toString());
	        	}
	        }
	        */
            System.out.println("Number of File Components Read In = " + Integer.toString(obocomponents.size()));

	        // Write out Obo File
	        componentOBO.setComponentList((ArrayList) obocomponents);
	        componentOBO.createTemplateRelationList();
	        
	        Boolean isProcessed = componentOBO.writeAll();

	        if (isProcessed) {
	            System.out.println("Obo File SUCCESSFULLY written to " + componentOBO.outputFile());
	        }
	        else {
	            System.out.println("Obo File FAILED to write to " + componentOBO.outputFile());
	        }
	        
		}
		catch (OBOException oboexception) {
			oboexception.printStackTrace();
		}
	}
}
