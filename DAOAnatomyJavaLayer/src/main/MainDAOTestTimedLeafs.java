/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
*
* Title:        MainDAOTestTimedLeafs.java
*
* Date:         2012
*
* Author:       Mike Wicks
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version:      1
*
* Description:  A Main Executable Class 
* 
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package main;

import utility.Wrapper;

import daolayer.DAOFactory;

import app.RunDAOTestTimedLeafsSubRoutine;


public class MainDAOTestTimedLeafs {
	/*
	 * Main Class
	 */
    public static void main(String[] args) throws Exception {

    	long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

        /*
         * MAINLINE
         */
        // Obtain DAOFactory.
        DAOFactory daofactory = DAOFactory.getInstance("mouseAnatomy008LocalhostDebug");

        String emapId = "";
        String stage = "";

        emapId = "EMAP:25766"; stage = "TS01";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25767"; stage = "TS02";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25768"; stage = "TS03";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25769"; stage = "TS04";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25770"; stage = "TS05";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25771"; stage = "TS06";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25772"; stage = "TS07";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25773"; stage = "TS08";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25774"; stage = "TS09";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25775"; stage = "TS10";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25776"; stage = "TS11";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25777"; stage = "TS12";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25778"; stage = "TS13";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25779"; stage = "TS14";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25780"; stage = "TS15";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25781"; stage = "TS16";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25782"; stage = "TS17";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25783"; stage = "TS18";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25784"; stage = "TS19";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25785"; stage = "TS20";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25786"; stage = "TS21";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25787"; stage = "TS22";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25788"; stage = "TS23";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25789"; stage = "TS24";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25790"; stage = "TS25";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:25791"; stage = "TS26";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:30155"; stage = "TS27";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
    	
        emapId = "EMAP:27551"; stage = "TS28";
        RunDAOTestTimedLeafsSubRoutine.run(daofactory, emapId, stage);
        
    	Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    }
}
