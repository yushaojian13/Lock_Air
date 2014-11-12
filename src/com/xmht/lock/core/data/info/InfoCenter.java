
package com.xmht.lock.core.data.info;

import java.util.ArrayList;
import java.util.List;

import android.net.wifi.WifiManager;

import com.xmht.lock.core.data.time.observe.InfoObserver;

public class InfoCenter {
    private static List<InfoObserver> wifiRssiObservers;
    
    static {
        wifiRssiObservers = new ArrayList<InfoObserver>();
    }
    
    public static void registerWifiRssiObserver(InfoObserver observer) {
        if (observer!= null && !wifiRssiObservers.contains(observer)) {
            wifiRssiObservers.add(observer);
        }
    }
    
    public static void unregisterWifiRssiObserver(InfoObserver observer) {
        if (wifiRssiObservers.contains(observer)) {
            wifiRssiObservers.remove(wifiRssiObservers.indexOf(observer));
        }
    }
    
    public static void notifyWifiRssiObservers(InfoType infoType, int level) {
        for (InfoObserver observer: wifiRssiObservers) {
            observer.onInfoLevelChanged(infoType, level);
        }
    }

    private static int wifiLevel;
    private static int batteryLevel;
    private static int signalLevel;
    
    public static void set(InfoType infoType, int level) {
        switch (infoType) {
            case Wifi:
                int curLevel = WifiManager.calculateSignalLevel(level, InfoObserver.MAX_LEVEL);
                if (wifiLevel != curLevel) {
                    wifiLevel = curLevel;
                    notifyWifiRssiObservers(InfoType.Wifi, wifiLevel);
                }
                break;
            case Battery:
                if (batteryLevel != level) {
                    batteryLevel = level;
                    notifyWifiRssiObservers(InfoType.Battery, batteryLevel);
                }
                break;
            case Signal:
                if (signalLevel != level) {
                    signalLevel = level;
                    notifyWifiRssiObservers(InfoType.Signal, signalLevel);
                }
                break;
            default:
                break;
        }
    }
    
    public enum InfoType {
        Wifi, Battery, Signal
    }

}
