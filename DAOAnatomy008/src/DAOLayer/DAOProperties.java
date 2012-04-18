/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        DAOProperties.java
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
* Description:  This class immediately loads the DAO properties file 'dao.properties' once 
*                into memory and provides a constructor which takes the specific key 
*                which is to be used as property key prefix of the DAO properties file.
*                
*               There is a property getter which only returns the property prefixed with 
*                'specificKey.' and provides the option to indicate whether the property 
*                is mandatory or not.
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; 21st March 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package DAOLayer;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class DAOProperties {

    // Constants ----------------------------------------------------------------------------------
    private static final String PROPERTIES_FILE = "dao.properties.test";
    private static final Properties PROPERTIES = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new DAOConfigurationException(
                "Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException e) {
            throw new DAOConfigurationException(
                "Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
        }
    }

    // Vars ---------------------------------------------------------------------------------------
    private String specificKey;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Construct a DAOProperties instance for the given specific key which is to be used as property
     * key prefix of the DAO properties file.
     */
    public DAOProperties(String specificKey) throws DAOConfigurationException {
        
    	this.specificKey = specificKey;
    }

    // Actions ------------------------------------------------------------------------------------
    /*
     * Returns the DAOProperties instance specific property value associated with the given key with
     * the option to indicate whether the property is mandatory or not.
     */
    public String getProperty(String key, boolean mandatory) throws DAOConfigurationException {
        
    	String fullKey = specificKey + "." + key;
        String property = PROPERTIES.getProperty(fullKey);

        if (property == null || property.trim().length() == 0) {
            if (mandatory) {
                throw new DAOConfigurationException("Required property '" + fullKey + "'"
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