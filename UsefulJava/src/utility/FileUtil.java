/*
*----------------------------------------------------------------------------------------------
* Project:      UsefulJava
*
* Title:        FileUtil.java
*
* Date:         2012
*
* Author:       BalusC
*
* Copyright:    2012
*               Medical Research Council, UK.
*               All rights reserved.
*
* Address:      MRC Human Genetics Unit,
*               Western General Hospital,
*               Edinburgh, EH4 2XU, UK.
*
* Version: 1
*
* Description:  Useful file system level utilities
*  
* net/balusc/util/FileUtil.java
* 
* Copyright (C) 2007 BalusC
* 
* This program is free software: you can redistribute it and/or modify it under the terms of the
* GNU Lesser General Public License as published by the Free Software Foundation, either version 3
* of the License, or (at your option) any later version.
* 
* This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
* even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License along with this library.
* If not, see <http://www.gnu.org/licenses/>.
* 
* 
* Methods
* -------
* 
* 1.  void write(File file, byte[] bytes)
*       - Overwrite a Byte Array to a File
*     
* 2.  void write(File file, byte[] bytes, boolean append)
*       - Write a Byte Array to a File, Overwrite=True/False
*     
* 3.  void write(File file, InputStream input)
*       - Overwrite an InputStream to a File
*     
* 4.  void write(File file, InputStream input, boolean append)
*       - Write an InputStream to a File, Overwrite=True/False
*     
* 5.  void write(File file, char[] chars)
*       - Overwrite a Character Array to a File
*     
* 6.  void write(File file, char[] chars, boolean append)
*       - Write a Character Array to a File, Overwrite=True/False
*     
* 7.  void write(File file, String string)
*       - Overwrite a String to a File
*     
* 8.  void write(File file, String string, boolean append)
*       - Write a String to a File, Overwrite=True/False
*     
* 9.  void write(File file, Reader reader)
*       - Overwrite a Character Reader to a File
*     
* 10.  void write(File file, Reader reader, boolean append)
*       - Write a Character Reader to a File, Overwrite=True/False
*     
* 11.  void write(File file, List<String> records)
*       - Overwrite a List of String Records to a File
*
* 12.  void write(File file, List<String> records, boolean append)
*       - Write a List of String Records to a File, Overwrite=True/False
*     
* 13.  byte[] readBytes(File file)
*       - Read a Byte Array from a File
*     
* 14.  InputStream readStream(File file)
*       - Read a Byte Stream (InputStream) from a File
*     
* 15.  char[] readChars(File file)
*       - Read a Character Array from a File
*     
* 16.  String readString(File file)
*       - Read a String from a File
*     
* 17.  Reader readReader(File file)
*       - Read a Character Reader (BufferedReader) from a File
*     
* 18.  List<String> readRecords(File file)
*       - Read list of String records from file
*     
* 19.  void copy(File source, File destination)
*       - Copy and Overwrite File A to File B
*       
* 20.  void copy(File source, File destination, boolean overwrite)
*       - Copy File A to File B, Overwrite=True/False
*       
* 21.  void move(File source, File destination) 
*       - Move (Rename) and Overwrite File A to File B
*       
* 22.  void move(File source, File destination, boolean overwrite)
*       - Move (Rename) File A to File B, Overwrite=True/False
*       
* 23.  String trimFilePath(String fileName)
*       - Remove the Path from a Path+Filename
*       
* 24.  File uniqueFile(File filePath, String fileName)
*       - Create a Unique Name for a Path and Filename
*       
* 25.  String roundObjectSize(long longObjectSize)
*       - Print File Size in a Usable Format
*       
* 26.  String[] listAllFilenamesInDirectory(String directory)
*       - Return an array of Strings that contain ALL the filenames in a given Directory
*       
* 27.  String[] listAllUnhiddenFilenamesInDirectory(String directory)
*       - Return an array of Strings that contain the NOT hidden filenames in a given Directory
*       
* 28.  File[] listAllDirectoriesInDirectory(String directory)
*       - Return an array of Files of ALL the Sub-Directories within a given Directory name
*       
* 29.  File[] listAllFilesInDirectory(String directory)
*       - Return an array of Files of ALL the Files within a given Directory name
*
*
* Helpers
* -------
* 
* 1.  void mkdirs(File file)
*       - Check and create missing parent directories for the given file
*       
* 2.  void close(Closeable resource, File file)
*       - Close the given I/O resource of the given file
*
*
* Maintenance:  Log changes below, with most recent at top of list.
*
* Who; When; What;
*
* Mike Wicks; November 2012; Create Class
*
*----------------------------------------------------------------------------------------------
*/
package utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.ArrayList;
import java.util.List;

