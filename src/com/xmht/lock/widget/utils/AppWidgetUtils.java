
package com.xmht.lock.widget.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.xmht.lock.debug.LOG;
import com.xmht.lock.widget.TimeDateAppWidget;
import com.xmht.lock.widget.activity.ConfigActivity;
import com.xmht.lock.widget.service.AppWidgetService;
import com.xmht.lockair.R;

public class AppWidgetUtils {

    public static void startUpdateWidget(Context context) {
        LOG.v("");
        Intent intent = new Intent(context, AppWidgetService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
                1000, alarmIntent);
    }

    public static void stopUpdateWidget(Context context) {
        LOG.v("");
        Intent intent = new Intent(context, AppWidgetService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmIntent);
    }

    public static void check(Context context) {
        LOG.v("");
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(context);
        ComponentName provider = new ComponentName(context,
                TimeDateAppWidget.class);
        int[] ids = appWidgetManager.getAppWidgetIds(provider);
        if (ids != null && ids.length > 0) {
            context.startService(new Intent(context, AppWidgetService.class));
            AppWidgetUtils.startUpdateWidget(context);
        }
    }

    public static void showBitmap(Context context, Bitmap bitmap) {
        LOG.v("");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        remoteViews.setImageViewBitmap(R.id.iv_time, bitmap);
        
        Intent newIntent = new Intent(context, ConfigActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                newIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_time, pendingIntent);
        
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(context);
        ComponentName provider = new ComponentName(context,
                TimeDateAppWidget.class);
        appWidgetManager.updateAppWidget(provider, remoteViews);
    };
}
