DROP VIEW IF EXISTS ANAV_STAGE_RANGE;
CREATE VIEW ANAV_STAGE_RANGE AS 
SELECT 
  ana_timed_node.ATN_NODE_FK AS ANAV_NODE_FK,
  MIN(ana_stage.STG_SEQUENCE) AS ANAV_STAGE_MIN,
  MAX(ana_stage.STG_SEQUENCE) AS ANAV_STAGE_MAX 
FROM ana_timed_node 
JOIN ana_stage ON ana_timed_node.ATN_STAGE_FK = ana_stage.STG_OID
GROUP BY ana_timed_node.ATN_NODE_FK;

DROP VIEW IF EXISTS ANAV_RELATION;
CREATE VIEW ANAV_RELATION AS
SELECT
  e.AME_OID AS ANAV_OID, 
  e.AME_ENTRY_EDGE_ID AS ANAV_ENTRY_EDGE_ID, 
  e.AME_DIRECT_EDGE_ID AS ANAV_DIRECT_EDGE_ID, 
  e.AME_EXIT_EDGE_ID AS ANAV_EXIT_EDGE_ID, 
  e.AME_CHILD_NODE AS ANAV_CHILD_ID, 
  a.ANO_PUBLIC_ID AS ANAV_CHILD_NAME, 
  a.ANO_COMPONENT_NAME AS ANAV_CHILD_DESC, 
  e.AME_CHILD_STG_MIN AS ANAV_CHILD_STG_MIN, 
  e.AME_CHILD_STG_MAX AS ANAV_CHILD_STG_MAX, 
  e.AME_PARENT_NODE AS ANAV_PARENT_ID,
  b.ANO_PUBLIC_ID AS ANAV_PARENT_NAME,
  b.ANO_COMPONENT_NAME AS ANAV_PARENT_DESC,
  e.AME_PARENT_STG_MIN AS ANAV_PARENT_STG_MIN,
  e.AME_PARENT_STG_MAX AS ANAV_PARENT_STG_MAX,
  e.AME_HOPS AS ANAV_HOPS,
  e.AME_SOURCE AS ANAV_SOURCE
FROM ANAM_EDGE e
JOIN ANA_NODE AS a ON e.AME_CHILD_NODE = a.ANO_OID
JOIN ANA_NODE AS b ON e.AME_PARENT_NODE = b.ANO_OID;

DROP VIEW IF EXISTS ANAV_GRAND_RELATION;
CREATE VIEW ANAV_GRAND_RELATION AS
SELECT
  a.ANAV_PARENT_ID as ANAV_ID_1,
  a.ANAV_PARENT_NAME as ANAV_NAME_1,
  a.ANAV_PARENT_DESC as ANAV_DESC_1,
  a.ANAV_PARENT_STG_MIN AS ANAV_STG_MIN_1, 
  a.ANAV_PARENT_STG_MAX AS ANAV_STG_MAX_1,
  b.ANAV_OID as ANAV_OID_2,
  b.ANAV_PARENT_ID as ANAV_ID_2,
  b.ANAV_PARENT_NAME as ANAV_NAME_2,
  b.ANAV_PARENT_DESC as ANAV_DESC_2,
  b.ANAV_PARENT_STG_MIN AS ANAV_STG_MIN_2, 
  b.ANAV_PARENT_STG_MAX AS ANAV_STG_MAX_2,
  b.ANAV_CHILD_ID as ANAV_ID_3,
  b.ANAV_CHILD_NAME as ANAV_NAME_3,
  b.ANAV_CHILD_DESC as ANAV_DESC_3,
  b.ANAV_CHILD_STG_MIN AS ANAV_STG_MIN_3, 
  b.ANAV_CHILD_STG_MAX AS ANAV_STG_MAX_3
FROM ANAV_RELATION a
JOIN ANAV_RELATION b on a.ANAV_CHILD_ID = b.ANAV_PARENT_ID
WHERE a.ANAV_HOPS = 0 AND b.ANAV_HOPS = 0
ORDER BY a.ANAV_PARENT_DESC, b.ANAV_PARENT_DESC;

DROP VIEW IF EXISTS ANAV_LEAF_RELATION;
CREATE VIEW ANAV_LEAF_RELATION AS
SELECT
  b.ANO_OID as ANAV_OID_1,
  b.ANO_PUBLIC_ID as ANAV_NAME_1, 
  b.ANO_COMPONENT_NAME as ANAV_DESC_1, 
  c.ANAV_STAGE_MIN AS ANAV_MIN_1,
  c.ANAV_STAGE_MAX AS ANAV_MAX_1,
  a.ANO_OID as ANAV_OID_2, 
  a.ANO_PUBLIC_ID as ANAV_NAME_2, 
  a.ANO_COMPONENT_NAME as ANAV_DESC_2,
  d.ANAV_STAGE_MIN AS ANAV_MIN_2,
  d.ANAV_STAGE_MAX AS ANAV_MAX_2
FROM ANA_RELATIONSHIP
JOIN ANA_NODE AS a ON REL_CHILD_FK = a.ANO_OID
JOIN ANA_NODE AS b ON REL_PARENT_FK = b.ANO_OID
JOIN ANAV_STAGE_RANGE AS d ON a.ANO_OID = d.ANAV_NODE_FK
JOIN ANAV_STAGE_RANGE AS c ON b.ANO_OID = c.ANAV_NODE_FK
WHERE REL_CHILD_FK NOT IN (SELECT REL_PARENT_FK FROM ANA_RELATIONSHIP)
ORDER BY b.ANO_PUBLIC_ID, b.ANO_COMPONENT_NAME;