/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        OBOFile.java
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
* Description:  This class represents a SQL Database Transfer Object for the OBOFile Table.
*                ANA_OBO_FILE
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

import java.io.InputStream;

public class OBOFile {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   AOF_OID                 - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   AOF_FILE_NAME           - varchar(255) NOT NULL,
     *   AOF_FILE_CONTENT        - mediumblob,
     *   AOF_FILE_CONTENT_TYPE   - varchar(255) NOT NULL,
     *   AOF_FILE_CONTENT_LENGTH - int(10) DEFAULT NULL,
     *   AOF_FILE_CONTENT_DATE   - datetime DEFAULT NULL,
     *   AOF_FILE_VALIDATION     - varchar(20) DEFAULT NULL COMMENT 'NEW/VALIDATED/FAILED',
     *   AOF_FILE_AUTHOR         - varchar(20) DEFAULT NULL,
     *   AOF_TEXT_REPORT_NAME    - varchar(255),
     *   AOF_TEXT_REPORT         - mediumblob,
     *   AOF_TEXT_REPORT_TYPE    - varchar(255) DEFAULT NULL,
     *   AOF_TEXT_REPORT_LENGTH  - int(10) DEFAULT NULL,
     *   AOF_TEXT_REPORT_DATE    - datetime DEFAULT NULL,
     *   AOF_PDF_REPORT_NAME     - varchar(255),
     *   AOF_PDF_REPORT          - mediumblob,
     *   AOF_PDF_REPORT_TYPE     - varchar(255) DEFAULT NULL,
     *   AOF_PDF_REPORT_LENGTH   - int(10) DEFAULT NULL,
     *   AOF_PDF_REPORT_DATE     - datetime DEFAULT NULL,
	 */
    private Long oid; 
    private String name; 
    private InputStream content; 
    private String contenttype; 
    private long contentlength; 
    private String contentdate; 
    private String validation;
    private String author;
    private String textreportname; 
    private InputStream textreport;
    private String textreporttype;
    private long textreportlength;
    private String textreportdate;
    private String pdfreportname; 
    private InputStream pdfreport;
    private String pdfreporttype;
    private long pdfreportlength;
    private String pdfreportdate;
    
    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public OBOFile() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public OBOFile(Long oid, 
    		String name, 
    		InputStream content,
    		String contenttype,
    		long contentlength,
    		String contentdate,
    		String validation,
    		String author) {
    	
        this.oid = oid;
        this.name = name; 
        this.content = content; 
        this.contenttype = contenttype;
        this.contentlength = contentlength; 
        this.contentdate = contentdate; 
        this.validation = validation; 
        this.author = author; 
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public OBOFile(Long oid, 
    		String name, 
    		String content,
    		String contenttype,
    		long contentlength,
    		String contentdate,
    		String validation,
    		String author) {
    	
        this.oid = oid;
        this.name = name; 
        this.content = null;
        this.contenttype = contenttype;
        this.contentlength = contentlength; 
        this.contentdate = contentdate; 
        this.validation = validation; 
        this.author = author; 
    }

    /*
     * Fuller constructor. 
     *  Contains required and optional fields.
     */
    public OBOFile(Long oid, 
    		String name, 
    		InputStream content,
    		String contenttype,
    		long contentlength,
    		String contentdate,
    		String validation,
    		String author, 
    		String textreportname, 
    	    InputStream textreport,
    	    String textreporttype,
    	    long textreportlength,
    	    String textreportdate) {

    	this(oid, name, content, contenttype, contentlength, contentdate, validation, author);

    	this.textreportname = textreportname; 
    	this.textreport = textreport; 
        this.textreporttype = textreporttype; 
        this.textreportlength = textreportlength; 
        this.textreportdate = textreportdate; 
    }

    /*
     * Fuller constructor. 
     *  Contains required and optional fields.
     */
    public OBOFile(Long oid, 
    		String name, 
    		String content,
    		String contenttype,
    		long contentlength,
    		String contentdate,
    		String validation,
    		String author, 
    		String textreportname, 
    		String textreport,
    	    String textreporttype,
    	    long textreportlength,
    	    String textreportdate) {

    	this(oid, name, content, contenttype, contentlength, contentdate, validation, author);

    	this.textreportname = textreportname; 
    	this.textreport = null; 
        this.textreporttype = textreporttype; 
        this.textreportlength = textreportlength; 
        this.textreportdate = textreportdate; 
    }

    /*
     * Full constructor. 
     *  Contains required and more optional fields.
     */
    public OBOFile(Long oid, 
    		String name, 
    		InputStream content,
    		String contenttype,
    		long contentlength,
    		String contentdate,
    		String validation,
    		String author, 
    		String textreportname, 
    	    InputStream textreport,
    	    String textreporttype,
    	    long textreportlength,
    	    String textreportdate, 
    		String pdfreportname, 
    	    InputStream pdfreport,
    	    String pdfreporttype,
    	    long pdfreportlength,
    	    String pdfreportdate) {

    	this(oid, name, content, contenttype, contentlength, contentdate, validation, author, textreportname, textreport, textreporttype, textreportlength, textreportdate);

    	this.pdfreportname = pdfreportname; 
    	this.pdfreport = pdfreport; 
        this.pdfreporttype = pdfreporttype; 
        this.pdfreportlength = pdfreportlength; 
        this.pdfreportdate = pdfreportdate; 
    }

    /*
     * Full constructor. 
     *  Contains required and more optional fields.
     */
    public OBOFile(Long oid, 
    		String name, 
    		String content,
    		String contenttype,
    		long contentlength,
    		String contentdate,
    		String validation,
    		String author, 
    		String textreportname, 
    		String textreport,
    	    String textreporttype,
    	    long textreportlength,
    	    String textreportdate, 
    		String pdfreportname, 
    		String pdfreport,
    	    String pdfreporttype,
    	    long pdfreportlength,
    	    String pdfreportdate) {

    	this(oid, name, content, contenttype, contentlength, contentdate, validation, author, textreportname, textreport, textreporttype, textreportlength, textreportdate);

    	this.pdfreportname = pdfreportname; 
    	this.pdfreport = null; 
        this.pdfreporttype = pdfreporttype; 
        this.pdfreportlength = pdfreportlength; 
        this.pdfreportdate = pdfreportdate; 
    }
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return oid;
    }
    public String getName() {
        return name;
    }
    public InputStream getContent() {
        return content;
    }
    public String getContenttype() {
        return contenttype;
    }
    public long getContentlength() {
        return contentlength;
    }
    public String getContentdate() {
        return contentdate;
    }
    public String getValidation() {
        return validation;
    }
    public String getAuthor() {
        return author;
    }
    public String getTextreportname() {
        return textreportname;
    }
    public InputStream getTextreport() {
        return textreport;
    }
    public String getTextreporttype() {
        return textreporttype;
    }
    public long getTextreportlength() {
        return textreportlength;
    }
    public String getTextreportdate() {
        return textreportdate;
    }
    public String getPdfreportname() {
        return pdfreportname;
    }
    public InputStream getPdfreport() {
        return pdfreport;
    }
    public String getPdfreporttype() {
        return pdfreporttype;
    }
    public long getPdfreportlength() {
        return pdfreportlength;
    }
    public String getPdfreportdate() {
        return pdfreportdate;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid(Long oid) {
        this.oid = oid;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContent(InputStream content) {
        this.content = content;
    }
    public void setContent(String content) {
        this.content = null;
    }
    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }
    public void setContentlength(long contentlength) {
        this.contentlength = contentlength;
    }
    public void setContentdate(String contentdate) {
        this.contentdate = contentdate;
    }
    public void setValidation(String validation) {
        this.validation = validation;
    }
    public void setAuthor(String author) {
    	this.author = author;
    }
    public void setTextreportname(String textreportname) {
    	this.textreportname = textreportname;
    }
    public void setTextreport(InputStream textreport) {
    	this.textreport = textreport;
    }
    public void setTextreport(String textreport) {
    	this.textreport = null;
    }
    public void setTextreporttype(String textreporttype) {
    	this.textreporttype = textreporttype;
    }
    public void setTextreportlength(long textreportlength) {
    	this.textreportlength = textreportlength;
    }
    public void setTextreportdate(String textreportdate) {
    	this.textreportdate = textreportdate;
    }
    public void setPdfreportname(String pdfreportname) {
    	this.pdfreportname = pdfreportname;
    }
    public void setPdfreport(InputStream pdfreport) {
    	this.pdfreport = pdfreport;
    }
    public void setPdfreport(String pdfreport) {
    	this.pdfreport = null;
    }
    public void setPdfreporttype(String pdfreporttype) {
    	this.pdfreporttype = pdfreporttype;
    }
    public void setPdfreportlength(long pdfreportlength) {
    	this.pdfreportlength = pdfreportlength;
    }
    public void setPdfreportdate(String pdfreportdate) {
    	this.pdfreportdate = pdfreportdate;
    }

    // Override -----------------------------------------------------------------------------------
    /*
     * Is this OBOFile the same as the Supplied OBOFile?
     */
    public boolean isSameAs(OBOFile daoobofile){

    	if (this.getName().equals(daoobofile.getName()) &&
    		this.getContenttype().equals(daoobofile.getContenttype()) &&
    		this.getContentlength() == daoobofile.getContentlength() &&
    		this.getContentdate().equals(daoobofile.getContentdate()) &&
    		this.getValidation().equals(daoobofile.getValidation()) &&
    		this.getAuthor().equals(daoobofile.getAuthor()) &&
    		this.getTextreportname().equals(daoobofile.getTextreportname()) &&
    		this.getTextreporttype().equals(daoobofile.getTextreporttype()) &&
    		this.getTextreportlength() == daoobofile.getTextreportlength() &&
    		this.getTextreportdate().equals(daoobofile.getTextreportdate()) &&
    		this.getPdfreportname().equals(daoobofile.getPdfreportname()) &&
    		this.getPdfreporttype().equals(daoobofile.getPdfreporttype()) &&
    		this.getPdfreportlength() == daoobofile.getPdfreportlength() &&
    		this.getPdfreportdate().equals(daoobofile.getPdfreportdate()) ) {

        	return true;
        }
        else {

        	return false;
        }
    }

    /*
     * The relation ID is unique for each OBOFile. So this should compare OBOFile by ID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof OBOFile) && (oid != null) 
        		? oid.equals(((OBOFile) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this User. Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("OBOFile [ oid=%d, name=%s, No Content Here!, contenttype=%s, contentlength=%d, contentdate=%s, validation=%s, author=%s, textreportname=%s, No Text Report Here!, textreporttype=%s, textreportlength=%d, textreportdate=%s, pdfreportname=%s, No PDF Report Here!, pdfreporttype=%s, pdfreportlength=%d, pdfreportdate=%s ]", 
            oid, name, contenttype, contentlength, contentdate, validation, author, textreportname, textreporttype, textreportlength, textreportdate, pdfreportname, pdfreporttype, pdfreportlength, pdfreportdate);
    }
}
