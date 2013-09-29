package com.prodyna.conference.mbean;

public class Entry {

	private final String service;
	private final String method;
	private long count;
	private long sum;
	private Long minTime;
	private Long maxTime;

	public Entry(String service, String method) {
		super();
		this.service = service;
		this.method = method;
	}

	public synchronized void report(long time) {
		count++;
		sum += time;
		if (minTime == null) {
			minTime = time;
		} else if (time < minTime) {
			minTime = time;
		}

		if (maxTime == null) {
			maxTime = time;
		} else if (time > maxTime) {
			maxTime = time;
		}
	}

	public float getAverage() {
		return (float) sum / (float) count;
	}

	public long getCount() {
		return count;
	}

	public long getMinTime() {
		return minTime;
	}

	public long getMaxTime() {
		return maxTime;
	}

	public String getService() {
		return service;
	}

	public String getMethod() {
		return method;
	}
}
