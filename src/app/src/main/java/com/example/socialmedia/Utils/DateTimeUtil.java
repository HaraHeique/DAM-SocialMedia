package com.example.socialmedia.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtil {

    public static final DateFormat defaultDateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final DateFormat defaultDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static Date ConvertToDateTime(String dateString) {
        Date date;

        try {
            date = defaultDateTimeFormatter.parse(dateString);
        }
        catch (ParseException e) {
            date = null;
        }

        return date;
    }

    public static Date ConvertToDate(String dateString) {
        Date date;

        try {
            date = defaultDateFormatter.parse(dateString);
        }
        catch (ParseException e) {
            date = null;
        }

        return date;
    }

    public static String ConvertToStrDateTime(Date date) {
        return defaultDateTimeFormatter.format(date);
    }

    public static String ConvertToStrDateTime(Date date, String formatter) {
        return new SimpleDateFormat(formatter).format(date);
    }

    public static String ConvertToStrDate(Date date) {
        return defaultDateFormatter.format(date);
    }

    public static long ConvertToUnixTimeStamp(Date date) {
        return date.getTime() / 1000L;
    }

    public static Date ConvertToDate(long unixTimeStamp) {
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
