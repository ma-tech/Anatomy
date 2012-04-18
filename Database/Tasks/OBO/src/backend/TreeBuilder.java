package backend;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class TreeBuilder {
    
    private Map mapChildren; // pass from MapBuilder
    private Map mapProperties; // pass from MapBuilder
    private HashMap<String, Vector<DefaultMutableTreeNode[]>> treePaths; //map < id => paths >
    
    private Vector<String> vRoots;
    
    private DefaultMutableTreeNode mothernode;
    
    private BufferedWriter printNodeText;
    private StringWriter stringWriter = new StringWriter();
    private PrintWriter printNode = new PrintWriter( stringWriter ) ; 
    
    
    public TreeBuilder(MapBuilder mappie){
        System.out.println("instantiating TreeBuilder");
        this.mapChildren = mappie.getChildren();
        this.mapProperties = mappie.getProperties();
        this.vRoots = mappie.getRootNodes();

        this.mothernode = new DefaultMutableTreeNode("root");
        this.treePaths = new HashMap<String, Vector<DefaultMutableTreeNode[]>>();

        
        //build tree starting from all defined roots
        for(int i=0; i< vRoots.size(); i++){
            //Component compie = (Component) this.mapProperties.get(vRoots.get(i));
            DefaultMutableTreeNode rootnode = recursiveAddNode(vRoots.get(i), new DefaultMutableTreeNode[]{this.mothernode});
            this.mothernode.add(rootnode);     
        }
    }

    public TreeBuilder(MapBuilder mappie, Vector vParents){
        System.out.println("instantiating TreeBuilder with selected parents");
        this.mapChildren = mappie.getChildren();
        this.mapProperties = mappie.getProperties();
        this.vRoots = vParents;

        this.mothernode = new DefaultMutableTreeNode("root");
        this.treePaths = new HashMap<String, Vector<DefaultMutableTreeNode[]>>();


        //build tree starting from all defined roots
        for(int i=0; i< vRoots.size(); i++){
            //Component compie = (Component) this.mapProperties.get(vRoots.get(i));
            DefaultMutableTreeNode rootnode = recursiveAddNode(vRoots.get(i), new DefaultMutableTreeNode[]{this.mothernode});
            this.mothernode.add(rootnode);
        }
    }
   
    public DefaultMutableTreeNode recursiveAddNode(String key, DefaultMutableTreeNode[] parent_path){
        
        //traverse from root component

        Component compie = (Component) this.mapProperties.get(key);
        //check that component is not null otherwise create dummy component for node
        //note: dummy component's sole purpose = a node for tree display; not included in any component lists
        if(compie==null) {
            compie = new Component();
            compie.setID(key);
            compie.setName("Missing link");
            compie.setFlagMissingRel(true);
            compie.setCheckComment("Disallowed deletion of component from file - parent to existing components.");
            this.mapProperties.put(key, compie);
        }

        //initialise new node for each component
        DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(compie);
        
        //check for cycles
        if ( this.containsComponent(parent_path, compie) ){
            compie.setStrRuleStatus("FAILED");
            compie.setFlagMissingRel(true);
            TreePath printPath = new TreePath(parent_path);
            compie.setCheckComment("There appears to be a cycle in your graph (" + compie.getID() + " appears more than once in " + printPath + ")");
            return newnode;
        } 
        
        //get children for each component
        Vector<String> v = (Vector<String>) this.mapChildren.get(key);
        
        //BUILD PATHS
        //get paths for each component
        Vector<DefaultMutableTreeNode[]> paths = (Vector<DefaultMutableTreeNode[]>) this.treePaths.get(key);
        if(paths == null) {
            paths = new Vector<DefaultMutableTreeNode[]>();
        }
        
        //create new path for each component, copy old path passed from last traversal
        DefaultMutableTreeNode[] new_path = new DefaultMutableTreeNode[parent_path.length+1];
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
    
    public Vector< DefaultMutableTreeNode > recursiveGetNodes(DefaultMutableTreeNode node, Vector<DefaultMutableTreeNode> nodes){
        
        Vector< DefaultMutableTreeNode > descendants = nodes;
        Vector< DefaultMutableTreeNode > children = new Vector<DefaultMutableTreeNode>();
        
        //Enumeration eChildren = node.children();
        for (Enumeration<DefaultMutableTreeNode> eChildren = node.children() ; eChildren.hasMoreElements() ;) {
            children.add( eChildren.nextElement() );
        }
        
        if ( children.isEmpty() ){
            //descendants.add(node);
            //System.out.println(node);
            //System.out.println("test recursiveGetNodes: is empty!");
            return descendants;
        }
        else{
            for (int i=0; i< children.size(); i++){
                descendants.add( children.get(i) );
                descendants = recursiveGetNodes( children.get(i), descendants );
            }
            return descendants;
        }
    } 
    
    public void writeNodeReport( String strFileName, String mothernodeID ){
        try{
            printNodeText = new BufferedWriter( new FileWriter( strFileName ) );
            DefaultMutableTreeNode dummy = recursiveWriteNode( mothernodeID, new DefaultMutableTreeNode[]{this.mothernode});
            printNodeText.write( stringWriter.toString() );
            printNodeText.close();
        }
        catch(IOException io){
            io.printStackTrace();
        }
    }
    
    public DefaultMutableTreeNode recursiveWriteNode(String key, DefaultMutableTreeNode[] parent_path){
        
        //traverse from root component

        Component compie = (Component) this.mapProperties.get(key);
        //check that component is not null otherwise create dummy component for node
        //note: dummy component's sole purpose = a node for tree display; not included in any component lists
        if(compie==null) {
            compie = new Component();
            compie.setID(key);
            compie.setName("Missing link");
            compie.setFlagMissingRel(true);
            compie.setCheckComment("Disallowed deletion of component from file - parent to existing components.");
            this.mapProperties.put(key, compie);
        }

        //initialise new node for each component
        DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(compie);
        if ( compie.getIntStartsAt()>=1 && compie.getIntEndsAt()<=20 && compie.getIsPrimary() ){ 
            for ( int i=0; i<parent_path.length; i++ ){
                printNode.print("  ");
            }
            printNode.println( "-" + compie.getName() );
        }
        
        
        //check for cycles
        if ( this.containsComponent(parent_path, compie) ){
            compie.setStrRuleStatus("FAILED");
            compie.setFlagMissingRel(true);
            TreePath printPath = new TreePath(parent_path);
            compie.setCheckComment("There appears to be a cycle in your graph (" + compie.getID() + " appears more than once in " + printPath + ")");
            return newnode;
        } 
        
        //get children for each component
        Vector<String> v = (Vector<String>) this.mapChildren.get(key);
        
        //BUILD PATHS
        //get paths for each component
        Vector<DefaultMutableTreeNode[]> paths = (Vector<DefaultMutableTreeNode[]>) this.treePaths.get(key);
        if(paths == null) {
            paths = new Vector<DefaultMutableTreeNode[]>();
        }
        
        //create new path for each component, copy old path passed from last traversal
        DefaultMutableTreeNode[] new_path = new DefaultMutableTreeNode[parent_path.length+1];
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
        return this.mothernode;
    }
    
    public Vector< DefaultMutableTreeNode > getNodes( String componentID ){
        //method that returns nodes of a component
        
        Vector< DefaultMutableTreeNode > nodes = new Vector< DefaultMutableTreeNode >();
        Vector< DefaultMutableTreeNode[] > paths = this.getPaths( componentID );
        if (paths==null) System.out.printf( "ID %s,  Nodes %s\n", componentID, nodes );
        
        for(DefaultMutableTreeNode[] path: paths){
            //get last node in each path
            DefaultMutableTreeNode node = path[path.length - 1];
            nodes.add(node);
        } 
        return nodes;
    }

    public Vector< DefaultMutableTreeNode[] > getPaths(String id){
        return this.treePaths.get(id);
    }

    public Vector< DefaultMutableTreeNode[] > getPathsTo(String id){
        
        Vector< DefaultMutableTreeNode[] > paths = getPaths(id);
        //Vector< DefaultMutableTreeNode[] > pathsTo = new Vector< DefaultMutableTreeNode[] >();
        for(int i = 0; i< paths.size(); i++){
            DefaultMutableTreeNode[] path = paths.get(i);
            if(path.length > 0) {
                DefaultMutableTreeNode[] pathTo = new DefaultMutableTreeNode[path.length - 1];
                System.arraycopy(path, 0, pathTo, 0, path.length - 1);   
                paths.remove(i);
                paths.add(pathTo);
            }
        }
        
        return paths;
    }
    
    public Vector< String > getStrPaths(String id){
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
        
        boolean match = true;
        
        //if not equal length return false
        if ( path1.length!=path2.length ) return false;
        
        //iterate thru each node in the path
        for (DefaultMutableTreeNode node1: path1){
            
            for (DefaultMutableTreeNode node2: path2){
                if ( !node1.toString().equals(node2.toString()) ) return false;
            }

        }
        return match;        
    }
    
    public boolean isPrimaryPath(DefaultMutableTreeNode[] pathTo){
        
        boolean isPrimaryPath = true;
        
        for(DefaultMutableTreeNode node: pathTo){
            Object nodeInfo = node.getUserObject(); 
            if(nodeInfo instanceof Component){
                Component compie = (Component) nodeInfo;
                if ( !compie.getIsPrimary() ) return false;
            }
        }
        
        return isPrimaryPath;
    }
   
    public boolean containsComponent(DefaultMutableTreeNode[] path, Component compie){
        
        boolean contains = false;
        
        //iterate thru each node in the path
        for (DefaultMutableTreeNode currentNode: path){
            //check to see whether each node in the path contains a component
            Object nodeInfo = currentNode.getUserObject();
            if (nodeInfo instanceof Component){
                Component currentCompie = (Component) nodeInfo;
                //if currentCompie is a group node, the path is not a valid path back to the root 
                //if ( !currentCompie.getIsPrimary() ) return false;
                //match current component to targeted component
                if ( compie.isSameAs(currentCompie) ) return true;
            }
        }   
        //Vector< DefaultMutableTreeNode > vPath = new Vector( java.util.Arrays.asList( path ) );  
        return contains;
    }
    
    public boolean containsNode(DefaultMutableTreeNode[] path, DefaultMutableTreeNode node){
        
        boolean contains = false;
        Component nodeCompie = new Component();
        
        Object nodeInfo = node.getUserObject();
        if (nodeInfo instanceof Component){
            nodeCompie = (Component) nodeInfo;
        }
        else return contains;
        
        //iterate thru each node in the path
        for (DefaultMutableTreeNode currentNode: path){
            //check to see whether each node in the path contains a component
            Object currentNodeInfo = currentNode.getUserObject();
            if (currentNodeInfo instanceof Component){
                Component currentCompie = (Component) currentNodeInfo;
                
                if ( nodeCompie.isSameAs(currentCompie) ) return true;
            }
        }    
        return contains;
    }
    
    
    public boolean hasGroupNodeAsAncestor( DefaultMutableTreeNode[] pathTo, Component compie ){
        
        //iterate thru each node in the path
        for ( DefaultMutableTreeNode currentNode: pathTo ){
            //check to see whether each node in the path contains a component
            Object nodeInfo = currentNode.getUserObject();
            if ( nodeInfo instanceof Component ){
                Component currentCompie = ( Component ) nodeInfo;
                //if currentCompie is a group node
                if ( !currentCompie.getIsPrimary() && !currentCompie.isSameAs(compie) ) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isPathInNamespace( DefaultMutableTreeNode[] path, Component rootCompie ){
        
        for ( DefaultMutableTreeNode node: path ){
            Object nodeInfo = node.getUserObject();
            if ( nodeInfo instanceof Component ){
                Component currentCompie = ( Component ) nodeInfo;
                //if currentCompie is a group node
                if ( !currentCompie.getNamespace().equals(rootCompie.getNamespace()) ) return false;
            }
        }
        return true;
    }
    
    
    public Map getTreePaths(){
        return this.treePaths;
    }
    
    
    public Component getComponent(String id){
        Component compie = (Component) this.mapProperties.get(id);
        return compie;
    }
    
    
    public Vector<String> getTreeRoots(){
        return this.vRoots;
    }

    
}
