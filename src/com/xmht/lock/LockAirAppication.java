package com.xmht.lock;

import android.app.Application;

import com.xmht.lock.debug.LOG;
import com.xmht.lock.utils.SPHelper;
import com.xmht.lock.widget.utils.AppWidgetUtils;

public class LockAirAppication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        LOG.enableLog(true);
        SPHelper.init(this);
        AppWidgetUtils.check(this);
    }
}
