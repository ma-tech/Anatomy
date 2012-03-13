package DAOLayer;

/**
 * This class represents a generic DAO exception.
 *  It should wrap any exception of the underlying code, such as SQLExceptions.
 *
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
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
