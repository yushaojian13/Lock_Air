
package com.xmht.lock.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.xmht.lock.core.activity.LockActivity;
import com.xmht.lock.core.data.info.InfoCenter;
import com.xmht.lock.core.data.info.InfoCenter.InfoType;
import com.xmht.lock.core.debug.LOG;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LOG.e(action);
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            onScreenOff(context);
        } else if (action.equals(Intent.ACTION_USER_PRESENT)
                || action.equals(Intent.ACTION_SCREEN_ON)) {
        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            onScreenOff(context);
        }
//        else if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
//            int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//            onBatteryChanged(rawlevel);
//        } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
//            NetworkInfo info = intent
//                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//            onWifiStateChanged(info);
//        } else if (action.equals(WifiManager.RSSI_CHANGED_ACTION)) {
//            onRssiChanged(context);
//        } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
//        }
    }

    private void onScreenOff(Context context) {
        Intent newIntent = new Intent(context, LockActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }

    private void onBatteryChanged(int level) {
        InfoCenter.set(InfoType.Battery, level);
    }

    private void onWifiStateChanged(NetworkInfo info) {
        switch (info.getState()) {
            case CONNECTING:
                break;
            case CONNECTED:
                break;
            case SUSPENDED:
                break;
            case DISCONNECTING:
                break;
            case DISCONNECTED:
                InfoCenter.set(InfoType.Wifi, -100);
                break;
            case UNKNOWN:
                break;
            default:
                break;
        }
    }

    private void onRssiChanged(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int rssi = wifiInfo.getRssi();
        InfoCenter.set(InfoType.Wifi, rssi);
    }

}
