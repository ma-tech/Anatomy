#cd ~/GitMahost/Anatomy/Database/Versions/Version009/Formats/Dumps/
#echo 'create database anatomy009;' | mysql -uroot -pbanana -hlocalhost 
mysqldump -uroot -pbanana -hlocalhost anatomy009 --routines > mySqlSchemaAndDataAndRoutinesDump.sql
