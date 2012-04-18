/*
################################################################################
# Project:      Anatomy
#
# Title:        MainGUI.java
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
#                             Add a properties file in which all settings are
#                             saved on Exit
#
################################################################################
*/

package frontend;

import backend.*;

import java.awt.Color;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;


public class MainGUI extends javax.swing.JFrame {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String START_URL = "jdbc:mysql://";
    private Connection connection; 
    private String species = "mouse";
    private String project = "GUDMAP";
    private String filetype = "Starts and Ends";
    private String stage = "TS01";
    private String rangestart = "TS01";
    private String rangeend = "TS28";
    private Integer rangestartint = 0;
    private Integer rangeendint = 27;
    private boolean defaultroot = true;

    private ArrayList < Component > expTermList = null;
    private ArrayList < Relation > expRelationList = null;

    private Component abstractClass,
                      stageClass,
                      groupClass,
                      groupTermClass,
                      stageAnatomyClass;
    
    private ArrayList < Component > parseOldTermList = null;
    private ArrayList < Component > parseNewTermList = null;
    
    //treeCurrent object
    private TreeBuilder propTreebuilder;

    //treeReferenced object
    private TreeBuilder refTreebuilder;

    //checker attached to treeCurrent
    private CheckComponents propCheckComponents; 
    private GenerateSQL genie = null;
    
    public Vector<String> vUserRoots = new Vector();
    
    public static final int CHECK_CHANGES_ONLY = 0;
    
    public static final int CHECK_RED_RULES = 1;

    //note: blue rules cannot be checked without checking red rules first!
    public static final int CHECK_RED_BLUE_RULES = 2; 

    public static final int CHECK_CHANGES_AND_RULES = 3;

    //preferences file
    private String fileName = ".db2ono";

    //private File iniFile;
    private BufferedWriter iniFile;
    private BufferedReader outFile;

    private String strURL = "";

    //fields for saving
    // Initial Values
    private String iniSavedByTF = "Your Name";
    private String iniRemarkTA
            = "Any relevant remarks on this exported OBO File";
    private String iniExportOBOFileTF = "Export_OBO_File.obo";
    private String iniExportTextFileTF = "Export_Tree_report.txt";

    private String iniTxtCurrentFileTF = "Import_OBO_file.obo";
    private String iniTxtReportTF = "Proposed_SQL_Report.sql";
    private String iniTxtEditorTF = "Proposed_Editor_Report.pdf";

    private String iniMouseDBHostNameTF = "localhost";
    private String iniMouseDBPortTF = "3306";
    private String iniMouseDBDbNameTF = "";
    private String iniMouseDBUserNameTF = "";

    private String iniMouseAbstractClassNameTF = "Abstract anatomy";
    private String iniMouseAbstractClassIDTF = "EMAPA:0";
    private String iniMouseAbstractClassNamespaceTF = "abstract_anatomy";

    private String iniMouseStageClassNameTF = "Theiler stage";
    private String iniMouseStageClassIDTF = "TS:0";
    private String iniMouseStageClassNamespaceTF = "theiler_stage";

    private String iniMouseGroupClassNameTF = "Tmp new group";
    private String iniMouseGroupClassIDTF = "Tmp_new_group";
    private String iniMouseGroupClassNamespaceTF = "new_group_namespace";

    private String iniMouseGroupTermClassNameTF = "Group term";
    private String iniMouseGroupTermClassIDTF = "group_term";
    private String iniMouseGroupTermClassNamespaceTF = "group_term";

    private String iniMouseFormatVersionTF = "1.2";
    private String iniMouseDefaultNamespaceTF = "mouse_ontology";


