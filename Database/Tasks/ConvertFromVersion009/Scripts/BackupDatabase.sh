#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        Backup Anatomy Database - Version009 and on
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

# BACKUP Anatomy Database'
echo 'BACKUP Anatomy Database'
mysqldump -uroot -pbanana --routines anatomy009 > ../../../Versions/Version009/Formats/Dumps/mySqlSchemaOldDataRoutinesDump.sql