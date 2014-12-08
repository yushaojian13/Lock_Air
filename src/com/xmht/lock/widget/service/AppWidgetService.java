
package com.xmht.lock.widget.service;

import java.io.IOException;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;

import com.xmht.lock.widget.bitmap.AppWidgetBitmap;
import com.xmht.lock.widget.bitmap.CircleAppWidgetBitmap;
import com.xmht.lock.widget.bitmap.DigitalAppWidgetBitmap;
import com.xmht.lock.widget.bitmap.LayerAppWidgetBitmap;
import com.xmht.lock.widget.bitmap.NewAppWidgetBitmap;
import com.xmht.lock.widget.utils.AppWidgetUtils;
import com.xmht.lock.widget.utils.BitmapUtils;
import com.xmht.lockair.R;
import com.ysj.tools.debug.LOG;
import com.ysj.tools.utils.SPHelper;

public class AppWidgetService extends Service {

    private ScreenOnReceiver screenOnReceiver;

    private Time time;

    private static final String FILE_NAME = "widget";

    private AppWidgetBitmap curAppWidgetBitmap;

    public static final String EXTRA_UPDATE_DELAY_NOT = "update_delay";

    public static final String EXTRA_WIDGET_STYLE = "widget_style";
    public static final String EXTRA_WIDGET_SHADOW = "widget_shadow";
    public static final String EXTRA_WIDGET_COLORS = "widget_colors";
    public static final String EXTRA_WIDGET_SCALE = "widget_scale";
    public static final String EXTRA_WIDGET_FONT = "widget_font";

    public static final String SP_WIDGET_STYLE = "sp_widget_style";

    @Override
    public void onCreate() {
        LOG.e("");
        super.onCreate();
        time = new Time();
        String style = SPHelper.get(SP_WIDGET_STYLE, getString(R.string.widget_style_digit));
        curAppWidgetBitmap = getByStyle(style);
        onUpdate(false);
        registerReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean delayNot = false;

        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();

            boolean hasStyle = extras.containsKey(EXTRA_WIDGET_STYLE);
            boolean hasFont = extras.containsKey(EXTRA_WIDGET_FONT);
            boolean hasColors = extras.containsKey(EXTRA_WIDGET_COLORS);
            boolean hasShadow = extras.containsKey(EXTRA_WIDGET_SHADOW);

            delayNot = hasStyle && hasShadow && hasColors && hasFont;
            if (delayNot) {
                AppWidgetBitmap newAppWidgetBitmap = getStyle(extras);
                getFont(newAppWidgetBitmap, extras);
                getColors(newAppWidgetBitmap, extras);
                getShadow(newAppWidgetBitmap, extras);
                String style = extras.getString(EXTRA_WIDGET_STYLE, null);
                if (newAppWidgetBitmap != null && style != null) {
                    curAppWidgetBitmap = newAppWidgetBitmap;
                    SPHelper.put(SP_WIDGET_STYLE, style);
                    onUpdate(false);
                }
            } else {
                delayNot = extras.getBoolean(EXTRA_UPDATE_DELAY_NOT);
            }
        }

        onUpdate(!delayNot);

        return START_STICKY;
    }

    private AppWidgetBitmap getStyle(Bundle extras) {
        String style = extras.getString(EXTRA_WIDGET_STYLE, null);
        return getByStyle(style);
    }

    private AppWidgetBitmap getByStyle(String style) {
        String digitStyle = getString(R.string.widget_style_digit);
        String newStyle = getString(R.string.widget_style_new);
        String cicleStyle = getString(R.string.widget_style_cicle);
        String layerStyle = getString(R.string.widget_style_layer);

        if (digitStyle.equals(style)) {
            return new DigitalAppWidgetBitmap(this, time);
        } else if (newStyle.equals(style)) {
            return new NewAppWidgetBitmap(this, time);
        } else if (cicleStyle.equals(style)) {
            return new CircleAppWidgetBitmap(this, time);
        } else if (layerStyle.equals(style)) {
            return new LayerAppWidgetBitmap(this, time);
        }

        return null;

    }

    private void getFont(AppWidgetBitmap appWidgetBitmap, Bundle extras) {
        if (appWidgetBitmap == null) {
            return;
        }

        String font = extras.getString(EXTRA_WIDGET_FONT, null);
        if (font == null) {
            font = "fonts/Helvetica-Light.ttf";
        }

        appWidgetBitmap.setFont(font);
    }

    private void getColors(AppWidgetBitmap appWidgetBitmap, Bundle extras) {
        if (appWidgetBitmap == null) {
            return;
        }

        int[] colors = extras.getIntArray(EXTRA_WIDGET_COLORS);
        appWidgetBitmap.setColors(colors);
    }

    private void getShadow(AppWidgetBitmap appWidgetBitmap, Bundle extras) {
        if (appWidgetBitmap == null) {
            return;
        }

        boolean shadowOn = extras.getBoolean(EXTRA_WIDGET_SHADOW, false);
        appWidgetBitmap.showShadow(shadowOn);
    }

    private Bitmap curBitmap;

    private void onUpdate(final boolean delay) {
        new AsyncTask<Void, Void, Void>() {
            protected void onPreExecute() {
                time.setToNow();
            };

            @Override
            protected Void doInBackground(Void... params) {
                if (!delay) {
                    curBitmap = curAppWidgetBitmap.create();
                    publishProgress();
                    genAndSaveNextBitmap();
                    return null;
                }

                if (time.second == 58) {
                    LOG.v("load bitmap " + time.year + ":" + time.month + ":" + time.monthDay + ":"
                            + time.hour + ":" + time.minute + ":" + time.second);
                    try {
                        curBitmap = BitmapUtils.loadCurBitmap(AppWidgetService.this, FILE_NAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Time tmpTime = new Time();
                        tmpTime.set(time.toMillis(false) + 2 * 1000);
                        curAppWidgetBitmap.setTime(tmpTime);
                        curBitmap = curAppWidgetBitmap.create();
                        curAppWidgetBitmap.setTime(time);
                    }
                } else if (time.second == 0) {
                    LOG.v("publish bitmap " + time.year + ":" + time.month + ":" + time.monthDay
                            + ":"
                            + time.hour + ":" + time.minute + ":" + time.second);
                    publishProgress();
                    genAndSaveNextBitmap();
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                LOG.v("show bitmap " + time.year + ":" + time.month + ":" + time.monthDay + ":"
                        + time.hour + ":" + time.minute + ":" + time.second);
                if (curBitmap == null) {
                    LOG.v("curBitmap is null, create it now! " + time.year + ":" + time.month + ":"
                            + time.monthDay + ":"
                            + time.hour + ":" + time.minute + ":" + time.second);
                    curBitmap = curAppWidgetBitmap.create();
                }
                AppWidgetUtils.showBitmap(AppWidgetService.this, curBitmap);
                curBitmap = null;
            };
        }.execute();

    }

    private void genAndSaveNextBitmap() {
        LOG.v("");
        Time tmpTime = new Time();
        tmpTime.set(time.toMillis(false) + 60 * 1000);
        curAppWidgetBitmap.setTime(tmpTime);
        Bitmap bitmap = curAppWidgetBitmap.create();
        curAppWidgetBitmap.setTime(time);
        BitmapUtils.saveNextBitmap(AppWidgetService.this, bitmap, FILE_NAME);
    }

    @Override
    public void onDestroy() {
        LOG.e("");
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

    private void registerReceiver() {
        screenOnReceiver = new ScreenOnReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenOnReceiver, filter);
    }

    private class ScreenOnReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LOG.v(action);
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                onUpdate(false);
            }
        }

    }
}
