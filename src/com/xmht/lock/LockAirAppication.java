
package com.xmht.lock;

import android.app.Application;

import com.xmht.lock.widget.utils.AppWidgetUtils;
import com.ysj.tools.debug.LOG;

public class LockAirAppication extends Application {
    public static final String SP_TAG = "LockAir";

    @Override
    public void onCreate() {
        super.onCreate();
        LOG.enableLog(true);
        AppWidgetUtils.check(this);
    }
}
