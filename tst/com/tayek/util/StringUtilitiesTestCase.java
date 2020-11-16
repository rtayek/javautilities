package com.tayek.util;
import java.io.*;
import java.util.*;
import junit.framework.*;
public class StringUtilitiesTestCase extends TestCase {
	public StringUtilitiesTestCase(java.lang.String testName) {
		super(testName);
	}
	public static Test suite() {
		TestSuite suite=new TestSuite(StringUtilitiesTestCase.class);
		return suite;
	}
	public void testToStringOfArrayOfStringWithNullArray() {
		assertNull(StringUtilities.toString((String[])null));
	}
	public void testToStringOfArrayOfStringWithEmptyArray() {
		assertNull(StringUtilities.toString(new String[0]));
	}
	public void testToStringOfArrayOfStringWithNullIterator() {
		assertNull(StringUtilities.toString((Iterator<String>)null));
	}
	public void testToStringOfArrayOfStringWithEmptyIterator() {
		assertNull(StringUtilities.toString(Collections.emptyListIterator()));
	}
	public void testToStringOfReaderWithNull() throws Exception {
		assertNull(StringUtilities.toString((Reader)null));
	}
	public void testToStringOfFileWithNull() throws Exception {
		assertNull(StringUtilities.toString((File)null));
	}
	public void testToReaderWithString() throws Exception {
		final String expected="a";
		assertEquals(expected,StringUtilities.toString(StringUtilities.toReader(expected)));
	}
	public void testToReaderWithArrayOfString() throws Exception {
		final String[] s=new String[]{"line 1","line 2"};
		final String expected=s[0]+s[1];
		assertEquals(expected,StringUtilities.toString(StringUtilities.toReader(s)));
	}
	public static void main(String[] argument) throws Exception {
		junit.textui.TestRunner.run(suite());
	}
}