/*
*---------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        ComponentOBO.java
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
* Description:  This class represents an exception in the OBO configuration which cannot be 
*                resolved at runtime, such as a missing resource in the classpath, a missing 
*                property in the properties file, etcetera.
*
* Link:         http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package OBOLayer;

public class OBOConfigurationException extends RuntimeException {
    // Constructors -------------------------------------------------------------------------------
    /*
     * Constructs a OBOConfigurationException with the given detail message.
     */
    public OBOConfigurationException(String message) {
    	
        super(message);
    }

    /*
     * Constructs a OBOConfigurationException with the given root cause.
     */
    public OBOConfigurationException(Throwable cause) {
        
    	super(cause);
    }

    /*
     * Constructs a OBOConfigurationException with the given detail message and root cause.
     */
    public OBOConfigurationException(String message, Throwable cause) {
        
    	super(message, cause);
    }
}
