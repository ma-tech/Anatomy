SELECT CONCAT("UPDATE ANA_TIMED_NODE SET ATN_DISPLAY_ID = \"EMAPT:00", SUBSTRING(ANO_PUBLIC_ID, 7), SUBSTRING(STG_NAME, 3), "\" WHERE ATN_OID = ", CAST(ATN_OID AS CHAR), ";") AS "-- TIMED_NODE_NEW_ID"
FROM ANA_TIMED_NODE
join ANA_NODE on ATN_NODE_FK = ANO_OID
join ANA_STAGE on ATN_STAGE_FK = STG_OID
order by ANO_PUBLIC_ID