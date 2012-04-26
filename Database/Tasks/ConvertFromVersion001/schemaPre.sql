/* Changes to Anatomy database for
 *  Dropping alternative stage tables.
 *  Adding short_extra_text column to stage table.
 *  Adding perspectives
 *  Converting logging from form-based to column based.
 *  Make version's PK be a foreign key to ANA_OBJECT.
 *  Add path stage columns to ANAD_PART_OF
 *  Update reference information.
 */


/* ================================================================
 * Drop alternative stage tables
 * ================================================================
 */

drop table ANA_ALTERNATIVE_STAGE;
drop table ANA_ALTERNATIVE_STAGE_SERIES;



/* ================================================================
 * Adding short_extra_text column to stage table.
 * ================================================================
 */

alter table ANA_STAGE add
  STG_SHORT_EXTRA_TEXT VARCHAR(25)
    COMMENT "Very short additional text describing the stage.  This is useful when real estate is tight but you still have enough space to give the user some additional information besides just the somtimes uninformative stage name.  For mouse, this will likely be an equivalent DPC stage range.  For Xenopus, this will likely be the short term for the stage, e.g., blastula.";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E0-2.5"
  where STG_NAME = "TS01";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E1-2.5"
  where STG_NAME = "TS02";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E1-3.5"
  where STG_NAME = "TS03";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E2-4"
  where STG_NAME = "TS04";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E3-5.5"
  where STG_NAME = "TS05";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E4-5.5"
  where STG_NAME = "TS06";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E4.5-6"
  where STG_NAME = "TS07";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E5-6.5"
  where STG_NAME = "TS08";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E6.25-7.25"
  where STG_NAME = "TS09";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E6.5-7.75"
  where STG_NAME = "TS10";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E7.25-8"
  where STG_NAME = "TS11";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E7.5-8.75"
  where STG_NAME = "TS12";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E8-9.25"
  where STG_NAME = "TS13";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E8.5-9.75"
  where STG_NAME = "TS14";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E9-10.25"
  where STG_NAME = "TS15";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E9.5-10.75"
  where STG_NAME = "TS16";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E10-11.25"
  where STG_NAME = "TS17";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E10.5-11.25"
  where STG_NAME = "TS18";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E11-12.25"
  where STG_NAME = "TS19";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E11.5-13"
  where STG_NAME = "TS20";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E12.5-14"
  where STG_NAME = "TS21";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E13.5-15"
  where STG_NAME = "TS22";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E15"
  where STG_NAME = "TS23";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E16"
  where STG_NAME = "TS24";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E17"
  where STG_NAME = "TS25";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "E18"
  where STG_NAME = "TS26";

update ANA_STAGE
  set STG_SHORT_EXTRA_TEXT = "P0-Adult"
  where STG_NAME = "TS28";


select STG_NAME, STG_SHORT_EXTRA_TEXT, STG_SEQUENCE 
  from ANA_STAGE order by STG_SEQUENCE;



/* ================================================================
 * Add Perspectives
 * ================================================================ 
 */

CREATE TABLE ANA_PERSPECTIVE 
  (
    PSP_NAME VARCHAR(25) NOT NULL
      COMMENT "Name of perspective",
    PSP_COMMENTS VARCHAR(1024)
      COMMENT "Description of perspective",
    PRIMARY KEY(PSP_NAME)
  )
  COMMENT 'Perspectives are particular views of subsets of the anatomy'
  ENGINE=InnoDB;

insert into ANA_PERSPECTIVE 
    ( PSP_NAME, PSP_COMMENTS )
  values
    ( "Urogenital", "Reproductive and renal/urinary subset of the mouse anatomy ontology." );

insert into ANA_PERSPECTIVE 
    ( PSP_NAME, PSP_COMMENTS )
  values
    ( "Renal", "Renal/urinary subset of the mouse anatomy ontology." );

insert into ANA_PERSPECTIVE 
    ( PSP_NAME, PSP_COMMENTS )
  values
    ( "Whole mouse", "The entire mouse anatomy ontology." );

select * from ANA_PERSPECTIVE;



