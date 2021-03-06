/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        OBOComponent.java
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
* Description:  A Wrapper Object for an OBO Component
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package obomodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import utility.Wrapper;
import utility.ObjectConverter;


public class OBOComponent implements Comparable<OBOComponent>{

	// CONSTANTS
    private static final int ERROR_STAGE = -1;
    private static final String ERROR_STAGE_STR = "UNKNOWN";
    
    private static final int MIN_MOUSE_STAGE = 0;
    private static final int MIN_HUMAN_STAGE = 0;
    private static final int MIN_CHICK_STAGE = 0;
    
    private static final String MIN_MOUSE_STAGE_STR = "TS01";
    private static final String MIN_HUMAN_STAGE_STR = "CS01";
    private static final String MIN_CHICK_STAGE_STR = "EGK-I";
    
    private static final int MAX_MOUSE_STAGE = 27;
    private static final int MAX_HUMAN_STAGE = 25;
    private static final int MAX_CHICK_STAGE = 60;
    
    private static final String MAX_MOUSE_STAGE_STR = "TS28";
    private static final String MAX_HUMAN_STAGE_STR = "CS23";
    private static final String MAX_CHICK_STAGE_STR = "HH48";

    // obocomponent fields
    private String name;
    private String id;
    private String displayId;
    private String dbID;
    private String newid;
    private String namespace;
    private String definition;
    private boolean group;
    private String start;
    private String end;
    private long startSequence;
    private long endSequence;
    private boolean present;
    private ArrayList<String> childOfs;
    private ArrayList<String> childOfTypes;
    private ArrayList<String> childOfNames;
    private ArrayList<String> synonyms;
    private String statusChange; //"NONE"|"INSERT"|"UPDATE"|"DELETE"
    private String statusRule;   //"UNCHECKED"|"PASSED"|"FAILED"
    private ArrayList<String> userComments;
    private String orderComment;
    private Set<String> comments; //store unique comments only
    
    private ArrayList<String> alternativeIds;

    private ArrayList<String> timedComponents;

    //missing relationships, starts < end
    private boolean flagMissingRel;
    
    //child node lifetime not within parent lifetime
    private boolean flagLifeTime;

    //path variables
    private boolean isPrimary;
    private DefaultMutableTreeNode[] primaryPath;
    private Vector<DefaultMutableTreeNode[]> paths;

    private String fullRelations;

    // Constructors -------------------------------------------------------------------------------
    /*
     * Default constructor.
     */
    public OBOComponent() {
    	
    	this.name = "";
    	this.id = "";
    	this.displayId = "";
    	this.dbID = "";
    	this.newid = "";
    	this.namespace = "";
    	this.definition = "";
    	this.group = false;
    	this.start = "";
    	this.end = "";
        this.startSequence = -1;
        this.endSequence = -1;
    	this.present = false;
        this.statusChange = "";
        this.statusRule = "";

        this.childOfs = new ArrayList<String>();
        this.childOfTypes = new ArrayList<String>();
        this.childOfNames = new ArrayList<String>();
        this.synonyms = new ArrayList<String>();
        this.userComments = new ArrayList<String>();
        this.orderComment = "";
        		
        this.flagMissingRel = false;
        this.flagLifeTime = false;
        
        this.paths = new Vector<DefaultMutableTreeNode[]>();
        this.isPrimary = true;
        this.primaryPath = null;

        this.comments = new TreeSet<String>();
        
        this.alternativeIds = new ArrayList<String>();

        this.timedComponents = new ArrayList<String>();
    }

    /*
     * Minimal constructor. Contains required fields.
     */
    public OBOComponent(String name, 
    		String id,
    		String displayId,
    		String dbID,
    		String newid,
    		String namespace,
    		String definition,
    		boolean group,
    		String start,
    		String end,
    		boolean present, 
    		String statusChange, 
    		String statusRule) {
    	
    	this.name = name;
    	this.id = id;
    	this.displayId = displayId;
    	this.dbID = dbID;
    	this.newid = newid;
    	this.namespace = namespace;
    	this.definition = definition;
    	this.group = group;

    	setStart(start);
    	setEnd(end);

    	this.present = present;
        this.statusChange = statusChange;
        this.statusRule = statusRule;

        this.childOfs = new ArrayList<String>();
        this.childOfTypes = new ArrayList<String>();
        this.childOfNames = new ArrayList<String>();
        this.synonyms = new ArrayList<String>();
        this.userComments = new ArrayList<String>();
        this.orderComment = "";
        
        this.flagMissingRel = false;
        this.flagLifeTime = false;
        
        this.paths = new Vector<DefaultMutableTreeNode[]>();
        this.isPrimary = true;
        this.primaryPath = null;

        this.comments = new TreeSet<String>();
    }

    /*
     * Fuller constructor. Contains required and optional fields.
     */
    public OBOComponent(String name, 
    		String id,
    		String displayId,
    		String dbID,
    		String newid,
    		String namespace,
    		String definition,
    		boolean group,
    		String start,
    		String end,
    		boolean present, 
    		String statusChange, 
    		String statusRule,
    		ArrayList<String> childOfs,
    		ArrayList<String> childOfNames,
    		ArrayList<String> childOfTypes,
    		ArrayList<String> synonyms, 
    		ArrayList<String> userComments,
    		String orderComment,
    		TreeSet<String> comments) {
    	
    	this(name, id, displayId, dbID, newid, namespace, definition, group, start, end, present, statusChange, statusRule);

    	this.childOfs = childOfs;
        this.childOfNames = childOfNames;
        this.childOfTypes = childOfTypes;
        this.synonyms = synonyms;
        this.userComments = userComments;
        this.orderComment = orderComment;
        this.comments = comments;
    }
    
    /*
     * Fuller constructor. Contains required and optional fields.
     */
    public OBOComponent(String name, 
    		String id,
    		String displayId,
    		String dbID,
    		String newid,
    		String namespace,
    		String definition,
    		boolean group,
    		String start,
    		String end,
    		boolean present, 
    		String statusChange, 
    		String statusRule,
    		ArrayList<String> childOfs,
    		ArrayList<String> childOfNames,
    		ArrayList<String> childOfTypes,
    		ArrayList<String> synonyms, 
    		ArrayList<String> userComments,
    		String orderComment,
    		TreeSet<String> comments,
    		ArrayList<String> alternativeIds) {
    	
    	this(name, id, displayId, dbID, newid, namespace, definition, group, start, end, present, statusChange, statusRule);

    	this.childOfs = childOfs;
    	this.childOfNames = childOfNames;
        this.childOfTypes = childOfTypes;
        this.synonyms = synonyms;
        this.userComments = userComments;
        this.orderComment = orderComment;
        this.comments = comments;
        this.alternativeIds = alternativeIds;
    }
    
    /*
     * Even MORE Fuller constructor. Contains required and optional fields.
     */
    public OBOComponent(String name, 
    		String id,
    		String displayId,
    		String dbID,
    		String newid,
    		String namespace,
    		String definition,
    		boolean group,
    		String start,
    		String end,
    		boolean present, 
    		String statusChange, 
    		String statusRule,
    		ArrayList<String> childOfs,
    		ArrayList<String> childOfNames,
    		ArrayList<String> childOfTypes,
    		ArrayList<String> synonyms, 
    		ArrayList<String> userComments,
    		String orderComment,
    		TreeSet<String> comments,
    		ArrayList<String> alternativeIds,
    		ArrayList<String> timedComponents) {
    	
    	this(name, id, displayId, dbID, newid, namespace, definition, group, start, end, present, statusChange, statusRule, childOfs, childOfNames, childOfTypes, synonyms, userComments, orderComment, comments, alternativeIds);

        this.timedComponents = timedComponents;
    }
    
    /*
     * Minimal constructor. Contains required fields.
     */
    public OBOComponent(String name, 
    		String id,
    		String displayId,
    		String dbID,
    		String newid,
    		String namespace,
    		String definition,
    		String group,
    		String start,
    		String end,
    		String present, 
    		String statusChange, 
    		String statusRule) {
    	
    	this.name = name;
    	this.id = id;
    	this.displayId = displayId;
    	this.dbID = dbID;
    	this.newid = newid;
    	this.namespace = namespace;
    	this.definition = definition;
    	this.group = ObjectConverter.convert(group, Boolean.class);

    	setStart(start);
    	setEnd(end);

    	this.present = ObjectConverter.convert(present, Boolean.class);
        this.statusChange = statusChange;
        this.statusRule = statusRule;

        this.childOfs = new ArrayList<String>();
        this.childOfNames = new ArrayList<String>();
        this.childOfTypes = new ArrayList<String>();
        this.synonyms = new ArrayList<String>();
        this.userComments = new ArrayList<String>();
        this.orderComment = "";
        
        this.flagMissingRel = false;
        this.flagLifeTime = false;
        
        this.paths = new Vector<DefaultMutableTreeNode[]>();
        this.isPrimary = true;
        this.primaryPath = null;

        this.comments = new TreeSet<String>();
    }

    /*
     * Fuller constructor. Contains required and optional fields.
     */
    public OBOComponent(String name, 
    		String id,
    		String displayId,
    		String dbID,
    		String newid,
    		String namespace,
    		String definition,
    		String group,
    		String start,
    		String end,
    		String present, 
    		String statusChange, 
    		String statusRule,
    		ArrayList<String> childOfs,
    		ArrayList<String> childOfNames,
    		ArrayList<String> childOfTypes,
    		ArrayList<String> synonyms, 
    		ArrayList<String> userComments,
    		String orderComment,
    		TreeSet<String> comments) {
    	
    	this(name, id, displayId, dbID, newid, namespace, definition, group, start, end, present, statusChange, statusRule);

    	this.childOfs = childOfs;
    	this.childOfNames = childOfNames;
        this.childOfTypes = childOfTypes;
        this.synonyms = synonyms;
        this.userComments = userComments;
        this.orderComment = orderComment;
        this.comments = comments;
    }
    
