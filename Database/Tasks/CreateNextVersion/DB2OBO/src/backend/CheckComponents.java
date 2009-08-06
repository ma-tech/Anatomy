package backend;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Maze Lam
 */
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author attila
 */
public class CheckComponents {

    private ArrayList<Component> proposedTermList = null; //new OBO file <= all checks are appended to this file, used to build maps+trees
    private ArrayList<Component> referenceTermList = null; //reference OBO file 
    private ArrayList<Component> passRedTermList = null;   //components that have passed red check
    private ArrayList<Component> passBlueTermList = null;  //components that have passed blue check
    private ArrayList<Component> changesTermList = null;  //components (new/existing) with proposed changes => comobox ChangedNodes
    private ArrayList<Component> problemTermList = null;  //components that have not passed red / blue check => combobox ProblemNodes
    private ArrayList<Component> groupTermList = null; //components that are group terms ..delete after testing
    private ArrayList<Component> abstractTermList = null; //components that are abstract anatomy terms excluding roots => used for ontology rules check
    
    private boolean passedRed; //passed red check: checkAbstractAnatomyLinks()
    private boolean passedBlue; //passed blue check: checkAbstractAnatomyStages()
    
    //flag for proceeding with each check in entire class
    private boolean proceed;
    
    //root nodes of the tree that are abstract anatomy terms - used for ontology rules check
    private ArrayList<Component> abstractRootList = null; 
    private String species;


    public CheckComponents(ArrayList<Component> newTermList, ArrayList<Component> oldTermList, TreeBuilder treebuilder, Component abstractClass, Component stageClass, Component groupClass, Component groupTermClass, String species) {

        //clear all comments and status from original term list so that children of new groups with calculated common ancestor
        //will not have any rule violations anymore
        this.clearStatusComments( newTermList );
        //instantiat term lists
        this.proposedTermList = newTermList;
        this.referenceTermList = oldTermList;
        this.passRedTermList = new ArrayList<Component>();
        this.passBlueTermList = new ArrayList<Component>();
        this.changesTermList = new ArrayList<Component>();
        this.problemTermList = new ArrayList<Component>();
        this.groupTermList = new ArrayList<Component>(); 
        this.abstractTermList = new ArrayList<Component>();

        this.passedRed = false;
        this.passedBlue = false;
        //not in use yet
        this.proceed = true;
        
        
        //set species
        this.species = species;
        
        //prepare abstract anatomy term list
          //check that roots of tree are same as configuration,
          //also detect correctly deleted components, all other changes are detected in checkchanges
        this.abstractRootList = new ArrayList<Component>();
        this.validateConfiguredRoots(treebuilder, abstractClass, stageClass, groupClass, groupTermClass); 
        this.abstractTermList = this.getAbstractAnatomyChildren(treebuilder, abstractClass, groupClass);
     
        //set and validate primary + alternate paths to abstract anatomy terms
        this.validatePaths(treebuilder, abstractClass);
        
        //perform checks on abstract anatomy terms
        if ( this.proceed ){
            //check for missing/incorrect relationships
            checkAbstractAnatomyLinks();
            //check for only one primary parent
            checkAbstractAnatomyParents( treebuilder );
            //check that component is within life time of primary parent
            checkAbstractAnatomyStages();
            //check ordering //block temporary to process jane's old file
            checkOrdering( treebuilder, abstractClass );
            //check for changes with referenced file - changed and new; deleted is found in validateConfigureRoots
            checkChanges( abstractClass );
        }

        System.out.println("CheckComponents Class: checking rules and changes completed.");
    }
    