CREATE TABLE ANA_NODE_IN_PERSPECTIVE 
  (
    NIP_OID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT
      COMMENT "Unique ID of this row.  This is not unique across the database.",
    NIP_PERSPECTIVE_FK VARCHAR(25) NOT NULL
      COMMENT "Perspective the node is a part of.",
    NIP_NODE_FK INTEGER UNSIGNED NOT NULL
      COMMENT "Node that is part of the perspective.",
    PRIMARY KEY(NIP_OID),
    INDEX ANA_NODE_IN_PERSPECTIVE_FKIndex1(NIP_NODE_FK),
    INDEX ANA_NODE_IN_PERSPECTIVE_FKIndex2(NIP_PERSPECTIVE_FK),
    FOREIGN KEY(NIP_NODE_FK)
      REFERENCES ANA_NODE(ANO_OID)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY(NIP_PERSPECTIVE_FK)
      REFERENCES ANA_PERSPECTIVE(PSP_NAME)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
  )
  COMMENT "Defines what nodes participate in what perspectives."
  ENGINE=InnoDB;

/* Populate Urogenital perspective, ignore TS28 for time being */

insert into ANA_NODE_IN_PERSPECTIVE 
    ( NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select "Urogenital", ANO_OID
    from ANA_NODE
    where ANO_COMPONENT_NAME = "renal/urinary system"
      and ANO_PUBLIC_ID = "EMAPA:17366";


insert into ANA_NODE_IN_PERSPECTIVE 
    ( NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select "Urogenital", ANO_OID
    from ANA_NODE
    where ANO_COMPONENT_NAME = "nephric duct group"
      and ANO_PUBLIC_ID = "EMAPA:28429";


insert into ANA_NODE_IN_PERSPECTIVE 
    ( NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select "Urogenital", ANO_OID
    from ANA_NODE
    where ANO_COMPONENT_NAME in
          ( "urogenital system",
            "reproductive system" );



/* Populate Renal perspective, ignore TS28 for time being. 
 * Not clear to me if we should include urogenital in toto, or
 * just subparts of it. */

insert into ANA_NODE_IN_PERSPECTIVE 
    ( NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select "Renal", ANO_OID
    from ANA_NODE
    where ANO_COMPONENT_NAME = "renal/urinary system"
      and ANO_PUBLIC_ID = "EMAPA:17366";


insert into ANA_NODE_IN_PERSPECTIVE 
    ( NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select "Renal", ANO_OID
    from ANA_NODE
    where ANO_COMPONENT_NAME = "nephric duct group"
      and ANO_PUBLIC_ID = "EMAPA:28429";


insert into ANA_NODE_IN_PERSPECTIVE 
    ( NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select "Renal", ANO_OID
    from ANA_NODE
    where ANO_COMPONENT_NAME = "urogenital system";



/* Populate Mouse */

insert into ANA_NODE_IN_PERSPECTIVE 
    ( NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select "Whole mouse", ANO_OID
    from ANA_NODE
    where ANO_COMPONENT_NAME = "mouse";


select * 
  from ANA_NODE_IN_PERSPECTIVE, ANA_NODE
  where NIP_NODE_FK = ANO_OID;



/* ================================================================
 * Convert logging from form based to column based. 
 * ================================================================
 */

drop table ANA_LOG_DETAIL;
drop table ANA_LOG_FORM_FIELD;
drop table ANA_LOG;
drop table ANA_LOG_FORM;

CREATE TABLE ANA_LOG 
  (
    LOG_OID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT
      COMMENT "Unique, persistent ID for this log entry.  This is not unique across the database.",
    LOG_LOGGED_OID INTEGER UNSIGNED NOT NULL
      COMMENT "OID of record that was updated.  This could be a foreign key to ANA_OBJECT, but then we couldn't keep history for deleted IDs.",
    LOG_VERSION_FK INTEGER UNSIGNED NOT NULL
      comment "What version this update became visible in.",
    LOG_COLUMN_NAME VARCHAR(64) NOT NULL
      COMMENT "Name of database column that was updated.  Maximum 64 characters in a MySQL column name.",
    LOG_OLD_VALUE VARCHAR(255)
      COMMENT "Old value of column expressed as a character string.",
    LOG_COMMENTS VARCHAR(255) NULL
      COMMENT "Comments on this update.",

    PRIMARY KEY(LOG_OID),
    UNIQUE INDEX ALOG_LOGGED_OID_INDEX(LOG_LOGGED_OID, LOG_VERSION_FK, LOG_COLUMN_NAME),
    INDEX ANA_LOG_FKIndex2(LOG_VERSION_FK),
    FOREIGN KEY(LOG_VERSION_FK)
      REFERENCES ANA_VERSION(VER_OID)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
  )
  COMMENT "Records updates to the database."
  ENGINE=InnoDB;




/* ================================================================
 * Make version's PK be a foreign key to ANA_OBJECT.
 * ================================================================
 */

insert into ANA_OBJECT
    ( OBJ_OID, OBJ_CREATION_DATETIME, OBJ_CREATOR_FK )
  select max(OBJ_OID) + 1, CURRENT_TIMESTAMP, NULL
    from ANA_OBJECT;

update ANA_VERSION
  set VER_OID = (select max(OBJ_OID) from ANA_OBJECT);

alter table ANA_VERSION 
  add constraint VER_OID_FOREIGN_KEY
    foreign key (VER_OID)
      references ANA_OBJECT(OBJ_OID)
      on delete no action
      on update no action;

select * from ANA_VERSION;



/* ================================================================
 * Add path stage columns to ANAD_PART_OF, rename existing.
 * ================================================================
 */

/* Have to drop FK constraints before renaming table */

alter table ANAD_PART_OF
  drop foreign key ANAD_PART_OF_ibfk_1;

alter table ANAD_PART_OF
  drop key APO_START_STAGE_FK;

alter table ANAD_PART_OF
  drop foreign key ANAD_PART_OF_ibfk_2;

alter table ANAD_PART_OF
  drop key APO_END_STAGE_FK;


alter table ANAD_PART_OF
  change
    APO_START_STAGE_FK APO_NODE_START_STAGE_FK integer unsigned NOT NULL
      COMMENT "Start stage for node.",
  change
    APO_END_STAGE_FK APO_NODE_END_STAGE_FK integer unsigned NOT NULL
      COMMENT "End stage for node.";

alter table ANAD_PART_OF
  add
    APO_PATH_START_STAGE_FK integer unsigned
      COMMENT "Start stage for node, possibly narrowed by path to get to it."
      after APO_NODE_END_STAGE_FK,
  add
    APO_PATH_END_STAGE_FK integer unsigned
      COMMENT "End stage for node, possibly narrowed by path to get to it."
      after APO_PATH_START_STAGE_FK;

alter table ANAD_PART_OF
  add index APO_NODE_START_STAGE_FK_INDEX (
    APO_NODE_START_STAGE_FK
  );

alter table ANAD_PART_OF
  add CONSTRAINT 
    APO_NODE_START_STAGE_FOREIGN_KEY 
      FOREIGN KEY (APO_NODE_START_STAGE_FK) 
        REFERENCES ANA_STAGE (STG_OID) 
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table ANAD_PART_OF
  add index APO_NODE_END_STAGE_FK_INDEX (
    APO_NODE_END_STAGE_FK
  );

alter table ANAD_PART_OF
  add CONSTRAINT 
    APO_NODE_END_STAGE_FOREIGN_KEY 
      FOREIGN KEY (APO_NODE_END_STAGE_FK) 
        REFERENCES ANA_STAGE (STG_OID) 
        ON DELETE NO ACTION ON UPDATE NO ACTION;


show create table ANAD_PART_OF;


/* ================================================================
 * Update Reference Data
 * ================================================================
 */

delete from REF_ABBREVIATION
  where ABB_TABLE_NAME = "ANA_ALTERNATIVE_STAGE";

delete from REF_ABBREVIATION
  where ABB_TABLE_NAME = "ANA_ALTERNATIVE_STAGE_SERIES";

delete from REF_ABBREVIATION
  where ABB_TABLE_NAME = "ANA_LOG_DETAIL";

delete from REF_ABBREVIATION
  where ABB_TABLE_NAME = "ANA_LOG_FORM_FIELD";

insert into REF_ABBREVIATION
    ( ABB_TABLE_NAME, ABB_COL_NAME )
  values
    ( "ANA_PERSPECTIVE", "PSP" );

insert into REF_ABBREVIATION
    ( ABB_TABLE_NAME, ABB_COL_NAME )
  values
    ( "ANA_NODE_IN_PERSPECTIVE", "NIP" );


select * from REF_ABBREVIATION;

  


/* ================================================================
 * run post load script after data has been loaded.
 * ================================================================
