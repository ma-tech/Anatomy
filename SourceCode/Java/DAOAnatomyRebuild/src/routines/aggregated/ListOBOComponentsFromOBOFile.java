/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
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
* Version:      1
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Description:  This Class extracts a list of components from an OBO file, and builds a list 
*                in the OBO style.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; February 2012; Completely rewrite to use standardised Data Access Object Layer
* Mike Wicks; November 2012; More tidying up
* 
*----------------------------------------------------------------------------------------------
*/
package routines.aggregated;

import java.util.ArrayList;
import java.util.List;

import utility.Wrapper;

import obomodel.OBOComponent;

import oboaccess.OBOComponentAccess;

import obolayer.OBOFactory;
import obolayer.OBOException;

public class ListOBOComponentsFromOBOFile {
    // Properties ---------------------------------------------------------------------------------
    // global variables
    private ArrayList<OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
    
    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromOBOFile( OBOFactory obofactory, String fileType) throws Exception {
    	
        Wrapper.printMessage("listobocomponentsfromobofile.constructor", "***", obofactory.getMsgLevel());
        
        try {
        	
        	if ( fileType.equals("INPUT")) {
        		
                OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();
                
                //this.inputFileContents = obofactory.getOBOComponentAccess().inputFileContents();
                
                List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
                obocomponents = obocomponentaccess.listAllInput();
                
                this.obocomponentList = (ArrayList<OBOComponent>) obocomponents;
        	}
        	else if (fileType.equals("BASE")) {
        		
                OBOComponentAccess obocomponentaccess = obofactory.getOBOComponentAccess();
                
                //this.inputFileContents = obofactory.getOBOComponentAccess().baseFileContents();
                
                List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
                obocomponents = obocomponentaccess.listAllBase();
                
                this.obocomponentList = (ArrayList<OBOComponent>) obocomponents;
        	}
        	else {
        		
                this.obocomponentList = null;
                
                Wrapper.printMessage("ERROR! Invalid File " + fileType + " passed to ListOBOComponentsFromOBOFile", "*", obofactory.getMsgLevel());
        	}
        	
        }
    	catch (OBOException oboexception) {
    		
    		oboexception.printStackTrace();
        }
    }
    
    // Getters ------------------------------------------------------------------------------------
    public ArrayList<OBOComponent> getObocomponentList() {
        return this.obocomponentList;
    }
}
