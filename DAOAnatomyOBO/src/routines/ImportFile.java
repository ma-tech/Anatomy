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

package routines;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import obomodel.OBOComponent;

import obolayer.ComponentOBO;
import obolayer.OBOFactory;
import obolayer.OBOException;

public class ImportFile {

    // Properties ---------------------------------------------------------------------------------
    // global variables
    private ArrayList<OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
    
    // Constructor --------------------------------------------------------------------------------
    public ImportFile() throws IOException {
    	
    	try {
            OBOFactory obofactory = OBOFactory.getInstance("file");

            ComponentOBO componentOBO = obofactory.getComponentOBO();
            
            List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
            obocomponents = componentOBO.listAll();
            
            obocomponentList = (ArrayList<OBOComponent>) obocomponents;
        }
    	catch (OBOException oboexception) {
    		oboexception.printStackTrace();
        }
    }

    public ImportFile(String filename) throws IOException{
        Parser parser = new Parser(filename);
        obocomponentList = (ArrayList<OBOComponent>) parser.getComponents();
    }

    public ArrayList<OBOComponent> getTermList() {
        return obocomponentList;
    }
}