public final class FileUtil {

    // Init ---------------------------------------------------------------------------------------
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    // Constants ----------------------------------------------------------------------------------
    private static final long ONE_BYTE   = 1L;
    private static final long KILO_BYTE  = 1024L;
    private static final long MEGA_BYTE  = 1048576L;
    private static final long GIGA_BYTE  = 1073741824L;
    private static final long TERRA_BYTE = 1099511627776L;
    private static final long PETTA_BYTE = 1125899906842624L;

    
    // Constructors -------------------------------------------------------------------------------
    private FileUtil() {
        // utility class, hide constructor.
    }

    // Writers ------------------------------------------------------------------------------------
    /*
     * Write byte array to file. If file already exists, it will be overwritten.
     * @param file The file where the given byte array have to be written to.
     * @param bytes The byte array which have to be written to the given file.
     */
    public static void write(File file, byte[] bytes) throws IOException {
      
    	write(file, new ByteArrayInputStream(bytes), false);
    }

    /*
     * Write byte array to file with option to append to file or not. If not, then any existing
     * file will be overwritten.
     * @param file The file where the given byte array have to be written to.
     * @param bytes The byte array which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, byte[] bytes, boolean append) throws IOException {
      
    	write(file, new ByteArrayInputStream(bytes), append);
    }

    /*
     * Write byte inputstream to file. If file already exists, it will be overwritten.It's highly
     * recommended to feed the inputstream as BufferedInputStream or ByteArrayInputStream as those
     * are been automatically buffered.
     * @param file The file where the given byte inputstream have to be written to.
     * @param input The byte inputstream which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, InputStream input) throws IOException {
     
    	write(file, input, false);
    }

    /*
     * Write byte inputstream to file with option to append to file or not. If not, then any
     * existing file will be overwritten. It's highly recommended to feed the inputstream as
     * BufferedInputStream or ByteArrayInputStream as those are been automatically buffered.
     * @param file The file where the given byte inputstream have to be written to.
     * @param input The byte inputstream which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, InputStream input, boolean append) throws IOException {
      
    	mkdirs(file);
        BufferedOutputStream output = null;

        try {
        
        	output = new BufferedOutputStream(new FileOutputStream(file, append));
            int data = -1;
            
            while ((data = input.read()) != -1) {
            
            	output.write(data);
            }
        } 
        finally {
        
        	close(input, file);
            close(output, file);
        }
    }

    /*
     * Write character array to file. If file already exists, it will be overwritten.
     * @param file The file where the given character array have to be written to.
     * @param chars The character array which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, char[] chars) throws IOException {
      
    	write(file, new CharArrayReader(chars), false);
    }

    /*
     * Write character array to file with option to append to file or not. If not, then any
     * existing file will be overwritten.
     * @param file The file where the given character array have to be written to.
     * @param chars The character array which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, char[] chars, boolean append) throws IOException {
      
    	write(file, new CharArrayReader(chars), append);
    }

    /*
     * Write string value to file. If file already exists, it will be overwritten.
     * @param file The file where the given string value have to be written to.
     * @param string The string value which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, String string) throws IOException {
      
    	write(file, new CharArrayReader(string.toCharArray()), false);
    }

    /*
     * Write string value to file with option to append to file or not. If not, then any existing
     * file will be overwritten.
     * @param file The file where the given string value have to be written to.
     * @param string The string value which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, String string, boolean append) throws IOException {
      
    	write(file, new CharArrayReader(string.toCharArray()), append);
    }

    /*
     * Write character reader to file. If file already exists, it will be overwritten. It's highly
     * recommended to feed the reader as BufferedReader or CharArrayReader as those are been
     * automatically buffered.
     * @param file The file where the given character reader have to be written to.
     * @param reader The character reader which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, Reader reader) throws IOException {
      
    	write(file, reader, false);
    }

    /*
     * Write character reader to file with option to append to file or not. If not, then any
     * existing file will be overwritten. It's highly recommended to feed the reader as
     * BufferedReader or CharArrayReader as those are been automatically buffered.
     * @param file The file where the given character reader have to be written to.
     * @param reader The character reader which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, Reader reader, boolean append) throws IOException {
      
    	mkdirs(file);
        BufferedWriter writer = null;

        try {
        
        	writer = new BufferedWriter(new FileWriter(file, append));
            int data = -1;
            
            while ((data = reader.read()) != -1) {
            
            	writer.write(data);
            }
        } 
        finally {
            close(reader, file);
            close(writer, file);
        }
    }

    /*
     * Write list of String records to file. If file already exists, it will be overwritten.
     * @param file The file where the given character reader have to be written to.
     * @param records The list of String records which have to be written to the given file.
     * @throws IOException If writing file fails.
     */
    public static void write(File file, List<String> records) throws IOException {
      
    	write(file, records, false);
    }

