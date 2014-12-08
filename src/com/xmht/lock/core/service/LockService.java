
package com.xmht.lock.core.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.xmht.lock.core.receiver.ScreenReceiver;
import com.ysj.tools.debug.LOG;

public class LockService extends Service {
    private ScreenReceiver screenReceiver;

    @Override
    public void onCreate() {
        LOG.v("");
        super.onCreate();
        registerScreenReceiver();
    }
    
    @Override
    public void onDestroy() {
        LOG.v("");
        super.onDestroy();
        unregisterScreenReceiver();
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

    private void unregisterScreenReceiver() {
        if (screenReceiver == null) {
            return;
        }

        unregisterReceiver(screenReceiver);
        Log.v("LockAir", "unregisterScreenReceiver");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
