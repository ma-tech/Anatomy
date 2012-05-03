/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        ComponentOBO.java
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
* Description:  A Wrapper Class for accessing OBO ComponentOBO files
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import daolayer.DAOException;
import daolayer.DAOFactory;
import daolayer.StageDAO;

import daomodel.Stage;

import obomodel.ComponentFile;
import obomodel.Relation;

public final class ComponentOBO {

    // Constants ----------------------------------------------------------------------------------

    // Vars ---------------------------------------------------------------------------------------
    private OBOFactory oboFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Component OBO for the given OBOFactory.
     * 
     *  Package private so that it can be constructed inside the OBO package only.
     */
    ComponentOBO(OBOFactory oboFactory) {
    	
        this.oboFactory = oboFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL Components, otherwise null.
     */
    public List<ComponentFile> listAll() throws OBOException {
    	
        return list();
    }
    
    /*
     * Returns a list of all components from the file. 
     *  The list is never null and is empty when the file does not contain any components.
     */
    public List<ComponentFile> list() throws OBOException {
      
        List<ComponentFile> obocomponents = new ArrayList<ComponentFile>();

        try {
        	obocomponents = oboFactory.getComponents();
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 

        return obocomponents;
    }

    /*
     * Within the OBOFactory, throw away any existing list of OBO Components, and replace them
     *  with the supplied list instead. 
     */
    public void setComponentFileList(ArrayList<ComponentFile> arrayobolist) throws OBOException {
    	
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
     * Within the OBOFactory, Add an additional lust of OBO Components to the existing list 
     */
    public void addComponentFileList(ArrayList<ComponentFile> arrayobolist) throws OBOException {
    	
    	oboFactory.addComponents(arrayobolist);
    }
    
    /*
     * Within the OBOFactory, write ALL the OBO Components in the existing OBO Factory list to File 
     */
    public Boolean writeAll() throws OBOException {
    	
        return write();
    }
    
    /*
     * write OBO Components
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

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Add the extra OBO components to the component list for Stages, Relationships etc
     */
    public void createTemplateRelationList() throws OBOException {
    	
        ArrayList<Relation> oborelationList = new ArrayList();
        ComponentFile obocomponent;

        ArrayList <ComponentFile> obocomponentList = new ArrayList <ComponentFile>();
        Relation oborelation;

    	try {
            // Obtain DAOFactory.
            DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
            //System.out.println("DAOFactory successfully obtained: " + anatomy008);

            // Obtain DAOs.
            StageDAO stageDAO = anatomy008.getStageDAO();

            // group obocomponents----------------------------------------------------------------------
            obocomponent = new ComponentFile();
            obocomponent.setName( "Group term" );
            obocomponent.setID( "group_term" );
            obocomponent.setNamespace( "group_term" );
            obocomponent.setDBID( "-1" );
            obocomponentList.add( obocomponent );

            // stage class------------------------------------------------------------------------------
            obocomponent = new ComponentFile();
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

          		obocomponent = new ComponentFile();
                obocomponent.setName( stage.getName() );
                obocomponent.setID( stage.getName() );
                obocomponent.setDBID( String.valueOf(stage.getOid()) );

                obocomponent.addChildOf( "TS:0" );
                obocomponent.addChildOfType( "IS_A" );

                obocomponent.setNamespace( "theiler_stage" );
                obocomponentList.add( obocomponent );
          	}

            // new group class
            obocomponent = new ComponentFile();
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
}
