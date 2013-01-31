#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        Reload Anatomy Database - Human009 and on
# Date:         April 2012
# Author:       Mike Wicks
# Copyright:    2009 Medical Research Council, UK.
#               All rights reserved.
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
# Version:      1
# Maintenance:  Log changes below, with most recent at top of list.
#########################################################################

# /Users/mwicks/GitMahost/Anatomy/Database/Tasks/CreateChick010/Scripts

echo 'Reload Chick010 Database'
echo 'drop database chick010; create database chick010;' | mysql -uroot -pbanana chick010
mysql -uroot -pbanana chick010 < ../../../Versions/Chick009/Formats/Dumps/mySqlSchemaAndDataAndRoutinesDump.sql