/*
*---------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
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
* Mike Wicks; November 2012; Yet More rejigging
*
*---------------------------------------------------------------------------------------------
*/
package routines.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;

import obomodel.OBOComponent;

import utility.Wrapper;

public class MapBuilder {

    private ArrayList<OBOComponent> obocomponents;

    //<EMAPA:ID -> component object>
    private HashMap<String, OBOComponent> treeProperty;
    
    //<EMAPA:ID -> children (emapa:id)>
    private HashMap<String, Vector<String>> treeChildren;
    
    //<EMAPA:ID -> time obocomponents (emap:id)>
    //private HashMap<String, Vector<String>> treeTimeComponent;
    private Vector<String> vRootNodes;

    private String requestMsgLevel;
    
    public MapBuilder(
    		String requestMsgLevel, 
    		ArrayList<OBOComponent> termList) throws Exception {

		this.requestMsgLevel = requestMsgLevel;
		
        Wrapper.printMessage("mapbuilder.constructor", "*****", this.requestMsgLevel);

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
    
    public void mapTreeProperty() throws Exception {
    	
        Wrapper.printMessage("mapbuilder.mapTreeProperty", "*****", this.requestMsgLevel);
    	
        OBOComponent obocomponent;
        
        for (Iterator<OBOComponent> i = obocomponents.iterator(); i.hasNext();) {

            obocomponent = i.next();
            
            //all obocomponents in component arraylist
            this.treeProperty.put(obocomponent.getID(), obocomponent);
        }
    }
    
    public void mapChildrenProperty() throws Exception {
    	
        Wrapper.printMessage("mapbuilder.mapChildrenProperty", "*****", this.requestMsgLevel);

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

                    Wrapper.printMessage("mapbuilder.mapChildrenProperty:Parent has been deleted from file: " +
                            parent + "!", "*", this.requestMsgLevel);
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
                    
                    Wrapper.printMessage("mapbuilder.mapChildrenProperty:obocomponent.toString() = " + obocomponent.toString() + "!", "*", this.requestMsgLevel);
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

    public void findRootNodes() throws Exception {

        Wrapper.printMessage("mapbuilder.findRootNodes", "*****", this.requestMsgLevel);
        	
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

    public void addTreePropertyEntry(String strEMAPA, OBOComponent obocomponent) throws Exception{

        Wrapper.printMessage("mapbuilder.addTreePropertyEntry", "*****", this.requestMsgLevel);
        
        this.treeProperty.put(strEMAPA, obocomponent);
    }

    public Map<String, Vector<String>> getChildren() throws Exception {
        
        Wrapper.printMessage("mapbuilder.getChildren", "*****", this.requestMsgLevel);
        
        return this.treeChildren;
    }

    public Map<String, OBOComponent> getProperties() throws Exception {
        
        Wrapper.printMessage("mapbuilder.getProperties", "*****", this.requestMsgLevel);

        return this.treeProperty;
    }

    public Vector<String> getRootNodes() throws Exception {
        
        Wrapper.printMessage("mapbuilder.getRootNodes", "*****", this.requestMsgLevel);
        
        return this.vRootNodes;
    }
}

