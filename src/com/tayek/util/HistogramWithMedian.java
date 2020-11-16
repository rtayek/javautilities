package com.tayek.util;
// should be called stats and should hane two imiplementatons
// one like this that uses buckets and one that keeps an accurate frequency distribution.
// maybe use http://jakarta.apache.org/commons/math/userguide/stat.html instead
public class HistogramWithMedian {
	public HistogramWithMedian(int bins,double low,double high) {
		//low>high should throw
		// low=high will never fill any bins
		// bins=1 should work
		// numBins=0 should work also, but maybe should force it to be one?
		this.bins=bins;
		bin=new int[bins];
		this.low=low;
		this.high=high;
		range=high-low;
	}
	public void add(double[] x) {
		for(int i=0;i<x.length;i++)
			add(x[i]);
	}
	public void add(double x) {
		n++;
		sum+=x;
		final double x2=x*x;
		sum2+=x2;
		//sum3+=x2*x;
		//sum4+=x2*x2;
		min=Math.min(min,x);
		max=Math.max(max,x);
		if(x>=high)
			overflows++;
		else if(x<low)
			underflows++;
		else {
			double val=x-low;
			int index=(int)(bins*(val/range));
			bin[index]++;
		}
	}
	public void clear() {
		for(int i=0;i<bins;i++)
			bin[i]=0;
		overflows=0;
		underflows=0;
	}
	public int n() {
		return n;
	}
	public double low() {
		return low;
	}
	public double high() {
		return high;
	}
	public double range() {
		return high()-low();
	}
	public int bins() {
		return bins;
	}
	public double min() {
		return n==0?Double.NaN:min;
	}
	public double max() {
		return n==0?Double.NaN:max;
	}
	public double sum() {
		return n==0?Double.NaN:sum;
	}
	public double mean() {
		return n==0?Double.NaN:sum/n;
	}
	public double variance() {
		return n==0?Double.NaN:sum2/n-mean()*mean();
	}
	public int bin(int index) {
		if(index<0)
			return underflows;
		else if(index>=bins)
			return overflows;
		else
			return bin[index];
	}
	public double maxDifference() {
		double max=0;
		for(int i=0;i<bins();i++)
			max=Math.max(max,Math.abs(bin(i)-n()/(double)bins())/(n()/(double)bins()));
		return max;
	}
	public String toString() {
		final StringBuffer sb=new StringBuffer();
		sb.append((float)min()).append("<=").append((float)mean()).append("<=").append((float)max()).append(" ");
		sb.append(bin(-1)).append(",[");
		for(int i=0;i<bins;i++)
			sb.append(i>0?",":"").append(bin(i));
		sb.append("],").append(bin(bins));
		System.out.println(sum3+" "+sum4); // shut up lint
		return sb.toString();
	}
	public double median() {
		//CArray<double,double> sorted;
		//values(sorted);
		//sortValues(sorted);
		//int n_=sorted.GetSize();
		//double value=n_==0?0.:n_%2?(sorted)[n_/2]:((sorted)[(n_-1)/2]+(sorted)[n_/2])/2.;
		//return value;
		throw new RuntimeException("not implemented");
	}
	private int[] bin;
	private int n,bins,underflows,overflows;
	private final double low,high,range;
	private double min=Double.MAX_VALUE,max=Double.MIN_VALUE,sum,sum2,sum3,sum4;
	// probably have to add median back in here, but do it so both flavors are the same size, since we use arrays of these
}