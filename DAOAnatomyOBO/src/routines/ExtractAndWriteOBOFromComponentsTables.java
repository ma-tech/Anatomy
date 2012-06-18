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

package routines;

import java.util.ArrayList;
import java.util.List;

import obomodel.OBOComponent;

import obolayer.OBOFactory;
import obolayer.ComponentOBO;

import routines.ListOBOComponentsFromComponentsTables;

public class ExtractAndWriteOBOFromComponentsTables {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

        // Obtain OBOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("file");
        ComponentOBO componentOBO = obofactory.getComponentOBO();
        
        //import database components table contents into OBOComponent format
        ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables();
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importcomponents.getTermList();
        
        // Write extracted OBOComponents into Obo File Format
        componentOBO.setComponentList((ArrayList<OBOComponent>) obocomponents);
        componentOBO.createTemplateRelationList();

        if ( componentOBO.writeAll() ) {
            System.out.println("Obo File SUCCESSFULLY written to " + componentOBO.outputFile());
        }
        else {
            System.out.println("Obo File FAILED to write to " + componentOBO.outputFile());
        }
    }
}
