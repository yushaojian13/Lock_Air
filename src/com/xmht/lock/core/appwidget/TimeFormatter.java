
package com.xmht.lock.core.appwidget;

import java.util.Locale;

import com.xmht.lockair.R;

import android.content.Context;
import android.text.format.Time;

public class TimeFormatter {
    public static final String[] MONTH = {
            "Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"
    };

    public static final String[] WEEK = {
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri",
            "Sat"
    };

    public static final String[] MONTH_FULL = {
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    public static final String[] WEEK_FULL = {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday",
    };
    
    public static String getHour(Time time, boolean is24) {
        int hour = time.hour;
        // [13-23]
        if (!is24 && time.hour > 12) {
            hour = time.hour - 12; // [1-11]
        }

        if (hour < 10) {
            return "0" + hour;
        } else {
            return "" + hour;
        }
    }

    public static String getMinute(Time time) {
        if (time.minute < 10) {
            return "0" + time.minute;
        } else {
            return "" + time.minute;
        }
    }

    public static String getSecond(Time time) {
        if (time.second < 10) {
            return "0" + time.second;
        } else {
            return "" + time.second;
        }
    }

    public static String getTime(Time time, boolean is24, boolean showSecond, String split) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHour(time, is24));
        sb.append(split);
        sb.append(getMinute(time));
        if (showSecond) {
            sb.append(split);
            sb.append(getSecond(time));
        }
        return sb.toString();
    }

    public static String getWeek(Time time, boolean full, boolean upper) {
        String weekStr;
        if (full) {
            weekStr = WEEK_FULL[time.weekDay];
        } else {
            weekStr = WEEK[time.weekDay];
        }

        if (upper) {
            weekStr = weekStr.toUpperCase(Locale.ENGLISH);
        }

        return weekStr;
    }
    
    public static String getWeek(Context context, Time time) {
        String[] weekArray = context.getResources().getStringArray(R.array.array_week);
        return weekArray[time.weekDay];
    }
    
    public static String getDate(Time time, boolean full, boolean upper, String split) {
        return getMonth(time, full, upper) + split + getDay(time, false);
    }

    public static String getMonth(Time time, boolean full, boolean upper) {
        String monthStr;
        if (full) {
            monthStr = MONTH_FULL[time.month];
        } else {
            monthStr = MONTH[time.month];
        }

        if (upper) {
            monthStr = monthStr.toUpperCase(Locale.ENGLISH);
        }

        return monthStr;
    }

    public static String getDay(Time time, boolean has2digits) {
        if (time.monthDay < 10 && has2digits) {
            return "0" + time.monthDay;
        }

        return "" + time.monthDay;
    }
}
