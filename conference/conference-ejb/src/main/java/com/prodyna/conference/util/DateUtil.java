package com.prodyna.conference.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Util-Methods for Datehandling.
 */
public class DateUtil {

	/**
	 * Das Format, dass zur Ausgabe in Servermeldungen verwendet werden soll.
	 */
	private static final DateFormat DEFAULT_FORMAT_PROTOTYPE = new SimpleDateFormat(
			"dd.MM.yyyy");

	/**
	 * Ein Tag, in Millisekunden.
	 */
	public static final long ONE_DAY_MILLIS = 1000 * 60 * 60 * 24;

	/**
	 * Das Format, dass zum Strippen benutzt wird.
	 */
	private static final SimpleDateFormat STRIP_FORMAT_PROTOTYPE = new SimpleDateFormat(
			"yyyyMMdd");

	private static final SimpleDateFormat HOUR_MINUTE_FORMAT_PROTOTYPE = new SimpleDateFormat(
			"dd.MM.yyyy k:mm");

	/**
	 * Gibt das Datum als formatierten String "dd.MM.yyyy" zurück.
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		if (date != null) {
			return ((SimpleDateFormat) DEFAULT_FORMAT_PROTOTYPE.clone())
					.format(date);
		}

		return "";
	}

	/**
	 * Gibt das Datum als formatierten String "yyyymmdd" zurück.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatCobol(Date date) {
		if (date != null) {
			return ((SimpleDateFormat) STRIP_FORMAT_PROTOTYPE.clone())
					.format(date);
		}

		return "";
	}

	/**
	 * 
	 * Liefert den String des übergebenen Datums im Format "dd.MM"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateDayMonth(Date date) {
		if (date != null) {
			return new SimpleDateFormat("dd.MM").format(date);
		}
		return "";
	}

	/**
	 * 
	 * Liefert die Nummer der Kalenderwoche für das übergebene Datum.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatWeekInYear(Date date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);

		return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
	}

	/**
	 * 
	 * Gibt eine Locale-abhängige Calendar Instanz zurück.
	 * 
	 * @return
	 */
	public static Calendar getCalendarInstance() {
		return Calendar.getInstance();
	}

	/**
	 * Liefert den String des übergebenen Datums im Format 'MMM'.
	 * 
	 */
	public static String formatMonth(Date date) {
		if (date != null) {
			return new SimpleDateFormat("MMM").format(date);
		}

		return "";
	}

	/**
	 * Überprüft, ob das angegebene {@link Date} im Verhältniss zu dem
	 * angegebenen {@link Date} arg1 entweder den gleichen Tag repräsentiert
	 * oder zeitlich davor ist.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean beforeOrSameDay(Date date1, Date date2) {
		Calendar cal1 = getCalendarInstance();
		Calendar cal2 = getCalendarInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) <= cal2.get(Calendar.DAY_OF_YEAR))
				|| cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR);
	}

	/**
	 * Überprüft, ob das angegebene {@link Date} im Verhältniss zu dem
	 * angegebenen {@link Date} arg1 entweder den gleichen Tag repräsentiert
	 * oder zeitlich danach ist.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean afterOrSameDay(Date date1, Date date2) {
		Calendar cal1 = getCalendarInstance();
		Calendar cal2 = getCalendarInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) >= cal2.get(Calendar.DAY_OF_YEAR))
				|| cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR);
	}

	/**
	 * Überprüft, ob das angegebene {@link Date} date1 zeitlich vor dem
	 * angegebenen {@link Date} date2 ist.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean before(Date date1, Date date2) {
		Calendar cal1 = getCalendarInstance();
		Calendar cal2 = getCalendarInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR))
				|| cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR);
	}

	/**
	 * Überprüft, ob das angegebene {@link Date} date1 zeitlich nach dem
	 * angegebenen {@link Date} date2 ist.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean after(Date date1, Date date2) {
		Calendar cal1 = getCalendarInstance();
		Calendar cal2 = getCalendarInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR))
				|| cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR);
	}

	/**
	 * Überprüft, ob das angegebene {@link Date} date im Zeitraum from bis to
	 * liegt (einschließlich).
	 * 
	 * @param date
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean inRange(Date date, Date from, Date to) {
		return afterOrSameDay(date, from) && beforeOrSameDay(date, to);
	}

	/**
	 * Überprüft, ob sich die angegebenen Zeiträume from1/to1 und from2/to2
	 * überlappen.
	 * 
	 * @param from1
	 * @param to1
	 * @param from2
	 * @param to2
	 * @return
	 */
	public static boolean overlappingRange(Date from1, Date to1, Date from2,
			Date to2) {
		return inRange(from1, from2, to2) || inRange(to1, from2, to2);
	}

