#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        PostProcessing - Version009 and on
# Date:         November 2009
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

# POST-PROCESSING - STEPS

# 1. Set-Up PYTHONPATH
echo '1. Set-Up PYTHONPATH'
export PYTHONPATH=/Users/mwicks/GitMahost/Anatomy/Gmerg/Common/lib/python

# 2. Generate Derived Data
echo '2. Generate Derived Data'
cd ../../CreateNextVersion/RegenerateDerivedData && ./regenerateDerivedData.py ../../CreateNextVersion/RegenerateDerivedData/version009.config >& /tmp/regenerateDerivedData.log

# 3. Apply Derived Data
echo '3. Apply Derived Data'
mysql -uroot -pbanana anatomy008 < ../../ConvertFromVersion008/SQLFiles/PostProcessing/replaceDerivedData.sql

# 4. Update ANA_OBJECT (Index Entries)
echo '4. Update ANA_OBJECT (Index Entries)'
# pwd
cd ../../ConvertFromVersion008/SQLFiles/PostProcessing/001AnaObject && ./createFiles.sh && ./applyFiles.sh

# 5. Generate Tree Formats
echo '5. Generate Tree Formats'
# pwd
cd ../../../../GenerateTreeFormats && ./generateTreeReports.py version009.config >& /tmp/generateTreeReports.log && ./prettyPrintJson.sh /Users/mwicks/GitMahost/Anatomy/Database/Versions/Version009/Formats/Trees
# pwd

# 6. Generate OBO File
echo '6. Generate OBO File'
echo 'NOT YET!!!'

