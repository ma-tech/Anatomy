-- MySQL dump 10.13  Distrib 5.6.15, for osx10.7 (x86_64)
--
-- Host: localhost    Database: mouse011
-- ------------------------------------------------------
-- Server version	5.6.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ANAD_PART_OF`
--

DROP TABLE IF EXISTS `ANAD_PART_OF`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANAD_PART_OF` (
  `APO_OID` int(10) unsigned NOT NULL COMMENT 'OID for this record. Since this table is derived, this does not persist across different generations of table.',
  `APO_SPECIES_FK` varchar(20) NOT NULL,
  `APO_NODE_START_STAGE_FK` int(10) unsigned NOT NULL COMMENT 'Start stage for node.',
  `APO_NODE_END_STAGE_FK` int(10) unsigned NOT NULL COMMENT 'End stage for node.',
  `APO_PATH_START_STAGE_FK` int(10) unsigned NOT NULL,
  `APO_PATH_END_STAGE_FK` int(10) unsigned NOT NULL,
  `APO_NODE_FK` int(10) unsigned NOT NULL,
  `APO_SEQUENCE` int(10) unsigned NOT NULL,
  `APO_DEPTH` int(10) unsigned NOT NULL,
  `APO_FULL_PATH` varchar(500) DEFAULT NULL,
  `APO_FULL_PATH_OIDS` varchar(500) DEFAULT NULL,
  `APO_FULL_PATH_EMAPAS` varchar(500) DEFAULT NULL,
  `APO_FULL_PATH_JSON_HEAD` varchar(3000) DEFAULT NULL,
  `APO_FULL_PATH_JSON_TAIL` varchar(500) DEFAULT NULL,
  `APO_IS_PRIMARY` tinyint(1) NOT NULL,
  `APO_IS_PRIMARY_PATH` tinyint(1) NOT NULL COMMENT 'True if this is the primary path to this node; false if the path to this node contains a group node.  Every node, including group nodes has exactly 1 primary path to it.',
  `APO_PARENT_APO_FK` int(10) unsigned DEFAULT NULL COMMENT 'OID of the ANAD_PART_OF row that is the parent of this row.',
  PRIMARY KEY (`APO_OID`),
  UNIQUE KEY `APOD_AK_INDEX` (`APO_SPECIES_FK`,`APO_SEQUENCE`),
  KEY `APO_NODE_FK` (`APO_NODE_FK`),
  KEY `APO_NODE_START_STAGE_FK_INDEX` (`APO_NODE_START_STAGE_FK`),
  KEY `APO_NODE_END_STAGE_FK_INDEX` (`APO_NODE_END_STAGE_FK`),
  KEY `APO_PATH_START_STAGE_FOREIGN_KEY` (`APO_PATH_START_STAGE_FK`),
  KEY `APO_PATH_END_STAGE_FOREIGN_KEY` (`APO_PATH_END_STAGE_FK`),
  KEY `APO_PARENT_APO_FOREIGN_KEY` (`APO_PARENT_APO_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANAD_PART_OF_PERSPECTIVE`
--

DROP TABLE IF EXISTS `ANAD_PART_OF_PERSPECTIVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANAD_PART_OF_PERSPECTIVE` (
  `POP_PERSPECTIVE_FK` varchar(25) NOT NULL COMMENT 'Perspective that APO record is a part of',
  `POP_APO_FK` int(10) unsigned NOT NULL COMMENT 'ANAD_PART_OF record that belongs to perspective',
  `POP_IS_ANCESTOR` tinyint(1) NOT NULL COMMENT 'True if this APO is not part of the perspective and is included here only to give context for nodes that are in the perspective.  If your application wants to see only APOs that are contained in the perspective then exclude ancestor records.',
  `POP_NODE_FK` int(10) unsigned NOT NULL COMMENT 'Node the ANAD_PART_OF record is for.',
  PRIMARY KEY (`POP_PERSPECTIVE_FK`,`POP_APO_FK`),
  KEY `POP_APO_FK_INDEX` (`POP_APO_FK`),
  KEY `POP_PERSPECTIVE_FK_INDEX` (`POP_PERSPECTIVE_FK`),
  KEY `POP_APO_NODE_FK` (`POP_NODE_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Derived; what ANAD_PART_OF records belong to each perspectiv';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANAD_RELATIONSHIP_TRANSITIVE`
--

DROP TABLE IF EXISTS `ANAD_RELATIONSHIP_TRANSITIVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANAD_RELATIONSHIP_TRANSITIVE` (
  `RTR_OID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `RTR_RELATIONSHIP_TYPE_FK` varchar(20) NOT NULL,
  `RTR_DESCENDENT_FK` int(10) unsigned NOT NULL,
  `RTR_ANCESTOR_FK` int(10) unsigned NOT NULL,
  PRIMARY KEY (`RTR_OID`),
  UNIQUE KEY `RTR_AK_INDEX` (`RTR_ANCESTOR_FK`,`RTR_DESCENDENT_FK`,`RTR_RELATIONSHIP_TYPE_FK`),
  KEY `RTR_RELATIONSHIP_TYPE_FK` (`RTR_RELATIONSHIP_TYPE_FK`),
  KEY `RTR_DESCENDENT_FK` (`RTR_DESCENDENT_FK`)
) ENGINE=MyISAM AUTO_INCREMENT=1065138 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANAM_EDGE`
--

DROP TABLE IF EXISTS `ANAM_EDGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANAM_EDGE` (
  `AME_OID` int(11) NOT NULL AUTO_INCREMENT,
  `AME_ENTRY_EDGE_ID` int(11) NOT NULL DEFAULT '0',
  `AME_DIRECT_EDGE_ID` int(11) NOT NULL DEFAULT '0',
  `AME_EXIT_EDGE_ID` int(11) NOT NULL DEFAULT '0',
  `AME_CHILD_NODE` varchar(64) NOT NULL DEFAULT ' ',
  `AME_CHILD_STG_MIN` varchar(64) NOT NULL DEFAULT ' ',
  `AME_CHILD_STG_MAX` varchar(64) NOT NULL DEFAULT ' ',
  `AME_PARENT_NODE` varchar(64) NOT NULL DEFAULT ' ',
  `AME_PARENT_STG_MIN` varchar(64) NOT NULL DEFAULT ' ',
  `AME_PARENT_STG_MAX` varchar(64) NOT NULL DEFAULT ' ',
  `AME_HOPS` int(11) NOT NULL DEFAULT '0',
  `AME_SOURCE` varchar(64) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`AME_OID`)
) ENGINE=InnoDB AUTO_INCREMENT=116187 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANAM_TIMED_EDGE`
--

DROP TABLE IF EXISTS `ANAM_TIMED_EDGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANAM_TIMED_EDGE` (
  `AME_T_OID` int(11) NOT NULL AUTO_INCREMENT,
  `AME_T_ENTRY_EDGE_ID` int(11) NOT NULL DEFAULT '0',
  `AME_T_DIRECT_EDGE_ID` int(11) NOT NULL DEFAULT '0',
  `AME_T_EXIT_EDGE_ID` int(11) NOT NULL DEFAULT '0',
  `AME_T_CHILD_NODE` varchar(64) NOT NULL DEFAULT ' ',
  `AME_T_PARENT_NODE` varchar(64) NOT NULL DEFAULT ' ',
  `AME_T_STAGE` varchar(64) NOT NULL DEFAULT ' ',
  `AME_T_HOPS` int(11) NOT NULL DEFAULT '0',
  `AME_T_SOURCE` varchar(64) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`AME_T_OID`)
) ENGINE=InnoDB AUTO_INCREMENT=365187 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_ATTRIBUTION`
--

DROP TABLE IF EXISTS `ANA_ATTRIBUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_ATTRIBUTION` (
  `ATR_OID` int(10) unsigned NOT NULL,
  `ATR_OBJECT_FK` int(10) unsigned NOT NULL,
  `ATR_SOURCE_FK` int(10) unsigned NOT NULL,
  `ATR_EVIDENCE_FK` varchar(50) NOT NULL,
  `ATR_COMMENTS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ATR_OID`),
  UNIQUE KEY `ATR_AK_INDEX` (`ATR_OBJECT_FK`,`ATR_SOURCE_FK`,`ATR_EVIDENCE_FK`),
  KEY `ATR_SOURCE_FK` (`ATR_SOURCE_FK`),
  KEY `ATR_EVIDENCE_FK` (`ATR_EVIDENCE_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_DELETED_PUBLIC_ID`
--

DROP TABLE IF EXISTS `ANA_DELETED_PUBLIC_ID`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_DELETED_PUBLIC_ID` (
  `DPI_DELETED_PUBLIC_ID` varchar(20) NOT NULL,
  `DPI_EDITOR_FK` int(10) unsigned NOT NULL,
  `DPI_REASON_FK` varchar(20) NOT NULL,
  `DPI_DATETIME` datetime NOT NULL,
  `DPI_COMMENTS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DPI_DELETED_PUBLIC_ID`),
  KEY `DPI_REASON_FK` (`DPI_REASON_FK`),
  KEY `DPI_EDITOR_FK` (`DPI_EDITOR_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_DELETE_REASON`
--

DROP TABLE IF EXISTS `ANA_DELETE_REASON`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_DELETE_REASON` (
  `DRE_NAME` varchar(20) NOT NULL,
  `DRE_COMMENTS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DRE_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_EDITOR`
--

DROP TABLE IF EXISTS `ANA_EDITOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_EDITOR` (
  `EDI_OID` int(10) unsigned NOT NULL,
  `EDI_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`EDI_OID`),
  UNIQUE KEY `ANA_EDITOR_NAME_INDEX` (`EDI_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_EVIDENCE`
--

DROP TABLE IF EXISTS `ANA_EVIDENCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_EVIDENCE` (
  `EVI_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`EVI_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_LOG`
--

DROP TABLE IF EXISTS `ANA_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_LOG` (
  `LOG_OID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique, persistent ID for this log entry.  This is not unique across the database.',
  `LOG_LOGGED_OID` int(10) unsigned NOT NULL COMMENT 'OID of record that was updated.  This could be a foreign key to ANA_OBJECT, but then we couldn''t keep history for deleted IDs.',
  `LOG_VERSION_FK` int(10) unsigned NOT NULL COMMENT 'What version this update became visible in.',
  `LOG_COLUMN_NAME` varchar(64) NOT NULL COMMENT 'Name of database column that was updated.  Maximum 64 characters in a MySQL column name.',
  `LOG_OLD_VALUE` varchar(255) DEFAULT NULL COMMENT 'Old value of column expressed as a character string.',
  `LOG_COMMENTS` varchar(255) DEFAULT NULL COMMENT 'Comments on this update.',
  `LOG_DATETIME` datetime DEFAULT NULL,
  `LOG_TABLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`LOG_OID`),
  KEY `ALOG_LOGGED_OID_INDEX` (`LOG_LOGGED_OID`,`LOG_VERSION_FK`,`LOG_COLUMN_NAME`),
  KEY `ANA_LOG_FKIndex2` (`LOG_VERSION_FK`)
) ENGINE=MyISAM AUTO_INCREMENT=11889 DEFAULT CHARSET=latin1 COMMENT='Records updates to the database.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_NODE`
--

DROP TABLE IF EXISTS `ANA_NODE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_NODE` (
  `ANO_OID` int(10) unsigned NOT NULL,
  `ANO_SPECIES_FK` varchar(20) NOT NULL,
  `ANO_COMPONENT_NAME` varchar(255) NOT NULL,
  `ANO_IS_PRIMARY` tinyint(1) NOT NULL,
  `ANO_IS_GROUP` tinyint(1) NOT NULL COMMENT 'True if the node is a group node, False if it is not.',
  `ANO_PUBLIC_ID` varchar(20) NOT NULL,
  `ANO_DESCRIPTION` varchar(2000) DEFAULT NULL COMMENT 'Description of this component.  Can be NULL.',
  `ANO_DISPLAY_ID` varchar(20) NOT NULL,
  PRIMARY KEY (`ANO_OID`),
  UNIQUE KEY `anode_public_id_index` (`ANO_PUBLIC_ID`),
  KEY `anode_component_name_index` (`ANO_COMPONENT_NAME`),
  KEY `ANO_SPECIES_FK` (`ANO_SPECIES_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_NODE_IN_PERSPECTIVE`
--

DROP TABLE IF EXISTS `ANA_NODE_IN_PERSPECTIVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_NODE_IN_PERSPECTIVE` (
  `NIP_OID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique ID of this row.  This is not unique across the database.',
  `NIP_PERSPECTIVE_FK` varchar(25) NOT NULL COMMENT 'Perspective the node is a part of.',
  `NIP_NODE_FK` int(10) unsigned NOT NULL COMMENT 'Node that is part of the perspective.',
  PRIMARY KEY (`NIP_OID`),
  KEY `ANA_NODE_IN_PERSPECTIVE_FKIndex1` (`NIP_NODE_FK`),
  KEY `ANA_NODE_IN_PERSPECTIVE_FKIndex2` (`NIP_PERSPECTIVE_FK`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 COMMENT='Defines what nodes participate in what perspectives.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_NODE_PERSPECTIVE_LINK`
--

DROP TABLE IF EXISTS `ANA_NODE_PERSPECTIVE_LINK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_NODE_PERSPECTIVE_LINK` (
  `NPL_OID` int(11) NOT NULL AUTO_INCREMENT,
  `NPL_PERSPECTIVE_FK` varchar(255) NOT NULL DEFAULT ' ',
  `NPL_NODE_FK` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`NPL_OID`)
) ENGINE=MyISAM AUTO_INCREMENT=6858 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBJECT`
--

DROP TABLE IF EXISTS `ANA_OBJECT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBJECT` (
  `OBJ_OID` int(10) unsigned NOT NULL,
  `OBJ_CREATION_DATETIME` datetime DEFAULT NULL,
  `OBJ_CREATOR_FK` int(10) unsigned DEFAULT NULL,
  `OBJ_DESCRIPTION` varchar(2000) NOT NULL DEFAULT '',
  `OBJ_TABLE` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`OBJ_OID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBO_COMPONENT`
--

DROP TABLE IF EXISTS `ANA_OBO_COMPONENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBO_COMPONENT` (
  `AOC_OID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `AOC_NAME` varchar(255) NOT NULL,
  `AOC_OBO_ID` varchar(25) NOT NULL,
  `AOC_DB_ID` varchar(25) NOT NULL,
  `AOC_NEW_ID` varchar(25) NOT NULL,
  `AOC_NAMESPACE` varchar(50) NOT NULL,
  `AOC_DEFINITION` varchar(1000) NOT NULL,
  `AOC_GROUP` tinyint(1) NOT NULL,
  `AOC_START` varchar(10) NOT NULL,
  `AOC_END` varchar(10) NOT NULL,
  `AOC_PRESENT` varchar(10) NOT NULL,
  `AOC_STATUS_CHANGE` varchar(10) NOT NULL COMMENT 'UNCHANGED NEW CHANGED DELETED',
  `AOC_STATUS_RULE` varchar(10) NOT NULL COMMENT 'UNCHECKED PASSED FAILED',
  PRIMARY KEY (`AOC_OID`),
  KEY `AOC_IDX1` (`AOC_OBO_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=326025 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBO_COMPONENT_ALTERNATIVE`
--

DROP TABLE IF EXISTS `ANA_OBO_COMPONENT_ALTERNATIVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBO_COMPONENT_ALTERNATIVE` (
  `ACA_OID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ACA_OBO_ID` varchar(25) NOT NULL,
  `ACA_OBO_ALT_ID` varchar(25) NOT NULL,
  PRIMARY KEY (`ACA_OID`),
  KEY `ACA_IDX1` (`ACA_OBO_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=369 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBO_COMPONENT_COMMENT`
--

DROP TABLE IF EXISTS `ANA_OBO_COMPONENT_COMMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBO_COMPONENT_COMMENT` (
  `ACC_OID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ACC_OBO_ID` varchar(25) NOT NULL,
  `ACC_OBO_GENERAL_COMMENT` varchar(1000) NOT NULL,
  `ACC_OBO_USER_COMMENT` varchar(1000) NOT NULL,
  `ACC_OBO_ORDER_COMMENT` varchar(1000) NOT NULL,
  PRIMARY KEY (`ACC_OID`),
  KEY `ACC_IDX1` (`ACC_OBO_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBO_COMPONENT_ORDER`
--

DROP TABLE IF EXISTS `ANA_OBO_COMPONENT_ORDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBO_COMPONENT_ORDER` (
  `ACO_OID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ACO_OBO_CHILD` varchar(25) NOT NULL,
  `ACO_OBO_PARENT` varchar(25) NOT NULL,
  `ACO_OBO_TYPE` varchar(25) NOT NULL,
  `ACO_OBO_ALPHA_ORDER` int(10) unsigned DEFAULT NULL,
  `ACO_OBO_SPECIAL_ORDER` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`ACO_OID`),
  KEY `ACO_IDX1` (`ACO_OBO_CHILD`),
  KEY `ACO_IDX2` (`ACO_OBO_PARENT`)
) ENGINE=MyISAM AUTO_INCREMENT=80714 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBO_COMPONENT_RELATIONSHIP`
--

DROP TABLE IF EXISTS `ANA_OBO_COMPONENT_RELATIONSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBO_COMPONENT_RELATIONSHIP` (
  `ACR_OID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ACR_OBO_CHILD` varchar(25) NOT NULL,
  `ACR_OBO_CHILD_START` bigint(20) NOT NULL,
  `ACR_OBO_CHILD_STOP` bigint(20) NOT NULL,
  `ACR_OBO_TYPE` varchar(25) NOT NULL,
  `ACR_OBO_PARENT` varchar(25) NOT NULL,
  PRIMARY KEY (`ACR_OID`),
  KEY `ACR_IDX1` (`ACR_OBO_CHILD`),
  KEY `ACR_IDX2` (`ACR_OBO_PARENT`)
) ENGINE=MyISAM AUTO_INCREMENT=359118 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBO_COMPONENT_SYNONYM`
--

DROP TABLE IF EXISTS `ANA_OBO_COMPONENT_SYNONYM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBO_COMPONENT_SYNONYM` (
  `ACS_OID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ACS_OBO_ID` varchar(25) NOT NULL,
  `ACS_OBO_TEXT` varchar(1000) NOT NULL,
  PRIMARY KEY (`ACS_OID`),
  KEY `ACS_IDX1` (`ACS_OBO_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=22034 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBO_FILE`
--

DROP TABLE IF EXISTS `ANA_OBO_FILE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBO_FILE` (
  `AOF_OID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `AOF_FILE_NAME` varchar(255) NOT NULL,
  `AOF_FILE_CONTENT` mediumblob,
  `AOF_FILE_CONTENT_TYPE` varchar(255) NOT NULL,
  `AOF_FILE_CONTENT_LENGTH` int(10) DEFAULT NULL,
  `AOF_FILE_CONTENT_DATE` datetime DEFAULT NULL,
  `AOF_FILE_VALIDATION` varchar(20) DEFAULT NULL COMMENT 'NEW/VALIDATED/FAILED',
  `AOF_FILE_AUTHOR` varchar(20) DEFAULT NULL,
  `AOF_TEXT_REPORT_NAME` varchar(255) DEFAULT NULL,
  `AOF_TEXT_REPORT` mediumblob,
  `AOF_TEXT_REPORT_TYPE` varchar(255) DEFAULT NULL,
  `AOF_TEXT_REPORT_LENGTH` int(10) DEFAULT NULL,
  `AOF_TEXT_REPORT_DATE` datetime DEFAULT NULL,
  `AOF_PDF_REPORT_NAME` varchar(255) DEFAULT NULL,
  `AOF_PDF_REPORT` mediumblob,
  `AOF_PDF_REPORT_TYPE` varchar(255) DEFAULT NULL,
  `AOF_PDF_REPORT_LENGTH` int(10) DEFAULT NULL,
  `AOF_PDF_REPORT_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`AOF_OID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_OBO_USER`
--

DROP TABLE IF EXISTS `ANA_OBO_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_OBO_USER` (
  `AOU_OID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `AOU_NAME` varchar(255) NOT NULL,
  `AOU_PASSWORD` varchar(255) NOT NULL,
  `AOU_EMAIL` varchar(255) NOT NULL,
  `AOU_ORGANISATION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AOU_OID`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_PERSPECTIVE`
--

DROP TABLE IF EXISTS `ANA_PERSPECTIVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_PERSPECTIVE` (
  `PSP_NAME` varchar(25) NOT NULL COMMENT 'Name of perspective',
  `PSP_COMMENTS` varchar(1024) DEFAULT NULL COMMENT 'Description of perspective',
  PRIMARY KEY (`PSP_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Perspectives are particular views of subsets of the anatomy';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_PERSPECTIVE_AMBIT`
--

DROP TABLE IF EXISTS `ANA_PERSPECTIVE_AMBIT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_PERSPECTIVE_AMBIT` (
  `PAM_OID` int(10) unsigned NOT NULL,
  `PAM_PERSPECTIVE_FK` varchar(25) NOT NULL,
  `PAM_NODE_FK` int(10) unsigned NOT NULL,
  `PAM_IS_START` tinyint(1) NOT NULL,
  `PAM_IS_STOP` tinyint(1) NOT NULL,
  `PAM_COMMENTS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PAM_OID`),
  UNIQUE KEY `PAM_OID` (`PAM_OID`),
  KEY `ANA_PERSPECTIVE_AMBIT_FKIndex1` (`PAM_OID`),
  KEY `ANA_PERSPECTIVE_AMBIT_FKIndex2` (`PAM_PERSPECTIVE_FK`),
  KEY `ANA_PERSPECTIVE_AMBIT_FKIndex3` (`PAM_NODE_FK`),
  KEY `PAM_AK_INDEX` (`PAM_PERSPECTIVE_FK`,`PAM_NODE_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Defines boundary nodes for a perspective';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_PROJECT`
--

DROP TABLE IF EXISTS `ANA_PROJECT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_PROJECT` (
  `APJ_NAME` char(30) NOT NULL,
  PRIMARY KEY (`APJ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_RELATIONSHIP`
--

DROP TABLE IF EXISTS `ANA_RELATIONSHIP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_RELATIONSHIP` (
  `REL_OID` int(10) unsigned NOT NULL,
  `REL_RELATIONSHIP_TYPE_FK` varchar(20) NOT NULL,
  `REL_CHILD_FK` int(10) unsigned NOT NULL,
  `REL_PARENT_FK` int(10) unsigned NOT NULL,
  PRIMARY KEY (`REL_OID`),
  KEY `REL_AK_IINDEX` (`REL_PARENT_FK`,`REL_CHILD_FK`,`REL_RELATIONSHIP_TYPE_FK`),
  KEY `REL_RELATIONSHIP_TYPE_FK` (`REL_RELATIONSHIP_TYPE_FK`),
  KEY `REL_CHILD_FK` (`REL_CHILD_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_RELATIONSHIP_PROJECT`
--

DROP TABLE IF EXISTS `ANA_RELATIONSHIP_PROJECT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_RELATIONSHIP_PROJECT` (
  `RLP_OID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `RLP_RELATIONSHIP_FK` int(10) unsigned NOT NULL,
  `RLP_PROJECT_FK` char(30) NOT NULL,
  `RLP_SEQUENCE` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`RLP_OID`),
  KEY `FK_RLP_RELATIONSHIP` (`RLP_RELATIONSHIP_FK`),
  KEY `FK_RLP_PROJECT` (`RLP_PROJECT_FK`)
) ENGINE=MyISAM AUTO_INCREMENT=14911 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_RELATIONSHIP_TYPE`
--

DROP TABLE IF EXISTS `ANA_RELATIONSHIP_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_RELATIONSHIP_TYPE` (
  `RTY_NAME` varchar(20) NOT NULL,
  `RTY_CHILD_TO_PARENT_DISPLAY` varchar(40) NOT NULL,
  `RTY_PARENT_TO_CHILD_DISPLAY` varchar(40) NOT NULL,
  PRIMARY KEY (`RTY_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_REPLACED_BY`
--

DROP TABLE IF EXISTS `ANA_REPLACED_BY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_REPLACED_BY` (
  `RPL_NEW_PUBLIC_ID` varchar(20) NOT NULL,
  `RPL_OLD_PUBLIC_ID` varchar(20) NOT NULL,
  PRIMARY KEY (`RPL_NEW_PUBLIC_ID`,`RPL_OLD_PUBLIC_ID`),
  KEY `RPL_OLD_PUBLIC_ID` (`RPL_OLD_PUBLIC_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_SOURCE`
--

DROP TABLE IF EXISTS `ANA_SOURCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_SOURCE` (
  `SRC_OID` int(10) unsigned NOT NULL,
  `SRC_NAME` varchar(255) NOT NULL,
  `SRC_AUTHORS` varchar(255) NOT NULL,
  `SRC_FORMAT_FK` varchar(30) NOT NULL,
  `SRC_YEAR` year(4) DEFAULT NULL,
  PRIMARY KEY (`SRC_OID`),
  UNIQUE KEY `ANA_SOURCE_AK_INDEX` (`SRC_NAME`,`SRC_AUTHORS`),
  KEY `SRC_FORMAT_FK` (`SRC_FORMAT_FK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_SOURCE_FORMAT`
--

DROP TABLE IF EXISTS `ANA_SOURCE_FORMAT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_SOURCE_FORMAT` (
  `SFM_NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`SFM_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_STAGE`
--

DROP TABLE IF EXISTS `ANA_STAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_STAGE` (
  `STG_OID` int(10) unsigned NOT NULL,
  `STG_SPECIES_FK` varchar(20) NOT NULL,
  `STG_NAME` varchar(20) NOT NULL,
  `STG_SEQUENCE` int(10) unsigned NOT NULL,
  `STG_DESCRIPTION` varchar(2000) DEFAULT NULL COMMENT 'Description of stage.  Alternatively, could replace this with a URL.',
  `STG_SHORT_EXTRA_TEXT` varchar(25) DEFAULT NULL COMMENT 'Very short additional text describing the stage.  This is useful when real estate is tight but you still have enough space to give the user some additional information besides just the somtimes uninformative stage name.  For mouse, this will likely be an ',
  `STG_PUBLIC_ID` varchar(20) DEFAULT NULL COMMENT 'Public ID of stage.  Null if stage does not have a public ID.',
  PRIMARY KEY (`STG_OID`),
  UNIQUE KEY `STG_NAME_INDEX` (`STG_NAME`,`STG_SPECIES_FK`),
  UNIQUE KEY `STG_SEQUENCE_INDEX` (`STG_SEQUENCE`,`STG_SPECIES_FK`),
  KEY `STG_SPECIES_FK` (`STG_SPECIES_FK`),
  KEY `STG_PUBLIC_ID_INDEX` (`STG_PUBLIC_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_STAGE_MODIFIER`
--

DROP TABLE IF EXISTS `ANA_STAGE_MODIFIER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_STAGE_MODIFIER` (
  `SMO_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`SMO_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_SYNONYM`
--

DROP TABLE IF EXISTS `ANA_SYNONYM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_SYNONYM` (
  `SYN_OID` int(10) unsigned NOT NULL,
  `SYN_OBJECT_FK` int(10) unsigned NOT NULL,
  `SYN_SYNONYM` varchar(100) NOT NULL,
  PRIMARY KEY (`SYN_OID`),
  UNIQUE KEY `SYN_AK_INDEX` (`SYN_OBJECT_FK`,`SYN_SYNONYM`),
  KEY `SYN_SYNONYM_INDEX` (`SYN_SYNONYM`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_TIMED_IDENTIFIER`
--

DROP TABLE IF EXISTS `ANA_TIMED_IDENTIFIER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_TIMED_IDENTIFIER` (
  `ATI_OID` int(10) unsigned NOT NULL,
  `ATI_OLD_PUBLIC_ID` varchar(20) NOT NULL,
  `ATI_OLD_DISPLAY_ID` varchar(20) NOT NULL,
  PRIMARY KEY (`ATI_OID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_TIMED_NODE`
--

DROP TABLE IF EXISTS `ANA_TIMED_NODE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_TIMED_NODE` (
  `ATN_OID` int(10) unsigned NOT NULL,
  `ATN_NODE_FK` int(10) unsigned NOT NULL,
  `ATN_STAGE_FK` int(10) unsigned NOT NULL,
  `ATN_STAGE_MODIFIER_FK` varchar(20) DEFAULT NULL,
  `ATN_PUBLIC_ID` varchar(20) NOT NULL,
  `ATN_DISPLAY_ID` varchar(20) NOT NULL,
  PRIMARY KEY (`ATN_OID`),
  UNIQUE KEY `atn_public_id_index` (`ATN_PUBLIC_ID`),
  UNIQUE KEY `ATN_AK2_INDEX` (`ATN_NODE_FK`,`ATN_STAGE_FK`),
  KEY `ATN_STAGE_FK` (`ATN_STAGE_FK`),
  KEY `ATN_STAGE_MODIFIER_FK` (`ATN_STAGE_MODIFIER_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ANA_VERSION`
--

DROP TABLE IF EXISTS `ANA_VERSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_VERSION` (
  `VER_OID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `VER_NUMBER` int(10) unsigned NOT NULL,
  `VER_DATE` datetime NOT NULL,
  `VER_COMMENTS` varchar(2000) NOT NULL,
  PRIMARY KEY (`VER_OID`),
  UNIQUE KEY `AVERS_NUMBER_INDEX` (`VER_NUMBER`),
  UNIQUE KEY `AVERS_DATE` (`VER_DATE`)
) ENGINE=MyISAM AUTO_INCREMENT=37378 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `REF_SPECIES`
--

DROP TABLE IF EXISTS `REF_SPECIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REF_SPECIES` (
  `RSP_NAME` varchar(20) NOT NULL,
  `RSP_LATIN_NAME` varchar(30) NOT NULL,
  `RSP_TIMED_NODE_ID_PREFIX` varchar(20) NOT NULL COMMENT 'For species with timed component ontologies (mouse), this string is at the front of all timed component IDs for this species (''EMAP:'').  For species that don''t distinguish timed from untimed components (Xenopus), the prefix is the same for both (''XAO:'').',
  `RSP_NODE_ID_PREFIX` varchar(20) NOT NULL COMMENT 'This string is at the front of all abstract (untimed) component IDs in this species'' ontology.  For example, ''EMAPA:'' or ''XAO:''.',
  PRIMARY KEY (`RSP_NAME`),
  UNIQUE KEY `RSP_LATIN_IDX` (`RSP_LATIN_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `anav_grand_relation`
--

DROP TABLE IF EXISTS `anav_grand_relation`;
/*!50001 DROP VIEW IF EXISTS `anav_grand_relation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `anav_grand_relation` (
  `ANAV_ID_1` tinyint NOT NULL,
  `ANAV_NAME_1` tinyint NOT NULL,
  `ANAV_DESC_1` tinyint NOT NULL,
  `ANAV_STG_MIN_1` tinyint NOT NULL,
  `ANAV_STG_MAX_1` tinyint NOT NULL,
  `ANAV_OID_2` tinyint NOT NULL,
  `ANAV_ID_2` tinyint NOT NULL,
  `ANAV_NAME_2` tinyint NOT NULL,
  `ANAV_DESC_2` tinyint NOT NULL,
  `ANAV_STG_MIN_2` tinyint NOT NULL,
  `ANAV_STG_MAX_2` tinyint NOT NULL,
  `ANAV_ID_3` tinyint NOT NULL,
  `ANAV_NAME_3` tinyint NOT NULL,
  `ANAV_DESC_3` tinyint NOT NULL,
  `ANAV_STG_MIN_3` tinyint NOT NULL,
  `ANAV_STG_MAX_3` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `anav_leaf_relation`
--

DROP TABLE IF EXISTS `anav_leaf_relation`;
/*!50001 DROP VIEW IF EXISTS `anav_leaf_relation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `anav_leaf_relation` (
  `ANAV_OID_1` tinyint NOT NULL,
  `ANAV_NAME_1` tinyint NOT NULL,
  `ANAV_DESC_1` tinyint NOT NULL,
  `ANAV_MIN_1` tinyint NOT NULL,
  `ANAV_MAX_1` tinyint NOT NULL,
  `ANAV_OID_2` tinyint NOT NULL,
  `ANAV_NAME_2` tinyint NOT NULL,
  `ANAV_DESC_2` tinyint NOT NULL,
  `ANAV_MIN_2` tinyint NOT NULL,
  `ANAV_MAX_2` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `anav_relation`
--

DROP TABLE IF EXISTS `anav_relation`;
/*!50001 DROP VIEW IF EXISTS `anav_relation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `anav_relation` (
  `ANAV_OID` tinyint NOT NULL,
  `ANAV_ENTRY_EDGE_ID` tinyint NOT NULL,
  `ANAV_DIRECT_EDGE_ID` tinyint NOT NULL,
  `ANAV_EXIT_EDGE_ID` tinyint NOT NULL,
  `ANAV_CHILD_ID` tinyint NOT NULL,
  `ANAV_CHILD_NAME` tinyint NOT NULL,
  `ANAV_CHILD_DESC` tinyint NOT NULL,
  `ANAV_CHILD_STG_MIN` tinyint NOT NULL,
  `ANAV_CHILD_STG_MAX` tinyint NOT NULL,
  `ANAV_PARENT_ID` tinyint NOT NULL,
  `ANAV_PARENT_NAME` tinyint NOT NULL,
  `ANAV_PARENT_DESC` tinyint NOT NULL,
  `ANAV_PARENT_STG_MIN` tinyint NOT NULL,
  `ANAV_PARENT_STG_MAX` tinyint NOT NULL,
  `ANAV_HOPS` tinyint NOT NULL,
  `ANAV_SOURCE` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `anav_relationship_timed`
--

DROP TABLE IF EXISTS `anav_relationship_timed`;
/*!50001 DROP VIEW IF EXISTS `anav_relationship_timed`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `anav_relationship_timed` (
  `ANAV_REL_OID` tinyint NOT NULL,
  `ANAV_REL_RELATIONSHIP_TYPE_FK` tinyint NOT NULL,
  `ANAV_REL_CHILD_FK` tinyint NOT NULL,
  `ANAV_REL_PARENT_FK` tinyint NOT NULL,
  `ANAV_REL_STAGE` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `anav_stage_range`
--

DROP TABLE IF EXISTS `anav_stage_range`;
/*!50001 DROP VIEW IF EXISTS `anav_stage_range`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `anav_stage_range` (
  `ANAV_NODE_FK` tinyint NOT NULL,
  `ANAV_STAGE_MIN` tinyint NOT NULL,
  `ANAV_STAGE_MAX` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `anav_timed_grand_relation`
--

DROP TABLE IF EXISTS `anav_timed_grand_relation`;
/*!50001 DROP VIEW IF EXISTS `anav_timed_grand_relation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `anav_timed_grand_relation` (
  `ANAV_STAGE` tinyint NOT NULL,
  `ANAV_ID_1` tinyint NOT NULL,
  `ANAV_NAME_1` tinyint NOT NULL,
  `ANAV_DESC_1` tinyint NOT NULL,
  `ANAV_OID_2` tinyint NOT NULL,
  `ANAV_ID_2` tinyint NOT NULL,
  `ANAV_NAME_2` tinyint NOT NULL,
  `ANAV_DESC_2` tinyint NOT NULL,
  `ANAV_ID_3` tinyint NOT NULL,
  `ANAV_NAME_3` tinyint NOT NULL,
  `ANAV_DESC_3` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `anav_timed_leaf_relation`
--

DROP TABLE IF EXISTS `anav_timed_leaf_relation`;
/*!50001 DROP VIEW IF EXISTS `anav_timed_leaf_relation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `anav_timed_leaf_relation` (
  `ANAV_STAGE` tinyint NOT NULL,
  `ANAV_OID_1` tinyint NOT NULL,
  `ANAV_NAME_1` tinyint NOT NULL,
  `ANAV_DESC_1` tinyint NOT NULL,
  `ANAV_OID_2` tinyint NOT NULL,
  `ANAV_NAME_2` tinyint NOT NULL,
  `ANAV_DESC_2` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `anav_timed_relation`
--

DROP TABLE IF EXISTS `anav_timed_relation`;
/*!50001 DROP VIEW IF EXISTS `anav_timed_relation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `anav_timed_relation` (
  `ANAV_T_OID` tinyint NOT NULL,
  `ANAV_T_ENTRY_EDGE_ID` tinyint NOT NULL,
  `ANAV_T_DIRECT_EDGE_ID` tinyint NOT NULL,
  `ANAV_T_EXIT_EDGE_ID` tinyint NOT NULL,
  `ANAV_T_STAGE` tinyint NOT NULL,
  `ANAV_T_CHILD_ID` tinyint NOT NULL,
  `ANAV_T_CHILD_NAME` tinyint NOT NULL,
  `ANAV_T_CHILD_DESC` tinyint NOT NULL,
  `ANAV_T_PARENT_ID` tinyint NOT NULL,
  `ANAV_T_PARENT_NAME` tinyint NOT NULL,
  `ANAV_T_PARENT_DESC` tinyint NOT NULL,
  `ANAV_T_HOPS` tinyint NOT NULL,
  `ANAV_T_SOURCE` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'mouse011'
--
/*!50003 DROP PROCEDURE IF EXISTS `ANASP_ADD_EDGE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ANASP_ADD_EDGE`( IN childnodeid VARCHAR(100),
                                 IN childminstage VARCHAR(100),
                                 IN childmaxstage VARCHAR(100),
                                 IN parentnodeid VARCHAR(100),
                                 IN parentminstage VARCHAR(100),
                                 IN parentmaxstage VARCHAR(100),
                                 IN sourceid VARCHAR(150) )
BEGIN
  DECLARE rows, id INT DEFAULT 0;

  IF (childnodeid != parentnodeid) THEN
    SELECT SQL_CALC_FOUND_ROWS AME_OID FROM ANAM_EDGE
              WHERE AME_CHILD_NODE = childnodeid
              AND AME_PARENT_NODE = parentnodeid
              AND AME_HOPS = 0;
    SELECT FOUND_ROWS() INTO rows;
    IF (rows <= 0) THEN
      SELECT SQL_CALC_FOUND_ROWS AME_OID FROM ANAM_EDGE
                WHERE AME_CHILD_NODE = parentnodeid
                AND AME_PARENT_NODE = childnodeid;
      SELECT FOUND_ROWS() INTO rows;
      IF (rows <= 0) THEN
        
        INSERT INTO ANAM_EDGE 
            (AME_CHILD_NODE,
             AME_CHILD_STG_MIN,
             AME_CHILD_STG_MAX,
             AME_PARENT_NODE,
             AME_PARENT_STG_MIN,
             AME_PARENT_STG_MAX,
             AME_HOPS,
             AME_SOURCE)
          VALUES 
            (childnodeid,
             childminstage,
             childmaxstage,
             parentnodeid,
             parentminstage,
             parentmaxstage,
             0,
             sourceid);
        SELECT MAX(AME_OID) INTO id FROM ANAM_EDGE;
        UPDATE ANAM_EDGE
          SET AME_ENTRY_EDGE_ID = id,
              AME_EXIT_EDGE_ID = id,
              AME_DIRECT_EDGE_ID = id
          WHERE AME_OID = id;

        
        INSERT INTO ANAM_EDGE (
          AME_ENTRY_EDGE_ID,
          AME_DIRECT_EDGE_ID,
          AME_EXIT_EDGE_ID,
          AME_CHILD_NODE,
          AME_CHILD_STG_MIN,
          AME_CHILD_STG_MAX,
          AME_PARENT_NODE,
          AME_PARENT_STG_MIN,
          AME_PARENT_STG_MAX,
          AME_HOPS,
          AME_SOURCE)
          SELECT AME_OID,
                 id,
                 id, 
                 AME_CHILD_NODE,
                 AME_CHILD_STG_MIN,
                 AME_CHILD_STG_MAX,
                 parentnodeid,
                 parentminstage,
                 parentmaxstage,
                 AME_HOPS + 1,
                 sourceid
          FROM ANAM_EDGE
          WHERE AME_PARENT_NODE = childnodeid;

        
        INSERT INTO ANAM_EDGE (
          AME_ENTRY_EDGE_ID,
          AME_DIRECT_EDGE_ID,
          AME_EXIT_EDGE_ID,
          AME_CHILD_NODE,
          AME_CHILD_STG_MIN,
          AME_CHILD_STG_MAX,
          AME_PARENT_NODE,
          AME_PARENT_STG_MIN,
          AME_PARENT_STG_MAX,
          AME_HOPS,
          AME_SOURCE)
          SELECT id,
                 id,
                 AME_OID, 
                 childnodeid,
                 childminstage,
                 childmaxstage,
                 AME_PARENT_NODE,
                 AME_PARENT_STG_MIN,
                 AME_PARENT_STG_MAX,
                 AME_HOPS + 1,
                 sourceid
          FROM ANAM_EDGE
          WHERE AME_CHILD_NODE = parentnodeid;

        
        INSERT INTO ANAM_EDGE (
          AME_ENTRY_EDGE_ID,
          AME_DIRECT_EDGE_ID,
          AME_EXIT_EDGE_ID,
          AME_CHILD_NODE,
          AME_CHILD_STG_MIN,
          AME_CHILD_STG_MAX,
          AME_PARENT_NODE,
          AME_PARENT_STG_MIN,
          AME_PARENT_STG_MAX,
          AME_HOPS,
          AME_SOURCE)
          SELECT a.AME_OID,
                 id,
                 b.AME_OID, 
                 a.AME_CHILD_NODE,
                 a.AME_CHILD_STG_MIN,
                 a.AME_CHILD_STG_MAX,
                 b.AME_PARENT_NODE,
                 b.AME_PARENT_STG_MIN,
                 b.AME_PARENT_STG_MAX,
                 a.AME_HOPS + b.AME_HOPS + 1,
                 sourceid
          FROM ANAM_EDGE a
          CROSS JOIN ANAM_EDGE b
          WHERE a.AME_PARENT_NODE = childnodeid
          AND b.AME_CHILD_NODE = parentnodeid;

      END IF;
    END IF;
  END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ANASP_ADD_TIMED_EDGE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ANASP_ADD_TIMED_EDGE`( IN childnodeid VARCHAR(100),
                                 IN parentnodeid VARCHAR(100),
                                 IN stage VARCHAR(100),
                                 IN sourceid VARCHAR(150) )
BEGIN
  DECLARE rows, id INT DEFAULT 0;

  IF (childnodeid != parentnodeid) THEN
    SELECT SQL_CALC_FOUND_ROWS AME_T_OID FROM ANAM_TIMED_EDGE
              WHERE AME_T_CHILD_NODE = childnodeid
              AND AME_T_PARENT_NODE = parentnodeid
              AND AME_T_HOPS = 0;
    SELECT FOUND_ROWS() INTO rows;
    IF (rows <= 0) THEN
      SELECT SQL_CALC_FOUND_ROWS AME_T_OID FROM ANAM_TIMED_EDGE
                WHERE AME_T_CHILD_NODE = parentnodeid
                AND AME_T_PARENT_NODE = childnodeid;
      SELECT FOUND_ROWS() INTO rows;
      IF (rows <= 0) THEN
        
        INSERT INTO ANAM_TIMED_EDGE 
            (AME_T_CHILD_NODE,
             AME_T_PARENT_NODE,
             AME_T_STAGE,
             AME_T_HOPS,
             AME_T_SOURCE)
          VALUES 
            (childnodeid,
             parentnodeid,
             stage,
             0,
             sourceid);
        SELECT MAX(AME_T_OID) INTO id FROM ANAM_TIMED_EDGE;
        UPDATE ANAM_TIMED_EDGE
          SET AME_T_ENTRY_EDGE_ID = id,
              AME_T_EXIT_EDGE_ID = id,
              AME_T_DIRECT_EDGE_ID = id
          WHERE AME_T_OID = id;

        
        INSERT INTO ANAM_TIMED_EDGE (
          AME_T_ENTRY_EDGE_ID,
          AME_T_DIRECT_EDGE_ID,
          AME_T_EXIT_EDGE_ID,
          AME_T_CHILD_NODE,
          AME_T_PARENT_NODE,
          AME_T_STAGE,
          AME_T_HOPS,
          AME_T_SOURCE)
          SELECT AME_T_OID,
                 id,
                 id, 
                 AME_T_CHILD_NODE,
                 parentnodeid,
                 stage,
                 AME_T_HOPS + 1,
                 sourceid
          FROM ANAM_TIMED_EDGE
          WHERE AME_T_PARENT_NODE = childnodeid;

        
        INSERT INTO ANAM_TIMED_EDGE (
          AME_T_ENTRY_EDGE_ID,
          AME_T_DIRECT_EDGE_ID,
          AME_T_EXIT_EDGE_ID,
          AME_T_CHILD_NODE,
          AME_T_PARENT_NODE,
          AME_T_STAGE,
          AME_T_HOPS,
          AME_T_SOURCE)
          SELECT id,
                 id,
                 AME_T_OID, 
                 childnodeid,
                 AME_T_PARENT_NODE,
                 AME_T_STAGE,
                 AME_T_HOPS + 1,
                 sourceid
          FROM ANAM_TIMED_EDGE
          WHERE AME_T_CHILD_NODE = parentnodeid;

        
        INSERT INTO ANAM_TIMED_EDGE (
          AME_T_ENTRY_EDGE_ID,
          AME_T_DIRECT_EDGE_ID,
          AME_T_EXIT_EDGE_ID,
          AME_T_CHILD_NODE,
          AME_T_PARENT_NODE,
          AME_T_STAGE,
          AME_T_HOPS,
          AME_T_SOURCE)
          SELECT a.AME_T_OID,
                 id,
                 b.AME_T_OID, 
                 a.AME_T_CHILD_NODE,
                 b.AME_T_PARENT_NODE,
                 b.AME_T_STAGE,
                 a.AME_T_HOPS + b.AME_T_HOPS + 1,
                 sourceid
          FROM ANAM_TIMED_EDGE a
          CROSS JOIN ANAM_TIMED_EDGE b
          WHERE a.AME_T_PARENT_NODE = childnodeid
          AND b.AME_T_CHILD_NODE = parentnodeid;

      END IF;
    END IF;
  END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ANASP_REMOVE_EDGE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ANASP_REMOVE_EDGE`( IN edgeid INT )
BEGIN
  DECLARE rows INT DEFAULT 0;

  SELECT SQL_CALC_FOUND_ROWS AME_OID FROM ANAM_EDGE
             WHERE AME_OID = edgeid
             AND AME_HOPS = 0;
  SELECT FOUND_ROWS() INTO rows;

  IF (rows > 0) THEN
    DROP TABLE IF EXISTS purge_edge;
    CREATE TABLE purge_edge ( del_id INT );

    INSERT INTO purge_edge 
      SELECT AME_OID
        FROM ANAM_EDGE
          WHERE AME_DIRECT_EDGE_ID = edgeid;

    WHILE rows <> 0 DO
      INSERT INTO purge_edge 
        SELECT AME_OID
          FROM ANAM_EDGE
            WHERE AME_HOPS > 0
            AND (AME_ENTRY_EDGE_ID IN (SELECT del_id FROM purge_edge)
                 OR AME_EXIT_EDGE_ID IN (SELECT del_id FROM purge_edge))
            AND AME_OID NOT IN (SELECT del_id FROM purge_edge);
      SELECT ROW_COUNT() INTO rows;
    END WHILE;

    DELETE FROM ANAM_EDGE
      WHERE AME_OID IN (SELECT del_id FROM purge_edge);

  END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ANASP_REMOVE_TIMED_EDGE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ANASP_REMOVE_TIMED_EDGE`( IN edgeid INT )
BEGIN
  DECLARE rows INT DEFAULT 0;

  SELECT SQL_CALC_FOUND_ROWS AME_OID FROM ANAM_TIMED_EDGE
             WHERE AME_T_OID = edgeid
             AND AME_T_HOPS = 0;
  SELECT FOUND_ROWS() INTO rows;

  IF (rows > 0) THEN
    DROP TABLE IF EXISTS purge_edge;
    CREATE TABLE purge_edge ( del_id INT );

    INSERT INTO purge_edge 
      SELECT AME_T_OID
        FROM ANAM_TIMED_EDGE
          WHERE AME_T_DIRECT_EDGE_ID = edgeid;

    WHILE rows <> 0 DO
      INSERT INTO purge_edge 
        SELECT AME_T_OID
          FROM ANAM_TIMED_EDGE
            WHERE AME_T_HOPS > 0
            AND (AME_T_ENTRY_EDGE_ID IN (SELECT del_id FROM purge_edge)
                 OR AME_T_EXIT_EDGE_ID IN (SELECT del_id FROM purge_edge))
            AND AME_T_OID NOT IN (SELECT del_id FROM purge_edge);
      SELECT ROW_COUNT() INTO rows;
    END WHILE;

    DELETE FROM ANAM_TIMED_EDGE
      WHERE AME_T_OID IN (SELECT del_id FROM purge_edge);

  END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `anav_grand_relation`
--

/*!50001 DROP TABLE IF EXISTS `anav_grand_relation`*/;
/*!50001 DROP VIEW IF EXISTS `anav_grand_relation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `anav_grand_relation` AS select `a`.`ANAV_PARENT_ID` AS `ANAV_ID_1`,`a`.`ANAV_PARENT_NAME` AS `ANAV_NAME_1`,`a`.`ANAV_PARENT_DESC` AS `ANAV_DESC_1`,`a`.`ANAV_PARENT_STG_MIN` AS `ANAV_STG_MIN_1`,`a`.`ANAV_PARENT_STG_MAX` AS `ANAV_STG_MAX_1`,`b`.`ANAV_OID` AS `ANAV_OID_2`,`b`.`ANAV_PARENT_ID` AS `ANAV_ID_2`,`b`.`ANAV_PARENT_NAME` AS `ANAV_NAME_2`,`b`.`ANAV_PARENT_DESC` AS `ANAV_DESC_2`,`b`.`ANAV_PARENT_STG_MIN` AS `ANAV_STG_MIN_2`,`b`.`ANAV_PARENT_STG_MAX` AS `ANAV_STG_MAX_2`,`b`.`ANAV_CHILD_ID` AS `ANAV_ID_3`,`b`.`ANAV_CHILD_NAME` AS `ANAV_NAME_3`,`b`.`ANAV_CHILD_DESC` AS `ANAV_DESC_3`,`b`.`ANAV_CHILD_STG_MIN` AS `ANAV_STG_MIN_3`,`b`.`ANAV_CHILD_STG_MAX` AS `ANAV_STG_MAX_3` from (`anav_relation` `a` join `anav_relation` `b` on((`a`.`ANAV_CHILD_ID` = `b`.`ANAV_PARENT_ID`))) where ((`a`.`ANAV_HOPS` = 0) and (`b`.`ANAV_HOPS` = 0)) order by `a`.`ANAV_PARENT_DESC`,`b`.`ANAV_PARENT_DESC` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `anav_leaf_relation`
--

/*!50001 DROP TABLE IF EXISTS `anav_leaf_relation`*/;
/*!50001 DROP VIEW IF EXISTS `anav_leaf_relation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `anav_leaf_relation` AS select `b`.`ANO_OID` AS `ANAV_OID_1`,`b`.`ANO_PUBLIC_ID` AS `ANAV_NAME_1`,`b`.`ANO_COMPONENT_NAME` AS `ANAV_DESC_1`,`c`.`ANAV_STAGE_MIN` AS `ANAV_MIN_1`,`c`.`ANAV_STAGE_MAX` AS `ANAV_MAX_1`,`a`.`ANO_OID` AS `ANAV_OID_2`,`a`.`ANO_PUBLIC_ID` AS `ANAV_NAME_2`,`a`.`ANO_COMPONENT_NAME` AS `ANAV_DESC_2`,`d`.`ANAV_STAGE_MIN` AS `ANAV_MIN_2`,`d`.`ANAV_STAGE_MAX` AS `ANAV_MAX_2` from ((((`ana_relationship` join `ana_node` `a` on((`ana_relationship`.`REL_CHILD_FK` = `a`.`ANO_OID`))) join `ana_node` `b` on((`ana_relationship`.`REL_PARENT_FK` = `b`.`ANO_OID`))) join `anav_stage_range` `d` on((`a`.`ANO_OID` = `d`.`ANAV_NODE_FK`))) join `anav_stage_range` `c` on((`b`.`ANO_OID` = `c`.`ANAV_NODE_FK`))) where (not(`ana_relationship`.`REL_CHILD_FK` in (select `ana_relationship`.`REL_PARENT_FK` from `ana_relationship`))) order by `b`.`ANO_PUBLIC_ID`,`b`.`ANO_COMPONENT_NAME` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `anav_relation`
--

/*!50001 DROP TABLE IF EXISTS `anav_relation`*/;
/*!50001 DROP VIEW IF EXISTS `anav_relation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `anav_relation` AS select `e`.`AME_OID` AS `ANAV_OID`,`e`.`AME_ENTRY_EDGE_ID` AS `ANAV_ENTRY_EDGE_ID`,`e`.`AME_DIRECT_EDGE_ID` AS `ANAV_DIRECT_EDGE_ID`,`e`.`AME_EXIT_EDGE_ID` AS `ANAV_EXIT_EDGE_ID`,`e`.`AME_CHILD_NODE` AS `ANAV_CHILD_ID`,`a`.`ANO_PUBLIC_ID` AS `ANAV_CHILD_NAME`,`a`.`ANO_COMPONENT_NAME` AS `ANAV_CHILD_DESC`,`e`.`AME_CHILD_STG_MIN` AS `ANAV_CHILD_STG_MIN`,`e`.`AME_CHILD_STG_MAX` AS `ANAV_CHILD_STG_MAX`,`e`.`AME_PARENT_NODE` AS `ANAV_PARENT_ID`,`b`.`ANO_PUBLIC_ID` AS `ANAV_PARENT_NAME`,`b`.`ANO_COMPONENT_NAME` AS `ANAV_PARENT_DESC`,`e`.`AME_PARENT_STG_MIN` AS `ANAV_PARENT_STG_MIN`,`e`.`AME_PARENT_STG_MAX` AS `ANAV_PARENT_STG_MAX`,`e`.`AME_HOPS` AS `ANAV_HOPS`,`e`.`AME_SOURCE` AS `ANAV_SOURCE` from ((`anam_edge` `e` join `ana_node` `a` on((`e`.`AME_CHILD_NODE` = `a`.`ANO_OID`))) join `ana_node` `b` on((`e`.`AME_PARENT_NODE` = `b`.`ANO_OID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `anav_relationship_timed`
--

/*!50001 DROP TABLE IF EXISTS `anav_relationship_timed`*/;
/*!50001 DROP VIEW IF EXISTS `anav_relationship_timed`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `anav_relationship_timed` AS select `ana_relationship`.`REL_OID` AS `ANAV_REL_OID`,`ana_relationship`.`REL_RELATIONSHIP_TYPE_FK` AS `ANAV_REL_RELATIONSHIP_TYPE_FK`,`a`.`ATN_OID` AS `ANAV_REL_CHILD_FK`,`b`.`ATN_OID` AS `ANAV_REL_PARENT_FK`,`a`.`ATN_STAGE_FK` AS `ANAV_REL_STAGE` from ((`ana_relationship` join `ana_timed_node` `a` on((`a`.`ATN_NODE_FK` = `ana_relationship`.`REL_CHILD_FK`))) join `ana_timed_node` `b` on((`b`.`ATN_NODE_FK` = `ana_relationship`.`REL_PARENT_FK`))) where (`a`.`ATN_STAGE_FK` = `b`.`ATN_STAGE_FK`) order by `a`.`ATN_STAGE_FK` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `anav_stage_range`
--

/*!50001 DROP TABLE IF EXISTS `anav_stage_range`*/;
/*!50001 DROP VIEW IF EXISTS `anav_stage_range`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `anav_stage_range` AS select `ana_timed_node`.`ATN_NODE_FK` AS `ANAV_NODE_FK`,min(`ana_stage`.`STG_SEQUENCE`) AS `ANAV_STAGE_MIN`,max(`ana_stage`.`STG_SEQUENCE`) AS `ANAV_STAGE_MAX` from (`ana_timed_node` join `ana_stage` on((`ana_timed_node`.`ATN_STAGE_FK` = `ana_stage`.`STG_OID`))) group by `ana_timed_node`.`ATN_NODE_FK` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `anav_timed_grand_relation`
--

/*!50001 DROP TABLE IF EXISTS `anav_timed_grand_relation`*/;
/*!50001 DROP VIEW IF EXISTS `anav_timed_grand_relation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `anav_timed_grand_relation` AS select `a`.`ANAV_T_STAGE` AS `ANAV_STAGE`,`a`.`ANAV_T_PARENT_ID` AS `ANAV_ID_1`,`a`.`ANAV_T_PARENT_NAME` AS `ANAV_NAME_1`,`a`.`ANAV_T_PARENT_DESC` AS `ANAV_DESC_1`,`b`.`ANAV_T_OID` AS `ANAV_OID_2`,`b`.`ANAV_T_PARENT_ID` AS `ANAV_ID_2`,`b`.`ANAV_T_PARENT_NAME` AS `ANAV_NAME_2`,`b`.`ANAV_T_PARENT_DESC` AS `ANAV_DESC_2`,`b`.`ANAV_T_CHILD_ID` AS `ANAV_ID_3`,`b`.`ANAV_T_CHILD_NAME` AS `ANAV_NAME_3`,`b`.`ANAV_T_CHILD_DESC` AS `ANAV_DESC_3` from (`anav_timed_relation` `a` join `anav_timed_relation` `b` on((`a`.`ANAV_T_CHILD_ID` = `b`.`ANAV_T_PARENT_ID`))) where ((`a`.`ANAV_T_HOPS` = 0) and (`b`.`ANAV_T_HOPS` = 0)) order by `a`.`ANAV_T_STAGE`,`a`.`ANAV_T_PARENT_NAME`,`b`.`ANAV_T_PARENT_DESC` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `anav_timed_leaf_relation`
--

/*!50001 DROP TABLE IF EXISTS `anav_timed_leaf_relation`*/;
/*!50001 DROP VIEW IF EXISTS `anav_timed_leaf_relation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `anav_timed_leaf_relation` AS select `c`.`STG_NAME` AS `ANAV_STAGE`,`b`.`ATN_OID` AS `ANAV_OID_1`,`b`.`ATN_PUBLIC_ID` AS `ANAV_NAME_1`,`e`.`ANO_COMPONENT_NAME` AS `ANAV_DESC_1`,`a`.`ATN_OID` AS `ANAV_OID_2`,`a`.`ATN_PUBLIC_ID` AS `ANAV_NAME_2`,`d`.`ANO_COMPONENT_NAME` AS `ANAV_DESC_2` from (((((`anav_relationship_timed` join `ana_timed_node` `a` on((`anav_relationship_timed`.`ANAV_REL_CHILD_FK` = `a`.`ATN_OID`))) join `ana_node` `d` on((`a`.`ATN_NODE_FK` = `d`.`ANO_OID`))) join `ana_timed_node` `b` on((`anav_relationship_timed`.`ANAV_REL_PARENT_FK` = `b`.`ATN_OID`))) join `ana_node` `e` on((`b`.`ATN_NODE_FK` = `e`.`ANO_OID`))) join `ana_stage` `c` on((`c`.`STG_OID` = `anav_relationship_timed`.`ANAV_REL_STAGE`))) where (not(`anav_relationship_timed`.`ANAV_REL_CHILD_FK` in (select `anav_relationship_timed`.`ANAV_REL_PARENT_FK` from `anav_relationship_timed`))) order by `c`.`STG_NAME`,`b`.`ATN_PUBLIC_ID`,`d`.`ANO_COMPONENT_NAME` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `anav_timed_relation`
--

/*!50001 DROP TABLE IF EXISTS `anav_timed_relation`*/;
/*!50001 DROP VIEW IF EXISTS `anav_timed_relation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `anav_timed_relation` AS select `e`.`AME_T_OID` AS `ANAV_T_OID`,`e`.`AME_T_ENTRY_EDGE_ID` AS `ANAV_T_ENTRY_EDGE_ID`,`e`.`AME_T_DIRECT_EDGE_ID` AS `ANAV_T_DIRECT_EDGE_ID`,`e`.`AME_T_EXIT_EDGE_ID` AS `ANAV_T_EXIT_EDGE_ID`,`e`.`AME_T_STAGE` AS `ANAV_T_STAGE`,`e`.`AME_T_CHILD_NODE` AS `ANAV_T_CHILD_ID`,`a`.`ATN_PUBLIC_ID` AS `ANAV_T_CHILD_NAME`,`c`.`ANO_COMPONENT_NAME` AS `ANAV_T_CHILD_DESC`,`e`.`AME_T_PARENT_NODE` AS `ANAV_T_PARENT_ID`,`b`.`ATN_PUBLIC_ID` AS `ANAV_T_PARENT_NAME`,`d`.`ANO_COMPONENT_NAME` AS `ANAV_T_PARENT_DESC`,`e`.`AME_T_HOPS` AS `ANAV_T_HOPS`,`e`.`AME_T_SOURCE` AS `ANAV_T_SOURCE` from ((((`anam_timed_edge` `e` join `ana_timed_node` `a` on((`e`.`AME_T_CHILD_NODE` = `a`.`ATN_OID`))) join `ana_timed_node` `b` on((`e`.`AME_T_PARENT_NODE` = `b`.`ATN_OID`))) join `ana_node` `c` on((`c`.`ANO_OID` = `a`.`ATN_NODE_FK`))) join `ana_node` `d` on((`d`.`ANO_OID` = `b`.`ATN_NODE_FK`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-14 10:13:20
