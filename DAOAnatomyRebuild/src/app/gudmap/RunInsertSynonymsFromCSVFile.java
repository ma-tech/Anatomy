/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        UpdateDatabaseFromComponentsTables.java
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
package app.gudmap;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import obomodel.OBOComponent;
import oboroutines.database.AnaSynonym;
import oboroutines.database.DatabaseException;

import daolayer.DAOFactory;

import utility.CsvUtil;
import utility.FileUtil;
import utility.Wrapper;



public class RunInsertSynonymsFromCSVFile {

	public static void run(String requestMsgLevel, DAOFactory daofactory) throws Exception {

        // Format InputStream for CSV.
        InputStream csvInput = FileUtil.readStream(new File("/Users/mwicks/GitMahost/Anatomy/Database/Tasks/ConvertFromMouse010/OBOInput/CsvSynonyms/JanesGUSynonyms_2013-05-29.csv"));
        
        // Create CSV List
        List<List<String>> csvList = CsvUtil.parseCsv(csvInput);

        // Create Output List of OBOComponents
    	ArrayList<OBOComponent> synonymComponents = new ArrayList<OBOComponent>();
    	 
        Iterator<List<String>> iteratorRow = csvList.iterator();
        
        String prevAnoOid = "";

        // Iterate through the CSV file ROWS
     	while (iteratorRow.hasNext()) {
    		
    		List<String> row = iteratorRow.next();

    		Iterator<String> iteratorColumn = row.iterator();
            
            int i = 1;
            /*
             * Columns:
             * ANO_OID
             * ANO_PUBLIC_ID
             * OLD_NAME
             * NEW_NAME
             * SYN_SYNONYM
             */
            String anoOid = "";
            String anoPublicId = "";
            String oldName = "";
            String newName = "";
            String synSynonym = "";
            
            // Iterate through the CSV file COLUMNS
         	while (iteratorColumn.hasNext()) {
        		
        		String column = iteratorColumn.next();
        		
        		if ( i == 1 ) {
        			anoOid = column;
        		}
        		if ( i == 2) {
        			anoPublicId = column;
        		}
        		if ( i == 3 ) {
        			oldName = column;
        		}
        		if ( i == 4) {
        			newName = column;
        		}
        		if ( i == 5) {
        			synSynonym = column;
        		}
        		
        		i++;
         	}

         	OBOComponent oboComponent = new OBOComponent();

         	if ( !anoOid.equals("ANO_OID")) {
         		
             	if ( !prevAnoOid.equals(anoOid)) {
             		
                 	oboComponent.setDBID(anoOid);
                 	oboComponent.setID(anoPublicId);
                 	oboComponent.setName(newName);
             		oboComponent.addSynonym(synSynonym);
                 	
                 	if ( !oldName.equals(newName)) {
                 		
                 		oboComponent.addSynonym(oldName);
                 	}

                 	synonymComponents.add(oboComponent);
             	}

             	prevAnoOid = anoOid;
         	}
     	}
   	 
        Iterator<OBOComponent> iteratorComponents = synonymComponents.iterator();

        while ( iteratorComponents.hasNext() ) {
        	
        	OBOComponent oboComponent = iteratorComponents.next();

            Wrapper.printMessage(oboComponent.toString() , "***", requestMsgLevel);
            
            // Create Output List of OBOComponents
        	ArrayList<String> componentSynonyms = new ArrayList<String>();
        	
        	componentSynonyms = oboComponent.getSynonyms();

            Iterator<String> iteratorComponentSynonyms = componentSynonyms.iterator();

            while ( iteratorComponentSynonyms.hasNext() ) {
            	
            	String synonym = iteratorComponentSynonyms.next();

                Wrapper.printMessage("SYNONYM: " + synonym , "***", requestMsgLevel);
            }
        }
        
        // INSERTS into ANA_SYNONYM
        AnaSynonym anasynonym = new AnaSynonym( requestMsgLevel, daofactory );
        
        if ( !anasynonym.insertANA_SYNONYM( synonymComponents, "INSERT" ) ) {

        	throw new DatabaseException("anasynonym.insertANA_SYNONYM");
        }

	}
}