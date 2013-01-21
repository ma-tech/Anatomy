package testapp;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class TestClassLoader {

	public static void main(String... args) throws Exception {
	    String source = "public class Test { static { System.out.println(\"test\"); } }";

	    File root = new File("/Users/mwicks/GitMahost/Anatomy/UsefulJava/src/testapp/test/");
	    File sourceFile = new File(root, "Test.java");
	    Writer writer = new FileWriter(sourceFile);
	    writer.write(source);
	    writer.close();

	    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    compiler.run(null, null, null, sourceFile.getPath());

	    URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
	    Class<?> cls = Class.forName("Test", true, classLoader);
	}
}
