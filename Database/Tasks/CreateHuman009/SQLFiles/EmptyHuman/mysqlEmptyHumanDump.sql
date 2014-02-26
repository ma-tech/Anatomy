-- MySQL dump 10.13  Distrib 5.1.45, for pc-linux-gnu (i686)
--
-- Host: localhost    Database: human_old
-- ------------------------------------------------------
-- Server version	5.1.45-log

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
  `APO_FULL_PATH` varchar(255) NOT NULL,
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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANAD_PART_OF`
--

LOCK TABLES `ANAD_PART_OF` WRITE;
/*!40000 ALTER TABLE `ANAD_PART_OF` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANAD_PART_OF` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ANAD_PART_OF_PERSPECTIVE`
--

LOCK TABLES `ANAD_PART_OF_PERSPECTIVE` WRITE;
/*!40000 ALTER TABLE `ANAD_PART_OF_PERSPECTIVE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANAD_PART_OF_PERSPECTIVE` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM AUTO_INCREMENT=18009 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANAD_RELATIONSHIP_TRANSITIVE`
--

LOCK TABLES `ANAD_RELATIONSHIP_TRANSITIVE` WRITE;
/*!40000 ALTER TABLE `ANAD_RELATIONSHIP_TRANSITIVE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANAD_RELATIONSHIP_TRANSITIVE` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_ATTRIBUTION`
--

LOCK TABLES `ANA_ATTRIBUTION` WRITE;
/*!40000 ALTER TABLE `ANA_ATTRIBUTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_ATTRIBUTION` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_DELETED_PUBLIC_ID`
--

LOCK TABLES `ANA_DELETED_PUBLIC_ID` WRITE;
/*!40000 ALTER TABLE `ANA_DELETED_PUBLIC_ID` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_DELETED_PUBLIC_ID` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_DELETE_REASON`
--

LOCK TABLES `ANA_DELETE_REASON` WRITE;
/*!40000 ALTER TABLE `ANA_DELETE_REASON` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_DELETE_REASON` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_EDITOR`
--

LOCK TABLES `ANA_EDITOR` WRITE;
/*!40000 ALTER TABLE `ANA_EDITOR` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_EDITOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ANA_EVIDENCE`
--

DROP TABLE IF EXISTS `ANA_EVIDENCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_EVIDENCE` (
  `EVI_NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`EVI_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_EVIDENCE`
--

LOCK TABLES `ANA_EVIDENCE` WRITE;
/*!40000 ALTER TABLE `ANA_EVIDENCE` DISABLE KEYS */;
INSERT INTO `ANA_EVIDENCE` VALUES ('text book without experimental reference');
/*!40000 ALTER TABLE `ANA_EVIDENCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ANA_LOG`
--

DROP TABLE IF EXISTS `ANA_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_LOG` (
  `LOG_OID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Unique, persistent ID for this log entry.  This is not unique across the database.',
  `LOG_VERSION_FK` int(10) unsigned NOT NULL COMMENT 'What version this update became visible in.',
  `LOG_COLUMN_NAME` varchar(64) NOT NULL COMMENT 'Name of database column that was updated.  Maximum 64 characters in a MySQL column name.',
  `LOG_OLD_VALUE` varchar(255) DEFAULT NULL COMMENT 'Old value of column expressed as a character string.',
  `LOG_COMMENTS` varchar(255) DEFAULT NULL COMMENT 'Comments on this update.',
  PRIMARY KEY (`LOG_OID`),
  UNIQUE KEY `ALOG_LOGGED_OID_INDEX` (`LOG_OID`,`LOG_VERSION_FK`,`LOG_COLUMN_NAME`),
  KEY `ANA_LOG_FKIndex2` (`LOG_VERSION_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Records updates to the database.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_LOG`
--

LOCK TABLES `ANA_LOG` WRITE;
/*!40000 ALTER TABLE `ANA_LOG` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_LOG` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`ANO_OID`),
  UNIQUE KEY `anode_public_id_index` (`ANO_PUBLIC_ID`),
  KEY `anode_component_name_index` (`ANO_COMPONENT_NAME`),
  KEY `ANO_SPECIES_FK` (`ANO_SPECIES_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_NODE`
--

LOCK TABLES `ANA_NODE` WRITE;
/*!40000 ALTER TABLE `ANA_NODE` DISABLE KEYS */;
INSERT INTO `ANA_NODE` VALUES (29,'human','human',1,0,'EHDAA:1',NULL);
/*!40000 ALTER TABLE `ANA_NODE` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Defines what nodes participate in what perspectives.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_NODE_IN_PERSPECTIVE`
--

LOCK TABLES `ANA_NODE_IN_PERSPECTIVE` WRITE;
/*!40000 ALTER TABLE `ANA_NODE_IN_PERSPECTIVE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_NODE_IN_PERSPECTIVE` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`OBJ_OID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_OBJECT`
--

LOCK TABLES `ANA_OBJECT` WRITE;
/*!40000 ALTER TABLE `ANA_OBJECT` DISABLE KEYS */;
INSERT INTO `ANA_OBJECT` VALUES (1,NULL,NULL),(2,NULL,NULL),(3,NULL,NULL),(4,NULL,NULL),(5,NULL,NULL),(6,NULL,NULL),(7,NULL,NULL),(8,NULL,NULL),(9,NULL,NULL),(10,NULL,NULL),(11,NULL,NULL),(12,NULL,NULL),(13,NULL,NULL),(14,NULL,NULL),(15,NULL,NULL),(16,NULL,NULL),(17,NULL,NULL),(18,NULL,NULL),(19,NULL,NULL),(20,NULL,NULL),(21,NULL,NULL),(22,NULL,NULL),(23,NULL,NULL),(24,NULL,NULL),(25,NULL,NULL),(26,NULL,NULL),(27,NULL,NULL),(28,'2007-08-22 14:37:44',NULL),(29,'2003-06-16 10:18:59',NULL);
/*!40000 ALTER TABLE `ANA_OBJECT` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ANA_PERSPECTIVE`
--

LOCK TABLES `ANA_PERSPECTIVE` WRITE;
/*!40000 ALTER TABLE `ANA_PERSPECTIVE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_PERSPECTIVE` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ANA_PERSPECTIVE_AMBIT`
--

LOCK TABLES `ANA_PERSPECTIVE_AMBIT` WRITE;
/*!40000 ALTER TABLE `ANA_PERSPECTIVE_AMBIT` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_PERSPECTIVE_AMBIT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ANA_PROJECT`
--

DROP TABLE IF EXISTS `ANA_PROJECT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_PROJECT` (
  `APJ_NAME` char(30) NOT NULL,
  PRIMARY KEY (`APJ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_PROJECT`
--

LOCK TABLES `ANA_PROJECT` WRITE;
/*!40000 ALTER TABLE `ANA_PROJECT` DISABLE KEYS */;
INSERT INTO `ANA_PROJECT` VALUES ('EMAGE'),('EMAP'),('EUREGENE'),('EUREXPRESS'),('GUDMAP');
/*!40000 ALTER TABLE `ANA_PROJECT` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_RELATIONSHIP`
--

LOCK TABLES `ANA_RELATIONSHIP` WRITE;
/*!40000 ALTER TABLE `ANA_RELATIONSHIP` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_RELATIONSHIP` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `ANA_RELATIONSHIP_PROJECT_ibfk_1` (`RLP_RELATIONSHIP_FK`),
  KEY `ANA_RELATIONSHIP_PROJECT_ibfk_2` (`RLP_PROJECT_FK`)
) ENGINE=MyISAM AUTO_INCREMENT=4655 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_RELATIONSHIP_PROJECT`
--

LOCK TABLES `ANA_RELATIONSHIP_PROJECT` WRITE;
/*!40000 ALTER TABLE `ANA_RELATIONSHIP_PROJECT` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_RELATIONSHIP_PROJECT` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_RELATIONSHIP_TYPE`
--

LOCK TABLES `ANA_RELATIONSHIP_TYPE` WRITE;
/*!40000 ALTER TABLE `ANA_RELATIONSHIP_TYPE` DISABLE KEYS */;
INSERT INTO `ANA_RELATIONSHIP_TYPE` VALUES ('derives-from','derives from','develops into'),('part-of','is part of','has part');
/*!40000 ALTER TABLE `ANA_RELATIONSHIP_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_REPLACED_BY`
--

LOCK TABLES `ANA_REPLACED_BY` WRITE;
/*!40000 ALTER TABLE `ANA_REPLACED_BY` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_REPLACED_BY` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_SOURCE`
--

LOCK TABLES `ANA_SOURCE` WRITE;
/*!40000 ALTER TABLE `ANA_SOURCE` DISABLE KEYS */;
INSERT INTO `ANA_SOURCE` VALUES (1,'Gray\'s Anatomy, 38th Edition','ed. by Williams, PL, Bannister, LH, Berry, MH, Collins, P, Dyson, M, Dussek, JE, Ferguson, MWJ','book',1995);
/*!40000 ALTER TABLE `ANA_SOURCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ANA_SOURCE_FORMAT`
--

DROP TABLE IF EXISTS `ANA_SOURCE_FORMAT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_SOURCE_FORMAT` (
  `SFM_NAME` varchar(30) NOT NULL,
  PRIMARY KEY (`SFM_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_SOURCE_FORMAT`
--

LOCK TABLES `ANA_SOURCE_FORMAT` WRITE;
/*!40000 ALTER TABLE `ANA_SOURCE_FORMAT` DISABLE KEYS */;
INSERT INTO `ANA_SOURCE_FORMAT` VALUES ('book'),('personal communication');
/*!40000 ALTER TABLE `ANA_SOURCE_FORMAT` ENABLE KEYS */;
UNLOCK TABLES;

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
  `STG_SHORT_EXTRA_TEXT` varchar(25) DEFAULT NULL COMMENT 'Very short additional text describing the stage.  This is useful when real estate is tight but you still have enough space to give the user some additional information besides just the somtimes uninformative stage name.  For human, this will likely be an ',
  `STG_PUBLIC_ID` varchar(20) DEFAULT NULL COMMENT 'Public ID of stage.  Null if stage does not have a public ID.',
  PRIMARY KEY (`STG_OID`),
  UNIQUE KEY `STG_NAME_INDEX` (`STG_NAME`,`STG_SPECIES_FK`),
  UNIQUE KEY `STG_SEQUENCE_INDEX` (`STG_SEQUENCE`,`STG_SPECIES_FK`),
  KEY `STG_SPECIES_FK` (`STG_SPECIES_FK`),
  KEY `STG_PUBLIC_ID_INDEX` (`STG_PUBLIC_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_STAGE`
--

LOCK TABLES `ANA_STAGE` WRITE;
/*!40000 ALTER TABLE `ANA_STAGE` DISABLE KEYS */;
INSERT INTO `ANA_STAGE` VALUES (2,'human','CS01',0,NULL,NULL,NULL),(3,'human','CS02',1,NULL,NULL,NULL),(4,'human','CS03',2,NULL,NULL,NULL),(5,'human','CS04',3,NULL,NULL,NULL),(6,'human','CS05a',4,NULL,NULL,NULL),(7,'human','CS05b',5,NULL,NULL,NULL),(8,'human','CS05c',6,NULL,NULL,NULL),(9,'human','CS06a',7,NULL,NULL,NULL),(10,'human','CS06b',8,NULL,NULL,NULL),(11,'human','CS07',9,NULL,NULL,NULL),(12,'human','CS08',10,NULL,NULL,NULL),(13,'human','CS09',11,NULL,NULL,NULL),(14,'human','CS10',12,NULL,NULL,NULL),(15,'human','CS11',13,NULL,NULL,NULL),(16,'human','CS12',14,NULL,NULL,NULL),(17,'human','CS13',15,NULL,NULL,NULL),(18,'human','CS14',16,NULL,NULL,NULL),(19,'human','CS15',17,NULL,NULL,NULL),(20,'human','CS16',18,NULL,NULL,NULL),(21,'human','CS17',19,NULL,NULL,NULL),(22,'human','CS18',20,NULL,NULL,NULL),(23,'human','CS19',21,NULL,NULL,NULL),(24,'human','CS20',22,NULL,NULL,NULL),(25,'human','CS21',23,NULL,NULL,NULL),(26,'human','CS22',24,NULL,NULL,NULL),(27,'human','CS23',25,NULL,NULL,NULL);
/*!40000 ALTER TABLE `ANA_STAGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ANA_STAGE_MODIFIER`
--

DROP TABLE IF EXISTS `ANA_STAGE_MODIFIER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANA_STAGE_MODIFIER` (
  `SMO_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`SMO_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_STAGE_MODIFIER`
--

LOCK TABLES `ANA_STAGE_MODIFIER` WRITE;
/*!40000 ALTER TABLE `ANA_STAGE_MODIFIER` DISABLE KEYS */;
INSERT INTO `ANA_STAGE_MODIFIER` VALUES ('Early'),('Late'),('LostLate');
/*!40000 ALTER TABLE `ANA_STAGE_MODIFIER` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_SYNONYM`
--

LOCK TABLES `ANA_SYNONYM` WRITE;
/*!40000 ALTER TABLE `ANA_SYNONYM` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_SYNONYM` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`ATN_OID`),
  UNIQUE KEY `atn_public_id_index` (`ATN_PUBLIC_ID`),
  UNIQUE KEY `ATN_AK2_INDEX` (`ATN_NODE_FK`,`ATN_STAGE_FK`),
  KEY `ATN_STAGE_FK` (`ATN_STAGE_FK`),
  KEY `ATN_STAGE_MODIFIER_FK` (`ATN_STAGE_MODIFIER_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_TIMED_NODE`
--

LOCK TABLES `ANA_TIMED_NODE` WRITE;
/*!40000 ALTER TABLE `ANA_TIMED_NODE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANA_TIMED_NODE` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANA_VERSION`
--

LOCK TABLES `ANA_VERSION` WRITE;
/*!40000 ALTER TABLE `ANA_VERSION` DISABLE KEYS */;
INSERT INTO `ANA_VERSION` VALUES (28,1,'2007-08-22 14:37:44','Version 1 of human anatomy database.');
/*!40000 ALTER TABLE `ANA_VERSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REF_SPECIES`
--

DROP TABLE IF EXISTS `REF_SPECIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REF_SPECIES` (
  `RSP_NAME` varchar(20) NOT NULL,
  `RSP_LATIN_NAME` varchar(30) NOT NULL,
  `RSP_TIMED_NODE_ID_PREFIX` varchar(20) NOT NULL COMMENT 'For species with timed component ontologies (human), this string is at the front of all timed component IDs for this species (''EHDA:'').  For species that don''t distinguish timed from untimed components (Xenopus), the prefix is the same for both (''XAO:'').',
  `RSP_NODE_ID_PREFIX` varchar(20) NOT NULL COMMENT 'This string is at the front of all abstract (untimed) component IDs in this species'' ontology.  For example, ''EHDAA:'' or ''XAO:''.',
  PRIMARY KEY (`RSP_NAME`),
  UNIQUE KEY `RSP_LATIN_IDX` (`RSP_LATIN_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REF_SPECIES`
--

LOCK TABLES `REF_SPECIES` WRITE;
/*!40000 ALTER TABLE `REF_SPECIES` DISABLE KEYS */;
INSERT INTO `REF_SPECIES` VALUES ('human','Homo sapiens','EHDA:','EHDAA:');
/*!40000 ALTER TABLE `REF_SPECIES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-07-29 11:32:31
