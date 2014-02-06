/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        OBOException.java
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
* Description:  This class represents a generic OBO exception.
*                It should wrap any exception of the underlying code, such as File IO Exceptions.
*                
* Link:         
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

public class OBOException extends Exception {
    // Constructors -------------------------------------------------------------------------------
    /*
     * Constructs a OBOException with the given detail message.
     */
    public OBOException(String message) {

    	super(message);
    }

    /*
     * Constructs a OBOException with the given root cause.
     */
    public OBOException(Throwable cause) {
        
    	super(cause);
    }

    /*
     * Constructs a OBOException with the given detail message and root cause.
     */
    public OBOException(String message, Throwable cause) {
        
    	super(message, cause);
    }
}