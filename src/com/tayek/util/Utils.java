package com.tayek.util;
import java.io.*;
import java.net.URL;
import java.util.*;
public class Utils {
	static void p(File file) {
		try {
			System.out.println("file="+file);
			System.out.println("file "+file+" "+(file.exists()?"exists":"does not exist"));
			System.out.println(".toString()="+file.toString());
			System.out.println("parent="+file.getParent()+",separator="+File.separator+", name="+file.getName());
			System.out.println(".getPath()="+file.getPath());
			System.out.println(".getAbsolutePath()="+file.getAbsolutePath());
			System.out.println(".getCanonicalPath()="+file.getCanonicalPath());
			System.out.println(".getCanonicalFile()="+file.getCanonicalFile());
			System.out.println(".getName()="+file.getName());
		} catch(IOException e) {
			System.out.println(e);
		}
	}
	public static List<String> getListOfLines(BufferedReader bufferedReader) {
		List<String> list=new LinkedList<String>();
		try {
			for(String line=bufferedReader.readLine();line!=null;line=bufferedReader.readLine())
				list.add(line);
		} catch(IOException e) {
			System.out.println(e);
		}
		return list;
	}
	public static String get(BufferedReader bufferedReader) {
		StringBuffer stringBuffer=new StringBuffer();
		try {
			for(String line=bufferedReader.readLine();line!=null;line=bufferedReader.readLine())
				stringBuffer.append(line).append(' ');
		} catch(IOException e) {
			System.out.println(e);
		}
		return stringBuffer.toString();
	}
	public static List getAsListOfStrings(URL url) { // get url as list of strings
		List list=null;
		BufferedReader bufferedReader=null;
		InputStream inputStream=null;
		try {
			inputStream=url.openStream();
		} catch(IOException e) {
			e.printStackTrace();
			return list;
		}
		try {
			bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			list=getListOfLines(bufferedReader);
			bufferedReader.close();
			return list;
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(bufferedReader!=null)
				try {
					bufferedReader.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
		}
		return Collections.EMPTY_LIST;
	}
	public static String get(URL url) {
		String string=null;
		BufferedReader bufferedReader=null;
		InputStream inputStream=null;
		try {
			inputStream=url.openStream();
		} catch(IOException e) {
			System.out.println(e);
			return string;
		}
		try {
			bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			string=get(bufferedReader);
			bufferedReader.close();
		} catch(IOException e) {
			System.out.println(e);
		} finally {
			if(bufferedReader!=null)
				try {
					bufferedReader.close();
				} catch(IOException e) {
					System.out.println(e);
				}
		}
		// may return an partial result!
		return string;
	}
	public static String get(final File file) {
		if(!file.canRead()) {
			System.out.println("can not read file: ="+file);
			return null;
		}
		BufferedReader bufferedReader=null;
		String string=null;
		try {
			bufferedReader=new BufferedReader(new FileReader(file));
			string=get(bufferedReader);
		} catch(IOException e) {
			System.out.println(e);
		} finally {
			if(bufferedReader!=null)
				try {
					bufferedReader.close();
				} catch(Exception e) {
					System.out.println(e);
				}
		}
		return string.toString();
	}
	public static void write(final String string,final File file) {
		try {
			FileWriter fileWriter=new FileWriter(file);
			BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
			bufferedWriter.write(string);
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
			//System.out.println("Created file: "+file.getName());
		} catch(Throwable t) {
			t.printStackTrace();
			throw new RuntimeException("can not write file: "+file);
		}
	}
}