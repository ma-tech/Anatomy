package DAOLayer;

/*
 * This class represents an exception in the DAO configuration which cannot be resolved at runtime,
 * such as a missing resource in the classpath, a missing property in the properties file, etcetera.
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