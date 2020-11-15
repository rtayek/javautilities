package com.tayek.util;
public class Memory {
	public static void memory(final String prefix) {
		long total=runtime.totalMemory();
		long free=runtime.freeMemory();
		System.err.print(prefix+"m:t "+total+"-f "+free+"= u"+(total-free));
	}
	public static void beforeAndAfterGc() {
		memory("\n before gc: ");
		System.gc();
		memory("\n  after gc: ");
		System.err.println();
	}
	public static final Runtime runtime=Runtime.getRuntime();
}