/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        Anatomy.java
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
* Description:  This Class represents a CSV File of Anatomy Data
*                The class is instantiated with a 2D Array of Strings, and a Separator Character
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 24th Feb2014; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package anatomy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daointerface.EditorDAO;
import daointerface.EvidenceDAO;
import daointerface.NodeDAO;
import daointerface.PerspectiveAmbitDAO;
import daointerface.PerspectiveAmbitFKDAO;
import daointerface.PerspectiveDAO;
import daointerface.ProjectDAO;
import daointerface.RelationshipDAO;
import daointerface.RelationshipFKDAO;
import daointerface.RelationshipTypeDAO;
import daointerface.SourceDAO;
import daointerface.SourceFormatDAO;
import daointerface.SpeciesDAO;
import daointerface.StageDAO;
import daointerface.StageModifierDAO;
import daointerface.SynonymDAO;
import daointerface.SynonymFKDAO;
import daointerface.ThingDAO;
import daointerface.TimedNodeDAO;
import daointerface.TimedNodeFKDAO;
import daointerface.VersionDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.Editor;
import daomodel.Evidence;
import daomodel.Node;
import daomodel.Perspective;
import daomodel.PerspectiveAmbit;
import daomodel.PerspectiveAmbitFK;
import daomodel.Project;
import daomodel.Relationship;
import daomodel.RelationshipFK;
import daomodel.RelationshipType;
import daomodel.Source;
import daomodel.SourceFormat;
import daomodel.Species;
import daomodel.Stage;
import daomodel.StageModifier;
import daomodel.Synonym;
import daomodel.SynonymFK;
import daomodel.Thing;
import daomodel.TimedNode;
import daomodel.TimedNodeFK;
import daomodel.Version;

public class Anatomy {
    // Properties ---------------------------------------------------------------------------------

    private DAOFactory daofactory;

    private List<List<String>> csv2DStringArray;

    private List<List<String>> listStringSpecies;
    private List<List<String>> listStringEvidence;
    private List<List<String>> listStringSourceFormat;
    private List<List<String>> listStringStageModifier;
    private List<List<String>> listStringRelationshipType;
    private List<List<String>> listStringProject;
    private List<List<String>> listStringPerspective;
    private List<List<String>> listStringEditor;
    private List<List<String>> listStringSource;
    private List<List<String>> listStringStage;
    private List<List<String>> listStringVersion;
    private List<List<String>> listStringNode;
    private List<List<String>> listStringSynonym;
    private List<List<String>> listStringRelationship;
    private List<List<String>> listStringTimedNode;
    private List<List<String>> listStringPerspectiveAmbit;

    private List<Species> listObjectSpecies;
    private List<Evidence> listObjectEvidence;
    private List<SourceFormat> listObjectSourceFormat;
    private List<StageModifier> listObjectStageModifier;
    private List<RelationshipType> listObjectRelationshipType;
    private List<Project> listObjectProject;
    private List<Perspective> listObjectPerspective;
    private List<Editor> listObjectEditor;
    private List<Source> listObjectSource;
    private List<Stage> listObjectStage;
    private List<Version> listObjectVersion;
    private List<Node> listObjectNode;
    private List<SynonymFK> listObjectSynonymFK;
    private List<RelationshipFK> listObjectRelationshipFK;
    private List<TimedNodeFK> listObjectTimedNodeFK;
    private List<PerspectiveAmbitFK> listObjectPerspectiveAmbitFK;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Anatomy() {
        
    }

    /*
     * constructor #1
     *  Contains required fields.
     */
    public Anatomy(
    		DAOFactory daofactory) {
    	
    	this.daofactory = daofactory;
		
    	this.csv2DStringArray = new ArrayList<List<String>>();
    	
    	this.listStringSpecies = new ArrayList<List<String>>();
    	this.listStringEvidence = new ArrayList<List<String>>();
    	this.listStringSourceFormat = new ArrayList<List<String>>();
    	this.listStringStageModifier = new ArrayList<List<String>>();
    	this.listStringRelationshipType = new ArrayList<List<String>>();
    	this.listStringProject = new ArrayList<List<String>>();
    	this.listStringPerspective = new ArrayList<List<String>>();
    	this.listStringEditor = new ArrayList<List<String>>();
    	this.listStringSource = new ArrayList<List<String>>();
    	this.listStringStage = new ArrayList<List<String>>();
    	this.listStringVersion = new ArrayList<List<String>>();
    	this.listStringNode = new ArrayList<List<String>>();
    	this.listStringSynonym = new ArrayList<List<String>>();
    	this.listStringRelationship = new ArrayList<List<String>>();
    	this.listStringTimedNode = new ArrayList<List<String>>();
    	this.listStringPerspectiveAmbit = new ArrayList<List<String>>();

    	this.listObjectSpecies = new ArrayList<Species>();
    	this.listObjectEvidence = new ArrayList<Evidence>();
    	this.listObjectSourceFormat = new ArrayList<SourceFormat>();
    	this.listObjectStageModifier = new ArrayList<StageModifier>();
    	this.listObjectRelationshipType = new ArrayList<RelationshipType>();
    	this.listObjectProject = new ArrayList<Project>();
    	this.listObjectPerspective = new ArrayList<Perspective>();
    	this.listObjectEditor = new ArrayList<Editor>();
    	this.listObjectSource = new ArrayList<Source>();
    	this.listObjectStage = new ArrayList<Stage>();
    	this.listObjectVersion = new ArrayList<Version>();
    	this.listObjectNode = new ArrayList<Node>();
    	this.listObjectSynonymFK = new ArrayList<SynonymFK>();
    	this.listObjectRelationshipFK = new ArrayList<RelationshipFK>();
    	this.listObjectTimedNodeFK = new ArrayList<TimedNodeFK>();
    	this.listObjectPerspectiveAmbitFK = new ArrayList<PerspectiveAmbitFK>();

    }

    /*
     * constructor #2
     *  Contains required fields.
     */
    public Anatomy(
    		DAOFactory daofactory,
    		List<List<String>> csv2DStringArray) {
    	
    	this(daofactory);
    	
    	this.csv2DStringArray = csv2DStringArray;
    	
    	this.listStringSpecies = new ArrayList<List<String>>();
    	this.listStringEvidence = new ArrayList<List<String>>();
    	this.listStringSourceFormat = new ArrayList<List<String>>();
    	this.listStringStageModifier = new ArrayList<List<String>>();
    	this.listStringRelationshipType = new ArrayList<List<String>>();
    	this.listStringProject = new ArrayList<List<String>>();
    	this.listStringPerspective = new ArrayList<List<String>>();
    	this.listStringEditor = new ArrayList<List<String>>();
    	this.listStringSource = new ArrayList<List<String>>();
    	this.listStringStage = new ArrayList<List<String>>();
    	this.listStringVersion = new ArrayList<List<String>>();
    	this.listStringNode = new ArrayList<List<String>>();
    	this.listStringSynonym = new ArrayList<List<String>>();
    	this.listStringRelationship = new ArrayList<List<String>>();
    	this.listStringTimedNode = new ArrayList<List<String>>();
    	this.listStringPerspectiveAmbit = new ArrayList<List<String>>();

    	this.listObjectSpecies = new ArrayList<Species>();
    	this.listObjectEvidence = new ArrayList<Evidence>();
    	this.listObjectSourceFormat = new ArrayList<SourceFormat>();
    	this.listObjectStageModifier = new ArrayList<StageModifier>();
    	this.listObjectRelationshipType = new ArrayList<RelationshipType>();
    	this.listObjectProject = new ArrayList<Project>();
    	this.listObjectPerspective = new ArrayList<Perspective>();
    	this.listObjectEditor = new ArrayList<Editor>();
    	this.listObjectSource = new ArrayList<Source>();
    	this.listObjectStage = new ArrayList<Stage>();
    	this.listObjectVersion = new ArrayList<Version>();
    	this.listObjectNode = new ArrayList<Node>();
    	this.listObjectSynonymFK = new ArrayList<SynonymFK>();
    	this.listObjectRelationshipFK = new ArrayList<RelationshipFK>();
    	this.listObjectTimedNodeFK = new ArrayList<TimedNodeFK>();
    	this.listObjectPerspectiveAmbitFK = new ArrayList<PerspectiveAmbitFK>();

    }

