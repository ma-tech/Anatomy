/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyJavaLayerRebuild
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
* Version:      1
*
* Description:  A runnable class that does various "find" operations 
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

import utility.Wrapper;
import utility.ObjectConverter;

//import daomodel.DerivedPartOfPerspectives;
//import daomodel.DerivedPartOfPerspectivesFK;
//import daomodel.DerivedPartOfPerspectivesJsonFK;
//import daomodel.DerivedRelationshipTransitive;
//import daomodel.JOINNodeRelationship;
//import daomodel.JOINNodeRelationshipNode;
//import daomodel.JOINNodeRelationshipRelationshipProject;
//import daomodel.JOINRelationshipProjectRelationship;
//import daomodel.JOINTimedNodeNodeStage;
//import daomodel.JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage;
//import daomodel.JOINTimedNodeStage;
//import daomodel.Leaf;
//import daomodel.Project;
//import daomodel.StageRange;
//import daomodel.TimedLeaf;
import daomodel.Component;
import daomodel.ComponentAlternative;
import daomodel.ComponentComment;
import daomodel.ComponentOrder;
import daomodel.ComponentRelationship;
import daomodel.ComponentSynonym;
import daomodel.Editor;
//import daomodel.Log;
import daomodel.Node;
import daomodel.OBOFile;
import daomodel.PerspectiveAmbit;
import daomodel.Relationship;
import daomodel.RelationshipProject;
import daomodel.Source;
import daomodel.Stage;
import daomodel.Synonym;
import daomodel.Thing;
import daomodel.TimedNode;
import daomodel.User;
import daomodel.Version;
import daomodel.DerivedPartOf;
import daomodel.DerivedPartOfFK;
import daomodel.ExtraTimedNode;

import daolayer.DAOException;
import daolayer.DAOFactory;

//import daointerface.DerivedPartOfPerspectivesDAO;
//import daointerface.DerivedPartOfPerspectivesFKDAO;
//import daointerface.DerivedPartOfPerspectivesJsonFKDAO;
//import daointerface.DerivedRelationshipTransitiveDAO;
//import daointerface.JOINNodeRelationshipDAO;
//import daointerface.JOINNodeRelationshipNodeDAO;
//import daointerface.JOINNodeRelationshipRelationshipProjectDAO;
//import daointerface.JOINRelationshipProjectRelationshipDAO;
//import daointerface.JOINTimedNodeNodeStageDAO;
//import daointerface.JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO;
//import daointerface.JOINTimedNodeStageDAO;
//import daointerface.LeafDAO;
//import daointerface.ProjectDAO;
//import daointerface.StageRangeDAO;
//import daointerface.TimedLeafDAO;
import daointerface.ComponentAlternativeDAO;
import daointerface.ComponentCommentDAO;
import daointerface.ComponentDAO;
import daointerface.ComponentOrderDAO;
import daointerface.ComponentRelationshipDAO;
import daointerface.ComponentSynonymDAO;
import daointerface.EditorDAO;
//import daointerface.LogDAO;
import daointerface.NodeDAO;
import daointerface.OBOFileDAO;
import daointerface.PerspectiveAmbitDAO;
import daointerface.RelationshipDAO;
import daointerface.RelationshipProjectDAO;
import daointerface.SourceDAO;
import daointerface.StageDAO;
import daointerface.SynonymDAO;
import daointerface.ThingDAO;
import daointerface.TimedNodeDAO;
import daointerface.UserDAO;
import daointerface.VersionDAO;
import daointerface.DerivedPartOfDAO;
import daointerface.DerivedPartOfFKDAO;
import daointerface.ExtraTimedNodeDAO;

public class RunDAOTest {

