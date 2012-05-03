/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        OBOProperties.java
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
* This class immediately loads the OBO properties file 'obo.properties' once in memory and provides
*  a constructor which takes the specific key which is to be used as property key prefix of the OBO
*  properties file.
*  
* There is a property getter which only returns the property prefixed with 'specificKey.' and 
*  provides the option to indicate whether the property is mandatory or not.
*
* http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
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
import java.io.InputStream;

import java.util.Properties;

public class OBOProperties {

    // Constants ----------------------------------------------------------------------------------
    private static final String PROPERTIES_FILE = "obo.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new OBOConfigurationException(
                "Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } 
        catch (IOException e) {
            throw new OBOConfigurationException(
                "Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
        }
    }

    // Vars ---------------------------------------------------------------------------------------
    private String specificKey;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a OBOProperties instance for the given specific key which is to be used as property
     * key prefix of the OBO properties file.
     */
    public OBOProperties(String specificKey) throws OBOConfigurationException {
        
    	this.specificKey = specificKey;
    }

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the OBOProperties instance specific property value associated with the given key with
     * the option to indicate whether the property is mandatory or not.
     */
    public String getProperty(String key, boolean mandatory) throws OBOConfigurationException {
        
    	String fullKey = specificKey + "." + key;
        String property = PROPERTIES.getProperty(fullKey);

        if (property == null || property.trim().length() == 0) {
            if (mandatory) {
                throw new OBOConfigurationException("Required property '" + fullKey + "'"
                    + " is missing in properties file '" + PROPERTIES_FILE + "'.");
            } 
            else {
                // Make empty value null. Empty Strings are evil.
                property = null;
            }
        }

        return property;
    }
}