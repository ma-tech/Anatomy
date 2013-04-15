#cd ~/GitMahost/Anatomy/Database/Versions/Mouse010/Formats/Dumps/
#echo 'create database mouse010;' | mysql -uroot -pbanana -hlocalhost 
mysqldump -uroot -pbanana -hlocalhost mouse010 --routines > Mouse010mySqlSchemaAndDataAndRoutinesDump.sql