    public MainGUI() {
        
        //initialise gui components
        initComponents();

        //change colour of fonts for tree nodes
        treeCurrent.setCellRenderer(new MyTreeCellRenderer()); 
        treeReferenced.setCellRenderer(new MyTreeCellRenderer());

        //locate the frame at the center of desktop
        setLocationRelativeTo(null); 

        //set default values for Radiobuttons + default species + filetype
        this.mouseRB.setSelected(true);
        this.startendRB.setSelected(true);

        this.mainTabbedPanel.setSelectedIndex(2);

        try{
            //check filepath exists
            File file = new File(this.fileName);

            if (file.exists()) {
                //initialise BufferedWriter for report
                //System.out.println("Read in File");
                this.outFile =
                        new BufferedReader(new FileReader(this.fileName));

                this.readIniRecords();

                this.outFile.close();
            }
            else {
                //Fill in default Text Values in Fields
                savedByTF.setText(this.iniSavedByTF);
                remarkTA.setText(this.iniRemarkTA);
                exportOBOFileTF.setText(this.iniExportOBOFileTF);
                exportTextFileTF.setText(this.iniExportTextFileTF);

                txtCurrentFile.setText(this.iniTxtCurrentFileTF);
                txtReport.setText(this.iniTxtReportTF);
                txtEditor.setText(this.iniTxtEditorTF);

                mouseDBHostNameTF.setText(this.iniMouseDBHostNameTF);
                mouseDBPortTF.setText(this.iniMouseDBPortTF);
                mouseDBDbNameTF.setText(this.iniMouseDBDbNameTF);
                mouseDBUserNameTF.setText(this.iniMouseDBUserNameTF);
                mouseDBPasswordPF.setText("");

                mouseAbstractClassNameTF.setText(
                        this.iniMouseAbstractClassNameTF);
                mouseAbstractClassIDTF.setText(
                        this.iniMouseAbstractClassIDTF);
                mouseAbstractClassNamespaceTF.setText(
                        this.iniMouseAbstractClassNamespaceTF);

                mouseStageClassNameTF.setText(
                        this.iniMouseStageClassNameTF);
                mouseStageClassIDTF.setText(
                        this.iniMouseStageClassIDTF);
                mouseStageClassNamespaceTF.setText(
                        this.iniMouseStageClassNamespaceTF);

                mouseGroupClassNameTF.setText(
                        this.iniMouseGroupClassNameTF);
                mouseGroupClassIDTF.setText(
                        this.iniMouseGroupClassIDTF);
                mouseGroupClassNamespaceTF.setText(
                        this.iniMouseGroupClassNamespaceTF);

                mouseGroupTermClassNameTF.setText(
                        this.iniMouseGroupTermClassNameTF);
                mouseGroupTermClassIDTF.setText(
                        this.iniMouseGroupTermClassIDTF);
                mouseGroupTermClassNamespaceTF.setText(
                        this.iniMouseGroupTermClassNamespaceTF);

                mouseFormatVersionTF.setText(
                        this.iniMouseFormatVersionTF);
                mouseDefaultNamespaceTF.setText(
                        this.iniMouseDefaultNamespaceTF);


            }
            //make root components for classes
            this.makeRootComponents();

        }
        catch(IOException io) {
            io.printStackTrace();
        }
        

        //load default reference tree
        //loadDefaultReferenceTree();
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        specieButtonGroup = new javax.swing.ButtonGroup();
        formatButtonGroup = new javax.swing.ButtonGroup();
        rootButtonGroup = new javax.swing.ButtonGroup();
        mainTabbedPanel = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        savedByTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        exportOBOFileTF = new javax.swing.JTextField();
        exportBrowseButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        remarkTA = new javax.swing.JTextArea();
        exportButton = new javax.swing.JButton();
        expTermNoLabel = new javax.swing.JLabel();
        cmdGenerateTreeReport = new javax.swing.JButton();
        exportTextFileTF = new javax.swing.JTextField();
        exportTextBrowseButton = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        mouseRB = new javax.swing.JRadioButton();
        humanRB = new javax.swing.JRadioButton();
        chickRB = new javax.swing.JRadioButton();
        jPanel36 = new javax.swing.JPanel();
        timedcompRB = new javax.swing.JRadioButton();
        startendRB = new javax.swing.JRadioButton();
        jPanel37 = new javax.swing.JPanel();
        rbDefaultRoot = new javax.swing.JRadioButton();
        rbSelectedComponents = new javax.swing.JRadioButton();
        cboAddedRoots = new javax.swing.JComboBox();
        cmdEditList = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        cboProject = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        treeCurrent = new javax.swing.JTree();
        jPanel21 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtStartStage = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEndStage = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listPaths = new javax.swing.JList();
        txtPrimaryPath = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        txtGroupPartOf = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        txtPartOf = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtSynonyms = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        chkIsPrimary = new javax.swing.JCheckBox();
        txtName = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtCurrentFile = new javax.swing.JTextField();
        cmdBrowseOld = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        txtChangedStatus = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtRulesStatus = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        listComments = new javax.swing.JList();
        jPanel25 = new javax.swing.JPanel();
        cboChangedNodes = new javax.swing.JComboBox();
        cmdPropCheck = new javax.swing.JButton();
        cboProblemNodes = new javax.swing.JComboBox();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        cmdFindCommonAncestor = new javax.swing.JButton();
        lblUnprocessed = new javax.swing.JLabel();
        lblNew = new javax.swing.JLabel();
        lblModified = new javax.swing.JLabel();
        lblDeleted = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        txtReport = new javax.swing.JTextField();
        cmdBrowseReport = new javax.swing.JButton();
        txtEditor = new javax.swing.JTextField();
        cmdEditorBrowse = new javax.swing.JButton();
        jLabel70 = new javax.swing.JLabel();
        cmdUpdateDB = new javax.swing.JButton();
        cmdGenerate = new javax.swing.JButton();
        cmdGenerateEditor = new javax.swing.JButton();
        lblPropFile = new javax.swing.JLabel();
        cmdLoadOBOFile = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        treeReferenced = new javax.swing.JTree();
        txtReferenceFile = new javax.swing.JTextField();
        cmdBrowseNew = new javax.swing.JButton();
        lblRefFile = new javax.swing.JLabel();
        cmdFromDatabase = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mouseDBHostNameTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        mouseDBPortTF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        mouseDBDbNameTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mouseDBUserNameTF = new javax.swing.JTextField();
        mouseDBPasswordPF = new javax.swing.JPasswordField();
        cmdReconnect = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        mouseAbstractClassNameTF = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        mouseAbstractClassIDTF = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        mouseAbstractClassNamespaceTF = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        mouseStageClassNameTF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        mouseStageClassIDTF = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        mouseStageClassNamespaceTF = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        mouseGroupClassNameTF = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        mouseGroupClassIDTF = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        mouseGroupClassNamespaceTF = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        mouseFormatVersionTF = new javax.swing.JTextField();
        mouseDefaultNamespaceTF = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        mouseGroupTermClassNameTF = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        mouseGroupTermClassIDTF = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        mouseGroupTermClassNamespaceTF = new javax.swing.JTextField();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Conversion Interface Between Anatomy Database and OBO format");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(" OBO file generation "));

        jLabel8.setText("Saved by:");

        jLabel1.setText("OBO file:");

        exportOBOFileTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportOBOFileTFActionPerformed(evt);
            }
        });

        exportBrowseButton.setText("Browse...");
        exportBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBrowseButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Remark:");

        remarkTA.setColumns(20);
        remarkTA.setRows(5);
        jScrollPane1.setViewportView(remarkTA);

        exportButton.setText("Generate OBO file");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        expTermNoLabel.setText("Number of terms:");

        cmdGenerateTreeReport.setText("Generate Tree Text File");
        cmdGenerateTreeReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGenerateTreeReportActionPerformed(evt);
            }
        });

        exportTextBrowseButton.setText("Browse...");
        exportTextBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportTextBrowseButtonActionPerformed(evt);
            }
        });

        jLabel23.setText("Text file:");

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel8))
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(16, 16, 16)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, expTermNoLabel)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel5Layout.createSequentialGroup()
                                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel1)
                                    .add(jLabel23))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(exportTextFileTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 363, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(exportOBOFileTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(exportBrowseButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(exportTextBrowseButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(exportButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(cmdGenerateTreeReport, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(19, 19, 19)
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(savedByTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel8)
                    .add(savedByTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel6)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(11, 11, 11)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(exportOBOFileTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(exportBrowseButton)
                    .add(exportButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(exportTextFileTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel23)
                    .add(exportTextBrowseButton)
                    .add(cmdGenerateTreeReport))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(expTermNoLabel)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Species Database"));

        specieButtonGroup.add(mouseRB);
        mouseRB.setSelected(true);
        mouseRB.setText("mouse");
        mouseRB.setToolTipText("Specify the database to use for extraction, updates and reference. Database settings can be changed in Tab 3 'Database and File Settings'");
        mouseRB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                mouseRBItemStateChanged(evt);
            }
        });

        specieButtonGroup.add(humanRB);
        humanRB.setText("human");
        humanRB.setToolTipText("Specify the database to use for extraction, updates and reference. Database settings can be changed in Tab 3 'Database and File Settings'");
        humanRB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                humanRBItemStateChanged(evt);
            }
        });
        humanRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                humanRBActionPerformed(evt);
            }
        });

        specieButtonGroup.add(chickRB);
        chickRB.setToolTipText("Specify the database to use for extraction, updates and reference. Database settings can be changed in Tab 3 'Database and File Settings'");
        chickRB.setActionCommand("chick");
        chickRB.setLabel("chick");
        chickRB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chickRBItemStateChanged(evt);
            }
        });
        chickRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chickRBActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(48, 48, 48)
                .add(mouseRB)
                .add(48, 48, 48)
                .add(humanRB)
                .add(57, 57, 57)
                .add(chickRB)
                .addContainerGap(695, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(mouseRB)
                    .add(humanRB)
                    .add(chickRB))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder("File Format"));

        formatButtonGroup.add(timedcompRB);
        timedcompRB.setText("Timed Component");
        timedcompRB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                timedcompRBItemStateChanged(evt);
            }
        });

        formatButtonGroup.add(startendRB);
        startendRB.setSelected(true);
        startendRB.setText("Starts and Ends");
        startendRB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                startendRBItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel36Layout = new org.jdesktop.layout.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .add(startendRB)
                .add(17, 17, 17)
                .add(timedcompRB)
                .addContainerGap(566, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel36Layout.createSequentialGroup()
                .add(jPanel36Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(timedcompRB)
                    .add(startendRB))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder("Root Component"));

        rootButtonGroup.add(rbDefaultRoot);
        rbDefaultRoot.setSelected(true);
        rbDefaultRoot.setText("Default Root");
        rbDefaultRoot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbDefaultRootItemStateChanged(evt);
            }
        });

        rootButtonGroup.add(rbSelectedComponents);
        rbSelectedComponents.setText("Selected Components");
        rbSelectedComponents.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbSelectedComponentsItemStateChanged(evt);
            }
        });

        cmdEditList.setText("Edit List");
        cmdEditList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditListActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel37Layout = new org.jdesktop.layout.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel37Layout.createSequentialGroup()
                .add(45, 45, 45)
                .add(rbDefaultRoot)
                .add(44, 44, 44)
                .add(rbSelectedComponents)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cboAddedRoots, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cmdEditList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(268, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel37Layout.createSequentialGroup()
                .add(jPanel37Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(rbDefaultRoot)
                    .add(rbSelectedComponents)
                    .add(cboAddedRoots, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cmdEditList))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder("Project"));

        cboProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProjectActionPerformed(evt);
            }
        });

        jLabel26.setText("Project");

        jLabel31.setText("selected for ordering");

        org.jdesktop.layout.GroupLayout jPanel27Layout = new org.jdesktop.layout.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel27Layout.createSequentialGroup()
                .add(54, 54, 54)
                .add(jLabel26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cboProject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 137, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel31)
                .addContainerGap(458, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel27Layout.createSequentialGroup()
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel26)
                    .add(jLabel31)
                    .add(cboProject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(991, 991, 991)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(197, 197, 197))
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel27, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel37, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel36, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(115, Short.MAX_VALUE))
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(126, 126, 126))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(12, 12, 12)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 34, Short.MAX_VALUE)
                .add(jPanel36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(241, 241, 241))
        );

        jPanel5.getAccessibleContext().setAccessibleName(" OBO file generation");

        mainTabbedPanel.addTab("Extract OBO File From DB", jPanel4);

        jPanel9.setAutoscrolls(true);

        treeCurrent.setModel(new DefaultTreeModel(null));
        treeCurrent.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeCurrentValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(treeCurrent);

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Component Properties"));

        jLabel9.setText("Starts At:");

        jLabel10.setText("Ends At:");

        jLabel49.setText("ID:");

        txtID.setEditable(false);

        jLabel50.setText("Alternative:");

        listPaths.setToolTipText("all other paths other than the primary path that leads to this component, parents specified in these paths are always group terms");
        jScrollPane5.setViewportView(listPaths);

        txtPrimaryPath.setToolTipText("the path where all terms are primary terms, the parent specified in this path is the primary parent");
        txtPrimaryPath.setMaximumSize(new java.awt.Dimension(0, 400));

        jLabel69.setText("Primary:");

        jLabel80.setText("Group Part Of:");

        jLabel43.setText("Part Of:");

        jLabel46.setText("Synonyms:");

        chkIsPrimary.setText("Is Primary");

        jLabel21.setText("Name:");

        org.jdesktop.layout.GroupLayout jPanel21Layout = new org.jdesktop.layout.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel49)
                    .add(jLabel43)
                    .add(jLabel80)
                    .add(jLabel46)
                    .add(jLabel69)
                    .add(jLabel50))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel21Layout.createSequentialGroup()
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(txtSynonyms)
                            .add(txtGroupPartOf)
                            .add(txtPartOf, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                        .add(18, 18, 18)
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel21Layout.createSequentialGroup()
                                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel9)
                                    .add(jLabel10))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(txtEndStage)
                                    .add(txtStartStage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 48, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(chkIsPrimary))
                        .add(96, 96, 96)
                        .add(jLabel79))
                    .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane5)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, txtPrimaryPath, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel21Layout.createSequentialGroup()
                            .add(txtID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(4, 4, 4)
                            .add(jLabel21)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(txtName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 260, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .add(11, 11, 11)
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel49)
                    .add(jLabel21)
                    .add(txtName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel21Layout.createSequentialGroup()
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel43)
                            .add(txtPartOf, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel80)
                            .add(txtGroupPartOf, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel46)
                            .add(txtSynonyms, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(9, 9, 9))
                    .add(jPanel21Layout.createSequentialGroup()
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel9)
                            .add(txtStartStage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel10)
                            .add(txtEndStage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(chkIsPrimary)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)))
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel69)
                    .add(txtPrimaryPath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel21Layout.createSequentialGroup()
                        .add(jLabel50)
                        .add(73, 73, 73)
                        .add(jLabel79))
                    .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel21Layout.linkSize(new java.awt.Component[] {txtEndStage, txtStartStage}, org.jdesktop.layout.GroupLayout.VERTICAL);

        txtCurrentFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentFileActionPerformed(evt);
            }
        });

        cmdBrowseOld.setText("...");
        cmdBrowseOld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBrowseOldActionPerformed(evt);
            }
        });

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("Component Status"));

        jLabel45.setText("Comments:");

        txtChangedStatus.setToolTipText("Compared to reference ontology for any changes in component, possible values are: UNCHECKED / UNCHANGED / MODIFIED / NEW / DELETED");

        jLabel44.setText("Changed Status:");

        jLabel48.setText("Rules Status:");

        txtRulesStatus.setToolTipText("Check for Rules Compliance, possible values are: PASSED / FAILED / UNCHECKED");

        jScrollPane7.setViewportView(listComments);

        org.jdesktop.layout.GroupLayout jPanel24Layout = new org.jdesktop.layout.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel48)
                    .add(jLabel45))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel24Layout.createSequentialGroup()
                        .add(txtRulesStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(30, 30, 30)
                        .add(jLabel44)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtChangedStatus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                        .addContainerGap(131, Short.MAX_VALUE))
                    .add(jPanel24Layout.createSequentialGroup()
                        .add(jScrollPane7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel24Layout.createSequentialGroup()
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel48)
                    .add(txtRulesStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel44)
                    .add(txtChangedStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel45)
                    .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder("Component Checks"));

        cboChangedNodes.setToolTipText("components that have changes: new / modified / deleted");
        cboChangedNodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChangedNodesActionPerformed(evt);
            }
        });
        cboChangedNodes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cboChangedNodesFocusGained(evt);
            }
        });

        cmdPropCheck.setText("Perform Checking");
        cmdPropCheck.setToolTipText("check file for rule violations and changes");
        cmdPropCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPropCheckActionPerformed(evt);
            }
        });

        cboProblemNodes.setToolTipText("components with rule violations");
        cboProblemNodes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProblemNodesActionPerformed(evt);
            }
        });
        cboProblemNodes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cboProblemNodesFocusGained(evt);
            }
        });

        jLabel71.setText("Rule Violations:");

        jLabel72.setText("Updated:");

        cmdFindCommonAncestor.setText("Find Common Ancestor");
        cmdFindCommonAncestor.setToolTipText("Find a common ancestor for all descendants of a temporary new group");
        cmdFindCommonAncestor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFindCommonAncestorActionPerformed(evt);
            }
        });

        lblUnprocessed.setFont(new java.awt.Font("Tahoma", 0, 9));
        lblUnprocessed.setText("unprocessed");

        lblNew.setFont(new java.awt.Font("Tahoma", 0, 9));
        lblNew.setText("new:?");

        lblModified.setFont(new java.awt.Font("Tahoma", 0, 9));
        lblModified.setText("modified:?");

        lblDeleted.setFont(new java.awt.Font("Tahoma", 0, 9));
        lblDeleted.setText("deleted:?");

        org.jdesktop.layout.GroupLayout jPanel25Layout = new org.jdesktop.layout.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel71)
                    .add(jLabel72))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(cboProblemNodes, 0, 220, Short.MAX_VALUE)
                    .add(cboChangedNodes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel25Layout.createSequentialGroup()
                        .add(lblUnprocessed)
                        .add(122, 122, 122))
                    .add(jPanel25Layout.createSequentialGroup()
                        .add(lblNew)
                        .add(38, 38, 38)
                        .add(lblModified, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                        .add(18, 18, 18)
                        .add(lblDeleted)
                        .addContainerGap())))
            .add(jPanel25Layout.createSequentialGroup()
                .add(33, 33, 33)
                .add(cmdFindCommonAncestor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 226, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 29, Short.MAX_VALUE)
                .add(cmdPropCheck, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 182, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(64, 64, 64))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel25Layout.createSequentialGroup()
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel71)
                    .add(cboProblemNodes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblUnprocessed))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel72)
                    .add(lblNew)
                    .add(cboChangedNodes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblDeleted)
                    .add(lblModified))
                .add(12, 12, 12)
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cmdPropCheck)
                    .add(cmdFindCommonAncestor))
                .addContainerGap())
        );

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder("Reports"));

        jLabel47.setText("Database:");

        cmdBrowseReport.setText("...");
        cmdBrowseReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBrowseReportActionPerformed(evt);
            }
        });

        cmdEditorBrowse.setText("...");
        cmdEditorBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditorBrowseActionPerformed(evt);
            }
        });

        jLabel70.setText("Editor:");

        cmdUpdateDB.setText("Update Database");
        cmdUpdateDB.setToolTipText("Executes SQL update to the selected database in Tab 1 'Extract OBO File From DB', database settings are specified in Tab 3 'Database and File Settings' ");
        cmdUpdateDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUpdateDBActionPerformed(evt);
            }
        });

        cmdGenerate.setText("Generate");
        cmdGenerate.setToolTipText("Generates a report of SQL queries needed to carry out the update of this ontology into the specified database. Reports any unallowed updates caused by components with rule violations to the Administrator, if any.");
        cmdGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGenerateActionPerformed(evt);
            }
        });

        cmdGenerateEditor.setText("Generate");
        cmdGenerateEditor.setToolTipText("Generates a PDF/text report for the editor of this ontology for reviewing purposes. Reports all terms with violations and changes.");
        cmdGenerateEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGenerateEditorActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel26Layout = new org.jdesktop.layout.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(cmdUpdateDB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel26Layout.createSequentialGroup()
                        .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(jPanel26Layout.createSequentialGroup()
                                .add(jLabel70)
                                .add(40, 40, 40))
                            .add(jPanel26Layout.createSequentialGroup()
                                .add(jLabel47, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)))
                        .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, txtEditor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, txtReport, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel26Layout.createSequentialGroup()
                                .add(cmdEditorBrowse, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cmdGenerateEditor))
                            .add(jPanel26Layout.createSequentialGroup()
                                .add(cmdBrowseReport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cmdGenerate)))))
                .add(62, 62, 62))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel26Layout.createSequentialGroup()
                .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel26Layout.createSequentialGroup()
                        .add(35, 35, 35)
                        .add(jLabel70))
                    .add(jPanel26Layout.createSequentialGroup()
                        .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(cmdGenerate)
                            .add(cmdBrowseReport)
                            .add(txtReport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel47))
                        .add(9, 9, 9)
                        .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(cmdGenerateEditor)
                            .add(cmdEditorBrowse)
                            .add(txtEditor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(cmdUpdateDB)
                .add(6, 6, 6))
        );

        lblPropFile.setText("Proposed:");

        cmdLoadOBOFile.setText("Load OBO File");
        cmdLoadOBOFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoadOBOFileActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel22Layout = new org.jdesktop.layout.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .add(jPanel22Layout.createSequentialGroup()
                        .add(lblPropFile)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cmdLoadOBOFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .add(jPanel22Layout.createSequentialGroup()
                                .add(txtCurrentFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cmdBrowseOld, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel25, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel21, 0, 544, Short.MAX_VALUE)
                    .add(jPanel24, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel22Layout.createSequentialGroup()
                        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(cmdBrowseOld)
                            .add(txtCurrentFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lblPropFile)))
                    .add(jPanel22Layout.createSequentialGroup()
                        .add(jPanel25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 228, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(cmdLoadOBOFile)
                .add(47, 47, 47))
        );

        jPanel21.getAccessibleContext().setAccessibleName(null);

        jTabbedPane2.addTab("Proposed", jPanel22);

        treeReferenced.setModel(new DefaultTreeModel(null));
        jScrollPane4.setViewportView(treeReferenced);

        txtReferenceFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReferenceFileActionPerformed(evt);
            }
        });

        cmdBrowseNew.setText("From File");
        cmdBrowseNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBrowseNewActionPerformed(evt);
            }
        });

        lblRefFile.setText("Referenced:");

        cmdFromDatabase.setText("From Database");
        cmdFromDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdFromDatabaseActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel23Layout = new org.jdesktop.layout.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                    .add(jPanel23Layout.createSequentialGroup()
                        .add(lblRefFile)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtReferenceFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 298, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cmdBrowseNew, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cmdFromDatabase)))
                .addContainerGap())
        );

        jPanel23Layout.linkSize(new java.awt.Component[] {cmdBrowseNew, cmdFromDatabase}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(cmdBrowseNew)
                        .add(cmdFromDatabase))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(lblRefFile)
                        .add(txtReferenceFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Reference", jPanel23);

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jTabbedPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName("Proposed");

        mainTabbedPanel.addTab("Introduce OBO File To DB", jPanel9);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(" Database connection "));

        jLabel2.setText("Hostname:");

        jLabel3.setText("Port:");

        jLabel7.setText("DB name:");

        mouseDBDbNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mouseDBDbNameTFActionPerformed(evt);
            }
        });

        jLabel4.setText("Username:");

        jLabel5.setText("Password:");

        mouseDBPasswordPF.setFocusCycleRoot(true);

        cmdReconnect.setText("Reconnect");
        cmdReconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReconnectActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel13Layout = new org.jdesktop.layout.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel2)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel3)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel7)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel4)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(mouseDBHostNameTF)
                    .add(mouseDBPortTF)
                    .add(mouseDBDbNameTF)
                    .add(mouseDBUserNameTF)
                    .add(jPanel13Layout.createSequentialGroup()
                        .add(mouseDBPasswordPF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 576, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cmdReconnect)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel13Layout.createSequentialGroup()
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(mouseDBHostNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(mouseDBPortTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(11, 11, 11)
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(mouseDBDbNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(mouseDBUserNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(mouseDBPasswordPF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(cmdReconnect))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(" OBO file "));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Abstract class"));

        jLabel11.setText("Name:");

        jLabel12.setText("Identifier:");

        jLabel13.setText("Namespace:");

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel11)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel12)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel13))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseAbstractClassNameTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseAbstractClassIDTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseAbstractClassNamespaceTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel11)
                    .add(mouseAbstractClassNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel12)
                    .add(mouseAbstractClassIDTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel13)
                    .add(mouseAbstractClassNamespaceTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Stage class"));

        jLabel14.setText("Name:");

        jLabel15.setText("Identifier:");

        mouseStageClassIDTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                mouseStageClassIDTFFocusLost(evt);
            }
        });

        jLabel16.setText("Namespace:");

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel15)
                    .add(jLabel16)
                    .add(jLabel14))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mouseStageClassIDTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .add(mouseStageClassNameTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .add(mouseStageClassNamespaceTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(mouseStageClassNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel14))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(mouseStageClassIDTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel15))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(mouseStageClassNamespaceTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel16))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("New group class"));

        jLabel17.setText("Name:");

        jLabel18.setText("Identifier:");

        jLabel19.setText("Namespace:");

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel17)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel18)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel19))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseGroupClassNameTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseGroupClassIDTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseGroupClassNamespaceTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel17)
                    .add(mouseGroupClassNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel18)
                    .add(mouseGroupClassIDTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel19)
                    .add(mouseGroupClassNamespaceTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(" Header values "));

        jLabel29.setText("Format version:");

        jLabel30.setText("Default namespace:");

        mouseDefaultNamespaceTF.setText("mouse_ontology");

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3Layout.createSequentialGroup()
                        .add(jLabel29)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(mouseFormatVersionTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3Layout.createSequentialGroup()
                        .add(jLabel30)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(mouseDefaultNamespaceTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel29)
                    .add(mouseFormatVersionTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel30)
                    .add(mouseDefaultNamespaceTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder("Group Term Class"));

        jLabel73.setText("Name:");

        jLabel74.setText("Identifier:");

        jLabel75.setText("Namespace:");

        org.jdesktop.layout.GroupLayout jPanel34Layout = new org.jdesktop.layout.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel73)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel74)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel75))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseGroupTermClassNameTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseGroupTermClassIDTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mouseGroupTermClassNamespaceTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel34Layout.createSequentialGroup()
                .add(jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel73)
                    .add(mouseGroupTermClassNameTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel74)
                    .add(mouseGroupTermClassIDTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel75)
                    .add(mouseGroupTermClassNamespaceTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel15Layout.createSequentialGroup()
                        .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jPanel34, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jPanel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel34, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Database", jPanel11);

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 819, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPanel.addTab("Database and File Settings", jPanel10);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainTabbedPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                    .add(exitButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(mainTabbedPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(exitButton)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed

        try{
            //check filepath exists
            File file = new File(this.fileName);

            //System.out.println("Write Out File");
            if (file.exists()) {
                file.delete();
            }

            //initialise BufferedWriter for report
            this.iniFile = new BufferedWriter(new FileWriter(this.fileName));

            this.writeIniRecords();

            this.iniFile.close();
        }
        catch(IOException io) {
            io.printStackTrace();
        }

        System.exit( 0 );
    }//GEN-LAST:event_exitButtonActionPerformed


    private void exportBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBrowseButtonActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        int result;
        result = fileChooser.showSaveDialog( this );

        if ( result != JFileChooser.CANCEL_OPTION ) {
            File file = fileChooser.getSelectedFile();
            exportOBOFileTF.setText( file.getAbsolutePath() );
        }            
    }//GEN-LAST:event_exportBrowseButtonActionPerformed


    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        
        this.makeRootComponents();
        
        // 5: connect to the database and get and export data
        if ( isConnectedToDatabase() ){
            if ( this.defaultroot ){
                ImportDatabase db =
                        new ImportDatabase( connection,
                        abstractClass,
                        stageClass,
                        groupClass,
                        groupTermClass,
                        species,
                        filetype,
                        stage,
                        rangestartint,
                        rangeendint,
                        defaultroot,
                        project );

                if ( db.getIsProcessed() ) {
                    expTermList = db.getTermList();
                    expRelationList = db.getRelationList();
                    expTermNoLabel.setText("Number of terms: " +
                            expTermList.size());

                    // save OBO file
                    if ( !exportOBOFileTF.getText().equals("") ) {
                        saveOBOFile();
                        JOptionPane.showMessageDialog( null, 
                                "OBO file has been generated and saved!",
                                " DB2OBO",
                                JOptionPane.INFORMATION_MESSAGE );

                    }
                    else {
                        JOptionPane.showMessageDialog( null,
                                "OBO file has been generated but not saved!",
                                "DB2OBO",
                                JOptionPane.INFORMATION_MESSAGE );
                    }
                
                }
                else {
                    JOptionPane.showMessageDialog( null,
                            "OBO file has not been generated! \nPlease " +
                            "check the database connection.",
                            "Error - Matrix generation",
                            JOptionPane.ERROR_MESSAGE );
                }
                disconnectFromDatabase();

            }
            else {
                ImportDatabase db =
                        new ImportDatabase( connection,
                        abstractClass,
                        stageClass,
                        groupClass,
                        groupTermClass,
                        species,
                        filetype,
                        stage,
                        rangestartint,
                        rangeendint,
                        true,
                        project );

                if ( db.getIsProcessed() ){
                    expTermList = db.getTermList();
                    expRelationList = db.getRelationList();

                    MapBuilder mapbuilder = new MapBuilder( expTermList );
                    TreeBuilder treebuilder = new TreeBuilder( mapbuilder, this.species );

                    //get new expTermList without components
                    ImportDatabase emptyDB =
                            new ImportDatabase( connection,
                            abstractClass,
                            stageClass,
                            groupClass,
                            groupTermClass,
                            species,
                            filetype,
                            stage,
                            rangestartint,
                            rangeendint,
                            defaultroot,
                            project );

                    expTermList = emptyDB.getTermList();

                    //set specified root components
                    Vector<String> vSelRoots = this.vUserRoots;

                    ArrayList<String> arrEmpty = new ArrayList();

                    //get descendants of roots and populate expTermList
                    for ( String strID: vSelRoots ){
                        //abstract components
                        //modify parent of specified roots to be 'root'
                        Component selRoot = treebuilder.getComponent( strID );
                        selRoot.setPartOf( arrEmpty );
                        selRoot.addPartOf( abstractClass.getID() );
                        this.expTermList.add(
                                treebuilder.getComponent( strID ) );

                        //get the node of each component
                        Vector<DefaultMutableTreeNode> vSelNodes =
                                treebuilder.getNodes( strID );

                        //for each node of one component
                        for ( DefaultMutableTreeNode selNode: vSelNodes ){
                            //get the descendants
                            Vector<DefaultMutableTreeNode> vDescNodes =
                                    new Vector();
                            vDescNodes = 
                                    treebuilder.recursiveGetNodes(
                                    selNode, vDescNodes);

                            //convert descendant nodes back into components
                            //add to expTermList
                            for ( DefaultMutableTreeNode descNode: vDescNodes ){
                                Component currentCompie =
                                        (Component) descNode.getUserObject();
                                ArrayList<String> arrParents = new ArrayList();
                                for ( String strParent:
                                      currentCompie.getPartOf() ){
                                    Component currentParent = 
                                            treebuilder.getComponent(
                                            strParent );
                                    if ( currentParent.getIsPrimary() ) {
                                        arrParents.add( strParent );
                                    }
                                }
                                currentCompie.setPartOf( arrParents );
                                this.expTermList.add( currentCompie );
                            }
                        }

                        //do same for time components
                        if ( filetype.equals("Timed Component") ){
                            Map mapTimeCompie = mapbuilder.getTimeComponents();
                            System.out.println("getting time components for " + 
                                    strID);

                            Vector<String> vTimeCompies =
                                    (Vector<String>) mapTimeCompie.get( strID );

                            for ( String timeCompie: vTimeCompies ){

                                //modify parent of specified roots to be 'root'
                                Component selTimeRoot =
                                        treebuilder.getComponent( timeCompie );
                                selTimeRoot.setPartOf( new ArrayList() );
                                selTimeRoot.addPartOf(
                                        stageAnatomyClass.getID() );
                                this.expTermList.add(
                                       treebuilder.getComponent( timeCompie ) );

                                //get the node of each component
                                Vector<DefaultMutableTreeNode> vSelTimeNodes =
                                        treebuilder.getNodes( timeCompie );

                                //for each node of one component
                                for ( DefaultMutableTreeNode selTimeNode:
                                      vSelTimeNodes ){

                                    //get the descendants
                                    Vector<DefaultMutableTreeNode>
                                            vDescTimeNodes = new Vector();
                                    vDescTimeNodes = 
                                            treebuilder.recursiveGetNodes(
                                            selTimeNode, vDescTimeNodes);

                                    //convert descendant nodes back into
                                    // components
                                    //add to expTermList
                                    for ( DefaultMutableTreeNode descTimeNode:
                                          vDescTimeNodes ){
                                        Component currentTimeCompie =
                                       (Component) descTimeNode.getUserObject();
                                        ArrayList<String> arrParents =
                                                new ArrayList();

                                        //remove non primary components
                                        for ( String strParent:
                                            currentTimeCompie.getPartOf() ){
                                            Component currentParent = 
                                                    treebuilder.getComponent(
                                                    strParent );

                                            Component abstractParent = 
                                                    treebuilder.getComponent(
                                           currentParent.getTimeComponentOf() );
                                            if ( abstractParent.getIsPrimary() ) 
                                            {
                                                arrParents.add( strParent );
                                            }
                                        }
                                        currentTimeCompie.setPartOf(
                                                arrParents );
                                        this.expTermList.add(
                                                currentTimeCompie );
                                    }
                                }
                            }
                        }
                    }

                    // save OBO file
                    if ( !exportOBOFileTF.getText().equals("") ) {
                        saveOBOFile();
                        JOptionPane.showMessageDialog( null,
                                "OBO file has been generated and saved!",
                                " DB2OBO",
                                JOptionPane.INFORMATION_MESSAGE );
                    }
                    else {
                        JOptionPane.showMessageDialog( null,
                                "OBO file has been generated but not saved!",
                                "DB2OBO",
                                JOptionPane.INFORMATION_MESSAGE );
                    }
                
                }
                else {
                    JOptionPane.showMessageDialog( null,
                            "OBO file has not been generated! \nPlease " +
                            "check the database connection.",
                            "Error - Matrix generation",
                            JOptionPane.ERROR_MESSAGE );
                }
                disconnectFromDatabase();
            }
        }
        else {
            JOptionPane.showMessageDialog( null,
                    "Cannot connect to database! \n" +
                    "Please check Parameter Settings and make sure your " +
                    "database server is turned on.",
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
        }
    }//GEN-LAST:event_exportButtonActionPerformed


private void mouseRBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_mouseRBItemStateChanged

    if ( mouseRB.isSelected() ) {
        this.species = "mouse";
    }
}//GEN-LAST:event_mouseRBItemStateChanged


