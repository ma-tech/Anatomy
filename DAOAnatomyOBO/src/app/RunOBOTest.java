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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import obomodel.OBOComponent;

import obolayer.OBOFactory;
import obolayer.OBOException;

import obolayer.ComponentOBO;

import daolayer.DAOFactory;


public class RunOBOTest {
	/*
	 * run Method
	 */
	public static void run (OBOFactory obofactory, DAOFactory daofactory) throws IOException {

		try {
	        // Obtain DAOs.
	        ComponentOBO componentOBO = obofactory.getComponentOBO();

	        // Read in OBO File
	        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
	        obocomponents = componentOBO.listAll();
	        
            System.out.println("Number of File Components Read In = " + Integer.toString(obocomponents.size()));

	        // Write out Obo File
	        componentOBO.setComponentList((ArrayList<OBOComponent>) obocomponents);
	        
	        Boolean isProcessed = componentOBO.writeAll( "Abstract" );

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
