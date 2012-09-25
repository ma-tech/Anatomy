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

package routinesbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;

import obomodel.OBOComponent;

public class MapBuilder {

    private ArrayList<OBOComponent> obocomponents;

    //<EMAPA:ID -> component object>
    private HashMap<String, OBOComponent> treeProperty;
    
    //<EMAPA:ID -> children (emapa:id)>
    private HashMap<String, Vector<String>> treeChildren;
    
    //<EMAPA:ID -> time obocomponents (emap:id)>
    //private HashMap<String, Vector<String>> treeTimeComponent;

    private Vector<String> vRootNodes;

	// A flag to print comments to System.out
	boolean debug = false;

    
    public MapBuilder(Boolean debug, 
    		ArrayList<OBOComponent> termList) {
    	//System.out.println("instantiating MapBuilder");

        this.debug = debug;
        
        if (this.debug) {
        	
            System.out.println("==========");
            System.out.println("MapBuilder - Constructor");
            System.out.println("==========");
        }

        //instantiate
        this.obocomponents = new ArrayList<OBOComponent>();
        this.treeProperty = new HashMap<String, OBOComponent>();
        this.treeChildren = new HashMap<String, Vector<String>>();
        this.vRootNodes = new Vector<String>();
        
        this.obocomponents.addAll(termList);

        mapTreeProperty();
        mapChildrenProperty();

        findRootNodes();
    }
    
    public void mapTreeProperty() {
    	
        if (this.debug) {
        	
            System.out.println("mapTreeProperty");
        }

        OBOComponent obocomponent;
        
        for (Iterator<OBOComponent> i = obocomponents.iterator(); i.hasNext();) {

            obocomponent = i.next();
            
            //all obocomponents in component arraylist
            this.treeProperty.put(obocomponent.getID(), obocomponent);
        }
    }
    
    public void mapChildrenProperty() {
    	
        if (this.debug) {
        	
            System.out.println("mapChildrenProperty");
        }

        OBOComponent obocomponent;
        
        for (Iterator<OBOComponent> i = obocomponents.iterator(); i.hasNext();) {

            obocomponent = i.next();

            //part of relationship
            for (Iterator<String> j = obocomponent.getChildOfs().iterator(); j.hasNext();) {

                String parent = j.next();

                /*
                 RULE CHECK: broken links
                 searching for parents that do not have a component entry
                  in the file
                 parent has been deleted, but children have not
                */
                OBOComponent instance_of_comp = (OBOComponent) this.treeProperty.get(parent);

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
                    
               		System.out.println("obocomponent.toString() = " + obocomponent.toString());

                }

                Vector<String> v = this.treeChildren.get(parent);
                if (v == null) {
                    v = new Vector<String>();
                }

                v.add(obocomponent.getID());
                this.treeChildren.put(parent, v);

            }
        }
    }

    public void findRootNodes() {

        if (this.debug) {
        	
            System.out.println("findRootNodes");
        }

        boolean notChildOf;

        for(OBOComponent obocomponent: this.obocomponents){    
            //if childOf is empty or itself
            notChildOf = ( obocomponent.getChildOfs().isEmpty() ||
                        ( obocomponent.getChildOfs().size() == 1 &&
                          obocomponent.getChildOfs().contains( obocomponent.getID() ) ) );

            if ( notChildOf ) {
                this.vRootNodes.add( obocomponent.getID() );
            }
        }

    }

    public void addTreePropertyEntry(String strEMAPA, OBOComponent obocomponent){

        if (this.debug) {
        	
            System.out.println("addTreePropertyEntry");
        }


        this.treeProperty.put(strEMAPA, obocomponent);
    }

    public Map<String, Vector<String>> getChildren() {
        
        if (this.debug) {
        	
            System.out.println("getChildren");
        }


        return this.treeChildren;
    }

    public Map<String, OBOComponent> getProperties() {
        
        if (this.debug) {
        	
            System.out.println("getProperties");
        }


        return this.treeProperty;
    }

    public Vector<String> getRootNodes() {
        
        if (this.debug) {
        	
            System.out.println("getRootNodes");
        }


        return this.vRootNodes;
    }
}