private void humanRBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_humanRBItemStateChanged
}//GEN-LAST:event_humanRBItemStateChanged


private void cmdBrowseOldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBrowseOldActionPerformed

    // Open file browser window and get file path
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
    int result;
    result = fileChooser.showOpenDialog( this );
        
    if ( result != JFileChooser.CANCEL_OPTION ) {
        File file = fileChooser.getSelectedFile();  
        txtCurrentFile.setText( file.getAbsolutePath() );
        //this.loadProposedTree();
        //cmdLoadTreeCurrentActionPerformed(evt); 
    }
}//GEN-LAST:event_cmdBrowseOldActionPerformed


private void cmdBrowseNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBrowseNewActionPerformed

    // Open file browser window and get file path
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
    int result;
    result = fileChooser.showOpenDialog( this );
        
    if ( result != JFileChooser.CANCEL_OPTION ) {
        File file = fileChooser.getSelectedFile();  
        txtReferenceFile.setText( file.getAbsolutePath() );
        //cmdLoadTreeReferenceActionPerformed(evt); 
        loadReferenceTree();
    }
}//GEN-LAST:event_cmdBrowseNewActionPerformed


private void cmdPropCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPropCheckActionPerformed

    //rebuild referenceList (parseOldTermList)
    //when loading from an old file 
    //this.loadReferenceTree();
    String strSize = "";
    
    if (this.parseNewTermList != null &&
            this.parseOldTermList != null){
  
        //check components on current termlist
        CheckComponents checkie = 
                    new CheckComponents( this.parseNewTermList,
                        this.parseOldTermList,
                        this.propTreebuilder,
                        this.abstractClass,
                        this.stageClass,
                        this.groupClass,
                        this.groupTermClass,
                        this.species );
            
        //pass to public check components
        this.propCheckComponents = checkie;

        //change indicator
        if ( checkie.getProblemTermList().isEmpty() ){
            lblUnprocessed.setForeground( new Color( 0, 140, 0 ) );
            lblUnprocessed.setText("PROCESSED");
                
            strSize = Integer.toString(
                    this.propCheckComponents.getNewTermList().size() );
            lblNew.setForeground( new Color( 0, 140, 0 ) );
            lblNew.setText( "new:" + strSize );
                
            strSize = Integer.toString(
                    this.propCheckComponents.getModifiedTermList().size() );
            lblModified.setForeground( new Color( 0, 140, 0 ) );
            lblModified.setText( "modified:" + strSize );
                
            strSize = Integer.toString(
                    this.propCheckComponents.getDeletedTermList().size() );
            lblDeleted.setForeground( new Color( 0, 140, 0 ) );
            lblDeleted.setText( "deleted:" + strSize );
                
        }
        else {
            lblUnprocessed.setForeground( Color.RED );
            lblUnprocessed.setText("PROCESSED: " +
                        checkie.getProblemTermList().size() + " failed" );
                
            strSize = Integer.toString(
                    this.propCheckComponents.getNewTermList().size() );
            lblNew.setForeground( new Color( 0, 140, 0 ) );
            lblNew.setText( "new:" + strSize );
                
            strSize = Integer.toString(
                    this.propCheckComponents.getModifiedTermList().size() );
            lblModified.setForeground( new Color( 0, 140, 0 ) );
            lblModified.setText( "modified:" + strSize );
                
            strSize = Integer.toString(
                    this.propCheckComponents.getDeletedTermList().size() );
            lblDeleted.setForeground( new Color( 0, 140, 0 ) );
            lblDeleted.setText( "deleted:" + strSize );
        }
            
        //build maps from checked components
        MapBuilder mapbuilder = new MapBuilder( this.parseNewTermList );

        //build tree
        //TreeBuilder obotree = new TreeBuilder(mapbuilder);
        this.propTreebuilder = new TreeBuilder( mapbuilder, this.species );
            
        //load tree
        DefaultTreeModel model = ( DefaultTreeModel ) treeCurrent.getModel();
        DefaultMutableTreeNode propRoot = this.propTreebuilder.getRootNode();
        propRoot.setUserObject( txtCurrentFile.getText() );
        model.setRoot( propRoot );        
            
        //load combobox
        //Populate the combobox list
        this.reloadComboBox(
                    cboProblemNodes, checkie.getProblemTermList() );
        this.reloadComboBox(
                    cboChangedNodes, checkie.getChangesTermList() );
    }
    else if ( this.parseNewTermList != null) {
            //check components for rules only
        CheckComponents rulesCheckie =
                    new CheckComponents(
                        this.parseNewTermList,
                        this.propTreebuilder,
                        this.abstractClass,
                        this.stageClass,
                        this.groupClass,
                        this.groupTermClass,
                        this.species );

        if ( rulesCheckie.getProblemTermList().isEmpty() ) {
            lblUnprocessed.setForeground( new Color( 0, 140, 0 ) );
            lblUnprocessed.setText("PROCESSED");
        }
        else {
            lblUnprocessed.setForeground( Color.RED );
            lblUnprocessed.setText("PROCESSED: " +
                        rulesCheckie.getProblemTermList().size() + " failed" );
        }
            
        //build maps from checked components
        MapBuilder mapbuilder = new MapBuilder ( this.parseNewTermList );

        //build tree
        this.propTreebuilder = new TreeBuilder( mapbuilder, this.species );

        //load tree
        DefaultTreeModel model = ( DefaultTreeModel ) treeCurrent.getModel();
        DefaultMutableTreeNode propRoot = this.propTreebuilder.getRootNode();
        propRoot.setUserObject( txtCurrentFile.getText() );
        model.setRoot( propRoot );

        //load problems combobox only
        this.reloadComboBox( cboProblemNodes,
                    rulesCheckie.getProblemTermList() );
            
        JOptionPane.showMessageDialog( null,
                    "No reference file loaded. Only performing rules check!",
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
    }
    else {
        JOptionPane.showMessageDialog( null,
                    "No file loaded yet!",
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
    }
}//GEN-LAST:event_cmdPropCheckActionPerformed


private void treeCurrentValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeCurrentValueChanged

    DefaultMutableTreeNode node =
            (DefaultMutableTreeNode) treeCurrent.getLastSelectedPathComponent();
        
    //Nothing is selected.
    if (node == null) {
        return;
    }

    Object nodeInfo = node.getUserObject();

    if (nodeInfo instanceof backend.Component) {
        backend.Component comp = (backend.Component) nodeInfo;

        loadTextBoxes(comp);
    }
}//GEN-LAST:event_treeCurrentValueChanged


