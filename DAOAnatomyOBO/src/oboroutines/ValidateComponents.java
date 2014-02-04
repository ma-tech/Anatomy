/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerOBO
*
* Title:        CheckComponents.java renamed
*               ValidateComponents.java
*
* Date:         2008
*
* Author:       MeiSze Lam and Attila Gyenesi
*
* Copyright:    2009 Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version:      1
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Description:  This Class validates and compares lists of OBO Style components
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; February 2012; Completely rewrite 
*
*----------------------------------------------------------------------------------------------
*/
package oboroutines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import obomodel.OBOComponent;
import obolayer.OBOFactory;
import obolayer.OBOException;
import utility.Wrapper;

public class ValidateComponents {

	// CONSTANTS
    private static final int ERROR_STAGE = -1;
    
    // Attributes
    private String species;
	
    private OBOComponent abstractclassobocomponent; 
    private OBOComponent stageclassobocomponent; 
    private OBOComponent groupclassobocomponent; 
    private OBOComponent grouptermclassobocomponent; 
	
    private int minStartSequence; 
    private int maxEndSequence; 

    private ArrayList<OBOComponent> proposedTermList = null; 
    //new OBO file <= all checks are appended to this file,
    // used to build maps+trees
    
    private ArrayList<OBOComponent> referenceTermList = null;
    //reference OBO file 
    
    private ArrayList<OBOComponent> passRedTermList = null;
    //components that have passed red check

    private ArrayList<OBOComponent> passBlueTermList = null;
    //components that have passed blue check
    
    private ArrayList<OBOComponent> changesTermList = null;
    //components (new/existing) with proposed changes
    // => comobox ChangedNodes
    
    private ArrayList<OBOComponent> problemTermList = null;
    //components that have not passed red / blue check 
    // => combobox ProblemNodes
  
    private ArrayList<OBOComponent> groupTermList = null;
    //components that are group terms ..delete after testing
    
    private ArrayList<OBOComponent> abstractTermList = null;
    //components that are abstract anatomy terms excluding roots 
    // => used for ontology rules check
    
    //root nodes of the tree that are abstract anatomy terms
    // => used for ontology rules check
    private ArrayList<OBOComponent> abstractRootList = null; 

    //flag for proceeding with each check in entire class
    private boolean proceed;
    
    private String requestMsgLevel;
    //----------------------------------------------------------------------------------------------

    // Constructor ---------------------------------------------------------------------------------
    //  A - 2 Lists of Terms
    public ValidateComponents(
    		String requestMsgLevel,  
    		OBOFactory obofactory,
    		ArrayList<OBOComponent> newTermList, 
            ArrayList<OBOComponent> oldTermList, 
            TreeBuilder treebuilder ) {

    	try {
    		
            this.requestMsgLevel = requestMsgLevel;
            
            Wrapper.printMessage("validatecomponents.constructor #1 - 2 Lists", "***", this.requestMsgLevel);

        	// 1: set abstract class parameters
            // 2: set stage class parameters
            // 3: temporary new group class parameters
            // 4: group term class parameters
            this.abstractclassobocomponent = new OBOComponent();
            this.stageclassobocomponent = new OBOComponent();
            this.groupclassobocomponent = new OBOComponent();
            this.grouptermclassobocomponent = new OBOComponent();

            this.abstractclassobocomponent.setName( obofactory.getOBOComponentAccess().abstractClassName() ); 
            this.abstractclassobocomponent.setID( obofactory.getOBOComponentAccess().abstractClassId() ); 
            this.abstractclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().abstractClassNamespace() ); 
            
            this.stageclassobocomponent.setName( obofactory.getOBOComponentAccess().stageClassName() ); 
            this.stageclassobocomponent.setID( obofactory.getOBOComponentAccess().stageClassId() ); 
            this.stageclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().stageClassNamespace() ); 