    public CheckComponents(ArrayList<Component> newTermList, TreeBuilder treebuilder, Component abstractClass, Component stageClass, Component groupClass, Component groupTermClass, String species) {

        System.out.println("CheckComponents Class: Instantiating...");
        //clear all comments and status from original term list
        this.clearStatusComments( newTermList );
        //instantiate term lists        
        this.proposedTermList = newTermList;
        this.passRedTermList = new ArrayList<Component>();
        this.passBlueTermList = new ArrayList<Component>();
        this.problemTermList = new ArrayList<Component>();
        this.groupTermList = new ArrayList<Component>(); 
        this.abstractTermList = new ArrayList<Component>();

        this.passedRed = false;
        this.passedBlue = false;
        //not in use yet
        this.proceed = true;
        
        
        //set species
        this.species = species;
        
        //prepare abstract anatomy term list
          //check that roots of tree are same as configuration
        this.abstractRootList = new ArrayList<Component>();
        this.validateConfiguredRoots(treebuilder, abstractClass, stageClass, groupClass, groupTermClass); 
        this.abstractTermList = this.getAbstractAnatomyChildren(treebuilder, abstractClass, groupClass);

        //set and validate primary + alternate paths to abstract anatomy terms
        this.validatePaths(treebuilder, abstractClass);
        
        //perform checks on abstract anatomy terms
        if ( this.proceed ){
            //check for missing/incorrect relationships
            checkAbstractAnatomyLinks();
            //check that component is within life time of primary parent
            checkAbstractAnatomyStages();
            //check ordering //block temporarily to process jane's old file
            checkOrdering( treebuilder, abstractClass );
            //no reference file
            //checkChanges();
        }

        System.out.println("CheckComponents Class: checking rules completed.");
    }
    
    
    //gets all abstract terms from the file
    //does not include abstract terms that are tree roots (eg. mouse, human or abstract terms whose parent link has been deleted)
    private ArrayList getAbstractAnatomyChildren(TreeBuilder treebuilder, Component abstractClass, Component groupClass){
        
        ArrayList<Component> abstractAnatomyChildren = new ArrayList<Component>();
        Vector<String> vRoots = treebuilder.getTreeRoots();
        
        if ( abstractRootList.isEmpty() ){
            proceed = false; //no tree roots have the namespace configured for the abstract anatomy in the gui //might be incorrectly named namespace
        }
        else{    
            for (int i = 0; i < this.proposedTermList.size(); i++){
                Component compie = this.proposedTermList.get(i);
                //is an abstract anatomy term
                if ( compie.getNamespace().equals( abstractClass.getNamespace() ) )
                    abstractAnatomyChildren.add( compie );
                //is a new group term
                if ( compie.getNamespace().equals( groupClass.getNamespace() ) && !vRoots.contains( compie.getID() ) )
                    abstractAnatomyChildren.add( compie );
            }
            
            //for (Component compie: abstractAnatomyChildren ){ //bug: new for iterator cannot be used in conjunction with remove
            for (int k = 0; k < this.proposedTermList.size(); k++){
                Component compie = this.proposedTermList.get(k);
                //is an abstract anatomy root
                for (int j = 0; j < this.abstractRootList.size(); j++ ){
                    Component rootCompie = this.abstractRootList.get(j);
                    //if ( compie.isSameAs(rootCompie) ) //MAZE:
                    //REPLACE isSamsAs with matching id, two compies can be the same but have modified properties
                    if ( compie.getID().equals(rootCompie.getID()) ){
                        abstractAnatomyChildren.remove( compie );
                    }
                }
                //is the first root, mouse - only checks name against gui
                //if ( compie.getName().equals(species) ){
                //    abstractAnatomyChildren.remove( compie );
                //}
                //is a concept of abstract anatomy, mouse conceptus
                if ( compie.getIsA().contains( abstractClass.getID() ) ){
                    abstractAnatomyChildren.remove( compie );
                }
                //is a valid delete term with obsolete property
                if ( compie.commentsContain("INFO: Obsolete Term") )
                    abstractAnatomyChildren.remove( compie );
            }
        }
        return abstractAnatomyChildren;
    }

