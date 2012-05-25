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
package routines;

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

import obomodel.OBOComponent;

public class Parser {

    private String strFile;
    private ArrayList<OBOComponent> componentList;
    private OBOSession oboSession;

    public Parser(String txtFileName) throws IOException{

        this.strFile = txtFileName.trim();
        this.componentList = addComponents(this.strFile);
    }

    // Getters ------------------------------------------------------------------------------------
    public String getFile(){
        return this.strFile;
    }
    public ArrayList<OBOComponent> getComponents(){
        return this.componentList;
    }
    public OBOSession getOboSession(){
        return this.oboSession;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setFile(String strFile){
        this.strFile = strFile;
    }
    public void setComponents(ArrayList<OBOComponent> componentList){
        this.componentList = componentList;
    }
    public void setOboSession(OBOSession oboSession){
        this.oboSession = oboSession;
    }
    
    
    // Methods ------------------------------------------------------------------------------------
    public OBOSession getSession(String path) 
    		throws IOException {

        DefaultOBOParser defaultoboparser = new DefaultOBOParser();
	    OBOParseEngine obooparseengine = new OBOParseEngine(defaultoboparser);

        /*
         * GOBOParseEngine can parse several files at once
	     *  and create one munged-together ontology,
	     *  so we need to provide a Collection to the setPaths() method
         */
	    
        try {
    	    Collection<String> paths = new LinkedList<String>();
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

        this.setOboSession(obosession);
        
        return obosession;
    }
    
    
    private ArrayList<OBOComponent> addComponents(String strFile) 
    		throws IOException {
        
    	OBOSession obosession = getSession(strFile);

        this.setOboSession(obosession);

    	//all terms in a map
    	Map<?, ?> map = obosession.getAllTermsHash();
            
        return this.addComponents(map);
    }
        
         
    private ArrayList<OBOComponent> addComponents(Map<?, ?> map){
            
     	ArrayList<OBOComponent> componentList = new ArrayList<OBOComponent>();
        			
        try {
            //iterate through each term in the map
            	
			@SuppressWarnings("unchecked")
			Iterator<Map<?, ?>> iteratorMap = (Iterator<Map<?, ?>>) map.keySet().iterator();
    
            while (iteratorMap.hasNext() ){

                OBOClassImpl oboclassimpl = (OBOClassImpl) iteratorMap.next();
                
                //remove 4 default extra terms in map created by obosession.getAllTermsHash()
                if(!oboclassimpl.getID().startsWith("obo")){

            		ArrayList<String> alternativeIds = new ArrayList<String>();
            		ArrayList<String> childOfs = new ArrayList<String>();
            		ArrayList<String> childOfTypes = new ArrayList<String>();
            		ArrayList<String> synonyms = new ArrayList<String>();
            		ArrayList<String> userComments = new ArrayList<String>();
            		TreeSet<String> comments = new TreeSet<String>();

            		OBOComponent obocomponent = new OBOComponent(oboclassimpl.toString(), 
                    		oboclassimpl.getID(), 
                    		"TBD",
                    		"TBD",
                    		oboclassimpl.getNamespace().toString(),
                    		oboclassimpl.getDefinition().toString(),
                    		false,
                    		"TBD",
                    		"TBD",
                    		0,
                    		"",
                    		//"UNCHANGED",
                    		"UNCHECKED",
                     		childOfs,
                    		childOfTypes,
                    		synonyms,
                    		userComments,
                    		"",
                    		comments,
                    		alternativeIds);
            		
                    /*
                     * set obocomponent synonyms (arraylist)
                     */
					@SuppressWarnings("unchecked")
					Iterator<Map<?, ?>> iteratorSynonyms = (Iterator<Map<?, ?>>) oboclassimpl.getSynonyms().iterator();
        		    
                    while (iteratorSynonyms.hasNext() ){
                    	obocomponent.addSynonym(iteratorSynonyms.next().toString());
                    }

					@SuppressWarnings("unchecked")
					Set<String> altIds = (Set<String>) oboclassimpl.getSecondaryIDs();

					Iterator<String> iteratorAltIds = altIds.iterator();

                    while (iteratorAltIds.hasNext() ){
                		obocomponent.addAlternative(iteratorAltIds.next());
                    }

                    /*
                     * set obocomponent relationships
                     *  parents(arraylist), startsat(string TSxx), endsat(TSxx)
                     *  note: j is one relationship,
                     *    child = node, parent = relationship postfix
                     */
					@SuppressWarnings("unchecked")
					Iterator<OBORestrictionImpl> iteratorParents = (Iterator<OBORestrictionImpl>) oboclassimpl.getParents().iterator();

                    while (iteratorParents.hasNext() ){

                        OBORestrictionImpl oborestrictionimpl = iteratorParents.next();

                        if (oborestrictionimpl.getType().getID().equals("part_of")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("PART_OF");
                        }
                        else if (oborestrictionimpl.getType().getID().equals("group_part_of")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("PART_OF");
                        	obocomponent.setIsPrimary(false);
                        }
                        else if (oborestrictionimpl.getType().getID().equals("OBO_REL:is_a")){
                        	if ( oborestrictionimpl.getParent().getID().equals("group_term") ) {
                        		obocomponent.setGroup(true);
                            	obocomponent.setIsPrimary(false);
                        	}
                        	else {
                        		obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                            	obocomponent.addChildOfType("IS_A");
                            	obocomponent.setIsPrimary(false);
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
                        else if (oborestrictionimpl.getType().getID().equals("develops_in")){
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
                        else if (oborestrictionimpl.getType().getID().equals("has_part")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("HAS_PART");
                        }
                        else {
                            System.out.println("TYPE = " + oborestrictionimpl.getType().getID());
                        }
                    }
                    componentList.add(obocomponent);
                }
            }
                
            //get obsolete terms
            Set<?> obsoTerms = this.oboSession.getObsoleteObjects();

			@SuppressWarnings("unchecked")
			Iterator<OBOClassImpl> iteratorObsoletes = (Iterator<OBOClassImpl>) obsoTerms.iterator();

            while (iteratorObsoletes.hasNext() ){

              	OBOClassImpl oboclassimpl = iteratorObsoletes.next();
                    
                ArrayList<String> childOfs = new ArrayList<String>();
            	ArrayList<String> childOfTypes = new ArrayList<String>();
            	ArrayList<String> synonyms = new ArrayList<String>();
            	ArrayList<String> userComments = new ArrayList<String>();
            	TreeSet<String> comments = new TreeSet<String>();

            	OBOComponent obocomponent = new OBOComponent(oboclassimpl.toString(), 
            		oboclassimpl.getID(), 
                    "TBD",
                    "TBD",
                    oboclassimpl.getNamespace().toString(),
                    oboclassimpl.getDefinition().toString(),
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
    			@SuppressWarnings("unchecked")
				Iterator<String> iteratorSynonyms = (Iterator<String>) oboclassimpl.getSynonyms();

                while (iteratorSynonyms.hasNext() ){
                	
                	obocomponent.addSynonym(iteratorSynonyms.next().toString());
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


