package com.vivekmohanan.indianculturalholidaysapp.utils;

import com.vivekmohanan.indianculturalholidaysapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static Date stringToDate(String dateAsString, String form) {
        SimpleDateFormat format = new SimpleDateFormat(form, Locale.getDefault());
        try {
            return format.parse(dateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String dateToString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }
}
