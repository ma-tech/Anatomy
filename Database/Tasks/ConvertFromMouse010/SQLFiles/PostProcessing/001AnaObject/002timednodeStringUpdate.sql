SELECT CONCAT("UPDATE ANA_OBJECT SET OBJ_TABLE = \"ANA_TIMED_NODE\", OBJ_DESCRIPTION = \"", ATN_PUBLIC_ID, " IS ", a.ANO_PUBLIC_ID, " (", a.ANO_COMPONENT_NAME, ") AT ", b.STG_NAME, "\", OBJ_CREATOR_FK = 2 WHERE OBJ_OID = ", ATN_OID, ";") 
AS '-- TIMED_NODE_STRING'
FROM ANA_TIMED_NODE
JOIN ANA_NODE a ON ATN_NODE_FK = a.ANO_OID
JOIN ANA_STAGE b ON ATN_STAGE_FK = b.STG_OID;