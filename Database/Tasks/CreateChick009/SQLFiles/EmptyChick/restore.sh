#cd ~/GitMahost/Anatomy/Database/Tasks/CreateChick009/SQLFiles/EmptyChick/
echo 'drop database chick009; create database chick009;' | mysql -uroot -pbanana chick009
mysql -uroot -pbanana -hlocalhost chick009 < mysqlAnatomy009NoDataRoutines.sql 
