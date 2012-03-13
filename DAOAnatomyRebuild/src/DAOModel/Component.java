/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        Component.java
#
# Date:         2012
#
# Author:       Mike Wicks
#
# Copyright:    2012
#               Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Description:  A Wrapper Object for an OBO Component inserted into the Anatomy Database
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package DAOModel;

import java.io.Serializable;

public class Component implements Serializable{

    // daocomponent fields
    private Long oid;
    private String name;
    private String id;
    private String dbid;
    private String newid;
    private String namespace;
    private Integer group;
    private String start;
    private String end;
    private Integer present;
    private String statuschange; //"UNCHANGED"|"NEW"|"CHANGED"|"DELETED"
    private String statusrule;   //"UNCHECKED"|"PASSED"|"FAILED"

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public Component() {
        // Always keep the default constructor alive in a Javabean class.
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public Component(Long oid,
    		String name, 
    		String id,
    		String dbid,
    		String newid,
    		String namespace,
    		Integer group,
    		String start,
    		String end,
    		Integer present, 
    		String statuschange, 
    		String statusrule) {
    	
    	this.oid = oid;
    	this.name = name;
    	this.id = id;
    	this.dbid = dbid;
    	this.newid = newid;
    	this.namespace = namespace;
    	this.group = group;
    	this.start = start;
    	this.end = end;
    	this.present = present;
        this.statuschange = statuschange;
        this.statusrule = statusrule;

    }

    
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return this.oid;
    }
    public String getId() {
        return this.id;
    }
    public String getDbId() {
        return this.dbid;
    }
    public String getNewId(){
        return this.newid;
    }
    public String getName() {
        return this.name;
    }
    public String getNamespace() {
        return this.namespace;
    }
    public Integer getGroup() {
        return this.group;
    }
    public String getStart() {
    	return this.start;
    }
    public String getEnd() {
    	return this.end;
    }
    public Integer getPresent(){
        return this.present;
    }
    public String getStatusChange(){
        return this.statuschange;
    }
    public String getStatusRule(){
        return this.statusrule;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid( Long oid ) {
        this.oid = oid;
    }
    public void setId( String id ) {
        this.id = id;
    }
    public void setDbId( String dbid ) {
        this.dbid = dbid;
    }
    public void setNewID(String newid){
        this.newid = newid;
    }
    public void setName( String name ) {
        this.name = name;
    }
    public void setNamespace( String namespace ) {
        this.namespace = namespace;
    }
    public void setGroup( Integer group ) {
        this.group = group;
    }
    public void setStart( String start ) {
    	this.start = start;
    }
    public void setEnd( String end ) {
    	this.end = end;
    }
    public void setPresent( int present ){
        this.present = present;
    }
    public void setStatusChange(String statuschange){
        this.statuschange = statuschange;
    }
    public void setStatusRule(String statusrule){
        this.statusrule = statusrule;
    }
    
    // Helpers ------------------------------------------------------------------------------------

    /*
     * Is this Component the same as the Supplied Component?
     */
    public boolean isComponentSameAs(Component daocomponent){
    	
        //EMAP id, description, start stage, end stage, parents, synonyms

    	if ( this.getId().equals(daocomponent.getId()) && 
             this.getName().equals(daocomponent.getName()) &&
             this.getStart() == daocomponent.getStart() &&
             this.getEnd() == daocomponent.getEnd() ){

        	return true;
        }
        else {
            return false;
        }
    }
    

    /*
     * The relation ID is unique for each Thing. So this should compare Thing by ID only.
     */
    public boolean equals(Object other) {
        return (other instanceof Component) && (oid != null) 
        		? oid.equals(((Component) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     */
    public String toString() {
        return String.format("Component [ oid=%d, id=%s, name=%s, statusChange=%s, statusRule=%s, dbID=%s, newid=%s, namespace=%s, group=%b, start=%s, end=%s, present=%d ]", 
        		oid, id, name, statuschange, statusrule, dbid, newid, namespace, group, start, end, present);
    }

}
