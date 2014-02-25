/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayer
*
* Title:        ComponentOrder.java
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
* Description:  This class represents a SQL Database Transfer Object for the 
*                ComponentOrder Table - ANA_OBO_COMPONENT_ORDER
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


public class JOINComponentOrderComponentComponent {
    // Properties ---------------------------------------------------------------------------------
	/*
     *   ACO_OID               - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   ACO_OBO_ID            - varchar(25) NOT NULL,
     *   ACO_OBO_PARENT        - varchar(25) NOT NULL,
     *   ACO_OBO_TYPE          - varchar(25) NOT NULL,
     *   ACO_OBO_ALPHA_ORDER   - int(20) unsigned NULL,
     *   ACO_OBO_SPECIAL_ORDER - int(20) unsigned NULL,
     *   
     *   AOC_OID           - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   AOC_NAME          - varchar(255) NOT NULL,
     *   AOC_OBO_ID        - varchar(25) NOT NULL,
     *   AOC_DB_ID         - varchar(25) NOT NULL,
     *   AOC_NEW_ID        - varchar(25) NOT NULL,
     *   AOC_NAMESPACE     - varchar(50) NOT NULL,
     *   AOC_DEFINITION    - varchar(510) NOT NULL,
     *   AOC_GROUP         - tinyint(1) NOT NULL,
     *   AOC_START         - varchar(10) NOT NULL,
     *   AOC_END           - varchar(10) NOT NULL,
     *   AOC_PRESENT       - varchar(10) NOT NULL,
     *   AOC_STATUS_CHANGE - varchar(10) NOT NULL COMMENT 'NONE, INSERT, UPDATE, DELETE',
     *   AOC_STATUS_RULE   - varchar(10) NOT NULL COMMENT 'UNCHECKED PASSED FAILED',
     *   
     *   AOC_OID           - bigint(20) unsigned NOT NULL AUTO_INCREMENT,
     *   AOC_NAME          - varchar(255) NOT NULL,
     *   AOC_OBO_ID        - varchar(25) NOT NULL,
     *   AOC_DB_ID         - varchar(25) NOT NULL,
     *   AOC_NEW_ID        - varchar(25) NOT NULL,
     *   AOC_NAMESPACE     - varchar(50) NOT NULL,
     *   AOC_DEFINITION    - varchar(510) NOT NULL,
     *   AOC_GROUP         - tinyint(1) NOT NULL,
     *   AOC_START         - varchar(10) NOT NULL,
     *   AOC_END           - varchar(10) NOT NULL,
     *   AOC_PRESENT       - varchar(10) NOT NULL,
     *   AOC_STATUS_CHANGE - varchar(10) NOT NULL COMMENT 'NONE, INSERT, UPDATE, DELETE',
     *   AOC_STATUS_RULE   - varchar(10) NOT NULL COMMENT 'UNCHECKED PASSED FAILED',
	 */
    private Long oid;
    private String child;
    private String parent;
    private String type;
    private long alphaorder;
    private long specialorder;
    
    private Long oidChild;
    private String nameChild;
    private String idChild;
    private String dbidChild;
    private String newidChild;
    private String namespaceChild;
    private String definitionChild;
    private boolean groupChild;
    private String startChild;
    private String endChild;
    private boolean presentChild;
    private String statuschangeChild; //'NONE, INSERT, UPDATE, DELETE'
    private String statusruleChild;   //"UNCHECKED"|"PASSED"|"FAILED"

