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

import utility.Wrapper;
import utility.FileUtil;

import obolayer.OBOFactory;

import daointerface.DerivedPartOfDAO;
import daointerface.DerivedPartOfPerspectivesDAO;
import daointerface.DerivedPartOfFKDAO;
import daointerface.NodeDAO;
import daointerface.StageDAO;
import daointerface.SynonymDAO;
import daointerface.VersionDAO;

import daolayer.DAOFactory;

import daomodel.DerivedPartOf;
import daomodel.DerivedPartOfPerspectives;
import daomodel.DerivedPartOfFK;
import daomodel.Node;
import daomodel.Stage;
import daomodel.Synonym;
import daomodel.Version;


public class ReportStagedGroupsTrailing {
	
    private static final Set<String> VALID_PERSPECTIVES = new HashSet<String>(Arrays.asList(new String[] {
    		"Adult Kidney (GenePaint)", 
            "Renal", 
            "Urogenital", 
            "Whole mouse"}));


	public static void run( OBOFactory obofactory, DAOFactory daofactory, String perspective, String filePath ) throws Exception {

	    Wrapper.printMessage("ReportStagedGroupsTrailing.run", "***", daofactory.getMsgLevel());
	    
	    List<String> recordsListAll = new ArrayList<String>();

        File fileReportAllStagesGroupsTrailing = new File(filePath + "/ByStage/GroupsTrailing/AllStagesGroupsTrailing.txt");
        
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
		    StageDAO stageDAO = daofactory.getDAOImpl(StageDAO.class);
		    
		    Version version = versionDAO.findMostRecent();
		    
		    ArrayList<Stage> stages = (ArrayList<Stage>) stageDAO.listAllBySequence();
		    
		    Iterator<Stage> iteratorStage = stages.iterator();

	      	while (iteratorStage.hasNext()) {
	      		
	      		Stage stage = iteratorStage.next();

			    File fileReportStagedGroupsTrailing = new File( filePath + "/ByStage/GroupsTrailing/" + stage.getName() + "GroupsTrailing.txt");

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
			    record = "Stage " + stage.getName();
			    recordsList.add(record);
			    record = "";
			    recordsList.add(record);
			    record = "";
			    recordsList.add(record);
			    record = stage.getName() + " " + perspective + " Excluding Groups";
			    recordsList.add(record);
			    record = "";
			    recordsList.add(record);

			    ArrayList<DerivedPartOf> derivedpartofs = (ArrayList<DerivedPartOf>) derivedpartofDAO.listAllByStageSequence(stage.getSequence(), stage.getSequence());
			    
			    Iterator<DerivedPartOf> iteratorDerivedPartOf = derivedpartofs.iterator();

		      	while (iteratorDerivedPartOf.hasNext()) {
		      		
		      		DerivedPartOf derivedpartof = iteratorDerivedPartOf.next();
		      		
		      		DerivedPartOfPerspectives derivedpartofperspectives = derivedpartofperspectivesDAO.findByApoFKAndPerspective(derivedpartof.getOid(), perspective);
		      		
		      		if ( derivedpartofperspectives != null ) {
		      			
		      			Node node = nodeDAO.findByOid(derivedpartof.getNodeFK());
		      			
			      		String reportLine = node.getPublicId();
			      		String synonymLine = " (syn: ";
			      		
			      		if ( derivedpartof.isPrimary() && derivedpartof.isPrimaryPath()) {
			      			
			      			reportLine = reportLine + "   "; 
	
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

				      		      		
				      		if ( derivedpartof.getDepth() == 1) {
				      		
				      			reportLine = reportLine + node.getComponentName() + synonymLine;
				      		}
				      		else {
				      			
				      			for ( int i = 1; i < derivedpartof.getDepth(); i++ ) {
				      				
				      				reportLine = reportLine + "- ";
				      			}
				      			
				      			reportLine = reportLine + node.getComponentName() + synonymLine;
				      			
				      		}
				      		
				      		record = reportLine;
						    recordsList.add(record);
			      		}
		      		}
		      	}

			    record = "";
			    recordsList.add(record);
			    record = "";
			    recordsList.add(record);
			    record = "";
			    recordsList.add(record);
			    record = "============================================================";
			    recordsList.add(record);
			    record = "";
			    recordsList.add(record);
			    record = "";
			    recordsList.add(record);
			    record = "";
			    recordsList.add(record);
			    record = stage.getName() + " " + perspective + " Showing Only Groups  =====";
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
			    
			    iteratorDerivedPartOf = derivedpartofs.iterator();

		      	while (iteratorDerivedPartOf.hasNext()) {
		      		
		      		DerivedPartOf derivedpartof = iteratorDerivedPartOf.next();
		      		
		      		DerivedPartOfPerspectives derivedpartofperspectives = derivedpartofperspectivesDAO.findByApoFKAndPerspective(derivedpartof.getOid(), perspective);
		      		
		      		if ( derivedpartofperspectives != null ) {
		      			
		      			Node node = nodeDAO.findByOid(derivedpartof.getNodeFK());

		      			String reportLine = node.getPublicId();
			      		String synonymLine = " (syn: ";
			      		
			      		if ( !derivedpartof.isPrimary() && !derivedpartof.isPrimaryPath()) {
				      		
	  		      		    if ( !derivedpartof.isPrimary() ) {
	  			      			
			      			    reportLine = reportLine + " G "; 
			      		    }
			      		    else if ( !derivedpartof.isPrimaryPath() ) {
			      			
			      			    DerivedPartOf derivedpartofparent = derivedpartofDAO.findByOid(derivedpartof.getParentFK());
			      			
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

				      		      		
				      		if ( derivedpartof.getDepth() == 1) {
				      		
				      			reportLine = reportLine + node.getComponentName() + synonymLine;
				      		}
				      		else {
				      			
				      			for ( int i = 1; i < derivedpartof.getDepth(); i++ ) {
				      				
				      				reportLine = reportLine + "- ";
				      			}
				      			
				      			reportLine = reportLine + node.getComponentName() + synonymLine;
				      			
				      		}
				      		
				      		record = reportLine;
						    recordsList.add(record);
			      		}
		      		}
		      		
		      	}

			    FileUtil.write(fileReportStagedGroupsTrailing, recordsList);

			    recordsListAll.addAll(recordsList);
			    
			    recordsList.clear();
	      	}

	      	FileUtil.write(fileReportAllStagesGroupsTrailing, recordsListAll);
	    }
	    else { 
	    	
	    	Wrapper.printMessage("ERROR! " + perspective + " is not a valid Perspective!  Try again!", "*", "*");
	    }
	}
}
