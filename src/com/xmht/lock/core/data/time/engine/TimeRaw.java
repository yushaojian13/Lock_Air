
package com.xmht.lock.core.data.time.engine;

import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;

import com.xmht.lock.core.data.time.TimeLevel;
import com.xmht.lock.core.data.time.observe.SecondObserver;
import com.xmht.lock.core.data.time.observe.TimeLevelObservable;
import com.xmht.lock.core.data.time.observe.TimeLevelObserver;
import com.ysj.tools.debug.LOG;

public class TimeRaw implements TimeLevelObservable, SecondObserver {

    private Time time;

    /**
     * Seconds [0-61] (2 leap seconds allowed)
     */
    private int second;

    /**
     * Minute [0-59]
     */
    private int minute;

    /**
     * Hour of day [0-23]
     */
    private int hour;

    /**
     * Day of month [1-31]
     */
    private int day;

    /**
     * Day of week [0-6]
     */
    private int week;

    /**
     * Month [0-11]
     */
    private int month;

    /**
     * Year. For example, 1970.
     */
    private int year;

    public int getSecond() {
        return second;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getWeek() {
        return week;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public void onUpdate() {
        time.setToNow();
        if (year != time.year) {
            year = time.year;
            month = time.month;
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
            notifyObservers(TimeLevel.YEAR);
        }
        else if (month != time.month) {
            month = time.month;
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
            notifyObservers(TimeLevel.MONTH);
        }
        else if (week != time.weekDay) {
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
            notifyObservers(TimeLevel.WEEK);
        }
        else if (day != time.monthDay) {
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
            notifyObservers(TimeLevel.DAY);
        }
        else if (hour != time.hour) {
            hour = time.hour;
            minute = time.minute;
            second = time.second;
            notifyObservers(TimeLevel.HOUR);
        }
        else if (minute != time.minute) {
            minute = time.minute;
            second = time.second;
            notifyObservers(TimeLevel.MINUTE);
        }
        else if (second != time.second) {
            second = time.second;
            notifyObservers(TimeLevel.SECOND);
        }
    }

    public List<TimeLevelObserver> getObservers() {
        return observers;
    }

    private List<TimeLevelObserver> observers = new ArrayList<TimeLevelObserver>();

    @Override
    public void registerObserver(TimeLevelObserver observer) {
        LOG.v("");
        if (observer == null || observers.contains(observer)) {
            return;
        }

        observer.onTimeChanged(TimeLevel.YEAR);
        observers.add(observer);
        
        TickEngine tickEngine = TickEngine.getInstance();
        tickEngine.registerObservers(instance);
    }

    @Override
    public void unregisterObserver(TimeLevelObserver observer) {
        LOG.v("");
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(i);
        }
        
        if (observers.size() == 0) {
            TickEngine tickEngine = TickEngine.getInstance();
            tickEngine.removeObservers(instance);
        }
    }

    @Override
    public void notifyObservers(TimeLevel level) {
        for (TimeLevelObserver observer : observers) {
            observer.onTimeChanged(level);
            LOG.v(observer.getClass().getSimpleName());
        }
    }

    private static TimeRaw instance;

    private TimeRaw() {
    }

    public static TimeRaw getInstance() {
        if (instance == null) {
            synchronized (TimeRaw.class) {
                if (instance == null) {
                    init();
                }
            }
        }

        return instance;
    }

    private static void init() {
        instance = new TimeRaw();
        instance.time = new Time();
        instance.time.setToNow();
        instance.year = instance.time.year;
        instance.month = instance.time.month;
        instance.day = instance.time.monthDay;
        instance.week = instance.time.weekDay;
        instance.hour = instance.time.hour;
        instance.minute = instance.time.minute;
        instance.second = instance.time.second;
        TickEngine tickEngine = TickEngine.getInstance();
        tickEngine.registerObservers(instance);
    }

}
