
package com.xmht.lock.core.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.xmht.lock.core.receiver.ScreenReceiver;

public class LockService extends Service {
    private ScreenReceiver screenReceiver;

    private static final int UPDATE_TIME = 1000;
    public static final String ACTION_TICK = "com.xmht.lock.air.tick";

    private UpdateThread updateThread;

    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenReceiver();
    }

    private void startTick() {
        if (updateThread == null) {
            updateThread = new UpdateThread();
            updateThread.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra("app_widget")) {
            boolean start = intent.getBooleanExtra("app_widget", false);
            if (start) {
                startTick();
            } else {
                stopTick();
            }
        }
        return START_STICKY;
    }

    private void stopTick() {
        if (updateThread != null) {
            updateThread.interrupt();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerScreenReceiver() {
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
        unregisterReceiver(screenReceiver);
        Log.v("LockAir", "unregisterScreenReceiver");
    }

    private class UpdateThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                while (true) {
                    Intent updateIntent = new Intent(ACTION_TICK);
                    sendBroadcast(updateIntent);
                    Thread.sleep(UPDATE_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
