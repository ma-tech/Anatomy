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

package app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentDAO;
import daolayer.DAOFactory;

import daomodel.ComponentRelationship;
import daomodel.Component;

import obomodel.OBOComponent;

import obolayer.OBOFactory;
import obolayer.ComponentOBO;

import utility.Producer;

import utility.ObjectConverter;

public class RunOBOExtractOBOFromComponents {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        //System.out.println("DAOFactory successfully obtained: " + anatomy008);
        // Obtain DAOs.
        ComponentRelationshipDAO componentrelationshipDAO = anatomy008.getComponentRelationshipDAO();
        ComponentDAO componentDAO = anatomy008.getComponentDAO();
        
        // Obtain OBOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("file");
        // Obtain DAOs.
        ComponentOBO componentOBO = obofactory.getComponentOBO();

        // Export Database Components to OBO File.
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();

        List<ComponentRelationship> componentrelationships = new ArrayList<ComponentRelationship>();
        List<Component> components = new ArrayList<Component>();

        Component component = new Component();
        ComponentRelationship componentrelationship = new ComponentRelationship();
        
        components = componentDAO.listAll();

    	Iterator<Component> iteratorComponent = components.iterator();

    	while (iteratorComponent.hasNext()) {
    		
    		component = iteratorComponent.next();
    		
            OBOComponent obocomponent = new OBOComponent();
            
    		obocomponent.setID(component.getId().replace("\n", " ").trim());
    		obocomponent.setDBID(component.getDbId().replace("\n", " ").trim());
    		obocomponent.setNewID(component.getNewId().replace("\n", " ").trim());
    		obocomponent.setName(component.getName().replace("\n", " ").trim());
    		obocomponent.setNamespace(component.getNamespace().replace("\n", " ").trim());
    		obocomponent.setDefinition(component.getDefinition().replace("\n", " ").trim());
    		obocomponent.setGroup(ObjectConverter.convert(component.getGroup(), Boolean.class));
    		obocomponent.setStart(component.getStart().replace("\n", " ").trim());
    		obocomponent.setEnd(component.getEnd().replace("\n", " ").trim());
    		obocomponent.setPresent(component.getPresent());
    		obocomponent.setStatusChange(component.getStatusChange().replace("\n", " ").trim());
    		obocomponent.setStatusRule(component.getStatusRule().replace("\n", " ").trim());

    		componentrelationships = componentrelationshipDAO.listByOboId(component.getId().replace("\n", " ").trim());

        	Iterator<ComponentRelationship> iteratorComponentRelationship = componentrelationships.iterator();

        	while (iteratorComponentRelationship.hasNext()) {
        		
        		componentrelationship = iteratorComponentRelationship.next();

        		obocomponent.addChildOf(componentrelationship.getParent().replace("\n", " ").trim());
        		obocomponent.addChildOfType(componentrelationship.getType().replace("\n", " ").trim());
        	}
        	
        	obocomponents.add(obocomponent);
    	}
    	
        // Write out Obo File
        componentOBO.setComponentList((ArrayList<OBOComponent>) obocomponents);
        componentOBO.createHumanRelationList();
        
        Boolean isProcessed = componentOBO.writeAll();

        if (isProcessed) {
            System.out.println("Obo File SUCCESSFULLY written to " + componentOBO.outputFile());
        }
        else {
            System.out.println("Obo File FAILED to write to " + componentOBO.outputFile());
        }
    }
}
