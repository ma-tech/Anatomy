/* ================================================================
 * CLEANUP ANA_LOG and REL_SEQUENCE
 * ================================================================
 */      

delete from ANA_LOG
  where LOG_COLUMN_NAME = "REL_SEQUENCE";

update ANA_RELATIONSHIP
  set REL_SEQUENCE = NULL
  where REL_SEQUENCE is not NULL;


/* ================================================================
 * Update orderings
 * ================================================================
 */      

select 'Running orderingUpdates.sql';
source orderingUpdates.sql;

/* ================================================================
 * REPLACE ANAD_PART_OF
 * ================================================================
 */      

select 'Replacing ANAD_PART_OF';
delete from ANAD_PART_OF;
select 'Running ANAD_PART_OF.inserts.sql';
source ANAD_PART_OF.inserts.sql;


/* ================================================================
 * POPULATE ANAD_PART_OF_PERSPECTIVE
 * ================================================================
 */      
delete from ANAD_PART_OF_PERSPECTIVE;

insert into ANAD_PART_OF_PERSPECTIVE
    ( POP_PERSPECTIVE_FK, POP_APO_FK )
  select distinct NIP_PERSPECTIVE_FK, APO_OID 
    from ANAD_PART_OF, ANA_NODE_IN_PERSPECTIVE,
         ANAD_RELATIONSHIP_TRANSITIVE
    where (   APO_NODE_FK = RTR_DESCENDENT_FK
           OR APO_NODE_FK = RTR_ANCESTOR_FK)
      and (   NIP_NODE_FK = RTR_DESCENDENT_FK
           OR NIP_NODE_FK = RTR_ANCESTOR_FK); 