    //match all roots of the tree to the configured roots
    private void validateConfiguredRoots(TreeBuilder treebuilder, Component abstractClass, Component stageClass, Component groupClass, Component groupTermClass ){
        
        String abstractID = abstractClass.getID();
        String stageID = stageClass.getID();
        String groupID = groupClass.getID();
        String groupTermID = groupTermClass.getID();
        String abstractNameSpace = abstractClass.getNamespace();
        String stageNameSpace = stageClass.getNamespace();
        String groupNameSpace = groupClass.getNamespace();
        String groupTermNameSpace = groupTermClass.getNamespace();
        String abstractName = abstractClass.getName();
        String stageName = stageClass.getName();
        String groupName = groupClass.getName();
        String groupTermName = groupTermClass.getName();
        String rootNameSpace = "";
        String rootName = "";

        Vector<String> roots = treebuilder.getTreeRoots();
        for(String emapID: roots){
            Component rootCompie = treebuilder.getComponent(emapID);
            rootNameSpace = rootCompie.getNamespace();
            rootName = rootCompie.getName();
            
            if ( abstractID.equals( emapID ) && abstractNameSpace.equals( rootNameSpace ) && abstractName.equals( rootName ) ){               
                //rootCompie.setStrRuleStatus("PASSED");
                abstractClass.setStrRuleStatus("PASSED"); //note not the tree component - but gui ref component
                //set abstract anatomy tree roots
                this.abstractRootList.add( rootCompie );
            }
            else if ( stageID.equals( emapID ) && stageNameSpace.equals( rootNameSpace ) && stageName.equals( rootName ) ){
                stageClass.setStrRuleStatus("PASSED");
                //set root group_term to isPrimary = false to exclude primary paths leading back to this term from pool of possible primary paths
                rootCompie.setIsPrimary(false);
            }
            else if ( groupID.equals( emapID ) && groupNameSpace.equals( rootNameSpace ) && groupName.equals( rootName ) ){
                groupClass.setStrRuleStatus("PASSED");
                //set root group_term to isPrimary = false to exclude primary paths leading back to this term from pool of possible primary paths
                rootCompie.setIsPrimary(false);
            }
            else if ( groupTermID.equals( emapID ) && groupTermNameSpace.equals( rootNameSpace ) && groupTermName.equals( rootName ) ){
                groupTermClass.setStrRuleStatus("PASSED");
                //set root group_term to isPrimary = false to exclude primary paths leading back to this term from pool of possible primary paths
                rootCompie.setIsPrimary(false);
            }
            else if ( rootCompie.commentsContain("INFO: Obsolete Term") ){
                //obsolete terms appear as roots
                //don't allow to fail
                rootCompie.setStrRuleStatus("PASSED");
                rootCompie.setCheckComment("Component has been deleted correctly from OBO File and can be scheduled for deletion in database");
                if ( this.changesTermList!=null ) {
                    this.changesTermList.add(rootCompie);
                } //<=== this is the component arraylist to generate SQL queries!!!
            }
            else {
                rootCompie.setStrRuleStatus("FAILED");
                rootCompie.setCheckComment("INFO: Root node not defined by OBO2DB NameSpace Configurations.");
                this.problemTermList.remove(rootCompie); //make sure it isn't added twice
                this.problemTermList.add(rootCompie);
            }
        }
    }
    
    //path checking performed for all components (in abstract anatomy except mouse)
    //set primary path :- a valid primary path = all nodes must be primary
    //set alternate paths 
    //check that each component only has one primary path :- flag
    private void validatePaths(TreeBuilder treebuilder, Component abstractClass){
        
        Vector<DefaultMutableTreeNode[]> paths = new Vector<DefaultMutableTreeNode[]>();
        boolean isPrimaryPath = false;
        
        //get all the terms that are in the abstract_anatomy (except mouse)
        //for each term get all the paths
            //for each path check whether it is primary
                //if primary check whether component.primaryPath == empty
                    //if empty, set component.primaryPath = this path
                    //if not empty, set blue flag = true, set comment = more than one primary path!, add to problem terms
                //if not primary, add to component.path list
        
        for ( Component compie: this.abstractTermList ){
            
            //reset the primary path for all components to null everytime this method is called
            compie.setPrimaryPath(null);
            compie.setPaths( new Vector< DefaultMutableTreeNode[] >() );
            paths = treebuilder.getPaths( compie.getID() );
            
            for (DefaultMutableTreeNode[] path: paths){
                
                isPrimaryPath = !( treebuilder.hasGroupNodeAsAncestor(path, compie) );
                //try first one 
                //isPrimaryPath = !( treebuilder.isPrimaryPath(pathTo) );
                
                if ( isPrimaryPath ){
                    if ( compie.getPrimaryPath()==null ) compie.setPrimaryPath(path);
                    else{
                        //DON'T FAIL THEM YET - UNLESS THEY ARE A DIRECT DESCENDANT OF TWO PRIMARY PARENTS
                        //ISSUE WARNING INSTEAD
                        compie.setStrRuleStatus("FAILED");//prev block
                        compie.setFlagLifeTime(true);//prev block
                        TreePath printPath = new TreePath( compie.shortenPath(path) );
                        compie.setCheckComment("More than one primary path! Alternate primary path = " + printPath);
                        //this.problemTermList.remove(compie);
                        //this.problemTermList.add(compie);
                    }
                }
                else {
                    if ( treebuilder.isPathInNamespace(path, abstractClass) ) compie.addPaths(path);
                }
            }
        }
    }
    
