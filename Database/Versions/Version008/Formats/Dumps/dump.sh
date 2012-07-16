#cd ~/GitMahost/Anatomy/Database/Versions/Version008/Formats/Dumps/
#echo 'create database anatomy008;' | mysql -uroot -pbanana -hlocalhost 
mysqldump -uroot -pbanana -hlocalhost anatomy008 --routines > mySqlSchemaAndDataAndRoutinesDump.sql
