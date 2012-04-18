/* Script that validates contents of the anatomy database tables.  
 * This is run after a new version has been generated to verify that
 * all expected conditions still hold.
 *
 * In general the tests below should return 0 rows.
 */

select "***** Results of validating the anatomy database. *****" as "";

select 
  "data that fails the test.  If all data passes then no data is displayed." as
  "This runs a series of tests, displaying a test description, followed by any";

select 
  "not conform to expectiations." as
  "This log file is provided to let you know where data in the database does";



/* ============================================================
 * ANA_OBJECT Tests
 *
 * Report any records in ANA_object that don't exist in another table.
 * ============================================================ 
 */

create temporary table temp_oids
  (
    oid integer unsigned not null,
    table_name varchar(64)not null,
    primary key (oid, table_name)
  );

insert into temp_oids
    ( oid, table_name )
  select ANO_OID, "ANA_NODE"
    from ANA_NODE;
    

insert into temp_oids
    ( oid, table_name )
  select ATN_OID, "ANA_TIMED_NODE"
    from ANA_TIMED_NODE;
    

insert into temp_oids
    ( oid, table_name )
  select STG_OID, "ANA_STAGE"
    from ANA_STAGE;
    

insert into temp_oids
    ( oid, table_name )
  select EDI_OID, "ANA_EDITOR"
    from ANA_EDITOR;
    

insert into temp_oids
    ( oid, table_name )
  select VER_OID, "ANA_VERSION"
    from ANA_VERSION;
    

insert into temp_oids
    ( oid, table_name )
  select ATR_OID, "ANA_ATTRIBUTION"
    from ANA_ATTRIBUTION;
    

insert into temp_oids
    ( oid, table_name )
  select SRC_OID, "ANA_SOURCE"
    from ANA_SOURCE;
    

insert into temp_oids
    ( oid, table_name )
  select REL_OID, "ANA_RELATIONSHIP"
    from ANA_RELATIONSHIP;
    

insert into temp_oids
    ( oid, table_name )
  select SYN_OID, "ANA_SYNONYM"
    from ANA_SYNONYM;

insert into temp_oids
    ( oid, table_name )
  select PAM_OID, "ANA_PERSPECTIVE_AMBIT"
    from ANA_PERSPECTIVE_AMBIT;


create temporary table temp_oids2
  (
    oid2 integer unsigned not null,
    table_name2 varchar(64)not null,
    primary key (oid2, table_name2)
  );

insert into temp_oids2
    ( oid2, table_name2 )
  select oid, table_name
    from temp_oids;



/* report IDs that exist in more than one table */


select "TEST: IDs from ANA_OBJECT do not exist in > 1 table" as "";

select oid as "Duplicated OID", table_name "In Tables"
  from temp_oids
  where exists (
        select count(*), oid2
          from temp_oids2
          where oid2 = oid
          group by oid2
          having count(*) > 1)
  order by oid, table_name;

/* Report IDs that exist in ANA_OBJECT, but not in tables */


select "TEST: IDs in ANA_OBJECT exist in another table" as "";

select OBJ_OID as "Orphaned ANA_OBJECT", 
       OBJ_CREATION_DATETIME as "Created",
       OBJ_CREATOR_FK as "Creator"
  from ANA_OBJECT
  where not exists (
          select 'x' 
            from temp_oids
            where oid = OBJ_OID);



/* ============================================================
 * PERSPECTIVE Definition Tests
 * ============================================================ 
 */

/* Perspective definitions are minimal sets.  There should not 
 * be any ancestor/descendent relationships between nodes in the 
 * perspective definitions. */

/* SEE ALSO: ANAD_PART_OF_PERSPECTIVE tests below */



select "TEST: Perspective definitions are minimal" as "";

select ances.ANO_OID as "AncesOID",
       ances.ANO_PUBLIC_ID as "Ances ID",
       ances.ANO_COMPONENT_NAME as "Ances Name",
       descend.ANO_OID as "DescendOID",
       descend.ANO_PUBLIC_ID as "Descend ID",
       descend.ANO_COMPONENT_NAME as "Descend Name",
       anip.NIP_PERSPECTIVE_FK as "Perspective"
  from ANA_NODE_IN_PERSPECTIVE anip, ANA_NODE_IN_PERSPECTIVE dnip,
       ANA_NODE ances, ANA_NODE descend, ANAD_RELATIONSHIP_TRANSITIVE
  where anip.NIP_NODE_FK = ances.ANO_OID
    and dnip.NIP_NODE_FK = descend.ANO_OID
    and ances.ANO_OID = RTR_ANCESTOR_FK
    and descend.ANO_OID = RTR_DESCENDENT_FK
    and anip.NIP_OID <> dnip.NIP_OID
    and anip.NIP_PERSPECTIVE_FK = dnip.NIP_PERSPECTIVE_FK;




select "TEST: Every perspective has at least 1 node" as "";

select PSP_NAME "Perspective without Nodes", PSP_COMMENTS
  from ANA_PERSPECTIVE
  where not exists (
          select 'x'
            from ANA_PERSPECTIVE_AMBIT
            where PAM_PERSPECTIVE_FK = PSP_NAME );



/* ============================================================
 * ANA_NODE Tests
 * ============================================================ 
 */

/* Check that every node has the correct format */


select "TEST: Nodes have valid public ID format" as "";

select ANO_OID as "OID", ANO_SPECIES_FK as "Species", 
       ANO_COMPONENT_NAME as "Name", 
       ANO_PUBLIC_ID as "Offending Public ID"
  from ANA_NODE, REF_SPECIES
  where ANO_SPECIES_FK = RSP_NAME
    and ANO_PUBLIC_ID not regexp concat("^", RSP_NODE_ID_PREFIX, 
                                        "[0-9]{1,7}$");


/* Valid names can
 * Start with number or character
 * contain embedded, but not trailing:
 *   single hyphens, apostrophes, and slashes
 *   comma-space pairs
 *   single spaces
 */

select "TEST: Nodes have valid component name format" as "";

select ANO_OID as "OID", ANO_SPECIES_FK as "Species", 
       ANO_COMPONENT_NAME as "Offending Name", 
       ANO_PUBLIC_ID as "Public ID"
  from ANA_NODE
  where ANO_COMPONENT_NAME not regexp "^([[:alnum:]]+(([-'/]|, | )?[[:alnum:]])+)+$";



/* ============================================================
 * ANA_TIMED_NODE Tests
 * ============================================================ 
 */

/* Make sure every node has at least one timed node. */


select "TEST: Every node has at least one timed node" as "";

select ANO_OID as "OID without Timed Nodes", 
       ANO_COMPONENT_NAME as "Name", 
       ANO_PUBLIC_ID as "Public ID"
  from ANA_NODE
  where not exists (
          select 'x' 
            from ANA_TIMED_NODE
            where ATN_NODE_FK = ANO_OID ); 


