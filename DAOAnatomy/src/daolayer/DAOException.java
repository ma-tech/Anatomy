/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomy
*
* Title:        DAOException.java
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
* Description:  This class represents a generic DAO exception.
*                It should wrap any exception of the underlying code, such as SQLExceptions.
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

public class DAOException extends Exception {
    // Constructors -------------------------------------------------------------------------------
    /*
     * Constructs a DAOException with the given detail message.
     */
    public DAOException(String message) {

    	super(message);
    }

    /*
     * Constructs a DAOException with the given root cause.
     */
    public DAOException(Throwable cause) {
        
    	super(cause);
    }

    /*
     * Constructs a DAOException with the given detail message and root cause.
     */
    public DAOException(String message, Throwable cause) {
        
    	super(message, cause);
    }
}
