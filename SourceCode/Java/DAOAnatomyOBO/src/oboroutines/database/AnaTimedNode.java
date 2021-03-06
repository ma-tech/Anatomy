/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyOBO
*
* Title:        AnaTimedNode.java
*
* Date:         2012
*
* Author:       Mike Wicks
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
* Description:  A Wrapper Class for the Table ANA_TIMED_NODE;
* 
*                Constructor requires a DAOFactory Object;
*                 Pass the Class Methods: a List of OBOComponents; CalledFrom String
*               
*               Methods:
*                1. insertANA_TIMED_NODE
*                2. createTimeComponents
*                3. deleteANA_TIMED_NODE
*                4. updateANA_TIMED_NODE
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; November 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package oboroutines.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import utility.Wrapper;
import utility.ObjectConverter;
import utility.MySQLDateTime;

import daointerface.LogDAO;
import daointerface.StageDAO;
import daointerface.ThingDAO;
import daointerface.TimedNodeDAO;
import daointerface.TimedIdentifierDAO;
import daointerface.VersionDAO;
import daointerface.JOINTimedNodeStageDAO;

import daolayer.DAOException;
import daolayer.DAOFactory;

import daomodel.Log;
import daomodel.Stage;
import daomodel.Thing;
import daomodel.TimedNode;
import daomodel.TimedIdentifier;
import daomodel.Version;
import daomodel.JOINTimedNodeStage;

import obomodel.OBOComponent;

import oboroutines.database.AnaObject;


public class AnaTimedNode {
	// Properties ---------------------------------------------------------------------------------
    private DAOFactory daofactory; 

    //check whether was processed all the way
    private boolean processed;
    
    //Data Access Objects (DAOs)
    private TimedNodeDAO timednodeDAO;
    private TimedIdentifierDAO timedidentifierDAO;
    private JOINTimedNodeStageDAO jointimednodestageDAO;
    private ThingDAO thingDAO;
    private StageDAO stageDAO;
    private VersionDAO versionDAO;
    private LogDAO logDAO;
    
    private long longLOG_VERSION_FK;
    
    //input Synonym list 
    private ArrayList<TimedNode> timednodeList;
   


    // Constructors -------------------------------------------------------------------------------
    public AnaTimedNode() {
    	
    }

