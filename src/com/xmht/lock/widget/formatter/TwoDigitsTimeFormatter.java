
package com.xmht.lock.widget.formatter;

import com.xmht.lockair.R;

import android.content.Context;
import android.text.format.Time;

public abstract class TwoDigitsTimeFormatter implements ITimeFormatter {

    protected Context context;
    protected Time time;

    public TwoDigitsTimeFormatter(Context context, Time time) {
        this.context = context;
        this.time = time;
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
    public String getTime() {
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
            return "0" + (time.month + 1);
        }

        return "" + (time.month + 1);
    }

    @Override
    public String getYear() {
        return "" + time.year;
    }

    @Override
    public void setTime(Time time) {
        this.time = time;
    }

}
