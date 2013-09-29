package com.prodyna.conference.mbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;

public class Performance implements PerformanceMBean {

	private Map<String, Entry> entries = new HashMap<String, Entry>();

	@Inject
	private Logger logger;

	@Override
	public void reset() {
		entries.clear();
	}

	@Override
	public List<Entry> getAll() {
		return new ArrayList<Entry>(entries.values());
	}

	@Override
	public Entry getWorstByTime() {
		Entry maxEntry = null;
		if (entries.size() > 0) {
			maxEntry = entries.get(0);
			for (int i = 1; i < entries.size(); i++) {
				Entry entry = entries.get(i);
				if (entry.getMaxTime() > maxEntry.getMaxTime()) {
					maxEntry = entry;
				}
			}
		}
		return maxEntry;
	}

	@Override
	public Entry getWorstByAverage() {
		Entry avgEntry = null;
		if (entries.size() > 0) {
			avgEntry = entries.get(0);
			for (int i = 1; i < entries.size(); i++) {
				Entry entry = entries.get(i);
				if (entry.getAverage() > avgEntry.getAverage()) {
					avgEntry = entry;
				}
			}
		}
		return avgEntry;
	}

	@Override
	public Entry getWorstByCount() {
		Entry countEntry = null;
		if (entries.size() > 0) {
			countEntry = entries.get(0);
			for (int i = 1; i < entries.size(); i++) {
				Entry entry = entries.get(i);
				if (entry.getCount() < countEntry.getCount()) {
					countEntry = entry;
				}
			}
		}
		return countEntry;
	}

	@Override
	public int getCount() {
		return entries.size();
	}

	@Override
	public synchronized void report(String service, String method, long time) {
		String key = service + ":" + method;
		Entry entry = entries.get(key);

		if (entry == null) {
			entry = new Entry(service, method);
			entries.put(key, entry);
		}
		entry.report(time);
	}

	@Override
	public void dump() {
		logger.info("============ Dump Performance Data: ==============\n");

		CSVBuilder builder = new CSVBuilder();
		// Header
		builder.appendCell("Service").appendCell("Method")
				.appendCell("MaxTime").appendCell("MinTime")
				.appendCell("Avg.Time").newLine();

		// Content
		for (java.util.Map.Entry<String, Entry> entry : entries.entrySet()) {
			Entry value = entry.getValue();
			builder.appendCell(value.getService())
					.appendCell(value.getMethod())
					.appendCell(String.valueOf(value.getMaxTime()))
					.appendCell(String.valueOf(value.getMinTime()))
					.appendCell(String.valueOf(value.getAverage())).newLine();
		}

		logger.info(builder.dump());
	}

	class CSVBuilder {
		private static final String NEW_LINE = "\n";
		private static final String CELL_DELIMITER = ",";
		private StringBuilder builder = new StringBuilder();

		public CSVBuilder appendCell(String value) {
			builder.append(value);
			builder.append(CELL_DELIMITER);

			return this;
		}

		public CSVBuilder newLine() {
			builder.append(NEW_LINE);
			return this;
		}

		public String dump() {
			return builder.toString();
		}
	}
}
