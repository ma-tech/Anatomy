package backend;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author attila
 */
public class ImportOBOFile {

    private ArrayList < Component > termList = new ArrayList < Component >();
    private ArrayList < Relation > relationList = new ArrayList < Relation >();
    private String savedBy = "";
    private String savedAt = "";
    private String remark = "";
    
    
    public ImportOBOFile(String fileName) {
        try {        
            String line = "";
            BufferedReader inputFile = new BufferedReader(new FileReader(fileName));

            // read lines
            line = inputFile.readLine();
            while ( inputFile.ready() ) {

                // 1: header parameters
                if ( line.contains("format-version: ") ) {
                    while ( !line.equals("") ) {
                        if ( line.contains("date: ") ) this.savedAt = line.substring(6,line.length());
                        if ( line.contains("saved-by: ") ) this.savedBy = line.substring(10,line.length());
                        if ( line.contains("remark: ") ) this.remark = line.substring(8,line.length());
                        // next line
                        line = inputFile.readLine();
                    }// while
                }// if - header parameters

                // 2: terms
                if ( line.equals("[Term]") ) {
                    line = inputFile.readLine();
                    Component term = new Component();
                    while ( !line.equals("") ) {
                        if ( line.contains("id: ") ) term.setID( line.substring(4,line.length()) );
                        if ( line.contains("name: ") ) term.setName( line.substring(6,line.length()) );
                        if ( line.contains("namespace: ") ) term.setNamespace( line.substring(11,line.length()) );
                        if ( line.contains("relationship: part_of ") ) {
                            if ( line.contains(" ! ") ) term.addPartOf( line.substring(22,line.indexOf(" ! ", 22)) );
                            else term.addPartOf( line.substring(22,line.length()) );
                        }
                        if ( line.contains("is_a: ") )  {
                            if ( line.contains(" ! ") ) term.setIsA( line.substring(6,line.indexOf(" ! ", 6)) );
                            else term.setIsA( line.substring(6,line.length()) );
                        }
                        if ( line.contains("relationship: starts_at ") ) { //more than 1 starts_at line will overwrite Component.StartsAt
                            if ( line.contains(" ! ") ) term.setStartsAt( line.substring(24,line.indexOf(" ! ", 24)) );
                            else term.setStartsAt( line.substring(24,line.length()) );
                        }
                        if ( line.contains("relationship: ends_at ") ) {
                            if ( line.contains(" ! ") ) term.setEndsAt( line.substring(22,line.indexOf(" ! ", 22)) );
                            else term.setEndsAt( line.substring(22,line.length()) );
                        }
                        if ( line.contains("related_synonym: ") ) term.addSynonym( line.substring(17,line.indexOf(" []", 17)) );
                        // next line
                        line = inputFile.readLine();
                    }// while
                    termList.add( term );
                }// if - terms

                // 3: relations
                if ( line.equals("[Typedef]") ) {
                    line = inputFile.readLine();
                    Relation rel = new Relation();
                    while ( !line.equals("") ) {
                        if ( line.contains("id: ") ) rel.setID( line.substring(4,line.length()) );
                        if ( line.contains("name: ") ) rel.setName( line.substring(6,line.length()) );
                        if ( line.contains("is_transitive: ") ) rel.setTransitive( line.substring(15,line.length()) );
                        // next line
                        line = inputFile.readLine();
                    }// while
                    relationList.add( rel );
                }// if - relations

                // next line    
                line = inputFile.readLine();
            }// while - read lines
          
            // close file
            inputFile.close();
       
        }
        catch(IOException io) {
            System.out.println("Unable to import the OBO file!");
        }
    }
    
    public String getSavedAt() {
        return this.savedAt;
    }
    
    public String getSavedBy() {
        return this.savedBy;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public ArrayList < Component > getTermList() {
            return termList;
    }
    
    public ArrayList < Relation > getRelationList() {
            return relationList;
    }
}