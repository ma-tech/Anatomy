#cd ~/GitMahost/Anatomy/Database/Versions/Version008/Formats/Dumps/
echo 'drop database anatomy008; create database anatomy008;' | mysql -uroot -pbanana anatomy008
#echo 'create database anatomy008;' | mysql -uroot -pbanana -hlocalhost 
mysql -uroot -pbanana anatomy008 < mySqlSchemaAndDataAndRoutinesDump.sql 
