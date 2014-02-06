package testapp;

import java.io.File;

import java.util.List;
import java.util.Iterator;

import utility.Wrapper;
import utility.FileUtil;

public class TestFileUtil {

	public static void main(String args[]){  

    	try {
    		long startTime = Wrapper.printPrologue("*", Wrapper.getExecutingClass());

    		
            File file1 = new File("/Users/mwicks/Desktop/LoremIpsum1.txt");
            FileUtil.write(file1, "The Cat Sat On The Mat\n", true);

            File file2 = new File("/Users/mwicks/Desktop/LoremIpsum2.txt");
            FileUtil.write(file2, "The Cat Sat On The Mat\n", false);

            File file3 = new File("/Users/mwicks/Desktop/LoremIpsum3.txt");
            FileUtil.write(file3, "The Cat Sat On The Mat\n");

            List<String> fileList1 = FileUtil.readRecords(file1);
        	Iterator<String> iterator1 = fileList1.iterator();
        	int i1 = 0;

        	while (iterator1.hasNext()) {
        		
        		i1++;
        		String str1 = iterator1.next();
                System.out.println("file1, Record " + i1 + " : " + str1);
        	}

            List<String> fileList2 = FileUtil.readRecords(file2);
        	Iterator<String> iterator2 = fileList2.iterator();
        	int i2 = 0;

        	while (iterator2.hasNext()) {
        		
        		i2++;
        		String str2 = iterator2.next();
                System.out.println("file2, Record " + i2 + " : " + str2);
        	}

            List<String> fileList3 = FileUtil.readRecords(file3);
        	Iterator<String> iterator3 = fileList3.iterator();
        	int i3 = 0;

        	while (iterator3.hasNext()) {
        		
        		i3++;
        		String str3 = iterator3.next();
                System.out.println("file3, Record " + i3 + " : " + str3);
        	}

            Wrapper.printEpilogue("*", Wrapper.getExecutingClass(), startTime);
    	}
    	catch (Exception e) {
    		
    		e.printStackTrace();
    	}
	}  
}
