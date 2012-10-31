#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        Replace Derived Data - Version009 and on
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

echo 'Replace Derived Data'
mysql -uroot -pbanana anatomy009 < ../SQLFiles/PostProcessing/replaceDerivedData.sql
