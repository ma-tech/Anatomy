mysql -uroot -pbanana anatomy008 < 001createAbstractEdgeTable.sql
mysql -uroot -pbanana anatomy008 < 002createTimedEdgeTable.sql
mysql -uroot -pbanana anatomy008 < 009inputAbstractEdgeSP.sql > update/011buildAbstractEdge.sql
mysql -uroot -pbanana anatomy008 < 010inputTimedEdgeSP.sql > update/012buildTimedEdge.sql
