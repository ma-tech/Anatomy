/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        OBOFactory.java
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
* Description:  A Wrapper Class for accessing OBO Components
*
* This class represents a OBO factory for ano OBO File. 
* 
*  You can use "getInstance(String)" to obtain a new instance for the given database name. 
*  The specific instance returned depends on the properties file configuration. 
*  You can obtain OBO's for the OBO factory instance using the OBO getters.
* 
* This class requires a properties file named 'dao.properties' in the classpath with under each
* the following properties:
* 
*  name.oboInFile *
*  name.debug *
* 
* Those marked with * are required, others are optional and can be left away or empty. 
* 
*  The 'name' must represent the database name in "getInstance(String)".
*  
*  The 'name.oboInFile' must represent either the JDBC URL or JNDI name of the database.
* 
*  The 'name.debug' states whether the factory is to print out SQL statements and other Information
*   to System.out.
*  
* Here are basic examples of valid properties for a file with the name 'file':
* 
*  file.oboInFile = /Users/mwicks/Desktop/Version008_TEST.obo
* 
* Here is a basic use example:
* 
*  OBOFactory fileobo = OBOFactory.getInstance("file");
*  FileOBO fileOBO = fileobo.getFileOBO();
* 
* See http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
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

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import obomodel.OBOComponent;
import obomodel.Relation;

import routines.Parser;
import routines.Producer;

public abstract class OBOFactory {
    // Constants ----------------------------------------------------------------------------------
    private static final String PROPERTY_OBO_IN_FILE = "oboinfile";
    private static final String PROPERTY_OBO_OUT_FILE = "obooutfile";
    private static final String PROPERTY_OBO_OUT_FILE_VERSION = "obooutfileversion";
    private static final String PROPERTY_OBO_OUT_FILE_NAMESPACE = "obooutfilenamespace";
    private static final String PROPERTY_OBO_OUT_FILE_SAVED_BY = "obooutfilesavedby";
    private static final String PROPERTY_OBO_OUT_FILE_REMARK = "obooutfileremark";
    private static final String PROPERTY_SUMMARY_REPORT = "summaryreport";
    private static final String PROPERTY_SUMMARY_REPORT_PDF = "summaryreportpdf";
    private static final String PROPERTY_DEBUG = "debug";
    private static final String PROPERTY_SPECIES = "species";
    private static final String PROPERTY_PROJECT = "project";

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a new OBOFactory instance for the given database name.
     */
    public static OBOFactory getInstance(String name) throws OBOConfigurationException {
    	
        if (name == null) {
            throw new OBOConfigurationException("OBO name is null.");
        }

        OBOProperties properties = new OBOProperties(name);
        
        String filename = properties.getName();

        String strOboInFile = properties.getProperty(PROPERTY_OBO_IN_FILE, true);
        String strOboOutFile = properties.getProperty(PROPERTY_OBO_OUT_FILE, false);
        String strOboOutFileVersion = properties.getProperty(PROPERTY_OBO_OUT_FILE_VERSION, false);
        String strOboOutFileNameSpace = properties.getProperty(PROPERTY_OBO_OUT_FILE_NAMESPACE, false);
        String strOboOutFileSavedBy = properties.getProperty(PROPERTY_OBO_OUT_FILE_SAVED_BY, false);
        String strOboOutFileRemark = properties.getProperty(PROPERTY_OBO_OUT_FILE_REMARK, false);
        String strSummaryReport = properties.getProperty(PROPERTY_SUMMARY_REPORT, false);
        String strSummaryReportPdf = properties.getProperty(PROPERTY_SUMMARY_REPORT_PDF, false);
        String strDebug = properties.getProperty(PROPERTY_DEBUG, true);
        String strSpecies = properties.getProperty(PROPERTY_SPECIES, true);
        String strProject = properties.getProperty(PROPERTY_PROJECT, true);

        Boolean debug = false;
        
        if (strDebug.equals("true")) {
        	debug = true;
        	System.out.println("=====");
        	System.out.println("DEBUG: OBO Properties File : " + filename);
        	System.out.println("-----");
        	System.out.println("     : oboinfile           : " + strOboInFile);
        	System.out.println("     : obooutfile          : " + strOboOutFile);
        	System.out.println("     : obooutfileversion   : " + strOboOutFileVersion);
        	System.out.println("     : obooutfilenamespace : " + strOboOutFileNameSpace);
        	System.out.println("     : obooutfilesavedby   : " + strOboOutFileSavedBy);
        	System.out.println("     : obooutfileremark    : " + strOboOutFileRemark);
        	System.out.println("     : summaryreport       : " + strSummaryReport);
        	System.out.println("     : summaryreportpdf    : " + strSummaryReportPdf);
        	System.out.println("     : debug               : " + strDebug);
        	System.out.println("     : species             : " + strSpecies);
        	System.out.println("     : project             : " + strProject);
        	System.out.println("=====");
        }
        
        OBOFactory instance;

        File infile = new File (strOboInFile);
        
        // If driver is specified, then load it to let it register itself with DriverManager.
        if (infile.exists()) {
            instance = new FileOBOFactory(strOboInFile, 
            		strOboOutFile, 
            		strOboOutFileVersion, 
            		strOboOutFileNameSpace, 
            		strOboOutFileSavedBy, 
            		strOboOutFileRemark, 
            		debug, 
            		strSummaryReport, 
            		strSummaryReportPdf, 
            		strSpecies, 
            		strProject);
        }
        else {
            throw new OBOConfigurationException(
                    "OBO File '" + strOboInFile + "' does NOT exist!");
        }

        return instance;
    }

