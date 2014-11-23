package com.xmht.lock.core.data.time.engine;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.xmht.lock.core.data.time.observe.SecondObservable;
import com.xmht.lock.core.data.time.observe.SecondObserver;

public class TickEngine implements SecondObservable {
    
    private static final int START = 0;

    private List<SecondObserver> observers = new ArrayList<SecondObserver>();

    private boolean refresh;
    
    private Handler handler;
    
    private static TickEngine instance;
    
    private TickEngine(){}
    
    public static TickEngine getInstance() {
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
        instance = new TickEngine();
        instance.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == START && instance.refresh) {
                    instance.notifyObservers();
                    sendEmptyMessageDelayed(START, 1000);
                }
            }
        };
    }

    public void start() {
        if (refresh) {
            return;
        }

        refresh = true;
        handler.sendEmptyMessage(START);
    }

    public void stop() {
        refresh = false;
    }
    
    @Override
    public void registerObservers(SecondObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObservers(SecondObserver observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (SecondObserver observer : observers) {
            observer.onUpdate();
        }
    }

}
