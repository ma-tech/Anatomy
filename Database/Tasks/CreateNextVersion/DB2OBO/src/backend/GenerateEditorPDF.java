/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Maze Lam
 */
public class GenerateEditorPDF {
     
    private ArrayList<Component> newTerms = new ArrayList<Component>();
    private ArrayList<Component> modifiedTerms = new ArrayList<Component>();
    private ArrayList<Component> deletedTerms = new ArrayList<Component>();
    private ArrayList<Component> unchangedTerms = new ArrayList<Component>();
    private ArrayList<Component> problemTerms = new ArrayList<Component>();
    
    private int failedNewTerms = 0;
    private int failedModifiedTerms = 0;
    private int failedDeletedTerms = 0;
    private int failedUnchangedTerms = 0;
    
    private Document pdfDocument = new Document();
    private TreeBuilder treebuilder;
    private boolean isProcessed = false;
    
    public GenerateEditorPDF( CheckComponents checkie, String fileName, String importedFileName, TreeBuilder treebuilder  ){

        this.treebuilder = treebuilder;

        //sort terms from CheckComponents class into categories
        //ArrayList<Component> changedTerms = checkie.getChangesTermList();
        ArrayList<Component> proposedTerms = checkie.getProposedTermList();
        problemTerms = checkie.getProblemTermList();
        this.sortChangedTerms( proposedTerms );        

        
        try {
            //check filepath exists
            File file = new File(fileName);
            if (!file.isDirectory())
                file = file.getParentFile();
            if (!file.exists()) return;

            //create pdf file
            PdfWriter.getInstance( pdfDocument,new FileOutputStream( fileName ) );
            pdfDocument.open();
            
            //pdf title
            Paragraph paraMainHeader = new Paragraph();
            Chunk chkMainHeader = new Chunk( "Editor Report for \n Import of OBO File: \n" + importedFileName, 
                    new Font( Font.HELVETICA, 14, Font.BOLD ) );
            paraMainHeader.add( chkMainHeader );
            paraMainHeader.add( Chunk.NEWLINE );
            paraMainHeader.add( Chunk.NEWLINE );
            pdfDocument.add( paraMainHeader );
            
            //summary
            //writeReportSummary(checkie);
            writeSummaryTable( checkie );
            
            //writing problem terms
            //writeProblemTerms( problemTerms );
            writeTerms( problemTerms, "CRITICAL COMPONENTS: REQUIRE REVISION", "Total Critical Components: ", Color.RED, "Problem Component ", "PROBLEM" );
            
            //writing new terms
            pdfDocument.add( Chunk.NEXTPAGE );
            writeTerms( newTerms, "NEW COMPONENTS", "Total New Components: ", new Color(0,140,0), "New Component " ,"NEW" );
            
            //writing modified terms
            pdfDocument.add( Chunk.NEXTPAGE );
            writeTerms( modifiedTerms, "MODIFIED COMPONENTS", "Total Modified Components: ", new Color(0,140,0), "Modified Component ", "MODIFIED" );
            
            //writing deleted terms
            pdfDocument.add( Chunk.NEXTPAGE );
            writeTerms( deletedTerms, "DELETED COMPONENTS", "Total Deleted Components: ", new Color(0,140,0), "Deleted Components ", "DELETED" );
            
            //appendix
            writeAppendix();
            
            //close pdf file
            pdfDocument.close();
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
                if ( compie.getStrRuleStatus().equals("FAILED") ) {
                    System.out.println("New and failed: " + compie.toString());
                    for (Object oComment: compie.getCheckComments() ){
                        String comment = (String) oComment;
                        System.out.println( comment );
                    }
                    failedNewTerms++;
                }
            } 
            else if ( compie.getStrChangeStatus().equals("CHANGED") ) {
                modifiedTerms.add(compie);
                if ( compie.getStrRuleStatus().equals("FAILED") ) {
                    System.out.println("Modified and failed: " + compie.toString());
                    for (Object oComment: compie.getCheckComments() ){
                        String comment = (String) oComment;
                        System.out.println( comment );
                    }
                    failedModifiedTerms++;
                }
            }
            else if ( compie.getStrChangeStatus().equals("DELETED") ) {
                deletedTerms.add(compie);
                if ( compie.getStrRuleStatus().equals("FAILED") ) {
                    System.out.println("Deleted and failed: " + compie.toString());
                    failedDeletedTerms++;
                }
            }
            else if ( compie.getStrChangeStatus().equals("UNCHANGED") ) {
                unchangedTerms.add(compie);
                if ( compie.getStrRuleStatus().equals("FAILED") ) {
                    System.out.println("Unchanged and failed: " + compie.toString());
                    for (Object oComment: compie.getCheckComments() ){
                        String comment = (String) oComment;
                        System.out.println( comment );
                    }
                    failedUnchangedTerms++;
                }
            }
        }
    }
    
  /*
    private void writeReportSummary(CheckComponents checkie){
        try{
            
            Chunk chkSummaryTitle = new Chunk("REPORT UPDATE SUMMARY", 
                    new Font( Font.COURIER, 12, Font.UNDERLINE ) );
            pdfDocument.add( chkSummaryTitle );
            pdfDocument.add( Chunk.NEWLINE ); pdfDocument.add( Chunk.NEWLINE );

            addSummaryEntry("Total Components from OBO File    : ", 
                    Integer.toString(checkie.getProposedTermList().size()), checkie );
            addSummaryEntry("New Components created by user   : ", 
                    Integer.toString( newTerms.size() ), checkie );
            addSummaryEntry("Modified Components edited by user   : ",
                    Integer.toString( modifiedTerms.size() ), checkie );
            addSummaryEntry("Deleted Components removed by user   : " ,
                    Integer.toString( deletedTerms.size() ), checkie );
            addSummaryEntry("Unchanged Components      : ", 
                    Integer.toString( unchangedTerms.size() ), checkie );
            addSummaryEntry("Critical Components in File   : ",
                    Integer.toString( problemTerms.size() ), checkie );

            Chunk chkSummaryFooter = new Chunk("------------------------------------------------------------------------------------------------------------");
            pdfDocument.add( chkSummaryFooter );
            pdfDocument.add( Chunk.NEWLINE ); pdfDocument.add( Chunk.NEWLINE );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }*/
    
    private void writeSummaryTable( CheckComponents checkie ){
        
        try{
            Table table = new Table( 4 );
            table.setPadding( (float)1.5 );
            table.setSpacing( 1 );
            
            Cell cell = new Cell();
            cell.setHeader( true );
            cell.setColspan( 1 );
            
            Chunk chkHeader = new Chunk( "Components Checked Status",
                    new Font( Font.COURIER, 12, Font.BOLDITALIC ) );
            cell.add( chkHeader );
            table.addCell( cell );
            chkHeader = new Chunk( "Failed*",
                    new Font( Font.COURIER, 12, Font.BOLDITALIC, Color.RED ) );
            cell = new Cell( chkHeader );
            table.addCell( cell );
            chkHeader = new Chunk( "Passed**",
                    new Font( Font.COURIER, 12, Font.BOLDITALIC, new Color(0,140,0) ) );
            cell = new Cell( chkHeader );
            table.addCell( cell );
            chkHeader = new Chunk( "Total",
                    new Font( Font.COURIER, 12, Font.BOLDITALIC ) );
            cell = new Cell( chkHeader );
            table.addCell( cell );
            
            table.endHeaders();
            
            table.addCell( makeSummaryTableLabel( "All Components" ) );
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( problemTerms.size() ), Font.BOLD, Color.RED ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( checkie.getProposedTermList().size()-problemTerms.size() ), 
                    Font.BOLD, Color.BLACK ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( checkie.getProposedTermList().size() ), Font.BOLD, Color.BLACK ) ); 
            
            table.addCell( makeSummaryTableLabel( "New" ) );
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( failedNewTerms ), Font.NORMAL, Color.RED ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( newTerms.size()-failedNewTerms ), 
                    Font.BOLD, new Color(0,140,0) ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( newTerms.size() ), Font.BOLD, Color.BLACK ) ); 
            
            table.addCell( makeSummaryTableLabel( "Modified" ) );
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( failedModifiedTerms ), Font.NORMAL, Color.RED ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( modifiedTerms.size()-failedModifiedTerms ), 
                    Font.BOLD, new Color(0,140,0) ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( modifiedTerms.size() ), Font.BOLD, Color.BLACK ) );
            
            table.addCell( makeSummaryTableLabel( "Deleted" ) );
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( failedDeletedTerms ), Font.NORMAL, Color.RED ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( deletedTerms.size()-failedDeletedTerms ), 
                    Font.BOLD, new Color(0,140,0) ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( deletedTerms.size() ), Font.BOLD, Color.BLACK ) );
            
            table.addCell( makeSummaryTableLabel( "Unchanged" ) );
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( failedUnchangedTerms ), Font.NORMAL, Color.RED ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( unchangedTerms.size()-failedUnchangedTerms ), 
                    Font.BOLD, new Color(0,140,0) ) ); 
            table.addCell( makeSummaryTableEntry( 
                    Integer.toString( unchangedTerms.size() ), Font.BOLD, Color.BLACK ) );
           
            pdfDocument.add( table );

            Chunk chkAsterisk1 = new Chunk( "*Failed: Some of the component's properties require revision by editor",
                    new Font( Font.NORMAL, 8, Font.ITALIC, Color.BLACK ) );
            Chunk chkAsterisk2 = new Chunk( "**Passed: Components have passed all checks and are approved for any updates that will take place",
                    new Font( Font.NORMAL, 8, Font.ITALIC, Color.BLACK ) );
            pdfDocument.add( chkAsterisk1 ); pdfDocument.add( Chunk.NEWLINE );
            pdfDocument.add( chkAsterisk2 );
            pdfDocument.add( Chunk.NEWLINE ); pdfDocument.add( Chunk.NEWLINE );
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private Cell makeSummaryTableLabel(String label){
        Cell cell = new Cell();
        Chunk chkLabel = new Chunk( label,
                new Font( Font.COURIER, 12, Font.BOLD ) );
        cell.add( chkLabel );
        return cell;
    }
    
    private Cell makeSummaryTableEntry(String entry, int style, Color color){
        Cell cell = new Cell();
        Chunk chkEntry = new Chunk( entry,
                new Font( Font.COURIER, 12, style, color ) );
        cell.add( chkEntry );
        return cell;
    }
    
    private void addSummaryEntry( String label, String item, CheckComponents checkie ){
        try{
            Chunk chkSummaryLabel = new Chunk( label, 
                    new Font( Font.COURIER, 10, Font.ITALIC ) );
            pdfDocument.add( chkSummaryLabel );
            
            Chunk chkSummaryItem = new Chunk( item,
                    new Font( Font.COURIER, 10, Font.BOLD ) );
            pdfDocument.add( chkSummaryItem );
            
            pdfDocument.add( Chunk.NEWLINE );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    private void makeComponentTable( Component compie, String tableHeader, int counter, Color tableColor ){
        try{
            //table
            Table table = new Table( 4 );
            table.setBorderWidth( 1 );
            table.setBorderColor( tableColor );
            table.setPadding( (float)1.5 );
            table.setSpacing( 1 );

            //table header
            Chunk chkHeader = new Chunk( tableHeader + counter,
                    new Font( Font.COURIER, 10, Font.BOLD ) );
            Cell cell = new Cell( chkHeader );
            cell.setHeader( true );
            cell.setColspan( 4 );
            table.addCell( cell );
            table.endHeaders();

            String parents = "";
            String groupparents = "";
            String strCompieID = ( !compie.getNewID().equals("") ) ? compie.getNewID(): compie.getID();
            for ( String parent: compie.getPartOf() )
                parents = parents + parent + "[" + treebuilder.getComponent( parent ).getName() + "] ";
            for ( String groupparent: compie.getGroupPartOf() )
                groupparents = groupparents + groupparent + "[" + treebuilder.getComponent( groupparent ).getName() + "] ";

            cell = makeLabel("ID"); cell.setColspan( 1 ); table.addCell( cell );
            cell = makeEntry( strCompieID ); cell.setColspan( 1 ); table.addCell( cell );
            
            cell = makeLabel("Is Group Term"); cell.setColspan( 1 ); table.addCell( cell );
            cell = makeEntry( Boolean.toString(!compie.getIsPrimary()) ); cell.setColspan( 1 ); table.addCell( cell );
            
            cell = makeLabel("Name"); cell.setColspan( 1 ); table.addCell( cell );
            cell = makeEntry( compie.getName() ) ; cell.setColspan( 3 ); table.addCell( cell );
            
            cell = makeLabel("Starts At"); cell.setColspan( 1 ); table.addCell( cell );
            cell = makeEntry( compie.getStartsAt() ); cell.setColspan( 1 ); table.addCell( cell );
            
            cell = makeLabel("Ends At" ); cell.setColspan( 1 ); table.addCell( cell );
            cell = makeEntry(compie.getEndsAt() ); cell.setColspan( 1 ); table.addCell( cell );
            
            cell = makeLabel("Primary Parents"); cell.setColspan( 1 ); table.addCell( cell );
            cell = makeEntry( parents ); cell.setColspan( 3 ); table.addCell( cell );
            
            cell = makeLabel("Group Parents"); cell.setColspan( 1 ); table.addCell( cell );
            cell = makeEntry( groupparents ); cell.setColspan( 3 ); table.addCell( cell );
            
            cell = makeLabel( "Synonyms" ); cell.setColspan( 1 ); table.addCell( cell );
            cell = makeEntry( (compie.getSynonym()).toString() ); cell.setColspan( 3 ); table.addCell( cell );
            
            if ( !tableHeader.startsWith("New") ){ //don't print paths for new components
                cell = makeLabel( "Primary Path" ); cell.setColspan( 1 ); table.addCell( cell );
                TreePath primaryPath = new TreePath( compie.getShortenedPrimaryPath() );
                cell = makeEntry( primaryPath.toString() ); cell.setColspan( 3 ); table.addCell( cell );

                cell = makeLabel( "Alternate Paths" ); cell.setColspan( 1 );
                cell.setRowspan( (compie.getPaths().size()==0) ? 1 : compie.getPaths().size() ); table.addCell( cell );

                Vector<DefaultMutableTreeNode[]> paths = compie.getShortenedPaths();
                if ( !paths.isEmpty() ){
                    int pathCounter = 0;
                    for( DefaultMutableTreeNode[] path : paths ){
                        pathCounter++;
                        TreePath alternatePath = new TreePath( path );
                        cell = makeEntry( pathCounter + ". " + alternatePath.toString() );
                        cell.setColspan( 3 ); table.addCell( cell );
                    }
                }else{
                    cell = makeEntry("");
                    cell.setColspan( 3 ); table.addCell( cell );
                }
            }
            
            cell = makeLabel( "Rule Status" ); cell.setColspan(1); table.addCell( cell );
            cell = makeEntry( compie.getStrRuleStatus() ); table.addCell( cell );
            
            cell = makeLabel( "Edit Status" ); table.addCell( cell );
            cell = makeEntry( compie.getStrChangeStatus() ); table.addCell( cell );
            
            cell = makeLabel( "Comments" ); cell.setColspan( 1 ); 
            cell.setRowspan( (compie.getCheckComments().size()==0) ? 1 : compie.getCheckComments().size() ); table.addCell( cell );
           
            Set<String> comments = compie.getCheckComments();
            int commentCounter = 0;
            for( String s: comments ){
                commentCounter++;
                cell = makeEntry( "     " + commentCounter +  ".  " + s );
                cell.setColspan( 3 ); table.addCell( cell );
            }
            
            pdfDocument.add( table );
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    private Cell makeLabel( String content ) throws BadElementException{
        //Chunk chkCell = new Chunk( content,
        //            new Font( Font.COURIER, 8, Font.ITALIC ) );
        Paragraph paraCell = new Paragraph( 9, content,
                new Font( Font.COURIER, 8, Font.ITALIC ) );
        Cell cell = new Cell( paraCell );
        return cell;
    }
    
    private Cell makeEntry( String content ) throws BadElementException{
        //Chunk chkCell = new Chunk( content,
        //            new Font( Font.COURIER, 8, Font.NORMAL ) );
        Paragraph paraCell = new Paragraph( 9, content,
                new Font( Font.COURIER, 10, Font.NORMAL ) );
        Cell cell = new Cell ( paraCell );
        return cell;
    }
    
    private void writeTerms( ArrayList<Component> termList, String strHeader, String strSubheader,
            Color headerColor, String strTableHeader, String strStatus ){
            int counter = 0;
            String strCompieID = "";
        
        try{
            //main header
            Chunk chkProblemTitle = new Chunk( strHeader, 
                    new Font( Font.COURIER, 12, Font.UNDERLINE, headerColor ) );
            pdfDocument.add( chkProblemTitle );
            pdfDocument.add( Chunk.NEWLINE ); pdfDocument.add( Chunk.NEWLINE );
            
            //total
            Chunk chkTotalLabel = new Chunk( strSubheader,
                    new Font( Font.NORMAL, 10, Font.ITALIC, headerColor ) ); //Color.RED
            Chunk chkTotalEntry = new Chunk( Integer.toString( termList.size() ),
                    new Font( Font.NORMAL, 10, Font.BOLD, headerColor ) );
            pdfDocument.add( chkTotalLabel );
            pdfDocument.add( chkTotalEntry );
            pdfDocument.add( Chunk.NEWLINE ); pdfDocument.add(Chunk.NEWLINE );
            
            //list items
            if ( !termList.isEmpty() ){
                //component ids + names

                for(Component compie: termList){
                    counter++;
                    strCompieID = ( !compie.getNewID().equals("") ) ? compie.getNewID(): compie.getID();

                    Paragraph paraListItem = new Paragraph();
                    Chunk chkListItem = new Chunk( "  " + counter + ". " + strCompieID + " - " + compie.getName(),
                            new Font( Font.NORMAL, 10, Font.NORMAL, Color.BLACK ) );
                    
                    paraListItem.setLeading( 9 );
                    paraListItem.add( chkListItem );
                    pdfDocument.add( paraListItem );
                }
                pdfDocument.add( Chunk.NEWLINE );
                
                //component detail header
                Chunk chkListDetail = new Chunk("DETAILS: ",
                    new Font( Font.NORMAL, 10, Font.UNDERLINE ) );
                pdfDocument.add( chkListDetail );
                pdfDocument.add( Chunk.NEWLINE ); pdfDocument.add( Chunk.NEWLINE );
                
                //component details
                counter = 0;
                Color tableColor = Color.BLACK;
                for(Component compie: termList){
                    counter++;
                    pdfDocument.add( Chunk.NEWLINE );
                    tableColor = ( compie.getStrRuleStatus().equals("FAILED") ) ? Color.RED : headerColor; 
                    makeComponentTable( compie, strTableHeader, counter, tableColor );
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void writeAppendix(){  
        try{
            pdfDocument.add( Chunk.NEWLINE ); pdfDocument.add( Chunk.NEWLINE );
            pdfDocument.add( Chunk.NEWLINE ); pdfDocument.add( Chunk.NEWLINE );

            Paragraph paraAppendix = new Paragraph();
            paraAppendix.setLeading( 9 );
            Chunk chkFooterLine = new Chunk( "--------------------------------------------------------------------------------------",
                    new Font( Font.COURIER, 10, Font.NORMAL, Color.BLACK ) );
            Chunk chkAppendixTitle = new Chunk("APPENDIX",
                    new Font( Font.COURIER, 12, Font.UNDERLINE, Color.BLACK ) );
            Chunk chkAppendixBody = new Chunk( "CRITICAL terms = Terms that require revision. \n" +
                    "NEW terms = Terms that did not exist in the original file/database. \n" + 
                    "MODIFIED terms = Terms that have a changed property. \n" +
                    "DELETED terms = Terms that exist in the original file/database but are no longer in the current file.",
                    new Font( Font.COURIER, 10, Font.ITALIC, Color.BLACK ) );
            paraAppendix.add( chkFooterLine );
            paraAppendix.add( Chunk.NEWLINE );
            paraAppendix.add( chkFooterLine );
            paraAppendix.add( Chunk.NEWLINE );
            paraAppendix.add( chkAppendixTitle );
            paraAppendix.add( Chunk.NEWLINE );
            paraAppendix.add( Chunk.NEWLINE );
            paraAppendix.add( chkAppendixBody );
            
            pdfDocument.add( paraAppendix );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean getIsProcessed(){
        return this.isProcessed;
    }
    
}




