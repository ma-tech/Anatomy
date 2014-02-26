
/* ================================================================
 *                Anatomy Database Version 5 Schema Changes
 * ================================================================
 *
 * This is the post-processing schema change file.  It does the final
 * tidying up in the Version 4 to Version 5 conversion.
 *
 * o Post defered log records to ANA_LOG.
 */


select "";
select "!!!!! THIS SCRIPT IS ENTIRELY UNTESTED !!!!!";
select "";

/* ================================================================
 * Post deferred log record to ANA_LOG.
 * ================================================================
 *
 * The schemaPre.sql file creates several log records that can't be
 * inserted into ANA_LOG until after the new ANA_VERSION record is
 * created.  That is created by the applyCiofChanges.sql script so
 * we can now insert the defered log records.
 */

select "Inserting deferred log records." as "";

insert into ANA_LOG
    ( LOG_LOGGED_OID, LOG_VERSION_FK, LOG_COLUMN_NAME, LOG_OLD_VALUE, LOG_COMMENTS )
  select LOG_LOGGED_OID,
         ( select max(VER_OID) from ANA_VERSION),
         LOG_COLUMN_NAME, LOG_OLD_VALUE, LOG_COMMENTS
    from ANA_LOG_CONVERT_TO_5_DEFERRED;

drop table ANA_LOG_CONVERT_TO_5_DEFERRED;
