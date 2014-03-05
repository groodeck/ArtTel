package org.arttel.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;

public class Translator {
	
	private static final Logger log = Logger.getLogger(Translator.class);
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static final String TRUE = "true";

	private Translator(){}
	
	public static Date parseDate(final String dateStr){
		return parseDate(dateStr, null);
	}
	
	public static Date parseDate(final String dateStr, String format){
		
		if(dateStr == null || !dateStr.matches("\\d{4}-\\d{2}-\\d{2}")){
			return null;
		}
		
		if(format == null){
			format = DEFAULT_DATE_FORMAT;
		}
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			final java.util.Date plainDate = sdf.parse(dateStr); 
			date = new Date(plainDate.getTime());
		} catch (ParseException e) {
			log.error("Cannot convert date: " + dateStr, e);
		}
		
		return date;
	}
	
	public static String parseDecimalStr(final String source){
		final String result;
		final Double doubleValue = parseDoubleIfNotNull(source);
		if(doubleValue == null){
			result = null;
		} else {
			result = doubleValue.toString();
		}
		return result;
	}
	
	public static String parseIntegerStr(final String source){
		final String result;
		final Double doubleValue = parseDoubleIfNotNull(source);
		if(doubleValue == null){
			result = null;
		} else {
			result = "" + doubleValue.intValue();
		}
		return result;
	}

	public static String emptyAsNull(final String parameter) {
		
		return (parameter == null || parameter.isEmpty()) ? null : parameter;
	}

	public static String join(final String... parts) {
		return Joiner.on(" ").skipNulls().join(parts);
	}

	public static String complement(final String word, final char c, final int resultLenght) {
		
		final StringBuilder sb = new StringBuilder(word != null ? word : "");
		final int wordLenght = sb.length();
		
		for(int i = resultLenght; i >= wordLenght; i-- ){
			sb.append(c);
		}
		return sb.toString();
	}

	public static int parseInt(String value) {
		int result = 0;
		if(value != null){
			result = parseIntNotNull(value);
		}
		return result;
	}

	private static int parseIntNotNull(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static boolean getBoolean(String parameter) {
		return StringUtils.isNotEmpty(parameter) && TRUE.equals(parameter);
	}
	
	public static Double parseDoubleIfNotNull(final String source){
		final Double result;
		if(StringUtils.isNotEmpty(source)){
			result = parseDouble(source.replace(',', '.'));
		} else {
			result = null;
		}
		return result;
	}

	private static Double parseDouble(final String source) {
		try{
			return new BigDecimal(source).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static String getCalendarMonthName(Calendar calendarDayFrom) {
		final String result;
		switch (calendarDayFrom.get(Calendar.MONTH)) {
		case 0:
			result = "Styczeñ";
			break;
		case 1:
			result = "Luty";
			break;
		case 2:
			result = "Marzec";
			break;
		case 3:
			result = "Kwiecieñ";
			break;
		case 4:
			result = "Maj";
			break;
		case 5:
			result = "Czerwiec";
			break;
		case 6:
			result = "Lipiec";
			break;
		case 7:
			result = "Sierpieñ";
			break;
		case 8:
			result = "Wrzesieñ";
			break;
		case 9:
			result = "PaŸdziernik";
			break;
		case 10:
			result = "Listopad";
			break;
		case 11:
			result = "Grudzieñ";
			break;
		default:
			result = "";
			
		}
		return result;
	}

	public static BigDecimal getDecimal(String vatAmount) {
		
		BigDecimal decimal = new BigDecimal(0);
		try {
			decimal = new BigDecimal(vatAmount);
		} catch (Exception e) {
			log.warn("Cannot convert to decimal value: " + vatAmount);
		}
		return decimal;
	}
}
