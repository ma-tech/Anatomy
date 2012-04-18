begin work;

/* SQL to create the Adult Kidney (GenePaint) perspective, a custom 
 * perspective for mouse adult kidney slides produced by GenePaint 
 * robots for the EuReGene project.
 *
 * These slides always show
 *  o only the kidney
 *  o the same 4 sections of the kidney
 *
 * Brigitte Kaissling uses this perspective in Fiatas to annotate 
 * these types of images.
 *
 * In other words, this is a highly specific perspective.
 */


/* Create the perspective defintion */
 
insert into ANA_PERSPECTIVE
    ( PSP_NAME, PSP_COMMENTS )
  values 
    ( "Adult Kidney (GenePaint)",
      "A custom perspective for use with adult kidney images produced by GenePaint for the EuReGene project.  Brigitte Kaissling uses this in Fiatas to annotate such images.  This is a highly idiosyncratic perspective and should not be used for any other purpose.  The whole need for this perspective may go away when we split TS28 into juvinile and adult." );


/* Define the top level nodes in the perspective. */

insert into ANA_NODE_IN_PERSPECTIVE
    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select NULL, "Adult Kidney (GenePaint)", ANO_OID
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:18679"; /* renal capsule */

insert into ANA_NODE_IN_PERSPECTIVE
    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select NULL, "Adult Kidney (GenePaint)", ANO_OID
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:17952"; /* renal cortex */

insert into ANA_NODE_IN_PERSPECTIVE
    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select NULL, "Adult Kidney (GenePaint)", ANO_OID
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:19279"; /* renal medulla */

insert into ANA_NODE_IN_PERSPECTIVE
    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select NULL, "Adult Kidney (GenePaint)", ANO_OID
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:18676"; /* calyx */

insert into ANA_NODE_IN_PERSPECTIVE
    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select NULL, "Adult Kidney (GenePaint)", ANO_OID
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:17948"; /* pelvis */

insert into ANA_NODE_IN_PERSPECTIVE
    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select NULL, "Adult Kidney (GenePaint)", ANO_OID
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:28373"; /* renal artery */

insert into ANA_NODE_IN_PERSPECTIVE
    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select NULL, "Adult Kidney (GenePaint)", ANO_OID
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:28376"; /* renal vein */

insert into ANA_NODE_IN_PERSPECTIVE
    ( NIP_OID, NIP_PERSPECTIVE_FK, NIP_NODE_FK)
  select NULL, "Adult Kidney (GenePaint)", ANO_OID
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:29691"; /* renal nerve */



/* Now populate ANA_PERSPECTIVE_AMBIT.
 * This contains everything from above as a start node, plus 
 * the metanephros as a start/stop, plus all the groups as
 * stop nodes.
 */

create temporary table PAM_TEMP (
  PAMT_OID int(10) unsigned NOT NULL,
  PAMT_PERSPECTIVE_FK varchar(25) NOT NULL,
  PAMT_NODE_FK int(10) unsigned NOT NULL,
  PAMT_IS_START tinyint(1) NOT NULL,
  PAMT_IS_STOP tinyint(1) NOT NULL,
  PAMT_COMMENTS varchar(255) default NULL,
  PRIMARY KEY  (`PAMT_OID`)
);

insert into PAM_TEMP
   select NIP_OID
            - ( select min(NIP_OID) 
                  from ANA_NODE_IN_PERSPECTIVE
                  where NIP_PERSPECTIVE_FK = "Adult Kidney (GenePaint)")
            + 1
            + ( select max(OBJ_OID)
                  from ANA_OBJECT ),
          NIP_PERSPECTIVE_FK,
          NIP_NODE_FK,
          true,
          false,
          null
    from ANA_NODE_IN_PERSPECTIVE
    where NIP_PERSPECTIVE_FK = "Adult Kidney (GenePaint)";


insert into ANA_OBJECT
    (OBJ_OID)
  select PAMT_OID
    from PAM_TEMP;

insert into ANA_PERSPECTIVE_AMBIT
    (PAM_OID, PAM_PERSPECTIVE_FK, PAM_NODE_FK, PAM_IS_START, PAM_IS_STOP, PAM_COMMENTS)
  select PAMT_OID, PAMT_PERSPECTIVE_FK, PAMT_NODE_FK, PAMT_IS_START, PAMT_IS_STOP, PAMT_COMMENTS
    from PAM_TEMP;

delete from PAM_TEMP;


/* Add metanephros as start/stop node. */

insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "inlcude metanephros only to support ubiquitous annotation."
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:17373";

insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


/* Add all the groups we can think of as stop nodes */

insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          false,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "developing capillary loop stage";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          false,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "maturing nephron";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          false,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "late tubule";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          false,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "juxtaglomerular complex";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          false,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "Loop of Henle group";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


/* Everything above this is a group that occurs under a start node.  
 * Everything below this is a group that occurs outside a start node.
 */

/* First list groups that don't belong in TS28 adult */

insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          false,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:28500"; /* early tubule */

insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


/* Next list groups that do belong in TS28 adult, but that we don't want to 
 * expand. 
 */

insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "mature nephron";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;




insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "collecting duct";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;



insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_PUBLIC_ID = "EMAPA:28494"; /* ureteric trunk */

insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;



insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "renal interstitium group";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;



insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "small blood vessels";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "renal capillaries";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;



insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "renal vasculature";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;




insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "renal arterial system";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;




insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "renal venous system";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


insert into PAM_TEMP
  select ( select max(OBJ_OID) + 1 
             from ANA_OBJECT ),
         "Adult Kidney (GenePaint)",
          ANO_OID,
          true,
          true,
          "suppress expansion of groups to make Fiatas display cleaner"
    from ANA_NODE
    where ANO_COMPONENT_NAME = "large blood vessels";


insert into ANA_OBJECT
    ( OBJ_OID )
  select max(PAMT_OID)
    from PAM_TEMP;


/* insert everything in PAM_TEMP into ANA_PERSPECTIVE_AMBIT */

insert into ANA_PERSPECTIVE_AMBIT
  select * from PAM_TEMP;




/* Now populate ANAD_PART_OF_PERSPECTIVE using the RegenerateDerivedData step at
 *  http://aberlour/Twiki/bin/view/Anatomy/RegenerateDerivedData
 */

commit work;