    private void checkAbstractAnatomyParents(TreeBuilder tree){
        
        int primaryParents = 0;
        ArrayList<String> primaryParentsList = new ArrayList();
        
        for ( Component compie: this.abstractTermList ){
            //reset counter primaryParents
            primaryParents = 0;
            //get all parents
            ArrayList<String> parents = new ArrayList();
            parents.addAll( compie.getPartOf() );
            parents.addAll( compie.getGroupPartOf() );
            //count how many primary parents
            primaryParentsList.clear();
            for ( String parent: parents ){
                Component parentCompie = tree.getComponent(parent);
                if ( parentCompie.getIsPrimary() ) {
                    primaryParents++;
                    primaryParentsList.add( parentCompie.toString() );
                }
            }
            //if more than one primary parent failed
            if ( primaryParents > 1 ) {
                compie.setStrRuleStatus("FAILED");
                compie.setFlagLifeTime(true);
                compie.setCheckComment("More than one primary parent! Primary Parents are: " + primaryParentsList);
                this.problemTermList.remove(compie);
                this.problemTermList.add(compie);
            }
        }
    }
    
    private void checkAbstractAnatomyLinks() {
        //Red check
        //checks for missing starts_at, ends_at, part_of relationships
        //checks that ends_at must be > starts_at
        //marks components in red, passes black components on to second check
        
        //this.passRedTermList.addAll(this.proposedTermList);
        //use abstractterm list instead 
        this.passRedTermList.addAll(this.abstractTermList);
        Component compie;
        boolean failed;
        
        //for (int i = 0; i < this.proposedTermList.size(); i++) {
        //    compie = this.proposedTermList.get(i);
        for (int i = 0; i < this.abstractTermList.size(); i++){
            compie = this.abstractTermList.get(i);
            
                //check missing ends_at stage
                if (compie.getEndsAt().equals("")) {
                    //System.out.println(compie.getID() + ": " + "Relation: Ends At -- Missing ends_at stage!");
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Relation: ends_at -- Missing ends at stage - Component's stage range cannot be determined.");
                    //remove from termlist before passing to blue check
                    //this.passRedTermList.remove(compie);
                    //add to problem list and update rule status = failed
                    //this.problemTermList.add(compie);
                    compie.setStrRuleStatus("FAILED");
                }
                //check missing starts_at stage
                if (compie.getStartsAt().equals("")) {
                    //System.out.println(compie.getID() + ": " + "Relation: Starts At -- Missing starts_at stage!");
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Relation: starts_at -- Missing starts at stage - Component's stage range cannot be determined.");
                    compie.setStrRuleStatus("FAILED");
                }
                //check missing part_of link
                if (compie.getIsPrimary() && compie.getPartOf().isEmpty()) {
                    //System.out.println(compie.getID() + ": " + "Relation: Part Of -- No parent entry!");
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Relation: part_of -- Missing relationship - Component has no parents.");
                    compie.setStrRuleStatus("FAILED");
                }
                //if group component and missing group part of link //must cross check with ref file
                //cannot allow user to create own groups
                if ( !compie.getIsPrimary() && compie.getGroupPartOf().isEmpty() ){
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Relation: group_part_of -- Missing relationship - Group component has no parents.");
                    compie.setStrRuleStatus("FAILED");
                }
                //check whether end after (or same stage as) start 
                if (compie.getIntEndsAt() < compie.getIntStartsAt()) {
                    //System.out.println(compie.getID() + ": " + "Relation: Ends At + Starts At -- Ends_at stage earlier than Starts_at stage!");
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Relation: starts_at, ends_at -- Ends at stage earlier than starts at stage.");
                    compie.setStrRuleStatus("FAILED");
                }
                //check whether stages are out of range
                if ( (compie.getIntStartsAt() < 1) || (compie.getIntEndsAt() > 28) ){
                    //System.out.println(compie.getID() + ": " + "Relation: Stages are out of range! [Start:" + compie.getStartsAt() + ", Ends:" + compie.getEndsAt() + "]");
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Relation: starts_at, ends_at -- Stages are out of range! [Start:" + compie.getStartsAt() + ", Ends:" + compie.getEndsAt() + "] Component cannot exist earlier than Stage 1 or later than Stage 28.");
                    compie.setStrRuleStatus("FAILED");
                }
                //check whether component is linked to a phantom parent (marked already by mapbuilder)
                //if ( compie.getCheckComments().contains("Broken Link: Phantom parent") ){
                if ( compie.commentsContain("Broken Link: Phantom parent") ){
                    //System.out.println(compie.getID() + ": " + "Relation: Part Of -- Parent has been deleted from file!");
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Orphan component -- Phantom parent! At least one term specified as a parent for this component could have been deleted. Please locate/include the parent terms for this component in the OBO tree!");
                    compie.setStrRuleStatus("FAILED");
                }
                //check whether each component has been assigned a primary path in validatePaths()
                if ( compie.getPrimaryPath()==null){
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("No primary path!");
                    compie.setStrRuleStatus("FAILED");
                }
                //check whether there are any invalid order comments that can't be picked up in order validation because they are invalid
                if ( compie.hasIncorrectOrderComments() ){
                    if ( compie.getID().equals("EMAPA:16118") ){
                        System.out.println("hideho");
                    }
                    compie.setFlagMissingRel(true);
                    //comment already set in Component class
                    compie.setStrRuleStatus("FAILED");
                }

                failed = compie.getStrRuleStatus().equals("FAILED"); 
                if ( failed ) {
                    this.passRedTermList.remove(compie);
                    this.problemTermList.remove(compie);
                    this.problemTermList.add( compie );
                }
                else compie.setStrRuleStatus("PASSED");
        }
    }

    
    //check for all components in the abstract anatomy (except mouse)
    //for the primary path, each node must be within the lifetime of the preceding node
    private void checkAbstractAnatomyStages(){
        
        //get the primary path for each component
        //for each path, iterate through each node and convert to component
            //if node = root ignore
        
        this.passBlueTermList.addAll( this.passRedTermList );

        //iterate for each component in termList
        for (Component compie: this.passRedTermList) {
            
            DefaultMutableTreeNode[] primaryPath = compie.getPrimaryPath();


            for(int pointer=2; pointer<primaryPath.length-1 && proceed; pointer++){
                Component parent = (Component) primaryPath[pointer].getUserObject();
                Component child = (Component) primaryPath[pointer+1].getUserObject();

                boolean within = ( ( parent.getIntStartsAt() <= child.getIntStartsAt() ) && ( parent.getIntEndsAt() >= child.getIntEndsAt() ) );
                if ( compie.getID().equals("EMAPA:31495") && !within ){
                    System.out.println( "Checking stage range for " + child.getID() );
                    System.out.println( "starts at " + child.getIntStartsAt() );
                    System.out.println( "ends at " + child.getIntEndsAt() );
                    System.out.println( "parent " + parent.getID() + " starts at " + parent.getIntStartsAt() );
                    System.out.println( "parent " + parent.getID() + " ends at " + parent.getIntEndsAt() );
                    System.out.println( "parent.getIntStartsAt() <= child.getIntStartsAt = " + Boolean.toString(parent.getIntStartsAt() <= child.getIntStartsAt()) );
                    System.out.println( "parent.getIntEndsAt() >= child.getIntEndsAt = " + parent.getIntEndsAt() + ">=" + child.getIntEndsAt() + " " + Boolean.toString(parent.getIntEndsAt() >= child.getIntEndsAt()) );
                }
                
                //boolean default_proceed = ( !parent.getNamespace().equals("abstract_anatomy") );
                if (!within) {
                    compie.setFlagLifeTime(true);
                    compie.setStrRuleStatus("FAILED");
                    compie.setCheckComment("Stage out of range: [" + child.getID() + " " + 
                            child.getName() + " " + child.getStartsAt() + "-" + child.getEndsAt() + 
                            "] is not within lifetime of parent [" + parent.getID() + " " + parent.getName() + " " +
                            parent.getStartsAt() + "-" + parent.getEndsAt() + "]");
                    this.problemTermList.add(compie);
                }
            }
        }
    }
    

