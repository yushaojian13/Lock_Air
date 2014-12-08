
package com.xmht.lock.widget.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.xmht.lock.widget.TimeDateAppWidget;
import com.xmht.lock.widget.TimeDateAppWidgetSmall;
import com.xmht.lock.widget.activity.ConfigActivity;
import com.xmht.lock.widget.service.AppWidgetService;
import com.xmht.lockair.R;
import com.ysj.tools.debug.LOG;

public class AppWidgetUtils {

    public static void onceUpdateWidget(Context context) {
        LOG.v("");
        Intent intentNow = new Intent(context, AppWidgetService.class);
        intentNow.putExtra(AppWidgetService.EXTRA_UPDATE_DELAY_NOT, true);
        context.startService(intentNow);
    }

    public static void repeateUpdateWidget(Context context) {
        LOG.v("");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AppWidgetService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
                1000, alarmIntent);
    }

    public static void stopUpdateWidget(Context context) {
        LOG.v("");
        Intent intent = new Intent(context, AppWidgetService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmIntent);
        context.stopService(new Intent(context, AppWidgetService.class));
    }

    public static void check(Context context) {
        LOG.v("");
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(context);
        ComponentName provider = new ComponentName(context,
                TimeDateAppWidget.class);
        int[] ids = appWidgetManager.getAppWidgetIds(provider);
        if (ids != null && ids.length > 0) {
            LOG.v("check have");
            context.startService(new Intent(context, AppWidgetService.class));
            AppWidgetUtils.repeateUpdateWidget(context);
        } else {
            LOG.v("check don't have");
        }
    }

    public static void showBitmap(Context context, Bitmap bitmap) {
        LOG.v("");
        if (bitmap == null) {
            return;
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget_clock);
        remoteViews.setImageViewBitmap(R.id.iv_time, bitmap);

        RemoteViews remoteViewsSmall = new RemoteViews(context.getPackageName(),
                R.layout.app_widget_clock);
        remoteViewsSmall.setImageViewBitmap(R.id.iv_time, bitmap);

        Intent newIntent = new Intent(context, ConfigActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                newIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_time, pendingIntent);
        remoteViewsSmall.setOnClickPendingIntent(R.id.iv_time, pendingIntent);

        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(context);
        ComponentName provider = new ComponentName(context,
                TimeDateAppWidget.class);
        ComponentName providerSmall = new ComponentName(context,
                TimeDateAppWidgetSmall.class);
        appWidgetManager.updateAppWidget(provider, remoteViews);
        appWidgetManager.updateAppWidget(providerSmall, remoteViewsSmall);
    };

    public static boolean checkExist(Context context, Class<? extends BroadcastReceiver> cls) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName provider = new ComponentName(context, cls);
        int[] ids = appWidgetManager.getAppWidgetIds(provider);
        if (ids != null && ids.length > 0) {
            return true;
        }

        return false;
    }

}