    /*
     * Write list of String records to file with option to append to file or not. If not, then any
     * existing file will be overwritten.
     * @param file The file where the given character reader have to be written to.
     * @param records The list of String records which have to be written to the given file.
     * @param append Append to file?
     * @throws IOException If writing file fails.
     */
    public static void write(File file, List<String> records, boolean append) throws IOException {
      
    	mkdirs(file);
        BufferedWriter writer = null;

        try {
        
        	writer = new BufferedWriter(new FileWriter(file, append));
            
        	for (String record : records) {
            
        		writer.write(record);
                writer.write(LINE_SEPARATOR);
            }
        } 
        finally {
            close(writer, file);
        }
    }

    // Readers ------------------------------------------------------------------------------------
    /*
     * Read byte array from file. Take care with big files, this would be memory hogging, rather
     * use readStream() instead.
     * @param file The file to read the byte array from.
     * @return The byte array with the file contents.
     * @throws IOException If reading file fails.
     */
    public static byte[] readBytes(File file) throws IOException {
      
    	BufferedInputStream stream = (BufferedInputStream) readStream(file);
        byte[] bytes = new byte[stream.available()];
        stream.read(bytes);
        
        return bytes;
    }

    /*
     * Read byte stream from file.
     * @param file The file to read the byte stream from.
     * @return The byte stream with the file contents (actually: BufferedInputStream).
     * @throws IOException If reading file fails.
     */
    public static InputStream readStream(File file) throws IOException {
      
    	return new BufferedInputStream(new FileInputStream(file));
    }

    /*
     * Read character array from file. Take care with big files, this would be memory hogging,
     * rather use readReader() instead.
     * @param file The file to read the character array from.
     * @return The character array with the file contents.
     * @throws IOException If reading file fails.
     */
    public static char[] readChars(File file) throws IOException {
      
    	BufferedReader reader = (BufferedReader) readReader(file);
        char[] chars = new char[(int) file.length()];
        reader.read(chars);
        
        return chars;
    }

    /*
     * Read string value from file. Take care with big files, this would be memory hogging, rather
     * use readReader() instead.
     * @param file The file to read the string value from.
     * @return The string value with the file contents.
     * @throws IOException If reading file fails.
     */
    public static String readString(File file) throws IOException {
      
    	return new String(readChars(file));
    }

