package com.tayek.util;
import java.util.*;
public class LruCaches<K,V> {
	public synchronized void add(final String name,final LruCache<K,V> lruCache) {
		cacheNameToLruCache.put(name,lruCache);
	}
	public synchronized void clearCache(final String name) {
		final LruCache<K,V> lruCache=cacheNameToLruCache.get(name);
		if(lruCache==null) throw new RuntimeException(this.getClass().getName()+" does not contain a value for name: "+name);
		lruCache.clear();
	}
	public synchronized Object get(final String name,final K key) {
		final LruCache<K,V> lruCache=cacheNameToLruCache.get(name);
		if(lruCache==null) throw new RuntimeException(this.getClass().getName()+" does not contain a value for name: "+name);
		return lruCache.get(key);
	}
	public synchronized Object put(final String name,final K key,final V value) {
		final LruCache<K,V> lruCache=cacheNameToLruCache.get(name);
		if(lruCache==null) throw new RuntimeException(this.getClass().getName()+" does not contain a value for name: "+name);
		return lruCache.put(key,value);
	}
	@SuppressWarnings("rawtypes") public static LruCaches instance() {
		return lruCaches;
	}
	private LruCaches() { // singleton
		add("Location",new LruCache<K,V>(10000));
		add("Other",new LruCache<K,V>(1000));
		add("More",new LruCache<K,V>(5000));
	}
	public static void main(String[] args) {}
	final Map<String,LruCache<K,V>> cacheNameToLruCache=new HashMap<String,LruCache<K,V>>();
	@SuppressWarnings("rawtypes") private static LruCaches lruCaches=new LruCaches();
	static final long SerialVersionUID=0;
}