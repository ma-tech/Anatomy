/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        GenerateEditorReport.java
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
* Description:  Generate the Text Editor Report.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; February 2012; Completely rewrite to use standardised Data Access Object Layer
* 
*----------------------------------------------------------------------------------------------
*/
package oboroutines;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import anatomy.AnatomyInPerspective;

import utility.Wrapper;

import obomodel.OBOComponent;


public class GenerateEditorReport {
	// Attributes ----------------------------------------------------------------------------------
    private BufferedWriter reportFile;

    private StringWriter stringWriter = 
            new StringWriter();

    private PrintWriter printWriter =
            new PrintWriter( stringWriter );
    
    private ArrayList<OBOComponent> newTerms =
            new ArrayList<OBOComponent>();
    
    private ArrayList<OBOComponent> modifiedTerms =
            new ArrayList<OBOComponent>();
    
    private ArrayList<OBOComponent> deletedTerms =
            new ArrayList<OBOComponent>();
    
    private ArrayList<OBOComponent> unchangedTerms =
            new ArrayList<OBOComponent>();
    
    private ArrayList<OBOComponent> problemTerms =
            new ArrayList<OBOComponent>();

    private ArrayList<OBOComponent> proposedTerms =
            new ArrayList<OBOComponent>();

    private String outputFileName = "";
    private String inputFileName = "";
    
    private boolean isProcessed = false;

    private String requestMsgLevel;

    private AnatomyInPerspective anatomyinperspective;
    

    //----------------------------------------------------------------------------------------------
    // Constructor # 1 -----------------------------------------------------------------------------
	public GenerateEditorReport( String requestMsgLevel, 
			ValidateComponents validatecomponents, 
			String infile, 
			String outfile ) throws Exception{

        this.requestMsgLevel = requestMsgLevel;
        
        Wrapper.printMessage("generateeditorreport.constructor", "****", this.requestMsgLevel);
        	
        this.anatomyinperspective = null; 
        
        //sort terms from ValidateComponents class into categories
        //ArrayList<OBOComponent> changedTerms = validatecomponent.getChangesTermList();
        proposedTerms = (ArrayList<OBOComponent>) validatecomponents.getProposedTermList();
        
        problemTerms = (ArrayList<OBOComponent>) validatecomponents.getProblemTermList();
        
        sortChangedTerms( proposedTerms );        
        
    	try {
    		
            outputFileName = outfile;
            inputFileName = infile;
            
            //check filepath exists
            File file = new File(outputFileName);
            
            if (!file.isDirectory()) {
            	
                file = file.getParentFile();
            }
            
            if (!file.exists()) {
            	
                return;
            }

            //create text file
            reportFile = new BufferedWriter( new FileWriter( outputFileName ) );
            reportFile.newLine();
            
            //title
            reportFile.write("Editor Report for Import of OBO File: " +
            		inputFileName);
            reportFile.newLine();
            reportFile.newLine();
            
            //summary
            writeReportSummary(validatecomponents);
            
            //writing problem terms
            writeProblemTerms( problemTerms );
            reportFile.write( stringWriter.toString() );
            
            //writing new terms
            flushStringWriter();
            writeNewTerms( newTerms );
            reportFile.write( stringWriter.toString() );
            
            //writing modified terms
            flushStringWriter();
            writeModifiedTerms( modifiedTerms );
            reportFile.write( stringWriter.toString() ); 
            
            //writing deleted terms
            flushStringWriter();
            writeDeletedTerms( deletedTerms );
            reportFile.write( stringWriter.toString() );
            
            //appendix
            flushStringWriter();
            writeAppendix();
            reportFile.write( stringWriter.toString() );
            
            printWriter.close();
            stringWriter.close();
            reportFile.close();
            
            this.isProcessed = true;

        }
        catch (Exception e){
        	
            e.printStackTrace();
            isProcessed = false;
        }
    }

