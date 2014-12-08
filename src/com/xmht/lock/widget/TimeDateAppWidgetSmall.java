
package com.xmht.lock.widget;

import android.appwidget.AppWidgetProvider;
import android.content.Context;

import com.xmht.lock.widget.utils.AppWidgetUtils;
import com.ysj.tools.debug.LOG;

public class TimeDateAppWidgetSmall extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {
        LOG.v("");
        super.onEnabled(context);
        if (AppWidgetUtils.checkExist(context, TimeDateAppWidget.class)) {
            AppWidgetUtils.onceUpdateWidget(context);
            return;
        }
        
        AppWidgetUtils.repeateUpdateWidget(context);
    }

    @Override
    public void onDisabled(Context context) {
        LOG.v("");
        super.onDisabled(context);
        if (AppWidgetUtils.checkExist(context, TimeDateAppWidget.class)) {
            return;
        }
        
        AppWidgetUtils.stopUpdateWidget(context);
    }

}
