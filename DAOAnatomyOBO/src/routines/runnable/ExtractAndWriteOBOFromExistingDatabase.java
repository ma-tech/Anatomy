/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        OBOExtractOBOFromExistingDatabase.java
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
* Description:  A Main Class that Reads an Anatomy Database and Writes out the data in OBO
*                Format
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
package routines.runnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import obolayer.OBOFactory;
import obolayer.ComponentOBO;

import obomodel.OBOComponent;
import obomodel.Relation;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;

import daolayer.DAOFactory;
import daolayer.StageDAO;

import daomodel.Stage;

import utility.Wrapper;

public class ExtractAndWriteOBOFromExistingDatabase {

	public static void run(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    Wrapper.printMessage("extractandwriteobofromexistingdatabase.run", "***", requestMsgLevel);

	    ComponentOBO componentOBO = obofactory.getComponentOBO();
        
        // Write out Abstract OBO file
        // Extract Components from RAW Database tables into OBOComponent format
        ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( requestMsgLevel, daofactory, obofactory, true );
        
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importdatabase.getTermList();
        
        List<Relation> relations = new ArrayList<Relation>();
        relations = importdatabase.getRelationList();
        
        // Write extracted OBOComponents into Obo File Format
        componentOBO.setComponentList((ArrayList<OBOComponent>) obocomponents);
        componentOBO.setRelationList((ArrayList<Relation>) relations);
        
        String newFileName = componentOBO.outputFile().substring(0, componentOBO.outputFile().indexOf(".")) + "_" + "Abstract" + ".obo";
        
        if ( componentOBO.writeAll( "Abstract" ) ) {
        	
        	Wrapper.printMessage("extractandwriteobofromexistingdatabase.run:Obo File SUCCESSFULLY written to " + newFileName + " for Species " + obofactory.getComponentOBO().species() + " and Project " + obofactory.getComponentOBO().project(), "***", requestMsgLevel);
        }
        else {
        	
        	Wrapper.printMessage("extractandwriteobofromexistingdatabase.run:Obo File FAILED written to " + newFileName + " for Species " + obofactory.getComponentOBO().species() + " and Project " + obofactory.getComponentOBO().project(), "***", requestMsgLevel);
        }
        
        // Write out Timed/Staged OBO file
        StageDAO stageDAO = daofactory.getStageDAO();
        
        List<Stage> stages = new ArrayList<Stage>();
        stages = stageDAO.listAllBySequence();
        
        Stage stage = new Stage();
        
        Iterator<Stage> iteratorStage = stages.iterator();
        
        while ( iteratorStage.hasNext() ) {
        	
           	stage = iteratorStage.next();
           	
            importdatabase = new ListOBOComponentsFromExistingDatabase( requestMsgLevel, daofactory, obofactory, true, stage.getName() );
            
            obocomponents = new ArrayList<OBOComponent>();
            obocomponents = importdatabase.getTermList();            
            relations = new ArrayList<Relation>();
            relations = importdatabase.getRelationList();
            
            // Write extracted OBOComponents into Obo File Format
            componentOBO.setComponentList((ArrayList<OBOComponent>) obocomponents);
            componentOBO.setRelationList((ArrayList<Relation>) relations);
            
            newFileName = componentOBO.outputFile().substring(0, componentOBO.outputFile().indexOf(".")) + "_" + stage.getName() + ".obo";

            if ( componentOBO.writeAll( stage.getName() ) ) {
            	
        	    Wrapper.printMessage("extractandwriteobofromexistingdatabase.run:Obo File SUCCESSFULLY written to " + newFileName + " for Species " + obofactory.getComponentOBO().species() + " and Project " + obofactory.getComponentOBO().project(), "***", requestMsgLevel);
            }
            else {

            	Wrapper.printMessage("extractandwriteobofromexistingdatabase.run:Obo File FAILED written to " + newFileName + " for Species " + obofactory.getComponentOBO().species() + " and Project " + obofactory.getComponentOBO().project(), "***", requestMsgLevel);
            }
        }
    }
}
