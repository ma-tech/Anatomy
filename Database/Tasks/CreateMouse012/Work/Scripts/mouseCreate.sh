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

cd /Users/mwicks/GitMahost/Anatomy/Database/Tasks/CreateMouse012/Work/SQL

echo 'CREATE mouse012 Anatomy Database from /Users/mwicks/GitMahost/Anatomy/Database/Tasks/CreateMouse012/Work/SQL'
echo 'create database mouse012;' | mysql -uroot
