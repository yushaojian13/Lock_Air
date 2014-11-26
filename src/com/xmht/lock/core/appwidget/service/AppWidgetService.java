
package com.xmht.lock.core.appwidget.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.format.Time;

import com.xmht.lock.core.appwidget.bitmap.AppWidgetBitmap;
import com.xmht.lock.core.appwidget.bitmap.CircleAppWidgetBitmap;
import com.xmht.lock.core.appwidget.utils.AppWidgetUtils;
import com.xmht.lock.core.appwidget.utils.BitmapUtils;
import com.xmht.lock.debug.LOG;

public class AppWidgetService extends Service {

    private ScreenOnReceiver screenOnReceiver;

    private Time time;

    private static final String FILE_NAME = "widget";

    private AppWidgetBitmap appWidgetBitmap;

    @Override
    public void onCreate() {
        LOG.e("");
        super.onCreate();
        time = new Time();
        appWidgetBitmap = new CircleAppWidgetBitmap(this, time);
        showBitmapNow();
        registerReceiver();
    }

    private void registerReceiver() {
        screenOnReceiver = new ScreenOnReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenOnReceiver, filter);
    }

    private void showBitmapNow() {
        new AsyncTask<Void, Bitmap, Bitmap>() {
            protected void onPreExecute() {
                time.setToNow();
            };

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = appWidgetBitmap.create();
                LOG.v("show bitmap " + time.year + ":" + time.month + ":" + time.monthDay + ":"
                        + time.hour + ":" + time.minute + ":" + time.second);
                if (bitmap == null) {
                    LOG.v("bitmap is null");
                }
                AppWidgetUtils.showBitmap(AppWidgetService.this, bitmap);
                bitmap = null;
                genAndSaveNextBitmap();
                return bitmap;
            }
        }.execute();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onUpdate();
        return START_STICKY;
    }

    private Bitmap curBitmap;

    private void onUpdate() {
        new AsyncTask<Void, Void, Void>() {
            protected void onPreExecute() {
                time.setToNow();
            };

            @Override
            protected Void doInBackground(Void... params) {
                if (time.second == 59) {
                    LOG.v("load bitmap " + time.year + ":" + time.month + ":" + time.monthDay + ":"
                            + time.hour + ":" + time.minute + ":" + time.second);
                    curBitmap = BitmapUtils.loadCurBitmap(AppWidgetService.this, FILE_NAME);
                } else if (time.second == 0) {
                    showCurBitmap();
                    genAndSaveNextBitmap();
                }

                return null;
            }

            private void showCurBitmap() {
                LOG.v("show bitmap " + time.year + ":" + time.month + ":" + time.monthDay + ":"
                        + time.hour + ":" + time.minute + ":" + time.second);
                if (curBitmap == null) {
                    LOG.v("curBitmap is null, create it: " + time.year + ":" + time.month + ":" + time.monthDay + ":"
                            + time.hour + ":" + time.minute + ":" + time.second);
                    curBitmap = appWidgetBitmap.create();
                }

                AppWidgetUtils.showBitmap(AppWidgetService.this, curBitmap);
                curBitmap = null;
            }
        }.execute();

    }

    private void genAndSaveNextBitmap() {
        time.minute += 1;
        Bitmap bitmap = appWidgetBitmap.create();
        BitmapUtils.saveNextBitmap(AppWidgetService.this, bitmap, FILE_NAME);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterScreenReceiver();
    }

    private void unregisterScreenReceiver() {
        if (screenOnReceiver == null) {
            return;
        }

        unregisterReceiver(screenOnReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ScreenOnReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LOG.v(action);
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                showBitmapNow();
            }
        }

    }
}
