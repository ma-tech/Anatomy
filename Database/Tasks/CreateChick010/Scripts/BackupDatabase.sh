#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        Backup Anatomy Database - Human009 and on
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

# BACKUP Anatomy Database'
echo 'BACKUP Chick010 Database'
mysqldump -uroot -pbanana --routines chick010 > ../../../Versions/Chick010/Formats/Dumps/mySqlSchemaAndDataAndRoutinesDump.sql