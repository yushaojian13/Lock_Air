
package com.xmht.lock.core.appwidget;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.xmht.lock.core.service.AppWidgetService;

public class TimeDateAppWidget2 extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        context.startService(new Intent(context, AppWidgetService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.startService(new Intent(context, AppWidgetService.class));
    }

}
