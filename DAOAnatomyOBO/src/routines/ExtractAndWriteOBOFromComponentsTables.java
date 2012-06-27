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

import daolayer.DAOFactory;

import routines.ListOBOComponentsFromComponentsTables;


public class ExtractAndWriteOBOFromComponentsTables {
	/*
	 * run Method
	 */
    public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {

        ComponentOBO componentOBO = obofactory.getComponentOBO();
        
        //import database components table contents into OBOComponent format
        ListOBOComponentsFromComponentsTables importcomponents = new ListOBOComponentsFromComponentsTables( daofactory, obofactory );
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importcomponents.getTermList();
        
        // Write extracted OBOComponents into Obo File Format
        componentOBO.setComponentList((ArrayList<OBOComponent>) obocomponents);

        if ( "mouse".equals(obofactory.getComponentOBO().species())) {
            componentOBO.createTemplateRelationList(daofactory);
        }
        if ( "human".equals(obofactory.getComponentOBO().species())) {
            componentOBO.createHumanRelationList();
        }
        
        if ( componentOBO.writeAll() ) {
        	if ( obofactory.getComponentOBO().debug() ) {
                System.out.println("Obo File SUCCESSFULLY written to " + componentOBO.outputFile() + " for Species " + obofactory.getComponentOBO().species() + " and Project " + obofactory.getComponentOBO().project());
        	}
        }
        else {
        	if ( obofactory.getComponentOBO().debug() ) {
                System.out.println("Obo File FAILED written to " + componentOBO.outputFile() + " for Species " + obofactory.getComponentOBO().species() + " and Project " + obofactory.getComponentOBO().project());
        	}
        }
    }
}
