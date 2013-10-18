package com.prodyna.conference.mbean;

import java.util.List;

public interface PerformanceMXBean {
	public void reset();

	public List<Entry> getAll();

	public Entry getWorstByTime();

	public Entry getWorstByAverage();

	public Entry getWorstByCount();

	public int getCount();

	public void report(String service, String method, long time);

	public void dump();
}
