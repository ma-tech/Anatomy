/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyOBO
#
# Title:        Producer.java
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
# Description:  Produced an OBO File from an ArrayList of OBO Components
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package routines.base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import obomodel.OBOComponent;
import obomodel.Relation;
import utility.Wrapper;

public class Producer {

    private static String HUMAN_STAGE =  "carnegie stage";
    private static String MOUSE_STAGE =  "theiler stage";
    private static String CHICK_STAGE =  "theiler stage";

    private static String HUMAN_NAME = "Abstract human developmental anatomy";
    private static String MOUSE_NAME = "Abstract anatomy";
    private static String CHICK_NAME = "Abstract anatomy";


    // Attributes ---------------------------------------------------------------------------------
    private String fileName;
    private String fileVersion;
    private String fileNameSpace;
    private String fileSavedBy;
    private String fileRemark;
    private ArrayList<OBOComponent> obocomponentList;
    private ArrayList<Relation> oborelationList;

    private boolean isProcessed;

    private String msgLevel;

    private boolean boolAlternatives;
    private boolean boolTimedComponents;

    // Constructor --------------------------------------------------------------------------------
    public Producer(
    		String msgLevel, 
    		String fileName, 
    		String fileVersion,
    		String fileNameSpace,
    		String fileSavedBy,
    		String fileRemark,
    		ArrayList<OBOComponent> obocomponentList, 
    		ArrayList<Relation> oborelationList,
    		Boolean boolAlternatives,
    		Boolean boolTimedComponents) throws Exception{
    	
	    this.msgLevel = msgLevel;
        
	    Wrapper.printMessage("producer.constructor", "***", this.msgLevel);

        this.fileName = fileName.trim();
        this.fileVersion = fileVersion.trim();
        this.fileNameSpace = fileNameSpace.trim();
        this.fileSavedBy = fileSavedBy.trim();
        this.fileRemark = fileRemark.trim();
        this.obocomponentList = obocomponentList;
        this.oborelationList = oborelationList;

        this.boolAlternatives = boolAlternatives;
        this.boolTimedComponents = boolTimedComponents;
    }