    /*
     * Fuller constructor. Contains required and optional fields.
     */
    public OBOComponent(String name, 
    		String id,
    		String displayId,
    		String dbID,
    		String newid,
    		String namespace,
    		String definition,
    		String group,
    		String start,
    		String end,
    		String present, 
    		String statusChange, 
    		String statusRule,
    		ArrayList<String> childOfs,
    		ArrayList<String> childOfNames,
    		ArrayList<String> childOfTypes,
    		ArrayList<String> synonyms, 
    		ArrayList<String> userComments,
    		String orderComment,
    		TreeSet<String> comments,
    		ArrayList<String> alternativeIds) {
    	
    	this(name, id, displayId, dbID, newid, namespace, definition, group, start, end, present, statusChange, statusRule);

    	this.childOfs = childOfs;
    	this.childOfNames = childOfNames;
        this.childOfTypes = childOfTypes;
        this.synonyms = synonyms;
        this.userComments = userComments;
        this.orderComment = orderComment;
        this.comments = comments;
        this.alternativeIds = alternativeIds;
    }
    
    /*
     * Even MORE Fuller constructor. Contains required and optional fields.
     */
    public OBOComponent(String name, 
    		String id,
    		String displayId,
    		String dbID,
    		String newid,
    		String namespace,
    		String definition,
    		String group,
    		String start,
    		String end,
    		String present, 
    		String statusChange, 
    		String statusRule,
    		ArrayList<String> childOfs,
    		ArrayList<String> childOfNames,
    		ArrayList<String> childOfTypes,
    		ArrayList<String> synonyms, 
    		ArrayList<String> userComments,
    		String orderComment,
    		TreeSet<String> comments,
    		ArrayList<String> alternativeIds,
    		ArrayList<String> timedComponents) {
    	
    	this(name, id, displayId, dbID, newid, namespace, definition, group, start, end, present, statusChange, statusRule, childOfs, childOfNames, childOfTypes, synonyms, userComments, orderComment, comments, alternativeIds);

        this.timedComponents = timedComponents;
    }
    
    // Getters ------------------------------------------------------------------------------------
    public String getID() {
        return this.id;
    }
    public String getDisplayId() {
        return this.displayId;
    }
    public String getDBID() {
        return this.dbID;
    }
    public String getNewID(){
        return this.newid;
    }
    public String getName() {
        return this.name;
    }
    public String getNamespace() {
        return this.namespace;
    }
    public String getDefinition() {
        return this.definition;
    }
    public String getStart() {
    	return this.start;
    }
    public String getEnd() {
    	return this.end;
    }
    public boolean isPresent(){
        return this.present;
    }
    public ArrayList<String> getChildOfs() {
        return this.childOfs;
    }
    public ArrayList<String> getChildOfNames() {
        return this.childOfNames;
    }
    public ArrayList<String> getChildOfTypes() {
        return this.childOfTypes;
    }
    public ArrayList<String> getSynonyms() {
        return this.synonyms;
    }
    public String getStatusChange(){
        return this.statusChange;
    }
    public String getStatusRule(){
        return this.statusRule;
    }
    public Set<String> getCheckComments(){
        return this.comments;
    }
    public ArrayList<String> getUserComments(){
        return this.userComments;
    }
    public String getOrderComment(){
        return this.orderComment;
    }
    public ArrayList<String> getAlternativeIds() {
        return this.alternativeIds;
    }
    public ArrayList<String> getTimedComponents() {
        return this.timedComponents;
    }

    public boolean getFlagMissingRel(){
        return this.flagMissingRel;
    }
    public boolean getFlagLifeTime(){
        return this.flagLifeTime;
    }
    public boolean isPrimary(){
        return this.isPrimary;
    }
    public Vector<DefaultMutableTreeNode[]> getPaths(){
        return this.paths;
    }
    public boolean isGroup(){
        return this.group;
    }
    
    public long getStartSequence() {
        return startSequence;
    }
    public long getEndSequence() {
        return endSequence;
    }
    
    public String getStartStageForSpecies(String species) {

        if (species.equals("mouse")) {
        	
        	return MIN_MOUSE_STAGE_STR;
        }
        else if (species.equals("human")) {
        	
        	return MIN_HUMAN_STAGE_STR;
        }
        
        else if (species.equals("chick")) {
        	
        	return MIN_CHICK_STAGE_STR;
        }
        else  {
        	
        	return ERROR_STAGE_STR;
        }
    }

    public String getEndStageForSpecies(String species) {

        if (species.equals("mouse")) {
        	
        	return MAX_MOUSE_STAGE_STR;
        }
        else if (species.equals("human")) {
        	
        	return MAX_HUMAN_STAGE_STR;
        }
        
        else if (species.equals("chick")) {
        	
        	return MAX_CHICK_STAGE_STR;
        }
        else  {
        	
        	return ERROR_STAGE_STR;
        }
    }