/* Check that every timed node has the correct format */


select "TEST: Timed Nodes have valid public ID format" as "";

/*
select ATN_OID as "ATN OID", 
       ATN_PUBLIC_ID as "Offending Public ID"
  from ANA_TIMED_NODE, REF_SPECIES
  where ATN_PUBLIC_ID not regexp concat("^", RSP_TIMED_NODE_ID_PREFIX, 
                                        "[0-9]{1,5}$");
*/

select ATN_OID as "ATN OID", 
       ATN_PUBLIC_ID as "Offending Public ID"
  from ANA_TIMED_NODE, ANA_NODE, REF_SPECIES
  where ATN_NODE_FK = ANO_OID
    and ANO_SPECIES_FK = RSP_NAME
    and ATN_PUBLIC_ID not regexp concat("^", RSP_TIMED_NODE_ID_PREFIX, 
                                        "[0-9]{1,7}$");


/* Check for holes in stage windows.  Do this by checking that the
 * difference between the min and max stage sequence is the same as 
 * the number of timed nodes.  
 * NOTE: This works only as long as stage sequences are dense. 
 */


select "TEST: No holes in stage windows for Nodes" as "";

select ANO_OID as "Node OID", 
       ANO_COMPONENT_NAME as "Name", 
       ANO_PUBLIC_ID as "Public ID", 
       max(STG_SEQUENCE) - min(STG_SEQUENCE) + 1 as "# of stages expected",
       count(*) as "Actual # timed nodes defined."
  from ANA_NODE, ANA_TIMED_NODE, ANA_STAGE
  where ANO_OID = ATN_NODE_FK
    and STG_OID = ATN_STAGE_FK
  group by ANO_OID, ANO_COMPONENT_NAME, ANO_PUBLIC_ID
  having max(STG_SEQUENCE) - min(STG_SEQUENCE) + 1 <> count(*);


/* Check stage modifiers only occur on first or last stage
 *  Early:    Should only occur on first stage
 *  Late:     Should only occur on first stage
 *  LostLate: Should only occur on last stage
 */


select "TEST: Stage modifiers only occur on first or last stage" as "";

/* Test assumptions before running test */

select SMO_NAME as "Untested stage modifier.  Please update test."
  from ANA_STAGE_MODIFIER
  where SMO_NAME not in ("Early", "Late", "LostLate");


/* Check modifiers that must occur on start stage */

select atn.ATN_OID as "ATN OID", 
       atn.ATN_PUBLIC_ID as "Public ID", 
       ANO_COMPONENT_NAME as "Name",
       atn.ATN_STAGE_MODIFIER_FK as "Modifier",
       atnStg.STG_NAME as "Occurs on Stage",
       minStg.STG_NAME as "1st stage",
       maxStg.STG_NAME as "Last stage"
  from ANA_TIMED_NODE atn, ANA_NODE,
       ANA_STAGE atnStg, ANA_STAGE minStg, ANA_STAGE maxStg
 where atn.ATN_STAGE_MODIFIER_FK in ("Early","Late") 
   and ANO_OID = atn.ATN_NODE_FK
   and atnStg.STG_OID = ATN_STAGE_FK
   and minStg.STG_SEQUENCE = (
         select min(STG_SEQUENCE) 
           from ANA_STAGE mns, ANA_TIMED_NODE minAtn
           where minAtn.ATN_NODE_FK = ANO_OID
             and mns.STG_OID = minAtn.ATN_STAGE_FK)
   and maxStg.STG_SEQUENCE = (
         select max(STG_SEQUENCE) 
           from ANA_STAGE mxs, ANA_TIMED_NODE maxAtn
           where maxAtn.ATN_NODE_FK = ANO_OID
             and mxs.STG_OID = maxAtn.ATN_STAGE_FK)
   and atnStg.STG_OID <> minStg.STG_OID;


/* Check modifiers that must occur on end stage */

select atn.ATN_OID as "ATN OID", 
       atn.ATN_PUBLIC_ID as "Public ID", 
       ANO_COMPONENT_NAME as "Name",
       atn.ATN_STAGE_MODIFIER_FK as "Modifier",
       atnStg.STG_NAME as "Occurs on Stage",
       minStg.STG_NAME as "1st stage",
       maxStg.STG_NAME as "Last stage"
  from ANA_TIMED_NODE atn, ANA_NODE,
       ANA_STAGE atnStg, ANA_STAGE minStg, ANA_STAGE maxStg
 where atn.ATN_STAGE_MODIFIER_FK = "LostLate"
   and ANO_OID = atn.ATN_NODE_FK
   and atnStg.STG_OID = ATN_STAGE_FK
   and minStg.STG_SEQUENCE = (
         select min(STG_SEQUENCE) 
           from ANA_STAGE mns, ANA_TIMED_NODE minAtn
           where minAtn.ATN_NODE_FK = ANO_OID
             and mns.STG_OID = minAtn.ATN_STAGE_FK)
   and maxStg.STG_SEQUENCE = (
         select max(STG_SEQUENCE) 
           from ANA_STAGE mxs, ANA_TIMED_NODE maxAtn
           where maxAtn.ATN_NODE_FK = ANO_OID
             and mxs.STG_OID = maxAtn.ATN_STAGE_FK)
   and atnStg.STG_OID <> maxStg.STG_OID;


/* ============================================================
 * STAGE Tests
 * ============================================================ 
 */

/* Test that stage sequence values have no gaps.  Much code will break
 * if these have gaps.
 */


select "TEST: No holes in stage sequence values" as "";

select STG_OID as "Stage OID", 
       STG_SPECIES_FK as "Species",
       STG_NAME as "Name", 
       STG_SEQUENCE as "Sequence"
  from ANA_STAGE
  where STG_SPECIES_FK = (
          select STG_SPECIES_FK
            from ANA_STAGE
            group by STG_SPECIES_FK
            having max(STG_SEQUENCE) - min(STG_SEQUENCE) + 1 <> count(*))
  order by STG_SEQUENCE;



/* ============================================================
 * RELATIONSHIP Tests
 * ============================================================ 
 */

/* Relationships should be between two objects of the same type.
 * furthermore, specific relationship types should involve only 
 * specific types of objects.
 */


select "TEST: Relationships are between expected record types." as "";

/* Check assumptions of test before running test */

select RTY_NAME as "Untested rel type.  Please revise tests."
  from ANA_RELATIONSHIP_TYPE
  where RTY_NAME not in ("part-of", "derives-from");