    // Constructor # 2 -----------------------------------------------------------------------------
	public GenerateEditorReport( String requestMsgLevel, 
			ValidateComponents validatecomponents, 
			String infile, 
			String outfile,
            AnatomyInPerspective anatomyinperspective ) throws Exception{

        this.requestMsgLevel = requestMsgLevel;
        
        Wrapper.printMessage("generateeditorreport.constructor", "****", this.requestMsgLevel);
        	
        this.anatomyinperspective = anatomyinperspective; 
        
        //sort terms from ValidateComponents class into categories
        //ArrayList<OBOComponent> changedTerms = validatecomponent.getChangesTermList();
        proposedTerms = (ArrayList<OBOComponent>) validatecomponents.getProposedTermList();
        
        problemTerms = (ArrayList<OBOComponent>) validatecomponents.getProblemTermList();
        
        sortChangedTerms( proposedTerms );        
        
    	try {
    		
            outputFileName = outfile;
            inputFileName = infile;
            
            //check filepath exists
            File file = new File(outputFileName);
            
            if (!file.isDirectory()) {
            	
                file = file.getParentFile();
            }
            
            if (!file.exists()) {
            	
                return;
            }

            //create text file
            reportFile = new BufferedWriter( new FileWriter( outputFileName ) );
            reportFile.newLine();
            
            //title
            reportFile.write("Editor Report for Import of OBO File: " +
            		inputFileName);
            reportFile.newLine();
            reportFile.newLine();
            
            //summary
            writeReportSummary(validatecomponents);
            
            //writing problem terms
            writeProblemTerms( problemTerms );
            reportFile.write( stringWriter.toString() );
            
            //writing new terms
            flushStringWriter();
            writeNewTerms( newTerms );
            reportFile.write( stringWriter.toString() );
            
            //writing modified terms
            flushStringWriter();
            writeModifiedTerms( modifiedTerms );
            reportFile.write( stringWriter.toString() ); 
            
            //writing deleted terms
            flushStringWriter();
            writeDeletedTerms( deletedTerms );
            reportFile.write( stringWriter.toString() );
            
            //appendix
            flushStringWriter();
            writeAppendix();
            reportFile.write( stringWriter.toString() );
            
            printWriter.close();
            stringWriter.close();
            reportFile.close();
            
            this.isProcessed = true;

        }
        catch (Exception e){
        	
            e.printStackTrace();
            isProcessed = false;
        }
    }

    //----------------------------------------------------------------------------------------------

    // Getters -------------------------------------------------------------------------------------
    public boolean getIsProcessed(){
        return isProcessed;
    }
    public String getOutputFileName(){
        return outputFileName;
    }
    public String getInputFileName(){
        return inputFileName;
    }

    // Private (Internal) Methods ------------------------------------------------------------------
    private void sortChangedTerms(ArrayList<OBOComponent> obocomponents) throws Exception{
        
        Wrapper.printMessage("generateeditorreport.sortChangedTerms", "****", this.requestMsgLevel);
        	
        for(OBOComponent obocomponent: obocomponents){
            
            if ( obocomponent.getStatusChange().equals("INSERT") ) {
            	
                newTerms.add(obocomponent);
            }
            else if ( obocomponent.getStatusChange().equals("UPDATE") ) {
            	
                modifiedTerms.add(obocomponent);
            }
            else if ( obocomponent.getStatusChange().equals("DELETE") ) {
            	
                deletedTerms.add(obocomponent);
            }
            else if ( obocomponent.getStatusChange().equals("NONE") ) {
            	
                unchangedTerms.add(obocomponent);
            }
        }
    }
    
    
    private void writeReportSummary(ValidateComponents validatecomponent) throws Exception{
    	
        Wrapper.printMessage("generateeditorreport.writeReportSummary", "****", this.requestMsgLevel);
        	
        try{
        	
           	printWriter.println();
            printWriter.println("REPORT UPDATE SUMMARY");
            printWriter.println();
            printWriter.println("=====================");
            printWriter.println();
            printWriter.println( "   Total Components from OBO File : " +
                    validatecomponent.getProposedTermList().size() );
            printWriter.println();
            printWriter.println( "A. Critical Components in file    : " +
                    problemTerms.size() );
            printWriter.println();
            printWriter.println( "B. New Components created         : " +
                    newTerms.size() );
            printWriter.println();
            printWriter.println( "C. Modified Components            : " +
                    modifiedTerms.size() );
            printWriter.println();
            printWriter.println( "D. Deleted Components             : " +
                    deletedTerms.size() );
            printWriter.println();
            printWriter.println( "   Unchanged Components           : " +
                    unchangedTerms.size() );
            printWriter.println();
            printWriter.println();
            printWriter.println("------------------------------------------" +
                    "--------------------------------------");
            printWriter.println();
            
        }
        catch(Exception e){
        	
            e.printStackTrace();
            isProcessed = false;
        }
    }
    
