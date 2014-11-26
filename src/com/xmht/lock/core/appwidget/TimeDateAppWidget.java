
package com.xmht.lock.core.appwidget;

import com.xmht.lock.core.appwidget.utils.AppWidgetUtils;

import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class TimeDateAppWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AppWidgetUtils.startUpdateWidget(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        AppWidgetUtils.stopUpdateWidget(context);
    }

}
