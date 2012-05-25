/*
*----------------------------------------------------------------------------------------------
* Project:      Anatomy
*
* Title:        ImportDatabase.java
*
* Date:         2008
*
* Author:       MeiSze Lam and Attila Gyenesi
*
* Copyright:    2009 Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Description:  This Class extracts a list of components from the database, and builds a list 
*                in the OBO style.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; February 2012; Completely rewrite to use standardised DAO Layer
* 
*----------------------------------------------------------------------------------------------
*/

package routines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//import daolayer.ComponentAlternativeDAO;
import daolayer.ComponentCommentDAO;
import daolayer.ComponentDAO;
import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentSynonymDAO;
import daolayer.DAOFactory;
import daolayer.DAOException;

import daomodel.Component;
import daomodel.ComponentComment;
import daomodel.ComponentRelationship;
import daomodel.ComponentSynonym;

import obomodel.OBOComponent;

import utility.ObjectConverter;


public class ImportComponents {

    // Properties ---------------------------------------------------------------------------------

    // global variables
    private ArrayList <OBOComponent> obocomponentList = new ArrayList <OBOComponent>();

    
    // Constructor --------------------------------------------------------------------------------
    public ImportComponents() throws IOException {
    	
    	try {
            // Obtain DAOFactory.
            DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");

            // Obtain DAOs.
            ComponentDAO componentDAO = anatomy008.getComponentDAO();
            ComponentRelationshipDAO componentrelationshipDAO = anatomy008.getComponentRelationshipDAO();
            ComponentCommentDAO componentcommentDAO = anatomy008.getComponentCommentDAO();
            ComponentSynonymDAO componentsynonymDAO = anatomy008.getComponentSynonymDAO();
            //ComponentAlternativeDAO componentalternativeDAO = anatomy008.getComponentAlternativeDAO();

            ArrayList<Component> components = (ArrayList<Component>) componentDAO.listAll(); 
            
          	Iterator<Component> iteratorComponent = components.iterator();

          	while (iteratorComponent.hasNext()) {
          		Component component = iteratorComponent.next();

           		if (component.getStatusChange().equals("DELETED") ) {
                    System.out.println("WARNING! obocomponent, = " + component.toString());
           		}
           		else {
               		OBOComponent obocomponent = new OBOComponent(
               				component.getName(),
               				component.getId(),
               				component.getDbId(),
               				component.getNewId(),
               				component.getNamespace(),
               				component.getDefinition(),
               				ObjectConverter.convert(component.getGroup(), Boolean.class),
               				component.getStart(),
               				component.getEnd(),
               				component.getPresent(),
               				component.getStatusChange(),
               				component.getStatusRule());
               		
               		// Get Relationships for this Component
               		ArrayList<ComponentRelationship> componentrelationships = 
               				(ArrayList<ComponentRelationship>) componentrelationshipDAO.listByOboId(component.getId());
               		
                  	Iterator<ComponentRelationship> iteratorComponentRelationship = componentrelationships.iterator();

                  	// Add Relationships to component
                  	while (iteratorComponentRelationship.hasNext()) {
                  		ComponentRelationship componentrelationship = iteratorComponentRelationship.next();

                  		if ( !"".equals(componentrelationship.getParent()) ) {
                      		obocomponent.addChildOf(componentrelationship.getParent());
                  		}
                  		if ( !"".equals(componentrelationship.getType()) ) {
                      		obocomponent.addChildOfType(componentrelationship.getType());
                  		}
                  	}

               		// Get Comments for this Component
               		ArrayList<ComponentComment> componentcomments = 
               				(ArrayList<ComponentComment>) componentcommentDAO.listByOboId(component.getId());
               		
                  	Iterator<ComponentComment> iteratorComponentComment = componentcomments.iterator();

                  	// Add Comments to component
                  	while (iteratorComponentComment.hasNext()) {
                  		ComponentComment componentcomment = iteratorComponentComment.next();
                  		
                  		if ( !"".equals(componentcomment.getUser()) ) {
                      		obocomponent.addUserComments(componentcomment.getUser());
                  		}
                  		if ( !"".equals(componentcomment.getGeneral()) ) {
                      		obocomponent.addUserComments(componentcomment.getGeneral());
                  		}

                  	}

               		// Get Synonyms for this Component
               		ArrayList<ComponentSynonym> componentsynonyms = 
               				(ArrayList<ComponentSynonym>) componentsynonymDAO.listByOboId(component.getId());

                  	Iterator<ComponentSynonym> iteratorComponentSynonym = componentsynonyms.iterator();

                  	// Add Synonyms to component
               		while (iteratorComponentSynonym.hasNext()) {
               			ComponentSynonym componentsynonym = iteratorComponentSynonym.next();

                  		if ( !"".equals(componentsynonym.getText()) ) {
                      		obocomponent.addSynonym(componentsynonym.getText());
                  		}
                  	}

               		obocomponentList.add(obocomponent);
           			
           		}
          	}
          	
    	}
    	catch (DAOException daoe){
    		daoe.printStackTrace();
    	}

    }

    public ArrayList<OBOComponent> getTermList() {
        return obocomponentList;
    }

}
