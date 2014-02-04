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
* Version:      1
*
* Description:  A Runnable Class that reads in a OBO file and Writes it out again 
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

import utility.Wrapper;

import obomodel.OBOComponent;

import oboaccess.OBOComponentAccess;

import obolayer.OBOFactory;
import obolayer.OBOException;

public class RunOBOTest {

	public static void run ( OBOFactory obofactory) throws Exception {

		try {
	        // Obtain DAOs.
	        OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();

	        // Read in OBO File
	        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
	        obocomponents = obocomponentaccess.listAllInput();
	        
	        Wrapper.printMessage("Number of File Components Read In = " + obocomponents.size(), "***", obofactory.getMsgLevel()) ;

	        // Write out Obo File
	        obocomponentaccess.setComponentList((ArrayList<OBOComponent>) obocomponents);
	        
	        if (obocomponentaccess.writeAll( "Abstract" )) {

	        	Wrapper.printMessage("Obo File SUCCESSFULLY written to " + obocomponentaccess.outputFileName(), "***", obofactory.getMsgLevel() );
	        }
	        else {
	            
	        	Wrapper.printMessage("Obo File FAILED to write to " + obocomponentaccess.outputFileName(), "***", obofactory.getMsgLevel() );
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
