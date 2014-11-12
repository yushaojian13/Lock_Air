
package com.xmht.lock.core.view;

import com.xmht.lock.core.data.info.InfoCenter;
import com.xmht.lock.core.data.info.InfoCenter.InfoType;
import com.xmht.lock.core.data.time.observe.InfoObserver;
import com.xmht.lockair.R;

import android.app.ApplicationErrorReport.BatteryInfo;
import android.content.Context;
import android.os.BatteryManager;
import android.util.AttributeSet;

public abstract class TimeDateInfoWidget extends TimeDateWidget implements InfoObserver {
    protected int[] wifiDrawable = new int[] {
            R.drawable.wifi_level_0, R.drawable.wifi_level_1, R.drawable.wifi_level_2,
            R.drawable.wifi_level_3, R.drawable.wifi_level_4
    };
    protected int[] batteryDrawable = new int[] {
            R.drawable.icon_power_0, R.drawable.icon_power_1, R.drawable.icon_power_2,
            R.drawable.icon_power_3, R.drawable.icon_power_4, R.drawable.icon_power_5
    };
    protected int[] signalDrawable = new int[] {
            R.drawable.signal_level_0, R.drawable.signal_level_1, R.drawable.signal_level_2,
            R.drawable.signal_level_3, R.drawable.signal_level_4
    };

    public TimeDateInfoWidget(Context context) {
        this(context, null);
    }

    public TimeDateInfoWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeDateInfoWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InfoCenter.registerWifiRssiObserver(this);
    }

    // YSJ 初始化设备信息
    @Override
    public void onStart() {
        super.onStart();
        onInfoLevelChanged(InfoType.Battery, 100);
        onInfoLevelChanged(InfoType.Wifi, wifiDrawable.length - 1);
        onInfoLevelChanged(InfoType.Signal, signalDrawable.length - 1);
        InfoCenter.registerWifiRssiObserver(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        InfoCenter.unregisterWifiRssiObserver(this);
    }
}
