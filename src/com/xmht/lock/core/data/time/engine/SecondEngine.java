
package com.xmht.lock.core.data.time.engine;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xmht.lock.core.data.time.observe.SecondObservable;
import com.xmht.lock.core.data.time.observe.SecondObserver;
import com.xmht.lock.debug.LOG;

public class SecondEngine extends Handler implements SecondObservable {
    private static final int START = 0;
    private static int count;

    private List<SecondObserver> observers = new ArrayList<SecondObserver>();

    private boolean refresh;

    public SecondEngine() {
        super();
        init();
    }

    public SecondEngine(Callback callback) {
        super(callback);
        init();
    }
    
    public SecondEngine(Looper looper) {
        super(looper);
        init();
    }

    public SecondEngine(Looper looper, Callback callback) {
        super(looper, callback);
        init();
    }
    
    private void init() {
        count++;
        LOG.e("instantiated " + count + " times");
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        if (msg.what == START && refresh) {
            notifyObservers();
            sendEmptyMessageDelayed(START, 1000);
        }
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

    public void start() {
        if (refresh) {
            return;
        }

        refresh = true;
        sendEmptyMessage(START);
    }

    public void stop() {
        refresh = false;
    }
}
