/*
*----------------------------------------------------------------------------------------------
* Project:      AnatomyOBO
*
* Title:        ListOBOComponentsFromOBOFile.java
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
* Mike Wicks; November 2012; More tidying up
* 
*----------------------------------------------------------------------------------------------
*/
package routines.aggregated;

import java.util.ArrayList;
import java.util.List;

import obomodel.OBOComponent;

import obolayer.ComponentOBO;
import obolayer.OBOFactory;
import obolayer.OBOException;

import daolayer.DAOFactory;

import utility.Wrapper;

public class ListOBOComponentsFromOBOFile {
    // Properties ---------------------------------------------------------------------------------
    // global variables
    private ArrayList<OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
    
    private String inputFileContents;
    
    private String requestMsgLevel;

    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromOBOFile(String requestMsgLevel, 
    		DAOFactory daofactory, 
    		OBOFactory obofactory) throws Exception {
    	
        this.requestMsgLevel = requestMsgLevel;
        
        Wrapper.printMessage("listobocomponentsfromobofile.constructor", "LOW", this.requestMsgLevel);
        
        try {
        	
            ComponentOBO componentOBO = obofactory.getComponentOBO();
            
            this.inputFileContents = obofactory.getComponentOBO().inputFileContents();
            
            List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
            obocomponents = componentOBO.listAll();
            
            this.obocomponentList = (ArrayList<OBOComponent>) obocomponents;
        }
    	catch (OBOException oboexception) {
    		
    		oboexception.printStackTrace();
        }
    }
    
    // Getters ------------------------------------------------------------------------------------
    public ArrayList<OBOComponent> getObocomponentList() {
        return this.obocomponentList;
    }
    public String getInputFileContents() {
        return this.inputFileContents;
    }
}