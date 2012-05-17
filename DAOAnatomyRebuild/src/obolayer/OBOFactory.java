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

import java.util.ArrayList;

import obomodel.OBOComponent;
import obomodel.Relation;

import utility.Parser;
import utility.Producer;

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

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a new OBOFactory instance for the given database name.
     */
    public static OBOFactory getInstance(String name) throws OBOConfigurationException {
    	
        if (name == null) {
            throw new OBOConfigurationException("OBO name is null.");
        }

        OBOProperties properties = new OBOProperties(name);
        
        String oboInFile = properties.getProperty(PROPERTY_OBO_IN_FILE, true);
        String oboOutFile = properties.getProperty(PROPERTY_OBO_OUT_FILE, false);
        String oboOutFileVersion = properties.getProperty(PROPERTY_OBO_OUT_FILE_VERSION, false);
        String oboOutFileNameSpace = properties.getProperty(PROPERTY_OBO_OUT_FILE_NAMESPACE, false);
        String oboOutFileSavedBy = properties.getProperty(PROPERTY_OBO_OUT_FILE_SAVED_BY, false);
        String oboOutFileRemark = properties.getProperty(PROPERTY_OBO_OUT_FILE_REMARK, false);
        String summaryReport = properties.getProperty(PROPERTY_SUMMARY_REPORT, false);
        String summaryReportPdf = properties.getProperty(PROPERTY_SUMMARY_REPORT_PDF, false);
        String strDebug = properties.getProperty(PROPERTY_DEBUG, true);

        Boolean debug = false;
        
        if (strDebug.equals("true")) {
        	debug = true;
        	System.out.println("====== ");
        	System.out.println("DEBUG: PROPERTY_OBO_IN_FILE            " + oboInFile);
        	System.out.println("DEBUG: PROPERTY_OBO_OUT_FILE           " + oboOutFile);
        	System.out.println("DEBUG: PROPERTY_OBO_OUT_FILE_VERSION   " + oboOutFileVersion);
        	System.out.println("DEBUG: PROPERTY_OBO_OUT_FILE_NAMESPACE " + oboOutFileNameSpace);
        	System.out.println("DEBUG: PROPERTY_OBO_OUT_FILE_SAVED_BY  " + oboOutFileSavedBy);
        	System.out.println("DEBUG: PROPERTY_OBO_OUT_FILE_REMARK    " + oboOutFileRemark);
        	System.out.println("DEBUG: PROPERTY_SUMMARY_REPORT         " + summaryReport);
        	System.out.println("DEBUG: PROPERTY_SUMMARY_REPORT_PDF     " + summaryReportPdf);
        	System.out.println("DEBUG: PROPERTY_DEBUG                  " + strDebug);
        	System.out.println("====== ");
        }
        
        OBOFactory instance;

        File infile = new File (oboInFile);
        //File outfile = new File (summaryReport);
        
        // If driver is specified, then load it to let it register itself with DriverManager.
        if (infile.exists()) {
            instance = new FileOBOFactory(oboInFile, 
            		oboOutFile, 
            		oboOutFileVersion, 
            		oboOutFileNameSpace, 
            		oboOutFileSavedBy, 
            		oboOutFileRemark, 
            		debug, 
            		summaryReport, 
            		summaryReportPdf);
        }
        else {
            throw new OBOConfigurationException(
                    "OBO File '" + oboInFile + "' does NOT exist!");
        }

        return instance;
    }

    /*
     * Returns a connection to the database. Package private so that it can be used inside the OBO
     * package only.
     */
    abstract ArrayList<OBOComponent> getComponents() throws OBOException;

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
	
    private ArrayList<OBOComponent> obocomponentList;
    private ArrayList <Relation> oborelationList;

	private ArrayList<OBOComponent> componentList;
	
    FileOBOFactory(String oboInFile, 
    		String oboOutFile, 
    		String oboOutFileVersion, 
    		String oboOutFileNameSpace, 
    		String oboOutFileSavedBy, 
    		String oboOutFileRemark, 
    		Boolean debug, 
    		String summaryReport, 
    		String summaryReportPdf) {
    
    	this.oboInFile = oboInFile;
    	this.oboOutFile = oboOutFile;
    	this.oboOutFileVersion = oboOutFileVersion;
    	this.oboOutFileNameSpace = oboOutFileNameSpace;
    	this.oboOutFileSavedBy = oboOutFileSavedBy;
    	this.oboOutFileRemark = oboOutFileRemark;
        this.debug = debug;
        this.summaryReport = summaryReport;
        this.summaryReportPdf = summaryReportPdf;
    }

    ArrayList<OBOComponent> getComponents() throws OBOConfigurationException {
    	
    	ArrayList<OBOComponent> componentList = new ArrayList();
    	Parser parser = new Parser(this.oboInFile);
    	componentList = parser.getComponents();
    	
    	return componentList;
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
