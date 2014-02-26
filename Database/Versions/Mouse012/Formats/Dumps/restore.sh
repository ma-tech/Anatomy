#cd ~/GitMahost/Anatomy/Database/Versions/Mouse011/Formats/Dumps/
echo 'drop database mouse011; create database mouse011;' | mysql -uroot -pbanana mouse011
mysql -uroot -pbanana mouse011 < mySqlSchemaAndDataAndRoutinesDump.sql 
