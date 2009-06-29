package backend;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author attila
 */
public class CheckChanges {

    private ArrayList < Component > oldTermList = null;
    private ArrayList < Relation > oldRelList = null;
    private ArrayList < Component > newTermList = null;
    private ArrayList < Relation > newRelList = null;
    private Component abstractClass, stageClass, groupClass;
    String stageNameID;
    int stageNameIDLength;

    public CheckChanges( ArrayList < Component > oldTermList, ArrayList < Relation > oldRelationList, ArrayList < Component > newTermList, ArrayList < Relation > newRelationList, Component abstractClass, Component stageClass, Component groupClass, String stageNameID ) {
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
            actTerm = newTermList.get(i); //maze: current component in newTermList
            actTermID = actTerm.getID(); //maze: emap id
            //maze: check if not root term
            if ( (!actTermID.equals(abstractClass.getID())) && (!actTermID.equals(stageClass.getID()))
                    && (!actTermID.equals(groupClass.getID())) && (!actTermID.substring(0,2).equals(stageNameID))) {

                // check start_at and ends_at relations
                //maze: check for missing stage relations
                if ( actTerm.getEndsAt().equals("") ) System.out.println(actTerm.getID()+": "+"missing ends_at relation!");
                if ( actTerm.getStartsAt().equals("") ) System.out.println(actTerm.getID()+": "+"missing starts_at relation!");
                if ( (!actTerm.getEndsAt().equals("")) && (!actTerm.getStartsAt().equals("")) && (Integer.parseInt(actTerm.getEndsAt().substring(stageNameIDLength)) < Integer.parseInt(actTerm.getStartsAt().substring(stageNameIDLength))) ) 
                    System.out.println(actTerm.getID()+": "+"ends_at < starts_at!");
                
                
            }// valid terms
            
            // group term
            if ( actTermID.equals(groupClass.getID()) ) {
                System.out.println("This is a group term.");
                
            }// if
            
        }// for i
        
    }
    
    
    
    
}
