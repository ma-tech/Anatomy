/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        ListOBOComponentsFromComponentsTables.java
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
* Mike Wicks; November 2012; More tidying up
* 
*----------------------------------------------------------------------------------------------
*/
package routines.aggregated;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daolayer.ComponentDAO;
import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentCommentDAO;
import daolayer.ComponentOrderDAO;
import daolayer.ComponentSynonymDAO;
import daolayer.ComponentAlternativeDAO;

import daolayer.DAOFactory;
import daolayer.DAOException;

import daomodel.Component;
import daomodel.ComponentRelationship;
import daomodel.ComponentComment;
import daomodel.ComponentOrder;
import daomodel.ComponentSynonym;
import daomodel.ComponentAlternative;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import utility.ObjectConverter;
import utility.Wrapper;

public class ListOBOComponentsFromComponentsTables {
    // Properties ---------------------------------------------------------------------------------
    // global variables
    private ArrayList <OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
    
    private String requestMsgLevel;
    
    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromComponentsTables(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
		this.requestMsgLevel = requestMsgLevel;
		
		Wrapper.printMessage("listobocomponentsfromcomponentstables.constructor", "LOW", this.requestMsgLevel);

    	try {
    		
            // Obtain DAOs.
            ComponentDAO componentDAO = daofactory.getComponentDAO();
            ComponentRelationshipDAO componentrelationshipDAO = daofactory.getComponentRelationshipDAO();
            ComponentOrderDAO componentorderDAO = daofactory.getComponentOrderDAO();
            ComponentCommentDAO componentcommentDAO = daofactory.getComponentCommentDAO();
            ComponentSynonymDAO componentsynonymDAO = daofactory.getComponentSynonymDAO();
            ComponentAlternativeDAO componentalternativeDAO = daofactory.getComponentAlternativeDAO();

            ArrayList<Component> components = (ArrayList<Component>) componentDAO.listAllOrderByEMAPA(); 
            Iterator<Component> iteratorComponent = components.iterator();

          	while (iteratorComponent.hasNext()) {
          		
          		Component component = iteratorComponent.next();

                OBOComponent obocomponent = new OBOComponent();
                
        		obocomponent.setID(component.getId().replace("\n", " ").trim());
        		obocomponent.setDBID(component.getDbId().replace("\n", " ").trim());
        		obocomponent.setNewID(component.getNewId().replace("\n", " ").trim());
        		obocomponent.setName(component.getName().replace("\n", " ").trim());
        		obocomponent.setNamespace(component.getNamespace().replace("\n", " ").trim());
        		obocomponent.setDefinition(component.getDefinition().replace("\n", " ").trim());
        		obocomponent.setGroup(ObjectConverter.convert(component.getGroup(), Boolean.class));
        		
        		if ( "TBD".equals(component.getStart().replace("\n", " ").trim()) ){
        			
        			obocomponent.setStartSequenceMin( obofactory.getComponentOBO().species() );
        		}
        		else {
        			
            		obocomponent.setStart(component.getStart().replace("\n", " ").trim());
        		}
        		
        		if ( "TBD".equals(component.getEnd().replace("\n", " ").trim()) ){
        			
        			obocomponent.setEndSequenceMax( obofactory.getComponentOBO().species() );
        		}
        		else {
        			
            		obocomponent.setEnd(component.getEnd().replace("\n", " ").trim());
        		}
        		
        		obocomponent.setPresent(component.getPresent());
        		obocomponent.setStatusChange(component.getStatusChange().replace("\n", " ").trim());
        		obocomponent.setStatusRule(component.getStatusRule().replace("\n", " ").trim());

        		List<ComponentRelationship> componentrelationships = componentrelationshipDAO.listByChild(component.getId().replace("\n", " ").trim());
            	Iterator<ComponentRelationship> iteratorComponentRelationship = componentrelationships.iterator();

            	while (iteratorComponentRelationship.hasNext()) {
            		
                    ComponentRelationship componentrelationship = new ComponentRelationship();
                    componentrelationship = iteratorComponentRelationship.next();

            		obocomponent.addChildOf(componentrelationship.getParent().replace("\n", " ").trim());
            		obocomponent.addChildOfType(componentrelationship.getType().replace("\n", " ").trim());
            	}
            	
        		List<ComponentOrder> componentorders = componentorderDAO.listByChild(component.getId().replace("\n", " ").trim());
            	Iterator<ComponentOrder> iteratorComponentOrder = componentorders.iterator();

            	while (iteratorComponentOrder.hasNext()) {
            		
            		ComponentOrder componentorder = new ComponentOrder();
            		componentorder = iteratorComponentOrder.next();

            		if ( "EMAP".equals(obofactory.getComponentOBO().project() ) ) {
            			
                       	obocomponent.addOrderComment("order=" + ObjectConverter.convert(componentorder.getAlphaorder(), String.class) + " for " + componentorder.getParent());
            		}
            		else {
            			
                       	obocomponent.addOrderComment("order=" + ObjectConverter.convert(componentorder.getSpecialorder(), String.class) + " for " + componentorder.getParent());
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
              	
           		if (component.getStatusChange().equals("DELETED") ) {
           			
           			obocomponent.addUserComments("INFO: Obsolete Term");
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

           		// Get Alternative Ids for this Component
           		ArrayList<ComponentAlternative> componentalternatives = 
           				(ArrayList<ComponentAlternative>) componentalternativeDAO.listByOboId(component.getId());

              	Iterator<ComponentAlternative> iteratorComponentAlternative = componentalternatives.iterator();

              	// Add Synonyms to component
           		while (iteratorComponentAlternative.hasNext()) {
           			
           			ComponentAlternative componentalternative = iteratorComponentAlternative.next();

              		if ( !"".equals(componentalternative.getAltId()) ) {
              			
                  		obocomponent.addAlternative(componentalternative.getAltId());
              		}
              	}

           		obocomponentList.add(obocomponent);
           		
           		if ( "part_of".equals(obocomponent.getName()) ) {

           			Wrapper.printMessage("listobocomponentsfromcomponentstables.constructor:obocomponent.toString() = " + obocomponent.toString(), "HIGH", requestMsgLevel);
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