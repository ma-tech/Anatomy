#cd ~/GitMahost/Anatomy/Database/Versions/Human009/Formats/Dumps/
#echo 'create database human009;' | mysql -uroot -pbanana -hlocalhost 
mysqldump -uroot -pbanana -hlocalhost chick009 --routines > mySqlSchemaAndDataAndRoutinesDump.sql
