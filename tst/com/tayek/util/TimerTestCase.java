package com.tayek.util;
import junit.framework.*;
public class TimerTestCase extends TestCase {
    public static Test suite() {
        TestSuite suite=new TestSuite(TimerTestCase.class);
        return suite;
    }
    class Sum { long sum; }
    public void test() {
        boolean l=false;
        Sum sum=new Sum();
        final long dt=((new Timer(new Timeable() {
            public void run(Object object) {
                long sum=0;
                for(long i=1;i<=n;i++) ((Sum)object).sum+=i;
                if(l) System.out.println(""+sum);
            }
        })).time(sum));
        if(l) System.out.println(dt); // shut up lint
        assertEquals(n*(n+1)/2,sum.sum);
    }
    public static void main(String[] args) {
        junit.textui.TestRunner.run(TimerTestCase.suite());
    }
    final long n=100;
}