    private void checkOrdering(TreeBuilder tree, Component abstractClass){
        //get all children for each component
        //check that ordering has no gaps

        //get abstract anatomy node
        String abstractID = abstractClass.getID();
        DefaultMutableTreeNode abstractmothernode = new DefaultMutableTreeNode();

        for (Enumeration<DefaultMutableTreeNode> eRootChildren = tree.getRootNode().children(); eRootChildren.hasMoreElements(); ){
            Component rootChildCompie = (Component) eRootChildren.nextElement().getUserObject();
            if ( rootChildCompie.getID().equals( abstractID ) ){
                abstractmothernode = eRootChildren.nextElement();
            }
        }

        //get all nodes in the tree, starting from root node
        Vector< DefaultMutableTreeNode > allNodes = new Vector< DefaultMutableTreeNode >();
        allNodes = tree.recursiveGetNodes( abstractmothernode, allNodes );
        //initialise children container, order container, child component, max ordering number
        Vector< Component > childrenCompie = new Vector< Component >();
        Vector< String > childrenOrder = new Vector< String >();
        int intMaxOrder = -1;
        boolean failedChild = false;
        boolean proceed = false;
        Component childCompie = new Component();
        //iterate all nodes
        for (DefaultMutableTreeNode nodie: allNodes){
            //clear children container and order container and maxseq for each parent
            childrenCompie.clear();
            childrenOrder.clear();
            intMaxOrder = -1;
            failedChild = false;
            proceed = true;
            //get all children
            for (Enumeration<DefaultMutableTreeNode> eChildren = nodie.children() ; eChildren.hasMoreElements() ;) {
                childrenCompie.add( (Component) eChildren.nextElement().getUserObject() );
            }
            //convert parent node to component
            Component parentCompie = (Component) nodie.getUserObject();
            //stop order checking if parent is a failed component anyway
            proceed = parentCompie.getStrRuleStatus().equals("FAILED") ? false : true;
            //stop order checking if any of the children fail
            for (Component compie: childrenCompie){
                failedChild = compie.getStrRuleStatus().equals("FAILED") ? true : false ;
                if (failedChild && !parentCompie.getStrRuleStatus().equals("FAILED") ){
                    //set fail to parent
                    parentCompie.setCheckComment("Ordering: The ordering for this components children will be ignored because one of the child components has a rule violation.");
                    parentCompie.setFlagMissingRel(true);
                    parentCompie.setStrRuleStatus("FAILED");
                    this.problemTermList.add(parentCompie);
                    proceed = false;
                }
            }

            //iterate through all children to check whether they have an order
            for (int k=0; k<childrenCompie.size() && proceed; k++){
                childCompie = childrenCompie.get(k);

                //get order from child compie based on the parent
                String[] arrOrderComments = childCompie.getOrderCommentOnParent(parentCompie.getID());
                //if there is an order put in order vector
                if ( arrOrderComments!=null ){
                    for (int i=0; i<arrOrderComments.length; i++){
                        childrenOrder.add(arrOrderComments[i]);
                        //find max order number for this series of siblings
                        String[] arrayFirstWord = arrOrderComments[i].split(" ");
                        if ( Integer.parseInt(arrayFirstWord[0]) > intMaxOrder ){
                            intMaxOrder = Integer.parseInt(arrayFirstWord[0]);
                            //System.out.println("max seq = " + intMaxOrder);
                        }
                    }
                }
            }

            //if max order+1 not == number of comments there are duplicate order sequence numbers
            if ( !childrenOrder.isEmpty() && childrenOrder.size()!=intMaxOrder+1 ){
                System.out.println("intMaxOrder = " + intMaxOrder + " childrenOrder.size = " + childrenOrder.size());
                //set fail to parent
                parentCompie.setCheckComment("Ordering: One of this component's children has a duplicate order sequence.");
                parentCompie.setFlagMissingRel(true);
                parentCompie.setStrRuleStatus("FAILED");
                this.problemTermList.add(parentCompie);
                for (Component compie: childrenCompie){
                    compie.setCheckComment("Ordering: One of the siblings of this component or this component itself has a duplicate order sequence.");
                    compie.setStrRuleStatus("FAILED");
                    compie.setFlagMissingRel(true);
                    this.problemTermList.add(compie);
                }
            //if order vector is not empty, there is at least one child with order
            }else if ( !childrenOrder.isEmpty() ){
                //check no gaps
                boolean notMatch = true;
                boolean flagStop = false;
                //for order 0 to max sequence number in siblings
                for (int i=0; i<=intMaxOrder && !flagStop; i++){
                    notMatch = true;
                    //find in all children
                    for (int k=0; k<childrenOrder.size() && notMatch; k++){
                        //an order that matches i
                        String strOrder = childrenOrder.get(k).substring(0, Integer.toString(i).length());
                        if ( strOrder.equals(Integer.toString(i)) ){
                            //System.out.println("Child of " + parentCompie.getID() + " has order " + strOrder);
                            notMatch=false;
                        } 
                    }
                    //order not found for i
                    if ( notMatch ){
                        flagStop = true; //stop check gaps process
                        //set fail to parent
                        parentCompie.setCheckComment("Ordering: One of this component's children has an incorrect sequence order.");
                        parentCompie.setFlagMissingRel(true);
                        parentCompie.setStrRuleStatus("FAILED");
                        this.problemTermList.add(parentCompie);
                        for (Component compie: childrenCompie){
                            compie.setCheckComment("Ordering: One of the siblings of this component or this component itself has an incorrect order sequence.");
                            compie.setStrRuleStatus("FAILED");
                            compie.setFlagMissingRel(true);
                            this.problemTermList.add(compie);
                        }
                    }
                }
            }
        }
    }

