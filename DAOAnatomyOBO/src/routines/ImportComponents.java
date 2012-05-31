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
import java.util.List;

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
            List<ComponentRelationship> componentrelationships = new ArrayList<ComponentRelationship>();

          	Iterator<Component> iteratorComponent = components.iterator();

          	while (iteratorComponent.hasNext()) {
          		Component component = iteratorComponent.next();

                ComponentRelationship componentrelationship = new ComponentRelationship();

           		if (component.getStatusChange().equals("DELETED") ) {
                    System.out.println("WARNING! obocomponent, = " + component.toString());
           		}
           		else {
           			
                    OBOComponent obocomponent = new OBOComponent();
                    
            		obocomponent.setID(component.getId().replace("\n", " ").trim());
            		obocomponent.setDBID(component.getDbId().replace("\n", " ").trim());
            		obocomponent.setNewID(component.getNewId().replace("\n", " ").trim());
            		obocomponent.setName(component.getName().replace("\n", " ").trim());
            		obocomponent.setNamespace(component.getNamespace().replace("\n", " ").trim());
            		obocomponent.setDefinition(component.getDefinition().replace("\n", " ").trim());
            		obocomponent.setGroup(ObjectConverter.convert(component.getGroup(), Boolean.class));
            		
            		if ( "TBD".equals(component.getStart().replace("\n", " ").trim()) ){
                		obocomponent.setStart("");
            		}
            		else {
                		obocomponent.setStart(component.getStart().replace("\n", " ").trim());
            		}
            		
            		if ( "TBD".equals(component.getEnd().replace("\n", " ").trim()) ){
                		obocomponent.setEnd("");
            		}
            		else {
                		obocomponent.setEnd(component.getEnd().replace("\n", " ").trim());
            		}
            		
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
