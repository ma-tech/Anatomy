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

echo 'CREATE human010 Anatomy Database from /Users/mwicks/GitMahost/Anatomy/SourceCode/DataScratchArea/SQL/'
echo 'drop database human010; create database human010;' | mysql -uroot human010

echo 'LOAD human010 Anatomy Database from /Users/mwicks/GitMahost/Anatomy/SourceCode/DataScratchArea/SQL/humanMySqlSchemaNODataAndRoutinesDump.sql'
mysql -uroot human010 < humanMySqlSchemaNODataAndRoutinesDump.sql