private void expandAll(JTree tree) {

    int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            row++;
        }
}


private void makeRootComponents(){
    
    // 1: set abstract class parameters
    abstractClass = new Component();
    abstractClass.setName( mouseAbstractClassNameTF.getText() );
    abstractClass.setID( mouseAbstractClassIDTF.getText() );
    abstractClass.setNamespace( mouseAbstractClassNamespaceTF.getText() );

    // 2: set stage class parameters
    stageClass = new Component();
    stageClass.setName( mouseStageClassNameTF.getText() );
    stageClass.setID( mouseStageClassIDTF.getText() );
    stageClass.setNamespace( mouseStageClassNamespaceTF.getText() );

    // 3: temporary new group class parameters
    groupClass = new Component();
    groupClass.setName( mouseGroupClassNameTF.getText() );
    groupClass.setID( mouseGroupClassIDTF.getText() );
    groupClass.setNamespace( mouseGroupClassNamespaceTF.getText() );
        
    // 4: set stage name ID
    /*
    if ( species.equals("mouse") ) {
        stageNameID =
                mouseStageClassIDTF.getText().substring(0,
                mouseStageClassIDTF.getText().indexOf(":"));
    }
    */

    // 5: group term class parameters
    groupTermClass = new Component();
    groupTermClass.setName( mouseGroupTermClassNameTF.getText() );
    groupTermClass.setID( mouseGroupTermClassIDTF.getText() );
    groupTermClass.setNamespace( mouseGroupTermClassNamespaceTF.getText() );

    // 6: stage anatomy class
    stageAnatomyClass = new Component();
    stageAnatomyClass.setName( "Stage anatomy" );
    stageAnatomyClass.setID( abstractClass.getID().substring(0, 4) + ":0" );
    stageAnatomyClass.setNamespace( "stage_anatomy" );
        
}


