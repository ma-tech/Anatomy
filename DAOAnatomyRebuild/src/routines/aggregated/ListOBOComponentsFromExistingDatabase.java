/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        ListOBOComponentsFromExistingDatabase.java
*
* Date:         2008
*
* Author:       MeiSze Lam and Attila Gyenesi
*
* Copyright:    2009 Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version:      1
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Description:  This Class extracts a list of components from the database, and builds a list 
*                in the OBO style.
*
* Who; When; What;
*
* Mike Wicks; September 2010; Tidy up and Document
* Mike Wicks; February 2012; Completely rewrite to use standardised DAO Layer
* Mike Wicks; November 2012; More Tidying up
* 
*----------------------------------------------------------------------------------------------
*/
package routines.aggregated;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daointerface.ComponentAlternativeDAO;
import daointerface.NodeDAO;
import daointerface.SynonymDAO;
import daointerface.TimedNodeDAO;
import daointerface.JOINNodeRelationshipRelationshipProjectDAO;
import daointerface.JOINNodeRelationshipNodeDAO;
import daointerface.JOINTimedNodeNodeStageDAO;
import daointerface.JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO;
import daointerface.JOINTimedNodeStageDAO;

import daomodel.ComponentAlternative;
import daomodel.Node;
import daomodel.Synonym;
import daomodel.TimedNode;
import daomodel.JOINNodeRelationshipRelationshipProject;
import daomodel.JOINNodeRelationshipNode;
import daomodel.JOINTimedNodeNodeStage;
import daomodel.JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage;
import daomodel.JOINTimedNodeStage;

import obolayer.OBOFactory;

import obomodel.OBOComponent;
import obomodel.OBORelation;

import utility.ObjectConverter;
import utility.Wrapper;

public class ListOBOComponentsFromExistingDatabase {
    // Properties ---------------------------------------------------------------------------------
    // global variables
    private ArrayList <OBOComponent> obocomponentList = new ArrayList <OBOComponent>();
    private ArrayList <OBORelation> relationList = new ArrayList <OBORelation>();
    
    private boolean defaultroot;
    
    private String requestMsgLevel;
    
    private static String HUMAN_NAME_SPACE = "human_developmental_anatomy";
    //private static String MOUSE_NAME_SPACE = "abstract_anatomy";
    private static String MOUSE_NAME_SPACE = "anatomical_structure";
    private static String CHICK_NAME_SPACE = "abstract_anatomy";
    
    private static String HUMAN_TIMED_NAME_SPACE = "human_developmental_anatomy";
    //private static String MOUSE_TIMED_NAME_SPACE = "timed_anatomy";
    private static String MOUSE_TIMED_NAME_SPACE = "stage_specific_anatomical_structure";
    private static String CHICK_TIMED_NAME_SPACE = "timed_anatomy";
    
    private static String HUMAN_NAME = "Abstract human developmental anatomy";
    //private static String MOUSE_NAME = "Abstract anatomy";
    private static String MOUSE_NAME = "Anatomical structure";
    private static String CHICK_NAME = "Abstract anatomy";
    
    private static String HUMAN_TIMED_NAME = "Timed human developmental anatomy";
    //private static String MOUSE_TIMED_NAME = "Timed anatomy";
    private static String MOUSE_TIMED_NAME = "Stage Specific Anatomical Structure";
    private static String CHICK_TIMED_NAME = "Timed anatomy";

    private static String HUMAN_ID = "EHDAA:0";
    private static String MOUSE_ID = "EMAPA:0";
    private static String CHICK_ID = "ECAPA:0";
    
    private static String HUMAN_TIMED_ID = "EHDA:0";
    private static String MOUSE_TIMED_ID = "EMAP:0";
    private static String CHICK_TIMED_ID = "ECAP:0";

    private static String HUMAN_STAGE_NAME_SPACE =  "carnegie_stage";
    private static String MOUSE_STAGE_NAME_SPACE =  "theiler_stage";
    private static String CHICK_STAGE_NAME_SPACE =  "hamburger_hamilton_stage";

    private static String HUMAN_STAGE_UPPER = "Carnegie stage";
    private static String MOUSE_STAGE_UPPER = "Theiler stage";
    private static String CHICK_STAGE_UPPER = "Hamburger Hamilton stage";

    private static String HUMAN_STAGE_MAX =  "CS23";
    private static String MOUSE_STAGE_MAX =  "TS28";
    private static String CHICK_STAGE_MAX =  "HH48";

    private static String HUMAN_STAGE_MIN =  "CS01";
    private static String MOUSE_STAGE_MIN =  "TS01";
    private static String CHICK_STAGE_MIN =  "EGK-I";
    
