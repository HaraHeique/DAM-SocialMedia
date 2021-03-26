package com.example.socialmedia.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtil {

    public static final String DEFAULT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static Date convertToDateTime(String dateString) {
        return convertToDateTime(dateString, DEFAULT_DATETIME_FORMAT);
    }

    public static Date convertToDateTime(String dateString, String formatter) {
        Date date;

        try {
            date = new SimpleDateFormat(formatter).parse(dateString);
        }
        catch (ParseException e) {
            date = null;
        }

        return date;
    }

    public static Date convertToDate(String dateString) {
        return convertToDate(dateString, DEFAULT_DATE_FORMAT);
    }

    public static Date convertToDate(String dateString, String formatter) {
        Date date;

        try {
            date = new SimpleDateFormat(formatter).parse(dateString);
        }
        catch (ParseException e) {
            date = null;
        }

        return date;
    }

    public static String convertToStrDateTime(Date date) {
        return convertToStrDateTime(date, DEFAULT_DATETIME_FORMAT);
    }

    public static String convertToStrDateTime(Date date, String formatter) {
        return new SimpleDateFormat(formatter).format(date);
    }

    public static String convertToStrDate(Date date) {
        return convertToStrDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String convertToStrDate(Date date, String formatter) {
        return new SimpleDateFormat(formatter).format(date);
    }

    public static long ConvertToUnixTimeStamp(Date date) {
        return date.getTime() / 1000L;
    }

    public static Date convertToDate(long unixTimeStamp) {
        return new Date(unixTimeStamp * 1000L);
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);

        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);

        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
            (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }

        return diff;
    }
}
