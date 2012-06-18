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

public class ListOBOComponentsFromOBOFile {

    // Properties ---------------------------------------------------------------------------------
    // global variables
    private ArrayList<OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
    
    private String inputFileName;
    private String inputFileContents;
    private String outputReportName;
    private String outputReportPDFName;
    
    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromOBOFile() throws IOException {
    	
    	try {
            OBOFactory obofactory = OBOFactory.getInstance("file");

            ComponentOBO componentOBO = obofactory.getComponentOBO();
            
            inputFileName = obofactory.getComponentOBO().inputFile();
            inputFileContents = obofactory.getComponentOBO().inputFileContents();
            outputReportName = obofactory.getComponentOBO().summaryReport();
            outputReportPDFName = obofactory.getComponentOBO().summaryReportPdf();
            
            List<OBOComponent> obocomponents = new ArrayList<OBOComponent>();
            obocomponents = componentOBO.listAll();
            
            obocomponentList = (ArrayList<OBOComponent>) obocomponents;
        }
    	catch (OBOException oboexception) {
    		oboexception.printStackTrace();
        }
    }

    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromOBOFile(String filename) throws IOException{
        Parser parser = new Parser(filename);
        obocomponentList = (ArrayList<OBOComponent>) parser.getComponents();
    }

    // Getters ------------------------------------------------------------------------------------
    public ArrayList<OBOComponent> getTermList() {
        return obocomponentList;
    }
    public String getInputFileContents() {
        return inputFileContents;
    }
    public String getInputFileName() {
        return inputFileName;
    }
    public String getOutputReportName() {
        return outputReportName;
    }
    public String getOutputReportPDFName() {
        return outputReportPDFName;
    }
}
