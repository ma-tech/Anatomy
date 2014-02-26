/* A better name for this might be preCiofCompare, since this needs to
 * be run before comparing the DB with the Version003 CIOF file.
 */

/* Changes to anatomy database for:
 *  Make room for new TS27
 *  Create ANAD_PART_OF_PERSPECTIVE
 *  Add ordering to ANA_RELATIONSHIP
 *  Add ID prefix to REF_SPECIES
 */


/* ================================================================
 * Make room for TS27 stage
 * ================================================================
 *  
 * This is only necessary because the CIOF translator runs inserts
 * before it runs updates. 
 */

update ANA_STAGE
  set STG_SEQUENCE = STG_SEQUENCE + 1
  where STG_NAME = "TS28";
show warnings;



/* ================================================================
 * Create new ANAD_PART_OF_PERSPECTIVE table
 * ================================================================
 */

CREATE TABLE ANAD_PART_OF_PERSPECTIVE (
  POP_PERSPECTIVE_FK VARCHAR(25) NOT NULL
    COMMENT 'Perspective that APO record is a part of',
  POP_APO_FK INTEGER UNSIGNED NOT NULL
    COMMENT 'ANAD_PART_OF record that belongs to perspective',
  PRIMARY KEY(POP_PERSPECTIVE_FK, POP_APO_FK),
  INDEX POP_APO_FK_INDEX(POP_APO_FK),
  INDEX POP_PERSPECTIVE_FK_INDEX(POP_PERSPECTIVE_FK)
)
TYPE=InnoDB
COMMENT='Derived; what ANAD_PART_OF records belong to each perspective';

insert into REF_ABBREVIATION 
    ( ABB_TABLE_NAME, ABB_COL_NAME )
  values 
    ( "ANA_PART_OF_PERSPECTIVE", "POP" );



/* ================================================================
 * Add ordering to ANA_RELATIONSHIP
 * ================================================================
 */

ALTER TABLE ANA_RELATIONSHIP 
  add REL_SEQUENCE INTEGER UNSIGNED NULL
    COMMENT "Order to display the child item underneath the parent item, relative to the parent's other children.  Ties are broken alphabetically.  If this is NULL, then order is up to application, but general rule is display NULLs in alphabetical order, after all items with a specified order.";



/* ================================================================
 * Add ID Prefix to REF_SPECIES
 * ================================================================
 */

ALTER TABLE REF_SPECIES 
  add RSP_ID_PREFIX VARCHAR(10) NULL
    COMMENT "For species that have defined ontologies, this string is prepended to the front of all IDs from that ontology.  For example: 'EMAP:'";

update REF_SPECIES
  set RSP_ID_PREFIX = "EMAP:" 
  where RSP_NAME = "mouse";


