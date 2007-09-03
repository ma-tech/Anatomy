begin work;
alter table ANA_TIMED_NODE drop foreign key ANA_TIMED_NODE_ibfk_1; show warnings;

alter table ANA_TIMED_NODE drop foreign key ANA_TIMED_NODE_ibfk_4; show warnings;

alter table ANA_TIMED_NODE drop key ATN_AK2_INDEX; show warnings;

select 'Running ANA_OBJECT.inserts.sql';
source ANA_OBJECT.inserts.sql;

select 'Running ANA_VERSION.inserts.sql';
source ANA_VERSION.inserts.sql;

select 'Running ANA_STAGE.inserts.sql';
source ANA_STAGE.inserts.sql;

select 'Running ANA_NODE.inserts.sql';
source ANA_NODE.inserts.sql;

select 'Running ANA_TIMED_NODE.inserts.sql';
source ANA_TIMED_NODE.inserts.sql;

select 'Running ANA_RELATIONSHIP.inserts.sql';
source ANA_RELATIONSHIP.inserts.sql;

select 'Running ANA_SYNONYM.inserts.sql';
source ANA_SYNONYM.inserts.sql;

select 'Running ANA_ATTRIBUTION.inserts.sql';
source ANA_ATTRIBUTION.inserts.sql;

select 'Running ANA_LOG.inserts.sql';
source ANA_LOG.inserts.sql;

select 'Running ANA_VERSION.updates.sql';
source ANA_VERSION.updates.sql;

select 'Running ANA_OBJECT.updates.sql';
source ANA_OBJECT.updates.sql;

select 'Running ANA_STAGE.updates.sql';
source ANA_STAGE.updates.sql;

select 'Running ANA_NODE.updates.sql';
source ANA_NODE.updates.sql;

select 'Running ANA_TIMED_NODE.updates.sql';
source ANA_TIMED_NODE.updates.sql;

select 'Running ANA_RELATIONSHIP.updates.sql';
source ANA_RELATIONSHIP.updates.sql;

select 'Running ANA_SYNONYM.updates.sql';
source ANA_SYNONYM.updates.sql;

select 'Running ANA_ATTRIBUTION.updates.sql';
source ANA_ATTRIBUTION.updates.sql;

select 'Running ANA_LOG.updates.sql';
source ANA_LOG.updates.sql;

select 'Replacing ANAD_PART_OF';
delete from ANAD_PART_OF;
select 'Running ANAD_PART_OF.inserts.sql';
source ANAD_PART_OF.inserts.sql;

select 'Replacing ANAD_RELATIONSHIP_TRANSITIVE';
delete from ANAD_RELATIONSHIP_TRANSITIVE;
select 'Running ANAD_RELATIONSHIP_TRANSITIVE.inserts.sql';
source ANAD_RELATIONSHIP_TRANSITIVE.inserts.sql;

select 'Running ANA_LOG.deletes.sql';
source ANA_LOG.deletes.sql;

select 'Running ANA_ATTRIBUTION.deletes.sql';
source ANA_ATTRIBUTION.deletes.sql;

select 'Running ANA_SYNONYM.deletes.sql';
source ANA_SYNONYM.deletes.sql;

select 'Running ANA_RELATIONSHIP.deletes.sql';
source ANA_RELATIONSHIP.deletes.sql;

select 'Running ANA_TIMED_NODE.deletes.sql';
source ANA_TIMED_NODE.deletes.sql;

select 'Running ANA_NODE.deletes.sql';
source ANA_NODE.deletes.sql;

select 'Running ANA_STAGE.deletes.sql';
source ANA_STAGE.deletes.sql;

select 'Running ANA_OBJECT.deletes.sql';
source ANA_OBJECT.deletes.sql;

select 'Running ANA_VERSION.deletes.sql';
source ANA_VERSION.deletes.sql;

select 'Running orderingUpdates.sql';
source orderingUpdates.sql;


alter table ANA_TIMED_NODE add constraint unique ATN_AK2_INDEX (ATN_NODE_FK,ATN_STAGE_FK); show warnings;

alter table ANA_TIMED_NODE add constraint ANA_TIMED_NODE_ibfk_1 foreign key (`ATN_STAGE_FK`) REFERENCES ANA_STAGE (STG_OID) ON DELETE NO ACTION ON UPDATE NO ACTION; show warnings;

alter table ANA_TIMED_NODE add CONSTRAINT ANA_TIMED_NODE_ibfk_4 FOREIGN KEY (ATN_NODE_FK) REFERENCES ANA_NODE (ANO_OID) ON DELETE NO ACTION ON UPDATE NO ACTION; show warnings;

commit work;