    public AnaTimedNode( DAOFactory daofactory ) {
    	
    	try {
    		
            this.daofactory = daofactory;

            Wrapper.printMessage("anatimednode.constructor", "***", this.daofactory.getMsgLevel());

        	this.versionDAO = daofactory.getDAOImpl(VersionDAO.class);
        	this.logDAO = daofactory.getDAOImpl(LogDAO.class);
        	this.thingDAO = daofactory.getDAOImpl(ThingDAO.class);
        	this.timednodeDAO = daofactory.getDAOImpl(TimedNodeDAO.class);
        	this.timedidentifierDAO = daofactory.getDAOImpl(TimedIdentifierDAO.class);
        	this.jointimednodestageDAO = daofactory.getDAOImpl(JOINTimedNodeStageDAO.class);
        	this.stageDAO = daofactory.getDAOImpl(StageDAO.class);
        	
        	Version version = versionDAO.findMostRecent();
            this.longLOG_VERSION_FK = version.getOid();
            
            this.timednodeList = new ArrayList<TimedNode>();

        	setProcessed( true );
    	}

        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
    }
    
    
    //  Insert new rows into ANA_TIMED_NODE
    public boolean insertANA_TIMED_NODE( ArrayList<OBOComponent> newTermList, 
    		boolean generateIdentifiers, 
    		String calledFrom, 
    		String strSpecies) throws Exception {

        Wrapper.printMessage("anatimednode.insertANA_TIMED_NODE : " + calledFrom, "***", this.daofactory.getMsgLevel());
        
        //PrintStream original = System.out;
        
        //System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("/Users/mwicks/Desktop/output.txt"))));
    		
        OBOComponent component;
        boolean flagInsert = false;
    	long longCurrentPublicID  = 0;
    	
        this.timednodeList.clear();
    	
        try {
        	
        	if ( newTermList.size() > 0 ) {
        		
                //INSERT timed component obj_oids into ANA_OBJECT
                AnaObject anaobject = new AnaObject( this.daofactory);
                
                if ( !anaobject.getMaxPublicId() ) {

              	   throw new DatabaseException("anatimednode.insertANA_TIMED_NODE : getMaxPublicId");
                }

                longCurrentPublicID = anaobject.getCurrentMaxPublicId();
                
                //System.out.println("anaobject.getCurrentMaxPublicId() = " + anaobject.getCurrentMaxPublicId());
                //System.out.println("longCurrentPublicID = " + longCurrentPublicID);
        	}
             
            //create timed components in ANA_OBJECT
            ArrayList<OBOComponent> timedComps = new ArrayList<OBOComponent>();
            
            for ( int i = 0; i< newTermList.size(); i++) {
         	   
                component = newTermList.get(i);

                //System.out.println("component.toString() = " + component.toString());
                //System.out.println("component.getCheckComments() = " + component.getCheckComments());

                if ( ( component.commentsContain("Relation: ends_at -- Missing ends at stage - OBOComponent's stage range cannot be determined.") ) ||
                     ( component.commentsContain("Relation: starts_at -- Missing starts at stage - OBOComponent's stage range cannot be determined.") ) ||
                     ( component.commentsContain("Relation: starts_at, ends_at -- Ends at stage earlier than starts at stage.") ) ||
                     ( component.commentsContain("Relation: Starts At - More than one Start Stage!") ) ||
                     ( component.commentsContain("Relation: Ends At - More than one End Stage!") ) ||
                     ( component.commentsContain("Relation: starts_at, ends_at -- Stages are out of range!") ) ) {
             	   
                    flagInsert = false; 
                    //System.out.println("A - NO INSERT!");
                }
                else {
             	   
                    flagInsert = true; 
                    //System.out.println("A - YES, INSERT!");
                }

                if ( ( component.getStatusChange().equals("INSERT") ) ) {

                	flagInsert = true; 
                    //System.out.println("B - YES, INSERT!");
                }
                else {
                
                	flagInsert = false; 
                	//System.out.println("B - NO INSERT!");
                }

                if (flagInsert) {
             	   
                    //longCurrentPublicID = longCurrentPublicID + 1;

                    //make a time component record for each stage
                    for (int j = ObjectConverter.convert(component.getStartSequence(), Integer.class); j <= component.getEndSequence(); j++ ) {

                        OBOComponent timedComponent = new OBOComponent();
                        		
                        timedComponent.setNamespace( component.getDBID() ); 
                        //current component
                        timedComponent.setStartSequence(j, strSpecies);
                        timedComponent.setEndSequence(j, strSpecies);
                        
                        //System.out.println("timedComponent.getStart() = " + timedComponent.getStart());
                        //System.out.println("timedComponent.getEnd() = " + timedComponent.getEnd());

                        char padChar = '0';
                        
                        longCurrentPublicID = longCurrentPublicID + 1;

                    	if ( generateIdentifiers ) {
                    		
                            if (strSpecies.equals("mouse")) {
                          	   
                                timedComponent.setID( "EMAP:" + longCurrentPublicID );
                                timedComponent.setDisplayId( "EMAP:" + utility.StringPad.pad(longCurrentPublicID, 7, padChar) );
                            }
                            else if (strSpecies.equals("human")) {
                         	   
                                timedComponent.setID( "EHDA:" + longCurrentPublicID );
                                timedComponent.setDisplayId( "EHDA:" + utility.StringPad.pad(longCurrentPublicID, 7, padChar) );
                            }
                            else if (strSpecies.equals("chick")) {
                         	   
                                timedComponent.setID( "ECAP:" + longCurrentPublicID );
                                timedComponent.setDisplayId( "ECAP:" + utility.StringPad.pad(longCurrentPublicID, 7, padChar) );
                            }
                            else {
                         	   
                                Wrapper.printMessage("anatimednode.insertANA_TIMED_NODE : " + calledFrom + ";" + " UNKNOWN Species Value = " + strSpecies, "*", this.daofactory.getMsgLevel());
                            }
                    	}
                    	else {
                    		
                            if (strSpecies.equals("mouse")) {

                            	timedComponent.setID( "EMAPT:" + component.getID().substring(6, 11) + timedComponent.getStart().substring(2, 4) );
                                timedComponent.setDisplayId( "EMAPT:00" + component.getID().substring(6, 11) + timedComponent.getStart().substring(2, 4) );
                            }
                            else if (strSpecies.equals("human")) {
                         	   
                        		String strPrefix = "EHDAT:";
                        		String strDigit = component.getID().substring(6);

                            	String strANO_PUBLIC_ID = strPrefix + utility.StringPad.pad(strDigit, 5, '0');
                        		String strANO_DISPLAY_ID = strPrefix + utility.StringPad.pad(strDigit, 7, '0');

                    			strANO_PUBLIC_ID = strANO_PUBLIC_ID + timedComponent.getStart().substring(2);
                    			strANO_DISPLAY_ID = strANO_DISPLAY_ID + timedComponent.getStart().substring(2);
                        		
                            	timedComponent.setID( strANO_PUBLIC_ID );
                                timedComponent.setDisplayId( strANO_DISPLAY_ID );

                                //System.out.println("timedComponent.getID()        = " + timedComponent.getID());
                                //System.out.println("timedComponent.getDisplayId() = " + timedComponent.getDisplayId());

                            }
                            else if (strSpecies.equals("chick")) {
                         	   
                            	timedComponent.setID( "ECAPT:" + component.getID().substring(6, 11) + timedComponent.getStart().substring(2, 4) );
                                timedComponent.setDisplayId( "ECAPT:00" + component.getID().substring(6, 11) + timedComponent.getStart().substring(2, 4) );
                            }
                            else {
                         	   
                                Wrapper.printMessage("anatimednode.insertANA_TIMED_NODE : " + calledFrom + ";" + " UNKNOWN Species Value = " + strSpecies, "*", this.daofactory.getMsgLevel());
                            }
                            
                            //System.out.println("timedComponent.getID() = " + timedComponent.getID());
                            //System.out.println("timedComponent.getDisplayId() = " + timedComponent.getDisplayId());
                    	}

                        timedComps.add(timedComponent);
                    }
                }
            }
        
            if ( !timedComps.isEmpty() ) {
            	
                //INSERT timed component obj_oids into ANA_OBJECT
                AnaObject anaobject = new AnaObject( this.daofactory);
                
                if ( !anaobject.insertANA_OBJECT(timedComps, "ANA_TIMED_NODE") ) {

               	   throw new DatabaseException("anatimednode.insertANA_TIMED_NODE : insertANA_OBJECT:ANA_TIMED_NODE");
                }

                long longPrevNode = 0;
                long intCompieStage = 0;

                for (int k = 0; k< timedComps.size(); k++) {
                	
                    component = timedComps.get(k);

                    //prepare values
                    long longATN_OID = ObjectConverter.convert(component.getDBID(), Long.class);
                    long longATN_NODE_FK = ObjectConverter.convert(component.getNamespace(), Long.class);
                    long longATN_STAGE_FK = 0;

                    //System.out.println("component.getStart() = " + component.getStart());
                    //System.out.println("component.getEnd() = " + component.getEnd());

                    if ( component.getStart().equals( component.getEnd() ) ) {
                    	
                        Stage stage = this.stageDAO.findByName( component.getStart() );
                        
                        longATN_STAGE_FK = stage.getOid();
                        
                        //System.out.println("intCompieStage = " + intCompieStage);
                        //System.out.println("stage.toString() = " + stage.toString());
                    }
                    else {
                    	
                        if (longPrevNode != longATN_NODE_FK) {
                        	
                            intCompieStage = component.getStartSequence();
                        }
                        else {
                        	
                            intCompieStage++;
                        }
                        
                        Stage stage = this.stageDAO.findBySequence(ObjectConverter.convert(intCompieStage, Long.class) );
                        
                        longATN_STAGE_FK = stage.getOid();
                        	
                        //System.out.println("intCompieStage = " + intCompieStage);
                        //System.out.println("stage.toString() = " + stage.toString());
                    }
                    
                    String strATN_PUBLIC_ID = component.getID();
                    String strATN_DISPLAY_ID = component.getDisplayId();
                    
                    TimedNode timednode = new TimedNode( longATN_OID, longATN_NODE_FK, longATN_STAGE_FK, "", strATN_PUBLIC_ID, strATN_DISPLAY_ID );
                    
                    //System.out.println("timednode.toString() = " + timednode.toString());

                	if ( !logANA_TIMED_NODE( timednode, calledFrom ) ) {
                		
                    	throw new DatabaseException("anatimednode.insertANA_TIMED_NODE : logANA_TIMED_NODE");
                	}
                	
                    this.timednodeList.add(timednode);
                    
                    this.timednodeDAO.create(timednode);

                    longPrevNode = longATN_NODE_FK;
                }
                
              	// Update ANA_OBJECT
               if ( !anaobject.updateANA_OBJECTinsertANA_TIMED_NODE(this.timednodeList) ) {

             	   throw new DatabaseException("ananode.insertANA_TIMED_NODE : updateANA_OBJECTinsertANA_TIMED_NODE");
               }
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
            dao.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }
        
        //System.setOut(original);
        
        return isProcessed();
    }
    
    
    //  Delete rows from ANA_TIMED_NODE 
    public boolean deleteANA_TIMED_NODE( ArrayList<OBOComponent> deleteTimedComponents, 
    		String calledFrom ) throws Exception {

        Wrapper.printMessage("anatimednode.deleteANA_TIMED_NODE : " + calledFrom, "***", this.daofactory.getMsgLevel());
        	
        ArrayList<OBOComponent> logDeleteTimedComponents = new ArrayList<OBOComponent>();
        
        try {
        	
            if ( !deleteTimedComponents.isEmpty() ) {

                for ( OBOComponent component: deleteTimedComponents ) {
            		
                    //System.out.println("component.toString() = " + component.toString());

                    if ( component.getStatusChange().equals("DELETE") ) {
                    	
                    	logDeleteTimedComponents.add(component);
                    	
                        ArrayList<JOINTimedNodeStage> jointimednodestages = 
                        		(ArrayList<JOINTimedNodeStage>) jointimednodestageDAO.listAllByNodeFkOrderByStageName( ObjectConverter.convert(component.getDBID(), Long.class) );
                		
                        Iterator<JOINTimedNodeStage> iteratorJointimednodestages = jointimednodestages.iterator();
                        
                        while ( iteratorJointimednodestages.hasNext() ) {
                        	
                        	JOINTimedNodeStage jointimednodestage = iteratorJointimednodestages.next();

                            //System.out.println("jointimednodestage.toString() = " + jointimednodestage.toString());

                            if ( ( jointimednodestage.getName().equals(component.getStart()) &&
                            	jointimednodestage.getName().equals(component.getEnd()) ) || 
                            	( component.getStart().equals(component.getStartStageForSpecies(jointimednodestage.getSpeciesFK())) &&
                            	component.getEnd().equals(component.getEndStageForSpecies(jointimednodestage.getSpeciesFK())) )) {
                            	
                                TimedNode timednode = timednodeDAO.findByOid(jointimednodestage.getOidTimedNode());

                                if ( timednode != null ) {
                                	
                                    //System.out.println("DELETING Timed Node! timednode.toString() = " + timednode.toString());

                                    Thing thing = thingDAO.findByOid(jointimednodestage.getOidTimedNode()); 

                                	if ( !logANA_TIMED_NODE( timednode, calledFrom ) ) {
                                		
                                    	throw new DatabaseException("anatimednode.deleteANA_TIMED_NODE : logANA_TIMED_NODE");
                                	}
                                    
                                    thingDAO.delete(thing);

                                    timednodeDAO.delete(timednode);
                                    
                                    TimedIdentifier timedidentifier = timedidentifierDAO.findByOid(jointimednodestage.getOidTimedNode());

                                    if ( timedidentifier != null ) {
                                    	
                                        //System.out.println("DELETING Timed Identifier! timedidentifier.toString() = " + timedidentifier.toString());
                                    	
                                    	timedidentifierDAO.delete(timedidentifier);
                                    }
                                }
                            }
                        }
                    }
            	}
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        }
        
        return isProcessed();
    }
    
    
    //  update rows in ANA_TIMED_NODE
	public boolean updateANA_TIMED_NODE( ArrayList<OBOComponent> changedTimedTermList, 
			boolean generateIdentifiers, 
			String calledFrom, 
			String strSpecies) throws Exception {

        Wrapper.printMessage("anatimednode.updateANA_TIMED_NODE : " + calledFrom, "***", this.daofactory.getMsgLevel());
        	
        try {
        	
            //System.out.println("changedTimedTermList.size() = " + changedTimedTermList.size());

            //insert time components in ANA_TIMED_NODE
            if ( !insertANA_TIMED_NODE( changedTimedTermList, generateIdentifiers, "INSERT", strSpecies) ) {

            	throw new DatabaseException("anatimednode.updateANA_TIMED_NODE : insertANA_TIMED_NODE:INSERT");
            }
            
            //System.out.println("changedTimedTermList.size()" + changedTimedTermList.size());

            //delete time components in ANA_TIMED_NODE
            if ( !deleteANA_TIMED_NODE( changedTimedTermList, "DELETE" ) ) {

            	throw new DatabaseException("anatimednode.updateANA_TIMED_NODE : deleteANA_TIMED_NODE:DELETE");
            }
        }
        catch ( DatabaseException dbex ) {
        	
        	setProcessed( false );
        	dbex.printStackTrace();
        }
        catch ( Exception ex ) {
        	
        	setProcessed( false );
            ex.printStackTrace();
        }

     	return isProcessed();
    }
	
	
    //  Insert into ANA_LOG for ANA_TIMED_NODE Insertions or Deletions
    public boolean logANA_TIMED_NODE( TimedNode timednode, 
    		String calledFrom ) throws Exception {

        Wrapper.printMessage("anatimednode.logANA_TIMED_NODE : " + calledFrom, "***", this.daofactory.getMsgLevel());
        	
        //System.out.println("timednode.toString() = " + timednode.toString());

        HashMap<String, String> atnOldValues = new HashMap<String, String>();
        
        long longLogLoggedOID = 0;
        long longLogOID = 0;
        
        //ANA_TIMED_NODE columns
        Vector<String> vATNcolumns = new Vector<String>();
        vATNcolumns.add("ATN_OID");
        vATNcolumns.add("ATN_STAGE_FK");
        vATNcolumns.add("ATN_STAGE_MODIFIER_FK");
        vATNcolumns.add("ATN_PUBLIC_ID");
        vATNcolumns.add("ATN_NODE_FK");
        vATNcolumns.add("ATN_DISPLAY_ID");
        
        //column values for insertion into ANA_LOG
        String strLOG_COLUMN_NAME = "";
        String strLOG_OLD_VALUE = "";
        
        //version_oid should be very first obj_oid created for easy tracing
        String strLOG_COMMENTS = "";
        String strLOG_DATETIME = MySQLDateTime.now();

        if ( calledFrom.equals("INSERT") ) {
        	
        	strLOG_COMMENTS = "INSERT TimedNodes";
        }
        else if ( calledFrom.equals("DELETE") ) {
        	
        	strLOG_COMMENTS = "DELETE TimedNodes";
        }
        else if ( calledFrom.equals("UPDATE") ) {
        	
        	strLOG_COMMENTS = "UDPATE TimedNodes";
        }
        else {
        	
        	strLOG_COMMENTS = "UNKNOWN REASON for TimedNodes INSERT into ANA_LOG";
        }
        
        try {
        	
        	//get max log_oid from new database
        	longLogOID = utility.ObjectConverter.convert(logDAO.maximumOid(), Long.class);

            //clear HashMap atnOldValues
            atnOldValues.clear();
            
            atnOldValues.put( "ATN_OID", utility.ObjectConverter.convert(timednode.getOid(), String.class) );
            atnOldValues.put( "ATN_STAGE_FK", utility.ObjectConverter.convert(timednode.getStageFK(), String.class) );
            atnOldValues.put( "ATN_STAGE_MODIFIER_FK", timednode.getStageModifierFK() );
            atnOldValues.put( "ATN_PUBLIC_ID", timednode.getPublicId() );
            atnOldValues.put( "ATN_NODE_FK", utility.ObjectConverter.convert(timednode.getNodeFK(), String.class) );
            atnOldValues.put( "ATN_DISPLAY_ID", timednode.getDisplayId() );
            
            longLogLoggedOID = timednode.getOid();

            for (String columnName: vATNcolumns) {
              	
                longLogOID++;
                strLOG_COLUMN_NAME = columnName;
                strLOG_OLD_VALUE = atnOldValues.get(columnName);

                Log log = new Log(longLogOID, longLogLoggedOID, this.longLOG_VERSION_FK, strLOG_COLUMN_NAME, strLOG_OLD_VALUE, strLOG_COMMENTS, strLOG_DATETIME, "ANA_TIMED_NODE");
               
                //System.out.println("log.toString() = " + log.toString());
                
                logDAO.create(log);
            }
        }
        catch ( DAOException dao ) {
        	
        	setProcessed( false );
        	dao.printStackTrace();
        } 
        catch ( Exception ex ) {
        	
        	setProcessed( false );
        	ex.printStackTrace();
        } 

        return isProcessed();
    }

    
    // Getters ------------------------------------------------------------------------------------
    public boolean isProcessed() {
        return this.processed;
    }
    // Setters ------------------------------------------------------------------------------------
    public void setProcessed( boolean processed ) {
        this.processed = processed;
    }
}
