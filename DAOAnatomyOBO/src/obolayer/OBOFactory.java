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
* Version:      1
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
* See 
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

import obolayer.OBOConfigurationException;
import obolayer.OBOFactory;
import obolayer.OBOProperty;
import obomodel.OBOComponent;
import obomodel.OBORelation;
import oboroutines.Parser;
import oboroutines.Producer;
import utility.ObjectConverter;
import utility.Wrapper;


public abstract class OBOFactory {
    // Constants ----------------------------------------------------------------------------------
    private static final String PROPERTIES_FILE = "obo.properties";

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns a new OBOFactory instance for the given database name.
     */
    public static OBOFactory getInstance(String name) throws Exception {
    	
        OBOFactory instance = null;

        if (name == null) {
        	
            throw new OBOConfigurationException("OBO name is null.");
        }

        OBOProperty oboproperty = OBOProperty.findProperties(PROPERTIES_FILE, name);

        String strFilename = PROPERTIES_FILE;

        String strMajorKey = oboproperty.getMajorKey();

        String strAbstractClassId = oboproperty.getAbstractClassId();
        String strAbstractClassName = oboproperty.getAbstractClassName();
        String strAbstractClassNamespace = oboproperty.getAbstractClassNameSpace();
        String strAlternatives = oboproperty.getAlternatives();
        String strDebug = oboproperty.getDebug();
        String strGenerateIdentifiers = oboproperty.getGenerateIdentifiers();
        String strGroupClassId = oboproperty.getGroupClassId();
        String strGroupClassName = oboproperty.getGroupClassName();
        String strGroupClassNamespace = oboproperty.getGroupClassNameSpace();
        String strGroupTermClassId = oboproperty.getGroupTermClassId();
        String strGroupTermClassName = oboproperty.getGroupTermClassName();
        String strGroupTermClassNamespace = oboproperty.getGroupTermClassNameSpace();
        String strMaxStageSequence = oboproperty.getMaxStageSequence();
        String strMinStageSequence = oboproperty.getMinStageSequence();
        String strMsgLevel = oboproperty.getMsgLevel();
        String strOboBaseFile = oboproperty.getOboBaseFile();
        String strOboInFile = oboproperty.getOboInFile();
        String strOboOutFileName = oboproperty.getOboOutFile();
        String strOboOutFileNameSpace = oboproperty.getOboOutFileNameSpace();
        String strOboOutFileRemark = oboproperty.getOboOutFileRemark();
        String strOboOutFileSavedBy = oboproperty.getOboOutFileSavedBy();
        String strOboOutFileVersion = oboproperty.getOboOutFileVersion();
        String strProject = oboproperty.getProject();
        String strSpecies = oboproperty.getSpecies();
        String strStageClassId = oboproperty.getStageClassId();
        String strStageClassName = oboproperty.getStageClassName();
        String strStageClassNamespace = oboproperty.getStageClassNameSpace();
        String strSummaryReport = oboproperty.getSummaryReport();
        String strTimedComponents = oboproperty.getTimedComponents();

        String strOboOutFileDateTime = utility.FileNamingDateTime.now();

        String strSummaryReportPdf = strSummaryReport;
        
        strSummaryReport = strSummaryReport + "/" + strOboOutFileDateTime + "_SummaryReport.txt";
        strSummaryReportPdf = strSummaryReportPdf + "/" + strOboOutFileDateTime + "_SummaryReport.pdf";
        
        int intMinStageSequence = ObjectConverter.convert(strMinStageSequence, Integer.class);
        int intMaxStageSequence = ObjectConverter.convert(strMaxStageSequence, Integer.class);
        
        boolean boolAlternatives = oboproperty.isAlternatives();
        boolean boolDebug = oboproperty.isDebug();
        boolean boolGenerateIdentifiers = oboproperty.isGenerateIdentifiers();
        boolean boolTimedComponents = oboproperty.isTimedComponents();
        
    	if (oboproperty.isDebug()) {
        	
        	Wrapper.printMessage("=====     :", "*", strMsgLevel);
        	Wrapper.printMessage("DEBUG     : OBO Properties File     : " + strFilename, "*", strMsgLevel);
        	Wrapper.printMessage("-----     : -------------------", "*", strMsgLevel);
        	Wrapper.printMessage("          : MajorKey                : " + strMajorKey, "*", strMsgLevel);
        	Wrapper.printMessage("          : --------                :", "*", strMsgLevel);
        	Wrapper.printMessage("          : abstractclassid         : " + strAbstractClassId, "*", strMsgLevel);
        	Wrapper.printMessage("          : abstractclassname       : " + strAbstractClassName, "*", strMsgLevel);
        	Wrapper.printMessage("          : abstractclassnamespace  : " + strAbstractClassNamespace, "*", strMsgLevel);
        	Wrapper.printMessage("          : alternatives            : " + strAlternatives, "*", strMsgLevel);
        	Wrapper.printMessage("          : debug                   : " + strDebug, "*", strMsgLevel);
        	Wrapper.printMessage("          : generateidentifiers     : " + strGenerateIdentifiers, "*", strMsgLevel);
        	Wrapper.printMessage("          : groupclassid            : " + strGroupClassId, "*", strMsgLevel);
        	Wrapper.printMessage("          : groupclassname          : " + strGroupClassName, "*", strMsgLevel);
        	Wrapper.printMessage("          : groupclassnamespace     : " + strGroupClassNamespace, "*", strMsgLevel);
        	Wrapper.printMessage("          : grouptermclassid        : " + strGroupTermClassId, "*", strMsgLevel);
        	Wrapper.printMessage("          : grouptermclassname      : " + strGroupTermClassName, "*", strMsgLevel);
        	Wrapper.printMessage("          : grouptermclassnamespace : " + strGroupTermClassNamespace, "*", strMsgLevel);
        	Wrapper.printMessage("          : maxstagesequence        : " + strMaxStageSequence, "*", strMsgLevel);
        	Wrapper.printMessage("          : minstagesequence        : " + strMinStageSequence, "*", strMsgLevel);
        	Wrapper.printMessage("          : msglevel                : " + strMsgLevel, "*", strMsgLevel);
        	Wrapper.printMessage("          : obobasefile             : " + strOboBaseFile, "*", strMsgLevel);
        	Wrapper.printMessage("          : oboinfile               : " + strOboInFile, "*", strMsgLevel);
        	Wrapper.printMessage("          : obooutfile              : " + strOboOutFileName, "*", strMsgLevel);
        	Wrapper.printMessage("          : obooutfilenamespace     : " + strOboOutFileNameSpace, "*", strMsgLevel);
        	Wrapper.printMessage("          : obooutfileremark        : " + strOboOutFileRemark, "*", strMsgLevel);
        	Wrapper.printMessage("          : obooutfilesavedby       : " + strOboOutFileSavedBy, "*", strMsgLevel);
        	Wrapper.printMessage("          : obooutfileversion       : " + strOboOutFileVersion, "*", strMsgLevel);
        	Wrapper.printMessage("          : project                 : " + strProject, "*", strMsgLevel);
        	Wrapper.printMessage("          : species                 : " + strSpecies, "*", strMsgLevel);
        	Wrapper.printMessage("          : stageclassid            : " + strStageClassId, "*", strMsgLevel);
        	Wrapper.printMessage("          : stageclassname          : " + strStageClassName, "*", strMsgLevel);
        	Wrapper.printMessage("          : stageclassnamespace     : " + strStageClassNamespace, "*", strMsgLevel);
        	Wrapper.printMessage("          : summaryreport           : " + strSummaryReport, "*", strMsgLevel);
        	Wrapper.printMessage("          : summaryreportpdf        : " + strSummaryReportPdf, "*", strMsgLevel);
        	Wrapper.printMessage("          : timedcomponents         : " + strTimedComponents, "*", strMsgLevel);
        	Wrapper.printMessage("========= :", "*", strMsgLevel);
        }
        
        File infile = new File (strOboInFile);
        
        // If driver is specified, then load it to let it register itself with DriverManager.
        if (infile.exists()) {
        	
            instance = new FileOBOFactory(
            		strOboBaseFile, 
            		strOboInFile,
            		strOboOutFileName, 
            		strOboOutFileDateTime, 
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
            		boolGenerateIdentifiers,
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
    abstract ArrayList<OBOComponent> getInputComponents() throws OBOException, IOException, Exception;
    abstract String getInputComponentContent() throws OBOException, IOException, Exception;
    abstract ArrayList<OBOComponent> getBaseComponents() throws OBOException, IOException, Exception;
    abstract String getBaseComponentContent() throws OBOException, IOException, Exception;
    abstract boolean writeComponents( String stage ) throws OBOException, Exception;
    abstract boolean isDebug();
    abstract String getSummaryReport();
    abstract String getSummaryReportPdf();
    abstract String getBaseFile();
    abstract String getInputFile();
    abstract String getOutputFileName();
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
    abstract boolean isGenerateIdentifiers();
    abstract void setComponents(ArrayList<OBOComponent> arrayobolist);
    abstract void setOBORelations(ArrayList<OBORelation> arrayrellist);
    abstract void addComponents(ArrayList<OBOComponent> arrayobolist);
	public abstract String getMsgLevel();
    
    // OBO getters --------------------------------------------------------------------------------
    /*
     * Returns the ... OBO associated with the current OBOFactory.
     */
    public OBOComponentAccess getOBOComponentAccess() {
    	
        return new OBOComponentAccess(this);
    }

    // You can add more OBO getters here.
}

// Default OBOFactory implementations -------------------------------------------------------------
/*
 * The DriverManager based OBOFactory.
 */
class FileOBOFactory extends OBOFactory {
    
	private String oboBaseFile;
	private String oboInFile;
	private String oboOutFileName;
	private String oboOutFileDateTime;
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
    private boolean boolGenerateIdentifiers;
    private String msgLevel;
	
    private ArrayList<OBOComponent> obocomponentList;
    private ArrayList <OBORelation> oborelationList;

    FileOBOFactory(
    		String oboBaseFile, 
    		String oboInFile,
    		String oboOutFileName, 
    		String oboOutFileDateTime, 
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
    		boolean boolGenerateIdentifiers,
    		String msgLevel) {
    
    	this.oboBaseFile = oboBaseFile;
    	this.oboInFile = oboInFile;
    	this.oboOutFileName = oboOutFileName;
    	this.oboOutFileDateTime = oboOutFileDateTime;
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
        this.boolGenerateIdentifiers = boolGenerateIdentifiers;
        this.msgLevel = msgLevel;
    }

    ArrayList<OBOComponent> getInputComponents() throws Exception {
    	
    	ArrayList<OBOComponent> componentList = new ArrayList<OBOComponent>();
    	
    	Parser parser = new Parser(
    			this.msgLevel,
    			this.oboInFile,
    			this.boolAlternatives,
    			this.species);
    	
    	componentList = parser.getComponents();
    	
    	return componentList;
    }
    
    ArrayList<OBOComponent> getBaseComponents() throws Exception {
    	
    	ArrayList<OBOComponent> componentList = new ArrayList<OBOComponent>();
    	
    	Parser parser = new Parser(
    			this.msgLevel,
    			this.oboBaseFile,
    			this.boolAlternatives,
    			this.species);
    	
    	componentList = parser.getComponents();
    	
    	return componentList;
    }
    
    String getInputComponentContent() throws Exception {
    	
    	Parser parser = new Parser(
    			this.msgLevel,
    			this.oboInFile,
    			this.boolAlternatives,
    			this.species);
    	
    	String componentContent = parser.getFileContent();
    	
    	return componentContent;
    }
    
    String getBaseComponentContent() throws Exception {
    	
    	Parser parser = new Parser(
    			this.msgLevel,
    			this.oboBaseFile,
    			this.boolAlternatives,
    			this.species);
    	
    	String componentContent = parser.getFileContent();
    	
    	return componentContent;
    }
    
    boolean writeComponents( String stage ) throws Exception {
    	
    	Producer producer = new Producer(
    			this.msgLevel,
    			this.oboOutFileName, 
    			this.oboOutFileDateTime,
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
    String getBaseFile() {
    	return oboBaseFile;
    }
    String getInputFile() {
    	return oboInFile;
    }
    String getOutputFileName() {
    	return oboOutFileName;
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
    boolean isGenerateIdentifiers() {
    	return boolGenerateIdentifiers;
    }
    public String getMsgLevel(){    	
    	return msgLevel;
    }
    
    void setComponents(ArrayList<OBOComponent> obocomponentList) {
    	this.obocomponentList = obocomponentList;
    }
    void setOBORelations(ArrayList<OBORelation> oborelationList) {
    	this.oborelationList = oborelationList;
    }
    void addComponents(ArrayList<OBOComponent> obocomponentList) {
    	this.obocomponentList.addAll(obocomponentList);
    }
}
