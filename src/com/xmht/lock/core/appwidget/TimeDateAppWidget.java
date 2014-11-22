package com.xmht.lock.core.appwidget;


import java.util.HashSet;
import java.util.Set;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.xmht.lock.core.data.time.format.TimeFormatter;
import com.xmht.lock.core.service.LockService;
import com.xmht.lock.debug.LOG;
import com.xmht.lockair.R;

public class TimeDateAppWidget extends AppWidgetProvider {
    private static Set<Integer> idSet = new HashSet<Integer>();
    private static int count;
    
    public TimeDateAppWidget() {
        super();
        count++;
        LOG.e("instantiated " + count + " times");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        LOG.v(action);
        if (LockService.ACTION_TICK.equals(action)) {
            update(context); 
        }
    }

    private void update(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_time_date_3);
        remoteViews.setTextViewText(R.id.date, TimeFormatter.getDate(false, false, " "));
        remoteViews.setTextViewText(R.id.week, TimeFormatter.getWeek(true, false));
        remoteViews.setTextViewText(R.id.time_am_pm, TimeFormatter.getAM(true));
        remoteViews.setTextViewText(R.id.time_h_m, TimeFormatter.getTime(false, false, ":"));
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        
        for (int appID : idSet) {
            appWidgetManager.updateAppWidget(appID, remoteViews);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds) {
            idSet.add(i);
            LOG.v(i);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for (int i : appWidgetIds) {
            idSet.remove(i);
            LOG.v(i);
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        LOG.v("");
        Intent intent = new Intent(context, LockService.class);
        intent.putExtra("app_widget", true);
        context.startService(intent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        LOG.v("");
        Intent intent = new Intent(context, LockService.class);
        intent.putExtra("app_widget", false);
        context.startService(intent);
    }
    
}
