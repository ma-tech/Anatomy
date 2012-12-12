/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        OBOExtractOBOFromExistingDatabase.java
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
* Description:  A Main Class that Reads an Anatomy Database and Writes out the data in OBO
*                Format
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
package routines.runnable.archive;

import java.util.ArrayList;
import java.util.List;

import obomodel.OBOComponent;

import obolayer.OBOFactory;
import obolayer.ComponentOBO;
import routines.aggregated.ListOBOComponentsFromComponentsTables;

import daolayer.DAOFactory;

import utility.Wrapper;

public class ExtractAndWriteOBOFromComponentsTables {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    Wrapper.printMessage("extractandwriteobofromcomponentstables.run", "***", requestMsgLevel);

	    ComponentOBO componentOBO = obofactory.getComponentOBO();
        
        //import database components table contents into OBOComponent format
        ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( requestMsgLevel, daofactory, obofactory );
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importcomponents.getTermList();
        
        // Write extracted OBOComponents into Obo File Format
        componentOBO.setComponentList((ArrayList<OBOComponent>) obocomponents);
        
        if ( componentOBO.writeAll( "Abstract" ) ) {

        	Wrapper.printMessage("extractandwriteobofromcomponentstables.run : Obo File SUCCESSFULLY written to " + componentOBO.outputFile() + " for Species " + obofactory.getComponentOBO().species() + " and Project " + obofactory.getComponentOBO().project(), "***", requestMsgLevel);
        }
        else {
        	
        	Wrapper.printMessage("extractandwriteobofromcomponentstables.run : Obo File FAILED written to " + componentOBO.outputFile() + " for Species " + obofactory.getComponentOBO().species() + " and Project " + obofactory.getComponentOBO().project(), "***", requestMsgLevel);
        }
    }
}
