#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        PostProcessing - Version009 and on
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

# POST-PROCESSING - STEPS

# 1. Set-Up PYTHONPATH
echo '1. Set-Up PYTHONPATH'
#pwd
export PYTHONPATH=/Users/mwicks/GitMahost/Anatomy/Gmerg/Common/lib/python

# 2. Generate Derived Data
echo '2. Generate Derived Data'
cd ../../CreateNextVersion/RegenerateDerivedData
#pwd
./regenerateDerivedData.py ../../CreateNextVersion/RegenerateDerivedData/version009.config >& /tmp/regenerateDerivedData.log

# 3. Apply Derived Data
echo '3. Apply Derived Data'
mysql -uroot -pbanana anatomy009 < ../../ConvertFromVersion009/SQLFiles/PostProcessing/replaceDerivedData.sql

# 4. Update ANA_OBJECT (Index Entries)
echo '4. Update ANA_OBJECT (Index Entries)'
cd ../../ConvertFromVersion009/SQLFiles/PostProcessing/001AnaObject
#pwd
./createFiles.sh && ./applyFiles.sh

# 5. Generate Tree Formats
echo '5. Generate Tree Formats'
cd ../../../../GenerateTreeFormats
#pwd
./generateTreeReports.py version009.config >& /tmp/generateTreeReports.log && ./prettyPrintJson.sh /Users/mwicks/GitMahost/Anatomy/Database/Versions/Version009/Formats/Trees

# 6. Rebuild ANA_PERSPECTIVE_LINK
echo '6. Rebuild ANA_PERSPECTIVE_LINK'
cd ../ConvertFromVersion009/SQLFiles/PostProcessing/002AnaNodePerspectiveLink
#pwd
mysql -uroot -pbanana anatomy009 < createAnaNodePerspectiveLinkData.sql > AnaNodePerspectiveLink.csv
mysql -uroot -pbanana anatomy009 < NewPerspectiveNodeLinkTable.sql 
mysql -uroot -pbanana anatomy009 < createAnaNodePerspectiveLinkData.sql > update/inputAnaNodePerspectiveLinkData.sql 
mysql -uroot -pbanana anatomy009 < update/inputAnaNodePerspectiveLinkData.sql 

# 7. Generate OBO File
echo '7. Generate OBO File'
cd ../../../Scripts
#pwd
./ExtractOBO.sh

