
package com.xmht.lock.core.appwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.widget.RemoteViews;

import com.xmht.lockair.R;

public class TimeDateAppWidget extends AppWidgetProvider {
    private static final String ACTION_UPDATE = "com.xmht.lock.air.tick";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (ACTION_UPDATE.equals(action)) {
            update(context);
        }
    }
    
    private void update(Context context) {
        Time time = new Time();
        time.setToNow();
        
        ComponentName provider = new ComponentName(
                context.getApplicationContext(), TimeDateAppWidget.class);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_time_date_1);
        remoteViews.setTextViewText(R.id.date, time.month + " " + time.monthDay);
        remoteViews.setTextViewText(R.id.week, time.weekDay + " ");
        remoteViews.setTextViewText(R.id.time, time.hour + ":" + time.minute + ":" + time.second);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        
        appWidgetManager.updateAppWidget(provider, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent intent = new Intent(ACTION_UPDATE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                1000, alarmIntent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Intent intent = new Intent(ACTION_UPDATE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmIntent);
    }

}
