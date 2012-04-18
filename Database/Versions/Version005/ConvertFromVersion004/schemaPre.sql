
/* ================================================================
 *                Anatomy Database Version 5 Schema Changes
 * ================================================================
 *
 *
 * o Add other species to REF_SPECIES.
 * o Repair relationship ordering to high level elements that was
 *   accidentally added in version 4.
 * o Drop deprecated primary columns.
 * o Drop deprecated ANA_NODE_IN_PERSPECTIVE table.
 * o Add large blood vessels group to Urogenital and Renal perspectives?
 */


select "";
select "!!!!! THIS SCRIPT IS ENTIRELY UNTESTED !!!!!";
select "";

/* ================================================================
 * Add other species to REF_SPECIES.
 * ================================================================
 *
 * The first set of species already exist in the GUDMAP DB, just adding them
 * here so they exist in the reference copy of the database.
 *
 * The second set of species already exist in the EuReGene DB.  They were
 * added to support OPT antibody data.
 *
 * These inserts will fail on GUDMAP DB and/or EuReGene DB, but so what.
 */

select
"are already defined in the GUDMAP DB." as
"Adding species to REF_SPECIES to bring it into synch with the species that";

select "";
select
"***** Confirm with Derek that the list here is still correct. ***** " as "";


insert into REF_SPECIES
    ( RSP_NAME, RSP_LATIN_NAME, RSP_TIMED_NODE_ID_PREFIX, RSP_NODE_ID_PREFIX )
  values
    ( "chick", "Gallus gallus", "Unknown", "Unknown" );

insert into REF_SPECIES
    ( RSP_NAME, RSP_LATIN_NAME, RSP_TIMED_NODE_ID_PREFIX, RSP_NODE_ID_PREFIX )
  values
    ( "human", "Homo sapiens", "EHDA:", "EHDAA:" );

insert into REF_SPECIES
    ( RSP_NAME, RSP_LATIN_NAME, RSP_TIMED_NODE_ID_PREFIX, RSP_NODE_ID_PREFIX )
  values
    ( "rat", "Rattus norvegicus", "Unknown", "Unknown" );

insert into REF_SPECIES
    ( RSP_NAME, RSP_LATIN_NAME, RSP_TIMED_NODE_ID_PREFIX, RSP_NODE_ID_PREFIX )
  values
    ( "Xenopus laevis", "Xenopus laevis", "XAO:", "XAO:" );


select "";
select
"***** replacing it with a new ANA_SPECIES table." as
"***** Think about moving REF_SPECIES out of the anatomy database and";



/* ================================================================
 * Repair relationship ordering to high level elements that was
 * accidentally added in version 4.
 * ================================================================
 *
 * These species already exist in the GUDMAP DB, just adding them here so
 * they exist in the reference copy of the database.
 *
 * These inserts will fail on GUDMAP DB, but so what.
 */

select "";
select
"added in version 4." as
"Removing sibling orderings on high-level anatomy that was accidentally";

select
"still limited to only the urogenital parts of the anatomy." as
"It only makes sense to remove these if, in version 5, sibling ordering is";


/* Log the changes
 *
 * Have a chicken-egg problem here.  These changes are part of version 5, but
 * version 5 does not exist until we have applied the CIOF changes.  Create
 * a permanent "temp" table to hold the log records for now, and then apply
 * them in schemaPost.sql.
 *
 * Why not define the version in this file?  We could, but ...
 * Currently the datetime of the version comes from the datetime of the CIOF
 * file.  If we change that to be the datetime this script is run, then we
 * could defne the version here.
 */

create table ANA_LOG_CONVERT_TO_5_DEFERRED
  (
    LOG_LOGGED_OID int(10) unsigned NOT NULL COMMENT
      'OID of record that was updated.  This could be a foreign key to ANA_OBJECT, but then we couldn''t keep history for deleted IDs.',
    LOG_COLUMN_NAME varchar(64) NOT NULL COMMENT
      'Name of database column that was updated.  Maximum 64 characters in a MySQL column name.',
    LOG_OLD_VALUE varchar(255) default NULL COMMENT
      'Old value of column expressed as a character string.',
    LOG_COMMENTS varchar(255) default NULL COMMENT
      'Comments on this update.'
  )
  ENGINE=InnoDB
  COMMENT=
    'Table to hold ANA_LOG updates that cannot be applied until after version 5 is created.';

