package com.tayek.util;
public class StringPair { // used in old mapping tools only?
	public StringPair(String first, String second) {
		this.first=first==null?first:first.trim();
		this.second=second==null?second:second.trim();;
	}
	public String toString() {
		return first+" "+second;
	}
	public void setFirst(final String first) {
		this.first=first==null?first:first.trim();
	}
	public String getFirst() {
		return first;
	}
	public void setSecond(final String second) {
		this.second=second==null?second:second.trim();
	}
	public String getSecond() {
		return second;
	}
	private String first,second;
}