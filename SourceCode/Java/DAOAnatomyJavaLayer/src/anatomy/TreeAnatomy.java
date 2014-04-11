/*
---------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        TreeAnatomy.java
*
* Date:         2014
*
* Author:       Mike Wicks
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
* Description:  This Class builds the Anatomy into various Tree Structures
*                
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; April 2014; Create Class
* 
---------------------------------------------------------------------------------------------
*/
package anatomy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import utility.Wrapper;

import obomodel.OBOComponent;


public class TreeAnatomy {

    //----------------------------------------------------------------------------------------------
    // Attributes ----------------------------------------------------------------------------------

	private ArrayList<OBOComponent> arraylistOBOComponents;

    //< EMAPA : ID -> component object >
    private HashMap<String, OBOComponent> hashmapTreePropertiesAll;
    
    //< EMAPA : ID -> children (emapa:id) >
    private HashMap<String, Vector<String>> hashmapTreeChildrenAll;
    
    //< EMAPA : ID -> children (emapa:id) >
    private HashMap<String, Vector<String>> hashmapTreeChildrenPartOfs;
    
    private Vector<String> vectorRootNodes;

	//map < EMAPA : ID => paths >
    private HashMap<String, Vector<DefaultMutableTreeNode[]>> hashmapTreePathsAll;
    
	//map < EMAPA : ID => paths >
    private HashMap<String, Vector<DefaultMutableTreeNode[]>> hashmapTreePathsPartOfs;
    
    
    private DefaultMutableTreeNode defaultmutabletreenodeMotherAll;

    private DefaultMutableTreeNode defaultmutabletreenodeMotherPartOfs;

    
    private String requestMsgLevel;
    
    //----------------------------------------------------------------------------------------------
    // Constructor ---------------------------------------------------------------------------------
    //  # 1 - Minimal
    public TreeAnatomy() throws Exception {

		this.requestMsgLevel = "";
		
        this.arraylistOBOComponents = new ArrayList<OBOComponent>();
        
        this.hashmapTreePropertiesAll = new HashMap<String, OBOComponent>();
        this.hashmapTreeChildrenAll = new HashMap<String, Vector<String>>();
        
        this.hashmapTreeChildrenPartOfs = new HashMap<String, Vector<String>>();
        
        this.vectorRootNodes = new Vector<String>();
        
        this.arraylistOBOComponents = new ArrayList<OBOComponent>();
        
        this.hashmapTreePathsAll = new HashMap<String, Vector<DefaultMutableTreeNode[]>>();

        this.hashmapTreePathsPartOfs = new HashMap<String, Vector<DefaultMutableTreeNode[]>>();

    }
    
