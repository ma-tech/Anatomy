begin work;
/* Changes to DB to run after upgrading it to version 3.
 *
 * Changes:
 *  populate ANAD_PART_OF_PERSPECTIVE
 *  Insert CIOF translation into ANA_DELETE_REASON
 *  Insert CIOF translator into ANA_EDITOR
 *  Several EMAP:IDs have been replaced by others.
 */

/* ================================================================
 * POPULATE ANAD_PART_OF_PERSPECTIVE
 * ================================================================
 *  
 * Move this code to the CIOF translator next time.
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


/* ================================================================
 * CREATE CIOF TRANSLATION DELETE REASON
 * ================================================================
 */

insert into ANA_DELETE_REASON
    ( DRE_NAME, DRE_COMMENTS )
  values 
    ( "CIOF translation",
      "Deletion was done as part of a CIOF translation step.  The specific reason why this ID was deleted was not recorded in the CIOF." );

/* ================================================================
 * CREATE CIOF TRANSLATOR EDITOR
 * ================================================================
 */

insert into ANA_OBJECT 
    ( OBJ_OID, OBJ_CREATION_DATETIME )
  select max(OBJ_OID) + 1, NOW()
    from ANA_OBJECT;

insert into ANA_EDITOR
    ( EDI_OID, EDI_NAME )
  select max(OBJ_OID), "CIOF Transator"
    from ANA_OBJECT;


/* ================================================================
 * REPLACE IDs
 * ================================================================
 */

select "List of EMAP IDs that have annotation but that no longer exist.";

select EXP_COMPONENT_ID, count(*)
   from ISH_EXPRESSION
   where not exists (
           select 's'
             from ANA_TIMED_NODE
             where EXP_COMPONENT_ID = ATN_PUBLIC_ID)
   group by EXP_COMPONENT_ID;


/* ID changes */

select "Replacing gonad -> gonad, 8 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:3226"
  where EXP_COMPONENT_ID = "EMAP:27646";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:27646", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:3226 went from (TS18 gonad primordium), to (TS18 gonad), replacing EMAP:27646 which had previously been (TS18 gonad)."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:3226", "EMAP:27646" );


/* --------------------------- */

select "Replacing ureter -> ureter, 1945 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:8234"
  where EXP_COMPONENT_ID = "EMAP:27704";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:27704", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:27704 (TS23 ureter) merged into EMAP:8234 (TS23 ureter) as part of drainage component reorganisation."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:8234", "EMAP:27704" );


/* --------------------------- */

select "Replacing primitive bladder -> primitive bladder, 1 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:30872"
  where EXP_COMPONENT_ID = "EMAP:28552";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28552", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:28552 (TS21 prmitive bladder) became EMAP:30872 (TS21 primitive bladder).  Only the ID changed."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:30872", "EMAP:28552" );


/* --------------------------- */

select "Replacing urethra -> urethra, 2 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:30892"
  where EXP_COMPONENT_ID = "EMAP:28580";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28580", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:28580 (TS21 urethra) became EMAP:30892 (TS21 urethra) as part of general mfemaile/male cleanup."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:30892", "EMAP:28580" );


/* --------------------------- */

/* Male/female distinction removed */
select "Replacing urethra of male -> urethra, 1 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:30902"
  where EXP_COMPONENT_ID = "EMAP:28686";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28686", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:28686 (TS22 urethra of male) became EMAP:30902 (TS22 urethra) as part of general femaile/male cleanup."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:30902", "EMAP:28686" );


/* --------------------------- */

select "Replacing urethra of female -> urethra, 1 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:30902"
  where EXP_COMPONENT_ID = "EMAP:28748";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28748", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:28748 (TS22 urethra of female) became EMAP:30902 (TS22 urethra) as part of general femaile/male cleanup."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:30902", "EMAP:28748" );


/* ----- Start second list of orphasns ---------------------- */

