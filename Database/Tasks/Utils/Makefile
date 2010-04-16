#
# Makefile to run various scripts against the anatomy database.
#
USAGE1 = gmake DB="-u username -p -h hostname dbname" target
TARGETS1 = Valid targets are:
TARGETS2 = "  dump:     Create a dump of the database."
TARGETS3 = "  empty:    Delete all data from all anatomy database tables."
TARGETS4 = "  drop:     Drop all anatomy database tables."
TARGETS5 = "  validate: Run validation script against database."



# ===================================================================
# VARIABLES
# ===================================================================

# Define all tables in anatomy database.  These are listed in FK order.

TABLES = \
  ANAD_PART_OF_PERSPECTIVE     \
  ANAD_PART_OF                 \
  ANAD_RELATIONSHIP_TRANSITIVE \
  ANA_ATTRIBUTION              \
  ANA_DELETED_PUBLIC_ID        \
  ANA_DELETE_REASON            \
  ANA_EVIDENCE                 \
  ANA_LOG                      \
  ANA_NODE_IN_PERSPECTIVE      \
  ANA_PERSPECTIVE_AMBIT        \
  ANA_PERSPECTIVE              \
  ANA_RELATIONSHIP             \
  ANA_RELATIONSHIP_TYPE        \
  ANA_REPLACED_BY              \
  ANA_SOURCE                   \
  ANA_SOURCE_FORMAT            \
  ANA_SYNONYM                  \
  ANA_TIMED_NODE               \
  ANA_NODE                     \
  ANA_STAGE_MODIFIER           \
  ANA_STAGE                    \
  ANA_EDITOR                   \
  ANA_VERSION                  \
  ANA_OBJECT                   \
  REF_SPECIES

TABLES_EMPTY = $(foreach TABLE,$(TABLES),$(TABLE).empty)
TABLES_DROP  = $(foreach TABLE,$(TABLES),$(TABLE).drop)


DUMP_FILE = ../../Versions/Version006/Formats/Dumps/mySqlSchemaAndDataDump.sql
VALIDATION_LOG_FILE = ../../Versions/Version006/Doc/Data/validation.log



# ===================================================================
# RULES
# ===================================================================


# Force the caller to provide a target
all :
	@echo
	@echo 'Please provide a specific target.'
	@echo $(TARGETS1)
	@echo $(TARGETS2)
	@echo $(TARGETS3)
	@echo $(TARGETS4)
	@echo $(TARGETS5)
	@echo

# Dump the anatomy database

dump : DbCheck
	mysqldump --single-transaction --routine --add-drop-table \
	$(DB) $(TABLES) > $(DUMP_FILE)


# Check database against test battery.

validate : DbCheck
	mysql $(DB) < validation.sql > $(VALIDATION_LOG_FILE)
	cat $(VALIDATION_LOG_FILE)


# Empty all anatomy database tables.  That is, delete all records in all tables.

empty : DbCheck $(TABLES_EMPTY)

$(TABLES_EMPTY) :
	@echo '*** Deleting all records in database table $(subst .empty,,$@). ***'
	echo '"delete from $(subst .empty,,$@);"' | mysql $(DB)
	@echo


# Drop all anatomy database tables.

drop : DbCheck $(TABLES_DROP)

$(TABLES_DROP) :
	@echo '*** Dropping database table $(subst .drop,,$@). ***'
	echo '"drop table $(subst .drop,,$@);"' | mysql $(DB)
	@echo



# =====================================================================
# Makefile Parameter Checking
# =====================================================================

# :BUG: Tried using ifdef/ifndef here, but it did not work.

DbCheck :
ifneq ($(DB),)

else
	@echo ERROR Missing DB argument.  Usage:
	@echo "  " '$(USAGE1)'
	@echo "  " $(TARGETS1)
	@echo "  " $(TARGETS2)
	@echo "  " $(TARGETS3)
	@echo "  " $(TARGETS4)
	@echo "  " $(TARGETS5)
	@echo
	exit -1
endif

