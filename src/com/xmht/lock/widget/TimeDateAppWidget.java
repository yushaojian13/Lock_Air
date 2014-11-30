
package com.xmht.lock.widget;

import com.xmht.lock.debug.LOG;
import com.xmht.lock.widget.utils.AppWidgetUtils;

import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class TimeDateAppWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        LOG.v("");
        super.onEnabled(context);
        AppWidgetUtils.startUpdateWidget(context);
    }

    @Override
    public void onDisabled(Context context) {
        LOG.v("");
        super.onDisabled(context);
        AppWidgetUtils.stopUpdateWidget(context);
    }

}
