/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        ListAnadPartOf.java
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
* Description:  A Main Class that rebuilds the ANAD_PART_OF Derived Data Table
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; April 2014; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.aggregated;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import utility.FileUtil;
import utility.Wrapper;
import obolayer.OBOFactory;
import daointerface.DerivedPartOfDAO;
import daointerface.DerivedPartOfPerspectivesDAO;
import daointerface.DerivedPartOfFKDAO;
import daointerface.NodeDAO;
import daointerface.SynonymDAO;
import daointerface.VersionDAO;
import daolayer.DAOFactory;
import daomodel.DerivedPartOf;
import daomodel.DerivedPartOfPerspectives;
import daomodel.DerivedPartOfFK;
import daomodel.Node;
import daomodel.Synonym;
import daomodel.Version;


public class ReportAbstractGroupsEmbedded {
	
    private static final Set<String> VALID_PERSPECTIVES = new HashSet<String>(Arrays.asList(new String[] {
    		"Adult Kidney (GenePaint)", 
            "Renal", 
            "Urogenital", 
            "Whole mouse"}));


	public static void run( OBOFactory obofactory, DAOFactory daofactory, String perspective, String filePath ) throws Exception {

	    Wrapper.printMessage("ReportAbstractGroupsEmbedded.run", "***", daofactory.getMsgLevel());
	    
	    File fileReportAbstractGroupsEmbedded = new File( filePath + "/Abstract/AbstractGroupsEmbedded.txt");
	    
	    List<String> recordsList = new ArrayList<String>();
	    
	    String record;
	    	    
	    if ( VALID_PERSPECTIVES.contains( perspective ) ) {
	    	
		    // Obtain DAOs.
		    DerivedPartOfPerspectivesDAO derivedpartofperspectivesDAO = daofactory.getDAOImpl(DerivedPartOfPerspectivesDAO.class);
		    DerivedPartOfFKDAO derivedpartoffkDAO = daofactory.getDAOImpl(DerivedPartOfFKDAO.class);
		    DerivedPartOfDAO derivedpartofDAO = daofactory.getDAOImpl(DerivedPartOfDAO.class);
		    NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
		    SynonymDAO synonymDAO = daofactory.getDAOImpl(SynonymDAO.class);
		    VersionDAO versionDAO = daofactory.getDAOImpl(VersionDAO.class);
		    
		    Version version = versionDAO.findMostRecent();
		    
		    
		    record = "";
		    recordsList.add(record);
		    record = "============================================================";
		    recordsList.add(record);
		    record = "===== Anatomy Database Version " + version.getNumber() + ", " + version.getDate() + "=====";
		    recordsList.add(record);
		    record = "============================================================";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);
		    record = "===== " + perspective + " Anatomy =====";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);
		    record = "Second column indicates the path's group status.";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);
		    record = "' ' : Term is not a group nor is it contained in a group.";
		    recordsList.add(record);
		    record = "'G' : Term is a group.";
		    recordsList.add(record);
		    record = "'>' : Term is directly contained in a group term.";
		    recordsList.add(record);
		    record = "'~' : Term is indirectly contained in a group term.";
		    recordsList.add(record);
		    record = "";
		    recordsList.add(record);

		    ArrayList<DerivedPartOfFK> derivedpartoffks = (ArrayList<DerivedPartOfFK>) derivedpartoffkDAO.display(0, 30000, "fullPath", true, "");
		    
		    Iterator<DerivedPartOfFK> iteratorDerivedPartOfFK = derivedpartoffks.iterator();

	      	while (iteratorDerivedPartOfFK.hasNext()) {
	      		
	      		DerivedPartOfFK derivedpartoffk = iteratorDerivedPartOfFK.next();
	      		
	      		DerivedPartOfPerspectives derivedpartofperspectives = derivedpartofperspectivesDAO.findByApoFKAndPerspective(derivedpartoffk.getOid(), perspective);
	      		
	      		if ( derivedpartofperspectives != null ) {
	      			
		      		String reportLine = derivedpartoffk.getNode();
		      		String synonymLine = " (syn: ";
		      		
		      		
		      		if ( !derivedpartoffk.isPrimary() ) {
		      			
		      			reportLine = reportLine + " G "; 
		      		}
		      		else if ( !derivedpartoffk.isPrimaryPath() ) {
		      			
		      			DerivedPartOf derivedpartofparent = derivedpartofDAO.findByOid(derivedpartoffk.getParentFK());
		      			
		      			if ( !derivedpartofparent.isPrimary() ) {
		      				
		          			reportLine = reportLine + " > "; 
		      			}
		      			else {
		      				
		          			reportLine = reportLine + " ~ "; 
		      			}
		      		}
		      		else {
		      			
		      			reportLine = reportLine + "   "; 
		      		}
		      		
		      		reportLine = reportLine  + derivedpartoffk.getNodeStart() + "-" + derivedpartoffk.getNodeStop() + " ";

		      		Node node = nodeDAO.findByPublicId(derivedpartoffk.getNode());
		      		
		      		ArrayList<Synonym> synonyms = (ArrayList<Synonym>) synonymDAO.listByObjectFK(node.getOid());
		    	    
		    	    Iterator<Synonym> iteratorSynonym = synonyms.iterator();
		    	    
		    	    int synCount = 0;

		          	while (iteratorSynonym.hasNext()) {

		          		Synonym synonym = iteratorSynonym.next();
		          		
		          		synonymLine = synonymLine + synonym.getName();

		          		synCount++;

		          		if ( synCount != synonyms.size() ) {
		          			
		              		synonymLine = synonymLine + "; ";
		          		}
		          	}

		      		if ( synonyms.size() == 0) {
		      			
		      			synonymLine = "";
		      		}
		      		else {
		      			
		          		synonymLine = synonymLine + ")";
		          	}

		      		      		
		      		if ( derivedpartoffk.getDepth() == 1) {
		      		
		      			reportLine = reportLine + node.getComponentName() + synonymLine;
		      		}
		      		else {
		      			
		      			for ( int i = 1; i < derivedpartoffk.getDepth(); i++ ) {
		      				
		      				reportLine = reportLine + "- ";
		      			}
		      			
		      			reportLine = reportLine + node.getComponentName() + synonymLine;
		      			
		      		}
		      		
		      		record = reportLine;
				    recordsList.add(record);
	      		}
	      	}
	    }
	    else { 
	    	
	    	Wrapper.printMessage("ERROR! " + perspective + " is not a valid Perspective!  Try again!", "*", "*");
	    }
	    
	    FileUtil.write(fileReportAbstractGroupsEmbedded, recordsList);
	}

}
