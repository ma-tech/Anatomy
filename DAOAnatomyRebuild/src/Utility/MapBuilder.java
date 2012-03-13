/*
*---------------------------------------------------------------------------------------------
* Project:      Anatomy
*
* Title:        MapBuilder.java
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
* Description:  This Class extracts a list of components from the database, and builds a list 
*                in the OBO style.
*                
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; March 2012; More rejigging
*
*---------------------------------------------------------------------------------------------
*/

package Utility;

import java.util.*;

import OBOModel.ComponentFile;

public class MapBuilder {

    private ArrayList<ComponentFile> obocomponents;

    //<EMAPA:ID -> component object>
    private HashMap<String, ComponentFile> treeProperty;
    
    //<EMAPA:ID -> children (emapa:id)>
    private HashMap<String, Vector<String>> treeChildren;
    
    //<EMAPA:ID -> time obocomponents (emap:id)>
    //private HashMap<String, Vector<String>> treeTimeComponent;

    private HashMap<String, Vector<ComponentFile[]>> treePaths;
    private Vector<String> vRootNodes;

    
    public MapBuilder(ArrayList<ComponentFile> termList) {
        
    	//System.out.println("instantiating MapBuilder");

        //instantiate
        this.obocomponents = new ArrayList();
        //this.rootobocomponents = new ArrayList();
        
        this.treeProperty = new HashMap();
        this.treeChildren = new HashMap();
        //this.treeTimeComponent = new HashMap();
        this.treePaths = new HashMap();
        this.vRootNodes = new Vector();

        this.obocomponents.addAll(termList);
        //this.rootobocomponents.addAll(this.obocomponents);

        //mapComponents();
        mapTreeProperty();
        mapChildrenProperty();
        //mapTimeComponents();
        findRootNodes();
        
    }

    
    public void mapTreeProperty() {
    	
        ComponentFile obocomponent;
        
        for (Iterator i = obocomponents.iterator(); i.hasNext();) {

            obocomponent = (ComponentFile) i.next();
            
            //all obocomponents in component arraylist
            this.treeProperty.put(obocomponent.getID(), obocomponent);
        }
    }
    
    
    public void mapChildrenProperty() {
    	
        ComponentFile obocomponent;
        
        for (Iterator i = obocomponents.iterator(); i.hasNext();) {

            obocomponent = (ComponentFile) i.next();

            //part of relationship
            for (Iterator j = obocomponent.getChildOfs().iterator(); j.hasNext();) {

                String parent = (String) j.next();

                /*
                 RULE CHECK: broken links
                 searching for parents that do not have a component entry
                  in the file
                 parent has been deleted, but children have not
                */
                ComponentFile instance_of_comp = (ComponentFile) this.treeProperty.get(parent);

                if ( instance_of_comp == null ) {
                    System.out.println("Parent has been deleted from file: " +
                            parent);
                    /*
                     set flagMissingRel to true to display component in red +
                      add comment
                    */
                    obocomponent.setFlagMissingRel(true);
                    obocomponent.setCheckComment("Broken Link: Phantom parent " +
                            parent + " deleted from OBO file.");
                    /*
                     Add to root nodes so that it can be displayed in the tree
                      treebuilder builds branches recursively from a list of
                      rootnodes
                    */
                    this.vRootNodes.add(parent);

                    /*
                     Question: Display under namespace OR
                      change parent to namespace root so that it is
                       displayed under the correct namespace
                       abtract_anatomy => hard coded at the moment;
                        preferably linked from gui
                     Display under a dummy component called 'missing parent'
                      this code takes place in TreeBuilder
                    */
                }

                Vector<String> v = this.treeChildren.get(parent);
                if (v == null) {
                    v = new Vector();
                }

                v.add(obocomponent.getID());
                this.treeChildren.put(parent, v);

            }
        }
    }


    public void findRootNodes() {

        boolean notChildOf;

        for(ComponentFile obocomponent: this.obocomponents){    
            //if childOf is empty or itself
            notChildOf = ( obocomponent.getChildOfs().isEmpty() ||
                        ( obocomponent.getChildOfs().size() == 1 &&
                          obocomponent.getChildOfs().contains( obocomponent.getID() ) ) );

            if ( notChildOf ) {
                this.vRootNodes.add( obocomponent.getID() );
            }
        }

    }


    public void addTreePropertyEntry(String strEMAPA, ComponentFile obocomponent){
        this.treeProperty.put(strEMAPA, obocomponent);
    }


    public Map getChildren() {
        return this.treeChildren;
    }


    public Map getProperties() {
        return this.treeProperty;
    }


    /*
    public Map getTimeComponents(){
        return this.treeTimeComponent;
    }
    */


    public Vector getRootNodes() {
        return this.vRootNodes;
    }

}

