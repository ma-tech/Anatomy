/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        OBOValidateComponents.java
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
* Description:  A Main Class that Validates OBO data previously loaded into ANA_COMPONENT... 
*                tables in the Anatomy database .
*
*               Required Files:
*                1. dao.properties file contains the database access attributes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package app.gudmap;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import obolayer.OBOFactory;

import utility.Wrapper;
import utility.ObjectConverter;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.ComponentDAO;
import daointerface.ComponentOrderDAO;

import daomodel.Component;
import daomodel.ComponentOrder;

public class RunOBOValidateComponentsOrder{

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
    	try {
    		
	        Wrapper.printMessage("RunOBOValidateComponentsOrder.run", "***", requestMsgLevel);

            // Obtain DAOs.
            ComponentDAO componentDAO = daofactory.getDAOImpl(ComponentDAO.class);
            ComponentOrderDAO componentorderDAO = daofactory.getDAOImpl(ComponentOrderDAO.class);

            ArrayList<Component> components = (ArrayList<Component>) componentDAO.listAllOrderByEMAPA(); 
            Iterator<Component> iteratorComponent = components.iterator();
            
            int errorCount = 0;

          	while (iteratorComponent.hasNext()) {
          		
          		Component component = iteratorComponent.next();

        		List<ComponentOrder> componentorders = componentorderDAO.listByParentAlphaOrder(component.getId());
            	Iterator<ComponentOrder> iteratorComponentOrder = componentorders.iterator();

        		long l = 0;
        		
            	while (iteratorComponentOrder.hasNext()) {
            		
            		ComponentOrder componentorder = new ComponentOrder();
            		componentorder = iteratorComponentOrder.next();
            		
            		if ( l != componentorder.getAlphaorder()) {
            			
            	        errorCount++;
            	        Wrapper.printMessage(errorCount + " Alpha Ordering Error: Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + "; Expected Order = " + ObjectConverter.convert(l, String.class)+ "; Actual Order = " + componentorder.getAlphaorder(), "*", "*");
            		}
            		
            		l++;
            	}
            	
        		l = 0;
        		
        		componentorders = componentorderDAO.listByParentSpecialOrder(component.getId().replace("\n", " ").trim());
            	iteratorComponentOrder = componentorders.iterator();

            	while (iteratorComponentOrder.hasNext()) {
            		
            		ComponentOrder componentorder = new ComponentOrder();
            		componentorder = iteratorComponentOrder.next();

            		if ( l != componentorder.getSpecialorder()) {
            			
            	        errorCount++;
            	        Wrapper.printMessage(errorCount + " Special Ordering Error: Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + "; Expected Order = " + ObjectConverter.convert(l, String.class)+ "; Actual Order = " + componentorder.getSpecialorder(), "*", "*");
            		}
            		
            		l++;
            	}
          	}
    	}
		catch (DAOException daoexception) {

			daoexception.printStackTrace();
		}
    }
}
