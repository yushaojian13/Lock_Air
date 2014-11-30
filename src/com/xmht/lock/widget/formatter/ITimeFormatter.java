package com.xmht.lock.widget.formatter;

import android.text.format.Time;

public interface ITimeFormatter {
    public String getSecond();
    public String getMinute();
    public String getHour();
    public boolean isAm();
    public String getAmPm();
    public String getTime();
    public String getDay();
    public String getMonth();
    public String getDate();
    public String getWeek();
    public String getYear();
    public void setTime(Time time);
}
