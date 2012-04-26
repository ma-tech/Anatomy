-- Database Report For 
--  Import of OBO File: /net/homehost/export/home/mwicks/ma-day/workspace/Anatomy/Database/Versions/Version008/ConvertFromVersion007/OBOFiles/JaneA_2010-12-09_Corrected.obo
--  Using Reference: jdbc:mysql://localhost:3306/mouse_jane

-- CRITICAL WARNINGS
-- =================


-- ANA_VERSION
-- ===========

-- Inserting First Object ID for entire database update --

INSERT INTO ANA_OBJECT (obj_oid , obj_creation_datetime, obj_creator_fk) VALUES (33975, NOW(), NULL);

-- Inserting Version --

-- INSERT INTO ANA_VERSION (ver_oid, ver_number, ver_date, ver_comments) VALUES (33975, 8, NOW(), 'DB2OBO Update: Editing the ontology');


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


-- No records inserted --


-- DELETION FROM ANA_TIMED_NODE FOR MODIFIED STAGE RANGES

-- ======================================================


-- No records deleted --


-- DELETION FROM ANA_OBJECT FOR MODIFIED COMPONENTS
-- ================================================

-- Deleting Object IDs of Timed Components for Shortening of Stage Ranges in Modified Components --

 -- No records deleted --


-- UPDATE ANA_NODE COMPONENT NAMES
-- ===============================

UPDATE ANA_NODE SET ano_component_name = 'submucosa of ventral pelvic urethra of male' WHERE ano_public_id = 'EMAPA:32298';


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

-- MODIFIED : 1

-- DELETED  : 0

-- BLOCKED UPDATES
-- -------------
-- Total Components containing rule violations: 0

-- Modified Components with rule violations: 0

-- Deleted Components with rule violations: 0


-- ANA_VERSION INSERT SUMMARY
-- ==========================
-- Object id for update starts at 33975
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
-- Number of Components whose Stage Range was Shortened: 0

-- EMAP:ID for Time Components created for stage range modification of existing Components: 
-- Number of Records Inserted in ANA_TIMED_NODE: 0
-- Number of OID Records Deleted for Timed Components in ANA_OBJECT: 0

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


