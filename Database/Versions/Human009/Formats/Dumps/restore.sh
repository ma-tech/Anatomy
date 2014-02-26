#cd ~/GitMahost/Anatomy/Database/Versions/Human009/Formats/Dumps/
echo 'drop database human009; create database human009;' | mysql -uroot -pbanana human009
#echo 'create database human009;' | mysql -uroot -pbanana -hlocalhost 
mysql -uroot -pbanana human009 < mySqlSchemaAndDataAndRoutinesDump.sql 
