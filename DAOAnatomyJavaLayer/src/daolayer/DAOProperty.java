/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        DAOProperty.java
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
* Description:  This class represents a SQL Database Transfer Object for the 
*                DAOProperty Table - ANA_OBO_COMPONENT
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
package daolayer;

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

public class DAOProperty {
	
    // Constants ----------------------------------------------------------------------------------
    private static final String PROPERTIES_FILE = "dao.properties";

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

    private static final Set<String> VALID_METHODS = new HashSet<String>(Arrays.asList(
            new String[] 
        	    {"JDBC"}
            ));
    

    // Properties ---------------------------------------------------------------------------------
	private String majorKey;
	private String access;
	private boolean debug; 
	private String driver; 
	private String msglevel; 
	private String password; 
	private String sqloutput; 
	private boolean update;
	private String url;
	private String username;
	
    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public DAOProperty() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public DAOProperty(String majorKey, 
    		String access,
    		boolean debug, 
    		String driver, 
    		String msglevel, 
    		String password, 
    		String sqloutput, 
    		boolean update, 
    		String url, 
    		String username) {
    	
    	this.majorKey = majorKey;
    	this.access = access;
    	this.debug = debug; 
    	this.driver = driver;
    	this.msglevel = msglevel;
    	this.password = password;
    	this.sqloutput = sqloutput;
    	this.update = update;
    	this.url = url;
    	this.username = username;
    }
    
    public DAOProperty(String majorKey, 
    		String access,
    		String debug, 
    		String driver, 
    		String msglevel, 
    		String password, 
    		String sqloutput, 
    		String update, 
    		String url, 
    		String username) {
    	
    	this.majorKey = majorKey;
    	this.access = access;
    	if ( debug.toLowerCase().equals("true") ) {
    		this.debug = true;
        }
        else {
        	this.debug = false;
        }
    	this.driver = driver;
    	this.msglevel = msglevel;
    	this.password = password;
    	this.sqloutput = sqloutput;
    	if ( update.toLowerCase().equals("true") ) {
    		this.update = true;
        }
        else {
        	this.update = false;
        }
    	this.url = url;
    	this.username = username;
    }
    
