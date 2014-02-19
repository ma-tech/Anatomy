## Welcome to the Anatomy Repository

This repository is the home of all HGU IGMM code for manipulating Anatomy Databases.

The code consists of:

1. Java Classes, including a full Java DAO (Data Access Object layer) ( on 
top of MySQL ), influenced by BalusC 
( eg. http://balusc.blogspot.co.uk/2008/07/dao-tutorial-data-layer.html )

2. Python Classes, again another DAO in Python; these rebuild the database's derived
data tables; and produce many reports in RTF, TXT and XML


The Database itself is implemented in MySQL, and is a realisation of a Directed Acyclic
Graph (DAG), as a Tree stored in Relational form.

The Database has the following "Core" tables:
EDITOR, SOURCE, STAGE, NODE, TIMED_NODE, RELATIONSHIP, SYNONYM, VERSION, PERSPECTIVE_AMBIT

Each of these Core Tables has a unique Surrogate Key, which is stored in the OBJECT table.

Of these Core Tables, STAGE, NODE, TIMED_NODE, RELATIONSHIP, provide the main structure 
to the database.  Nodes are Related to Other Nodes through the Relationship Table; Nodes 
exist for a range of Stages, and for each stage in the range, a Timed Node exists.