	public static void run (DAOFactory daofactory) throws Exception {

		try {
			
	        Wrapper.printMessage("RunDAOTest.run", "*", "*");

	        // Obtain DAOs.
	        ComponentDAO componentDAO = daofactory.getDAOImpl(ComponentDAO.class);
	        ComponentAlternativeDAO componentalternativeDAO = daofactory.getDAOImpl(ComponentAlternativeDAO.class);
	        ComponentCommentDAO componentcommentDAO = daofactory.getDAOImpl(ComponentCommentDAO.class);
	        ComponentOrderDAO componentorderDAO = daofactory.getDAOImpl(ComponentOrderDAO.class);
	        ComponentRelationshipDAO componentrelationshipDAO = daofactory.getDAOImpl(ComponentRelationshipDAO.class);
	        ComponentSynonymDAO componentsynonymDAO = daofactory.getDAOImpl(ComponentSynonymDAO.class);
	        DerivedPartOfDAO derivedpartofDAO = daofactory.getDAOImpl(DerivedPartOfDAO.class);
	        DerivedPartOfFKDAO derivedpartoffkDAO = daofactory.getDAOImpl(DerivedPartOfFKDAO.class);
	        //DerivedPartOfPerspectivesDAO derivedpartofperspectivesDAO = daofactory.getDAOImpl(DerivedPartOfPerspectivesDAO.class);
	        //DerivedPartOfPerspectivesFKDAO derivedpartofperspectivesfkDAO = daofactory.getDAOImpl(DerivedPartOfPerspectivesFKDAO.class);
	        //DerivedPartOfPerspectivesJsonFKDAO derivedpartofperspectivesjsonfkDAO = daofactory.getDAOImpl(DerivedPartOfPerspectivesJsonFKDAO.class);
	        //DerivedRelationshipTransitiveDAO derivedrelationshiptransitiveDAO = daofactory.getDAOImpl(DerivedRelationshipTransitiveDAO.class);
	        EditorDAO editorDAO = daofactory.getDAOImpl(EditorDAO.class);
	        ExtraTimedNodeDAO extratimednodeDAO = daofactory.getDAOImpl(ExtraTimedNodeDAO.class);
	        //JOINNodeRelationshipDAO joinnoderelationshipDAO = daofactory.getDAOImpl(JOINNodeRelationshipDAO.class);
	        //JOINNodeRelationshipNodeDAO joinnoderelationshipnodeDAO = daofactory.getDAOImpl(JOINNodeRelationshipNodeDAO.class);
	        //JOINNodeRelationshipRelationshipProjectDAO joinnoderelationshiprelationshipprojectDAO = daofactory.getDAOImpl(JOINNodeRelationshipRelationshipProjectDAO.class);
	        //JOINRelationshipProjectRelationshipDAO joinrelationshipprojectrelationshipDAO = daofactory.getDAOImpl(JOINRelationshipProjectRelationshipDAO.class);
	        //JOINTimedNodeNodeStageDAO jointimednodenodestageDAO = daofactory.getDAOImpl(JOINTimedNodeNodeStageDAO.class);
	        //JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO jointimednodenodestagerelationshipnodetimednodestageDAO = daofactory.getDAOImpl(JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO.class);
	        //JOINTimedNodeStageDAO jointimednodestageDAO = daofactory.getDAOImpl(JOINTimedNodeStageDAO.class);
	        //JsonNodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
	        //LeafDAO leafDAO = daofactory.getDAOImpl(LeafDAO.class);
	        //LogDAO logDAO = daofactory.getDAOImpl(LogDAO.class);
	        NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
	        OBOFileDAO obofileDAO = daofactory.getDAOImpl(OBOFileDAO.class);
	        PerspectiveAmbitDAO perspectiveambitDAO = daofactory.getDAOImpl(PerspectiveAmbitDAO.class);
	        //ProjectDAO projectDAO = daofactory.getDAOImpl(ProjectDAO.class);
	        RelationshipDAO relationshipDAO = daofactory.getDAOImpl(RelationshipDAO.class);
	        RelationshipProjectDAO relationshipprojectDAO = daofactory.getDAOImpl(RelationshipProjectDAO.class);
	        SourceDAO sourceDAO = daofactory.getDAOImpl(SourceDAO.class);
	        StageDAO stageDAO = daofactory.getDAOImpl(StageDAO.class);
	        //StageRangeDAO stageRangeDAO = daofactory.getDAOImpl(StageRangeDAO.class);
	        SynonymDAO synonymDAO = daofactory.getDAOImpl(SynonymDAO.class);
	        ThingDAO thingDAO = daofactory.getDAOImpl(ThingDAO.class);
	        //TimedLeafDAO timedleafDAO = daofactory.getDAOImpl(TimedLeafDAO.class);
	        TimedNodeDAO timednodeDAO = daofactory.getDAOImpl(TimedNodeDAO.class);
	        UserDAO userDAO = daofactory.getDAOImpl(UserDAO.class);
	        VersionDAO versionDAO = daofactory.getDAOImpl(VersionDAO.class);

	        // Find Node with OID = 33
	        long oid = 33;
	        
	        oid = 286970;

	        //ComponentDAO
	        if ( componentDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Component with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	Component component = componentDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + component.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Component with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        
	        oid = 1;

	        //ComponentAlternativeDAO
	        if ( componentalternativeDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentAlternative with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	ComponentAlternative componentalternative = componentalternativeDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + componentalternative.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentAlternative with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        
	        oid = 1;

	        //ComponentCommentDAO
	        if ( componentcommentDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentComment with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	ComponentComment componentcomment = componentcommentDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + componentcomment.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentComment with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        
	        oid = 37429;
	        
	        //ComponentOrderDAO
	        if ( componentorderDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentOrder with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	ComponentOrder componentorder = componentorderDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + componentorder.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentOrder with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 315711;
	        
	        //ComponentRelationshipDAO
	        if ( componentrelationshipDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentRelationship with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	ComponentRelationship componentrelationship = componentrelationshipDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + componentrelationship.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentRelationship with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        
	        oid = 18154;

	        //ComponentSynonymDAO
	        if ( componentsynonymDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentSynonym with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	ComponentSynonym componentsynonym = componentsynonymDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + componentsynonym.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The ComponentSynonym with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 1;

	        //DerivedPartOfDAO
	        if ( derivedpartofDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The DerivedPartOf with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	DerivedPartOf derivedpartof = derivedpartofDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + derivedpartof.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The DerivedPartOf with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 1;

	        //DerivedPartOfFKDAO
	        if ( derivedpartoffkDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The DerivedPartOfFK with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	DerivedPartOfFK derivedpartoffk = derivedpartoffkDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + derivedpartoffk.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The DerivedPartOfFK with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        
	        //DerivedPartOfPerspectivesDAO

	        //DerivedPartOfPerspectivesFKDAO
	        
	        //DerivedPartOfPerspectivesJsonFKDAO

	        //DerivedRelationshipTransitiveDAO

	        oid = 1;

	        //EditorDAO
	        if ( editorDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Editor with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	Editor editor = editorDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + editor.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Editor with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        
	        oid = 34;

	        //ExtraTimedNodeDAO
	        if ( extratimednodeDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The ExtraTimedNode with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	ExtraTimedNode extratimednode = extratimednodeDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + extratimednode.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The ExtraTimedNode with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        
	        //JOINNodeRelationshipDAO

	        //JOINNodeRelationshipNodeDAO
	        
	        //JOINNodeRelationshipRelationshipProjectDAO
	        
	        //JOINRelationshipProjectRelationshipDAO
	        
	        //JOINTimedNodeNodeStageDAO
	        
	        //JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO
	        
	        //JOINTimedNodeStageDAO

	        //LogDAO
	        /*
	        if ( logDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Log with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        Log log = logDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + log.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Log with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        */

	        oid = 33;

	        //NodeDAO
	        if ( nodeDAO.existOid(oid) ) {

	            Wrapper.printMessage("RunDAOTest.run : " + "The Node with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

	        	Node node = nodeDAO.findByOid(oid);
	            Wrapper.printMessage("RunDAOTest.run : " + node.toString(), "*", "*");
	        }
	        else {
	            
	            Wrapper.printMessage("RunDAOTest.run : " + "The Node with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 1;

	        //OBOFileDAO
	        if ( obofileDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The OBOFile with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        OBOFile obofile = obofileDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + obofile.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The OBOFile with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 31616;

	        //PerspectiveAmbitDAO
	        if ( perspectiveambitDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The PerspectiveAmbit with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        PerspectiveAmbit perspectiveambit = perspectiveambitDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + perspectiveambit.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The PerspectiveAmbit with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }


	        //ProjectDAO

	        oid = 83;

	        //RelationshipDAO
	        if ( relationshipDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Relationship with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        Relationship relationship = relationshipDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + relationship.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Relationship with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 12071;

	        //RelationshipProjectDAO
	        if ( relationshipprojectDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The RelationshipProject with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        RelationshipProject relationshipproject = relationshipprojectDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + relationshipproject.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The RelationshipProject with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 4;

	        //SourceDAO
	        if ( sourceDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Source with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        Source source = sourceDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + source.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Source with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        //StageDAO stageDAO = daofactory.getDAOImpl(StageDAO.class);

	        oid = 7;

	        //StageDAO
	        if ( stageDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Stage with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        Stage stage = stageDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + stage.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Stage with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        //StageRangeDAO

	        oid = 739;

	        //SynonymDAO
	        if ( synonymDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Synonym with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        Synonym synonym = synonymDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + synonym.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Synonym with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 100;

	        //ThingDAO
	        if ( thingDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Thing with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        Thing thing = thingDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + thing.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Thing with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }
	        
	        oid = 34;

	        //TimedNodeDAO
	        if ( timednodeDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The TimedNode with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        TimedNode timednode = timednodeDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + timednode.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The TimedNode with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 1;

	        //UserDAO
	        if ( userDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The User with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        User user = userDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + user.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The User with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

	        oid = 33977;

	        //VersionDAO
	        if ( versionDAO.existOid(oid) ) {

		        Wrapper.printMessage("RunDAOTest.run : " + "The Version with an OID of " + ObjectConverter.convert(oid, String.class) + " EXISTS!", "*", "*");

		        Version version = versionDAO.findByOid(oid);
		        Wrapper.printMessage("RunDAOTest.run : " + version.toString(), "*", "*");
	        }
	        else {
	            
		        Wrapper.printMessage("RunDAOTest.run : " + "The Version with an OID of " + ObjectConverter.convert(oid, String.class) + " DOES NOT EXIST!", "*", "*");
	        }

		}
		catch (DAOException daoe) {

			daoe.printStackTrace();
		}
	}
}
