package com.example.socialmedia.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static String ConvertToStrDate(Date date) {
        return defaultDateFormatter.format(date);
    }
}
