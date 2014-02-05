/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
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
* Version:      1
*
* Description:  
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

import utility.Wrapper;

import obomodel.OBOComponent;

import obolayer.OBOFactory;

import oboaccess.OBOComponentAccess;

import routines.aggregated.ListOBOComponentsFromComponentsTables;

import daolayer.DAOFactory;

public class ExtractAndWriteOBOFromComponentsTables {

	public static void run( DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    Wrapper.printMessage("extractandwriteobofromcomponentstables.run", "***", daofactory.getMsgLevel());

	    OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();
        
        //import database components table contents into OBOComponent format
        ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importcomponents.getTermList();
        
        // Write extracted OBOComponents into Obo File Format
        obocomponentaccess.setComponentList((ArrayList<OBOComponent>) obocomponents);
        
        if ( obocomponentaccess.writeAll( "Abstract" ) ) {

        	Wrapper.printMessage("extractandwriteobofromcomponentstables.run : Obo File SUCCESSFULLY written to " + obocomponentaccess.outputFileName() + " for Species " + obofactory.getOBOComponentAccess().species() + " and Project " + obofactory.getOBOComponentAccess().project(), "***", daofactory.getMsgLevel());
        }
        else {
        	
        	Wrapper.printMessage("extractandwriteobofromcomponentstables.run : Obo File FAILED written to " + obocomponentaccess.outputFileName() + " for Species " + obofactory.getOBOComponentAccess().species() + " and Project " + obofactory.getOBOComponentAccess().project(), "***", daofactory.getMsgLevel());
        }
    }
}
