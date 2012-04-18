package backend;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author attila
 */
public class Component {

    // component fields
    private String name;
    private String id;
    private String dbID;
    private String namespace;
    private ArrayList < String > partOf;
    private String isA;
    private String startsAt;
    private String endsAt;
    private String presentIn;
    private ArrayList < String > synonym;
    private ArrayList < String > hasTimeComponent;
    private String timeComponentOf;
    private ArrayList < String > groupPartOf;
    
    //component checking variables here
    private boolean flagMissingRel;  //missing relationships, starts < end
    private boolean flagLifeTime;    //child node lifetime not within parent lifetime    
    private String strChangeStatus;       //component is new, changed, or deleted "UNCHANGED"; NEW"; "CHANGED"; "DELETED"
    private String strRuleStatus;    //component is "UNCHECKED"; "PASSED"; "FAILED"; 
    private Set<String> comments; //store unique comments only
    
    //path variables
    private boolean isPrimary;
    private DefaultMutableTreeNode[] primaryPath;
    private Vector < DefaultMutableTreeNode[] > paths;
    
    public Component() {
        this.name = "";
        this.id = "";
        this.dbID = "";
        this.namespace = "";
        this.partOf = new ArrayList < String >();
        this.isA = "";
        this.startsAt = "";
        this.endsAt = "";
        this.presentIn = "";
        this.synonym = new ArrayList < String >();
        this.hasTimeComponent = new ArrayList < String >();
        this.timeComponentOf = "";
        this.groupPartOf = new ArrayList < String >();
        
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

    public void removePartOf( String partPartOf ){
        this.partOf.remove( partPartOf );
    }
    
    public void setIsA( String isA ) {
        this.isA = isA;
    }

    public void setStartsAt( String startsAt ) {
        this.startsAt = startsAt;
    }

    public void setEndsAt( String endsAt ) {
        this.endsAt = endsAt;
    }
    
    public void setStartsAt( int startsAt ){
        String strNumber = Integer.toString(startsAt);
        
        if ( strNumber.length()==1 ) setStartsAt("TS0" + strNumber);
        else setStartsAt("TS" + strNumber);
    }
    
    public void setEndsAt( int endsAt ){
        String strNumber = Integer.toString(endsAt);
        
        if (strNumber.length()==1 ) setEndsAt("TS0" + strNumber);
        else setEndsAt("TS" + strNumber);
    }
    
    public void setPresentIn( String presentIn ){
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
        //if(this.getID().equals( "Tmp_new_group" ) && flag.equals("PASSED") ) {
        //    throw new RuntimeException("BAD BAD FLAG!");            
        //} 
        this.strRuleStatus = flag;
    }
    
    public void setCheckComment(String comment){
        this.comments.add(comment);
    }
    
    public void clearCheckComment(){
        this.comments.clear();
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

    public String getID() {
        return this.id;
    }
    
    public String getDBID() {
        return this.dbID;
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

    public String getStartsAt() {
        return this.startsAt;
    }
    
    public String getEndsAt() {
        return this.endsAt;
    }
    
    public String getPresentIn(){
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
                    DefaultMutableTreeNode[] shortPath = new DefaultMutableTreeNode[longPath.length - 1];
                    System.arraycopy(longPath, 1, shortPath, 0, longPath.length - 1);   
                    shortPaths.add(shortPath);
                }
            }
        }
        return shortPaths;
    }
    
    public DefaultMutableTreeNode[] getShortenedPrimaryPath(){
        if (this.primaryPath!=null){
            DefaultMutableTreeNode[] longPath = this.primaryPath;        
            DefaultMutableTreeNode[] shortPath = new DefaultMutableTreeNode[longPath.length - 1];
            System.arraycopy(longPath, 1, shortPath, 0, longPath.length - 1); 
            return shortPath;
        }
        else{
            DefaultMutableTreeNode nullNode = new DefaultMutableTreeNode("N/A");
            DefaultMutableTreeNode[] shortPath = new DefaultMutableTreeNode[1];
            shortPath[0] = nullNode;
            return shortPath;
        }
    }
    
    public DefaultMutableTreeNode[] shortenPath( DefaultMutableTreeNode[] longPath ){
        DefaultMutableTreeNode[] shortPath = new DefaultMutableTreeNode[longPath.length - 1];
        System.arraycopy(longPath, 1, shortPath, 0, longPath.length - 1); 
        return shortPath;
    }
    
    
    
