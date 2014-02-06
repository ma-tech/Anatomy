/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        OBOProperty.java
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
* Description:  Access to a Java Properties File for Anatomy OBO Data Access Objects
*                Writes the required set to a obo.properties file for further use
*                Input properties files can have multiple entries
*
* Link:         
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package obolayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import utility.FileUtil;

public class OBOProperty {
	
    // Constants ----------------------------------------------------------------------------------
    private static final String PROPERTIES_FILE = "obo.properties";

    private static final Set<String> VALID_LEVELS = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"*****",
            	"****",
            	"***",
            	"**",
            	"*"}
            ));
    
    private static final Set<String> VALID_BOOLS = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"true",
            	"false"}
            ));

    private static final Set<String> VALID_SPECIES = new HashSet<String>(Arrays.asList(
            new String[] 
            	    {"mouse",
                	"human",
                	"chick"}
            ));
    
    private static final Set<String> VALID_PROJECTS = new HashSet<String>(Arrays.asList(
            new String[] 
            	    {"GUDMAP",
                	"EMAP"}
            ));

    // Properties ---------------------------------------------------------------------------------
	private String majorKey;
    private String abstractclassid;
    private String abstractclassname;
    private String abstractclassnamespace;
    private boolean alternatives;
    private boolean debug;
    private boolean generateidentifiers;
    private String groupclassid;
    private String groupclassname;
    private String groupclassnamespace;
    private String grouptermclassid;
    private String grouptermclassname;
    private String grouptermclassnamespace;
    private String maxstagesequence;
    private String minstagesequence;
    private String msglevel;
    private String obobasefile;
    private String oboinfile;
    private String obooutfile;
    private String obooutfilenamespace;
    private String obooutfileremark;
    private String obooutfilesavedby;
    private String obooutfileversion;
    private String project;
    private String species;
    private String stageclassid;
    private String stageclassname;
    private String stageclassnamespace;
    private String summaryreport;
    private boolean timedcomponents;
	
    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public OBOProperty() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public OBOProperty(
    		String majorKey, 
    		String abstractclassid,
    		String abstractclassname,
    		String abstractclassnamespace,
    		boolean alternatives,
    		boolean debug,
    		boolean generateidentifiers,
    		String groupclassid,
    		String groupclassname,
    		String groupclassnamespace,
    		String grouptermclassid,
    		String grouptermclassname,
    		String grouptermclassnamespace,
    		String maxstagesequence,
    		String minstagesequence,
    		String msglevel,
    		String obobasefile,
    		String oboinfile,
    		String obooutfile,
    		String obooutfilenamespace,
    		String obooutfileremark,
    		String obooutfilesavedby,
    		String obooutfileversion,
    		String project,
    		String species,
    		String stageclassid,
    		String stageclassname,
    		String stageclassnamespace,
    		String summaryreport,
    		boolean timedcomponents) {
    	
    	this.majorKey = majorKey;
    	this.abstractclassid = abstractclassid;
    	this.abstractclassname = abstractclassname;
    	this.abstractclassnamespace = abstractclassnamespace;
    	this.alternatives = alternatives;
    	this.debug = debug;
    	this.generateidentifiers = generateidentifiers;
    	this.groupclassid = groupclassid;
    	this.groupclassname = groupclassname;
    	this.groupclassnamespace = groupclassnamespace;
    	this.grouptermclassid = grouptermclassid;
    	this.grouptermclassname = grouptermclassname;
    	this.grouptermclassnamespace = grouptermclassnamespace;
    	this.maxstagesequence = maxstagesequence;
    	this.minstagesequence = minstagesequence;
    	this.msglevel = msglevel;
    	this.obobasefile = obobasefile;
    	this.oboinfile = oboinfile;
    	this.obooutfile = obooutfile;
    	this.obooutfilenamespace = obooutfilenamespace;
    	this.obooutfileremark = obooutfileremark;
    	this.obooutfilesavedby = obooutfilesavedby;
    	this.obooutfileversion = obooutfileversion;
    	this.project = project;
    	this.species = species;
    	this.stageclassid = stageclassid;
    	this.stageclassname = stageclassname;
    	this.stageclassnamespace = stageclassnamespace;
    	this.summaryreport = summaryreport;
    	this.timedcomponents = timedcomponents;
    }
    
    public OBOProperty(
    		String majorKey, 
    		String abstractclassid,
    		String abstractclassname,
    		String abstractclassnamespace,
    		String alternatives,
    		String debug,
    		String generateidentifiers,
    		String groupclassid,
    		String groupclassname,
    		String groupclassnamespace,
    		String grouptermclassid,
    		String grouptermclassname,
    		String grouptermclassnamespace,
    		String maxstagesequence,
    		String minstagesequence,
    		String msglevel,
    		String obobasefile,
    		String oboinfile,
    		String obooutfile,
    		String obooutfilenamespace,
    		String obooutfileremark,
    		String obooutfilesavedby,
    		String obooutfileversion,
    		String project,
    		String species,
    		String stageclassid,
    		String stageclassname,
    		String stageclassnamespace,
    		String summaryreport,
    		String timedcomponents) {
    	
    	this.majorKey = majorKey;
    	this.abstractclassid = abstractclassid;
    	this.abstractclassname = abstractclassname;
    	this.abstractclassnamespace = abstractclassnamespace;
    	if ( alternatives.toLowerCase().equals("true") ) {
    		this.alternatives = true;
        }
        else {
        	this.alternatives = false;
        }
    	if ( debug.toLowerCase().equals("true") ) {
    		this.debug = true;
        }
        else {
        	this.debug = false;
        }
    	if ( generateidentifiers.toLowerCase().equals("true") ) {
    		this.generateidentifiers = true;
        }
        else {
        	this.generateidentifiers = false;
        }
    	this.groupclassid = groupclassid;
    	this.groupclassname = groupclassname;
    	this.groupclassnamespace = groupclassnamespace;
    	this.grouptermclassid = grouptermclassid;
    	this.grouptermclassname = grouptermclassname;
    	this.grouptermclassnamespace = grouptermclassnamespace;
    	this.maxstagesequence = maxstagesequence;
    	this.minstagesequence = minstagesequence;
    	this.msglevel = msglevel;
    	this.obobasefile = obobasefile;
    	this.oboinfile = oboinfile;
    	this.obooutfile = obooutfile;
    	this.obooutfilenamespace = obooutfilenamespace;
    	this.obooutfileremark = obooutfileremark;
    	this.obooutfilesavedby = obooutfilesavedby;
    	this.obooutfileversion = obooutfileversion;
    	this.project = project;
    	this.species = species;
    	this.stageclassid = stageclassid;
    	this.stageclassname = stageclassname;
    	this.stageclassnamespace = stageclassnamespace;
    	this.summaryreport = summaryreport;
    	if ( timedcomponents.toLowerCase().equals("true") ) {
    		this.timedcomponents = true;
        }
        else {
        	this.timedcomponents = false;
        }
    }
    
    // Getters ------------------------------------------------------------------------------------
    public String getMajorKey() {
        return this.majorKey;
    }
    public String getAbstractClassId() {
        return this.abstractclassid;
    }
    public String getAbstractClassName() {
        return this.abstractclassname;
    }
    public String getAbstractClassNameSpace() {
        return this.abstractclassnamespace;
    }
    public boolean isAlternatives() {
        return this.alternatives;
    }
    public String getAlternatives() {
        if ( this.alternatives ) {
        	return "true";
        }
        else {
        	return "false";
        }
    }
    public boolean isDebug() {
        return this.debug;
    }
    public String getDebug() {
        if ( this.debug ) {
        	return "true";
        }
        else {
        	return "false";
        }
    }
    public boolean isGenerateIdentifiers() {
        return this.generateidentifiers;
    }
    public String getGenerateIdentifiers() {
        if ( this.generateidentifiers ) {
        	return "true";
        }
        else {
        	return "false";
        }
    }
    public String getGroupClassId() {
        return this.groupclassid;
    }
    public String getGroupClassName() {
        return this.groupclassname;
    }
    public String getGroupClassNameSpace() {
        return this.groupclassnamespace;
    }
    public String getGroupTermClassId() {
        return this.grouptermclassid;
    }
    public String getGroupTermClassName() {
        return this.grouptermclassname;
    }
    public String getGroupTermClassNameSpace() {
        return this.grouptermclassnamespace;
    }
    public String getMaxStageSequence() {
        return this.maxstagesequence;
    }
    public String getMinStageSequence() {
        return this.minstagesequence;
    }
    public String getMsgLevel() {
        return this.msglevel;
    }
    public String getOboBaseFile() {
        return this.obobasefile;
    }
    public String getOboInFile() {
        return this.oboinfile;
    }
    public String getOboOutFile() {
        return this.obooutfile;
    }
    public String getOboOutFileNameSpace() {
        return this.obooutfilenamespace;
    }
    public String getOboOutFileRemark() {
        return this.obooutfileremark;
    }
    public String getOboOutFileSavedBy() {
        return this.obooutfilesavedby;
    }
    public String getOboOutFileVersion() {
        return this.obooutfileversion;
    }
    public String getProject() {
        return this.project;
    }
    public String getSpecies() {
        return this.species;
    }
    public String getStageClassId() {
        return this.stageclassid;
    }
    public String getStageClassName() {
        return this.stageclassname;
    }
    public String getStageClassNameSpace() {
        return this.stageclassnamespace;
    }
    public String getSummaryReport() {
        return this.summaryreport;
    }
    public boolean isTimedComponents() {
        return this.timedcomponents;
    }
    public String getTimedComponents() {
        if ( this.timedcomponents ) {
        	return "true";
        }
        else {
        	return "false";
        }
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setMajorKey(String majorKey) {

    	if ( majorKey.toLowerCase().equals("null") ) {

    		this.majorKey = "";
    	}
    	else {
    		
            this.majorKey = majorKey;
    	}
    }
    public void setAbstractClassId(String abstractclassid) {

    	if ( abstractclassid.toLowerCase().equals("null") ) {

    		this.abstractclassid = "";
    	}
    	else {
    		
            this.abstractclassid = abstractclassid;
    	}
    }
    public void setAbstractClassName(String abstractclassname) {

    	if ( abstractclassname.toLowerCase().equals("null") ) {

    		this.abstractclassname = "";
    	}
    	else {
    		
            this.abstractclassname = abstractclassname;
    	}
    }
    public void setAbstractClassNameSpace(String abstractclassnamespace) {

    	if ( abstractclassnamespace.toLowerCase().equals("null") ) {

    		this.abstractclassnamespace = "";
    	}
    	else {
    		
            this.abstractclassnamespace = abstractclassnamespace;
    	}
    }
    public void setAlternatives(String alternatives) {

    	if ( !VALID_BOOLS.contains( alternatives ) ) {
        	
            throw new OBOPropertyConfigurationException(
                    "Alternatives '" + alternatives + "' : INVALID Value!");
    	}
    	else  {
    		if (alternatives.equals("true")) {
            	
    			this.alternatives = true;
            }
            if (alternatives.equals("false")) {
            	
            	this.alternatives = false;
            }
    	}
    }
    public void setAlternatives(boolean alternatives) {

    	this.alternatives = alternatives;
    }
    public void setDebug(String debug) {

    	if ( !VALID_BOOLS.contains( debug ) ) {
        	
            throw new OBOPropertyConfigurationException(
                    "Debug '" + debug + "' : INVALID Value!");
    	}
    	else  {
    		if (debug.equals("true")) {
            	
    			this.debug = true;
            }
            if (debug.equals("false")) {
            	
            	this.debug = false;
            }
    	}
    }
    public void setDebug(boolean debug) {

    	this.debug = debug;
    }

    public void setGenerateIdentifiers(String generateidentifiers) {

    	if ( !VALID_BOOLS.contains( generateidentifiers ) ) {
        	
            throw new OBOPropertyConfigurationException(
                    "GenerateIdentifiers '" + generateidentifiers + "' : INVALID Value!");
    	}
    	else  {
    		if (generateidentifiers.equals("true")) {
            	
    			this.generateidentifiers = true;
            }
            if (generateidentifiers.equals("false")) {
            	
            	this.generateidentifiers = false;
            }
    	}
    }
    public void setGenerateIdentifiers(boolean generateidentifiers) {

    	this.generateidentifiers = generateidentifiers;
    }
    public void setGroupClassId(String groupclassid) {

    	if ( groupclassid.toLowerCase().equals("null") ) {

    		this.groupclassid = "";
    	}
    	else {
    		
            this.groupclassid = groupclassid;
    	}
    }
    public void setGroupClassName(String groupclassname) {

    	if ( groupclassname.toLowerCase().equals("null") ) {

    		this.groupclassname = "";
    	}
    	else {
    		
            this.groupclassname = groupclassname;
    	}
    }
    public void setGroupClassNameSpace(String groupclassnamespace) {

    	if ( groupclassnamespace.toLowerCase().equals("null") ) {

    		this.groupclassnamespace = "";
    	}
    	else {
    		
            this.groupclassnamespace = groupclassnamespace;
    	}
    }
    public void setGroupTermClassId(String grouptermclassid) {

    	if ( grouptermclassid.toLowerCase().equals("null") ) {

    		this.grouptermclassid = "";
    	}
    	else {
    		
            this.grouptermclassid = grouptermclassid;
    	}
    }
    public void setGroupTermClassName(String grouptermclassname) {

    	if ( grouptermclassname.toLowerCase().equals("null") ) {

    		this.grouptermclassname = "";
    	}
    	else {
    		
            this.grouptermclassname = grouptermclassname;
    	}
    }
    public void setGrouptTermClassNameSpace(String grouptermclassnamespace) {

    	if ( grouptermclassnamespace.toLowerCase().equals("null") ) {

    		this.grouptermclassnamespace = "";
    	}
    	else {
    		
            this.grouptermclassnamespace = grouptermclassnamespace;
    	}
    }
    public void setMaxStageSequence(String maxstagesequence) {

    	if ( maxstagesequence.toLowerCase().equals("null") ) {

    		this.maxstagesequence = "";
    	}
    	else {
    		
            this.maxstagesequence = maxstagesequence;
    	}
    }
    public void setMinStageSequence(String minstagesequence) {

    	if ( minstagesequence.toLowerCase().equals("null") ) {

    		this.minstagesequence = "";
    	}
    	else {
    		
            this.minstagesequence = minstagesequence;
    	}
    }
    public void setMsglevel(String msglevel) {

    	if ( !VALID_LEVELS.contains( msglevel ) ) {
        	
            throw new OBOPropertyConfigurationException(
                    "Message Level '" + msglevel + "' : INVALID Value!");
    	}
    		
    	this.msglevel = msglevel;
    }
    public void setOboBaseFile(String obobasefile) {

    	if ( obobasefile.toLowerCase().equals("null") ) {

    		this.obobasefile = "";
    	}
    	else {
    		
            this.obobasefile = obobasefile;
    	}
    }
    public void setOboInFile(String oboinfile) {

    	if ( oboinfile.toLowerCase().equals("null") ) {

    		this.oboinfile = "";
    	}
    	else {
    		
            this.oboinfile = oboinfile;
    	}
    }
    public void setOboOutFile(String obooutfile) {

    	if ( obooutfile.toLowerCase().equals("null") ) {

    		this.obooutfile = "";
    	}
    	else {
    		
            this.obooutfile = obooutfile;
    	}
    }
    public void setOboOutFileNameSpace(String obooutfilenamespace) {

    	if ( obooutfilenamespace.toLowerCase().equals("null") ) {

    		this.obooutfilenamespace = "";
    	}
    	else {
    		
            this.obooutfilenamespace = obooutfilenamespace;
    	}
    }
    public void setOboOutFileRemark(String obooutfileremark) {

    	if ( obooutfileremark.toLowerCase().equals("null") ) {

    		this.obooutfileremark = "";
    	}
    	else {
    		
            this.obooutfileremark = obooutfileremark;
    	}
    }
    public void setOboOutFileSavedBy(String obooutfilesavedby) {

    	if ( obooutfilesavedby.toLowerCase().equals("null") ) {

    		this.obooutfilesavedby = "";
    	}
    	else {
    		
            this.obooutfilesavedby = obooutfilesavedby;
    	}
    }
    public void setOboOutFileVersion(String obooutfileversion) {

    	if ( obooutfileversion.toLowerCase().equals("null") ) {

    		this.obooutfileversion = "";
    	}
    	else {
    		
            this.obooutfileversion = obooutfileversion;
    	}
    }
    public void setProject(String project) {

    	if ( !VALID_PROJECTS.contains( project ) ) {
        	
            throw new OBOPropertyConfigurationException(
                    "Project '" + project + "' : INVALID Value!");
    	}
    	else {
    		
            this.project = project;
    	}
    }
    public void setSpecies(String species) {

    	if ( !VALID_SPECIES.contains( species ) ) {
        	
            throw new OBOPropertyConfigurationException(
                    "Species '" + species + "' : INVALID Value!");
    	}
    	else {
    		
            this.species = species;
    	}
    }
    public void setStageClassId(String stageclassid) {

    	if ( stageclassid.toLowerCase().equals("null") ) {

    		this.stageclassid = "";
    	}
    	else {
    		
            this.stageclassid = stageclassid;
    	}
    }
    public void setStageClassName(String stageclassname) {

    	if ( stageclassname.toLowerCase().equals("null") ) {

    		this.stageclassname = "";
    	}
    	else {
    		
            this.stageclassname = stageclassname;
    	}
    }
    public void setStageClassNameSpace(String stageclassnamespace) {

    	if ( stageclassnamespace.toLowerCase().equals("null") ) {

    		this.stageclassnamespace = "";
    	}
    	else {
    		
            this.stageclassnamespace = stageclassnamespace;
    	}
    }
    public void setSummaryReport(String summaryreport) {

    	if ( summaryreport.toLowerCase().equals("null") ) {

    		this.summaryreport = "";
    	}
    	else {
    		
            this.summaryreport = summaryreport;
    	}
    }
    public void setTimedComponents(String timedcomponents) {

    	if ( !VALID_BOOLS.contains( timedcomponents ) ) {
        	
            throw new OBOPropertyConfigurationException(
                    "TimedComponents '" + timedcomponents + "' : INVALID Value!");
    	}
    	else  {
    		if (timedcomponents.equals("true")) {
            	
    			this.timedcomponents = true;
            }
            if (timedcomponents.equals("false")) {
            	
            	this.timedcomponents = false;
            }
    	}
    }
    public void setTimedComponents(boolean timedcomponents) {

    	this.timedcomponents = timedcomponents;
    }

    
    // Helpers ------------------------------------------------------------------------------------
	public boolean setOBOProperty(String filename, String majorKey) {
		
		boolean boolReturn = false;

		//System.out.println("filename = " + filename);

		File file = new File(filename);
		
		if (file.exists()) {
	        
			OBOProperty oboproperty = readFile(filename, majorKey);
            
			
            if (oboproperty == null) {
            	
    			System.out.println("oboproperty NOT FOUND for majorKey = " + majorKey);
            }
            else {
            	
    			//System.out.println("oboproperty FOUND for majorKey = " + majorKey);
    			//System.out.println(oboproperty.toStringFile());
                writeProperty(PROPERTIES_FILE, oboproperty);
                
                boolReturn = true;
            }
		}
         
		return boolReturn;
	}
	
	public void writeFile(String filename, OBOProperty oboproperty) {
	    
		try {

			File file = new File(filename);
			if (file.exists()) {
		        
				file.delete();
			} 
			
			FileUtil.write(file, oboproperty.toStringFile(), true);
		} 
		catch (IOException io) {
			
			io.printStackTrace();
		} 
	}
	
	public void writeFile(String filename, ArrayList<OBOProperty> newDaoPropertyList) {
	    
		try {
        	File file = new File(filename);
			
        	if (file.exists()) {
		        
				file.delete();
			} 

			if ( !newDaoPropertyList.isEmpty() ) {

            	Iterator<OBOProperty> iteratorDaoPropertyList = newDaoPropertyList.iterator();
            	
                while ( iteratorDaoPropertyList.hasNext() ) {
                	
                	OBOProperty oboproperty = iteratorDaoPropertyList.next();
            
                	FileUtil.write(file, oboproperty.toStringFile(), true);
                }
            }
	 
		} 
		catch (IOException io) {
			
			io.printStackTrace();
		} 
	}
	
	public ArrayList<OBOProperty> readFile(String filename) {
	    
		ArrayList<OBOProperty> obopropertylist = new ArrayList<OBOProperty>();
		
		Map<String, String[]> unsortMap = new HashMap<String, String[]>();
		
		try {
        
			File file = new File(filename);
			
        	if (file.exists()) {
        		
        		ArrayList<String> fileArrayList = (ArrayList<String>) FileUtil.readRecords(file);
        		
        		if ( !fileArrayList.isEmpty() ) {

                	Iterator<String> iteratorFileArrayList = fileArrayList.iterator();
                	
                    while ( iteratorFileArrayList.hasNext() ) {
                    	
                    	String record = iteratorFileArrayList.next();
                    	
                    	//System.out.println("record = " + record + "*");

                    	if (!record.equals("")) {
                    		
                        	if (!record.substring(0, 1).equals(" ") &&
                            		!record.substring(0, 3).equals("-- ") ) {

                        		//System.out.println("record.substring(0, 3) = " + record.substring(0, 3));
                    			
                        		String[] keyParts = record.split("\\.");
                        		String majorKey = keyParts[0];
                        		String rest = keyParts[1];
                                
                        		//System.out.println("keyParts.length = " + keyParts.length);
                        		//System.out.println("rest = " + rest);
                                
                        		if ( keyParts.length > 2 ){
                                
                        			for (int i = 2; i < keyParts.length; i++) {
                                	
                        				rest = rest + "." + keyParts[i];
                        			}
                        		}
                                
                        		//System.out.println("rest = " + rest);
                                
                        		String[] valueParts = new String[2];
                        		valueParts = rest.split("=");
                        		String minorKey = valueParts[0];
                        		String value = valueParts[1];
                                
                        		//System.out.println("majorKey = " + majorKey);
                        		//System.out.println("minorKey = " + minorKey);
                        		//System.out.println("value = " + value);
                    			
                        		unsortMap.put(majorKey + "." + minorKey, valueParts);
                    			
                        		//System.out.println("majorKey : " + majorKey + ", value : " + value);
                        	}
                    	}
                    }
                    
                    Map<String, String[]> treeMap = new TreeMap<String, String[]>(unsortMap);
                    
        			//printMap(treeMap);
        			
        			//System.out.println("unsortMap.size() : " + unsortMap.size());
        			//System.out.println("treeMap.size() : " + treeMap.size());
        			
        			String majorKey = "";
        		    String abstractclassid = "";
        		    String abstractclassname = "";
        		    String abstractclassnamespace = "";
        		    String alternatives = "";
        		    String debug = "";
        		    String generateidentifiers = "";
        		    String groupclassid = "";
        		    String groupclassname = "";
        		    String groupclassnamespace = "";
        		    String grouptermclassid = "";
        		    String grouptermclassname = "";
        		    String grouptermclassnamespace = "";
        		    String maxstagesequence = "";
        		    String minstagesequence = "";
        		    String msglevel = "";
        		    String obobasefile = "";
        		    String oboinfile = "";
        		    String obooutfile = "";
        		    String obooutfilenamespace = "";
        		    String obooutfileremark = "";
        		    String obooutfilesavedby = "";
        		    String obooutfileversion = "";
        		    String project = "";
        		    String species = "";
        		    String stageclassid = "";
        		    String stageclassname = "";
        		    String stageclassnamespace = "";
        		    String summaryreport = "";
        		    String timedcomponents = "";
        		    
        		    int count = 0;
        			
                	for (Map.Entry entry : treeMap.entrySet()) {
                    	
                    	String[] keyParts = (String[]) ((String) entry.getKey()).split("\\.");

            			String majorKeyPart = keyParts[0];
            			majorKey = majorKeyPart;
            		
            			String[] keyValues = (String[]) entry.getValue();
                    	
            			String minorKeyPart = keyValues[0];
            			String minorKeyValue = keyValues[1];
            			
            			if (minorKeyPart.trim().equals("abstractclassid")){
            				abstractclassid = minorKeyValue;
            				count++;
            				////System.out.println("HERE 1");
            			}
            			if (minorKeyPart.equals("abstractclassname")){
            				abstractclassname = minorKeyValue;
            				count++;
            				////System.out.println("HERE 2");
            			}
            			if (minorKeyPart.equals("abstractclassnamespace")){
            				abstractclassnamespace = minorKeyValue;
            				count++;
            				////System.out.println("HERE 3");
            			}
            			if (minorKeyPart.equals("alternatives")){
            				alternatives = minorKeyValue;
            				count++;
            				////System.out.println("HERE 4");
            			}
            			if (minorKeyPart.equals("debug")){
            				debug = minorKeyValue;
            				count++;
            				////System.out.println("HERE 5");
            			}
            			if (minorKeyPart.equals("generateidentifiers")){
            				generateidentifiers = minorKeyValue;
            				count++;
            				//System.out.println("HERE 6");
            			}
            			if (minorKeyPart.equals("groupclassid")){
            				groupclassid = minorKeyValue;
            				count++;
            				//System.out.println("HERE 7");
            			}
            			if (minorKeyPart.equals("groupclassname")){
            				groupclassname = minorKeyValue;
            				count++;
            				//System.out.println("HERE 8");
            			}
            			if (minorKeyPart.equals("groupclassnamespace")){
            				groupclassnamespace = minorKeyValue;
            				count++;
            				//System.out.println("HERE 9");
            			}
            			if (minorKeyPart.equals("grouptermclassid")){
            				grouptermclassid = minorKeyValue;
            				count++;
            				//System.out.println("HERE 10");
            			}
            			if (minorKeyPart.equals("grouptermclassname")){
            				grouptermclassname = minorKeyValue;
            				count++;
            				//System.out.println("HERE 11");
            			}
            			if (minorKeyPart.equals("grouptermclassnamespace")){
            				grouptermclassnamespace = minorKeyValue;
            				count++;
            				//System.out.println("HERE 12");
            			}
            			if (minorKeyPart.equals("maxstagesequence")){
            				maxstagesequence = minorKeyValue;
            				count++;
            				//System.out.println("HERE 13");
            			}
            			if (minorKeyPart.equals("minstagesequence")){
            				minstagesequence = minorKeyValue;
            				count++;
            				//System.out.println("HERE 14");
            			}
            			if (minorKeyPart.equals("msglevel")){
            				msglevel = minorKeyValue;
            				count++;
            				//System.out.println("HERE 15");
            			}
            			if (minorKeyPart.equals("obobasefile")){
            				obobasefile = minorKeyValue;
            				count++;
            				//System.out.println("HERE 16");
            			}
            			if (minorKeyPart.equals("oboinfile")){
            				oboinfile = minorKeyValue;
            				count++;
            				/*
            				System.out.println("HERE 17");
            				System.out.println("oboinfile = " + oboinfile);
                			System.out.println("majorKeyPart = " + majorKeyPart);
                			System.out.println("minorKeyPart = " + minorKeyPart);
                			System.out.println("minorKeyValue = " + minorKeyValue);
                			*/
            			}
            			if (minorKeyPart.equals("obooutfile")){
            				obooutfile = minorKeyValue;
            				count++;
            				//System.out.println("HERE 18");
            			}
            			if (minorKeyPart.equals("obooutfilenamespace")){
            				obooutfilenamespace = minorKeyValue;
            				count++;
            				//System.out.println("HERE 19");
            			}
            			if (minorKeyPart.equals("obooutfileremark")){
            				obooutfileremark = minorKeyValue;
            				count++;
            				//System.out.println("HERE 20");
            			}
            			if (minorKeyPart.equals("obooutfilesavedby")){
            				obooutfilesavedby = minorKeyValue;
            				count++;
            				//System.out.println("HERE 21");
            			}
            			if (minorKeyPart.equals("obooutfileversion")){
            				obooutfileversion = minorKeyValue;
            				count++;
            				//System.out.println("HERE 22");
            			}
            			if (minorKeyPart.equals("project")){
            				project = minorKeyValue;
            				count++;
            				//System.out.println("HERE 23");
            			}
            			if (minorKeyPart.equals("species")){
            				species = minorKeyValue;
            				count++;
            				//System.out.println("HERE 24");
            			}
            			if (minorKeyPart.equals("stageclassid")){
            				stageclassid = minorKeyValue;
            				count++;
            				//System.out.println("HERE 25");
            			}
            			if (minorKeyPart.equals("stageclassname")){
            				stageclassname = minorKeyValue;
            				count++;
            				//System.out.println("HERE 26");
            			}
            			if (minorKeyPart.equals("stageclassnamespace")){
            				stageclassnamespace = minorKeyValue;
            				count++;
            				//System.out.println("HERE 27");
            			}
            			if (minorKeyPart.equals("summaryreport")){
            				summaryreport = minorKeyValue;
            				count++;
            				//System.out.println("HERE 28");
            			}
            			if (minorKeyPart.equals("timedcomponents")){
            				timedcomponents = minorKeyValue;
            				count++;
            				//System.out.println("HERE 29");
            			}
            			
            			if (!abstractclassid.equals("") && 
            			!abstractclassname.equals("") && 
            			!abstractclassnamespace.equals("") && 
            			!alternatives.equals("") && 
            			!debug.equals("") && 
            			!generateidentifiers.equals("") && 
            			!groupclassid.equals("") && 
            			!groupclassname.equals("") && 
            			!groupclassnamespace.equals("") && 
            			!grouptermclassid.equals("") && 
            			!grouptermclassname.equals("") && 
            			!grouptermclassnamespace.equals("") && 
            			!maxstagesequence.equals("") && 
            			!minstagesequence.equals("") && 
            			!msglevel.equals("") && 
            			!obobasefile.equals("") && 
            			!oboinfile.equals("") && 
            			!obooutfile.equals("") && 
            			!obooutfilenamespace.equals("") && 
            			!obooutfileremark.equals("") && 
            			!obooutfilesavedby.equals("") && 
            			!obooutfileversion.equals("") && 
            			!project.equals("") && 
            			!species.equals("") && 
            			!stageclassid.equals("") && 
            			!stageclassname.equals("") && 
            			!stageclassnamespace.equals("") && 
            			!summaryreport.equals("") && 
            			!timedcomponents.equals("") &&
            			count == 29) {        
            				
                 			OBOProperty oboproperty = new OBOProperty(
                 					majorKey, 
                 					abstractclassid,
                 		    		abstractclassname,
                 		    		abstractclassnamespace,
                 		    		alternatives,
                 		    		debug,
                 		    		generateidentifiers,
                 		    		groupclassid,
                 		    		groupclassname,
                 		    		groupclassnamespace,
                 		    		grouptermclassid,
                 		    		grouptermclassname,
                 		    		grouptermclassnamespace,
                 		    		maxstagesequence,
                 		    		minstagesequence,
                 		    		msglevel,
                 		    		obobasefile,
                 		    		oboinfile,
                 		    		obooutfile,
                 		    		obooutfilenamespace,
                 		    		obooutfileremark,
                 		    		obooutfilesavedby,
                 		    		obooutfileversion,
                 		    		project,
                 		    		species,
                 		    		stageclassid,
                 		    		stageclassname,
                 		    		stageclassnamespace,
                 		    		summaryreport,
                 		    		timedcomponents);

                 		    //System.out.println("oboproperty.oboproperty.toStringFile() =\n" + oboproperty.toStringFile());
                			
                			obopropertylist.add(oboproperty);
                			
                			majorKey = "";
                		    abstractclassid = "";
                		    abstractclassname = "";
                		    abstractclassnamespace = "";
                		    alternatives = "";
                		    debug = "";
                		    generateidentifiers = "";
                		    groupclassid = "";
                		    groupclassname = "";
                		    groupclassnamespace = "";
                		    grouptermclassid = "";
                		    grouptermclassname = "";
                		    grouptermclassnamespace = "";
                		    maxstagesequence = "";
                		    minstagesequence = "";
                		    msglevel = "";
                		    obobasefile = "";
                		    oboinfile = "";
                		    obooutfile = "";
                		    obooutfilenamespace = "";
                		    obooutfileremark = "";
                		    obooutfilesavedby = "";
                		    obooutfileversion = "";
                		    project = "";
                		    species = "";
                		    stageclassid = "";
                		    stageclassname = "";
                		    stageclassnamespace = "";
                		    summaryreport = "";
                		    timedcomponents = "";
                		    
                		    count = 0;
            			}
                	}
    			}
        	}
		        
		} 
		catch (IOException io) {
			
			io.printStackTrace();
		}
		
		return obopropertylist;
	}
	
	public OBOProperty readFile(String filename, String majorKey) {
	    
   		OBOProperty oboproperty = new OBOProperty();
   	 
		ArrayList<OBOProperty> obopropertylist = readFile(filename);

		Iterator<OBOProperty> iteratorobopropertylist = obopropertylist.iterator();
        
		while ( iteratorobopropertylist.hasNext() ) {

			oboproperty = iteratorobopropertylist.next();
        
			if (oboproperty.getMajorKey().equals(majorKey)) {
				
				return oboproperty;
			}
		}
		        
		return null;
	}
	
	public void writeProperty(String propertyfilename, OBOProperty oboproperty) {
	    
		Properties prop = new Properties();
		OutputStream output = null;
	 
		try {
	 
			output = new FileOutputStream(propertyfilename);
	 
			// set the properties value
			
			prop.setProperty(oboproperty.getMajorKey() + ".abstractclassid", oboproperty.getAbstractClassId());	 
			prop.setProperty(oboproperty.getMajorKey() + ".abstractclassname", oboproperty.getAbstractClassName());
			prop.setProperty(oboproperty.getMajorKey() + ".abstractclassnamespace", oboproperty.getAbstractClassNameSpace());
			prop.setProperty(oboproperty.getMajorKey() + ".alternatives", oboproperty.getAlternatives());
			prop.setProperty(oboproperty.getMajorKey() + ".debug", oboproperty.getDebug());
			prop.setProperty(oboproperty.getMajorKey() + ".generateidentifiers", oboproperty.getGenerateIdentifiers());
			prop.setProperty(oboproperty.getMajorKey() + ".groupclassid", oboproperty.getGroupClassId());
			prop.setProperty(oboproperty.getMajorKey() + ".groupclassname", oboproperty.getGroupClassName());
			prop.setProperty(oboproperty.getMajorKey() + ".groupclassnamespace", oboproperty.getGroupClassNameSpace());
			prop.setProperty(oboproperty.getMajorKey() + ".grouptermclassid", oboproperty.getGroupTermClassId());	 
			prop.setProperty(oboproperty.getMajorKey() + ".grouptermclassname", oboproperty.getGroupTermClassName());
			prop.setProperty(oboproperty.getMajorKey() + ".grouptermclassnamespace", oboproperty.getGroupTermClassNameSpace());
			prop.setProperty(oboproperty.getMajorKey() + ".maxstagesequence", oboproperty.getMaxStageSequence());
			prop.setProperty(oboproperty.getMajorKey() + ".minstagesequence", oboproperty.getMinStageSequence());
			prop.setProperty(oboproperty.getMajorKey() + ".msglevel", oboproperty.getMsgLevel());
			prop.setProperty(oboproperty.getMajorKey() + ".obobasefile", oboproperty.getOboBaseFile());
			prop.setProperty(oboproperty.getMajorKey() + ".oboinfile", oboproperty.getOboInFile());
			prop.setProperty(oboproperty.getMajorKey() + ".obooutfile", oboproperty.getOboOutFile());
			prop.setProperty(oboproperty.getMajorKey() + ".obooutfilenamespace", oboproperty.getOboOutFileNameSpace());	 
			prop.setProperty(oboproperty.getMajorKey() + ".obooutfileremark", oboproperty.getOboOutFileRemark());
			prop.setProperty(oboproperty.getMajorKey() + ".obooutfilesavedby", oboproperty.getOboOutFileSavedBy());
			prop.setProperty(oboproperty.getMajorKey() + ".obooutfileversion", oboproperty.getOboOutFileVersion());
			prop.setProperty(oboproperty.getMajorKey() + ".project", oboproperty.getProject());
			prop.setProperty(oboproperty.getMajorKey() + ".species", oboproperty.getSpecies());
			prop.setProperty(oboproperty.getMajorKey() + ".stageclassid", oboproperty.getStageClassId());
			prop.setProperty(oboproperty.getMajorKey() + ".stageclassname", oboproperty.getStageClassName());
			prop.setProperty(oboproperty.getMajorKey() + ".stageclassnamespace", oboproperty.getStageClassNameSpace());
			prop.setProperty(oboproperty.getMajorKey() + ".summaryreport", oboproperty.getSummaryReport());
			prop.setProperty(oboproperty.getMajorKey() + ".timedcomponents", oboproperty.getTimedComponents());
			
			// save properties to project root folder
			prop.store(output, null);
			
		} 
		catch (IOException io) {
			
			io.printStackTrace();
		} 
		finally {
			if (output != null) {
				try {
					output.close();
				} 
				catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	public static OBOProperty findProperties(String propertyfilename, String majorKey) {
	    
		Properties prop = new Properties();
		InputStream input = null;
		
		OBOProperty oboproperty = new OBOProperty();
		
		try {

			//System.out.println("propertyfilename = " + propertyfilename);
			input = new FileInputStream(propertyfilename);
			 
			// load a properties file
			prop.load(input);
			
			oboproperty.setMajorKey(majorKey);
			oboproperty.setAbstractClassId(prop.getProperty(majorKey + ".abstractclassid"));
			oboproperty.setAbstractClassName(prop.getProperty(majorKey + ".abstractclassname"));
			oboproperty.setAbstractClassNameSpace(prop.getProperty(majorKey + ".abstractclassnamespace"));
			oboproperty.setAlternatives(prop.getProperty(majorKey + ".alternatives"));
			oboproperty.setDebug(prop.getProperty(majorKey + ".debug"));
			oboproperty.setGenerateIdentifiers(prop.getProperty(majorKey + ".generateidentifiers"));
			oboproperty.setGroupClassId(prop.getProperty(majorKey + ".groupclassid"));
			oboproperty.setGroupClassName(prop.getProperty(majorKey + ".groupclassname"));
			oboproperty.setGroupClassNameSpace(prop.getProperty(majorKey + ".groupclassnamespace"));
			oboproperty.setGroupTermClassId(prop.getProperty(majorKey + ".grouptermclassid"));
			oboproperty.setGroupTermClassName(prop.getProperty(majorKey + ".grouptermclassname"));
			oboproperty.setGrouptTermClassNameSpace(prop.getProperty(majorKey + ".grouptermclassnamespace"));
			oboproperty.setMaxStageSequence(prop.getProperty(majorKey + ".maxstagesequence"));
			oboproperty.setMinStageSequence(prop.getProperty(majorKey + ".minstagesequence"));
			oboproperty.setMsglevel(prop.getProperty(majorKey + ".msglevel"));
			oboproperty.setOboBaseFile(prop.getProperty(majorKey + ".obobasefile"));
			oboproperty.setOboInFile(prop.getProperty(majorKey + ".oboinfile"));
			oboproperty.setOboOutFile(prop.getProperty(majorKey + ".obooutfile"));
			oboproperty.setOboOutFileNameSpace(prop.getProperty(majorKey + ".obooutfilenamespace"));
			oboproperty.setOboOutFileRemark(prop.getProperty(majorKey + ".obooutfileremark"));
			oboproperty.setOboOutFileSavedBy(prop.getProperty(majorKey + ".obooutfilesavedby"));
			oboproperty.setOboOutFileVersion(prop.getProperty(majorKey + ".obooutfileversion"));
			oboproperty.setProject(prop.getProperty(majorKey + ".project"));
			oboproperty.setSpecies(prop.getProperty(majorKey + ".species"));
			oboproperty.setStageClassId(prop.getProperty(majorKey + ".stageclassid"));
			oboproperty.setStageClassName(prop.getProperty(majorKey + ".stageclassname"));
			oboproperty.setStageClassNameSpace(prop.getProperty(majorKey + ".stageclassnamespace"));
			oboproperty.setSummaryReport(prop.getProperty(majorKey + ".summaryreport"));
			oboproperty.setTimedComponents(prop.getProperty(majorKey + ".timedcomponents"));
		} 
		catch (IOException io) {

			io.printStackTrace();
			return null;
		} 
		catch (OBOPropertyConfigurationException pce) {
			
			//pce.printStackTrace();
			return null;
		}
		finally {
			if (input != null) {
				try {
					
					input.close();
				} 
				catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}

		return oboproperty;

	}
		
	public void printMap(Map<String, String[]> map) {
		
		
		for (Map.Entry entry : map.entrySet()) {
			
			String[] keyParts = (String[]) ((String) entry.getKey()).split("\\.");
			
			String keyPart1 = keyParts[0];
			String keyPart2 = keyParts[1];
		
			keyParts = (String[]) entry.getValue();
			//System.out.println(keyPart1 + "." + keyPart2 + " = " + keyParts[1]);
		}
	}

    /*
     * Is this OBOProperty the same as the Supplied OBOProperty?
     */
    public boolean isSameAs(OBOProperty oboproperty){

    	if ( this.getMajorKey().equals(oboproperty.getMajorKey()) && 
    		    this.getAbstractClassId().equals(oboproperty.getAbstractClassId()) && 
    		    this.getAbstractClassName().equals(oboproperty.getAbstractClassName()) && 
    		    this.getAbstractClassNameSpace().equals(oboproperty.getAbstractClassNameSpace()) && 
    		    this.getAlternatives().equals(oboproperty.getAlternatives()) && 
    		    this.getDebug().equals(oboproperty.getDebug()) && 
    		    this.getGenerateIdentifiers().equals(oboproperty.getGenerateIdentifiers()) && 
    		    this.getGroupClassId().equals(oboproperty.getGroupClassId()) && 
    		    this.getGroupClassName().equals(oboproperty.getGroupClassName()) && 
    		    this.getGroupClassNameSpace().equals(oboproperty.getGroupClassNameSpace()) && 
    		    this.getGroupTermClassId().equals(oboproperty.getGroupTermClassId()) && 
    		    this.getGroupTermClassName().equals(oboproperty.getGroupTermClassName()) && 
    		    this.getGroupTermClassNameSpace().equals(oboproperty.getGroupTermClassNameSpace()) && 
    		    this.getMaxStageSequence().equals(oboproperty.getMaxStageSequence()) && 
    		    this.getMinStageSequence().equals(oboproperty.getMinStageSequence()) && 
    		    this.getMsgLevel().equals(oboproperty.getMsgLevel()) && 
    		    this.getOboBaseFile().equals(oboproperty.getOboBaseFile()) && 
    		    this.getOboInFile().equals(oboproperty.getOboInFile()) && 
    		    this.getOboOutFile().equals(oboproperty.getOboOutFile()) && 
    		    this.getOboOutFileNameSpace().equals(oboproperty.getOboOutFileNameSpace()) && 
    		    this.getOboOutFileRemark().equals(oboproperty.getOboOutFileRemark()) && 
    		    this.getOboOutFileSavedBy().equals(oboproperty.getOboOutFileSavedBy()) && 
    		    this.getOboOutFileVersion().equals(oboproperty.getOboOutFileVersion()) && 
    		    this.getProject().equals(oboproperty.getProject()) && 
    		    this.getSpecies().equals(oboproperty.getSpecies()) && 
    		    this.getStageClassId().equals(oboproperty.getStageClassId()) && 
    		    this.getStageClassName().equals(oboproperty.getStageClassName()) && 
    		    this.getStageClassNameSpace().equals(oboproperty.getStageClassNameSpace()) && 
    		    this.getSummaryReport().equals(oboproperty.getSummaryReport()) && 
    		    this.getTimedComponents().equals(oboproperty.getTimedComponents()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The majorKey is unique for each OBOProperty.
     *  So this should compare OBOProperty by majorKey only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof OBOProperty) && (majorKey != null) 
        		? majorKey.equals(((OBOProperty) other).majorKey) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this OBOProperty.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("OBOProperty [ majorKey=%s, " +
        		"abstractclassid=%s, " +
        		"abstractclassname=%s, " +
        		"abstractclassnamespace=%s, " +
        		"alternatives=%b, " +
        		"debug=%b, " +
        		"generateidentifiers=%b, " +
        		"groupclassid=%s, " +
        		"groupclassname=%s, " +
        		"groupclassnamespace=%s " +
        		"grouptermclassid=%s, " +
        		"grouptermclassname=%s, " +
        		"grouptermclassnamespace=%s, " +
        		"maxstagesequence=%s, " +
        		"minstagesequence=%s, " +
        		"msglevel=%s, " +
        		"obobasefile=%s, " +
        		"oboinfile=%s, " +
        		"obooutfile=%s " +
        		"obooutfilenamespace=%s, " +
        		"obooutfileremark=%s, " +
        		"obooutfilesavedby=%s, " +
        		"obooutfileversion=%s, " +
        		"project=%s, " +
        		"species=%s, " +
        		"stageclassid=%s, " +
        		"stageclassname=%s, " +
        		"stageclassnamespace=%s " +
        		"summaryreport=%s, " +
        		"timedcomponents=%b ]",
        		majorKey, 
        		abstractclassid, 
        		abstractclassname, 
        		abstractclassnamespace, 
        		alternatives, 
        		debug, 
        		generateidentifiers, 
        		groupclassid, 
        		groupclassname, 
        		groupclassnamespace,
        		grouptermclassid, 
        		grouptermclassname, 
        		grouptermclassnamespace, 
        		maxstagesequence, 
        		minstagesequence, 
        		msglevel, 
        		obobasefile, 
        		oboinfile, 
        		obooutfile,
        		obooutfilenamespace, 
        		obooutfileremark, 
        		obooutfilesavedby, 
        		obooutfileversion, 
        		project, 
        		species, 
        		stageclassid, 
        		stageclassname, 
        		stageclassnamespace,
        		summaryreport, 
        		timedcomponents);
    }

    /*
     * Returns the String representation of this OBOProperty.
     *  Not required, it just makes reading logs easier.
     */
    public String toStringFile() {
    	
        return String.format("%s.abstractclassid=%s\n" +
        		"%s.abstractclassname=%s\n" +
        		"%s.abstractclassnamespace=%s\n" +
        		"%s.alternatives=%b\n" +
        		"%s.debug=%b\n" +
        		"%s.generateidentifiers=%b\n" +
        		"%s.groupclassid=%s\n" +
        		"%s.groupclassname=%s\n" +
        		"%s.groupclassnamespace=%s\n" +
        		"%s.grouptermclassid=%s\n" +
        		"%s.grouptermclassname=%s\n" +
        		"%s.grouptermclassnamespace=%s\n" +
        		"%s.maxstagesequence=%s\n" +
        		"%s.minstagesequence=%s\n" +
        		"%s.msglevel=%s\n" +
        		"%s.obobasefile=%s\n" +
        		"%s.oboinfile=%s\n" +
        		"%s.obooutfile=%s\n" +
        		"%s.obooutfilenamespace=%s\n" +
        		"%s.obooutfileremark=%s\n" +
        		"%s.obooutfilesavedby=%s\n" +
        		"%s.obooutfileversion=%s\n" +
        		"%s.project=%s\n" +
        		"%s.species=%s\n" +
        		"%s.stageclassid=%s\n" +
        		"%s.stageclassname=%s\n" +
        		"%s.stageclassnamespace=%s\n" +
        		"%s.summaryreport=%s\n" +
        		"%s.timedcomponents=%b",
        		majorKey, abstractclassid, 
        		majorKey, abstractclassname, 
        		majorKey, abstractclassnamespace, 
        		majorKey, alternatives, 
        		majorKey, debug, 
        		majorKey, generateidentifiers, 
        		majorKey, groupclassid, 
        		majorKey, groupclassname, 
        		majorKey, groupclassnamespace,
        		majorKey, grouptermclassid, 
        		majorKey, grouptermclassname, 
        		majorKey, grouptermclassnamespace, 
        		majorKey, maxstagesequence, 
        		majorKey, minstagesequence, 
        		majorKey, msglevel, 
        		majorKey, obobasefile, 
        		majorKey, oboinfile, 
        		majorKey, obooutfile,
        		majorKey, obooutfilenamespace, 
        		majorKey, obooutfileremark, 
        		majorKey, obooutfilesavedby, 
        		majorKey, obooutfileversion, 
        		majorKey, project, 
        		majorKey, species, 
        		majorKey, stageclassid, 
        		majorKey, stageclassname, 
        		majorKey, stageclassnamespace,
        		majorKey, summaryreport, 
        		majorKey, timedcomponents);
    }
}