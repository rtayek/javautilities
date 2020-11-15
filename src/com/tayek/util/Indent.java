package com.tayek.util;
public class Indent {
	public Indent(final String string) {
		this.string=string;
	}
	public void in() {
		++indent;
	}
	public void out() {
		--indent;
	}
	public String indent() {
		stringBuffer.delete(0,stringBuffer.length());
		for(int i=0;i<indent;++i)
			stringBuffer.append(string);
		return stringBuffer.toString();
	}
	protected int indent;
	final protected String string;
	protected StringBuffer stringBuffer=new StringBuffer();
}