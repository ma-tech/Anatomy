/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;


/**
 *
 * @author Maze Lam
 */
public class GenerateSQL {
    //database connection
    private Connection newConnection;
    
    //term list to be updated and term list to refer to
    private ArrayList < Component > proposedTermList; 
    
    //file properties
    private String strSpecies = "";
    private String strProject = "";
    
    //treebuilder object to use hashmaps to get component (getComponent)
    private TreeBuilder tree; 
    
    //term list for timed components
    private ArrayList < Component > timedCompList;
    //term list for synonym components
    private ArrayList < Component > synonymCompList;
    //term list for disallowed deleted components 1. not found in db 2. is primary and have undeleted children
    private ArrayList < Component > unDeletedCompList;
    //term list for disallowed modified components 1. not found in db
    private ArrayList < Component > unModifiedCompList;
    //term list for temporary terms created for larger stage ranges from modified components
    private ArrayList < Component > diffCreateTimedCompList;
    //term list for timed components to be deleted for smaller stage ranges from modified components
    private ArrayList < Component > diffDeleteTimedCompList;
    //term list for relationships to be created for changed parents from modifed components
    private ArrayList < Component > diffCreateRelList;
    //term list for relationships to be created for deleted parents from modified components
    private ArrayList < Component > diffDeleteRelList;
    //term list for synonyms to be created for changed synonyms from modifed components
    private ArrayList < Component > diffCreateSynList;
    //term list for synonyms to be created for deleted synonyms from modified components
    private ArrayList < Component > diffDeleteSynList;
    
    //flag for updating DB (command button dependent)
    private boolean flagUpdateDB;
    //maximum public id
    private int intCurrentVersionID;
    private int intCurrentPublicID;
    private int intCurrentObjectID;
    
    //report for queries + warnings
    private BufferedWriter reportFile;
    private StringWriter stringWriter = new StringWriter() ; 
    private PrintWriter report = new PrintWriter(stringWriter) ;

    //check whether was processed all the way
    private boolean isProcessed = false;
    //abstract class configuration
    private Component abstractClass;
    
