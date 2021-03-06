/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
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
* Version:      1
*
* Description:  A Class that Reads an Anatomy Database and Writes out the data in OBO Format 
*                to File
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

import utility.Wrapper;

import obolayer.OBOFactory;

import oboaccess.OBOComponentAccess;

import obomodel.OBOComponent;
import obomodel.OBORelation;

import daolayer.DAOFactory;

import daointerface.StageDAO;

import daomodel.Stage;

import routines.aggregated.ListOBOComponentsFromExistingDatabase;

public class ExtractAndWriteOBOFromExistingDatabase {

	public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {

	    Wrapper.printMessage("extractandwriteobofromexistingdatabase.run", "***", daofactory.getMsgLevel());

	    OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();
        
        // Write out Abstract OBO file
        // Extract Components from RAW Database tables into OBOComponent format
        ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true, "" );
        
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
        obocomponents = importdatabase.getObocomponentAllOnomy();
        
        List<OBORelation> relations = new ArrayList<OBORelation>();
        relations = importdatabase.getOBORelationList();
        
        // Write extracted OBOComponents into Obo File Format
        obocomponentaccess.setComponentList((ArrayList<OBOComponent>) obocomponents);
        obocomponentaccess.setOBORelationList((ArrayList<OBORelation>) relations);
        
        if ( obocomponentaccess.writeAll( "Abstract" ) ) {
        	
        	Wrapper.printMessage("extractandwriteobofromexistingdatabase.run : Obo File SUCCESSFULLY written on " + "Abstract" + " for Species " + obofactory.getOBOComponentAccess().species() + " and Project " + obofactory.getOBOComponentAccess().project(), "***", daofactory.getMsgLevel());
        }
        else {
        	
        	Wrapper.printMessage("extractandwriteobofromexistingdatabase.run : Obo File FAILED written on " + "Abstract" + " for Species " + obofactory.getOBOComponentAccess().species() + " and Project " + obofactory.getOBOComponentAccess().project(), "*", daofactory.getMsgLevel());
        }
        
        // Write out Timed/Staged OBO file
        StageDAO stageDAO = daofactory.getDAOImpl(StageDAO.class);
        
        List<Stage> stages = new ArrayList<Stage>();
        stages = stageDAO.listAllBySequence();
        
        Stage stage = new Stage();
        
        Iterator<Stage> iteratorStage = stages.iterator();

        while ( iteratorStage.hasNext() ) {
        	
           	stage = iteratorStage.next();
           	
            importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true, stage.getName() );
            
            obocomponents = new ArrayList<OBOComponent>();
            obocomponents = importdatabase.getObocomponentAllOnomyStaged();    
            relations = new ArrayList<OBORelation>();
            relations = importdatabase.getOBORelationList();
            
            // Write extracted OBOComponents into Obo File Format
            obocomponentaccess.setComponentList((ArrayList<OBOComponent>) obocomponents);
            obocomponentaccess.setOBORelationList((ArrayList<OBORelation>) relations);
            
            if ( obocomponentaccess.writeAll( stage.getName() ) ) {
            	
        	    Wrapper.printMessage("extractandwriteobofromexistingdatabase.run : Obo File SUCCESSFULLY written on " + stage.getName() + " for Species " + obofactory.getOBOComponentAccess().species() + " and Project " + obofactory.getOBOComponentAccess().project(), "***", daofactory.getMsgLevel());
            }
            else {

            	Wrapper.printMessage("extractandwriteobofromexistingdatabase.run : Obo File FAILED written on " + stage.getName() + " for Species " + obofactory.getOBOComponentAccess().species() + " and Project " + obofactory.getOBOComponentAccess().project(), "*", daofactory.getMsgLevel());
            }
        }
    }
}
