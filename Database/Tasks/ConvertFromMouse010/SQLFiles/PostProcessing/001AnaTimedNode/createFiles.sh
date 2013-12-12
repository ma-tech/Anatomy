mysql -uroot -pbanana mouse011 < 001MoveOldPublicIdentifiers.sql > update/001MoveOldPublicIdentifiers.sql
mysql -uroot -pbanana mouse011 < 002MoveOldDisplayIdentifiers.sql > update/002MoveOldDisplayIdentifiers.sql
mysql -uroot -pbanana mouse011 < 003SetNewPublicIdentifiers.sql > update/003SetNewPublicIdentifiers.sql
mysql -uroot -pbanana mouse011 < 004SetNewDisplayIdentifiers.sql > update/004SetNewDisplayIdentifiers.sql
