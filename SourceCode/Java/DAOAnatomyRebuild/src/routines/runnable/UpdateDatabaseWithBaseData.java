/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        UpdateDatabaseWithPerspectiveAmbits.java
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
* Description:  A Class that Reads a CSV File of Perspective Ambits with Foreign Keys added
*                and Loads it into an existing Anatomy database;
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

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utility.CsvUtil;
import utility.FileUtil;
import utility.Wrapper;

import obolayer.OBOFactory;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.Species;
import daomodel.Evidence;
import daomodel.SourceFormat;
import daomodel.StageModifier;
import daomodel.RelationshipType;
import daomodel.Project;
import daomodel.Perspective;
import daomodel.Thing;
import daomodel.Editor;
import daomodel.Source;
import daomodel.Stage;
import daomodel.Version;
import daomodel.Node;
import daomodel.TimedNode;
import daomodel.TimedNodeFK;

import daointerface.SpeciesDAO;
import daointerface.EvidenceDAO;
import daointerface.SourceFormatDAO;
import daointerface.StageModifierDAO;
import daointerface.RelationshipTypeDAO;
import daointerface.ProjectDAO;
import daointerface.PerspectiveDAO;
import daointerface.ThingDAO;
import daointerface.EditorDAO;
import daointerface.SourceDAO;
import daointerface.StageDAO;
import daointerface.VersionDAO;
import daointerface.NodeDAO;
import daointerface.TimedNodeDAO;

public class UpdateDatabaseWithBaseData {
	
	public static void run(DAOFactory daofactory, OBOFactory obofactory, String fileName ) throws Exception {
		
		Wrapper.printMessage("UpdateDatabaseWithBaseData.run", "***", obofactory.getMsgLevel());
	    
        try {
        	
    	    SpeciesDAO speciesDAO = daofactory.getDAOImpl(SpeciesDAO.class);
    	    EvidenceDAO evidenceDAO = daofactory.getDAOImpl(EvidenceDAO.class);
    	    SourceFormatDAO sourceformatDAO = daofactory.getDAOImpl(SourceFormatDAO.class);
    	    StageModifierDAO stagemodifierDAO = daofactory.getDAOImpl(StageModifierDAO.class);
    	    RelationshipTypeDAO relationshiptypeDAO = daofactory.getDAOImpl(RelationshipTypeDAO.class);
    	    ProjectDAO projectDAO = daofactory.getDAOImpl(ProjectDAO.class);
    	    PerspectiveDAO perspectiveDAO = daofactory.getDAOImpl(PerspectiveDAO.class);
    	    ThingDAO thingDAO = daofactory.getDAOImpl(ThingDAO.class);
    	    EditorDAO editorDAO = daofactory.getDAOImpl(EditorDAO.class);
    	    SourceDAO sourceDAO = daofactory.getDAOImpl(SourceDAO.class);
    	    StageDAO stageDAO = daofactory.getDAOImpl(StageDAO.class);
    	    VersionDAO versionDAO = daofactory.getDAOImpl(VersionDAO.class);
    	    NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
    	    TimedNodeDAO timednodeDAO = daofactory.getDAOImpl(TimedNodeDAO.class);

            // Format InputStream for CSV.
            InputStream csvInput = FileUtil.readStream(new File( fileName ));
            
            char semiColon = ';';
            
            // Create CSV List
            List<List<String>> csvList = CsvUtil.parseCsv(csvInput, semiColon);

            // Create Output Lists
            List<Species> listSpecies = new ArrayList<Species>();
            List<Evidence> listEvidence = new ArrayList<Evidence>();
            List<SourceFormat> listSourceFormat = new ArrayList<SourceFormat>();
            List<StageModifier> listStageModifier = new ArrayList<StageModifier>();
            List<RelationshipType> listRelationshipType = new ArrayList<RelationshipType>();
            List<Project> listProject = new ArrayList<Project>();
            List<Perspective> listPerspective = new ArrayList<Perspective>();
            List<Editor> listEditor = new ArrayList<Editor>();
            List<Source> listSource = new ArrayList<Source>();
            List<Stage> listStage = new ArrayList<Stage>();
            List<Version> listVersion = new ArrayList<Version>();
            List<Node> listNode = new ArrayList<Node>();
            List<TimedNodeFK> listTimedNodeFK = new ArrayList<TimedNodeFK>();

            //Process each Row into Lists for Each Table
            Iterator<List<String>> iteratorRow = csvList.iterator();

         	while (iteratorRow.hasNext()) {
        		
        		List<String> row = iteratorRow.next();

        		// If the Row is a Species, create a Species Object and add to Species List
        		if ( row.get(0).equals("REF_SPECIES") ) {

                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Species species = new Species();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			species.setName(column);
                		}
                		if ( i == 3 ) {
                			species.setLatinName(column);
                		}
                		if ( i == 4 ) {
                			species.setTimedPrefix(column);
                		}
                		if ( i == 5 ) {
                			species.setAbstractPrefix(column);
                		}
                		
                		i++;
                 	}
                 	
        			//System.out.println(species.toString());
                 	listSpecies.add(species);
        		}
        		// If the Row is an Evidence, create an Evidence Object and add to Evidence List
        		else if ( row.get(0).equals("ANA_EVIDENCE") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Evidence evidence = new Evidence();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			evidence.setName(column);
                		}
                		
                		i++;
                 	}
                 	