    /*
     * Constructor #3
     */
    public Anatomy(
    		DAOFactory daofactory,
    		List<List<String>> csv2DStringArray,
    		List<List<String>> listStringSpecies,
    		List<List<String>> listStringEvidence,
    		List<List<String>> listStringSourceFormat,
    		List<List<String>> listStringStageModifier,
    		List<List<String>> listStringRelationshipType,
    		List<List<String>> listStringProject,
    		List<List<String>> listStringPerspective,
    		List<List<String>> listStringEditor,
    		List<List<String>> listStringSource,
    		List<List<String>> listStringStage,
    		List<List<String>> listStringVersion,
    		List<List<String>> listStringNode,
    		List<List<String>> listStringSynonym,
    		List<List<String>> listStringRelationship,
    		List<List<String>> listStringTimedNode,
    		List<List<String>> listStringPerspectiveAmbit) {

    	this(daofactory, csv2DStringArray);
    	
    	this.listStringSpecies = new ArrayList<List<String>>();
    	this.listStringEvidence = new ArrayList<List<String>>();
    	this.listStringSourceFormat = new ArrayList<List<String>>();
    	this.listStringStageModifier = new ArrayList<List<String>>();
    	this.listStringRelationshipType = new ArrayList<List<String>>();
    	this.listStringProject = new ArrayList<List<String>>();
    	this.listStringPerspective = new ArrayList<List<String>>();
    	this.listStringEditor = new ArrayList<List<String>>();
    	this.listStringSource = new ArrayList<List<String>>();
    	this.listStringStage = new ArrayList<List<String>>();
    	this.listStringVersion = new ArrayList<List<String>>();
    	this.listStringNode = new ArrayList<List<String>>();
    	this.listStringSynonym = new ArrayList<List<String>>();
    	this.listStringRelationship = new ArrayList<List<String>>();
    	this.listStringTimedNode = new ArrayList<List<String>>();
    	this.listStringPerspectiveAmbit = new ArrayList<List<String>>();
    }

    /*
     * Constructor #4
     */
    public Anatomy(
    		DAOFactory daofactory,
    		List<List<String>> csv2DStringArray,
    		List<List<String>> listStringSpecies,
    		List<List<String>> listStringEvidence,
    		List<List<String>> listStringSourceFormat,
    		List<List<String>> listStringStageModifier,
    		List<List<String>> listStringRelationshipType,
    		List<List<String>> listStringProject,
    		List<List<String>> listStringPerspective,
    		List<List<String>> listStringEditor,
    		List<List<String>> listStringSource,
    		List<List<String>> listStringStage,
    		List<List<String>> listStringVersion,
    		List<List<String>> listStringNode,
    		List<List<String>> listStringSynonym,
    		List<List<String>> listStringRelationship,
    		List<List<String>> listStringTimedNode,
    		List<List<String>> listStringPerspectiveAmbit,
    		List<Species> listObjectSpecies,
    		List<Evidence> listObjectEvidence,
    		List<SourceFormat> listObjectSourceFormat,
    		List<StageModifier> listObjectStageModifier,
    		List<RelationshipType> listObjectRelationshipType,
    		List<Project> listObjectProject,
    		List<Perspective> listObjectPerspective,
    		List<Editor> listObjectEditor,
    		List<Source> listObjectSource,
    		List<Stage> listObjectStage,
    		List<Version> listObjectVersion,
    		List<Node> listObjectNode,
    		List<SynonymFK> listObjectSynonymFK,
    		List<RelationshipFK> listObjectRelationshipFK,
    		List<TimedNodeFK> listObjectTimedNodeFK,
    		List<PerspectiveAmbitFK> listObjectPerspectiveAmbitFK) {

    	this(daofactory,
    			csv2DStringArray,
    			listStringSpecies,
    			listStringEvidence,
    			listStringSourceFormat,
    			listStringStageModifier,
    			listStringRelationshipType,
    			listStringProject,
    			listStringPerspective,
    			listStringEditor,
    			listStringSource,
    			listStringStage,
    			listStringVersion,
    			listStringNode,
    			listStringSynonym,
    			listStringRelationship,
    			listStringTimedNode,
    			listStringPerspectiveAmbit);
    	
    	this.listObjectSpecies = new ArrayList<Species>();
    	this.listObjectEvidence = new ArrayList<Evidence>();
    	this.listObjectSourceFormat = new ArrayList<SourceFormat>();
    	this.listObjectStageModifier = new ArrayList<StageModifier>();
    	this.listObjectRelationshipType = new ArrayList<RelationshipType>();
    	this.listObjectProject = new ArrayList<Project>();
    	this.listObjectPerspective = new ArrayList<Perspective>();
    	this.listObjectEditor = new ArrayList<Editor>();
    	this.listObjectSource = new ArrayList<Source>();
    	this.listObjectStage = new ArrayList<Stage>();
    	this.listObjectVersion = new ArrayList<Version>();
    	this.listObjectNode = new ArrayList<Node>();
    	this.listObjectSynonymFK = new ArrayList<SynonymFK>();
    	this.listObjectRelationshipFK = new ArrayList<RelationshipFK>();
    	this.listObjectTimedNodeFK = new ArrayList<TimedNodeFK>();
    	this.listObjectPerspectiveAmbitFK = new ArrayList<PerspectiveAmbitFK>();
    }
    
