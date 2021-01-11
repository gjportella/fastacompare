package br.unb.cic.laico.checkpoint.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

	private static String LONG_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static SimpleDateFormat LONG_DF = new SimpleDateFormat(LONG_DATE_TIME_FORMAT);

	private DateUtil() {
	}

	public static Date parseTextToDate(String text) throws ParseException {
		return LONG_DF.parse(text);
	}

	public static String formatDateToText(Date date) {
		return LONG_DF.format(date);
	}
}
