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

cd /Users/mwicks/GitMahost/Anatomy/Database/Versions/Mouse011/Formats/Dumps

echo 'RELOAD mouse011 Anatomy Database from /Users/mwicks/GitMahost/Anatomy/Database/Versions/Mouse011/Formats/Dumps'
echo 'drop database mouse011; create database mouse011;' | mysql -uroot -pbanana mouse011
mysql -uroot -pbanana mouse011 < mySqlSchemaAndDataAndRoutinesDump.sql
#mysql -uroot -pbanana mouse011 < Mouse010mySqlSchemaAndDataAndRoutinesDump.sql