package com.tayek.util;

import java.io.*;
import java.util.*;
import java.util.regex.*;
public class Matching {
	public static void collectionToFile(File file,Collection<File> collection) {
		try {
			Writer w=new FileWriter(file);
			for(File f:collection)
				w.write(f.toString()+'\n');
			w.close();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static Set<String> findMatchingFilenamesIn(final File file,Pattern pattern) {
		final Set<Pattern> patterns=new LinkedHashSet<Pattern>(1); // just in case we need to preserve the order
		patterns.add(pattern);
		return matchingFilenamesIn(file,patterns);
	}
	public static Set<String> findMatchingFilenamesIn(final File[] files,Pattern pattern) {
		final Set<Pattern> patterns=new LinkedHashSet<Pattern>(1); // just in case we need to preserve the order
		patterns.add(pattern);
		final Set<String> s=new LinkedHashSet<String>();
		addMatchingFilenamesToSet(files,patterns,s);
		return s;
	}
	private static Set<String> matchingFilenamesIn(final File file,Collection<Pattern> patterns) {
		final Set<String> s=new LinkedHashSet<String>(); // check the order on these!
		if(file!=null) if(file.exists()) if(file.canRead()) if(file.isDirectory()) addMatchingFilenamesToSet(file
				.listFiles(),patterns,s);
		else addMatchingFilenameToSet(file,patterns,s);
		else System.out.println("can not read file: "+file);
		else System.out.println("file: "+file+" does not exist");
		else System.out.println("file: "+file+" does not exist");
		System.out.println("filename order is: "+s);
		return s;
	}
	private static void addMatchingFilenamesToSet(final File[] files,Collection<Pattern> patterns,final Set<String> set) {
		for(File f:files)
			addMatchingFilenameToSet(f,patterns,set);
	}
	private static void addMatchingFilenameToSet(final File file,final Collection<Pattern> patterns,
			final Set<String> set) {
		if(patterns==null||patterns.size()==0) set.add(file.getName());
		else for(Pattern p:patterns)
			if(p.matcher(file.getName()).find()) set.add(file.getName());
	}
	// maybe use these to read save files?
	/* private */static void add(final File file,Set<File> files) {
		files.addAll(toListOfFiles(linesFromFile(file)));
	}
	public static List<String> linesFromFile(final File file) {
		final List<String> l=new LinkedList<String>();
		if(file.exists()&&file.canRead()) try {
			BufferedReader r=new BufferedReader(new FileReader(file));
			for(String line=r.readLine();line!=null;line=r.readLine())
				l.add(line);
			r.close();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		return l;
	}
	public static List<File> toListOfFiles(final List<String> filenames/* , final File directory */) {
		final List<File> l=new LinkedList<File>();
		for(String filename:filenames) {
			final File f=new File(filename); // cannonical form?
			if(f.exists()) if(f.canRead()) l.add(f);
			else System.out.println("can not read file: "+f);
			else System.out.println("file: "+f+" does not exist!");
		}
		return l;
	}
	public static File siblingOf(final File directory,final int n) {
		if(directory==null) return null;
		final File parent=directory.getParentFile();
		if(parent==null) return null; // return dir? should be a root?
		File[] siblings=parent.listFiles();
		SortedSet<File> s=new TreeSet<File>();
		s.addAll(Arrays.asList(siblings));
		final java.util.List<File> dirs=new ArrayList<File>();
		for(File f:s)
			if(f.isDirectory()) dirs.add(f);
		System.out.println("filename order in sorted set is: "+dirs);
		int i=dirs.indexOf(directory);
		if(i!=-1) {
			i=(i+n)%dirs.size();
			if(i<0) i+=dirs.size();
			return dirs.get(i);
		}
		System.out.println("can not find last directory: "+directory+" in siblings!");
		return null;
	}
	public static final void copyInputStream(InputStream in,OutputStream out) throws IOException {
		byte[] buffer=new byte[1024];
		for(int n=in.read(buffer);n>=0;n=in.read(buffer))
			out.write(buffer,0,n);
		in.close();
		out.close();
	}
}
