SELECT ATN_PUBLIC_ID, ANO_PUBLIC_ID, d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX
FROM ANA_TIMED_NODE
JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = ATN_NODE_FK
JOIN ANA_NODE ON ATN_NODE_FK = ANO_OID
JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE
JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE
WHERE ATN_PUBLIC_ID = 'EMAP:27679'


SELECT 
a.ATN_OID, a.ATN_NODE_FK, a.ATN_STAGE_FK, a.ATN_STAGE_MODIFIER_FK, a.ATN_PUBLIC_ID, b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE, d.STG_NAME AS STAGE_MIN, e.STG_NAME AS STAGE_MAX
FROM ANA_TIMED_NODE a 
JOIN ANAV_STAGE_RANGE ON ANAV_NODE_FK = a.ATN_NODE_FK
JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK 
JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK 
JOIN ANA_STAGE d ON ANAV_STAGE_MIN = d.STG_SEQUENCE
JOIN ANA_STAGE e ON ANAV_STAGE_MAX = e.STG_SEQUENCE
WHERE a.ATN_PUBLIC_ID = 'EMAP:27679' 