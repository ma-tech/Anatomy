DROP TABLE IF EXISTS ANAM_EDGE;
CREATE TABLE ANAM_EDGE (
  AME_OID            INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  AME_ENTRY_EDGE_ID  INT NOT NULL default '0',
  AME_DIRECT_EDGE_ID INT NOT NULL default '0',
  AME_EXIT_EDGE_ID   INT NOT NULL default '0',
  AME_CHILD_NODE     VARCHAR(64) NOT NULL default ' ',
  AME_CHILD_STG_MIN  VARCHAR(64) NOT NULL default ' ',
  AME_CHILD_STG_MAX  VARCHAR(64) NOT NULL default ' ',
  AME_PARENT_NODE    VARCHAR(64) NOT NULL default ' ',
  AME_PARENT_STG_MIN VARCHAR(64) NOT NULL default ' ',
  AME_PARENT_STG_MAX VARCHAR(64) NOT NULL default ' ',
  AME_HOPS           INT NOT NULL default '0',
  AME_SOURCE         VARCHAR(64) NOT NULL default ' '
) ENGINE=InnoDB DEFAULT CHARSET=latin1;