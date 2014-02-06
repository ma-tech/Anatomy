/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunOBOResetComponentsOrderAlpha.java
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
* Description:  A Main Class that resets the Ordering of Terms into Alphabetic Ordering
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

import utility.Wrapper;
import utility.ObjectConverter;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.ComponentDAO;
import daointerface.ComponentOrderDAO;
import daointerface.JOINComponentOrderComponentComponentDAO;

import daomodel.Component;
import daomodel.ComponentOrder;
import daomodel.JOINComponentOrderComponentComponent;

public class RunOBOResetComponentsOrderAlpha{

	public static void run( DAOFactory daofactory ) throws Exception {
    	
    	try {
    		
	        Wrapper.printMessage("RunOBOResetComponentsOrderAlpha.run", "***", daofactory.getMsgLevel());

            // Obtain DAOs.
            ComponentDAO componentDAO = daofactory.getDAOImpl(ComponentDAO.class);
	        JOINComponentOrderComponentComponentDAO joincomponentordercomponentcomponentDAO = daofactory.getDAOImpl(JOINComponentOrderComponentComponentDAO.class);
            ComponentOrderDAO componentorderDAO = daofactory.getDAOImpl(ComponentOrderDAO.class);

            ArrayList<Component> components = (ArrayList<Component>) componentDAO.listAll();
            Iterator<Component> iteratorComponents = components.iterator();
            
            int errorCount = 0;

          	while (iteratorComponents.hasNext()) {
          		
          		Component component = iteratorComponents.next();

        		List<JOINComponentOrderComponentComponent> joincomponentordercomponentcomponents = joincomponentordercomponentcomponentDAO.listOrderByParentByAlphaOrder(component.getId());
            	Iterator<JOINComponentOrderComponentComponent> iteratorJOINComponentOrderComponentComponent = joincomponentordercomponentcomponents.iterator();

        		long l = 0;
        		
            	while (iteratorJOINComponentOrderComponentComponent.hasNext()) {
            		
            		JOINComponentOrderComponentComponent joincomponentordercomponentcomponent = new JOINComponentOrderComponentComponent();
            		joincomponentordercomponentcomponent = iteratorJOINComponentOrderComponentComponent.next();
            		
            		/*
            		if ( "EMAPA:16177".equals(joincomponentordercomponentcomponent.getParent())) {
            			
            	        Wrapper.printMessage(joincomponentordercomponentcomponent.toString(), "*", "*");
            		}
            		*/
            		
            		if ( l != joincomponentordercomponentcomponent.getAlphaorder()) {
            			
            			ComponentOrder componentorder = new ComponentOrder(
            					joincomponentordercomponentcomponent.getOid(),
            					joincomponentordercomponentcomponent.getChild(),
            					joincomponentordercomponentcomponent.getParent(),
            					joincomponentordercomponentcomponent.getType(),
            					l,
            					joincomponentordercomponentcomponent.getSpecialorder());
            			
            	        errorCount++;
            	        
            	        Wrapper.printMessage(errorCount + " Alpha Ordering Error: Parent " + componentorder.getParent() + " Child " + componentorder.getChild() + "; Expected Alpha Order = " + ObjectConverter.convert(l, String.class)+ "; Actual Alpha Order = " + joincomponentordercomponentcomponent.getAlphaorder(), "*", "*");
            	        
            	        componentorderDAO.save(componentorder);
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
