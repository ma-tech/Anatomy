#! /bin/bash
#
# Usage "./prettyPrintJson.sh ../../Versions/Version009/Formats/Trees"
#  From "Anatomy/Database/Tasks/GenerateTreeFormats"
#
echo "Finding JSON files to Validate and Pretty Print"

find $1 -name '*.json' -type f |
   while read file
   do
       directory=`dirname "$file"`
       filename=`basename "$file"`
       pp="PP"
       testfile=${file%PP*}
       echo "BEFORE"
       echo $testfile
       echo $file
       if [ "$testfile" = "$file" ] 
       then
           outfile=$directory"/"$pp"/"$filename
           echo $directory
           echo $filename
           echo $pp
           echo "EQUAL"
           echo $outfile
           cat $file | python -msimplejson.tool > $outfile
           if [ $? = 0 ]
           then
               rm $file
           fi
       else
           echo "NOT EQUAL"
       fi
   done