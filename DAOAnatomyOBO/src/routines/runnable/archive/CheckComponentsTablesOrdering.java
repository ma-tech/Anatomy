/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        CheckComponentsTablesOrdering.java
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
package routines.runnable.archive;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import daolayer.DAOFactory;

import daointerface.ComponentOrderDAO;

import daomodel.ComponentOrder;

import obolayer.OBOFactory;

import utility.ObjectConverter;
import utility.Wrapper;

public class CheckComponentsTablesOrdering {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    Wrapper.printMessage("checkcomponentstablesordering.run", "***", requestMsgLevel);

	    // Obtain DAOs.
	    ComponentOrderDAO componentorderDAO = daofactory.getDAOImpl(ComponentOrderDAO.class);
        
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
        			
        		    Wrapper.printMessage("checkcomponentstablesordering.run : Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Special Order(" + ObjectConverter.convert(componentorder.getSpecialorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")", "***", requestMsgLevel);
        			errorCount++;
        		}
        	}
        	else {
        		
        		newCount = 0;
        		
        		if ( newCount != newParentCount ) {
        			
        		    Wrapper.printMessage("checkcomponentstablesordering.run : Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Special Order(" + ObjectConverter.convert(componentorder.getSpecialorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")", "***", requestMsgLevel);
        			errorCount++;
        		}
        	}

        	newCount++;
        	oldParentId = newParentId;
        }
	
	    Wrapper.printMessage("checkcomponentstablesordering.run : ----------------------------", "***", requestMsgLevel);
	    Wrapper.printMessage("checkcomponentstablesordering.run : Special Order Error Count    = " + ObjectConverter.convert(errorCount, String.class), "***", requestMsgLevel);
	    Wrapper.printMessage("checkcomponentstablesordering.run : ----------------------------", "***", requestMsgLevel);
		
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
        			
        		    Wrapper.printMessage("checkcomponentstablesordering.run : Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Alpha Order(" + ObjectConverter.convert(componentorder.getAlphaorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")", "***", requestMsgLevel);
        		    errorCount++;
        		}
        	}
        	else {
        		
        		newCount = 0;
        		
        		if ( newCount != newParentCount ) {
        			
        		    Wrapper.printMessage("checkcomponentstablesordering.run : Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Alpha Order(" + ObjectConverter.convert(componentorder.getAlphaorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")", "***", requestMsgLevel);
        		    errorCount++;
        		}
        	}

        	newCount++;
        	oldParentId = newParentId;
        }
	
	    Wrapper.printMessage("checkcomponentstablesordering.run : ----------------------------", "***", requestMsgLevel);
	    Wrapper.printMessage("checkcomponentstablesordering.run : Alphabetic Order Error Count = " + ObjectConverter.convert(errorCount, String.class), "***", requestMsgLevel);
	    Wrapper.printMessage("checkcomponentstablesordering.run : ----------------------------", "***", requestMsgLevel);
    }
}
