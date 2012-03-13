/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        ComponentFileOBO.java
#
# Date:         2012
#
# Author:       Mike Wicks
#
# Copyright:    2012
#               Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Description:  A Wrapper Class for accessing OBO ComponentFiles
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package OBOLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DAOLayer.DAOException;
import DAOLayer.DAOFactory;
import DAOLayer.StageDAO;

import DAOModel.Stage;

import OBOModel.ComponentFile;
import OBOModel.Relation;

/*
 * This class represents a File Database Access Object for the ComponentFile DTO.
 * 
 */
public final class ComponentOBO {

    // Constants ----------------------------------------------------------------------------------

    // Vars ---------------------------------------------------------------------------------------
    private OBOFactory oboFactory;

    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a Log DAO for the given DAOFactory.
     *  Package private so that it can be constructed inside the DAO package only.
     */
    ComponentOBO(OBOFactory oboFactory) {
    	
        this.oboFactory = oboFactory;
        
    }

    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL logs, otherwise null.
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
     * Returns a list of ALL logs, otherwise null.
     */
    public void setComponentFileList(ArrayList<ComponentFile> arrayobolist) throws OBOException {
    	
    	oboFactory.setComponents(arrayobolist);
        
    }
    
    public void setRelationList(ArrayList<Relation> arrayrellist) throws OBOException {
    	
    	oboFactory.setRelations(arrayrellist);
        
    }
    
    public void addComponentFileList(ArrayList<ComponentFile> arrayobolist) throws OBOException {
    	
    	oboFactory.addComponents(arrayobolist);
        
    }
    
    /*
     * Returns a list of ALL logs, otherwise null.
     */
    public Boolean writeAll() throws OBOException {
    	
        return write();
        
    }
    
    /*
     * Returns a list of all components from the file. 
     *  The list is never null and is empty when the file does not contain any components.
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
     * Returns a list of all components from the file. 
     *  The list is never null and is empty when the file does not contain any components.
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
     * Returns a list of all components from the file. 
     *  The list is never null and is empty when the file does not contain any components.
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
     * Returns a list of all components from the file. 
     *  The list is never null and is empty when the file does not contain any components.
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
     * Returns a list of all components from the file. 
     *  The list is never null and is empty when the file does not contain any components.
     */
    public String inputFile() throws OBOException {
      

        try {
        	
            return oboFactory.getInputFile();
            
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
       
    }

    public String inputFileVersion() throws OBOException {
        

        try {
        	
            return oboFactory.getOutputFileVersion();
            
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
       
    }

    public String inputFileNameSpace() throws OBOException {
        

        try {
        	
            return oboFactory.getOutputFileNameSpace();
            
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
       
    }

    public String inputFileSavedBy() throws OBOException {
        

        try {
        	
            return oboFactory.getOutputFileSavedBy();
            
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
       
    }

    public String inputFileRemark() throws OBOException {
        

        try {
        	
            return oboFactory.getOutputFileRemark();
            
        } 
        catch (OBOConfigurationException e) {
            throw new OBOException(e);
        } 
       
    }

    /*
     * Returns a list of all components from the file. 
     *  The list is never null and is empty when the file does not contain any components.
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

    	oboFactory.setRelations(oborelationList);

    	oboFactory.addComponents(obocomponentList);

    }
    

}
