package com.tayek.util;
import junit.framework.*;
import java.util.*;
import java.util.logging.*;
public class HistogramTestCase extends TestCase {
	public HistogramTestCase(String name) {
		super(name);
	}
	public static Test suite() {
		TestSuite suite=new TestSuite();
		suite.addTest(new HistogramTestCase("test1"));
		suite.addTest(new HistogramTestCase("test2"));
		suite.addTest(new HistogramTestCase("test3"));
		suite.addTest(new HistogramTestCase("test4"));
		return suite;
	}
	protected void setUp() throws Exception {
		super.setUp();
		logger.info(getName());
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	int randomKey1(final int n) {
		return random.nextInt(n);
	}
	int randomKey2(final int n,final int total) {
		final int k=random.nextInt(n);
		return k*total/n; // seems like this one was failing to get to the high, but it seems to be working now
	}
	int randomKey3(final int n,final int total) {
		final int k=random.nextInt(n);
		return (k*total+total-1)/n;
	}
	int randomKey4(final int n,final int total) {
		final int k=random.nextInt(n);
		return (int)Math.ceil(k*total/(double)n);
	}
	void doTestN(final int total,final int function,final int samples) { // really testing randomKey
		for(int i=0;i<samples;i++) {
			int sample=0;
			switch(function) {
				case 1:
					sample=(int)histogram.low()+randomKey1((int)histogram.range());
					break;
				case 2:
					sample=(int)histogram.low()+randomKey2((int)histogram.range(),total);
					break;
				case 3:
					sample=(int)histogram.low()+randomKey3((int)histogram.range(),total);
					break;
				case 4:
					sample=(int)histogram.low()+randomKey4((int)histogram.range(),total);
					break;
				default:
					fail("default in switch in doTest!");
			}
			histogram.add(sample);
		}
		assertEquals("underflows",0,histogram.bin(-1));
		assertEquals("overflows",0,histogram.bin(histogram.bins()));
	}
	public void doTest(final int function) {
		final int total=10000;
		final int bins=10;
		//final int samples=bins/2;
		//final int n=1000/samples;
		final int samples=100;
		final int n=10;
		histogram=new HistogramWithMedian(bins,0,total);
		for(int i=0;i<n;i++) {
			doTestN(total,function,samples);
			logger.fine(histogram.toString());
			logger.fine("max difference="+(float)histogram.maxDifference());
		}
		logger.info(histogram.toString());
		logger.info("max difference="+(float)histogram.maxDifference());
	}
	public void test1() {
		doTest(1);
	}
	public void test2() {
		doTest(2);
	}
	public void test3() {
		doTest(3);
	}
	public void test4() {
		doTest(4);
	}
	public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(suite());
	}
	HistogramWithMedian histogram;
	final Random random=new Random();
	public static final Logger logger=Logger.getLogger(HistogramTestCase.class.getName());
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