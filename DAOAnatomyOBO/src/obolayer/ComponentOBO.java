/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        OBOComponentOBO.java
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
* Description:  A Wrapper Class for accessing OBO OBOComponentOBO files
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package obolayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daolayer.DAOException;
import daolayer.DAOFactory;
import daolayer.StageDAO;

import daomodel.Stage;

import obomodel.OBOComponent;
import obomodel.Relation;

public final class ComponentOBO {

    // Constants ----------------------------------------------------------------------------------

    // Vars ---------------------------------------------------------------------------------------
    private OBOFactory oboFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a OBOComponent OBO for the given OBOFactory.
     * 
     *  Package private so that it can be constructed inside the OBO package only.
     */
    ComponentOBO(OBOFactory oboFactory) {
    	
        this.oboFactory = oboFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL OBOComponents, otherwise null.
     */
    public List<OBOComponent> listAll() throws OBOException, IOException {
    	
        return list();
    }
    
    /*
     * Returns a list of all components from the file. 
     *  The list is never null and is empty when the file does not contain any components.
     */
    public List<OBOComponent> list() throws OBOException, IOException {
      
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();

        try {
        	obocomponents = oboFactory.getComponents();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 

        return obocomponents;
    }

    /*
     * Within the OBOFactory, throw away any existing list of OBO OBOComponents, and replace them
     *  with the supplied list instead. 
     */
    public void setComponentList(ArrayList<OBOComponent> arrayobolist) throws OBOException {
    	
    	oboFactory.setComponents(arrayobolist);
    }
    
    /*
     * Within the OBOFactory, throw away any existing list of Relations, and replace them
     *  with the supplied list instead. 
     */
    public void setRelationList(ArrayList<Relation> arrayrellist) throws OBOException {
    	
    	oboFactory.setRelations(arrayrellist);
    }
    
    /*
     * Within the OBOFactory, Add an additional lust of OBO OBOComponents to the existing list 
     */
    public void addOBOComponentList(ArrayList<OBOComponent> arrayobolist) throws OBOException {
    	
    	oboFactory.addComponents(arrayobolist);
    }
    
    /*
     * Within the OBOFactory, write ALL the OBO OBOComponents in the existing OBO Factory list to File 
     */
    public Boolean writeAll() throws OBOException {
    	
        return write();
    }
    
    /*
     * write OBO OBOComponents
     */
    public Boolean write() throws OBOException {
      
        Boolean isProcessed = false; 
        		
        try {
        	isProcessed = oboFactory.writeComponents();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
        return isProcessed;
    }

    /*
     * Return the OBO Factory Debug flag - from the OBO properties file
     */
    public boolean debug() throws OBOException {

        try {
            return oboFactory.isDebug();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Summary Report TEXT File name - from the OBO properties file
     */
    public String summaryReport() throws OBOException {

        try {
        	return oboFactory.getSummaryReport();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Summary Report PDF File name - from the OBO properties file
     */
    public String summaryReportPdf() throws OBOException {

        try {
            return oboFactory.getSummaryReportPdf();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Input File name - from the OBO properties file
     */
    public String inputFile() throws OBOException {

        try {
            return oboFactory.getInputFile();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Input File name - from the OBO properties file
     */
    public String inputFileContents() throws OBOException, IOException {

        try {
            return oboFactory.getComponentContent();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Input File Version - from the OBO properties file
     */
    public String inputFileVersion() throws OBOException {

        try {
            return oboFactory.getOutputFileVersion();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Input File namespace - from the OBO properties file
     */
    public String inputFileNameSpace() throws OBOException {

        try {
            return oboFactory.getOutputFileNameSpace();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Input File Author - from the OBO properties file
     */
    public String inputFileSavedBy() throws OBOException {

        try {
            return oboFactory.getOutputFileSavedBy();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Input File Remark - from the OBO properties file
     */
    public String inputFileRemark() throws OBOException {

    	try {
            return oboFactory.getOutputFileRemark();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Output File - from the OBO properties file
     */
    public String outputFile() throws OBOException {

    	try {
            return oboFactory.getOutputFile();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Species - from the OBO properties file
     */
    public String species() throws OBOException {

    	try {
            return oboFactory.getSpecies();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory Project - from the OBO properties file
     */
    public String project() throws OBOException {

    	try {
            return oboFactory.getProject();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Add the extra OBO components to the component list for Stages, Relationships etc
     */
    public void createTemplateRelationList() throws OBOException {
    	
        ArrayList<Relation> oborelationList = new ArrayList<Relation>();
        OBOComponent obocomponent;

        ArrayList <OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
        Relation oborelation;

    	try {
            // Obtain DAOFactory.
            DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
            //System.out.println("DAOFactory successfully obtained: " + anatomy008);

            // Obtain DAOs.
            StageDAO stageDAO = anatomy008.getStageDAO();

            // group obocomponents----------------------------------------------------------------------
            obocomponent = new OBOComponent();
            obocomponent.setName( "Group term" );
            obocomponent.setID( "group_term" );
            obocomponent.setNamespace( "group_term" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // stage class------------------------------------------------------------------------------
            obocomponent = new OBOComponent();
            obocomponent.setName( "Theiler stage" );
            obocomponent.setID( "TS:0" );
            obocomponent.setNamespace( "theiler_stage" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // rest from db---------------------------------------------------------------------------
            List<Stage> stages = new ArrayList<Stage>();
            stages = stageDAO.listAll();
            Iterator<Stage> iteratorStage = stages.iterator();
                
          	while (iteratorStage.hasNext()) {
          		
                // query for the stages-----------------------------------------------------------------
          		Stage stage = iteratorStage.next();

          		obocomponent = new OBOComponent();
                obocomponent.setName( stage.getName() );
                obocomponent.setID( stage.getName() );
                obocomponent.setDBID( String.valueOf(stage.getOid()) );

                obocomponent.addChildOf( "TS:0" );
                obocomponent.addChildOfType( "IS_A" );

                obocomponent.setNamespace( "theiler_stage" );
                obocomponentList.add( obocomponent );
          	}

            // new group class
            obocomponent = new OBOComponent();
            obocomponent.setName( "Tmp new group" );
            obocomponent.setID( "Tmp_new_group" );
            obocomponent.setNamespace( "new_group_namespace" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // starts at
            oborelation = new Relation();
            oborelation.setName( "starts at" );
            oborelation.setID( "starts_at" );
            oborelationList.add( oborelation );

            // ends at
            oborelation = new Relation();
            oborelation.setName( "ends at" );
            oborelation.setID( "ends_at" );
            oborelationList.add( oborelation );

            // partOf
            oborelation = new Relation();
            oborelation.setName( "part of" );
            oborelation.setID( "part_of" );
            oborelation.setTransitive( "true" );
            oborelationList.add( oborelation );

            // groupPartOf
            oborelation = new Relation();
            oborelation.setName( "group part of" );
            oborelation.setID( "group_part_of" );
            oborelation.setTransitive( "true" );
            oborelationList.add( oborelation );

            // isA
            oborelation = new Relation();
            oborelation.setName( "is a" );
            oborelation.setID( "is_a" );
            oborelation.setTransitive( "true" );
            oborelationList.add( oborelation );
    	}
    	catch (DAOException ex) {
    		ex.printStackTrace();
    	}

    	oboFactory.setRelations(oborelationList);

    	oboFactory.addComponents(obocomponentList);
    }

    /*
     * Add the extra OBO components to the component list for Stages, Relationships etc
     */
    public void createHumanRelationList() throws OBOException {
    	
        ArrayList<Relation> oborelationList = new ArrayList<Relation>();
        OBOComponent obocomponent;

        ArrayList <OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
        Relation oborelation;


        // stage class------------------------------------------------------------------------------
        
        obocomponent = new OBOComponent();
        obocomponent.setName( "Carnegie stage" );
        obocomponent.setID( "CS:0" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );
        
        obocomponent = new OBOComponent();
        obocomponent.setName( "CS01" );
        obocomponent.setID( "CS01" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS02" );
        obocomponent.setID( "CS02" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS03" );
        obocomponent.setID( "CS03" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS04" );
        obocomponent.setID( "CS04" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS05a" );
        obocomponent.setID( "CS05a" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS05b" );
        obocomponent.setID( "CS05b" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS05c" );
        obocomponent.setID( "CS05c" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS06a" );
        obocomponent.setID( "CS06a" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS06b" );
        obocomponent.setID( "CS06b" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS07" );
        obocomponent.setID( "CS07" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS08" );
        obocomponent.setID( "CS08" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS09" );
        obocomponent.setID( "CS09" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS10" );
        obocomponent.setID( "CS10" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS11" );
        obocomponent.setID( "CS11" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS12" );
        obocomponent.setID( "CS12" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS13" );
        obocomponent.setID( "CS13" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS14" );
        obocomponent.setID( "CS01" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS15" );
        obocomponent.setID( "CS15" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS16" );
        obocomponent.setID( "CS16" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS17" );
        obocomponent.setID( "CS01" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS18" );
        obocomponent.setID( "CS18" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS19" );
        obocomponent.setID( "CS19" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS20" );
        obocomponent.setID( "CS20" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS21" );
        obocomponent.setID( "CS21" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS22" );
        obocomponent.setID( "CS22" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS23" );
        obocomponent.setID( "CS23" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( "carnegie_stage" );
        obocomponentList.add( obocomponent );

        // starts at
        oborelation = new Relation();
        oborelation.setName( "starts_at" );
        oborelation.setID( "starts_at" );
        oborelationList.add( oborelation );

        // ends at
        oborelation = new Relation();
        oborelation.setName( "ends_at" );
        oborelation.setID( "ends_at" );
        oborelationList.add( oborelation );

        // partOf
        oborelation = new Relation();
        oborelation.setName( "part_of" );
        oborelation.setID( "part_of" );
        oborelation.setTransitive( "true" );
        oborelationList.add( oborelation );

        // isA
        oborelation = new Relation();
        oborelation.setName( "is_a" );
        oborelation.setID( "is_a" );
        oborelation.setTransitive( "true" );
        oborelationList.add( oborelation );

        // DevelopsFrom
        oborelation = new Relation();
        oborelation.setName( "develops_from" );
        oborelation.setID( "develops_from" );
        oborelation.setTransitive( "true" );
        oborelationList.add( oborelation );

        // DevelopsIn
        oborelation = new Relation();
        oborelation.setName( "develops_in" );
        oborelation.setID( "develops_in" );
        oborelationList.add( oborelation );

        // AttachedTo
        oborelation = new Relation();
        oborelation.setName( "attached_to" );
        oborelation.setID( "attached_to" );
        oborelationList.add( oborelation );

        // LocatedIn
        oborelation = new Relation();
        oborelation.setName( "located_in" );
        oborelation.setID( "located_in" );
        oborelationList.add( oborelation );

        // HasPart
        oborelation = new Relation();
        oborelation.setName( "has_part" );
        oborelation.setID( "has_part" );
        oborelation.setTransitive( "true" );
        oborelationList.add( oborelation );

        // HasPart
        oborelation = new Relation();
        oborelation.setName( "disjoint_from" );
        oborelation.setID( "disjoint_from" );
        oborelationList.add( oborelation );

    	oboFactory.setRelations(oborelationList);

    	oboFactory.addComponents(obocomponentList);
    }
}