    // Getters ------------------------------------------------------------------------------------
    public List<List<String>> getCsv2DStringArray() {
        return this.csv2DStringArray;
    }
    public List<List<String>> getListStringSpecies() {
    	return this.listStringSpecies;
    }
    public List<List<String>> getListStringEvidence() {
    	return this.listStringEvidence;
    }
    public List<List<String>> getListStringSourceFormat() {
    	return this.listStringSourceFormat;
    }
    public List<List<String>> getListStringStageModifier() {
    	return this.listStringStageModifier;
    }
    public List<List<String>> getListStringRelationshipType() {
    	return this.listStringRelationshipType;
    }
    public List<List<String>> getListStringProject() {
    	return this.listStringProject;
    }
    public List<List<String>> getListStringPerspective() {
    	return this.listStringPerspective;
    }
    public List<List<String>> getListStringEditor() {
    	return this.listStringEditor;
    }
    public List<List<String>> getListStringSource() {
    	return this.listStringSource;
    }
    public List<List<String>> getListStringStage() {
    	return this.listStringStage;
    }
    public List<List<String>> getListStringVersion() {
    	return this.listStringVersion;
    }
    public List<List<String>> getListStringNode() {
    	return this.listStringNode;
    }
    public List<List<String>> getListStringSynonym() {
    	return this.listStringSynonym;
    }
    public List<List<String>> getListStringRelationship() {
    	return this.listStringRelationship;
    }
    public List<List<String>> getListStringTimedNode() {
    	return this.listStringTimedNode;
    }
    public List<List<String>> getListStringPerspectiveAmbit() {
    	return this.listStringPerspectiveAmbit;
    }
    public List<Species> getListObjectSpecies() {
    	return this.listObjectSpecies;
    }
    public List<Evidence> getListObjectEvidence() {
    	return this.listObjectEvidence;
    }
    public List<SourceFormat> getListObjectSourceFormat() {
    	return this.listObjectSourceFormat;
    }
    public List<StageModifier> getListObjectStageModifier() {
    	return this.listObjectStageModifier;
    }
    public List<RelationshipType> getListObjectRelationshipType() {
    	return this.listObjectRelationshipType;
    }
    public List<Project> getListObjectProject() {
    	return this.listObjectProject;
    }
    public List<Perspective> getListObjectPerspective() {
    	return this.listObjectPerspective;
    }
    public List<Editor> getListObjectEditor() {
    	return this.listObjectEditor;
    }
    public List<Source> getListObjectSource() {
    	return this.listObjectSource;
    }
    public List<Stage> getListObjectStage() {
    	return this.listObjectStage;
    }
    public List<Version> getListObjectVersion() {
    	return this.listObjectVersion;
    }
    public List<Node> getListObjectNode() {
    	return this.listObjectNode;
    }
    public List<SynonymFK> getListObjectSynonymFK() {
    	return this.listObjectSynonymFK;
    }
    public List<RelationshipFK> getListObjectRelationshipFK() {
    	return this.listObjectRelationshipFK;
    }
    public List<TimedNodeFK> getListObjectTimedNodeFK() {
    	return this.listObjectTimedNodeFK;
    }
    public List<PerspectiveAmbitFK> getListObjectPerspectiveAmbitFK() {
    	return this.listObjectPerspectiveAmbitFK;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setCsv2DStringArray( List<List<String>> csv2DStringArray ) {
        this.csv2DStringArray = csv2DStringArray;
    }
    public void setListStringSpecies( List<List<String>> listStringSpecies ) {
    	this.listStringSpecies = listStringSpecies;
    }
    public void setListStringEvidence( List<List<String>> listStringEvidence ) {
    	this.listStringEvidence = listStringEvidence;
    }
    public void setListStringSourceFormat( List<List<String>> listStringSourceFormat ) {
    	this.listStringSourceFormat = listStringSourceFormat;
    }
    public void setListStringStageModifier( List<List<String>> listStringStageModifier ) {
    	this.listStringStageModifier = listStringStageModifier;
    }
    public void setListStringRelationshipType( List<List<String>> listStringRelationshipType ) {
    	this.listStringRelationshipType = listStringRelationshipType;
    }
    public void setListStringProject( List<List<String>> listStringProject ) {
    	this.listStringProject = listStringProject;
    }
    public void setListStringPerspective( List<List<String>> listStringPerspective ) {
    	this.listStringPerspective = listStringPerspective;
    }
    public void setListStringEditor( List<List<String>> listStringEditor ) {
    	this.listStringEditor = listStringEditor;
    }
    public void setListStringSource( List<List<String>> listStringSource ) {
    	this.listStringSource = listStringSource;
    }
    public void setListStringStage( List<List<String>> listStringStage ) {
    	this.listStringStage = listStringStage;
    }
    public void setListStringVersion( List<List<String>> listStringVersion ) {
    	this.listStringVersion = listStringVersion;
    }
    public void setListStringNode( List<List<String>> listStringNode ) {
    	this.listStringNode = listStringNode;
    }
    public void setListStringSynonym( List<List<String>> listStringSynonym ) {
    	this.listStringSynonym = listStringSynonym;
    }
    public void setListStringRelationship( List<List<String>> listStringRelationship ) {
    	this.listStringRelationship = listStringRelationship;
    }
    public void setListStringTimedNode( List<List<String>> listStringTimedNode ) {
    	this.listStringTimedNode = listStringTimedNode;
    }
    public void setListStringPerspectiveAmbit( List<List<String>> listStringPerspectiveAmbit ) {
    	this.listStringPerspectiveAmbit = listStringPerspectiveAmbit;
    }
    public void setListObjectSpecies( List<Species> listObjectSpecies ) {
    	this.listObjectSpecies = listObjectSpecies;
    }
    public void setListObjectEvidence( List<Evidence> listObjectEvidence ) {
    	this.listObjectEvidence = listObjectEvidence;
    }
    public void setListObjectSourceFormat( List<SourceFormat> listObjectSourceFormat ) {
    	this.listObjectSourceFormat = listObjectSourceFormat;
    }
    public void setListObjectStageModifier( List<StageModifier> listObjectStageModifier ) {
    	this.listObjectStageModifier = listObjectStageModifier;
    }
    public void setListObjectRelationshipType( List<RelationshipType> listObjectRelationshipType ) {
    	this.listObjectRelationshipType = listObjectRelationshipType;
    }
    public void setListObjectProject( List<Project> listObjectProject ) {
    	this.listObjectProject = listObjectProject;
    }
    public void setListObjectPerspective( List<Perspective> listObjectPerspective ) {
    	this.listObjectPerspective = listObjectPerspective;
    }
    public void setListObjectEditor( List<Editor> listObjectEditor ) {
    	this.listObjectEditor = listObjectEditor;
    }
    public void setListObjectSource( List<Source> listObjectSource ) {
    	this.listObjectSource = listObjectSource;
    }
    public void setListObjectStage( List<Stage> listObjectStage ) {
    	this.listObjectStage = listObjectStage;
    }
    public void setListObjectVersion( List<Version> listObjectVersion ) {
    	this.listObjectVersion = listObjectVersion;
    }
    public void setListObjectNode( List<Node> listObjectNode ) {
    	this.listObjectNode = listObjectNode;
    }
    public void setListObjectSynonymFK( List<SynonymFK> listObjectSynonymFK ) {
    	this.listObjectSynonymFK = listObjectSynonymFK;
    }
    public void setListObjectRelationshipFK( List<RelationshipFK> listObjectRelationshipFK ) {
    	this.listObjectRelationshipFK = listObjectRelationshipFK;
    }
    public void setListObjectTimedNodeFK( List<TimedNodeFK> listObjectTimedNodeFK ) {
    	this.listObjectTimedNodeFK = listObjectTimedNodeFK;
    }
    public void setListObjectPerspectiveAmbitFK( List<PerspectiveAmbitFK> listObjectPerspectiveAmbitFK ) {
    	this.listObjectPerspectiveAmbitFK = listObjectPerspectiveAmbitFK;
    }
    
    // Helpers ------------------------------------------------------------------------------------

	/*
     * Create the Mega 2D Array from the 16 Anatomy DB Tables
     */
	public void createCSV2DArrayFromDatabase() throws Exception {
		
    	try {
        	
    		addSpeciesToCSV2DArray();
    		addEvidenceToCSV2DArray();
    		addSourceFormatToCSV2DArray();
    		addStageModifierToCSV2DArray();
    		addRelationshipTypeToCSV2DArray();
    		addProjectToCSV2DArray();
    		addPerspectiveToCSV2DArray();
    		addEditorToCSV2DArray();
    		addSourceToCSV2DArray();
    		addStageToCSV2DArray();
    		addVersionToCSV2DArray();
    		addNodeToCSV2DArray();
    		addSynonymFKToCSV2DArray();
    		addRelationshipFKToCSV2DArray();
    		addTimedNodeFKToCSV2DArray();
    		addPerspectiveAmbitFKToCSV2DArray();
    	}
    	catch ( DAOException dao ) {

    		dao.printStackTrace();
    	} 
    	catch ( Exception ex ) {
    
    		ex.printStackTrace();
    	} 
	}

	/*
     * Create the Mega 2D Array from the 16 Anatomy DB Tables
     */
	public void createCSV2DArrayFromDatabaseBaseDataOnly() throws Exception {
		
    	try {
        	
    		addSpeciesToCSV2DArray();
    		addEvidenceToCSV2DArray();
    		addSourceFormatToCSV2DArray();
    		addStageModifierToCSV2DArray();
    		addRelationshipTypeToCSV2DArray();
    		addProjectToCSV2DArray();
    		addPerspectiveToCSV2DArray();
    		addEditorToCSV2DArray();
    		addSourceToCSV2DArray();
    		addStageToCSV2DArray();
    		addVersionToCSV2DArray();
    	}
    	catch ( DAOException dao ) {

    		dao.printStackTrace();
    	} 
    	catch ( Exception ex ) {
    
    		ex.printStackTrace();
    	} 
	}

	/*
     * update the  Anatomy DB Table from 16 Lists of Objects
     */
	public void updateDatabase() throws Exception {
		
    	try {

    		splitArrayIntoLists();

    		convertStringListsIntoObjectLists();
        	
    		updateDatabaseSpecies();
    		updateDatabaseEvidence();
    		updateDatabaseSourceFormat();
    		updateDatabaseStageModifier();
    		updateDatabaseRelationshipType();
    		updateDatabaseProject();
    		updateDatabasePerspective();
    		updateDatabaseEditor();
    		updateDatabaseSource();
    		updateDatabaseStage();
    		updateDatabaseVersion();
    		updateDatabaseNode();
    		updateDatabaseRelationship();
    		updateDatabaseSynonym();
    		updateDatabaseTimedNode();
    		updateDatabasePerspectiveAmbit();

    	}
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}


	/*
     * Sort the Mega 2D Array into 16 Sub 2D Arrays for Each Anatomy DB Table
     */
    public void splitArrayIntoLists() {
    	
        //Process each Row into Lists for Each Table
    	Iterator<List<String>> iteratorRow = this.csv2DStringArray.iterator();

    	List<String> listRow = new ArrayList<String>();
    	
     	while (iteratorRow.hasNext()) {

     		listRow = iteratorRow.next();
     	
     		if ( listRow.get(0).equals("REF_SPECIES")) {
     			this.listStringSpecies.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_EVIDENCE")) {
     			this.listStringEvidence.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_SOURCE_FORMAT")) {
     			this.listStringSourceFormat.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_STAGE_MODIFIER")) {
     			this.listStringStageModifier.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_RELATIONSHIP_TYPE")) {
     			this.listStringRelationshipType.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_PROJECT")) {
     			this.listStringProject.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_PERSPECTIVE")) {
     			this.listStringPerspective.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_EDITOR")) {
     			this.listStringEditor.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_SOURCE")) {
     			this.listStringSource.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_STAGE")) {
     			this.listStringStage.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_VERSION")) {
     			this.listStringVersion.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_NODE")) {
     			this.listStringNode.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_SYNONYM")) {
     			this.listStringSynonym.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_RELATIONSHIP")) {
     			this.listStringRelationship.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_TIMED_NODE")) {
     			this.listStringTimedNode.add(listRow);
     		}
     		if ( listRow.get(0).equals("ANA_PERSPECTIVE_AMBIT")) {
     			
     			this.listStringPerspectiveAmbit.add(listRow);
     		}
     	}
    }
    
    /*
     * Convert the 16 Sub List of Strings into 16 Lists of Objects for Each Anatomy DB Table
     */
    public void convertStringListsIntoObjectLists() {
    	
    	createObjectsFromSpeciesStrings();
    	createObjectsFromEvidenceStrings();
    	createObjectsFromSourceFormatStrings();
    	createObjectsFromStageModifierStrings();
    	createObjectsFromRelationshipTypeStrings();
    	createObjectsFromProjectStrings();
    	createObjectsFromPerspectiveStrings();
    	createObjectsFromEditorStrings();
    	createObjectsFromSourceStrings();
    	createObjectsFromStageStrings();    
    	createObjectsFromVersionStrings();
    	createObjectsFromNodeStrings();
    	createObjectsFromSynonymFKStrings();
    	createObjectsFromRelationshipFKStrings();
    	createObjectsFromTimedNodeFKStrings();
    	createObjectsFromPerspectiveAmbitFKStrings();
    }
    
    /*
     * Convert the 2D Array of Species Strings into a list of Species Objects
     */
    private void createObjectsFromSpeciesStrings() {
    	
     	Iterator<List<String>>iteratorRow = this.listStringSpecies.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

     		Species species = new Species();

     		int i = 1;

         	Iterator<String> iteratorColumn = row.iterator();
         	
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
 			this.listObjectSpecies.add(species);
     	}        		
    }

   
    /*
     * Convert the 2D Array of Evidence Strings into a list of Evidence Objects
     */
    private void createObjectsFromEvidenceStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringEvidence.iterator();

        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

