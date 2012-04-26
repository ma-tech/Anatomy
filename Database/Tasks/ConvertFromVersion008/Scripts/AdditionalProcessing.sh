#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        AdditionalProcessing - Version009 and on
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

# ADDITIONAL-PROCESSING - STEPS

# 1. Rebuild ANA_PERSPECTIVE_LINK
echo '1. Rebuild ANA_PERSPECTIVE_LINK'
pwd
cd ../SQLFiles/UpdateProcessing/002NewData
pwd
#mysql -uroot -pbanana anatomy008 < createAnaNodePerspectiveLinkData.sql > AnaNodePerspectiveLink.csv

# TODO => Script to add OID row numbers on each row

#rm /tmp/AnaNodePerspectiveLink.csv
#cp AnaNodePerspectiveLink.csv /tmp/AnaNodePerspectiveLink.csv
#mysql -uroot -pbanana anatomy008 < NewPerspectiveNodeLinkTable.sql

# 2. Rebuild Materialised Views (ANAM_EDGE & ANAM_TIMED_EDGE)
echo '2. Rebuild Materialised Views'
cd ../001SetUp
pwd
mysql -uroot -pbanana anatomy008 < 009inputAbstractEdgeSP.sql > update/011buildAbstractEdge.sql
mysql -uroot -pbanana anatomy008 < 010inputTimedEdgeSP.sql > update/012buildTimedEdge.sql
mysql -uroot -pbanana anatomy008 < update/011buildAbstractEdge.sql
mysql -uroot -pbanana anatomy008 < update/012buildTimedEdge.sql


