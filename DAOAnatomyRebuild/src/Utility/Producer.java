/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import OBOModel.ComponentFile;
import OBOModel.Relation;


public class Producer {

    // Attributes ---------------------------------------------------------------------------------
	//  Output Filename
    private String fileName;
	//  Output Namespace
    private String fileVersion;
	//  Output Namespace
    private String fileNameSpace;
	//  Output fileSavedBy
    private String fileSavedBy;
	//  Output fileRemark
    private String fileRemark;
    //  Arraylist of <ComponentFile>s
    private ArrayList<ComponentFile> obocomponentList;
    private ArrayList <Relation> oborelationList;

    private boolean isProcessed;

    // Constructor --------------------------------------------------------------------------------
    public Producer(String fileName, 
    		String fileVersion,
    		String fileNameSpace,
    		String fileSavedBy,
    		String fileRemark,
    		ArrayList<ComponentFile> obocomponentList, 
    		ArrayList<Relation> oborelationList){
    	
        this.fileName = fileName.trim();
        this.fileVersion = fileVersion.trim();
        this.fileNameSpace = fileNameSpace.trim();
        this.fileSavedBy = fileSavedBy.trim();
        this.fileRemark = fileRemark.trim();
        this.obocomponentList = obocomponentList;
        this.oborelationList = oborelationList;
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
    public ArrayList getComponents(){
        return this.obocomponentList;
    }
    public ArrayList getRelations(){
        return this.oborelationList;
    }
    public Boolean getIsProcessed(){
        return this.isProcessed;
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
    public void setComponents(ArrayList<ComponentFile> obocomponentList){
        this.obocomponentList = obocomponentList;
    }
    public void setRelations(ArrayList<Relation> oborelationList){
        this.oborelationList = oborelationList;
    }
    public void setIsProcessed(Boolean isProcessed){
        this.isProcessed = isProcessed;
    }
    
    // Methods ------------------------------------------------------------------------------------
    public Boolean writeOboFile(){

        isProcessed = false;

    	try {
            //System.out.println("saveOBOFile #1");
            BufferedWriter outputFile =
                        new BufferedWriter(new FileWriter(fileName));

            // format-version
            outputFile.write("format-version: " + fileVersion + "\n");

            // date
            //outputFile.write("date: "+DATE+"\n");
            Calendar cal = Calendar.getInstance();
            outputFile.write("date: " + cal.get(Calendar.DAY_OF_MONTH) + ":" +
                    cal.get(Calendar.MONTH) + ":" + cal.get(Calendar.YEAR) + " " +
                    cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) +
                    "\n");

            // saved by
            outputFile.write("saved-by: " + fileSavedBy + "\n");

            // default-namespace
            outputFile.write("default-namespace: " + fileNameSpace + "\n");

            // remark
            outputFile.write("remark: " + fileRemark + "\n");

            // terms - ComponentFile
            //  for i
            for (int i=0; i<obocomponentList.size(); i++) {
            	
                if ( !obocomponentList.get(i).getStatusChange().equals("DELETED") ){

                	outputFile.write("\n[Term]\n");
                    outputFile.write("id: " + obocomponentList.get(i).getID() + "\n");
                    outputFile.write("name: " + obocomponentList.get(i).getName() + "\n");
                    outputFile.write("namespace: " +
                    		obocomponentList.get(i).getNamespace() + "\n");

                    if ( !obocomponentList.get(i).getNamespace().equals("theiler_stage") ||
                        !obocomponentList.get(i).getNamespace().equals("new_group_namespace") &&
                        !obocomponentList.get(i).getNamespace().equals("group_term") &&
                        !obocomponentList.get(i).getName().equals("Abstract anatomy") ){

                        // part_of relationships
                        for (int j=0; j<obocomponentList.get(i).getChildOfs().size(); j++) {
                        	
                            if (obocomponentList.get(i).getChildOfTypes().get(j).equals("PART_OF")) {
                                outputFile.write("relationship: part_of " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                            if (obocomponentList.get(i).getChildOfTypes().get(j).equals("IS_A")) {
                                outputFile.write("relationship: is_a " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                            if (obocomponentList.get(i).getChildOfTypes().get(j).equals("GROUP_PART_OF")) {
                                outputFile.write("relationship: group_part_of " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                            
                        }

                        if ( !obocomponentList.get(i).getStart().equals("") ) {
                            outputFile.write("relationship: starts_at " +
                            		obocomponentList.get(i).getStart() +
                                    "\n");
                        }

                        if ( !obocomponentList.get(i).getEnd().equals("") ) {
                            outputFile.write("relationship: ends_at " +
                            		obocomponentList.get(i).getEnd() + "\n");
                        }

                        if ( obocomponentList.get(i).getPresent() != 0 ) {
                            outputFile.write("relationship: present_in " +
                                    Integer.toString(obocomponentList.get(i).getPresent()) +
                                    "\n");
                        }

                        for (int j=0; j<obocomponentList.get(i).getSynonyms().size(); j++) {
                        	
                            outputFile.write("related_synonym: \"" +
                            		obocomponentList.get(i).getSynonyms().get(j) +
                                    "\" []\n");
                        
                        }

                        if (obocomponentList.get(i).getIsGroup()) {
                            outputFile.write("relationship: is_a group_term\n");
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
                        else {
                            outputFile.write("\n");
                        }
                    
                        outputFile.write(k.next());

                    }
                outputFile.write("\n");
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


