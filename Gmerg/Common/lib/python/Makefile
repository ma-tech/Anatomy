# Makefile for HGU Python Library
# ---------------------------------------------------------------------
#
# Makefile performs miscellaneous tasks on the HGU Python library, such 
# as generating documentation for python libraries. 
#
# This makefile doesn't compile anything per se.  Module compilation happens 
# automatically when you first access a module.

# --------------------------------------------------------------------
# VARIABLES

ROOT_DIR = hgu

DOC_DIR = doc

PYTHONPATH := PYTHONPATH=$(PWD); export PYTHONPATH;

# Get all non-CVS directories
DIRS := $(filter-out %/CVS %/CVS/%,$(shell find $(ROOT_DIR) -type d))

# Get all Python files except __init__.py
PYTHON_FILES := $(filter-out %/__init__.py, $(shell find $(ROOT_DIR) -name '*.py'))

# Get complete list without .py extensions
MODULES := $(subst /,.,$(subst .py,,$(PYTHON_FILES)))

PACKAGES := $(subst /,.,$(DIRS))

# PyDoc generation variables

HTMLS := $(foreach MP,$(MODULES) $(PACKAGES),$(DOC_DIR)/$(MP).html)


# --------------------------------------------------------------------
# RULES

# Generate PyDOC documentation for everything.

all : $(DOC_DIR) $(HTMLS)
	@echo
	@echo The HGU Python Library documentation can be found in the $(DOC_DIR) directory.
	@echo

$(DOC_DIR) :
	mkdir $@


# Create files for each module in the very top directory.  These all integrate
# with each other.

$(HTMLS) : $(PYTHON_FILES) # DIRS takes care of itself.
	$(PYTHONPATH) \
	cd $(DOC_DIR); pydoc -w $(MODULES) $(PACKAGES)


# Run the pylint static code analysis checker on all modules.
check:
	$(PYTHONPATH) \
	pylint --rcfile=pylint.rcfile $(MODULES)


# Don't remove doc dir.  It may contain other documentation.
clean: 
	rm $(HTMLS)
