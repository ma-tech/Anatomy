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

cd /Users/mwicks/GitMahost/Anatomy/Database/Versions/Human010/Formats/Dumps

echo 'RELOAD human010 Anatomy Database from Desktop'
echo 'drop database human010; create database human010;' | mysql -uroot -pbanana human010
mysql -uroot -pbanana human010 < mySqlSchemaAndDataAndRoutinesDump.sql
