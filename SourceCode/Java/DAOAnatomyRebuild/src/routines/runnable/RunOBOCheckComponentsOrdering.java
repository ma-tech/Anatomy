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
* Version:      1
*
* Description:  A Class that Reads an OBO File and validates the ordering against itself.
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.runnable;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import utility.Wrapper;
import utility.ObjectConverter;

import daolayer.DAOFactory;

import daointerface.ComponentOrderDAO;

import daomodel.ComponentOrder;

public class RunOBOCheckComponentsOrdering {

	public static void run(DAOFactory daofactory) throws Exception {

	    Wrapper.printMessage("runobocheckcomponentsordering.run", "***", daofactory.getMsgLevel());

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
        			
        		    //Wrapper.printMessage("runobocheckcomponentsordering.run : Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Special Order(" + ObjectConverter.convert(componentorder.getSpecialorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")", "***", daofactory.getMsgLevel());
        			errorCount++;
        		}
        	}
        	else {
        		
        		newCount = 0;
        		
        		if ( newCount != newParentCount ) {
        			
        		    //Wrapper.printMessage("runobocheckcomponentsordering.run : Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Special Order(" + ObjectConverter.convert(componentorder.getSpecialorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")", "***", daofactory.getMsgLevel());
        			errorCount++;
        		}
        	}

        	newCount++;
        	oldParentId = newParentId;
        }
	
	    Wrapper.printMessage("runobocheckcomponentsordering.run : ----------------------------", "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("runobocheckcomponentsordering.run : Special Order Error Count    = " + ObjectConverter.convert(errorCount, String.class), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("runobocheckcomponentsordering.run : ----------------------------", "***", daofactory.getMsgLevel());
		
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
        			
        		    //Wrapper.printMessage("runobocheckcomponentsordering.run : Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Alpha Order(" + ObjectConverter.convert(componentorder.getAlphaorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")", "***", daofactory.getMsgLevel());
        			errorCount++;
        		}
        	}
        	else {
        		
        		newCount = 0;
        		
        		if ( newCount != newParentCount ) {
        			
        		    //Wrapper.printMessage("runobocheckcomponentsordering.run : Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + " Alpha Order(" + ObjectConverter.convert(componentorder.getAlphaorder(), String.class) + ") NOT EQUAL to expected (" + ObjectConverter.convert(newCount, String.class) + ")", "***", daofactory.getMsgLevel());
        			errorCount++;
        		}
        	}

        	newCount++;
        	oldParentId = newParentId;
        }
	
	    Wrapper.printMessage("runobocheckcomponentsordering.run : ----------------------------", "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("runobocheckcomponentsordering.run : Alphabetic Order Error Count = " + ObjectConverter.convert(errorCount, String.class), "***", daofactory.getMsgLevel());
	    Wrapper.printMessage("runobocheckcomponentsordering.run : ----------------------------", "***", daofactory.getMsgLevel());
    }
}
