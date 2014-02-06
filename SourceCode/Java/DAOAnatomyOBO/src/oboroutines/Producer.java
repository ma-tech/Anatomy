/*
-----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        Producer.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  Produced an OBO File from an ArrayList of OBO Components
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
-----------------------------------------------------------------------------------------------
*/
package oboroutines;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import utility.Wrapper;

import obomodel.OBOComponent;
import obomodel.OBORelation;

public class Producer {

    private static String HUMAN_STAGE =  "Carnegie Stage";
    private static String MOUSE_STAGE =  "Theiler Stage";
    private static String CHICK_STAGE =  "Theiler Stage";

    private static String HUMAN_NAME = "Abstract human developmental anatomy";
    private static String MOUSE_NAME = "Anatomical Structure";
    private static String CHICK_NAME = "Abstract anatomy";

    private static String HUMAN_NAME_ABSTRACT_US = "Abstract human developmental anatomy";
    private static String MOUSE_NAME_ABSTRACT_US = "anatomical_structure";
    private static String CHICK_NAME_ABSTRACT_US = "Abstract anatomy";

    private static String HUMAN_NAME_TIMED_US = "Timed human developmental anatomy";
    private static String MOUSE_NAME_TIMED_US = "stage_specific_anatomical_structure";
    private static String CHICK_NAME_TIMED_US = "Abstract anatomy";

    // Attributes ---------------------------------------------------------------------------------
    private String fileName;
    private String fileDateTime;
    private String fileVersion;
    private String fileNameSpace;
    private String fileSavedBy;
    private String fileRemark;
    private ArrayList<OBOComponent> obocomponentList;
    private ArrayList<OBORelation> oborelationList;

    private boolean isProcessed;

    private String msgLevel;

    private boolean boolAlternatives;
    private boolean boolTimedComponents;

