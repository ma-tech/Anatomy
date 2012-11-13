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
package routinesbase;

import java.io.IOException;
import java.io.FileInputStream;

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

import utility.StringStreamConverter;

public class Parser {

    private static final String MIN_MOUSE_STAGE_STR = "TS01";
    private static final String MIN_HUMAN_STAGE_STR = "CS01";
    private static final String MIN_CHICK_STAGE_STR = "EGK-I";
    
    private static final String MAX_MOUSE_STAGE_STR = "TS28";
    private static final String MAX_HUMAN_STAGE_STR = "CS23";
    private static final String MAX_CHICK_STAGE_STR = "HH48";

    private String species;
    private String file;
    private String fileContent;
    private ArrayList<OBOComponent> componentList;
    private OBOSession oboSession;
    private Boolean debug;
    private Boolean boolAlternatives;

    public Parser(Boolean debug, 
    		String txtFileName,
    		Boolean boolAlternatives,
    		String species) throws IOException{

        this.debug = debug;
        
        if (this.debug) {
        	
            System.out.println("======");
            System.out.println("Parser - Constructor");
            System.out.println("======");
        }

        this.file = txtFileName.trim();
        this.species = species;
        this.boolAlternatives = boolAlternatives;

        this.componentList = addComponents(this.file);
        this.fileContent = StringStreamConverter.convertStreamToString(new FileInputStream(this.file));
    }

