/*
################################################################################
# Project:      Anatomy
#
# Title:        MapBuilder.java
#
# Date:         2008
#
# Author:       MeiSze Lam and Attila Gyenesi
#
# Copyright:    2009 Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; September 2010; Tidy up and Document
#
################################################################################
*/
package backend;

import java.util.*;


//puts components created by OBOParser into maps 
//components are mapped to parents
//components are mapped to its properties
//used by treebuilder to construct tree recursively
public class MapBuilder {

    private ArrayList<Component> components;

    //<EMAPA:ID -> component object>
    private HashMap<String, Component> treeProperty;

    //<EMAPA:ID -> children (emapa:id)>
    private HashMap<String, Vector<String>> treeChildren;

    //<EMAPA:ID -> time components (emap:id)>
    private HashMap<String, Vector<String>> treeTimeComponent;

    private HashMap<String, Vector<Component[]>> treePaths;
    private Vector<String> vRootNodes;

    
    public MapBuilder(ArrayList<Component> termList) {
        //System.out.println("instantiating MapBuilder");

        //instantiate
        this.components = new ArrayList();
        //this.rootcomponents = new ArrayList();
        
        this.treeProperty = new HashMap();
        this.treeChildren = new HashMap();
        this.treeTimeComponent = new HashMap();
        this.treePaths = new HashMap();
        this.vRootNodes = new Vector();

        //set 
        this.components.addAll(termList);
        //this.rootcomponents.addAll(this.components);

        //do
        //mapComponents();
        mapTreeProperty();
        mapChildrenProperty();
        mapTimeComponents();
        findRootNodes();
        
        //test
        //System.out.println("Root nodes defined as no part of/is a = " +
        // this.vRootNodes);
        //System.out.println("Root nodes defined as no entry in " +
        // "treeChildren = " + this.rootcomponents);
        //System.out.println("Looking for entry of EMAPA:16037 in " +
        // "treeChildren = " + this.treeChildren.get("EMAPA:16037"));
    }

    
    public void mapTreeProperty() {
        Component compie;
        
        for (Iterator i = components.iterator(); i.hasNext();) {

            compie = (Component) i.next();
            
            //look for duplicate ids not necessary -
            // OBOParser/OBO-Edit does not allow duplicate ids
            /*
            duplicateCompie = treeProperty.get( compie.getID() );
            if ( duplicateCompie!=null){
                System.out.println("found duplicate! " + 
                duplicateCompie.getID() + " matches current component " +
                compie.getID() );
                duplicateCompie.setStrRuleStatus("FAILED");
                duplicateCompie.setCheckComment("Duplicate ID: Component " +
                shares the same ID with the term '" + compie.getName() + "'" );
                compie.setID( compie.getID() + "copy");
            }*/
            //all components in component arraylist
            this.treeProperty.put(compie.getID(), compie);
        }
    }
    
    
    public void mapChildrenProperty() {
        Component compie;
        for (Iterator i = components.iterator(); i.hasNext();) {

            compie = (Component) i.next();

            //part of relationship
            for (Iterator j = compie.getPartOf().iterator(); j.hasNext();) {

                String parent = (String) j.next();
                
                
                //RULE CHECK: broken links
                //searching for parents that do not have a component entry
                // in the file
                //parent has been deleted, but children have not
                Component instance_of_comp =
                        (Component) this.treeProperty.get(parent);

                if ( instance_of_comp == null ) {
                    System.out.println("Parent has been deleted from file: " +
                            parent);
                    //set flagMissingRel to true to display component in red +
                    // add comment
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Broken Link: Phantom parent " +
                            parent + " deleted from OBO file.");
                    //add to root nodes so that it can be displayed in the tree 
                    //treebuilder builds branches recursively from a list of
                    // rootnodes
                    this.vRootNodes.add(parent);
                    
                    //Question: Display under namespace OR 
                        //change parent to namespace root so that it is
                        // displayed under the correct namespace
                        //abtract_anatomy => hard coded at the moment;
                        // preferably linked from gui
                    //Display under a dummy component called 'missing parent'
                        //this code takes place in TreeBuilder
                }
                                 

                Vector<String> v = this.treeChildren.get(parent);
                if (v == null) {
                    v = new Vector();
                }
                v.add(compie.getID());
                this.treeChildren.put(parent, v);

            }
            
             //group part of relationship
            for (Iterator k = compie.getGroupPartOf().iterator();
                 k.hasNext();) {

                String parent = (String) k.next();
                
                //RULE CHECK: broken links
                //searching for parents that do not have a component entry
                // in the file
                //parent has been deleted, but children have not
                Component instance_of_comp =
                        (Component) this.treeProperty.get(parent);
                
                if ( instance_of_comp == null ) {
                    System.out.println("Parent has been deleted from file: " +
                            parent);
                    //set flagMissingRel to true to display component in red +
                    // add comment
                    compie.setFlagMissingRel(true);
                    compie.setCheckComment("Broken Link: Phantom parent " +
                            parent + " deleted from OBO file.");
                    //add to root nodes so that it can be displayed in the tree 
                    //treebuilder builds branches recursively from a list of
                    // rootnodes
                    this.vRootNodes.add(parent);
                    
                    //Question: Display under namespace OR 
                        //change parent to namespace root so that it is
                        // displayed under the correct namespace
                        //abtract_anatomy => hard coded at the moment;
                        // preferably linked from gui
                    //Display under a dummy component called 'missing parent'
                        //this code takes place in TreeBuilder
                }

                Vector<String> v = this.treeChildren.get(parent);
                if (v == null) {
                    v = new Vector();
                }
                v.add(compie.getID());
                this.treeChildren.put(parent, v);
            }

            //is a relationship
            if ( !compie.getIsA().equals("") ) {
                Vector<String> v = this.treeChildren.get(compie.getIsA());
                if (v == null) {
                    v = new Vector();
                }
                v.add(compie.getID());
                this.treeChildren.put(compie.getIsA(), v);
                
                //remove from rootcomponent
                //this.rootcomponents.remove(compie);
            }
        }
    }

    public void mapTimeComponents(){
        Component compie;
        for (Iterator i = components.iterator(); i.hasNext();) {

            compie = (Component) i.next();
            //get time component
            for (Iterator j = compie.getHasTimeComponent().iterator();
                 j.hasNext();) {

                String timeCompie = (String) j.next();

                //RULE CHECK: broken links
                //searching for parents that do not have a component entry in
                // the file
                //parent has been deleted, but children have not
                Component instance_of_comp =
                        (Component) this.treeProperty.get(timeCompie);

                if ( instance_of_comp == null ) {
                    compie.setCheckComment("Broken Link: Phantom time " + 
                            "component " + timeCompie +
                            " deleted from OBO file.");
                }


                Vector<String> v = this.treeTimeComponent.get( compie.getID() );
                if (v == null) {
                    v = new Vector();
                }
                v.add( timeCompie );
                this.treeTimeComponent.put(compie.getID(), v);

            }
        }
    }
    
    public void findRootNodes() {

        boolean notPartOf;
        boolean notIsA;
        boolean notGroupPartOf;

        for(Component compie: this.components){    
            //if partOf is empty or itself
            notPartOf = ( compie.getPartOf().isEmpty() || 
                    ( compie.getPartOf().size() == 1 &&
                    compie.getPartOf().contains( compie.getID() ) ) );

            //if isA is empty or isA is itself
            notIsA = ( compie.getIsA().equals("") ||
                    compie.getIsA().equals( compie.getID() ) );

            //if groupPartOf is empty or itself
            notGroupPartOf = ( compie.getGroupPartOf().isEmpty() || 
                    ( compie.getGroupPartOf().size() == 1 &&
                    compie.getGroupPartOf().contains( compie.getID() ) ) );

            //check if components have any PartOf or IsA parents
            if ( notPartOf && 
                 notIsA &&
                 notGroupPartOf ) {
                this.vRootNodes.add( compie.getID() );
            }
        }
        //System.out.println("The roots of this list are " + this.vRootNodes);
    }
    
    public void addTreePropertyEntry(String strEMAPA, Component compie){
        this.treeProperty.put(strEMAPA, compie);
    }

    public Map getChildren() {
        return this.treeChildren;
    }

    public Map getProperties() {
        return this.treeProperty;
    }

    public Map getTimeComponents(){
        return this.treeTimeComponent;
    }

    public Vector getRootNodes() {
        return this.vRootNodes;
    }
}
