/* ================================================================
 *                Anatomy Database Version 4 Schema Changes
 * ================================================================
 *
 *
 * o Remove auto-increment from ANAD_PART_OF.APO_OID
 * o Add parent APO FK and APO_IS_PRIMARY_PATH to ANAD_PART_OF
 * o Add Ancestor flag, Node OID/FK to ANAD_PART_OF_PERSPECTIVE
 * o Add public ID for stages
 * o Lengthen STG_DESCRIPTION
 * o Add description and IS_GROUP columns to ANA_NODE
 * o Add node ID prefix to REF_SPECIES
 * o Create and populate ANA_PERSPECTIVE_AMBIT
 *
 * Most of these changes are motivated by the Xenopus work.
 */


/* ================================================================
 * REMOVE AUTO_INCREMENT FROM ANAD_PART_OF.APO_OID
 * ================================================================
 *
 * It causes us grief in the CIOF translation because parts of that 
 * now need to know the APO OID to generate the ANAD_PART_OR_PERSPECTIVE
 * table.  Have the CIOF translation provied the OIDs.
 */
alter table ANAD_PART_OF 
  modify APO_OID int(10) unsigned NOT NULL
    comment "OID for this record. Since this table is derived, this does not persist across different generations of table.";



/* ================================================================
 * ADD ANAD_PART_OF.APO_IS_PRIMARY_PATH
 * ================================================================
 *
 * Exactly the same value and meaning as existing ANAD_IS_PRIMARY
 * column, but with a better name.
 */

alter table ANAD_PART_OF
  add APO_IS_PRIMARY_PATH BOOL NOT NULL
    comment "True if this is the primary path to this node; false if the path to this node contains a group node.  Every node, including group nodes has exactly 1 primary path to it.";

update ANAD_PART_OF
  set APO_IS_PRIMARY_PATH = APO_IS_PRIMARY;


/* ================================================================
 * ADD ANAD_PART_OF.APO_PARENT_APO_FK
 * ================================================================
 *
 * Without this column, it takes a lot of hoop jumping to figure out 
 * which APO record is the parent of the current APO record.
 */

alter table ANAD_PART_OF
  add APO_PARENT_APO_FK INTEGER UNSIGNED NULL
    comment "OID of the ANAD_PART_OF row that is the parent of this row.";

alter table ANAD_PART_OF
  add constraint APO_PARENT_APO_FOREIGN_KEY 
    foreign key (APO_PARENT_APO_FK) 
      references ANAD_PART_OF (APO_OID)
      on delete cascade
      on update cascade;


/* ================================================================
 * ADD ANAD_PART_OF_PERSPECTIVE.POP_IS_ANCESTOR
 * ================================================================
 *
 * Flag records that are included only because they are ancestors of nodes
 * that are in the perspective.  These nodes are not officially part of the
 * perspective.  They are included here only to provide context, if an
 * application wants to show context.
 */

alter table ANAD_PART_OF_PERSPECTIVE
  add POP_IS_ANCESTOR BOOL NOT NULL
    comment "True if this APO is not part of the perspective and is included here only to give context for nodes that are in the perspective.  If your application wants to see only APOs that are contained in the perspective then exclude ancestor records.";

/* live with initial incorrect values.  They will be fixed by CIOF 
 * processing
 */



/* ================================================================
 * ADD ANAD_PART_OF_PERSPECTIVE.POP_NODE_FK
 * ================================================================
 *  
 * This is purely an ease-of-use change.  Without this column you have 
 * to do a 3 table join to get to nodes, or a 4 table join to get to 
 * timed nodes.
 *
 * So this is redundant data?  Yes, but the whole darn table is 
 * redundant, so if you're going that way already you might as well do 
 * it well.
 * 
 * There are several possibilities for the foreign key:
 *
 * 1. Just don't have one.  Have the validation script check it.
 * 2. Have it point to ANA_NODE.  Still need to have the validation
 *    script check that the (POP_APO_FK, POP_NODE_FK) pair exist in
 *    ANAD_PART_OF_PERSPECTIVE.
 * 3. Add an alternate key to ANAD_PART_OF_PERSPECTIVE on 
 *    (APO_OID, APO_NODE_FK) and have (POP_APO_FK, POP_NODE_FK)
 *    reference it as a foreign key.
 *
 * I like option 3.  It doesn't require an extra validity check, but it
 * does require an extraneous alternate key on ANAD_PART_OF.
 *
 * HOWEVER, I can't get option 3 to work in MySQL.  Switched to option 2.
 */

/*
alter table ANAD_PART_OF
  add constraint APO_AK_FOR_POP_FK
    unique ( APO_NODE_FK, APO_OID );
*/

alter table ANAD_PART_OF_PERSPECTIVE 
  add POP_NODE_FK integer(10) unsigned 
    comment "Node the ANAD_PART_OF record is for.";

/*
alter table ANAD_PART_OF_PERSPECTIVE 
  add constraint POP_APO_NODE_FK 
    foreign key ( POP_NODE_FK, POP_APO_FK )
      references ANA_PART_OF ( APO_NODE_FK, APO_OID )
      on delete cascade;
*/

alter table ANAD_PART_OF_PERSPECTIVE 
  add constraint POP_APO_NODE_FK 
    foreign key ( POP_NODE_FK )
      references ANA_NODE ( ANO_OID )
      on delete cascade;



/* ================================================================
 * ADD ANA_STAGE.STG_PUBLIC_ID
 * ================================================================
 *
 * Xenopus stages have public IDs.  So will EMAP stages as soon as we
 * move to OBO-Edit.
 * Since EMAP doesn't yet have IDs for stages, we can't put a not null
 * or a unique constraint on the column.
 */

alter table ANA_STAGE
  add STG_PUBLIC_ID varchar(20)
    comment "Public ID of stage.  Null if stage does not have a public ID.";

