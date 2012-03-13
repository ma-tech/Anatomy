/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        RunDAOTest.java
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
* Version: 1
*
* Description:  A Main Class that accesses an Anatomy Database via a DAO Layer;
*                Finds are performed using each Data Access Object 
*
*               Required Files:
*                1. dao.properties file contains the OBO file access attributes
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/

package App;

import DAOLayer.DAOException;
import DAOLayer.DAOFactory;
import DAOLayer.DerivedPartOfDAO;
import DAOLayer.LogDAO;
import DAOLayer.NodeDAO;
import DAOLayer.RelationshipDAO;
import DAOLayer.StageDAO;
import DAOLayer.SynonymDAO;
import DAOLayer.ThingDAO;
import DAOLayer.TimedNodeDAO;
import DAOLayer.VersionDAO;

import DAOModel.DerivedPartOf;
import DAOModel.Log;
import DAOModel.Node;
import DAOModel.Relationship;
import DAOModel.Stage;
import DAOModel.Synonym;
import DAOModel.Thing;
import DAOModel.TimedNode;
import DAOModel.Version;

public class RunDAOTest {
	/*
	 * run Method
	 */
	public static void run () {

		try {
		    // Obtain DAOFactory.
		    DAOFactory anatomy008 = DAOFactory.getInstance("anatomy008");
		    System.out.println("DAOFactory successfully obtained: " + anatomy008);

		    // Obtain DAOs.
		    DerivedPartOfDAO derivedpartofDAO = anatomy008.getDerivedPartOfDAO();
		    System.out.println("DerivedPartOfDAO successfully obtained: " + derivedpartofDAO);
		    LogDAO logDAO = anatomy008.getLogDAO();
		    System.out.println("LogDAO successfully obtained: " + logDAO);
		    NodeDAO nodeDAO = anatomy008.getNodeDAO();
		    System.out.println("NodeDAO successfully obtained: " + nodeDAO);
		    RelationshipDAO relationshipDAO = anatomy008.getRelationshipDAO();
		    System.out.println("RelationshipDAO successfully obtained: " + relationshipDAO);
		    StageDAO stageDAO = anatomy008.getStageDAO();
		    System.out.println("StageDAO successfully obtained: " + stageDAO);
		    SynonymDAO synonymDAO = anatomy008.getSynonymDAO();
		    System.out.println("SynonymDAO successfully obtained: " + synonymDAO);
		    ThingDAO thingDAO = anatomy008.getThingDAO();
		    System.out.println("ThingDAO successfully obtained: " + thingDAO);
		    TimedNodeDAO timednodeDAO = anatomy008.getTimedNodeDAO();
		    System.out.println("TimedNodeDAO successfully obtained: " + timednodeDAO);
		    VersionDAO versionDAO = anatomy008.getVersionDAO();
		    System.out.println("VersionDAO successfully obtained: " + versionDAO);
		    
		    // Find a DerivedPartOf
		    String derivedpartofOid = "180"; 
		    DerivedPartOf derivedpartof = derivedpartofDAO.findByOid(Long.parseLong(derivedpartofOid));
		    System.out.println("derivedpartofDAO.findByOid(\"180\")");
		    System.out.println("Retrieved DerivedPartOf: " + derivedpartof);

		    // Find a Log
		    String logOid = "180"; 
		    Log log = logDAO.findByOid(Long.parseLong(logOid));
		    System.out.println("logDAO.findByOid(\"180\")");
		    System.out.println("Retrieved Log: " + log);
		    
		    // Find a Node.
		    String nodeOid = "18697"; 
		    Node node = nodeDAO.findByOid(Long.parseLong(nodeOid));
		    System.out.println("nodeDAO.findByOid(\"18697\")");
		    System.out.println("Retrieved Node: " + node);
		    
		    // Find a Relationship.
		    String relationshipOid = "15954"; 
		    Relationship relationship = relationshipDAO.findByOid(Long.parseLong(relationshipOid));
		    System.out.println("relationshipDAO.findByOid(\"15954\")");
		    System.out.println("Retrieved Relationship: " + relationship);
		    
		    // Find a Stage.
		    String stageOid = "21"; 
		    Stage stage = stageDAO.findByOid(Long.parseLong(stageOid));
		    System.out.println("stageDAO.findByOid(\"21\")");
		    System.out.println("Retrieved Stage: " + stage);
		    
		    // Find a Synonym.
		    String synonymOid = "14069"; 
		    Synonym synonym = synonymDAO.findByOid(Long.parseLong(synonymOid));
		    System.out.println("synonymDAO.findByOid(\"14069\")");
		    System.out.println("Retrieved Synonym: " + synonym);
		    
		    // Find a Thing
		    String thingOid = "180"; 
		    Thing thing = thingDAO.findByOid(Long.parseLong(thingOid));
		    System.out.println("thingDAO.findByOid(\"180\")");
		    System.out.println("Retrieved Thing: " + thing);
		    
		    // Find a TimedNode.
		    String timednodeOid = "4948"; 
		    TimedNode timednode = timednodeDAO.findByOid(Long.parseLong(timednodeOid));
		    System.out.println("timednodeDAO.findByOid(\"4948\")");
		    System.out.println("Retrieved TimedNode: " + timednode);
		    
		    // Find a Version.
		    String versionOid = "30083"; 
		    Version version = versionDAO.findByOid(Long.parseLong(versionOid));
		    System.out.println("versionDAO.findByOid(\"4948\")");
		    System.out.println("Retrieved Version: " + version);

		}
		catch (DAOException daoexception) {
			daoexception.printStackTrace();
		}

	}
    
}
