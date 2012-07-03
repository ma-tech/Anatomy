/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOCheckComponentsOrdering.java
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
* Description:  A Main Class that Reads an OBO File and validates it against itself.
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

package routinesaggregated;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import daolayer.DAOFactory;
import daolayer.ComponentOrderDAO;

import daomodel.ComponentOrder;

import obolayer.OBOFactory;

import utility.ObjectConverter;


public class RunOBOCheckComponentsOrdering {
	/*
	 * run Method
	 */
    public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    // Obtain DAOs.
	    ComponentOrderDAO componentorderDAO = daofactory.getComponentOrderDAO();
        
	    List<ComponentOrder> specialcomponentorders = new ArrayList<ComponentOrder>();
	    specialcomponentorders = componentorderDAO.listOrderByParentBySpecialOrder();
        
        Iterator<ComponentOrder> iteratorSpecialComponentOrder = specialcomponentorders.iterator();

      	String oldParentId = "";
      	String newParentId = "";
      	int newCount = 0;
      	int newParentCount = 0;
      	int errorCount = 0;

        while (iteratorSpecialComponentOrder.hasNext()) {
        	ComponentOrder componentorder = iteratorSpecialComponentOrder.next();

        	newParentId = componentorder.getParent();
    		newParentCount = ObjectConverter.convert(componentorder.getSpecialorder(), Integer.class);
    		
        	if ( newParentId.equals(oldParentId) ) {
        		
        		if ( newCount != newParentCount ) {
        			System.out.println("Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Special Order(" + ObjectConverter.convert(componentorder.getSpecialorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")");
        			errorCount++;
        		}
        	}
        	else {
        		
        		newCount = 0;
        		
        		if ( newCount != newParentCount ) {
        			System.out.println("Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Special Order(" + ObjectConverter.convert(componentorder.getSpecialorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")");
        			errorCount++;
        		}
        	}

        	newCount++;
        	oldParentId = newParentId;
        }
	
		System.out.println("----------------------------");
		System.out.println("Special Order Error Count    = " + ObjectConverter.convert(errorCount, String.class));
		System.out.println("----------------------------");
		
	    List<ComponentOrder> alphacomponentorders = new ArrayList<ComponentOrder>();
	    alphacomponentorders = componentorderDAO.listOrderByParentByAlphaOrder();
        
        Iterator<ComponentOrder> iteratorAlphaComponentOrder = alphacomponentorders.iterator();

      	oldParentId = "";
      	newParentId = "";
      	newCount = 0;
      	newParentCount = 0;
      	errorCount = 0;

        while (iteratorAlphaComponentOrder.hasNext()) {
        	ComponentOrder componentorder = iteratorAlphaComponentOrder.next();

        	newParentId = componentorder.getParent();
    		newParentCount = ObjectConverter.convert(componentorder.getAlphaorder(), Integer.class);
    		
        	if ( newParentId.equals(oldParentId) ) {
        		
        		if ( newCount != newParentCount ) {
        			System.out.println("Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Alpha Order(" + ObjectConverter.convert(componentorder.getAlphaorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")");
        			errorCount++;
        		}
        	}
        	else {
        		
        		newCount = 0;
        		
        		if ( newCount != newParentCount ) {
        			System.out.println("Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Alpha Order(" + ObjectConverter.convert(componentorder.getAlphaorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")");
        			errorCount++;
        		}
        	}

        	newCount++;
        	oldParentId = newParentId;
        }
	
		System.out.println("----------------------------");
		System.out.println("Alphabetic Order Error Count = " + ObjectConverter.convert(errorCount, String.class)) ;
		System.out.println("----------------------------");
    }
}