     		Evidence evidence = new Evidence();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 2 ) {
     				evidence.setName(column);
     			}
            	
     			i++;
     		}

 			//System.out.println(evidence.toString());
            this.listObjectEvidence.add(evidence);
     	}        		
    }

 
    /*
     * Convert the 2D Array of SourceFormat Strings into a list of SourceFormat Objects
     */
    private void createObjectsFromSourceFormatStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringSourceFormat.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	SourceFormat sourceformat = new SourceFormat();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 2 ) {
     				sourceformat.setName(column);
     			}
            	
     			i++;
     		}

 			//System.out.println(sourceformat.toString());
            this.listObjectSourceFormat.add(sourceformat);
     	}        		
    }

  
    /*
     * Convert the 2D Array of StageModifier Strings into a list of StageModifier Objects
     */
    private void createObjectsFromStageModifierStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringStageModifier.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	StageModifier stagemodifier = new StageModifier();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 2 ) {
     				stagemodifier.setName(column);
     			}
            	
     			i++;
     		}

 			//System.out.println(stagemodifier.toString());
            this.listObjectStageModifier.add(stagemodifier);
     	}        		
    }

  
    /*
     * Convert the 2D Array of RelationshipType Strings into a list of RelationshipType Objects
     */
    private void createObjectsFromRelationshipTypeStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringRelationshipType.iterator();

        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	RelationshipType relationshiptype = new RelationshipType();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
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
            this.listObjectRelationshipType.add(relationshiptype);
     	}        		
    }

 
    /*
     * Convert the 2D Array of Project Strings into a list of Project Objects
     */
    private void createObjectsFromProjectStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringProject.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	Project project = new Project();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 2 ) {
     				project.setName(column);
     			}
            	
     			i++;
     		}

 		    //System.out.println(project.toString());
            this.listObjectProject.add(project);
     	}        		
    }


    /*
     * Convert the 2D Array of Perspective Strings into a list of Perspective Objects
     */
    private void createObjectsFromPerspectiveStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringPerspective.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	Perspective perspective = new Perspective();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
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
            this.listObjectPerspective.add(perspective);
     	}        		
    }

 
    /*
     * Convert the 2D Array of Editor Strings into a list of Editor Objects
     */
    private void createObjectsFromEditorStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringEditor.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	Editor editor = new Editor();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 2 ) {
     				editor.setName(column);
     			}
            	
     			i++;
     		}

 			//System.out.println(editor.toString());
            this.listObjectEditor.add(editor);
     	}        		
    }

 
    /*
     * Convert the 2D Array of Source Strings into a list of Source Objects
     */
    private void createObjectsFromSourceStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringSource.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	Source source = new Source();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
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
     				//System.out.println(column);
     				source.setYear(column);
     			}
            	
     			i++;
     		}

 			//System.out.println(source.toString());
            this.listObjectSource.add(source);
     	}        		
    }

  
    
    /*
     * Convert the 2D Array of Stage Strings into a list of Stage Objects
     */
    private void createObjectsFromStageStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringStage.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	Stage stage = new Stage();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
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

 			//System.out.println(stage.toString());
            this.listObjectStage.add(stage);
     	}        		
    }

  
    /*
     * Convert the 2D Array of Version Strings into a list of Version Objects
     */
    private void createObjectsFromVersionStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringVersion.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	Version version = new Version();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
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

 			//System.out.println(version.toString());
            this.listObjectVersion.add(version);
     	}        		
    }

  
    /*
     * Convert the 2D Array of Node Strings into a list of Node Objects
     */
    private void createObjectsFromNodeStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringNode.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	Node node = new Node();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
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

 			//System.out.println(node.toString());
            this.listObjectNode.add(node);
     	}        		
    }

  
    /*
     * Convert the 2D Array of SynonymFK Strings into a list of SynonymFK Objects
     */
    private void createObjectsFromSynonymFKStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringSynonym.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	SynonymFK synonymfk = new SynonymFK();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 2 ) {
     				synonymfk.setThingNameFK(column);
     			}
     			if ( i == 3 ) {
     				synonymfk.setName(column);
     			}
            	
     			i++;
     		}

 			//System.out.println(synonymfk.toString());
            this.listObjectSynonymFK.add(synonymfk);
     	}        		
    }

  
    /*
     * Convert the 2D Array of RelationshipFK Strings into a list of RelationshipFK Objects
     */
    private void createObjectsFromRelationshipFKStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringRelationship.iterator();
     	     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	RelationshipFK relationshipfk = new RelationshipFK();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 2 ) {
     				relationshipfk.setTypeFK(column);
     			}
     			if ( i == 3 ) {
     				relationshipfk.setChildNameFK(column);
     			}
     			if ( i == 4 ) {
     				relationshipfk.setParentNameFK(column);
     			}
            	
     			i++;
     		}

 			//System.out.println(relationshipfk.toString());
            this.listObjectRelationshipFK.add(relationshipfk);
     	}        		
    }

  
    /*
     * Convert the 2D Array of TimedNodeFK Strings into a list of TimedNodeFK Objects
     */
    private void createObjectsFromTimedNodeFKStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringTimedNode.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	TimedNodeFK timednodefk = new TimedNodeFK();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
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
            this.listObjectTimedNodeFK.add(timednodefk);
     	}        		
    }

 
    /*
     * Convert the 2D Array of PerspectiveAmbitFK Strings into a list of PerspectiveAmbitFK Objects
     */
    private void createObjectsFromPerspectiveAmbitFKStrings() {
    	
     	Iterator<List<String>> iteratorRow = this.listStringPerspectiveAmbit.iterator();
     	
        while (iteratorRow.hasNext()) {
        		
        	List<String> row = iteratorRow.next();

        	PerspectiveAmbitFK perspectiveambitfk = new PerspectiveAmbitFK();

     		int i = 1;
            
         	Iterator<String> iteratorColumn = row.iterator();
         	
     		while (iteratorColumn.hasNext()) {

     			String column = iteratorColumn.next();
            	
     			if ( i == 2 ) {
     				perspectiveambitfk.setPerspectiveFK(column);
     			}
     			if ( i == 3 ) {
     				perspectiveambitfk.setPublicId(column);
     			}
     			if ( i == 4 ) {
     				perspectiveambitfk.setStart(column);
     			}
     			if ( i == 5 ) {
     				perspectiveambitfk.setStop(column);
     			}
     			if ( i == 6 ) {
     				perspectiveambitfk.setComments(column);
     			}
            	
     			i++;
     		}

 			this.listObjectPerspectiveAmbitFK.add(perspectiveambitfk);
     	}        		
    }


    /*
     * update the Species Anatomy DB Table from the Species List
     */
	private void updateDatabaseSpecies() throws Exception {
		
    	try {
        	
    	    SpeciesDAO speciesDAO = this.daofactory.getDAOImpl(SpeciesDAO.class);
            Iterator<Species> iteratorSpecies = this.listObjectSpecies.iterator();
            
         	while (iteratorSpecies.hasNext()) {

         		Species species = iteratorSpecies.next();
                //System.out.println(species.toString());
         		speciesDAO.create(species);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}


	/*
     * update the Evidence Anatomy DB Table from the Evidence List
     */
	private void updateDatabaseEvidence() throws Exception {
		
    	try {
        	
    	    EvidenceDAO evidenceDAO = this.daofactory.getDAOImpl(EvidenceDAO.class);
            Iterator<Evidence> iteratorEvidence = this.listObjectEvidence.iterator();
            
         	while (iteratorEvidence.hasNext()) {

         		Evidence evidence = iteratorEvidence.next();
                //System.out.println(evidence.toString());
         		evidenceDAO.create(evidence);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}


	/*
     * update the SourceFormat Anatomy DB Table from the SourceFormat List
     */
	private void updateDatabaseSourceFormat() throws Exception {
		
    	try {
        	
    	    SourceFormatDAO sourceformatDAO = this.daofactory.getDAOImpl(SourceFormatDAO.class);
            Iterator<SourceFormat> iteratorSourceFormat = this.listObjectSourceFormat.iterator();
            
         	while (iteratorSourceFormat.hasNext()) {

         		SourceFormat sourceformat = iteratorSourceFormat.next();
                //System.out.println(sourceformat.toString());
         		sourceformatDAO.create(sourceformat);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}


	/*
     * update the StageModifier Anatomy DB Table from the StageModifier List
     */
	private void updateDatabaseStageModifier() throws Exception {
		
    	try {
        	
    	    StageModifierDAO stagemodifierDAO = this.daofactory.getDAOImpl(StageModifierDAO.class);
            Iterator<StageModifier> iteratorStageModifier = this.listObjectStageModifier.iterator();
            
         	while (iteratorStageModifier.hasNext()) {

         		StageModifier stagemodifier = iteratorStageModifier.next();
                //System.out.println(stagemodifier.toString());
         		stagemodifierDAO.create(stagemodifier);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}


	/*
     * update the RelationshipType Anatomy DB Table from the RelationshipType List
     */
	private void updateDatabaseRelationshipType() throws Exception {
		
    	try {
        	
    	    RelationshipTypeDAO relationshiptypeDAO = this.daofactory.getDAOImpl(RelationshipTypeDAO.class);
            Iterator<RelationshipType> iteratorRelationshipType = this.listObjectRelationshipType.iterator();
            
         	while (iteratorRelationshipType.hasNext()) {

         		RelationshipType relationshiptype = iteratorRelationshipType.next();
                //System.out.println(relationshiptype.toString());
         		relationshiptypeDAO.create(relationshiptype);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}


	/*
     * update the Project Anatomy DB Table from the Project List
     */
	private void updateDatabaseProject() throws Exception {
		
    	try {
        	
    	    ProjectDAO projectDAO = this.daofactory.getDAOImpl(ProjectDAO.class);
            Iterator<Project> iteratorProject = this.listObjectProject.iterator();
            
         	while (iteratorProject.hasNext()) {

         		Project project = iteratorProject.next();
                //System.out.println(project.toString());
         		projectDAO.create(project);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}


	/*
     * update the Perspective Anatomy DB Table from the Perspective List
     */
	private void updateDatabasePerspective() throws Exception {
		
    	try {
        	
    	    PerspectiveDAO perspectiveDAO = this.daofactory.getDAOImpl(PerspectiveDAO.class);
            Iterator<Perspective> iteratorPerspective = this.listObjectPerspective.iterator();
            
         	while (iteratorPerspective.hasNext()) {

         		Perspective perspective = iteratorPerspective.next();
                //System.out.println(perspective.toString());
         		perspectiveDAO.create(perspective);
         	}        		

        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}
         

	/*
     * update the Editor Anatomy DB Table from the Editor List
     */
	private void updateDatabaseEditor() throws Exception {
		
    	try {

    		ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    EditorDAO editorDAO = this.daofactory.getDAOImpl(EditorDAO.class);
    	    Iterator<Editor> iteratorEditor = this.listObjectEditor.iterator();

         	while (iteratorEditor.hasNext()) {

         		Editor editor = iteratorEditor.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_EDITOR";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		editor.setOid(longMaxOid);
                //System.out.println(editor.toString());
                editorDAO.create(editor);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, editor.toStringThing(), strCalledFromTable);
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


	/*
     * update the Source Anatomy DB Table from the Source List
     */
	private void updateDatabaseSource() throws Exception {
		
    	try {
        	
    	    ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    SourceDAO sourceDAO = this.daofactory.getDAOImpl(SourceDAO.class);
            Iterator<Source> iteratorSource = this.listObjectSource.iterator();
            
         	while (iteratorSource.hasNext()) {

         		Source source = iteratorSource.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_SOURCE";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		source.setOid(longMaxOid);
                //System.out.println(source.toString());
                sourceDAO.create(source);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, source.toStringThing(), strCalledFromTable);
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


	/*
     * update the Stage Anatomy DB Table from the Stage List
     */
	private void updateDatabaseStage() throws Exception {
		
    	try {
        	
    	    ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    StageDAO stageDAO = this.daofactory.getDAOImpl(StageDAO.class);
            Iterator<Stage> iteratorStage = this.listObjectStage.iterator();
            
         	while (iteratorStage.hasNext()) {

         		Stage stage = iteratorStage.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_STAGE";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		stage.setOid(longMaxOid);
                //System.out.println(stage.toString());
                stageDAO.create(stage);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, stage.toStringThing(), strCalledFromTable);
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


	/*
     * update the Version Anatomy DB Table from the Version List
     */
	private void updateDatabaseVersion() throws Exception {
		
    	try {
        	
    	    ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    VersionDAO versionDAO = this.daofactory.getDAOImpl(VersionDAO.class);
            Iterator<Version> iteratorVersion = this.listObjectVersion.iterator();
            
         	while (iteratorVersion.hasNext()) {

         		Version version = iteratorVersion.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_VERSION";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		version.setOid(longMaxOid);
                //System.out.println(version.toString());
                versionDAO.create(version);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, version.toStringThing(), strCalledFromTable);
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


	/*
     * update the Node Anatomy DB Table from the Node List
     */
	private void updateDatabaseNode() throws Exception {
		
    	try {
        	
    	    ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    NodeDAO nodeDAO = this.daofactory.getDAOImpl(NodeDAO.class);
            Iterator<Node> iteratorNode = this.listObjectNode.iterator();
            
         	while (iteratorNode.hasNext()) {

         		Node node = iteratorNode.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_NODE";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		node.setOid(longMaxOid);
                //System.out.println(node.toString());
                nodeDAO.create(node);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, node.toStringThing(), strCalledFromTable);
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


	/*
     * update the Relationship Anatomy DB Table from the RelationshipFK List
     */
	private void updateDatabaseRelationship() throws Exception {
		
    	try {
        	
    	    ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    NodeDAO nodeDAO = this.daofactory.getDAOImpl(NodeDAO.class);
    	    RelationshipDAO relationshipDAO = this.daofactory.getDAOImpl(RelationshipDAO.class);
    	    
         	Iterator<RelationshipFK> iteratorRelationshipFK = this.listObjectRelationshipFK.iterator();
            
         	while (iteratorRelationshipFK.hasNext()) {

         		RelationshipFK relationshipfk = iteratorRelationshipFK.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_RELATIONSHIP";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		Node nodeChild = nodeDAO.findByPublicId(relationshipfk.getChildNameFK());
         		Node nodeParent = nodeDAO.findByPublicId(relationshipfk.getParentNameFK());
         		
         		Relationship relationship = new Relationship();
        		
         		relationship.setOid(longMaxOid);
         		relationship.SetTypeFK(relationshipfk.getTypeFK());
         		relationship.setChildFK(nodeChild.getOid());
         		relationship.getParentFK(nodeParent.getOid());
                //System.out.println(relationship.toString());
                relationshipDAO.create(relationship);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, relationship.toStringThing(), strCalledFromTable);
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


	/*
     * update the Synonym Anatomy DB Table from the SynonymFK List
     */
	private void updateDatabaseSynonym() throws Exception {
		
    	try {
        	
    	    ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    SynonymDAO synonymDAO = this.daofactory.getDAOImpl(SynonymDAO.class);
    	    NodeDAO nodeDAO = this.daofactory.getDAOImpl(NodeDAO.class);
         	Iterator<SynonymFK> iteratorSynonymFK = this.listObjectSynonymFK.iterator();
            
         	while (iteratorSynonymFK.hasNext()) {

         		SynonymFK synonymfk = iteratorSynonymFK.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_SYNONYM";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		Node node = nodeDAO.findByPublicId(synonymfk.getThingNameFK());
         		
         		Synonym synonym = new Synonym();
        		
         		synonym.setOid(longMaxOid);
         		synonym.setThingFK(node.getOid());
         		synonym.setName(synonymfk.getName());
                //System.out.println(synonym.toString());
                synonymDAO.create(synonym);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, synonym.toStringThing(), strCalledFromTable);
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


	/*
     * update the TimedNode Anatomy DB Table from the TimedNodeFK List
     */
	private void updateDatabaseTimedNode() throws Exception {
		
    	try {
        	
    	    ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    NodeDAO nodeDAO = this.daofactory.getDAOImpl(NodeDAO.class);
    	    StageDAO stageDAO = this.daofactory.getDAOImpl(StageDAO.class);
    	    TimedNodeDAO timednodeDAO = this.daofactory.getDAOImpl(TimedNodeDAO.class);
    	    
         	Iterator<TimedNodeFK> iteratorTimedNodeFK = this.listObjectTimedNodeFK.iterator();
            
         	while (iteratorTimedNodeFK.hasNext()) {

         		TimedNodeFK timednodefk = iteratorTimedNodeFK.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_TIMED_NODE";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		Node node = nodeDAO.findByPublicId(timednodefk.getNodeNameFK());
         		Stage stage = stageDAO.findByName(timednodefk.getStageNameFK());
         		
         		TimedNode timednode = new TimedNode();
        		
         		timednode.setOid(longMaxOid);
         		timednode.setNodeFK(node.getOid());
         		timednode.setStageFK(stage.getOid());
         		timednode.setStageModifierFK(timednodefk.getStageModifierFK());
         		timednode.setPublicId(timednodefk.getPublicId());
         		timednode.setDisplayId(timednodefk.getDisplayId());
                //System.out.println(timednode.toString());
                timednodeDAO.create(timednode);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, timednode.toStringThing(), strCalledFromTable);
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
	
         	
	/*
     * update the PerspectiveAmbit Anatomy DB Table from the PerspectiveAmbitFK List
     */
	private void updateDatabasePerspectiveAmbit() throws Exception {
		
    	try {
        	
    	    ThingDAO thingDAO = this.daofactory.getDAOImpl(ThingDAO.class);
    	    NodeDAO nodeDAO = this.daofactory.getDAOImpl(NodeDAO.class);
    	    PerspectiveAmbitDAO perspectiveambitDAO = this.daofactory.getDAOImpl(PerspectiveAmbitDAO.class);
    	    
            Iterator<PerspectiveAmbitFK> iteratorPerspectiveAmbitFK = this.listObjectPerspectiveAmbitFK.iterator();
            
         	while (iteratorPerspectiveAmbitFK.hasNext()) {

         		PerspectiveAmbitFK perspectiveambitfk = iteratorPerspectiveAmbitFK.next();
         		
                String strDatetime = utility.MySQLDateTime.now();
                long longSysadmin = 2;
                String strCalledFromTable = "ANA_PERSPECTIVE_AMBIT";

                long longMaxOid = thingDAO.maximumOid();
         		longMaxOid++;
         		
         		Node node = nodeDAO.findByPublicId(perspectiveambitfk.getPublicId());
         		
         		PerspectiveAmbit perspectiveambit = new PerspectiveAmbit();
        		
         		perspectiveambit.setOid(longMaxOid);
         		perspectiveambit.setPerspectiveFK(perspectiveambitfk.getPerspectiveFK());
         		perspectiveambit.setNodeFK(node.getOid());
         		perspectiveambit.setStart(perspectiveambitfk.isStart());
         		perspectiveambit.setStop(perspectiveambitfk.isStop());
         		perspectiveambit.setComments(perspectiveambitfk.getComments());
                
         		perspectiveambitDAO.create(perspectiveambit);

                Thing thing = new Thing(longMaxOid, strDatetime, longSysadmin, perspectiveambit.toStringThing(), strCalledFromTable);

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
	

	/*
     * Add Species to the Mega 2D Array from the Species Anatomy DB Table
     */
	private void addSpeciesToCSV2DArray() throws Exception {
		
    	try {
        	
    	    SpeciesDAO speciesDAO = this.daofactory.getDAOImpl(SpeciesDAO.class);
    	    this.listObjectSpecies = speciesDAO.listAll();
    	    
         	Iterator<Species> iteratorSpecies = this.listObjectSpecies.iterator();
            while (iteratorSpecies.hasNext()) {

         		Species species = iteratorSpecies.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "REF_SPECIES");
                row.add(1, species.getName());
                row.add(2, species.getLatinName());
                row.add(3, species.getTimedPrefix());
                row.add(4, species.getAbstractPrefix());
         	    
                this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add Evidence to the Mega 2D Array from the Evidence Anatomy DB Table
     */
	private void addEvidenceToCSV2DArray() throws Exception {
		
    	try {
        	
    	    EvidenceDAO evidenceDAO = this.daofactory.getDAOImpl(EvidenceDAO.class);
    	    this.listObjectEvidence = evidenceDAO.listAll();
    	    
            Iterator<Evidence> iteratorEvidence = this.listObjectEvidence.iterator();
         	while (iteratorEvidence.hasNext()) {

         		Evidence evidence = iteratorEvidence.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_EVIDENCE");
    			row.add(1, evidence.getName());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add SourceFormat to the Mega 2D Array from the SourceFormat Anatomy DB Table
     */
	private void addSourceFormatToCSV2DArray() throws Exception {
		
    	try {
        	
    	    SourceFormatDAO sourceformatDAO = this.daofactory.getDAOImpl(SourceFormatDAO.class);
    	    this.listObjectSourceFormat = sourceformatDAO.listAll();
    	    
            Iterator<SourceFormat> iteratorSourceFormat = this.listObjectSourceFormat.iterator();
         	while (iteratorSourceFormat.hasNext()) {

         		SourceFormat sourceformat = iteratorSourceFormat.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_SOURCE_FORMAT");
    			row.add(1, sourceformat.getName());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add StageModifier to the Mega 2D Array from the StageModifier Anatomy DB Table
     */
	private void addStageModifierToCSV2DArray() throws Exception {
		
    	try {
        	
    	    StageModifierDAO stagemodifierDAO = this.daofactory.getDAOImpl(StageModifierDAO.class);
    	    this.listObjectStageModifier = stagemodifierDAO.listAll();
    	    
            Iterator<StageModifier> iteratorStageModifier = this.listObjectStageModifier.iterator();
         	while (iteratorStageModifier.hasNext()) {

         		StageModifier stagemodifier = iteratorStageModifier.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_STAGE_MODIFIER");
    			row.add(1, stagemodifier.getName());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add RelationshipType to the Mega 2D Array from the RelationshipType Anatomy DB Table
     */
	private void addRelationshipTypeToCSV2DArray() throws Exception {
		
    	try {
        	
    	    RelationshipTypeDAO relationshiptypeDAO = this.daofactory.getDAOImpl(RelationshipTypeDAO.class);
         	this.listObjectRelationshipType = relationshiptypeDAO.listAll();
         	
            Iterator<RelationshipType> iteratorRelationshipType = this.listObjectRelationshipType.iterator();
         	while (iteratorRelationshipType.hasNext()) {

         		RelationshipType relationshiptype = iteratorRelationshipType.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_RELATIONSHIP_TYPE");
    			row.add(1, relationshiptype.getName());
    			row.add(2, relationshiptype.getChild2Parent());
    			row.add(3, relationshiptype.getParent2Child());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add Project to the Mega 2D Array from the Project Anatomy DB Table
     */
	private void addProjectToCSV2DArray() throws Exception {
		
    	try {
        	
    	    ProjectDAO projectDAO = this.daofactory.getDAOImpl(ProjectDAO.class);
         	this.listObjectProject = projectDAO.listAll();
         	
            Iterator<Project> iteratorProject = this.listObjectProject.iterator();
         	while (iteratorProject.hasNext()) {

         		Project project = iteratorProject.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_PROJECT");
    			row.add(1, project.getName());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add Perspective to the Mega 2D Array from the Perspective Anatomy DB Table
     */
	private void addPerspectiveToCSV2DArray() throws Exception {
		
    	try {
        	
    	    PerspectiveDAO perspectiveDAO = this.daofactory.getDAOImpl(PerspectiveDAO.class);
         	this.listObjectPerspective = perspectiveDAO.listAll();
         	
            Iterator<Perspective> iteratorPerspective = this.listObjectPerspective.iterator();
         	while (iteratorPerspective.hasNext()) {

         		Perspective perspective = iteratorPerspective.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_PERSPECTIVE");
    			row.add(1, perspective.getName());
    			row.add(2, perspective.getComments());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add Editor to the Mega 2D Array from the Editor Anatomy DB Table
     */
	private void addEditorToCSV2DArray() throws Exception {
		
    	try {
        	
    	    EditorDAO editorDAO = this.daofactory.getDAOImpl(EditorDAO.class);
         	this.listObjectEditor = editorDAO.listAll();
         	
            Iterator<Editor> iteratorEditor = this.listObjectEditor.iterator();
         	while (iteratorEditor.hasNext()) {

         		Editor editor = iteratorEditor.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_EDITOR");
    			row.add(1, editor.getName());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add Source to the Mega 2D Array from the Source Anatomy DB Table
     */
	private void addSourceToCSV2DArray() throws Exception {
		
    	try {
        	
    	    SourceDAO sourceDAO = this.daofactory.getDAOImpl(SourceDAO.class);
         	this.listObjectSource = sourceDAO.listAll();
         	
            Iterator<Source> iteratorSource = this.listObjectSource.iterator();
         	while (iteratorSource.hasNext()) {

         		Source source = iteratorSource.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_SOURCE");
    			row.add(1, source.getName());
    			row.add(2, source.getAuthors());
    			row.add(3, source.getFormatFK());
    			row.add(4, source.getYearAsString());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add Stage to the Mega 2D Array from the Stage Anatomy DB Table
     */
	private void addStageToCSV2DArray() throws Exception {
		
    	try {

    		StageDAO stageDAO = this.daofactory.getDAOImpl(StageDAO.class);
         	this.listObjectStage = stageDAO.listAll();
         	
            Iterator<Stage> iteratorStage = this.listObjectStage.iterator();
         	while (iteratorStage.hasNext()) {

         		Stage stage = iteratorStage.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_STAGE");
    			row.add(1, stage.getSpeciesFK());
    			row.add(2, stage.getName());
    			row.add(3, stage.getSequenceAsString());
    			row.add(4, stage.getDescription());
    			row.add(5, stage.getExtraText());
    			row.add(6, stage.getPublicId());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add Version to the Mega 2D Array from the Version Anatomy DB Table
     */
	private void addVersionToCSV2DArray() throws Exception {
		
    	try {
        	

    	    VersionDAO versionDAO = this.daofactory.getDAOImpl(VersionDAO.class);
         	this.listObjectVersion = versionDAO.listAll();
         	
            Iterator<Version> iteratorVersion = this.listObjectVersion.iterator();
         	while (iteratorVersion.hasNext()) {

         		Version version = iteratorVersion.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_VERSION");
    			row.add(1, version.getNumberAsString());
    			row.add(2, version.getDate());
    			row.add(3, version.getComments());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add Node to the Mega 2D Array from the Node Anatomy DB Table
     */
	private void addNodeToCSV2DArray() throws Exception {
		
    	try {
        	
    	    NodeDAO nodeDAO = this.daofactory.getDAOImpl(NodeDAO.class);
         	this.listObjectNode = nodeDAO.listAll();
         	
            Iterator<Node> iteratorNode = this.listObjectNode.iterator();
         	while (iteratorNode.hasNext()) {

         		Node node = iteratorNode.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_NODE");
    			row.add(1, node.getSpeciesFK());
    			row.add(2, node.getComponentName());
    			row.add(3, node.getPrimary());
    			row.add(4, node.getGroup());
    			row.add(5, node.getPublicId());
    			row.add(6, node.getDescription());
    			row.add(7, node.getDisplayId());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add SynonymFK to the Mega 2D Array from the Synonym Anatomy DB Table
     */
	private void addSynonymFKToCSV2DArray() throws Exception {
		
    	try {
        	
    	    SynonymFKDAO synonymfkDAO = this.daofactory.getDAOImpl(SynonymFKDAO.class);
         	this.listObjectSynonymFK = synonymfkDAO.listAll();
         	
            Iterator<SynonymFK> iteratorSynonymFK = this.listObjectSynonymFK.iterator();
         	while (iteratorSynonymFK.hasNext()) {

         		SynonymFK synonymfk = iteratorSynonymFK.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_SYNONYM");
    			row.add(1, synonymfk.getThingNameFK());
    			row.add(2, synonymfk.getName());
         	    
    			this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add RelationshipFK to the Mega 2D Array from the Relationship Anatomy DB Table
     */
	private void addRelationshipFKToCSV2DArray() throws Exception {
		
    	try {
        	
    	    RelationshipFKDAO relationshipfkDAO = this.daofactory.getDAOImpl(RelationshipFKDAO.class);
         	this.listObjectRelationshipFK = relationshipfkDAO.listAll();
         	
            Iterator<RelationshipFK> iteratorRelationshipFK = this.listObjectRelationshipFK.iterator();
         	while (iteratorRelationshipFK.hasNext()) {

         		RelationshipFK relationshipfk = iteratorRelationshipFK.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_RELATIONSHIP");
         	    row.add(1, relationshipfk.getTypeFK());
         	    row.add(2, relationshipfk.getChildNameFK());
         	    row.add(3, relationshipfk.getParentNameFK());
         	    
         	   this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add TimedNodeFK to the Mega 2D Array from the TimedNode Anatomy DB Table
     */
	private void addTimedNodeFKToCSV2DArray() throws Exception {
		
    	try {
        	
    	    TimedNodeFKDAO timednodefkDAO = this.daofactory.getDAOImpl(TimedNodeFKDAO.class);
         	this.listObjectTimedNodeFK = timednodefkDAO.listAll();
         	
            Iterator<TimedNodeFK> iteratorTimedNodeFK = this.listObjectTimedNodeFK.iterator();
         	while (iteratorTimedNodeFK.hasNext()) {

         		TimedNodeFK timednodefk = iteratorTimedNodeFK.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_TIMED_NODE");
         	    row.add(1, timednodefk.getNodeNameFK());
         	    row.add(2, timednodefk.getStageNameFK());
         	    row.add(3, timednodefk.getStageModifierFK());
         	    row.add(4, timednodefk.getPublicId());
         	    row.add(5, timednodefk.getDisplayId());
         	    
         	   this.csv2DStringArray.add(row);
         	}        		
        }
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        } 
	}

	/*
     * Add PerspectiveAmbitFK to the Mega 2D Array from the PerspectiveAmbit Anatomy DB Table
     */
	private void addPerspectiveAmbitFKToCSV2DArray() throws Exception {
		
    	try {
        	
    	    PerspectiveAmbitFKDAO perspectiveambitfkDAO = this.daofactory.getDAOImpl(PerspectiveAmbitFKDAO.class);
         	this.listObjectPerspectiveAmbitFK = perspectiveambitfkDAO.listAll();

            Iterator<PerspectiveAmbitFK> iteratorPerspectiveAmbitFK = this.listObjectPerspectiveAmbitFK.iterator();
         	while (iteratorPerspectiveAmbitFK.hasNext()) {

         		PerspectiveAmbitFK perspectiveambitfk = iteratorPerspectiveAmbitFK.next();
         	
         	    List<String> row = new ArrayList<String>();
         	    
         	    row.add(0, "ANA_PERSPECTIVE_AMBIT");
         	    row.add(1, perspectiveambitfk.getPerspectiveFK());
         	    row.add(2, perspectiveambitfk.getPublicId());
         	    row.add(3, perspectiveambitfk.getStart());
         	    row.add(4, perspectiveambitfk.getStop());
         	    row.add(5, perspectiveambitfk.getComments());
         	  
         	    this.csv2DStringArray.add(row);
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
