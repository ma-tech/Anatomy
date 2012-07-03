/*
*----------------------------------------------------------------------------------------------
* Project:      Anatomy
*
* Title:        TreeBuilder.java
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
* Version: 1
*
* Description:  Produce a tree structure from an ArrayList of OBO Components
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; March 2012; More rejigging
*
*----------------------------------------------------------------------------------------------
*/

package routinesbase;

import java.util.Map;
import java.util.Vector;
import java.util.HashMap;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import obomodel.OBOComponent;

public class TreeBuilder {
    
    // pass from MapBuilder
    private Map<String, Vector<String>> mapChildren;

    // pass from MapBuilder
    private Map<String, OBOComponent> mapProperties;

    //map < id => paths >
    private HashMap<String, Vector<DefaultMutableTreeNode[]>> treePaths;
    
    private Vector<String> vRoots;
    private DefaultMutableTreeNode mothernode;

    private Boolean debug;

    
    public TreeBuilder(Boolean debug,
    		MapBuilder mapchildren){

    	this.debug = debug;
    	
        if (this.debug) {
        	
            System.out.println("===========");
            System.out.println("TreeBuilder - Constructor #1");
            System.out.println("===========");
            System.out.println(" instantiating TreeBuilder");
        }

        this.mapChildren = mapchildren.getChildren();
        this.mapProperties = mapchildren.getProperties();
        this.vRoots = mapchildren.getRootNodes();

        this.mothernode = new DefaultMutableTreeNode("root");
        this.treePaths = new HashMap<String, Vector<DefaultMutableTreeNode[]>>();

        //build tree starting from all defined roots
        for(int i=0; i< vRoots.size(); i++){
            DefaultMutableTreeNode rootnode = recursiveAddNode(vRoots.get(i), new DefaultMutableTreeNode[]{this.mothernode});
            this.mothernode.add(rootnode);     
        }
    }

    public TreeBuilder(Boolean debug, 
    		MapBuilder mapchildren, 
    		Vector<String> vParents){

    	this.debug = debug;
    	
        if (this.debug) {
        	
            System.out.println("===========");
            System.out.println("TreeBuilder - Constructor #2");
            System.out.println("===========");
            System.out.println(" instantiating TreeBuilder with selected parents");
        }

        this.mapChildren = mapchildren.getChildren();
        this.mapProperties = mapchildren.getProperties();
        this.vRoots = vParents;

        this.mothernode = new DefaultMutableTreeNode("root");
        this.treePaths = new HashMap<String, Vector<DefaultMutableTreeNode[]>>();

        //build tree starting from all defined roots
        for(int i=0; i< vRoots.size(); i++){
            DefaultMutableTreeNode rootnode =
                    recursiveAddNode(vRoots.get(i),
                    new DefaultMutableTreeNode[]{this.mothernode});
            this.mothernode.add(rootnode);
        }
    }

    public DefaultMutableTreeNode recursiveAddNode(String key,
            DefaultMutableTreeNode[] parent_path){

        if (this.debug) {
        	
            System.out.println("recursiveAddNode");
        }

    	//traverse from root component
        OBOComponent obocomponent = (OBOComponent) this.mapProperties.get(key);

        //check that component is not null otherwise create dummy component
        // for node
        //note: dummy component's sole purpose = a node for tree display;
        // not included in any component lists
        if (obocomponent == null) {
            obocomponent = new OBOComponent();
            obocomponent.setID(key);
            obocomponent.setName("Missing link");
            obocomponent.setFlagMissingRel(true);
            obocomponent.setCheckComment("Disallowed deletion of component from file - parent to existing components.");
            
            this.mapProperties.put(key, obocomponent);
        }

        //initialise new node for each component
        DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(obocomponent);
        
        //check for cycles
        if ( this.containsComponent(parent_path, obocomponent) ){
            obocomponent.setStatusRule("FAILED");
            obocomponent.setFlagMissingRel(true);
            TreePath printPath = new TreePath(parent_path);
            obocomponent.setCheckComment("There appears to be a cycle in your " +
                    "graph (" + obocomponent.getID() + " appears more than once " +
                    "in " + printPath + ")");
            return newnode;
        } 
        
        //get children for each component
        Vector<String> v = (Vector<String>) this.mapChildren.get(key);
        
        //BUILD PATHS
        //get paths for each component
        Vector<DefaultMutableTreeNode[]> paths = 
                (Vector<DefaultMutableTreeNode[]>) this.treePaths.get(key);
        if (paths == null) {
            paths = new Vector<DefaultMutableTreeNode[]>();
        }
        
        //create new path for each component, copy old path passed from last traversal
        DefaultMutableTreeNode[] new_path =
                new DefaultMutableTreeNode[parent_path.length+1];
        System.arraycopy(parent_path, 0, new_path, 0, parent_path.length);
        
        //add current component to new path; add new path to paths for current component
        new_path[parent_path.length] = newnode;
        paths.add(new_path);
        
        //entry in treePaths <component -> paths>
        treePaths.put(key, paths);
        //BUILD PATHS END

        //traverse down each branch of the tree to add all child nodes and create paths
        if (v!=null){
            for (int i=0; i<v.size(); i++){
                newnode.add(recursiveAddNode(v.get(i), new_path));
            }
        }
        return newnode;
    }

