package com.tayek.util;
import java.io.*;
import junit.framework.*;
public class UtilsTestCase extends TestCase {
	public UtilsTestCase(java.lang.String testName) {
		super(testName);
	}
	public static Test suite() {
		TestSuite suite = new TestSuite(UtilsTestCase.class);
		return suite;
	}
	public void testP() {
		final File file=new File("foo/bar");
		Utils.p(file);
	}
	public static void main(String[] argument) throws Exception {
		junit.textui.TestRunner.run(suite());
	}
}
