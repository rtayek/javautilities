package com.tayek.util;
public class Timer {
	Timer() {
		this(null);
	}
	Timer(Timeable timeable) {
		this.timeable=timeable;
	}
	long time(Object object) {
		if(timeable==null)
			return 0l;
		Time t=new Time();
		timeable.run(object);
		return t.dt();
	}
	private Timeable timeable;
}