    public GenerateSQL(Connection connection, ArrayList<Component> proposedTermList, TreeBuilder treebuilder, TreeBuilder refTreebuilder, String species, boolean flagUpdateDB, String fileName, Component abstractClass, String project  ){
        
        this.newConnection = connection;
        this.proposedTermList = proposedTermList;
        this.timedCompList = new ArrayList<Component>();
        this.synonymCompList = new ArrayList<Component>();
        this.unDeletedCompList = new ArrayList<Component>();
        this.unModifiedCompList = new ArrayList<Component>();
        this.diffCreateTimedCompList = new ArrayList<Component>();
        this.diffDeleteTimedCompList = new ArrayList<Component>();
        this.diffCreateRelList = new ArrayList<Component>();
        this.diffDeleteRelList = new ArrayList<Component>();
        this.diffCreateSynList = new ArrayList<Component>();
        this.diffDeleteSynList = new ArrayList<Component>();
        this.tree = treebuilder;
        this.strSpecies = species;
        this.flagUpdateDB = flagUpdateDB;
        this.intCurrentPublicID = 0;
        this.intCurrentObjectID = 0;
        this.abstractClass = abstractClass;
        this.strProject = project;

        //internal variables
        int intFailed = 0;
        
        try{
            //check filepath exists
            File file = new File(fileName);
            if (!file.isDirectory())
                file = file.getParentFile();
            if (!file.exists()) return;
            
            //initialise BufferedWriter for report
            this.reportFile = new BufferedWriter(new FileWriter(fileName));

            //File header
            this.reportFile.write("Database Report For ");
            this.reportFile.newLine();
            this.reportFile.write("  Import of OBO File: " + treebuilder.getRootNode() );
            this.reportFile.newLine();
            this.reportFile.write("  Using Reference: " + refTreebuilder.getRootNode() );
            this.reportFile.newLine();
            this.reportFile.newLine();
            //Critical Warnings
            this.reportFile.write("CRITICAL WARNINGS");
            this.reportFile.newLine();
            this.reportFile.write("=================");
            this.reportFile.newLine();
            
            
            //internal termlists for data manipulation
            ArrayList<Component> newComponents = new ArrayList < Component >();
            ArrayList<Component> deletedComponents = new ArrayList < Component >();
            ArrayList<Component> validDeletedComponents = new ArrayList < Component >();
            ArrayList<Component> changedComponents = new ArrayList < Component >();
            ArrayList<Component> changedPropComponents = new ArrayList < Component >();
            
            
            //construct internal arraylists
            Component compie = new Component();
            for (int i = 0; i<this.proposedTermList.size(); i++){
                compie = this.proposedTermList.get(i);
            
                if( compie.getStrChangeStatus().equals("NEW") ){
                    if( compie.getStrRuleStatus().equals("FAILED") ) {
                        intFailed++;
                        System.out.println( "SQL queries for New Component " + compie.getID() + " " + compie.getName() + " with rule violation have been generated!" );
                        this.reportFile.write( "SQL queries for New Component " + compie.getID() + " " + compie.getName() + " with rule violation have been generated!" );
                        this.reportFile.newLine();
                    }
                    newComponents.add( compie );
                }
                if( compie.getStrChangeStatus().equals("DELETED") ){
                    if( compie.getStrRuleStatus().equals("FAILED") ){
                        intFailed++;
                        System.out.println( "SQL queries for Deleted Component " + compie.getID() + " " + compie.getName() + " with rule violation have been generated!");
                        this.reportFile.write( "SQL queries for Deleted Component " + compie.getID() + " " + compie.getName() + " with rule violation have been generated!");
                        this.reportFile.newLine();
                    }
                    deletedComponents.add( compie );
                }
                if( compie.getStrChangeStatus().equals("CHANGED") ){
                    if( compie.getStrRuleStatus().equals("FAILED") ){
                        intFailed++;
                        System.out.println( "SQL queries for Changed Component " + compie.getID() + " " + compie.getName() + " with rule violation have been generated!");
                        this.reportFile.write( "SQL queries for Changed Component " + compie.getID() + " " + compie.getName() + " with rule violation have been generated!");
                    }
                    changedComponents.add( compie );
                }
            }
                
            //set version id
            initialiseVersionID();
            
            //set a version record in ANA_VERSION for this update
            insertANA_VERSION( this.intCurrentVersionID, newComponents, deletedComponents, changedComponents );

            //new components
            //insertANA_OBJECT(newComponents, "ANA_OBJECT");
            insertANA_NODE(newComponents);
            insertANA_RELATIONSHIP(newComponents, "NEW", this.strProject);
            insertANA_TIMED_NODE(newComponents, "NEW");
            insertANA_SYNONYM(newComponents, "NEW");

            /*
            //delete components
            ////delete components, set DBIDs and get only components that have dbids based on emap id
            deletedComponents = setDBIDs(deletedComponents);
            //CRITICAL DELETION VALIDATION: to disallow deletion of components that do have children in database
            //1. check that term exists in database
            //2. if term = primary, check that all descendants are due for deletion in obo file as well
            //3. if one descendant specified in database is not found in OBO file 
            //   OR descendant is found but not specified for deletion, 
            //4. pass on invalid term to unDeletedCompList
            //pass valid terms to validDeleteComponents
            validDeletedComponents = this.validateDeleteTermList( deletedComponents );
            //insert log records for deleted components
            insertANA_LOG( validDeletedComponents );
            //perform deletion on valid deletion term list
            deleteComponentFromTables( validDeletedComponents );
            //reorder siblings of deleted components that have order
            reorderANA_RELATIONSHIP( validDeletedComponents );
            //report for invalid delete term list that have not been deleted
            reportDeletionSummary(deletedComponents, validDeletedComponents, this.unDeletedCompList);*/
            
            reportUpdateSummary();
            //modify components, set DBIDs and get only components that have dbids based on emap id
            changedComponents = setDBIDs(changedComponents);
            //get components whose stage ranges have changed
            changedPropComponents = this.getChangedStagesTermList( changedComponents );
            //perform insertion and deletion for modified stage ranges
            updateStages( changedPropComponents );
            //get components whose names have changed
            changedPropComponents = this.getChangedNamesTermList( changedComponents );
            //perform update for modified names
            updateANA_NODE( changedPropComponents );
            //get components whose synonyms have changed
            changedPropComponents = this.getChangedSynonymsTermList( changedComponents );
            //perform insertion and deletion for modified synonyms
            updateSynonyms( changedPropComponents );
            //get components whose parents have changed
            changedPropComponents = this.getChangedParentsTermList( changedComponents );
            //perform insertion and deletion for modified parent relationships
            updateParents( changedPropComponents );
            //get components whose primary status have changed
            changedPropComponents = this.getChangedPrimaryStatusTermList( changedComponents );
            //perform update for modified primary status
            updateANA_NODE_primary( changedPropComponents );
            //get components whose ordering have changed
            changedPropComponents = this.getChangedOrderTermList( changedComponents );
            //perform reordering
            updateOrder( changedPropComponents );
            
            //delete components
            ////delete components, set DBIDs and get only components that have dbids based on emap id
            deletedComponents = setDBIDs(deletedComponents);
            //CRITICAL DELETION VALIDATION: to disallow deletion of components that do have children in database
            //1. check that term exists in database
            //2. if term = primary, check that all descendants are due for deletion in obo file as well
            //3. if one descendant specified in database is not found in OBO file
            //   OR descendant is found but not specified for deletion,
            //4. pass on invalid term to unDeletedCompList
            //pass valid terms to validDeleteComponents
            validDeletedComponents = this.validateDeleteTermList( deletedComponents );
            //insert log records for deleted components
            insertANA_LOG( validDeletedComponents );
            //perform deletion on valid deletion term list
            deleteComponentFromTables( validDeletedComponents );
            //reorder siblings of deleted components that have order
            reorderANA_RELATIONSHIP( validDeletedComponents, this.strProject );
            //report for invalid delete term list that have not been deleted
            reportDeletionSummary(deletedComponents, validDeletedComponents, this.unDeletedCompList);
            
            this.reportFile.newLine(); this.reportFile.newLine(); 
            this.reportFile.write("---------------------------------------------------------------------------------------------------------------------------");            
            this.reportFile.newLine();
            this.reportFile.write("---------------------------------------------------------------------------------------------------------------------------");            
            this.reportFile.newLine();
            this.reportFile.write("---------------------------------------------------------------------------------------------------------------------------");            
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("DATABASE UPDATE SUMMARY");
            this.reportFile.newLine();
            this.reportFile.write("=======================");
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "ALLOWED UPDATES" );
            this.reportFile.newLine();
            this.reportFile.write( "---------------" );
            this.reportFile.newLine();
            this.reportFile.write( "Total Components from OBO File: " + proposedTermList.size() );
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "NEW      : " + newComponents.size() );
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "MODIFIED : " + changedComponents.size() );
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "DELETED  : " + validDeletedComponents.size() );
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "BLOCKED UPDATES" );
            this.reportFile.newLine();
            this.reportFile.write( "---------------" );
            this.reportFile.newLine();
            this.reportFile.write( "Total Components containing rule violations: " + intFailed );
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "Modified Components with rule violations: " + this.unModifiedCompList.size() );
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "Deleted Components with rule violations: " + this.unDeletedCompList.size() );
            this.reportFile.newLine(); this.reportFile.newLine();
            
            this.reportFile.write( stringWriter.toString() ) ; 
            this.reportFile.close();

            this.isProcessed = true;
        }
        catch(IOException io){
            io.printStackTrace();
        }
    }
    
    
    private void initialiseVersionID(){
        //first obj_oid in ana_object for all updates
        //obj_oid > version_fk = all records related to this update
        this.intCurrentVersionID = this.getMaxObjectID();
        this.intCurrentVersionID++;
    }
    
    private void updateStages( ArrayList < Component > changedStageTermList ){

        ArrayList < Component > deleteTimedComponents = new ArrayList < Component >();
        
        //find ranges of stages that need to be inserted/deleted, create temporary components for ranges
        this.createDifferenceTimedComponents( changedStageTermList );
        report.println();
        report.println( "STAGES" );
        report.println( "------" );
        report.println("Number of Components whose Stage Range was Extended: " + this.diffCreateTimedCompList.size() );
        report.println("Number of Components whose Stage Range was Shortened: " + this.diffDeleteTimedCompList.size() );
        report.println();
        
        //insert time components in ANA_TIMED_NODE
        insertANA_TIMED_NODE( this.diffCreateTimedCompList, "MODIFY" );
        //insert time components to be deleted into ANA_LOG
          //insertANA_LOG_deletedStages( this.diffDeleteTimedCompList );
          //create timed components due for deletion
          //deleteTimedComponents = this.createTimeComponents( this.diffDeleteTimedCompList, "DELETE" ); //delete obj_oids!
        deleteTimedComponents = this.insertANA_LOG_deletedStages( this.diffDeleteTimedCompList );
        //delete time components in ANA_TIMED_NODE
        deleteANA_TIMED_NODE( deleteTimedComponents );
        //delete object oids of timed components in ANA_OBJECT
        deleteANA_OBJECT( deleteTimedComponents, "ANA_TIMED_NODE" );
        
    }
    
    private void updateParents( ArrayList < Component > changedParentsTermList ){

        ArrayList < Component > deleteRelComponents = new ArrayList < Component >();
        
        //find ranges of stages that need to be inserted/deleted, create temporary components for ranges
        this.createDifferenceParents( changedParentsTermList );
        report.println();
        report.println( "PARENTS" );
        report.println( "-------" );
        report.println("Number of Components whose Parents were increased: " + this.diffCreateRelList.size() );
        report.println("Number of Components whose Parents were removed: " + this.diffDeleteRelList.size() );
        report.println();
        
        //insert relationships in ANA_RELATIONSHIP
        insertANA_RELATIONSHIP( this.diffCreateRelList, "MODIFY", this.strProject );
        //insert relationships to be deleted in ANA_LOG
        deleteRelComponents = this.insertANA_LOG_deletedRels( this.diffDeleteRelList ); //delete obj_oids!
        //delete relationships in ANA_RELATIONSHIP
        deleteANA_RELATIONSHIP( deleteRelComponents );
        //delete object oids of relationship records in ANA_OBJECT
        deleteANA_OBJECT( deleteRelComponents, "ANA_RELATIONSHIP" );
        //reorder children in deleted/added child rows
    }
    
    private void updateSynonyms( ArrayList < Component > changedSynonymsTermList ){

        ArrayList < Component > deleteSynComponents = new ArrayList < Component >();
        
        //find ranges of stages that need to be inserted/deleted, create temporary components for ranges
        this.createDifferenceSynonyms( changedSynonymsTermList );
        
        report.println();
        report.println( "SYNONYMS" );
        report.println( "--------" );
        report.println("Number of Components whose Synonyms were increased: " + this.diffCreateSynList.size() );
        report.println("Number of Components whose Synonyms were removed: " + this.diffDeleteSynList.size() );
        report.println();

        //insert relationships in ANA_SYNONYM
        insertANA_SYNONYM( this.diffCreateSynList, "MODIFY" );
        //insert relationships to be deleted in ANA_LOG
        deleteSynComponents = this.insertANA_LOG_deletedSyns( this.diffDeleteSynList );
        //delete relationships in ANA_SYNONYM
        deleteANA_SYNONYM( deleteSynComponents );
        //delete object oids of synonym records in ANA_OBJECT
        deleteANA_OBJECT( deleteSynComponents, "ANA_SYNONYM" );
        
        
    }

    private void updateOrder( ArrayList < Component > changedOrderTermList ){
        //get all parents whose children have a changed order
        //for each parent
        //get the children that have an order sequence
        //line them up from 0 - max entry
        //send collection of parents-orderedchildren to querymaker
        HashMap<String, ArrayList<String>> mapOrderedChildren = new HashMap(); //parent-> child order 1, child2, child3
        ArrayList<String> parents = new ArrayList<String>();
        ArrayList<String> children = new ArrayList<String>();
        Component childCompie = new Component();
        ArrayList<String> commentsOnParent = new ArrayList<String>();
        int intMaxOrder = 0;
        String[] arrayFirstWord = null;
        String forChild = "";

        for (Component compie: changedOrderTermList){
            //get all parents
            parents.addAll(compie.getPartOf());
            parents.addAll(compie.getGroupPartOf());
            //check for each parent whether there is ordering
            for (String parent: parents){
                commentsOnParent.clear(); //reset for each parent
                children.clear(); //reset for each parent
                intMaxOrder = -1; //reset for each parent
                //get all children
                children.addAll( this.tree.getChildrenBasedOnParent(parent) );
                //iterate through all children of each parent and gather all order comments
                for (String child: children){
                    forChild = ""; //reset for each child
                    //get component for child
                    childCompie = this.tree.getComponent(child);
                    //get order from child compie based on the parent
                    String[] arrOrderComments = childCompie.getOrderCommentOnParent(parent);
                    //if there is an order put in order vector
                    if ( arrOrderComments!=null ){
                        //find max order number for this series of siblings
                        arrayFirstWord = arrOrderComments[0].split(" ");
                        if ( Integer.parseInt(arrayFirstWord[0]) > intMaxOrder ){
                            intMaxOrder = Integer.parseInt(arrayFirstWord[0]);
                        }

                        //get first word from order comment and append child to it to make a new comment based on 'for child'
                        forChild = arrayFirstWord[0] + " for " + childCompie.getID();
                        //add to order comments for this parent
                        commentsOnParent.add(forChild);

                        //should never enter here if rule properly checked in CheckComponents
                        if ( arrOrderComments.length>1 ) {
                            System.out.println("WARNING! more than one order comment for the same parent "
                                    + parent + " detected for component " + compie.getID());
                        }
                    } 
                }//iterated through list of children
                //once all order comments collected from the children of one parent
                //reorder the comments and put in Array based on sequence
                ArrayList<String> arrOrdered = null;
                if (intMaxOrder!=-1){
                    String[] ordered = new String[intMaxOrder+1];
                    arrOrdered = new ArrayList<String>();
                    int intOrder = 0;
                    String strChild = "";
                    for (String comment: commentsOnParent){
                        //get sequence number from order comment
                        arrayFirstWord = comment.split(" ");
                        intOrder = Integer.parseInt(arrayFirstWord[0]); //first item in array is order no.
                        strChild = arrayFirstWord[arrayFirstWord.length-1]; //last item in array is child component id
                        //build an ordered, sorted array of comments for the parent
                        if (intOrder!=-1){
                            ordered[intOrder] = strChild;
                        }
                    }
                    //convert ordered array to arraylist, retaining order of elements
                    //put unordered elements at the end of list
                    for(int i=0; i<ordered.length; i++){
                        arrOrdered.add(ordered[i]);
                    }
                }
                //build hashmap parent-> sorted children
                //don't add entry for parents that already have entry
                if ( mapOrderedChildren.get(parent)==null ){
                    mapOrderedChildren.put(parent, arrOrdered);
                }
            }
        }
        //test
        //System.out.println(mapOrderedChildren.toString());

        //send hashmap to update_orderANA_RELATIONSHIP
        update_orderANA_RELATIONSHIP(mapOrderedChildren);
    }
    
    private void updateANA_NODE( ArrayList< Component > changedNameTermList ){
        
        PreparedStatement preppie = null;
        
        try{
            
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "UPDATE ANA_NODE COMPONENT NAMES" );
            this.reportFile.newLine();
            this.reportFile.write( "===============================" );
            this.reportFile.newLine(); this.reportFile.newLine();
            
            if ( changedNameTermList.isEmpty() ){
                this.reportFile.write(" -- No records updated --");
                this.reportFile.newLine();
            }
            
            String query = "UPDATE ANA_NODE SET ano_component_name = ? " +
                           "WHERE ano_public_id = ?";
            preppie = this.newConnection.prepareStatement(query);
            
            for (Component compie: changedNameTermList){
                preppie.setString( 1, compie.getName() );
                preppie.setString( 2, compie.getID() );
                preppie.addBatch();
                
                this.reportFile.write("UPDATE ANA_NODE SET ano_component_name = '" + compie.getName() +
                                      "' WHERE ano_public_id = '" + compie.getID() + "'" );
                this.reportFile.newLine();
            }
            if ( this.flagUpdateDB ) preppie.executeBatch();
        }catch(Exception ex){
            
            ex.printStackTrace();
        }
    }
    
    private void updateANA_NODE_primary( ArrayList< Component > changedPrimaryTermList ){
        
        PreparedStatement preppie = null;
        int intIsPrimary = 0;
        int intIsGroup = 0;
        
        try{
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "UPDATE ANA_NODE PRIMARY STATUS" );
            this.reportFile.newLine();
            this.reportFile.write( "==============================" );
            this.reportFile.newLine(); this.reportFile.newLine();
            
            if ( changedPrimaryTermList.isEmpty() ){
                this.reportFile.write(" -- No records updated --");
                this.reportFile.newLine();
            }
            
            String query = "UPDATE ANA_NODE SET ano_is_primary = ?, ano_is_group = ? " +
                           "WHERE ano_public_id = ?";
            preppie = this.newConnection.prepareStatement(query);
            
            for (Component compie: changedPrimaryTermList){
                intIsPrimary = ( compie.getIsPrimary() ) ? 1 : 0;
                intIsGroup = ( compie.getIsPrimary() ) ? 0 : 1;
                preppie.setInt( 1,  intIsPrimary );
                preppie.setInt( 2, intIsGroup );
                preppie.setString( 2, compie.getID() );
                preppie.addBatch();
                
                this.reportFile.write("UPDATE ANA_NODE SET ano_component_name = " + intIsPrimary +
                                      ", ano_is_group = " + intIsGroup + 
                                      " WHERE ano_public_id = '" + compie.getID() + "'" );
                this.reportFile.newLine();
            }
            if ( this.flagUpdateDB ) preppie.executeBatch();
        }catch(Exception ex){
            
            ex.printStackTrace();
        }
    }
       
    private void insertANA_VERSION( int objectID, ArrayList<Component> newTermList, ArrayList<Component> delTermList, ArrayList<Component> modTermList ){

         //table desc
        //VER_OID
        //VER_NUMBER
        //VER_DATE
        //VER_COMMENTS
        
        Component compie;
        PreparedStatement preppie = null;
        ResultSet rs = null;
        int intVersionEntries = 0;
        Date now = new Date();
        String datetime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(now) + ":00";

        try{
           //get current max  id
           //this.intCurrentPublicID = this.getMaxPublicID();

            //ana_version summary
            report.println();
            report.println( "ANA_VERSION INSERT SUMMARY" );
            report.println( "==========================" );
            
           //ana_node report header
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "ANA_VERSION" );
            this.reportFile.newLine();
            this.reportFile.write( "===========" );
            this.reportFile.newLine(); this.reportFile.newLine();
            
           if ( newTermList.isEmpty() && delTermList.isEmpty() && modTermList.isEmpty() ){
               this.reportFile.write("-- No record inserted: Database Update did not occur because DB2OBO failed to detect any changes in the OBO File --");
               this.reportFile.newLine();
           } 
           else{        
               //insert into ANA_OBJECT
               Component verCompie = new Component();
               ArrayList< Component > verTermList = new ArrayList<Component>();
               verTermList.add( verCompie );
               insertANA_OBJECT( verTermList, "ANA_VERSION" );
               
               //INSERT timed components into ANA_VERSION
               this.reportFile.newLine();
               this.reportFile.write("-- Inserting Version --");
               this.reportFile.newLine(); this.reportFile.newLine();
               
               //find out which round of update this is to the db
               String query = "SELECT COUNT(*) AS rows FROM ANA_VERSION";
               preppie = this.newConnection.prepareStatement(query);
               rs = preppie.executeQuery(query);
               if (rs.next()) intVersionEntries = rs.getInt("rows");

               //prepare values for insertion
               int intVER_OID = this.intCurrentVersionID;
               int intVER_NUMBER = ++intVersionEntries;
               String strVER_DATE = datetime;
               String strVER_COMMENTS = "DB2OBO Update: Editing the ontology";
                   
               //INSERT INTO ANA_VERSION
               query = "INSERT INTO ANA_VERSION " +
                       "(ver_oid, ver_number, ver_date, ver_comments) " + 
                       "VALUES (" + intVER_OID + ", " + intVER_NUMBER + ", '" +
                             strVER_DATE + "', '" + strVER_COMMENTS + "')";
               preppie =  this.newConnection.prepareStatement(query); 

               //write sql query to report file
               this.reportFile.write(query);
               this.reportFile.newLine();
               
               //write summary to report file          
               report.println( "Object id for update starts at " + this.intCurrentVersionID );
               report.println( "Number of Records Inserted is always 1 if an update takes place." );


               if ( this.flagUpdateDB ) {
                   preppie.execute();
               }
               preppie.close();
           }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        /*
        finally {
            if (preppie != null) {
                try {
                    preppie.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
            if (newConnection != null) {
                try {
                newConnection.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
        }*/
    } 
    
    private void insertANA_OBJECT( ArrayList< Component > newTermList, String calledFromTable ){
        
        //table desc
        //OBJ_OID
        //OBJ_CREATION_DATETIME
        //OBJ_CREATOR_FK
        
        Component compie = new Component();
        PreparedStatement preppie = null;

        try{
            this.intCurrentObjectID = this.getMaxObjectID();
            
            //format date and time to mysql standards
            Date now = new Date();
            String datetime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(now) + ":00";

            //ana_object summary
            if ( calledFromTable.equals("ANA_OBJECT") ){
                //ana_object report header
                this.reportFile.newLine(); this.reportFile.newLine();
                this.reportFile.write("ANA_OBJECT \n");
                this.reportFile.newLine();
                this.reportFile.write("========== \n");
                this.reportFile.newLine();
                this.reportFile.newLine();
                
                report.println();
                report.println("ANA_OBJECT SUMMARY");
                report.println("==================");
                report.println("Number of Records Inserted: " + newTermList.size() );
                //System.out.println(newTermList.size() + " components to be updated.");
            }
            else if ( calledFromTable.equals("ANA_VERSION") ){
                this.reportFile.write("-- Inserting First Object ID for entire database update --");
                this.reportFile.newLine(); this.reportFile.newLine();
            }
            else if ( calledFromTable.equals("ANA_TIMED_NODE") ){
                this.reportFile.write("-- Inserting Object IDs for New Timed Components --");
                this.reportFile.newLine(); this.reportFile.newLine();
                report.println( "Number of OID Records Inserted for Timed Components in ANA_OBJECT: " + newTermList.size() );
            }
            else if ( calledFromTable.equals("ANA_SYNONYM") ){
                this.reportFile.write("-- Inserting Object IDs for New Synonyms --");
                this.reportFile.newLine(); this.reportFile.newLine();
                report.println( "Number of OID Records Inserted for Synonyms in ANA_OBJECT: " + newTermList.size() );
            }
            else if ( calledFromTable.equals("ANA_NODE") ){
                this.reportFile.write("-- Inserting Object IDs for Creation of New Components --");
                this.reportFile.newLine(); this.reportFile.newLine();
                report.println( "Number of OID Records Inserted for New Components in ANA_OBJECT: " + newTermList.size() );
            }
            else if ( calledFromTable.equals("ANA_RELATIONSHIP") ){
                this.reportFile.write("-- Inserting Object IDs for Adding New part-of Links --");
                this.reportFile.newLine(); this.reportFile.newLine();
                report.println( "Number of OID Records Inserted for New part-of Links in ANA_OBJECT: " + newTermList.size() );
            }
      
            
           if ( newTermList.isEmpty() ){
               this.reportFile.newLine();
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
           }
           else{
                //INSERT INTO ANA_OBJECT
                String query = "INSERT INTO ANA_OBJECT " + 
                               "(obj_oid , obj_creation_datetime, obj_creator_fk) " +
                               "VALUES (?, '" + datetime + "', NULL)";
                preppie =  this.newConnection.prepareStatement(query);
                
                for(int i=0; i<newTermList.size(); i++){
                    compie = newTermList.get(i);

                    //database values
                    int intOBJ_OID = ++this.intCurrentObjectID;
                    //String strOBJ_CREATION_DATETIME = datetime;
                    //String strOBJ_CREATOR_FK

                    //update new components with ano_oid/atn_oid
                    compie.setDBID(Integer.toString(intOBJ_OID));

                    preppie.setInt(1, intOBJ_OID);
                    preppie.addBatch();

                    this.reportFile.write("INSERT INTO ANA_OBJECT " + 
                        "(obj_oid , obj_creation_datetime, obj_creator_fk) " +
                        "VALUES (" + intOBJ_OID + ", '" + datetime + "', NULL)" + "\n");
                    this.reportFile.newLine();
                }
                if( this.flagUpdateDB ) {
                    preppie.executeBatch();
                }
                preppie.close();
           }
        } catch (Exception ex){
            ex.printStackTrace();
        } 
        /*finally{
            if (preppie != null) {
                try {
                    preppie.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
            if (newConnection != null) {
                try {
                newConnection.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
        } */       
    }
    
    
    private void insertANA_NODE( ArrayList<Component> newTermList ){
        
        //table desc
        //ANO_OID
        //ANO_SPECIES_FK
        //ANO_COMPONENT_NAME
        //ANO_IS_PRIMARY
        //ANO_IS_GROUP
        //ANO_PUBLIC_ID
        //ANO_DESCRIPTION
        
        Component compie;
        PreparedStatement preppie = null;
 
        try{
           //get current max public id
           this.intCurrentPublicID = this.getMaxPublicID();

            //ana_node summary
            report.println();
            report.println( "ANA_NODE INSERT SUMMARY" );
            report.println( "=======================" );
            report.println( " EMAPA:ID assigned for new components created by user: ");
            
           //ana_node report header
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write( "ANA_NODE" );
            this.reportFile.newLine();
            this.reportFile.write( "========" );
            this.reportFile.newLine(); this.reportFile.newLine();
            
           if ( newTermList.isEmpty() ){
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
           } 
           else{        
               //insert into ANA_OBJECT
               insertANA_OBJECT(newTermList, "ANA_NODE");
               
               //INSERT timed components into ANA_NODE
               this.reportFile.newLine();
               this.reportFile.write("-- Inserting New Components --");
               this.reportFile.newLine(); this.reportFile.newLine();
                
               //INSERT INTO ANA_NODE
               String query = "INSERT INTO ANA_NODE " +
                             "(ano_oid, ano_species_fk, ano_component_name, ano_is_primary, ano_is_group, " +
                             "ano_public_id, ano_description) " + 
                             "VALUES (?, ?, ?, ?, ?, ?, NULL)";
               preppie =  this.newConnection.prepareStatement(query);
                
               for(int i = 0; i< newTermList.size(); i++){

                   compie = newTermList.get(i);

                   //prepare values
                   int intANO_OID = Integer.parseInt(compie.getDBID());
                   String strANO_SPECIES_FK = strSpecies;
                   String strANO_COMPONENT_NAME = compie.getName();
                   int intANO_IS_PRIMARY = compie.getIsPrimary() ? 1 : 0 ; // 
                   int intANO_IS_GROUP = compie.getIsPrimary() ? 0 : 1; 
                   //assign new emapa ids
                   String strANO_PUBLIC_ID = "EMAPA:" + Integer.toString(++intCurrentPublicID);
                   //assign generated new EMAPA id to new components replacing temp id
                   ////add new entry to map so that new emapa id also points to existing new component
                   //// -- <<new Emapa id -> new component>> -- <<tmp id -> ne component>>
                   //this.tree.addMapPropertyEntry(strANO_PUBLIC_ID, compie);
                   compie.setNewID(strANO_PUBLIC_ID);
                   //System.out.println("assigning new Emapa id to new component: " + compie.getID());

                   
                   report.println( "  " + Integer.toString(i+1) + ". " + "Created by user: " + compie.getID() + " " + compie.getName() + " : Assigned ID: EMAPA:" + this.intCurrentPublicID );
                   //comment new component with generated EMAPA id
                   this.tree.getComponent( compie.getID() ).setCheckComment("New EMAPA:ID generated: " + strANO_PUBLIC_ID);

                   //String strANO_DESCRIPTION = "NULL"; //always null

                   //update new components with ano_oid
                   compie.setDBID(Integer.toString(intANO_OID));
                   //update new component with generated emapa id
                   //compie.setID(strANO_PUBLIC_ID);

                   //set values for each preparedstatement
                   preppie.setInt(1, intANO_OID);
                   preppie.setString(2, strANO_SPECIES_FK);
                   preppie.setString(3, strANO_COMPONENT_NAME);
                   preppie.setInt(4, intANO_IS_PRIMARY);
                   preppie.setInt(5, intANO_IS_GROUP);
                   preppie.setString(6, strANO_PUBLIC_ID);
                   //preppie.setString(7, strANO_DESCRIPTION);

                   //add to batch
                   preppie.addBatch();

                   //if too slow write code here to execute for every batch of 500 records
                   //and remove finally block

                   this.reportFile.write("INSERT INTO ANA_NODE (" +
                       "ano_oid, ano_species_fk, ano_component_name, ano_is_primary, ano_is_group, " +
                       "ano_public_id, ano_description) " + 
                       "VALUES (" +
                       intANO_OID + ", '" + strANO_SPECIES_FK + "', '" + strANO_COMPONENT_NAME + "', " +
                       intANO_IS_PRIMARY + ", " + intANO_IS_GROUP + ", '" + strANO_PUBLIC_ID + "', NULL)" + "\n");
                   this.reportFile.newLine();
               }
               if ( this.flagUpdateDB ) preppie.executeBatch();
               preppie.close();
           }
            
           report.println( "Number of Records Inserted: " + newTermList.size() );
        } catch (Exception ex){
            ex.printStackTrace();
        }
        /*
        finally {
            if (preppie != null) {
                try {
                    preppie.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
            if (newConnection != null) {
                try {
                newConnection.close();
                } catch (SQLException e) {
                } // nothing we can do
            }
        }*/
    }
    
    
    
    private void insertANA_RELATIONSHIP( ArrayList < Component > newTermList, String calledFrom, String project ){
        //table desc
        //REL_OID 
        //REL_RELATIONSHIP_TYPE_FK 
        //REL_CHILD_FK
        //REL_PARENT_FK
        //REL_SEQUENCE
        
        ArrayList < Component > insertRelObjects = new ArrayList<Component>();
        Component compie;
        PreparedStatement preppie = null;
        String[] orders = null;
        int intMax_OID = 0;
        int intMAX_PK = 0;
        int intRecords = 0;
        boolean flagInsert;
        String strSeq = "";
        int[] batchreturn = null;

        try{
            //get max oid from referenced database
            ResultSet oidRS = null;
            String query = "SELECT MAX(rel_oid) as max_oid FROM ANA_RELATIONSHIP";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intMax_OID = oidRS.getInt("max_oid");

            //get max pk from referenced ana_relationship_project
            oidRS = null;
            query = "SELECT MAX(rlp_oid) AS max_pk FROM ANA_RELATIONSHIP_PROJECT";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intMAX_PK = oidRS.getInt("max_pk");

            if ( calledFrom.equals("MODIFY") ){
               //ana_timed_node delete summary
                this.reportFile.newLine(); this.reportFile.newLine();
                this.reportFile.write("INSERTION IN ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT FOR CREATION OF NEW PARENTS FOR MODIFIED COMPONENTS \n");
                this.reportFile.newLine();
                this.reportFile.write("============================================================================================================== \n");
                this.reportFile.newLine(); this.reportFile.newLine();       
            }
            else if ( calledFrom.equals("NEW") ) {
                //ana_relationship query header
                this.reportFile.newLine(); this.reportFile.newLine();
                this.reportFile.write("ANA_RELATIONSHIP" + "\n");
                this.reportFile.newLine();
                this.reportFile.write("================" + "\n");
                this.reportFile.newLine(); this.reportFile.newLine(); 
               
                //summary
                //ana_relationship summary
                report.println();
                report.println("ANA_RELATIONSHIP INSERT SUMMARY");
                report.println("===============================");
            }

           
           if ( newTermList.isEmpty() ){
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
           }
           else{
               //INSERT INTO ANA_RELATIONSHIP
               query = "INSERT INTO ANA_RELATIONSHIP " +
                       "(rel_oid, rel_relationship_type_fk, rel_child_fk, rel_parent_fk, rel_sequence) " + 
                       "VALUES (?, 'part-of', ?, ?, ? )";
               preppie =  this.newConnection.prepareStatement(query);
               String queryProject = "INSERT INTO ANA_RELATIONSHIP_PROJECT " +
                              "(rlp_oid, rlp_relationship_fk, rlp_project_fk, rlp_sequence) " +
                              "VALUES (?, ?, '" + project + "', ?)";
               PreparedStatement preppieProject = this.newConnection.prepareStatement(queryProject);
               
               for(int i = 0; i< newTermList.size(); i++){

                   compie = newTermList.get(i);

                   //reset flagInsert for each new component
                   flagInsert = true;

                   //get parents + group parents
                   ArrayList < String > parents  = new ArrayList<String>();
                   parents.addAll(compie.getPartOf());
                   parents.addAll(compie.getGroupPartOf());
                   
                   //check whether component has any parents, if none issue warning, no need to proceed with insert
                   if ( parents.size()==0 ) {
                       //System.out.println("New Record Warning: New Component " + compie.getID() + " has been created in database " +
                       //                   "with no parent-child relationship entry. No parent has been specified for this component.");
                       report.println( compie.getID() + "- No child-parent record." );
                       report.println( "               " + "- Parent not specified in OBO File." );
                       flagInsert = false;
                   } 

                   for (int j = 0; j< parents.size(); j++){
                       //reset insertflag for each parent
                       flagInsert = true;
                       Component parent = (Component) this.tree.getComponent( parents.get(j) );

                       //check whether parent has been deleted from obo file, do not allow insertion
                       if(parent==null) {
                            //System.out.println("New Record Error: Parent(" + parents.get(j) + ") of Component " + compie.getID() + " has been deleted from OBO file. " +  
                            //                   "No database entry created for parent-child relationship[Parent:" + parents.get(j) + " Child: " + compie.getID());
                            report.println( compie.getID() + "- No child-parent record.");
                            report.println( "               " + "- Parent component" + parents.get(j) + " was specified by OBO File but parent component does not have its own OBO file entry." );
                            report.println( "               " + "- Check for entry of " + parents.get(j) + " in OBO File." );
                            flagInsert = false;
                       }
                       //UPDATED CODE: deleted components are now marked in proposed file as well and appear in the tree under its own root outside abstract anatomy
                       else if ( parent.getStrChangeStatus().equals("DELETED") ){
                           //System.out.println("New Record Error: Parent(" + parents.get(j) + ") of Component " + compie.getID() + " has been specified for deletion by user in proposed OBO file. " +  
                           //                   "No database entry created for parent-child relationship[Parent:" + parents.get(j) + " Child: " + compie.getID());
                           report.println( compie.getID() + "- No child-parent record.");
                           report.println( "               " + "- Parent component " + parent.getID() + " has been deleted from OBO file. ");
                           flagInsert = false;
                       }
                       //check whether any rules broken for each parent and print warning
                       //ignore any kind of rule violation for relationship record insertion except missing parent
                       else if ( parent.getStrRuleStatus().equals("FAILED") ){
                            //System.out.println("New Record Warning: Inserting Relationship for Component " + compie.getID() + 
                            //                   " to Parent with rule violation: " + parent.getID() );
                            report.println( compie.getID() + " - Child-parent record " + compie.getID() + "-" + parent.getID() + " has been inserted for a parent with a rule violation.");
                            report.println( "               " + "- Parent component " + parent.getID() + " has a rule violation." );
                       }
                       //if parent is root Tmp new group don't treat as relationship
                       else if ( !parent.getNamespace().equals( this.abstractClass.getNamespace() ) ){
                           flagInsert = false;
                       }
                   
                       //proceed with insertion 
                       if (flagInsert){
                           Component insertRelObject = new Component();
                           insertRelObject.setID( compie.getDBID() ); //child
                           
                           String strParentDBID = "";
                           //get DBID for parent 
                           if ( parent.getStrChangeStatus().equals("NEW") ){
                                strParentDBID = parent.getDBID();
                           }
                           //if component is not new 
                           //else if ( parent.getStrChangeStatus().equals("CHANGED") ||  ){
                           else{
                                query = "SELECT ano_oid FROM ANA_NODE WHERE ano_public_id = '" + 
                                               parent.getID() + "'";
                                oidRS = this.newConnection.createStatement().executeQuery(query);
                                if ( oidRS.next() ) {
                                    strParentDBID = Integer.toString( oidRS.getInt("ano_oid") );
                                    //set dbid for parent component
                                    //parent.setDBID( Integer.toString(intREL_PARENT_FK) ) ;
                                }
                           }
                           //get order for child based on parent
                           orders = compie.getOrderCommentOnParent( parent.getID() );
                           if ( orders!=null ){
                               String[] arrayFirstWord = orders[0].split(" ");
                               insertRelObject.setOrderComment(arrayFirstWord[0]);
                           } else{
                               insertRelObject.setOrderComment("");
                           }

                           insertRelObject.addPartOf( strParentDBID ); //parent dbid
                           insertRelObjects.add( insertRelObject );
                       }
                   }
               }
               
               //INSERT INTO ANA_RELATIONSHIP
               if ( insertRelObjects.isEmpty() ) {
                   reportFile.write(" -- No Records Inserted -- ");
               }
               else{
                   //INSERT INTO ANA_OBJECT and set DBIDs
                   insertANA_OBJECT( insertRelObjects, "ANA_RELATIONSHIP" );
                   
                   //INSERT timed components into ANA_TIMED_NODE
                   this.reportFile.newLine();
                   this.reportFile.write("-- Inserting Relationships --");
                   this.reportFile.newLine(); this.reportFile.newLine();
                   
                   //INSERT INTO ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT
                   for ( Component insertRelObject : insertRelObjects ){
                        int intREL_OID = Integer.parseInt( insertRelObject.getDBID() );
                        //String strREL_RELATIONSHIP_TYPE_FK = "part-of";
                        int intREL_CHILD_FK = Integer.parseInt( insertRelObject.getID() );
                        int intREL_SEQUENCE = -1;
                        if ( !insertRelObject.getOrderComment().equals("") ){
                            intREL_SEQUENCE = Integer.parseInt( insertRelObject.getOrderComment() );
                        }
                        try{
                            int intTryREL_PARENT_FK = Integer.parseInt( insertRelObject.getPartOf().get(0) );
                        }catch(Exception e){
                            System.out.println("Exception caught for child " + insertRelObject.getID() + " parent " + insertRelObject.getPartOf().toString() );
                            e.printStackTrace();
                        }
                        int intREL_PARENT_FK = Integer.parseInt( insertRelObject.getPartOf().get(0) );
                        //int intREL_SEQUENCE = 0;

                        strSeq = (intREL_SEQUENCE==-1) ? "null" : Integer.toString(intREL_SEQUENCE);
                        preppie.setInt( 1, intREL_OID );
                        preppie.setInt( 2, intREL_CHILD_FK );
                        preppie.setInt( 3, intREL_PARENT_FK );
                        if ( strSeq.equals("null") ){
                            preppie.setNull( 4, java.sql.Types.INTEGER );
                        } else{
                            preppie.setInt( 4, intREL_SEQUENCE );
                        }
                        /*System.out.println( "INSERT INTO ANA_RELATIONSHIP " +
                            "(rel_oid, rel_relationship_type_fk, rel_child_fk, rel_parent_fk, rel_sequence) " +
                            "VALUES (" + intREL_OID + ", 'part-of', " + intREL_CHILD_FK + ", " +
                            intREL_PARENT_FK + ", " + strSeq + ")" );*/
                        //if( this.flagUpdateDB ) preppie.execute();
                        preppie.addBatch();

                        intMAX_PK++; //get max primary key for ana_relationship_project
                        preppieProject.setInt( 1, intMAX_PK );
                        preppieProject.setInt( 2, intREL_OID );
                        if ( strSeq.equals("null") ){
                            preppieProject.setNull( 3, java.sql.Types.INTEGER );
                        } else{
                            preppieProject.setInt( 3, intREL_SEQUENCE );
                        }
                        preppieProject.addBatch();
                        

                        strSeq = (intREL_SEQUENCE==-1) ? "null" : Integer.toString(intREL_SEQUENCE);
                        this.reportFile.write( "INSERT INTO ANA_RELATIONSHIP " +
                            "(rel_oid, rel_relationship_type_fk, rel_child_fk, rel_parent_fk, rel_sequence) " + 
                            "VALUES (" + intREL_OID + ", 'part-of', " + intREL_CHILD_FK + ", " + 
                            intREL_PARENT_FK + ", " + strSeq + ")" );
                        this.reportFile.newLine();
                        this.reportFile.write( "INSERT INTO ANA_RELATIONSHIP_PROJECT " +
                                "(rlp_oid, rlp_relationship_fk, rlp_project_fk, rlp_sequence) " +
                                "VALUES (" + intMAX_PK + ", " + intREL_OID + ", " + project + ", " + strSeq + ")");
                        this.reportFile.newLine();
                        
                   }
               }
               if( this.flagUpdateDB ) {
                   preppie.executeBatch();
                   preppieProject.executeBatch();
               }
               preppie.close();
               preppieProject.close();
               
           }
            //print the number of records inserted for relationship after checks
            report.println("Number of Records Inserted: " + intRecords );
        } catch (Exception ex){
            System.out.println("problem in ana_relationship at query " + batchreturn );
            ex.printStackTrace();
        }  
    }
    
    
    private void insertANA_TIMED_NODE( ArrayList<Component> newTermList, String calledFrom ){
       
        //table desc
        //ATN_OID
        //ATN_NODE_FK
        //ATN_STAGE_FK
        //ATN_STAGE_MODIFIER_FK
        //ATN_PUBLIC_ID
        
        Component compie;
        PreparedStatement preppie = null;
        HashMap<String, Integer> mapStageIDs = mapStageIDs(); 
 
        try{
            
           this.intCurrentPublicID = this.getMaxPublicID();
            
           if ( calledFrom.equals("MODIFY")){
               //ana_timed_node delete summary
                this.reportFile.newLine(); this.reportFile.newLine();
                this.reportFile.write("INSERTION IN ANA_TIMED_NODE FOR CREATION OF LARGER STAGE RANGES FOR MODIFIED COMPONENTS \n");
                this.reportFile.newLine();
                this.reportFile.write("======================================================================================= \n");
                this.reportFile.newLine(); this.reportFile.newLine();        
           }
           else if ( calledFrom.equals("NEW") ) {
               //ana_timed_node report header
               this.reportFile.newLine(); this.reportFile.newLine();
               this.reportFile.write("ANA_TIMED_NODE");
               this.reportFile.newLine();
               this.reportFile.write("==============");
               this.reportFile.newLine(); this.reportFile.newLine();
               
               //summary
               //ana_timed_node summary
               report.println();
               report.println("ANA_TIMED_NODE INSERT SUMMARY");
               report.println("=============================");
           }


           //create timed components in ANA_OBJECT
           this.timedCompList = this.createTimeComponents(newTermList, calledFrom);
                          
           if ( timedCompList.isEmpty() ) {
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
           }
           else{
               //INSERT timed component obj_oids into ANA_OBJECT
               insertANA_OBJECT(timedCompList, "ANA_TIMED_NODE");
            
               //INSERT timed components into ANA_TIMED_NODE
               this.reportFile.newLine();
               this.reportFile.write("-- Inserting Timed Components --");
               this.reportFile.newLine(); this.reportFile.newLine();

               //INSERT INTO ANA_TIMED_NODE
               String query = "INSERT INTO ANA_TIMED_NODE " +
                      "(atn_oid, atn_node_fk, atn_stage_fk, atn_stage_modifier_fk, atn_public_id) " +
                      "VALUES (?, ?, ?, NULL, ?)";
               preppie =  this.newConnection.prepareStatement(query);
               
               for(int k = 0; k< timedCompList.size(); k++){
                   compie = timedCompList.get(k);

                   //prepare values
                   int intATN_OID = Integer.parseInt( compie.getDBID() );
                   int intATN_NODE_FK = Integer.parseInt( compie.getNamespace() );
                   //int intATN_STAGE_FK = compie.getIntStartsAt(); //vStageOID.get(stage - 1)= key for stage
                   int intATN_STAGE_FK = mapStageIDs.get( compie.getStartsAt() );
                   //String strATN_STAGE_MODIFIER_FK //default null
                   String strATN_PUBLIC_ID = compie.getID();

                   //set values for each preparedstatement
                   preppie.setInt(1, intATN_OID);
                   preppie.setInt(2, intATN_NODE_FK);
                   preppie.setInt(3, intATN_STAGE_FK);
                   preppie.setString(4, strATN_PUBLIC_ID);

                   //add to batch
                   preppie.addBatch();

                   //if too slow write code here to execute for every batch of 500 records
                   //and remove finally block

                   //test
                   //System.out.println("INSERT INTO ANA_TIMED_NODE " +
                   //                   "(atn_oid, atn_node_fk, atn_stage_fk, atn_stage_modifier_fk, atn_public_id) " +
                   //                   "VALUES (" + intATN_OID + ", " + intATN_NODE_FK + ", " +  intATN_STAGE_FK + ", NULL, " + strATN_PUBLIC_ID + ")");
                   this.reportFile.write("INSERT INTO ANA_TIMED_NODE " +
                                         "(atn_oid, atn_node_fk, atn_stage_fk, atn_stage_modifier_fk, atn_public_id) " +
                                         "VALUES (" + intATN_OID + ", " + intATN_NODE_FK + ", " +  intATN_STAGE_FK + ", NULL, " + strATN_PUBLIC_ID + ");" + "\n");
                   this.reportFile.newLine();
               }

               if ( this.flagUpdateDB ) preppie.executeBatch();
               preppie.close();
           }
           report.println("Number of Records Inserted in ANA_TIMED_NODE: " + timedCompList.size() );
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void insertANA_SYNONYM( ArrayList<Component> newTermList, String calledFrom ){
        //table desc
        //SYN_OID 
        //SYN_OBJECT_FK
        //SYN_SYNONYM
        
        Component compie;
        PreparedStatement preppie = null;
        this.synonymCompList.clear();

        try{
            
           if ( calledFrom.equals("MODIFY")){
               //ana_timed_node delete summary
                this.reportFile.newLine(); this.reportFile.newLine();
                this.reportFile.write("INSERTION IN ANA_SYNONYM FOR CREATION OF NEW SYNONYMS FOR MODIFIED COMPONENTS \n");
                this.reportFile.newLine();
                this.reportFile.write("============================================================================= \n");
                this.reportFile.newLine(); this.reportFile.newLine();       
           }
           else if ( calledFrom.equals("NEW") ) {
                //ana_synonym report header
                this.reportFile.newLine(); this.reportFile.newLine();
                this.reportFile.write("ANA_SYNONYM \n");
                this.reportFile.newLine();
                this.reportFile.write("=========== \n");
                this.reportFile.newLine(); this.reportFile.newLine();

                //ana_synonym summary
                report.println();
                report.println( "ANA_SYNONYM INSERT SUMMARY" );
                report.println( "==========================" );
           }
            
            //get max oid from referenced database
           this.intCurrentObjectID = this.getMaxObjectID(); 

           
           for(int i = 0; i< newTermList.size(); i++){
            
               compie = newTermList.get(i);
               
               //get parents 
               ArrayList < String > synonyms = compie.getSynonym();
               //System.out.println("synonyms to be created for " + compie.getID() + " " + compie.getName() + " :"  + synonyms );
               
               for (int j = 0; j< synonyms.size(); j++){
                   Component synonymCompie = new Component();
                   synonymCompie.setID( compie.getDBID() );
                   synonymCompie.setName( synonyms.get(j) );
                   this.synonymCompList.add( synonymCompie );
               }
           }
           
           if ( synonymCompList.isEmpty() ) {
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
           }
           else{
               insertANA_OBJECT(synonymCompList, "ANA_SYNONYM");

               //INSERT synonyms into ANA_SYNONYM
               this.reportFile.newLine();
               this.reportFile.write("-- Inserting Synonyms --");
               this.reportFile.newLine(); this.reportFile.newLine();
 
               //INSERT INTO ANA_SYNONYM
               String query = "INSERT INTO ANA_SYNONYM " +
                      "(syn_oid, syn_object_fk, syn_synonym) " + 
                      "VALUES (?, ?, ? )";
               preppie =  this.newConnection.prepareStatement(query);
               
               for( Component synCompie: synonymCompList ){

                   //proceed with insertion
                   int intSYN_OID = Integer.parseInt( synCompie.getDBID() );
                   int intSYN_OBJECT_FK = Integer.parseInt( synCompie.getID() );
                   String strSYN_SYNONYM = synCompie.getName();

                   //set values for each preparedstatement
                   preppie.setInt(1, intSYN_OID);
                   preppie.setInt(2, intSYN_OBJECT_FK);
                   preppie.setString(3, strSYN_SYNONYM);

                   //add to batch
                   preppie.addBatch();

                   //if too slow write code here to execute for every batch of 500 records

                   //test
                   //System.out.println( "INSERT INTO ANA_SYNONYM " +
                   // "(syn_oid, syn_object_fk, syn_synonym) " + 
                   // "VALUES (" + intSYN_OID + ", " + intSYN_OBJECT_FK + ", " + strSYN_SYNONYM + ");" );
                   this.reportFile.write( "INSERT INTO ANA_SYNONYM " +
                    "(syn_oid, syn_object_fk, syn_synonym) " + 
                    "VALUES (" + intSYN_OID + ", " + intSYN_OBJECT_FK + ", '" + strSYN_SYNONYM + "');" + "\n" );
                   this.reportFile.newLine();
               }
               if ( this.flagUpdateDB ) preppie.executeBatch();
               preppie.close();
           }
           report.println( "Number of records Inserted: " + synonymCompList.size() );
           
        } catch (Exception ex){
            ex.printStackTrace();
        }  
    }
    
    
  private void insertANA_LOG(ArrayList<Component> recordTermList){

    //create one record 
    int intLogOID = 0;
    int intLogLoggedOID = 0;
    int intIsPrimary = 0;
    String query = "";
    PreparedStatement preppie = null;
    ResultSet rs = null;
    HashMap<String, String> anoOldValues = new HashMap(); 
    HashMap<String, String> atnOldValues = new HashMap(); 
    HashMap<String, String> relOldValues = new HashMap(); 
    HashMap<String, String> synOldValues = new HashMap(); 
    
    //ANA_NODE columns
    Vector<String> vANOcolumns = new Vector();
    vANOcolumns.add("ANO_COMPONENT_NAME");
    vANOcolumns.add("ANO_IS_PRIMARY");
    vANOcolumns.add("ANO_OID");
    vANOcolumns.add("ANO_PUBLIC_ID");
    vANOcolumns.add("ANO_SPECIES_FK");
    
    //ANA_RELATIONSHIP columns
    Vector<String> vRELcolumns = new Vector();
    vRELcolumns.add("REL_OID");
    vRELcolumns.add("REL_RELATIONSHIP_TYPE_FK");
    vRELcolumns.add("REL_PARENT_FK");
    vRELcolumns.add("REL_CHILD_FK");
    vRELcolumns.add("REL_SEQUENCE");
    
    //ANA_TIMED_NODE columns
    Vector<String> vATNcolumns = new Vector();
    vATNcolumns.add("ATN_OID");
    vATNcolumns.add("ATN_STAGE_FK");
    vATNcolumns.add("ATN_STAGE_MODIFIER_FK");
    vATNcolumns.add("ATN_PUBLIC_ID");
    vATNcolumns.add("ATN_NODE_FK");
    
    //ANA_SYNONYM columns
    Vector<String> vSYNcolumns = new Vector();
    vSYNcolumns.add("SYN_OID");
    vSYNcolumns.add("SYN_OBJECT_FK");
    vSYNcolumns.add("SYN_SYNONYM");
    
    //column values for insertion into ANA_LOG
    int intLOG_OID = 0;
    int intLOG_LOGGED_OID = 0;
    String strLOG_COLUMN_NAME = "";
    String strLOG_OLD_VALUE = "";
    int intLOG_VERSION_FK = this.intCurrentVersionID; //version_oid should be very first obj_oid created for easy tracing

    //column values for selection from ANA_TIMED_NODE
    int intATN_OID = 0;
    int intATN_STAGE_FK = 0;
    String strATN_STAGE_MODIFIER_FK = "";
    String strATN_PUBLIC_ID = "";
    int intATN_NODE_FK = 0;

    //column values for selection from ANA_RELATIONSHIP
    int intREL_OID = 0;
    String strREL_RELATIONSHIP_TYPE_FK = "";
    int intREL_PARENT_FK = 0;
    int intREL_CHILD_FK = 0;
    int intREL_SEQUENCE = 0;

    //column values for selection from ANA_SYNONYM
    int intSYN_OID = 0;
    int intSYN_OBJECT_FK = 0;
    String strSYN_SYNONYM = "";

    try{
            //ana_log report header
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("ANA_LOG \n");
            this.reportFile.newLine();
            this.reportFile.write("======= \n");
            this.reportFile.newLine(); this.reportFile.newLine();
        
            //get max log_oid from new database
            ResultSet oidRS = null;
            query = "SELECT MAX(log_oid) as max_oid FROM ana_log";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intLogOID = oidRS.getInt("max_oid");
            oidRS.close();

            //get max log_logged_oid from new database
            query = "SELECT MAX(log_logged_oid) as max_oid FROM ana_log";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intLogLoggedOID = oidRS.getInt("max_oid");
            oidRS.close();

            //INSERT INTO ANA_LOG
            query = "INSERT INTO ANA_LOG " +
                    "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                    "VALUES (?, ?, ?, ?, " + intLOG_VERSION_FK + " )";
            preppie =  this.newConnection.prepareStatement(query);

            if ( recordTermList.isEmpty() ) {
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
            } 
            else{
                //for each component to be deleted
                for (Component component: recordTermList){

                    intIsPrimary = ( component.getIsPrimary() ) ? 1 : 0; 


                    anoOldValues.clear();
                    anoOldValues.put( "ANO_COMPONENT_NAME", component.getName().toString() );
                    anoOldValues.put( "ANO_IS_PRIMARY", Integer.toString( intIsPrimary ));
                    anoOldValues.put( "ANO_OID", component.getDBID().toString() );
                    anoOldValues.put( "ANO_PUBLIC_ID", component.getID().toString() );
                    anoOldValues.put( "ANO_SPECIES_FK", this.strSpecies );//get species from maingui //set from ana_node ano_species_fk)

                    //increment for each component
                    ++intLogLoggedOID;

                    for (String columnName: vANOcolumns){	
                        intLOG_OID = ++intLogOID;
                        intLOG_LOGGED_OID = intLogLoggedOID;
                        strLOG_COLUMN_NAME = columnName;
                        strLOG_OLD_VALUE = anoOldValues.get(columnName);

                        preppie.setInt(1, intLOG_OID);
                        preppie.setInt(2, intLOG_LOGGED_OID);
                        preppie.setString(3, strLOG_COLUMN_NAME);
                        preppie.setString(4, strLOG_OLD_VALUE);
                        //add each record
                        preppie.addBatch();
                        //if ( flagUpdateDB ){
                        //    System.out.println("about to execute: " + query);
                        //    preppie.execute();
                        //}

                        this.reportFile.write( "INSERT INTO ANA_LOG " +
                            "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                            "VALUES (" + intLOG_OID + ", " + intLOG_LOGGED_OID + ", '" +  strLOG_COLUMN_NAME + 
                            "', '" + strLOG_OLD_VALUE + "', " + intLOG_VERSION_FK + ")" );
                        this.reportFile.newLine();
                    }

                    query = "SELECT * FROM ANA_TIMED_NODE WHERE atn_node_fk = " + component.getDBID();
                    rs = this.newConnection.createStatement().executeQuery(query);
                    while ( rs.next() ){
                        intATN_OID = rs.getInt("atn_oid");
                        intATN_STAGE_FK = rs.getInt("atn_stage_fk");
                        strATN_STAGE_MODIFIER_FK = rs.getString("atn_stage_modifier_fk");
                        strATN_PUBLIC_ID = rs.getString("atn_public_id");
                        intATN_NODE_FK = rs.getInt("atn_node_fk");

                        //increment for each timed component record
                        ++intLogLoggedOID;

                        //clear HashMap atnOldValues
                        atnOldValues.clear();
                        atnOldValues.put( "ATN_OID", Integer.toString( intATN_OID ) );
                        atnOldValues.put( "ATN_STAGE_FK", Integer.toString( intATN_STAGE_FK ) );
                        atnOldValues.put( "ATN_STAGE_MODIFIER_FK", strATN_STAGE_MODIFIER_FK );
                        atnOldValues.put( "ATN_PUBLIC_ID", strATN_PUBLIC_ID );
                        atnOldValues.put( "ATN_NODE_FK", Integer.toString( intATN_NODE_FK ) );

                        for (String columnName: vATNcolumns){
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = atnOldValues.get(columnName);

                                preppie.setInt(1, intLOG_OID);
                                preppie.setInt(2, intLOG_LOGGED_OID);
                                preppie.setString(3, strLOG_COLUMN_NAME);
                                preppie.setString(4, strLOG_OLD_VALUE);
                                //add each record
                                preppie.addBatch();
                                /*if ( flagUpdateDB ){
                                    System.out.println("about to execute: " + query);
                                    preppie.execute();
                                }*/

                                this.reportFile.write( "INSERT INTO ANA_LOG " +
                                "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                                "VALUES (" + intLOG_OID + ", " + intLOG_LOGGED_OID + ", '" +  strLOG_COLUMN_NAME + 
                                "', '" + strLOG_OLD_VALUE + "', " + intLOG_VERSION_FK + ")" );
                                this.reportFile.newLine();
                        }     
                    }

                    query = "SELECT * FROM ANA_RELATIONSHIP WHERE rel_child_fk = " + component.getDBID();
                    rs = this.newConnection.createStatement().executeQuery(query);
                    while ( rs.next() ){
                        intREL_OID = rs.getInt("rel_oid");
                        strREL_RELATIONSHIP_TYPE_FK = rs.getString("rel_relationship_type_fk");
                        intREL_PARENT_FK = rs.getInt("rel_parent_fk");
                        intREL_CHILD_FK = rs.getInt("rel_child_fk");
                        intREL_SEQUENCE = rs.getInt("rel_sequence");

                        //increment for each relationship record
                        ++intLogLoggedOID;

                        //clear HashMap relOldValues
                        relOldValues.clear();
                        relOldValues.put( "REL_OID", Integer.toString( intREL_OID ) );
                        relOldValues.put( "REL_RELATIONSHIP_TYPE_FK", strREL_RELATIONSHIP_TYPE_FK );
                        relOldValues.put( "REL_PARENT_FK", Integer.toString( intREL_PARENT_FK ) );
                        relOldValues.put( "REL_CHILD_FK", Integer.toString( intREL_CHILD_FK ) );
                        relOldValues.put( "REL_SEQUENCE", Integer.toString( intREL_SEQUENCE ) );

                        for (String columnName: vRELcolumns){
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = relOldValues.get(columnName);

                                preppie.setInt(1, intLOG_OID);
                                preppie.setInt(2, intLOG_LOGGED_OID);
                                preppie.setString(3, strLOG_COLUMN_NAME);
                                preppie.setString(4, strLOG_OLD_VALUE);
                                //add each record
                                preppie.addBatch();
                                //if ( flagUpdateDB ){
                                //    System.out.println("about to execute: " + query);
                                //    preppie.execute();
                                //}

                                this.reportFile.write( "INSERT INTO ANA_LOG " +
                                "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                                "VALUES (" + intLOG_OID + ", " + intLOG_LOGGED_OID + ", '" +  strLOG_COLUMN_NAME + 
                                "', '" + strLOG_OLD_VALUE + "', " + intLOG_VERSION_FK + ")" );
                                this.reportFile.newLine();
                        }     
                    }

                    query = "SELECT * FROM ANA_SYNONYM WHERE syn_object_fk = " + component.getDBID();
                    rs = this.newConnection.createStatement().executeQuery(query);
                    while ( rs.next() ){
                        intSYN_OID = rs.getInt("syn_oid");
                        intSYN_OBJECT_FK = rs.getInt("syn_object_fk");
                        strSYN_SYNONYM = rs.getString("syn_synonym");

                        //increment for each relationship record
                        ++intLogLoggedOID;

                        //clear HashMap synOldValues
                        synOldValues.clear();
                        synOldValues.put("SYN_OID", Integer.toString(intSYN_OID) );
                        synOldValues.put("SYN_OBJECT_FK", Integer.toString(intSYN_OBJECT_FK) );
                        synOldValues.put("SYN_SYNONYM", strSYN_SYNONYM);

                        for (String columnName: vSYNcolumns){
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = synOldValues.get(columnName);

                                preppie.setInt(1, intLOG_OID);
                                preppie.setInt(2, intLOG_LOGGED_OID);
                                preppie.setString(3, strLOG_COLUMN_NAME);
                                preppie.setString(4, strLOG_OLD_VALUE);
                                //add each record
                                preppie.addBatch();
                                //if ( flagUpdateDB ){
                                //    System.out.println("about to execute: " + query);
                                //    preppie.execute();
                                //}

                                this.reportFile.write( "INSERT INTO ANA_LOG " +
                                "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                                "VALUES (" + intLOG_OID + ", " + intLOG_LOGGED_OID + ", '" +  strLOG_COLUMN_NAME + 
                                "', '" + strLOG_OLD_VALUE + "', " + intLOG_VERSION_FK + ")" );
                                this.reportFile.newLine();
                        }     
                    }
                }
                if ( this.flagUpdateDB ) preppie.executeBatch();
                preppie.close();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
  }

  private ArrayList insertANA_LOG_deletedStages( ArrayList<Component> diffDeleteTimeComponents ){
        
        ArrayList < Component > deleteTimeComponents = new ArrayList < Component >();
        HashMap<String, String> atnOldValues = new HashMap(); 
        int intLogLoggedOID = 0;
        int intLogOID = 0;
        PreparedStatement preppie = null;
        int intStartKey = 0;
        int intEndKey = 0;
        
        //ANA_TIMED_NODE columns
        Vector<String> vATNcolumns = new Vector();
        vATNcolumns.add("ATN_OID");
        vATNcolumns.add("ATN_STAGE_FK");
        vATNcolumns.add("ATN_STAGE_MODIFIER_FK");
        vATNcolumns.add("ATN_PUBLIC_ID");
        vATNcolumns.add("ATN_NODE_FK");
        
        //column values for selection from ANA_TIMED_NODE
        int intATN_OID = 0;
        int intATN_STAGE_FK = 0;
        String strATN_STAGE_MODIFIER_FK = "";
        String strATN_PUBLIC_ID = "";
        int intATN_NODE_FK = 0;
        
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        int intLOG_VERSION_FK = this.intCurrentVersionID; //version_oid should be very first obj_oid created for easy tracing

        
        HashMap<String, Integer> mapStageIDs = this.mapStageIDs();
   
        try{    
            
            //ana_timed_node delete summary
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("INSERTION IN ANA_LOG FOR DELETION OF TIME COMPONENTS FOR MODIFIED STAGE RANGES \n");
            this.reportFile.newLine();
            this.reportFile.write("============================================================================== \n");
            this.reportFile.newLine(); this.reportFile.newLine(); 
            //get max log_oid from new database
            ResultSet oidRS = null;
            String query = "SELECT MAX(log_oid) as max_oid FROM ana_log";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intLogOID = oidRS.getInt("max_oid");
            oidRS.close();

            //get max log_logged_oid from new database
            query = "SELECT MAX(log_logged_oid) as max_oid FROM ana_log";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intLogLoggedOID = oidRS.getInt("max_oid");
            oidRS.close();

            //INSERT INTO ANA_LOG
            query = "INSERT INTO ANA_LOG " +
                    "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                    "VALUES (?, ?, ?, ?, " + intLOG_VERSION_FK + " )";
            preppie =  this.newConnection.prepareStatement(query);
            
            if ( diffDeleteTimeComponents.isEmpty() ) {
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
            }
            else{
                for ( Component compie: diffDeleteTimeComponents ){

                    intStartKey = mapStageIDs.get( compie.getStartsAt() );
                    intEndKey = mapStageIDs.get( compie.getEndsAt() );

                    for ( int stage=intStartKey; stage<=intEndKey; stage++ ){

                        query = "SELECT * FROM ANA_TIMED_NODE WHERE atn_node_fk = " + compie.getDBID() + " " +
                                "AND atn_stage_fk = " + stage;
                        ResultSet rs = this.newConnection.createStatement().executeQuery(query);

                        while ( rs.next() ){ //should have only one record for each abstract timed node+stage combo
                            Component deleteTimeComponent = new Component();
                            deleteTimeComponent.setDBID( rs.getString("atn_oid") ); //dbid
                            deleteTimeComponent.setID( rs.getString("atn_public_id") ); //child
                            deleteTimeComponent.setNamespace( compie.getDBID() ); //abstract term dbid
                            deleteTimeComponent.setStartsAt( stage ); //stage key
                            deleteTimeComponents.add( deleteTimeComponent );
                            
                            //System.out.println("creating timed component for deletion at stage " + stage + ": " + deleteTimeComponent.getDBID() + " " + deleteTimeComponent.getID() + " " + deleteTimeComponent.getIntStartsAt() );
                            
                            intATN_OID = rs.getInt("atn_oid");
                            intATN_STAGE_FK = rs.getInt("atn_stage_fk");
                            strATN_STAGE_MODIFIER_FK = rs.getString("atn_stage_modifier_fk");
                            strATN_PUBLIC_ID = rs.getString("atn_public_id");
                            intATN_NODE_FK = rs.getInt("atn_node_fk");

                            //increment for each timed component record
                            ++intLogLoggedOID;

                            //clear HashMap atnOldValues
                            atnOldValues.clear();
                            atnOldValues.put( "ATN_OID", Integer.toString( intATN_OID ) );
                            atnOldValues.put( "ATN_STAGE_FK", Integer.toString( intATN_STAGE_FK ) );
                            atnOldValues.put( "ATN_STAGE_MODIFIER_FK", strATN_STAGE_MODIFIER_FK );
                            atnOldValues.put( "ATN_PUBLIC_ID", strATN_PUBLIC_ID );
                            atnOldValues.put( "ATN_NODE_FK", Integer.toString( intATN_NODE_FK ) );

                            for (String columnName: vATNcolumns){
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = atnOldValues.get(columnName);

                                preppie.setInt(1, intLOG_OID);
                                preppie.setInt(2, intLOG_LOGGED_OID);
                                preppie.setString(3, strLOG_COLUMN_NAME);
                                preppie.setString(4, strLOG_OLD_VALUE);
                                //add each record
                                preppie.addBatch();

                                this.reportFile.write( "INSERT INTO ANA_LOG " +
                                "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                                "VALUES (" + intLOG_OID + ", " + intLOG_LOGGED_OID + ", '" +  strLOG_COLUMN_NAME + 
                                "', '" + strLOG_OLD_VALUE + "', " + intLOG_VERSION_FK + ")" );
                                this.reportFile.newLine();
                            }     
                        }
                    }    
                }
                if ( this.flagUpdateDB ) preppie.executeBatch();
                preppie.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return deleteTimeComponents;
  }
  
  private ArrayList insertANA_LOG_deletedRels( ArrayList<Component> diffDeleteRels ){
        
        ArrayList< Component > deleteRelComponents = new ArrayList<Component>(); 
        HashMap<String, String> relOldValues = new HashMap(); 
        int intLogLoggedOID = 0;
        int intLogOID = 0;
        PreparedStatement preppie = null;
        ResultSet rs = null;
        ArrayList < String > deleteParents = new ArrayList<String>();
        int intParentDBID = 0;
        
        //ANA_RELATIONSHIP columns
        Vector<String> vRELcolumns = new Vector();
        vRELcolumns.add("REL_OID");
        vRELcolumns.add("REL_RELATIONSHIP_TYPE_FK");
        vRELcolumns.add("REL_PARENT_FK");
        vRELcolumns.add("REL_CHILD_FK");
        vRELcolumns.add("REL_SEQUENCE");
        
        //column values for selection from ANA_RELATIONSHIP
        int intREL_OID = 0;
        String strREL_RELATIONSHIP_TYPE_FK = "";
        int intREL_PARENT_FK = 0;
        int intREL_CHILD_FK = 0;
        int intREL_SEQUENCE = 0;
        
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        int intLOG_VERSION_FK = this.intCurrentVersionID; //version_oid should be very first obj_oid created for easy tracing

   
        try{    
            //ana_timed_node delete summary
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("INSERTION IN ANA_LOG FOR DELETION OF PARENT RELATIONSHIPS OF MODIFIED COMPONENTS \n");
            this.reportFile.newLine();
            this.reportFile.write("================================================================================ \n");
            this.reportFile.newLine(); this.reportFile.newLine();
            //get max log_oid from new database
            ResultSet oidRS = null;
            String query = "SELECT MAX(log_oid) as max_oid FROM ana_log";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intLogOID = oidRS.getInt("max_oid");
            oidRS.close();

            //get max log_logged_oid from new database
            query = "SELECT MAX(log_logged_oid) as max_oid FROM ana_log";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intLogLoggedOID = oidRS.getInt("max_oid");
            oidRS.close();

            //INSERT INTO ANA_LOG
            query = "INSERT INTO ANA_LOG " +
                    "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                    "VALUES (?, ?, ?, ?, " + intLOG_VERSION_FK + " )";
            preppie =  this.newConnection.prepareStatement( query );
            
            if ( diffDeleteRels.isEmpty() ) {
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
            }
            else {
                for ( Component compie: diffDeleteRels ){

                    deleteParents = compie.getPartOf();

                    for ( String deleteParent: deleteParents ){

                        intParentDBID = this.getDBID( deleteParent ); 

                        query = "SELECT * FROM ANA_RELATIONSHIP " +
                                "WHERE rel_child_fk = " + compie.getDBID() + " " + 
                                "AND rel_parent_fk = " + intParentDBID;
                        rs = this.newConnection.createStatement().executeQuery( query );
                        //should only have one record!
                        if ( rs.next() ){
                            Component deleteRelComponent = new Component();
                            deleteRelComponent.setDBID( rs.getString("rel_oid") ); //dbid
                            deleteRelComponent.setID( compie.getDBID() ); //child
                            deleteRelComponent.addPartOf( Integer.toString( intParentDBID ) ); //parent
                            deleteRelComponents.add( deleteRelComponent );

                            intREL_OID = rs.getInt("rel_oid");
                            strREL_RELATIONSHIP_TYPE_FK = rs.getString("rel_relationship_type_fk");
                            intREL_PARENT_FK = rs.getInt("rel_parent_fk");
                            intREL_CHILD_FK = rs.getInt("rel_child_fk");
                            intREL_SEQUENCE = rs.getInt("rel_sequence");

                            //increment for each relationship record
                            ++intLogLoggedOID;

                            //clear HashMap relOldValues
                            relOldValues.clear();
                            relOldValues.put( "REL_OID", Integer.toString( intREL_OID ) );
                            relOldValues.put( "REL_RELATIONSHIP_TYPE_FK", strREL_RELATIONSHIP_TYPE_FK );
                            relOldValues.put( "REL_PARENT_FK", Integer.toString( intREL_PARENT_FK ) );
                            relOldValues.put( "REL_CHILD_FK", Integer.toString( intREL_CHILD_FK ) );
                            relOldValues.put( "REL_SEQUENCE", Integer.toString( intREL_SEQUENCE ) );

                            for (String columnName: vRELcolumns){
                                    intLOG_OID = ++intLogOID;
                                    intLOG_LOGGED_OID = intLogLoggedOID;
                                    strLOG_COLUMN_NAME = columnName;
                                    strLOG_OLD_VALUE = relOldValues.get(columnName);

                                    preppie.setInt(1, intLOG_OID);
                                    preppie.setInt(2, intLOG_LOGGED_OID);
                                    preppie.setString(3, strLOG_COLUMN_NAME);
                                    preppie.setString(4, strLOG_OLD_VALUE);
                                    //add each record
                                    preppie.addBatch();

                                    this.reportFile.write("INSERT INTO ANA_LOG " +
                                    "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                                    "VALUES (" + intLOG_OID + ", " + intLOG_LOGGED_OID + ", '" +  strLOG_COLUMN_NAME + 
                                    "', '" + strLOG_OLD_VALUE + "', " + intLOG_VERSION_FK + ")");
                                    this.reportFile.newLine();
                            }     
                        }
                        else {
                            this.report.println("Delete Record Info: Attempt to remove component " 
                                    +  compie.getID() + " " + compie.getName() + "'s linkage to parent " + deleteParent + 
                                    " did not proceed. No parent-child relationship record for " + deleteParent + "-" + 
                                    compie.getID() + "could be found in ANA_RELATIONSHIP.");
                        }
                    }
                }
                if ( this.flagUpdateDB ) preppie.executeBatch();
                preppie.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return deleteRelComponents;
    }
  
    private ArrayList insertANA_LOG_deletedSyns( ArrayList<Component> diffDeleteSyns ){
        
        //System.out.println("inserting records into ana_log for deleting synonyms of " + diffDeleteSyns);
        
        ArrayList< Component > deleteSynComponents = new ArrayList<Component>(); 
        HashMap<String, String> synOldValues = new HashMap(); 
        int intLogLoggedOID = 0;
        int intLogOID = 0;
        PreparedStatement preppie = null;
        ResultSet rs = null;
        ArrayList < String > deleteSynonyms = new ArrayList<String>();
        
        //ANA_SYNONYM columns
        Vector<String> vSYNcolumns = new Vector();
        vSYNcolumns.add("SYN_OID");
        vSYNcolumns.add("SYN_OBJECT_FK");
        vSYNcolumns.add("SYN_SYNONYM");

        //column values for selection from ANA_SYNONYM
        int intSYN_OID = 0;
        int intSYN_OBJECT_FK = 0;
        String strSYN_SYNONYM = "";
        
        //column values for insertion into ANA_LOG
        int intLOG_OID = 0;
        int intLOG_LOGGED_OID = 0;
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        int intLOG_VERSION_FK = this.intCurrentVersionID; //version_oid should be very first obj_oid created for easy tracing

   
        try{    
            //ana_timed_node delete summary
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("INSERTION IN ANA_LOG FOR DELETION OF SYNONYMS OF MODIFIED COMPONENTS \n");
            this.reportFile.newLine();
            this.reportFile.write("==================================================================== \n");
            this.reportFile.newLine(); this.reportFile.newLine();
            //get max log_oid from new database
            ResultSet oidRS = null;
            String query = "SELECT MAX(log_oid) as max_oid FROM ana_log";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intLogOID = oidRS.getInt("max_oid");
            oidRS.close();

            //get max log_logged_oid from new database
            query = "SELECT MAX(log_logged_oid) as max_oid FROM ana_log";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intLogLoggedOID = oidRS.getInt("max_oid");
            oidRS.close();

            //INSERT INTO ANA_LOG
            query = "INSERT INTO ANA_LOG " +
                    "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                    "VALUES (?, ?, ?, ?, " + intLOG_VERSION_FK + " )";
            preppie =  this.newConnection.prepareStatement( query );
            
            if ( diffDeleteSyns.isEmpty() ) {
               this.reportFile.write("-- No records inserted --");
               this.reportFile.newLine();
            }
            else {
                for ( Component compie: diffDeleteSyns ){

                    deleteSynonyms = compie.getSynonym();

                    for ( String deleteSynonym: deleteSynonyms ){

                        query = "SELECT syn_oid FROM ANA_SYNONYM " +
                                "WHERE syn_object_fk = " + compie.getDBID() + " " +
                                "AND syn_synonym = '" + deleteSynonym + "'";
                        rs = this.newConnection.createStatement().executeQuery( query );
                        //should only have one record!
                        if ( rs.next() ){
                            Component deleteSynComponent = new Component();
                            deleteSynComponent.setName( deleteSynonym );
                            deleteSynComponent.setDBID( rs.getString("syn_oid") );
                            deleteSynComponent.setID( compie.getDBID() );//temp use 
                            deleteSynComponents.add( deleteSynComponent );
                            //System.out.println("deleting synonym with object id " + deleteSynComponent.getDBID() + " from term " + deleteSynComponent.getName() + " " + deleteSynComponent.getID() );

                            //set record values
                            intSYN_OID = rs.getInt("syn_oid");
                            intSYN_OBJECT_FK = Integer.parseInt( compie.getDBID() );
                            strSYN_SYNONYM = deleteSynonym;

                            //increment for each relationship record
                            ++intLogLoggedOID;

                            //clear HashMap synOldValues
                            synOldValues.clear();
                            synOldValues.put("SYN_OID", Integer.toString(intSYN_OID) );
                            synOldValues.put("SYN_OBJECT_FK", Integer.toString(intSYN_OBJECT_FK) );
                            synOldValues.put("SYN_SYNONYM", strSYN_SYNONYM);

                            for (String columnName: vSYNcolumns){
                                intLOG_OID = ++intLogOID;
                                intLOG_LOGGED_OID = intLogLoggedOID;
                                strLOG_COLUMN_NAME = columnName;
                                strLOG_OLD_VALUE = synOldValues.get(columnName);

                                preppie.setInt(1, intLOG_OID);
                                preppie.setInt(2, intLOG_LOGGED_OID);
                                preppie.setString(3, strLOG_COLUMN_NAME);
                                preppie.setString(4, strLOG_OLD_VALUE);
                                //add each record
                                preppie.addBatch();

                                this.reportFile.write( "INSERT INTO ANA_LOG " +
                                "(log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) " + 
                                "VALUES (" + intLOG_OID + ", " + intLOG_LOGGED_OID + ", '" +  strLOG_COLUMN_NAME + 
                                "', '" + strLOG_OLD_VALUE + "', " + intLOG_VERSION_FK + ")" );
                                this.reportFile.newLine();
                            }     
                        }
                        else {
                            this.report.println("Delete Record Info: Attempt to delete synonym " + deleteSynonym + 
                                    " belonging to " +  compie.getID() + " " + compie.getName() + 
                                    " did not proceed. No synonym record for " + deleteSynonym + 
                                    " could be found in ANA_RELATIONSHIP.");
                        }
                    }
                }
                if ( this.flagUpdateDB ) preppie.executeBatch();
                preppie.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return deleteSynComponents;
      
    }

    
    private void deleteComponentFromTables (ArrayList <Component> validDeleteTermList){
        
        String query = "";
        int intDeleted = 0; //write code to use for report purposes
        Vector<String> deleteQueries = new Vector<String>();
        PreparedStatement preppieATN = null;
        PreparedStatement preppieSYN = null;
        PreparedStatement preppieREL = null;
        PreparedStatement preppieDeleteObjects = null;
        ResultSet rs = null;

        deleteQueries.add("DELETE FROM ANAD_RELATIONSHIP_TRANSITIVE WHERE rtr_descendent_fk = ");
        deleteQueries.add("DELETE FROM ANAD_PART_OF WHERE apo_node_fk = ");
        deleteQueries.add("DELETE FROM ANA_TIMED_NODE WHERE atn_node_fk = "); //not enough!! have to delete obj_oids for timed_components
        deleteQueries.add("DELETE FROM ANA_NODE WHERE ano_oid = ");
        deleteQueries.add("DELETE FROM ANA_RELATIONSHIP WHERE rel_child_fk = ");
        deleteQueries.add("DELETE FROM ANA_SYNONYM WHERE syn_object_fk = "); //have to delete obj_oids for synonyms!
        deleteQueries.add("DELETE FROM ANA_OBJECT WHERE obj_oid = ");

        String deleteProjectQuery = "DELETE FROM ANA_RELATIONSHIP_PROJECT WHERE rlp_relationship_fk = ";

        try{
            //delete component report header
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("DELETION FROM ANA_OBJECT, ANA_NODE, ANA_RELATIONSHIP, ANA_TIMED_NODE, ANA_SYNONYM \n");
            this.reportFile.newLine();
            this.reportFile.write("================================================================================= \n");
            this.reportFile.newLine(); this.reportFile.newLine();         

            //select object ids for all relationships from ANA_RELATIONSHIP
            query = "SELECT rel_oid FROM ANA_RELATIONSHIP WHERE rel_child_fk = ?";
            preppieREL = this.newConnection.prepareStatement( query );
            
            //select object ids for all timed components from ANA_TIMED_NODE
            query = "SELECT atn_oid FROM ANA_TIMED_NODE WHERE atn_node_fk = ?";
            preppieATN = this.newConnection.prepareStatement( query );
            
            //select object ids for all synonyms from ANA_SYNONYM
            query = "SELECT syn_oid FROM ANA_SYNONYM WHERE syn_object_fk = ?";
            preppieSYN = this.newConnection.prepareStatement( query );
            
            //delete from ana_object
            query = "DELETE FROM ANA_OBJECT WHERE obj_oid = ?";
            preppieDeleteObjects = this.newConnection.prepareStatement(query);
            
            if ( validDeleteTermList.isEmpty() ) {
               this.reportFile.write("-- No records deleted --");
               this.reportFile.newLine();
            }
            else {
                for (Component compie: validDeleteTermList){
                    this.reportFile.write("-- Deleting component " + compie.getID() + " " + compie.getName() + " --" );
                    this.reportFile.newLine();
                    this.reportFile.write("-- Deleting " + compie.getID() + "'s relationship object IDs --");
                    this.reportFile.newLine();
                    this.reportFile.write("-- Deleting " + compie.getID() + "'s time component object IDs --");
                    this.reportFile.newLine();
                    this.reportFile.write("-- Deleting " + compie.getID() + "'s synonym object IDs --");
                    this.reportFile.newLine();

                    //delete ana_relationship_project that have foreign key constraints on ana_Relationship
                    preppieREL.setInt(1, Integer.parseInt( compie.getDBID() ) );
                    rs = preppieREL.executeQuery();
                    while ( rs.next() ){
                        deleteProjectQuery = deleteProjectQuery + rs.getInt("rel_oid");
                        if ( this.flagUpdateDB ) intDeleted = this.newConnection.createStatement().executeUpdate(deleteProjectQuery);
                        this.reportFile.write(deleteProjectQuery);
                        this.reportFile.newLine();
                    }

                    //delete main records that have foreign key constraints
                    for (String deleteQuery: deleteQueries){
                        query = deleteQuery + compie.getDBID();
                        if ( this.flagUpdateDB ) intDeleted = this.newConnection.createStatement().executeUpdate(query);
                        this.reportFile.write(query);
                        this.reportFile.newLine();
                    }

                    
                    //delete obj_oids for relationships
                    preppieREL.setInt(1, Integer.parseInt( compie.getDBID() ) );
                    rs = preppieREL.executeQuery();
                    while ( rs.next() ){
                        preppieDeleteObjects.setInt(1, rs.getInt("rel_oid"));
                        preppieDeleteObjects.addBatch();
                        this.reportFile.write("DELETE FROM ANA_OBJECT WHERE obj_oid = " + rs.getInt("rel_oid"));
                        this.reportFile.newLine();
                    }

                    //delete obj_oids for timed components
                    preppieATN.setInt(1, Integer.parseInt( compie.getDBID() ) );
                    rs = preppieATN.executeQuery();
                    while ( rs.next() ){
                        preppieDeleteObjects.setInt(1, rs.getInt("atn_oid"));
                        preppieDeleteObjects.addBatch();
                        this.reportFile.write("DELETE FROM ANA_OBJECT WHERE obj_oid = " + rs.getInt("atn_oid"));
                        this.reportFile.newLine();
                    }


                    //delete obj_oids for synonyms
                    preppieSYN.setInt(1, Integer.parseInt( compie.getDBID() ) );
                    rs = preppieSYN.executeQuery();
                    while ( rs.next() ){
                        preppieDeleteObjects.setInt(1, rs.getInt("syn_oid"));
                        preppieDeleteObjects.addBatch();
                        this.reportFile.write("DELETE FROM ANA_OBJECT WHERE obj_oid = " + rs.getInt("syn_oid"));
                        this.reportFile.newLine();
                    }
                    this.reportFile.newLine();

                    if ( this.flagUpdateDB ) preppieDeleteObjects.executeBatch();
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /*
     * Reorders a collection of siblings that have order sequence entries in the ANA_RELATIONSHIP
     *   when one of the siblings have been deleted, re-ordering is based on original order and closes
     *   the sequence gap left by the deleted sibling
     * NOTE: function is obsolete if editor re-orders the remaining siblings, current rules
     * checking do not allow database to be updated if the ordering has a gap anyway
     */
    private void reorderANA_RELATIONSHIP(ArrayList <Component> validDeleteTermList, String project){
        ArrayList <String> compieParents = new ArrayList<String>();
        String skipRecords = "";
        Integer skipOID = -1;
        int parentDBID = -1;
        int childDBID = -1;
        int intREL_OID = -1;
        int intSEQ = -1;
        
        String queryChildren = "SELECT rlp_relationship_fk, rlp_sequence "
                                + "FROM ANA_RELATIONSHIP_PROJECT, ANA_RELATIONSHIP "
                                + "WHERE rel_parent_fk = ? "
                                + "AND rlp_sequence IS NOT NULL "
                                + "AND rlp_project_fk = ? "
                                + "AND rel_oid = rlp_relationship_fk ";
        PreparedStatement preppieChildren = null;
        ResultSet rsChildren = null;

        String queryParents = "SELECT ano_public_id, rlp_sequence, rlp_oid FROM ANA_NODE, ANA_RELATIONSHIP, ANA_RELATIONSHIP_PROJECT "
                                + "WHERE rel_child_fk = ? AND rel_parent_fk = ano_oid "
                                + "AND rel_oid = rlp_relationship_fk";
        PreparedStatement preppieParents = null;
        ResultSet rsParents = null;

        String queryReorder = "UPDATE ANA_RELATIONSHIP_PROJECT SET rlp_sequence = ? WHERE rlp_relationship_fk = ? AND rlp_project_fk = '" + project + "'";
        PreparedStatement preppieReorder = null;

        try{
            //reorder component report header
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("REORDERING REL_SEQUENCE IN ANA_RELATIONSHIP FOR DELETED COMPONENTS \n");
            this.reportFile.newLine();
            this.reportFile.write("================================================================== \n");
            this.reportFile.newLine(); this.reportFile.newLine();

            if ( validDeleteTermList.isEmpty() ) {
               this.reportFile.write("-- No records reordered --");
               this.reportFile.newLine();
            }
            else {
                //for each component to be deleted
                for (Component compie: validDeleteTermList){
                    //get all parent-child relationship entries with ordering
                    childDBID = this.getDBID(compie.getID());
                    preppieParents = this.newConnection.prepareCall( queryParents );
                    preppieParents.setInt(1, childDBID);
                    rsParents = preppieParents.executeQuery();
                    skipRecords = "";
                    while ( rsParents.next() ){
                        if (rsParents.getString("rlp_sequence")!=null){
                            compieParents.add( rsParents.getString("ano_public_id") );
                            //make a list of parent-child relationship entries to be deleted
                            skipRecords = skipRecords + rsParents.getInt("rlp_oid") + ",";
                        }
                    }
                }
                if (!skipRecords.equals("")){
                    skipRecords = skipRecords.substring(0, skipRecords.length()-1);
                    skipRecords = "(" + skipRecords + ")";
                    //get all relationship entries of deleted component's siblings
                    queryChildren = queryChildren + "AND rlp_oid NOT IN " + skipRecords + " ORDER BY rlp_sequence";
                }
                //prepare reorder update query
                preppieReorder = this.newConnection.prepareStatement( queryReorder );
                //check for each parent whether there is ordering
                for (String parent: compieParents){
                    //get parent dbid
                    parentDBID = this.getDBID(parent);
                    //reset REL_SEQ
                    intSEQ = 0;
                    //get all children records from ANA_RELATIONSHIP_PROJECT for this parent
                    //that has an order sequence entry, order by sequence
                    //exclude entries that are scheduled for deletion
                    preppieChildren = this.newConnection.prepareStatement( queryChildren );
                    preppieChildren.setInt(1, parentDBID);
                    preppieChildren.setString(2, "GUDMAP");
                    rsChildren = preppieChildren.executeQuery();
                    preppieReorder = this.newConnection.prepareStatement( queryReorder );
                    //reorder all child records that have a rel_seq entry
                    while (rsChildren.next()){
                        intREL_OID = rsChildren.getInt("rlp_relationship_fk");
                        preppieReorder.setInt( 1, intSEQ );
                        preppieReorder.setInt( 2, intREL_OID );
                        preppieReorder.addBatch();

                        this.reportFile.write("UPDATE ANA_RELATIONSHIP_PROJECT SET rlp_sequence = " + intSEQ +
                              " WHERE rlp_relationship_fk = " + intREL_OID );
                        this.reportFile.newLine();
                        //increment rel_seq for next record
                        intSEQ++;
                    }
                }
                if ( this.flagUpdateDB ) preppieReorder.executeBatch();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void update_orderANA_RELATIONSHIP( HashMap<String, ArrayList<String>> mapParentChildren){
        String parent = "";
        int parentDBID = -1;
        int childDBID = -1;
        int intREL_OID = -1;
        int intSEQ = -1;
        int intNumRecords = 0;
        ArrayList<String> orderedchildren = new ArrayList<String>();

        String queryOID = "SELECT rel_oid FROM ANA_RELATIONSHIP WHERE rel_parent_fk = ? AND rel_child_fk = ?";
        PreparedStatement preppieOID = null;
        ResultSet rsOID = null;

        String queryRels = "SELECT rel_oid FROM ANA_RELATIONSHIP WHERE rel_parent_fk = ?";
        PreparedStatement preppieRels = null;
        ResultSet rsRels = null;

        String queryRelsUnordered = "SELECT rel_oid FROM ANA_RELATIONSHIP WHERE rel_parent_fk = ? ";
        PreparedStatement preppieRelsUnordered = null;
        ResultSet rsRelsUnordered = null;

        String queryUpdateProject = "UPDATE ANA_RELATIONSHIP_PROJECT SET rlp_sequence = ? WHERE rlp_relationship_fk = ?";
        PreparedStatement preppieUpdateProject = null;
        
        String queryUpdate = "UPDATE ANA_RELATIONSHIP SET rel_sequence = ? WHERE rel_oid = ?";
        PreparedStatement preppieUpdate = null;

        try{
            //reorder component report header
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("REORDERING REL_SEQUENCE IN ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT FOR MODIFIED COMPONENTS \n");
            this.reportFile.newLine();
            this.reportFile.write("================================================================================================ \n");
            this.reportFile.newLine(); this.reportFile.newLine();

            if ( mapParentChildren.isEmpty() ) {
               this.reportFile.write("-- No records reordered --");
               this.reportFile.newLine();
            }
            else {
                //prepare update statement
                preppieUpdateProject = this.newConnection.prepareStatement( queryUpdateProject );
                preppieUpdate = this.newConnection.prepareStatement( queryUpdate );
                preppieRels = this.newConnection.prepareStatement( queryRels );
                //for each entry in the map
                for ( Iterator<String> i = mapParentChildren.keySet().iterator(); i.hasNext(); ){
                    parent = i.next();
                    //get dbid of parent
                    parentDBID = this.getDBID(parent);
                    intSEQ = -1; //reset order for each parent
                    intNumRecords = 0; //reset number of child records for each parent
                    //get number of child records for each parent from database
                    preppieRels.setInt(1, parentDBID);
                    rsRels = preppieRels.executeQuery();
                    while ( rsRels.next() ){ intNumRecords++; }
                    //iterate through all children for each parent
                    //if children==null, no child has an order in the proposed file
                    //update all relationship entries to sequence=null
                    if ( mapParentChildren.get(parent)==null ){
                        rsRels.beforeFirst();
                        while ( rsRels.next() ){
                            intREL_OID = rsRels.getInt("rel_oid");
                            //update ana_relationship_project
                            preppieUpdateProject.setNull(1, java.sql.Types.INTEGER);
                            preppieUpdateProject.setInt(2, intREL_OID);
                            preppieUpdateProject.addBatch();
                            //update ana_relationship
                            preppieUpdate.setNull(1, java.sql.Types.INTEGER);
                            preppieUpdate.setInt(2, intREL_OID);
                            preppieUpdate.addBatch();

                            this.reportFile.write("UPDATE ANA_RELATIONSHIP_PROJECT SET rlp_sequence = null WHERE rlp_relationship_fk = " + intREL_OID);
                            this.reportFile.newLine();
                            this.reportFile.write("UPDATE ANA_RELATIONSHIP SET rel_sequence = null WHERE rel_oid = " + intREL_OID);
                            this.reportFile.newLine();
                        }
                    } else{
                        for (String child: mapParentChildren.get(parent)){
                            //get dbid of child
                            childDBID = this.getDBID(child);
                            //get rel_oid of this relationship
                            preppieOID = this.newConnection.prepareStatement( queryOID );
                            preppieOID.setInt(1, parentDBID);
                            preppieOID.setInt(2, childDBID);
                            rsOID = preppieOID.executeQuery();
                            if ( rsOID.next() ) { //prepare update query if relationship record found
                                intREL_OID = rsOID.getInt("rel_oid");
                                //put in ordered children rows cache for later comparison
                                orderedchildren.add( Integer.toString(intREL_OID) );
                                intSEQ++;
                                //update ana_relationship_project
                                preppieUpdateProject.setInt(1, intSEQ);
                                preppieUpdateProject.setInt(2, intREL_OID);
                                preppieUpdateProject.addBatch();
                                //update ana_relationship
                                preppieUpdate.setInt(1, intSEQ);
                                preppieUpdate.setInt(2, intREL_OID);
                                preppieUpdate.addBatch();

                                this.reportFile.write("UPDATE ANA_RELATIONSHIP_PROJECT SET rlp_sequence = " + intSEQ + " WHERE rlp_relationship_fk = " + intREL_OID);
                                this.reportFile.newLine();
                                this.reportFile.write("UPDATE ANA_RELATIONSHIP SET rel_sequence = " + intSEQ + " WHERE rel_oid = " + intREL_OID);
                                this.reportFile.newLine();
                            } else{ //if no relationship record found, child component is a new component
                                intSEQ++; //skip this sequence number
                                //entry will be inserted into ana_relationship/project later for new components
                            }
                            preppieOID.close();
                            rsOID.close();
                        }
                    }
                    //if there are more child records than the number of ordered children
                    //fill up the rest with rel_sequence = null
                    if ( orderedchildren.size()<intNumRecords ){
                        //prepare string of ordered rel_oid
                        String strOrdered = "";
                        for(String orderedchild: orderedchildren ){
                            strOrdered = strOrdered + orderedchild + ",";
                        }
                        if ( !strOrdered.equals("") ) {
                            strOrdered = strOrdered.substring(0, strOrdered.length()-1);
                            queryRelsUnordered = queryRelsUnordered + "(" + strOrdered + ")";
                        }
                        //get all relationship entries that are unordered
                        preppieRelsUnordered = this.newConnection.prepareStatement( queryRelsUnordered );
                        preppieRelsUnordered.setInt(1, parentDBID);
                        rsRelsUnordered = preppieRelsUnordered.executeQuery();
                        while (rsRelsUnordered.next()){
                            intREL_OID = rsRelsUnordered.getInt("rel_oid");
                            //update ana_relationship_project
                            preppieUpdateProject.setNull(1, java.sql.Types.INTEGER);
                            preppieUpdateProject.setInt(2, intREL_OID);
                            preppieUpdateProject.addBatch();
                            //update ana_relationship
                            preppieUpdate.setNull(1, java.sql.Types.INTEGER);
                            preppieUpdate.setInt(2, intREL_OID);
                            preppieUpdate.addBatch();

                            this.reportFile.write("UPDATE ANA_RELATIONSHIP_PROJECT SET rlp_sequence = null WHERE rlp_relationship_fk = " + intREL_OID);
                            this.reportFile.newLine();
                            this.reportFile.write("UPDATE ANA_RELATIONSHIP SET rel_sequence = null WHERE rel_oid = " + intREL_OID);
                            this.reportFile.newLine();
                        }
                        preppieRelsUnordered.close();
                        rsRelsUnordered.close();
                    }
                    rsRels.close();
                }
                if ( this.flagUpdateDB ){
                    preppieUpdateProject.executeBatch();
                    preppieUpdate.executeBatch();
                }
                preppieUpdate.close();
                preppieUpdateProject.close();
                preppieRels.close();
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void deleteANA_OBJECT( ArrayList<Component> deleteObjects, String calledFromTable ){
        
        try{
            //ana_object delete summary
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("DELETION FROM ANA_OBJECT FOR MODIFIED COMPONENTS");
            this.reportFile.newLine();
            this.reportFile.write("================================================");
            this.reportFile.newLine(); this.reportFile.newLine();
            
            if ( calledFromTable.equals("ANA_TIMED_NODE") ){
                this.reportFile.write("-- Deleting Object IDs of Timed Components for Shortening of Stage Ranges in Modified Components --");
                this.reportFile.newLine(); this.reportFile.newLine();
                report.println( "Number of OID Records Deleted for Timed Components in ANA_OBJECT: " + deleteObjects.size() );
            }
            else if ( calledFromTable.equals("ANA_SYNONYM") ){
                this.reportFile.write("-- Deleting Object IDs of Synonyms for Removal of Synonyms in Modified Components --");
                this.reportFile.newLine(); this.reportFile.newLine();
                report.println( "Number of OID Records Deleted for Synonyms in ANA_OBJECT: " + deleteObjects.size() );
            }
            else if ( calledFromTable.equals("ANA_RELATIONSHIP") ){
                this.reportFile.write("-- Deleting Object IDs of Relationships for Removal of Parents in Modified Components --");
                this.reportFile.newLine(); this.reportFile.newLine();
                report.println( "Number of OID Records Deleted for Relationships in ANA_OBJECT: " + deleteObjects.size() );
            }
            
            String query = "DELETE FROM ANA_OBJECT WHERE obj_oid = ?";
            PreparedStatement preppie = this.newConnection.prepareStatement(query);
            
            if ( deleteObjects.isEmpty() ){
                this.reportFile.write(" -- No records deleted --");
                this.reportFile.newLine();
            }
            else{
                for ( Component deleteObject: deleteObjects ){
                    //System.out.println("deleting object from ana_object for " + deleteObject.getDBID() + " " + deleteObject.getID() );
                    preppie.setInt( 1, Integer.parseInt( deleteObject.getDBID() ) );
                    preppie.addBatch();
                    this.reportFile.write("DELETE FROM ANA_OBJECT WHERE obj_oid = " + deleteObject.getDBID() );
                    this.reportFile.newLine();
                }
                if ( flagUpdateDB ) preppie.executeBatch();
            }
                      
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    
    private void deleteANA_TIMED_NODE( ArrayList<Component> deleteTimedComponents ){
        
        try{
            //ana_timed_node delete summary
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("DELETION FROM ANA_TIMED_NODE FOR MODIFIED STAGE RANGES \n");
            this.reportFile.newLine();
            this.reportFile.write("====================================================== \n");
            this.reportFile.newLine(); this.reportFile.newLine();
        
            String query = "DELETE FROM ANA_TIMED_NODE WHERE atn_node_fk = ? " +
                "AND atn_stage_fk = ?"; 
            PreparedStatement preppie = this.newConnection.prepareStatement(query);
            
            if ( deleteTimedComponents.isEmpty() ) {
               this.reportFile.write("-- No records deleted --");
               this.reportFile.newLine();
            }
            else {
                for ( Component compie: deleteTimedComponents ){
                    //System.out.println( compie.getID() + " name:" + compie.getName() + " namespace:" + compie.getNamespace() );
                    preppie.setInt(1, Integer.parseInt( compie.getNamespace() ) );
                    preppie.setInt(2, compie.getIntStartsAt() );
                    preppie.addBatch();
                    this.reportFile.write("DELETE FROM ANA_TIMED_NODE WHERE atn_node_fk = " + compie.getNamespace() + 
                                          " AND atn_stage_fk = " + compie.getIntStartsAt() );
                    this.reportFile.newLine();
                }
                if ( flagUpdateDB ) preppie.executeBatch();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void deleteANA_RELATIONSHIP( ArrayList<Component> deleteRelComponents ){
        
        try{
            //ana_relationship delete summary
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("DELETION FROM ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT FOR MODIFIED COMPONENTS \n");
            this.reportFile.newLine();
            this.reportFile.write("=================================================================================== \n");
            this.reportFile.newLine(); this.reportFile.newLine();           
            
            String query = "DELETE FROM ANA_RELATIONSHIP WHERE rel_child_fk = ? " +
                    " AND rel_parent_fk = ?";
            PreparedStatement preppie = this.newConnection.prepareStatement(query);
            String queryProject = "DELETE FROM ANA_RELATIONSHIP_PROJECT WHERE rlp_relationship_fk = ? ";
            PreparedStatement preppieProject = this.newConnection.prepareStatement(queryProject);

            if ( deleteRelComponents.isEmpty() ) {
               this.reportFile.write("-- No records deleted --");
               this.reportFile.newLine(); 
            }
            else {
                for ( Component deleteRelCompie: deleteRelComponents ){
                    //child-parent deleteRelCompie.DBID():getPartOf()
                    preppie.setInt( 1, Integer.parseInt( deleteRelCompie.getID() ) ); //child
                    preppie.setInt( 2, Integer.parseInt( deleteRelCompie.getPartOf().get(0) ) ); //parent's DBID, always only one element
                    preppie.addBatch();
                    //delete from ana_relationship_project
                    preppieProject.setInt( 1, Integer.parseInt( deleteRelCompie.getDBID() ) );
                    preppieProject.addBatch();
                    //record
                    this.reportFile.write("DELETE FROM ANA_RELATIONSHIP WHERE rel_child_fk = " +
                            deleteRelCompie.getID() + " AND rel_parent_fk = " + deleteRelCompie.getPartOf().get(0) );
                    this.reportFile.newLine();
                    this.reportFile.write("DELETE FROM ANA_RELATIONSHIP_PROJECT WHERE " +
                                          "rlp_relationship_fk = " + deleteRelCompie.getDBID() );
                    this.reportFile.newLine();
                }
                if ( flagUpdateDB ){
                    preppieProject.executeBatch();
                    preppie.executeBatch();
                }
                preppie.close();
                preppieProject.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void deleteANA_SYNONYM( ArrayList<Component> deleteSynComponents ){
        
        try{
            //ana_relationship delete summary
            this.reportFile.newLine(); this.reportFile.newLine();
            this.reportFile.write("DELETION FROM ANA_SYNONYM FOR MODIFIED COMPONENTS \n");
            this.reportFile.newLine();
            this.reportFile.write("================================================= \n");
            this.reportFile.newLine(); this.reportFile.newLine();           
            
            String query = "DELETE FROM ANA_SYNONYM WHERE syn_oid = ?";
            PreparedStatement preppie = this.newConnection.prepareStatement(query);
            
            if ( deleteSynComponents.isEmpty() ) {
               this.reportFile.write("-- No records deleted --");
               this.reportFile.newLine();
            }
            else {
                for ( Component deleteSynCompie: deleteSynComponents ){
                    preppie.setInt( 1, Integer.parseInt( deleteSynCompie.getDBID() ) ); 
                    preppie.addBatch();
                    this.reportFile.write("DELETE FROM ANA_SYNONYM WHERE syn_oid = " + deleteSynCompie.getDBID() );
                    this.reportFile.newLine();
                }
                if ( flagUpdateDB ) preppie.executeBatch();
            } 
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void updateANA_RELATIONSHIP(){
        
    }
    
    private void updateANA_SYNONYM(){
        
    }
    
    private void updateANA_STAGE(){
        
    }
    
    private void updateANA_PART_OF(){
        
    }
    
    //method to sort through modified component list for changed stages
    private ArrayList< Component > getChangedStagesTermList( ArrayList<Component> changedTermList ){
        
        ArrayList < Component > termList = new ArrayList<Component>();
        for ( Component compie: changedTermList ){
            if ( compie.hasDifferenceComment("Different Start Stage") || 
                 compie.hasDifferenceComment("Different End Stage") )
                termList.add( compie );
        }
        return termList;
    }
    
    //method to sort through modified component list for changed names
    private ArrayList< Component > getChangedNamesTermList( ArrayList<Component> changedTermList ){
        
        ArrayList < Component > termList = new ArrayList<Component>();
        for ( Component compie: changedTermList ){
            if ( compie.hasDifferenceComment("Different Name") ) termList.add( compie );
        }
        return termList;
    }
    
    //method to sort through modified component list for changed parents
    private ArrayList< Component > getChangedParentsTermList( ArrayList<Component> changedTermList ){
        
        ArrayList < Component > termList = new ArrayList<Component>();
        for ( Component compie: changedTermList ){
            if ( compie.hasDifferenceComment("Different Parents") ) termList.add( compie );
            if ( compie.hasDifferenceComment("Different Group Parents") ) termList.add( compie );
        }
        return termList;
    }
    
    //method to sort through modified component list for changed synonyms
    private ArrayList< Component > getChangedSynonymsTermList( ArrayList<Component> changedTermList ){
        
        ArrayList < Component > termList = new ArrayList<Component>();
        for ( Component compie: changedTermList ){
            if ( compie.hasDifferenceComment("Different Synonyms") ) termList.add( compie );
        }
        return termList;
    }
    
    private ArrayList< Component > getChangedPrimaryStatusTermList( ArrayList<Component> changedTermList ){
        
        ArrayList < Component > termList = new ArrayList<Component>();
        for ( Component compie: changedTermList ){
            if ( compie.hasDifferenceComment("Different Primary Status") ) termList.add( compie );
        }
        return termList;
    }

    private ArrayList< Component > getChangedOrderTermList( ArrayList<Component> changedTermList ){
        ArrayList < Component > termList = new ArrayList<Component>();
        for ( Component compie: changedTermList ){
            if ( compie.hasDifferenceComment("Different Order") ) termList.add( compie );
        }
        return termList;
    }
    
    private void createDifferenceSynonyms( ArrayList<Component> diffSynonymTermList ){
 
        ResultSet rs = null;
        PreparedStatement preppie = null;
        
        String query = "SELECT syn_synonym " +
                       "FROM ANA_SYNONYM " +
                       "WHERE syn_object_fk = ?";
        Component dbCompie = new Component();
        Component deleteSynCompie = new Component();
        Component insertSynCompie = new Component();        
        ArrayList<String> synonyms = new ArrayList<String>();
        ArrayList<String> deleteSynonyms = new ArrayList<String>();
        ArrayList<String> insertSynonyms = new ArrayList<String>();
        
        try{
            preppie = this.newConnection.prepareStatement(query);
            
            //for each component where parents have changed
            for(Component compie: diffSynonymTermList){
                //get all parents from ANA_RELATIONSHIP
                preppie.setInt( 1, Integer.parseInt( compie.getDBID() ) );
                rs = preppie.executeQuery();
                //reset temporary component's parents for each component
                dbCompie.setSynonym( new ArrayList<String>() );
                //add to temporary component
                while ( rs.next() ){
                    dbCompie.addSynonym( rs.getString("syn_synonym") );
                }
   
                //make 2 arraylists to compare with component's parents and group parents
                synonyms.clear();
                synonyms.addAll( compie.getSynonym() );
                
                //get parents to be deleted
                //parents owned by dbCompie but not by compie
                deleteSynonyms.clear(); //reset for each dbCompie to compie comparison
                deleteSynonyms.addAll( dbCompie.getSynonym() );
                deleteSynonyms.removeAll( synonyms );
                if ( !deleteSynonyms.isEmpty() ){
                    deleteSynCompie = new Component();
                    ArrayList < String > copyDeleteSynonyms = new ArrayList<String>();
                    copyDeleteSynonyms.addAll( deleteSynonyms );
                    deleteSynCompie.setDBID( compie.getDBID() );
                    deleteSynCompie.setName( compie.getName() );
                    deleteSynCompie.setSynonym( copyDeleteSynonyms );
                    this.diffDeleteSynList.add( deleteSynCompie );
                }
                
                //get parents to be inserted
                //parents owned by compie but not by dbCompie
                insertSynonyms.clear(); //reset 
                insertSynonyms.addAll( synonyms );
                insertSynonyms.removeAll( dbCompie.getSynonym() );
                if ( !insertSynonyms.isEmpty() ){
                    insertSynCompie = new Component();
                    ArrayList < String > copyInsertSynonyms = new ArrayList<String>();
                    copyInsertSynonyms.addAll( insertSynonyms );
                    insertSynCompie.setDBID( compie.getDBID() );
                    insertSynCompie.setName( compie.getName() );
                    insertSynCompie.setSynonym( copyInsertSynonyms );
                    this.diffCreateSynList.add( insertSynCompie );
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    //method to detect difference in parents between modified components and existing components in DB
    private void createDifferenceParents( ArrayList<Component> diffParentTermList ){
        
        ResultSet rs = null;
        PreparedStatement preppie = null;
        
        String query = "SELECT rel_parent_fk, ano_public_id " +
                       "FROM ANA_NODE, ANA_RELATIONSHIP " +
                       "WHERE ano_oid = rel_parent_fk " +
                       "AND rel_child_fk = ?";
        Component dbCompie = new Component();
        Component deleteRelCompie = new Component();
        Component insertRelCompie = new Component();        
        ArrayList<String> parents = new ArrayList<String>();
        ArrayList<String> deleteParents = new ArrayList<String>();
        ArrayList<String> insertParents = new ArrayList<String>();
        
        try{
            preppie = this.newConnection.prepareStatement(query);
            
            //for each component where parents have changed
            for(Component compie: diffParentTermList){
                //get all parents from ANA_RELATIONSHIP
                preppie.setInt( 1, Integer.parseInt( compie.getDBID() ) );
                rs = preppie.executeQuery();
                //reset temporary component's parents for each component
                dbCompie.setPartOf( new ArrayList<String>() );
                //add to temporary component
                while ( rs.next() ){
                    dbCompie.addPartOf( rs.getString("ano_public_id") );
                }
                //compare with component's parents and group parents
                parents.clear();
                parents.addAll( compie.getPartOf() );
                parents.addAll( compie.getGroupPartOf() );
                
                //get parents to be deleted
                //parents owned by dbCompie but not by compie
                deleteParents.clear(); //reset for each dbCompie to compie comparison
                deleteParents.addAll( dbCompie.getPartOf() );
                deleteParents.removeAll( parents );
                if ( !deleteParents.isEmpty() ){
                    deleteRelCompie = new Component();
                    ArrayList < String > copyDeleteParents = new ArrayList<String>();
                    copyDeleteParents.addAll( deleteParents );
                    deleteRelCompie.setDBID( compie.getDBID() );
                    deleteRelCompie.setID( compie.getID() );
                    deleteRelCompie.setName( compie.getName() );
                    deleteRelCompie.setPartOf( copyDeleteParents );
                    this.diffDeleteRelList.add( deleteRelCompie );
                }
                
                //get parents to be inserted
                //parents owned by compie but not by dbCompie
                insertParents.clear(); //reset 
                insertParents.addAll( parents );
                insertParents.removeAll( dbCompie.getPartOf() );
                if ( !insertParents.isEmpty() ){
                    insertRelCompie = new Component();
                    ArrayList < String > copyInsertParents = new ArrayList<String>();
                    copyInsertParents.addAll( insertParents );
                    insertRelCompie.setDBID( compie.getDBID() );
                    insertRelCompie.setID( compie.getID() );
                    insertRelCompie.setName( compie.getName() );
                    insertRelCompie.setPartOf( copyInsertParents );
                    
                    this.diffCreateRelList.add( insertRelCompie );
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    //method to measure difference in stage ranges between modified components and existing components in DB
    private void createDifferenceTimedComponents( ArrayList<Component> diffStageTermList ){
        
        ResultSet rs = null;
        PreparedStatement preppie = null;
        String query = "SELECT stg_name FROM ANA_TIMED_NODE, ANA_STAGE " + 
                       "WHERE atn_node_fk = ? " +
                       "AND atn_stage_fk = stg_oid " +
                       "ORDER BY stg_name";
        HashMap<String, Integer> mapStageIDs = mapStageIDs();

        
        try{
            for (Component compie: diffStageTermList){
                //get stage range from database
                preppie = this.newConnection.prepareStatement(query);
                preppie.setInt( 1, Integer.parseInt(compie.getDBID()) );
                rs = preppie.executeQuery();
                
                Component dbCompie = new Component();
                rs.next();
                dbCompie.setStartsAt( rs.getString("stg_name") );
                rs.last();
                dbCompie.setEndsAt( rs.getString("stg_name") );

                //compare stage ranges between compie and dbCompie
                //for creating new timed components
                if ( dbCompie.getIntStartsAt() > compie.getIntStartsAt() ){
                   Component createTimedCompie = new Component();
                   createTimedCompie.setID( compie.getID() );
                   createTimedCompie.setName( compie.getName() );
                   createTimedCompie.setDBID( compie.getDBID() );
                   createTimedCompie.setStartsAt( compie.getStartsAt() );
                   createTimedCompie.setEndsAt( dbCompie.getIntStartsAt()-1 );
                   //System.out.println(createTimedCompie.getDBID() + " start:" + createTimedCompie.getStartsAt() + " end:" + createTimedCompie.getEndsAt() );
                   this.diffCreateTimedCompList.add( createTimedCompie );
                }
                if ( dbCompie.getIntEndsAt() < compie.getIntEndsAt() ){
                   Component createTimedCompie = new Component();
                   createTimedCompie.setID( compie.getID() );
                   createTimedCompie.setName( compie.getName() );                   
                   createTimedCompie.setDBID( compie.getDBID() );
                   createTimedCompie.setStartsAt( dbCompie.getIntEndsAt()+1 );
                   createTimedCompie.setEndsAt( compie.getEndsAt() );   
                   //System.out.println(createTimedCompie.getDBID() + " start:" + createTimedCompie.getStartsAt() + " end:" + createTimedCompie.getEndsAt() );
                   this.diffCreateTimedCompList.add( createTimedCompie );
                }
                
                //for deleting existing timed components
                if ( dbCompie.getIntStartsAt() < compie.getIntStartsAt() ){
                   Component delTimedCompie = new Component();
                   delTimedCompie.setID( compie.getID() );
                   delTimedCompie.setName( compie.getName() );
                   delTimedCompie.setDBID( compie.getDBID() );
                   delTimedCompie.setStartsAt( dbCompie.getStartsAt() );
                   delTimedCompie.setEndsAt( compie.getIntStartsAt()-1 );
                   //System.out.println(delTimedCompie.getDBID() + " start:" + delTimedCompie.getStartsAt() + " end:" + delTimedCompie.getEndsAt() );
                   this.diffDeleteTimedCompList.add( delTimedCompie );
                }
                if ( dbCompie.getIntEndsAt() > compie.getIntEndsAt() ){
                    Component delTimedCompie = new Component();
                    delTimedCompie.setID( compie.getID() );
                    delTimedCompie.setName( compie.getName() );
                    delTimedCompie.setDBID( compie.getDBID() );
                    delTimedCompie.setStartsAt( compie.getIntEndsAt()+1 );
                    delTimedCompie.setEndsAt( dbCompie.getEndsAt() );
                    //System.out.println(delTimedCompie.getDBID() + " start:" + delTimedCompie.getStartsAt() + " end:" + delTimedCompie.getEndsAt() );
                    this.diffDeleteTimedCompList.add( delTimedCompie );
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    

    //method to validate term list scheduled for deletion
    //check that all for each component in term list, if its primary, then all descendants are scheduled to be deleted
    //if one of its descendants cannot be found in OBO file or has not been scheduled for deletion
    //the current component will be removed from the returned term list
    //returned term list will be used for actual deletion
    //and updated to ANA_LOG
    //IMPORTANT: deletion is ONLY ALLOWED if the terms to be deleted by the user does not contain any
    //           child-parent relationships in ANA_RELATIONSHIP where the component to be deleted is the parent
    //           and the child is not due for deletion ie. not in the deletedTermList
    //JUSTIFICATION: if term has a child term in the DB term list but not in proposed term list,
    //               then deleting the term will cause undesirable orphan terms    
    private ArrayList < Component > validateDeleteTermList(ArrayList<Component> deletedTermList){

        ArrayList<Component> dbTermList = new ArrayList<Component>();
        Vector<String> dependentDescendants = new Vector<String>();
        Boolean invalidDelete = false;
        
        try{
            //for each term in deletedTermList
            for (Component deleteCompie: deletedTermList){
                //get all dependent descendants
                dependentDescendants = recursiveGetDependentDescendants(deleteCompie.getDBID(), dependentDescendants, invalidDelete);
                if ( invalidDelete ) {
                    //disallow deletion
                    //put to unDeletedCompList for report purposes
                    deleteCompie.setCheckComment("Delete Record Warning: " + deleteCompie.getID() + " still has descendants in the database. Deletion not allowed.");
                    deleteCompie.setCheckComment("Delete Record Warning: Deletion of this term could potentially result in undesirable orphan terms or other ontology violations. Please generate a new OBO file from the database and retry deletion");
                    this.unDeletedCompList.add(deleteCompie);
                }
                else dbTermList.add(deleteCompie);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return dbTermList;
    }
    
      
    public Vector< String > recursiveGetDependentDescendants(String compieDBID, Vector< String > compieDBIDs, boolean invalidDelete){
        
        Vector< String > descendants = compieDBIDs;
        Vector< String > childrenDBIDs = new Vector<String>();
        boolean isPrimary = false;
        Component compie = new Component();
        Component deletedCompie = new Component();
        
        try {
            //System.out.println("recursiveGetDependentDescendants: " + compieDBID);
            String queryANA_NODE = "SELECT ano_is_primary FROM ANA_NODE WHERE ano_oid = " + compieDBID;
            ResultSet rsANA_NODE = this.newConnection.createStatement().executeQuery(queryANA_NODE); 
        
            if ( rsANA_NODE.next() ) {
                isPrimary = rsANA_NODE.getBoolean("ano_is_primary");
            }

            //if it is a primary node all children matter
            if ( isPrimary ){
                String query = "SELECT rel_child_fk FROM ANA_RELATIONSHIP WHERE rel_parent_fk = " + compieDBID;
                ResultSet rs = this.newConnection.createStatement().executeQuery(query);

                while ( rs.next() ){
                    childrenDBIDs.add( rs.getString("rel_child_fk") );
                }

                if ( childrenDBIDs.isEmpty() ){
                    return descendants;
                }
                else{
                    for (String s: childrenDBIDs){
                        //check to see whether all dependent descendants have been specified for deletion
                        compie = this.tree.getComponent(s);
                        //if component is not found in tree OR component is found but is not scheduled for deletion
                        if ( compie==null || !compie.getStrChangeStatus().equals("DELETED") ) {
                            invalidDelete = true;
                            deletedCompie = this.tree.getComponent( compieDBID );
                            deletedCompie.setCheckComment("Delete Record Warning: Deletion of this term results in orphan term " + compie.getID() + " " + compie.getName() + ". Please generate a new OBO file from the database and retry deletion.");
                        } 
                        descendants.add( s );
                        descendants = recursiveGetDependentDescendants( s, descendants, invalidDelete );
                    }
                }
            }
            //if it is a group node children don't have to be deleted
            else return descendants; 
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return descendants;
    } 
    
    private void reportDeletionSummary(ArrayList<Component> scheduledDeleteTermList, ArrayList<Component> deletedTermList, ArrayList<Component> notDeletedTermList){
        
           //ana_timed_node summary
           report.println();
           report.println( "DELETION SUMMARY" );
           report.println( "================" );
           report.println( "Number of Components Scheduled to be Deleted: " + scheduledDeleteTermList.size() );
           for (int i = 0; i < scheduledDeleteTermList.size(); i++){
               report.println( "  " + scheduledDeleteTermList.get(i) );
           }
           report.println( "Number of Components Succesfully Deleted: " + deletedTermList.size() );
           for (int i = 0; i < deletedTermList.size(); i++){
               report.println( "  " + deletedTermList.get(i) );
           }
           report.println( "Number of Scheduled Components for Deletion that were not Deleted: " + notDeletedTermList.size() );
           for (int i = 0; i < notDeletedTermList.size(); i++){
               report.println( "  " + notDeletedTermList.get(i) );
               Set<String> comments = notDeletedTermList.get(i).getCheckComments(); 
               for ( String comment: comments ){
                   report.println( "   - " + comment );
               }
           }
           report.println();
           report.println();
    }
    
    private void reportUpdateSummary(){
        report.println();
        report.println( "UPDATE SUMMARY" );
        report.println( "==============" );
    }
    
    private ArrayList < Component > createTimeComponents(ArrayList<Component> termList, String calledFrom){
       //create timed components in ANA_OBJECT
       Component compie = new Component();
       boolean flagInsert;
       ArrayList<Component> timedComps = new ArrayList<Component>();

       if ( calledFrom.equals("NEW") )
            report.println( " EMAP:ID for Time Components created for new Components: " );
       else if ( calledFrom.equals("MODIFY") )
            report.println( " EMAP:ID for Time Components created for stage range modification of existing Components: ");
            
       for(int i = 0; i< termList.size(); i++){

           compie = termList.get(i);
           flagInsert = true; //reset flag

           flagInsert = ( !( compie.commentsContain("Relation: ends_at -- Missing ends at stage - Component's stage range cannot be determined.") ) &&
                          !( compie.commentsContain("Relation: starts_at -- Missing starts at stage - Component's stage range cannot be determined.") ) &&
                          !( compie.commentsContain("Relation: starts_at, ends_at -- Ends at stage earlier than starts at stage.") ) &&
                          !( compie.commentsContain("Relation: Starts At - More than one Start Stage!") ) &&
                          !( compie.commentsContain("Relation: Ends At - More than one End Stage!") ) &&
                          !( compie.commentsContain("Relation: starts_at, ends_at -- Stages are out of range!") ) );


           if (flagInsert){
               //make a time component record for each stage
               for(int j = compie.getIntStartsAt(); j <= compie.getIntEndsAt(); j++ ){

                   //prepare timed components to insert into ANA_OBJECT
                   //ATN_OID = DBID call insertANA_OBJECT to get assignment
                   //ATN_NODE_FK = Namespace 
                   //ATN_STAGE_FK = StartsAt 
                   //ATN_STAGE_MODIFIER = n/a
                   //ATN_PUBLIC_ID = ID

                   String strStage = ( Integer.toString(j).length()==1 ) ? "0"+ Integer.toString(j) : Integer.toString(j);

                   Component timedCompie = new Component();
                   timedCompie.setNamespace( compie.getDBID() ); //current component
                   timedCompie.setID( "EMAP:" + Integer.toString( ++this.intCurrentPublicID ) );
                   //timedCompie.setStartsAt("TS" + Integer.toString(vStageOID.get(j-1)) );//vStageOID.get(stage - 1)= key for stage             
                   timedCompie.setStartsAt( "TS" + strStage );
                   timedComps.add(timedCompie);  
                   //object_counter++;
                   
                   if ( calledFrom.equals("NEW") )
                       report.println( "  " + timedComps.size() + ". " + "Time component created for new component " + compie.getID() + " " + compie.getName() + " : " + timedCompie.getID() + " for " + timedCompie.getStartsAt() );
                   else if ( calledFrom.equals("MODIFY") )
                       report.println( "  " + timedComps.size() + ". " + "Time component created for modification of stage range of component " + compie.getID() + " " + compie.getName() + " : " + timedCompie.getID() + " for " + timedCompie.getStartsAt() );
                   //else if ( calledFrom.equals("DELETE") )
                       //do nothing just used to update 
                       //report.println( "  " + timedComps.size() + ". " + "Time component created for " + compie.getID() + " " + compie.getName() + " : " + timedCompie.getID() + " for " + timedCompie.getStartsAt() );
               }
           }
           //potential modification to do:
           //set default values for start stage and end stage to fit that of parent if (!flagInsert)
           if (!flagInsert) {
               //System.out.println("New Record Error: Component " + compie.getID() + " does not have a correct stage range." +  
               //                   "No database entries created for timed components of Component " + compie.getID() );
               report.println( compie.getID() + " - No timed component records.");
               report.println( "               " + " - Does not have a correct stage range.");
               report.println( "               " + " - Next attempt to construct OBO file from database will trigger an error: No Starts_At & Ends_At for component entry.");
           }                                         
       }
       return timedComps;
    }
    
    private HashMap mapStageIDs(){
        
        HashMap <String, Integer> stageNameToIDs = new HashMap();
        ResultSet oidRS = null;
        
        try{
            String query = "SELECT stg_oid, stg_name FROM ANA_STAGE ORDER BY stg_name";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            while ( oidRS.next() ){
                stageNameToIDs.put( oidRS.getString("stg_name"), oidRS.getInt("stg_oid") );                
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return stageNameToIDs;
    }
    
    private int getMaxPublicID(){
        
        //use new connection
        //select max public id from ana_node
        //select max public id from ana_timed_node
        
        int intMax_ATNpublicID = 0;
        int intMax_ANOpublicID = 0;
        int intMaxPublicID = 0;
        
        //if method called before this.intMaxPublicID should not be 0, return current intMaxPublicID
        //if ( this.intCurrentPublicID > 0 ) return this.intCurrentPublicID;
        
        try{
            //get max emap id from updated ana_timed_node
            ResultSet oidRS = null;
            String query = "SELECT MAX(CAST( SUBSTRING(atn_public_id, 6) AS SIGNED )) AS max_emap FROM ANA_TIMED_NODE";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intMax_ATNpublicID = oidRS.getInt("max_emap");
            oidRS.close();

            //get max emapa id from updated ana_node
            query = "SELECT MAX(CAST( SUBSTRING(ano_public_id, 7) AS SIGNED )) AS max_emapa FROM ANA_NODE";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intMax_ANOpublicID = oidRS.getInt("max_emapa");
            oidRS.close();

            //get larger public id
            intMaxPublicID = ( intMax_ATNpublicID >= intMax_ANOpublicID ) ? intMax_ATNpublicID : intMax_ANOpublicID;

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //note: intCurrentPublicID should be larger than current intMaxPublicID if method has been called before
        intMaxPublicID = (this.intCurrentPublicID >= intMaxPublicID) ? this.intCurrentPublicID : intMaxPublicID;
        return intMaxPublicID;
    }
    
    private int getMaxObjectID(){
                
        //use old and new connection
        //select max public id from old ana_object
        //select max public id from new ana_object
        
        int intMax_newOID = 0;
        int intMax_oldOID = 0;
        int intMaxObjectID = 0;
        
        //if method called before this.intMaxPublicID should not be 0, return current intMaxPublicID
        //if ( this.intCurrentObjectID > 0 ) return this.intCurrentObjectID;
        
        try{
            //get max oid from referenced database
            ResultSet oidRS = null;
            String query = "SELECT MAX(obj_oid) as max_oid FROM ANA_OBJECT";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intMax_newOID = oidRS.getInt("max_oid");
            
            /*
            //only while still doing updates to test database
            //check to see whether max oid from referenced database > max oid from prop database
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) intMax_oldOID = oidRS.getInt("max_oid");
            */

            //get larger public id
            intMaxObjectID = ( intMax_newOID >= this.intCurrentObjectID ) ? intMax_newOID : this.intCurrentObjectID;
            return intMaxObjectID;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        intMaxObjectID = ( this.intCurrentObjectID >= intMaxObjectID ) ? this.intCurrentObjectID : intMaxObjectID;
        return intMaxObjectID;
    }
    
    private int getDBID( String id ){
        ResultSet oidRS = null;
        String query = "";
        
        try{
            query = "SELECT ano_oid FROM ANA_NODE WHERE ano_public_id = '" + id + "'";
            oidRS = this.newConnection.createStatement().executeQuery(query);
            if ( oidRS.next() ) {
                return oidRS.getInt("ano_oid");
            }
            //if term with ano_public_id is not found add to appropriate term list :
            else {
                //no component found in ana_node with this id
                //System.out.println("Warning! No component with the id " + id + " exists in the current database!");
                return 0;
            } 
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return 0;
    }
 
    private ArrayList setDBIDs( ArrayList<Component> termList ){
        
        int intDBID = 0;
        ResultSet oidRS = null;
        String query = "";
        Component compie = new Component();
        
        try{
            //for (Component compie : termList){
            for (int i = 0; i<termList.size(); i++ ){
                compie = termList.get(i);
                
                query = "SELECT ano_oid FROM ANA_NODE WHERE ano_public_id = '" + 
                         compie.getID() + "'";
                oidRS = this.newConnection.createStatement().executeQuery(query);
                if ( oidRS.next() ) {
                    intDBID = oidRS.getInt("ano_oid");
                    //set dbid for component
                    compie.setDBID( Integer.toString(intDBID) );
                }
                //if term with ano_public_id is not found add to appropriate term list :
                //unDeletedCompList for passed DELETED terms report purposes
                //unModifiedCompList for passed CHANGED terms report purposes
                else {
                    if ( compie.getStrChangeStatus().equals("DELETED") ){
                        compie.setCheckComment("Delete Record Warning: No term with the ID " + compie.getID() + " exists in ANA_NODE, deletion for this component did not proceed.");
                        termList.remove(i);
                        i--;
                        this.unDeletedCompList.add(compie);
                    }
                    else if ( compie.getStrChangeStatus().equals("CHANGED") ){
                        compie.setCheckComment("Update Record Warning: No term with the ID " + compie.getID() + " exists in ANA_NODE, changes made by the user for this component were not update in the database.");
                        termList.remove(i);
                        i--;
                        this.unModifiedCompList.add(compie);
                    }
                } 
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return termList;
    }
    
    public Connection getImportConnection(){
        return this.newConnection;
    }
    
    public static void processUpdateCounts(int[] updateCounts) {
        
        for (int i=0; i<updateCounts.length; i++) {
            if (updateCounts[i] >= 0) {
                // Successfully executed; the number represents number of affected rows
            } else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
                // Successfully executed; number of affected rows not available
            } else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
                // Failed to execute
            }
        }
    }

    public boolean getIsProcessed(){
        return this.isProcessed;
    }

    
}