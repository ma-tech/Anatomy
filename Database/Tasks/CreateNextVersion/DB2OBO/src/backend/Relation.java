/*
################################################################################
# Project:      Anatomy
#
# Title:        Relation.java
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

public class Relation {
    private String id;
    private String name;
    private String transitive;
    
    public Relation() {
        this.id = "";
        this.name = "";
        this.transitive = "";
    }
    
    public void setID( String id ) {
        this.id = id;
    }
    
    public void setName( String name ) {
        this.name = name;
    }

    public void setTransitive( String transitive ) {
        this.transitive = transitive;
    }

    public String getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }

    public String getTransitive() {
        return this.transitive;
    }
    
}
