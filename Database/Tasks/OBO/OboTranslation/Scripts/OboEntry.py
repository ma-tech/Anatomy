#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-1 -*-
"""
Everything after the header in an OBO file is an OBO entry.
This file defines them.
"""

import re
import sets
import sys

from hgu import Util

import OboOntology
import OboLine


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------


# Define entry types

TERM    = "Term"
TYPEDEF = "Typedef"
EOF     = "EOF"


# Define entry types that occur explicitly in the file.
ENTRY_TYPES = sets.Set([TERM, TYPEDEF])


# Define what types of lines can be in each entry type,
# and the min and max arity of each.

MIN_IDX = 0
MAX_IDX = 1

CARDINALITY_1         = (1, 1)
CARDINALITY_0_TO_1    = (0, 1)
CARDINALITY_0_TO_MANY = (0, sys.maxint)

ENTRY_TYPE_TAGS = {
    TERM: {
        OboLine.ID:              CARDINALITY_1,
        OboLine.NAME:            CARDINALITY_1,
        OboLine.RELATIONSHIP:    CARDINALITY_0_TO_MANY,
        OboLine.RELATED_SYNONYM: CARDINALITY_0_TO_MANY,
        OboLine.IS_A:            CARDINALITY_0_TO_MANY,
        OboLine.SUBSET:          CARDINALITY_0_TO_MANY,
        OboLine.DEF:             CARDINALITY_0_TO_1,
        OboLine.NAMESPACE:       CARDINALITY_0_TO_1,
        OboLine.IS_OBSOLETE:     CARDINALITY_0_TO_1
        },
    TYPEDEF: {
        OboLine.ID:          CARDINALITY_1,
        OboLine.NAME:        CARDINALITY_1,
        OboLine.NAMESPACE:   CARDINALITY_0_TO_1,
        OboLine.IS_OBSOLETE: CARDINALITY_0_TO_1
        }
    }


# Define the list of IDs that can occur in Typedef entries.  We need
# to define these because we do different things with them.

DEVELOPS_FROM = "develops_from"
PART_OF       = "part_of"
STARTS_AT     = "starts_at"
ENDS_AT       = "ends_at"

# is_a is not defined as a typedef ID, but rather as a tag in
# term entries.  I don't know any reason for this.
#
# So, should we reproduce this split in our internal
# representation of an OBO ontology, or should we fold is_a
# in with the other relationship types?
#
# Fold it in.

IS_A          = OboLine.IS_A

TYPEDEF_IDS = sets.Set([
    DEVELOPS_FROM,
    PART_OF,
    IS_A,
    STARTS_AT,
    ENDS_AT])



# ------------------------------------------------------------------
# OBO HEADER
# ------------------------------------------------------------------

