#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
****************************************
THIS SCRIPT IS NOW OBSOLETE

REPLACED BY DB2OBO METHOD OF DATA ENTRY

MNW, JULY 2009

****************************************


Basic Definitions related to CIOF.

This defines attributes and entities.
"""

import datetime
import Util                             # error reporting


# ------------------------------------------------------------------
# constants / REFERENCE DATA
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# CIOF_ATTRIBUTE CLASS
# ------------------------------------------------------------------

class CiofAttribute:
    """
    CiofEntities have multiple attibutes, which each have a name and
    a list of values.
    """

    def __init__(self, attrName, valuesString):
        """
        Given the name and a text string containing a list of values, build a
        CiofAttribute.
        """

        self.__attributeName = attrName

        # parse comma separated values list into a python list.
        blanksAreBad = lambda value: value.strip()
        self.__values = map(blanksAreBad, valuesString.split(","))

        # some values are actually compound with a name and a value.
        # Not going to split them up just yet.

        return None


    def getValues(self):
        """
        Return list of values from the attribute
        """
        return self.__values



    def getValue(self):
        """
        Get attribute value (note singular).

        Throws exception if attribute has more than one value.
        """
        values = self.getValues()
        if len(values) > 1:
            Util.fatalError([
                "Attempt to treat a multi-valued attribute " +
                "as a single valued attribute.",
                "Attribute Name: '" + self.__attributeName + "'"])
        else:
            return values[0]





# ------------------------------------------------------------------
# CIOF_ENTITY CLASS
# ------------------------------------------------------------------

class CiofEntity:
    """
    ciof streams are very clearly delineated into separate entities.
    """

    def __init__(self, entityType, entityId):
        """
        Initialise a CIOF entity.
        """
        self.__type = entityType
        self.__id = entityId
        self.__attributes = {}

        return None

    def getId(self):
        """
        Get the ID of this CIOF entity.
        """
        return self.__id

    def getType(self):
        """
        Return the type of this CIOF entity.
        """
        return self.__type

    def getAttribute(self, attrName):
        """
        Get attribute by attribute name.

        Returns none if attribute does not exist.
        """
        if attrName in self.__attributes:
            return self.__attributes[attrName]
        else:
            return None


    def getAttributeValue(self, attrName):
        """
        Get attribute value (note singular) by attribute name.

        Throws exception if attribute has more than one value.
        Returns None if attribute does not exist.
        """
        attribute = self.getAttribute(attrName)
        if attribute:
            return attribute.getValue()
        else:
            return None


    def getAttributeValues(self, attrName):
        """
        Get attribute values by attribute name

        Returns empty list if attribute name does not exist.
        """
        attribute = self.getAttribute(attrName)
        if attribute:
            return attribute.getValues()
        else:
            return []



    def getAttributeValuesJoined(self, attrName):
        """
        Compensate for inconsistent usage of commas in CIOF files.

        Sometimes they
        separate multiple values, and other times they are just a part
        of a text description.  This routine is called to return attribute
        values that can contain embedded commas as a single attribute value.
        Returns none if attribute name does not exist.
        """
        attribute = self.getAttribute(attrName)
        joined = None
        if attribute:
            def concat(part1, part2):
                "Comma concat two values."
                return part1 + ", " + part2
            joined = reduce(concat, attribute.getValues())
        return joined



    def getCreationDateTime(self):
        """
        Return the creation date time of the entity, if it has one.

        Reformat posix time (time since 1970) into python datetime.
        Returns None if entity does not have a creation date time.
        """
        cdt = self.getAttributeValue("CreationTime")
        if cdt:
            return datetime.datetime.fromtimestamp(int(cdt))
        else:
            return None


    def getModificationDateTime(self):
        """
        Return the last modification date time of the entity, if it has one.

        Even if it does have one, it is unreliable.

        Reformat posix time (time since 1970) into python datetime.
        Returns None if entity does not have a creation date time.
        """
        mdt = self.getAttributeValue("LastModificationTime")
        if mdt:
            return datetime.datetime.fromtimestamp(int(mdt))
        else:
            return None


    def addAttribute(self, attrName, values):
        """
        Creates a new attribute and adds it to the entity.

        Does not do any content checking to make sure this attribute is
        valid for the entity type.
        Returns the newly created attribute.
        """
        if attrName in self.__attributes:
            Util.fatalError([
                "Attribute '" + attrName + "' already exists for",
                "Entity: type: '" + self.getType() + "', ID: '" +
                self.getId() + "'"])

        attrib = CiofAttribute(attrName, values)
        self.__attributes[attrName] = attrib

        return attrib


    def isDeleted(self):
        """
        Return True if CIOF entity is flagged as deleted, False otherwise.
        """
        deleted = False
        if self.getAttributeValue("Deleted") == "True":
            deleted = True

        return deleted



# ------------------------------------------------------------------
# MAIN / GLOBALS
# ------------------------------------------------------------------

# Called first time module is loaded.
