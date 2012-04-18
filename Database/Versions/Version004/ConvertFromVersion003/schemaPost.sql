/* ================================================================
 *       Anatomy Database Version 4 Schema Changes: POST Loading
 * ================================================================
 *
 * Work that needs to be done after the database has been updated.
 * o Made the new APO_NODE_FK column be non-NULL
 */




/* ================================================================
 * ANAD_PART_OF_PERSPECTIVE.APO_NODE_FK cannot be NULL
 * ================================================================
 */

/* make the new APO_NODE_FK column be not null. */

alter table ANAD_PART_OF_PERSPECTIVE 
  modify POP_NODE_FK integer(10) unsigned NOT NULL
    comment "Node the ANAD_PART_OF record is for.";