    // Constructor ---------------------------------------------------------------------------------
    //  # 2 - With list if OBOComponents - MAXIMAL
    public TreeAnatomy(
    		String requestMsgLevel, 
    		ArrayList<OBOComponent> arraylistOBOComponent) throws Exception {

    	this();
    	
		this.requestMsgLevel = requestMsgLevel;

		Wrapper.printMessage("TreeAnatomy.constructor #2", "*****", this.requestMsgLevel);
        
        this.arraylistOBOComponents.addAll(arraylistOBOComponent);
        
        setRootNodes();
        
        createHashmapTreePropertiesAll();
        createHashmapChildrenPropertiesPartOfs();
        createHashmapChildrenPropertiesAll();

        findRootNodes();
        
        recursivelyBuildTreeAll();
        recursivelyBuildTreePartOfs();
    }

    
    // Getters ------------------------------------------------------------------------------------
    public ArrayList<OBOComponent> getArraylistOBOComponents() {
        return this.arraylistOBOComponents;
    }
    public HashMap<String, OBOComponent> getHashmapTreePropertiesAll() {
        return this.hashmapTreePropertiesAll;
    }
    public HashMap<String, Vector<String>> getHashmapTreeChildrenAll() {
        return this.hashmapTreeChildrenAll;
    }
    public HashMap<String, Vector<String>> getHashmapTreeChildrenPartOfs() {
        return this.hashmapTreeChildrenPartOfs;
    }
    public Vector<String> getVectorRootNodes() {
        return this.vectorRootNodes;
    }
    public HashMap<String, Vector<DefaultMutableTreeNode[]>> getHashmapTreePathsAll() {
        return this.hashmapTreePathsAll;
    }
    public DefaultMutableTreeNode getDefaultmutabletreenodeMotherAll() {
        return this.defaultmutabletreenodeMotherAll;
    }
    public HashMap<String, Vector<DefaultMutableTreeNode[]>> getHashmapTreePathsPartOfs() {
        return this.hashmapTreePathsPartOfs;
    }
    public DefaultMutableTreeNode getDefaultmutabletreenodeMotherPartOfs() {
        return this.defaultmutabletreenodeMotherPartOfs;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setArraylistOBOComponents(ArrayList<OBOComponent> arraylistOBOComponents) {
        this.arraylistOBOComponents = arraylistOBOComponents;
    }
    public void setHashmapTreePropertiesAll(HashMap<String, OBOComponent> hashmapTreePropertiesAll) {
        this.hashmapTreePropertiesAll = hashmapTreePropertiesAll;
    }
    public void setHashmapTreeChildrenAll(HashMap<String, Vector<String>> hashmapTreeChildrenAll) {
        this.hashmapTreeChildrenAll = hashmapTreeChildrenAll;
    }
    public void setHashmapTreeChildrenPartOfs(HashMap<String, Vector<String>> hashmapTreeChildrenPartOfs) {
        this.hashmapTreeChildrenPartOfs = hashmapTreeChildrenPartOfs;
    }
    public void setVectorRootNodes(Vector<String> vectorRootNodes) {
        this.vectorRootNodes = vectorRootNodes;
    }
    public void setHashmapTreePathsAll(HashMap<String, Vector<DefaultMutableTreeNode[]>> hashmapTreePathsAll) {
        this.hashmapTreePathsAll = hashmapTreePathsAll;
    }
    public void setDefaultmutabletreenodeMotherAll(DefaultMutableTreeNode defaultmutabletreenodeMotherAll) {
        this.defaultmutabletreenodeMotherAll = defaultmutabletreenodeMotherAll;
    }
    public void setHashmapTreePathsPartOfs(HashMap<String, Vector<DefaultMutableTreeNode[]>> hashmapTreePathsPartOfs) {
        this.hashmapTreePathsPartOfs = hashmapTreePathsPartOfs;
    }
    public void setDefaultmutabletreenodeMother(DefaultMutableTreeNode defaultmutabletreenodeMotherPartOfs) {
        this.defaultmutabletreenodeMotherPartOfs = defaultmutabletreenodeMotherPartOfs;
    }


    
    // Helpers ------------------------------------------------------------------------------------

    private void setRootNodes() throws Exception {

        Wrapper.printMessage("TreeAnatomy.setRootNodes", "*****", this.requestMsgLevel);
    	
    	this.defaultmutabletreenodeMotherAll = new DefaultMutableTreeNode("root");
    	this.defaultmutabletreenodeMotherPartOfs = new DefaultMutableTreeNode("root");
    }

    
    private void createHashmapTreePropertiesAll() throws Exception {
    	
        Wrapper.printMessage("TreeAnatomy.createHashmapTreePropertiesAll", "*****", this.requestMsgLevel);
    	
        Iterator<OBOComponent> iteratorObocomponents = this.arraylistOBOComponents.iterator();
        
      	while ( iteratorObocomponents.hasNext() ) {
      		
      		OBOComponent obocomponent = iteratorObocomponents.next();
            
            this.hashmapTreePropertiesAll.put(obocomponent.getID(), obocomponent);
        }
    }

    
    private void createHashmapChildrenPropertiesPartOfs() throws Exception {
    	
        Wrapper.printMessage("TreeAnatomy.createHashmapChildrenPropertiesPartOfs", "*****", this.requestMsgLevel);

        Iterator<OBOComponent> iteratorObocomponents = this.arraylistOBOComponents.iterator();
        
      	while ( iteratorObocomponents.hasNext() ) {
      		
      	    OBOComponent obocomponent = iteratorObocomponents.next();
      		
      	    Iterator<String> iteratorObocomponentChildOfs = obocomponent.getChildOfs().iterator();
      	    Iterator<String> iteratorObocomponentChildOfTypes = obocomponent.getChildOfTypes().iterator();
            
      	    while ( iteratorObocomponentChildOfs.hasNext() ) {
            
      	    	String partOfParent = iteratorObocomponentChildOfs.next();
      	    	String partOfParentType = iteratorObocomponentChildOfTypes.next();
              	
      	    	/*
      	    	 * PART OF Relationships ONLY
      	    	 */
      	    	if ( partOfParentType.equals("PART_OF") ) {
              	
      	    		/* 
                     * RULE CHECK: broken links
                     */
      	    		OBOComponent instance_of_comp = (OBOComponent) this.hashmapTreePropertiesAll.get(partOfParent);
                    
      	    		if ( instance_of_comp == null ) {
                    
      	    			Wrapper.printMessage("TreeAnatomy.createHashmapChildrenPropertiesPartOfs : Parent has been deleted from file: " +
                        		   partOfParent + "!", "*", this.requestMsgLevel);
                        
      	    			/* 
                         * set flagMissingRel to true to display component in red add comment
                         */
      	    			obocomponent.setFlagMissingRel(true);
                        
      	    			obocomponent.setCheckComment("Broken Link: Phantom parent " +
                        		   partOfParent + " deleted from OBO file.");
                        
      	    			/*
                         * Add to root nodes so that it can be displayed in the tree
                         * treebuilder builds branches recursively from a list of
                         * rootnodes
                         */
      	    			this.vectorRootNodes.add(partOfParent);
                        
      	    			Wrapper.printMessage("TreeAnatomy.createHashmapChildrenPropertiesPartOfs : obocomponent.toString() = " + 
                                obocomponent.toString() + "!", "*", this.requestMsgLevel);
      	    		}
                    
      	    		Vector<String> stringVector = this.hashmapTreeChildrenPartOfs.get(partOfParent);
                    
      	    		if ( stringVector == null ) {
                    
      	    			stringVector = new Vector<String>();
      	    		}
                    
      	    		stringVector.add(obocomponent.getID());
                    
      	    		this.hashmapTreeChildrenPartOfs.put(partOfParent, stringVector);
      	    	}
      	    }
      	}
    }

    
    private void createHashmapChildrenPropertiesAll() throws Exception {
    	
        Wrapper.printMessage("TreeAnatomy.createHashmapChildrenPropertiesAll", "*****", this.requestMsgLevel);

        Iterator<OBOComponent> iteratorObocomponents = this.arraylistOBOComponents.iterator();
        
      	while ( iteratorObocomponents.hasNext() ) {
      		
      	    OBOComponent obocomponent = iteratorObocomponents.next();
      		
      	    Iterator<String> iteratorObocomponentChildOfs = obocomponent.getChildOfs().iterator();
            
      	    while (iteratorObocomponentChildOfs.hasNext()) {
            
      	    	String Parent = iteratorObocomponentChildOfs.next();

      	    	/*
      	    	 * ALL Relationships 
      	    	 */
  	    		/* 
                 * RULE CHECK: broken links
                 */
  	    		OBOComponent instance_of_comp = (OBOComponent) this.hashmapTreePropertiesAll.get(Parent);
                
  	    		if ( instance_of_comp == null ) {
                
  	    			Wrapper.printMessage("TreeAnatomy.createHashmapChildrenPropertiesAll : Parent has been deleted from file: " +
  	    					Parent + "!", "*", this.requestMsgLevel);
                    
  	    			/* 
                     * set flagMissingRel to true to display component in red add comment
                     */
  	    			obocomponent.setFlagMissingRel(true);
                    
  	    			obocomponent.setCheckComment("Broken Link: Phantom parent " +
  	    					Parent + " deleted from OBO file.");
                    
  	    			/*
                     * Add to root nodes so that it can be displayed in the tree
                     * treebuilder builds branches recursively from a list of
                     * rootnodes
                     */
  	    			this.vectorRootNodes.add(Parent);
                    
  	    			Wrapper.printMessage("TreeAnatomy.createHashmapChildrenPropertiesAll : obocomponent.toString() = " + 
                            obocomponent.toString() + "!", "*", this.requestMsgLevel);
  	    		}
                
  	    		Vector<String> stringVector = this.hashmapTreeChildrenAll.get(Parent);
                
  	    		if ( stringVector == null ) {
                
  	    			stringVector = new Vector<String>();
  	    		}
                
  	    		stringVector.add(obocomponent.getID());
                
  	    		this.hashmapTreeChildrenAll.put(Parent, stringVector);
      	    }
      	}
    }


    private void findRootNodes() throws Exception {

        Wrapper.printMessage("TreeAnatomy.findRootNodes", "*****", this.requestMsgLevel);
        	
        boolean notChildOf;

        for ( OBOComponent obocomponent: this.arraylistOBOComponents ){    
        	
            //if childOf is empty or itself
            notChildOf = ( obocomponent.getChildOfs().isEmpty() ||
                        ( obocomponent.getChildOfs().size() == 1 &&
                          obocomponent.getChildOfs().contains( obocomponent.getID() ) ) );

            if ( notChildOf ) {

            	this.vectorRootNodes.add( obocomponent.getID() );
            }
        }
    }

    
    private void addHashmapTreePropertyEntryAll(String strEMAPA, OBOComponent obocomponent) throws Exception{

        Wrapper.printMessage("TreeAnatomy.addHashmapTreePropertyEntryAll", "*****", this.requestMsgLevel);
        
        this.hashmapTreePropertiesAll.put(strEMAPA, obocomponent);
    }
    

    private void recursivelyBuildTreeAll() throws Exception {

        Wrapper.printMessage("TreeAnatomy.recursivelyBuildTreeAll", "*****", this.requestMsgLevel);
        
        //build tree starting from all defined roots
        for( int i=0; i< vectorRootNodes.size(); i++ ){
        	
            DefaultMutableTreeNode rootnode = 
            		recursivelyAddNodeAll( vectorRootNodes.get(i), 
            				new DefaultMutableTreeNode[]{this.defaultmutabletreenodeMotherAll} );
            
            this.defaultmutabletreenodeMotherAll.add(rootnode);
        }
    }
    
    
    private void recursivelyBuildTreePartOfs() throws Exception {

        Wrapper.printMessage("TreeAnatomy.recursivelyBuildTreePartOfs", "*****", this.requestMsgLevel);
        
        //build tree starting from all defined roots
        for ( int i=0; i< vectorRootNodes.size(); i++ ) {
        	
            DefaultMutableTreeNode rootnode = 
            		recursivelyAddNodePartOfs( vectorRootNodes.get(i), 
            				new DefaultMutableTreeNode[]{this.defaultmutabletreenodeMotherPartOfs} );
            
            this.defaultmutabletreenodeMotherPartOfs.add(rootnode);
        }
    }
    
    
    private DefaultMutableTreeNode recursivelyAddNodeAll(String key, DefaultMutableTreeNode[] parent_path) throws Exception {

        Wrapper.printMessage("TreeAnatomy.recursivelyAddNodeAll", "*****", this.requestMsgLevel);
        	
    	//traverse from root component
        OBOComponent obocomponent = (OBOComponent) this.hashmapTreePropertiesAll.get(key);

        //check that component is not null otherwise create dummy component
        // for node
        //note: dummy component's sole purpose = a node for tree display;
        // not included in any component lists
        if ( obocomponent == null ) {
        	
            obocomponent = new OBOComponent();
            obocomponent.setID(key);
            obocomponent.setName("Missing link");
            obocomponent.setFlagMissingRel(true);
            obocomponent.setCheckComment("Disallowed deletion of component from file - parent to existing components.");
            
            this.hashmapTreePropertiesAll.put(key, obocomponent);
        }

        //initialise new node for each component
        DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(obocomponent);
        
        //check for cycles
        if ( doesPathcontainComponent(parent_path, obocomponent) ){
        	
            obocomponent.setStatusRule("FAILED");
            obocomponent.setFlagMissingRel(true);
            TreePath printPath = new TreePath(parent_path);
            obocomponent.setCheckComment("There appears to be a cycle in your " +
                    "graph (" + obocomponent.getID() + " appears more than once " +
                    "in " + printPath + ")");
            
            return newnode;
        } 
        
        //get children for each component
        Vector<String> vectorString = (Vector<String>) this.hashmapTreeChildrenAll.get(key);
        
        //BUILD PATHS
        //get paths for each component
        Vector<DefaultMutableTreeNode[]> paths = (Vector<DefaultMutableTreeNode[]>) this.hashmapTreePathsAll.get(key);

        if ( paths == null ) {
        	
            paths = new Vector<DefaultMutableTreeNode[]>();
        }
        
        //create new path for each component, copy old path passed from last traversal
        DefaultMutableTreeNode[] new_path = new DefaultMutableTreeNode[parent_path.length+1];
        
        System.arraycopy(parent_path, 0, new_path, 0, parent_path.length);
        
        //add current component to new path; add new path to paths for current component
        new_path[parent_path.length] = newnode;
        paths.add(new_path);
        
        //entry in treePaths <component -> paths>
        hashmapTreePathsAll.put(key, paths);
        //BUILD PATHS END

        //traverse down each branch of the tree to add all child nodes and create paths
        if ( vectorString != null ){
        	
            for ( int i=0; i<vectorString.size(); i++ ){
            	
                newnode.add(recursivelyAddNodeAll(vectorString.get(i), new_path));
            }
        }
        
        return newnode;
    }
    

    private DefaultMutableTreeNode recursivelyAddNodePartOfs(String key, DefaultMutableTreeNode[] parent_path) throws Exception {

        Wrapper.printMessage("TreeAnatomy.recursivelyAddNodePartOfs", "*****", this.requestMsgLevel);
        	
    	//traverse from root component
        OBOComponent obocomponent = (OBOComponent) this.hashmapTreePropertiesAll.get(key);

        //check that component is not null otherwise create dummy component
        // for node
        //note: dummy component's sole purpose = a node for tree display;
        // not included in any component lists
        if ( obocomponent == null ) {
        	
            obocomponent = new OBOComponent();
            obocomponent.setID(key);
            obocomponent.setName("Missing link");
            obocomponent.setFlagMissingRel(true);
            obocomponent.setCheckComment("Disallowed deletion of component from file - parent to existing components.");
            
            this.hashmapTreePropertiesAll.put(key, obocomponent);
        }

        //initialise new node for each component
        DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(obocomponent);
        
        //check for cycles
        if ( doesPathcontainComponent(parent_path, obocomponent) ){
        	
            obocomponent.setStatusRule("FAILED");
            obocomponent.setFlagMissingRel(true);
            TreePath printPath = new TreePath(parent_path);
            obocomponent.setCheckComment("There appears to be a cycle in your " +
                    "graph (" + obocomponent.getID() + " appears more than once " +
                    "in " + printPath + ")");
            
            return newnode;
        } 
        
        //get children for each component
        Vector<String> vectorString = (Vector<String>) this.hashmapTreeChildrenAll.get(key);
        
        //BUILD PATHS
        //get paths for each component
        Vector<DefaultMutableTreeNode[]> paths = (Vector<DefaultMutableTreeNode[]>) this.hashmapTreePathsPartOfs.get(key);

        if ( paths == null ) {
        	
            paths = new Vector<DefaultMutableTreeNode[]>();
        }
        
        //create new path for each component, copy old path passed from last traversal
        DefaultMutableTreeNode[] new_path = new DefaultMutableTreeNode[parent_path.length+1];
        
        System.arraycopy(parent_path, 0, new_path, 0, parent_path.length);
        
        //add current component to new path; add new path to paths for current component
        new_path[parent_path.length] = newnode;
        paths.add(new_path);
        
        //entry in treePaths <component -> paths>
        hashmapTreePathsPartOfs.put(key, paths);
        //BUILD PATHS END

        //traverse down each branch of the tree to add all child nodes and create paths
        if ( vectorString != null){
        	
            for ( int i=0; i<vectorString.size(); i++ ){
            	
                newnode.add(recursivelyAddNodePartOfs(vectorString.get(i), new_path));
            }
        }
        
        return newnode;
    }
    
    
    public boolean doesPathcontainComponent(DefaultMutableTreeNode[] path, OBOComponent inobocomponent) throws Exception{

        Wrapper.printMessage("TreeAnatomy.doesPathcontainComponent", "*****", this.requestMsgLevel);
        	
        boolean contains = false;
        
        //iterate thru each node in the path
        for ( DefaultMutableTreeNode currentNode: path ){
        	
            //check to see whether each node in the path contains a component
            Object nodeInfo = currentNode.getUserObject();
            
            if ( nodeInfo instanceof OBOComponent ){
            	
                OBOComponent obocomponent = (OBOComponent) nodeInfo;
                //if currentcomponent is a group node, the path is not a valid path back to the root 
                //if ( !currentcomponent.getIsPrimary() ) return false;
                //match current component to targeted component
                //if ( obocomponent.isSameAs(currentcomponent) ) return true;//MAZE:
                //REPLACE isSamsAs with matching id, two obocomponents can be the same but have modified properties
                if ( inobocomponent.getID().equals(obocomponent.getID()) ) {
                	
                    return true;
                }
            }
        }   
        
        //Vector< DefaultMutableTreeNode > vPath = new Vector( java.util.Arrays.asList( path ) );  
        return contains;
    }
    

    public boolean hasComponentGroupNodeAsAncestorInPath( DefaultMutableTreeNode[] pathTo, OBOComponent inobocomponent ) throws Exception{

        Wrapper.printMessage("TreeAnatomy.hasComponentGroupNodeAsAncestorInPath", "*****", this.requestMsgLevel);
        	
    	boolean groupNodeAsAncestor = false;
    	
        //iterate thru each node in the path
        for ( DefaultMutableTreeNode currentNode: pathTo ){
        	
            //check to see whether each node in the path contains a component
            Object nodeInfo = currentNode.getUserObject();
            
            if ( nodeInfo instanceof OBOComponent ){
            	
                OBOComponent obocomponent = ( OBOComponent ) nodeInfo;
                
                //if currentcomponent is a group node
                //if ( !currentcomponent.getIsPrimary() && !currentcomponent.isComponentSameAs(obocomponent) ) {//MAZE:
                //REPLACE isSamsAs with matching id, two obocomponents can be the same but have modified properties
                //if ( !currentcomponent.getIsPrimary() && !currentcomponent.getID().equals(obocomponent.getID()) ){
                //if ( !currentcomponent.getIsGroup() && !currentcomponent.getID().equals(obocomponent.getID()) ){
                if ( obocomponent.isGroup() && !obocomponent.getID().equals(inobocomponent.getID()) ){
                    
                	groupNodeAsAncestor = true;
                }
            }
        }

        return groupNodeAsAncestor;
    }

    
    public boolean hasComponentNamespanceInPath( DefaultMutableTreeNode[] path, OBOComponent inobocomponent ) throws Exception{
        
        Wrapper.printMessage("TreeAnatomy.hasComponentNamespanceInPath", "*****", this.requestMsgLevel);

        for ( DefaultMutableTreeNode node: path ){
        	
            Object nodeInfo = node.getUserObject();
            
            if ( nodeInfo instanceof OBOComponent ){
            	
                OBOComponent obocomponent = ( OBOComponent ) nodeInfo;
                
                //if currentcomponent is a group node
                if ( !obocomponent.getNamespace().equals(inobocomponent.getNamespace()) ) {
                	
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
    public boolean containsComponentInPath(DefaultMutableTreeNode[] path, OBOComponent inobocomponent) throws Exception{

        Wrapper.printMessage("TreeAnatomy.containsComponentInPath", "*****", this.requestMsgLevel);
        	
        boolean contains = false;
        
        //iterate thru each node in the path
        for ( DefaultMutableTreeNode currentNode: path ){
        	
            //check to see whether each node in the path contains a component
            Object nodeInfo = currentNode.getUserObject();
            
            if ( nodeInfo instanceof OBOComponent ){
            	
                OBOComponent obocomponent = (OBOComponent) nodeInfo;
                
                //if currentcomponent is a group node, the path is not a valid path back to the root 
                //if ( !currentcomponent.getIsPrimary() ) return false;
                //match current component to targeted component
                //if ( obocomponent.isSameAs(currentcomponent) ) return true;//MAZE:
                //REPLACE isSamsAs with matching id, two obocomponents can be the same but have modified properties
                if ( inobocomponent.getID().equals(obocomponent.getID()) ) {
                	
                    return true;
                }
            }
        }   
        
        //Vector< DefaultMutableTreeNode > vPath = new Vector( java.util.Arrays.asList( path ) );  
        return contains;
    }
    

    public boolean containsNodeInPath(DefaultMutableTreeNode[] path, DefaultMutableTreeNode node) throws Exception{

        Wrapper.printMessage("TreeAnatomy.containsNodeInPath", "*****", this.requestMsgLevel);
        	
        boolean contains = false;
        
        OBOComponent obocomponent1 = new OBOComponent();
        
        Object nodeInfo = node.getUserObject();
        
        if ( nodeInfo instanceof OBOComponent ){
        	
        	obocomponent1 = (OBOComponent) nodeInfo;
        }
        else {
        	
            return contains;
        }
        
        //iterate thru each node in the path
        for (DefaultMutableTreeNode currentNode: path){
        	
            //check to see whether each node in the path contains a component
            Object currentNodeInfo = currentNode.getUserObject();
            
            if ( currentNodeInfo instanceof OBOComponent ){
            	
                OBOComponent obocomponent2 = (OBOComponent) currentNodeInfo;
                
                //if ( nodeCompie.isSameAs(currentcomponent) ) return true;//MAZE:
                //REPLACE isSamsAs with matching id, two obocomponents can be the same but have modified properties
                if ( obocomponent1.getID().equals(obocomponent2.getID()) ) {
                	
                    return true;
                }
            }
        }    
        
        return contains;
    }

    
    public OBOComponent getComponentInAll(String strEMAPA) throws Exception{

        Wrapper.printMessage("TreeAnatomy.getComponentInAll", "*****", this.requestMsgLevel);
    	
    	try {
    		
            OBOComponent obocomponent = (OBOComponent) this.hashmapTreePropertiesAll.get(strEMAPA);
            
            return obocomponent;
        }
        catch ( NullPointerException np ) {
        	
            //np.printStackTrace();
            return null;
        }
    }

    
    public Vector<String> getChildrenInAllBasedOnParent(String strEMAPA) throws Exception{
        
        Wrapper.printMessage("TreeAnatomy.getChildrenInAllBasedOnParent", "*****", this.requestMsgLevel);
        	
        return (Vector<String>) this.hashmapTreeChildrenAll.get(strEMAPA);
    }

    
    public Vector<DefaultMutableTreeNode> getNodesInAll( String strEMAPA ) throws Exception{

        Wrapper.printMessage("TreeAnatomy.getNodesInAll", "*****", this.requestMsgLevel);
        	
        //method that returns nodes of a component
        
        Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();
        Vector<DefaultMutableTreeNode[]> paths = this.getPathsInAll( strEMAPA );

        if ( paths == null) {
        	
            Wrapper.printMessage("TreeAnatomy.getNodesInAll : ID " + strEMAPA + ",  Nodes " + nodes + "!", "*", this.requestMsgLevel);
        }
        
        for ( DefaultMutableTreeNode[] path: paths ){
        	
            //get last node in each path
            DefaultMutableTreeNode node = path[path.length - 1];
            nodes.add(node);
        } 
        
        return nodes;
    }
    

    public Vector<DefaultMutableTreeNode[]> getPathsInAll(String strEMAPA) throws Exception{

        Wrapper.printMessage("TreeAnatomy.getPathsInAll", "*****", this.requestMsgLevel);
        	
        return this.hashmapTreePathsAll.get(strEMAPA);
    }

    
    public Vector< DefaultMutableTreeNode > getNodesInPartOfs( String strEMAPA ) throws Exception{

        Wrapper.printMessage("TreeAnatomy.TreeAnatomy", "*****", this.requestMsgLevel);
        	
        //method that returns nodes of a component
        
        Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();
        Vector<DefaultMutableTreeNode[]> paths = this.getPathsInPartOfs( strEMAPA );

        if ( paths == null) {
        	
            Wrapper.printMessage("TreeAnatomy.TreeAnatomy : ID " + strEMAPA + ",  Nodes " + nodes + "!", "*", this.requestMsgLevel);
        }
        
        for ( DefaultMutableTreeNode[] path: paths ){
        	
            //get last node in each path
            DefaultMutableTreeNode node = path[path.length - 1];
            nodes.add(node);
        } 
        
        return nodes;
    }
    

    public Vector<DefaultMutableTreeNode[]> getPathsInPartOfs(String strEMAPA) throws Exception{

        Wrapper.printMessage("TreeAnatomy.getPathsInPartOfs", "*****", this.requestMsgLevel);
        	
        return this.hashmapTreePathsPartOfs.get(strEMAPA);
    }

    
    public Vector<DefaultMutableTreeNode[]> getPathsToInAll(String strEMAPA) throws Exception{
        
        Wrapper.printMessage("TreeAnatomy.getPathsToInAll", "*****", this.requestMsgLevel);
        	
        Vector<DefaultMutableTreeNode[]> paths = getPathsInAll(strEMAPA);

        for ( int i = 0; i< paths.size(); i++ ){
        	
            DefaultMutableTreeNode[] path = paths.get(i);

            if ( path.length > 0 ) {
            	
                DefaultMutableTreeNode[] pathTo =
                        new DefaultMutableTreeNode[path.length - 1];
                        
                System.arraycopy(path, 0, pathTo, 0, path.length - 1);   
                
                paths.remove(i);
                paths.add(pathTo);
            }
        }
        
        return paths;
    }
    

    public Vector<DefaultMutableTreeNode[]> getPathsToInPartOfs(String strEMAPA) throws Exception{
        
        Wrapper.printMessage("TreeAnatomy.getPathsToInPartOfs", "*****", this.requestMsgLevel);
        	
        Vector<DefaultMutableTreeNode[]> paths = getPathsInPartOfs(strEMAPA);

        for ( int i = 0; i< paths.size(); i++ ){
        	
            DefaultMutableTreeNode[] path = paths.get(i);

            if ( path.length > 0 ) {
            	
                DefaultMutableTreeNode[] pathTo =
                        new DefaultMutableTreeNode[path.length - 1];
                        
                System.arraycopy(path, 0, pathTo, 0, path.length - 1);
                
                paths.remove(i);
                paths.add(pathTo);
            }
        }
        
        return paths;
    }
    

    public Vector<String> getAsStringPathsToInAll(String strEMAPA) throws Exception{
     
        Wrapper.printMessage("TreeAnatomy.getAsStringPathsToInAll", "*****", this.requestMsgLevel);
        	
    	Vector<DefaultMutableTreeNode[]> paths = getPathsInAll(strEMAPA);
    	
        Vector<String> strPaths = new Vector<String>();

        for ( int i = 0; i< paths.size(); i++ ){
        	
            DefaultMutableTreeNode[] path = paths.get(i);
            
            //System.out.print( path.toString() );
            strPaths.add( path.toString() );     
        }
        
        return strPaths;
    }
    

    public Vector< String > getAsStringPathsToInPartOfs(String strEMAPA) throws Exception{
     
        Wrapper.printMessage("TreeAnatomy.getAsStringPathsToInPartOfs", "*****", this.requestMsgLevel);
        	
    	Vector< DefaultMutableTreeNode[] > paths = getPathsInPartOfs(strEMAPA);
    	
        Vector< String > strPaths = new Vector< String >();

        for ( int i = 0; i< paths.size(); i++ ){
        	
            DefaultMutableTreeNode[] path = paths.get(i);
            //System.out.print( path.toString() );
            strPaths.add( path.toString() );     
        }
        
        return strPaths;
    }
    
    
    public boolean isSamePathAs(DefaultMutableTreeNode[] defaultmutabletreenodearray1, 
    		DefaultMutableTreeNode[] defaultmutabletreenodearray2) throws Exception {
        
        Wrapper.printMessage("TreeAnatomy.isSamePathAs", "*****", this.requestMsgLevel);
        	
        boolean match = true;
        
        //if not equal length return false
        if ( defaultmutabletreenodearray1.length != defaultmutabletreenodearray2.length ) {
        	
            return false;
        }
        
        //iterate thru each node in the path
        for (DefaultMutableTreeNode defaultmutabletreenode1: defaultmutabletreenodearray1){
            
            for (DefaultMutableTreeNode defaultmutabletreenode2: defaultmutabletreenodearray2){
            	
                if ( !defaultmutabletreenode1.toString().equals(defaultmutabletreenode2.toString()) ) {
                	
                    return false;
                }
            }

        }
        
        return match;        
    }
    

    public boolean isPrimaryPath(DefaultMutableTreeNode[] defaultmutabletreenodearray) throws Exception {
        
        Wrapper.printMessage("TreeAnatomy.isPrimaryPath", "*****", this.requestMsgLevel);
        	
        boolean isPrimaryPath = true;
        
        for(DefaultMutableTreeNode defaultmutabletreenode: defaultmutabletreenodearray){
        	
            Object defaultmutabletreenodeinfo = defaultmutabletreenode.getUserObject(); 
            
            if (defaultmutabletreenodeinfo instanceof OBOComponent){
            	
            	OBOComponent obocomponent = (OBOComponent) defaultmutabletreenodeinfo;
            
                if ( !obocomponent.isPrimary() ) {
                	
                    return false;
                }
            }
        }
        
        return isPrimaryPath;
    }
}