	/**
	 * Liefert die dem Datum entsprechende Zeit in Millisekunden seit dem
	 * 1.1.1970, abzüglich der Tageszeit.
	 * 
	 * @param date
	 *            Das Datum.
	 * @return die Anzahl der Millisekunden.
	 */
	public static long strippedTime(Date date) {
		try {
			return ((SimpleDateFormat) STRIP_FORMAT_PROTOTYPE.clone()).parse(
					((SimpleDateFormat) STRIP_FORMAT_PROTOTYPE.clone())
							.format(date)).getTime();
		} catch (ParseException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Konstruiert ein neues {@link DateUtil} Objekt.
	 */
	private DateUtil() {
		// Versteckter Default-Konstruktor
	}

	/**
	 * Überprüft, ob die angegebenen {@link Date} Objekte die gleiche Tag
	 * repräsentieren.
	 * 
	 * @param arg0
	 *            Das erste zu prüfende Datum.
	 * @param arg1
	 *            Das zweite zu prüfende Datum.
	 * @return <code>true</code> wenn die beiden angegebenen {@link Date}
	 *         Objekte die gleiche Tag repräsentieren; andernfalls
	 *         <code>false</code>.
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		Calendar cal1 = getCalendarInstance();
		Calendar cal2 = getCalendarInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * Überprüft, ob die angegebenen {@link Date} Objekte in der gleichen
	 * Kalenderwoche liegen
	 * 
	 * @param arg0
	 *            Das erste zu prüfende Datum.
	 * @param arg1
	 *            Das zweite zu prüfende Datum.
	 * @return <code>true</code> wenn die beiden angegebenen {@link Date}
	 *         Objekte in der gleichen Kalenderwoche liegen; andernfalls
	 *         <code>false</code>.
	 */
	public static boolean isSameWeek(Date arg0, Date arg1) {
		Calendar cal1 = getCalendarInstance();
		cal1.setTime(arg0);

		Calendar cal2 = getCalendarInstance();
		cal2.setTime(arg1);

		return cal1.get(Calendar.WEEK_OF_YEAR) == cal2
				.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Überprüft, ob die angegebenen {@link Date} Objekte im gleichen Monat
	 * liegen
	 * 
	 * @param arg0
	 *            Das erste zu prüfende Datum.
	 * @param arg1
	 *            Das zweite zu prüfende Datum.
	 * @return <code>true</code> wenn die beiden angegebenen {@link Date}
	 *         Objekte im gleichen Monat liegen; andernfalls <code>false</code>.
	 */
	public static boolean isSameMonth(Date arg0, Date arg1) {
		Calendar cal1 = getCalendarInstance();
		cal1.setTime(arg0);

		Calendar cal2 = getCalendarInstance();
		cal2.setTime(arg1);

		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	}

	/**
	 * Liefert das Datum des Vortages (aus heutiger Sicht)
	 * 
	 * @return
	 */
	public static long yesterday() {
		Calendar cal = getCalendarInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		return strippedTime(cal.getTime());
	}

	/**
	 * Liefert das Datum abzüglich der Tageszeit.
	 * 
	 * @param date
	 *            Das Datum.
	 * @return die Anzahl der Millisekunden.
	 */
	public static Date strippedDate(Date date) {
		try {
			return ((SimpleDateFormat) STRIP_FORMAT_PROTOTYPE.clone())
					.parse(((SimpleDateFormat) STRIP_FORMAT_PROTOTYPE.clone())
							.format(date));
		} catch (ParseException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Addiert die angegebene Anzahl von Tagen zum angegebenen Datum.
	 * 
	 * @param date
	 *            Das Datum.
	 * @param numberOfDays
	 *            Die Anzahl der Tage.
	 */
	public static void shift(Date date, int numberOfDays) {
		date.setTime(date.getTime() + (ONE_DAY_MILLIS * numberOfDays));
	}

	/**
	 * Addiert einen Tag zum angegebenen Datum.
	 * 
	 * @param date
	 *            Das Datum.
	 */
	public static void advance(Date date) {
		shift(date, 1);
	}

	/**
	 * Subtrahiert einen Tag vom angegebenen Datum.
	 * 
	 * @param date
	 *            Das Datum.
	 */
	public static void regress(Date date) {
		shift(date, -1);
	}

	/**
	 * 
	 * Gibt das übergebene Datum in der MOJA-Notation zurück.
	 * 
	 * Date: Jan.1999 -> 199.901 01 steht für Januar 199.9 für das Jahr.
	 * 
	 * @param calendar
	 * @return
	 */
	public static Long getLongRepresentation(Calendar calendar) {
		return Long
				.valueOf(String.valueOf(calendar.get(Calendar.YEAR))
						+ String.valueOf(addZeros(
								calendar.get(Calendar.MONTH) + 1, 2)));
	}

	/**
	 * 
	 * Fügt führende 0-en bis zu angegebenen Länge hinzu.
	 * 
	 * @param number
	 * @param length
	 * @return
	 */
	private static String addZeros(int number, int length) {
		String s = String.valueOf(number);
		while (s.length() < length)
			s = "0" + s;
		return s;
	}

	/**
	 * 
	 * Gibt ein Calendar-Objekt zurück, welches das Datum als Vorjahreswert
	 * enthält.
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar regressOneYear(Date date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);
		return calendar;
	}

	/**
	 * 
	 * Gibt die Differenz zwischen den beiden Daten in Tagen zurück.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static final int getDaysDifference(Date date1, Date date2) {
		Calendar cal1 = getCalendarInstance();
		cal1.setTime(date1);

		Calendar cal2 = getCalendarInstance();
		cal2.setTime(date2);

		int daysBetween = 0;
		while (cal1.before(cal2)) {
			cal1.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	/**
	 * 
	 * Erzeugt ein Datum nach 'dd.mm.yyyy'-Format.
	 * 
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String string) throws ParseException {
		return ((SimpleDateFormat) DEFAULT_FORMAT_PROTOTYPE.clone())
				.parse(string);
	}

	/**
	 * 
	 * Erzeugt ein Datum nach 'dd.mm.yyyy k:mm'-Format.
	 * 
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date parseHourMinute(String string) throws ParseException {
		return ((SimpleDateFormat) HOUR_MINUTE_FORMAT_PROTOTYPE.clone())
				.parse(string);
	}

	/**
	 * 
	 * Gibt den ersten Tag der identische Woche des übergebenen Datums zurück.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal = getCalendarInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * Gibt den ersten Tag im identische Monat des übergebenen Datums zurück.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = getCalendarInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * Gibt den Montag der identische Woche des übergebenen Datums zurück.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar cal = getCalendarInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK,
				cal.getActualMaximum(Calendar.DAY_OF_WEEK));
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * Gibt den ersten Tag im identische Monat des übergebenen Datums zurück.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = getCalendarInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
