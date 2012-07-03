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

package routinesaggregated;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import obomodel.OBOComponent;

import obolayer.ComponentOBO;
import obolayer.OBOFactory;
import obolayer.OBOException;
import routinesbase.Parser;

import daolayer.DAOFactory;


public class ListOBOComponentsFromOBOFile {

    // Properties ---------------------------------------------------------------------------------
    // global variables
    private ArrayList<OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
    
    private String inputFileContents;
    
    private Boolean debug;

    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromOBOFile(Boolean debug, 
    		DAOFactory daofactory, 
    		OBOFactory obofactory) throws IOException {
    	
        this.debug = debug;
        
        if (this.debug) {
        	
            System.out.println("============================");
            System.out.println("ListOBOComponentsFromOBOFile - Constructor #1");
            System.out.println("============================");
        }

    	try {
            ComponentOBO componentOBO = obofactory.getComponentOBO();
            
            inputFileContents = obofactory.getComponentOBO().inputFileContents();
            
            List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
            obocomponents = componentOBO.listAll();
            
            obocomponentList = (ArrayList<OBOComponent>) obocomponents;
        }
    	catch (OBOException oboexception) {
    		oboexception.printStackTrace();
        }
    }

    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromOBOFile(Boolean debug,
    		String filename) throws IOException{

        this.debug = debug;
        
        if (this.debug) {
        	
            System.out.println("============================");
            System.out.println("ListOBOComponentsFromOBOFile - Constructor #2");
            System.out.println("============================");
        }

        Parser parser = new Parser(this.debug, filename);
        obocomponentList = (ArrayList<OBOComponent>) parser.getComponents();
    }

    // Getters ------------------------------------------------------------------------------------
    public ArrayList<OBOComponent> getTermList() {
        return obocomponentList;
    }
    public String getInputFileContents() {
        return inputFileContents;
    }
}
