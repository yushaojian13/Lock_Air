package com.xmht.lock;

import android.app.Application;

import com.xmht.lock.widget.utils.AppWidgetUtils;
import com.ysj.tools.debug.LOG;
import com.ysj.tools.utils.SPHelper;

public class LockAirAppication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        LOG.enableLog(true);
        SPHelper.init(this);
        AppWidgetUtils.check(this);
    }
}
