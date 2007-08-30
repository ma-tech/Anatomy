#
# Makefile to run various scripts against the anatomy database.
#
USAGE = gmake DB="-u username -p -h hostname dbname" target


# ===================================================================
# VALIDATION
# ===================================================================

ifndef $(DB)
NoDbDefined :
	@echo
	@echo ERROR Missing DB argument.  Usage:
	@echo "  " '$(USAGE)'
	@echo
	exit -1
endif


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


# ===================================================================
# RULES
# ===================================================================


# Force the caller to provide a target
all :
	echo 'Please specify a target.'



# Dump the anatomy database

dump :
	mysqldump --single-transaction --routine --add-drop-table $(DB) $(TABLES) > ../../Versions/Version004/Dumps/mySqlSchemaAndDataDump.sql


validate :
	mysql $(DB) < validation.sql


# Empty all anatomy database tables.  That is, delete all records in all tables.

empty : $(TABLES_EMPTY)

$(TABLES_EMPTY) :
	echo 'Deleting all records in database table $(subst .empty,,$@).'
	echo 'delete from $(subst .empty,,$@)' | mysql $(DB)