select REL_OID as "Rel OID",
       REL_RELATIONSHIP_TYPE_FK as "Rel Type",
       REL_CHILD_FK as "Child OID",
       child.ANO_COMPONENT_NAME as "Child Name",
       child.ANO_PUBLIC_ID as "Child Public ID",
       REL_PARENT_FK "Parent OID",
       parent.ANO_COMPONENT_NAME as "Parent Name",
       parent.ANO_PUBLIC_ID as "Parent Public ID"
  from ANA_RELATIONSHIP
       left outer join ANA_NODE child
         on child.ANO_OID = REL_CHILD_FK
       left outer join ANA_NODE parent
         on parent.ANO_OID = REL_PARENT_FK
  where REL_RELATIONSHIP_TYPE_FK = "part-of"
    and (   child.ANO_OID is NULL
         or parent.ANO_OID is NULL);

select REL_OID as "Rel OID",
       REL_RELATIONSHIP_TYPE_FK as "Rel Type",
       REL_CHILD_FK as "Child OID",
       childNode.ANO_COMPONENT_NAME as "Child Name",
       childAtn.ATN_PUBLIC_ID as "Child Public ID",
       REL_PARENT_FK "Parent OID",
       parentNode.ANO_COMPONENT_NAME as "Parent Name",
       parentAtn.ATN_PUBLIC_ID as "Parent Public ID"
  from ANA_RELATIONSHIP
       left outer join ANA_TIMED_NODE childAtn
         on childAtn.ATN_OID = REL_CHILD_FK
       left outer join ANA_NODE childNode
         on childNode.ANO_OID = childAtn.ATN_NODE_FK
       left outer join ANA_TIMED_NODE parentAtn
         on parentAtn.ATN_OID = REL_PARENT_FK
       left outer join ANA_NODE parentNode
         on parentNode.ANO_OID = parentAtn.ATN_NODE_FK
  where REL_RELATIONSHIP_TYPE_FK = "derives-from"
    and (   childAtn.ATN_OID is NULL
         or parentAtn.ATN_OID is NULL);



/* Every node should participate in at least one part-of relationship */


select "TEST: Every node in at least 1 part-of relationship" as "";

select ANO_OID as "Node OID",
       ANO_COMPONENT_NAME as "Name",
       ANO_PUBLIC_ID as "Public ID"
  from ANA_NODE
  where not exists (
          select 'x'
            from ANA_RELATIONSHIP
            where ANO_OID = REL_CHILD_FK)
    and not exists (
          select 'x'
            from ANA_RELATIONSHIP
            where ANO_OID = REL_PARENT_FK); 


/* Every group node should be a parent in at least one part-of relationship */


select "TEST: Every group has members." as "";

select ANO_OID as "Group Node OID",
       ANO_COMPONENT_NAME as "Name",
       ANO_PUBLIC_ID as "Public ID"
  from ANA_NODE
  where not ANO_IS_PRIMARY 
    and not exists (
          select 'x'
            from ANA_RELATIONSHIP
            where ANO_OID = REL_PARENT_FK);



/* Every lineage relationship from
 *  earlier to later timed nodes, or 
 *  concurrent to concurrent timed nodes 
 */


select "TEST: Lineage relationships move forward in time by at most one stage" as "";

select REL_OID as "Rel OID",
       REL_RELATIONSHIP_TYPE_FK as "Rel Type",
       REL_PARENT_FK "Parent OID",
       parentNode.ANO_COMPONENT_NAME as "Parent Name",
       parentAtn.ATN_PUBLIC_ID as "Parent Public ID",
       parentStage.STG_SEQUENCE as "Parent Stage",
       REL_CHILD_FK as "Child OID",
       childNode.ANO_COMPONENT_NAME as "Child Name",
       childAtn.ATN_PUBLIC_ID as "Child Public ID",
       childStage.STG_SEQUENCE as "Child Stage"
  from ANA_RELATIONSHIP
       join ANA_TIMED_NODE childAtn
         on childAtn.ATN_OID = REL_CHILD_FK
       join ANA_NODE childNode
         on childNode.ANO_OID = childAtn.ATN_NODE_FK
       join ANA_STAGE childStage
         on childAtn.ATN_STAGE_FK = childStage.STG_OID
       join ANA_TIMED_NODE parentAtn
         on parentAtn.ATN_OID = REL_PARENT_FK
       join ANA_NODE parentNode
         on parentNode.ANO_OID = parentAtn.ATN_NODE_FK
       join ANA_STAGE parentStage
         on parentAtn.ATN_STAGE_FK = parentStage.STG_OID
  where REL_RELATIONSHIP_TYPE_FK = "derives-from"
    and childStage.STG_SEQUENCE not between parentStage.STG_SEQUENCE
                                        and parentStage.STG_SEQUENCE + 1;


/* Should only be one root node */


select "TEST: Only one root node." as "";

select one.ANO_OID as "Root Node OID",
       one.ANO_COMPONENT_NAME as "Name",
       one.ANO_PUBLIC_ID as "Public ID"
  from ANA_NODE one, ANA_NODE two
  where not exists (
          select 'x'
            from ANA_RELATIONSHIP
            where REL_CHILD_FK = one.ANO_OID)
    and not exists (
          select 'x'
            from ANA_RELATIONSHIP
            where REL_CHILD_FK = two.ANO_OID)
    and one.ANO_OID <> two.ANO_OID
  group by one.ANO_OID, one.ANO_COMPONENT_NAME, one.ANO_PUBLIC_ID
  order by one.ANO_OID;




/* ============================================================
 * VERSION Tests
 * ============================================================ 
 */

/* Version number and date should increase with each other. */


select "TEST: Version number and date increase in synch" as "";

select verX.VER_OID      as "One Version OID",
       verX.VER_NUMBER   as "One Version Number",
       verX.VER_DATE     as "One Version Date",
       verX.VER_COMMENTS as "One Version Comments",
       verY.VER_OID      as "Other Version OID",
       verY.VER_NUMBER   as "Other Version Number",
       verY.VER_DATE     as "Other Version Date",
       verY.VER_COMMENTS as "Other Version Comments"
  from ANA_VERSION verX, ANA_VERSION verY
  where verX.VER_OID < verY.VER_OID
    and (   (    verX.VER_NUMBER < verY.VER_NUMBER
             and verX.VER_DATE   > verY.VER_DATE)
         or (    verX.VER_NUMBER > verY.VER_NUMBER
             and verX.VER_DATE   < verY.VER_DATE)
        )
  order by verX.VER_NUMBER;



/* ============================================================
 * LOG Tests
 * ============================================================ 
 */

/* Test that most recent old value is not the same as current 
 * value of item (if it still exists).
 * NOTE: Can't figure out how to run this test.
 */

/* Date on version should be > Object's create date, if object
 * still exists.  Only tests cases where object still exists in
 * database.
 */


select "TEST: Date on Log entry for object > object's create date." as "";