	private void writeProblemTerms(ArrayList<OBOComponent> obocomponents) throws Exception{
        
        Wrapper.printMessage("generateeditorreport.writeProblemTerms", "****", this.requestMsgLevel);
        	
        int counter = 0;
        int commentCounter = 0;
        Set<String> comments;
        int pathCounter = 0;
        Vector<DefaultMutableTreeNode[]> paths;
        
        try{
        	
            printWriter.println();
            printWriter.println("A. CRITICAL COMPONENTS: REQUIRE REVISION");
            printWriter.println();
            printWriter.println("   =====================================");
            printWriter.println();
            printWriter.println("  Total Critical Components: " + 
                    obocomponents.size());
            printWriter.println("  -----------------------------");
            printWriter.println();
            
            if ( !obocomponents.isEmpty() ){
            	
                for(OBOComponent obocomponent: obocomponents){
                
                	/* 
                	 * if we haven't been given an AnatomyInPerspective Object then we print ALL the terms
                	 */
                	if ( anatomyinperspective == null ) {
                		
                    	printWriter.println( "  " + counter + ". " +
                                obocomponent.getID() + " - " + obocomponent.getName() );

                	}
                	/* 
                	 * if we HAVE been given an AnatomyInPerspective Object then we check to see if this term 
                	 *  in the Perspective provided
                	 */
                	else {
                		
                		/*
                		 * Is this term in the provided Perspective?
                		 * Yes - Print it
                		 */
                        String [] parts1 = obocomponent.getName().split("\\,");
                        String componentName = parts1[0];

                    	if ( anatomyinperspective.containsPublicId(obocomponent.getID()) ||
                         anatomyinperspective.containsComponentName(obocomponent.getName()) ) {

                        	printWriter.println( "  " + counter + ". " +
                                    obocomponent.getID() + " - " + obocomponent.getName() );
                    		
                    	}
                    	/*
                    	 * No - Ignore the term
                    	 */
                	}
                	counter++;
                    
                }

                printWriter.println();
                printWriter.println("  DETAILS");
                printWriter.println("  -------");
                
                counter = 0;
                
                for(OBOComponent obocomponent: obocomponents){
                	
                    counter++;
                    
                	/* 
                	 * if we haven't been given an AnatomyInPerspective Object then we print ALL the terms
                	 */
                	if ( anatomyinperspective == null ) {
                		
                        printWriter.println();
                        printWriter.println();
                        printWriter.println( "   Problem OBOComponent " +
                                counter );
                        printWriter.println( "    ID        : " +
                                obocomponent.getID() );
                        printWriter.println( "    Name      : " +
                                obocomponent.getName() );
                        printWriter.println( "    Starts At : " +
                                obocomponent.getStart() );
                        printWriter.println( "    Ends At   : " +
                                obocomponent.getEnd() );
                        printWriter.println( "    Parents   : " + 
                                obocomponent.getChildOfs() );
                        printWriter.println( "    Synonyms  : " +
                                obocomponent.getSynonyms() );
                        printWriter.println( "    Alternate Paths     : " );
                        
                        paths = (Vector<DefaultMutableTreeNode[]>) obocomponent.getShortenedPaths();
                        pathCounter = 0;
                        
                        for( DefaultMutableTreeNode[] path : paths ){
                        
                        	pathCounter++;
                            printWriter.println( "     " + pathCounter +
                                    ".  " + new TreePath(path) );
                        }
                        
                        printWriter.println( "    Change Check Status : " +
                                obocomponent.getStatusChange() );
                        printWriter.println( "    Rules Check Status  : " +
                                obocomponent.getStatusRule() );
                        printWriter.println( "    Comments: ");
                    
                        comments = obocomponent.getCheckComments();
                        commentCounter = 0;
                        
                        for( String s: comments ){
                        
                        	commentCounter++;
                            printWriter.println( "     " + commentCounter +
                                    ".  " + s );
                        }

                	}
                	/* 
                	 * if we HAVE been given an AnatomyInPerspective Object then we check to see if this term 
                	 *  in the Perspective provided
                	 */
                	else {
                		
                		/*
                		 * Is this term in the provided Perspective?
                		 * Yes - Print it
                		 */
                        String [] parts1 = obocomponent.getName().split("\\,");
                        String componentName = parts1[0];

                    	if ( anatomyinperspective.containsPublicId(obocomponent.getID()) ||
                         anatomyinperspective.containsComponentName(obocomponent.getName()) ) {

                            printWriter.println();
                            printWriter.println();
                            printWriter.println( "   Problem OBOComponent " +
                                    counter );
                            printWriter.println( "    ID        : " +
                                    obocomponent.getID() );
                            printWriter.println( "    Name      : " +
                                    obocomponent.getName() );
                            printWriter.println( "    Starts At : " +
                                    obocomponent.getStart() );
                            printWriter.println( "    Ends At   : " +
                                    obocomponent.getEnd() );
                            printWriter.println( "    Parents   : " + 
                                    obocomponent.getChildOfs() );
                            printWriter.println( "    Synonyms  : " +
                                    obocomponent.getSynonyms() );
                            printWriter.println( "    Alternate Paths     : " );
                            
                            paths = (Vector<DefaultMutableTreeNode[]>) obocomponent.getShortenedPaths();
                            pathCounter = 0;
                            
                            for( DefaultMutableTreeNode[] path : paths ){
                            
                            	pathCounter++;
                                printWriter.println( "     " + pathCounter +
                                        ".  " + new TreePath(path) );
                            }
                            
                            printWriter.println( "    Change Check Status : " +
                                    obocomponent.getStatusChange() );
                            printWriter.println( "    Rules Check Status  : " +
                                    obocomponent.getStatusRule() );
                            printWriter.println( "    Comments: ");
                        
                            comments = obocomponent.getCheckComments();
                            commentCounter = 0;
                            
                            for( String s: comments ){
                            
                            	commentCounter++;
                                printWriter.println( "     " + commentCounter +
                                        ".  " + s );
                            }
                    		
                    	}
                    	/*
                    	 * No - Ignore the term
                    	 */
                	}
                }
            }
        }
        catch(Exception e){
        	
            e.printStackTrace();
            isProcessed = false;
        }
    }

