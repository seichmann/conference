package com.prodyna.conference.test.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		return cal.getTime();
	}
}
