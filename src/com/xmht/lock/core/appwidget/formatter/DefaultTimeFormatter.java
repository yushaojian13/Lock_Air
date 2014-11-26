package com.xmht.lock.core.appwidget.formatter;

import android.content.Context;
import android.text.format.Time;

import com.xmht.lockair.R;

public class DefaultTimeFormatter implements ITimeFormater {
    
    protected Context context;
    protected Time time;
    
    public DefaultTimeFormatter(Context context, Time time) {
        this.context = context;
        this.time = time;
    }

    @Override
    public String getSecond() {
            return "" + time.second;
   }

    @Override
    public String getMinute() {
            return "" + time.minute;
    }

    @Override
    public String getHour() {
            return "" + time.hour;
    }
    
    @Override
    public boolean isAm() {
        return time.hour < 12;
    }
    
    @Override
    public String getAmPm() {
        if (time.hour < 12) {
            return context.getString(R.string.am);
        } 
        
        return context.getString(R.string.pm);

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
        return "" + time.monthDay;
    }

    @Override
    public String getMonth() {
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
