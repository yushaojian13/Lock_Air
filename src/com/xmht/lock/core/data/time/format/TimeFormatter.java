
package com.xmht.lock.core.data.time.format;

import java.util.Locale;

import com.xmht.lock.core.data.time.engine.TimeRaw;
import com.xmht.lock.core.data.time.observe.TimeLevelObserver;
import com.ysj.tools.debug.LOG;

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
            "Friday", "Saturday"
    };

    private static TimeRaw timeRaw;

    static {
        timeRaw = TimeRaw.getInstance();
    }

    private TimeFormatter() {
    }

    public static void register(TimeLevelObserver observer) {
        LOG.v("");
        if (observer == null) {
            LOG.e("Operation cannot be done, observer is null!");
            return;
        }

        timeRaw.registerObserver(observer);
    }

    public static void unregister(TimeLevelObserver observer) {
        LOG.v("");
        if (observer == null) {
            LOG.e("Operation cannot be done, observer is null!");
            return;
        }

        timeRaw.unregisterObserver(observer);
    }

    public static String getSecond() {
        if (timeRaw.getSecond() < 10) {
            return "0" + timeRaw.getSecond();
        } else {
            return "" + timeRaw.getSecond();
        }
    }

    public static String getMinute() {
        if (timeRaw.getMinute() < 10) {
            return "0" + timeRaw.getMinute();
        } else {
            return "" + timeRaw.getMinute();
        }
    }

    public static String getHour(boolean is24) {
        if (timeRaw.getHour() < 0 || timeRaw.getHour() > 23) {
            return "ERROR";
        }

        int hour = timeRaw.getHour();
        // [13-23]
        if (!is24 && timeRaw.getHour() > 12) {
            hour = timeRaw.getHour() - 12; // [1-11]
        }

        if (hour < 10) {
            return "0" + hour;
        } else {
            return "" + hour;
        }
    }

    public static boolean isAM() {
        if (timeRaw.getHour() < 12) {
            return true;
        }

        return false;
    }

    public static String getAM(boolean upper) {
        String am = "pm";
        if (isAM()) {
            am = "am";
        }

        if (upper) {
            am = am.toUpperCase(Locale.ENGLISH);
        }

        return am;
    }

    public static String getTime(boolean is24, boolean showSecond, String split) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHour(is24));
        sb.append(split);
        sb.append(getMinute());
        if (showSecond) {
            sb.append(split);
            sb.append(getSecond());
        }
        return sb.toString();
    }

    public static String getDay(boolean has2digits) {
        if (timeRaw.getDay() < 10 && has2digits) {
            return "0" + timeRaw.getDay();
        }

        return "" + timeRaw.getDay();
    }

    public static String getMonth(boolean full, boolean upper) {
        if (timeRaw.getMonth() < 1 || timeRaw.getMonth() > 12) {
            return "ERROR";
        }

        String monthStr;
        if (full) {
            monthStr = MONTH_FULL[timeRaw.getMonth()];
        } else {
            monthStr = MONTH[timeRaw.getMonth()];
        }

        if (upper) {
            monthStr = monthStr.toUpperCase(Locale.ENGLISH);
        }

        return monthStr;
    }

    public static String getDate(boolean full, boolean upper, String split) {
        return getMonth(full, upper) + split + getDay(false);
    }

    public static String getWeek(boolean full, boolean upper) {
        if (timeRaw.getWeek() < 0 || timeRaw.getWeek() > 6) {
            return "ERROR";
        }

        String weekStr;
        if (full) {
            weekStr = WEEK_FULL[timeRaw.getWeek()];
        } else {
            weekStr = WEEK[timeRaw.getWeek()];
        }

        if (upper) {
            weekStr = weekStr.toUpperCase(Locale.ENGLISH);
        }

        return weekStr;
    }

}
