begin work;

-- delete the sequence column in ANA_RELATIONSHIP
--  NB. Sequence data is now maintained in ANA_RELATIONSHIP_PROJECT
--
ALTER TABLE ANA_RELATIONSHIP
  DROP COLUMN REL_SEQUENCE;

commit work;