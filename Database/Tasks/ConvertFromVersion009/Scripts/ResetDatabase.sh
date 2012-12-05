#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        Reload Anatomy Database - Version009 and on
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

# /Users/mwicks/GitMahost/Anatomy/Database/Tasks/ConvertFromVersion009/Scripts

echo 'Reset Mouse010 Database'
echo 'drop database mouse010; create database mouse010;' | mysql -uroot -pbanana mouse010
mysql -uroot -pbanana mouse010 < ../../../Versions/Version009/Formats/Dumps/mySqlSchemaAndDataAndRoutinesDump.sql