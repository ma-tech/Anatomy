#!/usr/bin/env /usr/bin/python
# -*- coding: iso-8859-15 -*-
#-------------------------------------------------------------------
#
# National Institute fpr Basic Biology (NIBB) Xenopus laevis EST Database (XDB)
# clone library information.
#
# The information in this module is copied from:
#
#   http://xenopus.nibb.ac.jp/lib.html


import urllib                           # HTTP access.

from hgu import Util

import NibbXdbClone



# ------------------------------------------------------------------
# CONSTANTS / REFERENCE DATA
# ------------------------------------------------------------------

# First 5 characters of an ID (after the prefix) determine which library
# the clone came from.

CLONE_ID_LIBRARY_KEY_LENGTH = 5



# ------------------------------------------------------------------
# GLOBALS
# ------------------------------------------------------------------

_libraries = []                         # List of libraries.



# ------------------------------------------------------------------
# NIBB XDB LIBRARY INFORMATION
# ------------------------------------------------------------------


# 
#

class NibbXdbLibrary:
    """
    Information about a NIBB XDB library.

    The library information was copied form the page:
    
      http://xenopus.nibb.ac.jp/lib.html
      
    as it existed on 15 Feb 2007.  This page does not really lend itself to
    dynamic parsing, and any dynamic parsing would be likely to break if the
    page were modified in anyway.  In addition, this information is not that
    likely to change anyway.
    
    So the information from that page has been hardcoded here.
    
    Which set of information to use depends on the ID used.  Different ranges
    map to differnt libraries.
    """

    def __init__(self, name, description, stage, tissue, startIdKey, endIdKey):
        """
        Initialise an XDB Library.
        """

        self.__name = name
        self.__description = description
        self.__species = "Xenopus laevis"
        self.__stage = stage
        self.__tissue = tissue
        self.__startIdKey = startIdKey
        self.__endIdKey = endIdKey

        return None

    def getName(self):
        """
        Get library name.
        """
        return self.__name

    def getStartIdKey(self):
        """
        Return the starting ID key for this library.  Clones are in this
        library if the first 5 characters of their ID are between the
        start and end keys for the library (inclusive).
        """
        return self.__startIdKey
    

    def getEndIdKey(self):
        """
        Return the ending ID key for this library.  Clones are in this
        library if the first 5 characters of their ID are between the
        start and end keys for the library (inclusive).
        """
        return self.__endIdKey
    
    def getSpecies(self):
        return self.__species

    def getTissue(self):
        return self.__tissue

    def getStage(self):
        return self.__stage


# ---------------------------------------------------------------------
# Internal Functions
# ---------------------------------------------------------------------





# ---------------------------------------------------------------------
# MODULE LEVEL ROUTINES
# ---------------------------------------------------------------------

def getLibraryByNibbXdbId(nibbXdbId):
    """
    Return the library information for the given NIBB XDB ID.  Returns None
    if ID does not occur in any library.

    The ID can be with or without the leading NIBB: prefix.
    """
    cloneId = Util.stripPublicIdPrefix(nibbXdbId)
    cloneIdStart = cloneId[0:CLONE_ID_LIBRARY_KEY_LENGTH]

    matchingLib = None
    for lib in _libraries:
        if lib.getStartIdKey() <= cloneIdStart <= lib.getEndIdKey():
            matchingLib = lib
            break

    return matchingLib




# ---------------------------------------------------------------------
# MAIN
# ---------------------------------------------------------------------

# Initialise GLOBALS at module load time.

# Populate the _libraries list with the list of libraries.


# Initialise Mochii libs.  They share a common description

