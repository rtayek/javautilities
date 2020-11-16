package com.tayek.util;
import junit.framework.*;
import java.util.*;
import java.util.logging.*;
public class LruMapTestCase extends TestCase {
	public LruMapTestCase(String name) {
		super(name);
	}
	public static Test suite() {
		TestSuite suite=new TestSuite();
		suite.addTest(new LruMapTestCase("testSanity"));
		suite.addTest(new LruMapTestCase("test1"));
		suite.addTest(new LruMapTestCase("testInOrder"));
		suite.addTest(new LruMapTestCase("testReverseOrder"));
		suite.addTest(new LruMapTestCase("testRandomOrder"));
		suite.addTest(new LruMapTestCase("testLinkedHashMapForSpeed"));
		suite.addTest(new LruMapTestCase("testLruMapForSpeed"));
		return suite;
	}
	protected void setUp() throws Exception {
		super.setUp();
		logger.info(getName());
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testSanity() {
		final Integer value=Integer.valueOf(2);
		final Integer key=Integer.valueOf(1);
		final Integer o=integerToIntegerLruMap.get(key);
		assertNull(o);
		final Integer o2=integerToIntegerLruMap.put(key,value);
		assertNull(o2);
		final Integer o3=integerToIntegerLruMap.get(key);
		assertNotNull(o3);
		assertEquals(value,o3);
	}
	public void test1() {
		final int size=10;
		final int n=20;
		integerToIntegerLruMap=new LruMap<Integer,Integer>(size);
		for(int i=0;i<n;i++)
			integerToIntegerLruMap.put(Integer.valueOf(i),Integer.valueOf(i));
		logger.fine("before: "+integerToIntegerLruMap);
		for(int i=n-1;i>=0;i--)
			assertEquals(i<size?null:Integer.valueOf(i),integerToIntegerLruMap.get(Integer.valueOf(i)));
		logger.fine("after: "+integerToIntegerLruMap);
	}
	void w(final int time) {
		if(time>0) try {
			Thread.sleep(time);
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	void use(final Integer key) {
		if(integerToByteArrayLruMap.get(key)!=null)
			hits++;
		else {
			misses++;
			w(waitTime);
			integerToByteArrayLruMap.put(key,new byte[bytes]);
		}
	}
	void inOrder(final int n) {
		for(int i=0;i<n;i++) {
			final Integer key=Integer.valueOf(i);
			use(key);
		}
	}
	void reverseOrder(int n) {
		for(int i=n-1;i>=0;i--) {
			final Integer key=Integer.valueOf(i);
			use(key);
		}
	}
	void randomOrder(final int unique,final int howMany) {
		for(int i=0;i<howMany;i++) {
			final Integer key=Integer.valueOf(r.nextInt(unique));
			use(key);
		}
	}
	void doInorder(final int size,final int n) {
		integerToByteArrayLruMap=new LruMap<Integer,byte[]>(size);
		for(int i=0;i<4;i++) {
			hits=misses=0;
			final Time t=new Time();
			inOrder(n);
			final long dt=t.dt();
			logger.fine(n+" items/"+dt+" ms. = "+(float)(n*1000./dt)+" items/second, hits: "+hits+", misses: "+misses);
			logger.fine("lruCache: "+integerToByteArrayLruMap);
			if(i==0) {
				assertEquals(0,hits);
				assertEquals(n,misses);
			} else {
				assertEquals(size<n?0:n,hits);
				assertEquals(size<n?n:0,misses);
			}
		}
	}
	public void testInOrder() {
		waitTime=0;
		bytes=1000;
		final int n=10;
		doInorder(0,n);
		doInorder(1,n);
		doInorder(n/2-1,n);
		doInorder(n/2,n);
		doInorder(n/2+1,n);
		doInorder(n-1,n);
		doInorder(n,n);
		doInorder(n+1,n);
	}
	void doReverseOrder(final int size,final int n) {
		integerToByteArrayLruMap=new LruMap<Integer,byte[]>(size);
		for(int i=0;i<3;i++) {
			hits=misses=0;
			final Time t=new Time();
			if(i==0)
				inOrder(n);
			else reverseOrder(n);
			final long dt=t.dt();
			logger.fine(n+" items/"+dt+" ms. = "+(float)(n*1000./dt)+" items/second, hits: "+hits+", misses: "+misses);
			logger.fine("lruCache: "+integerToByteArrayLruMap);
			if(i==0) {
				assertEquals(0,hits);
				assertEquals(n,misses);
			} else if(i==1) {
				assertEquals(size<n?size:n,hits);
				assertEquals(size<n?n-size:0,misses);
			} else {
				assertEquals(size<n?0:n,hits);
				assertEquals(size<n?n:0,misses);
			}
		}
	}
	public void testReverseOrder() {
		waitTime=0;
		bytes=1000;
		final int n=10;
		doReverseOrder(0,n);
		doReverseOrder(1,n);
		doReverseOrder(n/2-1,n);
		doReverseOrder(n/2,n);
		doReverseOrder(n/2+1,n);
		doReverseOrder(n-1,n);
		doReverseOrder(n,n);
		doReverseOrder(n+1,n);
	}
	int percent(final int i,final int n) {
		return (int)Math.round(i*100./n);
	}
	void doRandomOrder(final int size,final int unique,final int howMany,final int n) {
		logger.info("lruCache size="+size+", "+percent(size,unique)+"%of unique="+unique+", howMany="+howMany+", "+percent(howMany,n)+"% of total");
		integerToByteArrayLruMap=new LruMap<Integer,byte[]>(size);
		Time t=null;
		long dt=0;
		for(int i=0;i<30;i++) {
			hits=misses=0;
			t=new Time();
			randomOrder(unique,howMany);
			dt=t.dt();
			logger.fine(n+" items/"+dt+" ms. = "+(float)(n*1000./dt)+" items/sec."+", hit: "+hits+", miss: "+misses+", "+percent(hits,howMany)+"%");
			logger.fine("diff="+(float)Math.abs(size/(double)unique-hits/(double)(hits+misses)));
			logger.fine("lruCache: "+integerToByteArrayLruMap);
		}
		double diff=size/(double)unique-hits/(double)(hits+misses);
		if(Math.abs(diff)>.1) {
			logger.warning(n+" items/"+dt+" ms. = "+(float)(n*1000./dt)+" items/sec."+", hit: "+hits+", miss: "+misses+", "+percent(hits,howMany)+"%");
			logger.warning("diff="+(float)diff);
			logger.warning("lruCache: "+integerToByteArrayLruMap);
		}
	}
	public void testRandomOrder() {
		waitTime=0;
		bytes=100000;
		final int n=1000;
		doRandomOrder(0*n/1000,n/20,n/50,n);
		doRandomOrder(10*n/1000,n/20,n/50,n);
		doRandomOrder(20*n/1000,n/20,n/50,n);
		doRandomOrder(20*n/1000,n/20,n/50,n);
	}
	public void testLinkedHashMapForSpeed() {
		final Map<Integer,Integer> m=new LinkedHashMap<Integer,Integer>();
		final int n=100000;
		final Time t=new Time();
		for(int i=0;i<n;i++)
			m.put(Integer.valueOf(i),Integer.valueOf(5*i%3));
		final long dt=t.dt();
		logger.info(n+" items/"+dt+" ms. = "+(float)(n*1000./dt)+" items/second.");
		final Time t2=new Time();
		final int n2=10*n;
		for(int i=0;i<n2;i++)
			m.get(Integer.valueOf(i%(2*n)));
		final long dt2=t2.dt();
		logger.info(n2+" items/"+dt2+" ms. = "+(float)(n2*1000./dt2)+" items/second.");
	}
	public void testLruMapForSpeed() {
		final int n=10000;
		final LruMap<Integer,Integer> m=new LruMap<Integer,Integer>(n);
		final Time t=new Time();
		for(int i=0;i<n;i++)
			m.put(Integer.valueOf(i),Integer.valueOf(5*i%3));
		final long dt=t.dt();
		logger.info(n+" items/"+dt+" ms. = "+(float)(n*1000./dt)+" items/second.");
		final Time t2=new Time();
		final int n2=10*n;
		for(int i=0;i<n2;i++)
			if(m.get(Integer.valueOf(i%(2*n)))==null) m.put(Integer.valueOf(i%(2*n)),Integer.valueOf(i%(2*n)));
		final long dt2=t2.dt();
		logger.info(n2+" items/"+dt2+" ms. = "+(float)(n2*1000./dt2)+" items/second.");
	}
	public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(suite());
	}
	int hits,misses,bytes,waitTime;
	LruMap<Integer,Integer> integerToIntegerLruMap=new LruMap<Integer,Integer>(10);
	LruMap<Integer,byte[]> integerToByteArrayLruMap=new LruMap<Integer,byte[]>(10);
	Random r=new Random();
	public static final Logger logger=Logger.getLogger(LruMapTestCase.class.getName());
	static Level level=Level.INFO;
	static {
		logger.setLevel(level);
		final Handler handler=new ConsoleHandler();
		handler.setLevel(Level.FINEST);
		logger.setUseParentHandlers(false);
		handler.setFormatter(new ASimpleFormatter());
		logger.addHandler(handler);
	};
}