/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        UpdateDatabaseWithPerspectiveAmbits.java
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
* Description:  A Class that Reads a CSV File of Perspective Ambits with Foreign Keys added
*                and Loads it into an existing Anatomy database;
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

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utility.CsvUtil;
import utility.FileUtil;
import utility.Wrapper;

import obolayer.OBOFactory;

import daointerface.NodeDAO;
import daointerface.ThingDAO;
import daointerface.PerspectiveAmbitDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.PerspectiveAmbitFK;
import daomodel.PerspectiveAmbit;
import daomodel.Node;
import daomodel.Thing;

public class UpdateDatabaseWithPerspectiveAmbits {
	
	public static void run(DAOFactory daofactory, OBOFactory obofactory, String fileName ) throws Exception {
		
		Wrapper.printMessage("updatedatabasewithperspectiveambits.run", "***", obofactory.getMsgLevel());
	    
        try {
        	
    	    NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
    	    ThingDAO thingDAO = daofactory.getDAOImpl(ThingDAO.class);
    	    PerspectiveAmbitDAO perspectiveambitDAO = daofactory.getDAOImpl(PerspectiveAmbitDAO.class);

            // Format InputStream for CSV.
            InputStream csvInput = FileUtil.readStream(new File( fileName ));
            
            // Create CSV List
            List<List<String>> csvList = CsvUtil.parseCsv(csvInput);

            // Create Output Lists
            List<PerspectiveAmbitFK> listPerspectiveAmbitFK = new ArrayList<PerspectiveAmbitFK>();
            List<PerspectiveAmbit> listPerspectiveAmbit = new ArrayList<PerspectiveAmbit>();

            Iterator<List<String>> iteratorRow = csvList.iterator();

         	while (iteratorRow.hasNext()) {
        		
        		List<String> row = iteratorRow.next();

                Iterator<String> iteratorColumn = row.iterator();
                
                int i = 1;
                
                PerspectiveAmbitFK perspectiveambitfk = new PerspectiveAmbitFK();
        		
             	while (iteratorColumn.hasNext()) {
            		
            		String column = iteratorColumn.next();
            		
            		if ( i == 1 ) {
            			perspectiveambitfk.setPerspectiveFK(column);
            		}
            		if ( i == 2 ) {
            			perspectiveambitfk.setPublicId(column);
            		}
            		if ( i == 3 ) {
            			if ( column.equals("1") ){
            				perspectiveambitfk.setStart(true);
            			}
            			else {
                			perspectiveambitfk.setStart(false);
            			}
            		}
            		if ( i == 4 ) {
            			if ( column.equals("1") ){
            				perspectiveambitfk.setStop(true);
            			}
            			else {
                			perspectiveambitfk.setStop(false);
            			}
            		}
            		if ( i == 5 ) {
            			perspectiveambitfk.setComments(column);
            		}
            		
            		i++;
             	}

                listPerspectiveAmbitFK.add(perspectiveambitfk);
         	}

            Iterator<PerspectiveAmbitFK> iteratorPerspectiveAmbitFK = listPerspectiveAmbitFK.iterator();
            
         	while (iteratorPerspectiveAmbitFK.hasNext()) {

         		PerspectiveAmbitFK perspectiveambitfk = iteratorPerspectiveAmbitFK.next();
         		
         		Node node = nodeDAO.findByPublicId(perspectiveambitfk.getPublicId());
         		
                PerspectiveAmbit perspectiveambit = new PerspectiveAmbit();
        		
                perspectiveambit.setPerspectiveFK(perspectiveambitfk.getPerspectiveFK());
                perspectiveambit.setNodeFK(node.getOid());
                perspectiveambit.setStart(perspectiveambitfk.isStart());
                perspectiveambit.setStop(perspectiveambitfk.isStop());
                perspectiveambit.setComments(perspectiveambitfk.getComments());
         	
                listPerspectiveAmbit.add(perspectiveambit);
         	}
        		
            Iterator<PerspectiveAmbit> iteratorPerspectiveAmbit = listPerspectiveAmbit.iterator();
            
            int intOBJ_OID = thingDAO.maximumOid();
            
            String datetime = utility.MySQLDateTime.now();
            long sysadmin = 2;
            String calledFromTable = "ANA_PERSPECTIVE_AMBIT";

         	while (iteratorPerspectiveAmbit.hasNext()) {

         		PerspectiveAmbit perspectiveambit = iteratorPerspectiveAmbit.next();
         		
                intOBJ_OID++;

                perspectiveambit.setOid((long) intOBJ_OID);
                
                Thing thing = new Thing((long) intOBJ_OID, datetime, sysadmin, perspectiveambit.toStringThing(), calledFromTable);

                thingDAO.create(thing);
                
                perspectiveambitDAO.create(perspectiveambit);
         	}
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}
}