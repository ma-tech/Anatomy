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
* Version: 1
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

import daolayer.DAOFactory;
import daolayer.OBOFileDAO;

import daomodel.OBOFile;


public class RunListOBOFileContents {
	/*
	 * run Method
	 */
    public static void run() throws Exception {
    	
        System.out.println("Obtain DAO");
	    // Obtain DAOFactory.
	    DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
	    // Obtain DAOs.
	    OBOFileDAO obofileDAO = anatomy008.getOBOFileDAO();

        List<OBOFile> obofiles = new ArrayList<OBOFile>();
        obofiles = obofileDAO.listAll();
        
        Iterator<OBOFile> iteratorOBOFile = obofiles.iterator();

        int i = 0;

        if ( obofiles.size() > 0 ) {
            System.out.println("List ALL OBOFile Rows");
            System.out.println("");
            System.out.println("\t=============================================");
            System.out.println("\tA List of the Uploaded and Validated OBOFiles = " + Integer.toString(obofiles.size()));
            System.out.println("\t=============================================");

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
            
            System.out.println(String.format("\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s", 
            		oid, name, contenttype, contentlength, contentdate, validation, author, 
            		textreportname, textreporttype, textreportlength, textreportdate, 
            		pdfreportname, pdfreporttype, pdfreportlength, pdfreportdate));

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
            
            System.out.println(String.format("\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s", 
            		oid, name, contenttype, contentlength, contentdate, validation, author, 
            		textreportname, textreporttype, textreportlength, textreportdate, 
            		pdfreportname, pdfreporttype, pdfreportlength, pdfreportdate));

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
            
            System.out.println(String.format("\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s\t%10s", 
            		oid, name, contenttype, contentlength, contentdate, validation, author, 
            		textreportname, textreporttype, textreportlength, textreportdate, 
            		pdfreportname, pdfreporttype, pdfreportlength, pdfreportdate));

            while (iteratorOBOFile.hasNext()) {
            	OBOFile obofileListed = iteratorOBOFile.next();
           		i++;
           		System.out.println(Integer.toString(i) + obofileListed.toString());
          	}

        }


    }
}
