package DAOModel;

import java.io.Serializable;

/**
 * This class represents a Data Transfer Object for the Log. 
 */
public class Log implements Serializable {

    // Properties ---------------------------------------------------------------------------------
	/*
	 *  ANA_Log - A Log of all Updates to the Anatomy Database
     *  
     *  Columns:
     *   1.  LOG_OID         => int(10) unsigned
     *   2.  LOG_LOGGED_OID  => int(10) unsigned
     *   3.  LOG_VERSION_FK  => int(10) unsigned
     *   4.  LOG_COLUMN_NAME => varchar(64)
     *   5.  LOG_OLD_VALUE   => varchar(255)
     *   6.  LOG_COMMENTS    => varchar(255)
     *   
	 */
    private Long oid; 
    private Long loggedOid; 
    private Long versionFK; 
    private String columnName;
    private String oldValue; 
    private String comments;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public Log() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /**
     * Minimal constructor. Contains required fields.
     * Full constructor. Contains required and optional fields.
     *  The Full Constructor is the Minimal Constructor
     * 
     */
    public Log(Long oid,
    		   Long loggedOid, 
    		   Long versionFK, 
    		   String columnName,
    	       String oldValue, 
    		   String comments) {
    	
    	this.oid = oid;
    	this.loggedOid = loggedOid;
    	this.versionFK = versionFK;
    	this.columnName = columnName;
    	this.oldValue = oldValue;
    	this.comments = comments;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public Long getLoggedOid() {
        return loggedOid;
    } 
    public Long getVersionFK() {
        return versionFK;
    } 
    public String getColumnName() {
        return columnName;
    }
    public String getOldValue() {
        return oldValue;
    } 
    public String getComments() {
        return comments;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setLoggedOid(Long loggedOid) {
        this.loggedOid = loggedOid;
    } 
    public void setVersionFK(Long versionFK) {
        this.versionFK = versionFK;
    } 
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    } 
    public void setComments(String comments) {
        this.comments = comments;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * The relation ID is unique for each Log. 
     *  So this should compare Log by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Log) && (oid != null) 
        		? oid.equals(((Log) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this Log.
     *  Not required, it just aids log reading.
     */
    public String toString() {
        return String.format("Log [ oid=%d, loggedOid=%d, versionFK=%d, columnName=%s, oldValue=%s, comments=%s ]", 
        		oid, loggedOid, versionFK, columnName, oldValue, comments);

    }
}