    private Long oidParent;
    private String nameParent;
    private String idParent;
    private String dbidParent;
    private String newidParent;
    private String namespaceParent;
    private String definitionParent;
    private boolean groupParent;
    private String startParent;
    private String endParent;
    private boolean presentParent;
    private String statuschangeParent; //'NONE, INSERT, UPDATE, DELETE'
    private String statusruleParent;   //"UNCHECKED"|"PASSED"|"FAILED"


    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public JOINComponentOrderComponentComponent() {
        
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINComponentOrderComponentComponent(Long oid,
    		String child, 
    		String parent,
    		String type,
    		long alphaorder,
    		long specialorder,
    		
    		Long oidChild,
    		String nameChild, 
    		String idChild,
    		String dbidChild,
    		String newidChild,
    		String namespaceChild,
    		String definitionChild,
    		boolean groupChild,
    		String startChild,
    		String endChild,
    		boolean presentChild, 
    		String statuschangeChild, 
    		String statusruleChild,
    		
    		Long oidParent,
    		String nameParent, 
    		String idParent,
    		String dbidParent,
    		String newidParent,
    		String namespaceParent,
    		String definitionParent,
    		boolean groupParent,
    		String startParent,
    		String endParent,
    		boolean presentParent, 
    		String statuschangeParent, 
    		String statusruleParent) {
    	
    	this.oid = oid;
    	this.child = child;
        this.parent = parent;
        this.type = type;
        this.alphaorder = alphaorder;
        this.specialorder = specialorder;
        
    	this.oidChild = oidChild;
    	this.nameChild = nameChild;
    	this.idChild = idChild;
    	this.dbidChild = dbidChild;
    	this.newidChild = newidChild;
    	this.namespaceChild = namespaceChild;
    	this.definitionChild = definitionChild;
    	this.groupChild = groupChild;
    	this.startChild = startChild;
    	this.endChild = endChild;
    	this.presentChild = presentChild;
        this.statuschangeChild = statuschangeChild;
        this.statusruleChild = statusruleChild;
    	
    	this.oidParent = oidParent;
    	this.nameParent = nameParent;
    	this.idParent = idParent;
    	this.dbidParent = dbidParent;
    	this.newidParent = newidParent;
    	this.namespaceParent = namespaceParent;
    	this.definitionParent = definitionParent;
    	this.groupParent = groupParent;
    	this.startParent = startParent;
    	this.endParent = endParent;
    	this.presentParent = presentParent;
        this.statuschangeParent = statuschangeParent;
        this.statusruleParent = statusruleParent;

    }
    
    /*
     * Minimal constructor. Contains required fields.
     */
    public JOINComponentOrderComponentComponent(Long oid,
    		String child, 
    		String parent,
    		String type,
    		String alphaorder,
    		String specialorder,
    		
    		Long oidChild,
    		String nameChild, 
    		String idChild,
    		String dbidChild,
    		String newidChild,
    		String namespaceChild,
    		String definitionChild,
    		String groupChild,
    		String startChild,
    		String endChild,
    		String presentChild, 
    		String statuschangeChild, 
    		String statusruleChild,
    		
    		Long oidParent,
    		String nameParent, 
    		String idParent,
    		String dbidParent,
    		String newidParent,
    		String namespaceParent,
    		String definitionParent,
    		String groupParent,
    		String startParent,
    		String endParent,
    		String presentParent, 
    		String statuschangeParent, 
    		String statusruleParent) {
    	
    	this.oid = oid;
    	this.child = child;
        this.parent = parent;
        this.type = type;
        this.alphaorder = ObjectConverter.convert(alphaorder, Long.class);
        this.specialorder = ObjectConverter.convert(specialorder, Long.class);
        
    	this.oidChild = oidChild;
    	this.nameChild = nameChild;
    	this.idChild = idChild;
    	this.dbidChild = dbidChild;
    	this.newidChild = newidChild;
    	this.namespaceChild = namespaceChild;
    	this.definitionChild = definitionChild;
    	this.groupChild = ObjectConverter.convert(groupChild, Boolean.class);
    	this.startChild = startChild;
    	this.endChild = endChild;
    	this.presentChild = ObjectConverter.convert(presentChild, Boolean.class);
        this.statuschangeChild = statuschangeChild;
        this.statusruleChild = statusruleChild;
    	
    	this.oidParent = oidParent;
    	this.nameParent = nameParent;
    	this.idParent = idParent;
    	this.dbidParent = dbidParent;
    	this.newidParent = newidParent;
    	this.namespaceParent = namespaceParent;
    	this.definitionParent = definitionParent;
    	this.groupParent = ObjectConverter.convert(groupParent, Boolean.class);
    	this.startParent = startParent;
    	this.endParent = endParent;
    	this.presentParent = ObjectConverter.convert(presentParent, Boolean.class);
        this.statuschangeParent = statuschangeParent;
        this.statusruleParent = statusruleParent;

    }
    
    // Getters ------------------------------------------------------------------------------------
    public Long getOid() {
        return this.oid;
    }
    public String getChild() {
        return this.child;
    }
    public String getParent() {
        return this.parent;
    }
    public String getType() {
        return this.type;
    }
    public long getAlphaorder() {
        return this.alphaorder;
    }
    public String getAlphaorderAsString() {
        return ObjectConverter.convert(this.alphaorder, String.class);
    }
    public long getSpecialorder() {
        return this.specialorder;
    }
    public String getSpecialorderAsString() {
        return ObjectConverter.convert(this.specialorder, String.class);
    }

    public Long getOidChild() {
        return this.oidChild;
    }
    public String getIdChild() {
        return this.idChild;
    }
    public String getDbIdChild() {
        return this.dbidChild;
    }
    public String getNewIdChild(){
        return this.newidChild;
    }
    public String getNameChild() {
        return this.nameChild;
    }
    public String getNamespaceChild() {
        return this.namespaceChild;
    }
    public String getDefinitionChild() {
        return this.definitionChild;
    }
    public boolean isGroupChild() {
        return this.groupChild;
    }
    public String getGroupChild() {
        return ObjectConverter.convert(this.groupChild, String.class);
    }
    public String getStartChild() {
    	return this.startChild;
    }
    public String getEndChild() {
    	return this.endChild;
    }
    public boolean isPresentChild(){
        return this.presentChild;
    }
    public String getPresentChild(){
        return ObjectConverter.convert(this.presentChild, String.class);
    }
    public String getStatusChangeChild(){
        return this.statuschangeChild;
    }
    public String getStatusRuleChild(){
        return this.statusruleChild;
    }

    public Long getOidParent() {
        return this.oidParent;
    }
    public String getIdParent() {
        return this.idParent;
    }
    public String getDbIdParent() {
        return this.dbidParent;
    }
    public String getNewIdParent(){
        return this.newidParent;
    }
    public String getNameParent() {
        return this.nameParent;
    }
    public String getNamespaceParent() {
        return this.namespaceParent;
    }
    public String getDefinitionParent() {
        return this.definitionParent;
    }
    public boolean isGroupParent() {
        return this.groupParent;
    }
    public String getGroupParent() {
        return ObjectConverter.convert(this.groupParent, String.class);
    }
    public String getStartParent() {
    	return this.startParent;
    }
    public String getEndParent() {
    	return this.endParent;
    }
    public boolean isPresentParent(){
        return this.presentParent;
    }
    public String getPresentParent(){
        return ObjectConverter.convert(this.presentParent, String.class);
    }
    public String getStatusChangeParent(){
        return this.statuschangeParent;
    }
    public String getStatusRuleParent(){
        return this.statusruleParent;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setOid( Long oid ) {
        this.oid = oid;
    }
    public void setChild( String child ) {
        this.child = child;
    }
    public void setParent( String parent ) {
        this.parent = parent;
    }
    public void setType( String type ) {
        this.type = type;
    }
    public void setAlphaorder( long alphaorder ) {
        this.alphaorder = alphaorder;
    }
    public void setAlphaorder( String alphaorder ) {
        this.alphaorder = ObjectConverter.convert(alphaorder, Long.class);
    }
    public void setSpecialorder( long specialorder ) {
        this.specialorder = specialorder;
    }
    public void setSpecialorder( String specialorder ) {
        this.specialorder = ObjectConverter.convert(specialorder, Long.class);
    }
    
    public void setOidChild( Long oidChild ) {
        this.oidChild = oidChild;
    }
    public void setIdChild( String idChild ) {
        this.idChild = idChild;
    }
    public void setDbIdChild( String dbidChild ) {
        this.dbidChild = dbidChild;
    }
    public void setNewIDChild(String newidChild){
        this.newidChild = newidChild;
    }
    public void setNameChild( String nameChild ) {
        this.nameChild = nameChild;
    }
    public void setNamespaceChild( String namespaceChild ) {
        this.namespaceChild = namespaceChild;
    }
    public void setDefinitionChild( String definitionChild ) {
        this.definitionChild = definitionChild;
    }
    public void setGroupChild( boolean groupChild ) {
        this.groupChild = groupChild;
    }
    public void setGroupChild( String groupChild ) {
        this.groupChild = ObjectConverter.convert(groupChild, Boolean.class);
    }
    public void setStartChild( String startChild ) {
    	this.startChild = startChild;
    }
    public void setEndChild( String endChild ) {
    	this.endChild = endChild;
    }
    public void setPresentChild( boolean presentChild ){
        this.presentChild = presentChild;
    }
    public void setPresentChild( String presentChild ){
        this.presentChild = ObjectConverter.convert(presentChild, Boolean.class);
    }
    public void setStatusChangeChild(String statuschangeChild){
        this.statuschangeChild = statuschangeChild;
    }
    public void setStatusRuleChild(String statusruleChild){
        this.statusruleChild = statusruleChild;
    }

    public void setOidParent( Long oidParent ) {
        this.oidParent = oidParent;
    }
    public void setIdParent( String idParent ) {
        this.idParent = idParent;
    }
    public void setDbIdParent( String dbidParent ) {
        this.dbidParent = dbidParent;
    }
    public void setNewIDParent(String newidParent){
        this.newidParent = newidParent;
    }
    public void setNameParent( String nameParent ) {
        this.nameParent = nameParent;
    }
    public void setNamespaceParent( String namespaceParent ) {
        this.namespaceParent = namespaceParent;
    }
    public void setDefinitionParent( String definitionParent ) {
        this.definitionParent = definitionParent;
    }
    public void setGroupParent( boolean groupParent ) {
        this.groupParent = groupParent;
    }
    public void setGroupParent( String groupParent ) {
        this.groupParent = ObjectConverter.convert(groupParent, Boolean.class);
    }
    public void setStartParent( String startParent ) {
    	this.startParent = startParent;
    }
    public void setEndParent( String endParent ) {
    	this.endParent = endParent;
    }
    public void setPresentParent( boolean presentParent ){
        this.presentParent = presentParent;
    }
    public void setPresentParent( String presentParent ){
        this.presentParent = ObjectConverter.convert(presentParent, Boolean.class);
    }
    public void setStatusChangeParent(String statuschangeParent){
        this.statuschangeParent = statuschangeParent;
    }
    public void setStatusRuleParent(String statusruleParent){
        this.statusruleParent = statusruleParent;
    }
    
    // Helpers ------------------------------------------------------------------------------------
    /*
     * Is this ComponentOrder the same as the Supplied ComponentOrder?
     */
    public boolean isSameAs(JOINComponentOrderComponentComponent joincomponentordercomponentcomponent){
    	
    	if ( this.getChild().equals(joincomponentordercomponentcomponent.getChild()) && 
             this.getParent() == joincomponentordercomponentcomponent.getParent() && 
             this.getType() == joincomponentordercomponentcomponent.getType() && 
             this.getAlphaorder() == joincomponentordercomponentcomponent.getAlphaorder() && 
             this.getSpecialorder() == joincomponentordercomponentcomponent.getSpecialorder() &&
 
             this.getIdChild().equals(joincomponentordercomponentcomponent.getIdChild()) && 
             this.getDbIdChild().equals(joincomponentordercomponentcomponent.getDbIdChild()) && 
             this.getNewIdChild().equals(joincomponentordercomponentcomponent.getNewIdChild()) && 
             this.getNameChild().equals(joincomponentordercomponentcomponent.getNameChild()) && 
             this.getNamespaceChild().equals(joincomponentordercomponentcomponent.getNamespaceChild()) && 
             this.getDefinitionChild().equals(joincomponentordercomponentcomponent.getDefinitionChild()) && 
             this.isGroupChild() == joincomponentordercomponentcomponent.isGroupChild() && 
             this.getStartChild().equals(joincomponentordercomponentcomponent.getStartChild()) && 
             this.getEndChild().equals(joincomponentordercomponentcomponent.getEndChild()) && 
             this.isPresentChild() == joincomponentordercomponentcomponent.isPresentChild() && 
             this.getStatusChangeChild().equals(joincomponentordercomponentcomponent.getStatusChangeChild()) && 
             this.getStatusRuleChild().equals(joincomponentordercomponentcomponent.getStatusRuleChild()) &&
             
             this.getIdParent().equals(joincomponentordercomponentcomponent.getIdParent()) && 
             this.getDbIdParent().equals(joincomponentordercomponentcomponent.getDbIdParent()) && 
             this.getNewIdParent().equals(joincomponentordercomponentcomponent.getNewIdParent()) && 
             this.getNameParent().equals(joincomponentordercomponentcomponent.getNameParent()) && 
             this.getNamespaceParent().equals(joincomponentordercomponentcomponent.getNamespaceParent()) && 
             this.getDefinitionParent().equals(joincomponentordercomponentcomponent.getDefinitionParent()) && 
             this.isGroupParent() == joincomponentordercomponentcomponent.isGroupParent() && 
             this.getStartParent().equals(joincomponentordercomponentcomponent.getStartParent()) && 
             this.getEndParent().equals(joincomponentordercomponentcomponent.getEndParent()) && 
             this.isPresentParent() == joincomponentordercomponentcomponent.isPresentParent() && 
             this.getStatusChangeParent().equals(joincomponentordercomponentcomponent.getStatusChangeParent()) && 
             this.getStatusRuleParent().equals(joincomponentordercomponentcomponent.getStatusRuleParent())
    		){

        	return true;
        }
        else {
        	
            return false;
        }
    }

    /*
     * The OID is unique for each ComponentOrder.
     *  So this should compare ComponentOrder by OID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof JOINComponentOrderComponentComponent) && (oid != null) 
        		? oid.equals(((JOINComponentOrderComponentComponent) other).oid) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this ComponentOrder.
     *  Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format(
        		"ComponentOrder [ oid=%d, child=%s, parent=%s, type=%s, alphaorder=%d, specialorder=%d ]\n" + 
                "Component(Child) [ oidChild=%d, nameChild=%s, idChild=%s, dbidChild=%s, newidChild=%s, namespaceChild=%s, definitionChild=%s, groupChild=%b, startChild=%s, endChild=%s, presentChild=%b, statuschangeChild=%s, statusruleChild=%s ]\n" + 
                "Component(Parent) [ oidParent=%d, nameParent=%s, idParent=%s, dbidParent=%s, newidParent=%s, namespaceParent=%s, definitionParent=%s, groupParent=%b, startParent=%s, endParent=%s, presentParent=%b, statuschangeParent=%s, statusruleParent=%s ]", 
        		    oid, child, parent, type, alphaorder, specialorder,
        		    oidChild, nameChild, idChild, dbidChild, newidChild, namespaceChild, definitionChild, groupChild, startChild, endChild, presentChild, statuschangeChild, statusruleChild,
        		    oidParent, nameParent, idParent, dbidParent, newidParent, namespaceParent, definitionParent, groupParent, startParent, endParent, presentParent, statuschangeParent, statusruleParent 
        );
    }
}
	