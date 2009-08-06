/********************************************
 * Script to convert anatomy database from one version to the next.
 *
 * NOTA BENE: No CIOF changes to be applied as data is sourced
 *             from OBO Edit from now on
 *
 */

 select "";

 select "Converting anatomy database from Version004 to Version006" as "";

 select "***** Running schemaPre script. *****"
     as "=====================================";
 source schemaPre.sql;

 select "***** Running createAnaRelationshipProject script. *****"
     as "==============================================";
 source createAnaRelationshipProject.sql;

 select "***** Running createAdultKidneyGenePaint script. *****"
     as "==============================================";
 source createAdultKidneyGenePaint.sql;

 select "***** Running replaceDerivedData script. *****"
     as "==============================================";
 source replaceDerivedData.sql;

 select "***** Running schemaPost script. *****"
     as "======================================";
 source schemaPost.sql;

select "***** Done. *****"
    as "=================";