	private void writeNewTerms(ArrayList<OBOComponent> obocomponents) throws Exception{
        
        Wrapper.printMessage("generateeditorreport.writeNewTerms", "****", this.requestMsgLevel);
        	
        int counter = 0;
        int commentCounter = 0;
        Set<String> comments;
        
        try{
        	
            printWriter.println();
            printWriter.println("B. NEW COMPONENTS");
            printWriter.println();
            printWriter.println("   ==============");
            printWriter.println();
            printWriter.println("  Total New Components: " +
                    obocomponents.size());
            printWriter.println("  -------------------------------");
            printWriter.println();
       
            for(OBOComponent obocomponent: obocomponents){
            	
                counter++;

            	/* 
            	 * if we haven't been given an AnatomyInPerspective Object then we print ALL the terms
            	 */
            	if ( anatomyinperspective == null ) {
            		
            		printWriter.println( "  " + counter + ". " +
                            obocomponent.getID() + " - " + obocomponent.getName() );
                    //obocomponent.getNewID() + " - " + obocomponent.getName() );

            	}
            	/* 
            	 * if we HAVE been given an AnatomyInPerspective Object then we check to see if this term 
            	 *  in the Perspective provided
            	 */
            	else {
            		
            		/*
            		 * Is this term in the provided Perspective?
            		 * Yes - Print it
            		 */
                    String [] parts1 = obocomponent.getName().split("\\,");
                    String componentName = parts1[0];

                	if ( anatomyinperspective.containsPublicId(obocomponent.getID()) ||
                     anatomyinperspective.containsComponentName(obocomponent.getName()) ) {

                		printWriter.println( "  " + counter + ". " +
                                obocomponent.getID() + " - " + obocomponent.getName() );
                        //obocomponent.getNewID() + " - " + obocomponent.getName() );
                		
                	}
                	/*
                	 * No - Ignore the term
                	 */
            	}
            }
            
            if ( !obocomponents.isEmpty() ){
                
            	printWriter.println();
                printWriter.println("  DETAILS");
                printWriter.println("  -------");
                
                counter = 0;
            
                for(OBOComponent obocomponent: obocomponents){
                	
                    counter++;
                    
                	/* 
                	 * if we haven't been given an AnatomyInPerspective Object then we print ALL the terms
                	 */
                	if ( anatomyinperspective == null ) {
                		
                        printWriter.println();
                        printWriter.println( "   New OBOComponent " +
                                counter );
                        printWriter.println( "    ID        : " +
                                obocomponent.getID() );
                        //obocomponent.getNewID() );
                        printWriter.println( "    Name      : " +
                                obocomponent.getName() );
                        printWriter.println( "    Starts At : " +
                                obocomponent.getStart() );
                        printWriter.println( "    Ends At   : " +
                                obocomponent.getEnd() );
                        printWriter.println( "    Parents   : " + 
                                obocomponent.getChildOfs() );
                        printWriter.println( "    Synonyms  : " +
                                obocomponent.getSynonyms() );
                        printWriter.println( "    Change Check Status : " +
                                obocomponent.getStatusChange() );
                        printWriter.println( "    Rules Check Status  : " +
                                obocomponent.getStatusRule() );
                                    
                        comments = obocomponent.getNewComments();
                        commentCounter = 0;
                        
                        for( String s: comments ){
                        
                        	commentCounter++;
                            
                        	printWriter.println( "    Comments  : " +
                                    commentCounter +  ".  " + s );
                        }

                	}
                	/* 
                	 * if we HAVE been given an AnatomyInPerspective Object then we check to see if this term 
                	 *  in the Perspective provided
                	 */
                	else {
                		
                		/*
                		 * Is this term in the provided Perspective?
                		 * Yes - Print it
                		 */
                        String [] parts1 = obocomponent.getName().split("\\,");
                        String componentName = parts1[0];

                    	if ( anatomyinperspective.containsPublicId(obocomponent.getID()) ||
                         anatomyinperspective.containsComponentName(obocomponent.getName()) ) {

                            printWriter.println();
                            printWriter.println( "   New OBOComponent " +
                                    counter );
                            printWriter.println( "    ID        : " +
                                    obocomponent.getID() );
                            //obocomponent.getNewID() );
                            printWriter.println( "    Name      : " +
                                    obocomponent.getName() );
                            printWriter.println( "    Starts At : " +
                                    obocomponent.getStart() );
                            printWriter.println( "    Ends At   : " +
                                    obocomponent.getEnd() );
                            printWriter.println( "    Parents   : " + 
                                    obocomponent.getChildOfs() );
                            printWriter.println( "    Synonyms  : " +
                                    obocomponent.getSynonyms() );
                            printWriter.println( "    Change Check Status : " +
                                    obocomponent.getStatusChange() );
                            printWriter.println( "    Rules Check Status  : " +
                                    obocomponent.getStatusRule() );
                                        
                            comments = obocomponent.getNewComments();
                            commentCounter = 0;
                            
                            for( String s: comments ){
                            
                            	commentCounter++;
                                
                            	printWriter.println( "    Comments  : " +
                                        commentCounter +  ".  " + s );
                            }
                    		
                    	}
                    	/*
                    	 * No - Ignore the term
                    	 */
                	}
                }
            }
        }
        catch(Exception e){
        	
            e.printStackTrace();
            isProcessed = false;
        }
    }

