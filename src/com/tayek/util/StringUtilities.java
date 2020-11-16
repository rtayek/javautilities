package com.tayek.util;
import java.io.*; 
import java.util.*;
public class StringUtilities {
	public static Properties loadProperties(final File propertiesFile) {
		final Properties p=new Properties();
		loadProperties(p,propertiesFile);
		return p;
	}
	public static void loadProperties(final Properties properties,final File propertiesFile) {
		try {
			final InputStream is=new FileInputStream(propertiesFile);
			properties.load(is);
			is.close();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	// many of these string utilities will return null or empty lists if given a null!
	public static String toString(final String[] s) {
		return s!=null?toString(Arrays.asList(s).iterator(),null,false):null;
	}
	public static String toString(final Iterator<String> i) {
		return toString(i,null,false);
	}
	public static String toString(final String[] string,final String separator,final boolean addSeparatorAtEnd) {
		return toString(Arrays.asList(string).iterator(),separator,addSeparatorAtEnd);
	}
	public static String toString(final Iterator<String> i,final String separator,final boolean addSeparatorAtEnd) {
		String s=null;
		if(i!=null&&i.hasNext()) {
			final StringBuffer sb=new StringBuffer();
			for(;i.hasNext();) {
				sb.append(i.next().toString());
				if(separator!=null&&(i.hasNext()||addSeparatorAtEnd)) sb.append(separator);
				s=sb.toString();
			}
		}
		return s;
	}
	public static String toString(final Reader reader) throws IOException {
		if(reader==null) return null;
		StringBuffer sb=new StringBuffer();
		for(int c=reader.read();c!=-1;c=reader.read())
			sb.append((char)c);
		reader.close();
		return sb.toString();
	}
	public static String toString(final File file) throws FileNotFoundException,IOException {
		return file!=null?toString(new BufferedReader(new FileReader(file))):null;
	}
	public static List<String> toStrings(final Reader reader) throws IOException {
		List<String> l=new LinkedList<String>();
		if(reader!=null) {
			final BufferedReader r=reader instanceof BufferedReader?(BufferedReader)reader:new BufferedReader(reader);
			for(String line=r.readLine();line!=null;line=r.readLine())
				l.add(line);
			r.close();
		}
		return l;
	}
	public static List<String> toStrings(final File file) throws FileNotFoundException,IOException {
		return file!=null?toStrings(new BufferedReader(new FileReader(file))):Collections.emptyList();
	}
	public static Reader toReader(final String s) {
		return s!=null?new StringReader(s):null;
	}
	public static Reader toReader(final String[] s) {
		return s!=null&&s.length!=0?new StringReader(toString(s)):null;
	}
	public static void toFile(final String string,final File file) throws IOException {
		final Writer out=new BufferedWriter(new FileWriter(file));
		out.write(string);
		out.close();
	}
	public static void toNewFile(final String string,final File file) throws IOException {
		boolean justDeleted=false;
		if(file.exists()) {
			if(file.canWrite()) {
				file.delete();
				justDeleted=true;
			} else
				throw new RuntimeException("attempt to delete non writable file: "+file);
		}
		try {
			toFile(string,file);
		} catch(FileNotFoundException e) {
			System.err.println("got a "+e+" with justDeleted="+justDeleted);
		}
	}
	// should't these exist somewhere in the java lib?
	// these are probably not all of the cases either
	public static String quote(final String string,final String quoted) {// make this quote anything and write it's inverse
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<string.length();i++) {
			char c=string.charAt(i);
			if(quoted.indexOf(c)!=-1) {
				switch(c) {
					case '\r':
						sb.append('\\').append('r');
						break;
					case '\n':
						sb.append('\\').append('n');
						break;
					case '\t':
						sb.append('\\').append('t');
						break;
					default:
						sb.append('\\').append(c);
						break;
				}
			} else
				sb.append(c);
		}
		return sb.toString();
	}
	public static String unQuote(final String string) { // not really an inverse as it will remove all initial /'s!
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<string.length();i++) {
			char c=string.charAt(i);
			if(c!='\\')
				sb.append(c);
			else
				switch(string.charAt(i++)) {
					case 'r':
						sb.append('\r');
						break;
					case 'n':
						sb.append('\n');
						break;
					case 't':
						sb.append('\t');
						break;
					default:
						sb.append(c);
						break;
				}
		}
		return sb.toString();
	}
	public static String quote(final String string,final Map<Character,String> map) {
		StringBuffer sb=new StringBuffer();
		char c;
		String s;
		for(int i=0;i<string.length();i++) {
			Character ch=Character.valueOf(c=string.charAt(i));
			if((s=map.get(ch))!=null)
				sb.append(s);
			else
				sb.append(c);
		}
		return sb.toString();
	}
	public static String quoteXml(final String string) {
		return quote(string,xmlQuoteMap);
	}
	static final Map<Character,String> xmlQuoteMap=new HashMap<Character,String>();
	static {
		xmlQuoteMap.put(Character.valueOf('&'),"&amp;");
		xmlQuoteMap.put(Character.valueOf('<'),"&lt;");
		xmlQuoteMap.put(Character.valueOf('>'),"&gt;");
	}
	public static final String[] emptyStringArray=new String[0];
}