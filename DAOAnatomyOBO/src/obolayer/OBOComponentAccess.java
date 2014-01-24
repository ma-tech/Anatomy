/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        OBOComponentAccess.java
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
* Description:  A Wrapper Class for accessing OBO OBOComponent files
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
import java.util.List;

import obomodel.OBOComponent;
import obomodel.OBORelation;

public final class OBOComponentAccess {
    // Constants ----------------------------------------------------------------------------------

    // Vars ---------------------------------------------------------------------------------------
    private OBOFactory oboFactory;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a OBOComponent OBO for the given OBOFactory.
     * 
     *  Package private so that it can be constructed inside the OBO package only.
     */
    OBOComponentAccess(OBOFactory oboFactory) {
    	
        this.oboFactory = oboFactory;
    }
    
    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a list of ALL INPUT OBOComponents, otherwise null.
     */
    public List<OBOComponent> listAllInput() throws Exception {
    	
        return listInput();
    }
    
    /*
     * Returns a list of ALL BASE OBOComponents, otherwise null.
     */
    public List<OBOComponent> listAllBase() throws Exception {
    	
        return listBase();
    }
    
    /*
     * Returns a list of all components from the INPUT file. 
     *  The list is never null and is empty when the file does not contain any components.
     */
    public List<OBOComponent> listInput() throws Exception {
      
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();

        try {
        	
        	obocomponents = oboFactory.getInputComponents();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 

        return obocomponents;
    }

    /*
     * Returns a list of all components from the BASE file. 
     *  The list is never null and is empty when the file does not contain any components.
     */
    public List<OBOComponent> listBase() throws Exception {
      
        List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();

        try {
        	
        	obocomponents = oboFactory.getBaseComponents();
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
     * Within the OBOFactory, throw away any existing list of OBORelations, and replace them
     *  with the supplied list instead. 
     */
    public void setOBORelationList(ArrayList<OBORelation> arrayrellist) throws OBOException {
    	
    	oboFactory.setOBORelations(arrayrellist);
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
    public boolean writeAll(String stage) throws Exception {
    	
        return write( stage );
    }
    
    /*
     * write OBO OBOComponents
     */
    public boolean write( String stage ) throws Exception {
      
        boolean isProcessed = false; 
        		
        try {
        	
        	isProcessed = oboFactory.writeComponents( stage );
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
        
        return isProcessed;
    }

    /*
     * Return the OBO Factory Message Level - from the OBO properties file
     */
    public String msgLevel() throws OBOException {

        try {
        	
            return oboFactory.getMsgLevel();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
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
     * Return the OBO Factory GenerateIdentifiers flag - from the OBO properties file
     */
    public boolean generateIdentifiers() throws OBOException {

        try {
        	
            return oboFactory.isGenerateIdentifiers();
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
     * Return the OBO Factory Base File name - from the OBO properties file
     */
    public String baseFile() throws OBOException {

        try {
        	
            return oboFactory.getBaseFile();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory BASE File name - from the OBO properties file
     */
    public String baseFileContents() throws Exception {

        try {
        	
            return oboFactory.getBaseComponentContent();
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
    public String inputFileContents() throws Exception {

        try {
        	
            return oboFactory.getInputComponentContent();
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
    public String outputFileName() throws OBOException {

    	try {
    		
            return oboFactory.getOutputFileName();
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
    
    /*
     * Return the OBO Factory AbstractClassName - from the OBO properties file
     */
    public String abstractClassName() throws OBOException {

    	try {
    		
            return oboFactory.getAbstractClassName();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory AbstractClassId - from the OBO properties file
     */
    public String abstractClassId() throws OBOException {

    	try {
    		
            return oboFactory.getAbstractClassId();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory AbstractClassNamespace - from the OBO properties file
     */
    public String abstractClassNamespace() throws OBOException {

    	try {
    		
            return oboFactory.getAbstractClassNamespace();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory StageClassName - from the OBO properties file
     */
    public String stageClassName() throws OBOException {

    	try {
    		
            return oboFactory.getStageClassName();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory StageClassId - from the OBO properties file
     */
    public String stageClassId() throws OBOException {

    	try {
    		
            return oboFactory.getStageClassId();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory StageClassNamespace - from the OBO properties file
     */
    public String stageClassNamespace() throws OBOException {

    	try {
    		
            return oboFactory.getStageClassNamespace();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory GroupClassName - from the OBO properties file
     */
    public String groupClassName() throws OBOException {

    	try {
    		
            return oboFactory.getGroupClassName();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory GroupClassId - from the OBO properties file
     */
    public String groupClassId() throws OBOException {

    	try {
    		
            return oboFactory.getGroupClassId();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory GroupClassNamespace - from the OBO properties file
     */
    public String groupClassNamespace() throws OBOException {

    	try {
    		
            return oboFactory.getGroupClassNamespace();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory GroupTermClassName - from the OBO properties file
     */
    public String groupTermClassName() throws OBOException {

    	try {
    		
            return oboFactory.getGroupTermClassName();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory GroupTermClassId - from the OBO properties file
     */
    public String groupTermClassId() throws OBOException {

    	try {
    		
            return oboFactory.getGroupTermClassId();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory GroupTermClassNamespace - from the OBO properties file
     */
    public String groupTermClassNamespace() throws OBOException {

    	try {
    		
            return oboFactory.getGroupTermClassNamespace();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }
    
    /*
     * Return the OBO Factory MinStageSequence - from the OBO properties file
     */
    public int minStageSequence() throws OBOException {

    	try {
    		
            return oboFactory.getMinStageSequence();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }

    /*
     * Return the OBO Factory MaxStageSequence - from the OBO properties file
     */
    public int maxStageSequence() throws OBOException {

    	try {
    		
            return oboFactory.getMaxStageSequence();
        } 
        catch (OBOConfigurationException e) {
        	
            throw new OBOException(e);
        } 
    }
}
