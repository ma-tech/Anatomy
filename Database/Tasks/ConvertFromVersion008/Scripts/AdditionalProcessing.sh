#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        AdditionalProcessing - Version009 and on
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

# ADDITIONAL-PROCESSING - STEPS

# 1. Rebuild Materialised Views (ANAM_EDGE & ANAM_TIMED_EDGE)
echo '1. Rebuild Materialised Views'
cd ../SQLFiles/AdditionalProcessing/001SetUp/
#pwd

echo 'A. Drop Existing Materialised Views'
mysql -uroot -pbanana anatomy009 < 001createAbstractEdgeTable.sql
mysql -uroot -pbanana anatomy009 < 002createTimedEdgeTable.sql
echo 'A. Drop Existing Materialised Views - DONE'
echo 'B. Build Data Files'
mysql -uroot -pbanana anatomy009 < 009inputAbstractEdgeSP.sql > update/011buildAbstractEdge.sql
mysql -uroot -pbanana anatomy009 < 010inputTimedEdgeSP.sql > update/012buildTimedEdge.sql
echo 'B. Build Data Files - DONE'
echo 'C. Insert Data Files'
mysql -uroot -pbanana anatomy009 < update/011buildAbstractEdge.sql > /log/011buildAbstractEdge.log
mysql -uroot -pbanana anatomy009 < update/012buildTimedEdge.sql > /log/012buildTimedEdge.log
echo 'C. Insert Data Files - DONE'
echo '1. Rebuild Materialised Views - ALL DONE'
