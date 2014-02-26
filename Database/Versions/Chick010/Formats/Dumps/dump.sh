#cd ~/GitMahost/Anatomy/Database/Versions/Chick010/Formats/Dumps/
#echo 'create database chick010;' | mysql -uroot -pbanana -hlocalhost 
mysqldump -uroot -pbanana -hlocalhost chick010 --routines > mySqlSchemaAndDataAndRoutinesDump.sql
