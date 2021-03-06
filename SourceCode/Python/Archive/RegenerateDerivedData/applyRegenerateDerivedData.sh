#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        Apply Regenerated Derived Data
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

echo 'APPLY regenerated Derived Data for Mouse011 Anatomy Database'
mysql -uroot -pbanana mouse011 < ../../ConvertFromMouse010/SQLFiles/PostProcessing/replaceDerivedData.sql