/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunListOBOFileContents.java
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
* Description:  A Main Class that Reads an OBO File and Loads it into an existing 
*                Anatomy database;
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
package app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import obolayer.OBOFactory;

import daolayer.DAOFactory;

import daointerface.OBOFileDAO;

import daomodel.OBOFile;

import utility.Wrapper;

public class RunListOBOFileContents {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    // Obtain DAOs.
	    OBOFileDAO obofileDAO = daofactory.getDAOImpl(OBOFileDAO.class);

        List<OBOFile> obofiles = new ArrayList<OBOFile>();
        obofiles = obofileDAO.listAll();
        
        Iterator<OBOFile> iteratorOBOFile = obofiles.iterator();

        int i = 0;

        if ( obofiles.size() > 0 ) {
        	
    	    Wrapper.printMessage("List ALL OBOFile Rows", "***", requestMsgLevel);
    	    Wrapper.printMessage("", "***", requestMsgLevel);
    	    Wrapper.printMessage("\t=============================================", "***", requestMsgLevel);
    	    Wrapper.printMessage("\tA List of the Uploaded and Validated OBOFiles = " + obofiles.size(), "***", requestMsgLevel);
    	    Wrapper.printMessage("\t=============================================", "***", requestMsgLevel);

            String oid = "";
            String name = "";
            String contenttype = "";
            String contentlength = "";
            String contentdate = "";
            String validation = "";
            String author = "";
            String textreportname = "";
            String textreporttype = "";
            String textreportlength = "";
            String textreportdate = "";
            String pdfreportname = "";
            String pdfreporttype = "";
            String pdfreportlength = "";
            String pdfreportdate = "";
      
    	    Wrapper.printMessage(String.format("\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s", 
            		oid, name, contenttype, contentlength, contentdate, validation, author, 
            		textreportname, textreporttype, textreportlength, textreportdate, 
            		pdfreportname, pdfreporttype, pdfreportlength, pdfreportdate), "***", requestMsgLevel);

            oid = "oid";
            name = "name";
            contenttype = "contenttype";
            contentlength = "contentlength";
            contentdate = "contentdate";
            validation = "validation";
            author = "author";
            textreportname = "textreportname";
            textreporttype = "textreporttype";
            textreportlength = "textreportlength";
            textreportdate = "textreportdate";
            pdfreportname = "pdfreportname";
            pdfreporttype = "pdfreporttype";
            pdfreportlength = "pdfreportlength";
            pdfreportdate = "pdfreportdate";
            
    	    Wrapper.printMessage(String.format("\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s", 
            		oid, name, contenttype, contentlength, contentdate, validation, author, 
            		textreportname, textreporttype, textreportlength, textreportdate, 
            		pdfreportname, pdfreporttype, pdfreportlength, pdfreportdate), "***", requestMsgLevel);

            oid = "-";
            name = "-";
            contenttype = "-";
            contentlength = "-";
            contentdate = "-";
            validation = "-";
            author = "-";
            textreportname = "-";
            textreporttype = "-";
            textreportlength = "-";
            textreportdate = "-";
            pdfreportname = "-";
            pdfreporttype = "-";
            pdfreportlength = "-";
            pdfreportdate = "-";
            
    	    Wrapper.printMessage(String.format("\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s", 
            		oid, name, contenttype, contentlength, contentdate, validation, author, 
            		textreportname, textreporttype, textreportlength, textreportdate, 
            		pdfreportname, pdfreporttype, pdfreportlength, pdfreportdate), "***", requestMsgLevel);

            while (iteratorOBOFile.hasNext()) {
            	
            	OBOFile obofileListed = iteratorOBOFile.next();
           		i++;
           		
        	    Wrapper.printMessage(i + obofileListed.toString(), "***", requestMsgLevel);
          	}
        }
    }
}
