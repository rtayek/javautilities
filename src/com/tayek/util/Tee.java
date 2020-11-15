package com.tayek.util;
import java.io.*;
import java.util.*;
public class Tee extends FilterOutputStream /* make this into a writer or make a version for writers */
{ // tee utility
	public Tee(OutputStream out) {
		super(out);
		stream.addElement(out);
	}
	public void teeTo() {
	//Tee tee=new Tee(fileOutputStream);
	//tee.addOutputStream(System.out); /* make into a constructor and put into Tee? */
	}
	public synchronized void addOutputStream(OutputStream out) {
		stream.addElement(out);
	}
	public synchronized void write(int b) throws IOException {
		for(Enumeration e=stream.elements();e.hasMoreElements();) {
			OutputStream out=(OutputStream)e.nextElement();
			out.write(b);
			out.flush();
		}
	}
	public synchronized void write(byte[] data,int offset,int length) throws IOException {
		for(Enumeration e=stream.elements();e.hasMoreElements();) {
			OutputStream out=(OutputStream)e.nextElement();
			out.write(data,offset,length);
			out.flush();
		}
	}
	public static void tee(File file) {
		tee(file,true);
	}
	public static void tee(File file,boolean delete) {
		if(delete&&file.exists())
			file.delete();
		Tee tee=new Tee(System.out);
		PrintStream printStream=new PrintStream(tee,true);
		System.setOut(printStream);
		System.setErr(printStream);
		try {
			tee.addOutputStream(new FileOutputStream(file.toString(),true));
		} catch(IOException e) {
			System.out.println(e);
		}
		System.out.println("tee'd");
	}
	Vector<OutputStream> stream=new Vector<OutputStream>();
}