    // Getters ------------------------------------------------------------------------------------
    public Boolean getAlternatives(){
        return this.boolAlternatives;
    }
    public String getFile(){
        return this.file;
    }
    public String getFileContent(){
        return this.fileContent;
    }
    public ArrayList<OBOComponent> getComponents(){
        return this.componentList;
    }
    public OBOSession getOboSession(){
        return this.oboSession;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setAlternatives(Boolean boolAlternatives){
        this.boolAlternatives = boolAlternatives;
    }
    public void setFile(String file){
        this.file = file;
    }
    public void setFileContent(String fileContent){
        this.fileContent = fileContent;
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

        if (this.debug) {
        	
            System.out.println("getSession");
        }

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
    
    
    private ArrayList<OBOComponent> addComponents(String file) 
    		throws IOException {
        
        if (this.debug) {
        	
            System.out.println("addComponents");
        }

    	OBOSession obosession = getSession(file);

        this.setOboSession(obosession);

    	//all terms in a map
    	Map map = obosession.getAllTermsHash();
            
        return this.addComponents(map);
    }
        
         
    private ArrayList<OBOComponent> addComponents(Map map){
            
        if (this.debug) {
        	
            System.out.println("addComponents");
        }

		if ( !this.boolAlternatives ) {
            
            System.out.println("IGNORING Alternative Ids");
		}

     	ArrayList<OBOComponent> componentList = new ArrayList<OBOComponent>();
        			
        try {
            //iterate through each term in the map
			Iterator iteratorMap = map.keySet().iterator();
    
            while (iteratorMap.hasNext() ){

                OBOClassImpl oboclassimpl = (OBOClassImpl) map.get(iteratorMap.next());
                
                //remove 4 default extra terms in map created by obosession.getAllTermsHash()
                if(!oboclassimpl.getID().startsWith("obo")){

            		ArrayList<String> alternativeIds = new ArrayList<String>();
            		ArrayList<String> childOfs = new ArrayList<String>();
            		ArrayList<String> childOfTypes = new ArrayList<String>();
            		ArrayList<String> synonyms = new ArrayList<String>();
            		ArrayList<String> userComments = new ArrayList<String>();
            		TreeSet<String> comments = new TreeSet<String>();
            		
            		String [] words = oboclassimpl.getComment().split("\n");
            		String orderComments = "";

            		String maxStage = "";
            		String minStage = "";
            		
                    if (species.equals("mouse")) {
                    	minStage = MIN_MOUSE_STAGE_STR;
                    	maxStage = MAX_MOUSE_STAGE_STR;
                    }
                    else if (species.equals("human")) {
                    	minStage = MIN_HUMAN_STAGE_STR;
                    	maxStage = MAX_HUMAN_STAGE_STR;
                    }
                    else if (species.equals("chick")) {
                    	minStage = MIN_CHICK_STAGE_STR;
                    	maxStage = MAX_CHICK_STAGE_STR;
                    }

                    for ( int i = 0; i < words.length; i++ ) {
                		userComments.add(words[i]);
                		orderComments = orderComments + " " + words[i];
                    }

                    /*
                    if ( "EMAPA:31194".equals(oboclassimpl.getID()) ) {
                        System.out.println("Comments for " + oboclassimpl.getID() + " : " + userComments.toString());
                    }
                    */

                    /*
                    public OBOComponent(String name, 
			    		String id,
			    		String dbID,
			    		String newid,
			    		String namespace,
			    		String definition,
			    		Boolean group,
			    		String start,
			    		String end,
			    		Integer present, 
			    		String statusChange, 
			    		String statusRule,
			    		ArrayList<String> childOfs,
			    		ArrayList<String> childOfTypes,
			    		ArrayList<String> synonyms, 
			    		ArrayList<String> userComments,
			    		String orderComment,
			    		TreeSet<String> comments,
			    		ArrayList<String> alternativeIds
			    	)
                    */
                    OBOComponent obocomponent = new OBOComponent(oboclassimpl.toString(), 
                    		oboclassimpl.getID(), 
                    		"",
                    		"TBD",
                    		"TBD",
                    		oboclassimpl.getNamespace().toString(),
                    		oboclassimpl.getDefinition().toString(),
                    		false,
                    		minStage,
                    		maxStage,
                    		0,
                    		"",
                    		//"UNCHANGED",
                    		"UNCHECKED",
                     		childOfs,
                    		childOfTypes,
                    		synonyms,
                    		userComments,
                    		orderComments,
                    		comments,
                    		alternativeIds);
            		
                    /*
                     * set obocomponent synonyms (arraylist)
                     */
					Iterator iteratorSynonyms = oboclassimpl.getSynonyms().iterator();
        		    
                    while (iteratorSynonyms.hasNext() ){
                    	obocomponent.addSynonym(iteratorSynonyms.next().toString());
                    }


					@SuppressWarnings("unchecked")
					Set<String> altIds = (Set<String>) oboclassimpl.getSecondaryIDs();

					
					Iterator<String> iteratorAltIds = altIds.iterator();

					if ( boolAlternatives ) {
	                
						while (iteratorAltIds.hasNext() ){
	                	
							obocomponent.addAlternative(iteratorAltIds.next());
	                    }
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
                    		obocomponent.setGroup(false);
                        	obocomponent.setIsPrimary(true);
                        }
                        else if (oborestrictionimpl.getType().getID().equals("group_part_of")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("PART_OF");
                    		obocomponent.setGroup(true);
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
                        		obocomponent.setGroup(false);
                            	obocomponent.setIsPrimary(false);
                        	}
                        }
                        else if (oborestrictionimpl.getType().getID().equals("starts_at")){
                        	obocomponent.setStart(oborestrictionimpl.getParent().getID());
                        }
                        else if (oborestrictionimpl.getType().getID().equals("ends_at")){
                        	obocomponent.setEnd(oborestrictionimpl.getParent().getID());
                        }
                        else if (oborestrictionimpl.getType().getID().equals("derives_from")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("DERIVES_FROM");
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
                        /*
                        else if (oborestrictionimpl.getType().getID().equals("surrounds")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("SURROUNDS");
                        }
                        */
                        else if (oborestrictionimpl.getType().getID().equals("attached_to")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("ATTACHED_TO");
                        }
                        else if (oborestrictionimpl.getType().getID().equals("has_part")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("HAS_PART");
                        }
                        else if (oborestrictionimpl.getType().getID().equals("connected_to")){
                        	obocomponent.addChildOf(oborestrictionimpl.getParent().getID());
                        	obocomponent.addChildOfType("CONNECTED_TO");
                        }
                        else if (oborestrictionimpl.getType().getID().equals("has_timed_component")){
                            System.out.println("IGNORING Timed Component = " + oborestrictionimpl.getParent().getID() );
                        }
                        else {
                            System.out.println("UNKNOWN Relationship Type = " + oborestrictionimpl.getType().getID());
                        }
                    }
                    componentList.add(obocomponent);
                }
            }
                
            //get obsolete terms
            Set obsoTerms = this.oboSession.getObsoleteObjects();

			Iterator<OBOClassImpl> iteratorObsoletes = obsoTerms.iterator();

            while (iteratorObsoletes.hasNext() ){

              	OBOClassImpl oboclassimpl = iteratorObsoletes.next();
                    
                ArrayList<String> childOfs = new ArrayList<String>();
            	ArrayList<String> childOfTypes = new ArrayList<String>();
            	ArrayList<String> synonyms = new ArrayList<String>();
            	ArrayList<String> userComments = new ArrayList<String>();
            	TreeSet<String> comments = new TreeSet<String>();

                /*
                public OBOComponent(String name, 
		    		String id,
		    		String dbID,
		    		String newid,
		    		String namespace,
		    		String definition,
		    		Boolean group,
		    		String start,
		    		String end,
		    		Integer present, 
		    		String statusChange, 
		    		String statusRule,
		    		ArrayList<String> childOfs,
		    		ArrayList<String> childOfTypes,
		    		ArrayList<String> synonyms, 
		    		ArrayList<String> userComments,
		    		String orderComment,
		    		TreeSet<String> comments,
		    		ArrayList<String> alternativeIds
		    	)
                */
            	OBOComponent obocomponent = new OBOComponent(oboclassimpl.toString(), 
            		oboclassimpl.getID(), 
            		"",
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

                componentList.add(obocomponent);
      
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return componentList;
    }
        
}


