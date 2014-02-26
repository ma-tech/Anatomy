-- Database Report For 
--  Import of OBO File: /net/homehost/export/home/mwicks/ma-day/workspace/Anatomy/Database/Versions/Version008/ConvertFromVersion007/OBOFiles/Version008_CORRECTIONS_2011-01-10.obo
--  Using Reference: jdbc:mysql://localhost:3306/mouse_jane

-- CRITICAL WARNINGS
-- =================


-- ANA_VERSION
-- ===========

-- Inserting First Object ID for entire database update --

INSERT INTO ANA_OBJECT (obj_oid , obj_creation_datetime, obj_creator_fk) VALUES (33976, NOW(), NULL);

-- Inserting Version --

-- INSERT INTO ANA_VERSION (ver_oid, ver_number, ver_date, ver_comments) VALUES (33976, 8, NOW(), 'DB2OBO Update: Editing the ontology');


-- ANA_NODE
-- ========

-- No records inserted --


-- ANA_RELATIONSHIP

-- ================


-- No records inserted --


-- ANA_TIMED_NODE
-- ==============

-- No records inserted --


-- ANA_SYNONYM

-- ===========


-- No records inserted --


-- INSERTION IN ANA_TIMED_NODE FOR CREATION OF LARGER STAGE RANGES FOR MODIFIED COMPONENTS

-- =======================================================================================


-- No records inserted --


-- INSERTION IN ANA_LOG FOR DELETION OF TIME COMPONENTS FOR MODIFIED STAGE RANGES

-- ==============================================================================


INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9373, 31671, 'ATN_OID', '33108', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9374, 31671, 'ATN_STAGE_FK', '29', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9375, 31671, 'ATN_STAGE_MODIFIER_FK', 'null', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9376, 31671, 'ATN_PUBLIC_ID', 'EMAP:31856', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9377, 31671, 'ATN_NODE_FK', '30239', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9378, 31672, 'ATN_OID', '33921', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9379, 31672, 'ATN_STAGE_FK', '31', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9380, 31672, 'ATN_STAGE_MODIFIER_FK', 'null', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9381, 31672, 'ATN_PUBLIC_ID', 'EMAP:32571', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9382, 31672, 'ATN_NODE_FK', '32666', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9383, 31673, 'ATN_OID', '33922', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9384, 31673, 'ATN_STAGE_FK', '30084', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9385, 31673, 'ATN_STAGE_MODIFIER_FK', 'null', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9386, 31673, 'ATN_PUBLIC_ID', 'EMAP:32572', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9387, 31673, 'ATN_NODE_FK', '32666', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9388, 31674, 'ATN_OID', '33935', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9389, 31674, 'ATN_STAGE_FK', '31', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9390, 31674, 'ATN_STAGE_MODIFIER_FK', 'null', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9391, 31674, 'ATN_PUBLIC_ID', 'EMAP:32585', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9392, 31674, 'ATN_NODE_FK', '32662', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9393, 31675, 'ATN_OID', '33936', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9394, 31675, 'ATN_STAGE_FK', '30084', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9395, 31675, 'ATN_STAGE_MODIFIER_FK', 'null', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9396, 31675, 'ATN_PUBLIC_ID', 'EMAP:32586', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9397, 31675, 'ATN_NODE_FK', '32662', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9398, 31676, 'ATN_OID', '33937', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9399, 31676, 'ATN_STAGE_FK', '31', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9400, 31676, 'ATN_STAGE_MODIFIER_FK', 'null', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9401, 31676, 'ATN_PUBLIC_ID', 'EMAP:32587', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9402, 31676, 'ATN_NODE_FK', '32665', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9403, 31677, 'ATN_OID', '33938', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9404, 31677, 'ATN_STAGE_FK', '30084', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9405, 31677, 'ATN_STAGE_MODIFIER_FK', 'null', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9406, 31677, 'ATN_PUBLIC_ID', 'EMAP:32588', 33976);
INSERT INTO ANA_LOG (log_oid, log_logged_oid, log_column_name, log_old_value, log_version_fk) VALUES (9407, 31677, 'ATN_NODE_FK', '32665', 33976);


-- DELETION FROM ANA_TIMED_NODE FOR MODIFIED STAGE RANGES

-- ======================================================


DELETE FROM ANA_TIMED_NODE WHERE atn_oid = 33108;
DELETE FROM ANA_TIMED_NODE WHERE atn_oid = 33921;
DELETE FROM ANA_TIMED_NODE WHERE atn_oid = 33922;
DELETE FROM ANA_TIMED_NODE WHERE atn_oid = 33935;
DELETE FROM ANA_TIMED_NODE WHERE atn_oid = 33936;
DELETE FROM ANA_TIMED_NODE WHERE atn_oid = 33937;
DELETE FROM ANA_TIMED_NODE WHERE atn_oid = 33938;


-- DELETION FROM ANA_OBJECT FOR MODIFIED COMPONENTS
-- ================================================

-- Deleting Object IDs of Timed Components for Shortening of Stage Ranges in Modified Components --

