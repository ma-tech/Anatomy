/* ================================================================
 * Run this script after populating the tables.
 * ================================================================
 */





/* include insert that fails on load because of case change. */

insert into ANA_SYNONYM    
    ( SYN_OID, SYN_OBJECT_FK, SYN_SYNONYM)
  values
    ( 30044, 12392, "Malpighian corpuscle");


/* Add not null and FKs to new columns in ANAD_PART_OF */

alter table ANAD_PART_OF
  change APO_PATH_START_STAGE_FK 
         APO_PATH_START_STAGE_FK integer unsigned NOT NULL,
  change APO_PATH_END_STAGE_FK 
         APO_PATH_END_STAGE_FK integer unsigned NOT NULL;

alter table ANAD_PART_OF
  add constraint APO_PATH_START_STAGE_FOREIGN_KEY
    foreign key (APO_PATH_START_STAGE_FK)
      references ANA_STAGE(STG_OID)
      on delete no action
      on update no action;

alter table ANAD_PART_OF
  add constraint APO_PATH_END_STAGE_FOREIGN_KEY
    foreign key (APO_PATH_END_STAGE_FK)
      references ANA_STAGE(STG_OID)
      on delete no action
      on update no action;