select "Replacing mesonephric mesenchyme -> mesonephric mesenchyme 1 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:2577"
  where EXP_COMPONENT_ID = "EMAP:27586";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:27586", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:27586 (TS17 mesonephric mesenchyme) replaced by EMAP:2577 (TS17 mesonephric mesenchyme).  Only ID changed."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:2577", "EMAP:27586" );


/* --------------------------- */

select "Replacing pelvis -> pelvis 2 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:8232"
  where EXP_COMPONENT_ID = "EMAP:27800";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:27800", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:28585 (TS23 pelvis) replaced by EMAP:8232 (TS23 pelvis).  Only ID changed."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:8232", "EMAP:27800" );


/* --------------------------- */

select "Replacing renal cortex -> renal cortex 3 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:8236"
  where EXP_COMPONENT_ID = "EMAP:27819";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:27819", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:27819 (TS23 renal cortex) replaced by EMAP:8236 (TS23 renal cortex).  Only ID changed."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:8236", "EMAP:27819" );


/* --------------------------- */

select "Replacing renal medullary interstitium -> renal medullary interstitium 4 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:8241"
  where EXP_COMPONENT_ID = "EMAP:28067";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28067", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:28067 (TS23 renal medullary interstitium) replaced by EMAP:8241 (TS23 renal medullary interstitium).  Only ID changed."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:8241", "EMAP:28067" );



/* --------------------------- */

select "Replacing immature loop of Henle -> immature loop of Henle 1 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:30864"
  where EXP_COMPONENT_ID = "EMAP:28173";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28173", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:28173 (TS23 immature loop of Henle) replaced by EMAP:30864 (TS23 immature loop of Henle).  Only ID changed."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:30864", "EMAP:28173" );


/* --------------------------- */

select "Replacing primitive bladder -> primitive bladder 1 record(s)";

update ISH_EXPRESSION
  set EXP_COMPONENT_ID = "EMAP:30871"
  where EXP_COMPONENT_ID = "EMAP:28551";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28551", max(EDI_OID), "CIOF translation", NOW(),
         "EMAP:28551 (TS23 primitive bladder) replaced by EMAP:30871 (TS23 primitive bladder).  Only ID changed."
    from ANA_EDITOR;

insert into ANA_REPLACED_BY
    ( RPL_NEW_PUBLIC_ID, RPL_OLD_PUBLIC_ID )
  values 
    ( "EMAP:30871", "EMAP:28551" );


/* --------------------------- */

select "Deleting papilla/inner medulla at TS23, TS24";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28182", max(EDI_OID), "CIOF translation", NOW(),
         "The papilla / inner medulla does not exist at TS23."
    from ANA_EDITOR;

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28183", max(EDI_OID), "CIOF translation", NOW(),
         "The papilla / inner medulla does not exist at TS24."
    from ANA_EDITOR;


/* --------------------------- */

select "Deleting inner medullary collecting duct at TS23, TS24";

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28185", max(EDI_OID), "CIOF translation", NOW(),
         "The inner medullary collecting duct does not exist at TS23."
    from ANA_EDITOR;

insert into ANA_DELETED_PUBLIC_ID 
    ( DPI_DELETED_PUBLIC_ID, DPI_EDITOR_FK, DPI_REASON_FK, DPI_DATETIME, 
      DPI_COMMENTS )
  select "EMAP:28186", max(EDI_OID), "CIOF translation", NOW(),
         "The inner medullary collecting duct does not exist at TS24."
    from ANA_EDITOR;




/* --------------------------- */

select "List of EMAP IDs that have annotation but that no longer exist.";

select EXP_COMPONENT_ID, count(*)
   from ISH_EXPRESSION
   where not exists (
           select 's'
             from ANA_TIMED_NODE
             where EXP_COMPONENT_ID = ATN_PUBLIC_ID)
   group by EXP_COMPONENT_ID;

select *
   from ISH_EXPRESSION, ISH_SUBMISSION
   where EXP_SUBMISSION_FK = SUB_OID 
     and not exists (
           select 's'
             from ANA_TIMED_NODE
             where EXP_COMPONENT_ID = ATN_PUBLIC_ID)
;


/* --------------------------- */

commit work;
