/*
*----------------------------------------------------------------------------------------------
* Project:      Anatomy
*
* Title:        ImportDatabase.java
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
* Maintenance:  Log changes below, with most recent at top of list.
*
* Description:  This Class extracts a list of components from the database, and builds a list 
*                in the OBO style.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; February 2012; Completely rewrite to use standardised DAO Layer
* 
*----------------------------------------------------------------------------------------------
*/

package Utility;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Calendar;

import OBOModel.ComponentFile;
import OBOModel.Relation;

import OBOLayer.ComponentOBO;
import OBOLayer.OBOFactory;
import OBOLayer.OBOException;


public class ImportFile {

    // Properties ---------------------------------------------------------------------------------

    // global variables
    private ArrayList <ComponentFile> obocomponentList = new ArrayList <ComponentFile>();
    private ArrayList <Relation> oborelationList = new ArrayList <Relation>();

    
    // Constructor --------------------------------------------------------------------------------
    public ImportFile() {
    	
    	try {
    		
            OBOFactory obofactory = OBOFactory.getInstance("file");
            //System.out.println("OBOFactory successfully obtained: " + obofactory);

            ComponentOBO componentOBO = obofactory.getComponentOBO();
            //System.out.println("ComponentOBO successfully obtained: " + componentOBO);
            
            List<ComponentFile> obocomponents = new ArrayList<ComponentFile>();
            obocomponents = componentOBO.listAll();
            
            obocomponentList = (ArrayList) obocomponents;

        }
    	catch (OBOException oboexception) {
    		oboexception.printStackTrace();
        }

    }

    public ImportFile(String filename) {
    	
    	ArrayList<ComponentFile> componentList = new ArrayList();
        Parser parser = new Parser(filename);
        obocomponentList = (ArrayList) parser.getComponents();
    }

    public ArrayList<ComponentFile> getTermList() {

        return obocomponentList;

    }

    
    public void saveOBOFile(String fileName) {

        try {

            //System.out.println("saveOBOFile #1");

            BufferedWriter outputFile =
                        new BufferedWriter(new FileWriter(fileName));

            /*
            // format-version
            outputFile.write("format-version: " + mouseFormatVersionTF.getText() +
                    "\n");
            */
            // format-version
            outputFile.write("format-version: " + "\n");

            // date
            //outputFile.write("date: "+DATE+"\n");
            Calendar cal = Calendar.getInstance();
            outputFile.write("date: " + cal.get(Calendar.DAY_OF_MONTH) + ":" +
                    cal.get(Calendar.MONTH) + ":" + cal.get(Calendar.YEAR) + " " +
                    cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) +
                    "\n");

            // saved by
            outputFile.write("saved-by: " + "\n");

            // default-namespace
            outputFile.write("default-namespace: " + "\n");

            // remark
            outputFile.write("remark: " + "\n");
            /*
            // saved by
            outputFile.write("saved-by: " + savedByTF.getText() + "\n");

            // default-namespace
            outputFile.write("default-namespace: " +
                            mouseDefaultNamespaceTF.getText() + "\n");

            // remark
            outputFile.write("remark: " + remarkTA.getText() + "\n");
            */

            // terms - ComponentFile
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
                            /*
                            if (obocomponentList.get(i).getChildOfTypes().get(j).equals("GROUP_PART_OF")) {
                                outputFile.write("relationship: group_part_of " +
                                		obocomponentList.get(i).getChildOfs().get(j) + "\n");
                            }
                            */
                            if (obocomponentList.get(i).getChildOfTypes().get(j).equals("GROUP_PART_OF")) {
                                outputFile.write("relationship: part_of " +
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

                        if (obocomponentList.get(i).getIsGroup()) {
                            outputFile.write("relationship: is_a group_term\n");
                        }

                        for (int j=0; j<obocomponentList.get(i).getSynonyms().size(); j++) {
                            outputFile.write("related_synonym: \"" +
                            		obocomponentList.get(i).getSynonyms().get(j) +
                                    "\" []\n");
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

            }// for i

            // relations
            for (int i=0; i<oborelationList.size(); i++) {

            	outputFile.write("\n[Typedef]\n");
                outputFile.write("id: " + oborelationList.get(i).getID() +
                        "\n");
                outputFile.write("name: " + oborelationList.get(i).getName() +
                        "\n");
                
                if ( !oborelationList.get(i).getTransitive().equals("") ) {
                    outputFile.write("is_transitive: " +
                    		oborelationList.get(i).getTransitive() +
                            "\n");
                }
                
            }
            outputFile.write("\n");
            outputFile.close(); // close outputFile
        }
        catch(IOException io) {
            io.printStackTrace();
        }
    }

}
