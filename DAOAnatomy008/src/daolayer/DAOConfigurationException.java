/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy008
*
* Title:        DAOConfigurationException.java
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
* Description:  This class represents an exception in the DAO configuration which cannot be 
*                resolved at runtime, such as a missing resource in the classpath, a missing 
*                property in the properties file, etcetera.
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

package daolayer;

/*
 *
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public class DAOConfigurationException extends RuntimeException {
	
    // Constructors -------------------------------------------------------------------------------
    /*
     * Constructs a DAOConfigurationException with the given detail message.
     */
    public DAOConfigurationException(String message) {
    	
        super(message);
    }

    /*
     * Constructs a DAOConfigurationException with the given root cause.
     */
    public DAOConfigurationException(Throwable cause) {
        
    	super(cause);
    }

    /*
     * Constructs a DAOConfigurationException with the given detail message and root cause.
     */
    public DAOConfigurationException(String message, Throwable cause) {
        
    	super(message, cause);
    }
}
