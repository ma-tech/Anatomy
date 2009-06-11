package backend;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author attila
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ImportDatabase {

    // global variables
    private Connection connection;
    private boolean isProcessed;

    private ArrayList < Component > termList = new ArrayList < Component >();
    private ArrayList < Relation > relationList = new ArrayList < Relation >();
    
    public ImportDatabase( Connection connection, Component abstractClass, Component stageClass, Component groupClass, Component groupTermClass, String species, String fileType, String stage, String start, String end, boolean defaultroot) {
        this.connection = connection;
        this.isProcessed = false;
      
        // 1: abstract class
        Component term;
        // 1_1: main abstract term 
        term = new Component();
        term.setName( abstractClass.getName() );
        term.setID( abstractClass.getID() );
        term.setNamespace( abstractClass.getNamespace() );
        term.setDBID( "-1" );
        termList.add( term );
        // 1_2: rest from db
        ResultSet nodeRS = null;
        try {
            // 1_2_1: query for the terms
            String nodeQuery = "";
            if ( fileType.equals("Abstract Stage") )
                nodeQuery = "SELECT ano_oid, ano_component_name, ano_public_id, ano_is_primary"
                        +" FROM ANA_NODE, ANA_TIMED_NODE, ANA_STAGE"
                        +" WHERE atn_node_fk = ano_oid"
                        +" AND atn_stage_fk = stg_oid"
                        +" AND stg_name = '" + stage + "'"
                        +" AND ano_is_primary = 1 ";
            else if ( fileType.equals("Abstract Stage Range") ){
                     nodeQuery = "SELECT ano_oid, ano_component_name, ano_public_id, ano_is_primary"
                        +" FROM ANA_NODE, ANA_TIMED_NODE, ANA_STAGE"
                        +" WHERE atn_node_fk = ano_oid"
                        +" AND atn_stage_fk = stg_oid"
                        +" AND SUBSTR(stg_name, 3) >= " + Integer.parseInt(start.substring(2))
                        +" AND SUBSTR(stg_name, 3) <= " + Integer.parseInt(end.substring(2));
            }
            else nodeQuery = "SELECT ano_oid, ano_component_name, ano_public_id, ano_is_primary FROM ANA_NODE";

            nodeRS = connection.createStatement().executeQuery( nodeQuery );
            while ( nodeRS.next() )  {
                term = new Component();
                term.setName( nodeRS.getString("ano_component_name") );
                term.setID( nodeRS.getString("ano_public_id") );
                term.setDBID( nodeRS.getString("ano_oid") ); 
                term.setIsPrimary( nodeRS.getBoolean("ano_is_primary") );
                
                // 1_2_2: query for the node's partOf relationship
                ResultSet partofRS = null;
                try {
                    String partofQuery = "";
                    if ( fileType.equals("Abstract Stage") || fileType.equals("Abstract Stage Range") )
                        partofQuery = "SELECT ano_public_id FROM ANA_RELATIONSHIP, ANA_NODE"
                        +" WHERE rel_child_fk = '" + term.getDBID() + "'"
                        +" AND rel_relationship_type_fk = 'part-of'"
                        +" AND rel_parent_fk = ano_oid"
                        +" AND ano_is_primary = 1"; //exclude group nodes because they can be start/end beyond/before selected stage and still have child nodes that are within selected stage
                        //eg. EMAPA:28445 starts and ends at TS22 but have children from TS22 - TS24
                        //eg. EMAPA:31464 starts TS25 and ends TS28 but have children TS21- TS26
                    else partofQuery = "SELECT ano_public_id FROM ANA_RELATIONSHIP, ANA_NODE"
                        +" WHERE rel_child_fk = '" + term.getDBID() + "'"
                        +" AND rel_relationship_type_fk = 'part-of'"
                        +" AND rel_parent_fk = ano_oid";

                    partofRS = connection.createStatement().executeQuery( partofQuery );
                    if ( term.getIsPrimary() ){
                        while ( partofRS.next() ) {
                            term.addPartOf( partofRS.getString("ano_public_id") );
                        } // while partofRS 
                    }
                    else {
                        while ( partofRS.next() ) {
                            term.addGroupPartOf( partofRS.getString("ano_public_id") );
                            //term.setIsA("group_term");
                            term.setIsA( groupTermClass.getID() ); 
                        } // 
                    }
                
                    partofRS.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }             

                // 1_2_3: query for the node's synonyms
                ResultSet synRS = null;
                try {
                    synRS = connection.createStatement().executeQuery( "SELECT syn_synonym FROM ANA_SYNONYM"
                        +" WHERE syn_object_fk = '" + term.getDBID() + "'");
                    while ( synRS.next() )  {
                        term.addSynonym( synRS.getString("syn_synonym") );
                    } // while synRS                 
                    synRS.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }             

                // 1_2_4: query for the node's start and end stage
                //maze: can also query table anad_part_of to get start and end stage
                //note: table is derived, constraints might not exist
                //advantage: not necessary to join two tables and order for first and last record
                ResultSet nodestageRS = null;
                try {
                    nodestageRS = connection.createStatement().executeQuery( "SELECT stg_name ,atn_public_id FROM ANA_TIMED_NODE, ANA_STAGE"
                        +" WHERE atn_node_fk = '" + term.getDBID() + "'"
                        +" AND atn_stage_fk = stg_oid"
                        +" ORDER BY stg_name");
                    nodestageRS.next();
                    term.setStartsAt( nodestageRS.getString("stg_name") );
                    nodestageRS.last();
                    term.setEndsAt( nodestageRS.getString("stg_name") );

                    if ( fileType.equals("Timed Component") ){
                        nodestageRS.beforeFirst();
                        while ( nodestageRS.next() )  {
                            term.addHasTimeComponent( nodestageRS.getString("atn_public_id") );
                        }
                    }
                    nodestageRS.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                

                // 1_2_5: add term to the term list
                //maze: term name that is equal to 'mouse' or 'human' is set to IsA
                term.setNamespace( abstractClass.getNamespace() );
                if ( term.getName().equals( species ) ) term.setIsA( abstractClass.getID() );
                if ( defaultroot ) termList.add( term );

            } // while nodeRS                
            nodeRS.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //4_0: timed components
        if ( fileType.equals("Timed Component") ){

            //4.1 make root node
            Component stageAnatomyClass = new Component();
            stageAnatomyClass.setName( "Stage anatomy" );
            stageAnatomyClass.setID( abstractClass.getID().substring(0, 4) + ":0" );
            stageAnatomyClass.setNamespace( "stage_anatomy" );
            termList.add( stageAnatomyClass );

            //get time components
            ResultSet stagetermRS = null;
            try {
    
                stagetermRS = connection.createStatement().executeQuery( "SELECT ano_component_name, atn_public_id, stg_name, atn_node_fk"
                    +" FROM ana_timed_node, ana_node, ana_stage"
                    +" WHERE ano_oid = atn_node_fk" 
                    +" AND atn_stage_fk = stg_oid" );
                while ( stagetermRS.next() ){            
                    Component stageTerm = new Component();
                    
                    stageTerm.setID( stagetermRS.getString("atn_public_id") );
                    stageTerm.setName( stagetermRS.getString("ano_component_name") );
                    stageTerm.setPresentIn( stagetermRS.getString("stg_name") );
                    stageTerm.setNamespace("stage_anatomy");
                    stageTerm.setDBID( Integer.toString(stagetermRS.getInt("atn_node_fk")) );
                    if ( stageTerm.getName().equals( species ) ) stageTerm.setIsA( stageAnatomyClass.getID() );
                    
                    //timed_component_of
                    ResultSet timedcompofRS = null;
                    try {
                        timedcompofRS = connection.createStatement().executeQuery( "SELECT ano_public_id FROM ANA_NODE"
                            +" WHERE ano_oid  = '" + stageTerm.getDBID() + "'");
                        while ( timedcompofRS.next() )  {
                            stageTerm.setTimeComponentOf( timedcompofRS.getString("ano_public_id") );
                        }                
                        timedcompofRS.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    
                    //
                    ResultSet partofRS = null;
                    try {
                        partofRS = connection.createStatement().executeQuery( 
                            "SELECT atn_public_id FROM ANA_TIMED_NODE, ANA_STAGE " +
                            "WHERE atn_node_fk IN " +
                            "( SELECT rel_parent_fk FROM ANA_RELATIONSHIP, ANA_TIMED_NODE, ANA_STAGE " +
                               "WHERE rel_child_fk = atn_node_fk " + 
                               "AND atn_node_fk = " + stageTerm.getDBID() + " " + 
                               "AND atn_stage_fk = stg_oid " +
                               "AND stg_name = '" + stageTerm.getPresentIn() + "') " +
                            "AND atn_stage_fk = stg_oid " + 
                            "AND stg_name = '" + stageTerm.getPresentIn() + "'" );
                        while ( partofRS.next() )  {
                            //System.out.println("adding parent for time component " + stageTerm.getName() + " at " +
                            //        stageTerm.getPresentIn() + ": " + partofRS.getString("atn_public_id") );
                            stageTerm.addPartOf( partofRS.getString("atn_public_id") );
                        } // while partofRS                 
                        partofRS.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }     
                    
                    //add stage term to term list
                    if ( defaultroot ) termList.add(stageTerm);
                }
                stagetermRS.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        //5: group terms
        //5:1:         
        term = new Component();
        term.setName( groupTermClass.getName() );
        term.setID( groupTermClass.getID() );
        term.setNamespace( groupTermClass.getNamespace() );
        term.setDBID( "-1" );
        termList.add( term );
        
        /*
        term = new Component();
        term.setName( "Group term" );
        term.setID( "group_term" );
        term.setNamespace( "group_term" );
        term.setDBID( "-1" );
        termList.add( term );
        */

        // 2: stage class
        // 2_1: main stage term 
        term = new Component();
        term.setName( stageClass.getName() );
        term.setID( stageClass.getID() );
        term.setNamespace( stageClass.getNamespace() );
        term.setDBID( "-1" );
        termList.add( term );
        // 2_2: rest from db
        ResultSet stageRS = null;
        try {
            // 2_2_1: query for the stages
            stageRS = connection.createStatement().executeQuery( "SELECT stg_oid, stg_name from ANA_STAGE" );
            while ( stageRS.next() )  {
                term = new Component();
                term.setName( stageRS.getString("stg_name") );
                term.setID( stageRS.getString("stg_name") );
                term.setDBID( stageRS.getString("stg_oid") );
                // add term to the term list
                term.setIsA( stageClass.getID() );
                term.setNamespace( stageClass.getNamespace() );
                termList.add( term );
            } // while nodeRS                
            stageRS.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        

        // 3: new group class
        term = new Component();
        term.setName( groupClass.getName() );
        term.setID( groupClass.getID() );
        term.setNamespace( groupClass.getNamespace() );
        term.setDBID( "-1" );
        termList.add( term );
        
        
        //replace below with this
        makeRelationList(fileType);
        
        /*
        // 3: set relations
        Relation rel;
        // 3_1: starts at
        rel = new Relation();
        rel.setName( "starts at" );
        rel.setID( "starts_at" );
        relationList.add( rel );
        // 3_2: ends at
        rel = new Relation();
        rel.setName( "ends at" );
        rel.setID( "ends_at" );
        relationList.add( rel );
        // 3_3: partOf
        rel = new Relation();
        rel.setName( "part of" );
        rel.setID( "part_of" );
        rel.setTransitive( "true" );
        relationList.add( rel );
        // 3_4: groupPartOf
        rel = new Relation();
        rel.setName( "group part of" );
        rel.setID( "group_part_of" );
        rel.setTransitive( "true" );
        relationList.add( rel );
        */
        
        this.isProcessed = true;
    }
    
    public void makeRelationList(String fileType){
         Relation rel;
        // 3_1: starts at
        rel = new Relation();
        rel.setName( "starts at" );
        rel.setID( "starts_at" );
        relationList.add( rel );
        // 3_2: ends at
        rel = new Relation();
        rel.setName( "ends at" );
        rel.setID( "ends_at" );
        relationList.add( rel );
        // 3_3: partOf
        rel = new Relation();
        rel.setName( "part of" );
        rel.setID( "part_of" );
        rel.setTransitive( "true" );
        relationList.add( rel );
        // 3_4: groupPartOf
        rel = new Relation();
        rel.setName( "group part of" );
        rel.setID( "group_part_of" );
        rel.setTransitive( "true" );
        relationList.add( rel );
        //3_5: hasTimeComponent
        if ( fileType.equals("Timed Component") ){
            rel = new Relation();
            rel.setName( "has time component");
            rel.setID("has_time_component");
            rel.setTransitive("false");
            relationList.add( rel );

            rel = new Relation();
            rel.setName("time component of");
            rel.setID("time_component_of");
            rel.setTransitive("false");
            relationList.add( rel );

            rel = new Relation();
            rel.setName("present in");
            rel.setID("present_in");
            rel.setTransitive("false");
            relationList.add( rel );
        }
    }
    
    public ArrayList < Component > getTermList() {
        return termList;
    }
    
    public ArrayList < Relation > getRelationList() {
        return relationList;
    }
    
    public boolean getIsProcessed() {
        return isProcessed;
    }
    
}
