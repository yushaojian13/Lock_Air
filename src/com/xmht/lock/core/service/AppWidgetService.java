
package com.xmht.lock.core.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.xmht.lock.core.appwidget.TimeDateAppWidget2;
import com.xmht.lock.core.data.time.engine.TickEngine;
import com.xmht.lock.core.data.time.format.TimeFormatter;
import com.xmht.lock.core.data.time.observe.SecondObserver;
import com.xmht.lock.debug.LOG;
import com.xmht.lockair.R;

public class AppWidgetService extends Service implements SecondObserver {
    private TickEngine tickEngine;

    @Override
    public void onCreate() {
        super.onCreate();
        startTick();
        LOG.v("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTick();
        LOG.v("");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startTick() {
        LOG.v("");
        if (tickEngine != null) {
            return;
        }

        tickEngine = TickEngine.getInstance();
        tickEngine.registerObservers(this);
    }

    private void stopTick() {
        LOG.v("");
        if (tickEngine == null) {
            return;
        }

        tickEngine.removeObservers(this);
    }

    @Override
    public void onUpdate() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_time_date_3);
        remoteViews.setTextViewText(R.id.date, TimeFormatter.getDate(false, false, " "));
        remoteViews.setTextViewText(R.id.week, TimeFormatter.getWeek(false, false));
        remoteViews.setTextViewText(R.id.time, TimeFormatter.getTime(false, true, ":"));
        remoteViews.setTextViewText(R.id.am_pm, TimeFormatter.getAM(true));
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        ComponentName provider = new ComponentName(getApplicationContext(), TimeDateAppWidget2.class);
        appWidgetManager.updateAppWidget(provider, remoteViews);
    }

}