select LOG_OID as "Log OID",
       LOG_LOGGED_OID as "Object OID",
       LOG_COLUMN_NAME as "Changed Column",
       LOG_OLD_VALUE as "Old Value",
       LOG_COMMENTS as "Log Comments",
       VER_NUMBER as "Version # in Log",
       VER_DATE as "Version Date",
       OBJ_CREATION_DATETIME as "Datetime Object Created"
  from ANA_LOG, ANA_VERSION, ANA_OBJECT
  where LOG_VERSION_FK = VER_OID
    and LOG_LOGGED_OID = OBJ_OID
    and VER_DATE <= OBJ_CREATION_DATETIME;


/* Two consecutive old_values should not have the same value. 
 * NOTE: This test only works as long as version numbers are dense 
 *       integers
 */


select "TEST: Two consecutive old values in log are not the same." as "";

select logPrev.LOG_LOGGED_OID  as "Object OID",
       logPrev.LOG_COLUMN_NAME as "Changed Column",
       logPrev.LOG_OLD_VALUE   as "Old Value in both recs",
       verPrev.VER_NUMBER      as "Older Version #",
       logPrev.LOG_OID         as "Older Log OID",
       logPrev.LOG_COMMENTS    as "Older Log Comments",
       logNext.LOG_OID         as "Younger Log OID",
       logNext.LOG_COMMENTS    as "Younger Log Comments",
       verNext.VER_NUMBER      as "Younger Version #"
  from ANA_LOG logPrev, ANA_VERSION verPrev,
       ANA_LOG logNext, ANA_VERSION verNext
  where logPrev.LOG_VERSION_FK = verPrev.VER_OID
    and logNext.LOG_VERSION_FK = verNext.VER_OID
    and verPrev.VER_NUMBER = verNext.VER_NUMBER + 1
    and logPrev.LOG_LOGGED_OID = logNext.LOG_LOGGED_OID
    and logPrev.LOG_COLUMN_NAME = logNext.LOG_COLUMN_NAME
    and logPrev.LOG_OLD_VALUE = logNext.LOG_OLD_VALUE;
/*
    and verNext.VER_NUMBER = (
          select max(VER_NUMBER)
            from ANA_VERSION);
*/


/* ============================================================
 * DELETED_PUBLIC_ID Tests
 * ============================================================ 
 */

/* Make sure ID is really Deleted */


select "TEST: Deleted Public IDs are no longer active." as "";

select DPI_DELETED_PUBLIC_ID as "'Deleted' ID that still exists in DB"
  from ANA_DELETED_PUBLIC_ID, ANA_TIMED_NODE
  where DPI_DELETED_PUBLIC_ID = ATN_PUBLIC_ID;

select DPI_DELETED_PUBLIC_ID as "'Deleted' ID that still exists in DB"
  from ANA_DELETED_PUBLIC_ID, ANA_NODE
  where DPI_DELETED_PUBLIC_ID = ANO_PUBLIC_ID;



/* ============================================================
 * ANAD_PART_OF derived table Tests
 * ============================================================ 
 */


/* ***** Create temp table for reuse in several queries ***** */


select "Creating temp table(s) for testing ANAD_PART_OF (very slow)" as "";


/* Temp table keeps track of any APO entry that has child entries.
 * For each parent it also keeps track of the SEQUENCE number of the
 * next parent in the tree at the same or lesser depth.  Everything
 * between the parent's sequence number and the next parent's sequence 
 * number is, by definition, a descendent of this parent.
 * 
 * We could use APO_PARENT_APO_FK to speed up the preparation, but then 
 * tests would not be valid, as that is one of the things we wish to test.
 * 
 * THIS TABLE IS USED IN LATER TESTS.
 */

create temporary table nextParentApo_temp 
  (
    NPAT_PARENT_APO_FK integer unsigned,
    NPAT_NEXT_PARENT_SEQUENCE integer unsigned,
    primary key (NPAT_PARENT_APO_FK)
  );

/* get every record with a record after it at lesser or equal depth */
insert into nextParentApo_temp
    (NPAT_PARENT_APO_FK, NPAT_NEXT_PARENT_SEQUENCE)
  select parent.APO_OID, min(nextParent.APO_SEQUENCE)
    from ANAD_PART_OF parent, ANAD_PART_OF nextParent
    where nextParent.APO_DEPTH   <= parent.APO_DEPTH
      and nextParent.APO_SEQUENCE > parent.APO_SEQUENCE /* +1 */
    group by parent.APO_OID;

select "... 1/2 done" as "";

/* create records for the last parents at each depth*/
insert into nextParentApo_temp
    (NPAT_PARENT_APO_FK, NPAT_NEXT_PARENT_SEQUENCE)
  select parent.APO_OID, 
         (select max(lastApo.APO_SEQUENCE) + 1 from ANAD_PART_OF lastApo) 
    from ANAD_PART_OF parent
    where not exists (
            select 'x' 
              from ANAD_PART_OF nextParent
              where nextParent.APO_DEPTH   <= parent.APO_DEPTH
                and nextParent.APO_SEQUENCE > parent.APO_SEQUENCE );

select "... done" as "";



/* ***** Test ANAD_PART_OF root node consistency ***** */


select "TEST: ANAD_PART_OF has only one root node." as "";

select APO_OID       as "APO OID",
       APO_SEQUENCE  as "Sequence",
       APO_DEPTH     as "Depth",
       APO_FULL_PATH as "Path",
       APO_PARENT_APO_FK as "Parent APO OID"
  from ANAD_PART_OF
  where APO_SEQUENCE <> (select min(APO_SEQUENCE) from ANAD_PART_OF) 
    and (   APO_DEPTH = 0 
         or APO_PARENT_APO_FK is NULL
         or position("." in APO_FULL_PATH = 0) )
  order by APO_SEQUENCE;



select "TEST: First record in ANAD_PART_OF is a root node." as "";

select APO_OID       as "APO OID",
       APO_SEQUENCE  as "Sequence",
       APO_DEPTH     as "Depth",
       APO_FULL_PATH as "Path",
       APO_PARENT_APO_FK as "Parent APO OID"
  from ANAD_PART_OF
  where APO_SEQUENCE = (select min(APO_SEQUENCE) from ANAD_PART_OF)
    and (   APO_DEPTH <> 0 
         or APO_PARENT_APO_FK is not NULL
         or position("." in APO_FULL_PATH > 0) );




/* ***** Test ANAD_PART_OF and ANA_RELATIONSHOP consistency ***** */

/* Check that every parent-child combo in ANAD_PART_OF has a corresponding 
 * part of relationship in ANA_RELATIONSHIP 
 */


select "TEST: Every parent-child pair in ANAD_PART_OF has a relationship in ANA_RELATIONSHIP." as "";

