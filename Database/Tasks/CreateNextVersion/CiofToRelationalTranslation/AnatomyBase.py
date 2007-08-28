#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
#-------------------------------------------------------------------
"""
Internal python structures to represent anatomy.

The code here takes adavantage of the CIOF file being ordered in a
very nice fashion.  That is, when you read in something, you can be
confident that everything it references has alaredy been read in.

If this assumption changes then this code will need to be rewritten.
"""

import AnatomyObject
import Attribution
import DbAccess                         # wrapper around DBMS
import Editor
import Node                             # untimed anatomy
import PartOfDerived
import PartOfPerspective
import Perspective
import PerspectiveAmbit
import Relationship
import RelationshipType
import RelationshipTransitive
import Source
import Species
import Stage
import Synonym
import TimedNode                        # timed anatomy
import Version
import Util                             # Error handling


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# LOCAL SUBROUTINES
# ------------------------------------------------------------------


def _dumpObjects(filename, iteratorFunc):
    """
    Dump all objects of a given type to the given file
    """
    dumpFile = open(filename, "w")
    objIter = iteratorFunc()
    for obj in objIter:
        objFields = obj.genDumpFields()
        dumpLine = None
        for field in objFields:
            if field:
                dumpField = field
            else:
                dumpField = "\N"
            if dumpLine:
                dumpLine = dumpLine + "\t" + dumpField
            else:
                dumpLine = dumpField
        dumpFile.write(dumpLine + "\n")
    dumpFile.close()
    return None




# ------------------------------------------------------------------
# KNOWLEDGE BASE
# ------------------------------------------------------------------


def initialiseKnowledge(timeStamp, versionComments, outDir,
                        dbHost, dbName, dbUser, dbPass):
    """
    Initialise something for every object type.
    """

    DbAccess.initialise(outDir, dbHost, dbName, dbUser, dbPass)

    # Read in all anatomy objects.
    AnatomyObject.initialise()

    # Initialise a bunch of reference data that is not explicitly defined
    # in the CIOF file.  These are read from the DB.
    RelationshipType.initialise()
    Version.initialise(timeStamp, versionComments)
    Editor.initialise()
    Source.initialise()
    Species.initialise()

    # Initialise items where we get information from both DB and CIOF.
    Stage.initialise()
    Node.initialise()
    TimedNode.initialise()
    Relationship.initialise()
    Synonym.initialise()
    Attribution.initialise()

    # Initialise items that are entirely defined in the DB,
    # but we have to wait until other information is read from the DB
    # before we can read these.
    Perspective.initialise()
    PerspectiveAmbit.initialise()

    # Initialise derived information
    PartOfDerived.initialise()
    RelationshipTransitive.initialise()
    PartOfPerspective.initialise()


    return None




def addToKnowledge(entity):
    """
    Assign a CIOF entity to the appropriate class
    based on entity type, and then add it to the knowledge base.
    """

    # Build giant case statement to deal with different entity types
    # There must be a better way.

    entityType = entity.getType()

    if entityType in ("Database", "IDRegister", "User",
                      "WM2DView", "WM2DPatch"):
        pass                            # Ignore all these
    elif entityType in ("AnatomicalPart", "Group"):
        anatomyNode = Node.Node(entity, Species.MOUSE)
        anatomyNode.addToKnowledgeBase()
    elif entityType == "LineageAnnotation":
        attribution = Attribution.Attribution(entity)
        if not attribution.isDeleted():
            attribution.addToKnowledgeBase()
    elif entityType == "LineageLink":
        lineageRel = Relationship.Relationship(
            lineageLinkEntity = entity)
        lineageRel.addToKnowledgeBase()
    elif entityType == "Stage":
        # There is one stage entity and it defines all stages
        Stage.addStagesToKnowledgeBase(entity)
    elif entityType == "TC":
        # TC entities, when they exist, contain optional additional
        # information about timed nodes (TC = timed component)
        # Modifiers to start and end stages for anatomy nodes.
        TimedNode.addTcCiofEntityToKnowledgeBase(entity)
    else:
        Util.fatalError(["Unrecognised entity type: '" + entityType + '"'])

    return None


def dealWithTcFallout():
    """
    TC CIOF entries can delete a timed node.  Unfortunately, they occur at the
    very end of the CIOF file, long after much calculation has already been
    done.  After they have been read in, some things need to be undone.

    This routine undoes them.

    A better solution (for this whole program) would be read in the entire
    CIOF file before processing any of it.  That way problems with deleted
    items could be handled at the CIOF level.
    """
    Node.dealWithTcFallout()



def readDb():
    """
    Read data from the relational database.

    This is done after the CIOF file is read in.
    """
    # First read in each object type from DB.  Then figure out differences
    # and do the right thing.

    Stage.readDb()
    Node.readDb()
    TimedNode.readDb()
    Relationship.readDb()
    Synonym.readDb()
    Attribution.readDb()

    Perspective.readDb()
    PerspectiveAmbit.readDb()

    return None


def readRelationshipSequence(relationshipSequenceFile):
    """
    Read in the sequence the tree is supposed to be displayed in.

    relationshipSequenceFile: Either
        Name of file containing sequence information.
        None: In this case sequence information already in the database
              will be used.

    See comments at the top of translateCiof.py for what this routine is
    about.
    """

    if relationshipSequenceFile:
        Relationship.readSequenceFromFile(relationshipSequenceFile)

    else:
        # Use information that has already been read in from the database.
        pass

    return None



def deriveKnowledge(abstractSortOrder):
    """
    Called after all knowledge has been read in from the CIOF file.
    This routine derives additional information, and integrates all
    the information together.
    """
    # Derive the transitive closure of the relationship graph
    RelationshipTransitive.deriveRelationshipsTransitive()

    # Derive the quick tree display
    PartOfDerived.derivePartOf(abstractSortOrder)

    # Derive the part of perspective tree for each perspective
    PartOfPerspective.derivePartOfPerspective()

    return None



def exportKnowledge():
    """
    Exports knowledge and updates to the databse.
    """
    # Generate SQL updates, deletes, and inserts for base tables.
    DbAccess.genClassAllSql(Version.Version, Version.AllIter)
    DbAccess.genClassAllSql(Stage.Stage, Stage.AllIter)
    DbAccess.genClassAllSql(Node.Node, Node.AllIter)
    DbAccess.genClassAllSql(TimedNode.TimedNode, TimedNode.AllIter)
    DbAccess.genClassAllSql(Relationship.Relationship, Relationship.AllIter)
    DbAccess.genClassAllSql(Synonym.Synonym, Synonym.AllIter)
    DbAccess.genClassAllSql(Attribution.Attribution, Attribution.AllIter)
    DbAccess.genClassAllSql(RelationshipTransitive.RelationshipTransitive,
                            RelationshipTransitive.AllIter)
    DbAccess.genClassAllSql(PartOfDerived.PartOfDerived, PartOfDerived.AllIter)
    DbAccess.genClassAllSql(PartOfPerspective.PartOfPerspective,
                            PartOfPerspective.AllIter)

    return None




def genReport(showSynonymOverlapWarnings):
    """
    Perform final analysis on database and report and issues that may need
    attention.
    """
    Node.genNomenclatureReport(showSynonymOverlapWarnings)
    Node.genStageWindowReport()

    return None



def finalise():
    """
    Perform final cleanup and shutdown tasks.
    """
    DbAccess.finalise()

    return None



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Run first time module is loaded.

