package com.prodyna.conference.test.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date getDate(int year, int month, int day) {
		return getDate(year, month, day, 0, 0);
	}

	public static Date getDate(int year, int month, int day, int hour,
			int minute) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTime();
	}
}