select parent.APO_OID       as "PARENT APO OID",
       parent.APO_SEQUENCE  as "Sequence",
       parent.APO_DEPTH     as "Depth",
       parent.APO_FULL_PATH as "Path",
       child.APO_OID        as "CHILD APO OID",
       child.APO_SEQUENCE   as "Sequence",
       child.APO_DEPTH      as "Depth",
       child.APO_FULL_PATH  as "Path"
  from ANAD_PART_OF child, ANAD_PART_OF parent
  where parent.APO_OID = child.APO_PARENT_APO_FK
    and not exists (
          select 'x'
            from ANA_RELATIONSHIP
            where REL_PARENT_FK = parent.APO_NODE_FK
              and REL_CHILD_FK  = child.APO_NODE_FK
              and REL_RELATIONSHIP_TYPE_FK = "part-of" );


/* and vice versa */


select "TEST: Every parent-child part-of pair in ANA_RELATIONSHIP is in ANAD_PART_OF." as "";

select parent.ANO_OID            as "PARENT Node OID",
       parent.ANO_COMPONENT_NAME as "Name",
       parent.ANO_PUBLIC_ID      as "Public ID",
       child.ANO_OID             as "CHILD Node OID",
       child.ANO_COMPONENT_NAME  as "Name",
       child.ANO_PUBLIC_ID       as "Public ID"
  from ANA_RELATIONSHIP, ANA_NODE parent, ANA_NODE child
  where REL_PARENT_FK = parent.ANO_OID
    and REL_CHILD_FK  = child.ANO_OID
    and REL_RELATIONSHIP_TYPE_FK = "part-of"
    and not exists (
          select 'x' 
            from ANAD_PART_OF childApo, ANAD_PART_OF parentApo
            where child.ANO_OID = childApo.APO_NODE_FK
              and parentApo.APO_OID = childApo.APO_PARENT_APO_FK
              and parentApo.APO_NODE_FK = parent.ANO_OID );



/* ***** Test ANAD_PART_OF stage windows ***** */

/* Node stage window should agree with ANA_TIMED_NODE */


select "TEST: ANAD_PART_OF node stage window reflects timed nodes." as "";

select APO_OID as "APO OID",
       ANO_PUBLIC_ID as "Node Public ID",
       startStg.STG_NAME as "APO Node Start Stage",
       endStg.STG_NAME as "APO Node Start Stage",
       minStg.STG_NAME as "Actual Node start stage",
       maxStg.STG_NAME as "Actual Node end stage",
       APO_FULL_PATH as "Path"
  from ANAD_PART_OF, ANA_NODE, 
       ANA_STAGE startStg, ANA_STAGE endStg,
       ANA_STAGE minStg, ANA_STAGE maxStg
  where APO_NODE_FK = ANO_OID
    and APO_NODE_START_STAGE_FK = startStg.STG_OID
    and APO_NODE_END_STAGE_FK   = endStg.STG_OID
    and minStg.STG_SEQUENCE = (
          select min(STG_SEQUENCE)
            from ANA_STAGE, ANA_TIMED_NODE
            where ATN_STAGE_FK = STG_OID
              and ATN_NODE_FK = ANO_OID)
    and maxStg.STG_SEQUENCE = (
          select max(STG_SEQUENCE)
            from ANA_STAGE, ANA_TIMED_NODE
            where ATN_STAGE_FK = STG_OID
              and ATN_NODE_FK = ANO_OID)
    and (   startStg.STG_OID <> minStg.STG_OID
         or endStg.STG_OID <> maxStg.STG_OID);
                        

/* Node stage window should be >= path stage window. */


select "TEST: ANAD_PART_OF node stage window >= path stage window" as "";

select APO_OID               as "APO OID",
       ANO_PUBLIC_ID         as "Node Public ID",
       nodeStartStg.STG_NAME as "Node Start Stage",
       nodeEndStg.STG_NAME   as "Node Start Stage",
       pathStartStg.STG_NAME as "Path start stage",
       pathEndStg.STG_NAME   as "Path end stage",
       APO_FULL_PATH         as "Path"
  from ANAD_PART_OF, ANA_NODE, 
       ANA_STAGE nodeStartStg, ANA_STAGE nodeEndStg,
       ANA_STAGE pathStartStg, ANA_STAGE pathEndStg
  where APO_NODE_FK = ANO_OID
    and APO_NODE_START_STAGE_FK = nodeStartStg.STG_OID
    and APO_NODE_END_STAGE_FK   = nodeEndStg.STG_OID
    and APO_PATH_START_STAGE_FK = pathStartStg.STG_OID
    and APO_PATH_END_STAGE_FK   = pathEndStg.STG_OID
    and (   nodeStartStg.STG_SEQUENCE > pathStartStg.STG_SEQUENCE
         or nodeEndStg.STG_SEQUENCE   < pathEndStg.STG_SEQUENCE);




/* Check child path stage ranges against parent path stage ranges */


select "TEST: ANAD_PART_OF child stage ranges within parent's." as "";

select parent.APO_SEQUENCE   as "PARENT Sequence",
       parent.APO_DEPTH      as "Depth",
       parent.APO_FULL_PATH  as "Path",
       parent.APO_IS_PRIMARY as "primary?",
       child.APO_SEQUENCE    as "CHILD Sequence",
       child.APO_DEPTH       as "Depth",
       child.APO_FULL_PATH   as "Path",
       child.APO_IS_PRIMARY  as "primary?",
       pNodeStart.STG_NAME   as "PARENT node start",
       pPathStart.STG_NAME   as "path start",
       pPathEnd.STG_NAME     as "path end",
       pNodeEnd.STG_NAME     as "node end",
       cNodeStart.STG_NAME   as "CHILD node start",
       cPathStart.STG_NAME   as "path start",
       cPathEnd.STG_NAME     as "path end",
       cNodeEnd.STG_NAME     as "node end"
  from ANAD_PART_OF parent, ANAD_PART_OF child,
       ANA_STAGE pNodeStart, ANA_STAGE pNodeEnd, 
       ANA_STAGE pPathStart, ANA_STAGE pPathEnd, 
       ANA_STAGE cNodeStart, ANA_STAGE cNodeEnd, 
       ANA_STAGE cPathStart, ANA_STAGE cPathEnd
  where parent.APO_OID     = child.APO_PARENT_APO_FK
    /* get all the stages */
    and pNodeStart.STG_OID = parent.APO_NODE_START_STAGE_FK
    and pNodeEnd.STG_OID   = parent.APO_NODE_END_STAGE_FK
    and pPathStart.STG_OID = parent.APO_PATH_START_STAGE_FK
    and pPathEnd.STG_OID   = parent.APO_PATH_END_STAGE_FK
    and cNodeStart.STG_OID = child.APO_NODE_START_STAGE_FK
    and cNodeEnd.STG_OID   = child.APO_NODE_END_STAGE_FK
    and cPathStart.STG_OID = child.APO_PATH_START_STAGE_FK
    and cPathEnd.STG_OID   = child.APO_PATH_END_STAGE_FK
    /* report anything where child outside parents */
    and (   cPathStart.STG_SEQUENCE < pPathStart.STG_SEQUENCE
         or cPathEnd.STG_SEQUENCE   > pPathEnd.STG_SEQUENCE
         or (    child.APO_IS_PRIMARY
             and (   cNodeStart.STG_SEQUENCE < pNodeStart.STG_SEQUENCE
                  or cNodeEnd.STG_SEQUENCE   > pNodeEnd.STG_SEQUENCE)));




