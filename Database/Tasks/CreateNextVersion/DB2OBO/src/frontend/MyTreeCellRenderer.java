/*
################################################################################
# Project:      Anatomy
#
# Title:        MyTreeCellRenderer.java
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

package frontend;

import backend.*;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;


public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public java.awt.Component getTreeCellRendererComponent(JTree tree, 
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        Component c = 
                super.getTreeCellRendererComponent(tree,
                    value, sel, expanded, leaf, row, hasFocus);
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object user_object = node.getUserObject();
        
        /*Map<TextAttribute, Object> map =
             new Hashtable<TextAttribute, Object>();
        map.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        java.awt.Font font = c.getFont();
        c.setFont(font);*/
        
       ;
        
        if(user_object instanceof backend.Component) {
            
            backend.Component comp = (backend.Component) user_object;

            if ( comp.getStrChangeStatus().equals("NEW") ||
                 comp.getStrChangeStatus().equals("DELETED") ||
                 comp.getStrChangeStatus().equals("CHANGED") ) {
                c.setForeground( new Color( 0, 140, 0 ));
            }

            if ( comp.getFlagLifeTime() ) {
                c.setForeground(Color.BLUE);
            }

            if ( comp.getFlagMissingRel() ) {
                c.setForeground(Color.RED);
            }
            
            if ( comp.getStrChangeStatus().equals("DELETED") ) {
                //java.awt.Font font = c.getFont();
                //font.deriveFont( map );
                //c.setFont(font);
                
                //c.setForeground(Color.MAGENTA); //set to strikethrough
            } 
        }
        
        return c;
    }
}