        			//System.out.println(evidence.toString());
                 	listEvidence.add(evidence);
        		}
        		// If the Row is a SourceFormat, create a SourceFormat Object and add to SourceFormat List
        		else if ( row.get(0).equals("ANA_SOURCE_FORMAT") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    SourceFormat sourceformat = new SourceFormat();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			sourceformat.setName(column);
                		}
                		
                		i++;
                 	}
                 	
        			//System.out.println(sourceformat.toString());
                 	listSourceFormat.add(sourceformat);
        		}
        		// If the Row is a StageModifier, create a StageModifier Object and add to StageModifier List
        		else if ( row.get(0).equals("ANA_STAGE_MODIFIER") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    StageModifier stagemodifier = new StageModifier();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			stagemodifier.setName(column);
                		}
                		
                		i++;
                 	}
                 	
        			//System.out.println(stagemodifier.toString());
                 	listStageModifier.add(stagemodifier);
        		}
        		// If the Row is a RelationshipType, create a RelationshipType Object and add to RelationshipType List
        		else if ( row.get(0).equals("ANA_RELATIONSHIP_TYPE") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    RelationshipType relationshiptype = new RelationshipType();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			relationshiptype.setName(column);
                		}
                		if ( i == 3 ) {
                			relationshiptype.setChild2Parent(column);
                		}
                		if ( i == 4 ) {
                			relationshiptype.setParent2Child(column);
                		}
                		
                		i++;
                 	}
                 	
        			//System.out.println(relationshiptype.toString());
                 	listRelationshipType.add(relationshiptype);
        		}
        		// If the Row is a Project, create a Project Object and add to Project List
        		else if ( row.get(0).equals("ANA_PROJECT") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Project project = new Project();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			project.setName(column);
                		}
                		
                		i++;
                 	}
                 	
        			//System.out.println(project.toString());
                 	listProject.add(project);
        		}
        		// If the Row is a Perspective, create a Perspective Object and add to Perspective List
        		else if ( row.get(0).equals("ANA_PERSPECTIVE") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Perspective perspective = new Perspective();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			perspective.setName(column);
                		}
                		if ( i == 3 ) {
                			perspective.setComments(column);
                		}
                		
                		i++;
                 	}
                 	
        			//System.out.println(perspective.toString());
                 	listPerspective.add(perspective);
        		}
        		// If the Row is an Editor, create an Editor Object and add to Editor List
        		else if ( row.get(0).equals("ANA_EDITOR") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Editor editor = new Editor();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			editor.setName(column);
                		}

                		i++;
                 	}
                 	
        			//System.out.println(editor.toString());
                 	listEditor.add(editor);
        		}
        		// If the Row is a Source, create a Source Object and add to Source List
        		else if ( row.get(0).equals("ANA_SOURCE") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Source source = new Source();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			source.setName(column);
                		}
                		if ( i == 3 ) {
                			source.setAuthors(column);
                		}
                		if ( i == 4 ) {
                			source.setFormatFK(column);
                		}
                		if ( i == 5 ) {
                			source.setYear(column);
                		}

                		i++;
                 	}
                 	
        			//System.out.println(source.toString());
                 	listSource.add(source);
        		}
        		// If the Row is a Stage, create a Stage Object and add to Stage List
        		else if ( row.get(0).equals("ANA_STAGE") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Stage stage = new Stage();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			stage.setSpeciesFK(column);
                		}
                		if ( i == 3 ) {
                			stage.setName(column);
                		}
                		if ( i == 4 ) {
                			stage.setSequence(column);
                		}
                		if ( i == 5 ) {
                			stage.setDescription(column);
                		}
                		if ( i == 6 ) {
                			stage.setExtraText(column);
                		}
                		if ( i == 7 ) {
                			stage.setPublicId(column);
                		}

                		i++;
                 	}
                 	
                 	listStage.add(stage);
        		}
        		// If the Row is a Node, create a Node Object and add to Node List
        		else if ( row.get(0).equals("ANA_NODE") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Node node = new Node();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();

                		if ( i == 2 ) {
                			node.setSpeciesFK(column);
                		}
                		if ( i == 3 ) {
                			node.setComponentName(column);
                		}
                		if ( i == 4 ) {
                			node.setPrimary(column);
                		}
                		if ( i == 5 ) {
                			node.setGroup(column);
                		}
                		if ( i == 6 ) {
                			node.setPublicId(column);
                		}
                		if ( i == 7 ) {
                			node.setDescription(column);
                		}
                		if ( i == 8 ) {
                			node.setDisplayId(column);
                		}

                		i++;
                 	}
                 	
                 	listNode.add(node);
        		}
        		// If the Row is a Version, create a Version Object and add to Version List
        		else if ( row.get(0).equals("ANA_VERSION") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    Version version = new Version();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			version.setNumber(column);
                		}
                		if ( i == 3 ) {
                			version.setDate(column);
                		}
                		if ( i == 4 ) {
                			version.setComments(column);
                		}

                		i++;
                 	}
                 	
                 	listVersion.add(version);
        		}
        		// If the Row is a Timed Node, create a TimedNode WITH Foreign Keys Object 
        		//  and add to TimedNode WITH Foreign Keys List
        		else if ( row.get(0).equals("ANA_TIMED_NODE") ) {
        			
                    Iterator<String> iteratorColumn = row.iterator();
                    
                    int i = 1;
                    
                    TimedNodeFK timednodefk = new TimedNodeFK();
            		
                 	while (iteratorColumn.hasNext()) {
                		
                		String column = iteratorColumn.next();
                		
                		if ( i == 2 ) {
                			timednodefk.setNodeNameFK(column);
                		}
                		if ( i == 3 ) {
                			timednodefk.setStageNameFK(column);
                		}
                		if ( i == 4 ) {
                			timednodefk.setStageModifierFK(column);
                		}
                		if ( i == 5 ) {
                			timednodefk.setPublicId(column);
                		}
                		if ( i == 6 ) {
                			timednodefk.setDisplayId(column);
                		}

                		i++;
                 	}
                 	
                 	//System.out.println(timednodefk.toString());
                 	listTimedNodeFK.add(timednodefk);
        		}
        		else {
        			
        		}
         	}

            Iterator<Species> iteratorSpecies = listSpecies.iterator();
            
         	while (iteratorSpecies.hasNext()) {

         		Species species = iteratorSpecies.next();
         	
                //System.out.println(species.toString());
         		speciesDAO.create(species);
         	}        		

            Iterator<Evidence> iteratorEvidence = listEvidence.iterator();
            
         	while (iteratorEvidence.hasNext()) {

         		Evidence evidence = iteratorEvidence.next();
         	
                //System.out.println(evidence.toString());
         		evidenceDAO.create(evidence);
         	}        		

            Iterator<SourceFormat> iteratorSourceFormat = listSourceFormat.iterator();
            
         	while (iteratorSourceFormat.hasNext()) {

         		SourceFormat sourceformat = iteratorSourceFormat.next();
         	
                //System.out.println(sourceformat.toString());
         		sourceformatDAO.create(sourceformat);
         	}        		

            Iterator<StageModifier> iteratorStageModifier = listStageModifier.iterator();
            
         	while (iteratorStageModifier.hasNext()) {

         		StageModifier stagemodifier = iteratorStageModifier.next();
         	
                //System.out.println(stagemodifier.toString());
         		stagemodifierDAO.create(stagemodifier);
         	}        		

            Iterator<RelationshipType> iteratorRelationshipType = listRelationshipType.iterator();
            
         	while (iteratorRelationshipType.hasNext()) {

         		RelationshipType relationshiptype = iteratorRelationshipType.next();
         	
                //System.out.println(relationshiptype.toString());
         		relationshiptypeDAO.create(relationshiptype);
         	}        		

            Iterator<Project> iteratorProject = listProject.iterator();
            
         	while (iteratorProject.hasNext()) {

         		Project project = iteratorProject.next();
         	
                //System.out.println(project.toString());
         		projectDAO.create(project);
         	}        		

            Iterator<Perspective> iteratorPerspective = listPerspective.iterator();
            
         	while (iteratorPerspective.hasNext()) {

         		Perspective perspective = iteratorPerspective.next();
         	
                //System.out.println(perspective.toString());
         		perspectiveDAO.create(perspective);
         	}        		

            Iterator<Editor> iteratorEditor = listEditor.iterator();
            
         	while (iteratorEditor.hasNext()) {

         		Editor editor = iteratorEditor.next();
         		
                String datetime = utility.MySQLDateTime.now();
                long sysadmin = 2;
                String calledFromTable = "ANA_EDITOR";

                long maxOid = thingDAO.maximumOid();
         		maxOid++;
         		
         		editor.setOid(maxOid);
         	
                //System.out.println(editor.toString());
                editorDAO.create(editor);

                Thing thing = new Thing(maxOid, datetime, sysadmin, editor.toStringThing(), calledFromTable);

                //System.out.println(thing.toString());
                thingDAO.create(thing);
         	}        		

            Iterator<Source> iteratorSource = listSource.iterator();
            
         	while (iteratorSource.hasNext()) {

         		Source source = iteratorSource.next();
         		
                String datetime = utility.MySQLDateTime.now();
                long sysadmin = 2;
                String calledFromTable = "ANA_SOURCE";

                long maxOid = thingDAO.maximumOid();
         		maxOid++;
         		
         		source.setOid(maxOid);
         	
                //System.out.println(source.toString());
                sourceDAO.create(source);

                Thing thing = new Thing(maxOid, datetime, sysadmin, source.toStringThing(), calledFromTable);

                //System.out.println(thing.toString());
                thingDAO.create(thing);
         	}        		

            Iterator<Stage> iteratorStage = listStage.iterator();
            
         	while (iteratorStage.hasNext()) {

         		Stage stage = iteratorStage.next();
         		
                String datetime = utility.MySQLDateTime.now();
                long sysadmin = 2;
                String calledFromTable = "ANA_STAGE";

                long maxOid = thingDAO.maximumOid();
         		maxOid++;
         		
         		stage.setOid(maxOid);
         	
                //System.out.println(stage.toString());
                stageDAO.create(stage);

                Thing thing = new Thing(maxOid, datetime, sysadmin, stage.toStringThing(), calledFromTable);

                //System.out.println(thing.toString());
                thingDAO.create(thing);
         	}        		

            Iterator<Version> iteratorVersion = listVersion.iterator();
            
         	while (iteratorVersion.hasNext()) {

         		Version version = iteratorVersion.next();
         		
                String datetime = utility.MySQLDateTime.now();
                long sysadmin = 2;
                String calledFromTable = "ANA_VERSION";

                long maxOid = thingDAO.maximumOid();
         		maxOid++;
         		
         		version.setOid(maxOid);
         	
                //System.out.println(version.toString());
                versionDAO.create(version);

                Thing thing = new Thing(maxOid, datetime, sysadmin, version.toStringThing(), calledFromTable);

                //System.out.println(thing.toString());
                thingDAO.create(thing);
         	}        		

            Iterator<Node> iteratorNode = listNode.iterator();
            
         	while (iteratorNode.hasNext()) {

         		Node node = iteratorNode.next();
         		
                String datetime = utility.MySQLDateTime.now();
                long sysadmin = 2;
                String calledFromTable = "ANA_NODE";

                long maxOid = thingDAO.maximumOid();
         		maxOid++;
         		
         		node.setOid(maxOid);
         	
                //System.out.println(node.toString());
                nodeDAO.create(node);

                Thing thing = new Thing(maxOid, datetime, sysadmin, node.toStringThing(), calledFromTable);
                
                //System.out.println(thing.toString());

                thingDAO.create(thing);
         	}        		

            Iterator<TimedNodeFK> iteratorTimedNodeFK = listTimedNodeFK.iterator();
            
         	while (iteratorTimedNodeFK.hasNext()) {

         		TimedNodeFK timednodefk = iteratorTimedNodeFK.next();
         		
                String datetime = utility.MySQLDateTime.now();
                long sysadmin = 2;
                String calledFromTable = "ANA_TIMED_NODE";

                long maxOid = thingDAO.maximumOid();
         		maxOid++;
         		
         		Node node = nodeDAO.findByPublicId(timednodefk.getNodeNameFK());
         		Stage stage = stageDAO.findByName(timednodefk.getStageNameFK());
         		
         		TimedNode timednode = new TimedNode();
        		
         		timednode.setOid(maxOid);
         		timednode.setNodeFK(node.getOid());
         		timednode.setStageFK(stage.getOid());
         		timednode.setStageModifierFK(timednodefk.getStageModifierFK());
         		timednode.setPublicId(timednodefk.getPublicId());
         		timednode.setDisplayId(timednodefk.getDisplayId());
         	
                //System.out.println(timednode.toString());
                timednodeDAO.create(timednode);

                Thing thing = new Thing(maxOid, datetime, sysadmin, timednode.toStringThing(), calledFromTable);

                //System.out.println(thing.toString());
                thingDAO.create(thing);
         	}
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}
}