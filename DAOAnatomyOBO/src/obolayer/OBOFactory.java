/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import daolayer.DAOConfigurationException;

import obomodel.OBOComponent;
import obomodel.Relation;

import routines.base.Parser;
import routines.base.Producer;

import utility.ObjectConverter;
import utility.Wrapper;

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
    private static final String PROPERTY_ABSTRACT_CLASS_NAME = "abstractclassname";
    private static final String PROPERTY_ABSTRACT_CLASS_ID = "abstractclassid";
    private static final String PROPERTY_ABSTRACT_CLASS_NAMESPACE = "abstractclassnamespace";
    private static final String PROPERTY_STAGE_CLASS_NAME = "stageclassname";
    private static final String PROPERTY_STAGE_CLASS_ID = "stageclassid";
    private static final String PROPERTY_STAGE_CLASS_NAMESPACE = "stageclassnamespace";
    private static final String PROPERTY_GROUP_CLASS_NAME = "groupclassname";
    private static final String PROPERTY_GROUP_CLASS_ID = "groupclassid";
    private static final String PROPERTY_GROUP_CLASS_NAMESPACE = "groupclassnamespace";
    private static final String PROPERTY_GROUP_TERM_CLASS_NAME = "grouptermclassname";
    private static final String PROPERTY_GROUP_TERM_CLASS_ID = "grouptermclassid";
    private static final String PROPERTY_GROUP_TERM_CLASS_NAMESPACE = "grouptermclassnamespace";
    private static final String PROPERTY_MIN_STAGE_SEQUENCE = "minstagesequence";
    private static final String PROPERTY_MAX_STAGE_SEQUENCE = "maxstagesequence";
    private static final String PROPERTY_ALTERNATIVES = "alternatives";
    private static final String PROPERTY_TIMED_COMPONENTS = "timedcomponents";
    private static final String PROPERTY_MESSAGE_LEVEL = "msglevel";

    private static final Set<String> VALID_LEVELS = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"*****","****","***","**","*"}
            ));

    private static final Set<String> VALID_BOOLS = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"true","false"}
            ));

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a new OBOFactory instance for the given database name.
     */
    public static OBOFactory getInstance(String name) throws Exception {
    	
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
        String strAbstractClassName = properties.getProperty(PROPERTY_ABSTRACT_CLASS_NAME, true);
        String strAbstractClassId = properties.getProperty(PROPERTY_ABSTRACT_CLASS_ID, true);
        String strAbstractClassNamespace = properties.getProperty(PROPERTY_ABSTRACT_CLASS_NAMESPACE, true);
        String strStageClassName = properties.getProperty(PROPERTY_STAGE_CLASS_NAME, true);
        String strStageClassId = properties.getProperty(PROPERTY_STAGE_CLASS_ID, true);
        String strStageClassNamespace = properties.getProperty(PROPERTY_STAGE_CLASS_NAMESPACE, true);
        String strGroupClassName = properties.getProperty(PROPERTY_GROUP_CLASS_NAME, true);
        String strGroupClassId = properties.getProperty(PROPERTY_GROUP_CLASS_ID, true);
        String strGroupClassNamespace = properties.getProperty(PROPERTY_GROUP_CLASS_NAMESPACE, true);
        String strGroupTermClassName = properties.getProperty(PROPERTY_GROUP_TERM_CLASS_NAME, true);
        String strGroupTermClassId = properties.getProperty(PROPERTY_GROUP_TERM_CLASS_ID, true);
        String strGroupTermClassNamespace = properties.getProperty(PROPERTY_GROUP_TERM_CLASS_NAMESPACE, true);
        String strMinStageSequence = properties.getProperty(PROPERTY_MIN_STAGE_SEQUENCE, true);
        String strMaxStageSequence = properties.getProperty(PROPERTY_MAX_STAGE_SEQUENCE, true);
        String strAlternatives = properties.getProperty(PROPERTY_ALTERNATIVES, true);
        String strTimedComponents = properties.getProperty(PROPERTY_TIMED_COMPONENTS, true);
        String strMsgLevel = properties.getProperty(PROPERTY_MESSAGE_LEVEL, true);

        int intMinStageSequence = ObjectConverter.convert(strMinStageSequence, Integer.class);
        int intMaxStageSequence = ObjectConverter.convert(strMaxStageSequence, Integer.class);
        
        boolean boolDebug = false;
        boolean boolAlternatives = false;
        boolean boolTimedComponents = false;
        
        String level = "";

    	if ( !VALID_LEVELS.contains( strMsgLevel ) ) {
        	
            throw new DAOConfigurationException(
                    "Message Level '" + strMsgLevel + "' : INVALID Value!");
    	}
    	else {
    		
    		level = strMsgLevel;
    	}
    		
    	if ( !VALID_BOOLS.contains( strAlternatives ) ) {
        	
            throw new OBOConfigurationException(
                    "Alternatives '" + strAlternatives + "' : INVALID Value!");
    	}
    	else {
    		
            if (strAlternatives.equals("true")) {
            	
            	boolAlternatives = true;
            }
            
            if (strAlternatives.equals("false")) {
            	
            	boolAlternatives = false;
            }
    	}

    	if ( !VALID_BOOLS.contains( strTimedComponents ) ) {
        	
            throw new OBOConfigurationException(
                    "TimedComponents '" + strTimedComponents + "' : INVALID Value!");
    	}
    	else {
    		
            if (strTimedComponents.equals("true")) {
            	
            	boolAlternatives = true;
            }
            
            if (strTimedComponents.equals("false")) {
            	
            	boolAlternatives = false;
            }
    	}

    	if ( !VALID_BOOLS.contains( strDebug ) ) {
        	
            throw new OBOConfigurationException(
                    "Debug '" + strDebug + "' : INVALID Value!");
    	}
    	else {
    		
            if (strDebug.equals("true")) {
            	
            	boolDebug = true;
            }
            
            if (strDebug.equals("false")) {
            	
            	boolDebug = false;
            }
    	}

    	if (strDebug.equals("true")) {
        	
        	boolDebug = true;
        	//"========= : "
        	Wrapper.printMessage("========= :", "*", level);
        	Wrapper.printMessage("DEBUG     : OBO Properties File     : " + filename, "*", level);
        	Wrapper.printMessage("--------- :", "*", level);
        	Wrapper.printMessage("          : oboinfile               : " + strOboInFile, "*", level);
        	Wrapper.printMessage("          : obooutfile              : " + strOboOutFile, "*", level);
        	Wrapper.printMessage("          : obooutfileversion       : " + strOboOutFileVersion, "*", level);
        	Wrapper.printMessage("          : obooutfilenamespace     : " + strOboOutFileNameSpace, "*", level);
        	Wrapper.printMessage("          : obooutfilesavedby       : " + strOboOutFileSavedBy, "*", level);
        	Wrapper.printMessage("          : obooutfileremark        : " + strOboOutFileRemark, "*", level);
        	Wrapper.printMessage("          : summaryreport           : " + strSummaryReport, "*", level);
        	Wrapper.printMessage("          : summaryreportpdf        : " + strSummaryReportPdf, "*", level);
        	Wrapper.printMessage("          : debug                   : " + strDebug, "*", level);
        	Wrapper.printMessage("          : species                 : " + strSpecies, "*", level);
        	Wrapper.printMessage("          : project                 : " + strProject, "*", level);
        	Wrapper.printMessage("          : AbstractClassName       : " + strAbstractClassName, "*", level);
        	Wrapper.printMessage("          : AbstractClassId         : " + strAbstractClassId, "*", level);
        	Wrapper.printMessage("          : AbstractClassNamespace  : " + strAbstractClassNamespace, "*", level);
        	Wrapper.printMessage("          : StageClassName          : " + strStageClassName, "*", level);
        	Wrapper.printMessage("          : StageClassId            : " + strStageClassId, "*", level);
        	Wrapper.printMessage("          : StageClassNamespace     : " + strStageClassNamespace, "*", level);
        	Wrapper.printMessage("          : GroupClassName          : " + strGroupClassName, "*", level);
        	Wrapper.printMessage("          : GroupClassId            : " + strGroupClassId, "*", level);
        	Wrapper.printMessage("          : GroupClassNamespace     : " + strGroupClassNamespace, "*", level);
        	Wrapper.printMessage("          : GroupTermClassName      : " + strGroupTermClassName, "*", level);
        	Wrapper.printMessage("          : GroupTermClassId        : " + strGroupTermClassId, "*", level);
        	Wrapper.printMessage("          : GroupTermClassNamespace : " + strGroupTermClassNamespace, "*", level);
        	Wrapper.printMessage("          : MinStageSequence        : " + strMinStageSequence, "*", level);
        	Wrapper.printMessage("          : MaxStageSequence        : " + strMaxStageSequence, "*", level);
        	Wrapper.printMessage("          : Alternatives            : " + strAlternatives, "*", level);
        	Wrapper.printMessage("          : TimedComponents         : " + strTimedComponents, "*", level);
        	Wrapper.printMessage("          : msglevel                : " + strMsgLevel, "*", level);
        	Wrapper.printMessage("========= :", "*", level);
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
            		boolDebug, 
            		strSummaryReport, 
            		strSummaryReportPdf, 
            		strSpecies, 
            		strProject,
            		strAbstractClassName, 
            		strAbstractClassId, 
            		strAbstractClassNamespace, 
            		strStageClassName, 
            		strStageClassId, 
            		strStageClassNamespace, 
            		strGroupClassName, 
            		strGroupClassId, 
            		strGroupClassNamespace, 
            		strGroupTermClassName, 
            		strGroupTermClassId, 
            		strGroupTermClassNamespace,
            		intMinStageSequence,
            		intMaxStageSequence,
            		boolAlternatives,
            		boolTimedComponents,
            		strMsgLevel);
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
    abstract ArrayList<OBOComponent> getComponents() throws OBOException, IOException, Exception;
    abstract String getComponentContent() throws OBOException, IOException, Exception;
    abstract boolean writeComponents( String stage ) throws OBOException, Exception;
    abstract boolean isDebug();
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
    abstract String getAbstractClassName(); 
	abstract String getAbstractClassId(); 
	abstract String getAbstractClassNamespace(); 
	abstract String getStageClassName(); 
	abstract String getStageClassId(); 
	abstract String getStageClassNamespace(); 
	abstract String getGroupClassName(); 
	abstract String getGroupClassId(); 
	abstract String getGroupClassNamespace(); 
	abstract String getGroupTermClassName(); 
	abstract String getGroupTermClassId(); 
	abstract String getGroupTermClassNamespace();
	abstract int getMinStageSequence();
	abstract int getMaxStageSequence();
    abstract boolean isAlternatives();
    abstract boolean isTimedComponents();
	abstract void setComponents(ArrayList<OBOComponent> arrayobolist);
    abstract void setRelations(ArrayList<Relation> arrayrellist);
    abstract void addComponents(ArrayList<OBOComponent> arrayobolist);
	abstract String getMsgLevel();
    
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
    private boolean boolDebug;
	private String summaryReport;
	private String summaryReportPdf;
	private String species;
	private String project;
	private String abstractClassName;
	private String abstractClassId;
    private String abstractClassNamespace;
    private String stageClassName;
    private String stageClassId;
    private String stageClassNamespace;
    private String groupClassName;
    private String groupClassId;
    private String groupClassNamespace;
    private String groupTermClassName;
    private String groupTermClassId;
    private String groupTermClassNamespace;
    private int intMinStageSequence;
    private int intMaxStageSequence;
    private boolean boolAlternatives;
    private boolean boolTimedComponents;
    private String msgLevel;
	
    private ArrayList<OBOComponent> obocomponentList;
    private ArrayList <Relation> oborelationList;

    FileOBOFactory(String oboInFile, 
    		String oboOutFile, 
    		String oboOutFileVersion, 
    		String oboOutFileNameSpace, 
    		String oboOutFileSavedBy, 
    		String oboOutFileRemark, 
    		boolean boolDebug, 
    		String summaryReport, 
    		String summaryReportPdf, 
    		String species, 
    		String project,
    		String abstractClassName, 
    		String abstractClassId, 
    		String abstractClassNamespace, 
    		String stageClassName, 
    		String stageClassId, 
    		String stageClassNamespace, 
    		String groupClassName, 
    		String groupClassId, 
    		String groupClassNamespace, 
    		String groupTermClassName, 
    		String groupTermClassId, 
    		String groupTermClassNamespace,
    		int intMinStageSequence,
    		int intMaxStageSequence, 
    		boolean boolAlternatives,  
    		boolean boolTimedComponents,
    		String msgLevel) {
    
    	this.oboInFile = oboInFile;
    	this.oboOutFile = oboOutFile;
    	this.oboOutFileVersion = oboOutFileVersion;
    	this.oboOutFileNameSpace = oboOutFileNameSpace;
    	this.oboOutFileSavedBy = oboOutFileSavedBy;
    	this.oboOutFileRemark = oboOutFileRemark;
        this.boolDebug = boolDebug;
        this.summaryReport = summaryReport;
        this.summaryReportPdf = summaryReportPdf;
        this.species = species;
        this.project = project;
    	this.abstractClassName = abstractClassName;
		this.abstractClassId = abstractClassId;
	    this.abstractClassNamespace = abstractClassNamespace;
	    this.stageClassName = stageClassName;
	    this.stageClassId = stageClassId;
	    this.stageClassNamespace = stageClassNamespace;
	    this.groupClassName = groupClassName;
	    this.groupClassId = groupClassId;
	    this.groupClassNamespace = groupClassNamespace;
	    this.groupTermClassName = groupTermClassName;
	    this.groupTermClassId = groupTermClassId;
	    this.groupTermClassNamespace = groupTermClassNamespace;
	    this.intMinStageSequence = intMinStageSequence;
	    this.intMaxStageSequence = intMaxStageSequence;
        this.boolAlternatives = boolAlternatives;
        this.boolTimedComponents = boolTimedComponents;
        this.msgLevel = msgLevel;
    }

    ArrayList<OBOComponent> getComponents() throws Exception {
    	
    	ArrayList<OBOComponent> componentList = new ArrayList<OBOComponent>();
    	
    	Parser parser = new Parser(
    			this.msgLevel,
    			this.oboInFile,
    			this.boolAlternatives,
    			this.species);
    	
    	componentList = parser.getComponents();
    	
    	return componentList;
    }
    
    String getComponentContent() throws Exception {
    	
    	Parser parser = new Parser(
    			this.msgLevel,
    			this.oboInFile,
    			this.boolAlternatives,
    			this.species);
    	
    	String componentContent = parser.getFileContent();
    	
    	return componentContent;
    }
    
    boolean writeComponents( String stage ) throws Exception {
    	
    	Producer producer = new Producer(
    			this.msgLevel,
    			this.oboOutFile, 
    			this.oboOutFileVersion,
    			this.oboOutFileNameSpace,
    			this.oboOutFileSavedBy,
    			this.oboOutFileRemark,
    			this.obocomponentList, 
    			this.oborelationList,
    			this.boolAlternatives,
    			this.boolTimedComponents);
    	
    	boolean isProcessed = producer.writeOboFile( stage );
    	
    	return isProcessed;
    }
    
    boolean isDebug() {
    	return boolDebug;
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
    String getAbstractClassName(){
    	return abstractClassName;
    } 
    String getAbstractClassId(){
     	return abstractClassId;
    } 
    String getAbstractClassNamespace(){
    	return abstractClassNamespace;
    } 
    String getStageClassName(){    	
    	return stageClassName;
    } 
    String getStageClassId(){    	
    	return stageClassId;
    } 
    String getStageClassNamespace(){    	
    	return stageClassNamespace;
    } 
    String getGroupClassName(){    	
    	return groupClassName;
    } 
    String getGroupClassId(){    	
    	return groupClassId;
    } 
    String getGroupClassNamespace(){    	
    	return groupClassNamespace;
    } 
    String getGroupTermClassName(){    	
    	return groupTermClassName;
    } 
    String getGroupTermClassId(){    	
    	return groupTermClassId;
    } 
    String getGroupTermClassNamespace(){    	
    	return groupTermClassNamespace;
    }
    int getMinStageSequence(){    	
    	return intMinStageSequence;
    }
    int getMaxStageSequence(){    	
    	return intMaxStageSequence;
    }
    boolean isAlternatives() {
    	return boolAlternatives;
    }
    boolean isTimedComponents() {
    	return boolTimedComponents;
    }
    String getMsgLevel(){    	
    	return msgLevel;
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