/* ***** Test ANAD_PART_OF sequence, depth, and isPrimary basics ***** */

/* Check that sequence is dense */


select "TEST: ANAD_PART_OF sequence values are dense." as "";

select count(*) as "Expected sequence range",
       max(APO_SEQUENCE) - min(APO_SEQUENCE) + 1 as "Actual sequence Range"
  from ANAD_PART_OF
  having count(*) <> max(APO_SEQUENCE) - min(APO_SEQUENCE) + 1;



/* Check that depth, when it increases, only increases by one. */


select "TEST: ANAD_PART_OF depth increases by at most 1 (slow)" as "";

select one.APO_OID       as "1st APO OID",
       one.APO_SEQUENCE  as "1st Sequence",
       one.APO_DEPTH     as "1st Depth",
       one.APO_FULL_PATH as "1st Path",
       two.APO_OID       as "2nd APO OID",
       two.APO_SEQUENCE  as "2nd Sequence",
       two.APO_DEPTH     as "2nd Depth",
       two.APO_FULL_PATH as "2nd Path"
  from ANAD_PART_OF one, ANAD_PART_OF two
  where two.APO_SEQUENCE = one.APO_SEQUENCE + 1 
    and two.APO_DEPTH > one.APO_DEPTH + 1
  order by one.APO_SEQUENCE;



/* Check each node has only one primary path to it in ANAD_PART_OF.
 */


select "TEST: ANAD_PART_OF has one and only one primary path to each node" as "";

select ANO_PUBLIC_ID            as "Public ID", 
       ANO_COMPONENT_NAME       as "Name",
       (select count(*) 
          from ANAD_PART_OF
          where APO_NODE_FK = ANO_OID
            and APO_IS_PRIMARY) as "# Primary Paths"
  from ANA_NODE
  where (select count(*) 
          from ANAD_PART_OF
          where APO_NODE_FK = ANO_OID
            and APO_IS_PRIMARY) <> 1;



/* ***** Check depth, sequence, full path, parent all agree ***** */


/* Create list of all parent-child pairings that exist in ANAD_PART_OF,
 * based on ANA_SEQUENCE and ANA_DEPTH, and then compare the list against 
 * APO_PARENT_APO_FK.
 *
 * Previous tests have verified that ANAD_PART_OF contains only valid
 * relationships, and that it contains all valid relationships.  This
 * test makes sure those relationships occur in the right order in
 * ANAD_PART_OF.
 */


select "TEST: ANAD_PART_OF parent-child pairs based on depth/sequence agree with APO_PARENT_APO_FK (slow)." as "";

create temporary table parentChildApo_temp 
  (
    PCAT_PARENT_APO_FK integer unsigned,
    PCAT_CHILD_APO_FK  integer unsigned,
    primary key (PCAT_PARENT_APO_FK, PCAT_CHILD_APO_FK)
  );

insert into parentChildApo_temp
    ( PCAT_PARENT_APO_FK, PCAT_CHILD_APO_FK )
  select parent.APO_OID, child.APO_OID
  from ANAD_PART_OF parent, ANAD_PART_OF child, nextParentApo_temp
  where parent.APO_OID = NPAT_PARENT_APO_FK
    /* identify immediate children of each parent node */
    and parent.APO_DEPTH  + 1 =  child.APO_DEPTH
    and parent.APO_SEQUENCE < child.APO_SEQUENCE
    and child.APO_SEQUENCE < NPAT_NEXT_PARENT_SEQUENCE;

select parent.APO_SEQUENCE  as "PARENT Sequence",
       parent.APO_DEPTH     as "Depth",
       parent.APO_FULL_PATH as "Path",
       child.APO_SEQUENCE   as "CHILD Sequence",
       child.APO_DEPTH      as "Depth",
       child.APO_FULL_PATH  as "Path"
  from ANAD_PART_OF parent, ANAD_PART_OF child, parentChildApo_temp
  where parent.APO_OID    = PCAT_PARENT_APO_FK
    and child.APO_OID     = PCAT_CHILD_APO_FK
    and parent.APO_OID   <> child.APO_PARENT_APO_FK
  order by child.APO_SEQUENCE;



select "TEST: ANAD_PART_OF parent-child pairs based on APO_PARENT_APO_FK agree with depth/sequence pairings." as "";

select parent.APO_SEQUENCE  as "PARENT Sequence",
       parent.APO_DEPTH     as "Depth",
       parent.APO_FULL_PATH as "Path",
       child.APO_SEQUENCE   as "CHILD Sequence",
       child.APO_DEPTH      as "Depth",
       child.APO_FULL_PATH  as "Path"
  from ANAD_PART_OF parent, ANAD_PART_OF child
  where parent.APO_OID    = child.APO_PARENT_APO_FK
    and not exists (
          select 'x' 
            from parentChildApo_temp
            where parent.APO_OID = PCAT_PARENT_APO_FK
              and child.APO_OID  = PCAT_CHILD_APO_FK )
  order by child.APO_SEQUENCE;



select "TEST: All ANAD_PART_OF full path agrees with parent-child pairs." as "";

select parent.APO_SEQUENCE  as "PARENT Sequence",
       parent.APO_DEPTH     as "Depth",
       parent.APO_FULL_PATH as "Path",
       child.APO_SEQUENCE   as "CHILD Sequence",
       child.APO_DEPTH      as "Depth",
       child.APO_FULL_PATH  as "Path"
  from ANAD_PART_OF parent, ANAD_PART_OF child, ANA_NODE childNode
  where parent.APO_OID    = child.APO_PARENT_APO_FK
    and childNode.ANO_OID = child.APO_NODE_FK
    and child.APO_FULL_PATH <> concat(parent.APO_FULL_PATH, ".",  
                                      ANO_COMPONENT_NAME);




/* Check all descendents of groups are marked as not primary. */


