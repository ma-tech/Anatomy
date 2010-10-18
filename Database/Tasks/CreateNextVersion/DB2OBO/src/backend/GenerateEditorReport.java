/*
################################################################################
# Project:      Anatomy
#
# Title:        GenerateEditorReport.java
#
# Date:         2008
#
# Author:       MeiSze Lam and Attila Gyenesi
#
# Copyright:    2009 Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; September 2010; Tidy up and Document
#
################################################################################
*/

package backend;

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

public class GenerateEditorReport {
    
    private BufferedWriter reportFile;

    private StringWriter stringWriter = 
            new StringWriter();

    private PrintWriter printWriter =
            new PrintWriter( stringWriter );
    
    private ArrayList<Component> newTerms =
            new ArrayList<Component>();
    
    private ArrayList<Component> modifiedTerms =
            new ArrayList<Component>();
    
    private ArrayList<Component> deletedTerms =
            new ArrayList<Component>();
    
    private ArrayList<Component> unchangedTerms =
            new ArrayList<Component>();
    
    private ArrayList<Component> problemTerms =
            new ArrayList<Component>();

    private boolean isProcessed = false;

    private String species = "";

    public GenerateEditorReport( CheckComponents checkie, 
            String fileName,
            String importedFileName,
            String species){

        this.species = species;
        
        //sort terms from CheckComponents class into categories
        //ArrayList<Component> changedTerms = checkie.getChangesTermList();
        ArrayList<Component> proposedTerms = checkie.getProposedTermList();
        problemTerms = checkie.getProblemTermList();
        this.sortChangedTerms( proposedTerms );        
    
        try {
            //check filepath exists
            File file = new File(fileName);

            if (!file.isDirectory()) {
                file = file.getParentFile();
            }
                
            if (!file.exists()) {
                return;
            }

            //create text file
            this.reportFile = new BufferedWriter( new FileWriter( fileName ) );
            this.reportFile.newLine();
            
            //title
            this.reportFile.write("Editor Report for Import of OBO File: " +
                    importedFileName);
            this.reportFile.newLine();
            this.reportFile.newLine();
            
            //summary
            writeReportSummary(checkie);
            
            //writing problem terms
            writeProblemTerms( problemTerms );
            this.reportFile.write( this.stringWriter.toString() );
            
            //writing new terms
            //can't find a way to flush the stringWriter after every method
            this.flushStringWriter(); 
            writeNewTerms( newTerms );
            this.reportFile.write( this.stringWriter.toString() );
            
            //writing modified terms
            //can't find a way to flush the stringWriter after every method
            this.flushStringWriter(); 
            writeModifiedTerms( modifiedTerms );
            this.reportFile.write( this.stringWriter.toString() ); 
            
            //writing deleted terms
            this.flushStringWriter();
            writeDeletedTerms( deletedTerms );
            this.reportFile.write( this.stringWriter.toString() );
            
            //appendix
            this.flushStringWriter();
            writeAppendix();
            this.reportFile.write( this.stringWriter.toString() );
            
            this.printWriter.close();
            this.stringWriter.close();
            this.reportFile.close();
            this.isProcessed = true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void sortChangedTerms(ArrayList<Component> allTerms){
        
        for(Component compie: allTerms){
            
            if ( compie.getStrChangeStatus().equals("NEW") ) {
                newTerms.add(compie);
            }
            else if ( compie.getStrChangeStatus().equals("CHANGED") ) {
                modifiedTerms.add(compie);
            }
            else if ( compie.getStrChangeStatus().equals("DELETED") ) {
                deletedTerms.add(compie);
            }
            else if ( compie.getStrChangeStatus().equals("UNCHANGED") ) {
                unchangedTerms.add(compie);
            }
        }
    }
    
    
    private void writeReportSummary(CheckComponents checkie){
        this.printWriter.println();
        this.printWriter.println("REPORT UPDATE SUMMARY");
        this.printWriter.println();
        this.printWriter.println("=====================");
        this.printWriter.println();
        this.printWriter.println( "Total Components from OBO File : " +
                checkie.getProposedTermList().size() );
        this.printWriter.println();
        this.printWriter.println( "New Components created         : " +
                newTerms.size() );
        this.printWriter.println();
        this.printWriter.println( "Modified Components            : " +
                modifiedTerms.size() );
        this.printWriter.println();
        this.printWriter.println( "Deleted Components             : " +
                deletedTerms.size() );
        this.printWriter.println();
        this.printWriter.println( "Unchanged Components           : " +
                unchangedTerms.size() );
        this.printWriter.println();
        this.printWriter.println( "Critical Components in file    : " +
                problemTerms.size() );
        this.printWriter.println();
        this.printWriter.println();
        this.printWriter.println("------------------------------------------" +
                "--------------------------------------");
        this.printWriter.println();
    }
    
    
    private void writeProblemTerms(ArrayList<Component> termList){
        
        int counter = 0;
        int commentCounter = 0;
        Set<String> comments;
        int pathCounter = 0;
        Vector<DefaultMutableTreeNode[]> paths;
        
        try{
            this.printWriter.println();
            this.printWriter.println("CRITICAL COMPONENTS: REQUIRE REVISION");
            this.printWriter.println();
            this.printWriter.println("=====================================");
            this.printWriter.println();
            this.printWriter.println("  Total Critical Components: " +
                    termList.size());
            this.printWriter.println("  -----------------------------");
            this.printWriter.println();
            
            if ( !termList.isEmpty() ){
                for(Component compie: termList){
                    counter++;
                    this.printWriter.println( "  " + counter + ". " +
                            compie.getID() + " - " + compie.getName() );
                }

                this.printWriter.println();
                this.printWriter.println("  DETAILS");
                this.printWriter.println("  -------");
                counter = 0;
                
                System.out.println(termList.size());
                for(Component compie: termList){
                    counter++;
                    this.printWriter.println();
                    this.printWriter.println( "   Problem Component " +
                            counter );
                    this.printWriter.println( "    ID        : " +
                            compie.getID() );
                    this.printWriter.println( "    Name      : " +
                            compie.getName() );
                    this.printWriter.println( "    Starts At : " +
                            compie.getStartsAtStr(this.species) );
                    this.printWriter.println( "    Ends At   : " +
                            compie.getEndsAtStr(this.species) );
                    this.printWriter.println( "    Parents   : " +
                            compie.getPartOf() );
                    this.printWriter.println( "    GParents  : " +
                            compie.getGroupPartOf() );
                    this.printWriter.println( "    Is Group  : " +
                            !compie.getIsPrimary() );
                    this.printWriter.println( "    Synonyms  : " +
                            compie.getSynonym() );
                    this.printWriter.println( "    Primary Path        : " +
                            new TreePath(compie.getShortenedPrimaryPath()) );
                    this.printWriter.println( "    Alternate Paths     : " );
                    paths = compie.getShortenedPaths();
                    pathCounter = 0;
                    for( DefaultMutableTreeNode[] path : paths ){
                        pathCounter++;
                        this.printWriter.println( "     " + pathCounter +
                                ".  " + new TreePath(path) );
                    }
                    this.printWriter.println( "    Change Check Status : " +
                            compie.getStrChangeStatus() );
                    this.printWriter.println( "    Rules Check Status  : " +
                            compie.getStrRuleStatus() );
                    this.printWriter.println( "    Comments: ");
                
                    comments = compie.getCheckComments();
                    commentCounter = 0;
                    for( String s: comments ){
                        commentCounter++;
                        this.printWriter.println( "     " + commentCounter +
                                ".  " + s );
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void writeNewTerms(ArrayList<Component> termList){
        
        int counter = 0;
        int commentCounter = 0;
        Set<String> comments;
        
        try{
            this.printWriter.println();
            this.printWriter.println("NEW COMPONENTS");
            this.printWriter.println();
            this.printWriter.println("==============");
            this.printWriter.println();
            this.printWriter.println("  Total New Components: " +
                    termList.size());
            this.printWriter.println("  -------------------------------");
            this.printWriter.println();
       
            for(Component compie: termList){
                counter++;
                this.printWriter.println( "  " + counter + ". " +
                        compie.getNewID() + " - " + compie.getName() );
            }
            
            if ( !termList.isEmpty() ){
                this.printWriter.println();
                this.printWriter.println("  DETAILS");
                this.printWriter.println("  -------");
                counter = 0;
            
                for(Component compie: termList){
                    counter++;
                    this.printWriter.println();
                    this.printWriter.println( "   New Component " +
                            counter );
                    this.printWriter.println( "    ID        : " +
                            compie.getNewID() );
                    this.printWriter.println( "    Name      : " +
                            compie.getName() );
                    this.printWriter.println( "    Starts At : " +
                            compie.getStartsAtStr(this.species) );
                    this.printWriter.println( "    Ends At   : " +
                            compie.getEndsAtStr(this.species) );
                    this.printWriter.println( "    Parents   : " +
                            compie.getPartOf() );
                    this.printWriter.println( "    GParents  : " +
                            compie.getGroupPartOf() );
                    this.printWriter.println( "    Is Group  : " +
                            !compie.getIsPrimary() );
                    this.printWriter.println( "    Synonyms  : " +
                            compie.getSynonym() );
                    this.printWriter.println( "    Change Check Status : " +
                            compie.getStrChangeStatus() );
                    this.printWriter.println( "    Rules Check Status  : " +
                            compie.getStrRuleStatus() );
                                
                    comments = compie.getNewComments();
                    commentCounter = 0;
                    for( String s: comments ){
                        commentCounter++;
                        this.printWriter.println( "    Comments  : " +
                                commentCounter +  ".  " + s );
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void writeModifiedTerms(ArrayList<Component> termList){
        
        int counter = 0;
        int commentCounter = 0;
        Set<String> comments;
        
        try{
            this.printWriter.println();
            this.printWriter.println();
            this.printWriter.println("MODIFIED COMPONENTS");
            this.printWriter.println();
            this.printWriter.println("===================");
            this.printWriter.println();
            this.printWriter.println("  Total Modified Components: " +
                    termList.size());
            this.printWriter.println("  -------------------------------");
            this.printWriter.println();
            
            if (!termList.isEmpty()){
            
                for(Component compie: termList){
                    counter++;
                    this.printWriter.println( "  " + counter + ". " +
                            compie.getID() + " - " + compie.getName() );
                    comments = compie.getDifferenceComments();
                    for( String s: comments ){
                        this.printWriter.println( "       - " + s );
                    }
                    this.printWriter.println();
                }
            
                this.printWriter.println();
                this.printWriter.println("  DETAILS");
                this.printWriter.println("  -------");
                counter = 0;

                for(Component compie: termList){
                    counter++;
                    this.printWriter.println();
                    this.printWriter.println( "   Modified Component " +
                            counter );
                    this.printWriter.println( "    ID        : " +
                            compie.getID() );
                    this.printWriter.println( "    Name      : " +
                            compie.getName() );
                    this.printWriter.println( "    Starts At : " +
                            compie.getStartsAtStr(this.species) );
                    this.printWriter.println( "    Ends At   : " +
                            compie.getEndsAtStr(this.species) );
                    this.printWriter.println( "    Parents   : " +
                            compie.getPartOf() );
                    this.printWriter.println( "    GParents  : " +
                            compie.getGroupPartOf() );
                    this.printWriter.println( "    Is Group  : " +
                            !compie.getIsPrimary() );
                    this.printWriter.println( "    Synonyms  : " +
                            compie.getSynonym() );
                    this.printWriter.println( "    Change Check Status : " +
                            compie.getStrChangeStatus() );
                    this.printWriter.println( "    Rules Check Status  : " +
                            compie.getStrRuleStatus() );
                    this.printWriter.println( "    Changed Property    : ");
                                
                    comments = compie.getDifferenceComments();
                    commentCounter = 0;
                    for( String s: comments ){
                        commentCounter++;
                        this.printWriter.println( "     " + commentCounter +
                                ".  " + s );
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void writeDeletedTerms(ArrayList<Component> termList){
        
        int counter = 0;
        int commentCounter = 0;
        Set<String> comments;
        
        try{
            this.printWriter.println();
            this.printWriter.println("DELETED COMPONENTS");
            this.printWriter.println();
            this.printWriter.println("==================");
            this.printWriter.println();
            this.printWriter.println("  Total Deleted Components: " +
                    termList.size());
            this.printWriter.println("  -----------------------------");
            this.printWriter.println();
            
            if (!termList.isEmpty()){
            
                for(Component compie: termList){
                    counter++;
                    this.printWriter.println( "  " + counter + ". " +
                            compie.getID() + " - " + compie.getName() );
                }
            
                this.printWriter.println();
                this.printWriter.println("  DETAILS");
                this.printWriter.println("  -------");
                counter = 0;

                for(Component compie: termList){
                    counter++;
                    this.printWriter.println();
                    this.printWriter.println( "   Deleted Component " +
                            counter );
                    this.printWriter.println( "    ID        : " +
                            compie.getID() );
                    this.printWriter.println( "    Name      : " +
                            compie.getName() );
                    this.printWriter.println( "    Starts At : " +
                            compie.getStartsAtStr(this.species) );
                    this.printWriter.println( "    Ends At   : " +
                            compie.getEndsAtStr(this.species) );
                    this.printWriter.println( "    Parents   : " +
                            compie.getPartOf() );
                    this.printWriter.println( "    GParents  : " +
                            compie.getGroupPartOf() );
                    this.printWriter.println( "    Is Group  : " +
                            !compie.getIsPrimary() );
                    this.printWriter.println( "    Synonyms  : " +
                            compie.getSynonym() );
                    this.printWriter.println( "    Change Check Status : " +
                            compie.getStrChangeStatus() );
                    this.printWriter.println( "    Rules Check Status  : " +
                            compie.getStrRuleStatus() );
                    this.printWriter.println( "    Comments  : ");
                                
                    comments = compie.getCheckComments();
                    commentCounter = 0;
                    for( String s: comments ){
                        commentCounter++;
                        this.printWriter.println( "     " + commentCounter +
                                ".  " + s );
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    private void flushStringWriter(){
        stringWriter = new StringWriter();
        printWriter = new PrintWriter( stringWriter ); 
    }
    
    
    private void writeAppendix(){  
        
        this.printWriter.println();
        this.printWriter.println();
        this.printWriter.println();
        this.printWriter.println();
        this.printWriter.println("----------------------------------------");
        this.printWriter.println("----------------------------------------");
        this.printWriter.println("----------------Appendix----------------");
        this.printWriter.println("----------------------------------------");
        this.printWriter.println();
        
        this.printWriter.println("CRITICAL terms = Terms that require " +
                "revision.");
        this.printWriter.println("NEW terms = Terms that did not exist in " +
                "the original file/database.");
        this.printWriter.println("MODIFIED terms = Terms that have a " +
                "changed property.");
        this.printWriter.println("DELETED terms = Terms that exist in the " +
                "original file/database but are no longer in the current " +
                "file.");
        

    }
    
    public boolean getIsProcessed(){
        return isProcessed;
    }
}




