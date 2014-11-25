
package com.xmht.lock.core.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.format.Time;
import android.widget.RemoteViews;

import com.xmht.lock.core.appwidget.TimeDateAppWidget;
import com.xmht.lock.utils.Utils;
import com.xmht.lockair.R;

public class AppWidgetService extends Service {
    
    @Override
    public void onCreate() {
        super.onCreate();
        Time time = new Time();
        time.setToNow();
        String text = time.hour + ":" + time.minute;
        String font = "fonts/Helvetica-Light.ttf";
        Bitmap bitmap = createBitmap(text, font);
        showBitmap(bitmap);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onUpdate();
        return START_STICKY;
    }

    private Bitmap curBitmap;

    public void onUpdate() {
        new AsyncTask<Void, Bitmap, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                // 1. 0:0:59s load curr time bitmap form file
                // 2. 0:1:0s publishProgress(values); remoteView refer bitmap
                // 3. gen next bitmap, save to file
                // 4. currBitmap = next Bitmap

                Time time = new Time();
                long now = System.currentTimeMillis();
                time.set(now);

                if (time.second == 59) {
                    loadCurBitmap();
                } else if (time.second == 0) {
                    if (curBitmap != null) {
                        publishProgress(curBitmap);
                        curBitmap = null;
                    } else {
                        String text = time.hour + ":" + time.minute;
                        String font = "fonts/Helvetica-Light.ttf";
                        Bitmap bitmap = createBitmap(text, font);
                        publishProgress(bitmap);
                    }

                    time.set(now + 1000 * 60);
                    String text = time.hour + ":" + time.minute;
                    String font = "fonts/Helvetica-Light.ttf";
                    Bitmap bitmap = createBitmap(text, font);
                    saveNextBitmap(bitmap);
                }
                
                return null;
            }

            @Override
            protected void onProgressUpdate(Bitmap... values) {
                if (values == null || values.length == 0) {
                    return;
                }

                showBitmap(values[0]);
            }
        }.execute();

    }
    
    private Bitmap createBitmap(String text, String font) {
        Typeface typeface = Utils.createTypeface(AppWidgetService.this, font);

        Paint paint = new Paint();
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.LEFT);
        paint.setTextSize(80);
        paint.setColor(Color.WHITE);

        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);
        Bitmap bitmap = Bitmap.createBitmap(bound.width(), bound.height(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0x6600ff00);
        canvas.drawText(text, -bound.left, -bound.top, paint);
        return bitmap;
    }

    private void saveNextBitmap(Bitmap bitmap) {
        OutputStream os = null;
        try {
            os = openFileOutput("widget", MODE_PRIVATE);
            bitmap.compress(CompressFormat.PNG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadCurBitmap() {
        FileInputStream fis = null;
        try {
            fis = openFileInput("widget");
            curBitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            curBitmap = null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void showBitmap(Bitmap bitmap) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.app_widget);
        remoteViews.setImageViewBitmap(R.id.iv_time, bitmap);
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(AppWidgetService.this);
        ComponentName provider = new ComponentName(getApplicationContext(),
                TimeDateAppWidget.class);
        appWidgetManager.updateAppWidget(provider, remoteViews);
    };
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
