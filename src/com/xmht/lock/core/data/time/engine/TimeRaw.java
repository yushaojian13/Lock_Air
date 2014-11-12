
package com.xmht.lock.core.data.time.engine;

import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;

import com.xmht.lock.core.data.time.observe.SecondObserver;
import com.xmht.lock.core.data.time.observe.UpdateLevel;
import com.xmht.lock.core.data.time.observe.UpdateLevelObservable;
import com.xmht.lock.core.data.time.observe.UpdateLevelObserver;

public class TimeRaw implements UpdateLevelObservable, SecondObserver {

    private Time time;

    /**
     * Seconds [0-61] (2 leap seconds allowed)
     */
    public int second;

    /**
     * Minute [0-59]
     */
    public int minute;

    /**
     * Hour of day [0-23]
     */
    public int hour;

    /**
     * Day of month [1-31]
     */
    public int day;

    /**
     * Day of week [0-6]
     */
    public int week;

    /**
     * Month [0-11]
     */
    public int month;

    /**
     * Year. For example, 1970.
     */
    public int year;

    @Override
    public void onUpdate() {
        time.setToNow();
        if (year != time.year) {
            notifyObservers(UpdateLevel.YEAR);
            year = time.year;
            month = time.month;
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (month != time.month) {
            notifyObservers(UpdateLevel.MONTH);
            month = time.month;
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (week != time.weekDay) {
            notifyObservers(UpdateLevel.WEEK);
            week = time.weekDay;
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (day != time.monthDay) {
            notifyObservers(UpdateLevel.DAY);
            day = time.monthDay;
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (hour != time.hour) {
            notifyObservers(UpdateLevel.HOUR);
            hour = time.hour;
            minute = time.minute;
            second = time.second;
        }
        else if (minute != time.minute) {
            notifyObservers(UpdateLevel.MINUTE);
            minute = time.minute;
            second = time.second;
        }
        else if (second != time.second) {
            notifyObservers(UpdateLevel.SECOND);
            second = time.second;
        }
    }

    private List<UpdateLevelObserver> observers = new ArrayList<UpdateLevelObserver>();

    @Override
    public void registerObserver(UpdateLevelObserver observer) {
        if (observers.contains(observer)) {
            return;
        }
        
        observer.onUpdate(UpdateLevel.YEAR);
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(UpdateLevelObserver observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers(UpdateLevel level) {
        for (UpdateLevelObserver observer : observers) {
            observer.onUpdate(level);
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
