/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        LoadOBOFileIntoComponentsTables.java
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
* Description:  This Class extracts a list of components from an OBO file, and loads the data 
*                into the Components tables of the Anatomy database.
*                
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.aggregated;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import utility.Wrapper;
import utility.ObjectConverter;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import daointerface.ComponentAlternativeDAO;
import daointerface.ComponentCommentDAO;
import daointerface.ComponentDAO;
import daointerface.ComponentOrderDAO;
import daointerface.ComponentRelationshipDAO;
import daointerface.ComponentSynonymDAO;

import daolayer.DAOFactory;

import daomodel.Component;
import daomodel.ComponentAlternative;
import daomodel.ComponentOrder;
import daomodel.ComponentRelationship;
import daomodel.ComponentSynonym;

public class LoadOBOFileIntoComponentsTables {

	public static void run( DAOFactory daofactory, OBOFactory obofactory, List<OBOComponent> obocomponents) throws Exception {

	    Wrapper.printMessage("loadobofileintocomponentstables.run", "***", daofactory.getMsgLevel());

        // Obtain DAOs.
        ComponentDAO componentDAO = daofactory.getDAOImpl(ComponentDAO.class);
        ComponentRelationshipDAO componentrelationshipDAO = daofactory.getDAOImpl(ComponentRelationshipDAO.class);
        ComponentCommentDAO componentcommentDAO = daofactory.getDAOImpl(ComponentCommentDAO.class);
        ComponentOrderDAO componentorderDAO = daofactory.getDAOImpl(ComponentOrderDAO.class);
        ComponentSynonymDAO componentsynonymDAO = daofactory.getDAOImpl(ComponentSynonymDAO.class);
        ComponentAlternativeDAO componentalternativeDAO = daofactory.getDAOImpl(ComponentAlternativeDAO.class);

      	Iterator<OBOComponent> iteratorComponent = obocomponents.iterator();
      	
      	while (iteratorComponent.hasNext()) {
      		
      		OBOComponent obocomponent = iteratorComponent.next();

       		Component daocomponent = new Component(
       				null,
       				obocomponent.getName(),
       				obocomponent.getID(),
       				obocomponent.getDBID(),
       				obocomponent.getNewID(),
       				obocomponent.getNamespace(),
       				obocomponent.getDefinition(),
       				obocomponent.isGroup(),
       				obocomponent.getStart(),
       				obocomponent.getEnd(),
       				obocomponent.isPresent(),
       				obocomponent.getStatusChange(),
       				obocomponent.getStatusRule()
       				);
       		
       		componentDAO.save(daocomponent);
       		
       		List<String> oboalternativeids = new ArrayList<String>();
       		oboalternativeids = obocomponent.getAlternativeIds();
   		
       		List<String> obopartofs = new ArrayList<String>();
       		obopartofs = obocomponent.getChildOfs();
       		
       		List<String> obopartoftypes = new ArrayList<String>();
       		obopartoftypes = obocomponent.getChildOfTypes();
       		
            if ( oboalternativeids != null ){
            	
           		for ( int i = 0; i < oboalternativeids.size(); i++ ) {

               		ComponentAlternative daocomponentalternative = new ComponentAlternative(
               				null,
               				obocomponent.getID(),
               				oboalternativeids.get(i)
               				);
               		
               		componentalternativeDAO.save(daocomponentalternative);
           		}
            }

            if ( !obopartofs.isEmpty() ) {
            	
                for ( int i = 0; i < obopartofs.size(); i++ ) {

               		ComponentRelationship daocomponentrelationship = new ComponentRelationship(
               				null,
               				obocomponent.getID(),
               				obocomponent.getStartSequence(),
               				obocomponent.getEndSequence(),
               				obopartoftypes.get(i),
               				obopartofs.get(i)
               				);
               		
               		componentrelationshipDAO.save(daocomponentrelationship);
           		}
            }

            String [] orderComments = obocomponent.getOrderComments();

            if ( orderComments != null && orderComments.length > 0) {

            	for ( int i = 0; i < orderComments.length; i++ ) {

                    String ordercomment = orderComments[i];
                    String [] words = ordercomment.split(" "); 
                    
                    
                    /*
                    if ( words.length < 3 ) {
                    	
                    	System.out.println("obocomponent.getID() = " + obocomponent.getID());
                    	System.out.println("words = " + words);
                    	System.out.println("words[0] = " + words[0]);
                    }
                    */
                    
                    /*
                     *  ComponentOrder
                     *      Long oid
                     *      String child
                     *      String parent
                     *      String type
                     *      long alphaorder - EMAP Order
                     *      long specialorder - GUDMAP Order
                     */
                    
                	ComponentOrder daocomponentorder = new ComponentOrder(
                			null,
               				obocomponent.getID(),
               	    		words[2],
               	    		"PART_OF",
               	    		0,
               	    		0
               	    		);

                	if ( "EMAP".equals(obofactory.getOBOComponentAccess().project()) ) {
                		
                		daocomponentorder.setAlphaorder(ObjectConverter.convert(words[0], Long.class));
                	}

                	if ( "GUDMAP".equals(obofactory.getOBOComponentAccess().project()) ) {
                		
                		daocomponentorder.setSpecialorder(ObjectConverter.convert(words[0], Long.class));
                	}
               		
               		componentorderDAO.save(daocomponentorder);
           		}
            }

            List<String> synonyms = obocomponent.getSynonyms();

            if ( !synonyms.isEmpty() ) {
            	
                if (synonyms.size() > 0) {
                	
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
        componentrelationships = componentrelationshipDAO.listAllAlphabeticWithinParentIdPartOF();
        
        List<ComponentOrder> componentorders2 = new ArrayList<ComponentOrder>();
        componentorders2 = componentorderDAO.listAll();

        Iterator<ComponentRelationship> iteratorComponentRelationship = componentrelationships.iterator();
      	
      	String oldParentId = "";
      	String newParentId = "";
      	int counter = -1;
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

      			ComponentOrder componentorderInsert = new ComponentOrder(
      					null,
            			componentrelationship.getChild(),
            			componentrelationship.getParent(),
            			componentrelationship.getType(),
           	    		ObjectConverter.convert(typeInsert, Long.class),
           	    		ObjectConverter.convert(typeInsert, Long.class)
           	    		);

      			componentorderDAO.save(componentorderInsert);
      		}

      		if ( componentorders.size() > 0 ){

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

              		componentorderUpdate.setAlphaorder(ObjectConverter.convert(typeUpdate, Long.class));

              		componentorderDAO.save(componentorderUpdate);
              	}
      		}
      	}
      	
        List<ComponentRelationship> componentrelationships2 = new ArrayList<ComponentRelationship>();
        componentrelationships2 = componentrelationshipDAO.listAllAlphabeticWithinParentIdNotPartOf();

        Iterator<ComponentRelationship> iteratorComponentRelationship2 = componentrelationships2.iterator();
      	
      	while (iteratorComponentRelationship2.hasNext()) {
      		
      		ComponentRelationship componentrelationship2 = iteratorComponentRelationship2.next();
      	
  			ComponentOrder componentorderIsa = new ComponentOrder(
  					null,
  					componentrelationship2.getChild(),
  					componentrelationship2.getParent(),
  					componentrelationship2.getType(),
       	    		ObjectConverter.convert(0, Long.class),
       	    		ObjectConverter.convert(0, Long.class)
       	    		);

  			componentorderDAO.save(componentorderIsa);

      	}

	    Wrapper.printMessage("loadobofileintocomponentstables.run : ---------------------------------------------------------------", "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstables.run : The Number of Rows INSERTed into ANA_OBO_COMPONENT              = " + ObjectConverter.convert(componentDAO.countAll(), String.class), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstables.run : The Number of Rows INSERTed into ANA_OBO_COMPONENT_RELATIONSHIP = " + ObjectConverter.convert(componentrelationshipDAO.countAll(), String.class), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstables.run : The Number of Rows INSERTed into ANA_OBO_COMPONENT_COMMENTS     = " + ObjectConverter.convert(componentcommentDAO.countAll(), String.class), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstables.run : The Number of Rows INSERTed into ANA_OBO_COMPONENT_ORDER        = " + ObjectConverter.convert(componentorderDAO.countAll(), String.class), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstables.run : The Number of Rows INSERTed into ANA_OBO_COMPONENT_SYNONYM      = " + ObjectConverter.convert(componentsynonymDAO.countAll(), String.class), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstables.run : The Number of Rows INSERTed into ANA_OBO_COMPONENT_ALTERNATIVE  = " + ObjectConverter.convert(componentalternativeDAO.countAll(), String.class), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("loadobofileintocomponentstables.run : ---------------------------------------------------------------", "***", daofactory.getMsgLevel());
    }
}