            this.groupclassobocomponent.setName( obofactory.getOBOComponentAccess().groupClassName() ); 
            this.groupclassobocomponent.setID( obofactory.getOBOComponentAccess().groupClassId() ); 
            this.groupclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().groupClassNamespace() ); 

            this.grouptermclassobocomponent.setName( obofactory.getOBOComponentAccess().groupTermClassName() ); 
            this.grouptermclassobocomponent.setID( obofactory.getOBOComponentAccess().groupTermClassId() ); 
            this.grouptermclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().groupTermClassNamespace() ); 
            
            this.species = obofactory.getOBOComponentAccess().species();

            this.minStartSequence = obofactory.getOBOComponentAccess().minStageSequence();
            this.maxEndSequence = obofactory.getOBOComponentAccess().maxStageSequence(); 
            
            //clear all comments and status from original term list so that
            // children of
            // new groups with calculated common ancestor
            //  will not have any rule violations anymore
            // A-2
            clearStatusComments( newTermList );

            //instantiate term lists
            this.proposedTermList = newTermList;
            this.referenceTermList = oldTermList;
            this.passRedTermList = new ArrayList<OBOComponent>();
            this.passBlueTermList = new ArrayList<OBOComponent>();
            this.changesTermList = new ArrayList<OBOComponent>();
            this.problemTermList = new ArrayList<OBOComponent>();
            this.groupTermList = new ArrayList<OBOComponent>(); 
            this.abstractTermList = new ArrayList<OBOComponent>();

            //not in use yet
            this.proceed = true;

            //prepare abstract anatomy term list
            // check that roots of tree are same as configuration,
            //  also detect correctly deleted components, all other changes are
            //   detected in checkchanges
            this.abstractRootList = new ArrayList<OBOComponent>();

            // A-3
            validateConfiguredRoots(treebuilder);

            // A-4
            this.abstractTermList = (ArrayList<OBOComponent>) getAbstractAnatomyChildren(treebuilder);

            /*
            System.out.println("this.abstractTermList.size() = " + this.abstractTermList.size());
            System.out.println("this.proposedTermList.size() = " + this.proposedTermList.size());
            System.out.println("this.referenceTermList.size() = " + this.referenceTermList.size());
            */
            
            // A-5
            //set and validate primary + alternate paths to abstract anatomy terms
            validatePaths(treebuilder);

            //perform checks on abstract anatomy terms
            if ( this.proceed ){
            	
                // A-6
            	//check for missing/incorrect relationships
                checkAbstractAnatomyLinks();

                // A-7
                //check for only one primary parent
                checkAbstractAnatomyParents( treebuilder );

                // A-8
                //check that component is within life time of primary parent
                checkAbstractAnatomyStages();
                
                // A-10
                checkChanges();
            }
    	}
        catch ( OBOException obo ) {
        	
            obo.printStackTrace();
        }
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        }

    }


    // Constructor ---------------------------------------------------------------------------------
    //  B - 1 List of Terms
    public ValidateComponents(
    		String requestMsgLevel, 
    		OBOFactory obofactory, 
    		ArrayList<OBOComponent> newTermList, 
    		TreeBuilder treebuilder) {

    	try {
    		
            this.requestMsgLevel = requestMsgLevel;
    	
            Wrapper.printMessage("validatecomponents.constructor #2 - 1 List", "***", this.requestMsgLevel);

            // 1: set abstract class parameters
            // 2: set stage class parameters
            // 3: temporary new group class parameters
            // 4: group term class parameters
            this.abstractclassobocomponent = new OBOComponent();
            this.stageclassobocomponent = new OBOComponent();
            this.groupclassobocomponent = new OBOComponent();
            this.grouptermclassobocomponent = new OBOComponent();
            
            this.species = obofactory.getOBOComponentAccess().species();

            this.abstractclassobocomponent.setName( obofactory.getOBOComponentAccess().abstractClassName() ); 
            this.abstractclassobocomponent.setID( obofactory.getOBOComponentAccess().abstractClassId() ); 
            this.abstractclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().abstractClassNamespace() ); 
            
            this.stageclassobocomponent.setName( obofactory.getOBOComponentAccess().stageClassName() ); 
            this.stageclassobocomponent.setID( obofactory.getOBOComponentAccess().stageClassId() ); 
            this.stageclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().stageClassNamespace() ); 

            this.groupclassobocomponent.setName( obofactory.getOBOComponentAccess().groupClassName() ); 
            this.groupclassobocomponent.setID( obofactory.getOBOComponentAccess().groupClassId() ); 
            this.groupclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().groupClassNamespace() ); 

            this.grouptermclassobocomponent.setName( obofactory.getOBOComponentAccess().groupTermClassName() ); 
            this.grouptermclassobocomponent.setID( obofactory.getOBOComponentAccess().groupTermClassId() ); 
            this.grouptermclassobocomponent.setNamespace( obofactory.getOBOComponentAccess().groupTermClassNamespace() ); 
            
            this.minStartSequence = obofactory.getOBOComponentAccess().minStageSequence();
            this.maxEndSequence = obofactory.getOBOComponentAccess().maxStageSequence(); 

            //clear all comments and status from original term list
            // B-2
            clearStatusComments( newTermList );

            //instantiate term lists        
            this.proposedTermList = newTermList;
            this.passRedTermList = new ArrayList<OBOComponent>();
            this.passBlueTermList = new ArrayList<OBOComponent>();
            this.problemTermList = new ArrayList<OBOComponent>();
            this.groupTermList = new ArrayList<OBOComponent>(); 
            this.abstractTermList = new ArrayList<OBOComponent>();

            //not in use yet
            this.proceed = true;

            //prepare abstract anatomy term list
            // check that roots of tree are same as configuration
            this.abstractRootList = new ArrayList<OBOComponent>();
            
            // B-3
            validateConfiguredRoots(treebuilder);
            
            // B-4
            this.abstractTermList = (ArrayList<OBOComponent>) getAbstractAnatomyChildren(treebuilder);

            //set and validate primary + alternate paths to abstract anatomy terms
            // B-5
            validatePaths(treebuilder);

            //perform checks on abstract anatomy terms
            if ( this.proceed ){
            	
                // B-6
                //check for missing/incorrect relationships
                checkAbstractAnatomyLinks();
                // B-7
                //check that component is within life time of primary parent
                //checkAbstractAnatomyStages();
                // B-8
                //no reference file
                //checkChanges();
            }
    	}
        catch ( OBOException obo ) {
        	
            obo.printStackTrace();
        }
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        }
    }

    
    /*
     * A-2
     * B-2
     * 
     * Clear all the Status Comments and Flags
     */
   public void clearStatusComments( ArrayList<OBOComponent>termList ) throws Exception{

       Wrapper.printMessage("validatecomponents.clearStatusComments", "***", this.requestMsgLevel);
       	
       for ( OBOComponent obocomponent: termList ){
    	   
           //clear everything except deleted terms that can't be detected any
           // other way
           // and recently added to common ancestor group components
           if ( !obocomponent.commentsContain("INFO: Obsolete Term") && 
                !obocomponent.commentsContain("New Group Component") ) {
           	
               obocomponent.clearCheckComment();
               obocomponent.setStatusRule("UNCHECKED");
               obocomponent.setStatusChange("UNCHECKED");
               obocomponent.setFlagLifeTime(false);
               obocomponent.setFlagMissingRel(false);
           }
       }
   }


    /*
     * A-3
     * B-3
     * 
     * match all roots of the tree to the configured roots
     */
    private void validateConfiguredRoots(TreeBuilder treebuilder ) throws Exception{

    	/*
    	 * This routine examines the roots in the tree
    	 *  this includes any proposed INSERT, UPDATE or DELETE components
    	 *   we need to check that these conform to the root characteristics
    	 * 
    	 */
        Wrapper.printMessage("validatecomponents.validateConfiguredRoots", "***", this.requestMsgLevel);
        
        String rootNameSpace = "";
        String rootName = "";
        ArrayList<String> rootComments;

        Vector<String> roots = treebuilder.getTreeRoots();
        
        //System.out.println("roots.size():" + roots.size());

        for(String emapID: roots){
        	
            OBOComponent rootobocomponent = treebuilder.getComponent(emapID);
            
            rootNameSpace = rootobocomponent.getNamespace();
            rootName = rootobocomponent.getName();
            rootComments = rootobocomponent.getUserComments();

            /*
            if ( emapID.equals("EMAPA:35041") 
        	|| ( emapID.equals("JFA:0003000") ) ) {
        	
                System.out.println("=============");
                System.out.println("emapID        = " + emapID);
                System.out.println("rootNameSpace = " + rootNameSpace);
                System.out.println("rootName      = " + rootName);
                System.out.println("=============");
                
                Iterator<String> iteratorRootComments = rootComments.iterator();
                
             	while (iteratorRootComments.hasNext()) {
            		
            		String comment = iteratorRootComments.next();
                    System.out.println("rootComment:" + comment);
             	}

                System.out.println("abstractclassobocomponent.getID()         = " + abstractclassobocomponent.getID());
                System.out.println("abstractclassobocomponent.getNamespace()  = " + abstractclassobocomponent.getNamespace());
                System.out.println("abstractclassobocomponent.getName()       = " + abstractclassobocomponent.getName());
                System.out.println("-------------");
                System.out.println("stageclassobocomponent.getID()            = " + stageclassobocomponent.getID());
                System.out.println("stageclassobocomponent.getNamespace()     = " + stageclassobocomponent.getNamespace());
                System.out.println("stageclassobocomponent.getName()          = " + stageclassobocomponent.getName());
                System.out.println("-------------");
                System.out.println("groupclassobocomponent.getID()            = " + groupclassobocomponent.getID());
                System.out.println("groupclassobocomponent.getNamespace()     = " + groupclassobocomponent.getNamespace());
                System.out.println("groupclassobocomponent.getName()          = " + groupclassobocomponent.getName());
                System.out.println("-------------");
                System.out.println("grouptermclassobocomponent.getID()        = " + grouptermclassobocomponent.getID());
                System.out.println("grouptermclassobocomponent.getNamespace() = " + grouptermclassobocomponent.getNamespace());
                System.out.println("grouptermclassobocomponent.getName()      = " + grouptermclassobocomponent.getName());
                System.out.println("=============");
            }
            */

            if ( abstractclassobocomponent.getID().equals( emapID ) && 
            	abstractclassobocomponent.getNamespace().equals( rootNameSpace ) && 
            	abstractclassobocomponent.getName().equals( rootName ) ){
            	/*
            	System.out.println("------");
                System.out.println("PASSED - abstractclassobocomponent");
            	System.out.println("------");
            	*/
                abstractclassobocomponent.setStatusRule("PASSED"); 
                //note not the tree component - but gui ref component
                //set abstract anatomy tree roots
                this.abstractRootList.add( rootobocomponent );
            }
            else if ( stageclassobocomponent.getID().equals( emapID ) && 
            		stageclassobocomponent.getNamespace().equals( rootNameSpace ) && 
            		stageclassobocomponent.getName().equals( rootName ) ) {
            	/*
            	System.out.println("------");
                System.out.println("PASSED - stageclassobocomponent");
            	System.out.println("------");
            	*/
                stageclassobocomponent.setStatusRule("PASSED");
                //set root group_term to isPrimary = false 
                //  to exclude primary paths leading back to this term from
                //   pool of
                //    possible primary paths
                rootobocomponent.setIsPrimary(false);
            }
            else if ( groupclassobocomponent.getID().equals( emapID ) && 
            		groupclassobocomponent.getNamespace().equals( rootNameSpace ) && 
            		groupclassobocomponent.getName().equals( rootName ) ){
            	/*
            	System.out.println("------");
                System.out.println("PASSED - groupclassobocomponent");
            	System.out.println("------");
            	*/
                groupclassobocomponent.setStatusRule("PASSED");
                //set root group_term to isPrimary = false to exclude primary paths 
                // leading back to this term from pool of possible primary paths
                rootobocomponent.setIsPrimary(false);
            }
            else if ( grouptermclassobocomponent.getID().equals( emapID ) && 
            		grouptermclassobocomponent.getNamespace().equals( rootNameSpace ) && 
            		grouptermclassobocomponent.getName().equals( rootName ) ){
            	/*
            	System.out.println("------");
                System.out.println("PASSED - grouptermclassobocomponent");
            	System.out.println("------");
            	*/
                grouptermclassobocomponent.setStatusRule("PASSED");
                //set root group_term to isPrimary = false to exclude primary paths 
                // leading back to this term from pool of possible primary paths
                rootobocomponent.setIsPrimary(false);
            }
            else if ( rootobocomponent.commentsContain("INFO: Obsolete Term") ) {
            	
                //obsolete terms appear as roots
                //don't allow to fail
            	/*
            	System.out.println("------");
                System.out.println("PASSED - INFO: Obsolete Term");
            	System.out.println("------");
            	*/
            	rootobocomponent.setStatusRule("PASSED");
                rootobocomponent.setCheckComment("Component has been deleted " +
                        "correctly from OBO File and can be scheduled for " +
                        "deletion in database");
                
                /*
                if ( this.changesTermList != null ) {
                    //<=== this is the component arraylist to generate SQL
                    //      queries!!!
                    this.changesTermList.add(rootobocomponent);
                    
                }
                */
            }
        }
    }


    /*
     * A-4
     * B-4
     * 
     * gets all abstract terms from the file
     *  does not include abstract terms that are tree roots (eg. mouse, 
     *   human or abstract terms whose parent link has been deleted)
     */
    private ArrayList<OBOComponent> getAbstractAnatomyChildren(TreeBuilder treebuilder) throws Exception{

        Wrapper.printMessage("validatecomponents.getAbstractAnatomyChildren", "***", this.requestMsgLevel);
        
        ArrayList<OBOComponent> abstractAnatomyChildren = new ArrayList<OBOComponent>();
        
        Vector<String> vRoots = treebuilder.getTreeRoots();

        //if ( this.abstractRootList.isEmpty() ){
        if ( vRoots.isEmpty() ){
        	
        	Wrapper.printMessage("validatecomponents.getAbstractAnatomyChildren : Failed NO TREE ROOTS!", "*", this.requestMsgLevel);
            //no tree roots have the namespace configured for the abstract 
            // anatomy in the gui 
            //might be incorrectly named namespace
            proceed = false; 
        }
        else {
        	
            for (int i = 0; i < this.proposedTermList.size(); i++){
            	
                OBOComponent obocomponent = this.proposedTermList.get(i);

                //is an abstract anatomy term
                if ( obocomponent.getNamespace().equals( abstractclassobocomponent.getNamespace() ) ) {
                	
                    abstractAnatomyChildren.add( obocomponent );
                }
                else {

            		/*
                	if ( obocomponent.getID().equals("EMAPA:29976") || ( obocomponent.getID().equals("JFA:0003000") ) ) {
                        System.out.println("Failed Condition #1");
                        System.out.println("obocomponent.getNamespace() = " + obocomponent.getNamespace());
                        System.out.println("abstractclassobocomponent.getNamespace() = " + abstractclassobocomponent.getNamespace());
                	}
                    */
                }
                
                //is a new group term
                if ( obocomponent.getNamespace().equals( groupclassobocomponent.getNamespace() ) &&
                     !vRoots.contains( obocomponent.getID() ) ) {
                	
                    abstractAnatomyChildren.add( obocomponent );
                }
                else {

                	/*
                	if ( obocomponent.getID().equals("EMAPA:29976") || ( obocomponent.getID().equals("JFA:0003000") ) ) {
                        System.out.println("Failed Condition #2");
                        System.out.println("obocomponent.getNamespace() = " + obocomponent.getNamespace());
                        System.out.println("groupclassobocomponent.getNamespace() = " + groupclassobocomponent.getNamespace());
                	}
                    */
                }
                
                //is a new group term
                if ( obocomponent.getNamespace().equals( grouptermclassobocomponent.getNamespace() ) &&
                     !vRoots.contains( obocomponent.getID() ) ) {
                	
                    abstractAnatomyChildren.add( obocomponent );
                }
                else {

                	/*
                	if ( obocomponent.getID().equals("EMAPA:29976") || ( obocomponent.getID().equals("JFA:0003000") ) ) {
                        System.out.println("Failed Condition #3");
                        System.out.println("obocomponent.getNamespace() = " + obocomponent.getNamespace());
                        System.out.println("grouptermclassobocomponent.getNamespace() = " + grouptermclassobocomponent.getNamespace());
                	}
                    */
                }
            }

            //for (Component obocomponent: abstractAnatomyChildren ){ 
            // bug: new for iterator cannot be used in conjunction with remove
            for (int k = 0; k < this.proposedTermList.size(); k++){
            	
                OBOComponent obocomponent = this.proposedTermList.get(k);

                //is an abstract anatomy root
                for (int j = 0; j < this.abstractRootList.size(); j++ ){
                	
                    OBOComponent rootobocomponent = this.abstractRootList.get(j);
                    //if ( obocomponent.isComponentSameAs(rootobocomponent) ) 
                    //MAZE:
                    // REPLACE isSamsAs with matching id, two obocomponents can be the 
                    //  same but have modified properties
                    if ( obocomponent.getID().equals(rootobocomponent.getID()) ){
                    	
                        abstractAnatomyChildren.remove( obocomponent );
                    }

                    /*
                    if ( obocomponent.commentsContain("INFO: Obsolete Term") ){
                    	
                        abstractAnatomyChildren.remove( obocomponent );
                    }
                    */
                }
                //is a concept of abstract anatomy, mouse conceptus
                if ( obocomponent.getChildOfs().contains( abstractclassobocomponent.getID() ) ){
                	
                    abstractAnatomyChildren.remove( obocomponent );
                }
            }
        }
        
        return abstractAnatomyChildren;
    }


    /*
     * A-5
     * B-5
     * 
     * validate the paths in the tree
     */
    private void validatePaths(TreeBuilder treebuilder) throws Exception{

        Wrapper.printMessage("validatecomponents.validatePaths", "***", this.requestMsgLevel);
        
        Vector<DefaultMutableTreeNode[]> paths = new Vector<DefaultMutableTreeNode[]>();

        boolean isPrimaryPath = false;
        
        //get all the terms that are in the abstract_anatomy (except mouse)
        //for each term get all the paths
            //for each path check whether it is primary
                //if primary check whether component.primaryPath == empty
                    //if empty, set component.primaryPath = this path
                    //if not empty, set blue flag = true, set comment =
                    // more than one primary path!, add to problem terms
                //if not primary, add to component.path list
        
        for ( OBOComponent obocomponent: this.abstractTermList ){

        	obocomponent.setPrimaryPath(null);
            obocomponent.setPaths( new Vector< DefaultMutableTreeNode[] >() );
            paths = treebuilder.getPaths( obocomponent.getID() );

            for (DefaultMutableTreeNode[] path: paths){
            	
                isPrimaryPath = !( treebuilder.hasGroupNodeAsAncestor(
                        path, obocomponent) );
                //try first one 
                //isPrimaryPath = !( treebuilder.isPrimaryPath(pathTo) );
                
                if ( isPrimaryPath ){
                	
                    if ( obocomponent.getPrimaryPath() == null ) {
                    	
                    	obocomponent.setPrimaryPath(path);
                    }
                    else {
                    	
                    	obocomponent.setFlagLifeTime(true);
                    	/*
                        TreePath printPath = 
                                new TreePath( obocomponent.shortenPath( path) );
                        */
                    }
                }
                else {
                	
                    if ( treebuilder.isPathInNamespace(path, abstractclassobocomponent) ) {
                    	
    		            obocomponent.addPaths(path);
                    }
                }

            }
        }
    }


    /*
     * A-6
     * B-6
     * 
     * Check the links between the abstract anatomy components
     */
    private void checkAbstractAnatomyLinks( ) throws Exception {

        Wrapper.printMessage("validatecomponents.checkAbstractAnatomyLinks", "***", this.requestMsgLevel);

        //RED CHECK
        
        //checks for 
        // missing starts_at, ends_at, part_of relationships
        // ends_at must be > starts_at
        
        //marks components in red, passes black components on to second check
        
        //use abstractterm list instead 
        this.passRedTermList.addAll(this.abstractTermList);

        OBOComponent obocomponent;
        
        for (int i = 0; i < this.abstractTermList.size(); i++){
        
            obocomponent = this.abstractTermList.get(i);
            
        	obocomponent.setStatusRule("PASSED");
            
        	if ( !obocomponent.commentsContain("INFO: Obsolete Term") ) {
        		
                if (obocomponent.getEndSequence() == ERROR_STAGE && 
                    obocomponent.getStartSequence() == ERROR_STAGE ) {

                    obocomponent.setStartSequenceMin(species);
                    obocomponent.setEndSequenceMax(species);
                }
                else if (obocomponent.getEndSequence() != ERROR_STAGE && 
                    obocomponent.getStartSequence() == ERROR_STAGE ) {

                    Wrapper.printMessage("validatecomponents.checkAbstractAnatomyLinks:"+ obocomponent.getID() + ": " +
                            "Relation: Starts At -- Missing starts_at stage!", "*", this.requestMsgLevel);

                    obocomponent.setFlagMissingRel(true);
                    obocomponent.setCheckComment("Relation: starts_at -- Missing " +
                                   "starts at stage - Component's stage range cannot be " +
                                   "determined.");

                    obocomponent.setStatusRule("FAILED");
                }
                else if (obocomponent.getEndSequence() == ERROR_STAGE && 
                    obocomponent.getStartSequence() != ERROR_STAGE ) {

                    Wrapper.printMessage("validatecomponents.checkAbstractAnatomyLinks:"+ obocomponent.getID() + ": " +
                            "Relation: Ends At -- Missing ends_at stage!", "*", this.requestMsgLevel);
                               
                    obocomponent.setFlagMissingRel(true);
                    obocomponent.setCheckComment("validatecomponents.checkAbstractAnatomyLinks:" + "Relation: ends_at -- " +
                                   "Missing ends at stage - Component's stage range " +
                                   "cannot be determined.");

                    obocomponent.setStatusRule("FAILED");
                }
                else if (obocomponent.getEndSequence() != ERROR_STAGE && 
                    obocomponent.getStartSequence() != ERROR_STAGE ) {
                    	
                    if ( obocomponent.getEndSequence() < obocomponent.getStartSequence() ) {
                        	
                        Wrapper.printMessage("validatecomponents.checkAbstractAnatomyLinks:" + obocomponent.getID() + ": " +
                                "Relation: Ends At + Starts At -- Ends_at stage " +
                                "earlier than Starts_at stage!", "*", this.requestMsgLevel);
                        
                        obocomponent.setFlagMissingRel(true);
                        obocomponent.setCheckComment("Relation: starts_at, ends_at " +
                                "-- Ends at stage earlier than starts at stage.");

                        obocomponent.setStatusRule("FAILED");
                    }
                    else if ( (obocomponent.getStartSequence() < minStartSequence ) ||
                              (obocomponent.getEndSequence() > maxEndSequence ) ) {
                            	
                        Wrapper.printMessage("validatecomponents.checkAbstractAnatomyLinks:" + obocomponent.getID() + ": " +
                                "Relation: Stages are out of range! [Start: " +
                                obocomponent.getStartSequence() + ", Ends: " +
                                obocomponent.getEndSequence() + "]", "*", this.requestMsgLevel);

                        obocomponent.setFlagMissingRel(true);
                        obocomponent.setCheckComment("Relation: starts_at, ends_at " +
                                    "-- Stages are out of range! [Start Stage:" +
                                    obocomponent.getStart() +
                                    ", End Stage:" +
                                    obocomponent.getEnd() +
                                    "] OBOComponent cannot exist "+
                                    "earlier than First Stage or later than " +
                                    "Last Stage.");
                                
                        obocomponent.setStatusRule("FAILED");
                    }
                }
                
                //check missing part_of link
                if (obocomponent.getChildOfs().isEmpty() ){
                	
                    Wrapper.printMessage("validatecomponents.checkAbstractAnatomyLinks:" + obocomponent.getID() + ": " +
                            "Relation: Part Of -- No parent entry!", "*", this.requestMsgLevel);
                    
                    obocomponent.setFlagMissingRel(true);
                    obocomponent.setCheckComment("Relation: part_of -- " +
                        "Missing relationship - OBOComponent has no " +
                        "parents.");
                    
                    obocomponent.setStatusRule("FAILED");
                }

                //check whether component is linked to a phantom parent
                // (marked already by mapbuilder)
                if ( obocomponent.commentsContain("Broken Link: Phantom parent") ){

                    Wrapper.printMessage("validatecomponents.checkAbstractAnatomyLinks:" + obocomponent.getID() + ": " +
                            "Relation: Part Of -- Parent has been deleted from " +
                            "file!", "*", this.requestMsgLevel);

                    obocomponent.setFlagMissingRel(true);
                    obocomponent.setCheckComment("Orphan component -- Phantom " +
                        "parent! At least one term specified as a " +
                        "parent for this component could have been " +
                        "deleted. Please locate/include the parent " +
                        "terms for this component in the OBO tree!");

                    obocomponent.setStatusRule("FAILED");
                }

                //check whether each component has been assigned a primary
                // path in validatePaths()

                //check whether there are any invalid order comments that 
                // can't be picked up in order validation because they are
                // invalid
                /*
                if ( obocomponent.hasIncorrectOrderComments() ){

                	obocomponent.setFlagMissingRel(true);
                    obocomponent.setStatusRule("FAILED");
                }*/
        	}
        		
            if ( obocomponent.getStatusRule().equals("FAILED") ) {

                this.passRedTermList.remove(obocomponent);
                this.problemTermList.remove(obocomponent);
                this.problemTermList.add(obocomponent);
            }
        }
    }

    
    /*
     * A-8
     * B-7
     * 
     * Check the Stages between the abstract anatomy components
     * 
     *  check for all components in the abstract anatomy (except mouse)
     *   for the primary path, each node must be within the lifetime of the preceding node
     */
	private void checkAbstractAnatomyStages() throws Exception{

        Wrapper.printMessage("validatecomponents.checkAbstractAnatomyStages", "***", this.requestMsgLevel);

		//get the primary path for each component
        // for each path, iterate through each node and convert to component
        //  if node = root ignore

        this.passBlueTermList.addAll( this.passRedTermList );

        //iterate for each component in termList
        for (OBOComponent obocomponent: this.passRedTermList) {
    	    
        	Vector<DefaultMutableTreeNode[]> paths = 
    	    		(Vector<DefaultMutableTreeNode[]>) obocomponent.getPaths();

            if ( paths != null) {
                	
                for(DefaultMutableTreeNode[] path: paths){

                    for ( int pointer=2; pointer < path.length-1 && proceed; pointer++ ) {
                    
                    	OBOComponent parent = (OBOComponent) path[pointer].getUserObject();
                        OBOComponent child = (OBOComponent) path[pointer+1].getUserObject();

                        if ( !( ( parent.getStartSequence() <= child.getStartSequence() ) &&
    			                ( parent.getEndSequence() >= child.getEndSequence() ) ) ) {
                        	
                        	if ( !parent.isGroup() ) {
                        		
                                obocomponent.setFlagLifeTime(true);
                                obocomponent.setStatusRule("FAILED");
                                obocomponent.setCheckComment("Stage out of range: [" +
                                    child.getID() + " " +
                                    child.getName() + " " + 
                                    child.getStart() + "-" +
                                    child.getEnd() +
                                    "] is not within lifetime of parent [" + 
                                    parent.getID() + " " +
                                    parent.getName() + " " +
                                    parent.getStart() + "-" +
                                    parent.getEnd() + "]");

                                this.problemTermList.remove(obocomponent);
                                this.problemTermList.add(obocomponent);
                            }
                        }
                    }
                }
            }
        }
    }


    /*
     * A-7
     * 
     * Check the abstract anatomy parents
     *  There must only be 1 Primary Parent
     */
    private void checkAbstractAnatomyParents(TreeBuilder tree) throws Exception{
        
        Wrapper.printMessage("validatecomponents.checkAbstractAnatomyParents", "***", this.requestMsgLevel);
        
        int primaryParents = 0;
        ArrayList<String> primaryParentsList = new ArrayList<String>();
        
        for ( OBOComponent obocomponent: this.abstractTermList ) {
        
        	//reset counter primaryParents
            primaryParents = 0;
            
            //get all parents
            ArrayList<String> parents = new ArrayList<String>();
            parents.addAll( obocomponent.getChildOfs() );

            //count how many primary parents
            primaryParentsList.clear();
            
            for ( String parent: parents ) {
            
                OBOComponent parentcomponent = tree.getComponent(parent);
                
                if ( parentcomponent.isPrimary() ) {

                    primaryParents++;
                    primaryParentsList.add( parentcomponent.toString() );
                }
            }
            
            if ( primaryParents > 1 ) {
            	
            	obocomponent.setFlagLifeTime(true);
            }
            else {
            	
            	obocomponent.setFlagLifeTime(false);
            }
        }
    }

    /*
     * A-10
     * 
     * check for changes with referenced file - changed and new;
     *  deleted is found in validateConfigureRoots
     */
    private void checkChanges() throws Exception {

        Wrapper.printMessage("validatecomponents.checkChanges", "***", this.requestMsgLevel);
        
        OBOComponent proposed, reference;
        boolean flagFound;

        //Look for new and changed nodes 
        //For each component in newTermList
        for (Iterator<OBOComponent> i = this.abstractTermList.iterator(); i.hasNext();){

        	proposed = i.next();

            //set to unchanged 
            proposed.setStatusChange("NONE");
            proposed.setStatusRule("UNCHECKED");

            /*
            if ( proposed.getID().equals("EMAPA:32806") 
        	|| ( proposed.getID().equals("JFA:0003000") ) ) {
            	
                System.out.println("proposed.toString()  :" + proposed.toString());
            }
            */

            flagFound = false;

            //look for component in oldTermList
            for (Iterator<OBOComponent> k = this.referenceTermList.iterator(); k.hasNext() && !flagFound;) {
            	
                reference = k.next();

                /*
                if ( reference.getID().equals("EMAPA:32806") 
            	&& ( proposed.getID().equals("EMAPA:32806") ) ) {
                	
                    System.out.println("reference.toString():" + reference.toString());
                }
                */

                //if found,
                if ( proposed.getID().equals( reference.getID() ) ) {

                	/*
                	if ( proposed.getID().equals("EMAPA:32806") 
                	|| ( proposed.getID().equals("JFA:0003000") ) ) {
                    	
                        System.out.println("Here AAAAA!");
                    }
                    */

                    if ( proposed.commentsContain("INFO: Obsolete Term")) {
                    
                    	/*
                        if ( proposed.getID().equals("EMAPA:32806") 
                       	|| ( proposed.getID().equals("JFA:0003000") ) ) {
                        	
                            System.out.println("Here BBBBB!");
                        }
                        */

                        proposed.setStatusChange("DELETE");
                        proposed.setStatusRule("PASSED");

                        ArrayList<String> formerParents = reference.getChildOfs();
                        
                        proposed.setCheckComment("Obsolete term formerly linked to : " + formerParents);
                        this.changesTermList.add(proposed);
                    }
                    else {

                    	/*
                        if ( proposed.getID().equals("EMAPA:32806") 
                        	|| ( reference.getID().equals("EMAPA:32806") ) ) {
                            	
                        	System.out.println("proposed.getName()  : " + proposed.getName());
                        	System.out.println("reference.getName() : " + reference.getName());
                        }
                        */

                        if (proposed.isOBOComponentSameAs(reference) ) {

                        	/*
                            if ( proposed.getID().equals("EMAPA:32806") 
                           	|| ( proposed.getID().equals("JFA:0003000") ) ) {
                            	
                            	System.out.println("Here CCCCC!");
                            }
                            */

                            //set to unchanged 
                            proposed.setStatusChange("NONE");
                            proposed.setStatusRule("CHECKED");
                            //else mark green in newTermList and add to new ArrayList    
                        }
                        else {

                        	/*
                            if ( proposed.getID().equals("EMAPA:32806") 
                            || ( proposed.getID().equals("JFA:0003000") ) ) {
                            	
                                System.out.println("Here DDDDD!");
                            }
                            */
                        	
                            proposed.setStatusChange("UPDATE");
                            proposed.setStatusRule("PASSED");

                            ArrayList<String> arrDifference = proposed.getDifferenceWith(reference);

                            for ( String diff: arrDifference ) {
                            
                            	proposed.setCheckComment( diff );
                            	
                                Wrapper.printMessage("validatecomponents.checkChanges:Comments diff = " + diff + "!", "***", this.requestMsgLevel);
                           }
                            
                        	this.changesTermList.add(proposed);
                        }
                    }

                    //change flag to found so that iterator stops
                    flagFound = true;
                }
            }
            
            //iterated through whole list 
            //if not found, 
            if ( proposed.getStatusChange().equals("DELETE") ) {

            	/*
                if ( proposed.getID().equals("EMAPA:32806") 
            	|| ( proposed.getID().equals("JFA:0003000") ) ) {
                	
                	System.out.println("Here EEEEE!");
                }
                */
                
            	if ( !flagFound ) {
            	
            		/*
                    if ( proposed.getID().equals("EMAPA:32806") 
                	|| ( proposed.getID().equals("JFA:0003000") ) ) {
                    	
                    	System.out.println("Here FFFFF!");
                    }
                    */
                    
                    proposed.setStatusRule("FAILED");
                    proposed.setCheckComment("Obsolete term does not exist in database. No deletion will take place.");
                    this.changesTermList.add(proposed);
                }    
            }
            else {
            	
                if ( !flagFound ){
            	
                	/*
                    if ( proposed.getID().equals("EMAPA:32806") 
                	|| ( proposed.getID().equals("JFA:0003000") ) ) {
                        	
                    	System.out.println("Here GGGGG!");
                    }
                    */

                	proposed.setStatusChange("INSERT");
                    proposed.setStatusRule("PASSED");

                    this.changesTermList.add(proposed);
                }
            }
        }

        //Look for deleted nodes
        //For each component in referenceTermList
        for (Iterator<OBOComponent> i = this.referenceTermList.iterator(); i.hasNext();) {
        	
            reference = i.next();

            if ( reference.getName().equals(abstractclassobocomponent.getName() ) || 
                	reference.getNamespace().equals(stageclassobocomponent.getNamespace() ) ) {

                	flagFound = true;

                	if ( this.species.equals("human") && reference.getName().equals(this.species) ) {
                		
                		reference.setName("human conceptus");
                	}
                }

                else {

                	flagFound = false;

                    //look for component in newTermList
                    for (Iterator<OBOComponent> k = this.proposedTermList.iterator(); k.hasNext()
                         && !flagFound;) {
                    	
                        proposed = k.next();

                    	//if found,
                        if (reference.getID().equals(proposed.getID())) {
                        	
                            //set flagFound true to stop iterator
                            flagFound = true;
                        }
                    }
                }

            //iterated through whole list 
            //if not found
            if (!flagFound && 
                 !reference.getNamespace().equals( this.abstractclassobocomponent.getNamespace() ) ) {
            	
                reference.setStatusChange("DELETE");
                reference.setStatusRule("FAILED");   
                reference.setCheckComment("INFO: OBOComponent was deleted from OBO file bypassing obsolete procedures.");
                
            	reference.setFlagMissingRel(true);
 
                //add comment for parents in reference file
                ArrayList<String> formerParents = reference.getChildOfs();
                reference.setCheckComment("Obsolete term used to have former parents: " + formerParents);
 
                //remove parents and add back to proposedlist to display as root
                ArrayList<String> emptyList = new ArrayList<String>();
                reference.setChildOfs( emptyList );

                //add to proposedTermList
                this.proposedTermList.add(reference);
                //add to problemTermList, disallowed deletions
                this.problemTermList.add(reference);

                Wrapper.printMessage("validatecomponents.checkChanges:Failed OBOComponent = " + reference.getID() + "!", "*", this.requestMsgLevel);
                Wrapper.printMessage("validatecomponents.checkChanges:" + reference.toString() + "!", "*", this.requestMsgLevel);

                this.changesTermList.add(reference);
            }
        }
    }

    
    // Helpers-------------------------------------------------------------------------------------

    // Getters ------------------------------------------------------------------------------------
    public ArrayList<OBOComponent> getProblemTermList(){
    	
        return this.problemTermList;
    }
    
    public ArrayList<OBOComponent> getChangesTermList(){

        return this.changesTermList;
    }
    
    public ArrayList<OBOComponent> getNewTermList() throws Exception{
        
        Wrapper.printMessage("validatecomponents.getNewTermList", "***", this.requestMsgLevel);
           	
    	ArrayList<OBOComponent> newTerms = new ArrayList<OBOComponent>();
        
    	for ( OBOComponent term: this.changesTermList ){
    		
            if ( term.getStatusChange().equals("INSERT") ) {
            	
                newTerms.add( term );
            }
        }
    	
        return newTerms;
    }
    
    public ArrayList<OBOComponent> getDeletedTermList() throws Exception{
        
        Wrapper.printMessage("validatecomponents.getDeletedTermList", "***", this.requestMsgLevel);
        
        ArrayList<OBOComponent>deletedTerms = new ArrayList<OBOComponent>();
        
    	for ( OBOComponent term: this.changesTermList ){
    		
            if ( term.getStatusChange().equals("DELETE") ) {
            	
                deletedTerms.add( term );
            }
        }
    	
        return deletedTerms;
    }
    
    public ArrayList<OBOComponent> getModifiedTermList() throws Exception{
    
        Wrapper.printMessage("validatecomponents.getModifiedTermList", "***", this.requestMsgLevel);
        
    	ArrayList<OBOComponent>modifiedTerms = new ArrayList<OBOComponent>();
        
    	for ( OBOComponent term: this.changesTermList ){
    		
        	if ( term.getStatusChange().equals("UPDATE") ) {
        		
                modifiedTerms.add( term );
            }
        }
    	
        return modifiedTerms;
    }
    
    public ArrayList<OBOComponent> getGroupTermList(){
    	
        return this.groupTermList;
    }
    
    public ArrayList<OBOComponent> getProposedTermList(){
    	
        return this.proposedTermList;
    }
}