select "TEST: ANAD_PART_OF all descendents of groups marked as not primary." as "";
select ancesApo.APO_SEQUENCE  as "ANCESTOR Sequence",
       ancesApo.APO_DEPTH     as "Depth",
       ancesApo.APO_FULL_PATH as "Path",
       descendApo.APO_SEQUENCE   as "DESCENDENT Sequence",
       descendApo.APO_DEPTH      as "Depth",
       descendApo.APO_FULL_PATH  as "Path"
  from ANAD_PART_OF ancesApo, ANAD_PART_OF descendApo, 
       ANA_NODE ancesNode,    ANA_NODE descendNode, 
       nextParentApo_temp
  where ancesApo.APO_OID     = NPAT_PARENT_APO_FK
    and ancesApo.APO_NODE_FK = ancesNode.ANO_OID
    and not ancesNode.ANO_IS_PRIMARY
    /* identify all descendents of each ancestor node */
    and ancesApo.APO_DEPTH      < descendApo.APO_DEPTH 
    and ancesApo.APO_SEQUENCE   < descendApo.APO_SEQUENCE
    and descendApo.APO_SEQUENCE < NPAT_NEXT_PARENT_SEQUENCE
    and descendApo.APO_NODE_FK  = descendNode.ANO_OID
    and descendApo.APO_IS_PRIMARY;



/* ============================================================
 * ANAD_PART_OF_PERSPECTIVE derived table Tests
 * ============================================================ 
 *
 * Repeat some of ANAD_PART_OF tests for perspectives
 */


select "Creating temp table(s) for testing ANAD_PART_OF_PERSPECTIVE (PAINFULLY slow)" as "";

/* Temp table keeps track of any APO entry in a perspective that has 
 * child entries within that same perspective.
 * For each parent it also keeps track of the SEQUENCE number of the
 * next parent in the tree at the same or lesser depth.  Everything
 * between the parent's sequence number and the next parent's sequence 
 * number is, by definition, a descendent of this parent.
 */

create temporary table nextParentPop_temp 
  (
    NPOP_PARENT_APO_FK integer unsigned,
    NPOP_NEXT_PARENT_SEQUENCE integer unsigned,
    NPOP_PERSPECTIVE_FK varchar(25),
    primary key (NPOP_PARENT_APO_FK, NPOP_PERSPECTIVE_FK)
  );

/* this code can probably be sped up by reusing the nextParentApo_temp
 * table used above.  NOPE. I don't think that can be done.  The calculation
 * of what has a next parent after it is perspective dependent.
 */

/* get every record with a record after it at <= depth */
insert into nextParentPop_temp
    (NPOP_PARENT_APO_FK, NPOP_NEXT_PARENT_SEQUENCE, NPOP_PERSPECTIVE_FK)
  select parentApo.APO_OID, min(nextParentApo.APO_SEQUENCE),
         parentPop.POP_PERSPECTIVE_FK
    from ANAD_PART_OF parentApo, ANAD_PART_OF nextParentApo,
         ANAD_PART_OF_PERSPECTIVE parentPop, 
         ANAD_PART_OF_PERSPECTIVE nextParentPop
    where nextParentApo.APO_DEPTH     <= parentApo.APO_DEPTH
      and nextParentApo.APO_SEQUENCE   > parentApo.APO_SEQUENCE
      and parentApo.APO_OID            = parentPop.POP_APO_FK
      and nextParentApo.APO_OID        = nextParentPop.POP_APO_FK
      and parentPop.POP_PERSPECTIVE_FK = nextParentPop.POP_PERSPECTIVE_FK 
    group by parentApo.APO_OID;

select "... 1/2 done" as "";

/* create records for the last parents at each depth*/
insert into nextParentPop_temp
    (NPOP_PARENT_APO_FK, NPOP_NEXT_PARENT_SEQUENCE, NPOP_PERSPECTIVE_FK)
  select parentApo.APO_OID, 
         (select max(lastApo.APO_SEQUENCE) + 1 
            from ANAD_PART_OF lastApo, ANAD_PART_OF_PERSPECTIVE lastPop
            where lastApo.APO_OID = lastPop.POP_APO_FK
              and lastPop.POP_PERSPECTIVE_FK = parentPop.POP_PERSPECTIVE_FK),
         parentPop.POP_PERSPECTIVE_FK
    from ANAD_PART_OF parentApo, ANAD_PART_OF_PERSPECTIVE parentPop
    where parentApo.APO_OID = parentPop.POP_APO_FK
      and not exists (
            select 'x' 
              from ANAD_PART_OF nextParentApo, 
                   ANAD_PART_OF_PERSPECTIVE nextParentPop
              where nextParentApo.APO_OID = nextParentPop.POP_APO_FK
                and nextParentPop.POP_PERSPECTIVE_FK = parentPop.POP_PERSPECTIVE_FK
                and nextParentApo.APO_DEPTH   <= parentApo.APO_DEPTH
                and nextParentApo.APO_SEQUENCE > parentApo.APO_SEQUENCE );

select "... done" as "";



select "TEST: First record in perspective is a root node." as "";

select min(APO_SEQUENCE), POP_PERSPECTIVE_FK
  from  ANAD_PART_OF_PERSPECTIVE, ANAD_PART_OF
  where POP_APO_FK = APO_OID
  group by POP_PERSPECTIVE_FK
  having min(APO_SEQUENCE) <> (
           select min(APO_SEQUENCE) 
             from ANAD_PART_OF );


/* Check that (POP_APO_FK, POP_NODE_FK) pair points at valid record
 * in ANAD_PART_OF.  This test is only necessary because I can't get the
 * (POP_APO_FK, POP_NODE_FK) pair to be a foreign key to 
 * (APO_OID,    APO_NODE_FK).
 */


select "TEST: ANAD_PART_OF_PERSPECTIVE (POP_APO_FK, POP_NODE_FK) pair point at valid ANAD_PART_OF record." as "";

select POP_PERSPECTIVE_FK     as "Perspective",
       POP_NODE_FK            as "POP Node OID",
       popNode.ANO_PUBLIC_ID as "POP Node Name",
       APO_NODE_FK            as "APO Node OID",
       apoNode.ANO_PUBLIC_ID as "APO Node Name",
       POP_APO_FK             as "APO OID",
       APO_SEQUENCE           as "Sequence",
       APO_DEPTH              as "Depth",
       APO_FULL_PATH          as "Path"
  from ANAD_PART_OF_PERSPECTIVE, ANAD_PART_OF, 
       ANA_NODE popNode, ANA_NODE apoNode
  where POP_APO_FK = APO_OID
    and POP_NODE_FK <> APO_NODE_FK
    and popNode.ANO_OID = POP_NODE_FK
    and apoNode.ANO_OID = APO_NODE_FK;



/* Check that depth, when it increases, only increases by one. */


select "TEST: ANAD_PART_OF_PERSPECTIVE depth increases by at most 1 (slow)" as "";

