package com.prodyna.conference.test.mock;

import java.util.List;

import com.prodyna.conference.mbean.Entry;
import com.prodyna.conference.mbean.PerformanceMXBean;

public class PerformanceMock implements PerformanceMXBean {

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Entry> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry getWorstByTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry getWorstByAverage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry getWorstByCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void report(String service, String method, long time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dump() {
		// TODO Auto-generated method stub

	}

}
