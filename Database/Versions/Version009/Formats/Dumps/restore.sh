#cd ~/GitMahost/Anatomy/Database/Versions/Version009/Formats/Dumps/
echo 'drop database anatomy009; create database anatomy009;' | mysql -uroot -pbanana anatomy009
#echo 'create database anatomy009;' | mysql -uroot -pbanana -hlocalhost 
mysql -uroot -pbanana anatomy009 < mySqlSchemaAndDataAndRoutinesDump.sql 
