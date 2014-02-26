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



PLEASE READ THE FOLLOWING DOCUMENT FOR A FULL DESCRIPTION OF THIS REPOSITORY COMPONENTS!

Anatomy/Documentation/TheHGUAnatomyDatabase_2013-05-01.doc



The HGU Anatomy Database

Mike Wicks’ Developments by 26th February 2014


Executive Summary

NOTA BENE

This GIT Repository is hosted on MAHOST ( git@mahost:Anatomy ) as well as GITHUB ( https://github.com/ma-tech/Anatomy.git  - which EXCLUDES the /Anatomy/Database folder)


Anatomy Update Processing

A combination of Java and Python programs are used to update the Anatomy database, whenever a new version is required.

New Folder structures must first be added to GIT each time a new version of the database is required.

There are a number of steps to producing a new version of the database:

0. Create Folder Structure for New Version
1. Create an Empty Anatomy Database & Populate with Base Data
2. Load and Validation of an OBO file against the Empty Database
3. Update of the Database (produces new EMAP(A) terms and amends existing terms)
4. Produce a new OBO file from the updated database
5. Produce SQL queries to populate derived tables
6. Apply SQL derived table queries to the database
7. Produce new Tree Reports
8. Produce SQL queries to create Materialised View tables
9. Apply SQL materialised view queries (long running).

All program code is in GIT under “Anatomy” – BOTH Java and PYTHON.

These steps are completed using the following programs:

1. Anatomy/SourceCode/Java/DAOAnatomyRebuild
Main0UpdateDatabaseWithBaseData.jar

2. Anatomy/SourceCode/Java/DAOAnatomyRebuild
Main1LoadOBOFileIntoComponentsTablesAndValidate.jar

3. Anatomy/SourceCode/Java/DAOAnatomyRebuild
Main2UpdateDatabaseFromComponentsTables.jar

4. Anatomy/SourceCode/Java/DAOAnatomyRebuild
Main3UpdateDatabaseWithPerspectiveAmbits.jar

5. Anatomy/SourceCode/Python/CreateNextVersion/RegenerateDerivedData
regenerateDerivedData.py

6. Anatomy/Database/Tasks/ConvertFromMouseXXX/SQLFiles/PostProcessing
replaceDerivedData.sql

7. Anatomy/SourceCode/Python/CreateNextVersion/GenerateTreeFormats
generateTreeReports.py

8. Anatomy/Database/Tasks/ConvertFromMouseXXX/Scripts
AdditionalProcessing.sh

9. Anatomy/Database/Tasks/ConvertFromMouseXXX/Scripts
AdditionalProcessing.sh (long running!)


Java Programs are used to achieve the Basic Load, Validation and Update Steps.  These programs are executed at the command line, and require 2 Properties files to be updated to produce the desired results.

Python programs reproduce the Derived Data Tables (“ANAD_...”) and Tree Reports (Abstract and Timed, in Text, RTF and XML formats). Again, these programs are executed at the command line, and require a Properties file to be updated to produce the desired results.

MySQL Stored Procedures combined with Views (ANAV_ …) in the Database, are used to generate the Materialised Views (ANAM_ ….).  This is a very long running task, regrettably.


Python was used for the initial update processing, with the remaining programs used for non-update requirements.

The Java processing was inherited from a Java Swing (GUI) application, with major refactoring of the business logic, implementation of a new Database Access Object layer (DAO), and then GUI thrown away in favour of a command line approach.



The Technical Bit


The Structure and Contents of the GIT Database Repository are now discussed


Anatomy GIT Repository

There are a number of top level folders in the Anatomy GIT Repository:


Sub Project
Description



1
Database
Anatomy Database Versions and Tasks

2
SourceCode/
DataScratchArea
A working are for testing new versions of the Anatomy database

2
SourceCode/
Python/
Gmerg
Anatomy Data Access Layer in PYTHON



3
SourceCode/
Java/
DAOAnatomyJavaLayer
Anatomy MySQL Database Access Objects (DAO) Layer


4
SourceCode/
Java/
DAOAnatomyOBO
Anatomy OBO File Access Layer



5
SourceCode/
Java/
DAOAnatomyRebuild
Command Line Java Programs




6
SourceCode/
Java/
DAOAnatomyJSP
A JSP Application for AJAX Tree Browse in EMAP


7
SourceCode/
Java/
JavaLibraries
Anatomy Data Access Layer required Java Jar Files


8
SourceCode/
Java/
Utilities
Supporting Java Static Classes



9
SourceCode/
Java/
DAOAnatomyDatabaseJSF
OBSOLETE

A “Test” JSF Application for Browsing the Anatomy Database


10
SourceCode/
Java/
DAOAnatomyUploadJSF
OBSOLETE

A “Test” JSF Application for Validating Candidate OBO Files

11
Notes
Documentation

12
SourceCode/
Properties
Properties files

Used to direct the processing of ALL Java Programs



Nota Bene

All of the Java Anatomy Sub Folders can be imported into Eclipse, and run from there directly.  All Python code is run outside Eclipse

These Folders will now be discussed, in turn:



Database

GIT Location: Anatomy/Database


This is where all “Tasks” and “Versions” of the anatomy database are stored – the names of the folder structure should be sufficient to clearly identify the purpose of the sub-folder contents.

This Folder and its sub-folders are NOT ON GITHUB!


Gmerg

GIT Location: Anatomy/SourceCode/Python/Gmerg/Common/lib/python/hgu


This is where the Python Anatomy Database Access Layer is stored.

Should any Schema changes to the anatomy database be required, consequent changes to the Python Database Access layer may be required to reflect this.



DAOAnatomyJavaLayer

GIT Location: Anatomy/SourceCode/Java/DAOAnatomyJavaLayer/src


Source Code folder for all Java Anatomy Database Access Layer Classes

No Programs are executed here!  Any Main Classes found here are either obsolete or exist for testing purposes only.


Sub Folders

daomodel
	Row-level objects for ALL anatomy database tables and JOINs
daointerface
	A Promise for all row-level objects to implement data retrieval methods
daojdbc
	An implementation of the promises using JDBC
daolayer
	Classes that do the “Heavy Lifting“ in SQL
app
	Candidate example classes that access the database
csvmodel
	Classes that enable the data to be converted to CSV format
main
	Main classes that execute the candidate classes
test
	Some Junit testing classes



DAOAnatomyOBO

GIT Location: Anatomy/SourceCode/Java/DAOAnatomyOBO/src/


Source Code folder for all Anatomy OBO File Access Classes

No Programs are executed here! Any Main Classes found here are either obsolete or exist for testing purposes only.


Sub-Folders

oboaccess
	Higher level OBO File access class
obomodel
	Component-level objects for ALL objects in an OBO Text File
obolayer
	Classes that do the “Heavy Lifting“ to and from the OBO Text File
routines
	A Library of classes to access an Anatomy Database and an OBO File
routines/database
	access to database tables for supplied lists; logging implemented
app
	Candidate example classes that access the OBO file
main
	Main classes that execute the candidate classes



DAOAnatomyRebuild

GIT Location: Anatomy/SourceCode/Java/DAOAnatomyRebuild/src/ – Source Code folder for all DAO Anatomy Database access Classes


Sub-Folders

app
	Candidate example classes that access the OBO file and database
main
	THE MAIN CLASSES for the LOAD & VALIDATE, UPDATE, and EXTRACT processes for the Anatomy Database:

		Main0UpdateDatabaseWithBaseData.java
		Main1LoadOBOFileIntoComponentsTablesAndValidate.java
		Main2UpdateDatabaseFromComponentsTables.java
		Main3UpdateDatabaseWithPerspectiveAmbits.java
		Main4ExtractAndWriteOBOFromExistingDatabase.java

routines/aggregated 
	a suite of functions
routines/runnable
	a runnable suite of functions



DAOAnatomyJSP

GIT Location: Anatomy/SourceCode/Java/DAOAnatomyJSP


This is the Project has been implemented to support the AJAX Tree Calls, used here:
http://www.emouseatlas.org/emap/ema/DAOAnatomyJSP/anatomy.html

Ajax calls are implemented by 7 Servlets, stored in Anatomy/SourceCode/Java/DAOAnatomyJSP/Server/src/controller 

The Web Application is defined in 
Anatomy/SourceCode/Java/DAOAnatomyJSP/Server/WEB-INF/web.xml
(Database access parameters may be modified there)




JavaLibraries


GIT Location: Anatomy/JavaLibraries

A folder for ALL required Java Jars in the Anatomy GIT Repository



Utilities

GIT Location: Anatomy/SourceCode/Java/Utilities/src


Sub-Folders

utility
	Utility classes for universal use
testapp
	test harness classes for all utility classes




DAOAnatomyDatabaseJSF

GIT Location: Anatomy/SourceCode/Java/DAOAnatomyDatabaseJSF


OBSOLETE - A Test Java Server Faces Web Application



DAOAnatomyUploadJSF

GIT Location: Anatomy/SourceCode/Java/DAOAnatomyUploadJSF


OBSOLETE - A Test Java Server Faces Web Application



Documentation

GIT Location: Anatomy/Documentation


Where this document resides.

Also includes:
Tables.xls – A Description of ALL the tables in the Anatomy Database Schema



Properties

GIT Location: Anatomy/SourceCode/Properties


The location of the Properties files that are used to drive proceessing of the Database and OBO files.


dao.properties.input
	A properties file that specifies the access parameters to an Anatomy Database

These parameters are accessed using a Major Key, allowing multiple instances of parameters to be stored and accessed

Parameter
Meaning


url
Which database do you intend to access?

driver
The MySQL JDBC driver class

username


password


debug
“True” or “False”?

True spits out ALL the SQL statements before they are executed

update

“True” or “False”?

Update the Database, Yes or No

sqloutput

A log file location for all SQL statements

Blank means System.Out

msglevel
1 to 5 Stars

Minimal to maximum possible debug statements to be printed out to System.Out

access
“JDBC”

is the only available option here, with no other methods implemented yet (eg. Hibernate etc)




obo.properties
	A properties file that specifies the access parameters to an OBO File

These parameters are accessed using a Major Key, allowing multiple instances of parameters to be stored and accessed

Parameter
Meaning


obobasefile

A Base OBO File Path

Used as an OBO representation of the database, and provides a swifter way of validating any proposed Input OBO file

oboinfile
Input OBO File Path

obooutfile
Output OBO File Path

summaryreport
Validation Report File Path

obooutfileremark
Output OBO File field value

obooutfileversion
Output OBO File field value

obooutfilenamespace
Output OBO File field value

obooutfilesavedby
Output OBO File field value

species
mouse, chick or human?

project
“EMAP” or “GUDMAP”?

abstractclassid
The OBO File must include a Abstract Class

abstractclassnamespace
The OBO File must include a Abstract Class

abstractclassname
The OBO File must include a Abstract Class

stageclassname
The OBO File must include a Stage Class

stageclassid
The OBO File must include a Stage Class

stageclassnamespace
The OBO File must include a Stage Class

groupclassname
The OBO File must include a Group Class

groupclassid
The OBO File must include a Group Class

groupclassnamespace
The OBO File must include a Group Class

grouptermclassname
The OBO File must include a Group Term Class

grouptermclassid
The OBO File must include a Group Term Class

grouptermclassnamespace
The OBO File must include a Group Term Class

minstagesequence
For this Species, what is the minimum stage sequence number?

maxstagesequence

For this Species, what is the maximum stage sequence number?

alternatives
“True” or ”False”

Do you want Alternative IDs in your Output OBO File?

timedcomponents
“True” or ”False”

Do you want Timed Component IDs in your Output OBO File?

generateidentifiers
“True” or ”False”

Do you want the program to generate IDs for any new components, or use the values as supplied by you?

debug
“True” or ”False”?

msglevel
1 to 5 Stars

Minimal to maximal possible debugging statements to be printed out to System.Out




