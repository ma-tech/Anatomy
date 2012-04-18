#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
"""
Wrapper around the original image data from the relational databases.
"""

from hgu.db import DbAccess                         # DB Connection.
from hgu.db import DbRecord                         # DB Records
from hgu.db import DbTable                          # DB Tables


from hgu.gmergDb import RefUrlDb


# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

TABLE_NAME = "ISH_ORIGINAL_IMAGE"

OID_COLUMN           = "IMG_OID"
SUBMISSION_FK_COLUMN = "IMG_SUBMISSION_FK"
FILEPATH_COLUMN      = "IMG_FILEPATH"
FILENAME_COLUMN      = "IMG_FILENAME"
PUBLIC_COLUMN        = "IMG_IS_PUBLIC"

# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_table = None
_imageUrlDbRec = None


# ------------------------------------------------------------------
# ORIGINAL IMAGE DB RECORD
# ------------------------------------------------------------------

class IshOriginalImageDbRecord:
    """
    Python wrapper around DbRecord containing a Original Image database record.

    NOTE: This is not a complete wrapper.  It only provides access to the data
    that we actually need.
    """

    def __init__(self, dbRecord):
        """
        Create Python wrapper around DbRecord containing a
        Original Image database record.
        """
        self.__dbRecord = dbRecord
        return None

    def getDbRecord(self):
        """
        Return the DB record for this original image.
        """
        return self.__dbRecord

    def getOid(self):
        """
        Return the OID for this original image DB record.
        """
        return self.getDbRecord().getColumnValue("IMG_OID")

    def getSubmissionOid(self):
        """
        Get the OID of the submission record this image is associated with.
        """
        return self.getDbRecord().getColumnValue("IMG_SUBMISSION_FK")

    def getFilePath(self):
        """
        Get the file path of the image.  This is relative to the URL for images
        in REF_URL.
        """
        return self.getDbRecord().getColumnValue("IMG_FILEPATH")

    def getFileName(self):
        """
        Return the name of image file.
        """
        return self.getDbRecord().getColumnValue("IMG_FILENAME")

    def getUrl(self):
        """
        Return the full URL of the image.
        """
        return (
            _getImageUrlDbRec().getUrl() + self.getFilePath() + self.getFileName())

    def getThumbnailUrl(self):
        """
        Return the full URL of the thumbnail.
        """
        imageUrlDbRec = _getImageUrlDbRec()
        return (
            imageUrlDbRec.getUrl() + self.getFilePath() +
            imageUrlDbRec.getSuffix() + self.getFileName())


    def getPublic(self):
        """
        Return the Public Flag.
        """
        return self.getDbRecord().getColumnValue(PUBLIC_COLUMN)


    def setPublic(self, public):
        """
        Set the value (the note) of this record.
        """
        return self.getDbRecord().setColumnValue(PUBLIC_COLUMN, public)


    def insert(self):
        """
        Generate insert statement to add this record to the database.
        """
        self.getDbRecord().insert()

        return


    def update(self):
        """
        Generate update statement to update this record in the database.
        """
        self.getDbRecord().update()



# ------------------------------------------------------------------
# LOCAL ROUTINES
# ------------------------------------------------------------------


def _getImageUrlDbRec():
    """
    Return the REF_URL record for images.
    """
    global _imageUrlDbRec

    if _imageUrlDbRec == None:
        # Get URL info for the EuReGene image files.

        _imageUrlDbRec = RefUrlDb.getByInstituteType(
            "European Renal Genome Project", "euregene_images")

    return _imageUrlDbRec



# ------------------------------------------------------------------
# ITERATOR
# ------------------------------------------------------------------


class OriginalImagesForSubmissionIterator:
    """
    Iterate through all original image records for the given submission
    """

    def __init__(self, submissionOid):

        where = (
            SUBMISSION_FK_COLUMN + " = " +
            DbAccess.formatSqlValue(submissionOid))

        self.__tableIter = _table.Iterator(where = where)
        return None

    def __iter__(self):
        return self

    def next(self):
        """
        Returns a original image DB Record or raises a StopIteration.
        """
        return self.__tableIter.next()





# ------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ------------------------------------------------------------------




# ------------------------------------------------------------------
# MAIN
# ------------------------------------------------------------------


_table = DbTable.registerTable(TABLE_NAME, IshOriginalImageDbRecord)

_table.registerColumn(OID_COLUMN,           DbTable.IS_KEY)
_table.registerColumn(SUBMISSION_FK_COLUMN, DbTable.IS_NOT_KEY)
_table.registerColumn(FILEPATH_COLUMN,      DbTable.IS_NOT_KEY)
_table.registerColumn(FILENAME_COLUMN,      DbTable.IS_NOT_KEY)
_table.registerColumn(PUBLIC_COLUMN,      DbTable.IS_NOT_KEY)