    private void checkChanges( Component abstractClass ) {
        Component proposed,  reference;
        boolean flagFound;
        int intTest = 0;
           
        //Look for new and changed nodes 
        //For each component in newTermList
        for (Iterator<Component> i = this.abstractTermList.iterator(); i.hasNext();){
        //for (Iterator<Component> i = this.proposedTermList.iterator(); i.hasNext();) {
            proposed = i.next();
            
            //set to unchanged 
            //proposed.setStrChangeStatus("UNCHANGED");
            flagFound = false;
            //look for component in oldTermList
            for (Iterator<Component> k = this.referenceTermList.iterator(); k.hasNext() && !flagFound;) {
                reference = k.next();

                //if found,
                if ( proposed.getID().equals( reference.getID() ) ) {
                    //compare: if same do nothing
                    if (proposed.isSameAs(reference)) {
                        //set to unchanged 
                        proposed.setStrChangeStatus("UNCHANGED");
                    } //else mark green in newTermList and add to new ArrayList
                    //can't detect deleted components here because component has been removed from abstractTermList
                    /*else if ( proposed.getStrChangeStatus().equals("DELETED") && proposed.commentsContain("INFO: Obsolete Term")){
                        System.out.println("Deleted component detected: " + proposed.getID());
                        ArrayList<String> formerParents = reference.getPartOf();
                        formerParents.addAll( reference.getGroupPartOf() );
                        proposed.setCheckComment("Obsolete term formerly linked to : " + formerParents);
                        this.changesTermList.add(proposed); //<=== this is the component arraylist to generate SQL queries!!!
                    }*/
                    else {
                        proposed.setStrChangeStatus("CHANGED");
                        //System.out.println("Difference detected, compared proposed: " + proposed.getID() + " vs reference: " + reference.getID());
                        ArrayList<String> arrDifference = proposed.getDifferenceWith(reference);
                        for ( String diff: arrDifference )
                            proposed.setCheckComment( diff );
                        this.changesTermList.add(proposed); //<=== this is the component arraylist to generate SQL queries!!!
                    }
                    //change flag to found so that iterator stops
                    flagFound = true;
                }
            }
            //iterated through whole list 
            //if not found, 
            if ( !flagFound && proposed.getStrChangeStatus().equals("DELETED") ) {
                proposed.setCheckComment("Obsolete term does not exist in database. No deletion will take place.");
                this.changesTermList.add(proposed);
            }
            else if ( !flagFound ){
                proposed.setStrChangeStatus("NEW");
                this.changesTermList.add(proposed); //<=== this is the component arraylist to generate SQL queries!!!
            }

        }
        
        //Look for deleted nodes
        //For each component in referenceTermList
        for (Iterator<Component> i = this.referenceTermList.iterator(); i.hasNext();) {
            reference = i.next();
            
                flagFound = false;
                //look for component in newTermList
                for (Iterator<Component> k = this.proposedTermList.iterator(); k.hasNext() && !flagFound;) {
                    proposed = k.next();

                    //if found,
                    if (reference.getID().equals(proposed.getID())) {
                        //set flagFound true to stop iterator
                        flagFound = true;
                    }
                }
                //iterated through whole list 
                //if not found
                if (!flagFound && reference.getNamespace().equals( abstractClass.getNamespace() ) ) {
                    reference.setStrChangeStatus("DELETED");
                    reference.setStrRuleStatus("FAILED");   
                    reference.setCheckComment("INFO: Component was deleted from OBO file bypassing obsolete procedures.");
                    reference.setFlagMissingRel(true);
                    //add comment for parents in reference file
                    ArrayList<String> formerParents = reference.getPartOf();
                    formerParents.addAll( reference.getGroupPartOf() );
                    reference.setCheckComment("Obsolete term used to have former parents: " + formerParents);
                    //remove parents and add back to proposedlist to display as root
                    ArrayList<String> emptyList = new ArrayList<String>();
                    reference.setPartOf( emptyList );
                    reference.setGroupPartOf( emptyList );
                    
                    //add to proposedTermList
                    this.proposedTermList.add(reference);
                    //add to problemTermList, disallowed deletions
                    this.problemTermList.add(reference); 
                    this.changesTermList.add(reference); //<=== this is the component arraylist to generate SQL queries!!!

                }
                //if not found:-
                //1. destroyed in OBOEdit:- not in file at all
                //2. text deleted manually:- not in file at all
        }  
    }
    
