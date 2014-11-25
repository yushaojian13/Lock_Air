package com.xmht.lock.core;

import android.app.Application;

import com.xmht.lock.core.appwidget.AppWidgetUtils;
import com.xmht.lock.debug.LOG;
import com.xmht.lock.utils.SPHelper;

public class LockAirAppication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        LOG.enableLog(true);
        SPHelper.init(this);
        AppWidgetUtils.check(this);
    }
}
