package com.tayek.util;
public class Time {
	public Time(long t) {
		this.t=t;
	}
	public Time() {
		this(System.currentTimeMillis());
	}
	public final long dt() {
		return System.currentTimeMillis()-t;
	}
	public final long dt(Time time) {
		return time.t-this.t;
	}
	protected final long t;
}