    // Constructor --------------------------------------------------------------------------------
    public Producer(
    		String msgLevel, 
    		String fileName, 
    		String fileDateTime, 
    		String fileVersion,
    		String fileNameSpace,
    		String fileSavedBy,
    		String fileRemark,
    		ArrayList<OBOComponent> obocomponentList, 
    		ArrayList<OBORelation> oborelationList,
    		boolean boolAlternatives,
    		boolean boolTimedComponents) throws Exception{
    	
	    this.msgLevel = msgLevel;
        
	    Wrapper.printMessage("producer.constructor", "***", this.msgLevel);

        this.fileName = fileName.trim();
        this.fileDateTime = fileDateTime.trim();
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
    public String getFileName(){
        return this.fileName;
    }
    public String getFileDateTime(){
        return this.fileDateTime;
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
    public ArrayList<OBORelation> getOBORelations(){
        return this.oborelationList;
    }
    public boolean isProcessed(){
        return this.isProcessed;
    }
    public boolean getAlternatives(){
        return this.boolAlternatives;
    }
    public boolean geTimedComponents(){
        return this.boolTimedComponents;
    }
    
    // Setters ------------------------------------------------------------------------------------
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    public void setFileDateTime(String fileDateTime){
        this.fileDateTime = fileDateTime;
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
    public void setOBORelations(ArrayList<OBORelation> oborelationList){
        this.oborelationList = oborelationList;
    }
    public void setIsProcessed(boolean isProcessed){
        this.isProcessed = isProcessed;
    }
    public void setAlternatives(boolean boolAlternatives){
        this.boolAlternatives = boolAlternatives;
    }
    public void setTimedComponents(boolean boolTimedComponents){
        this.boolTimedComponents = boolTimedComponents;
    }
    
    // Methods ------------------------------------------------------------------------------------
    public boolean writeOboFile( String stage ) throws Exception{

	    Wrapper.printMessage("producer.writeOboFile", "***", this.msgLevel);

        isProcessed = false;

    	try {
    		
            String newFileName = fileName + "/" + fileDateTime + "_OUTPUT_" + stage + ".obo";

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
            	
                if ( !obocomponentList.get(i).getStatusChange().equals("DELETE") ){

                	outputFile.write("[Term]\n");
                    outputFile.write("id: " + obocomponentList.get(i).getID() + "\n");
                    outputFile.write("name: " + obocomponentList.get(i).getName() + "\n");

                    /*
                    if ( stage.equals("TS01") ) {
                    	
                        System.out.println("stage = " + stage);
                        System.out.println("obocomponentList.get(i).getNamespace() = " + obocomponentList.get(i).getNamespace());
                    }
                    */
                    
                	if ( obocomponentList.get(i).getNamespace().equals("group_term") ) {

                        outputFile.write("namespace: " + "group_term" + "\n");
                    }
                	if ( obocomponentList.get(i).getNamespace().equals("theiler_stage") ) {

                        outputFile.write("namespace: " + "theiler_stage" + "\n");
                    }
                	if ( obocomponentList.get(i).getNamespace().equals("new_group_namespace") ) {

                        outputFile.write("namespace: " + "new_group_namespace" + "\n");
                    }

                    if ( stage.equals("Abstract")) {
                    	
                    	if ( obocomponentList.get(i).getNamespace().equals(MOUSE_NAME_ABSTRACT_US) ) {
                    		
                            outputFile.write("namespace: " + MOUSE_NAME_ABSTRACT_US + "\n");
                    	}
                    	if ( obocomponentList.get(i).getNamespace().equals(CHICK_NAME_ABSTRACT_US) ) {
                    		
                            outputFile.write("namespace: " + CHICK_NAME_ABSTRACT_US + "\n");
                    	}
                    	if ( obocomponentList.get(i).getNamespace().equals(HUMAN_NAME_ABSTRACT_US) ) {
                    		
                            outputFile.write("namespace: " + HUMAN_NAME_ABSTRACT_US + "\n");
                    	}
                    }
                    else {
                    	
                    	if ( obocomponentList.get(i).getNamespace().equals(MOUSE_NAME_ABSTRACT_US) ) {
                    		
                            outputFile.write("namespace: " + MOUSE_NAME_TIMED_US + "\n");
                    	}
                    	if ( obocomponentList.get(i).getNamespace().equals(CHICK_NAME_ABSTRACT_US) ) {
                    		
                            outputFile.write("namespace: " + CHICK_NAME_TIMED_US + "\n");
                    	}
                    	if ( obocomponentList.get(i).getNamespace().equals(HUMAN_NAME_ABSTRACT_US) ) {
                    		
                            outputFile.write("namespace: " + HUMAN_NAME_TIMED_US + "\n");
                    	}
                    	if ( obocomponentList.get(i).getNamespace().equals(MOUSE_NAME_TIMED_US) ) {
                    		
                            outputFile.write("namespace: " + MOUSE_NAME_TIMED_US + "\n");
                    	}
                    	if ( obocomponentList.get(i).getNamespace().equals(CHICK_NAME_TIMED_US) ) {
                    		
                            outputFile.write("namespace: " + CHICK_NAME_TIMED_US + "\n");
                    	}
                    	if ( obocomponentList.get(i).getNamespace().equals(HUMAN_NAME_TIMED_US) ) {
                    		
                            outputFile.write("namespace: " + HUMAN_NAME_TIMED_US + "\n");
                    	}
                    }
                    
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
                            	
                        	    Wrapper.printMessage("producer.writeOboFile:UNKNOWN OBORelationship Type = " + obocomponentList.get(i).getChildOfTypes().get(j) + "!", "*", this.msgLevel);
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
                        
                        /*
                        if ( obocomponentList.get(i).isPresent() == false ) {
                        	
                        	if ( !(obocomponentList.get(i).getNamespace().equals("group_term") ||
                        			obocomponentList.get(i).getNamespace().equals("new_group_namespace") ||
                        			obocomponentList.get(i).getNamespace().equals("theiler_stage") ) ) {

                                outputFile.write("relationship: present_in " +
                                        Boolean.valueOf(obocomponentList.get(i).isPresent()) + "\n");
                            }
                        }
                        */

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

                        if (obocomponentList.get(i).isGroup()) {
                        	
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