mochiiDesc = """
Construction of the libraries is described in Kitayama et al. (in preparation). They were arrayed in NIBB and sequenced by NIG.

Briefly, cDNA libraries were constructed with ZAP-cDNA Gigapack III cloning kit (Stratagene) according to manufacturer's instruction with minor modification. 5 ug of poly A+ RNA from each of the three embryonic stages was oligo dT primed (XhoI dT primer). After second strand synthesis, EcoRI adapters were ligated and the cDNA was directionally cloned with EcoRI at the 5' end and XhoI at the 3' end. Normalization was carried out with ssDNA phagemids hybridized to biotinylated driver, which was prepared from the same library by PCR. The average insert size of about 1.5kb. The vector used is pBlueScript SK(-) (Stratagene) and the host bacteria is DH10B (GIBCO).

Primers for PCR amplification:
T3 primer:        ATT AAC CCT CAC TAA AG (17mer)
T7 primer:        AAT ACG ACT CAC TAT AG (17mer)
"""

_libraries.append(
    NibbXdbLibrary(
        name        = "NIBB Mochii-normalized Xenopus neurula library",
        description = mochiiDesc,
        stage       = "NF15",
        tissue      = None,
        startIdKey  = "XL001",
        endIdKey    = "XL050"
    ))

_libraries.append(
    NibbXdbLibrary(
        name        = "NIBB Mochii-normalized Xenopus tailbud library",
        description = mochiiDesc,
        stage       = "NF25",
        tissue      = None,
        startIdKey  = "XL051",
        endIdKey    = "XL110"
    ))

_libraries.append(
    NibbXdbLibrary(
        name        = "NIBB Mochii-normalized Xenopus early gastrula library",
        description = mochiiDesc,
        stage       = "NF10.5",
        tissue      = None,
        startIdKey  = "XL111",
        endIdKey    = "XL220"
    ))

dmzDesc = """
Construction of the library is described in Hyodo-Miura et al. (in preparation ). They were arrayed in NIBB and sequenced by NIG as the National Bio Resource Project.

About 1000pieces of Keller explants were dissected at stage 10.5 and cultured in 0.1% BSA/1 x steinberg's solution. Total RNA was isolated by the acid guanidinium thiocyanate-phenolchloroform method from explants at stage 11~15. A cDNA library was made with the ZAP-cDNA Synthesis Kit (Stratagene ) according to manufacturer's instruction with minor modification. 4ug of polyA+RNA was oligo dT primed (XhoI dT primer). After second strand synthesis, EcoRI adaptor was ligated and the cDNA was directionally cloned with EcoRI at the 5' end and XhoI at the 3' end into the pCS2p+ vector. The average insert size was about 2kb. The host bacteria was XL2-Blue (Stratagene ).
"""

_libraries.append(
    NibbXdbLibrary(
        name        = "Yamamoto / Hyodo-Miura NIBB / NBRP Xenopus DMZ library",
        description = dmzDesc,
        stage       = "NF10.5 through NF15",
        tissue      = None,
        startIdKey  = "XL221",
        endIdKey    = "XL341"
    ))


aneDesc = """
Construction of the libraries is described in Osada et al. (Development 130, 1783-1794, 2003). They were arrayed in NIBB and sequenced by NIG as the National Bio Resource Project.

About 500 pieces of anterior neural plates were dissected from stage 12-12.5 embryos. The neuroectoderm layer was separated from the underlying mesendoderm layer in 1 x modified Barth's solution containing 1-2 mg/ml collagenase. After poly (A)+RNA selection by an oligo(dT) cellulose column, a cDNA library was made with the SuperScript plasmid system for cDNA synthesis and plasmid cloning (Invitrogen) and the pCS105 vector (Hsu D.R. et al., Mol Cell. 1, 673-83, 1998). The first strand cDNA was synthesized with oligo dT-NotI primer. After second strand synthesis, SalI adapter (gtcgacCCACGCGTCCG; the lower case letters show SalI site) were ligated and the cDNA was directionally cloned with SalI at the 5' end and NotI at the 3' end. The average insert size is about 2kb and the host bacteria is XL1 blue (Stratagene) (?).
"""

_libraries.append(
    NibbXdbLibrary(
        name        = "Osada/Taira NBRP Xenopus ANE library",
        description = aneDesc,
        stage       = "NF12 and NF12.5",
        tissue      = "anterior neural plate",
        startIdKey  = "XL401",
        endIdKey    = "XL520"
    ))

