
package com.xmht.lock.core.data.time.engine;

import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;

import com.xmht.lock.core.data.time.TimeLevel;
import com.xmht.lock.core.data.time.observe.SecondObserver;
import com.xmht.lock.core.data.time.observe.TimeLevelObservable;
import com.xmht.lock.core.data.time.observe.TimeLevelObserver;

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
            notifyObservers(TimeLevel.YEAR);
            year = time.year;
            month = time.month;
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (month != time.month) {
            notifyObservers(TimeLevel.MONTH);
            month = time.month;
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (week != time.weekDay) {
            notifyObservers(TimeLevel.WEEK);
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (day != time.monthDay) {
            notifyObservers(TimeLevel.DAY);
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (hour != time.hour) {
            notifyObservers(TimeLevel.HOUR);
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (minute != time.minute) {
            notifyObservers(TimeLevel.MINUTE);
            minute = time.minute;
            second = time.second;
        }
        else if (second != time.second) {
            notifyObservers(TimeLevel.SECOND);
            second = time.second;
        }
    }

    public List<TimeLevelObserver> getObservers() {
        return observers;
    }

    private List<TimeLevelObserver> observers = new ArrayList<TimeLevelObserver>();

    @Override
    public void registerObserver(TimeLevelObserver observer) {
        if (observers.contains(observer)) {
            return;
        }

        observer.onTimeChanged(TimeLevel.YEAR);
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(TimeLevelObserver observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers(TimeLevel level) {
        for (TimeLevelObserver observer : observers) {
            observer.onTimeChanged(level);
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

        SecondEngine timeEngine = new SecondEngine();
        timeEngine.registerObservers(instance);
        timeEngine.start();
    }

}