    //method called from MainGUI, passing component and treebuilder object
    //not necessarily the same treebuilder as the one used and instantiated in the constructor
    public Component getCommonAncestor(DefaultMutableTreeNode groupNode, TreeBuilder treebuilder){
        
        DefaultMutableTreeNode commonAncestor = new DefaultMutableTreeNode(); 
        Component compie = new Component();
        Component descCompie = new Component();
        Component copyCompie = new Component();
        int earliestStart = 28;
        int latestEnd = 0;
        
        //get all child nodes
        //note: child nodes of a component are different from child components
        Vector< DefaultMutableTreeNode > childNodes = new Vector< DefaultMutableTreeNode >();
        childNodes = treebuilder.recursiveGetNodes( groupNode, childNodes );
        
        //get primary paths for all child nodes
        Vector< DefaultMutableTreeNode[] > primaryPaths = new Vector< DefaultMutableTreeNode[] >();
        for(int i = 0; i < childNodes.size(); i++){
            descCompie = (Component) childNodes.get(i).getUserObject(); 
            //if one descendant does not have a primary path, common ancestor cannot be determined
            if ( descCompie.getPrimaryPath()==null ) return compie;
            primaryPaths.add( descCompie.getPrimaryPath() ) ;
            //get the earliest start stage and latest end stage amongst all descendants
            earliestStart = ( descCompie.getIntStartsAt() < earliestStart ) ? descCompie.getIntStartsAt() : earliestStart;
            latestEnd = ( descCompie.getIntEndsAt() > latestEnd ) ? descCompie.getIntEndsAt() : latestEnd;
        } 
 
        //if there are primary paths find the common ancestor for all primary paths
        if ( !primaryPaths.isEmpty() ){   
            commonAncestor = findCommonAncestor(primaryPaths, treebuilder);
            //check if return node for commonAncestor is a component
            Object o = commonAncestor.getUserObject();
            if (o instanceof Component){
                compie = (Component) commonAncestor.getUserObject();
                //create a clone of the common ancestor to use for passing the calculated lifetime for the new group component
                copyCompie = compie.clone();
                copyCompie.setStartsAt( earliestStart );
                copyCompie.setEndsAt( latestEnd );
            }
        }
        return copyCompie;
    }
    
