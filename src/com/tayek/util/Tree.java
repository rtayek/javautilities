package com.tayek.util;
import java.util.*;
public class Tree {
	public Tree() {
		this(new LinkedHashSet<Object>());
	}
	public Tree(final Set<Object> children) {
		check(children);
		this.children=children;
	}
	public Set children() {
		return Collections.unmodifiableSet(children);
	}
	public boolean add(final Set children) {
		check(children);
		return this.children.add(children);
	}
	public boolean remove(final Set children) {
		check(children);
		return this.children.remove(children);
	}
	public Object object() {
		return object;
	}
	public Object object(Object object) {
		Object old=this.object;
		this.object=object;
		return old;
	}
	private void check(final Set s) {
		for(Iterator i=s.iterator();i.hasNext();)
			if(!(i.next() instanceof Tree)) throw new RuntimeException("element in "+s+" is not a "+getClass().getName());
	}
	protected final Set<Object> children;
	protected Object object;
}