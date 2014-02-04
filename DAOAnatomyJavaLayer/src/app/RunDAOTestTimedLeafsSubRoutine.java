/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
*
* Title:        RunDAOTestTimedLeafsSubRoutine.java
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
* Description:  A runnable class that accesses "TimedLeafs" by EMAP Id
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package app;

import java.util.ArrayList;
import java.util.List;

import utility.Wrapper;

import daomodel.TimedLeaf;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.ThingDAO;
import daointerface.TimedLeafDAO;

public class RunDAOTestTimedLeafsSubRoutine {

	public static void run (DAOFactory daofactory, String emapId, String stage) throws Exception {

		try {

			Wrapper.printMessage("RunDAOTestTimedLeafsSubRoutine.run", "***", daofactory.getDAOImpl(ThingDAO.class).getLevel());

			TimedLeafDAO timedleafDAO = daofactory.getDAOImpl(TimedLeafDAO.class);

	    	List<TimedLeaf> timedleafs = new ArrayList<TimedLeaf>();

        	timedleafs = timedleafDAO.listAllTimedNodesByRootNameByChildDesc( emapId, stage, emapId, stage );

			Wrapper.printMessage("RunDAOTestTimedLeafsSubRoutine.run : ==========================", "***", daofactory.getDAOImpl(ThingDAO.class).getLevel());
			Wrapper.printMessage("RunDAOTestTimedLeafsSubRoutine.run : " + emapId + " ; Stage : " + stage + " - convertLeafListToStringJsonAggregate", "***", daofactory.getDAOImpl(ThingDAO.class).getLevel());
			Wrapper.printMessage("RunDAOTestTimedLeafsSubRoutine.run : --------------------------", "***", daofactory.getDAOImpl(ThingDAO.class).getLevel());
			Wrapper.printMessage("RunDAOTestTimedLeafsSubRoutine.run : " + timedleafDAO.convertLeafListToStringJsonAggregate(timedleafs), "***", daofactory.getDAOImpl(ThingDAO.class).getLevel());
		}
		catch (DAOException daoe) {

			daoe.printStackTrace();
		}
	}
}
