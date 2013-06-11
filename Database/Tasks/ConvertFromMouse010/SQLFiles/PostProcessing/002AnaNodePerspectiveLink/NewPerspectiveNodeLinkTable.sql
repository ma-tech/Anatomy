DROP TABLE IF EXISTS ANA_NODE_PERSPECTIVE_LINK;

CREATE TABLE ANA_NODE_PERSPECTIVE_LINK (
  NPL_OID int(11) NOT NULL AUTO_INCREMENT,
  NPL_PERSPECTIVE_FK varchar(255) NOT NULL DEFAULT ' ',
  NPL_NODE_FK int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (NPL_OID)
) ENGINE=InnoDB;

-- load data infile '/tmp/AnaNodePerspectiveLink.csv'
-- replace into table ANA_NODE_PERSPECTIVE_LINK
-- fields terminated by ','
-- lines terminated by '\n'
-- ignore 1 lines;