DELETE FROM ANA_OBJECT WHERE obj_oid = 33108;
DELETE FROM ANA_OBJECT WHERE obj_oid = 33921;
DELETE FROM ANA_OBJECT WHERE obj_oid = 33922;
DELETE FROM ANA_OBJECT WHERE obj_oid = 33935;
DELETE FROM ANA_OBJECT WHERE obj_oid = 33936;
DELETE FROM ANA_OBJECT WHERE obj_oid = 33937;
DELETE FROM ANA_OBJECT WHERE obj_oid = 33938;


-- UPDATE ANA_NODE COMPONENT NAMES
-- ===============================

 -- No records updated --


-- INSERTION IN ANA_SYNONYM FOR CREATION OF NEW SYNONYMS FOR MODIFIED COMPONENTS

-- =============================================================================


-- No records inserted --


-- INSERTION IN ANA_LOG FOR DELETION OF SYNONYMS OF MODIFIED COMPONENTS

-- ===================================================================


-- No records inserted --


-- DELETION FROM ANA_SYNONYM FOR MODIFIED COMPONENTS

-- =================================================


-- No records deleted --


-- DELETION FROM ANA_OBJECT FOR MODIFIED COMPONENTS
-- ================================================

-- Deleting Object IDs of Synonyms for Removal of Synonyms in Modified Components --

 -- No records deleted --


-- INSERTION IN ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT FOR CREATION OF NEW PARENTS FOR MODIFIED COMPONENTS

-- ==============================================================================================================


-- No records inserted --


-- INSERTION IN ANA_LOG FOR DELETION OF PARENT RELATIONSHIPS OF MODIFIED COMPONENTS

-- ================================================================================


-- No records inserted --


-- DELETION FROM ANA_RELATIONSHIP AND ANA_RELATIONSHIP_PROJECT FOR MODIFIED COMPONENTS

-- ===================================================================================


-- No records deleted --


-- DELETION FROM ANA_OBJECT FOR MODIFIED COMPONENTS
-- ================================================

-- Deleting Object IDs of Relationships for Removal of Parents in Modified Components --

 -- No records deleted --


-- UPDATE ANA_NODE PRIMARY STATUS
-- ==============================

 -- No records updated --


-- REORDERING RLP_SEQUENCE IN ANA_RELATIONSHIP_PROJECT FOR MODIFIED COMPONENTS

-- ===========================================================================


-- No records reordered --


-- ANA_LOG

-- =======


-- No records inserted --


-- DELETION FROM ANA_OBJECT, ANA_NODE, ANA_RELATIONSHIP, ANA_TIMED_NODE, ANA_SYNONYM

-- =================================================================================


-- No records deleted --


-- REORDERING RLP_SEQUENCE IN ANA_RELATIONSHIP_PROJECT FOR DELETED COMPONENTS

-- ========================================================================== 


-- No records reordered --


-- -------------------------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------------------------------------------------

-- DATABASE UPDATE SUMMARY
-- =======================

-- ALLOWED UPDATES
-- -------------
-- Total Components from OBO File: 5273

-- NEW      : 0

-- MODIFIED : 4

-- DELETED  : 0

-- BLOCKED UPDATES
-- -------------
-- Total Components containing rule violations: 0

-- Modified Components with rule violations: 0

-- Deleted Components with rule violations: 0


-- ANA_VERSION INSERT SUMMARY
-- ==========================
-- Object id for update starts at 33976
-- Number of Records Inserted is always 1 if an update takes place.

-- ANA_NODE INSERT SUMMARY
-- =======================
-- EMAPA:ID assigned for new components created by user: 
-- Number of Records Inserted: 0

-- ANA_RELATIONSHIP INSERT SUMMARY
-- ===============================
-- Number of Records Inserted: 0

-- ANA_TIMED_NODE INSERT SUMMARY
-- =============================
-- EMAP:ID for Time Components created for new Components: 
-- Number of Records Inserted in ANA_TIMED_NODE: 0

-- ANA_SYNONYM INSERT SUMMARY
-- ==========================
-- Number of records Inserted: 0

-- UPDATE SUMMARY
-- ==============

-- STAGES
-- ----
-- Number of Components whose Stage Range was Extended: 0
-- Number of Components whose Stage Range was Shortened: 4

-- EMAP:ID for Time Components created for stage range modification of existing Components: 
-- Number of Records Inserted in ANA_TIMED_NODE: 0
-- Number of OID Records Deleted for Timed Components in ANA_OBJECT: 7

-- SYNONYMS
-- ------
-- Number of Components whose Synonyms were increased: 0
-- Number of Components whose Synonyms were removed: 0

-- Number of records Inserted: 0
-- Number of OID Records Deleted for Synonyms in ANA_OBJECT: 0

-- PARENTS
-- -----
-- Number of Components whose Parents were increased: 0
-- Number of Components whose Parents were removed: 0

-- Number of Records Inserted: 0
-- Number of OID Records Deleted for Relationships in ANA_OBJECT: 0

-- DELETION SUMMARY
-- ================
-- Number of Components Scheduled to be Deleted: 0
-- Number of Components Succesfully Deleted: 0
-- Number of Scheduled Components for Deletion that were not Deleted: 0


