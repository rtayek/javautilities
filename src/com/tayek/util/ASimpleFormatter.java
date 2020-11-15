package com.tayek.util;
import java.util.logging.*;
public class ASimpleFormatter extends SimpleFormatter {
	public synchronized String format(LogRecord record) {
		String s=super.format(record);
		int index=s.indexOf(eoln);
		if(index!=-1)
			s=s.substring(0,index)+'\t'+s.substring(index+eoln.length());
		return s;
	}
	private static final String eoln=System.lineSeparator();
}