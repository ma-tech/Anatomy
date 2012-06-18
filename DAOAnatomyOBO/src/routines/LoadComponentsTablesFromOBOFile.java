/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOLoadComponents.java
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
* Description:  A Main Class that Reads an OBO File and populates 4 tables in the anatomy
*                database with the extracted data.
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

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import obolayer.OBOFactory;
import obolayer.ComponentOBO;

import obomodel.OBOComponent;

import daolayer.ComponentDAO;
import daolayer.ComponentRelationshipDAO;
import daolayer.ComponentCommentDAO;
import daolayer.ComponentOrderDAO;
import daolayer.ComponentSynonymDAO;
import daolayer.ComponentAlternativeDAO;

import daolayer.DAOFactory;

import daomodel.Component;
import daomodel.ComponentRelationship;
import daomodel.ComponentComment;
import daomodel.ComponentOrder;
import daomodel.ComponentSynonym;
import daomodel.ComponentAlternative;

import utility.ObjectConverter;

public class LoadComponentsTablesFromOBOFile {
	/*
	 * run Method
	 */
    public static void run() throws Exception {

        // Obtain OBOFactory.
        OBOFactory obofactory = OBOFactory.getInstance("file");
        // Obtain DAOs.
        ComponentOBO componentOBO = obofactory.getComponentOBO();

        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = componentOBO.listAll();

        // Obtain DAOFactory.
        DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
        // Obtain DAOs.
        ComponentDAO componentDAO = anatomy008.getComponentDAO();
        ComponentRelationshipDAO componentrelationshipDAO = anatomy008.getComponentRelationshipDAO();
        ComponentCommentDAO componentcommentDAO = anatomy008.getComponentCommentDAO();
        ComponentOrderDAO componentorderDAO = anatomy008.getComponentOrderDAO();
        ComponentSynonymDAO componentsynonymDAO = anatomy008.getComponentSynonymDAO();
        ComponentAlternativeDAO componentalternativeDAO = anatomy008.getComponentAlternativeDAO();

        System.out.println("===============================================================");
        if ( componentDAO.countAll() > 0 ) {
            System.out.println("EMPTYING ANA_OBO_COMPONENT");
       		componentDAO.empty();
        }
        else {
            System.out.println("ANA_OBO_COMPONENT IS ALREADY EMPTY!");
        }
        if ( componentrelationshipDAO.countAll() > 0 ) {
            System.out.println("EMPTYING ANA_OBO_COMPONENT_RELATIONSHIP");
       		componentrelationshipDAO.empty();
        }
        else {
            System.out.println("==> ANA_OBO_COMPONENT_RELATIONSHIP IS ALREADY EMPTY!");
        }
        if ( componentcommentDAO.countAll() > 0 ) {
            System.out.println("EMPTYING ANA_OBO_COMPONENT_COMMENT");
       		componentcommentDAO.empty();
        }
        else {
            System.out.println("==> ANA_OBO_COMPONENT_COMMENT IS ALREADY EMPTY!");
        }
        if ( componentorderDAO.countAll() > 0 ) {
            System.out.println("EMPTYING ANA_OBO_COMPONENT_ORDER");
       		componentorderDAO.empty();
        }
        else {
            System.out.println("==> ANA_OBO_COMPONENT_ORDER IS ALREADY EMPTY!");
        }
        if ( componentsynonymDAO.countAll() > 0 ) {
            System.out.println("EMPTYING ANA_OBO_COMPONENT_SYNONYM");
            componentsynonymDAO.empty();
        }
        else {
            System.out.println("==> ANA_OBO_COMPONENT_SYNONYM IS ALREADY EMPTY!");
        }
        if ( componentalternativeDAO.countAll() > 0 ) {
            System.out.println("EMPTYING ANA_OBO_COMPONENT_ALTERNATIVE");
            componentalternativeDAO.empty();
        }
        else {
            System.out.println("==> ANA_OBO_COMPONENT_ALTERNATIVE IS ALREADY EMPTY!");
        }
        System.out.println("===============================================================");

      	Iterator<OBOComponent> iteratorComponent = obocomponents.iterator();
      	while (iteratorComponent.hasNext()) {
      		OBOComponent obocomponent = iteratorComponent.next();

       		if ( obocomponent.getStatusChange().equals("DELETED") ) {
                System.out.println("WARNING! obocomponent, = " + obocomponent.toString());
       		}
       		else {
           		Component daocomponent = new Component(null,
           				obocomponent.getName(),
           				obocomponent.getID(),
           				obocomponent.getDBID(),
           				obocomponent.getNewID(),
           				obocomponent.getNamespace(),
           				obocomponent.getDefinition(),
           				ObjectConverter.convert(obocomponent.getIsGroup(), Integer.class),
           				obocomponent.getStart(),
           				obocomponent.getEnd(),
           				obocomponent.getPresent(),
           				obocomponent.getStatusChange(),
           				obocomponent.getStatusRule());
           		
           		componentDAO.save(daocomponent);
           		
           		List<String> oboalternativeids = new ArrayList<String>();
           		oboalternativeids = obocomponent.getAlternativeIds();
       		
           		List<String> obopartofs = new ArrayList<String>();
           		obopartofs = obocomponent.getChildOfs();
           		
           		List<String> obopartoftypes = new ArrayList<String>();
           		obopartoftypes = obocomponent.getChildOfTypes();
           		
                for ( int i = 0; i < oboalternativeids.size(); i++ ) {

               		ComponentAlternative daocomponentalternative = new ComponentAlternative(null,
               				obocomponent.getID(),
               				oboalternativeids.get(i));
               		
               		componentalternativeDAO.save(daocomponentalternative);
           		}

                for ( int i = 0; i < obopartofs.size(); i++ ) {

               		ComponentRelationship daocomponentrelationship = new ComponentRelationship(null,
               				obocomponent.getID(),
               				ObjectConverter.convert(obocomponent.getStartSequence(), Long.class),
               				ObjectConverter.convert(obocomponent.getEndSequence(), Long.class),
               				obopartoftypes.get(i),
               				obopartofs.get(i)
               				);
               		
               		componentrelationshipDAO.save(daocomponentrelationship);
           		}

                String [] orderComments = obocomponent.getOrderComments();

                if ( orderComments != null && orderComments.length > 0) {

                	for ( int i = 0; i < orderComments.length; i++ ) {

                        String ordercomment = orderComments[i];
                        String [] words = ordercomment.split(" "); 
                        
                    	ComponentOrder daocomponentorder = new ComponentOrder(null,
                   				obocomponent.getID(),
                   	    		words[2],
                   	    		"PART_OF",
                   	    		ObjectConverter.convert(words[0], Long.class));
                   		
                   		componentorderDAO.save(daocomponentorder);
               		}
                }

                List<String> synonyms = obocomponent.getSynonyms();
                
                if (synonyms.size() > 1) {
                    for ( int i = 0; i < synonyms.size(); i++ ) {

                   		ComponentSynonym daocomponentsynonym = new ComponentSynonym(null,
                   				obocomponent.getID(),
                   				synonyms.get(i));
                   		
                   		componentsynonymDAO.save(daocomponentsynonym);
               		}
                }
       		}
      	}
      	
        List<ComponentRelationship> componentrelationships = new ArrayList<ComponentRelationship>();
        componentrelationships = componentrelationshipDAO.listAllAlphabeticWithinParentId();
        
        List<ComponentOrder> componentorders2 = new ArrayList<ComponentOrder>();
        componentorders2 = componentorderDAO.listAll();

        /*
        System.out.println("componentorders2.size() " + 
                ObjectConverter.convert(componentorders2.size(), String.class));
        System.out.println("componentrelationships.size() " + 
                ObjectConverter.convert(componentrelationships.size(), String.class));
        */

        Iterator<ComponentRelationship> iteratorComponentRelationship = componentrelationships.iterator();
      	
      	String oldParentId = "";
      	String newParentId = "";
      	int counter = -1;
      	//int countInsert = 0;
      	//int countUpdate = 0;
      	int typeInsert = 0;
      	int typeUpdate = 0;

      	while (iteratorComponentRelationship.hasNext()) {
      		ComponentRelationship componentrelationship = iteratorComponentRelationship.next();
      		
      		oldParentId = newParentId;
      		
      		newParentId = componentrelationship.getParent();
      		
      		if ( newParentId.equals(oldParentId)) {
          		
      			counter++;
      		}
      		else {
      			
      			counter = 0;
      		}
      		
            List<ComponentOrder> componentorders = new ArrayList<ComponentOrder>();
            componentorders = componentorderDAO.listByChildIdAndParentID(componentrelationship.getChild(), 
      						                                              componentrelationship.getParent());

      		if ( componentorders.size() == 0 ){

      			if ( "PART_OF".equals(componentrelationship.getType()) ) {
      				
      				typeInsert = counter;
      			}
      			else {
      				
      				typeInsert = 0;
      			}

      			ComponentOrder componentorderInsert = new ComponentOrder(null,
            			componentrelationship.getChild(),
            			componentrelationship.getParent(),
            			componentrelationship.getType(),
           	    		ObjectConverter.convert(typeInsert, Long.class));

      			componentorderDAO.save(componentorderInsert);
      			
      			//countInsert++;
      		}

      		if ( componentorders.size() > 0 ){

      			//countUpdate++;

          		Iterator<ComponentOrder> iteratorComponentOrder = componentorders.iterator();

              	while (iteratorComponentOrder.hasNext()) {
              		
              		ComponentOrder componentorderUpdate = iteratorComponentOrder.next();
              		
                    if (  componentorders2.size() == 0 ) {

                    	componentorderUpdate.setOid(null);
                    }
                    
          			if ( "PART_OF".equals(componentorderUpdate.getType()) ) {

          				typeUpdate = counter;
          			}
          			else {
          				
          				typeUpdate = 0;
          			}

              		componentorderUpdate.setOrder(ObjectConverter.convert(typeUpdate, Long.class));

          			componentorderDAO.save(componentorderUpdate);
              	}

      		}

      	}

        /*
        System.out.println("countInsert " + 
                ObjectConverter.convert(countInsert, String.class));
        System.out.println("countUpdate " + 
                ObjectConverter.convert(countUpdate, String.class));
        */

        System.out.println("===============================================================");
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT              = " + 
            ObjectConverter.convert(componentDAO.countAll(), String.class));
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_RELATIONSHIP = " + 
            ObjectConverter.convert(componentrelationshipDAO.countAll(), String.class));
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_COMMENTS     = " + 
            ObjectConverter.convert(componentcommentDAO.countAll(), String.class));
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_ORDER        = " + 
            ObjectConverter.convert(componentorderDAO.countAll(), String.class));
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_SYNONYM      = " + 
            ObjectConverter.convert(componentsynonymDAO.countAll(), String.class));
        System.out.println("The Number of Rows INSERTed into ANA_OBO_COMPONENT_ALTERNATIVE  = " + 
            ObjectConverter.convert(componentalternativeDAO.countAll(), String.class));
        System.out.println("===============================================================");
    }
}