    private DefaultMutableTreeNode findCommonAncestor( Vector< DefaultMutableTreeNode[] > paths, TreeBuilder tree){
        
        DefaultMutableTreeNode[] basePath = paths.firstElement();
        DefaultMutableTreeNode commonAncestor = new DefaultMutableTreeNode("not found");
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(); //current node
        boolean found = true;
        int pathsWithNode = 0;
        
        if (basePath.length==0) return commonAncestor;
        else{
            //compare base path against all paths in path list
            for (int pointer = basePath.length-1; pointer>=0; pointer--){
            
                //get furthest node 
                node = basePath[pointer];
                //reset pathsWithNode for each new node in base path
                pathsWithNode = 0;

                for (DefaultMutableTreeNode[] path: paths){
                    found = tree.containsNode(path, node);
                    if (found) pathsWithNode++;
                }
                if ( pathsWithNode==paths.size() ) return node;
            
            }//finish comparing all nodes in base path 
        }
        
        return commonAncestor;
    }
    
    public void clearStatusComments( ArrayList < Component > termList ){
        for ( Component compie: termList ){
            if ( !compie.commentsContain("INFO: Obsolete Term") && //clear everything except deleted terms that can't be detected any other way
                 !compie.commentsContain("New Group Component") ) { //and recently added to common ancestor group components
                compie.clearCheckComment();
                compie.setStrRuleStatus("UNCHECKED");
                compie.setStrChangeStatus("UNCHECKED");
                compie.setFlagLifeTime(false);
                compie.setFlagMissingRel(false);
            }
        }
    }
    
    public ArrayList getProblemTermList(){
        return this.problemTermList;
    }
    
    public ArrayList getChangesTermList(){
        return this.changesTermList;
    }
    
    public ArrayList getNewTermList(){
        ArrayList < Component > newTerms = new ArrayList<Component>();
        for ( Component term: this.changesTermList ){
            if ( term.getStrChangeStatus().equals("NEW") ) newTerms.add( term );
        }
        return newTerms;
    }
    
    public ArrayList getDeletedTermList(){
        ArrayList < Component > deletedTerms = new ArrayList<Component>();
        for ( Component term: this.changesTermList ){
            if ( term.getStrChangeStatus().equals("DELETED") ) deletedTerms.add( term );
        }
        return deletedTerms;
    }
    
    public ArrayList getModifiedTermList(){
        ArrayList < Component > modifiedTerms = new ArrayList<Component>();
        for ( Component term: this.changesTermList ){
            if ( term.getStrChangeStatus().equals("CHANGED") ) modifiedTerms.add( term );
        }
        return modifiedTerms;
    }
    
    public ArrayList getGroupTermList(){
        return this.groupTermList;
    }
    
    public ArrayList getProposedTermList(){
        return this.proposedTermList;
    }
    
}
