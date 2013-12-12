mysql -uroot -pbanana mouse011 < update/001MoveOldPublicIdentifiers.sql
mysql -uroot -pbanana mouse011 < update/002MoveOldDisplayIdentifiers.sql
mysql -uroot -pbanana mouse011 < update/003SetNewPublicIdentifiers.sql
mysql -uroot -pbanana mouse011 < update/004SetNewDisplayIdentifiers.sql