#cd ~/GitMahost/Anatomy/Database/Versions/Chick010/Formats/Dumps/
#echo 'drop database chick010; create database chick010;' | mysql -uroot -pbanana chick010
echo 'create database chick010;' | mysql -uroot -pbanana -hlocalhost 
mysql -uroot -pbanana chick010 < mySqlSchemaAndDataAndRoutinesDump.sql 
