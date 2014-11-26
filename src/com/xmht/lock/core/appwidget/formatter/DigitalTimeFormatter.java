package com.xmht.lock.core.appwidget.formatter;

import android.content.Context;
import android.text.format.Time;

import com.xmht.lockair.R;

public class DigitalTimeFormatter extends DefaultTimeFormatter {
    
    public DigitalTimeFormatter(Context context, Time time) {
        super(context, time);
    }

    @Override
    public String getSecond() {
        if (time.second < 10) {
            return "0" + time.second;
        } else {
            return "" + time.second;
        }
   }

    @Override
    public String getMinute() {
        if (time.minute < 10) {
            return "0" + time.minute;
        } else {
            return "" + time.minute;
        }
    }

    @Override
    public String getHour() {
        int hour = time.hour;

        if (hour < 10) {
            return "0" + hour;
        } else {
            return "" + hour;
        }
    }
    
    @Override
    public boolean isAm() {
        return false;
    }
    
    @Override
    public String getAmPm() {
        return "";
    }

    @Override
    public String getTime( ) {
        StringBuilder sb = new StringBuilder();
        sb.append(getHour());
        sb.append(":");
        sb.append(getMinute());
        return sb.toString();
    }

    @Override
    public String getDay() {
        if (time.monthDay < 10) {
            return "0" + time.monthDay;
        }

        return "" + time.monthDay;
    }

    @Override
    public String getMonth() {
        if (time.month < 10) {
            return "0" + time.month;
        }

        return "" + time.month;
    }
    
    @Override
    public String getYear() {
        return "" + time.year;
    }

    @Override
    public String getDate() {
        return getMonth() + ":" + getDay();
    }

    @Override
    public String getWeek() {
        String[] weekArray = context.getResources().getStringArray(R.array.array_week_short);
        return weekArray[time.weekDay];
    }

}
