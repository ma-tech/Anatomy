#!/usr/bin/python 
from distutils.core import setup

setup (name = 'GenerateTreeFormats', 
          description = "Generates all the Tree Reports from the Anatomy MySQL Database", 
          author = "David Clements", 
          author_email = "daveclements@gmail.com", 
          maintainer = "Mike Wicks", 
          maintainer_email = "Michael.Wicks@igmm.ed.ac.uk", 
          version = '1.0.0',
          url = 'http://www.igmm.ac.uk/',
          package_dir = {'hgu': '/Users/mwicks/eclipseWorkspace/PythonAnatomy/Gmerg/Common/lib/python/hgu',
                         'PyRTF': '/Users/mwicks/Downloads/PyRTF-0.45/PyRTF'},
          packages = ['',
                      'hgu', 
                      'hgu.anatomyDb', 
                      'hgu.anatomyDb.version006', 
                      'hgu.db', 
                      'hgu.externalDataSource', 
                      'hgu.externalDataSource.hgnc', 
                      'hgu.externalDataSource.imageConsortium', 
                      'hgu.externalDataSource.mgi', 
                      'hgu.externalDataSource.nibb', 
                      'hgu.externalDataSource.xenbase', 
                      'hgu.gmergDb',
                      'PyRTF']
          )
