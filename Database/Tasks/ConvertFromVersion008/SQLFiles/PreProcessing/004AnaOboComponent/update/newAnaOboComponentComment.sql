DROP TABLE IF EXISTS ANA_OBO_COMPONENT_COMMENT;
CREATE TABLE ANA_OBO_COMPONENT_COMMENT (
    ACC_OID BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    ACC_OBO_ID VARCHAR(25) NOT NULL,
    ACC_OBO_GENERAL_COMMENT VARCHAR(1000) NOT NULL,
    ACC_OBO_USER_COMMENT VARCHAR(1000) NOT NULL,
    ACC_OBO_ORDER_COMMENT VARCHAR(1000) NOT NULL,
    INDEX ACC_IDX1 (ACC_OBO_ID),
    PRIMARY KEY (ACC_OID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;