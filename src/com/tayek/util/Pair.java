package com.tayek.util;
public class Pair {
	public Pair(final Object first,final Object second) {
		this.first=first;
		this.second=second;
	}
	public String toString() {
		return ""+'('+first+' '+second+')';
	}
	public Object first,second;
}