    /*
     * Read character reader from file.
     * @param file The file to read the character reader from.
     * @return The character reader with the file contents (actually: BufferedReader).
     * @throws IOException If reading file fails.
     */
    public static Reader readReader(File file) throws IOException {
      
    	return new BufferedReader(new FileReader(file));
    }

    /*
     * Read list of String records from file.
     * @param file The file to read the character writer from.
     * @return A list of String records which represents lines of the file contents.
     * @throws IOException If reading file fails.
     */
    public static List<String> readRecords(File file) throws IOException {
      
    	BufferedReader reader = (BufferedReader) readReader(file);
        List<String> records = new ArrayList<String>();
        String record = null;

        try {
        
        	while ((record = reader.readLine()) != null) {
            
        		records.add(record);
            }
        } 
        finally {
        
        	close(reader, file);
        }

        return records;
    }

    // Copiers ------------------------------------------------------------------------------------
    /*
     * Copy file. Any existing file at the destination will be overwritten.
     * @param source The file to read the contents from.
     * @param destination The file to write the contents to.
     * @throws IOException If copying file fails.
     */
    public static void copy(File source, File destination) throws IOException {
     
    	copy(source, destination, true);
    }

    /*
     * Copy file with the option to overwrite any existing file at the destination.
     * @param source The file to read the contents from.
     * @param destination The file to write the contents to.
     * @param overwrite Set whether to overwrite any existing file at the destination.
     * @throws IOException If the destination file already exists while <tt>overwrite</tt> is set
     * to false, or if copying file fails.
     */
    public static void copy(File source, File destination, boolean overwrite) throws IOException {
      
    	if (destination.exists() && !overwrite) {
        
    		throw new IOException(
                "Copying file " + source.getPath() + " to " + destination.getPath() + " failed."
                    + " The destination file already exists.");
        }

        mkdirs(destination);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            
        	input = new BufferedInputStream(new FileInputStream(source));
            output = new BufferedOutputStream(new FileOutputStream(destination));
            int data = -1;
            
            while ((data = input.read()) != -1) {
            
            	output.write(data);
            }
        }
        finally {
        
        	close(input, source);
            close(output, destination);
        }
    }

    // Movers -------------------------------------------------------------------------------------
    /*
     * Move (rename) file. Any existing file at the destination will be overwritten.
     * @param source The file to be moved.
     * @param destination The new destination of the file.
     * @throws IOException If moving file fails.
     */
    public static void move(File source, File destination) throws IOException {
      
    	move(source, destination, true);
    }

    /*
     * Move (rename) file with the option to overwrite any existing file at the destination.
     * @param source The file to be moved.
     * @param destination The new destination of the file.
     * @param overwrite Set whether to overwrite any existing file at the destination.
     * @throws IOException If the destination file already exists while <tt>overwrite</tt> is set
     * to false, or if moving file fails.
     */
    public static void move(File source, File destination, boolean overwrite) throws IOException {
      
    	if (destination.exists()) {
        
    		if (overwrite) {
            
    			destination.delete();
            } 
    		else {
            
    			throw new IOException(
                    "Moving file " + source.getPath() + " to " + destination.getPath() + " failed."
                        + " The destination file already exists.");
            }
        }

        mkdirs(destination);

        if (!source.renameTo(destination)) {
            
        	throw new IOException(
                "Moving file " + source.getPath() + " to " + destination.getPath() + " failed.");
        }
    }

    // Filenames ----------------------------------------------------------------------------------
    /*
     * Trim the eventual file path from the given file name. Anything before the last occurred "/"
     * and "\" will be trimmed, including the slash.
     * @param fileName The file name to trim the file path from.
     * @return The file name with the file path trimmed.
     */
    public static String trimFilePath(String fileName) {
     
    	return fileName
            .substring(fileName.lastIndexOf("/") + 1)
            .substring(fileName.lastIndexOf("\\") + 1);
    }

    /*
     * Generate unique file based on the given path and name. If the file exists, then it will
     * add "[i]" to the file name as long as the file exists. The value of i can be between
     * 0 and 2147483647 (the value of Integer.MAX_VALUE).
     * @param filePath The path of the unique file.
     * @param fileName The name of the unique file.
     * @return The unique file.
     * @throws IOException If unique file cannot be generated, this can be caused if all file
     * names are already in use. You may consider another filename instead.
     */
    public static File uniqueFile(File filePath, String fileName) throws IOException {
      
    	File file = new File(filePath, fileName);
        
        if (file.exists()) {

            // Split filename and add braces, e.g. "name.ext" --> "name[", "].ext".
            String prefix;
            String suffix;
            int dotIndex = fileName.lastIndexOf(".");

            if (dotIndex > -1) {
        
            	prefix = fileName.substring(0, dotIndex) + "[";
                suffix = "]" + fileName.substring(dotIndex);
            } else {
                
            	prefix = fileName + "[";
                suffix = "]";
            }

            int count = 0;

            // Add counter to filename as long as file exists.
            while (file.exists()) {
                
            	if (count < 0) { // int++ restarts at -2147483648 after 2147483647.
                
            		throw new IOException("No unique filename available for " + fileName 
                        + " in path " + filePath.getPath() + ".");
                }

                // Glue counter between prefix and suffix, e.g. "name[" + count + "].ext".
                file = new File(filePath, prefix + (count++) + suffix);
            }
        }

        return file;
    }

    // Helpers ------------------------------------------------------------------------------------
    /*
     * Check and create missing parent directories for the given file.
     * @param file The file to check and create the missing parent directories for.
     * @throws IOException If the given file is actually not a file or if creating parent 
     * directories fails.
     */
    private static void mkdirs(File file) throws IOException {
     
    	if (file.exists() && !file.isFile()) {
        
    		throw new IOException("File " + file.getPath() + " is actually not a file.");
        }
    	
        File parentFile = file.getParentFile();
        
        if (!parentFile.exists() && !parentFile.mkdirs()) {
        
        	throw new IOException("Creating directories " + parentFile.getPath() + " failed.");
        }
    }

    /*
     * Close the given I/O resource of the given file.
     * @param resource The I/O resource to be closed.
     * @param file The I/O resource's subject.
     */
    private static void close(Closeable resource, File file) {
      
    	if (resource != null) {
        
    		try {
            
    			resource.close();
            }
    		catch (IOException e) {
                
    			String message = "Closing file " + file.getPath() + " failed.";
                // Do your thing with the exception and the message. Print it, log it or mail it.
                System.err.println(message);
                e.printStackTrace();
            }
        }
    }
    
    // Others -------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    /*
     * Display the file size (long) in a useful String format.
     * @param the size of the file in question.
     */
    public static String roundObjectSize(long longObjectSize) {

        String strObjectSizeOut = "";
        
		if ( longObjectSize == 0 ) {
			
			strObjectSizeOut = "0 Bytes";
		}
		else if ( longObjectSize > 0 && longObjectSize < KILO_BYTE - 1 ) {
			
			long longObjectSizeDivOne   = longObjectSize / ONE_BYTE;
			
			if ( longObjectSizeDivOne <= 1000 ) {

				strObjectSizeOut = ObjectConverter.convert(longObjectSizeDivOne, String.class) + " Bytes";
			}
			else {
				
				strObjectSizeOut = "1,00 KB";
			}
		}
		else if ( longObjectSize >= KILO_BYTE && longObjectSize < MEGA_BYTE - 1 ) {
			
			long longObjectSizeDivKilo  = longObjectSize / KILO_BYTE;

			if ( longObjectSizeDivKilo <= 1000 ) {

				strObjectSizeOut = ObjectConverter.convert(longObjectSizeDivKilo, String.class) + " KB";
			}
			else {
				
				strObjectSizeOut = "1,00 MB";
			}
		}
		else if ( longObjectSize >= MEGA_BYTE && longObjectSize < GIGA_BYTE - 1 ) {
			
			long longObjectSizeDivMega  = longObjectSize / MEGA_BYTE;

			if ( longObjectSizeDivMega <= 1000 ) {

				strObjectSizeOut = ObjectConverter.convert(longObjectSizeDivMega, String.class) + " MB";
			}
			else {
				
				strObjectSizeOut = "1,00 GB";
			}
		}
		else if ( longObjectSize >= GIGA_BYTE && longObjectSize < TERRA_BYTE - 1 ) {
			
			long longObjectSizeDivGiga  = longObjectSize / GIGA_BYTE;

			if ( longObjectSizeDivGiga <= 1000 ) {

				strObjectSizeOut = ObjectConverter.convert(longObjectSizeDivGiga, String.class) + " GB";
			}
			else {
				
				strObjectSizeOut = "1,00 TB";
			}
		}
		else if ( longObjectSize >= TERRA_BYTE && longObjectSize < PETTA_BYTE - 1 ) {
			
			long longObjectSizeDivTerra = longObjectSize / TERRA_BYTE;

			if ( longObjectSizeDivTerra <= 1000 ) {

				strObjectSizeOut = ObjectConverter.convert(longObjectSizeDivTerra, String.class) + " TB";
			}
			else {
				
				strObjectSizeOut = "1,00 PB";
			}
		}

		return strObjectSizeOut;
    }
    
    /*
     * Return an array of Strings that contain all the filenames, hidden and unhidden, for a supplied directory name.
     * @param the directory to be listed.
     */
    public static String[] listAllFilenamesInDirectory(String directory) {
    	
        String[] files = null;
        
        File dir = new File(directory);

        if ( dir.exists() ) {

            files = dir.list();

            if (files == null) {

            	files[0] = "EMPTY Directory!";
            } 
        }
        else {
        	
            files[0] = "Directory DOES NOT Exist!";
        }

        return files;
    }

    /*
     * Return an array of Strings that contain all the filenames, unhidden, for a supplied directory name.
     * @param the directory to be listed.
     */
    public static String[] listAllUnhiddenFilenamesInDirectory(String directory) {
    	
        String[] files = null;
        
        File dir = new File(directory);

        if ( dir.exists() ) {

        	FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return !name.startsWith(".");
                }
            };
            
            files = dir.list(filter);

            if (files == null) {

            	files[0] = "No UnHidden Files!";
            }
        }
        else {
        	
            files[0] = "Directory DOES NOT Exist!";
        }

        return files;
    }

    /*
     * Return an array of File Objects that contain all the sub-directories within a supplied directory name.
     * @param the directory to be listed.
     */
    public static File[] listAllDirectoriesInDirectory(String directory) {
    	
        File dir = new File(directory);

        // The list of files can also be retrieved as File objects
        File[] directories = null;

        if ( dir.exists() ) {

        	// This filter only returns directories
            FileFilter fileFilter = new FileFilter() {
                public boolean accept(File file) {
                    return file.isDirectory();
                }
            };
            
            directories = dir.listFiles(fileFilter);
            
        }

        return directories;
    }
    
    /*
     * Return an array of File Objects that contain all the files within a supplied directory name.
     * @param the directory to be listed.
     */
    public static File[] listAllFilesInDirectory(String directory) {
    	
        File dir = new File(directory);

        // The list of files can also be retrieved as File objects
        File[] directories = null;

        if ( dir.exists() ) {

        	// This filter only returns directories
            FileFilter fileFilter = new FileFilter() {
                public boolean accept(File file) {
                    return !file.isDirectory();
                }
            };
            
            directories = dir.listFiles(fileFilter);
            
        }

        return directories;
    }
}