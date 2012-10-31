#cd ~/GitMahost/Anatomy/Database/Versions/Mouse010/Formats/Dumps/
echo 'drop database mouse010; create database mouse010;' | mysql -uroot -pbanana mouse010
#echo 'create database mouse010;' | mysql -uroot -pbanana -hlocalhost 
mysql -uroot -pbanana mouse010 < mySqlSchemaAndDataAndRoutinesDump.sql 
