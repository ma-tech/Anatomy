/*
################################################################################
# Project:      Anatomy
#
# Title:        Component.java
#
# Date:         2008
#
# Author:       MeiSze Lam and Attila Gyenesi
#
# Copyright:    2009 Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; September 2010; Tidy up and Document
#
################################################################################
*/

package backend;

import java.util.ArrayList;

import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

public class Component {

    // component fields
    private String name;
    private String id;
    private String dbID;
    private String newid;
    private String namespace;
    private ArrayList < String > partOf;
    private String isA;
    private Integer startsAt;
    private Integer endsAt;
    private Integer presentIn;
    private ArrayList < String > synonym;
    private ArrayList < String > hasTimeComponent;
    private String timeComponentOf;
    private ArrayList < String > groupPartOf;
    private ArrayList < String > userComments;
    private String orderComment;
    
    //component checking variables here

    //missing relationships, starts < end
    private boolean flagMissingRel;

    //child node lifetime not within parent lifetime
    private boolean flagLifeTime;

    //component is new, changed, or deleted "UNCHANGED";
    //                                      "NEW";
    //                                      "CHANGED";
    //                                      "DELETED"
    private String strChangeStatus;

    //component is "UNCHECKED"; 
    //             "PASSED";
    //             "FAILED";
    private String strRuleStatus;

    //store unique comments only
    private Set<String> comments; 
    
    //path variables
    private boolean isPrimary;
    private DefaultMutableTreeNode[] primaryPath;
    private Vector < DefaultMutableTreeNode[] > paths;
    
    public Component() {
        this.name = "";
        this.id = "";
        this.dbID = "";
        this.newid = "";
        this.namespace = "";
        this.partOf = new ArrayList < String >();
        this.isA = "";
        this.startsAt = -1;
        this.endsAt = -1;
        this.presentIn = 0;
        this.synonym = new ArrayList < String >();
        this.hasTimeComponent = new ArrayList < String >();
        this.timeComponentOf = "";
        this.groupPartOf = new ArrayList < String >();
        this.userComments = new ArrayList < String >();
        this.orderComment = "";
        
        this.flagMissingRel = false;
        this.flagLifeTime = false;
        this.strChangeStatus = "UNCHECKED";
        this.strRuleStatus = "UNCHECKED";
        this.comments = new TreeSet();
        
        this.isPrimary = true;
        this.primaryPath = null;
        this.paths = new Vector<DefaultMutableTreeNode[]>();
        
    }
    
