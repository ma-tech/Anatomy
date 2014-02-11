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

# /Users/mwicks/GitMahost/Anatomy/Database/Tasks/ConvertFromVersion008/Scripts

echo 'Reload Anatomy Database'
echo 'drop database human009; create database human009;' | mysql -uroot -pbanana human009
mysql -uroot -pbanana human009 < ../../../Versions/Human009/Formats/Dumps/mySqlSchemaOldDataRoutinesDump.sql