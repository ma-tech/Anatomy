#cd ~/GitMahost/Anatomy/Database/Tasks/CreateHuman009/Scripts/EmptyHuman/
echo 'drop database human009; create database human009;' | mysql -uroot -pbanana human009
mysql -uroot -pbanana -hlocalhost human009 < mysqlAnatomy009NoDataRoutines.sql 