    public boolean commentsContain(String fragment){
        Set<String> strComments = this.getCheckComments();
        for( String s: strComments ){
            if( s.contains(fragment) ) return true;
        } 
        return false;
    }
    
    //public String[] getCheckComments(){
    //    //ArrayList<String> comments = new ArrayList<String>();
    //    String[] arrComments = checkComment.split(";", 0);
    //    return arrComments;
    //}
    
    public int getIntStartsAt(){
        
        String strStage = this.getStartsAt();
        
        if (strStage.equals("")) return 0;
        else 
            try{
                return Integer.parseInt(strStage.substring(2));
            }
            catch(Exception e){
                return 0;
            }
    }
    
    public int getIntEndsAt(){
        
        String strStage = this.getEndsAt();

        if (strStage.equals("")) return 0;
        else{
            try{
                return Integer.parseInt(strStage.substring(2));
            }
            catch(Exception e){
                return 0;
            }
        }  
    }
    
    public boolean isSameAs(Component compie){
        //EMAP id, description, start stage, end stage, parents, synonyms

        if (this.getID().equals(compie.getID()) && 
            this.getName().equals(compie.getName()) &&
            this.getStartsAt().equals(compie.getStartsAt()) && 
            this.getEndsAt().equals(compie.getEndsAt()) &&
            //containsAll both ways = a in b and b in a; a = b
            this.getPartOf().containsAll(compie.getPartOf()) &&
            compie.getPartOf().containsAll(this.getPartOf()) && 
            this.getSynonym().containsAll(compie.getSynonym()) && 
            compie.getSynonym().containsAll(this.getSynonym()) &&
            this.getGroupPartOf().containsAll(compie.getGroupPartOf()) &&
            compie.getGroupPartOf().containsAll(this.getGroupPartOf()) ) 
            return true;
        else
            return false;
    }
    
    public ArrayList<String> getDifferenceWith(Component compie){
        
        ArrayList<String> arrDifferenceWith = new ArrayList();
        
            if ( !this.getID().equals(compie.getID()) ) arrDifferenceWith.add( "Different ID - Referenced Component has ID [" + compie.getID() + "]" );
            if ( !this.getName().equals(compie.getName())) arrDifferenceWith.add( "Different Name - Referenced Component " + compie.getID() + " has name [" + compie.getName() + "]" );
            if ( !this.getStartsAt().equals(compie.getStartsAt()) ) arrDifferenceWith.add( "Different Start Stage - Referenced Component " + compie.getID() + " has Start Stage [" + compie.getStartsAt() + "]" );
            if ( !this.getEndsAt().equals(compie.getEndsAt()) ) arrDifferenceWith.add( "Different End Stage - Referenced Component " + compie.getID() + " has End Stage [" + compie.getEndsAt() + "]" );
            if ( !this.getPartOf().containsAll(compie.getPartOf()) || !compie.getPartOf().containsAll(this.getPartOf()) ) 
                arrDifferenceWith.add( "Different Parents - Referenced Component " + compie.getID() + " has parents " + compie.getPartOf().toString() );
            if ( !this.getGroupPartOf().containsAll(compie.getGroupPartOf()) || !compie.getGroupPartOf().containsAll(this.getGroupPartOf()) ) 
                arrDifferenceWith.add( "Different Group Parents - Referenced Component " + compie.getID() + " has group parents " + compie.getGroupPartOf().toString() );
            if ( !this.getSynonym().containsAll(compie.getSynonym()) || !compie.getSynonym().containsAll(this.getSynonym()) ) 
                arrDifferenceWith.add( "Different Synonyms - Referenced Component " + compie.getID() + " has synonyms " + compie.getSynonym().toString() );
            if ( !this.getIsPrimary()==compie.getIsPrimary() ) {
                String strPrimary = ( this.getIsPrimary() ) ? "Primary" : "Group";
                arrDifferenceWith.add( "Different Primary Status - Referenced Component was a " + strPrimary + " component." );
            } 
        
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
           if ( s.startsWith(strDifferent) ) return true;
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
   
   public Component clone(){
       Component copyCompie = new Component();
       
       copyCompie.setID( this.getID() );
       copyCompie.setDBID( this.getDBID() );
       copyCompie.setName( this.getName() );
       copyCompie.setNamespace( this.getNamespace() );
       copyCompie.setPartOf( this.getPartOf() );
       copyCompie.setGroupPartOf( this.getGroupPartOf() );
       copyCompie.setIsA( this.getIsA() );
       copyCompie.setStartsAt( this.getStartsAt() );
       copyCompie.setEndsAt( this.getEndsAt() );
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