    public DefaultMutableTreeNode[] getPrimaryPath(){
        return this.primaryPath;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setID( String id ) {
        this.id = id;
    }
    public void setDisplayId( String displayId ) {
        this.displayId = displayId;
    }
    public void setDBID( String dbID ) {
        this.dbID = dbID;
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
    public void setDefinition( String definition ) {
        this.definition = definition;
    }
    public void setStart( String start ) {
    	this.start = start;
    	if (this.start.equals("TS01")) {
    		this.startSequence = 0;
    	}
    	else if (this.start.equals("TS02")) {
    		this.startSequence = 1;
    	}
    	else if (this.start.equals("TS03")) {
    		this.startSequence = 2;
    	}
    	else if (this.start.equals("TS04")) {
    		this.startSequence = 3;
    	}
    	else if (this.start.equals("TS05")) {
    		this.startSequence = 4;
    	}
    	else if (this.start.equals("TS06")) {
    		this.startSequence = 5;
    	}
    	else if (this.start.equals("TS07")) {
    		this.startSequence = 6;
    	}
    	else if (this.start.equals("TS08")) {
    		this.startSequence = 7;
    	}
    	else if (this.start.equals("TS09")) {
    		this.startSequence = 8;
    	}
    	else if (this.start.equals("TS10")) {
    		this.startSequence = 9;
    	}
    	else if (this.start.equals("TS11")) {
    		this.startSequence = 10;
    	}
    	else if (this.start.equals("TS12")) {
    		this.startSequence = 11;
    	}
    	else if (this.start.equals("TS13")) {
    		this.startSequence = 12;
    	}
    	else if (this.start.equals("TS14")) {
    		this.startSequence = 13;
    	}
    	else if (this.start.equals("TS15")) {
    		this.startSequence = 14;
    	}
    	else if (this.start.equals("TS16")) {
    		this.startSequence = 15;
    	}
    	else if (this.start.equals("TS17")) {
    		this.startSequence = 16;
    	}
    	else if (this.start.equals("TS18")) {
    		this.startSequence = 17;
    	}
    	else if (this.start.equals("TS19")) {
    		this.startSequence = 18;
    	}
    	else if (this.start.equals("TS20")) {
    		this.startSequence = 19;
    	}
    	else if (this.start.equals("TS21")) {
    		this.startSequence = 20;
    	}
    	else if (this.start.equals("TS22")) {
    		this.startSequence = 21;
    	}
    	else if (this.start.equals("TS23")) {
    		this.startSequence = 22;
    	}
    	else if (this.start.equals("TS24")) {
    		this.startSequence = 23;
    	}
    	else if (this.start.equals("TS25")) {
    		this.startSequence = 24;
    	}
    	else if (this.start.equals("TS26")) {
    		this.startSequence = 25;
    	}
    	else if (this.start.equals("TS27")) {
    		this.startSequence = 26;
    	}
    	else if (this.start.equals("TS28")) {
    		this.startSequence = 27;
    	}
    	else if (this.start.equals("CS01")) {
    		this.startSequence = 0;
    	}
    	else if (this.start.equals("CS02")) {
    		this.startSequence = 1;
    	}
    	else if (this.start.equals("CS03")) {
    		this.startSequence = 2;
    	}
    	else if (this.start.equals("CS04")) {
    		this.startSequence = 3;
    	}
    	else if (this.start.equals("CS05a")) {
    		this.startSequence = 4;
    	}
    	else if (this.start.equals("CS05b")) {
    		this.startSequence = 5;
    	}
    	else if (this.start.equals("CS05c")) {
    		this.startSequence = 6;
    	}
    	else if (this.start.equals("CS06a")) {
    		this.startSequence = 7;
    	}
    	else if (this.start.equals("CS06b")) {
    		this.startSequence = 8;
    	}
    	else if (this.start.equals("CS07")) {
    		this.startSequence = 9;
    	}
    	else if (this.start.equals("CS08")) {
    		this.startSequence = 10;
    	}
    	else if (this.start.equals("CS09")) {
    		this.startSequence = 11;
    	}
    	else if (this.start.equals("CS10")) {
    		this.startSequence = 12;
    	}
    	else if (this.start.equals("CS11")) {
    		this.startSequence = 13;
    	}
    	else if (this.start.equals("CS12")) {
    		this.startSequence = 14;
    	}
    	else if (this.start.equals("CS13")) {
    		this.startSequence = 15;
    	}
    	else if (this.start.equals("CS14")) {
    		this.startSequence = 16;
    	}
    	else if (this.start.equals("CS15")) {
    		this.startSequence = 17;
    	}
    	else if (this.start.equals("CS16")) {
    		this.startSequence = 18;
    	}
    	else if (this.start.equals("CS17")) {
    		this.startSequence = 19;
    	}
    	else if (this.start.equals("CS18")) {
    		this.startSequence = 20;
    	}
    	else if (this.start.equals("CS19")) {
    		this.startSequence = 21;
    	}
    	else if (this.start.equals("CS20")) {
    		this.startSequence = 22;
    	}
    	else if (this.start.equals("CS21")) {
    		this.startSequence = 23;
    	}
    	else if (this.start.equals("CS22")) {
    		this.startSequence = 24;
    	}
    	else if (this.start.equals("CS23")) {
    		this.startSequence = 25;
    	}
    	else if (this.start.equals("EGK-I")) {
    		this.startSequence = 0;
    	}
    	else if (this.start.equals("EGK-II")) {
    		this.startSequence = 1;
    	}
    	else if (this.start.equals("EGK-III")) {
    		this.startSequence = 2;
    	}
    	else if (this.start.equals("EGK-IV")) {
    		this.startSequence = 3;
    	}
    	else if (this.start.equals("EGK-V")) {
    		this.startSequence = 4;
    	}
    	else if (this.start.equals("EGK-VI")) {
    		this.startSequence = 5;
    	}
    	else if (this.start.equals("EGK-VII")) {
    		this.startSequence = 6;
    	}
    	else if (this.start.equals("EGK-VIII")) {
    		this.startSequence = 7;
    	}
    	else if (this.start.equals("EGK-IX")) {
    		this.startSequence = 8;
    	}
    	else if (this.start.equals("EGK-X")) {
    		this.startSequence = 9;
    	}
    	else if (this.start.equals("EGK-XI")) {
    		this.startSequence = 10;
    	}
    	else if (this.start.equals("EGK-XII")) {
    		this.startSequence = 11;
    	}
    	else if (this.start.equals("EGK-XIII")) {
    		this.startSequence = 12;
    	}
    	else if (this.start.equals("EGK-XIV")) {
    		this.startSequence = 13;
    	}
    	else if (this.start.equals("HH02")) {
    		this.startSequence = 14;
    	}
    	else if (this.start.equals("HH03")) {
    		this.startSequence = 15;
    	}
    	else if (this.start.equals("HH04")) {
    		this.startSequence = 16;
    	}
    	else if (this.start.equals("HH05")) {
    		this.startSequence = 17;
    	}
    	else if (this.start.equals("HH06")) {
    		this.startSequence = 18;
    	}
    	else if (this.start.equals("HH07")) {
    		this.startSequence = 19;
    	}
    	else if (this.start.equals("HH08")) {
    		this.startSequence = 20;
    	}
    	else if (this.start.equals("HH09")) {
    		this.startSequence = 21;
    	}
    	else if (this.start.equals("HH10")) {
    		this.startSequence = 22;
    	}
    	else if (this.start.equals("HH11")) {
    		this.startSequence = 23;
    	}
    	else if (this.start.equals("HH12")) {
    		this.startSequence = 24;
    	}
    	else if (this.start.equals("HH13")) {
    		this.startSequence = 25;
    	}
    	else if (this.start.equals("HH14")) {
    		this.startSequence = 26;
    	}
    	else if (this.start.equals("HH15")) {
    		this.startSequence = 27;
    	}
    	else if (this.start.equals("HH16")) {
    		this.startSequence = 28;
    	}
    	else if (this.start.equals("HH17")) {
    		this.startSequence = 29;
    	}
    	else if (this.start.equals("HH18")) {
    		this.startSequence = 30;
    	}
    	else if (this.start.equals("HH19")) {
    		this.startSequence = 31;
    	}
    	else if (this.start.equals("HH20")) {
    		this.startSequence = 32;
    	}
    	else if (this.start.equals("HH21")) {
    		this.startSequence = 33;
    	}
    	else if (this.start.equals("HH22")) {
    		this.startSequence = 34;
    	}
    	else if (this.start.equals("HH23")) {
    		this.startSequence = 35;
    	}
    	else if (this.start.equals("HH24")) {
    		this.startSequence = 36;
    	}
    	else if (this.start.equals("HH25")) {
    		this.startSequence = 37;
    	}
    	else if (this.start.equals("HH26")) {
    		this.startSequence = 38;
    	}
    	else if (this.start.equals("HH27")) {
    		this.startSequence = 39;
    	}
    	else if (this.start.equals("HH28")) {
    		this.startSequence = 40;
    	}
    	else if (this.start.equals("HH29")) {
    		this.startSequence = 41;
    	}
    	else if (this.start.equals("HH30")) {
    		this.startSequence = 42;
    	}
    	else if (this.start.equals("HH31")) {
    		this.startSequence = 43;
    	}
    	else if (this.start.equals("HH32")) {
    		this.startSequence = 44;
    	}
    	else if (this.start.equals("HH33")) {
    		this.startSequence = 45;
    	}
    	else if (this.start.equals("HH34")) {
    		this.startSequence = 46;
    	}
    	else if (this.start.equals("HH35")) {
    		this.startSequence = 47;
    	}
    	else if (this.start.equals("HH36")) {
    		this.startSequence = 48;
    	}
    	else if (this.start.equals("HH37")) {
    		this.startSequence = 49;
    	}
    	else if (this.start.equals("HH38")) {
    		this.startSequence = 50;
    	}
    	else if (this.start.equals("HH39")) {
    		this.startSequence = 51;
    	}
    	else if (this.start.equals("HH40")) {
    		this.startSequence = 52;
    	}
    	else if (this.start.equals("HH41")) {
    		this.startSequence = 53;
    	}
    	else if (this.start.equals("HH42")) {
    		this.startSequence = 54;
    	}
    	else if (this.start.equals("HH43")) {
    		this.startSequence = 55;
    	}
    	else if (this.start.equals("HH44")) {
    		this.startSequence = 56;
    	}
    	else if (this.start.equals("HH45")) {
    		this.startSequence = 57;
    	}
    	else if (this.start.equals("HH46")) {
    		this.startSequence = 58;
    	}
    	else if (this.start.equals("HH47")) {
    		this.startSequence = 59;
    	}
    	else if (this.start.equals("HH48")) {
    		this.startSequence = 60;
    	}
    	else {
    		this.startSequence = ERROR_STAGE;
    	}
    }
    
    public void setStartSequenceMin(String species) {

        if (species.equals("mouse")) {
        	
        	this.startSequence = MIN_MOUSE_STAGE;
    	    this.start = MIN_MOUSE_STAGE_STR;
        }
        else if (species.equals("human")) {
        	
        	this.startSequence = MIN_HUMAN_STAGE;
    	    this.start = MIN_HUMAN_STAGE_STR;
        }
        else if (species.equals("chick")) {
        	
        	this.startSequence = MIN_CHICK_STAGE;
    	    this.start = MIN_CHICK_STAGE_STR;
        }
        else  {
        	
        	this.startSequence = ERROR_STAGE;
    	    this.start = ERROR_STAGE_STR;
        }
    }

    public void setStartSequence( long startSequence, String species ) {
    	
        this.startSequence = startSequence;
    	
        if (species.equals("mouse")) {
            if (startSequence == 0) {
        	    this.start = "TS01";
        	}
        	else if (startSequence == 1) {
       	        this.start = "TS02";
        	}
        	else if (startSequence == 2) {
        	    this.start = "TS03";
        	}
        	else if (startSequence == 3) {
        	    this.start = "TS04";
        	}
        	else if (startSequence == 4) {
        	    this.start = "TS05";
        	}
        	else if (startSequence == 5) {
        	    this.start = "TS06";
        	}
        	else if (startSequence == 6) {
        	    this.start = "TS07";
        	}
        	else if (startSequence == 7) {
        	    this.start = "TS08";
        	}
        	else if (startSequence == 8) {
        	    this.start = "TS09";
        	}
        	else if (startSequence == 9) {
        	    this.start = "TS10";
        	}
        	else if (startSequence == 10) {
        	    this.start = "TS11";
        	}
        	else if (startSequence == 11) {
        	    this.start = "TS12";
        	}
        	else if (startSequence == 12) {
        	    this.start = "TS13";
        	}
        	else if (startSequence == 13) {
        	    this.start = "TS14";
        	}
        	else if (startSequence == 14) {
        	    this.start = "TS15";
        	}
        	else if (startSequence == 15) {
        	    this.start = "TS16";
        	}
        	else if (startSequence == 16) {
        	    this.start = "TS17";
        	}
        	else if (startSequence == 17) {
        	    this.start = "TS18";
        	}
        	else if (startSequence == 18) {
        	    this.start = "TS19";
        	}
        	else if (startSequence == 19) {
        	    this.start = "TS20";
        	}
        	else if (startSequence == 20) {
        	    this.start = "TS21";
        	}
        	else if (startSequence == 21) {
        	    this.start = "TS22";
        	}
        	else if (startSequence == 22) {
        	    this.start = "TS23";
        	}
        	else if (startSequence == 23) {
        	    this.start = "TS24";
        	}
        	else if (startSequence == 24) {
        	    this.start = "TS25";
        	}
        	else if (startSequence == 25) {
        	    this.start = "TS26";
        	}
        	else if (startSequence == 26) {
        	    this.start = "TS27";
        	}
        	else if (startSequence == 27) {
        	    this.start = "TS28";
        	}
    	}
    	else if (species.equals("human")) {
	    	if (startSequence == 0) {
	    		this.start = "CS01";
	    	}
	    	else if (startSequence == 1) {
	    		this.start = "CS02";
	    	}
	    	else if (startSequence == 2) {
	    	    this.start = "CS03";
	    	}
	    	else if (startSequence == 3) {
	    	    this.start = "CS04";
	    	}
	    	else if (startSequence == 4) {
	    	    this.start = "CS05a";
	    	}
	    	else if (startSequence == 5) {
	    	    this.start = "CS05b";
	    	}
	    	else if (startSequence == 6) {
	    	    this.start = "CS05c";
	    	}
	    	else if (startSequence == 7) {
	    	    this.start = "CS06a";
	    	}
	    	else if (startSequence == 8) {
	    	    this.start = "CS06b";
	    	}
	    	else if (startSequence == 9) {
	    	    this.start = "CS07";
	    	}
	    	else if (startSequence == 10) {
	    	    this.start = "CS08";
	    	}
	    	else if (startSequence == 11) {
	    	    this.start = "CS09";
	    	}
	    	else if (startSequence == 12) {
	    	    this.start = "CS10";
	    	}
	    	else if (startSequence == 13) {
	    	    this.start = "CS11";
	    	}
	    	else if (startSequence == 14) {
	    	    this.start = "CS12";
	    	}
	    	else if (startSequence == 15) {
	    	    this.start = "CS13";
	    	}
	    	else if (startSequence == 16) {
	    	    this.start = "CS14";
	    	}
	    	else if (startSequence == 17) {
	    	    this.start = "CS15";
	    	}
	    	else if (startSequence == 18) {
	    	    this.start = "CS16";
	    	}
	    	else if (startSequence == 19) {
	    	    this.start = "CS17";
	    	}
	    	else if (startSequence == 20) {
	    	    this.start = "CS18";
	    	}
	    	else if (startSequence == 21) {
	    	    this.start = "CS19";
	    	}
	    	else if (startSequence == 22) {
	    	    this.start = "CS20";
	    	}
	    	else if (startSequence == 23) {
	    	    this.start = "CS21";
	    	}
	    	else if (startSequence == 24) {
	    	    this.start = "CS22";
	    	}
	    	else if (startSequence == 25) {
	    	    this.start = "CS23";
	    	}

    	}
    	else if (species.equals("chick")) {
        	if (startSequence == 0) {
        	    this.start = "EGK-I";
        	}
        	else if (startSequence == 1) {
        	    this.start = "EGK-II";
        	}
        	else if (startSequence == 2) {
        	    this.start = "EGK-III";
        	}
        	else if (startSequence == 3) {
        	    this.start = "EGK-IV";
        	}
        	else if (startSequence == 4) {
        	    this.start = "EGK-V";
        	}
        	else if (startSequence == 5) {
        	    this.start = "EGK-VI";
        	}
        	else if (startSequence == 6) {
        	    this.start = "EGK-VII";
        	}
        	else if (startSequence == 7) {
        	    this.start = "EGK-VIII";
        	}
        	else if (startSequence == 8) {
        	    this.start = "EGK-IX";
        	}
        	else if (startSequence == 9) {
        	    this.start = "EGK-X";
        	}
        	else if (startSequence == 10) {
        	    this.start = "EGK-XI";
        	}
        	else if (startSequence == 11) {
        	    this.start = "EGK-XII";
        	}
        	else if (startSequence == 12) {
        	    this.start = "EGK-XIII";
        	}
        	else if (startSequence == 13) {
        	    this.start = "EGK-XIV";
        	}
        	else if (startSequence == 14) {
        	    this.start = "HH02";
        	}
        	else if (startSequence == 15) {
        	    this.start = "HH03";
        	}
        	else if (startSequence == 16) {
        	    this.start = "HH04";
        	}
        	else if (startSequence == 17) {
        	    this.start = "HH05";
        	}
        	else if (startSequence == 18) {
        	    this.start = "HH06";
        	}
        	else if (startSequence == 19) {
        	    this.start = "HH07";
        	}
        	else if (startSequence == 20) {
        	    this.start = "HH08";
        	}
        	else if (startSequence == 21) {
        	    this.start = "HH09";
        	}
        	else if (startSequence == 22) {
        	    this.start = "HH10";
        	}
        	else if (startSequence == 23) {
        	    this.start = "HH11";
        	}
        	else if (startSequence == 24) {
        	    this.start = "HH12";
        	}
        	else if (startSequence == 25) {
        	    this.start = "HH13";
        	}
        	else if (startSequence == 26) {
        	    this.start = "HH14";
        	}
        	else if (startSequence == 27) {
        	    this.start = "HH15";
        	}
        	else if (startSequence == 28) {
        	    this.start = "HH16";
        	}
        	else if (startSequence == 29) {
        	    this.start = "HH17";
        	}
        	else if (startSequence == 30) {
        	    this.start = "HH18";
        	}
        	else if (startSequence == 31) {
        	    this.start = "HH19";
        	}
        	else if (startSequence == 32) {
        	    this.start = "HH20";
        	}
        	else if (startSequence == 33) {
        	    this.start = "HH21";
        	}
        	else if (startSequence == 34) {
        	    this.start = "HH22";
        	}
        	else if (startSequence == 35) {
        	    this.start = "HH23";
        	}
        	else if (startSequence == 36) {
        	    this.start = "HH24";
        	}
        	else if (startSequence == 37) {
        	    this.start = "HH25";
        	}
        	else if (startSequence == 38) {
        	    this.start = "HH26";
        	}
        	else if (startSequence == 39) {
        	    this.start = "HH27";
        	}
        	else if (startSequence == 40) {
        	    this.start = "HH28";
        	}
        	else if (startSequence == 41) {
        	    this.start = "HH29";
        	}
        	else if (startSequence == 42) {
        	    this.start = "HH30";
        	}
        	else if (startSequence == 43) {
        	    this.start = "HH31";
        	}
        	else if (startSequence == 44) {
        	    this.start = "HH32";
        	}
        	else if (startSequence == 45) {
        	    this.start = "HH33";
        	}
        	else if (startSequence == 46) {
        	    this.start = "HH34";
        	}
        	else if (startSequence == 47) {
        	    this.start = "HH35";
        	}
        	else if (startSequence == 48) {
        	    this.start = "HH36";
        	}
        	else if (startSequence == 49) {
        	    this.start = "HH37";
        	}
        	else if (startSequence == 50) {
        	    this.start = "HH38";
        	}
        	else if (startSequence == 51) {
        	    this.start = "HH39";
        	}
        	else if (startSequence == 52) {
        	    this.start = "HH40";
        	}
        	else if (startSequence == 53) {
        	    this.start = "HH41";
        	}
        	else if (startSequence == 54) {
        	    this.start = "HH42";
        	}
        	else if (startSequence == 55) {
        	    this.start = "HH43";
        	}
        	else if (startSequence == 56) {
        	    this.start = "HH44";
        	}
        	else if (startSequence == 57) {
        	    this.start = "HH45";
        	}
        	else if (startSequence == 58) {
        	    this.start = "HH46";
        	}
        	else if (startSequence == 59) {
        	    this.start = "HH47";
        	}
        	else if (startSequence == 60) {
        	    this.start = "HH48";
        	}
    	}
    	else {
    		this.start = ERROR_STAGE_STR;
    	}
    }
    
    public void setEnd( String end ) {
    	
    	this.end = end;
    	
    	if (this.end.equals("TS01")) {
    		this.endSequence = 0;
    	}
    	else if (this.end.equals("TS02")) {
    		this.endSequence = 1;
    	}
    	else if  (this.end.equals("TS03")) {
    		this.endSequence = 2;
    	}
    	else if  (this.end.equals("TS04")) {
    		this.endSequence = 3;
    	}
    	else if  (this.end.equals("TS05")) {
    		this.endSequence = 4;
    	}
    	else if  (this.end.equals("TS06")) {
    		this.endSequence = 5;
    	}
    	else if  (this.end.equals("TS07")) {
    		this.endSequence = 6;
    	}
    	else if  (this.end.equals("TS08")) {
    		this.endSequence = 7;
    	}
    	else if  (this.end.equals("TS09")) {
    		this.endSequence = 8;
    	}
    	else if  (this.end.equals("TS10")) {
    		this.endSequence = 9;
    	}
    	else if  (this.end.equals("TS11")) {
    		this.endSequence = 10;
    	}
    	else if  (this.end.equals("TS12")) {
    		this.endSequence = 11;
    	}
    	else if  (this.end.equals("TS13")) {
    		this.endSequence = 12;
    	}
    	else if  (this.end.equals("TS14")) {
    		this.endSequence = 13;
    	}
    	else if  (this.end.equals("TS15")) {
    		this.endSequence = 14;
    	}
    	else if  (this.end.equals("TS16")) {
    		this.endSequence = 15;
    	}
    	else if  (this.end.equals("TS17")) {
    		this.endSequence = 16;
    	}
    	else if  (this.end.equals("TS18")) {
    		this.endSequence = 17;
    	}
    	else if  (this.end.equals("TS19")) {
    		this.endSequence = 18;
    	}
    	else if  (this.end.equals("TS20")) {
    		this.endSequence = 19;
    	}
    	else if  (this.end.equals("TS21")) {
    		this.endSequence = 20;
    	}
    	else if  (this.end.equals("TS22")) {
    		this.endSequence = 21;
    	}
    	else if  (this.end.equals("TS23")) {
    		this.endSequence = 22;
    	}
    	else if  (this.end.equals("TS24")) {
    		this.endSequence = 23;
    	}
    	else if  (this.end.equals("TS25")) {
    		this.endSequence = 24;
    	}
    	else if  (this.end.equals("TS26")) {
    		this.endSequence = 25;
    	}
    	else if  (this.end.equals("TS27")) {
    		this.endSequence = 26;
    	}
    	else if  (this.end.equals("TS28")) {
    		this.endSequence = 27;
    	}
    	else if (this.end.equals("CS01")) {
    		this.endSequence = 0;
    	}
    	else if (this.end.equals("CS02")) {
    		this.endSequence = 1;
    	}
    	else if (this.end.equals("CS03")) {
    		this.endSequence = 2;
    	}
    	else if (this.end.equals("CS04")) {
    		this.endSequence = 3;
    	}
    	else if (this.end.equals("CS05a")) {
    		this.endSequence = 4;
    	}
    	else if (this.end.equals("CS05b")) {
    		this.endSequence = 5;
    	}
    	else if (this.end.equals("CS05c")) {
    		this.endSequence = 6;
    	}
    	else if (this.end.equals("CS06a")) {
    		this.endSequence = 7;
    	}
    	else if (this.end.equals("CS06b")) {
    		this.endSequence = 8;
    	}
    	else if (this.end.equals("CS07")) {
    		this.endSequence = 9;
    	}
    	else if (this.end.equals("CS08")) {
    		this.endSequence = 10;
    	}
    	else if (this.end.equals("CS09")) {
    		this.endSequence = 11;
    	}
    	else if (this.end.equals("CS10")) {
    		this.endSequence = 12;
    	}
    	else if (this.end.equals("CS11")) {
    		this.endSequence = 13;
    	}
    	else if (this.end.equals("CS12")) {
    		this.endSequence = 14;
    	}
    	else if (this.end.equals("CS13")) {
    		this.endSequence = 15;
    	}
    	else if (this.end.equals("CS14")) {
    		this.endSequence = 16;
    	}
    	else if (this.end.equals("CS15")) {
    		this.endSequence = 17;
    	}
    	else if (this.end.equals("CS16")) {
    		this.endSequence = 18;
    	}
    	else if (this.end.equals("CS17")) {
    		this.endSequence = 19;
    	}
    	else if (this.end.equals("CS18")) {
    		this.endSequence = 20;
    	}
    	else if (this.end.equals("CS19")) {
    		this.endSequence = 21;
    	}
    	else if (this.end.equals("CS20")) {
    		this.endSequence = 22;
    	}
    	else if (this.end.equals("CS21")) {
    		this.endSequence = 23;
    	}
    	else if (this.end.equals("CS22")) {
    		this.endSequence = 24;
    	}
    	else if (this.end.equals("CS23")) {
    		this.endSequence = 25;
    	}
    	else if (this.end.equals("EGK-I")) {
    		this.endSequence = 0;
    	}
    	else if (this.end.equals("EGK-II")) {
    		this.endSequence = 1;
    	}
    	else if (this.end.equals("EGK-III")) {
    		this.endSequence = 2;
    	}
    	else if (this.end.equals("EGK-IV")) {
    		this.endSequence = 3;
    	}
    	else if (this.end.equals("EGK-V")) {
    		this.endSequence = 4;
    	}
    	else if (this.end.equals("EGK-VI")) {
    		this.endSequence = 5;
    	}
    	else if (this.end.equals("EGK-VII")) {
    		this.endSequence = 6;
    	}
    	else if (this.end.equals("EGK-VIII")) {
    		this.endSequence = 7;
    	}
    	else if (this.end.equals("EGK-IX")) {
    		this.endSequence = 8;
    	}
    	else if (this.end.equals("EGK-X")) {
    		this.endSequence = 9;
    	}
    	else if (this.end.equals("EGK-XI")) {
    		this.endSequence = 10;
    	}
    	else if (this.end.equals("EGK-XII")) {
    		this.endSequence = 11;
    	}
    	else if (this.end.equals("EGK-XIII")) {
    		this.endSequence = 12;
    	}
    	else if (this.end.equals("EGK-XIV")) {
    		this.endSequence = 13;
    	}
    	else if (this.end.equals("HH02")) {
    		this.endSequence = 14;
    	}
    	else if (this.end.equals("HH03")) {
    		this.endSequence = 15;
    	}
    	else if (this.end.equals("HH04")) {
    		this.endSequence = 16;
    	}
    	else if (this.end.equals("HH05")) {
    		this.endSequence = 17;
    	}
    	else if (this.end.equals("HH06")) {
    		this.endSequence = 18;
    	}
    	else if (this.end.equals("HH07")) {
    		this.endSequence = 19;
    	}
    	else if (this.end.equals("HH08")) {
    		this.endSequence = 20;
    	}
    	else if (this.end.equals("HH09")) {
    		this.endSequence = 21;
    	}
    	else if (this.end.equals("HH10")) {
    		this.endSequence = 22;
    	}
    	else if (this.end.equals("HH11")) {
    		this.endSequence = 23;
    	}
    	else if (this.end.equals("HH12")) {
    		this.endSequence = 24;
    	}
    	else if (this.end.equals("HH13")) {
    		this.endSequence = 25;
    	}
    	else if (this.end.equals("HH14")) {
    		this.endSequence = 26;
    	}
    	else if (this.end.equals("HH15")) {
    		this.endSequence = 27;
    	}
    	else if (this.end.equals("HH16")) {
    		this.endSequence = 28;
    	}
    	else if (this.end.equals("HH17")) {
    		this.endSequence = 29;
    	}
    	else if (this.end.equals("HH18")) {
    		this.endSequence = 30;
    	}
    	else if (this.end.equals("HH19")) {
    		this.endSequence = 31;
    	}
    	else if (this.end.equals("HH20")) {
    		this.endSequence = 32;
    	}
    	else if (this.end.equals("HH21")) {
    		this.endSequence = 33;
    	}
    	else if (this.end.equals("HH22")) {
    		this.endSequence = 34;
    	}
    	else if (this.end.equals("HH23")) {
    		this.endSequence = 35;
    	}
    	else if (this.end.equals("HH24")) {
    		this.endSequence = 36;
    	}
    	else if (this.end.equals("HH25")) {
    		this.endSequence = 37;
    	}
    	else if (this.end.equals("HH26")) {
    		this.endSequence = 38;
    	}
    	else if (this.end.equals("HH27")) {
    		this.endSequence = 39;
    	}
    	else if (this.end.equals("HH28")) {
    		this.endSequence = 40;
    	}
    	else if (this.end.equals("HH29")) {
    		this.endSequence = 41;
    	}
    	else if (this.end.equals("HH30")) {
    		this.endSequence = 42;
    	}
    	else if (this.end.equals("HH31")) {
    		this.endSequence = 43;
    	}
    	else if (this.end.equals("HH32")) {
    		this.endSequence = 44;
    	}
    	else if (this.end.equals("HH33")) {
    		this.endSequence = 45;
    	}
    	else if (this.end.equals("HH34")) {
    		this.endSequence = 46;
    	}
    	else if (this.end.equals("HH35")) {
    		this.endSequence = 47;
    	}
    	else if (this.end.equals("HH36")) {
    		this.endSequence = 48;
    	}
    	else if (this.end.equals("HH37")) {
    		this.endSequence = 49;
    	}
    	else if (this.end.equals("HH38")) {
    		this.endSequence = 50;
    	}
    	else if (this.end.equals("HH39")) {
    		this.endSequence = 51;
    	}
    	else if (this.end.equals("HH40")) {
    		this.endSequence = 52;
    	}
    	else if (this.end.equals("HH41")) {
    		this.endSequence = 53;
    	}
    	else if (this.end.equals("HH42")) {
    		this.endSequence = 54;
    	}
    	else if (this.end.equals("HH43")) {
    		this.endSequence = 55;
    	}
    	else if (this.end.equals("HH44")) {
    		this.endSequence = 56;
    	}
    	else if (this.end.equals("HH45")) {
    		this.endSequence = 57;
    	}
    	else if (this.end.equals("HH46")) {
    		this.endSequence = 58;
    	}
    	else if (this.end.equals("HH47")) {
    		this.endSequence = 59;
    	}
    	else if (this.end.equals("HH48")) {
    		this.endSequence = 60;
    	}
    	else {
    		this.endSequence = ERROR_STAGE;
    	}
    }
    
    public void setEndSequenceMax(String species) {

        if (species.equals("mouse")) {
        	
        	this.endSequence = MAX_MOUSE_STAGE;
    	    this.end = MAX_MOUSE_STAGE_STR;
        }
        else if (species.equals("human")) {
        	
        	this.endSequence = MAX_HUMAN_STAGE;
    	    this.end = MAX_HUMAN_STAGE_STR;
        }
        
        else if (species.equals("chick")) {
        	this.endSequence = MAX_CHICK_STAGE;
    	    this.end = MAX_CHICK_STAGE_STR;
        }
        else  {
        	
        	this.endSequence = ERROR_STAGE;
    	    this.end = ERROR_STAGE_STR;
        }
    }

    public void setEndSequence( long endSequence, String species ) {
    	
        this.endSequence = endSequence;
    	
        if (species.equals("mouse")) {
            if (endSequence == 0) {
        	    this.end = "TS01";
        	}
        	else if (endSequence == 1) {
       	        this.end = "TS02";
        	}
        	else if (endSequence == 2) {
        	    this.end = "TS03";
        	}
        	else if (endSequence == 3) {
        	    this.end = "TS04";
        	}
        	else if (endSequence == 4) {
        	    this.end = "TS05";
        	}
        	else if (endSequence == 5) {
        	    this.end = "TS06";
        	}
        	else if (endSequence == 6) {
        	    this.end = "TS07";
        	}
        	else if (endSequence == 7) {
        	    this.end = "TS08";
        	}
        	else if (endSequence == 8) {
        	    this.end = "TS09";
        	}
        	else if (endSequence == 9) {
        	    this.end = "TS10";
        	}
        	else if (endSequence == 10) {
        	    this.end = "TS11";
        	}
        	else if (endSequence == 11) {
        	    this.end = "TS12";
        	}
        	else if (endSequence == 12) {
        	    this.end = "TS13";
        	}
        	else if (endSequence == 13) {
        	    this.end = "TS14";
        	}
        	else if (endSequence == 14) {
        	    this.end = "TS15";
        	}
        	else if (endSequence == 15) {
        	    this.end = "TS16";
        	}
        	else if (endSequence == 16) {
        	    this.end = "TS17";
        	}
        	else if (endSequence == 17) {
        	    this.end = "TS18";
        	}
        	else if (endSequence == 18) {
        	    this.end = "TS19";
        	}
        	else if (endSequence == 19) {
        	    this.end = "TS20";
        	}
        	else if (endSequence == 20) {
        	    this.end = "TS21";
        	}
        	else if (endSequence == 21) {
        	    this.end = "TS22";
        	}
        	else if (endSequence == 22) {
        	    this.end = "TS23";
        	}
        	else if (endSequence == 23) {
        	    this.end = "TS24";
        	}
        	else if (endSequence == 24) {
        	    this.end = "TS25";
        	}
        	else if (endSequence == 25) {
        	    this.end = "TS26";
        	}
        	else if (endSequence == 26) {
        	    this.end = "TS27";
        	}
        	else if (endSequence == 27) {
        	    this.end = "TS28";
        	}
    	}
    	else if (species.equals("human")) {
	    	if (endSequence == 0) {
	    		this.end = "CS01";
	    	}
	    	else if (endSequence == 1) {
	    		this.end = "CS02";
	    	}
	    	else if (endSequence == 2) {
	    	    this.end = "CS03";
	    	}
	    	else if (endSequence == 3) {
	    	    this.end = "CS04";
	    	}
	    	else if (endSequence == 4) {
	    	    this.end = "CS05a";
	    	}
	    	else if (endSequence == 5) {
	    	    this.end = "CS05b";
	    	}
	    	else if (endSequence == 6) {
	    	    this.end = "CS05c";
	    	}
	    	else if (endSequence == 7) {
	    	    this.end = "CS06a";
	    	}
	    	else if (endSequence == 8) {
	    	    this.end = "CS06b";
	    	}
	    	else if (endSequence == 9) {
	    	    this.end = "CS07";
	    	}
	    	else if (endSequence == 10) {
	    	    this.end = "CS08";
	    	}
	    	else if (endSequence == 11) {
	    	    this.end = "CS09";
	    	}
	    	else if (endSequence == 12) {
	    	    this.end = "CS10";
	    	}
	    	else if (endSequence == 13) {
	    	    this.end = "CS11";
	    	}
	    	else if (endSequence == 14) {
	    	    this.end = "CS12";
	    	}
	    	else if (endSequence == 15) {
	    	    this.end = "CS13";
	    	}
	    	else if (endSequence == 16) {
	    	    this.end = "CS14";
	    	}
	    	else if (endSequence == 17) {
	    	    this.end = "CS15";
	    	}
	    	else if (endSequence == 18) {
	    	    this.end = "CS16";
	    	}
	    	else if (endSequence == 19) {
	    	    this.end = "CS17";
	    	}
	    	else if (endSequence == 20) {
	    	    this.end = "CS18";
	    	}
	    	else if (endSequence == 21) {
	    	    this.end = "CS19";
	    	}
	    	else if (endSequence == 22) {
	    	    this.end = "CS20";
	    	}
	    	else if (endSequence == 23) {
	    	    this.end = "CS21";
	    	}
	    	else if (endSequence == 24) {
	    	    this.end = "CS22";
	    	}
	    	else if (endSequence == 25) {
	    	    this.end = "CS23";
	    	}

    	}
    	else if (species.equals("chick")) {
        	if (endSequence == 0) {
        	    this.end = "EGK-I";
        	}
        	else if (endSequence == 1) {
        	    this.end = "EGK-II";
        	}
        	else if (endSequence == 2) {
        	    this.end = "EGK-III";
        	}
        	else if (endSequence == 3) {
        	    this.end = "EGK-IV";
        	}
        	else if (endSequence == 4) {
        	    this.end = "EGK-V";
        	}
        	else if (endSequence == 5) {
        	    this.end = "EGK-VI";
        	}
        	else if (endSequence == 6) {
        	    this.end = "EGK-VII";
        	}
        	else if (endSequence == 7) {
        	    this.end = "EGK-VIII";
        	}
        	else if (endSequence == 8) {
        	    this.end = "EGK-IX";
        	}
        	else if (endSequence == 9) {
        	    this.end = "EGK-X";
        	}
        	else if (endSequence == 10) {
        	    this.end = "EGK-XI";
        	}
        	else if (endSequence == 11) {
        	    this.end = "EGK-XII";
        	}
        	else if (endSequence == 12) {
        	    this.end = "EGK-XIII";
        	}
        	else if (endSequence == 13) {
        	    this.end = "EGK-XIV";
        	}
        	else if (endSequence == 14) {
        	    this.end = "HH02";
        	}
        	else if (endSequence == 15) {
        	    this.end = "HH03";
        	}
        	else if (endSequence == 16) {
        	    this.end = "HH04";
        	}
        	else if (endSequence == 17) {
        	    this.end = "HH05";
        	}
        	else if (endSequence == 18) {
        	    this.end = "HH06";
        	}
        	else if (endSequence == 19) {
        	    this.end = "HH07";
        	}
        	else if (endSequence == 20) {
        	    this.end = "HH08";
        	}
        	else if (endSequence == 21) {
        	    this.end = "HH09";
        	}
        	else if (endSequence == 22) {
        	    this.end = "HH10";
        	}
        	else if (endSequence == 23) {
        	    this.end = "HH11";
        	}
        	else if (endSequence == 24) {
        	    this.end = "HH12";
        	}
        	else if (endSequence == 25) {
        	    this.end = "HH13";
        	}
        	else if (endSequence == 26) {
        	    this.end = "HH14";
        	}
        	else if (endSequence == 27) {
        	    this.end = "HH15";
        	}
        	else if (endSequence == 28) {
        	    this.end = "HH16";
        	}
        	else if (endSequence == 29) {
        	    this.end = "HH17";
        	}
        	else if (endSequence == 30) {
        	    this.end = "HH18";
        	}
        	else if (endSequence == 31) {
        	    this.end = "HH19";
        	}
        	else if (endSequence == 32) {
        	    this.end = "HH20";
        	}
        	else if (endSequence == 33) {
        	    this.end = "HH21";
        	}
        	else if (endSequence == 34) {
        	    this.end = "HH22";
        	}
        	else if (endSequence == 35) {
        	    this.end = "HH23";
        	}
        	else if (endSequence == 36) {
        	    this.end = "HH24";
        	}
        	else if (endSequence == 37) {
        	    this.end = "HH25";
        	}
        	else if (endSequence == 38) {
        	    this.end = "HH26";
        	}
        	else if (endSequence == 39) {
        	    this.end = "HH27";
        	}
        	else if (endSequence == 40) {
        	    this.end = "HH28";
        	}
        	else if (endSequence == 41) {
        	    this.end = "HH29";
        	}
        	else if (endSequence == 42) {
        	    this.end = "HH30";
        	}
        	else if (endSequence == 43) {
        	    this.end = "HH31";
        	}
        	else if (endSequence == 44) {
        	    this.end = "HH32";
        	}
        	else if (endSequence == 45) {
        	    this.end = "HH33";
        	}
        	else if (endSequence == 46) {
        	    this.end = "HH34";
        	}
        	else if (endSequence == 47) {
        	    this.end = "HH35";
        	}
        	else if (endSequence == 48) {
        	    this.end = "HH36";
        	}
        	else if (endSequence == 49) {
        	    this.end = "HH37";
        	}
        	else if (endSequence == 50) {
        	    this.end = "HH38";
        	}
        	else if (endSequence == 51) {
        	    this.end = "HH39";
        	}
        	else if (endSequence == 52) {
        	    this.end = "HH40";
        	}
        	else if (endSequence == 53) {
        	    this.end = "HH41";
        	}
        	else if (endSequence == 54) {
        	    this.end = "HH42";
        	}
        	else if (endSequence == 55) {
        	    this.end = "HH43";
        	}
        	else if (endSequence == 56) {
        	    this.end = "HH44";
        	}
        	else if (endSequence == 57) {
        	    this.end = "HH45";
        	}
        	else if (endSequence == 58) {
        	    this.end = "HH46";
        	}
        	else if (endSequence == 59) {
        	    this.end = "HH47";
        	}
        	else if (endSequence == 60) {
        	    this.end = "HH48";
        	}
    	}
    	else {
    		this.end = ERROR_STAGE_STR;
    	}
    }
    
    public void setPresent( boolean present ){
        this.present = present;
    }
    public void setChildOfs( ArrayList<String> childOfs ) {
        this.childOfs = childOfs;
    }
    public void setChildOfNames( ArrayList<String> childOfNames ) {
        this.childOfNames = childOfNames;
    }
    public void setChildOfTypes( ArrayList<String> childOfTypes ) {
        this.childOfTypes = childOfTypes;
    }
    public void setSynonyms( ArrayList<String> synonyms ) {
        this.synonyms = synonyms;
    }
    public void setStatusChange(String statusChange){
        this.statusChange = statusChange;
    }
    public void setStatusRule(String statusRule){
        this.statusRule = statusRule;
    }
    public void setCheckComment(String comment){
        this.comments.add(comment);
    }
    public void setOrderComment(String s){
        this.orderComment = s;
    }
    public void setAlternativeIds( ArrayList<String> alternativeIds ) {
        this.alternativeIds = alternativeIds;
    }
    
    public void setFlagMissingRel(boolean flag){
        this.flagMissingRel = flag;
    } 
    public void setFlagMissingRel(String flag){
        this.flagMissingRel = ObjectConverter.convert(flag, Boolean.class);
    } 
    public void setFlagLifeTime(boolean flag){
        this.flagLifeTime = flag;
    }
    public void setFlagLifeTime(String flag){
        this.flagLifeTime = ObjectConverter.convert(flag, Boolean.class);
    }
    public void setIsPrimary(boolean isprimary){
        this.isPrimary = isprimary;
    }
    public void setIsPrimary(String isprimary){
        this.isPrimary = ObjectConverter.convert(isprimary, Boolean.class);
    }
    public void setPaths( Vector<DefaultMutableTreeNode[]> paths ){
        this.paths = paths;
    }
    public void setGroup(boolean group){
        this.group = group;
    }
    public void setGroup(String group){
        this.group = ObjectConverter.convert(group, Boolean.class);
    }
    public void setPrimaryPath( DefaultMutableTreeNode[] path ){
        this.primaryPath = path;
    }
    
    public void setTimedComponents( ArrayList<String> timedComponents ){
        this.timedComponents = timedComponents;
    }
    public void addTimedComponent( String timedComponent ){
        this.timedComponents.add( timedComponent );
    }

    // Helpers ------------------------------------------------------------------------------------
    public void addAlternative( String alternative ) {
        this.alternativeIds.add( alternative ); 
    }
    public void addChildOf( String childOf ) {
        this.childOfs.add( childOf ); 
    }
    public void addChildOfName( String childOfName ) {
        this.childOfNames.add( childOfName ); 
    }
    public int getChildOfIndex( String childOf ) {
    	
        Iterator<String> iteratorChildOfs = this.getChildOfs().iterator();
        
        int index = -1;

        while (iteratorChildOfs.hasNext()) {
        	
        	String thisChildOf = iteratorChildOfs.next();
        	index++;

        	if ( thisChildOf.equals(childOf) ) {
        		
        		return index;
        	}
        }
        
        return -1;
    }
    public void setChildOfFullString() {
    	
        Iterator<String> iteratorChildOfs = this.getChildOfs().iterator();
       
        String output = "";
        String thisChildOf = "";
        String thisChildOfType = "";
        
        int index = 0;
        int count = 0;

        while (iteratorChildOfs.hasNext()) {
        	
        	count++;
        	
        	thisChildOf = iteratorChildOfs.next();
        	index = getChildOfIndex( thisChildOf );

        	thisChildOfType = this.childOfTypes.get(index);
        	
        	if ( count == 1 ) {
        		
            	output = output + thisChildOfType + "-" + thisChildOf;
        	}
        	else {
        		
            	output = output + "," + thisChildOfType + "-" + thisChildOf;
        	}
        	
        }
        
        this.fullRelations = output;
    }
    public void addChildOfType( String childOfType ) {
        this.childOfTypes.add( childOfType ); 
    }
    public void removeAlternative( String alternative ){
        this.alternativeIds.remove( alternative );
    }
    public void removeTimedComponent( String timedComponent ){
        this.timedComponents.remove( timedComponent );
    }
    public void removeChildOf( String childOf ){
        this.childOfs.remove( childOf );
    }
    public void removeChildOfName( String childOfName ){
        this.childOfNames.remove( childOfName );
    }
    public void removeChildOfType( String childOfType ){
        this.childOfTypes.remove( childOfType );
    }
    public void addSynonym( String synonym ) {
        this.synonyms.add( synonym );
    }
    public void clearCheckComment(){
        this.comments.clear();
        this.comments.addAll(this.userComments);
    }
    public void addUserComments( String s ){
        this.userComments.add(s);
    }
    public void addPaths( DefaultMutableTreeNode[] path ){
        this.paths.add(path);
    }
    public void addOrderComment( String s ){
    	
    	if (this.orderComment.equals("")) {
    		
    		this.orderComment = s;
    	}
    	else {

    		this.orderComment = this.orderComment + "\\n" + s;
    	}
    }

    public Vector<DefaultMutableTreeNode[]> getShortenedPaths(){

        Vector<DefaultMutableTreeNode[]> longPaths = new Vector<DefaultMutableTreeNode[]>();
        Vector<DefaultMutableTreeNode[]> shortPaths = new Vector<DefaultMutableTreeNode[]>();
        
        if ( !this.paths.isEmpty() ){
        	
            longPaths.addAll(this.paths);
            
            for(int i = 0; i< longPaths.size(); i++){
            
            	DefaultMutableTreeNode[] longPath = longPaths.get(i);
                
            	if (longPath.length > 0) {
                
            		DefaultMutableTreeNode[] shortPath =
                            new DefaultMutableTreeNode[longPath.length - 1];
                    
            		System.arraycopy(longPath, 1, 
                            shortPath, 0,
                            longPath.length - 1);
                    
            		shortPaths.add(shortPath);
                }
            }
        }

        return shortPaths;
    }
    
    public DefaultMutableTreeNode[] getShortenedPrimaryPath(){
        
        if (this.primaryPath != null){
        	
            DefaultMutableTreeNode[] longPath = this.primaryPath;        
            DefaultMutableTreeNode[] shortPath =
                    new DefaultMutableTreeNode[longPath.length - 1];
            System.arraycopy(longPath, 1, shortPath, 0, longPath.length - 1); 
            
            return shortPath;
        }
        else {
        	
            DefaultMutableTreeNode nullNode = new DefaultMutableTreeNode("N/A");
            DefaultMutableTreeNode[] shortPath = new DefaultMutableTreeNode[1];
            shortPath[0] = nullNode;
            
            return shortPath;
        }
    }
    
    public DefaultMutableTreeNode[] shortenPath(
            DefaultMutableTreeNode[] longPath ){

        DefaultMutableTreeNode[] shortPath =
                new DefaultMutableTreeNode[longPath.length - 1];
        
        System.arraycopy(longPath, 1, shortPath, 0, longPath.length - 1); 
        
        return shortPath;
    }

    /*
     * Find Clone this OBOOBOComponent
     */
    public OBOComponent cloneOBOOBOComponent(){
    	
        OBOComponent copyobocomponent = new OBOComponent();
        
        copyobocomponent.setID( this.getID() );
        copyobocomponent.setDisplayId( this.getDisplayId() );
        copyobocomponent.setDBID( this.getDBID() );
        copyobocomponent.setName( this.getName() );
        copyobocomponent.setNamespace( this.getNamespace() );
        copyobocomponent.setChildOfs( this.getChildOfs() );
        copyobocomponent.setChildOfTypes( this.getChildOfTypes() );
        copyobocomponent.setStart( this.getStart() );
        copyobocomponent.setEnd( this.getEnd() );
        copyobocomponent.setPresent( this.isPresent() );
        copyobocomponent.setSynonyms( this.getSynonyms() );
        copyobocomponent.setStatusChange( this.getStatusChange() );
        copyobocomponent.setStatusRule( this.getStatusRule() );
        copyobocomponent.setFlagMissingRel( this.getFlagMissingRel() );
        copyobocomponent.setFlagLifeTime( this.getFlagLifeTime() );

        Set<String> copyComments = this.getCheckComments();       
        
        for (String s: copyComments){
        	
        	copyobocomponent.setCheckComment(s);
        }
        
        copyobocomponent.setIsPrimary( this.isPrimary() );
        copyobocomponent.setPrimaryPath( this.getPrimaryPath() );

        copyobocomponent.setPaths( this.getPaths() );
        
        return copyobocomponent;
        
    }

    /*
     * Is this OBOOBOComponent the same as the Supplied OBOOBOComponent?
     */
    public boolean isOBOComponentSameAs(OBOComponent obocomponent){
    	
        //EMAP id, description, start stage, end stage, parents, synonyms
        ArrayList<String> orderdiff = null;

        orderdiff =  this.compareOrderComments(obocomponent, orderdiff);

        /*
        if ( this.getID().equals("EMAPA:32806") 
        	|| ( obocomponent.getID().equals("EMAPA:32806") ) ) {
        	
            System.out.println("this.getName()         : " + this.getName());
            System.out.println("obocomponent.getName() : " + obocomponent.getName());
        }
        */
        
        if ( this.getID().equals(obocomponent.getID()) && 
             this.getName().equals(obocomponent.getName()) &&
             this.getStartSequence() == obocomponent.getStartSequence() &&
             this.getEndSequence() == obocomponent.getEndSequence() &&
             this.getChildOfs().containsAll(obocomponent.getChildOfs()) &&
             obocomponent.getChildOfs().containsAll(this.getChildOfs()) && 
             this.getSynonyms().containsAll(obocomponent.getSynonyms()) && 
             obocomponent.getSynonyms().containsAll(this.getSynonyms()) &&
             orderdiff==null ){

        	return true;
        }
        else {
        	
        	return false;
        }
    }
    
    public ArrayList<String> getDifferenceWith(OBOComponent obocomponent){

        ArrayList<String> arrDifferenceWith = new ArrayList<String>();
        ArrayList<String> arrDiffOrder = null;
        
        if ( !this.getID().equals(obocomponent.getID()) ) {
        	
            arrDifferenceWith.add( "Different ID - Referenced OBOComponent " +
                   "has ID [" + this.getID() + "]" );
        }

        if ( !this.getName().equals(obocomponent.getName())) {
        	
            arrDifferenceWith.add( "Different Name - Referenced OBOComponent " +
            		this.getID() + " has name [" +
            		this.getName() + "]" );
        }

        if ( this.getStartSequence() != obocomponent.getStartSequence() ) {
        	
            arrDifferenceWith.add( "Different Start Stage - Referenced " +
                    "OBOComponent " + this.getID() + " has Start Stage " +
                    this.getStart() );
        }

        if ( this.getEndSequence() != obocomponent.getEndSequence() ) {
        	
            arrDifferenceWith.add( "Different End Stage - Referenced " +
                    "OBOComponent " + this.getID() + " has End Stage " +
                    this.getEnd() );
        }
        /*
       	if (this.getID().equals("EMAPA:35041")) {
            //System.out.println("OBOComponent.java");
            //System.out.println("------------");
            //System.out.println("this.getChildOfs().toString() " + this.getChildOfs().toString());
            //System.out.println("obocomponent.getChildOfs().toString() " + obocomponent.getChildOfs().toString());
            //System.out.println("------------");
       	}
       	*/
       	
       	ArrayList<String> newParents = new ArrayList<String>();
        ////System.out.println("Finding NEW PARENTS");
        String thisChildOf = "";
        String thatChildOf = "";
        
        Iterator<String> iteratorThisChildOfs = this.getChildOfs().iterator();
        Iterator<String> iteratorThatChildOfs = obocomponent.getChildOfs().iterator();

        if ( this.getChildOfs().size() > 0 && 
        	obocomponent.getChildOfs().size() > 0 ) {
        	
            while (iteratorThisChildOfs.hasNext()) {
            	
            	thisChildOf = iteratorThisChildOfs.next();
            	
            	while ( iteratorThatChildOfs.hasNext() ){

                	thatChildOf = iteratorThatChildOfs.next();

                	if ( !thisChildOf.equals(thatChildOf) ) {

                		/*
                        //System.out.println("MATCH Found!");
                        //System.out.println("thisChildOf = " + thisChildOf);
                        //System.out.println("thatChildOf = " + thatChildOf);
                        
                	}
                	else {
                		*/
                        newParents.add(thisChildOf);

                        /*
                        //System.out.println("MIS-MATCH Found!");
                        //System.out.println("thisChildOf = " + thisChildOf);
                        //System.out.println("thatChildOf = " + thatChildOf);
                        */
                	}
            	}
            	
            	iteratorThatChildOfs = obocomponent.getChildOfs().iterator();
          	}

            if (  newParents.size() > 0 ) {
            	
            	arrDifferenceWith.add( "New Parents - Referenced OBOComponent " +
            			this.getID() + " New has parents " + 
                        newParents.toString());
            }
        }
        
        if ( !obocomponent.getChildOfs().containsAll(this.getChildOfs()) ) {

        	/*
           	if (this.getID().equals("EMAPA:18305")) {
                //System.out.println("HERE!");
           	}
           	*/
        	arrDifferenceWith.add( "Different Parents - Referenced OBOComponent " +
        			this.getID() + " has parents " + 
                    this.getChildOfs().toString());
                    //obocomponent.getChildOfs().toString());
        }

        if ( !this.getChildOfs().containsAll(obocomponent.getChildOfs()) ) {
           	
        	/*
           	if (this.getID().equals("EMAPA:18305")) {
                //System.out.println("THERE!");
           	}
           	*/
            arrDifferenceWith.add( "Different Parents - Referenced OBOComponent " +
            		this.getID() + " has parents " + 
                       this.getChildOfs().toString());
                       //obocomponent.getChildOfs().toString());
        }

        if ( !this.isPrimary() == obocomponent.isPrimary() ) {

        	String strPrimary = "";
            
        	if ( this.isPrimary()){
        		
                strPrimary = "Primary";
            }
            else {
            	
                strPrimary = "Group";
            }

            arrDifferenceWith.add( "Different Primary Status - " +
                    "Referenced OBOComponent was a " + strPrimary +
                    " component." );
        }


        if ( this.getSynonyms().isEmpty() && obocomponent.getSynonyms().isEmpty() ) {
        	
            //System.out.println("Weve got NO Synonyms HERE!");
            
            //System.out.println("this.getSynonyms() = " + this.getSynonyms());
            //System.out.println("obocomponent.getSynonyms() = " + obocomponent.getSynonyms());
        }
        else {
        	
        	//System.out.println("Weve got SOME Synonyms SOMEWHERE!");

        	//System.out.println("this.getSynonyms() = " + this.getSynonyms());
            //System.out.println("obocomponent.getSynonyms() = " + obocomponent.getSynonyms());

            if ( !this.getSynonyms().containsAll(obocomponent.getSynonyms()) ||
                !obocomponent.getSynonyms().containsAll(this.getSynonyms()) ) {
            
            	arrDifferenceWith.add( "Different Synonyms - Referenced OBOComponent " + 
                   		this.getID() + " has synonyms " + 
                   		this.getSynonyms().toString() );
            }
        }

        arrDiffOrder = this.compareOrderComments(obocomponent, arrDiffOrder);

        if ( arrDiffOrder!=null ){
        	
            arrDifferenceWith.addAll(arrDiffOrder);
        }

        return arrDifferenceWith;
    }

    /*
     * Find comments that have changed
     */
    public Set<String> getDifferenceComments(){
        
        Set<String> allComments = this.getCheckComments();
        Set<String> changedComments = new TreeSet<String>();
        
        for (String s: allComments){
        	
            if (s.startsWith("Different")){
            	
                changedComments.add(s);
            } 
        }
        
        return changedComments;
    }

    /*
     * Compare Comments with supplied string.
     */
    public boolean hasDifferenceComment(String strDifferent){
        
        Set<String> allComments = this.getCheckComments();

        for (String s: allComments){
        	
            if ( s.startsWith(strDifferent) ) {
            	
            	return true;
            }
        }
        
        return false;
    }

    /*
     * Determine if the Comments contain a String fragment
     */
    public boolean commentsContain(String fragment){

        Set<String> strComments = this.getCheckComments();

        for( String s: strComments ){
        	
            if ( s.contains(fragment) ) {
            	
                return true;
            }
        } 
        
        return false;
    }

    /*
     * If the Comments are New, refresh the comments
     */
    public Set<String> getNewComments(){
        
        Set<String> allComments = this.getCheckComments();
        Set<String> changedComments = new TreeSet<String>();
        
        for (String s: allComments){
        	
            if (s.startsWith("New")){
            	
                changedComments.add(s);
            } 
        }
        return changedComments;
    }

    /*
     * Return an array of Comments
     */
    public String[] getOrderComments(){

    	Vector< String > vComments = new Vector<String>();
        vComments.addAll(this.userComments);
        String unprocessed = "";
        int intValidString = 0;

        this.orderComment = "";

        for ( int i=0; i < vComments.size(); i++ ){
        	
            if ( vComments.get(i).contains("order=") ){
            	
                this.orderComment = this.orderComment + vComments.get(i).trim();
            }
        }

        if ( !this.orderComment.equals("") ){
        	
            String[] orderArray = this.orderComment.split("order=");
            String[] processedOrderArray = null;
            
            for (int i=0; i<orderArray.length; i++){
            	
                unprocessed = orderArray[i];
                unprocessed = unprocessed.replace("\n", "");
                unprocessed = unprocessed.trim();
                
                // Chop off trailing full stops!!
                if ( unprocessed.length() > 1 ) {
                	
                    if ( unprocessed.substring( 0, unprocessed.length() - 1 ).equals(".") ) {
                    	
                    	unprocessed = unprocessed.substring( 0, unprocessed.length() - 1 );
                    }
                }
               
                orderArray[i] = unprocessed;
                
                if ( !unprocessed.equals("") ) {
                	
                    intValidString++;
                }
            }
            if (intValidString > 0){
            	
                int j = 0;
                processedOrderArray = new String[intValidString];
                
                for (int i=0; i<orderArray.length; i++){
                	
                    if ( !orderArray[i].equals("") ){
                    	
                        processedOrderArray[j] = orderArray[i];
                        j++;
                    }
                }
            }

            this.orderComment = "";

            for ( int i=0; i < processedOrderArray.length; i++ ){
            	
                this.orderComment = this.orderComment + ", order=" + processedOrderArray[i];
            }
            
            return processedOrderArray;
        }

        return null;
    }

    /*
     * For  given Parent ID, return the order Comments
     */
    public String[] getOrderCommentOnParent(String parentid){

        String[] ordercomments = this.getOrderComments();
        ArrayList<String> arrBased = new ArrayList<String>();

        if ( ordercomments!=null ){
        	
            for (int i=0; i < ordercomments.length; i++){
            	
            	if ( ordercomments[i].contains(parentid) ){
            		
                    arrBased.add(ordercomments[i]);
                }
            }
            if (arrBased.isEmpty()){
            	
                return null; 
            }
            else {
            	
                return arrBased.toArray( new String[arrBased.size()] );
            }
        }
        else {
        	
            return null;
        }
    }

    /*
     * Determine whether the order comments are correct.
     */
    public boolean hasIncorrectOrderComments() throws Exception{

        String[] results = new String[this.userComments.size()];
        ArrayList<String> parents = new ArrayList<String>();
        int intCounter = 0;
        boolean foundIncorrect = false;
        boolean foundParent = false;
        String strNumber = "";

        Vector< String > vComments = new Vector<String>();
        vComments.addAll(this.userComments);

        for ( int i=0; i < vComments.size(); i++ ){
        	
            if ( vComments.get(i).contains("order") ){
            	
                results[intCounter] = vComments.get(i).trim();
                intCounter++;
            }
        }

        //go through each string with 'order'
        for (int k=0; k<results.length && results[k]!=null; k++){
        	
            foundIncorrect = false;
            
            //check that string order= is in the string
            if ( !results[k].contains("order=") ){
            	
        	    Wrapper.printMessage("checkcomponentstablesordering.hasIncorrectOrderComments:" + 
        	            this.id + "Ordering: There is an order " +
                        "comment that does not have an = sign " +
                        "immediately after the term order", "*", "*");

                this.setCheckComment("Ordering: There is an order comment " +
                        "that does not have an = sign immediately after " +
                        "the term order");
                
                foundIncorrect = true;
            }
            //ir order= is in place check that immediately
            //after that is a valid integer
            else if (foundIncorrect==false){

            	try{
                
            		strNumber = results[k];
                    strNumber = strNumber.replaceAll("order=", "");
                }
                catch(NumberFormatException nEx){

            	    Wrapper.printMessage("checkcomponentstablesordering.hasIncorrectOrderComments:" + 
            	            this.id + "Ordering: There is an " +
                            "order comment that does not have a number " +
                            "immediately after order=; offending string = " +
                            strNumber.split(" ")[0], "*", "*");

            	    this.setCheckComment("Ordering: There is an order " +
                            "comment that does not have a number immediately " +
                            "after order=");
                    foundIncorrect = true;
                }
            }
            
            //check that parent in order comment is a valid parent
            parents.addAll( this.getChildOfs() );

            for (int i=0; i<parents.size() && !foundParent; i++){
            	
                if ( results[k].contains(parents.get(i)) ) {
                	
                    foundParent = true;
                }
            }
            
            if (!foundParent){
            	
        	    Wrapper.printMessage("checkcomponentstablesordering.hasIncorrectOrderComments:" + 
        	    		this.id + "Ordering: There is an order " +
                        "comment that does not reference a valid parent", "*", "*");

                this.setCheckComment("Ordering: There is an order comment " +
                        "that does not reference a valid parent");
                foundIncorrect = true;
            }
        }
        return foundIncorrect;
    }

    /* 
    * Compare the Order Comments
    */
    public ArrayList<String> compareOrderComments(OBOComponent obocomponent, ArrayList<String> diff){
        //find new/additional comments
        
        boolean notFound = true;

        if (this.getOrderComments() == null &&
        		obocomponent.getOrderComments() == null){
        	
            return diff;
        }
        else if (this.getOrderComments() == null &&
        		obocomponent.getOrderComments() != null){
        	
            for (int i=0; i<obocomponent.getOrderComments().length; i++){
            	
                if (diff == null){
                	
                    diff = new ArrayList<String>();
                }
                
                diff.add( "Different Order - This obocomponent has no order " +
                        "entries; Referenced OBOOBOComponent (" + obocomponent.getID() +
                        ") has order entry <" + obocomponent.getOrderComments()[i] +
                        ">");
            }
            return diff;
        }
        else if (this.getOrderComments() != null &&
        		obocomponent.getOrderComments() == null){
        	
            for (int i=0; i<this.getOrderComments().length; i++){
            	
                if (diff==null){
                	
                     diff = new ArrayList<String>();
                }
                
                diff.add( "Different Order - Referenced OBOOBOComponent has no " +
                        "order entries; This OBOOBOComponent has order entry <" +
                        this.getOrderComments()[i] + ">");
            }
            return diff;
        }
        else{
            for ( String orderobocomponent: this.getOrderComments() ){
            	
                notFound = true;
                //reset notfound for each comment
                
                for ( int i=0; i<obocomponent.getOrderComments().length &&
                        notFound &&
                        !orderobocomponent.equals("");
                      i++ ){
                	
                    //found matching comment stop inner loop
                    if ( orderobocomponent.equals( obocomponent.getOrderComments()[i] ) ){
                    	
                        notFound = false;
                    }
                }

                if (notFound && !orderobocomponent.equals("")){
                	
                //iterated through all comments in referenced obocomponent and
                // no matching comment found
                    if (diff == null){
                    	
                        diff = new ArrayList<String>();
                    }
                    
                    diff.add( "Different Order - This OBOOBOComponent has order entry <" +
                    		orderobocomponent + ">");
                }
            }
            //find deleted/missing comments
            for ( String orderobocomponent: obocomponent.getOrderComments() ){
            	
                //reset notfound for each comment
                notFound = true; 
                for ( int i=0; i<this.getOrderComments().length &&
                      notFound &&
                      !orderobocomponent.equals("");
                      i++ ){
                	
                    if ( orderobocomponent.equals( this.getOrderComments()[i] ) ){
                    	
                        notFound = false; 
                        //found matching comment stop inner loop
                    }
                }
                
                //iterated through all comments in referenced obocomponent and
                // no matching comment found
                if (notFound && !orderobocomponent.equals("")){
                	
                    if (diff == null){
                    	
                        diff = new ArrayList<String>();
                    }
                    
                    diff.add( "Different Order - Referenced OBOOBOComponent (" + 
                    		obocomponent.getID() + ") has order entry <" + orderobocomponent +
                            ">");
                }
            }

            return diff;
        }
    }
    

    /*
     * The relation ID is unique for each Thing. So this should compare Thing by ID only.
     */
    public boolean equals(Object other) {
    	
        return (other instanceof OBOComponent) && (name != null) 
        		? name.equals(((OBOComponent) other).name) 
        		: (other == this);
    }

    /*
     * Returns the String representation of this User. Not required, it just makes reading logs easier.
     */
    public String toString() {
    	
        return String.format("\nOBOComponent [ id=%s, name=%s, statusChange=%s, statusRule=%s, dbID=%s, newid=%s, namespace=%s, group=%b, start=%s, end=%s, present=%b ]", 
        		id, name, statusChange, statusRule, dbID, newid, namespace, group, start, end, present);
    }

    /*
     * Returns the String representation of this User. Not required, it just makes reading logs easier.
     */
    public String toStringFull() {
    	
        return String.format("%s,%s,%s,%s,%s,%b", 
        		id, start, end, fullRelations, name, group);
    }
    
    /*
     * Implement the Comparable Interface to enable ordering of lists of OBOComponents based on ID
     */
    public int compareTo(OBOComponent obocomponent) {
        int lastCmp = id.toUpperCase().compareTo((obocomponent.id).toUpperCase());
        return (lastCmp != 0 ? lastCmp : id.compareTo(obocomponent.id));
    }

}
