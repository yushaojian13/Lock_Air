package com.xmht.lock.core.activity;

import com.xmht.lockair.R;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WidgetClockConfigActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_clock_config);
        TextView textView = (TextView) findViewById(R.id.tv_clock_id);
        Intent intent = getIntent();
        int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        textView.setText("widget id = " + id);
    }
}