    @SuppressWarnings("unchecked")
	public Vector< DefaultMutableTreeNode > recursiveGetNodes(
            DefaultMutableTreeNode node,
            Vector<DefaultMutableTreeNode> nodes){
        
        if (this.debug) {
        	
            System.out.println("recursiveGetNodes");
        }

        Vector< DefaultMutableTreeNode > descendants = nodes;
        Vector< DefaultMutableTreeNode > children =
                new Vector<DefaultMutableTreeNode>();
        
        for (Enumeration<DefaultMutableTreeNode> eChildren = (Enumeration<DefaultMutableTreeNode>) node.children() ;
             eChildren.hasMoreElements() ;) {
            children.add( eChildren.nextElement() );
        }
        
        if ( children.isEmpty() ){
            return descendants;
        }
        else {
            for (int i=0; i< children.size(); i++){
                descendants.add( children.get(i) );
                descendants = recursiveGetNodes( children.get(i), descendants );
            }
            return descendants;
        }
    } 

    public DefaultMutableTreeNode recursiveWriteNode(String key,
            DefaultMutableTreeNode[] parent_path){
        
        if (this.debug) {
        	
            System.out.println("recursiveWriteNode");
        }

        //traverse from root component
        OBOComponent obocomponent = (OBOComponent) this.mapProperties.get(key);

        //check that component is not null otherwise create dummy component for node
        //note: dummy component's sole purpose = a node for tree display; not included in any component lists
        if ( obocomponent == null ) {
            obocomponent = new OBOComponent();
            obocomponent.setID(key);
            obocomponent.setName("Missing link");
            obocomponent.setFlagMissingRel(true);
            obocomponent.setCheckComment("Disallowed deletion of component from " +
                    "file - parent to existing components.");
            this.mapProperties.put(key, obocomponent);
        }

        //initialise new node for each component
        DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(obocomponent);

        //check for cycles
        if ( this.containsComponent(parent_path, obocomponent) ){
            obocomponent.setStatusRule("FAILED");
            obocomponent.setFlagMissingRel(true);
            TreePath printPath = new TreePath(parent_path);
            obocomponent.setCheckComment("There appears to be a cycle in your " +
                    "graph (" + obocomponent.getID() + " appears more than once " +
                    "in " + printPath + ")");
            return newnode;
        } 
        
        //get children for each component
        Vector<String> v = (Vector<String>) this.mapChildren.get(key);
        
        //BUILD PATHS
        //get paths for each component
        Vector<DefaultMutableTreeNode[]> paths =
                (Vector<DefaultMutableTreeNode[]>) this.treePaths.get(key);
        if ( paths == null ) {
            paths = new Vector<DefaultMutableTreeNode[]>();
        }
        
        //create new path for each component, copy old path passed from last traversal
        DefaultMutableTreeNode[] new_path = 
                new DefaultMutableTreeNode[parent_path.length + 1];
        System.arraycopy(parent_path, 0, new_path, 0, parent_path.length);
        
        //add current component to new path; add new path to paths for current component
        new_path[parent_path.length] = newnode;
        paths.add(new_path);
        
        //entry in treePaths <component -> paths>
        treePaths.put(key, paths);
        //BUILD PATHS END

        //traverse down each branch of the tree to add all child nodes and create paths
        if (v!=null){
            for (int i=0; i<v.size(); i++){
                newnode.add(recursiveWriteNode(v.get(i), new_path));
            }
        }
        return newnode;
    }
    