    // Constructor --------------------------------------------------------------------------------
    public ListOBOComponentsFromExistingDatabase(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory, boolean defaultroot ) throws Exception {
    	
    	this.defaultroot = defaultroot;
    	
		this.requestMsgLevel = requestMsgLevel;
		
		Wrapper.printMessage("listobocomponentsfromexistingdatabase.constructor#1", "***", this.requestMsgLevel);

		try {
			
    	    // Obtain DAOs.
            NodeDAO nodeDAO = daofactory.getDAOImpl(NodeDAO.class);
            TimedNodeDAO timednodeDAO = daofactory.getDAOImpl(TimedNodeDAO.class);
            SynonymDAO synonymDAO = daofactory.getDAOImpl(SynonymDAO.class);
            ComponentAlternativeDAO componentalternativeDAO = daofactory.getDAOImpl(ComponentAlternativeDAO.class);

            JOINNodeRelationshipRelationshipProjectDAO nrrpjoinDAO = daofactory.getDAOImpl(JOINNodeRelationshipRelationshipProjectDAO.class); 
            JOINNodeRelationshipNodeDAO nrnjoinDAO = daofactory.getDAOImpl(JOINNodeRelationshipNodeDAO.class); 
            JOINTimedNodeStageDAO jointimednodestageDAO = daofactory.getDAOImpl(JOINTimedNodeStageDAO.class);

            // 1: abstract class---------------------------------------------------------------------------
            OBOComponent obocomponent;

      		if ( obofactory.getOBOComponentAccess().species().equals("mouse") ) {
      			
      			addAbstractMouseDetails();
      		}
      		else if ( obofactory.getOBOComponentAccess().species().equals("chick") ) {
      			
      			addAbstractChickDetails();
      		}
      		else if ( obofactory.getOBOComponentAccess().species().equals("human") ) {
      			
      			addAbstractHumanDetails();
      		}
      		else {
      			
      			Wrapper.printMessage("1 - listobocomponentsfromexistingdatabase.constructor#1:UNKNOWN Species = " + obofactory.getOBOComponentAccess().species(), "*", this.requestMsgLevel);
      		}

      		List<Node> nodes = new ArrayList<Node>();
            nodes = nodeDAO.listAll();
            
            Iterator<Node> iteratorNode = nodes.iterator();
                
          	while (iteratorNode.hasNext()) {
          	
          		Node node = iteratorNode.next();

                obocomponent = new OBOComponent();
                
                if ( node.getComponentName().equals(obofactory.getOBOComponentAccess().species()) ) {
                	
                    obocomponent.setName( node.getComponentName());
                    obocomponent.setID( node.getPublicId() );
                    
              		if ( obofactory.getOBOComponentAccess().species().equals("mouse") ) {
              			
                        obocomponent.setNamespace( MOUSE_NAME_SPACE );
                        obocomponent.addChildOf( MOUSE_ID );
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("chick") ) {
              			
                        obocomponent.setNamespace( CHICK_NAME_SPACE );
                        obocomponent.addChildOf( CHICK_ID );
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("human") ) {
              			
                        obocomponent.setNamespace( HUMAN_NAME_SPACE );
                        obocomponent.addChildOf( HUMAN_ID );
              		}
              		else {
              			
              			Wrapper.printMessage("2 - listobocomponentsfromexistingdatabase.constructor#1:UNKNOWN Species = " + obofactory.getOBOComponentAccess().species(), "*", this.requestMsgLevel);
              		}

              		obocomponent.addChildOfType( "IS_A" );
                }
                else {
                
                    obocomponent.setName( node.getComponentName());

              		if ( obofactory.getOBOComponentAccess().species().equals("mouse") ) {
              			
                        obocomponent.setNamespace(MOUSE_NAME_SPACE);
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("chick") ) {
              			
                        obocomponent.setNamespace(CHICK_NAME_SPACE);
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("human") ) {
              			
                        obocomponent.setNamespace(HUMAN_NAME_SPACE);
              		}
              		else {
              			
              			Wrapper.printMessage("3 - listobocomponentsfromexistingdatabase.constructor#1:UNKNOWN Species = " + obofactory.getOBOComponentAccess().species(), "*", this.requestMsgLevel);
              		}

                    obocomponent.setID( node.getPublicId() );
                    obocomponent.setDBID( Long.toString(node.getOid()) );
                    obocomponent.setIsPrimary( node.isPrimary() );

                    // query for the node's partOf relationship---------------------------------------------
                    List<JOINNodeRelationshipRelationshipProject> nrrpJoins = nrrpjoinDAO.listAllByChildAndProject(Long.valueOf(obocomponent.getDBID()), obofactory.getOBOComponentAccess().project());
                    
                    Iterator<JOINNodeRelationshipRelationshipProject> iteratorNrrpJoin = nrrpJoins.iterator();
                        
                    while ( iteratorNrrpJoin.hasNext() ) {
                    	
                       	JOINNodeRelationshipRelationshipProject nrrpJoin = iteratorNrrpJoin.next();
                       	
                       	obocomponent.addOrderComment("order=" + ObjectConverter.convert(nrrpJoin.getSequenceFK(), String.class) + " for " + nrrpJoin.getPublicId());
                       	
                        obocomponent.addChildOf( nrrpJoin.getPublicId() );

                        if ( nrrpJoin.getTypeFK().equals("part-of") ) {
                        	
                            obocomponent.addChildOfType( "PART_OF" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("is-a") ) {
                        	
                            obocomponent.addChildOfType( "IS_A" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("derives-from") ) {
                        	
                        	obocomponent.addChildOfType( "DERIVES_FROM" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("develops-from") ) {
                        	
                        	obocomponent.addChildOfType( "DEVELOPS_FROM" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("located-in") ) {
                        	
                        	obocomponent.addChildOfType( "LOCATED_IN" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("develops-in") ) {
                        	
                        	obocomponent.addChildOfType( "DEVELOPS_IN" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("disjoint-from") ) {
                        	
                        	obocomponent.addChildOfType( "DISJOINT_FROM" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("attached-to") ) {
                        	
                        	obocomponent.addChildOfType( "ATTACHED_TO" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("has-part") ) {
                        	
                        	obocomponent.addChildOfType( "HAS_PART" );
                        }
                        else if ( nrrpJoin.getTypeFK().equals("connected_to") ) {
                        	
                        	obocomponent.addChildOfType( "CONNECTED_TO" );
                        }
                        else {
                        	
                    	    Wrapper.printMessage("4 - listobocomponentsfromexistingdatabase.constructor#1:UNKNOWN Relationship Type = " + nrrpJoin.getTypeFK(), "*", this.requestMsgLevel);
                        }
                    }

                    // query for the node's ISA relationship---------------------------------------------
                    List<JOINNodeRelationshipNode> nrnJoins = nrnjoinDAO.listAllIsAsByChild(obocomponent.getDBID());
                    
                    Iterator<JOINNodeRelationshipNode> iteratornrnJoin = nrnJoins.iterator();
                        
                    while ( iteratornrnJoin.hasNext() ) {
                    	
                       	JOINNodeRelationshipNode nrnJoin = iteratornrnJoin.next();
                       	
                        obocomponent.addChildOf( nrnJoin.getBPublicId() );
                        obocomponent.addChildOfType( "IS_A" );
                    }

                    // query for the node's synonyms--------------------------------------------------------
                    List<Synonym> synonyms = synonymDAO.listByObjectFK( Long.valueOf(obocomponent.getDBID()) );
                    
                    Iterator<Synonym> iteratorSynonym = synonyms.iterator();
                        
                  	while (iteratorSynonym.hasNext()) {
                  		
                  		Synonym synonym = iteratorSynonym.next();
                  		
                  		obocomponent.addSynonym( synonym.getName() );
                    }

                    // query for the node's ALTERNATIVES--------------------------------------------------------
                    List<ComponentAlternative> componentalternatives = componentalternativeDAO.listByOboId( obocomponent.getID() );
                    
                    Iterator<ComponentAlternative> iteratorComponentAlternative = componentalternatives.iterator();
                        
                  	while (iteratorComponentAlternative.hasNext()) {
                  		
                  		ComponentAlternative componentalternative = iteratorComponentAlternative.next();
                  		
                  		obocomponent.addAlternative( componentalternative.getAltId() );
                    }

                    // query for the node's Timed Components --------------------------------------------------------
                    List<TimedNode> timednodes = timednodeDAO.listByNodeFK( node.getOid() );
                    
                    Iterator<TimedNode> iteratorTimedNode = timednodes.iterator();
                        
                  	while (iteratorTimedNode.hasNext()) {
                  		
                  		TimedNode timednode = iteratorTimedNode.next();

                  		obocomponent.addTimedComponent( timednode.getPublicId() );
                    }

                    // query for the node's start and end stage---------------------------------------------
                    List<JOINTimedNodeStage> tnsJoins = jointimednodestageDAO.listAllByNodeFkOrderByStageSequence( Long.valueOf(obocomponent.getDBID()) );
                    
                    Iterator<JOINTimedNodeStage> iteratorTnsJoin = tnsJoins.iterator();
                    
                    int rowCount = jointimednodestageDAO.countAllByNodeFk( Long.valueOf(obocomponent.getDBID()) );
              		int i = 0;

              		if ( obofactory.getOBOComponentAccess().species().equals("mouse") ) {
              			
              			obocomponent.setStart( MOUSE_STAGE_MIN );
                  		obocomponent.setEnd( MOUSE_STAGE_MAX );
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("chick") ) {
              			
              			obocomponent.setStart( CHICK_STAGE_MIN );
                  		obocomponent.setEnd( CHICK_STAGE_MAX );
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("human") ) {
              			
              			obocomponent.setStart( HUMAN_STAGE_MIN );
                  		obocomponent.setEnd( HUMAN_STAGE_MAX );
              		}
              		else {
              			
              			Wrapper.printMessage("5 - listobocomponentsfromexistingdatabase.constructor#1:UNKNOWN Species = " + obofactory.getOBOComponentAccess().species(), "*", this.requestMsgLevel);
              		}

                  	while (iteratorTnsJoin.hasNext()) {
                  		
                  		JOINTimedNodeStage tnsJoin = iteratorTnsJoin.next();

                  		i++;
                  		
                  		if ( i == 1) {
                  			
                  			obocomponent.setStart( tnsJoin.getName() );
                  		}
                  		
                  		if ( i == rowCount) {
                  			
                  			obocomponent.setEnd( tnsJoin.getName() );
                  		}
                    }
                }

              	obocomponentList.add( obocomponent );
            }
    	}

        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        }
    }

    public ListOBOComponentsFromExistingDatabase(String requestMsgLevel, DAOFactory daofactory, OBOFactory obofactory, boolean defaultroot, String stage ) throws Exception {
    	
    	this.defaultroot = defaultroot;
    	
		this.requestMsgLevel = requestMsgLevel;
		
		Wrapper.printMessage("listobocomponentsfromexistingdatabase.constructor#2", "***", this.requestMsgLevel);

		try {
    		
            // Obtain DAOs.

            JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO tnnsrntnsjoinDAO = daofactory.getDAOImpl(JOINTimedNodeNodeStageRelationshipNodeTimedNodeStageDAO.class); 
            JOINTimedNodeNodeStageDAO jointimednodenodestageDAO = daofactory.getDAOImpl(JOINTimedNodeNodeStageDAO.class);

            // 1: abstract class---------------------------------------------------------------------------
            OBOComponent obocomponent;

      		if ( obofactory.getOBOComponentAccess().species().equals("mouse") ) {
      			
      			addTimedMouseDetails( stage );
      		}
      		else if ( obofactory.getOBOComponentAccess().species().equals("chick") ) {
      			
      			addTimedChickDetails( stage );
      		}
      		else if ( obofactory.getOBOComponentAccess().species().equals("human") ) {
      			
      			addTimedHumanDetails( stage );
      		}
      		else {
      			
      			Wrapper.printMessage("6 - listobocomponentsfromexistingdatabase.constructor#2:UNKNOWN Species = " + obofactory.getOBOComponentAccess().species(), "*", this.requestMsgLevel);
      		}


      		List<JOINTimedNodeNodeStage> jointimednodenodestages = new ArrayList<JOINTimedNodeNodeStage>();
      		jointimednodenodestages = jointimednodenodestageDAO.listAllByStageName(stage);
            
            Iterator<JOINTimedNodeNodeStage> iteratorJOINTimedNodeNodeStage = jointimednodenodestages.iterator();
                
          	while (iteratorJOINTimedNodeNodeStage.hasNext()) {
          	
          		JOINTimedNodeNodeStage jointimednodenodestage = iteratorJOINTimedNodeNodeStage.next();

                obocomponent = new OBOComponent();
                
                if ( jointimednodenodestage.getComponentName().equals(obofactory.getOBOComponentAccess().species()) ) {
                	
                    obocomponent.setName( jointimednodenodestage.getComponentName());
                    obocomponent.setID( jointimednodenodestage.getPublicTimedNodeId() );
                    
              		if ( obofactory.getOBOComponentAccess().species().equals("mouse") ) {
              			
                        obocomponent.setNamespace( MOUSE_TIMED_NAME_SPACE );
                        obocomponent.addChildOf( MOUSE_TIMED_ID );
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("chick") ) {
              			
                        obocomponent.setNamespace( CHICK_TIMED_NAME_SPACE );
                        obocomponent.addChildOf( CHICK_TIMED_ID );
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("human") ) {
              			
                        obocomponent.setNamespace( HUMAN_TIMED_NAME_SPACE );
                        obocomponent.addChildOf( HUMAN_TIMED_ID );
              		}
              		else {
              			
              			Wrapper.printMessage("7 - listobocomponentsfromexistingdatabase.constructor#2:UNKNOWN Species = " + obofactory.getOBOComponentAccess().species(), "*", this.requestMsgLevel);
              		}

              		obocomponent.addChildOfType( "IS_A" );
                }
                else {
                
                    obocomponent.setName( jointimednodenodestage.getComponentName());
                    
              		if ( obofactory.getOBOComponentAccess().species().equals("mouse") ) {
              			
                        obocomponent.setNamespace(MOUSE_NAME_SPACE);
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("chick") ) {
              			
                        obocomponent.setNamespace(CHICK_NAME_SPACE);
              		}
              		else if ( obofactory.getOBOComponentAccess().species().equals("human") ) {
              			
                        obocomponent.setNamespace(HUMAN_NAME_SPACE);
              		}
              		else {
              			
              			Wrapper.printMessage("8 - listobocomponentsfromexistingdatabase.constructor#2:UNKNOWN Species = " + obofactory.getOBOComponentAccess().species(), "*", this.requestMsgLevel);
              		}

                    obocomponent.setID( jointimednodenodestage.getPublicTimedNodeId() );
                    obocomponent.setDBID( Long.toString(jointimednodenodestage.getOidNode()) );
                    obocomponent.setIsPrimary( jointimednodenodestage.isPrimary() );

                    // query for the node's partOf relationship---------------------------------------------
                    List<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> tnnsrntnsjoins = 
                    		tnnsrntnsjoinDAO.listAllStageNameAndParent(stage, stage, jointimednodenodestage.getPublicTimedNodeId());
                    
                    Iterator<JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage> iteratorTnnsrntnsJoin = tnnsrntnsjoins.iterator();
                        
                    while ( iteratorTnnsrntnsJoin.hasNext() ) {
                    	
                    	JOINTimedNodeNodeStageRelationshipNodeTimedNodeStage tnnsrntnsJoin = iteratorTnnsrntnsJoin.next();
                       	
                        obocomponent.addChildOf( tnnsrntnsJoin.getPublicTimedNodeIdII() );

                        if ( tnnsrntnsJoin.getTypeFK().equals("part-of") ) {
                        	
                            obocomponent.addChildOfType( "PART_OF" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("is-a") ) {
                        	
                            obocomponent.addChildOfType( "IS_A" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("derives-from") ) {
                        	
                        	obocomponent.addChildOfType( "DERIVES_FROM" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("develops-from") ) {
                        	
                        	obocomponent.addChildOfType( "DEVELOPS_FROM" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("located-in") ) {
                        	
                        	obocomponent.addChildOfType( "LOCATED_IN" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("develops-in") ) {
                        	
                        	obocomponent.addChildOfType( "DEVELOPS_IN" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("disjoint-from") ) {
                        	
                        	obocomponent.addChildOfType( "DISJOINT_FROM" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("attached-to") ) {
                        	
                        	obocomponent.addChildOfType( "ATTACHED_TO" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("has-part") ) {
                        	
                        	obocomponent.addChildOfType( "HAS_PART" );
                        }
                        else if ( tnnsrntnsJoin.getTypeFK().equals("connected_to") ) {
                        	
                        	obocomponent.addChildOfType( "CONNECTED_TO" );
                        }
                        else {

                    	    Wrapper.printMessage("9 - listobocomponentsfromexistingdatabase.constructor#2:UNKNOWN Relationship Type = " + tnnsrntnsJoin.getTypeFK(), "*", this.requestMsgLevel);
                        }
                    }
                }

              	obocomponentList.add( obocomponent );
            }
    	}
        catch ( DAOException dao ) {
        	
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
            ex.printStackTrace();
        }
    }

    public ArrayList<OBOComponent> getTermList() {
        return obocomponentList;
    }
    
    public ArrayList<OBORelation> getOBORelationList() {
        return relationList;
    }
    
    public void addAbstractHumanDetails() {

    	OBOComponent obocomponent = new OBOComponent();
        obocomponent.setName( HUMAN_NAME );
        obocomponent.setID( HUMAN_ID );
        obocomponent.setNamespace( HUMAN_NAME_SPACE );
        obocomponent.setDBID( "-1" );

        if ( defaultroot ) {
        	
            obocomponentList.add( obocomponent );
        }
        
        // stage class------------------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( HUMAN_STAGE_UPPER );
        obocomponent.setID( "CS:0" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );
        
        obocomponent = new OBOComponent();
        obocomponent.setName( "CS01" );
        obocomponent.setID( "CS01" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS02" );
        obocomponent.setID( "CS02" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS03" );
        obocomponent.setID( "CS03" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS04" );
        obocomponent.setID( "CS04" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS05a" );
        obocomponent.setID( "CS05a" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS05b" );
        obocomponent.setID( "CS05b" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS05c" );
        obocomponent.setID( "CS05c" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS06a" );
        obocomponent.setID( "CS06a" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS06b" );
        obocomponent.setID( "CS06b" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS07" );
        obocomponent.setID( "CS07" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS08" );
        obocomponent.setID( "CS08" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS09" );
        obocomponent.setID( "CS09" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS10" );
        obocomponent.setID( "CS10" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS11" );
        obocomponent.setID( "CS11" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS12" );
        obocomponent.setID( "CS12" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS13" );
        obocomponent.setID( "CS13" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS14" );
        obocomponent.setID( "CS14" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS15" );
        obocomponent.setID( "CS15" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS16" );
        obocomponent.setID( "CS16" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS17" );
        obocomponent.setID( "CS17" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS18" );
        obocomponent.setID( "CS18" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS19" );
        obocomponent.setID( "CS19" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS20" );
        obocomponent.setID( "CS20" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS21" );
        obocomponent.setID( "CS21" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS22" );
        obocomponent.setID( "CS22" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "CS23" );
        obocomponent.setID( "CS23" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "CS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        // starts at
        OBORelation relation = new OBORelation();
        relation.setName( "starts_at" );
        relation.setID( "starts_at" );
        relationList.add( relation );

        // ends at
        relation = new OBORelation();
        relation.setName( "ends_at" );
        relation.setID( "ends_at" );
        relationList.add( relation );

        // partOf
        relation = new OBORelation();
        relation.setName( "part_of" );
        relation.setID( "part_of" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // isA
        relation = new OBORelation();
        relation.setName( "is_a" );
        relation.setID( "is_a" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsFrom
        relation = new OBORelation();
        relation.setName( "develops_from" );
        relation.setID( "develops_from" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsIn
        relation = new OBORelation();
        relation.setName( "develops_in" );
        relation.setID( "develops_in" );
        relationList.add( relation );

        // AttachedTo
        relation = new OBORelation();
        relation.setName( "attached_to" );
        relation.setID( "attached_to" );
        relationList.add( relation );

        // LocatedIn
        relation = new OBORelation();
        relation.setName( "located_in" );
        relation.setID( "located_in" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "has_part" );
        relation.setID( "has_part" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "disjoint_from" );
        relation.setID( "disjoint_from" );
        relationList.add( relation );
    }
    
    public void addTimedHumanDetails(String stage) {

    	OBOComponent obocomponent = new OBOComponent();
        obocomponent.setName( HUMAN_TIMED_NAME );
        obocomponent.setID( HUMAN_TIMED_ID );
        obocomponent.setNamespace( HUMAN_TIMED_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        //obocomponent.addChildOf( HUMAN_ID  );
        //obocomponent.addChildOfType( "IS_A" );
        
        if ( defaultroot ) {
        	
            obocomponentList.add( obocomponent );
        }
        
        // group obocomponents----------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( "Group term" );
        obocomponent.setID( "group_term" );
        obocomponent.setNamespace( "group_term" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // stage class------------------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( HUMAN_STAGE_UPPER );
        obocomponent.setID( "TS:0" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( stage );
        obocomponent.setID( stage );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( HUMAN_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );


        // new group class
        obocomponent = new OBOComponent();
        obocomponent.setName( "Tmp new group" );
        obocomponent.setID( "Tmp_new_group" );
        obocomponent.setNamespace( "new_group_namespace" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // partOf
        OBORelation relation = new OBORelation();
        relation.setName( "part_of" );
        relation.setID( "part_of" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // isA
        relation = new OBORelation();
        relation.setName( "is_a" );
        relation.setID( "is_a" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsFrom
        relation = new OBORelation();
        relation.setName( "develops_from" );
        relation.setID( "develops_from" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsIn
        relation = new OBORelation();
        relation.setName( "develops_in" );
        relation.setID( "develops_in" );
        relationList.add( relation );

        // AttachedTo
        relation = new OBORelation();
        relation.setName( "attached_to" );
        relation.setID( "attached_to" );
        relationList.add( relation );

        // LocatedIn
        relation = new OBORelation();
        relation.setName( "located_in" );
        relation.setID( "located_in" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "has_part" );
        relation.setID( "has_part" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "disjoint_from" );
        relation.setID( "disjoint_from" );
        relationList.add( relation );
    }
    
    public void addAbstractMouseDetails() {

    	OBOComponent obocomponent = new OBOComponent();
        obocomponent.setName( MOUSE_NAME );
        obocomponent.setID( MOUSE_ID );
        obocomponent.setNamespace( MOUSE_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        
        if ( defaultroot ) {
        	
            obocomponentList.add( obocomponent );
        }
        
        // group obocomponents----------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( "Group term" );
        obocomponent.setID( "group_term" );
        obocomponent.setNamespace( "group_term" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // stage class------------------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( MOUSE_STAGE_UPPER );
        obocomponent.setID( "TS:0" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS01" );
        obocomponent.setID( "TS01" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS02" );
        obocomponent.setID( "TS02" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS03" );
        obocomponent.setID( "TS03" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS04" );
        obocomponent.setID( "TS04" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS05" );
        obocomponent.setID( "TS05" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS06" );
        obocomponent.setID( "TS06" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS07" );
        obocomponent.setID( "TS07" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS08" );
        obocomponent.setID( "TS08" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS09" );
        obocomponent.setID( "TS09" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS10" );
        obocomponent.setID( "TS10" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS11" );
        obocomponent.setID( "TS11" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS12" );
        obocomponent.setID( "TS12" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS13" );
        obocomponent.setID( "TS13" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS14" );
        obocomponent.setID( "TS14" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS15" );
        obocomponent.setID( "TS15" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS16" );
        obocomponent.setID( "TS16" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS17" );
        obocomponent.setID( "TS17" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS18" );
        obocomponent.setID( "TS18" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS19" );
        obocomponent.setID( "TS19" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS20" );
        obocomponent.setID( "TS20" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS21" );
        obocomponent.setID( "TS21" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS22" );
        obocomponent.setID( "TS22" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS23" );
        obocomponent.setID( "TS23" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS24" );
        obocomponent.setID( "TS24" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS25" );
        obocomponent.setID( "TS25" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS26" );
        obocomponent.setID( "TS26" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS27" );
        obocomponent.setID( "TS27" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "TS28" );
        obocomponent.setID( "TS28" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        // new group class
        obocomponent = new OBOComponent();
        obocomponent.setName( "Tmp new group" );
        obocomponent.setID( "Tmp_new_group" );
        obocomponent.setNamespace( "new_group_namespace" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // starts at
        OBORelation relation = new OBORelation();
        relation.setName( "starts_at" );
        relation.setID( "starts_at" );
        relationList.add( relation );

        // ends at
        relation = new OBORelation();
        relation.setName( "ends_at" );
        relation.setID( "ends_at" );
        relationList.add( relation );

        // partOf
        relation = new OBORelation();
        relation.setName( "part_of" );
        relation.setID( "part_of" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // isA
        relation = new OBORelation();
        relation.setName( "is_a" );
        relation.setID( "is_a" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsFrom
        relation = new OBORelation();
        relation.setName( "develops_from" );
        relation.setID( "develops_from" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsIn
        relation = new OBORelation();
        relation.setName( "develops_in" );
        relation.setID( "develops_in" );
        relationList.add( relation );

        // AttachedTo
        relation = new OBORelation();
        relation.setName( "attached_to" );
        relation.setID( "attached_to" );
        relationList.add( relation );

        // LocatedIn
        relation = new OBORelation();
        relation.setName( "located_in" );
        relation.setID( "located_in" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "has_part" );
        relation.setID( "has_part" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "disjoint_from" );
        relation.setID( "disjoint_from" );
        relationList.add( relation );
    }
    
    public void addTimedMouseDetails(String stage) {

    	OBOComponent obocomponent = new OBOComponent();
        obocomponent.setName( MOUSE_TIMED_NAME );
        obocomponent.setID( MOUSE_TIMED_ID );
        obocomponent.setNamespace( MOUSE_TIMED_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        //obocomponent.addChildOf( MOUSE_ID  );
        //obocomponent.addChildOfType( "IS_A" );
        
        if ( defaultroot ) {
        	
            obocomponentList.add( obocomponent );
        }
        
        // group obocomponents----------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( "Group term" );
        obocomponent.setID( "group_term" );
        obocomponent.setNamespace( "group_term" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // stage class------------------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( MOUSE_STAGE_UPPER );
        obocomponent.setID( "TS:0" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( stage );
        obocomponent.setID( stage );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( MOUSE_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );


        // new group class
        obocomponent = new OBOComponent();
        obocomponent.setName( "Tmp new group" );
        obocomponent.setID( "Tmp_new_group" );
        obocomponent.setNamespace( "new_group_namespace" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // partOf
        OBORelation relation = new OBORelation();
        relation.setName( "part_of" );
        relation.setID( "part_of" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // isA
        relation = new OBORelation();
        relation.setName( "is_a" );
        relation.setID( "is_a" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsFrom
        relation = new OBORelation();
        relation.setName( "develops_from" );
        relation.setID( "develops_from" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsIn
        relation = new OBORelation();
        relation.setName( "develops_in" );
        relation.setID( "develops_in" );
        relationList.add( relation );

        // AttachedTo
        relation = new OBORelation();
        relation.setName( "attached_to" );
        relation.setID( "attached_to" );
        relationList.add( relation );

        // LocatedIn
        relation = new OBORelation();
        relation.setName( "located_in" );
        relation.setID( "located_in" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "has_part" );
        relation.setID( "has_part" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "disjoint_from" );
        relation.setID( "disjoint_from" );
        relationList.add( relation );
    }
    
    public void addAbstractChickDetails() {

    	OBOComponent obocomponent = new OBOComponent();
        obocomponent.setName( CHICK_NAME );
        obocomponent.setID( CHICK_ID );
        obocomponent.setNamespace( CHICK_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        
        if ( defaultroot ) {
        	
            obocomponentList.add( obocomponent );
        }
        
        // group obocomponents----------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( "Group term" );
        obocomponent.setID( "group_term" );
        obocomponent.setNamespace( "group_term" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // stage class------------------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( CHICK_STAGE_UPPER );
        obocomponent.setID( "HH:0" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-I" );
        obocomponent.setID( "EGK-I" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-II" );
        obocomponent.setID( "EGK-II" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-III" );
        obocomponent.setID( "EGK-III" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-IV" );
        obocomponent.setID( "EGK-IV" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-V" );
        obocomponent.setID( "EGK-V" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-VI" );
        obocomponent.setID( "EGK-VI" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-VII" );
        obocomponent.setID( "EGK-VII" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-VIII" );
        obocomponent.setID( "EGK-VIII" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-IX" );
        obocomponent.setID( "EGK-IX" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-X" );
        obocomponent.setID( "EGK-X" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-XI" );
        obocomponent.setID( "EGK-XI" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-XII" );
        obocomponent.setID( "EGK-XII" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-XIII" );
        obocomponent.setID( "EGK-XIII" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "EGK-XIV" );
        obocomponent.setID( "EGK-XIV" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH01" );
        obocomponent.setID( "HH01" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH02" );
        obocomponent.setID( "HH02" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH03" );
        obocomponent.setID( "HH03" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH04" );
        obocomponent.setID( "HH04" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH05" );
        obocomponent.setID( "HH05" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH06" );
        obocomponent.setID( "HH06" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH07" );
        obocomponent.setID( "HH07" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH08" );
        obocomponent.setID( "HH08" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH09" );
        obocomponent.setID( "HH09" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH10" );
        obocomponent.setID( "HH10" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH11" );
        obocomponent.setID( "HH11" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH12" );
        obocomponent.setID( "HH12" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH13" );
        obocomponent.setID( "HH13" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH14" );
        obocomponent.setID( "HH14" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH15" );
        obocomponent.setID( "HH15" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH16" );
        obocomponent.setID( "HH16" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH17" );
        obocomponent.setID( "HH17" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH18" );
        obocomponent.setID( "HH18" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH19" );
        obocomponent.setID( "HH19" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH20" );
        obocomponent.setID( "HH20" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH21" );
        obocomponent.setID( "HH21" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH22" );
        obocomponent.setID( "HH22" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH23" );
        obocomponent.setID( "HH23" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH24" );
        obocomponent.setID( "HH24" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH25" );
        obocomponent.setID( "HH25" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH26" );
        obocomponent.setID( "HH26" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH27" );
        obocomponent.setID( "HH27" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH28" );
        obocomponent.setID( "HH28" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH29" );
        obocomponent.setID( "HH29" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH30" );
        obocomponent.setID( "HH30" );
        obocomponent.setDBID( "-1" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH31" );
        obocomponent.setID( "HH31" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH32" );
        obocomponent.setID( "HH32" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH33" );
        obocomponent.setID( "HH33" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH34" );
        obocomponent.setID( "HH34" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH35" );
        obocomponent.setID( "HH35" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH36" );
        obocomponent.setID( "HH36" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH37" );
        obocomponent.setID( "HH37" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH38" );
        obocomponent.setID( "HH38" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH39" );
        obocomponent.setID( "HH39" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH40" );
        obocomponent.setID( "HH40" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH41" );
        obocomponent.setID( "HH41" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH42" );
        obocomponent.setID( "HH42" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH43" );
        obocomponent.setID( "HH43" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH44" );
        obocomponent.setID( "HH44" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH45" );
        obocomponent.setID( "HH45" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH46" );
        obocomponent.setID( "HH46" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH47" );
        obocomponent.setID( "HH47" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( "HH48" );
        obocomponent.setID( "HH48" );
        obocomponent.addChildOf( "HH:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );


        // new group class
        obocomponent = new OBOComponent();
        obocomponent.setName( "Tmp new group" );
        obocomponent.setID( "Tmp_new_group" );
        obocomponent.setNamespace( "new_group_namespace" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // starts at
        OBORelation relation = new OBORelation();
        relation.setName( "starts_at" );
        relation.setID( "starts_at" );
        relationList.add( relation );

        // ends at
        relation = new OBORelation();
        relation.setName( "ends_at" );
        relation.setID( "ends_at" );
        relationList.add( relation );

        // partOf
        relation = new OBORelation();
        relation.setName( "part_of" );
        relation.setID( "part_of" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // isA
        relation = new OBORelation();
        relation.setName( "is_a" );
        relation.setID( "is_a" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsFrom
        relation = new OBORelation();
        relation.setName( "develops_from" );
        relation.setID( "develops_from" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsIn
        relation = new OBORelation();
        relation.setName( "develops_in" );
        relation.setID( "develops_in" );
        relationList.add( relation );

        // AttachedTo
        relation = new OBORelation();
        relation.setName( "attached_to" );
        relation.setID( "attached_to" );
        relationList.add( relation );

        // LocatedIn
        relation = new OBORelation();
        relation.setName( "located_in" );
        relation.setID( "located_in" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "has_part" );
        relation.setID( "has_part" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "disjoint_from" );
        relation.setID( "disjoint_from" );
        relationList.add( relation );
    }
    
    public void addTimedChickDetails(String stage) {

    	OBOComponent obocomponent = new OBOComponent();
        obocomponent.setName( CHICK_TIMED_NAME );
        obocomponent.setID( CHICK_TIMED_ID );
        obocomponent.setNamespace( CHICK_TIMED_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        //obocomponent.addChildOf( CHICK_ID  );
        //obocomponent.addChildOfType( "IS_A" );
        
        if ( defaultroot ) {
        	
            obocomponentList.add( obocomponent );
        }
        
        // group obocomponents----------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( "Group term" );
        obocomponent.setID( "group_term" );
        obocomponent.setNamespace( "group_term" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // stage class------------------------------------------------------------------------------
        obocomponent = new OBOComponent();
        obocomponent.setName( CHICK_STAGE_UPPER );
        obocomponent.setID( "TS:0" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        obocomponent = new OBOComponent();
        obocomponent.setName( stage );
        obocomponent.setID( stage );
        obocomponent.addChildOf( "TS:0" );
        obocomponent.addChildOfType( "IS_A" );
        obocomponent.setNamespace( CHICK_STAGE_NAME_SPACE );
        obocomponentList.add( obocomponent );


        // new group class
        obocomponent = new OBOComponent();
        obocomponent.setName( "Tmp new group" );
        obocomponent.setID( "Tmp_new_group" );
        obocomponent.setNamespace( "new_group_namespace" );
        obocomponent.setDBID( "-1" );
        obocomponentList.add( obocomponent );

        // partOf
        OBORelation relation = new OBORelation();
        relation.setName( "part_of" );
        relation.setID( "part_of" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // isA
        relation = new OBORelation();
        relation.setName( "is_a" );
        relation.setID( "is_a" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsFrom
        relation = new OBORelation();
        relation.setName( "develops_from" );
        relation.setID( "develops_from" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // DevelopsIn
        relation = new OBORelation();
        relation.setName( "develops_in" );
        relation.setID( "develops_in" );
        relationList.add( relation );

        // AttachedTo
        relation = new OBORelation();
        relation.setName( "attached_to" );
        relation.setID( "attached_to" );
        relationList.add( relation );

        // LocatedIn
        relation = new OBORelation();
        relation.setName( "located_in" );
        relation.setID( "located_in" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "has_part" );
        relation.setID( "has_part" );
        relation.setTransitive( "true" );
        relationList.add( relation );

        // HasPart
        relation = new OBORelation();
        relation.setName( "disjoint_from" );
        relation.setID( "disjoint_from" );
        relationList.add( relation );
    }
}