	private void writeModifiedTerms(ArrayList<OBOComponent> obocomponents) throws Exception{
        
        Wrapper.printMessage("generateeditorreport.writeModifiedTerms", "****", this.requestMsgLevel);
        	
        int counter = 0;
        int commentCounter = 0;
        Set<String> comments;
        
        try{
        	
            printWriter.println();
            printWriter.println();
            printWriter.println("C. MODIFIED COMPONENTS");
            printWriter.println();
            printWriter.println("   ===================");
            printWriter.println();
            printWriter.println("  Total Modified Components: " + 
                    obocomponents.size());
            printWriter.println("  -------------------------------");
            printWriter.println();
            
            if (!obocomponents.isEmpty()){
            
                for(OBOComponent obocomponent: obocomponents){
                	
                    counter++;
                    
                	/* 
                	 * if we haven't been given an AnatomyInPerspective Object then we print ALL the terms
                	 */
                	if ( anatomyinperspective == null ) {
                		
                        printWriter.println( "  " + counter + ". " +
                                obocomponent.getID() + " - " + obocomponent.getName() );
                        comments = obocomponent.getDifferenceComments();
                        
                        for( String s: comments ){
                        
                        	printWriter.println( "       - " + s );
                        	
                        }
                        
                        printWriter.println();

                	}
                	/* 
                	 * if we HAVE been given an AnatomyInPerspective Object then we check to see if this term 
                	 *  in the Perspective provided
                	 */
                	else {
                		
                		/*
                		 * Is this term in the provided Perspective?
                		 * Yes - Print it
                		 */
                        String [] parts1 = obocomponent.getName().split("\\,");
                        String componentName = parts1[0];

                    	if ( anatomyinperspective.containsPublicId(obocomponent.getID()) ||
                         anatomyinperspective.containsComponentName(obocomponent.getName()) ) {

                            printWriter.println( "  " + counter + ". " +
                                    obocomponent.getID() + " - " + obocomponent.getName() );
                            comments = obocomponent.getDifferenceComments();
                            
                            for( String s: comments ){
                            
                            	printWriter.println( "       - " + s );
                            	
                            }
                            
                            printWriter.println();
                    		
                    	}
                    	/*
                    	 * No - Ignore the term
                    	 */
                	}
                }
            
                printWriter.println();
                printWriter.println("  DETAILS");
                printWriter.println("  -------");
                
                counter = 0;

                for(OBOComponent obocomponent: obocomponents){
                
                	counter++;
                    
                	/* 
                	 * if we haven't been given an AnatomyInPerspective Object then we print ALL the terms
                	 */
                	if ( anatomyinperspective == null ) {
                		
                    	printWriter.println();
                        printWriter.println( "   Modified OBOComponent " +
                                counter );
                        printWriter.println( "    ID        : " +
                                obocomponent.getID() );
                        printWriter.println( "    Name      : " +
                                obocomponent.getName() );
                        printWriter.println( "    Starts At : " +
                                obocomponent.getStart() );
                        printWriter.println( "    Ends At   : " +
                                obocomponent.getEnd() );
                        printWriter.println( "    Parents   : " + 
                                obocomponent.getChildOfs() );
                        printWriter.println( "    Synonyms  : " +
                                obocomponent.getSynonyms() );
                        printWriter.println( "    Change Check Status : " +
                                obocomponent.getStatusChange() );
                        printWriter.println( "    Rules Check Status  : " +
                                obocomponent.getStatusRule() );
                        printWriter.println( "    Changed Property    : ");
                                    
                        comments = obocomponent.getDifferenceComments();
                        commentCounter = 0;
                        
                        for( String s: comments ){
                        
                        	commentCounter++;
                            
                        	printWriter.println( "     " + commentCounter +
                                    ".  " + s );
                        }

                	}
                	/* 
                	 * if we HAVE been given an AnatomyInPerspective Object then we check to see if this term 
                	 *  in the Perspective provided
                	 */
                	else {
                		
                		/*
                		 * Is this term in the provided Perspective?
                		 * Yes - Print it
                		 */
                        String [] parts1 = obocomponent.getName().split("\\,");
                        String componentName = parts1[0];

                    	if ( anatomyinperspective.containsPublicId(obocomponent.getID()) ||
                         anatomyinperspective.containsComponentName(obocomponent.getName()) ) {

                        	printWriter.println();
                            printWriter.println( "   Modified OBOComponent " +
                                    counter );
                            printWriter.println( "    ID        : " +
                                    obocomponent.getID() );
                            printWriter.println( "    Name      : " +
                                    obocomponent.getName() );
                            printWriter.println( "    Starts At : " +
                                    obocomponent.getStart() );
                            printWriter.println( "    Ends At   : " +
                                    obocomponent.getEnd() );
                            printWriter.println( "    Parents   : " + 
                                    obocomponent.getChildOfs() );
                            printWriter.println( "    Synonyms  : " +
                                    obocomponent.getSynonyms() );
                            printWriter.println( "    Change Check Status : " +
                                    obocomponent.getStatusChange() );
                            printWriter.println( "    Rules Check Status  : " +
                                    obocomponent.getStatusRule() );
                            printWriter.println( "    Changed Property    : ");
                                        
                            comments = obocomponent.getDifferenceComments();
                            commentCounter = 0;
                            
                            for( String s: comments ){
                            
                            	commentCounter++;
                                
                            	printWriter.println( "     " + commentCounter +
                                        ".  " + s );
                            }
                    		
                    	}
                    	/*
                    	 * No - Ignore the term
                    	 */
                	}
                }
            }
        }
        catch(Exception e){
        	
            e.printStackTrace();
            isProcessed = false;
        }
    }


