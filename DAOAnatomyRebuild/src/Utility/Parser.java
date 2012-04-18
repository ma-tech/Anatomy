/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        Parser.java
#
# Date:         2012
#
# Author:       Mike Wicks
#
# Copyright:    2012
#               Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Description:  Parse an OBO File, and return an ArrayList of OBO Components
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Set;

import org.geneontology.oboedit.dataadapter.DefaultOBOParser;
import org.geneontology.oboedit.dataadapter.OBOParseEngine;
import org.geneontology.oboedit.dataadapter.OBOParseException;
import org.geneontology.oboedit.datamodel.OBOSession;
import org.geneontology.oboedit.datamodel.impl.OBOClassImpl;
import org.geneontology.oboedit.datamodel.impl.OBORestrictionImpl;

import OBOModel.ComponentFile;


public class Parser {

    private String strFile;

    //instantiated components
    private ArrayList<ComponentFile> componentList;


    public Parser(String txtFileName){

        this.strFile = txtFileName.trim();
        this.componentList = addComponents(this.strFile);

    }

    // Getters ------------------------------------------------------------------------------------
    public String getFile(){
        return this.strFile;
    }
    public ArrayList getComponents(){
        return this.componentList;
    }
    
    // Methods ------------------------------------------------------------------------------------
    public static OBOSession getSession(String path) throws IOException {

        DefaultOBOParser defaultoboparser = new DefaultOBOParser();
	    OBOParseEngine obooparseengine = new OBOParseEngine(defaultoboparser);

        /*
         * GOBOParseEngine can parse several files at once
	     *  and create one munged-together ontology,
	     *  so we need to provide a Collection to the setPaths() method
         */
	    
        try {
    	    Collection paths = new LinkedList();
    	    paths.add(path);
    	    obooparseengine.setPaths(paths);
        	obooparseengine.parse();
        }
        catch(OBOParseException e){
            e.printStackTrace();
        }
        catch(java.io.FileNotFoundException io){
            io.printStackTrace();
        }

        OBOSession obosession = defaultoboparser.getSession();

        return obosession;
    }
    
    
    private ArrayList<ComponentFile> addComponents(String strFile){
        
    	ArrayList<ComponentFile> componentList = new ArrayList<ComponentFile>();
    			
        try {
            OBOSession obosession = getSession(strFile);

            //all terms in a map
            Map map = obosession.getAllTermsHash();
            
            /*
            System.out.println("Roots: " + obosession.getRoots());
            System.out.println("Relations: " + obosession.getRelationshipTypes());
            System.out.println("No of terms in map: " + map.size());
            System.out.println("");

            System.out.println("obosession.getCurrentHistory() : " + obosession.getCurrentHistory());
            System.out.println("obosession.getDefaultNamespace() : " + obosession.getDefaultNamespace());
            */

            //iterate through each term in the map
            for(Iterator i = map.keySet().iterator(); i.hasNext(); ){

                OBOClassImpl oboclassimpl = (OBOClassImpl) map.get(i.next());
                
            	//System.out.println("ID = " + oboclassimpl.getID());
            	
                //remove 4 default extra terms in map created by obosession.getAllTermsHash()
                if(!oboclassimpl.getID().startsWith("obo")){

            		ArrayList<String> childOfs = new ArrayList<String>();
            		ArrayList<String> childOfTypes = new ArrayList<String>();
            		ArrayList<String> synonyms = new ArrayList<String>();
            		ArrayList<String> userComments = new ArrayList<String>();
            		TreeSet comments = new TreeSet();

            		ComponentFile obocomponent = new ComponentFile(oboclassimpl.toString(), 
                    		oboclassimpl.getID(), 
                    		"TBD",
                    		"TBD",
                    		oboclassimpl.getNamespace().toString(),
                    		false,
                    		"TBD",
                    		"TBD",
                    		0,
                    		"UNCHANGED",
                    		"UNCHECKED",
                     		childOfs,
                    		childOfTypes,
                    		synonyms,
                    		userComments,
                    		"",
                    		comments);
                    
                    //get comments
            		/*
            		obocomponent.setCheckComment( oboclassimpl.getComment() );
            		obocomponent.addUserComments( oboclassimpl.getComment() );
            		*/
                	
            		/*
            		if ( oboclassimpl.getComment().equals("order=1 for EMAPA:29316")) {
                    	System.out.println("ID = " + oboclassimpl.getID());

                		System.out.println("oboclassimpl.getComment() = " + oboclassimpl.getComment());
            		}
            		*/

                    /*
                     * set obocomponent synonyms (arraylist)
                     */
                    for(Iterator k = oboclassimpl.getSynonyms().iterator(); k.hasNext(); ){
                    	obocomponent.addSynonym(k.next().toString());
                    }

                    /*
                     * set obocomponent relationships
                     *  parents(arraylist), startsat(string TSxx), endsat(TSxx)
                     *  note: j is one relationship,
                     *    child = node, parent = relationship postfix
                     */
                    for(Iterator j = oboclassimpl.getParents().iterator(); j.hasNext();  ){
                        OBORestrictionImpl oborestrictionimpl = (OBORestrictionImpl) j.next();

                        if (oborestrictionimpl.getType().getID().equals("part_of")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("PART_OF");
                        }

                        else if (oborestrictionimpl.getType().getID().equals("group_part_of")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("GROUP_PART_OF");
                        	//obocomponent.setIsPrimary(false);
                        }

                        else if (oborestrictionimpl.getType().getID().equals("OBO_REL:is_a")){
                        	if ( oborestrictionimpl.getParent().getID().equals("group_term") ) {
                        		//System.out.println("HELLO!  I'm adding group_term as a relationship = NOOOOOO!");
                        		obocomponent.setGroup(true);
                            	//obocomponent.setIsPrimary(false);
                        	}
                        	else {
                        		obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                            	obocomponent.addChildOfType("IS_A");
                            	//obocomponent.setIsPrimary(false);
                        	}
                        }

                        else if (oborestrictionimpl.getType().getID().equals("starts_at")){
                        	obocomponent.setStart(oborestrictionimpl.getParent().getID());
                        }

                        else if (oborestrictionimpl.getType().getID().equals("ends_at")){
                        	obocomponent.setEnd(oborestrictionimpl.getParent().getID());
                        }

                        else if (oborestrictionimpl.getType().getID().equals("develops_from")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("DEVELOPS_FROM");
                        }

                        else if (oborestrictionimpl.getType().getID().equals("located_in")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("LOCATED_IN");
                        }

                        else if (oborestrictionimpl.getType().getID().equals("develops_IN")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("DEVELOPS_IN");
                        }

                        else if (oborestrictionimpl.getType().getID().equals("disjoint_from")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("DISJOINT_FROM");
                        }

                        else if (oborestrictionimpl.getType().getID().equals("surrounds")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("SURROUNDS");
                        }

                        else if (oborestrictionimpl.getType().getID().equals("attached_to")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("ATTACHED_TO");
                        }

                        else {
                            System.out.println("TYPE = " + oborestrictionimpl.getType().getID());
                        }

                        
                    }

                    //System.out.println("terms with undefined rels = " + counterRel);
                    componentList.add(obocomponent);
                    
                }
            }
                
            //get obsolete terms
            Set obsoTerms = obosession.getObsoleteObjects();

            for( Iterator m = obsoTerms.iterator(); m.hasNext(); ){
              	OBOClassImpl oboclassimpl = (OBOClassImpl) m.next();
                    
                ArrayList<String> childOfs = new ArrayList<String>();
            	ArrayList<String> childOfTypes = new ArrayList<String>();
            	ArrayList<String> synonyms = new ArrayList<String>();
            	ArrayList<String> userComments = new ArrayList<String>();
            	TreeSet comments = new TreeSet();

            	ComponentFile obocomponent = new ComponentFile(oboclassimpl.toString(), 
            		oboclassimpl.getID(), 
                    "TBD",
                    "TBD",
                    oboclassimpl.getNamespace().toString(),
                    false,
                    "TBD",
                    "TBD",
                    0,
                    "DELETED",
                    "UNCHECKED",
                    childOfs,
                    childOfTypes,
                    synonyms,
                    userComments,
                    "",
                    comments);

            	obocomponent.setCheckComment("INFO: Obsolete Term");

                //set synonyms (not string in oboparser)
                for(Iterator n = oboclassimpl.getSynonyms().iterator(); n.hasNext(); ){
                	obocomponent.addSynonym(n.next().toString());
                }

                componentList.add(obocomponent);
      
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return componentList;
    }
        
}


