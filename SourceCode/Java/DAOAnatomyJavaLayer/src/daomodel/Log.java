/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        Log.java
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
* Description:  This class represents a SQL Database Transfer Object for the Log Table.
*                ANA_LOG
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
package daomodel;

import utility.ObjectConverter;

public class Log {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   1.  LOG_OID         => int(10) unsigned
     *   2.  LOG_LOGGED_OID  => int(10) unsigned
     *   3.  LOG_VERSION_FK  => int(10) unsigned
     *   4.  LOG_COLUMN_NAME => varchar(64)
     *   5.  LOG_OLD_VALUE   => varchar(255)
     *   6.  LOG_COMMENTS    => varchar(255)
     *   7.  LOG_DATETIME    => datetime
     *   8.  LOG_TABLE       => varchar(255)
	 */
    private Long oid; 
    private long loggedOid; 
    private long versionFK; 
    private String columnName;
    private String oldValue; 
    private String comments;
    private String datetime;
    private String table;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Log() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Log(Long oid,
    		long loggedOid, 
    		long versionFK, 
    		   String columnName,
    	       String oldValue, 
    		   String comments, 
    		   String datetime, 
    		   String table) {
    	
    	this.oid = oid;
    	this.loggedOid = loggedOid;
    	this.versionFK = versionFK;
    	this.columnName = columnName;
    	this.oldValue = oldValue;
    	this.comments = comments;
    	this.datetime = datetime;
    	this.table = table;
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Log(Long oid,
    		String loggedOid, 
    		String versionFK, 
    		   String columnName,
    	       String oldValue, 
    		   String comments, 
    		   String datetime, 
    		   String table) {
    	
    	this.oid = oid;
    	this.loggedOid = ObjectConverter.convert(loggedOid, Long.class);
    	this.versionFK = ObjectConverter.convert(versionFK, Long.class);
    	this.columnName = columnName;
    	this.oldValue = oldValue;
    	this.comments = comments;
    	this.datetime = datetime;
    	this.table = table;
    }

    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public long getLoggedOid() {
        return loggedOid;
    } 
    public String getLoggedOidAsString() {
        return ObjectConverter.convert(loggedOid, String.class);
    } 
    public long getVersionFK() {
        return versionFK;
    } 
    public String getVersionFKAsString() {
        return ObjectConverter.convert(versionFK, String.class);
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
    public String getDatetime() {
        return datetime;
    }
    public String getTable() {
        return table;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setLoggedOid(long loggedOid) {
        this.loggedOid = loggedOid;
    } 
    public void setLoggedOid(String loggedOid) {
        this.loggedOid = ObjectConverter.convert(loggedOid, Long.class);
    } 
    public void setVersionFK(long versionFK) {
        this.versionFK = versionFK;
    } 
    public void setVersionFK(String versionFK) {
        this.versionFK = ObjectConverter.convert(versionFK, Long.class);
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
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public void setTable(String table) {
        this.table = table;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this Log the same as the Supplied Log?
     */
    public boolean isSameAs(Log daolog){

    	if (this.getLoggedOid() == daolog.getLoggedOid() &&
    		this.getVersionFK() == daolog.getVersionFK() &&
    		this.getColumnName().equals(daolog.getColumnName()) &&
    		this.getOldValue().equals(daolog.getOldValue()) &&
    		this.getComments().equals(daolog.getComments()) &&
    		this.getDatetime().equals(daolog.getDatetime()) &&
    		this.getTable().equals(daolog.getTable()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The Log OID is unique for each Log. 
     *  So this should compare Log by OID only.
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
    	
        return String.format("Log [ oid=%d, loggedOid=%d, versionFK=%d, columnName=%s, oldValue=%s, comments=%s, datetime=%s, table=%s ]", 
        		oid, loggedOid, versionFK, columnName, oldValue, comments, datetime, table);
    }
    
    /*
     * Returns the Java Object String representation of this Component.
     */
    public String toStringJava() {
    	
        return String.format("log%d = new Log( %d, %d, %d, \"%s\", \"%s\", \"%s\", \"%s\", \"%s\" );", 
        		oid, oid, loggedOid, versionFK, columnName, oldValue, comments, datetime, table );
    }

}