insert into ANA_LOG_CONVERT_TO_5_DEFERRED
    ( LOG_LOGGED_OID, LOG_COLUMN_NAME, LOG_OLD_VALUE, LOG_COMMENTS )
  select REL_OID, "REL_SEQUENCE", CAST(REL_SEQUENCE as CHAR),
         "Value for this high-level anatomy term was incorrectly set in Version 4.  Set to NULL in version 5."
    from ANA_RELATIONSHIP
    where exists (
            select 'x'
              from ANA_NODE
              where ANO_PUBLIC_ID in (
                      "EMAPA:16103", /* organ system */
                      "EMAPA:16245", /* visceral organ */
                      "EMAPA:16367", /* genitourinary system */
                      "EMAPA:17366", /* urinary system */
                      "EMAPA:17381"  /* reproductive system */
                    )
                and REL_CHILD_FK = ANO_OID
          )
      and REL_SEQUENCE is not NULL;


update ANA_RELATIONSHIP
  set REL_SEQUENCE = NULL
  where exists (
          select 'x'
            from ANA_NODE
            where ANO_PUBLIC_ID in (
                    "EMAPA:16103", /* organ system */
                    "EMAPA:16245", /* visceral organ */
                    "EMAPA:16367", /* genitourinary system */
                    "EMAPA:17366", /* urinary system */
                    "EMAPA:17381"  /* reproductive system */
                  )
              and REL_CHILD_FK = ANO_OID
        )
    and REL_SEQUENCE is not NULL;


/* ================================================================
 * Drop deprecated primary columns.
 * ================================================================
 *
 * Newer, more clearly named columns were added in Version 4.
 */

select "";
select
"columns." as
"Dropping deprecated ANAD_PART_OF.APO_IS_PRIMARY and ANA_NODE.ANO_IS_PRIMARY";

alter table ANAD_PART_OF
  drop APO_IS_PRIMARY;

alter table ANA_NODE
  drop ANO_IS_PRIMARY;



/* ================================================================
 * Drop deprecated ANA_NODE_IN_PERSPECTIVE table.
 * ================================================================
 *
 * This was replaced by the more flexible ANA_PERSPECTIVE_AMBIT table in
 * version 4.
 */

select
"by ANA_PERSPECTIVE_AMBIT." as
"Dropping deprecated table ANA_NODE_IN_PERSPECTIVE.  This has been repalced";

drop table ANA_NODE_IN_PERSPECTIVE;



/* ================================================================
 * Add large blood vessels group to Urogenital and Renal perspectives
 * ================================================================
 *
 * This was inadvertantly left out of these perspectives in version 4.
 */

select "";
select
"Add large blood vessels group to Urogenital and renal perspectives." as "";

/* Urogenital */

insert into ANA_OBJECT
    ( OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK )
  select max(OBJ_OID) + 1, NOW(), NULL
    from ANA_OBJECT;


insert into ANA_PERSPECTIVE_AMBIT
    ( PAM_OID, PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP,
      PAM_COMMENTS )
  select max(OBJ_OID), "Urogenital",
         ( select ANO_OID
             from ANA_NODE
             where ANO_PUBLIC_ID = "EMAPA:31464" ),
         TRUE, FALSE,
         "Contains only arteries and veins in the renal system."
    from ANA_OBJECT;


/* Renal */

insert into ANA_OBJECT
    ( OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK )
  select max(OBJ_OID) + 1, NOW(), NULL
    from ANA_OBJECT;


insert into ANA_PERSPECTIVE_AMBIT
    ( PAM_OID, PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP,
      PAM_COMMENTS )
  select max(OBJ_OID), "Renal",
         ( select ANO_OID
             from ANA_NODE
             where ANO_PUBLIC_ID = "EMAPA:31464" ),
         TRUE, FALSE,
         "Contains only arteries and veins in the renal system."
    from ANA_OBJECT;
