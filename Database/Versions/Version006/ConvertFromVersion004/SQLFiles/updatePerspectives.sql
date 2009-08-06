begin work;

/* Define the top level nodes in the perspective. */

--insert into ANA_NODE_IN_PERSPECTIVE
--    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
--  select NULL, "Urogenital", ANO_OID
--    from ANA_NODE
--    where ANO_PUBLIC_ID = "EMAPA:31581"; /* developing vasculature of genitourinary system GROUP */


/* Now populate ANA_PERSPECTIVE_AMBIT.
 * This contains everything from above as a start node, plus 
 * the metanephros as a start/stop, plus all the groups as
 * stop nodes.
 */

--create temporary table PAM_TEMP (
drop table if exists PAM_TEMP;
create table PAM_TEMP (
  PAMT_OID int(10) unsigned NOT NULL,
  PAMT_PERSPECTIVE_FK varchar(25) NOT NULL,
  PAMT_NODE_FK int(10) unsigned NOT NULL,
  PAMT_IS_START tinyint(1) NOT NULL,
  PAMT_IS_STOP tinyint(1) NOT NULL,
  PAMT_COMMENTS varchar(255) default NULL,
  PRIMARY KEY  (`PAMT_OID`)
);

insert into PAM_TEMP
   select ( select max(OBJ_OID)
              from ANA_OBJECT )
          + 1 ,
          "Urogenital",
          ANO_OID,
          true,
          false,
          null
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:31581";


insert into ANA_OBJECT
    (OBJ_OID)
  select PAMT_OID
    from PAM_TEMP;

insert into ANA_PERSPECTIVE_AMBIT
    (PAM_OID, PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS)
  select PAMT_OID, PAMT_PERSPECTIVE_FK, PAMT_NODE_FK, PAMT_IS_START, PAMT_IS_STOP, PAMT_COMMENTS
    from PAM_TEMP;

delete from PAM_TEMP;


commit work;
