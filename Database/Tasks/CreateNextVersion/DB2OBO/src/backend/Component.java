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
    private String newid;
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
    private ArrayList < String > userComments;
    private String orderComment;
    
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
        this.newid = "";
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

    public ArrayList getUserComments(){
        return this.userComments;
    }

    public String[] getOrderComments(){
        Vector< String > vComments = new Vector<String>();
        vComments.addAll(this.userComments);
        String unprocessed = "";
        int intValidString = 0;
        this.orderComment = ""; //refresh orderComment everytime the function is called

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
                orderArray[i] = unprocessed;
                if ( !unprocessed.equals("") ) intValidString++;
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
                //System.out.println("going thru order comments for " + parentid + ": " + ordercomments[i]);
                if ( ordercomments[i].contains(parentid) ){
                    //System.out.println("found a comment that refers to order to parent");
                    arrBased.add(ordercomments[i]);
                }
            }
            if (arrBased.isEmpty()){
                return null; //no matching comment on parent
            }else{
                return arrBased.toArray( new String[arrBased.size()] );
            }
        }else{
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
            //System.out.println("checking order comment " + results[k] + " for component " + this.id);
            //reset for each order comment
            foundIncorrect = false;
            //check that string order= is in the string
            if ( !results[k].contains("order=") ){
                System.out.println(this.id + "Ordering: There is an order comment that does not have an = sign immediately after the term order");
                this.setCheckComment("Ordering: There is an order comment that does not have an = sign immediately after the term order");
                foundIncorrect = true;
            }
            //ir order= is in place check that immediately after that is a valid integer
            else if (foundIncorrect==false){
                try{
                    strNumber = results[k];
                    strNumber = strNumber.replaceAll("order=", "");
                    intOrder = Integer.parseInt( strNumber.split(" ")[0] );
                }catch(NumberFormatException nEx){
                    System.out.println(this.id + "Ordering: There is an order comment that does not have a number immediately after order=; offending string = " + strNumber.split(" ")[0]);
                    this.setCheckComment("Ordering: There is an order comment that does not have a number immediately after order=");
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
                System.out.println(this.id + "Ordering: There is an order comment that does not reference a valid parent");
                this.setCheckComment("Ordering: There is an order comment that does not reference a valid parent");
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
        ArrayList<String> orderdiff = null;
        orderdiff =  this.compareOrderComments(compie, orderdiff);

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
            compie.getGroupPartOf().containsAll(this.getGroupPartOf()) &&
            orderdiff==null )
            {
                return true;
            } else{
                return false;
            }
    }
    
    public ArrayList<String> getDifferenceWith(Component compie){
        //System.out.println("doing getDifferenceWith between " + compie.getID() + " and " + this.id);
        ArrayList<String> arrDifferenceWith = new ArrayList();
        ArrayList<String> arrDiffOrder = null;
        
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
            arrDiffOrder = this.compareOrderComments(compie, arrDiffOrder);
            if ( arrDiffOrder!=null ){
                arrDifferenceWith.addAll(arrDiffOrder);
            }

        //System.out.println("finish getDifferenceWith between " + compie.getID() + " and " + this.id);
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

   public ArrayList compareOrderComments(Component compie, ArrayList<String> diff){
       //find new/additional comments
       
       boolean notFound = true;
       if (this.getOrderComments()==null && compie.getOrderComments()==null){
           return diff;
       }
       else if (this.getOrderComments()==null && compie.getOrderComments()!=null){
           for (int i=0; i<compie.getOrderComments().length; i++){
               if (diff==null){
                    diff = new ArrayList<String>();
               }
               diff.add( "Different Order - This component has no order entries; Referenced Component (" + compie.getID() + ") has order entry <" + compie.getOrderComments()[i] + ">");
           }
           return diff;
       }
       else if (this.getOrderComments()!=null && compie.getOrderComments()==null){
           for (int i=0; i<this.getOrderComments().length; i++){
               if (diff==null){
                    diff = new ArrayList<String>();
               }
               diff.add( "Different Order - Referenced Component has no order entries; This Component has order entry <" + this.getOrderComments()[i] + ">");
           }
           return diff;
       }
       else{

        for ( String ordercommie: this.getOrderComments() ){
            notFound = true; //reset notfound for each comment
            for ( int i=0; i<compie.getOrderComments().length && notFound && !ordercommie.equals(""); i++ ){
                if ( ordercommie.equals( compie.getOrderComments()[i] ) ){
                    notFound = false; //found matching comment stop inner loop
                }
            }
            if (notFound && !ordercommie.equals("")){ //iterated through all comments in referenced component and no matching comment found
                if (diff==null){
                    diff = new ArrayList<String>();
                }
                diff.add( "Different Order - This Component has order entry <" + ordercommie + ">");
                //System.out.println( "2 Different Order Entry - This Component has order entry <" + ordercommie + ">");
            }
        }
        //find deleted/missing comments
        for ( String ordercommie: compie.getOrderComments() ){
            notFound = true; //reset notfound for each comment
            for ( int i=0; i<this.getOrderComments().length && notFound && !ordercommie.equals(""); i++ ){
                if ( ordercommie.equals( this.getOrderComments()[i] ) ){
                    notFound = false; //found matching comment stop inner loop
                }
            }
            if (notFound && !ordercommie.equals("")){ //iterated through all comments in referenced component and no matching comment found
                if (diff==null){
                    diff = new ArrayList<String>();
                }
                diff.add( "Different Order - Referenced Component (" + compie.getID() + ") has order entry <" + ordercommie + ">");
                //System.out.println( "1 Different Order Entry - Referenced Component has order entry <" + ordercommie + ">");
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
