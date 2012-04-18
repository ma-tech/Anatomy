/*
################################################################################
# Project:      Anatomy
#
# Title:        CheckChanges.java
#
# Date:         2008
#
# Author:       MeiSze Lam and Attila Gyenesi
#
# Copyright:    2009 Medical Research Council, UK.
#               All rights reserved.
#
# Address:      MRC Human Genetics Unit,
#               Western General Hospital,
#               Edinburgh, EH4 2XU, UK.
#
# Version: 1
#
# Maintenance:  Log changes below, with most recent at top of list.
#
# Who; When; What;
#
# Mike Wicks; September 2010; Tidy up and Document
#
################################################################################
*/

package backend;

import java.util.ArrayList;

public class CheckChanges {

    private ArrayList < Component > oldTermList = null;
    private ArrayList < Relation > oldRelList = null;
    private ArrayList < Component > newTermList = null;
    private ArrayList < Relation > newRelList = null;
    private Component abstractClass, stageClass, groupClass;
    String stageNameID;
    int stageNameIDLength;

    public CheckChanges( ArrayList < Component > oldTermList,
            ArrayList < Relation > oldRelationList,
            ArrayList < Component > newTermList,
            ArrayList < Relation > newRelationList,
            Component abstractClass,
            Component stageClass,
            Component groupClass,
            String stageNameID ) {

        this.oldTermList = oldTermList;
        this.newTermList = newTermList;
        this.oldRelList = oldRelationList;
        this.newRelList = newRelationList;
        this.abstractClass = abstractClass;
        this.stageClass = stageClass;
        this.groupClass = groupClass;
        this.stageNameID = stageNameID;
        this.stageNameIDLength = this.stageNameID.length();
        
        Component actTerm;
        String actTermID;

        for (int i=0; i<newTermList.size(); i++) {
            //maze: current component in newTermList
            actTerm = newTermList.get(i);
            //maze: emap id
            actTermID = actTerm.getID();
            //maze: check if not root term
            if ( ( !actTermID.equals(abstractClass.getID()) ) &&
                 ( !actTermID.equals(stageClass.getID()) ) &&
                 ( !actTermID.equals(groupClass.getID()) ) &&
                 ( !actTermID.substring(0,2).equals(stageNameID)) ) {

                // check start_at and ends_at relations
                //maze: check for missing stage relations
                if ( actTerm.getEndsAt() == -1  ) {
                    System.out.println(actTerm.getID() + ": " +
                            "missing ends_at relation!");
                }

                if ( actTerm.getStartsAt() == -1 ) {
                    System.out.println(actTerm.getID() + ": " +
                            "missing starts_at relation!");
                }

                if ( (actTerm.getEndsAt() != -1) &&
                     (actTerm.getStartsAt() !=  -1 ) &&
                     (actTerm.getEndsAt() < actTerm.getStartsAt() ) ) {
                    System.out.println(actTerm.getID() + ": " +
                            "ends_at < starts_at!");
                }
            }// valid terms
            // group term
            if ( actTermID.equals(groupClass.getID()) ) {
                System.out.println("This is a group term.");
            }// if
        }// for i
    }
}
