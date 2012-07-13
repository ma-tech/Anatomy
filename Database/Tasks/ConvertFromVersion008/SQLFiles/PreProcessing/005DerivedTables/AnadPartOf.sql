DROP TABLE IF EXISTS ANAD_PART_OF;
CREATE TABLE ANAD_PART_OF (
  APO_OID int(10) unsigned NOT NULL COMMENT 'OID for this record. Since this table is derived, this does not persist across different generations of table.',
  APO_SPECIES_FK varchar(20) NOT NULL,
  APO_NODE_START_STAGE_FK int(10) unsigned NOT NULL COMMENT 'Start stage for node.',
  APO_NODE_END_STAGE_FK int(10) unsigned NOT NULL COMMENT 'End stage for node.',
  APO_PATH_START_STAGE_FK int(10) unsigned NOT NULL,
  APO_PATH_END_STAGE_FK int(10) unsigned NOT NULL,
  APO_NODE_FK int(10) unsigned NOT NULL,
  APO_SEQUENCE int(10) unsigned NOT NULL,
  APO_DEPTH int(10) unsigned NOT NULL,
  APO_FULL_PATH varchar(500) DEFAULT NULL,
  APO_FULL_PATH_OIDS varchar(500) DEFAULT NULL,
  APO_FULL_PATH_JSON_HEAD varchar(3000) DEFAULT NULL,
  APO_FULL_PATH_JSON_TAIL varchar(500) DEFAULT NULL,
  APO_IS_PRIMARY tinyint(1) NOT NULL,
  APO_IS_PRIMARY_PATH tinyint(1) NOT NULL COMMENT 'True if this is the primary path to this node; false if the path to this node contains a group node.  Every node, including group nodes has exactly 1 primary path to it.',
  APO_PARENT_APO_FK int(10) unsigned DEFAULT NULL COMMENT 'OID of the ANAD_PART_OF row that is the parent of this row.',
  PRIMARY KEY (APO_OID),
  UNIQUE KEY APOD_AK_INDEX (APO_SPECIES_FK,APO_SEQUENCE),
  KEY APO_NODE_FK (APO_NODE_FK),
  KEY APO_NODE_START_STAGE_FK_INDEX (APO_NODE_START_STAGE_FK),
  KEY APO_NODE_END_STAGE_FK_INDEX (APO_NODE_END_STAGE_FK),
  KEY APO_PATH_START_STAGE_FOREIGN_KEY (APO_PATH_START_STAGE_FK),
  KEY APO_PATH_END_STAGE_FOREIGN_KEY (APO_PATH_END_STAGE_FK),
  KEY APO_PARENT_APO_FOREIGN_KEY (APO_PARENT_APO_FK)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

