/*
*----------------------------------------------------------------------------------------------
* Project:      Anatomy
*
* Title:        ImportDatabase.java
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

package Utility;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Calendar;


import DAOLayer.DAOException;
import DAOLayer.DAOFactory;
import DAOLayer.JOINNodeRelationshipRelationshipProjectDAO;
import DAOLayer.JOINTimedNodeStageDAO;
import DAOLayer.NodeDAO;
import DAOLayer.SynonymDAO;
import DAOLayer.StageDAO;
import DAOLayer.ProjectDAO;

import DAOModel.JOINNodeRelationshipRelationshipProject;
import DAOModel.JOINTimedNodeStage;
import DAOModel.Node;
import DAOModel.Synonym;
import DAOModel.Stage;
import DAOModel.Project;

import OBOModel.ComponentFile;
import OBOModel.Relation;



public class ImportDatabase {

    // Properties ---------------------------------------------------------------------------------

    // global variables
    private ArrayList <ComponentFile> obocomponentList = new ArrayList <ComponentFile>();
    private ArrayList <Relation> oborelationList = new ArrayList <Relation>();

    
    // Constructor --------------------------------------------------------------------------------
    public ImportDatabase(boolean defaultroot,
            String project ) {
    	
    	try {
            // Obtain DAOFactory.
            DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
            //System.out.println("DAOFactory successfully obtained: " + anatomy008);

            // Obtain DAOs.
            NodeDAO nodeDAO = anatomy008.getNodeDAO();
            JOINNodeRelationshipRelationshipProjectDAO nrrpjoinDAO = 
            		anatomy008.getJOINNodeRelationshipRelationshipProjectDAO(); 
            SynonymDAO synonymDAO = anatomy008.getSynonymDAO();
            JOINTimedNodeStageDAO timednodestagejoinDAO = anatomy008.getJOINTimedNodeStageDAO();
            StageDAO stageDAO = anatomy008.getStageDAO();

            // 1: abstract class---------------------------------------------------------------------------
            ComponentFile obocomponent;

            // 1_1: main abstract obocomponent-------------------------------------------------------------
            obocomponent = new ComponentFile();
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

                obocomponent = new ComponentFile();
                obocomponent.setName( node.getComponentName());
                obocomponent.setID( node.getPublicId() );
                obocomponent.setDBID( Long.toString(node.getOid()) );
                obocomponent.setIsPrimary( node.isPrimary() );

                // 1_2_2: query for the node's partOf relationship---------------------------------------------
                List<JOINNodeRelationshipRelationshipProject> nrrpJoins = 
                		new ArrayList<JOINNodeRelationshipRelationshipProject>();
                nrrpJoins = nrrpjoinDAO.listAllByChildAndProject(String.valueOf(obocomponent.getDBID()), project);
                Iterator<JOINNodeRelationshipRelationshipProject> iteratorNrrpJoin = 
                   		nrrpJoins.iterator();
                    
                while ( iteratorNrrpJoin.hasNext() ) {
                   	JOINNodeRelationshipRelationshipProject nrrpJoin = iteratorNrrpJoin.next();
                   	
                   	//System.out.println( nrrpJoin );
                    	
                    obocomponent.addChildOf( nrrpJoin.getPublicId());

                    if ( nrrpJoin.getTypeFK().equals("part-of") ) {
                        obocomponent.addChildOfType( "PART_OF" );
                    }
                    if ( nrrpJoin.getTypeFK().equals("is-a") ) {
                        obocomponent.addChildOfType( "IS_A" );
                    }
                    if ( nrrpJoin.getTypeFK().equals("derives-from") ) {
                    	obocomponent.addChildOfType( "DERIVES_FROM" );
                    }

                    //add comment for ordering nodes
                    /*
                    if ( nrrpJoin.getSequenceFK() != null ) {
                        obocomponent.addUserComments("order=" + nrrpJoin.getSequenceFK() + " for " + nrrpJoin.getPublicId() );
                    }
                    */
                }

                // 1_2_3: query for the node's synonyms--------------------------------------------------------
                List<Synonym> synonyms = new ArrayList<Synonym>();
                synonyms = synonymDAO.listByObjectFK( Long.valueOf(obocomponent.getDBID()) );
                Iterator<Synonym> iteratorSynonym = synonyms.iterator();
                    
              	while (iteratorSynonym.hasNext()) {
              		Synonym synonym = iteratorSynonym.next();
              		
              		obocomponent.addSynonym( synonym.getName() );
                }

                // 1_2_4: query for the node's start and end stage---------------------------------------------
                List<JOINTimedNodeStage> tnsJoins = new ArrayList<JOINTimedNodeStage>();
                tnsJoins = timednodestagejoinDAO.listAllByNodeFkOrderByStageSequence( obocomponent.getDBID() );
                Iterator<JOINTimedNodeStage> iteratorTnsJoin = tnsJoins.iterator();
                
                int rowCount = timednodestagejoinDAO.countAllByNodeFk( obocomponent.getDBID() );
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
                	//System.out.println( obocomponent );
                    obocomponent.addChildOf( "EMAPA:0"  );
                    obocomponent.addChildOfType( "IS_A" );
                }

                if ( defaultroot ) {
                    obocomponentList.add( obocomponent );
                }
                
          	}

            // 5: group obocomponents----------------------------------------------------------------------
            // 5:1:----------------------------------------------------------------------------------------
            obocomponent = new ComponentFile();
            obocomponent.setName( "Group term" );
            obocomponent.setID( "group_term" );
            obocomponent.setNamespace( "group_term" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // 2: stage class------------------------------------------------------------------------------
            // 2_1: main stage obocomponent----------------------------------------------------------------
            obocomponent = new ComponentFile();
            obocomponent.setName( "Theiler stage" );
            obocomponent.setID( "TS:0" );
            obocomponent.setNamespace( "theiler_stage" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // 2_2: rest from db---------------------------------------------------------------------------
            List<Stage> stages = new ArrayList<Stage>();
            stages = stageDAO.listAll();
            Iterator<Stage> iteratorStage = stages.iterator();
                
          	while (iteratorStage.hasNext()) {
          		
                // 2_2_1: query for the stages
                // 2_2_1: query for the stages-----------------------------------------------------------------
          		Stage stage = iteratorStage.next();

          		obocomponent = new ComponentFile();
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
            obocomponent = new ComponentFile();
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

            /* IS_A ????
            rel = new Relation();
            rel.setName( "is a" );
            rel.setID( "is_a" );
            rel.setTransitive( "true" );
            relationList.add( rel );
            */

        }
    	catch (DAOException ex) {
            ex.printStackTrace();
        }

    }

    /*
    public void makeRelationList(String fileType){


    }
    */

    
    public ArrayList<ComponentFile> getTermList() {

        return obocomponentList;

    }

    
    public ArrayList<Relation> getRelationList() {

        return oborelationList;

    }

    
    public ArrayList<String> getProjects() {

        ArrayList<String> projectStrings = new ArrayList<String>();

        try {
            // Obtain DAOFactory.
            DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
            //System.out.println("DAOFactory successfully obtained: " + anatomy008);

            // Obtain DAOs.
            ProjectDAO projectDAO = anatomy008.getProjectDAO();
            
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

    public void saveOBOFile(String fileName) {

        try {

            //System.out.println("saveOBOFile #1");

            BufferedWriter outputFile =
                        new BufferedWriter(new FileWriter(fileName));

            /*
            // format-version
            outputFile.write("format-version: " + mouseFormatVersionTF.getText() +
                    "\n");
            */
            // format-version
            outputFile.write("format-version: " + "\n");

            // date
            //outputFile.write("date: "+DATE+"\n");
            Calendar cal = Calendar.getInstance();
            outputFile.write("date: " + cal.get(Calendar.DAY_OF_MONTH) + ":" +
                    cal.get(Calendar.MONTH) + ":" + cal.get(Calendar.YEAR) + " " +
                    cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) +
                    "\n");

            // saved by
            outputFile.write("saved-by: " + "\n");

            // default-namespace
            outputFile.write("default-namespace: " + "\n");

            // remark
            outputFile.write("remark: " + "\n");
            /*
            // saved by
            outputFile.write("saved-by: " + savedByTF.getText() + "\n");

            // default-namespace
            outputFile.write("default-namespace: " +
                            mouseDefaultNamespaceTF.getText() + "\n");

            // remark
            outputFile.write("remark: " + remarkTA.getText() + "\n");
            */

            // terms - ComponentFile
            for (int i=0; i<obocomponentList.size(); i++) {
                if ( !obocomponentList.get(i).getStatusChange().equals("DELETED") ){

                	outputFile.write("\n[Term]\n");
                    outputFile.write("id: " + obocomponentList.get(i).getID() + "\n");
                    outputFile.write("name: " + obocomponentList.get(i).getName() + "\n");
                    outputFile.write("namespace: " +
                    		obocomponentList.get(i).getNamespace() + "\n");

                    if ( !obocomponentList.get(i).getNamespace().equals("theiler_stage") ||
                        !obocomponentList.get(i).getNamespace().equals("new_group_namespace") &&
                        !obocomponentList.get(i).getNamespace().equals("group_term") &&
                        !obocomponentList.get(i).getName().equals("Abstract anatomy") ){

                        // part_of relationships
                        for (int j=0; j<obocomponentList.get(i).getChildOfs().size(); j++) {
                            if (obocomponentList.get(i).getChildOfTypes().get(j).equals("PART_OF")) {
                                outputFile.write("relationship: part_of " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                            if (obocomponentList.get(i).getChildOfTypes().get(j).equals("IS_A")) {
                                outputFile.write("relationship: is_a " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                            if (obocomponentList.get(i).getChildOfTypes().get(j).equals("GROUP_PART_OF")) {
                                outputFile.write("relationship: group_part_of " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        }

                        if ( !obocomponentList.get(i).getStart().equals("") ) {
                            outputFile.write("relationship: starts_at " +
                            		obocomponentList.get(i).getStart() +
                                    "\n");
                        }

                        if ( !obocomponentList.get(i).getEnd().equals("") ) {
                            outputFile.write("relationship: ends_at " +
                            		obocomponentList.get(i).getEnd() + "\n");
                        }

                        if ( obocomponentList.get(i).getPresent() != 0 ) {
                            outputFile.write("relationship: present_in " +
                                    Integer.toString(obocomponentList.get(i).getPresent()) +
                                    "\n");
                        }

                        for (int j=0; j<obocomponentList.get(i).getSynonyms().size(); j++) {
                            outputFile.write("related_synonym: \"" +
                            		obocomponentList.get(i).getSynonyms().get(j) +
                                    "\" []\n");
                        }

                        if (obocomponentList.get(i).getIsGroup()) {
                            outputFile.write("relationship: is_a group_term\n");
                        }

                    }

                    boolean firstComment = true;

                    for (Iterator<String> k =
                    		obocomponentList.get(i).getUserComments().iterator();
                            k.hasNext();){
                    	
                        if (firstComment){
                            outputFile.write("comment: ");
                            firstComment = false;
                        } 
                        else {
                            outputFile.write("\n");
                        }
                    
                        outputFile.write(k.next());

                    }
                outputFile.write("\n");
                }

            }// for i

            // relations
            for (int i=0; i<oborelationList.size(); i++) {

            	outputFile.write("\n[Typedef]\n");
                outputFile.write("id: " + oborelationList.get(i).getID() +
                        "\n");
                outputFile.write("name: " + oborelationList.get(i).getName() +
                        "\n");
                
                if ( !oborelationList.get(i).getTransitive().equals("") ) {
                    outputFile.write("is_transitive: " +
                    		oborelationList.get(i).getTransitive() +
                            "\n");
                }
                
            }
            outputFile.write("\\n");
            outputFile.close(); // close outputFile
        }
        catch(IOException io) {
            io.printStackTrace();
        }
    }

}
