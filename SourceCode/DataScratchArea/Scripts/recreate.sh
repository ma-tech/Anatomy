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

cd /Users/mwicks/GitMahost/Anatomy/SourceCode/DataScratchArea/SQL

echo 'RELOAD empty011 Anatomy Database from /Users/mwicks/GitMahost/Anatomy/SourceCode/DataScratchArea/'
echo 'drop database empty011; create database empty011;' | mysql -uroot empty011

#mysql -uroot empty011 < mySqlSchemaAndDataAndRoutinesDump.sql
echo 'LOAD empty011 Anatomy Database from /Users/mwicks/GitMahost/Anatomy/SourceCode/DataScratchArea/mySqlSchemaNODataAndRoutinesDump.sql'
mysql -uroot empty011 < mySqlSchemaNODataAndRoutinesDump.sql

echo 'RELOAD empty011 Anatomy Database from /Users/mwicks/GitMahost/Anatomy/SourceCode/DataScratchArea/insertEmptyMouse.sql'
mysql -uroot empty011 < insertEmptyMouse.sql