select apo1.APO_OID       as "1st APO OID",
       apo1.APO_SEQUENCE  as "1st Sequence",
       apo1.APO_DEPTH     as "1st Depth",
       apo1.APO_FULL_PATH as "1st Path",
       apo2.APO_OID       as "2nd APO OID",
       apo2.APO_SEQUENCE  as "2nd Sequence",
       apo2.APO_DEPTH     as "2nd Depth",
       apo2.APO_FULL_PATH as "2nd Path"
  from ANAD_PART_OF apo1, ANAD_PART_OF apo2, 
       ANAD_PART_OF_PERSPECTIVE pop1, ANAD_PART_OF_PERSPECTIVE pop2
  where apo2.APO_SEQUENCE = apo1.APO_SEQUENCE + 1 
    and apo2.APO_DEPTH > apo1.APO_DEPTH + 1
    and apo2.APO_OID = pop2.POP_APO_FK
    and apo1.APO_OID = pop1.POP_APO_FK
  order by apo1.APO_SEQUENCE;


/* ***** Check depth, sequence, full path, parent all agree ***** */

/* This code could be sped up by using the APO_PARENT_APO_FK column */


select "TEST: ANAD_PART_OF_PERSPECTIVE depth, sequence, and full path agree with relationships (PAINFULLY SLOW!)" as "";


select POP_PERSPECTIVE_FK   as "Perspective",
       parent.APO_SEQUENCE  as "PARENT Sequence",
       parent.APO_DEPTH     as "Depth",
       parent.APO_FULL_PATH as "Path",
       child.APO_SEQUENCE   as "CHILD Sequence",
       child.APO_DEPTH      as "Depth",
       child.APO_FULL_PATH  as "Path"
  from ANAD_PART_OF parent, ANAD_PART_OF child, ANA_NODE, 
       nextParentPop_temp, ANAD_PART_OF_PERSPECTIVE
  where child.APO_NODE_FK   = ANO_OID
    and parent.APO_OID      = NPOP_PARENT_APO_FK
    and POP_APO_FK          = child.APO_OID
    and NPOP_PARENT_APO_FK  = parent.APO_OID
    and NPOP_PERSPECTIVE_FK = POP_PERSPECTIVE_FK
    /* identify immediate children of each parent node */
    and parent.APO_DEPTH + 1 = child.APO_DEPTH
    and parent.APO_SEQUENCE < child.APO_SEQUENCE
    and child.APO_SEQUENCE < NPOP_NEXT_PARENT_SEQUENCE
    and (
         /* report anything that does not have corresponding relationship */
            not exists (
              select 'x'
                from ANA_RELATIONSHIP
                where child.APO_NODE_FK = REL_CHILD_FK 
                  and parent.APO_NODE_FK = REL_PARENT_FK )
         /* report anything with wrong path */
         or 
            child.APO_FULL_PATH <> concat(parent.APO_FULL_PATH, ".",  
                                          ANO_COMPONENT_NAME) );

select "... done." as "";



/* ============================================================
 * ANAD_RELATIONSHIP_TRANSITIVE derived table Tests
 * ============================================================ 
 */

/* Check relationship transitive against ANAD_PART_OF table, reusing 
 * temp tables from above
 */


select "TEST: ANAD_RELATIONSHIP_TRANSITIVE relationships missing from ANAD_PART_OF (slow)" as "";

/* Create list of all ancestor-descendent pairings that exist in ANAD_PART_OF. 
 *
 * THIS TEMP TABLE IS REUSED IN OTHER TESTS. 
 */

create temporary table ancestorDescendantApo_temp 
  (
    ADAT_ANCESTOR_APO_FK   integer unsigned,
    ADAT_DESCENDANT_APO_FK integer unsigned,
    primary key (ADAT_ANCESTOR_APO_FK, ADAT_DESCENDANT_APO_FK)
  );

insert into ancestorDescendantApo_temp
    ( ADAT_ANCESTOR_APO_FK, ADAT_DESCENDANT_APO_FK )
  select ances.APO_OID, descend.APO_OID
  from ANAD_PART_OF ances, ANAD_PART_OF descend, nextParentApo_temp
  where ances.APO_OID = NPAT_PARENT_APO_FK
    /* identify descendants of each ancestor node */
    and descend.APO_SEQUENCE > ances.APO_SEQUENCE  
    and descend.APO_SEQUENCE < NPAT_NEXT_PARENT_SEQUENCE;

select RTR_RELATIONSHIP_TYPE_FK       as "Type",
       ancesNode.ANO_PUBLIC_ID        as "ANCESTOR Public ID",
       ancesNode.ANO_COMPONENT_NAME   as "Name",
       descendNode.ANO_PUBLIC_ID      as "DESCENDENT Public ID",
       descendNode.ANO_COMPONENT_NAME as "Name"
  from ANAD_RELATIONSHIP_TRANSITIVE, ANA_NODE ancesNode, ANA_NODE descendNode
  where RTR_ANCESTOR_FK   = ancesNode.ANO_OID
    and RTR_DESCENDENT_FK = descendNode.ANO_OID
    and RTR_ANCESTOR_FK <> RTR_DESCENDENT_FK
    and not exists (
          select 'x'
            from ANAD_PART_OF ancesApo, ANAD_PART_OF descendApo, 
                 ancestorDescendantApo_temp
            where ancesApo.APO_NODE_FK    = ancesNode.ANO_OID
              and ancesApo.APO_OID        = ADAT_ANCESTOR_APO_FK
              and descendApo.APO_NODE_FK  = descendNode.ANO_OID
              and descendApo.APO_OID      = ADAT_DESCENDANT_APO_FK)
  order by ancesNode.ANO_COMPONENT_NAME, descendNode.ANO_COMPONENT_NAME;



select "TEST: ANAD_PART_OF relationships missing from ANAD_RELATIONSHHIP_TRANSITIVE" as "";

select ances.APO_SEQUENCE    as "ANCESTOR Sequence",
       ances.APO_DEPTH       as "Depth",
       ances.APO_FULL_PATH   as "Path",
       descend.APO_SEQUENCE  as "DESCENDENT Sequence",
       descend.APO_DEPTH     as "Depth",
       descend.APO_FULL_PATH as "Path"
  from ANAD_PART_OF ances, ANAD_PART_OF descend, ancestorDescendantApo_temp 
  where ances.APO_OID   = ADAT_ANCESTOR_APO_FK
    and descend.APO_OID = ADAT_DESCENDANT_APO_FK
    and not exists (
          select 'x' 
            from ANAD_RELATIONSHIP_TRANSITIVE 
            where RTR_ANCESTOR_FK   = ances.APO_NODE_FK
              and RTR_DESCENDENT_FK = descend.APO_NODE_FK)
  order by descend.APO_SEQUENCE;


