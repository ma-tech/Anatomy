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
    private HashMap<String, OBOComponent> hashmapTreeProperties;
    
    //< EMAPA : ID -> children (emapa:id) >
    private HashMap<String, Vector<String>> hashmapTreeChildren;
    
    private Vector<String> vectorRootNodes;

	//map < EMAPA : ID => paths >
    private HashMap<String, Vector<DefaultMutableTreeNode[]>> hashmapTreePaths;
    
    private DefaultMutableTreeNode defaultmutabletreenodeMother;

    private String requestMsgLevel;
    
    //----------------------------------------------------------------------------------------------
    // Constructor ---------------------------------------------------------------------------------
    //  # 1 - Minimal
    public TreeAnatomy() throws Exception {

		this.requestMsgLevel = "";
		
        this.arraylistOBOComponents = new ArrayList<OBOComponent>();
        
        this.hashmapTreeProperties = new HashMap<String, OBOComponent>();
        this.hashmapTreeChildren = new HashMap<String, Vector<String>>();
        
        this.vectorRootNodes = new Vector<String>();
        
        this.arraylistOBOComponents = new ArrayList<OBOComponent>();
        
        this.hashmapTreePaths = new HashMap<String, Vector<DefaultMutableTreeNode[]>>();

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
        
        setRootNode();
        
        createHashmapTreeProperties();
        
        createHashmapChildrenProperties();

        findRootNodes();

        recursivelyBuildTree();
    }

    
    // Getters ------------------------------------------------------------------------------------
    public ArrayList<OBOComponent> getArraylistOBOComponents() {
        return this.arraylistOBOComponents;
    }
    public HashMap<String, OBOComponent> getHashmapTreeProperties() {
        return this.hashmapTreeProperties;
    }
    public HashMap<String, Vector<String>> getHashmapTreeChildren() {
        return this.hashmapTreeChildren;
    }
    public Vector<String> getVectorRootNodes() {
        return this.vectorRootNodes;
    }
    public HashMap<String, Vector<DefaultMutableTreeNode[]>> getHashmapTreePaths() {
        return this.hashmapTreePaths;
    }
    public DefaultMutableTreeNode getDefaultmutabletreenodeMother() {
        return this.defaultmutabletreenodeMother;
    }

    // Setters ------------------------------------------------------------------------------------
    public void setArraylistOBOComponents(ArrayList<OBOComponent> arraylistOBOComponents) {
        this.arraylistOBOComponents = arraylistOBOComponents;
    }
    public void setHashmapTreeProperties(HashMap<String, OBOComponent> hashmapTreeProperties) {
        this.hashmapTreeProperties = hashmapTreeProperties;
    }
    public void setHashmapTreeChildren(HashMap<String, Vector<String>> hashmapTreeChildren) {
        this.hashmapTreeChildren = hashmapTreeChildren;
    }
    public void setVectorRootNodes(Vector<String> vectorRootNodes) {
        this.vectorRootNodes = vectorRootNodes;
    }
    public void setHashmapTreePaths(HashMap<String, Vector<DefaultMutableTreeNode[]>> hashmapTreePaths) {
        this.hashmapTreePaths = hashmapTreePaths;
    }
    public void setDefaultmutabletreenodeMother(DefaultMutableTreeNode defaultmutabletreenodeMother) {
        this.defaultmutabletreenodeMother = defaultmutabletreenodeMother;
    }

    
    // Helpers ------------------------------------------------------------------------------------

    private void addToDefaultmutabletreenodeMother(DefaultMutableTreeNode rootnode) {

    	this.defaultmutabletreenodeMother.add( rootnode ) ;
    }

    
    private void addToVectorRootNodes(String strEMAPAid) {

    	this.vectorRootNodes.add( strEMAPAid) ;
    }

    
    private Vector<String> getVectorFromHashmapTreeChildren(String strEMAPAid) {

    	return this.hashmapTreeChildren.get( strEMAPAid );
    }

    
    private void addOBOComponentInHashmapTreeProperties(String strEMAPA, OBOComponent obocomponent) throws Exception{

        this.hashmapTreeProperties.put( strEMAPA, obocomponent) ;
    }
    

    public OBOComponent getOBOComponentInHashmapTreeProperties(String strEMAPAid) throws Exception{

        return (OBOComponent) this.hashmapTreeProperties.get( strEMAPAid );
    }
    
 
    public Vector<DefaultMutableTreeNode[]> getTreePaths(String strEMAPAid) {

    	return this.hashmapTreePaths.get( strEMAPAid );
    }


    private void putHashmapTreePaths(String strEMAPAid, Vector<DefaultMutableTreeNode[]> paths) {

    	//System.out.println("putHashmapTreePaths " + strEMAPAid);
    	
    	this.hashmapTreePaths.put( strEMAPAid, paths );
    }


    private void putVectorIntoHashmapTreeChildren(String strEMAPAid, Vector<String> stringVector) {

    	this.hashmapTreeChildren.put( strEMAPAid, stringVector );
    }


    private void setRootNode() throws Exception {

    	this.defaultmutabletreenodeMother = new DefaultMutableTreeNode("root");
    }

    
    private void createHashmapTreeProperties() throws Exception {
    	
        Wrapper.printMessage("TreeAnatomy.createHashmapTreeProperties", "*****", this.requestMsgLevel);
    	
        Iterator<OBOComponent> iteratorObocomponents = this.arraylistOBOComponents.iterator();
        
      	while ( iteratorObocomponents.hasNext() ) {
      		
      		OBOComponent obocomponent = iteratorObocomponents.next();
            
            addOBOComponentInHashmapTreeProperties(obocomponent.getID(), obocomponent);
        }
    }

    
    private void createHashmapChildrenProperties() throws Exception {
    	
        Wrapper.printMessage("TreeAnatomy.createHashmapChildrenProperties", "*****", this.requestMsgLevel);

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
  	    		OBOComponent instance_of_comp = getOBOComponentInHashmapTreeProperties(Parent);
                
  	    		if ( instance_of_comp == null ) {
                
  	    			Wrapper.printMessage("TreeAnatomy.createHashmapChildrenProperties : Parent has been deleted from file: " +
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
  	    			addToVectorRootNodes(Parent);
                    
  	    			Wrapper.printMessage("TreeAnatomy.createHashmapChildrenProperties : obocomponent.toString() = " + 
                            obocomponent.toString() + "!", "*", this.requestMsgLevel);
  	    		}
                
  	    		Vector<String> stringVector = getVectorFromHashmapTreeChildren(Parent);
                
  	    		if ( stringVector == null ) {
                
  	    			stringVector = new Vector<String>();
  	    		}
                
  	    		stringVector.add(obocomponent.getID());
                
  	    		putVectorIntoHashmapTreeChildren(Parent, stringVector);
      	    }
      	}
      	
      	//System.out.println("this.hashmapTreeChildren.size() " + this.hashmapTreeChildren.size());
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
            	
            	//System.out.println("Root = " + obocomponent.getID() );

            	addToVectorRootNodes( obocomponent.getID() );
            }
        }
    }

    
    private void recursivelyBuildTree() throws Exception {

        Wrapper.printMessage("TreeAnatomy.recursivelyBuildTree", "*****", this.requestMsgLevel);
        
        //build tree starting from all defined roots
        Iterator<String> iteratorVectorString = this.vectorRootNodes.iterator();
        
        while (iteratorVectorString.hasNext()) {

        	String node = iteratorVectorString.next();
            
        	//System.out.println("recursivelyBuildTree " + node);
        	
        	DefaultMutableTreeNode rootnode = 
            		recursivelyAddNode( node,
            				new DefaultMutableTreeNode[]{getDefaultmutabletreenodeMother()} );
            
            addToDefaultmutabletreenodeMother(rootnode);
        }
    }
    
    
    private DefaultMutableTreeNode recursivelyAddNode(String key, DefaultMutableTreeNode[] parent_path) throws Exception {

        Wrapper.printMessage("TreeAnatomy.recursivelyAddNode", "*****", this.requestMsgLevel);
        	
    	//traverse from root component
        OBOComponent obocomponent = getOBOComponentInHashmapTreeProperties(key);

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
            
            addOBOComponentInHashmapTreeProperties(key, obocomponent);
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
        Vector<String> vectorString = getVectorFromHashmapTreeChildren(key);
        
        //BUILD PATHS
        //get paths for each component
        Vector<DefaultMutableTreeNode[]> paths = getTreePaths(key);

        if ( paths == null ) {
        	
            paths = new Vector<DefaultMutableTreeNode[]>();
        }
        
        /*
        if ( key.equals("EMAPA:0") ||
        		key.equals("group_term") ||
        		key.equals("Tmp_new_group") ||
        		key.equals("TS:0") ) {
        	
            System.out.println("paths.size() " + paths.size());
        }
        */

        //create new path for each component, copy old path passed from last traversal
        DefaultMutableTreeNode[] new_path = new DefaultMutableTreeNode[parent_path.length+1];
        
        System.arraycopy(parent_path, 0, new_path, 0, parent_path.length);
        
        //add current component to new path; add new path to paths for current component
        new_path[parent_path.length] = newnode;
        paths.add(new_path);
        
        //entry in treePaths <component -> paths>
        putHashmapTreePaths(key, paths);
        //BUILD PATHS END

        //traverse down each branch of the tree to add all child nodes and create paths
        
        if ( vectorString != null ) {
        	
            Iterator<String> iteratorVectorString = vectorString.iterator();
            
            //System.out.println("recursivelyAddNode " + iteratorVectorString.toString());
            
            while (iteratorVectorString.hasNext()) {

            	String stringRecurse = iteratorVectorString.next();
            	
                //System.out.println("recursivelyAddNode " + stringRecurse);
                
                newnode.add(recursivelyAddNode(stringRecurse, new_path));
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

    
    public Vector<DefaultMutableTreeNode> getNodes( String strEMAPA, Vector<DefaultMutableTreeNode[]> paths ) throws Exception{

        Wrapper.printMessage("TreeAnatomy.getNodes", "*****", this.requestMsgLevel);
        	
        //method that returns nodes of a component
        
        Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();

        if ( paths == null) {
        	
            Wrapper.printMessage("TreeAnatomy.getNodes : ID " + strEMAPA + ",  Nodes " + nodes + "!", "*", this.requestMsgLevel);
        }
        
        for ( DefaultMutableTreeNode[] path: paths ){
        	
            //get last node in each path
            DefaultMutableTreeNode node = path[path.length - 1];
            nodes.add(node);
        } 
        
        return nodes;
    }
    
    
    public Vector<DefaultMutableTreeNode[]> getPathsTo(String strEMAPA, Vector<DefaultMutableTreeNode[]> paths) throws Exception{
        
        Wrapper.printMessage("TreeAnatomy.getPathsTo", "*****", this.requestMsgLevel);
        
        if (paths == null) {
        	
        	System.out.println("Paths are null!");
        }
        
        for ( int i = 0; i< paths.size(); i++ ) {
        	
            DefaultMutableTreeNode[] path = paths.get(i);

            if (path.length > 0) {
            	
                DefaultMutableTreeNode[] pathTo = new DefaultMutableTreeNode[path.length - 1];
                
                System.arraycopy(path, 0, pathTo, 0, path.length - 1);   
                
                paths.remove(i);
                
                paths.add(pathTo);
            }
        }

        return paths;
    }
    

    public Vector<String> getAsStringPathsTo(String strEMAPA, Vector<DefaultMutableTreeNode[]> paths) throws Exception{
     
        Wrapper.printMessage("TreeAnatomy.getAsStringPathsTo", "*****", this.requestMsgLevel);
        	
        Vector<String> strPaths = new Vector<String>();

        String strPathway = "";
        		
        for ( DefaultMutableTreeNode[] path: paths ){

            for (DefaultMutableTreeNode pathway: path){

          		Object nodeInfo = pathway.getUserObject(); 
                
                if (nodeInfo instanceof OBOComponent){
                	
                	OBOComponent obocomponent = (OBOComponent) nodeInfo;
                	
                	strPathway = strPathway + obocomponent.getID() + "(" + obocomponent.getName() + ");";
                }
            }
            
            strPaths.add( strPathway );
            strPathway = "";
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

