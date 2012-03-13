/*
-----------------------------------------------------------------------------------------------
# Project:      DAOAnatomyRebuild
#
# Title:        OBOValidateComponents.java
#
# Date:         2012
#
# Author:       Mike Wicks
#
# Copyright:    2012
#               Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Description:  A Main Class that Reads an OBO File and populates 4 tables in the anatomy
#                database with the extracted data.
#
#               Required Files:
#                1. dao.properties file contains the database access attributes
#                2. obo.properties file contains the OBO file access attributes
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; February 2012; Create Class
#
-----------------------------------------------------------------------------------------------
*/
package App;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import DAOLayer.DAOException;
import DAOLayer.DAOFactory;
import DAOLayer.StageRangeDAO;

import DAOModel.StageRange;

public class RunOBOValidateComponents {

    public static void run() {
    	
    	try {
            /*
            System.out.println("Create OBOFactory");
            System.out.println("=================");
            */
            // Obtain DAOFactory.
            DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
            //System.out.println("DAOFactory successfully obtained: " + anatomy008);

            /*
            System.out.println("Create DAOs");
            System.out.println("===========");
            */

            // Obtain DAOs.
            StageRangeDAO stagerangeDAO = anatomy008.getStageRangeDAO();
            //System.out.println("StageRangeDAO successfully obtained: " + componentDAO);

            List<StageRange> daostageranges = new ArrayList<StageRange>();
            daostageranges = stagerangeDAO.listByExistingChildExistingParent();
            
            int i = 0;

            System.out.println("");
            System.out.println("Tests 1, 2, 3 & 4 validate the stage ranges of components WITHIN the submitted OBO File ONLY");
            System.out.println("");
            System.out.println("Test 5 is slightly different!");
            System.out.println(" It validates the stage ranges of amended components WITHIN the submitted OBO File");
            System.out.println("  against the existing DATABASE, for those components whose parents have not been changed!");
            
            System.out.println("");
            System.out.println("Test 1");
            System.out.println("\t========================================================================");
            System.out.println("\tThe Number of EXISTING Child Nodes OUTSIDE EXISTING Parent Stage Range = " + Integer.toString(daostageranges.size()));
            System.out.println("\t========================================================================");
            System.out.println("\tchildId\t\tchildStart\tchildEnd\tparentId\tparentStart\tparentEnd");
            System.out.println("\t-------\t\t----------\t--------\t--------\t-----------\t---------");

            Iterator<StageRange> iteratorStageRange = daostageranges.iterator();
          	while (iteratorStageRange.hasNext()) {
           		StageRange stagerange = iteratorStageRange.next();
           		i++;
           		System.out.println(Integer.toString(i) + stagerange.reportStageRange());
          	}

            daostageranges = stagerangeDAO.listByExistingChildProposedParent();
            i = 0;

            System.out.println("");
            System.out.println("Test 2");
            System.out.println("\t========================================================================");
            System.out.println("\tThe Number of EXISTING Child Nodes OUTSIDE PROPOSED Parent Stage Range = " + Integer.toString(daostageranges.size()));
            System.out.println("\t========================================================================");
            System.out.println("\tchildId\t\tchildStart\tchildEnd\tparentId\tparentStart\tparentEnd");
            System.out.println("\t-------\t\t----------\t--------\t--------\t-----------\t---------");

            iteratorStageRange = daostageranges.iterator();
          	while (iteratorStageRange.hasNext()) {
           		StageRange stagerange = iteratorStageRange.next();
           		i++;
           		System.out.println(Integer.toString(i) + stagerange.reportStageRange());
          	}

          	daostageranges = stagerangeDAO.listByProposedChildProposedParent();
            i = 0;

            System.out.println("");
            System.out.println("Test 3");
            System.out.println("\t========================================================================");
            System.out.println("\tThe Number of PROPOSED Child Nodes OUTSIDE PROPOSED Parent Stage Range = " + Integer.toString(daostageranges.size()));
            System.out.println("\t========================================================================");
            System.out.println("\tchildId\t\tchildStart\tchildEnd\tparentId\tparentStart\tparentEnd");
            System.out.println("\t-------\t\t----------\t--------\t--------\t-----------\t---------");

            iteratorStageRange = daostageranges.iterator();
          	while (iteratorStageRange.hasNext()) {
           		StageRange stagerange = iteratorStageRange.next();
           		i++;
           		System.out.println(Integer.toString(i) + stagerange.reportStageRange());
          	}

            daostageranges = stagerangeDAO.listByProposedChildExistingParent();
            i = 0;

            System.out.println("");
            System.out.println("Test 4");
            System.out.println("\t========================================================================");
            System.out.println("\tThe Number of PROPOSED Child Nodes OUTSIDE EXISTING Parent Stage Range = " + Integer.toString(daostageranges.size()));
            System.out.println("\t========================================================================");
            System.out.println("\tchildId\t\tchildStart\tchildEnd\tparentId\tparentStart\tparentEnd");
            System.out.println("\t-------\t\t----------\t--------\t--------\t-----------\t---------");

            iteratorStageRange = daostageranges.iterator();
          	while (iteratorStageRange.hasNext()) {
           		StageRange stagerange = iteratorStageRange.next();
           		i++;
           		System.out.println(Integer.toString(i) + stagerange.reportStageRange());
          	}

            System.out.println("");

            daostageranges = stagerangeDAO.listByExistingChildExistingParentDatabase();
            i = 0;

            System.out.println("");
            System.out.println("Test 5");
            System.out.println("\t========================================================================================");
            System.out.println("\tThe Number of EXISTING Child Nodes OUTSIDE EXISTING Parent Stage Range within DATABASE = " + Integer.toString(daostageranges.size()));
            System.out.println("\t========================================================================================");
            System.out.println("\tchildId\t\tchildStart\tchildEnd\tparentId\tparentStart\tparentEnd");
            System.out.println("\t-------\t\t----------\t--------\t--------\t-----------\t---------");

            iteratorStageRange = daostageranges.iterator();
          	while (iteratorStageRange.hasNext()) {
           		StageRange stagerange = iteratorStageRange.next();
           		i++;
           		System.out.println(Integer.toString(i) + stagerange.reportStageRange());
          	}
    	}
		catch (DAOException daoexception) {
			daoexception.printStackTrace();
		}

    }

}
