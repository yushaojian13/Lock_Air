
package com.xmht.lock.core.data.time.format;

import java.util.Locale;

import com.xmht.lock.core.data.time.engine.TimeRaw;
import com.xmht.lock.core.data.time.observe.TimeLevelObserver;
import com.xmht.lock.core.debug.LOG;

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

    private static TimeRaw timeRaw;

    static {
        timeRaw = TimeRaw.getInstance();
    }

    public static void register(TimeLevelObserver observer) {
        if (observer == null) {
            LOG.e("Operation cannot be done, observer is null!");
            return;
        }

        timeRaw.registerObserver(observer);
    }

    public static void unregister(TimeLevelObserver observer) {
        if (observer == null) {
            LOG.e("Operation cannot be done, observer is null!");
            return;
        }

        timeRaw.unregisterObserver(observer);
    }

    public static String getDate(boolean full, boolean upper, String split) {
        return getMonth(full, upper) + split + getDay(false);
    }

    public static String getMonth(boolean full, boolean upper) {
        if (timeRaw.month < 1 || timeRaw.month > 12) {
            return "ERROR";
        }

        String monthStr;
        if (full) {
            monthStr = MONTH_FULL[timeRaw.month];
        } else {
            monthStr = MONTH[timeRaw.month];
        }

        if (upper) {
            monthStr = monthStr.toUpperCase(Locale.ENGLISH);
        }

        return monthStr;
    }

    public static String getDay(boolean has2digits) {
        if (timeRaw.day < 10 && has2digits) {
            return "0" + timeRaw.day;
        }

        return "" + timeRaw.day;
    }

    public static String getWeek(boolean full, boolean upper) {
        if (timeRaw.week < 0 || timeRaw.week > 6) {
            return "ERROR";
        }

        String weekStr;
        if (full) {
            weekStr = WEEK_FULL[timeRaw.week];
        } else {
            weekStr = WEEK[timeRaw.week];
        }

        if (upper) {
            weekStr = weekStr.toUpperCase(Locale.ENGLISH);
        }

        return weekStr;
    }

    public static boolean isAM() {
        if (timeRaw.hour < 12) {
            return true;
        }

        return false;
    }

    public static String getAM(boolean upper) {
        String am = "pm";
        if (timeRaw.hour < 12) {
            am = "am";
        }

        if (upper) {
            return am.toUpperCase(Locale.ENGLISH);
        }

        return am;
    }

    public static String getHour(boolean is24) {
        if (timeRaw.hour < 0 || timeRaw.hour > 23) {
            return "ERROR";
        }

        if (!is24 && timeRaw.hour > 12) {
            timeRaw.hour = timeRaw.hour - 12;
        }

        if (timeRaw.hour < 10) {
            return "0" + timeRaw.hour;
        } else {
            return "" + timeRaw.hour;
        }
    }

    public static String getMinute() {
        if (timeRaw.minute < 10) {
            return "0" + timeRaw.minute;
        } else {
            return "" + timeRaw.minute;
        }
    }

    public static String getSecond() {
        if (timeRaw.second < 10) {
            return "0" + timeRaw.second;
        } else {
            return "" + timeRaw.second;
        }
    }

    public static String getTime(boolean is24, boolean showSecond, String split) {
        StringBuilder sb = new StringBuilder();
        sb.append(TimeFormatter.getHour(is24));
        sb.append(split);
        sb.append(TimeFormatter.getMinute());
        if (showSecond) {
            sb.append(split);
            sb.append(TimeFormatter.getSecond());
        }
        return sb.toString();
    }
}
