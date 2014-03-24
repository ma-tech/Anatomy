mysql -uroot -pbanana mouse010 < 001createAbstractEdgeTable.sql
mysql -uroot -pbanana mouse010 < 002createTimedEdgeTable.sql
mysql -uroot -pbanana mouse010 < 009inputAbstractEdgeSP.sql > update/011buildAbstractEdge.sql
mysql -uroot -pbanana mouse010 < 010inputTimedEdgeSP.sql > update/012buildTimedEdge.sql