class OboEntry:
    """
    OBO files contain a series of OBO entries.
    """

    def __init__(self, oboInputStream):
        """
        Build an OBO entry by reading it from a file.
        """
        self.__type = None
        self.__lines = {}
        self.__oboHeader = oboInputStream.getHeader()
        # Next two items are populated after whole ontology read in.
        # List of relationships where the entry is the subject.
        self.__relsWhereSubject = None
        # List of relationships where this entry is the object.
        self.__relsWhereObject = None
        stream = oboInputStream.getInputStream()
        # Read to first line of entry
        textLine = stream.readline()
        while textLine == "\n":
            textLine = stream.readline()

        # Get Entry type
        if textLine == "":                  # at EOF
            self.__type = EOF

        else:
            entryType = re.match("^\[(\w+)\]\n", textLine).groups()[0]
            if entryType not in ENTRY_TYPES:
                Util.fatalError([
                    "Unrecognised entry type '" + entryType +
                    "' in OBO file."])
            self.__type = entryType

            for tag in ENTRY_TYPE_TAGS[entryType]:
                self.__lines[tag] = []

            # Read in the rest of the defs.
            textLine = stream.readline()
            while textLine != "\n" and textLine != "":
                oboLine = OboLine.createLine(textLine)
                tag = oboLine.getTag()
                if tag not in ENTRY_TYPE_TAGS[entryType]:
                    Util.fatalError([
                        "Unexpected tag '" + tag +
                        "' found in entry of type '" + entryType + "'.",
                        "  " + textLine])
                self.__lines[tag].append(oboLine)

                textLine = stream.readline()

            # Reached end of entry def.  Now validate it.
            self.__validate()

        return


    def getType(self):
        """
        Return the entry type of this entry.
        """
        return self.__type


    def getId(self):
        """
        Get the ID of this entry.  Every entry has an ID.
        """
        return self.__lines[OboLine.ID][0].getValue()


    def getName(self):
        """
        Get the name of this entry.  Every entry has a name.
        Names are unique within namespaces.
        """
        return self.__lines[OboLine.NAME][0].getValue()


    def getDef(self):
        """
        Get the definition string of this entry.  If the entry does not
        have a definition string then return None.

        OBO Definition strings are wrapped in quotes "".  I don't know why.
        They also escape embedded quotes with \".
        This returns a version of the string without the wrapping quotes,
        and removes the escpae on the embedded quotes.
        """
        if len(self.__lines[OboLine.DEF]) > 0:
            defString = self.__lines[OboLine.DEF][0].getValue()[1:-1]
            defString = defString.replace('\\"', '"')
        else:
            defString = None
        return defString


    def getNamespace(self):
        """
        Returns the namespace for this entry.

        If the entry does not have an explicit namespace set then the
        default namespace for the ontology is used.
        """
        if len(self.__lines[OboLine.NAMESPACE]) > 0:
            namespace = self.__lines[OboLine.NAMESPACE][0].getValue()
        else:
            namespace = self.__oboHeader.getDefaultNamespace()

        return namespace

    def getObjectIdsInRelsWhereSubject(self, relType):
        """
        For all relationships of the given type where this entry is the subject,
        return the IDs of the entries that are the object tof the relationships.

        Reminder: subject rel object.

        This method can only be called after haveFamilyReunion has been called.
        """
        return self.__relsWhereSubject[relType]


    def getSubjectIdsInRelsWhereObject(self, relType):
        """
        For all relationships of the given type where this entry is the object,
        return the IDs of the entries that are the subject tof the relationships.

        Reminder: subject rel object.

        This method can only be called after haveFamilyReunion has been called.
        """
        return self.__relsWhereObject[relType]

    def getStartsAtStageId(self):
        """
        Returns the ID of the stage where this entry starts at.
        Returns none, if the start stage is not defined.
        """
        stageId = None
        starts = self.getObjectIdsInRelsWhereSubject(STARTS_AT)
        if len(starts) > 1:
            Util.warning([
                "OBO entry ('" + self.getId() + "', '" + self.getName() +"')",
                "has more than one '" + STARTS_AT + "' relationship.",
                "Using first one, ignoring others."])
        if len(starts) >= 1:
            stageId = starts.keys()[0]

        return stageId


    def getEndsAtStageId(self):
        """
        Returns the ID of the stage where this entry ends at.
        Returns none, if the end stage is not defined.
        """
        stageId = None
        ends = self.getObjectIdsInRelsWhereSubject(ENDS_AT)
        if len(ends) > 1:
            Util.warning([
                "OBO entry ('" + self.getId() + "', '" + self.getName() +"')",
                "has more than one '" + ENDS_AT + "' relationship.",
                "Using first one, ignoring others."])
        if len(ends) >= 1:
            stageId = ends.keys()[0]

        return stageId

    def getStageWindowIds(self):
        """
        Return the IDs of the start and stop stages of the given entry.

        If the start or stop is not found, None is returned for that ID.
        """
        startId = self.getStartsAtStageId()
        stopId =  self.getEndsAtStageId()

        return (startId, stopId)


    def isEof(self):
        """
        Returns True if entry is an end of file entry.
        """
        return self.__type == EOF

    def isObsolete(self):
        """
        Returns True if the entry is flagged as obsolete.
        """
        return (len(self.__lines[OboLine.IS_OBSOLETE]) > 0 and
                self.__lines[OboLine.IS_OBSOLETE][0].getValue() == "true")


    def prepareForFamilyReunion(self):
        """
        Prepare for a family reunion by:
          First get a watermelon and a bottle of vodka.
          Then carefully cut a hole in the watermelon exactly the size
            of the mouth of the vodka bottle.  Save the cutout circle.
          Open the vodka bottle and quickly place it in the hole in
            the watermelon.
          Wait a day.  The vodka will slowly be absorbed into the
            watermelon.
          Pull the vodka bottle out and then reinsert the cutout.
          You are ready to go.

        Just kidding.  As anyone who has ever been to one knows, you can't
        really prepare for a family reunion.

        Called prior to having the family reunion to set up necessary
        data structures.

        Initialises the subject and object lists to empty.  They are set to
        None before this so if they are inadvertantly used, they will crash
        the program.
        """
        self.__relsWhereSubject = {}
        self.__relsWhereObject = {}
        for relType in TYPEDEF_IDS:
            self.__relsWhereSubject[relType] = {} # will be indexed by ID
            self.__relsWhereObject[relType] = {}  # ditto

        return



    def haveFamilyReunion(self):
        """
        Called to connect this entry to the other entries it has
        relationships with.

        This is only called after the entire ontology has been read in,
        and after prepareForFamilyReunion has
        """
        # Only terms have family reunions.
        if self.getType() != TYPEDEF:
            # Walk my relationships updating my subject rels and others'
            # object rels.
            for rel in self.__lines[OboLine.RELATIONSHIP]:
                relType = rel.getRelationshipType()
                objectId = rel.getObjectOfRelationshipsId()
                objectEntry = OboOntology.getById(objectId)
                self.__relsWhereSubject[relType][objectId] = objectEntry
                objectEntry.addRelationshipWhereObject(relType, self)

            # pretend like those is_a tags are also relationships.
            for isaRel in self.__lines[OboLine.IS_A]:
                objectId = isaRel.getValue()
                objectEntry = OboOntology.getById(objectId)
                self.__relsWhereSubject[IS_A][objectId] = objectEntry
                objectEntry.addRelationshipWhereObject(IS_A, self)

        return


    def addRelationshipWhereObject(self, relType, subjectEntry):
        """
        This entry is the object of a relationship defined in another entry.
        Add knowledge of that relationship to this entry.
        """
        self.__relsWhereObject[relType][subjectEntry.getId()] = subjectEntry
        return


    def __validate(self):
        """
        Perform validation that can be done by just looking at the entry
        definition.

        Verify that all required tags were present and had the right number
        of occurrences, and that optional tags had the right number of
        occurrences.

        Verify that relationships are types we know what to do with.
        """
        entryType = self.getType()

        # Verify cardinatlities
        for tag, cardinality in ENTRY_TYPE_TAGS[entryType].items():
            count = len(self.__lines[tag])

            if count < cardinality[MIN_IDX] or count > cardinality[MAX_IDX]:
                Util.fatalError([
                    "Tag '" + tag + "' expected to occur between " +
                    str(cardinality[MIN_IDX]) + " and " +
                    str(cardinality[MAX_IDX]) +
                    " times in '" + entryType + "' entries.",
                    "Occurred " + str(count) + " time(s)." ])

        # Verify relationship types.
        if entryType == TERM:
            for rel in self.__lines[OboLine.RELATIONSHIP]:
                relType = rel.getRelationshipType()
                if relType not in TYPEDEF_IDS:
                    Util.fatalError([
                        "Unexpected relationship type '" + relType +
                        "' in '" + entryType + "' definition."
                        ])

        elif entryType == TYPEDEF:
            relId = self.getId()
            if relId not in TYPEDEF_IDS:
                Util.fatalError([
                    "Unexpected id (relationhsip type) '" + relId +
                    "' in '" + entryType + "' definition."
                    ])

        return


    def spew(self, label = ""):
        """
        Debugging routine to show contents of an OBO entry.
        """
        print "========== Spewing Entry:", label, "=============="
        print "Type:", self.getType()

        for lineType, lines in self.__lines.iteritems():
            if len(lines) > 0:
                print lineType, "lines"
                for line in lines:
                    line.spew(label)

        if self.__relsWhereSubject != None and len(self.__relsWhereSubject) > 0:
            print "Subject rels:"
            for relType, objectEntries in self.__relsWhereSubject.iteritems():
                for entryId in objectEntries:
                    print self.getId(), relType, entryId

        if self.__relsWhereObject != None and len(self.__relsWhereObject) > 0:
            print "Object rels:"
            for relType, subjectEntries in self.__relsWhereObject.iteritems():
                for entryId in subjectEntries:
                    print entryId, relType, self.getId()

        return

