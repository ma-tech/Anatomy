/*
################################################################################
# Project:      Anatomy
#
# Title:        ImportDatabase.java
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
    
    public ImportDatabase( Connection connection,
            Component abstractClass,
            Component stageClass,
            Component groupClass,
            Component groupTermClass,
            String species,
            String fileType,
            String stage,
            Integer start,
            Integer end,
            boolean defaultroot,
            String project) {

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

            // REDUNDANT!!!
            if ( fileType.equals("Abstract Stage") ) {
                nodeQuery = "SELECT ano_oid, ano_component_name, " +
                            "ano_public_id, ano_is_primary " +
                            "FROM ANA_NODE, ANA_TIMED_NODE, ANA_STAGE " +
                            "WHERE atn_node_fk = ano_oid " +
                            "AND atn_stage_fk = stg_oid " +
                            "AND stg_name = '" + stage + "' " +
                            "AND ano_is_primary = 1";
                //System.out.println("nodeQuery = " + nodeQuery);
            }
            // REDUNDANT!!!
            if ( fileType.equals("Abstract Stage Range") ) {
                nodeQuery = "SELECT ano_oid, ano_component_name, " +
                        "ano_public_id, ano_is_primary " +
                        "FROM ANA_NODE, ANA_TIMED_NODE, ANA_STAGE " +
                        "WHERE atn_node_fk = ano_oid " +
                        "AND atn_stage_fk = stg_oid " +
                        "AND stg_sequence >= " + start  + " " +
                        "AND stg_sequence <= " + end + ";";
                /*
                        "AND SUBSTR(stg_name, 3) >= " +
                        Integer.parseInt(start.substring(2)) + " " +
                        "AND SUBSTR(stg_name, 3) <= " +
                        Integer.parseInt(end.substring(2));
                */
                //System.out.println("nodeQuery = " + nodeQuery);
            }
            // DEFAULT!!!
            if ( fileType.equals("Timed Component") ||
                 fileType.equals("Starts and Ends") ) {
                //System.out.println("fileType = " + fileType);
                nodeQuery = "SELECT ano_oid, ano_component_name, " +
                        "ano_public_id, ano_is_primary FROM ANA_NODE";
                //System.out.println("nodeQuery = " + nodeQuery);
            }

            nodeRS = connection.createStatement().executeQuery( nodeQuery );
            //System.out.println("Executing nodeQuery");
            while ( nodeRS.next() )  {
                //System.out.println("Processing ResultSet");

                //System.out.println("ano_component_name = " +
                // nodeRS.getString("ano_component_name"));
                //System.out.println("ano_public_id = " +
                // nodeRS.getString("ano_public_id"));
                //System.out.println("ano_oid = " +
                // nodeRS.getString("ano_oid"));
                //System.out.println("ano_is_primary = " +
                // nodeRS.getBoolean("ano_is_primary"));

                term = new Component();
                term.setName( nodeRS.getString("ano_component_name") );
                term.setID( nodeRS.getString("ano_public_id") );
                term.setDBID( nodeRS.getString("ano_oid") ); 
                term.setIsPrimary( nodeRS.getBoolean("ano_is_primary") );
                
                // 1_2_2: query for the node's partOf relationship
                ResultSet partofRS = null;
                try {
                    String partofQuery = "";
                    // REDUNDANT!!!
                    if ( fileType.equals("Abstract Stage") ||
                            fileType.equals("Abstract Stage Range") ) {
                        partofQuery = "SELECT ano_public_id, rlp_sequence " +
                        "FROM ANA_RELATIONSHIP, ANA_NODE, " +
                        "ANA_RELATIONSHIP_PROJECT " +
                        "WHERE rel_child_fk = '" + term.getDBID() + "' " +
                        "AND rel_relationship_type_fk = 'part-of' " +
                        "AND rel_parent_fk = ano_oid " +
                        "AND rel_oid = rlp_relationship_fk " +
                        "AND rlp_project_fk = '" + project + "' " +
                        "AND ano_is_primary = 1";
                        //exclude group nodes because they can be start/end
                        // beyond/before selected stage and still have child
                        // nodes that are within selected stage
                        //eg. EMAPA:28445 starts and ends at TS22
                        // but have children from TS22 - TS24
                        //eg. EMAPA:31464 starts TS25 and ends TS28
                        // but have children TS21- TS26
                    }
                    // DEFAULT!!!
                    if ( fileType.equals("Timed Component") ||
                         fileType.equals("Starts and Ends") ) {
                        partofQuery = "SELECT ano_public_id, rlp_sequence " +
                        "FROM ANA_RELATIONSHIP, ANA_NODE, " +
                        "ANA_RELATIONSHIP_PROJECT " +
                        "WHERE rel_child_fk = '" + term.getDBID() + "' " +
                        "AND rel_relationship_type_fk = 'part-of' " +
                        "AND rel_parent_fk = ano_oid " +
                        "AND rel_oid = rlp_relationship_fk " +
                        "AND rlp_project_fk = '" + project + "'";
                    }

                    //System.out.println("partofQuery = " + partofQuery);

                    partofRS =
                       connection.createStatement().executeQuery( partofQuery );
                    /*if ( term.getIsPrimary() ){
                        while ( partofRS.next() ) {
                            term.addPartOf(
                                partofRS.getString("ano_public_id") );
                            //add comment for ordering nodes
                            if ( partofRS.getString("rlp_sequence")!= null ){
                                term.addUserComments("order=" +
                                    partofRS.getString("rlp_sequence") +
                                    " for " +
                                    partofRS.getString("ano_public_id"));
                            }
                        } // while partofRS 
                    }
                    else {
                        while ( partofRS.next() ) {
                            term.addGroupPartOf(
                                 partofRS.getString("ano_public_id") );
                            //term.setIsA("group_term");
                            term.setIsA( groupTermClass.getID() ); 
                            //add comment for ordering nodes
                            if ( partofRS.getString("rlp_sequence") != null ){
                                term.addUserComments("order=" +
                                    partofRS.getString("rlp_sequence") +
                                    " for " +
                                    partofRS.getString("ano_public_id"));
                            }
                        } // 
                    }*/

                    while ( partofRS.next() ) {
                        //System.out.println("");
                        if ( term.getIsPrimary() ){
                            term.addPartOf(
                                    partofRS.getString("ano_public_id") );
                            //add comment for ordering nodes
                            if ( partofRS.getString("rlp_sequence") != null ){
                                term.addUserComments("order=" +
                                        partofRS.getString("rlp_sequence") +
                                        " for " +
                                        partofRS.getString("ano_public_id"));
                            }
                        }
                        else {
                            term.addGroupPartOf(
                                    partofRS.getString("ano_public_id") );
                            //term.setIsA("group_term");
                            term.setIsA( groupTermClass.getID() );
                            //add comment for ordering nodes
                            if ( partofRS.getString("rlp_sequence") != null ){
                                term.addUserComments("order=" +
                                        partofRS.getString("rlp_sequence")  +
                                        " for " +
                                        partofRS.getString("ano_public_id"));
                            }
                        }
                    }

                    partofRS.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }             

                // 1_2_3: query for the node's synonyms
                ResultSet synRS = null;
                try {
                    //System.out.println("next Query = " + 
                    // "SELECT syn_synonym FROM ANA_SYNONYM WHERE " +
                    // "syn_object_fk = '" + term.getDBID() + "'");
                    synRS = connection.createStatement().executeQuery( 
                            "SELECT syn_synonym FROM ANA_SYNONYM " +
                            "WHERE syn_object_fk = '" + term.getDBID() + "'");
                    while ( synRS.next() )  {
                        term.addSynonym( synRS.getString("syn_synonym") );
                    }
                    synRS.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }             

                // 1_2_4: query for the node's start and end stage
                ResultSet nodestageRS = null;
                try {
                    /*System.out.println("next Query = SELECT stg_name, " +
                            "stg_sequence, atn_public_id FROM " +
                            "ANA_TIMED_NODE, ANA_STAGE WHERE atn_node_fk = '" +
                            term.getDBID() + "'" + " AND atn_stage_fk = " +
                            "stg_oid" +  " ORDER BY stg_name");*/
                    nodestageRS = connection.createStatement().executeQuery(
                            "SELECT stg_name, stg_sequence, atn_public_id " +
                            "FROM ANA_TIMED_NODE, ANA_STAGE " +
                            "WHERE atn_node_fk = '" + term.getDBID() + "' " +
                            "AND atn_stage_fk = stg_oid " +
                            "ORDER BY stg_name");
                    //nodestageRS.next();
                    if ( nodestageRS.next() ) {
                        //term.setStartsAt( nodestageRS.getString("stg_name") );
                        //System.out.println("min stg_sequence = " +
                        //        nodestageRS.getString("stg_sequence"));
                        term.setStartsAtInt( 
                                nodestageRS.getInt( "stg_sequence") );
                        nodestageRS.last();
                        //term.setEndsAt( nodestageRS.getString("stg_name") );
                        //System.out.println("max stg_sequence = " +
                        //        nodestageRS.getString("stg_sequence"));
                        term.setEndsAtInt(
                                nodestageRS.getInt("stg_sequence") );
                    }
                    else {
                        ResultSet maxstageRS = null;
                        ResultSet minstageRS = null;
                        //System.out.println("next Query = SELECT "+
                        //        "min(stg_sequence) as min FROM ANA_STAGE");
                        minstageRS =
                                connection.createStatement().executeQuery(
                                "SELECT min(stg_sequence) as min " +
                                "FROM ANA_STAGE");
                        if ( minstageRS.next() ){
                            //System.out.println("min = " +
                            //        minstageRS.getInt("min"));
                            term.setStartsAtInt( minstageRS.getInt("min") );
                        }
                        //System.out.println("next Query = SELECT " +
                        //        "max(stg_sequence) as max FROM ANA_STAGE");
                        maxstageRS =
                                connection.createStatement().executeQuery(
                                "SELECT max(stg_sequence) as max " +
                                "FROM ANA_STAGE");
                        if ( maxstageRS.next() ){
                            //System.out.println("max = " +
                            //        maxstageRS.getInt("max"));
                            term.setEndsAtInt( maxstageRS.getInt("max") );
                        }

                    }

                    if ( fileType.equals("Timed Component") ){
                        nodestageRS.beforeFirst();
                        while ( nodestageRS.next() )  {
                            term.addHasTimeComponent( nodestageRS.getString(
                                    "atn_public_id") );
                        }
                    }
                    nodestageRS.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
                

                // 1_2_5: add term to the term list
                //maze: term name that is equal to 'mouse' or 'human' is set
                // to IsA
                term.setNamespace( abstractClass.getNamespace() );

                if ( term.getName().equals( species ) ) {
                    term.setIsA( abstractClass.getID() );
                }

                if ( defaultroot ) {
                    termList.add( term );
                }

            } // while nodeRS                
            nodeRS.close();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        //4_0: timed components
        if ( fileType.equals("Timed Component") ){

            //4.1 make root node
            Component stageAnatomyClass = new Component();

            stageAnatomyClass.setName( "Stage anatomy" );
            stageAnatomyClass.setID( abstractClass.getID().substring(0, 4) +
                    ":0" );
            stageAnatomyClass.setNamespace( "stage_anatomy" );
            termList.add( stageAnatomyClass );

            //get time components
            ResultSet stagetermRS = null;

            try {
                stagetermRS = connection.createStatement().executeQuery(
                        "SELECT ano_component_name, atn_public_id, " +
                        "stg_sequence, atn_node_fk " +
                        "FROM ANA_TIMED_NODE, ANA_NODE, ANA_STAGE " +
                        "WHERE ano_oid = atn_node_fk " +
                        "AND atn_stage_fk = stg_oid" );

                while ( stagetermRS.next() ){            
                    Component stageTerm = new Component();
                    
                    stageTerm.setID( stagetermRS.getString("atn_public_id") );
                    stageTerm.setName( stagetermRS.getString(
                            "ano_component_name") );
                    stageTerm.setPresentIn( stagetermRS.getInt(
                            "stg_sequence") );
                    stageTerm.setNamespace("stage_anatomy");
                    stageTerm.setDBID( Integer.toString(stagetermRS.getInt(
                            "atn_node_fk")) );

                    if ( stageTerm.getName().equals( species ) ) {
                        stageTerm.setIsA( stageAnatomyClass.getID() );
                    }
                    
                    //timed_component_of
                    ResultSet timedcompofRS = null;
                    try {
                        timedcompofRS =
                                connection.createStatement().executeQuery(
                                "SELECT ano_public_id FROM ANA_NODE " +
                                "WHERE ano_oid  = '" + stageTerm.getDBID() +
                                "'");

                        while ( timedcompofRS.next() )  {
                            stageTerm.setTimeComponentOf(
                                    timedcompofRS.getString("ano_public_id") );
                        }                

                        timedcompofRS.close();
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    
                    //
                    ResultSet partofRS = null;

                    try {
                        partofRS = connection.createStatement().executeQuery( 
                            "SELECT atn_public_id FROM ANA_TIMED_NODE, " +
                            "ANA_STAGE " +
                            "WHERE atn_node_fk IN " +
                            "( SELECT rel_parent_fk FROM ANA_RELATIONSHIP, " +
                            "ANA_TIMED_NODE, ANA_STAGE " +
                            "WHERE rel_child_fk = atn_node_fk " + 
                            "AND atn_node_fk = " + stageTerm.getDBID() + " " + 
                            "AND atn_stage_fk = stg_oid " +
                            "AND stg_sequence = '" + stageTerm.getPresentIn() +
                            "') " +
                            "AND atn_stage_fk = stg_oid " + 
                            "AND stg_sequence = '" + stageTerm.getPresentIn() +
                            "'" );

                        while ( partofRS.next() )  {
                            //System.out.println("adding parent for time
                            // "component " + stageTerm.getName() + " at " +
                            // stageTerm.getPresentIn() + ": " +
                            // partofRS.getString("atn_public_id") );
                            stageTerm.addPartOf( partofRS.getString(
                                    "atn_public_id") );
                        } // while partofRS                 

                        partofRS.close();
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }     
                    
                    //add stage term to term list
                    if ( defaultroot ) termList.add(stageTerm);
                }

                stagetermRS.close();
            }
            catch (SQLException ex) {
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
            stageRS = connection.createStatement().executeQuery(
                    "SELECT stg_oid, stg_name from ANA_STAGE" );
        
            // while nodeRS
            while ( stageRS.next() )  {
                term = new Component();
                term.setName( stageRS.getString("stg_name") );
                term.setID( stageRS.getString("stg_name") );
                term.setDBID( stageRS.getString("stg_oid") );

                // add term to the term list
                term.setIsA( stageClass.getID() );
                term.setNamespace( stageClass.getNamespace() );
                termList.add( term );
            } 

            stageRS.close();
        }
        catch (SQLException ex) {
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

        /* IS_A ????
        rel = new Relation();
        rel.setName( "is a" );
        rel.setID( "is_a" );
        rel.setTransitive( "true" );
        relationList.add( rel );
        */

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

    public ArrayList < String > getProjects(){

        ArrayList<String> projects = new ArrayList<String>();

        try
        {
            String query = "SELECT APJ_NAME FROM ANA_PROJECT";

            ResultSet rs =
                    this.connection.createStatement().executeQuery(query);

            while (rs.next()){
                projects.add( rs.getString("APJ_NAME") );
            }

            rs.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return projects;
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