    /*
     * Returns a connection to the database. Package private so that it can be used inside the OBO
     * package only.
     */
    abstract ArrayList<OBOComponent> getComponents() throws OBOException, IOException;
    abstract String getComponentContent() throws OBOException, IOException;
    abstract Boolean writeComponents() throws OBOException;
    abstract Boolean isDebug();
    abstract String getSummaryReport();
    abstract String getSummaryReportPdf();
    abstract String getInputFile();
    abstract String getOutputFile();
    abstract String getOutputFileVersion();
    abstract String getOutputFileNameSpace();
    abstract String getOutputFileSavedBy();
    abstract String getOutputFileRemark();
    abstract String getSpecies();
    abstract String getProject();
    abstract void setComponents(ArrayList<OBOComponent> arrayobolist);
    abstract void setRelations(ArrayList<Relation> arrayrellist);
    abstract void addComponents(ArrayList<OBOComponent> arrayobolist);
    
    // OBO getters --------------------------------------------------------------------------------
    /*
     * Returns the ... OBO associated with the current OBOFactory.
     */
    public ComponentOBO getComponentOBO() {
        return new ComponentOBO(this);
    }

    // You can add more OBO getters here.
}

// Default OBOFactory implementations -------------------------------------------------------------
/*
 * The DriverManager based OBOFactory.
 */
class FileOBOFactory extends OBOFactory {
    
	private String oboInFile;
	private String oboOutFile;
	private String oboOutFileVersion;
	private String oboOutFileNameSpace;
	private String oboOutFileSavedBy;
	private String oboOutFileRemark;
    private Boolean debug;
	private String summaryReport;
	private String summaryReportPdf;
	private String species;
	private String project;
	
    private ArrayList<OBOComponent> obocomponentList;
    private ArrayList <Relation> oborelationList;

    FileOBOFactory(String oboInFile, 
    		String oboOutFile, 
    		String oboOutFileVersion, 
    		String oboOutFileNameSpace, 
    		String oboOutFileSavedBy, 
    		String oboOutFileRemark, 
    		Boolean debug, 
    		String summaryReport, 
    		String summaryReportPdf, 
    		String species, 
    		String project) {
    
    	this.oboInFile = oboInFile;
    	this.oboOutFile = oboOutFile;
    	this.oboOutFileVersion = oboOutFileVersion;
    	this.oboOutFileNameSpace = oboOutFileNameSpace;
    	this.oboOutFileSavedBy = oboOutFileSavedBy;
    	this.oboOutFileRemark = oboOutFileRemark;
        this.debug = debug;
        this.summaryReport = summaryReport;
        this.summaryReportPdf = summaryReportPdf;
        this.species = species;
        this.project = project;
    }

    ArrayList<OBOComponent> getComponents() throws OBOConfigurationException, IOException {
    	
    	ArrayList<OBOComponent> componentList = new ArrayList<OBOComponent>();
    	Parser parser = new Parser(this.oboInFile);
    	componentList = parser.getComponents();
    	
    	return componentList;
    }
    
    String getComponentContent() throws OBOConfigurationException, IOException {
    	
    	Parser parser = new Parser(this.oboInFile);
    	String componentContent = parser.getFileContent();
    	
    	return componentContent;
    }
    
    Boolean writeComponents() throws OBOConfigurationException {
    	
    	Producer producer = new Producer(this.oboOutFile, 
    			this.oboOutFileVersion,
    			this.oboOutFileNameSpace,
    			this.oboOutFileSavedBy,
    			this.oboOutFileRemark,
    			this.obocomponentList, 
    			this.oborelationList);
    	
    	Boolean isProcessed = producer.writeOboFile();
    	
    	return isProcessed;
    }
    
    Boolean isDebug() {
    	return debug;
    }
    String getSpecies() {
    	return species;
    }
    String getProject() {
    	return project;
    }
    String getSummaryReport() {
    	return summaryReport;
    }
    String getSummaryReportPdf() {
    	return summaryReportPdf;
    }
    String getInputFile() {
    	return oboInFile;
    }
    String getOutputFile() {
    	return oboOutFile;
    }
    String getOutputFileVersion() {
    	return oboOutFileVersion;
    }
    String getOutputFileNameSpace() {
    	return oboOutFileNameSpace;
    }
    String getOutputFileSavedBy() {
    	return oboOutFileSavedBy;
    }
    String getOutputFileRemark() {
    	return oboOutFileRemark;
    }
    void setComponents(ArrayList<OBOComponent> obocomponentList) {
    	this.obocomponentList = obocomponentList;
    }
    void setRelations(ArrayList<Relation> oborelationList) {
    	this.oborelationList = oborelationList;
    }
    void addComponents(ArrayList<OBOComponent> obocomponentList) {
    	this.obocomponentList.addAll(obocomponentList);
    }

}