    // Getters ------------------------------------------------------------------------------------
    public String getFile(){
        return this.fileName;
    }
    public String getFileVersion(){
        return this.fileVersion;
    }
    public String getFileNameSpace(){
        return this.fileNameSpace;
    }
    public String getFileSavedBy(){
        return this.fileSavedBy;
    }
    public String getFileRemark(){
        return this.fileRemark;
    }
    public ArrayList<OBOComponent> getComponents(){
        return this.obocomponentList;
    }
    public ArrayList<Relation> getRelations(){
        return this.oborelationList;
    }
    public Boolean getIsProcessed(){
        return this.isProcessed;
    }
    public Boolean getAlternatives(){
        return this.boolAlternatives;
    }
    public Boolean geTimedComponents(){
        return this.boolTimedComponents;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setFile(String file){
        this.fileName = file;
    }
    public void setFileVersion(String fileVersion){
        this.fileVersion = fileVersion;
    }
    public void setFileNameSpace(String fileNameSpace){
        this.fileNameSpace = fileNameSpace;
    }
    public void setFileSavedBy(String fileSavedBy){
        this.fileSavedBy = fileSavedBy;
    }
    public void setFileRemark(String fileRemark){
        this.fileRemark = fileRemark;
    }
    public void setComponents(ArrayList<OBOComponent> obocomponentList){
        this.obocomponentList = obocomponentList;
    }
    public void setRelations(ArrayList<Relation> oborelationList){
        this.oborelationList = oborelationList;
    }
    public void setIsProcessed(Boolean isProcessed){
        this.isProcessed = isProcessed;
    }
    public void setAlternatives(Boolean boolAlternatives){
        this.boolAlternatives = boolAlternatives;
    }
    public void setTimedComponents(Boolean boolTimedComponents){
        this.boolTimedComponents = boolTimedComponents;
    }
    
    // Methods ------------------------------------------------------------------------------------
    public Boolean writeOboFile( String stage ) throws Exception{

	    Wrapper.printMessage("producer.writeOboFile", "***", this.msgLevel);

        isProcessed = false;

    	try {
    		
    		String newFileName = fileName.substring(0, fileName.indexOf(".")) + "_" + stage + ".obo";
    		
            BufferedWriter outputFile =
                        new BufferedWriter(new FileWriter(newFileName));

            outputFile.write("format-version: " + fileVersion + "\n");

            Date today = new Date();
        	SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        	String formattedDate = format.format(today);
            outputFile.write("date: " + formattedDate + "\n");

            outputFile.write("saved-by: " + fileSavedBy + "\n");
            outputFile.write("default-namespace: " + fileNameSpace + "\n");
            outputFile.write("remark: " + fileRemark + "\n\n");

            // terms - OBOComponent
            //  for i
            for (int i=0; i<obocomponentList.size(); i++) {
            	
                if ( !obocomponentList.get(i).getStatusChange().equals("DELETED") ){

                	outputFile.write("[Term]\n");
                    outputFile.write("id: " + obocomponentList.get(i).getID() + "\n");
                    outputFile.write("name: " + obocomponentList.get(i).getName() + "\n");
                    outputFile.write("namespace: " + obocomponentList.get(i).getNamespace() + "\n");
                    
                    if ( !obocomponentList.get(i).getNamespace().equals(MOUSE_STAGE) ||
                    	!obocomponentList.get(i).getNamespace().equals(HUMAN_STAGE) ||
                    	!obocomponentList.get(i).getNamespace().equals(CHICK_STAGE) ||
                    		
                        !obocomponentList.get(i).getNamespace().equals("new_group_namespace") &&
                        !obocomponentList.get(i).getNamespace().equals("group_term") &&
                        
                        !obocomponentList.get(i).getName().equals(MOUSE_NAME) &&
                        !obocomponentList.get(i).getName().equals(HUMAN_NAME) &&
                        !obocomponentList.get(i).getName().equals(CHICK_NAME) ){

                    	// part_of relationships
                        for (int j=0; j<obocomponentList.get(i).getChildOfs().size(); j++) {
                        	
                        	if (obocomponentList.get(i).getChildOfTypes().get(j).equals("DEVELOPS_FROM")) {
                        		
                                outputFile.write("relationship: develops_from " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("LOCATED_IN")) {
                        		
                                outputFile.write("relationship: located_in " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("DEVELOPS_IN")) {
                        		
                                outputFile.write("relationship: develops_in " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("HAS_PART")) {
                        		
                                outputFile.write("relationship: has_part " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("ATTACHED_TO")) {
                        		
                                outputFile.write("relationship: attached_to " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("DISJOINT_FROM")) {
                        		
                                outputFile.write("relationship: disjoint_from " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("PART_OF")) {
                        		
                                outputFile.write("relationship: part_of " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("IS_A")) {
                        		
                                outputFile.write("relationship: is_a " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("GROUP_PART_OF")) {
                        		
                                outputFile.write("relationship: group_part_of " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                        	else if (obocomponentList.get(i).getChildOfTypes().get(j).equals("CONNECTED_TO")) {
                        		
                                outputFile.write("relationship: connected_to " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                            else {
                            	
                        	    Wrapper.printMessage("producer.writeOboFile:UNKNOWN Relationship Type = " + obocomponentList.get(i).getChildOfTypes().get(j) + "!", "*", this.msgLevel);
                            }
                        }

                        if ( !obocomponentList.get(i).getStart().equals("") ) {
                        	
                            outputFile.write("relationship: starts_at " +
                            		obocomponentList.get(i).getStart() + "\n");
                        }
                        
                        if ( !obocomponentList.get(i).getEnd().equals("") ) {
                        	
                            outputFile.write("relationship: ends_at " +
                            		obocomponentList.get(i).getEnd() + "\n");
                        }
                        
                        if ( obocomponentList.get(i).getPresent() != 0 ) {
                        	
                            outputFile.write("relationship: present_in " +
                                    Integer.toString(obocomponentList.get(i).getPresent()) + "\n");
                        }

                        for (int j=0; j<obocomponentList.get(i).getSynonyms().size(); j++) {
                        	
                            outputFile.write("related_synonym: \"" +
                            		obocomponentList.get(i).getSynonyms().get(j) + "\" []\n");
                        }

                        if ( this.boolAlternatives ) {
                        	
                            for (int k=0; k<obocomponentList.get(i).getAlternativeIds().size(); k++) {
                            	
                                outputFile.write("alt_id: " +
                                		obocomponentList.get(i).getAlternativeIds().get(k) + "\n");
                            }
                        }

                        if ( this.boolTimedComponents ) {
                        	
                            for (int l=0; l<obocomponentList.get(i).getTimedComponents().size(); l++) {
                            	
                                outputFile.write("alt_id: " +
                                		obocomponentList.get(i).getTimedComponents().get(l) + "\n");
                            }
                        }

                        if (obocomponentList.get(i).getIsGroup()) {
                        	
                            outputFile.write("relationship: is_a group_term\n");
                        }

                        if (!obocomponentList.get(i).getOrderComment().equals("")) {
                        	
                            outputFile.write("comment: " + obocomponentList.get(i).getOrderComment() + "\n");
                        }
                    }

                    boolean firstComment = true;

                    for (Iterator<String> k =
                    		obocomponentList.get(i).getUserComments().iterator();
                            k.hasNext();){
                    	
                        if (firstComment){
                        	
                            outputFile.write("comment: ");
                            firstComment = false;
                        } 

                        outputFile.write(k.next());

                    }
                    
                outputFile.write("\n");
                }
            }

            
            if ( oborelationList != null) {
            	
                // terms - OBOComponent
                //  for i
                for (int i=0; i<oborelationList.size(); i++) {
                
                	outputFile.write("\n[Typedef]\n");
                    outputFile.write("id: " + oborelationList.get(i).getID() + "\n");
                    outputFile.write("name: " + oborelationList.get(i).getName() + "\n");
                    
                    if ( "true".equals(oborelationList.get(i).getTransitive()) ) {
                    	
                        outputFile.write("is_transitive: true\n");
                    }
                }
            }

            outputFile.close();
            
            isProcessed = true;
    	}
    
    	catch(IOException io) {
    		
            isProcessed = false;
    		io.printStackTrace();
    	}
    	
    	return isProcessed;
    }
}
