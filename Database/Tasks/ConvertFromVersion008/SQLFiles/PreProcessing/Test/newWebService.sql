
SELECT a.ATN_PUBLIC_ID, b.ANO_PUBLIC_ID, c.STG_NAME, c.STG_SEQUENCE
FROM ANA_TIMED_NODE a
JOIN ANA_NODE b ON b.ANO_OID = a.ATN_NODE_FK
JOIN ANA_STAGE c ON c.STG_OID = a.ATN_STAGE_FK
WHERE b.ANO_PUBLIC_ID = 'EMAPA:27678'
AND c.STG_SEQUENCE = 20

CREATE TABLE ANA_TIMED_NODE (
  ATN_OID int(10) unsigned NOT NULL,
  ATN_NODE_FK int(10) unsigned NOT NULL,
  ATN_STAGE_FK int(10) unsigned NOT NULL,
  ATN_STAGE_MODIFIER_FK varchar(20) DEFAULT NULL,
  ATN_PUBLIC_ID varchar(20) NOT NULL
  
CREATE TABLE ANA_NODE (
  ANO_OID int(10) unsigned NOT NULL,
  ANO_SPECIES_FK varchar(20) NOT NULL,
  ANO_COMPONENT_NAME varchar(255) NOT NULL,
  ANO_IS_PRIMARY tinyint(1) NOT NULL,
  ANO_IS_GROUP tinyint(1) NOT NULL COMMENT 'True if the node is a group node, False if it is not.',
  ANO_PUBLIC_ID varchar(20) NOT NULL,
  ANO_DESCRIPTION varchar(2000) DEFAULT NULL COMMENT 'Description of this component.  Can be NULL.'
  

CREATE TABLE ANA_STAGE (
  STG_OID int(10) unsigned NOT NULL,
  STG_SPECIES_FK varchar(20) NOT NULL,
  STG_NAME varchar(20) NOT NULL,
  STG_SEQUENCE int(10) unsigned NOT NULL,
  STG_DESCRIPTION varchar(2000) DEFAULT NULL COMMENT 'Description of stage.  Alternatively, could replace this with a URL.',
  STG_SHORT_EXTRA_TEXT varchar(25) DEFAULT NULL COMMENT 'Very short additional text describing the stage.  This is useful when real estate is tight but you still have enough space to give the user some additional information besides just the somtimes uninformative stage name.  For mouse, this will likely be an ',
  STG_PUBLIC_ID varchar(20) DEFAULT NULL COMMENT 'Public ID of stage.  Null if stage does not have a public ID.'


http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27678&stageSeq=18
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27678&stageSeq=19
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27678&stageSeq=20
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27678&stageSeq=21

http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=20
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=21
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=22
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=23
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=24
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=25
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=26
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=27
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27829&stageSeq=28

http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=12345&stageSeq=
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=&stageSeq=12
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=&stageSeq=
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=1234&stageSeq=12
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=123456&stageSeq=12
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=ABCDE&stageSeq=12
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=12345&stageSeq=123
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=12345&stageSeq=AB
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=12345&stageSeq=28


Error! Node EMAPA:27678 is UNKNOWN at Stage Sequence 18
Success! Node EMAPA:27678 is EMAP:27679 at Stage Sequence 19
Success! Node EMAPA:27678 is EMAP:27680 at Stage Sequence 20
Error! Node EMAPA:27678 is UNKNOWN at Stage Sequence 21

Error! Node EMAPA:27829 is UNKNOWN at Stage Sequence 20
Success! Node EMAPA:27829 is EMAP:27830 at Stage Sequence 21
Success! Node EMAPA:27829 is EMAP:27831 at Stage Sequence 22
Success! Node EMAPA:27829 is EMAP:27832 at Stage Sequence 23
Success! Node EMAPA:27829 is EMAP:27833 at Stage Sequence 24
Success! Node EMAPA:27829 is EMAP:27834 at Stage Sequence 25
Success! Node EMAPA:27829 is EMAP:30316 at Stage Sequence 26
Error! Node EMAPA:27829 is UNKNOWN at Stage Sequence 27

Please enter a Stage Sequence.
Please enter an EMAPA Id.
Please enter an EMAPA Id.
EMAPA Id should be at least 5 Digits long!
EMAPA Id should be at least 5 Digits long!
EMAPA Id should be Digits ONLY!
Stage Sequence cannot be more than 2 Digits!
Stage Sequence should be Digits ONLY!
Stage Sequence cannot be more than 27!


http://localhost:8080/DAOAnatomyJSP/listbytimedrootnamejsononly?rootName=EMAP:25766&stage=TS01

http://localhost:8080/DAOAnatomyJSP/listbyrootnamejsononly?rootName=EMAPA:25765


=> EMAPA:25678 at TS21
http://localhost:8080/DAOAnatomyJSP/findemapbyemapaidandstage?publicEmapaId=27678&stageSeq=20
=> EMAP:27680 at TS21
http://localhost:8080/DAOAnatomyJSP/findleafsbyemapandstage?rootName=27680&stage=TS21
