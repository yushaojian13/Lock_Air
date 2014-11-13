package com.xmht.lock.core;

import com.xmht.lock.core.utils.SPHelper;

import android.app.Application;

public class LockAirAppication extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        SPHelper.init(this);
    }
}
