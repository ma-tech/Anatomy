/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        LoadInputOBOFileIntoComponentsTablesAndValidate.java
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
* Description:  A Class that Reads an OBO File and Loads it into the Components tables in the 
*                Anatomy database;
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

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utility.Wrapper;

import oboaccess.OBOComponentAccess;

import obolayer.OBOFactory;

import obomodel.OBOComponent;

import daolayer.DAOFactory;

import daointerface.OBOFileDAO;

import daomodel.OBOFile;

public class PrintOutOBOFile {

	public static void run( DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("printoutobofile.run", "***", daofactory.getMsgLevel());

        // Obtain DAOs.
	    OBOFileDAO obofileDAO = daofactory.getDAOImpl(OBOFileDAO.class);
        
	    Wrapper.printMessage("printoutobofile.run : Clear Out OBOFile Table", "***", daofactory.getMsgLevel());
    
	    List<OBOFile> obofiles = new ArrayList<OBOFile>();
        obofiles = obofileDAO.listAll();
        
        Iterator<OBOFile> iteratorOBOFilePre = obofiles.iterator();

        if ( obofiles.size() > 0 ) {
        	
            while (iteratorOBOFilePre.hasNext()) {
            	
            	OBOFile obofileListPre = iteratorOBOFilePre.next();
            	
            	obofileDAO.delete(obofileListPre);
          	}
        }

	    // Obtain DAOs.
        OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();

        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = obocomponentaccess.listAllInput();

        Iterator<OBOComponent> iteratorObocomponents = obocomponents.iterator();

        PrintStream original = System.out;
        
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("/Users/mwicks/Desktop/output.txt"))));
        
        while (iteratorObocomponents.hasNext()) {
        	
        	OBOComponent obocomponent = iteratorObocomponents.next();
        	obocomponent.setChildOfFullString();

            System.out.println(obocomponent.toStringFull());
      	}

        System.setOut(original);
    }
}