    public void setID( String id ) {
        this.id = id;
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

    public void setPartOf( ArrayList < String > partOf ) {
        this.partOf = partOf;
    }
    
    public void addPartOf( String partPartOf ) {
        this.partOf.add( partPartOf ); 
    }

    public void addIsA( String isIsA ) {
        this.isA = isIsA ;
    }

    public void removePartOf( String partPartOf ){
        this.partOf.remove( partPartOf );
    }
    
    public void setIsA( String isA ) {
        this.isA = isA;
    }

    public void setStartsAt( String startsAt ) {
        if (startsAt.length() >= 4){
            if (startsAt.equals("TS01")) this.startsAt = 0;
            if (startsAt.equals("TS02")) this.startsAt = 1;
            if (startsAt.equals("TS03")) this.startsAt = 2;
            if (startsAt.equals("TS04")) this.startsAt = 3;
            if (startsAt.equals("TS05")) this.startsAt = 4;
            if (startsAt.equals("TS06")) this.startsAt = 5;
            if (startsAt.equals("TS07")) this.startsAt = 6;
            if (startsAt.equals("TS08")) this.startsAt = 7;
            if (startsAt.equals("TS09")) this.startsAt = 8;
            if (startsAt.equals("TS10")) this.startsAt = 9;
            if (startsAt.equals("TS11")) this.startsAt = 10;
            if (startsAt.equals("TS12")) this.startsAt = 11;
            if (startsAt.equals("TS13")) this.startsAt = 12;
            if (startsAt.equals("TS14")) this.startsAt = 13;
            if (startsAt.equals("TS15")) this.startsAt = 14;
            if (startsAt.equals("TS16")) this.startsAt = 15;
            if (startsAt.equals("TS17")) this.startsAt = 16;
            if (startsAt.equals("TS18")) this.startsAt = 17;
            if (startsAt.equals("TS19")) this.startsAt = 18;
            if (startsAt.equals("TS20")) this.startsAt = 19;
            if (startsAt.equals("TS21")) this.startsAt = 20;
            if (startsAt.equals("TS22")) this.startsAt = 21;
            if (startsAt.equals("TS23")) this.startsAt = 22;
            if (startsAt.equals("TS24")) this.startsAt = 23;
            if (startsAt.equals("TS25")) this.startsAt = 24;
            if (startsAt.equals("TS26")) this.startsAt = 25;
            if (startsAt.equals("TS27")) this.startsAt = 26;
            if (startsAt.equals("TS28")) this.startsAt = 27;
            if (startsAt.equals("CS01")) this.startsAt = 0;
            if (startsAt.equals("CS02")) this.startsAt = 1;
            if (startsAt.equals("CS03")) this.startsAt = 2;
            if (startsAt.equals("CS04")) this.startsAt = 3;
            if (startsAt.equals("CS05a")) this.startsAt =4;
            if (startsAt.equals("CS05b")) this.startsAt =5;
            if (startsAt.equals("CS05c")) this.startsAt =6;
            if (startsAt.equals("CS06a")) this.startsAt =7;
            if (startsAt.equals("CS06b")) this.startsAt =8;
            if (startsAt.equals("CS07")) this.startsAt = 9;
            if (startsAt.equals("CS08")) this.startsAt = 10;
            if (startsAt.equals("CS09")) this.startsAt = 11;
            if (startsAt.equals("CS10")) this.startsAt = 12;
            if (startsAt.equals("CS11")) this.startsAt = 13;
            if (startsAt.equals("CS12")) this.startsAt = 14;
            if (startsAt.equals("CS13")) this.startsAt = 15;
            if (startsAt.equals("CS14")) this.startsAt = 16;
            if (startsAt.equals("CS15")) this.startsAt = 17;
            if (startsAt.equals("CS16")) this.startsAt = 18;
            if (startsAt.equals("CS17")) this.startsAt = 19;
            if (startsAt.equals("CS18")) this.startsAt = 20;
            if (startsAt.equals("CS19")) this.startsAt = 21;
            if (startsAt.equals("CS20")) this.startsAt = 22;
            if (startsAt.equals("CS21")) this.startsAt = 23;
            if (startsAt.equals("CS22")) this.startsAt = 24;
            if (startsAt.equals("CS23")) this.startsAt = 25;
        }
        else {
            this.startsAt = -1;
        }
    }

    public void setEndsAt( String endsAt ) {
        if (endsAt.length() >= 4){
            if (endsAt.equals("TS01")) this.endsAt = 0;
            if (endsAt.equals("TS02")) this.endsAt = 1;
            if (endsAt.equals("TS03")) this.endsAt = 2;
            if (endsAt.equals("TS04")) this.endsAt = 3;
            if (endsAt.equals("TS05")) this.endsAt = 4;
            if (endsAt.equals("TS06")) this.endsAt = 5;
            if (endsAt.equals("TS07")) this.endsAt = 6;
            if (endsAt.equals("TS08")) this.endsAt = 7;
            if (endsAt.equals("TS09")) this.endsAt = 8;
            if (endsAt.equals("TS10")) this.endsAt = 9;
            if (endsAt.equals("TS11")) this.endsAt = 10;
            if (endsAt.equals("TS12")) this.endsAt = 11;
            if (endsAt.equals("TS13")) this.endsAt = 12;
            if (endsAt.equals("TS14")) this.endsAt = 13;
            if (endsAt.equals("TS15")) this.endsAt = 14;
            if (endsAt.equals("TS16")) this.endsAt = 15;
            if (endsAt.equals("TS17")) this.endsAt = 16;
            if (endsAt.equals("TS18")) this.endsAt = 17;
            if (endsAt.equals("TS19")) this.endsAt = 18;
            if (endsAt.equals("TS20")) this.endsAt = 19;
            if (endsAt.equals("TS21")) this.endsAt = 20;
            if (endsAt.equals("TS22")) this.endsAt = 21;
            if (endsAt.equals("TS23")) this.endsAt = 22;
            if (endsAt.equals("TS24")) this.endsAt = 23;
            if (endsAt.equals("TS25")) this.endsAt = 24;
            if (endsAt.equals("TS26")) this.endsAt = 25;
            if (endsAt.equals("TS27")) this.endsAt = 26;
            if (endsAt.equals("TS28")) this.endsAt = 27;
            if (endsAt.equals("CS01")) this.endsAt = 0;
            if (endsAt.equals("CS02")) this.endsAt = 1;
            if (endsAt.equals("CS03")) this.endsAt = 2;
            if (endsAt.equals("CS04")) this.endsAt = 3;
            if (endsAt.equals("CS05a")) this.endsAt = 4;
            if (endsAt.equals("CS05b")) this.endsAt = 5;
            if (endsAt.equals("CS05c")) this.endsAt = 6;
            if (endsAt.equals("CS06a")) this.endsAt = 7;
            if (endsAt.equals("CS06b")) this.endsAt = 8;
            if (endsAt.equals("CS07")) this.endsAt = 9;
            if (endsAt.equals("CS08")) this.endsAt = 10;
            if (endsAt.equals("CS09")) this.endsAt = 11;
            if (endsAt.equals("CS10")) this.endsAt = 12;
            if (endsAt.equals("CS11")) this.endsAt = 13;
            if (endsAt.equals("CS12")) this.endsAt = 14;
            if (endsAt.equals("CS13")) this.endsAt = 15;
            if (endsAt.equals("CS14")) this.endsAt = 16;
            if (endsAt.equals("CS15")) this.endsAt = 17;
            if (endsAt.equals("CS16")) this.endsAt = 18;
            if (endsAt.equals("CS17")) this.endsAt = 19;
            if (endsAt.equals("CS18")) this.endsAt = 20;
            if (endsAt.equals("CS19")) this.endsAt = 21;
            if (endsAt.equals("CS20")) this.endsAt = 22;
            if (endsAt.equals("CS21")) this.endsAt = 23;
            if (endsAt.equals("CS22")) this.endsAt = 24;
            if (endsAt.equals("CS23")) this.endsAt = 25;
        }
        else {
            this.endsAt = -1;
        }
    }
    
    public void setStartsAtInt( int startsAt ){
        this.startsAt = startsAt;
    }
    
    public void setEndsAtInt( int endsAt ){
        this.endsAt = endsAt;
    }
    
    public void setPresentIn( int presentIn ){
        this.presentIn = presentIn;
    }

    public void setSynonym( ArrayList < String > synonym ) {
        this.synonym = synonym;
    }
    
    public void addSynonym( String partSynonym ) {
        this.synonym.add( partSynonym );
    }
    
    public void setHasTimeComponent ( ArrayList < String > timedComponent ){
        this.hasTimeComponent = timedComponent;
    }
    
    public void addHasTimeComponent( String partTimedComponent){
        this.hasTimeComponent.add( partTimedComponent );
    }
    
    public void setTimeComponentOf ( String timeComponentOf ){
        this.timeComponentOf = timeComponentOf;
    }
 
    public void addGroupPartOf ( String groupPartOf  ){
       this.groupPartOf.add( groupPartOf ); 
    }
    
    public void setGroupPartOf( ArrayList < String > groupPartOf ) {
        this.groupPartOf = groupPartOf;
    }
    
    public void setFlagMissingRel(boolean flag){
        this.flagMissingRel = flag;
    } 
    
    public void setFlagLifeTime(boolean flag){
        this.flagLifeTime = flag;
    }
    
    public void setStrChangeStatus(String flag){
        this.strChangeStatus = flag;
    }
    
    public void setStrRuleStatus(String flag){
        this.strRuleStatus = flag;
    }
    
    public void setCheckComment(String comment){
        this.comments.add(comment);
    }
    
    public void clearCheckComment(){
        this.comments.clear();
        this.comments.addAll(this.userComments);
    }
    
    public void setIsPrimary(boolean isprimary){
        this.isPrimary = isprimary;
    }
    
    public void setPrimaryPath( DefaultMutableTreeNode[] path ){
        /*may not need this if path is passed directly as reference
        int intArraySize = path.length;
        
        //allocate array size first
        this.primaryPath = new DefaultMutableTreeNode[intArraySize];
        */
        this.primaryPath = path;
    }
    
    public void addPaths( DefaultMutableTreeNode[] path ){
        this.paths.add(path);
    }
    
    public void setPaths( Vector<DefaultMutableTreeNode[]> paths ){
        this.paths = paths;
    }

    public void addUserComments( String s ){
        this.userComments.add(s);
    }

    public void setOrderComment(String s){
        this.orderComment = s;
    }

    public String getID() {
        return this.id;
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

    public ArrayList < String > getPartOf() {
        return this.partOf;
    }

    public String getIsA() {
        return this.isA;
    }

    public Integer getStartsAt() {
        return this.startsAt;
    }
    
    public Integer getEndsAt() {
        return this.endsAt;
    }
    
    public String getStartsAtStr( String species ) {

        String rtnStr = "";

        if ( species.equals("mouse") ){
            if (this.startsAt == 0) rtnStr = "TS01";
            if (this.startsAt == 1) rtnStr = "TS02";
            if (this.startsAt == 2) rtnStr = "TS03";
            if (this.startsAt == 3) rtnStr = "TS04";
            if (this.startsAt == 4) rtnStr = "TS05";
            if (this.startsAt == 5) rtnStr = "TS06";
            if (this.startsAt == 6) rtnStr = "TS07";
            if (this.startsAt == 7) rtnStr = "TS08";
            if (this.startsAt == 8) rtnStr = "TS09";
            if (this.startsAt == 9) rtnStr = "TS10";
            if (this.startsAt == 10) rtnStr = "TS11";
            if (this.startsAt == 11) rtnStr = "TS12";
            if (this.startsAt == 12) rtnStr = "TS13";
            if (this.startsAt == 13) rtnStr = "TS14";
            if (this.startsAt == 14) rtnStr = "TS15";
            if (this.startsAt == 15) rtnStr = "TS16";
            if (this.startsAt == 16) rtnStr = "TS17";
            if (this.startsAt == 17) rtnStr = "TS18";
            if (this.startsAt == 18) rtnStr = "TS19";
            if (this.startsAt == 19) rtnStr = "TS20";
            if (this.startsAt == 20) rtnStr = "TS21";
            if (this.startsAt == 21) rtnStr = "TS22";
            if (this.startsAt == 22) rtnStr = "TS23";
            if (this.startsAt == 23) rtnStr = "TS24";
            if (this.startsAt == 24) rtnStr = "TS25";
            if (this.startsAt == 25) rtnStr = "TS26";
            if (this.startsAt == 26) rtnStr = "TS27";
            if (this.startsAt == 27) rtnStr = "TS28";
        }
        if ( species.equals("human") ){
            if (this.startsAt == 0) rtnStr = "CS01";
            if (this.startsAt == 1) rtnStr = "CS02";
            if (this.startsAt == 2) rtnStr = "CS03";
            if (this.startsAt == 3) rtnStr = "CS04";
            if (this.startsAt == 4) rtnStr = "CS05a";
            if (this.startsAt == 5) rtnStr = "CS05b";
            if (this.startsAt == 6) rtnStr = "CS05c";
            if (this.startsAt == 7) rtnStr = "CS06a";
            if (this.startsAt == 8) rtnStr = "CS06b";
            if (this.startsAt == 9) rtnStr = "CS08";
            if (this.startsAt == 10) rtnStr = "CS09";
            if (this.startsAt == 11) rtnStr = "CS10";
            if (this.startsAt == 12) rtnStr = "CS11";
            if (this.startsAt == 13) rtnStr = "CS12";
            if (this.startsAt == 14) rtnStr = "CS13";
            if (this.startsAt == 15) rtnStr = "CS14";
            if (this.startsAt == 16) rtnStr = "CS15";
            if (this.startsAt == 17) rtnStr = "CS16";
            if (this.startsAt == 18) rtnStr = "CS17";
            if (this.startsAt == 19) rtnStr = "CS18";
            if (this.startsAt == 20) rtnStr = "CS19";
            if (this.startsAt == 21) rtnStr = "CS20";
            if (this.startsAt == 22) rtnStr = "CS21";
            if (this.startsAt == 23) rtnStr = "CS22";
            if (this.startsAt == 24) rtnStr = "CS23";
        }

        return rtnStr;
    }

    public String getEndsAtStr( String species ) {

        String rtnStr = "";

        if ( species.equals("mouse") ){
            if (this.endsAt == 0) rtnStr = "TS01";
            if (this.endsAt == 1) rtnStr = "TS02";
            if (this.endsAt == 2) rtnStr = "TS03";
            if (this.endsAt == 3) rtnStr = "TS04";
            if (this.endsAt == 4) rtnStr = "TS05";
            if (this.endsAt == 5) rtnStr = "TS06";
            if (this.endsAt == 6) rtnStr = "TS07";
            if (this.endsAt == 7) rtnStr = "TS08";
            if (this.endsAt == 8) rtnStr = "TS09";
            if (this.endsAt == 9) rtnStr = "TS10";
            if (this.endsAt == 10) rtnStr = "TS11";
            if (this.endsAt == 11) rtnStr = "TS12";
            if (this.endsAt == 12) rtnStr = "TS13";
            if (this.endsAt == 13) rtnStr = "TS14";
            if (this.endsAt == 14) rtnStr = "TS15";
            if (this.endsAt == 15) rtnStr = "TS16";
            if (this.endsAt == 16) rtnStr = "TS17";
            if (this.endsAt == 17) rtnStr = "TS18";
            if (this.endsAt == 18) rtnStr = "TS19";
            if (this.endsAt == 19) rtnStr = "TS20";
            if (this.endsAt == 20) rtnStr = "TS21";
            if (this.endsAt == 21) rtnStr = "TS22";
            if (this.endsAt == 22) rtnStr = "TS23";
            if (this.endsAt == 23) rtnStr = "TS24";
            if (this.endsAt == 24) rtnStr = "TS25";
            if (this.endsAt == 25) rtnStr = "TS26";
            if (this.endsAt == 26) rtnStr = "TS27";
            if (this.endsAt == 27) rtnStr = "TS28";
        }
        if ( species.equals("human") ){
            if (this.endsAt == 0) rtnStr = "CS01";
            if (this.endsAt == 1) rtnStr = "CS02";
            if (this.endsAt == 2) rtnStr = "CS03";
            if (this.endsAt == 3) rtnStr = "CS04";
            if (this.endsAt == 4) rtnStr = "CS05a";
            if (this.endsAt == 5) rtnStr = "CS05b";
            if (this.endsAt == 6) rtnStr = "CS05c";
            if (this.endsAt == 7) rtnStr = "CS06a";
            if (this.endsAt == 8) rtnStr = "CS06b";
            if (this.endsAt == 9) rtnStr = "CS08";
            if (this.endsAt == 10) rtnStr = "CS09";
            if (this.endsAt == 11) rtnStr = "CS10";
            if (this.endsAt == 12) rtnStr = "CS11";
            if (this.endsAt == 13) rtnStr = "CS12";
            if (this.endsAt == 14) rtnStr = "CS13";
            if (this.endsAt == 15) rtnStr = "CS14";
            if (this.endsAt == 16) rtnStr = "CS15";
            if (this.endsAt == 17) rtnStr = "CS16";
            if (this.endsAt == 18) rtnStr = "CS17";
            if (this.endsAt == 19) rtnStr = "CS18";
            if (this.endsAt == 20) rtnStr = "CS19";
            if (this.endsAt == 21) rtnStr = "CS20";
            if (this.endsAt == 22) rtnStr = "CS21";
            if (this.endsAt == 23) rtnStr = "CS22";
            if (this.endsAt == 24) rtnStr = "CS23";
        }

        return rtnStr;
    }


    public Integer getPresentIn(){
        return this.presentIn;
    }

    public ArrayList < String > getSynonym() {
        return this.synonym;
    }
    
    public ArrayList < String > getHasTimeComponent() {
        return this.hasTimeComponent;
    }
    
    public String getTimeComponentOf(){
        return this.timeComponentOf;
    }
    
    public ArrayList < String > getGroupPartOf(){
        return this.groupPartOf;
    }
    
    //public String getGroupPartOf(){
    //    return this.groupPartOf;
    //}
    
    public boolean getFlagMissingRel(){
        return this.flagMissingRel;
    }
    
    public boolean getFlagLifeTime(){
        return this.flagLifeTime;
    }
    
    public String getStrChangeStatus(){
        return this.strChangeStatus;
    }
    
    public String getStrRuleStatus(){
        return this.strRuleStatus;
    }

    public Set getCheckComments(){
        return this.comments;
    }

    public ArrayList getUserComments(){
        return this.userComments;
    }

    public String[] getOrderComments(){

        Vector< String > vComments = new Vector<String>();
        vComments.addAll(this.userComments);
        String unprocessed = "";
        int intValidString = 0;

        //refresh orderComment everytime the function is called
        this.orderComment = ""; 

        for ( int i=0; i < vComments.size(); i++ ){

            //System.out.println("vComments.get(i) = " + vComments.get(i).trim());

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
            return processedOrderArray;
        }

        return null;
    }

    public String[] getOrderCommentOnParent(String parentid){

        String[] ordercomments = this.getOrderComments();
        ArrayList<String> arrBased = new ArrayList<String>();

        if ( ordercomments!=null ){
            for (int i=0; i < ordercomments.length; i++){
                /*
                if (parentid.equals("EMAPA:28500")){
                    System.out.println("going thru order comments for " +
                                    parentid + ": " + ordercomments[i]);
                }
                */
                if ( ordercomments[i].contains(parentid) ){
                    /*
                    if (parentid.equals("EMAPA:28500")){
                        System.out.println("found a comment that refers to " +
                                       "order to parent");
                    }
                    */
                    arrBased.add(ordercomments[i]);
                }
            }
            if (arrBased.isEmpty()){
                return null; //no matching comment on parent
            }
            else {
                return arrBased.toArray( new String[arrBased.size()] );
            }
        }
        else {
            return null;
        }
    }

    public String getOrderComment(){
        return this.orderComment;
    }

    public boolean hasIncorrectOrderComments(){

        String[] results = new String[this.userComments.size()];
        ArrayList<String> parents = new ArrayList<String>();
        int intCounter = 0;
        boolean foundIncorrect = false;
        boolean foundParent = false;
        int intOrder = -1;
        String strNumber = "";

        Vector< String > vComments = new Vector<String>();
        vComments.addAll(this.userComments);

        for ( int i=0; i < vComments.size(); i++ ){
            //get all the user comments with keyword order for checking
            if ( vComments.get(i).contains("order") ){
                results[intCounter] = vComments.get(i).trim();
                intCounter++;
            }
        }

        //go through each string with 'order'
        for (int k=0; k<results.length && results[k]!=null; k++){
            //System.out.println("checking order comment " + results[k] +
            //                   " for component " + this.id);
            //reset for each order comment
            foundIncorrect = false;
            //check that string order= is in the string
            if ( !results[k].contains("order=") ){
                System.out.println(this.id + "Ordering: There is an order " +
                         "comment that does not have an = sign " +
                         "immediately after the term order");
                this.setCheckComment("Ordering: There is an order comment " +
                        "that does not have an = sign immediately after " +
                        "the term order");
                foundIncorrect = true;
            }
            //ir order= is in place check that immediately
            //after that is a valid integer
            else if (foundIncorrect == false){
                try{
                    strNumber = results[k];
                    strNumber = strNumber.replaceAll("order=", "");
                    intOrder = Integer.parseInt( strNumber.split(" ")[0] );
                }catch(NumberFormatException nEx){
                    System.out.println(this.id + "Ordering: There is an " +
                            "order comment that does not have a number " +
                            "immediately after order=; offending string = " +
                            strNumber.split(" ")[0]);
                    this.setCheckComment("Ordering: There is an order " +
                            "comment that does not have a number immediately " +
                            "after order=");
                    foundIncorrect = true;
                }
            }
            //check that parent in order comment is a valid parent
            parents.addAll( this.getPartOf() );
            parents.addAll( this.getGroupPartOf() );
            for (int i=0; i<parents.size() && !foundParent; i++){
                if ( results[k].contains(parents.get(i)) ) {
                    foundParent = true;
                }
            }
            if (!foundParent){
                System.out.println(this.id + "Ordering: There is an order " +
                        "comment that does not reference a valid parent");
                this.setCheckComment("Ordering: There is an order comment " +
                        "that does not reference a valid parent");
                foundIncorrect = true;
            }
            

        }

        return foundIncorrect;
    }
    
    public boolean getIsPrimary(){
        return this.isPrimary;
    }
    
    public DefaultMutableTreeNode[] getPrimaryPath(){
        return this.primaryPath;
    }
    
    public Vector getPaths(){
        return this.paths;
    }
    
    public Vector getShortenedPaths(){

        Vector<DefaultMutableTreeNode[]> longPaths = new Vector();
        Vector<DefaultMutableTreeNode[]> shortPaths = new Vector();
        
        if ( !this.paths.isEmpty() ){
            longPaths.addAll(this.paths);
            for(int i = 0; i< longPaths.size(); i++){
                DefaultMutableTreeNode[] longPath = longPaths.get(i);
                if(longPath.length > 0) {
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
    
    
    
    public boolean commentsContain(String fragment){

        Set<String> strComments = this.getCheckComments();

        for( String s: strComments ){
            if( s.contains(fragment) ) {
                return true;
            }
        } 
        return false;
    }
    
    public boolean isSameAs(Component compie){

        //EMAP id, description, start stage, end stage, parents, synonyms
        ArrayList<String> orderdiff = null;
        orderdiff =  this.compareOrderComments(compie, orderdiff);

        if (this.getID().equals(compie.getID()) && 
            this.getName().equals(compie.getName()) &&
            this.getStartsAt() == compie.getStartsAt() &&
            this.getEndsAt() == compie.getEndsAt() &&
            //containsAll both ways = a in b and b in a; a = b
            this.getPartOf().containsAll(compie.getPartOf()) &&
            compie.getPartOf().containsAll(this.getPartOf()) && 
            this.getSynonym().containsAll(compie.getSynonym()) && 
            compie.getSynonym().containsAll(this.getSynonym()) &&
            this.getGroupPartOf().containsAll(compie.getGroupPartOf()) &&
            compie.getGroupPartOf().containsAll(this.getGroupPartOf()) &&
            orderdiff==null ) {
                return true;
            } else {
                return false;
            }
    }
    
    public ArrayList<String> getDifferenceWith(Component compie, String species){

        //System.out.println("doing getDifferenceWith between " +
        //                   compie.getID() + " and " + this.id);
        ArrayList<String> arrDifferenceWith = new ArrayList();
        ArrayList<String> arrDiffOrder = null;
        
            if ( !this.getID().equals(compie.getID()) ) {
                arrDifferenceWith.add( "Different ID - Referenced Component " +
                        "has ID [" + compie.getID() + "]" );
            }

            if ( !this.getName().equals(compie.getName())) {
                arrDifferenceWith.add( "Different Name - Referenced Component "
                        + compie.getID() + " has name [" +
                        compie.getName() + "]" );
            }

            if ( this.getStartsAt() != compie.getStartsAt() ) {
                arrDifferenceWith.add( "Different Start Stage - Referenced " +
                        "Component " + compie.getID() + " has Start Stage " +
                        compie.getStartsAtStr(species) );
            }

            if ( this.getEndsAt() != compie.getEndsAt() ) {
                arrDifferenceWith.add( "Different End Stage - Referenced " +
                        "Component " + compie.getID() + " has End Stage " +
                        compie.getEndsAtStr(species) );
            }

            if ( !this.getPartOf().containsAll(compie.getPartOf()) || 
                  !compie.getPartOf().containsAll(this.getPartOf()) ) {
                arrDifferenceWith.add( "Different Parents - Referenced " +
                        "Component " + compie.getID() + " has parents " +
                        compie.getPartOf().toString() );
            }

            if ( !this.getGroupPartOf().containsAll(compie.getGroupPartOf()) ||
                 !compie.getGroupPartOf().containsAll(this.getGroupPartOf()) ) {
                arrDifferenceWith.add( "Different Group Parents - Referenced " +
                        "Component " + compie.getID() + " has group parents " +
                        compie.getGroupPartOf().toString() );
            }

            if ( !this.getSynonym().containsAll(compie.getSynonym()) || 
                 !compie.getSynonym().containsAll(this.getSynonym()) ) {
                arrDifferenceWith.add( "Different Synonyms - Referenced " +
                        "Component " + compie.getID() + " has synonyms " +
                        compie.getSynonym().toString() );
            }

            if ( !this.getIsPrimary() == compie.getIsPrimary() ) {
                //String strPrimary = ( this.getIsPrimary() ) ?
                // "Primary" : "Group";
                String strPrimary = "";
                if ( this.getIsPrimary()){
                    strPrimary = "Primary";
                }
                else {
                    strPrimary = "Group";
                }

                arrDifferenceWith.add( "Different Primary Status - " +
                        "Referenced Component was a " + strPrimary +
                        " component." );
            }
            
        arrDiffOrder = this.compareOrderComments(compie, arrDiffOrder);

            if ( arrDiffOrder!=null ){
                arrDifferenceWith.addAll(arrDiffOrder);
            }
        //System.out.println("finish getDifferenceWith between " +
        //                   compie.getID() + " and " + this.id);
        return arrDifferenceWith;
    }
    
   public Set getDifferenceComments(){
       
       Set<String> allComments = this.getCheckComments();
       Set<String> changedComments = new TreeSet();
       
       for (String s: allComments){   
           if (s.startsWith("Different")){
               changedComments.add(s);
           } 
       }
       return changedComments;
   }
   
   public boolean hasDifferenceComment(String strDifferent){
       
       Set<String> allComments = this.getCheckComments();
       for (String s: allComments){
           if ( s.startsWith(strDifferent) ) {
               return true;
           }
       }
       return false;
   }
   
   public Set getNewComments(){
       
       Set<String> allComments = this.getCheckComments();
       Set<String> changedComments = new TreeSet();
       
       for (String s: allComments){   
           if (s.startsWith("New")){
               changedComments.add(s);
           } 
       }
       return changedComments;
   
   }

   public ArrayList compareOrderComments(Component compie,
           ArrayList<String> diff){
       //find new/additional comments
       
       boolean notFound = true;
       if (this.getOrderComments() == null &&
               compie.getOrderComments() == null){
           return diff;
       }
       else if (this.getOrderComments() == null &&
               compie.getOrderComments() != null){
           for (int i=0; i<compie.getOrderComments().length; i++){
               if (diff == null){
                    diff = new ArrayList<String>();
               }
               diff.add( "Different Order - This component has no order " +
                       "entries; Referenced Component (" + compie.getID() +
                       ") has order entry <" + compie.getOrderComments()[i] +
                       ">");
           }
           return diff;
       }
       else if (this.getOrderComments() != null &&
               compie.getOrderComments() == null){
           for (int i=0; i<this.getOrderComments().length; i++){
               if (diff == null){
                    diff = new ArrayList<String>();
               }
               diff.add( "Different Order - Referenced Component has no " +
                       "order entries; This Component has order entry <" +
                       this.getOrderComments()[i] + ">");
           }
           return diff;
       }
       else{

        for ( String ordercommie: this.getOrderComments() ){
            notFound = true; //reset notfound for each comment
            for ( int i=0; i<compie.getOrderComments().length && 
                    notFound &&
                    !ordercommie.equals(""); i++ ){
                if ( ordercommie.equals( compie.getOrderComments()[i] ) ){
                    notFound = false; //found matching comment stop inner loop
                }
            }
            //iterated through all comments in referenced component and
            // no matching comment found
            if (notFound && 
                    !ordercommie.equals("")){
                if (diff == null){
                    diff = new ArrayList<String>();
                }
                diff.add( "Different Order - This Component has order entry <" +
                        ordercommie + ">");
                //System.out.println( "2 Different Order Entry - This " =
                // "Component has order entry <" + ordercommie + ">");
            }
        }
        //find deleted/missing comments
        for ( String ordercommie: compie.getOrderComments() ){
            notFound = true; //reset notfound for each comment
            for ( int i=0; i<this.getOrderComments().length && 
                    notFound &&
                    !ordercommie.equals(""); i++ ){
                if ( ordercommie.equals( this.getOrderComments()[i] ) ){
                    notFound = false; //found matching comment stop inner loop
                }
            }
            //iterated through all comments in referenced component and
            // no matching comment found
            if (notFound && !ordercommie.equals("")){ 
                if (diff == null){
                    diff = new ArrayList<String>();
                }
                diff.add( "Different Order - Referenced Component (" + 
                        compie.getID() + ") has order entry <" + ordercommie +
                        ">");
                //System.out.println( "1 Different Order Entry - Referenced " +
                // "Component has order entry <" + ordercommie + ">");
            }
        }

       return diff;
       }
   }
   
   public Component clone(){
       Component copyCompie = new Component();
       
       copyCompie.setID( this.getID() );
       copyCompie.setDBID( this.getDBID() );
       copyCompie.setName( this.getName() );
       copyCompie.setNamespace( this.getNamespace() );
       copyCompie.setPartOf( this.getPartOf() );
       copyCompie.setGroupPartOf( this.getGroupPartOf() );
       copyCompie.setIsA( this.getIsA() );
       copyCompie.setStartsAtInt( this.getStartsAt() );
       copyCompie.setEndsAtInt( this.getEndsAt() );
       copyCompie.setPresentIn( this.getPresentIn() );
       copyCompie.setSynonym( this.getSynonym() );
       copyCompie.setHasTimeComponent( this.getHasTimeComponent() );
       copyCompie.setTimeComponentOf( this.getTimeComponentOf() );
       
       copyCompie.setFlagMissingRel( this.getFlagMissingRel() );
       copyCompie.setFlagLifeTime( this.getFlagLifeTime() );
       copyCompie.setStrChangeStatus( this.getStrChangeStatus() );
       copyCompie.setStrRuleStatus( this.getStrRuleStatus() );
       
       Set<String> copyComments = this.getCheckComments();       
       for (String s: copyComments){   
           copyCompie.setCheckComment(s);
       }
       
       copyCompie.setIsPrimary( this.getIsPrimary() );
       copyCompie.setPrimaryPath( this.getPrimaryPath() );
       copyCompie.setPaths( this.getPaths() );
       
       return copyCompie;
   }
    
    @Override
    public String toString() {

        return getName()+"("+getID()+")";
        //temp block for making checkPathLifeTime
        //return this.getID();
    }
    
    @Override
    public boolean equals(Object o){

        if(o instanceof Component){
            Component compie = (Component) o;
            return isSameAs(compie);
        }
        else return false;
    }
}
