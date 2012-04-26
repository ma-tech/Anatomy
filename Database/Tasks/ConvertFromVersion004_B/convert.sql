/********************************************
 * Script to convert anatomy database from one version to the next.
 */

 select "";

 select "Converting anatomy database from Version 4 to Version 4" as "";

 select "***** Running schemaPre script. *****"
     as "=====================================";
 source schemaPre.sql;

 select "***** Running applyCiofChanges script. *****"
     as "============================================";
 source applyCiofChanges.sql;

 select "***** Running updateSiblingOrder script. *****"
     as "==============================================";
 source updateSiblingOrder.sql;

 select "***** Running replaceDerivedData script. *****"
     as "==============================================";
 source replaceDerivedData.sql;

 select "***** Running schemaPost script. *****"
     as "======================================";
 source schemaPost.sql;

select "***** Done. *****"
    as "=================";

