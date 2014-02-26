#cd ~/GitMahost/Anatomy/Database/Versions/Human009/Formats/Dumps/
#echo 'create database human009;' | mysql -uroot -pbanana -hlocalhost 
mysqldump -uroot -pbanana -hlocalhost human009 --routines > mySqlSchemaAndDataAndRoutinesDump.sql
