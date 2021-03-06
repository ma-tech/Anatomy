#! /bin/bash
#########################################################################
# Project:      Anatomy
# Title:        Regenerate Derived Data
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

echo 'GENERATE Tree Reports for Mouse012 Anatomy Database'
./generateTreeReports011.py Config/mouse012.config &> Log/generateTreeReports.log