private void writeIniRecords(){

    try {
        this.iniFile.write("[DB2OBO]" );
        this.iniFile.newLine();
        this.iniFile.write("db_url=" + this.strURL );
        this.iniFile.newLine();
        this.iniFile.write("db_host=" + mouseDBHostNameTF.getText() );
        this.iniFile.newLine();
        this.iniFile.write("db_port=" + mouseDBPortTF.getText() );
        this.iniFile.newLine();
        this.iniFile.write("db_name=" + mouseDBDbNameTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("db_user=" + mouseDBUserNameTF.getText() );
        this.iniFile.newLine();
        this.iniFile.write("species=" + this.species);
        this.iniFile.newLine();
        this.iniFile.write("stage=" + this.stage);
        this.iniFile.newLine();
        this.iniFile.write("rangestart=" + this.rangestart);
        this.iniFile.newLine();
        this.iniFile.write("rangestartint=" +
                Integer.toString(this.rangestartint));
        this.iniFile.newLine();
        this.iniFile.write("rangeend=" + this.rangeend);
        this.iniFile.newLine();
        this.iniFile.write("rangeendint=" + Integer.toString(this.rangeendint));
        this.iniFile.newLine();
        this.iniFile.write("abstractClassName=" + 
                mouseAbstractClassNameTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("abstractClassId=" + 
                mouseAbstractClassIDTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("abstractClassNamespace=" +
                mouseAbstractClassNamespaceTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("stageClassName=" + mouseStageClassNameTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("stageClassId=" + mouseStageClassIDTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("stageClassNamespace=" +
                mouseStageClassNamespaceTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("groupClassName=" + mouseGroupClassNameTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("groupClassId=" + mouseGroupClassIDTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("groupClassNamespace=" + 
                mouseGroupClassNamespaceTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("groupTermClassName=" + 
                mouseGroupTermClassNameTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("groupTermClassId=" + 
                mouseGroupTermClassIDTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("groupTermClassNamespace=" +
                mouseGroupTermClassNamespaceTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("savedBy=" + savedByTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("remark=" + remarkTA.getText());
        this.iniFile.newLine();
        this.iniFile.write("exportOBOFile=" + exportOBOFileTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("exportTextFile=" + exportTextFileTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("txtCurrentFile=" + txtCurrentFile.getText());
        this.iniFile.newLine();
        this.iniFile.write("txtReport=" + txtReport.getText());
        this.iniFile.newLine();
        this.iniFile.write("txtEditor=" + txtEditor.getText());
        this.iniFile.newLine();
        this.iniFile.write("mouseFormatVersion=" +
                mouseFormatVersionTF.getText());
        this.iniFile.newLine();
        this.iniFile.write("mouseDefaultNamespace=" +
                mouseDefaultNamespaceTF.getText());
        this.iniFile.newLine();

    }
    catch(IOException io) {
        io.printStackTrace();
    }

}


private void readIniRecords(){

    String line = "";
    String[] temp;

    //System.out.println("Read Records");

    try {
        line = this.outFile.readLine();
        //System.out.println("line = " + line);
        line = this.outFile.readLine();
        //System.out.println("line = " + line);
        temp = line.split("=");
        if (temp.length == 1 ) {
            this.strURL = "";
        }
        else {
            this.strURL = temp[1];
        }
        //System.out.println("this.strURL = " + this.strURL);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseDBHostNameTF.setText("");
        }
        else {
            mouseDBHostNameTF.setText(temp[1]);
        }
        
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseDBPortTF.setText("");
        }
        else {
            mouseDBPortTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseDBDbNameTF.setText("");
        }
        else {
            mouseDBDbNameTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseDBUserNameTF.setText("");
        }
        else {
            mouseDBUserNameTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        mouseDBPasswordPF.setText("");

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            this.species = "";
        }
        else {
            this.species = temp[1];
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            this.stage = "";
        }
        else {
            this.stage = temp[1];
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            this.rangestart = "";
        }
        else {
            this.rangestart = temp[1];
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            this.rangestartint = 0;
        }
        else {
            this.rangestartint = Integer.parseInt(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            this.rangeend = "";
        }
        else {
            this.rangeend = temp[1];
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            this.rangeendint = 0;
        }
        else {
        this.rangeendint = Integer.parseInt(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);


        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseAbstractClassNameTF.setText("");
        }
        else {
            mouseAbstractClassNameTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseAbstractClassIDTF.setText("");
        }
        else {
            mouseAbstractClassIDTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseAbstractClassNamespaceTF.setText("");
        }
        else {
            mouseAbstractClassNamespaceTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseStageClassNameTF.setText("");
        }
        else {
            mouseStageClassNameTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseStageClassIDTF.setText("");
        }
        else {
            mouseStageClassIDTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseStageClassNamespaceTF.setText("");
        }
        else {
            mouseStageClassNamespaceTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseGroupClassNameTF.setText("");
        }
        else {
            mouseGroupClassNameTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseGroupClassIDTF.setText("");
        }
        else {
            mouseGroupClassIDTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseGroupClassNamespaceTF.setText("");
        }
        else {
            mouseGroupClassNamespaceTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseGroupTermClassNameTF.setText("");
        }
        else {
            mouseGroupTermClassNameTF.setText(temp[1]);
        }
        // 5: group term class parameters
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseGroupTermClassIDTF.setText("");
        }
        else {
            mouseGroupTermClassIDTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseGroupTermClassNamespaceTF.setText("");
        }
        else {
            mouseGroupTermClassNamespaceTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);


        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            savedByTF.setText("");
        }
        else {
            savedByTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            remarkTA.setText("");
        }
        else {
            remarkTA.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            exportOBOFileTF.setText("");
        }
        else {
            exportOBOFileTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            exportTextFileTF.setText("");
        }
        else {
            exportTextFileTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);


        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            txtCurrentFile.setText("");
        }
        else {
            txtCurrentFile.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            txtReport.setText("");
        }
        else {
            txtReport.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            txtEditor.setText("");
        }
        else {
            txtEditor.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);


        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseFormatVersionTF.setText("");
        }
        else {
            mouseFormatVersionTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

        line = this.outFile.readLine();
        temp = line.split("=");
        if (temp.length == 1 ) {
            mouseDefaultNamespaceTF.setText("");
        }
        else {
            mouseDefaultNamespaceTF.setText(temp[1]);
        }
        //System.out.println("temp[1] = " + temp[1]);

    }
    catch(IOException io) {
        io.printStackTrace();
    }

}


private void reloadTree(JTree tree, ArrayList<Component> termList,
        TreeBuilder treebuilder, String fileName){

    //Build hashmap of components
    MapBuilder mapbuilder = new MapBuilder(termList);

    //Build tree
    treebuilder = new TreeBuilder(mapbuilder, this.species);

    //load tree
    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
    DefaultMutableTreeNode root = treebuilder.getRootNode();
    root.setUserObject( fileName );
    model.setRoot( root );
        
    //test
    //System.out.println( "checking root node from reloadTree: " +
    // treebuilder.getRootNode() );
}


private void expandPathsTo(JTree tree, TreeBuilder treebuilder,
        Component compie){

    for(int i = 1; i <= tree.getRowCount(); i++) {
        tree.collapseRow(i);
    }

    Vector< DefaultMutableTreeNode[] > paths =
            treebuilder.getPaths( compie.getID() );
        
        
    if (paths != null) {
        TreePath[] new_paths = new TreePath[paths.size()];
        for(int i=0; i< paths.size(); i++){
            new_paths[i] = new TreePath(paths.get(i));
        }        
        tree.getSelectionModel().setSelectionPaths(new_paths);   
    }
    else {
        JOptionPane.showMessageDialog( null,
                "Component not found! Check reference tree for " +
                compie.getID() + " " + compie.getName(),
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
    loadTextBoxes(compie);
}


private void loadTextBoxes(Component compie){

    txtID.setText( compie.getID() );
    txtName.setText( compie.getName() );
    txtStartStage.setText( compie.getStartsAtStr(this.species) );
    txtEndStage.setText( compie.getEndsAtStr(this.species)  );
    txtPartOf.setText( compie.getPartOf().toString() );
    txtGroupPartOf.setText( compie.getGroupPartOf().toString() );
    txtSynonyms.setText( compie.getSynonym().toString() ); 
    txtChangedStatus.setText( compie.getStrChangeStatus() );
    txtRulesStatus.setText( compie.getStrRuleStatus() );
    chkIsPrimary.setSelected( compie.getIsPrimary() );
        
    
    if ( compie.getPrimaryPath() != null ) {
        TreePath pathDisplay = new TreePath( compie.getPrimaryPath() );
        txtPrimaryPath.setText( pathDisplay.toString() );
    }
    else {
        txtPrimaryPath.setText("");
    }
        
    //Paths
    DefaultListModel listPathsModel = new DefaultListModel();

    //Vector< DefaultMutableTreeNode[] > paths =
    // this.propTreebuilder.getPaths( compie.getID() );
    Vector< DefaultMutableTreeNode[] > paths = compie.getPaths();

    for (int i =0; i< paths.size(); i++){
        TreePath treepath = new TreePath( paths.get(i) );
        listPathsModel.addElement( treepath );
    }

    listPaths.setModel( listPathsModel );
        
    //Comments
    DefaultListModel listModel = new DefaultListModel();
    Set<String> comments = compie.getCheckComments();

    for (String s: comments){
        listModel.addElement(s);
    }
    listComments.setModel( listModel );
}


private void clearReportTextBoxes(){

    txtID.setText("");
    txtName.setText("");
    txtStartStage.setText("");
    txtEndStage.setText("");
    txtPartOf.setText("");
    txtGroupPartOf.setText("");
    txtChangedStatus.setText("");
    txtRulesStatus.setText("");
    txtPrimaryPath.setText("");
    txtSynonyms.setText("");
    txtRulesStatus.setText("");
    txtChangedStatus.setText("");
    chkIsPrimary.setSelected( false );
        
    DefaultListModel listModel = new DefaultListModel();

    listPaths.setModel( listModel );
    listComments.setModel( listModel );
}


private void reloadComboBox(JComboBox cbo, ArrayList<Component> termList){

    Component compie;
    
    cbo.removeAllItems();
    for(int i=0; i <termList.size(); i++){
        compie = termList.get(i);
        cbo.addItem(compie);                              
    }
}


private boolean loadDefaultReferenceTree(){
    
    boolean loaded = false;
    
    this.strURL = START_URL +
            mouseDBHostNameTF.getText() + ":" +
            mouseDBPortTF.getText() + "/" +
            mouseDBDbNameTF.getText();
   
    if ( this.isConnectedToDatabase() ){
        
        //get configured roots from gui
        makeRootComponents();

        //import database
        ImportDatabase db =
                new ImportDatabase( connection,
                abstractClass,
                stageClass,
                groupClass,
                groupTermClass,
                species,
                filetype,
                stage,
                rangestartint,
                rangeendint,
                defaultroot,
                project );

        expTermList = db.getTermList();
        expRelationList = db.getRelationList();

        this.loadProjects( db.getProjects() );
        
        //reset CheckComponent object
        this.propCheckComponents = null;

        //reset indicator
        lblUnprocessed.setForeground( Color.BLACK );
        lblUnprocessed.setText("UNPROCESSED");
        lblNew.setForeground( Color.BLACK );
        lblNew.setText("new:?");
        lblModified.setForeground( Color.BLACK );
        lblModified.setText("modified:?");
        lblDeleted.setForeground( Color.BLACK );
        lblDeleted.setText("deleted:?");
        
        //reset changes list
        this.clearReportTextBoxes();
        this.cboChangedNodes.removeAllItems();
        
        //set oldTermList to expTermList
        this.parseOldTermList = this.expTermList;

        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder(this.parseOldTermList);

        //Build tree
        TreeBuilder obotree = new TreeBuilder(mapbuilder, this.species);

        //check for rules violation
        CheckComponents checkie =
                new CheckComponents( this.parseOldTermList,
                obotree,
                this.abstractClass,
                this.stageClass,
                this.groupClass,
                this.groupTermClass,
                this.species );

        //if file has problems don't allow to load
        if ( checkie.getProblemTermList().isEmpty() ){
            //load tree
            DefaultTreeModel model =
                    (DefaultTreeModel) treeReferenced.getModel();
            DefaultMutableTreeNode refRoot = obotree.getRootNode();
            refRoot.setUserObject( strURL );
            model.setRoot( refRoot );
 
            //indicate source loaded 
            this.txtReferenceFile.setText(strURL);
            
            //set refTreebuilder to current obo tree
            this.refTreebuilder = obotree;
            
            loaded = true;
            //reload proposed tree to clear any deleted components added to
            // parseNewTermList
            //File propFiley = new File( txtCurrentFile.getText() );
            //if ( propFiley.exists() ) this.loadProposedTree();
            /*
            try {
                //set remark text area to loaded database
                this.remarkTA.setText("Database loaded: " +
                        this.connection.getMetaData().getDatabaseProductName());
            } catch (SQLException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,
                        null, ex);
            }*/
        }
        else{
            //reset parseOldTermList
            this.parseOldTermList = null;

            //reset tree
            DefaultTreeModel model =
                    (DefaultTreeModel) treeReferenced.getModel();
            model.setRoot(null);

            JOptionPane.showMessageDialog( null,
                    "Loading Default Reference Tree: Some components in the " +
                    "specified reference contain rule violations. \n" +
                    "Please load the reference under the proposed tab to fix " +
                    "the problem; \n" +
                    "Alternatively, please select another reference.", 
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );

            System.out.println( checkie.getProblemTermList() );
            
            Component probCompie =
                    (Component) checkie.getProblemTermList().get(0);
            
            System.out.println("no. of components with problems = " +
                    checkie.getProblemTermList().size());
            System.out.println("comments = " + probCompie.getCheckComments());
        }
    }
    else {
        //reset everything related to reference file
        //reset CheckComponent object
        this.propCheckComponents = null;

        //reset indicator
        lblUnprocessed.setForeground( Color.BLACK );
        lblUnprocessed.setText("UNPROCESSED");
        lblNew.setForeground( Color.BLACK );
        lblNew.setText("new:?");
        lblModified.setForeground( Color.BLACK );
        lblModified.setText("modified:?");
        lblDeleted.setForeground( Color.BLACK );
        lblDeleted.setText("deleted:?");

        //reset changes list
        this.clearReportTextBoxes();
        this.cboChangedNodes.removeAllItems();

        //reset old term list
        this.parseOldTermList = null;

        //set tree to nothing
        DefaultTreeModel model = (DefaultTreeModel) treeReferenced.getModel();
        model.setRoot(null);

        JOptionPane.showMessageDialog( null, 
                "Failed to load reference tree from database. Please check " +
                "connection.",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
    
    return loaded;
}


private void loadProposedTree(){

    File filey = new File(txtCurrentFile.getText());

    if ( filey.exists() ){
        //reset CheckComponents object
        this.propCheckComponents = null;

        //reset indicator
        lblUnprocessed.setForeground( Color.BLACK );
        lblUnprocessed.setText("UNPROCESSED");
        lblNew.setForeground( Color.BLACK );
        lblNew.setText("new:?");
        lblModified.setForeground( Color.BLACK );
        lblModified.setText("modified:?");
        lblDeleted.setForeground( Color.BLACK );
        lblDeleted.setText("deleted:?");

        //reset gui
        this.clearReportTextBoxes();
        this.cboProblemNodes.removeAllItems();
        this.cboChangedNodes.removeAllItems();

        //Parse file
        OBOParser obofile = new OBOParser(txtCurrentFile.getText());

        //Build components
        this.parseNewTermList = obofile.getComponents();

        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder(this.parseNewTermList);

        //Build tree
        this.propTreebuilder = new TreeBuilder(mapbuilder, this.species);

        //load tree
        DefaultTreeModel model = (DefaultTreeModel) treeCurrent.getModel();
        DefaultMutableTreeNode propRoot = this.propTreebuilder.getRootNode();
        propRoot.setUserObject( txtCurrentFile.getText() );
        model.setRoot( propRoot );
        //model.setRoot(this.propTreebuilder.getRootNode());
            
    }
    else {
        //reset everything related to proposed file
        //reset CheckComponents object
        this.propCheckComponents = null;

        //reset indicator
        lblUnprocessed.setForeground( Color.BLACK );
        lblUnprocessed.setText("UNPROCESSED");
        lblNew.setForeground( Color.BLACK );
        lblNew.setText("new:?");
        lblModified.setForeground( Color.BLACK );
        lblModified.setText("modified:?");
        lblDeleted.setForeground( Color.BLACK );
        lblDeleted.setText("deleted:?");

        //reset gui
        this.clearReportTextBoxes();
        this.cboProblemNodes.removeAllItems();
        this.cboChangedNodes.removeAllItems();

        //reset new term list
        this.parseNewTermList = null;

        //set tree to nothing
        DefaultTreeModel model = (DefaultTreeModel) treeCurrent.getModel();
        model.setRoot(null);
        JOptionPane.showMessageDialog( null,
                "File Not Found!",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
}


private void loadReferenceTree(){

    File filey = new File( txtReferenceFile.getText() );

    if ( filey.exists() ) {
        //reset changes list
        this.clearReportTextBoxes();
        this.cboChangedNodes.removeAllItems();

        //set checkComponents to null
        this.propCheckComponents = null;

        //reset indicator
        lblUnprocessed.setForeground( Color.BLACK );
        lblUnprocessed.setText("UNPROCESSED");
        lblNew.setForeground( Color.BLACK );
        lblNew.setText("new:?");
        lblModified.setForeground( Color.BLACK );
        lblModified.setText("modified:?");
        lblDeleted.setForeground( Color.BLACK );
        lblDeleted.setText("deleted:?");

        //Parse file
        OBOParser obofile = new OBOParser(txtReferenceFile.getText());

        //Build components
        this.parseOldTermList = obofile.getComponents();

        //Build hashmap of components
        MapBuilder mapbuilder = new MapBuilder(this.parseOldTermList);

        //Build tree
        TreeBuilder obotree = new TreeBuilder(mapbuilder, this.species);

        //check for rules violation
        CheckComponents checkie =
                new CheckComponents( this.parseOldTermList,
                    obotree,
                    this.abstractClass,
                    this.stageClass,
                    this.groupClass,
                    this.groupTermClass,
                    this.species );

        //if file has problems don't allow to load
        if ( checkie.getProblemTermList().isEmpty() ){
            //load tree
            DefaultTreeModel model = (DefaultTreeModel) treeReferenced.getModel();
            DefaultMutableTreeNode refRoot = obotree.getRootNode();
            refRoot.setUserObject( txtReferenceFile.getText() );
            model.setRoot( refRoot );

            //reload proposed tree to clear any deleted components added to parseNewTermList
            File propFiley = new File( txtCurrentFile.getText() );

            if ( propFiley.exists() ) this.loadProposedTree();

            //set refTreebuilder to current obo tree
            this.refTreebuilder = obotree;
            try {
                //set remark text area to loaded database
                this.remarkTA.setText("Database loaded: " +
                        this.connection.getMetaData().getDatabaseProductName());
            }
            catch (SQLException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
        else {
            //reset old term list
            this.parseOldTermList = null;

            //reset tree
            DefaultTreeModel model =
                    (DefaultTreeModel) treeReferenced.getModel();
            model.setRoot(null);
            JOptionPane.showMessageDialog( null, 
                    "Loading Reference Tree: Some components in the " +
                    "specified reference contain rule violations. \n" +
                    "Please load the reference under the proposed tab to fix " +
                    "the problem; \n" +
                    "Alternatively, please select another reference.", 
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
        }

    }
    else {
        //reset everything related to proposed file
        //reset CheckComponents object
        this.propCheckComponents = null;

        //reset indicator
        lblUnprocessed.setForeground( Color.BLACK );
        lblUnprocessed.setText("UNPROCESSED");
        lblNew.setForeground( Color.BLACK );
        lblNew.setText("new:?");
        lblModified.setForeground( Color.BLACK );
        lblModified.setText("modified:?");
        lblDeleted.setForeground( Color.BLACK );
        lblDeleted.setText("deleted:?");

        //reset gui
        this.clearReportTextBoxes();
        this.cboProblemNodes.removeAllItems();
        this.cboChangedNodes.removeAllItems();

        //reset old term list
        this.parseOldTermList = null;

        //set tree to nothing
        DefaultTreeModel model = (DefaultTreeModel) treeReferenced.getModel();
        model.setRoot(null);
        JOptionPane.showMessageDialog( null,
                "Proposed File Not Found!",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
}


private boolean noRuleViolationsInDB(){
    
    boolean proceed = false;
    
    //try to load tree from database
    if ( this.loadDefaultReferenceTree() ){
        //if loading successful, db tree does not have violations

        //perform full checks
        this.propCheckComponents = 
                new CheckComponents( this.parseNewTermList,
                     this.parseOldTermList,
                     this.propTreebuilder,
                     this.abstractClass,
                     this.stageClass,
                     this.groupClass,
                     this.groupTermClass,
                     this.species );
        
        //return true to allow db update to proceed if checking detects no
        // rule violations in proposed file
        if ( this.propCheckComponents.getProblemTermList().isEmpty() ) {
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Rule violations detected! \n" + 
                "Please ensure that all components in the proposed file are " +
                "free of rule violations. \n" +
                "Database Update did not proceed.", 
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE);

            System.out.println( this.propCheckComponents.getProblemTermList() );

            ArrayList <Component> problems =
                    this.propCheckComponents.getProblemTermList();

            for ( Component compie: problems ){
                System.out.println( compie.getCheckComments() );
            }
            return false;
        }
    }
  
    return proceed;

}


private void txtCurrentFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentFileActionPerformed

    //cmdLoadTreeCurrentActionPerformed(evt);
    this.loadProposedTree();

}//GEN-LAST:event_txtCurrentFileActionPerformed


private void cboProblemNodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProblemNodesActionPerformed

    Component compie = (Component) (cboProblemNodes.getSelectedItem());
    if (compie!=null) {
        loadTextBoxes(compie);            
        expandPathsTo(treeCurrent, this.propTreebuilder, compie);
    }
}//GEN-LAST:event_cboProblemNodesActionPerformed


private void cmdGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGenerateActionPerformed
        
    int proceed = 0;    
    boolean flagUpdateDB = false;
    
    if ( ( this.parseNewTermList != null ) &&
         ( this.propCheckComponents != null ) &&
           !this.project.equals("") ) {
        if ( isConnectedToDatabase() ) {
            //allow generation of report even if there are failed components
            // but warn user
            if ( !this.propCheckComponents.getProblemTermList().isEmpty() ) {
                proceed = JOptionPane.showConfirmDialog( null ,
                        "Rule Violations Detected! \n " +
                        "Program will generate SQL Query Report - No actual " +
                        "update can take place \n " +
                        "before the components with Rule Violations are " +
                        "attended to.",
                        "DB2OBO",
                        JOptionPane.OK_CANCEL_OPTION );
            }
            //if user clicks ok generate the sql report
            if ( proceed == 0 ) {
                System.out.println( this.propTreebuilder.getRootNode() );

                this.reloadTree( treeCurrent, 
                        this.parseNewTermList,
                        this.propTreebuilder,
                        txtCurrentFile.getText() );

                this.genie = 
                        new GenerateSQL(this.connection,
                            this.parseNewTermList,
                            this.propTreebuilder,
                            this.refTreebuilder,
                            this.species,
                            flagUpdateDB,
                            txtReport.getText(),
                            abstractClass,
                            this.project );

                disconnectFromDatabase();

                if ( genie.getIsProcessed() ) {
                    JOptionPane.showMessageDialog( null, 
                            "SQL Query Report generated.",
                            "DB2OBO",
                            JOptionPane.INFORMATION_MESSAGE );

                    this.reloadTree( treeCurrent,
                            this.parseNewTermList,
                            this.propTreebuilder,
                            txtCurrentFile.getText() );
                }
                else {
                    JOptionPane.showMessageDialog( null,
                            "SQL Query Report could not be saved. Check " +
                            "path in save file textbox." ,
                            "DB2OBO",
                            JOptionPane.INFORMATION_MESSAGE );
                }
            }
        }
        else {
            JOptionPane.showMessageDialog( null,
                    "Cannot connect to databases!",
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
        }
    }
    else if ( ( this.parseNewTermList != null ) ||
              ( this.propCheckComponents != null ) ) {
        JOptionPane.showMessageDialog( null, "Perform Full Checks first!", 
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
    else {
        JOptionPane.showMessageDialog( null, "Select a Project to reference!", 
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
//GEN-LAST:event_cmdGenerateActionPerformed
}                                           


private void cboChangedNodesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChangedNodesActionPerformed

    Component compie = (Component) (cboChangedNodes.getSelectedItem());

    if (compie!=null) {
        loadTextBoxes(compie);
        expandPathsTo(treeCurrent, this.propTreebuilder, compie);
    }     
}//GEN-LAST:event_cboChangedNodesActionPerformed


private void cmdFindCommonAncestorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFindCommonAncestorActionPerformed

    int intProceed = 0;
    Component selectedCompie = null;
    
    //find component that is currently displayed in txtID
    if (this.propCheckComponents != null) {
           
        //find last selected node from tree and change to component
        DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode)
                    treeCurrent.getLastSelectedPathComponent();
            
        if (node == null) {
            //Nothing is selected.
            return;
        }
          
        //change selected node into a component, make sure it is in the parsed
        // list
        Object nodeInfo = node.getUserObject();

        if ( nodeInfo instanceof backend.Component ){
            Component compie = (Component) nodeInfo;
            selectedCompie =
                    this.propTreebuilder.getComponent( compie.getID() );
        }
            
        if ( selectedCompie != null ){

            //if common ancester has already been found
            ArrayList<String> groupparents = selectedCompie.getGroupPartOf();

            for ( String groupparent: groupparents ){
                if ( this.propTreebuilder.getComponent(
                        groupparent ).getIsPrimary() ) {
                    JOptionPane.showMessageDialog( null ,
                            "Cannot add another common ancestor! \n" +
                            "This term already has a primary parent - " +
                            groupparent +
                            "[" + 
                    this.propTreebuilder.getComponent( groupparent ).getName() +
                            "]",
                            "DB2OBO",
                            JOptionPane.INFORMATION_MESSAGE );
                    return;
                }
            }

            ArrayList<String> primaryparents = selectedCompie.getPartOf();

            for ( String primaryparent: primaryparents ){
                Component primaryCompie =
                        this.propTreebuilder.getComponent(primaryparent);
                if ( primaryCompie.getIsPrimary() &&
                        !primaryCompie.getID().equals( groupClass.getID() ) ){
                    JOptionPane.showMessageDialog( null ,
                            "Cannot add another common ancestor! \n" +
                            "This term already has a primary parent - " +
                            primaryCompie.getID() +
                            "[" + primaryCompie.getName() + "]", 
                            "DB2OBO",
                            JOptionPane.INFORMATION_MESSAGE );
                        return;
                }
            }

            if ( node.getChildCount() == 1 ){

                //only one child, not allowed to be new group component
                selectedCompie.setCheckComment("Invalid Group Term - " +
                        "contains only one child term.");
                 
                JOptionPane.showMessageDialog( null , "Invalid Group Term! \n" + 
                            "A Group Term must have more than one child " +
                            "component.",
                            "DB2OBO",
                            JOptionPane.INFORMATION_MESSAGE );
                return;
            }

            //method findCommonAncestor gets all children of compie to
            // findCommonAncestor
            //pass node back to findCommonAncestor method so that children of
            // node can be passed along
            Component commonAncestor = 
                    this.propCheckComponents.getCommonAncestor( node,
                        this.propTreebuilder );

            //get common ancestor component from tree
            Component commie =
                    this.propTreebuilder.getComponent( commonAncestor.getID() );

            //if common ancestor id is empty it is an invalid component passed
            // by getCommonAncestor
            //if ( commonAncestor.getID().isEmpty() || commie==null ){
            if ( commonAncestor.getID() == null || 
                    commie == null ){
                JOptionPane.showMessageDialog( null,
                        "Could not find common ancestor for children under " +
                        node + "! Returned output: " + commonAncestor, 
                        "DB2OBO",
                        JOptionPane.INFORMATION_MESSAGE );
            }
            else {
                intProceed = JOptionPane.showConfirmDialog( null,
                        "Common Ancestor for all the children under " +
                        "\n      " + node + " is: \n         " + 
                        commonAncestor + "\n Create new group term '" +
                        node + "' under " + commonAncestor +
                        "?" ,
                        "DB2OBO",
                        JOptionPane.YES_NO_CANCEL_OPTION );

                if ( intProceed == 0 ){
                    //ASSIGN properties to NEW GROUP TERM
                    //selected component is now a group term
                    selectedCompie.setIsPrimary( false );

                    //add group part of [ common ancestor ] to selected node
                    selectedCompie.addGroupPartOf( commie.getID() );

                    //add is a group to selected node
                    selectedCompie.setIsA( this.groupTermClass.getID() );

                    //change namespace to abstract anatomy
                    selectedCompie.setNamespace(
                            this.abstractClass.getNamespace() );

                    //set default start at and ends at same as parent for now
                    //note: if have time write alternate method to find min
                    // start and max end for all children
                    //note: alternate method not important unless one of the
                    // children is a new node
                    selectedCompie.setStartsAt(
                            commonAncestor.getStartsAtStr(this.species) );
                    selectedCompie.setEndsAt( 
                            commonAncestor.getEndsAtStr(this.species) );

                    //clear rule and changed status
                    selectedCompie.setStrRuleStatus("PASSED");
                    selectedCompie.setStrChangeStatus("NEW");
                    selectedCompie.setFlagMissingRel(false);

                    //set checkComment
                    selectedCompie.clearCheckComment();
                    selectedCompie.setCheckComment("New Group Component - " +
                            "Common Ancestor is " +
                            commie.getID() + "[" + 
                            this.propTreebuilder.getComponent(
                            commie.getID()).getName() + "]" );
                              
                        
                    MapBuilder mapbuilder =
                            new MapBuilder(this.parseNewTermList);
                    this.propTreebuilder = new TreeBuilder(mapbuilder, this.species);

                    this.propCheckComponents = new CheckComponents( 
                            this.parseNewTermList,
                            this.parseOldTermList,
                            this.propTreebuilder,
                            this.abstractClass,
                            this.stageClass,
                            this.groupClass,
                            this.groupTermClass,
                            this.species );
                        
                    this.reloadTree( treeCurrent, this.parseNewTermList,
                            this.propTreebuilder, txtCurrentFile.getText() );

                    //set obo file name to root: operation in reloadTree does
                    // not have any
                    //effect on propTreebuilder - could it be pass by reference
                    // problem?
                    //load tree
                    DefaultTreeModel model =
                            (DefaultTreeModel) treeCurrent.getModel();
                    DefaultMutableTreeNode root =
                            this.propTreebuilder.getRootNode();
                    root.setUserObject( txtCurrentFile.getText() );
                    model.setRoot( root );
                        
                    //load combobox
                    //Populate the combobox list
                    this.reloadComboBox( cboProblemNodes,
                            this.propCheckComponents.getProblemTermList() );
                    this.reloadComboBox( cboChangedNodes,
                            this.propCheckComponents.getChangesTermList() );

                    //reload lable
                    if (
                    !this.propCheckComponents.getProblemTermList().isEmpty() ) {
                        lblUnprocessed.setForeground( Color.RED );
                        lblUnprocessed.setText("PROCESSED: " + 
                          this.propCheckComponents.getProblemTermList().size() +
                          " failed" );
                    }
                    else {
                        lblUnprocessed.setForeground( Color.GREEN );
                        lblUnprocessed.setText("PROCESSED");
                    }
                }
            }
        }
        else {
            JOptionPane.showMessageDialog( null,
                    "Selected node is not a valid component!",
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
        }
    }
    else {
        JOptionPane.showMessageDialog( null ,
                "Perform Check Components to build paths for each node first!",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
}//GEN-LAST:event_cmdFindCommonAncestorActionPerformed


private void timedcompRBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_timedcompRBItemStateChanged

    if ( timedcompRB.isSelected() ) {
        this.filetype = "Timed Component";
    }

}//GEN-LAST:event_timedcompRBItemStateChanged


private void startendRBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_startendRBItemStateChanged

    if (startendRB.isSelected() ) {
        this.filetype = "Starts and Ends";
    }

}//GEN-LAST:event_startendRBItemStateChanged


private void cmdUpdateDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdateDBActionPerformed
    
    if( ( this.parseNewTermList != null ) &&
        ( this.propCheckComponents != null ) &&
          !this.project.equals("") ) {
        if ( noRuleViolationsInDB() ) {
            if ( isConnectedToDatabase() ) {

                System.out.println("Connected to database for obo file " +
                        "import!");
                
                //change this to true!
                boolean flagUpdateDB = true;

                this.genie =
                        new GenerateSQL(this.connection,
                        this.parseNewTermList,
                        this.propTreebuilder,
                        this.refTreebuilder,
                        this.species,
                        flagUpdateDB,
                        txtReport.getText(),
                        abstractClass,
                        this.project );

                if ( this.genie.getIsProcessed() ){
                    JOptionPane.showMessageDialog( null, 
                            "Database update completed succesfully!",
                            "DB2OBO",
                            JOptionPane.INFORMATION_MESSAGE );

                    this.reloadTree( treeCurrent,
                            this.parseNewTermList,
                            this.propTreebuilder,
                            txtCurrentFile.getText() );
                }
                else { 
                    JOptionPane.showMessageDialog( null,
                            "ERROR: Database update could not be completed!",
                            "DB2OBO",
                            JOptionPane.ERROR_MESSAGE);
                }

                //make new obo file with generated emapa ids
                String strUpdatedFile = "";

                if ( txtCurrentFile.getText().endsWith(".obo") ) {
                    strUpdatedFile = 
                            strUpdatedFile.copyValueOf(
                                txtCurrentFile.getText().toCharArray(),
                                0, txtCurrentFile.getText().length()-4 );

                    strUpdatedFile = strUpdatedFile + "_processed.obo";

                }
                else {
                    strUpdatedFile = txtCurrentFile.getText() +
                            "_processed.obo";
                }

                /*saveOBOFile( strUpdatedFile,
                        this.propCheckComponents.getProposedTermList() );*/
                
                //txtCurrentFile.setText( strUpdatedFile );
                this.loadProposedTree(); 
                //this.loadDefaultReferenceTree();
                
                this.propCheckComponents = null;

                //reset indicator
                lblUnprocessed.setForeground( Color.BLACK );
                lblUnprocessed.setText("UNPROCESSED");
                lblNew.setForeground( Color.BLACK );
                lblNew.setText("new:?");
                lblModified.setForeground( Color.BLACK );
                lblModified.setText("modified:?");
                lblDeleted.setForeground( Color.BLACK );
                lblDeleted.setText("deleted:?");

                disconnectFromDatabase();
            }
        }
        else {
            JOptionPane.showMessageDialog( null,
                    "Database Update Cancelled! \n" +
                    "There was a problem performing the checks against the " +
                    "database. \n" +
                    "Please load a reference file from the database you wish " +
                    "to perform the update to and try again. \n",
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
        }
    }
    else if( ( this.parseNewTermList != null ) ||
             ( this.propCheckComponents != null ) ) {
        JOptionPane.showMessageDialog( null, "Perform Full Checks first!", 
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
    else {
        JOptionPane.showMessageDialog( null, "Select a Project to reference!", 
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
}//GEN-LAST:event_cmdUpdateDBActionPerformed


private void cmdBrowseReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBrowseReportActionPerformed

    // Open file browser window and get file path
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
    int result;
    result = fileChooser.showSaveDialog( this );
       
    if ( result != JFileChooser.CANCEL_OPTION ) {
        File file = fileChooser.getSelectedFile();  
        txtReport.setText( file.getAbsolutePath() );
    }
}//GEN-LAST:event_cmdBrowseReportActionPerformed


private void txtReferenceFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReferenceFileActionPerformed

    this.loadReferenceTree();
}//GEN-LAST:event_txtReferenceFileActionPerformed


private void cmdEditorBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditorBrowseActionPerformed

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
    int result;
    result = fileChooser.showSaveDialog( this );

    if ( result != JFileChooser.CANCEL_OPTION ) {
        File file = fileChooser.getSelectedFile();
        txtEditor.setText( file.getAbsolutePath() );
    } 
}//GEN-LAST:event_cmdEditorBrowseActionPerformed


private void cmdGenerateEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGenerateEditorActionPerformed

    if ( this.propCheckComponents != null ) {
        int intFormat = 0;
        String strFileName = txtEditor.getText(); 
        String strExtension = 
                strFileName.substring( txtEditor.getText().length() - 4,
                    txtEditor.getText().length() );
        
        intFormat = JOptionPane.showConfirmDialog( null,
                "The Editor Report is in text format by default, \n" +
                "Would you like DB2OBO to generate the report in PDF format " +
                "instead?",
                "DB2OBO",
                JOptionPane.YES_NO_CANCEL_OPTION );
        
        if (intFormat == 0) {

            //validate file extension for pdf
            if ( !strExtension.equals(".pdf") ) {
                strFileName = strFileName + ".pdf";
            }

            txtEditor.setText( strFileName );

            //generate pdf
            GenerateEditorPDF piddy = 
                    new GenerateEditorPDF( this.propCheckComponents,
                        strFileName,
                        txtCurrentFile.getText(),
                        this.propTreebuilder,
                        this.species);

            if ( piddy.getIsProcessed() ) {
                JOptionPane.showMessageDialog( null, "PDF file " + strFileName +
                        " generated and saved!",
                        "DB2OBO",
                        JOptionPane.INFORMATION_MESSAGE );
            }
            else {
                JOptionPane.showMessageDialog( null, "PDF file " + strFileName +
                        " could not be saved. Check path in save file textbox.",
                        "DB2OBO",
                        JOptionPane.INFORMATION_MESSAGE );
            }
        }
        else if (intFormat == 1) {
            //validate file extension for txt
            if ( !strExtension.equals(".txt") ) {
                strFileName = strFileName + ".txt";
            }

            txtEditor.setText( strFileName );

            //generate txt
            GenerateEditorReport eddie =
                    new GenerateEditorReport( this.propCheckComponents,
                        strFileName,
                        txtCurrentFile.getText(),
                        this.species);
            
            if ( eddie.getIsProcessed() ) {
                JOptionPane.showMessageDialog( null, "Text file " +
                        strFileName + " generated and saved!",
                        "DB2OBO",
                        JOptionPane.INFORMATION_MESSAGE );
            }
            else {
                JOptionPane.showMessageDialog( null, "Text file " +
                        strFileName + " could not be saved. Check path in " +
                        "save file textbox.",
                        "DB2OBO",
                        JOptionPane.INFORMATION_MESSAGE );
            }
        }
    }
    else {
        JOptionPane.showMessageDialog( null, "Perform Full Checks first!",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
}//GEN-LAST:event_cmdGenerateEditorActionPerformed


private void cboProblemNodesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboProblemNodesFocusGained

    Component compie = (Component) (cboProblemNodes.getSelectedItem());
    
    if (compie != null) {
        loadTextBoxes(compie);            
        expandPathsTo(treeCurrent, this.propTreebuilder, compie);
    }
}//GEN-LAST:event_cboProblemNodesFocusGained


private void cboChangedNodesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboChangedNodesFocusGained

    Component compie = (Component) (cboChangedNodes.getSelectedItem());

    if (compie != null) {
        loadTextBoxes(compie);
        expandPathsTo(treeCurrent, this.propTreebuilder, compie);
    }  
}//GEN-LAST:event_cboChangedNodesFocusGained


private void cmdGenerateTreeReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGenerateTreeReportActionPerformed

    this.loadDefaultReferenceTree();

    // save Tree Report
    if ( !exportTextFileTF.getText().equals("") ) {
        if ( this.species.equals("mouse") ) {
            this.refTreebuilder.writeNodeReport(exportTextFileTF.getText(),
                    "EMAPA:0");
        }
        if ( this.species.equals("human") ) {
            this.refTreebuilder.writeNodeReport(exportTextFileTF.getText(),
                    "EHDAA:0");
        }
        JOptionPane.showMessageDialog( null,
                "Tree Report has been generated and saved!",
                " DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );

    }
    else {
        JOptionPane.showMessageDialog( null,
                "Tree Report has NOT been generated!",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }

}//GEN-LAST:event_cmdGenerateTreeReportActionPerformed


private void exportTextBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportTextBrowseButtonActionPerformed

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
    int result;
    result = fileChooser.showSaveDialog( this );

    if ( result != JFileChooser.CANCEL_OPTION ) {
        File file = fileChooser.getSelectedFile();
        exportTextFileTF.setText( file.getAbsolutePath() );
    }   
}//GEN-LAST:event_exportTextBrowseButtonActionPerformed


private void rbDefaultRootItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbDefaultRootItemStateChanged

    if ( rbDefaultRoot.isSelected() ) {
        this.defaultroot = true;
    }

}//GEN-LAST:event_rbDefaultRootItemStateChanged


private void rbSelectedComponentsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbSelectedComponentsItemStateChanged
    
    if ( rbSelectedComponents.isSelected() ) {
        this.defaultroot = false;
    }

}//GEN-LAST:event_rbSelectedComponentsItemStateChanged


private void cmdEditListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditListActionPerformed

    ListRoots listie = new ListRoots(this);
    listie.setVisible(true);

    //new ListRoots().setVisible(true);
}//GEN-LAST:event_cmdEditListActionPerformed


private void cmdFromDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdFromDatabaseActionPerformed

    loadDefaultReferenceTree();
}//GEN-LAST:event_cmdFromDatabaseActionPerformed


private void cboProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProjectActionPerformed
    
    if (cboProject.getSelectedItem() != null){
        this.project = cboProject.getSelectedItem().toString();
    }

}//GEN-LAST:event_cboProjectActionPerformed


private void humanRB1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chickRBItemStateChanged
}//GEN-LAST:event_chickRBItemStateChanged


private void humanRB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chickRBActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_chickRBActionPerformed


private void chickDBDbNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chickDBDbNameTFActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_chickDBDbNameTFActionPerformed


private void cmdReconnectCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdReconnectCActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_cmdReconnectCActionPerformed


private void chickStageClassNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chickStageClassNameTFActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_chickStageClassNameTFActionPerformed


private void exportOBOFileTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportOBOFileTFActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_exportOBOFileTFActionPerformed


private void chickRBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_humanRB1ItemStateChanged
    // TODO add your handling code here:
}//GEN-LAST:event_humanRB1ItemStateChanged


private void humanRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_humanRBActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_humanRBActionPerformed


private void chickRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_humanRB1ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_humanRB1ActionPerformed


private void mouseStageClassIDTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mouseStageClassIDTFFocusLost
    // TODO add your handling code here:
}//GEN-LAST:event_mouseStageClassIDTFFocusLost


private void cmdReconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdReconnectActionPerformed

    if ( isConnectedToDatabase() ){
        //boolean connected = loadDefaultReferenceTree();
        if ( loadDefaultReferenceTree() ) {
            JOptionPane.showMessageDialog( null,
                    "Connected to database. Reference ontology reloaded.",
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
        }
    } 
    /*else {
        JOptionPane.showMessageDialog( null,
                "Failed to establish connection! Please check database " +
                "settings.",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }*/
}//GEN-LAST:event_cmdReconnectActionPerformed


private void mouseDBDbNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mouseDBDbNameTFActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_mouseDBDbNameTFActionPerformed

private void cmdLoadOBOFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoadOBOFileActionPerformed

    if ( txtCurrentFile.getText().equals("") ) {
        JOptionPane.showMessageDialog( null, "Please Enter a valid Obo File",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
    }
    else {
        this.loadProposedTree();
    }


}//GEN-LAST:event_cmdLoadOBOFileActionPerformed


public void loadAddedRoots(Vector<String> roots){
    this.vUserRoots.clear();
    cboAddedRoots.removeAllItems();

    for (String root: roots){
        this.vUserRoots.add( root );
        cboAddedRoots.addItem( (Object) root );
    }
}


public void loadProjects(ArrayList<String> projects){

    cboProject.removeAllItems();
    int currentIndex = 0;

    for (String strProject: projects){
        //System.out.println("Adding project to combobox: " + strProject);
        cboProject.addItem( (Object) strProject );

        if ( strProject.equals("GUDMAP") ){
            cboProject.setSelectedIndex(currentIndex);
            this.project = strProject;
        }

        currentIndex++;
    }
}


private boolean isConnectedToDatabase() {

    String query = "";

    try {
        Class.forName(DRIVER);
        if ( mouseDBPasswordPF.getText().equals("")) {
            JOptionPane.showMessageDialog( null,
                    "Password is blank - Please enter a Password!",
                    "DB2OBO",
                    JOptionPane.INFORMATION_MESSAGE );
            return false;
        }
        else {
            this.connection = DriverManager.getConnection( START_URL +
                            mouseDBHostNameTF.getText() + ":" +
                            mouseDBPortTF.getText() + "/" +
                            mouseDBDbNameTF.getText(),
                            mouseDBUserNameTF.getText(),
                            new String(mouseDBPasswordPF.getText()) );

            ResultSet speciesRS = null;
            query = "SELECT rsp_name FROM REF_SPECIES";

            speciesRS = this.connection.createStatement().executeQuery(query);

            if ( speciesRS.next() ) {
                this.species = speciesRS.getString("rsp_name");
            }

            ResultSet stageRS = null;
            query = "SELECT stg_sequence, stg_name FROM ANA_STAGE ORDER " +
                    "BY stg_sequence";

            stageRS = this.connection.createStatement().executeQuery(query);

            if ( stageRS.next() ) {
                stageRS.first();
                this.stage = stageRS.getString("stg_name");
                this.rangestart = stageRS.getString("stg_name");
                this.rangestartint = stageRS.getInt("stg_sequence");
                stageRS.last();
                this.rangeend = stageRS.getString("stg_name");
                this.rangeendint = stageRS.getInt("stg_sequence");
            }
        }
        return true;
    } 
    catch (SQLException ex) {
        //ex.printStackTrace();
        System.out.println("Cannot connect to the database!");
        JOptionPane.showMessageDialog( null,
                "Cannot connect to the database!",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
        return false;
    } 
    catch (ClassNotFoundException ex) {
        //ex.printStackTrace();
        System.out.println("Missing database driver! (Please install MySQL " +
                "Connector/J.)");
        JOptionPane.showMessageDialog( null,
                "Missing database driver! (Please install MySQL " +
                "Connector/J.)",
                "DB2OBO",
                JOptionPane.INFORMATION_MESSAGE );
        return false;
    }
}
    
   
private void disconnectFromDatabase() {              

    try {
        System.out.println("Disconnecting from Database");
        connection.close();
    }
    catch (SQLException ex) {
        System.out.println("Database error during disconnection");
    }                             
}


private void saveOBOFile(String fileName, ArrayList<Component> termList) {

    try {

        //System.out.println("saveOBOFile #1");

        BufferedWriter outputFile =
                    new BufferedWriter(new FileWriter(fileName));

        // format-version
        outputFile.write("format-version: " +
                        mouseFormatVersionTF.getText() + "\n");
        // date
        //outputFile.write("date: "+DATE+"\n");
        Calendar cal = Calendar.getInstance();
        outputFile.write("date: " + cal.get(Calendar.DAY_OF_MONTH) + ":" +
                    cal.get(Calendar.MONTH) + ":" + cal.get(Calendar.YEAR) +
                    " " + cal.get(Calendar.HOUR_OF_DAY) + ":" +
                    cal.get(Calendar.MINUTE)+"\n");
        // saved by
        outputFile.write("saved-by: " + savedByTF.getText() + "\n");

        // default-namespace
        outputFile.write("default-namespace: " +
                        mouseDefaultNamespaceTF.getText() + "\n");
        // remark
        outputFile.write("remark: " + remarkTA.getText() + "\n");

        // terms - component
        for (int i=0; i<termList.size(); i++) {
            if ( !termList.get(i).getStrChangeStatus().equals("DELETED") ){
                outputFile.write("\n[Term]\n");
                // id
                outputFile.write("id: " + termList.get(i).getID() + "\n");
                /*System.out.println("id : " +
                     termList.get(i).getID());*/
                // name
                outputFile.write("name: " + termList.get(i).getName() + "\n");
                /*System.out.println("name : " +
                     termList.get(i).getName());*/
                // namespace
                outputFile.write("namespace: " +
                            termList.get(i).getNamespace() + "\n");

                //System.out.println("termList.get(i).getNamespace() = " + termList.get(i).getNamespace());
                //System.out.println("termList.get(i).getName() = " + termList.get(i).getName());

                if ( !termList.get(i).getIsA().equals("") ) {
                    outputFile.write("relationship: is_a " +
                            termList.get(i).getIsA() + "\n");
                    /*System.out.println("relationship: is_a " +
                     termList.get(i).getIsA());*/
                }

                if ( !termList.get(i).getNamespace().equals("theiler_stage") ||
                     !termList.get(i).getNamespace().equals("new_group_namespace") &&
                     !termList.get(i).getNamespace().equals("group_term") &&
                     !termList.get(i).getName().equals("Abstract anatomy") ){

                    // is_a relationship
                    // part_of relationships
                    //System.out.println("id: "+termList.get(i).getID()+);
                    for (int j=0; j<termList.get(i).getPartOf().size(); j++) {
                        /*System.out.println("relationship: part_of " +
                         termList.get(i).getPartOf());*/
                        outputFile.write("relationship: part_of " +
                                termList.get(i).getPartOf().get(j) + "\n");
                    }

                    // starts_at relationship
                    if ( !termList.get(i).getStartsAt().equals("") ) {
                        outputFile.write("relationship: starts_at " +
                                termList.get(i).getStartsAtStr(this.species) +
                                "\n");
                    }

                    // ends_at relationship
                    if ( !termList.get(i).getEndsAt().equals("") ) {
                        outputFile.write("relationship: ends_at " +
                                termList.get(i).getEndsAtStr(this.species) + "\n");
                    }

                    // has timed_component relationship
                    for (int j=0; j<termList.get(i).getHasTimeComponent().size();
                         j++) {
                        outputFile.write("relationship: has_timed_component " +
                                termList.get(i).getHasTimeComponent().get(j) +
                                "\n");
                    }

                    // present_in relationship
                    if ( termList.get(i).getPresentIn() != 0 ) {
                        outputFile.write("relationship: present_in " +
                                Integer.toString(termList.get(i).getPresentIn()) +
                                "\n");
                    }

                    // time_component_of relationship
                    if ( !termList.get(i).getTimeComponentOf().equals("") ) {
                          outputFile.write("relationship: time_component_of " +
                                  termList.get(i).getTimeComponentOf() + "\n");
                    }

                    // group_part_of relationship
                    for (int j=0; j<termList.get(i).getGroupPartOf().size();
                         j++) {
                        outputFile.write("relationship: group_part_of " +
                                termList.get(i).getGroupPartOf().get(j) + "\n");
                    }

                    // synonyms
                    for (int j=0; j<termList.get(i).getSynonym().size(); j++) {
                        outputFile.write("related_synonym: \"" +
                                termList.get(i).getSynonym().get(j) +
                                "\" []\n");
                    }


                }
                // comments
                boolean firstComment = true;

                for (Iterator<String> k =
                        termList.get(i).getUserComments().iterator();
                        k.hasNext();){
                    if (firstComment){
                        outputFile.write("comment: ");
                        firstComment = false;
                    } 
                    else {
                        outputFile.write("\n");
                    }
                    outputFile.write(k.next());
                    //line carriage after last comment
                    //if ( !k.hasNext() ){ 
                    //    outputFile.write("\n");
                    //}
                }
            }
        }// for i

        // relations
        for (int i=0; i<expRelationList.size(); i++) {
            outputFile.write("\n[Typedef]\n");
            outputFile.write("id: " + expRelationList.get(i).getID() +
                    "\n");
            outputFile.write("name: " + expRelationList.get(i).getName() +
                    "\n");
            if ( !expRelationList.get(i).getTransitive().equals("") ) {
                outputFile.write("is_transitive: " +
                        expRelationList.get(i).getTransitive() +
                        "\n");
            }
        }
        outputFile.write("\\n");
        outputFile.close(); // close outputFile
    }
    catch(IOException io) {
        io.printStackTrace();
    }
}


private void saveOBOFile() {

    try {        
        BufferedWriter outputFile =
                new BufferedWriter(new FileWriter(exportOBOFileTF.getText()));

        //System.out.println("saveOBOFile #2");

        // format-version
        outputFile.write("format-version: " + mouseFormatVersionTF.getText() +
                "\n");

        // date
        //outputFile.write("date: "+DATE+"\n");
        Calendar cal = Calendar.getInstance();
        outputFile.write("date: " + cal.get(Calendar.DAY_OF_MONTH) + ":" +
                cal.get(Calendar.MONTH) + ":" + cal.get(Calendar.YEAR) + " " +
                cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) +
                "\n");

        // saved by
        outputFile.write("saved-by: " + savedByTF.getText() + "\n");

        // default-namespace
        outputFile.write("default-namespace: " +
                        mouseDefaultNamespaceTF.getText() + "\n");

        // remark
        outputFile.write("remark: " + remarkTA.getText() + "\n");


        // terms - component
        for (int i=0; i<expTermList.size(); i++) {
            outputFile.write("\n[Term]\n");

            // id
            outputFile.write("id: " + expTermList.get(i).getID() + "\n");
            //System.out.println("id : " + expTermList.get(i).getID());

            // name
            outputFile.write("name: " + expTermList.get(i).getName() + "\n");
            //System.out.println("name : " + expTermList.get(i).getName());
                
            // namespace
            outputFile.write("namespace: " + expTermList.get(i).getNamespace() +
                    "\n");

            //System.out.println("expTermList.get(i).getNamespace() = " + expTermList.get(i).getNamespace());
            //System.out.println("expTermList.get(i).getName() = " + expTermList.get(i).getName());

            // is_a relationship
            if ( !expTermList.get(i).getIsA().equals("") ) {
                outputFile.write("relationship: is_a " +
                        expTermList.get(i).getIsA() + "\n");
                /*System.out.println("relationship: is_a " +
                 termList.get(i).getIsA());*/
            }

            // is_a relationship
            if ( !expTermList.get(i).getNamespace().equals("theiler_stage") &&
                 !expTermList.get(i).getNamespace().equals("new_group_namespace") &&
                 !expTermList.get(i).getNamespace().equals("group_term") &&
                 !expTermList.get(i).getName().equals("Abstract anatomy") ) {

                // part_of relationships
                //System.out.println("id: "+termList.get(i).getID()+);
                for (int j=0; j<expTermList.get(i).getPartOf().size(); j++) {
                    /*System.out.println("relationship: part_of " +
                     termList.get(i).getPartOf());*/
                    outputFile.write("relationship: part_of " +
                            expTermList.get(i).getPartOf().get(j) + "\n");
                }

                // starts_at relationship
                if ( !expTermList.get(i).getStartsAt().equals("") ) {
                    outputFile.write("relationship: starts_at " +
                            expTermList.get(i).getStartsAtStr(this.species) +
                            "\n");
                }

                // ends_at relationship
                if ( !expTermList.get(i).getEndsAt().equals("") ) {
                    outputFile.write("relationship: ends_at " +
                            expTermList.get(i).getEndsAtStr(this.species) + "\n");
                }

                // has timed_component relationship
                for (int j=0; j<expTermList.get(i).getHasTimeComponent().size();
                     j++) {
                    outputFile.write("relationship: has_timed_component " +
                            expTermList.get(i).getHasTimeComponent().get(j) +
                            "\n");
                }

                // present_in relationship
                if ( expTermList.get(i).getPresentIn() != 0 ) {
                    outputFile.write("relationship: present_in " +
                            Integer.toString(expTermList.get(i).getPresentIn()) +
                            "\n");
                }

                // time_component_of relationship
                if ( !expTermList.get(i).getTimeComponentOf().equals("") ) {
                      outputFile.write("relationship: time_component_of " +
                              expTermList.get(i).getTimeComponentOf() + "\n");
                }

                // group_part_of relationship
                for (int j=0; j<expTermList.get(i).getGroupPartOf().size();
                     j++) {
                    outputFile.write("relationship: group_part_of " +
                            expTermList.get(i).getGroupPartOf().get(j) + "\n");
                }

                // synonyms
                for (int j=0; j<expTermList.get(i).getSynonym().size(); j++) {
                    outputFile.write("related_synonym: \"" +
                            expTermList.get(i).getSynonym().get(j) +
                            "\" []\n");
                }


            }
            //comments
            boolean firstComment = true;

            for (Iterator<String> k = 
                    expTermList.get(i).getUserComments().iterator();
                    k.hasNext();){
                if ( firstComment ) {
                    outputFile.write("comment: ");
                    firstComment = false;
                } 
                else {
                    outputFile.write("\\n");
                }
                outputFile.write(k.next());

                if ( !k.hasNext() ){ 
                        outputFile.write("\n");
                }
            }
        }// for i

        // relations
        for (int i=0; i<expRelationList.size(); i++) {
            outputFile.write("\n[Typedef]\n");
            outputFile.write("id: " + expRelationList.get(i).getID() + "\n");
            outputFile.write("name: " + expRelationList.get(i).getName() + "\n");
            if ( !expRelationList.get(i).getTransitive().equals("") ) {
                outputFile.write("is_transitive: " +
                        expRelationList.get(i).getTransitive() + "\n");
            }
        }
        outputFile.write("\n");
            
        outputFile.close(); 
    }

    catch(IOException io) {
            io.printStackTrace();
    }
}
    

public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

        public void run() {
        /*
        String osName = System.getProperty("os.name");
        System.out.println("os.name = -->"+osName+"<--");
        String osArch = System.getProperty("os.arch");
        System.out.println("os.arch = -->"+osArch+"<--");

        System.out.println("java.library.path = "+System.getProperty("java.library.path"));
        System.out.println("java.ext.dirs = "+System.getProperty("java.ext.dirs"));
        System.out.println("java.class.path = "+System.getProperty("java.class.path"));
        */
            new MainGUI().setVisible(true);
        }
        
    } );

}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboAddedRoots;
    private javax.swing.JComboBox cboChangedNodes;
    private javax.swing.JComboBox cboProblemNodes;
    private javax.swing.JComboBox cboProject;
    private javax.swing.JRadioButton chickRB;
    private javax.swing.JCheckBox chkIsPrimary;
    private javax.swing.JButton cmdBrowseNew;
    private javax.swing.JButton cmdBrowseOld;
    private javax.swing.JButton cmdBrowseReport;
    private javax.swing.JButton cmdEditList;
    private javax.swing.JButton cmdEditorBrowse;
    private javax.swing.JButton cmdFindCommonAncestor;
    private javax.swing.JButton cmdFromDatabase;
    private javax.swing.JButton cmdGenerate;
    private javax.swing.JButton cmdGenerateEditor;
    private javax.swing.JButton cmdGenerateTreeReport;
    private javax.swing.JButton cmdLoadOBOFile;
    private javax.swing.JButton cmdPropCheck;
    private javax.swing.JButton cmdReconnect;
    private javax.swing.JButton cmdUpdateDB;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel expTermNoLabel;
    private javax.swing.JButton exportBrowseButton;
    private javax.swing.JButton exportButton;
    private javax.swing.JTextField exportOBOFileTF;
    private javax.swing.JButton exportTextBrowseButton;
    private javax.swing.JTextField exportTextFileTF;
    private javax.swing.ButtonGroup formatButtonGroup;
    private javax.swing.JRadioButton humanRB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblDeleted;
    private javax.swing.JLabel lblModified;
    private javax.swing.JLabel lblNew;
    private javax.swing.JLabel lblPropFile;
    private javax.swing.JLabel lblRefFile;
    private javax.swing.JLabel lblUnprocessed;
    private javax.swing.JList listComments;
    private javax.swing.JList listPaths;
    private javax.swing.JTabbedPane mainTabbedPanel;
    private javax.swing.JTextField mouseAbstractClassIDTF;
    private javax.swing.JTextField mouseAbstractClassNameTF;
    private javax.swing.JTextField mouseAbstractClassNamespaceTF;
    private javax.swing.JTextField mouseDBDbNameTF;
    private javax.swing.JTextField mouseDBHostNameTF;
    private javax.swing.JPasswordField mouseDBPasswordPF;
    private javax.swing.JTextField mouseDBPortTF;
    private javax.swing.JTextField mouseDBUserNameTF;
    private javax.swing.JTextField mouseDefaultNamespaceTF;
    private javax.swing.JTextField mouseFormatVersionTF;
    private javax.swing.JTextField mouseGroupClassIDTF;
    private javax.swing.JTextField mouseGroupClassNameTF;
    private javax.swing.JTextField mouseGroupClassNamespaceTF;
    private javax.swing.JTextField mouseGroupTermClassIDTF;
    private javax.swing.JTextField mouseGroupTermClassNameTF;
    private javax.swing.JTextField mouseGroupTermClassNamespaceTF;
    private javax.swing.JRadioButton mouseRB;
    private javax.swing.JTextField mouseStageClassIDTF;
    private javax.swing.JTextField mouseStageClassNameTF;
    private javax.swing.JTextField mouseStageClassNamespaceTF;
    private javax.swing.JRadioButton rbDefaultRoot;
    private javax.swing.JRadioButton rbSelectedComponents;
    private javax.swing.JTextArea remarkTA;
    private javax.swing.ButtonGroup rootButtonGroup;
    private javax.swing.JTextField savedByTF;
    private javax.swing.ButtonGroup specieButtonGroup;
    private javax.swing.JRadioButton startendRB;
    private javax.swing.JRadioButton timedcompRB;
    private javax.swing.JTree treeCurrent;
    private javax.swing.JTree treeReferenced;
    private javax.swing.JTextField txtChangedStatus;
    private javax.swing.JTextField txtCurrentFile;
    private javax.swing.JTextField txtEditor;
    private javax.swing.JTextField txtEndStage;
    private javax.swing.JTextField txtGroupPartOf;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPartOf;
    private javax.swing.JTextField txtPrimaryPath;
    private javax.swing.JTextField txtReferenceFile;
    private javax.swing.JTextField txtReport;
    private javax.swing.JTextField txtRulesStatus;
    private javax.swing.JTextField txtStartStage;
    private javax.swing.JTextField txtSynonyms;
    // End of variables declaration//GEN-END:variables
    
}
