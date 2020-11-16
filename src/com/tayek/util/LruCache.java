package com.tayek.util;
import java.util.*;
public class LruCache<K,V> {
	public LruCache() {
		this(defaultMax);
	}
	public LruCache(final int max) {
		this(max,false);
	}
	public LruCache(boolean nullIsAllowed) {
		this(defaultMax,nullIsAllowed);
	}
	public LruCache(final int max,boolean nullIsAllowed) {
		this.max=max;
		this.nullIsAllowed=nullIsAllowed;
		cache=new LinkedHashMap<K,V>(max+1,.75F,true) { // lruCache = (Map)Collections.synchronizedMap(lruCache);
			/**
             * 
             */
            private static final long serialVersionUID=1L;

            public boolean removeEldestEntry(Map.Entry<K,V> eldest) {
				return size()>max;
			}
		};
	}
	public V get(final K key) {
		V o=cache.get(key);
		final boolean isInMap=o!=null||nullIsAllowed&&cache.containsKey(key);
		return isInMap?o:null;
	}
	public Object put(final K key,final V value) {
		return cache.put(key,value);
	}
	public void clear() {
		cache.clear();
	}
	public String toString() {
		return cache.toString();
	}
	final boolean nullIsAllowed;
	final int max;
	final Map<K,V> cache;
	static final int defaultMax=100;
	static final long SerialVersionUID=0;
}