    public DefaultMutableTreeNode getRootNode(){

        if (this.debug) {
        	
            System.out.println("getRootNode");
        }

        return this.mothernode;
    }
        
    public Vector< DefaultMutableTreeNode > getNodes( String componentID ){

        if (this.debug) {
        	
            System.out.println("getNodes");
        }

        //method that returns nodes of a component
        
        Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();
        Vector<DefaultMutableTreeNode[]> paths = this.getPaths( componentID );

        if ( paths == null) {
            System.out.printf( "ID %s,  Nodes %s\n", componentID, nodes );
        }
        
        for(DefaultMutableTreeNode[] path: paths){
            //get last node in each path
            DefaultMutableTreeNode node = path[path.length - 1];
            nodes.add(node);
        } 
        return nodes;
    }

    public Vector<DefaultMutableTreeNode[]> getPaths(String id){

        if (this.debug) {
        	
            System.out.println("getPaths");
        }

        return this.treePaths.get(id);
    }

    public Vector<DefaultMutableTreeNode[]> getPathsTo(String id){
        
        if (this.debug) {
        	
            System.out.println("getPathsTo");
        }

        Vector< DefaultMutableTreeNode[] > paths = getPaths(id);

        for(int i = 0; i< paths.size(); i++){
            DefaultMutableTreeNode[] path = paths.get(i);

            if (path.length > 0) {
                DefaultMutableTreeNode[] pathTo =
                        new DefaultMutableTreeNode[path.length - 1];
                System.arraycopy(path, 0, pathTo, 0, path.length - 1);   
                paths.remove(i);
                paths.add(pathTo);
            }
        }
        return paths;
    }

    public Vector< String > getStrPaths(String id){
     
        if (this.debug) {
        	
            System.out.println("getStrPaths");
        }

    	Vector< DefaultMutableTreeNode[] > paths = getPaths(id);
        Vector< String > strPaths = new Vector< String >();

        for(int i = 0; i< paths.size(); i++){
            DefaultMutableTreeNode[] path = paths.get(i);
            System.out.print( path.toString() );
            strPaths.add( path.toString() );     
        }
        return strPaths;
    }

    public boolean isSamePathAs(DefaultMutableTreeNode[] path1, DefaultMutableTreeNode[] path2){
        
        if (this.debug) {
        	
            System.out.println("isSamePathAs");
        }

        boolean match = true;
        
        //if not equal length return false
        if ( path1.length!=path2.length ) {
            return false;
        }
        
        //iterate thru each node in the path
        for (DefaultMutableTreeNode node1: path1){
            
            for (DefaultMutableTreeNode node2: path2){
                if ( !node1.toString().equals(node2.toString()) ) {
                    return false;
                }
            }

        }
        return match;        
    }

    public boolean isPrimaryPath(DefaultMutableTreeNode[] pathTo){
        
        if (this.debug) {
        	
            System.out.println("isPrimaryPath");
        }

        boolean isPrimaryPath = true;
        
        for(DefaultMutableTreeNode node: pathTo){
        	
            Object nodeInfo = node.getUserObject(); 
            
            if (nodeInfo instanceof OBOComponent){
            	OBOComponent obocomponent = (OBOComponent) nodeInfo;
            
                if ( !obocomponent.getIsPrimary() ) {
                    return false;
                }
            }
        }
        
        return isPrimaryPath;
    }

    public boolean containsComponent(DefaultMutableTreeNode[] path,
            OBOComponent obocomponent){

        if (this.debug) {
        	
            System.out.println("containsComponent");
        }

        boolean contains = false;
        
        //iterate thru each node in the path
        for (DefaultMutableTreeNode currentNode: path){
            //check to see whether each node in the path contains a component
            Object nodeInfo = currentNode.getUserObject();
            if (nodeInfo instanceof OBOComponent){
                OBOComponent currentcomponent = (OBOComponent) nodeInfo;
                //if currentcomponent is a group node, the path is not a valid path back to the root 
                //if ( !currentcomponent.getIsPrimary() ) return false;
                //match current component to targeted component
                //if ( obocomponent.isSameAs(currentcomponent) ) return true;//MAZE:
                //REPLACE isSamsAs with matching id, two obocomponents can be the same but have modified properties
                if (obocomponent.getID().equals(currentcomponent.getID())) {
                    return true;
                }
            }
        }   
        //Vector< DefaultMutableTreeNode > vPath = new Vector( java.util.Arrays.asList( path ) );  
        return contains;
    }

