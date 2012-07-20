/*
*----------------------------------------------------------------------------------------------
* Project:      Anatomy
*
* Title:        ListOBOComponentsFromExistingDatabase.java
*
* Date:         2008
*
* Author:       MeiSze Lam and Attila Gyenesi
*
* Copyright:    2009 Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Description:  This Class extracts a list of components from the database, and builds a list 
*                in the OBO style.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; February 2012; Completely rewrite to use standardised DAO Layer
* 
*----------------------------------------------------------------------------------------------
*/

package routinesaggregated;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import daolayer.DAOException;
import daolayer.DAOFactory;
import daolayer.JOINNodeRelationshipRelationshipProjectDAO;
import daolayer.JOINTimedNodeStageDAO;
import daolayer.NodeDAO;
import daolayer.SynonymDAO;
import daolayer.StageDAO;
import daolayer.ProjectDAO;

import daomodel.JOINNodeRelationshipRelationshipProject;
import daomodel.JOINTimedNodeStage;
import daomodel.Node;
import daomodel.Synonym;
import daomodel.Stage;
import daomodel.Project;

import obolayer.OBOFactory;

import obomodel.OBOComponent;
import obomodel.Relation;

import utility.ObjectConverter;


public class ListOBOComponentsFromExistingDatabase {

    // Properties ---------------------------------------------------------------------------------
    // global variables
    private ArrayList <OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
    private ArrayList <Relation> oborelationList = new ArrayList <Relation>();
    
    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromExistingDatabase(DAOFactory daofactory, OBOFactory obofactory, boolean defaultroot ) throws Exception {
    	
    	try {
            // Obtain DAOs.
            NodeDAO nodeDAO = daofactory.getNodeDAO();
            JOINNodeRelationshipRelationshipProjectDAO nrrpjoinDAO = 
            		daofactory.getJOINNodeRelationshipRelationshipProjectDAO(); 
            SynonymDAO synonymDAO = daofactory.getSynonymDAO();
            JOINTimedNodeStageDAO timednodestagejoinDAO = daofactory.getJOINTimedNodeStageDAO();
            StageDAO stageDAO = daofactory.getStageDAO();

            // 1: abstract class---------------------------------------------------------------------------
            OBOComponent obocomponent;

            // 1_1: main abstract obocomponent-------------------------------------------------------------
            obocomponent = new OBOComponent();
            obocomponent.setName( "Abstract anatomy" );
            obocomponent.setID( "EMAPA:0" );
            obocomponent.setNamespace( "abstract_anatomy" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // DEFAULT!!! (There have been other variations here .... )
            // 1_1: main abstract obocomponent-------------------------------------------------------------
            List<Node> nodes = new ArrayList<Node>();
            nodes = nodeDAO.listAll();
            Iterator<Node> iteratorNode = nodes.iterator();
                
          	while (iteratorNode.hasNext()) {
          		Node node = iteratorNode.next();

                obocomponent = new OBOComponent();
                obocomponent.setName( node.getComponentName());
                obocomponent.setID( node.getPublicId() );
                obocomponent.setDBID( Long.toString(node.getOid()) );
                obocomponent.setIsPrimary( node.isPrimary() );

                // 1_2_2: query for the node's partOf relationship---------------------------------------------
                List<JOINNodeRelationshipRelationshipProject> nrrpJoins = 
                		nrrpjoinDAO.listAllByChildAndProject(Long.valueOf(obocomponent.getDBID()), obofactory.getComponentOBO().project());
                
                Iterator<JOINNodeRelationshipRelationshipProject> iteratorNrrpJoin = 
                   		nrrpJoins.iterator();
                    
                while ( iteratorNrrpJoin.hasNext() ) {
                   	JOINNodeRelationshipRelationshipProject nrrpJoin = iteratorNrrpJoin.next();
                   	
                   	obocomponent.addOrderComment("order=" + ObjectConverter.convert(nrrpJoin.getSequenceFK(), String.class) + " for " + nrrpJoin.getPublicId());
                   	
                    obocomponent.addChildOf( nrrpJoin.getPublicId());

                    if ( nrrpJoin.getTypeFK().equals("part-of") ) {
                        obocomponent.addChildOfType( "PART_OF" );
                    }
                    else if ( nrrpJoin.getTypeFK().equals("is-a") ) {
                        obocomponent.addChildOfType( "IS_A" );
                    }
                    else if ( nrrpJoin.getTypeFK().equals("derives-from") ) {
                    	obocomponent.addChildOfType( "DERIVES_FROM" );
                    }
                    else if ( nrrpJoin.getTypeFK().equals("develops_from") ) {
                    	obocomponent.addChildOfType( "DEVELOPS_FROM" );
                    }
                    else if ( nrrpJoin.getTypeFK().equals("located_in") ) {
                    	obocomponent.addChildOfType( "LOCATED_IN" );
                    }
                    else if ( nrrpJoin.getTypeFK().equals("develops_in") ) {
                    	obocomponent.addChildOfType( "DEVELOPS_IN" );
                    }
                    else if ( nrrpJoin.getTypeFK().equals("disjoint_from") ) {
                    	obocomponent.addChildOfType( "DISJOINT_FROM" );
                    }
                    /*
                    else if ( nrrpJoin.getTypeFK().equals("surrounds") ) {
                    	obocomponent.addChildOfType( "SURROUNDS" );
                    }
                    */
                    else if ( nrrpJoin.getTypeFK().equals("attached-to") ) {
                    	obocomponent.addChildOfType( "ATTACHED_TO" );
                    }
                    else if ( nrrpJoin.getTypeFK().equals("has-part") ) {
                    	obocomponent.addChildOfType( "HAS_PART" );
                    }
                    else {
                        System.out.println("UNKNOWN Relationship Type = " + nrrpJoin.getTypeFK());
                    }
                }

                // 1_2_3: query for the node's synonyms--------------------------------------------------------
                List<Synonym> synonyms = synonymDAO.listByObjectFK( Long.valueOf(obocomponent.getDBID()) );
                
                Iterator<Synonym> iteratorSynonym = synonyms.iterator();
                    
              	while (iteratorSynonym.hasNext()) {
              		Synonym synonym = iteratorSynonym.next();
              		
              		obocomponent.addSynonym( synonym.getName() );
                }

                // 1_2_4: query for the node's start and end stage---------------------------------------------
                List<JOINTimedNodeStage> tnsJoins = 
                		timednodestagejoinDAO.listAllByNodeFkOrderByStageSequence( Long.valueOf(obocomponent.getDBID()) );
                
                Iterator<JOINTimedNodeStage> iteratorTnsJoin = tnsJoins.iterator();
                
                int rowCount = timednodestagejoinDAO.countAllByNodeFk( Long.valueOf(obocomponent.getDBID()) );
          		int i = 0;

          		obocomponent.setStart( "TS01" );
          		obocomponent.setEnd( "TS28" );
          		
              	while (iteratorTnsJoin.hasNext()) {
              		JOINTimedNodeStage tnsJoin = iteratorTnsJoin.next();

              		i++;
              		if ( i == 1) {
              			obocomponent.setStart( tnsJoin.getName() );
              		}
              		if ( i == rowCount) {
              			obocomponent.setEnd( tnsJoin.getName() );
              		}
                }

                // 1_2_5: add obocomponent to the obocomponent list--------------------------------------------
                /*
                 * maze: obocomponent name that is equal to 'mouse' or 'human' is set to IsA
                 */
                obocomponent.setNamespace( "abstract_anatomy" );
                
                if ( obocomponent.getName().equals( "mouse" ) ) {
                    obocomponent.addChildOf( "EMAPA:0"  );
                    obocomponent.addChildOfType( "IS_A" );
                }
                if ( defaultroot ) {
                    obocomponentList.add( obocomponent );
                }
          	}

            // 5: group obocomponents----------------------------------------------------------------------
            // 5:1:----------------------------------------------------------------------------------------
            obocomponent = new OBOComponent();
            obocomponent.setName( "Group term" );
            obocomponent.setID( "group_term" );
            obocomponent.setNamespace( "group_term" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // 2: stage class------------------------------------------------------------------------------
            // 2_1: main stage obocomponent----------------------------------------------------------------
            obocomponent = new OBOComponent();
            obocomponent.setName( "Theiler stage" );
            obocomponent.setID( "TS:0" );
            obocomponent.setNamespace( "theiler_stage" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // 2_2: rest from db---------------------------------------------------------------------------
            List<Stage> stages = stageDAO.listAll();
            
            Iterator<Stage> iteratorStage = stages.iterator();
                
          	while (iteratorStage.hasNext()) {
          		
                // 2_2_1: query for the stages-----------------------------------------------------------------
          		Stage stage = iteratorStage.next();

          		obocomponent = new OBOComponent();
                obocomponent.setName( stage.getName() );
                obocomponent.setID( stage.getName() );
                obocomponent.setDBID( String.valueOf(stage.getOid()) );

                // add obocomponent to the obocomponent list
                obocomponent.addChildOf( "TS:0" );
                obocomponent.addChildOfType( "IS_A" );

                obocomponent.setNamespace( "theiler_stage" );
                obocomponentList.add( obocomponent );
          	}

            // 3: new group class
            obocomponent = new OBOComponent();
            obocomponent.setName( "Tmp new group" );
            obocomponent.setID( "Tmp_new_group" );
            obocomponent.setNamespace( "new_group_namespace" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            //replace below with this
            Relation oborelation;

            // 3_1: starts at
            oborelation = new Relation();
            oborelation.setName( "starts at" );
            oborelation.setID( "starts_at" );
            oborelationList.add( oborelation );

            // 3_2: ends at
            oborelation = new Relation();
            oborelation.setName( "ends at" );
            oborelation.setID( "ends_at" );
            oborelationList.add( oborelation );

            // 3_3: partOf
            oborelation = new Relation();
            oborelation.setName( "part of" );
            oborelation.setID( "part_of" );
            oborelation.setTransitive( "true" );
            oborelationList.add( oborelation );

            // 3_4: groupPartOf
            oborelation = new Relation();
            oborelation.setName( "group part of" );
            oborelation.setID( "group_part_of" );
            oborelation.setTransitive( "true" );
            oborelationList.add( oborelation );

        }
    	catch (DAOException ex) {
            ex.printStackTrace();
        }

    }

    public ArrayList<OBOComponent> getTermList() {
        return obocomponentList;
    }
    
    public ArrayList<Relation> getRelationList() {
        return oborelationList;
    }
    
    public ArrayList<String> getProjects() {

        ArrayList<String> projectStrings = new ArrayList<String>();

        try {
            // Obtain DAOFactory.
            DAOFactory daofactory = DAOFactory.getInstance("daofactory");

            // Obtain DAOs.
            ProjectDAO projectDAO = daofactory.getProjectDAO();
            
            List<Project> projects = new ArrayList<Project>();
            projects = projectDAO.listAll();
            
            Iterator<Project> iteratorProject = projects.iterator();
            
          	while (iteratorProject.hasNext()) {
          		Project project = iteratorProject.next();
          		projectStrings.add(project.getName());
          	}
    	}
        catch(DAOException ex)
        {
            ex.printStackTrace();
        }
        return projectStrings;
    }

}
