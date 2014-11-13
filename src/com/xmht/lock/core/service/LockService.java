
package com.xmht.lock.core.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.xmht.lock.core.receiver.ScreenReceiver;

public class LockService extends Service {
    private ScreenReceiver screenReceiver;
//    private TelephonyManager telephonyManager;
//    private SignalListener signalListener;

    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
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
//        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(screenReceiver, filter);
//
//        signalListener = new SignalListener();
//        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(signalListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        Log.e("LockAir", "registerScreenReceiver");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenReceiver);
//        telephonyManager.listen(signalListener, PhoneStateListener.LISTEN_NONE);
        Log.e("LockAir", "unregisterScreenReceiver");
    }

}
