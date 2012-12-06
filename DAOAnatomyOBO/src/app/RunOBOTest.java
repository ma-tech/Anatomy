/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
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

import daolayer.DAOFactory;

import utility.Wrapper;

public class RunOBOTest {

	public static void run ( String requestMsgLevel, OBOFactory obofactory, DAOFactory daofactory) throws Exception {

		try {
	        // Obtain DAOs.
	        ComponentOBO componentOBO = obofactory.getComponentOBO();

	        // Read in OBO File
	        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
	        obocomponents = componentOBO.listAll();
	        
	        Wrapper.printMessage("Number of File Components Read In = " + Integer.toString(obocomponents.size()), "***", requestMsgLevel);

	        // Write out Obo File
	        componentOBO.setComponentList((ArrayList<OBOComponent>) obocomponents);
	        
	        if (componentOBO.writeAll( "Abstract" )) {

	        	Wrapper.printMessage("Obo File SUCCESSFULLY written to " + componentOBO.outputFile(), "***", requestMsgLevel);
	        }
	        else {
	            
	        	Wrapper.printMessage("Obo File FAILED to write to " + componentOBO.outputFile(), "***", requestMsgLevel);
	        }
	        
		}
		catch (OBOException oboexception) {

			oboexception.printStackTrace();
		}
		catch (Exception exception) {
			
			exception.printStackTrace();
		}
	}
}