    // Getters ------------------------------------------------------------------------------------
    public String getMajorKey() {
        return this.majorKey;
    }
    public String getAccess() {
        return this.access;
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
    public String getDriver() {
        return this.driver;
    }
    public String getMsglevel() {
        return this.msglevel;
    }
    public String getPassword() {
        return this.password;
    }
    public String getSqloutput() {
        return this.sqloutput;
    }
    public boolean isUpdate() {
        return this.update;
    }
    public String getUpdate() {
        if ( this.update ) {
        	return "true";
        }
        else {
        	return "false";
        }
    }
    public String getUrl() {
        return this.url;
    }
    public String getUsername() {
        return this.username;
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
    public void setAccess(String access) {

    	if ( !VALID_METHODS.contains( access ) ) {
        	
            throw new DAOPropertyConfigurationException(
            		"Access Method '" + access + "' : INVALID Value!");
    	}

    	if ( access.toLowerCase().equals("null") ) {

    		this.access = "";
    	}
    	else {
    		
    		this.access = access;
    	}
    }
    public void setDebug(String debug) {

    	if ( !VALID_BOOLS.contains( debug ) ) {
        	
            throw new DAOPropertyConfigurationException(
                    "Debug '" + debug + "' : INVALID Value!");
    	}
    	else  {
    		if (debug.equals("true")) {
            	
    			this.debug = true;
            }
            if (debug.equals("false")) {
            	
            	this.debug = true;
            }
    	}
    }
    public void setDebug(boolean debug) {

    	this.debug = true;
    }
    public void setDriver(String driver) {

    	if ( driver.toLowerCase().equals("null") ) {

    		this.driver = "";
    	}
    	else {
    		
    		this.driver = driver;
    	}
    }
    public void setMsglevel(String msglevel) {

    	if ( !VALID_LEVELS.contains( msglevel ) ) {
        	
            throw new DAOPropertyConfigurationException(
                    "Message Level '" + msglevel + "' : INVALID Value!");
    	}
    		
    	this.msglevel = msglevel;
    }
    public void setPassword(String password) {
 
    	if ( password.toLowerCase().equals("null") ) {

    		this.password = "";
    	}
    	else {
    		
    		this.password = password;
    	}
    }
    public void setSqloutput(String sqloutput) {

    	if ( sqloutput.toLowerCase().equals("null") ) {

    		this.sqloutput = "";
    	}
    	else {
    		
    		this.sqloutput = sqloutput;
    	}
    }
    public void setUpdate(String update) {

    	if ( !VALID_BOOLS.contains( update ) ) {
        	
            throw new DAOPropertyConfigurationException(
                    "Update '" + update + "' : INVALID Value!");
    	}
    	else  {
    		if (update.equals("true")) {
            	
    			this.update = true;
            }
            if (update.equals("false")) {
            	
            	this.update = true;
            }
    	}
    }
    public void setUpdate(boolean update) {

    	this.update = true;
    }
    public void setUrl(String url) {
    	
    	if ( url.toLowerCase().equals("null") ) {

    		this.url = "";
    	}
    	else {
    		
    		this.url = url;
    	}
    }
    public void setUsername(String username) {

    	if ( username.toLowerCase().equals("null") ) {

    		this.username = "";
    	}
    	else {
    		
    		this.username = username;
    	}
    }
    
    // Helpers ------------------------------------------------------------------------------------
	public boolean setDAOProperty(String filename, String majorKey) {
		
		boolean boolReturn = false;

		File file = new File(filename);
		
		if (file.exists()) {
	        
			DAOProperty daoproperty = readFile(filename, majorKey);
            
            if (daoproperty == null) {
            	
    			System.out.println("daoproperty NOT FOUND for majorKey = " + majorKey);
            }
            else {
            	
    			//System.out.println("daoproperty FOUND for majorKey = " + majorKey);
    			//System.out.println(daoproperty.toStringFile());
                writeProperty(PROPERTIES_FILE, daoproperty);
                
                boolReturn = true;
            }
		}
         
		return boolReturn;
	}
	
	public void writeFile(String filename, DAOProperty daoproperty) {
	    
		try {

			File file = new File(filename);
			if (file.exists()) {
		        
				file.delete();
			} 
			
			FileUtil.write(file, daoproperty.toStringFile(), true);
		} 
		catch (IOException io) {
			
			io.printStackTrace();
		} 
	}
	
	public void writeFile(String filename, ArrayList<DAOProperty> newDaoPropertyList) {
	    
		try {
        	File file = new File(filename);
			
        	if (file.exists()) {
		        
				file.delete();
			} 

			if ( !newDaoPropertyList.isEmpty() ) {

            	Iterator<DAOProperty> iteratorDaoPropertyList = newDaoPropertyList.iterator();
            	
                while ( iteratorDaoPropertyList.hasNext() ) {
                	
                	DAOProperty daoproperty = iteratorDaoPropertyList.next();
            
                	FileUtil.write(file, daoproperty.toStringFile(), true);
                }
            }
	 
		} 
		catch (IOException io) {
			
			io.printStackTrace();
		} 
	}
	
	public ArrayList<DAOProperty> readFile(String filename) {
	    
		ArrayList<DAOProperty> daopropertylist = new ArrayList<DAOProperty>();
		
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

                    	//System.out.println("record.substring(0, 3) = " + record.substring(0, 3));

                    	if (!record.equals("")) {
                    		
                        	if (!record.substring(0, 1).equals(" ") &&
                            		!record.substring(0, 3).equals("-- ") ) {
                            		
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
            		String access = "";
            		String debug = "";
            		String driver = "";
            		String msglevel = "";
            		String password = "";
            		String sqloutput = "";
            		String update = "";
            		String url = "";
            		String username = "";
        			
            		int count = 0;
            		
                	for (Map.Entry entry : treeMap.entrySet()) {
                    	
                    	String[] keyParts = (String[]) ((String) entry.getKey()).split("\\.");

            			String majorKeyPart = keyParts[0];
            			//System.out.println("majorKeyPart = " + majorKeyPart);
            			majorKey = majorKeyPart;
            		
            			String[] keyValues = (String[]) entry.getValue();
                    	
            			String minorKeyPart = keyValues[0];
            			//System.out.println("minorKeyPart = " + minorKeyPart);
            			String minorKeyValue = keyValues[1];
            			//System.out.println("minorKeyValue = " + minorKeyValue);
            			
            			if (minorKeyPart.equals("access")){
            				access = minorKeyValue;
            				count++;
            			}
            			if (minorKeyPart.equals("debug")){
            				debug = minorKeyValue;
            				count++;
            			}
            			if (minorKeyPart.equals("driver")){
            				driver = minorKeyValue;
            				count++;
            			}
            			if (minorKeyPart.equals("msglevel")){
            				msglevel = minorKeyValue;
            				count++;
            			}
            			if (minorKeyPart.equals("password")){
            				password = minorKeyValue;
            				count++;
            			}
            			if (minorKeyPart.equals("sqloutput")){
            				sqloutput = minorKeyValue;
            				count++;
            			}
            			if (minorKeyPart.equals("update")){
            				update = minorKeyValue;
            				count++;
            			}
            			if (minorKeyPart.equals("url")){
            				url = minorKeyValue;
            				count++;
            			}
            			if (minorKeyPart.equals("username")){
            				username = minorKeyValue;
            				count++;
            			}

            			if (!access.equals("") &&
            			 !debug.equals("") &&
            			 !driver.equals("") &&
            			 !msglevel.equals("") &&
            		     !password.equals("") &&
            			 !sqloutput.equals("") &&
            			 !update.equals("") &&
            			 !url.equals("") &&
            			 !username.equals("") &&
            			 count == 9) {
            				
                 			DAOProperty daoproperty = new DAOProperty(majorKey, access, debug, driver, msglevel, password, sqloutput, update, url, username);
                			
                			daopropertylist.add(daoproperty);
                			
                			majorKey = "";
                			access = "";
                			debug = "";
                			driver = "";
                			msglevel = "";
                			password = "";
                			sqloutput = "";
                			update = "";
                			url = "";
                			username = "";
                			
                			count = 0;
            			}
                	}
    			}
        	}
		        
		} 
		catch (IOException io) {
			
			io.printStackTrace();
		}
		
		return daopropertylist;
	}
	
	public DAOProperty readFile(String filename, String majorKey) {
	    
   		DAOProperty daoproperty = new DAOProperty();
   	 
		ArrayList<DAOProperty> daopropertylist = readFile(filename);

		Iterator<DAOProperty> iteratordaopropertylist = daopropertylist.iterator();
        
		while ( iteratordaopropertylist.hasNext() ) {

			daoproperty = iteratordaopropertylist.next();
        
			if (daoproperty.getMajorKey().equals(majorKey)) {
				
				return daoproperty;
			}
		}
		        
		return null;
	}
	
	public void writeProperty(String propertyfilename, DAOProperty daoproperty) {
	    
		Properties prop = new Properties();
		OutputStream output = null;
	 
		try {
	 
			output = new FileOutputStream(propertyfilename);
	 
			// set the properties value
			
			prop.setProperty(daoproperty.getMajorKey() + ".access", daoproperty.getAccess());	 
			prop.setProperty(daoproperty.getMajorKey() + ".debug", daoproperty.getDebug());
			prop.setProperty(daoproperty.getMajorKey() + ".driver", daoproperty.getDriver());
			prop.setProperty(daoproperty.getMajorKey() + ".msglevel", daoproperty.getMsglevel());
			prop.setProperty(daoproperty.getMajorKey() + ".password", daoproperty.getPassword());
			prop.setProperty(daoproperty.getMajorKey() + ".sqloutput", daoproperty.getSqloutput());
			prop.setProperty(daoproperty.getMajorKey() + ".update", daoproperty.getUpdate());
			prop.setProperty(daoproperty.getMajorKey() + ".url", daoproperty.getUrl());
			prop.setProperty(daoproperty.getMajorKey() + ".username", daoproperty.getUsername());
			
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
	
	public static DAOProperty findProperties(String propertyfilename, String majorKey) {
	    
		Properties prop = new Properties();
		InputStream input = null;
		
		DAOProperty daoproperty = new DAOProperty();
		
		try {

			input = new FileInputStream(propertyfilename);
			 
			// load a properties file
			prop.load(input);
			
			daoproperty.setMajorKey(majorKey);
			daoproperty.setAccess(prop.getProperty(majorKey + ".access"));
			daoproperty.setDebug(prop.getProperty(majorKey + ".debug"));
			daoproperty.setDriver(prop.getProperty(majorKey + ".driver"));
			daoproperty.setMsglevel(prop.getProperty(majorKey + ".msglevel"));
			daoproperty.setPassword(prop.getProperty(majorKey + ".password"));
			daoproperty.setSqloutput(prop.getProperty(majorKey + ".sqloutput"));
			daoproperty.setUpdate(prop.getProperty(majorKey + ".update"));
			daoproperty.setUrl(prop.getProperty(majorKey + ".url"));
			daoproperty.setUsername(prop.getProperty(majorKey + ".username"));
			
		} 
		catch (IOException io) {

			io.printStackTrace();
			return null;
		} 
		catch (DAOPropertyConfigurationException pce) {
			
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

		return daoproperty;

	}
		
	public void printMap(Map<String, String[]> map) {
		
		
		for (Map.Entry entry : map.entrySet()) {
			
			String[] keyParts = (String[]) ((String) entry.getKey()).split("\\.");
			
			String keyPart1 = keyParts[0];
			String keyPart2 = keyParts[1];
		
			keyParts = (String[]) entry.getValue();
			System.out.println(keyPart1 + "." + keyPart2 + " = " + keyParts[1]);
		}
	}

    /*
     * Is this DAOProperty the same as the Supplied DAOProperty?
     */
    public boolean isSameAs(DAOProperty daoproperty){

    	if ( this.getMajorKey().equals(daoproperty.getMajorKey()) && 
    			this.getAccess().equals(daoproperty.getAccess()) && 
    			this.getDebug() == daoproperty.getDebug() && 
    			this.getDriver().equals(daoproperty.getDriver()) && 
    			this.getMsglevel().equals(daoproperty.getMsglevel()) && 
    			this.getPassword().equals(daoproperty.getPassword()) && 
    			this.getSqloutput().equals(daoproperty.getSqloutput()) && 
    			this.getUpdate() == daoproperty.getUpdate() && 
    			this.getUrl().equals(daoproperty.getUrl()) && 
    			this.getUsername().equals(daoproperty.getUsername())) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The majorKey is unique for each DAOProperty.
     *  So this should compare DAOProperty by majorKey only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof DAOProperty) && (majorKey != null) 
        		? majorKey.equals(((DAOProperty) other).majorKey) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this DAOProperty.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("DAOProperty [ majorKey=%s, access=%s, debug=%b, driver=%s, msglevel=%s, password=%s, sqloutput=%s, update=%b, url=%s, username=%s ]", 
        		majorKey, access, debug, driver, msglevel, password, sqloutput, update, url, username
 );
    }

    /*
     * Returns the String representation of this DAOProperty.
     *  Not required, it just makes reading logs easier.
     */
    public String toStringFile() {
    	
        return String.format("%s.access=%s\n"
        		+ "%s.debug=%b\n"
        		+ "%s.driver=%s\n"
        		+ "%s.msglevel=%s\n"
        		+ "%s.password=%s\n"
        		+ "%s.sqloutput=%s\n"
        		+ "%s.update=%b\n"
        		+ "%s.url=%s\n"
        		+ "%s.username=%s", 
        		majorKey, access, 
        		majorKey, debug, 
        		majorKey, driver, 
        		majorKey, msglevel, 
        		majorKey, password, 
        		majorKey, sqloutput, 
        		majorKey, update, 
        		majorKey, url, 
        		majorKey, username );
    }
    /*
     * Returns the Java Object String representation of this DAOProperty.
     */
    public String toStringJava() {
    	
        return String.format("component%d = new DAOProperty( \"%s\", \"%s\", \"%s\", \"%b\", \"%s\", \"%s\", \"%s\", \"%s\", \"%b\", \"%s\", \"%s\" );", 
        		majorKey, majorKey, access, debug, driver, msglevel, password, sqloutput, update, url, username
 );
    }

}