create index STG_PUBLIC_ID_INDEX 
  on ANA_STAGE ( STG_PUBLIC_ID );



/* ================================================================
 * LENGTHEN STG_DESCRIPTION
 * ================================================================
 *
 * Xenopus stage descriptions are longish.
 */

alter table ANA_STAGE
  modify STG_DESCRIPTION varchar(2000)
    comment "Description of stage.  Alternatively, could replace this with a URL.";


/* ================================================================
 * ADD ANA_NODE.ANO_IS_GROUP
 * ================================================================
 *
 * Has the inverse meaning and value as existing ANO_IS_PRIAMRY column,
 * which this is replacing.  The new name/meaning was chosen because
 * it is less confusing when used with APO_IS_PRIMARY_PATH.
 */

alter table ANA_NODE
  add ANO_IS_GROUP BOOL NOT NULL
    comment "True if the node is a group node, False if it is not."
    after ANO_IS_PRIMARY;

update ANA_NODE
  set ANO_IS_GROUP = not ANO_IS_PRIMARY;




/* ================================================================
 * ADD ANA_NODE.ANO_DESCRIPTION
 * ================================================================
 *
 * Xenopus has component descriptions for some items.  Once we move 
 * EMAP to OBO-Edit, it could too.
 */

alter table ANA_NODE
  add ANO_DESCRIPTION varchar(2000)
    comment "Description of this component.  Can be NULL.";


/* ================================================================
 * ADD NODE ID PREFIX to REF_SPECIES
 * ================================================================
 *
 * In version 3 we added RSP_ID_PREFIX, which is the ID prefix for 
 * timed nodes.  I didn't even think about (abstract) nodes, which in 
 * EMAP have a different prefix.
 *
 * Xenopus muddies this picture as it doesn't even have timed nodes.
 */

alter table REF_SPECIES
  change 
    RSP_ID_PREFIX 
    RSP_TIMED_NODE_ID_PREFIX varchar(20) NOT NULL
      comment "For species with timed component ontologies (mouse), this string is at the front of all timed component IDs for this species ('EMAP:').  For species that don't distinguish timed from untimed components (Xenopus), the prefix is the same for both ('XAO:').";

alter table REF_SPECIES
  add RSP_NODE_ID_PREFIX varchar(20)
    comment "This string is prepended to all abstract (untimed) component IDs in this species' ontology.  For example, 'EMAPA:' or 'XAO:'.";

update REF_SPECIES
  set RSP_NODE_ID_PREFIX = "EMAPA:" 
  where RSP_NAME = "mouse";

alter table REF_SPECIES
  modify RSP_NODE_ID_PREFIX varchar(20) NOT NULL
     comment "This string is at the front of all abstract (untimed) component IDs in this species' ontology.  For example, 'EMAPA:' or 'XAO:'.";


/* ================================================================
 * CREATE AND POPULATE ANA_PERSPECTIVE_AMBIT
 * ================================================================
 *
 * Defines the perimeter of a perspective.  This replaces 
 * ANA_NODE_IN_PERSPECTIVE which defined only start nodes.
 * This table supports both start and stop nodes.
 */
 
/* create table with autoincrement PK */

CREATE TABLE ANA_PERSPECTIVE_AMBIT (
  PAM_OID INTEGER UNSIGNED NOT NULL auto_increment unique
    comment "",
  PAM_PERSPECTIVE_FK VARCHAR(25) NOT NULL
    comment "",
  PAM_NODE_FK INTEGER UNSIGNED NOT NULL
    comment "",
  PAM_IS_START BOOL NOT NULL
    comment "",
  PAM_IS_STOP BOOL NOT NULL
    comment "",
  PAM_COMMENTS VARCHAR(255) NULL
    comment "",
  PRIMARY KEY(PAM_OID),
  INDEX ANA_PERSPECTIVE_AMBIT_FKIndex1(PAM_OID),
  INDEX ANA_PERSPECTIVE_AMBIT_FKIndex2(PAM_PERSPECTIVE_FK),
  INDEX ANA_PERSPECTIVE_AMBIT_FKIndex3(PAM_NODE_FK),
  INDEX PAM_AK_INDEX(PAM_PERSPECTIVE_FK, PAM_NODE_FK),
  FOREIGN KEY(PAM_PERSPECTIVE_FK)
    REFERENCES ANA_PERSPECTIVE(PSP_NAME)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(PAM_NODE_FK)
    REFERENCES ANA_NODE(ANO_OID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
comment="Defines boundary nodes for a perspective"
ENGINE=InnoDB;


/* populate it from ANA_NODE_INPERSPECTIVE */

insert into ANA_PERSPECTIVE_AMBIT
    ( PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP,
      PAM_COMMENTS )
  select NIP_PERSPECTIVE_FK, NIP_NODE_FK, TRUE, FALSE, NULL
    from ANA_NODE_IN_PERSPECTIVE;


/* Now line up the OIDs with those in ANA_OBJECT */

alter table ANA_PERSPECTIVE_AMBIT
  modify
     PAM_OID INTEGER UNSIGNED NOT NULL
     comment "";

update ANA_PERSPECTIVE_AMBIT 
  set PAM_OID = PAM_OID + (select max(OBJ_OID) from ANA_OBJECT);


/* and insert them in ANA_OBJECT */

insert into ANA_OBJECT 
    ( OBJ_OID )
  select PAM_OID
    from ANA_PERSPECTIVE_AMBIT;


/* and add the FK */

alter table ANA_PERSPECTIVE_AMBIT
  add constraint PAM_OID_FOREIGN_KEY 
    foreign key (PAM_OID) 
      references ANA_OBJECT (OBJ_OID)
      on delete cascade
      on update cascade;


