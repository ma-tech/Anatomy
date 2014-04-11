/*
*----------------------------------------------------------------------------------------------
* Project:      DAOAnatomyRebuild
*
* Title:        UpdateDatabaseFromComponentsTables.java
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
* Description:  A Class that Reads an OBO File and Loads it into an existing Anatomy database;
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; February 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package routines.runnable;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import anatomy.TreeAnatomy;
import utility.Wrapper;
import obolayer.OBOFactory;
import obomodel.OBOComponent;
import daolayer.DAOFactory;
import routines.aggregated.ListOBOComponentsFromExistingDatabase;


public class RealisePathsFromDatabase {

	public static void run(DAOFactory daofactory, OBOFactory obofactory) throws Exception {
    	
	    Wrapper.printMessage("realisepathsfromdatabase.run", "***", obofactory.getMsgLevel());

        //import Database from dao.properties
	    ListOBOComponentsFromExistingDatabase importdatabase = new ListOBOComponentsFromExistingDatabase( daofactory, obofactory, true );
	    
	    TreeAnatomy treeanatomy = new TreeAnatomy(obofactory.getMsgLevel(), importdatabase.getTermList());

        System.out.println();
        System.out.println("All Paths to EMAPA:32765, inc. IS_As and PART_OFs");
        System.out.println("=================================================");

        //Vector<DefaultMutableTreeNode[]> defaultmutabletreenodesvector = treebuilder.getPathsTo("EMAPA:31865");
        Vector<DefaultMutableTreeNode[]> defaultmutabletreenodesvector = treeanatomy.getPathsToInAll("EMAPA:32765");
        
        System.out.println("defaultmutabletreenodesvector.size() = " + defaultmutabletreenodesvector.size());
        
        
        Iterator<DefaultMutableTreeNode[]> iteratorDefaultmutabletreenodesvector = defaultmutabletreenodesvector.iterator();
        
      	while (iteratorDefaultmutabletreenodesvector.hasNext()) {
      		
      		DefaultMutableTreeNode [] defaultmutabletreenodearray = iteratorDefaultmutabletreenodesvector.next();
      		 
            System.out.println("defaultmutabletreenodearray.length = " + defaultmutabletreenodearray.length);

            for (DefaultMutableTreeNode defaultmutabletreenode: defaultmutabletreenodearray){

          		Object nodeInfo = defaultmutabletreenode.getUserObject(); 
                
                if (nodeInfo instanceof OBOComponent){
                	
                	OBOComponent obocomponent = (OBOComponent) nodeInfo;
                	
                	System.out.println(obocomponent.getID() + " (" + obocomponent.getName() + ")");
                	//System.out.println(obocomponent.getID() + " : " + obocomponent.toString());
                }

            }
        }

        System.out.println();
        System.out.println("PART_OF Paths to EMAPA:32765 ONLY");
        System.out.println("=================================");

        Vector<DefaultMutableTreeNode[]> defaultmutabletreenodesvector2 = treeanatomy.getPathsToInPartOfs("EMAPA:32765");
        
        System.out.println("defaultmutabletreenodesvector2.size() = " + defaultmutabletreenodesvector2.size());
        
        
        Iterator<DefaultMutableTreeNode[]> iteratorDefaultmutabletreenodesvector2 = defaultmutabletreenodesvector2.iterator();
        
      	while (iteratorDefaultmutabletreenodesvector2.hasNext()) {
      		
      		DefaultMutableTreeNode [] defaultmutabletreenodearray = iteratorDefaultmutabletreenodesvector2.next();
      		 
            System.out.println("defaultmutabletreenodearray.length = " + defaultmutabletreenodearray.length);

            for (DefaultMutableTreeNode defaultmutabletreenode: defaultmutabletreenodearray){

          		Object nodeInfo = defaultmutabletreenode.getUserObject(); 
                
                if (nodeInfo instanceof OBOComponent){
                	
                	OBOComponent obocomponent = (OBOComponent) nodeInfo;
                	
                	System.out.println(obocomponent.getID() + " (" + obocomponent.getName() + ")");
                	//System.out.println(obocomponent.getID() + " : " + obocomponent.toString());
                }

            }
        }

	}
}