
package com.xmht.lock.core.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.format.Time;

import com.xmht.lock.core.appwidget.AppWidgetUtils;
import com.xmht.lock.core.appwidget.BitmapUtils;
import com.xmht.lock.core.appwidget.TimeFormatter;
import com.xmht.lock.debug.LOG;

public class AppWidgetService extends Service {

    private Time time;
    
    private static final String FILE_NAME = "widget";

    @Override
    public void onCreate() {
        super.onCreate();
        time = new Time();
        showBitmapNow();
    }

    private void showBitmapNow() {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                time.setToNow();
                String text = TimeFormatter.getTime(time, true, false, ":");
                String font = "fonts/UnidreamLED.ttf";
                Bitmap bitmap = BitmapUtils.createBitmap(AppWidgetService.this, text, font);
                return bitmap;
            }

            protected void onPostExecute(Bitmap result) {
                AppWidgetUtils.showBitmap(AppWidgetService.this, result);
            };
        }.execute();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onUpdate();
        return START_STICKY;
    }

    private Bitmap curBitmap;

    private void onUpdate() {
        LOG.v("");
        new AsyncTask<Void, Void, Void>() {
            long now = System.currentTimeMillis();

            protected void onPreExecute() {
                time.set(now);
            };

            @Override
            protected Void doInBackground(Void... params) {
                if (time.second == 59) {
                    curBitmap = BitmapUtils.loadCurBitmap(AppWidgetService.this, FILE_NAME);
                } else if (time.second == 0) {
                    showCurBitmap();
                    genAndSaveNextBitmap();
                }

                return null;
            }

            private void showCurBitmap() {
                if (curBitmap == null) {
                    String text = TimeFormatter.getTime(time, true, false, ":");
                    String font = "fonts/UnidreamLED.ttf";
                    curBitmap = BitmapUtils.createBitmap(AppWidgetService.this, text, font);
                }

                AppWidgetUtils.showBitmap(AppWidgetService.this, curBitmap);
                curBitmap = null;
            }

            private void genAndSaveNextBitmap() {
                time.set(now + 1000 * 60);
                String text = TimeFormatter.getTime(time, true, false, ":");
                String font = "fonts/UnidreamLED.ttf";
                Bitmap bitmap = BitmapUtils.createBitmap(AppWidgetService.this, text, font);
                BitmapUtils.saveNextBitmap(AppWidgetService.this, bitmap, FILE_NAME);
            }
        }.execute();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
