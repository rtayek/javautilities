package com.tayek.util;
public class Semaphore {
	public Semaphore(int n) {
		this.n=n;
	}
	public Semaphore() {
		this(0);
	}
	synchronized public void p() throws InterruptedException {
		while(n==0)
			wait();
		--n;
	}
	synchronized public void v() {
		++n;
		notify();
	}
	private int n;
}