    public boolean containsNode(DefaultMutableTreeNode[] path, DefaultMutableTreeNode node){

        if (this.debug) {
        	
            System.out.println("containsNode");
        }

        boolean contains = false;
        OBOComponent nodeCompie = new OBOComponent();
        
        Object nodeInfo = node.getUserObject();
        if (nodeInfo instanceof OBOComponent){
            nodeCompie = (OBOComponent) nodeInfo;
        }
        else {
            return contains;
        }
        
        //iterate thru each node in the path
        for (DefaultMutableTreeNode currentNode: path){
            //check to see whether each node in the path contains a component
            Object currentNodeInfo = currentNode.getUserObject();
            if (currentNodeInfo instanceof OBOComponent){
                OBOComponent currentcomponent = (OBOComponent) currentNodeInfo;
                
                //if ( nodeCompie.isSameAs(currentcomponent) ) return true;//MAZE:
                //REPLACE isSamsAs with matching id, two obocomponents can be the same but have modified properties
                if ( nodeCompie.getID().equals(currentcomponent.getID()) ) {
                    return true;
                }
            }
        }    
        return contains;
    }
    
    public boolean hasGroupNodeAsAncestor( DefaultMutableTreeNode[] pathTo, OBOComponent obocomponent ){

        if (this.debug) {
        	
            System.out.println("hasGroupNodeAsAncestor");
        }

    	boolean groupNodeAsAncestor = false;
    	
        //iterate thru each node in the path
        for ( DefaultMutableTreeNode currentNode: pathTo ){
            //check to see whether each node in the path contains a component
        	
            Object nodeInfo = currentNode.getUserObject();
            
            if ( nodeInfo instanceof OBOComponent ){
                OBOComponent currentcomponent = ( OBOComponent ) nodeInfo;
                //if currentcomponent is a group node
                //if ( !currentcomponent.getIsPrimary() && !currentcomponent.isComponentSameAs(obocomponent) ) {//MAZE:
                //REPLACE isSamsAs with matching id, two obocomponents can be the same but have modified properties
                //if ( !currentcomponent.getIsPrimary() && !currentcomponent.getID().equals(obocomponent.getID()) ){
                //if ( !currentcomponent.getIsGroup() && !currentcomponent.getID().equals(obocomponent.getID()) ){
                if ( currentcomponent.getIsGroup() && !currentcomponent.getID().equals(obocomponent.getID()) ){
                    
                	groupNodeAsAncestor = true;
                }
            }
        }
        return groupNodeAsAncestor;
    }

    public boolean isPathInNamespace( DefaultMutableTreeNode[] path, OBOComponent rootobocomponent ){
        
        if (this.debug) {
        	
            System.out.println("isPathInNamespace");
        }

        for ( DefaultMutableTreeNode node: path ){
        	
            Object nodeInfo = node.getUserObject();
            
            if ( nodeInfo instanceof OBOComponent ){
                OBOComponent currentcomponent = ( OBOComponent ) nodeInfo;
                //if currentcomponent is a group node
            
                if ( !currentcomponent.getNamespace().equals(rootobocomponent.getNamespace()) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map<String, Vector<DefaultMutableTreeNode[]>> getTreePaths(){

    	if (this.debug) {
        	
            System.out.println("getTreePaths");
        }

        return this.treePaths;
    }
    
    public OBOComponent getComponent(String id){

    	/*
        if (this.debug) {
        	
            System.out.println("getComponent");
        }
        */

    	try{
            OBOComponent obocomponent = (OBOComponent) this.mapProperties.get(id);
            return obocomponent;
        }
        catch(NullPointerException np){
            //np.printStackTrace();
            return null;
        }
    }

    public Vector<String> getTreeRoots(){

    	if (this.debug) {
        	
            System.out.println("getTreeRoots");
        }

        return this.vRoots;
    }

    public Vector<String> getChildrenBasedOnParent(String parent){
        
        if (this.debug) {
        	
            System.out.println("getChildrenBasedOnParent");
        }

        return (Vector<String>) this.mapChildren.get(parent);
    }
}
