
package com.xmht.lock.core.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.xmht.lock.core.data.time.engine.TickEngine;
import com.xmht.lock.core.data.time.observe.SecondObserver;
import com.xmht.lock.core.receiver.ScreenReceiver;
import com.xmht.lock.debug.LOG;

public class LockService extends Service implements SecondObserver {
    private ScreenReceiver screenReceiver;
    private TickEngine tickEngine;

    public static final String EXTRA_RECEIVER = "extra_receiver";
    public static final String EXTRA_TICK = "extra_tick";
    
    public static final String ACTION_TICK = "com.xmht.lock.air.tick";
    
    public static void action(Context context, boolean startReceiver, boolean startTick) {
        Intent intent = new Intent(context, LockService.class);
        intent.putExtra(LockService.EXTRA_RECEIVER, startReceiver);
        intent.putExtra(LockService.EXTRA_TICK, startTick);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LOG.v("");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean startReceiver = intent.hasExtra(EXTRA_RECEIVER) && intent.getBooleanExtra(EXTRA_RECEIVER, false);
        
        if (startReceiver) {
            registerScreenReceiver();
        } else {
            unregisterReceiver(screenReceiver);
        }
        
        boolean startTick = intent.hasExtra(EXTRA_TICK) && intent.getBooleanExtra(EXTRA_TICK, false);
        if (startTick) {
            startTick();
        } else {
            stopTick();
        }
        
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerScreenReceiver() {
        if (screenReceiver != null) {
            return;
        }
        
        screenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenReceiver, filter);
        Log.v("LockAir", "registerScreenReceiver");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTick();
        unregisterScreenReceiver();
        LOG.v("");
    }

    private void unregisterScreenReceiver() {
        if (screenReceiver == null) {
            return;
        }
        
        unregisterReceiver(screenReceiver);
        Log.v("LockAir", "unregisterScreenReceiver");
    }

    private void startTick() {
        if (tickEngine != null) {
            return;
        }
        
        tickEngine = TickEngine.getInstance();
        tickEngine.registerObservers(this);
        tickEngine.start();
    }
    
    private void stopTick() {
        if (tickEngine == null) {
            return;
        }
        
        tickEngine.stop();
        tickEngine.removeObservers(this);
    }

    @Override
    public void onUpdate() {
        sendBroadcast(new Intent(ACTION_TICK));
    }

}
