package com.xmht.lock.core.appwidget.formatter;

public interface ITimeFormater {
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
}