    private void writeDeletedTerms(ArrayList<OBOComponent> obocomponents) throws Exception{
        
        Wrapper.printMessage("generateeditorreport.writeDeletedTerms", "****", this.requestMsgLevel);
        	
        int counter = 0;
        int commentCounter = 0;
        Set<String> comments;
        
        try{
        	
            printWriter.println();
            printWriter.println("D. DELETE COMPONENTS");
            printWriter.println();
            printWriter.println("   ==================");
            printWriter.println();
            printWriter.println("  Total Deleted Components: " + 
                    obocomponents.size());
            printWriter.println("  -----------------------------");
            printWriter.println();
            
            if (!obocomponents.isEmpty()){
            
                for(OBOComponent obocomponent: obocomponents){
                	
                    counter++;
                    
                	/* 
                	 * if we haven't been given an AnatomyInPerspective Object then we print ALL the terms
                	 */
                	if ( anatomyinperspective == null ) {
                		
                        printWriter.println( "  " + counter + ". " +
                                obocomponent.getID() + " - " + obocomponent.getName() );

                	}
                	/* 
                	 * if we HAVE been given an AnatomyInPerspective Object then we check to see if this term 
                	 *  in the Perspective provided
                	 */
                	else {
                		
                		/*
                		 * Is this term in the provided Perspective?
                		 * Yes - Print it
                		 */
                        String [] parts1 = obocomponent.getName().split("\\,");
                        String componentName = parts1[0];

                    	if ( anatomyinperspective.containsPublicId(obocomponent.getID()) ||
                         anatomyinperspective.containsComponentName(obocomponent.getName()) ) {

                            printWriter.println( "  " + counter + ". " +
                                    obocomponent.getID() + " - " + obocomponent.getName() );
                    		
                    	}
                    	/*
                    	 * No - Ignore the term
                    	 */
                	}
                }
            
                printWriter.println();
                printWriter.println("  DETAILS");
                printWriter.println("  -------");
                
                counter = 0;
                
                for(OBOComponent obocomponent: obocomponents){

                	counter++;
                    
                	/* 
                	 * if we haven't been given an AnatomyInPerspective Object then we print ALL the terms
                	 */
                	if ( anatomyinperspective == null ) {
                		
                    	printWriter.println();
                        printWriter.println( "   Deleted OBOComponent " +
                                counter );
                        printWriter.println( "    ID        : " +
                                obocomponent.getID() );
                        printWriter.println( "    Name      : " +
                                obocomponent.getName() );
                        printWriter.println( "    Starts At : " +
                                obocomponent.getStart() );
                        printWriter.println( "    Ends At   : " +
                                obocomponent.getEnd() );
                        printWriter.println( "    Parents   : " + 
                                obocomponent.getChildOfs() );
                        printWriter.println( "    Synonyms  : " +
                                obocomponent.getSynonyms() );
                        printWriter.println( "    Change Check Status : " +
                                obocomponent.getStatusChange() );
                        printWriter.println( "    Rules Check Status  : " +
                                obocomponent.getStatusRule() );
                        printWriter.println( "    Comments  : ");
                                    
                        comments = obocomponent.getCheckComments();
                        commentCounter = 0;
                        
                        for( String s: comments ){
                        
                        	commentCounter++;
                            
                        	printWriter.println( "     " + commentCounter +
                                    ".  " + s );
                        }

                	}
                	/* 
                	 * if we HAVE been given an AnatomyInPerspective Object then we check to see if this term 
                	 *  in the Perspective provided
                	 */
                	else {
                		
                		/*
                		 * Is this term in the provided Perspective?
                		 * Yes - Print it
                		 */
                        String [] parts1 = obocomponent.getName().split("\\,");
                        String componentName = parts1[0];

                    	if ( anatomyinperspective.containsPublicId(obocomponent.getID()) ||
                         anatomyinperspective.containsComponentName(obocomponent.getName()) ) {

                        	printWriter.println();
                            printWriter.println( "   Deleted OBOComponent " +
                                    counter );
                            printWriter.println( "    ID        : " +
                                    obocomponent.getID() );
                            printWriter.println( "    Name      : " +
                                    obocomponent.getName() );
                            printWriter.println( "    Starts At : " +
                                    obocomponent.getStart() );
                            printWriter.println( "    Ends At   : " +
                                    obocomponent.getEnd() );
                            printWriter.println( "    Parents   : " + 
                                    obocomponent.getChildOfs() );
                            printWriter.println( "    Synonyms  : " +
                                    obocomponent.getSynonyms() );
                            printWriter.println( "    Change Check Status : " +
                                    obocomponent.getStatusChange() );
                            printWriter.println( "    Rules Check Status  : " +
                                    obocomponent.getStatusRule() );
                            printWriter.println( "    Comments  : ");
                                        
                            comments = obocomponent.getCheckComments();
                            commentCounter = 0;
                            
                            for( String s: comments ){
                            
                            	commentCounter++;
                                
                            	printWriter.println( "     " + commentCounter +
                                        ".  " + s );
                            }
                    		
                    	}
                    	/*
                    	 * No - Ignore the term
                    	 */
                	}
                }
            }
        }
        catch(Exception e){
        	
            e.printStackTrace();
            isProcessed = false;
        }
    }
    
    
    private void flushStringWriter() throws Exception{

        Wrapper.printMessage("generateeditorreport.flushStringWriter", "****", this.requestMsgLevel);
        	
        stringWriter = new StringWriter();
        printWriter = new PrintWriter( stringWriter ); 
    }
    
    
    private void writeAppendix() throws Exception{  
        
        Wrapper.printMessage("generateeditorreport.writeAppendix", "****", this.requestMsgLevel);
        	
        printWriter.println();
        printWriter.println();
        printWriter.println();
        printWriter.println();
        printWriter.println("----------------------------------------");
        printWriter.println("----------------------------------------");
        printWriter.println("----------------Appendix----------------");
        printWriter.println("----------------------------------------");
        printWriter.println();
        
        printWriter.println("CRITICAL terms = Terms that require " +
                "revision.");
        printWriter.println("NEW terms = Terms that did not exist in " +
                "the original file/database.");
        printWriter.println("MODIFIED terms = Terms that have a " +
                "changed property.");
        printWriter.println("DELETE terms = Terms that exist in the " +
                "original file/database but are no longer in the current " +
                "file.");
    }
}
