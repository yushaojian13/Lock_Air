
package com.xmht.lock.core.data.time.engine;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.xmht.lock.core.data.time.observe.SecondObservable;
import com.xmht.lock.core.data.time.observe.SecondObserver;
import com.xmht.lock.debug.LOG;

public class TickEngine implements SecondObservable {

    private static final int START = 0;

    private List<SecondObserver> observers = new ArrayList<SecondObserver>();

    private boolean refresh;

    private Handler handler;

    private static TickEngine instance;

    private TickEngine() {
    }

    public static TickEngine getInstance() {
        if (instance == null) {
            synchronized (TimeRaw.class) {
                if (instance == null) {
                    instance = new TickEngine();
                }
            }
        }

        return instance;
    }

    private void start() {
        if (refresh) {
            return;
        }

        refresh = true;
        handler = new TickHandler();
        handler.sendEmptyMessage(START);
    }
    
    private static class TickHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == START && instance.refresh) {
                instance.notifyObservers();
                sendEmptyMessageDelayed(START, 1000);
                LOG.v("Tick");
            }
        }
    };

    private void stop() {
        refresh = false;
        handler = null;
    }

    @Override
    public void registerObservers(SecondObserver observer) {
        if (observer == null || observers.contains(observer)) {
            return;
        }

        observers.add(observer);
        start();
    }

    @Override
    public void removeObservers(SecondObserver observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(i);
        }
        if (observers.size() == 0) {
            stop();
        }
    }

    @Override
    public void notifyObservers() {
        for (SecondObserver observer : observers) {
            observer.onUpdate();
            LOG.v(observer.getClass().getSimpleName());
        }
    }

}
